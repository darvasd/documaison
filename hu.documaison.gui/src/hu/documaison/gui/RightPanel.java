package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class RightPanel extends Composite{

	public RightPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		DocumentItem doc = new DocumentItem(this, SWT.BORDER);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(0, 123);
		doc.setLayoutData(data);
		
		DocumentItem doc2 = new DocumentItem(this, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(doc, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.height = 123;
		doc2.setLayoutData(data);
		
	}

}
