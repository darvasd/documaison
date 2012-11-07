package hu.documaison.gui.document;

import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.metadataPanel.MetadataPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public class NewDocumentPanel extends InnerPanel {

	public NewDocumentPanel(Composite parent, int style) {
		super(parent, style, "Add new document");
	}

	@Override
	protected void createComposite() {
		MetadataPanel metadataPanel = new MetadataPanel(this, SWT.BORDER);
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		metadataPanel.setLayoutData(data);
	}
}
