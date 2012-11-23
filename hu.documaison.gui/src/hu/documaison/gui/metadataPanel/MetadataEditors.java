package hu.documaison.gui.metadataPanel;

import hu.documaison.data.entities.Document;
import hu.documaison.gui.document.DocumentItem;
import hu.documaison.gui.document.DocumentLister;

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
	private final DocumentLister lister;

	public MetadataEditors(Composite parent, int style, DocumentLister lister) {
		super(parent, style);
		this.lister = lister;
		setLayout(new FormLayout());
	}

	public void showDoc(Document doc) {
		if (mainPanel != null) {
			mainPanel.dispose();
		}
		if (morePanel != null) {
			morePanel.dispose();
		}
		document = doc;
		mainPanel = new MainDetailsPanel(this, SWT.NONE, doc);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(50, 0);
		data.bottom = new FormAttachment(100, 0);
		mainPanel.setLayoutData(data);

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
		if (doc == null || document == null) {
			return false;
		} else {
			if (doc.getId() == document.getId()) {
				document = doc;
				return true;
			} else {
				return false;
			}
		}
	}

	public void showDoc(DocumentItem documentItem) {
		if (item != null && item != documentItem) {
			if (item.isDisposed()) {
				item = null;
			} else {
				DocumentItem itemToUnselect = lister
						.getItemForDocument(document.getId());
				itemToUnselect.setSelection(false, false);
			}
		}
		showDoc(documentItem.getDoc());
		item = documentItem;
	}

	public void setHidden() {
		document = null;
		item = null;

	}

}
