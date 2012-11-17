package hu.documaison.gui.commentstags;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Tag;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.exceptions.UnknownTagException;
import hu.documaison.gui.NotifactionWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddTagDialog {

	private Button existingRadio;
	private Button newRadio;
	private Combo existingCombo;
	private Text newTagName;
	private Combo colorCombo;
	private Button saveBtn;
	private Document doc;
	private Shell dialog;

	public void showAndHandle(Shell parent, final Document document) {
		try {
			doc = Application.getBll().getDocument(document.getId());
		} catch (UnknownDocumentException e) {
			doc = document;
			NotifactionWindow.showError("Database error",
					"Failed to update the document from the database.");
		}
		dialog = new Shell(parent, SWT.APPLICATION_MODAL | SWT.BORDER
				| SWT.CLOSE | SWT.TITLE);
		dialog.setText("Add new tag");
		dialog.setSize(500, 150);
		dialog.setLayout(new FillLayout());

		Composite composite = new Composite(dialog, SWT.NONE);
		composite.setLayout(new FormLayout());

		existingRadio = new Button(composite, SWT.RADIO);
		existingRadio.setText("Existing tag:");
		newRadio = new Button(composite, SWT.RADIO);
		newRadio.setText("New tag:");

		FormData data = new FormData();
		data.top = new FormAttachment(0, 15);
		data.left = new FormAttachment(0, 10);
		existingRadio.setLayoutData(data);
		existingRadio.setSelection(true);
		data = new FormData();
		data.top = new FormAttachment(existingRadio, 5);
		data.left = new FormAttachment(0, 10);
		newRadio.setLayoutData(data);
		newRadio.setSelection(false);

		existingCombo = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY
				| SWT.DROP_DOWN);
		for (Tag tag : Application.getBll().getTags()) {
			if (tag.getName() != null) {
				existingCombo.add(tag.getName());
			}
		}
		data = new FormData();
		data.top = new FormAttachment(existingRadio, 0, SWT.CENTER);
		data.left = new FormAttachment(existingRadio, 10);
		existingCombo.setLayoutData(data);

		newTagName = new Text(composite, SWT.BORDER);
		newTagName.setEnabled(false);
		data = new FormData();
		data.top = new FormAttachment(newRadio, 0, SWT.CENTER);
		data.left = new FormAttachment(existingRadio, 10);
		data.width = 200;
		newTagName.setLayoutData(data);

		colorCombo = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY
				| SWT.DROP_DOWN);
		colorCombo.setEnabled(false);
		List<String> colors = new ArrayList<String>(ColorMap.get().getColors());
		Collections.sort(colors);
		for (String color : colors) {
			colorCombo.add(color);
		}
		data = new FormData();
		data.top = new FormAttachment(newRadio, 0, SWT.CENTER);
		data.left = new FormAttachment(newTagName);
		colorCombo.setLayoutData(data);
		colorCombo.select(0);

		saveBtn = new Button(composite, SWT.PUSH);
		saveBtn.setText("Add tag");
		data = new FormData();
		data.bottom = new FormAttachment(100, -15);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		saveBtn.setLayoutData(data);
		saveBtn.setEnabled(false);

		addEventListeners();

		dialog.open();

		// Create and check the event loop
		while (!dialog.isDisposed()) {
			if (!dialog.getDisplay().readAndDispatch())
				dialog.getDisplay().sleep();
		}

	}

	private void addEventListeners() {
		existingRadio.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				// newRadio.setSelection(false);
				newTagName.setEnabled(false);
				colorCombo.setEnabled(false);
				existingCombo.setEnabled(true);
				existingCombo.setFocus();
				validate();
			}
		});

		newRadio.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				// existingRadio.setSelection(false);
				newTagName.setEnabled(true);

				colorCombo.setEnabled(true);
				existingCombo.setEnabled(false);
				newTagName.setFocus();
				validate();
			}
		});

		existingCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validate();
			}
		});

		newTagName.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validate();
			}
		});

		colorCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validate();
			}
		});

		saveBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				try {
					if (existingRadio.getSelection()) {
						Tag tag = Application.getBll().getTag(
								existingCombo.getItem(existingCombo
										.getSelectionIndex()));
						Application.getBll().addTagToDocument(tag, doc);
					} else {
						Tag tag = Application.getBll().createTag(
								newTagName.getText());
						tag.setColorName(colorCombo.getItem(colorCombo
								.getSelectionIndex()));
						tag.setName(newTagName.getText());
						Application.getBll().updateTag(tag);
						tag = Application.getBll().getTag(tag.getId());
						Application.getBll().addTagToDocument(tag, doc);
					}
				} catch (UnableToCreateException e1) {
					NotifactionWindow.showError("Database error",
							"Failed to save the tag due to an error in the database. ("
									+ e1.getMessage() + ")");
				} catch (InvalidParameterException e1) {
					NotifactionWindow.showError("Parameter error",
							"Can't add the selected tag to the document. ("
									+ e1.getMessage() + ")");
				} catch (UnknownTagException e1) {
					NotifactionWindow
							.showError("Database error",
									"Unable to locate the selected tag in the database.");
				}

				dialog.dispose();
			}
		});

	}

	private void validate() {
		if (existingRadio.getSelection()
				&& existingCombo.getSelectionIndex() != -1) {
			saveBtn.setEnabled(true);
		} else if (newRadio.getSelection()
				&& newTagName.getText().isEmpty() == false
				&& colorCombo.getSelectionIndex() != -1) {
			saveBtn.setEnabled(true);
		} else {
			saveBtn.setEnabled(false);
		}
	}

}
