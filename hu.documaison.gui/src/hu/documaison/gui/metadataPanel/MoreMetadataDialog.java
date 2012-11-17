package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.entities.MetadataType;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.gui.NotifactionWindow;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MoreMetadataDialog {

	private Control c;
	private Combo typeCombo;
	private Composite composite;
	private Button saveBtn;
	private Document parentDoc;
	private Text nameText;
	private Shell dialog;
	private Metadata metadata;

	public Metadata showAndHandle(Shell parent, Document doc) {
		parentDoc = doc;
		dialog = new Shell(parent, SWT.APPLICATION_MODAL | SWT.BORDER
				| SWT.CLOSE | SWT.TITLE);
		dialog.setText("Add more metadata");
		dialog.setLayout(new FillLayout());

		composite = new Composite(dialog, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 15;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;
		composite.setLayout(gridLayout);

		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Metadata name:");
		nameText = new Text(composite, SWT.BORDER);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		nameText.setLayoutData(gridData);

		Label typeLabel = new Label(composite, SWT.None);
		typeLabel.setText("Metadata type:");
		typeCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		MetadataType[] values = MetadataType.values();
		for (int i = 0; i < values.length; i++) {
			MetadataType metadataType = values[i];
			typeCombo.add(metadataType + "");
		}
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		typeCombo.setLayoutData(gridData);

		Label dvLabel = new Label(composite, SWT.None);
		dvLabel.setText("Value:");
		c = createOneField(composite, typeCombo.getItem(0), null);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		c.setLayoutData(gridData);

		saveBtn = new Button(composite, SWT.PUSH);
		saveBtn.setText("Add");
		gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		saveBtn.setLayoutData(gridData);

		addEventHandlers();

		dialog.pack();
		dialog.open();

		// Create and check the event loop
		while (!dialog.isDisposed()) {
			if (!dialog.getDisplay().readAndDispatch())
				dialog.getDisplay().sleep();
		}
		return metadata;

	}

	private Control createOneField(Composite parent, String type, Object value) {
		if (type.equalsIgnoreCase("Text")) {
			Text textField = new Text(parent, SWT.BORDER);
			if (value != null) {
				textField.setText((String) value);
			}
			return textField;
		} else if (type.equalsIgnoreCase("Integer")) {
			Text textField = new Text(parent, SWT.BORDER);
			if (value != null) {
				textField.setText((String) value);
			}
			return textField;
		} else if (type.equalsIgnoreCase("Date")) {
			DateTime dateField = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN
					| SWT.MEDIUM);
			if (value != null) {
				Date valueD = (Date) value;
				Calendar cal = new GregorianCalendar();
				cal.setTime(valueD);
				dateField
						.setDate(cal.get(Calendar.YEAR),
								cal.get(Calendar.MONTH),
								cal.get(Calendar.DAY_OF_MONTH));
			}
			return dateField;
		} else {
			NotifactionWindow.showError("Internal error",
					"Unknown metadata type, can't create field for " + type
							+ ".");
			Text textField = new Text(parent, SWT.BORDER);
			return textField;
		}
	}

	private void addEventHandlers() {
		typeCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Control old = c;
				c = createOneField(composite,
						typeCombo.getItem(typeCombo.getSelectionIndex()), null);
				GridData gridData = new GridData(SWT.FILL, SWT.FILL, true,
						false);
				gridData.widthHint = 300;
				c.setLayoutData(gridData);
				c.moveAbove(old);
				old.dispose();
				composite.layout();
			}
		});

		saveBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				try {
					metadata = Application.getBll().createMetadata(parentDoc);
				} catch (UnableToCreateException e1) {
					NotifactionWindow
							.showError("Database error",
									"Unable to create metadata entry, the changes are not saved!");
					return;
				}

				metadata.setName(nameText.getText());
				String typeName = typeCombo.getItem(typeCombo
						.getSelectionIndex());
				MetadataType[] values = MetadataType.values();
				for (int i = 0; i < values.length; i++) {
					MetadataType metadataType = values[i];
					if (typeName.equalsIgnoreCase(metadataType + "")) {
						metadata.setMetadataType(metadataType);
					}
				}
				if (c instanceof Text) {
					Text text = (Text) c;
					try {
						if (text.getText().isEmpty() == false) {
							if (metadata.getMetadataType() == MetadataType.Integer) {
								metadata.setValue(Integer.parseInt(text
										.getText()));
							} else {
								metadata.setValue(text.getText());
							}
						}
					} catch (Exception ex) {
						NotifactionWindow.showError("Conversion error",
								"Failed to convert data");
					}
				} else if (c instanceof DateTime) {
					DateTime dt = (DateTime) c;
					Calendar cal = new GregorianCalendar();
					cal.set(dt.getYear(), dt.getMonth(), dt.getDay());
					metadata.setValue(cal.getTime());
				} else {
					NotifactionWindow.showError("Conversion error",
							"Can't handle the selected metadata type");
				}
				Application.getBll().updateMetadata(metadata);
				dialog.dispose();
			}
		});
	}
}
