package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SearchPanel extends Composite {

	public SearchPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Text searchField = new Text(this, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 20);
		data.left = new FormAttachment(0, 20);
		data.right = new FormAttachment(100, -20);
		searchField.setLayoutData(data);
		
		Button advancedSearch = new Button(this, SWT.PUSH);
		data = new FormData();
		data.top = new FormAttachment(searchField, 10);
		data.left = new FormAttachment(0, 40);
		data.right = new FormAttachment(100, -40);
		
		advancedSearch.setLayoutData(data);
		advancedSearch.setText("Advanced search");
		
		Button newDocument = new Button(this, SWT.PUSH);
		data = new FormData();
		data.top = new FormAttachment(advancedSearch, 0);
		data.left = new FormAttachment(0, 40);
		data.right = new FormAttachment(100, -40);
		
		newDocument.setLayoutData(data);
		newDocument.setText("New document");
		
	}

}
