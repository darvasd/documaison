package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.mihalis.opal.breadcrumb.Breadcrumb;
import org.mihalis.opal.breadcrumb.BreadcrumbItem;

public abstract class InnerPanel extends Composite {

	protected BreadcrumbItem home;
	protected BreadcrumbItem item;
	protected Label titleLabel;
	protected InnerPanel previous = null;
	private Breadcrumb breadcrumb;
	private final String title;
	private BreadcrumbItem newItem;

	public InnerPanel(Composite parent, int style, String title) {
		super(parent, style);
		this.title = title;
		FormLayout layout = new FormLayout();
		setLayout(layout);

		createBreadcrumb();

		titleLabel = new Label(this, SWT.None);
		titleLabel.setText(title);
		FontData[] fD = titleLabel.getFont().getFontData();
		fD[0].setHeight(16);
		titleLabel.setFont(new Font(null, fD[0]));
		FormData data = new FormData();
		data.top = new FormAttachment(0, 40);
		data.left = new FormAttachment(0, 10);
		titleLabel.setLayoutData(data);

		createComposite();
	}

	private void createBreadcrumb() {
		breadcrumb = new Breadcrumb(this, SWT.BORDER_SOLID);
		home = new BreadcrumbItem(breadcrumb, SWT.PUSH);
		home.setText("  DocuMaison  ");
		item = new BreadcrumbItem(breadcrumb, SWT.PUSH);
		
		item.setText("  " + title + "  ");
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		breadcrumb.setLayoutData(data);
		breadcrumb.pack();

	}

	protected abstract void createComposite();

	public void addHomeSelectionListener(SelectionListener listener) {
		home.addSelectionListener(listener);
	}

	public void setPreviousPanel(final InnerPanel panel) {
		previous = panel;
		if (panel != null) {
			item.setText("  " + panel.getTitle() + "  ");
			item.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {
					ViewManager.getDefault().showView(panel);
				}
			});
			newItem = new BreadcrumbItem(breadcrumb, SWT.PUSH);
			newItem.setText("  " + title + "  ");
		} else if (panel == null) {
			breadcrumb.dispose();
			createBreadcrumb();
			layout();
		}
	}

	public String getTitle() {
		return title;
	}

	public void showed() {
		layout();
	}

}
