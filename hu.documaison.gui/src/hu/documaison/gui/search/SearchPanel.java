package hu.documaison.gui.search;

import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.ViewManager;
import hu.documaison.gui.document.DocumentLister;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SearchPanel extends Composite {

	public SearchPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		final Text searchField = new Text(this, SWT.SEARCH | SWT.ICON_CANCEL
				| SWT.ICON_SEARCH);
		searchField.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				InnerPanel sheet = ViewManager.getDefault()
						.getView("documents");
				if (sheet != null) {
					((DocumentLister) sheet).freetextSearch(searchField
							.getText());
					ViewManager.getDefault().showView(sheet);
				}
			}
		});
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
		advancedSearch.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewManager.getDefault().showView("advancedSearch");
			}

		});

		Button newDocument = new Button(this, SWT.PUSH);
		data = new FormData();
		data.top = new FormAttachment(advancedSearch, 0);
		data.left = new FormAttachment(0, 40);
		data.right = new FormAttachment(100, -40);

		newDocument.setLayoutData(data);
		newDocument.setText("New document");

		newDocument.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				ViewManager.getDefault().showView("newDocument");
			}
		});

	}

}
