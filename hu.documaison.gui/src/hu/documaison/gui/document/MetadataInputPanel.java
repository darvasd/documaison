package hu.documaison.gui.document;

import hu.documaison.data.entities.Document;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.metadataPanel.MainDetailsPanel;
import hu.documaison.gui.metadataPanel.MetadataPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class MetadataInputPanel extends InnerPanel {

	private Document documentToEdit = null;
	private MetadataPanel mtdtPanel;
	private Label infoLabel;
	private MainDetailsPanel mainDetails;

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
		mainDetails = new MainDetailsPanel(this, SWT.None, doc);
		FormData data = new FormData();
		data.top = new FormAttachment(infoLabel, 10);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		mainDetails.setLayoutData(data);
		mainDetails.pack();
		mainDetails.layout();
		mtdtPanel = new MetadataPanel(this, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(mainDetails, 10);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		data.bottom = new FormAttachment(100, -50);
		mtdtPanel.setLayoutData(data);
		mtdtPanel.setDocument(doc);

		layout();
	}

}
