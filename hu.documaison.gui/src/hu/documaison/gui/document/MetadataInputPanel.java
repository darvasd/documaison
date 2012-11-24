package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.ViewManager;
import hu.documaison.gui.metadataPanel.MetadataEditors;
import hu.documaison.gui.metadataPanel.MetadataPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;


public class MetadataInputPanel extends InnerPanel {

	private Document documentToEdit = null;
	private MetadataPanel mtdtPanel;
	private Label infoLabel;
	private MetadataEditors editors;

	public MetadataInputPanel(Composite parent, int style) {
		super(parent, style, "Edit document metadata");
	}

	@Override
	protected void createComposite() {

		infoLabel = new Label(this, SWT.WRAP);
		infoLabel
				.setText("Edit the document's metadata. You can also add and remove any metadata entry.");
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 20);
		infoLabel.setLayoutData(data);

		Button saveBtn = new Button(this, SWT.PUSH);
		saveBtn.setText("Save document");
		data = new FormData();
		data.bottom = new FormAttachment(100, -10);
		data.right = new FormAttachment(100, -10);
		saveBtn.setLayoutData(data);
		
		Button cancelBtn = new Button(this, SWT.PUSH);
		cancelBtn.setText("Cancel");
		data = new FormData();
		data.bottom = new FormAttachment(100, -10);
		data.right = new FormAttachment(saveBtn, -10);
		cancelBtn.setLayoutData(data);

		editors = new MetadataEditors(this, SWT.NONE, null);
		data = new FormData();
		data.top = new FormAttachment(infoLabel, 5);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		data.bottom = new FormAttachment(100, -50);
		editors.setLayoutData(data);

		saveBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				DocumentObserver.notifyLister();
				ViewManager.getDefault().showView("documents");
			}
		});
		
		cancelBtn.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Application.getBll().removeDocument(documentToEdit.getId());
				ViewManager.getDefault().showView("documents");
			}
		});

	}

	@Override
	public void showed() {
		if (documentToEdit != null) {
			mtdtPanel.setDocument(documentToEdit);
		}
		super.showed();
	}

	public void setDocument(Document doc) {
		documentToEdit = doc;
		editors.showDoc(doc);
		layout();
	}

}
