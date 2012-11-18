package hu.documaison.gui.document;

import hu.documaison.data.entities.Document;
import hu.documaison.gui.metadataPanel.MainDetailsPanel;
import hu.documaison.gui.metadataPanel.MetadataPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class MetadataEditors extends Composite {

	private MainDetailsPanel mainPanel;
	private MetadataPanel morePanel;
	private Document document;
	private DocumentItem item;

	public MetadataEditors(Composite parent, int style) {
		super(parent, style);

		setLayout(new FormLayout());
	}

	public void showDoc(Document doc) {
		document = doc;
		if (mainPanel != null) {
			mainPanel.dispose();
		}
		if (morePanel != null) {
			morePanel.dispose();
		}
		mainPanel = new MainDetailsPanel(this, SWT.NONE, doc);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(50, 0);
		data.bottom = new FormAttachment(100, 0);
		mainPanel.setLayoutData(data);
		mainPanel.pack();
		mainPanel.layout();
		morePanel = new MetadataPanel(this, SWT.None);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(50, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(100, 0);
		morePanel.setLayoutData(data);
		morePanel.setDocument(doc);

		layout();
	}

	public boolean isShowed(Document doc) {
		return document == doc;
	}

	public void showDoc(DocumentItem documentItem) {
		if (item != null) {
			item.setSelection(false);
		}
		showDoc(documentItem.getDoc());
		item = documentItem;

	}
}
