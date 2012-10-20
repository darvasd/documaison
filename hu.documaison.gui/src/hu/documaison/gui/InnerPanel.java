package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.mihalis.opal.breadcrumb.Breadcrumb;
import org.mihalis.opal.breadcrumb.BreadcrumbItem;

public abstract class InnerPanel extends Composite{

	protected final BreadcrumbItem home;
	protected final BreadcrumbItem item;
	protected final Label titleLabel;
	
	
	public InnerPanel(Composite parent, int style, String title) {
		super(parent, style);
		
		
			FormLayout layout = new FormLayout();
			setLayout(layout);
			
			Breadcrumb breadcrumb = new Breadcrumb(this, SWT.BORDER_SOLID);
			home = new BreadcrumbItem(breadcrumb, SWT.PUSH);
			home.setText("  Documents  ");
			item = new BreadcrumbItem(breadcrumb, SWT.PUSH);
			item.setText("  " + title + "  ");
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.top = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			breadcrumb.setLayoutData(data);
			
			titleLabel = new Label(this, SWT.None);
			titleLabel.setText(title);
			FontData[] fD = titleLabel.getFont().getFontData();
			fD[0].setHeight(16);
			titleLabel.setFont( new Font(null,fD[0]));
			data = new FormData();
			data.top = new FormAttachment(breadcrumb, 10);
			data.left = new FormAttachment(0, 10);
			titleLabel.setLayoutData(data);
			
			createComposite();
	}
	
	protected abstract void createComposite();
	
	public void addHomeSelectionListener(SelectionListener listener) {
		home.addSelectionListener(listener);
	}

}
