package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.bll.ws.doidata.DoiQuery;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.data.exceptions.UnknownDocumentTypeException;
import hu.documaison.data.helper.DataHelper;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.NotificationWindow;
import hu.documaison.gui.ViewManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class NewDocumentPanel extends InnerPanel {

	private Link typeLink;
	private Combo typeCombo;
	private Button nextBtn;
	private Text locText;
	private HashMap<String, Integer> typeIdMap;
	private Button browseButton;
	private Button cancelBtn;

	public NewDocumentPanel(Composite parent, int style) {
		super(parent, style, "Add new document");
	}

	@Override
	protected void createComposite() {

		Label locLabel = new Label(this, SWT.None);
		locLabel.setText("Document location (file or URL or DOI):");
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		locLabel.setLayoutData(data);

		browseButton = new Button(this, SWT.PUSH);
		browseButton.setText("Browse");
		data = new FormData();
		data.top = new FormAttachment(locLabel);
		data.right = new FormAttachment(100, -20);
		browseButton.setLayoutData(data);

		locText = new Text(this, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(browseButton, 0, SWT.CENTER);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(browseButton);
		locText.setLayoutData(data);

		Label hintLabel = new Label(this, SWT.WRAP);
		hintLabel
				.setText("The document location shall be an absolute path in case of a local files or an URL, started with \"http://\" in case of remote documents, started with \"doi:\" for DOI query.");
		// hintLabel.setBackground(new Color(getDisplay(), 255, 255, 204));
		FontData[] fD = hintLabel.getFont().getFontData();
		fD[0].setHeight(10);
		hintLabel.setFont(new Font(getDisplay(), fD[0]));

		data = new FormData();
		data.top = new FormAttachment(locText, 10);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(browseButton);
		hintLabel.setLayoutData(data);

		Label typeLabel = new Label(this, SWT.NONE);
		typeLabel.setText("Document type:");
		data = new FormData();
		data.top = new FormAttachment(hintLabel, 20);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		typeLabel.setLayoutData(data);

		typeCombo = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY | SWT.SINGLE);
		loadDocTypes();
		data = new FormData();
		data.top = new FormAttachment(typeLabel, 0);
		data.left = new FormAttachment(0, 20);
		data.width = 150;
		typeCombo.setLayoutData(data);

		typeLink = new Link(this, SWT.None);
		typeLink.setText("<a>Manage document types</a>");
		data = new FormData();
		data.top = new FormAttachment(typeCombo, 0, SWT.CENTER);
		data.left = new FormAttachment(typeCombo, 10);
		typeLink.setLayoutData(data);

		nextBtn = new Button(this, SWT.PUSH);
		nextBtn.setText("Next");
		nextBtn.setEnabled(false);
		data = new FormData();
		data.top = new FormAttachment(typeCombo, 30);
		data.left = new FormAttachment(0, 150);
		data.right = new FormAttachment(100, -100);
		nextBtn.setLayoutData(data);

		cancelBtn = new Button(this, SWT.PUSH);
		cancelBtn.setText("Cancel");
		data = new FormData();
		data.top = new FormAttachment(typeCombo, 30);
		data.right = new FormAttachment(nextBtn, -10);
		cancelBtn.setLayoutData(data);

		addEventListeners();

	}

	private void addEventListeners() {

		typeLink.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				ViewManager.getDefault().showView("doctypeeditor",
						NewDocumentPanel.this);
			}
		});
		typeCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validate();
			}
		});

		locText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validate();
			}
		});

		nextBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				try {
					Document doc = Application.getBll().createDocument(
							typeIdMap.get(typeCombo.getItem(typeCombo
									.getSelectionIndex())));
					doc.setLocation(locText.getText());

					// DOI
					if (DataHelper.isDOI(locText.getText())) {
						doc.setLocation(DataHelper.doiToUrl(locText.getText()));
						
						try {
							List<Metadata> mdList = DoiQuery.query(doc, locText.getText());
							if (mdList == null){
								throw new IllegalArgumentException("mdList null");
							}
							for (Metadata md : mdList){
								doc.addMetadata(md);
							}
						} catch (Exception ex) {
							NotificationWindow.showError("DOI query error",
									"Failed to load the data from the DOI database. ("
											+ ex.getMessage() + ")");
							return;
						}
					}
					
					MetadataInputPanel panel = (MetadataInputPanel) (ViewManager
							.getDefault().showView("metaedit",
							NewDocumentPanel.this));
					Application.getBll().updateDocument(doc);
					panel.setDocument(doc);
				} catch (UnknownDocumentTypeException e1) {
					NotificationWindow.showError("Unknown type",
							"Failed to load the definition of the selected type. ("
									+ e1.getMessage() + ")");
				} catch (UnableToCreateException e1) {
					NotificationWindow.showError(
							"Database error",
							"Unable to create new document. ("
									+ e1.getMessage() + ")");
				}

			}
		});

		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				FileDialog dialog = new FileDialog(getShell());
				dialog.setFilterExtensions(new String[] { "*.*" });
				String path = dialog.open();
				if (path != null) {
					locText.setText(path);
				}
			}
		});

		cancelBtn.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				ViewManager.getDefault().showView("documents");
			}
		});
	}

	@Override
	public void showed() {
		locText.setText("");
		loadDocTypes();
		layout();
		validate();
		super.showed();
	}

	private void loadDocTypes() {
		typeCombo.removeAll();
		typeIdMap = new HashMap<String, Integer>();
		for (DocumentType dt : Application.getBll().getDocumentTypes()) {
			typeCombo.add(dt.getTypeName());
			typeIdMap.put(dt.getTypeName(), dt.getId());
		}
	}

	private void validate() {
		nextBtn.setEnabled(false);
		if (locText.getText().isEmpty() == false) {
			File f = new File(locText.getText());
			if (f.exists() || DataHelper.isURL(locText.getText())
					|| DataHelper.isDOI(locText.getText())) {
				if (typeCombo.getSelectionIndex() != -1) {
					nextBtn.setEnabled(true);
				}
			}
		}
	}
}
