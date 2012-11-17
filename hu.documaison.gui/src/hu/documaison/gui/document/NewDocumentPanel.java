package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.ViewManager;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class NewDocumentPanel extends InnerPanel {

	private Link typeLink;
	private Combo typeCombo;
	private Button nextBtn;
	private Text locText;

	public NewDocumentPanel(Composite parent, int style) {
		super(parent, style, "Add new document");
	}

	@Override
	protected void createComposite() {

		Label locLabel = new Label(this, SWT.None);
		locLabel.setText("Document location (file or URL):");
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		locLabel.setLayoutData(data);

		Button browseButton = new Button(this, SWT.PUSH);
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
				.setText("The document location shall be an absolute path in case of a local files or an URL, started with \"http://\" in case of remote documents.");
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
		data.left = new FormAttachment(0, 100);
		data.right = new FormAttachment(100, -100);
		nextBtn.setLayoutData(data);

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

		locText.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validate();
			}
		});
	}

	@Override
	public void showed() {
		loadDocTypes();
		validate();
		super.showed();
	}

	private void loadDocTypes() {
		typeCombo.removeAll();
		for (DocumentType dt : Application.getBll().getDocumentTypes()) {
			typeCombo.add(dt.getTypeName());
		}
	}

	private void validate() {
		nextBtn.setEnabled(false);
		if (locText.getText().isEmpty() == false) {
			File f = new File(locText.getText());
			if (f.exists() || locText.getText().startsWith("http://")) {
				if (typeCombo.getSelectionIndex() != -1) {
					nextBtn.setEnabled(true);
				}
			}
		}
	}
}
