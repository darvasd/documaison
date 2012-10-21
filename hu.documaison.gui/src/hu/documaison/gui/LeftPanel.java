package hu.documaison.gui;

import hu.documaison.gui.search.SearchPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class LeftPanel extends Composite {

	public LeftPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		TagPanel tagPanel = new TagPanel(this, SWT.BORDER, null);
		SearchPanel searchPanel = new SearchPanel(this, SWT.BORDER);
		FormData data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(100, -130);
		searchPanel.setLayoutData(data);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(searchPanel, 0);
		tagPanel.setLayoutData(data);
		
	}


}
