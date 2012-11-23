package hu.documaison.gui;

import hu.documaison.gui.doctype.DocTypeEditor;
import hu.documaison.gui.document.DocumentLister;
import hu.documaison.gui.document.MetadataInputPanel;
import hu.documaison.gui.document.NewDocumentPanel;
import hu.documaison.gui.search.AdvancedSearchPanel;
import hu.documaison.gui.settings.SettingsPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public class RightPanel extends Composite {

	public RightPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		final MultiPanel multiPanel = new MultiPanel(this, SWT.NONE);
		ViewManager.getDefault().setViewer(multiPanel);
		// Composite tab1Composite = new Composite(multiPanel, SWT.NONE);
		// multiPanel.addSheet(tab1Composite, "documents");
		// tab1Composite.setLayout(new FormLayout());
		// DocumentItem doc = new DocumentItem(tab1Composite, SWT.BORDER);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(0, 123);
		// doc.setLayoutData(data);

		// DocumentItem doc2 = new DocumentItem(tab1Composite, SWT.BORDER);
		// data = new FormData();
		// data.top = new FormAttachment(doc, 0);
		// data.left = new FormAttachment(0, 0);
		// data.right = new FormAttachment(100, 0);
		// data.height = 123;
		// doc2.setLayoutData(data);



		SettingsPanel sp = new SettingsPanel(multiPanel, SWT.None);
		multiPanel.addSheet(sp, "settings");
		sp.addHomeSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("documents");
			}

		});

		DocTypeEditor dte = new DocTypeEditor(multiPanel, SWT.NONE);
		multiPanel.addSheet(dte, "doctypeeditor");

		MetadataInputPanel metadataInputPanel = new MetadataInputPanel(
				multiPanel, SWT.NONE);
		multiPanel.addSheet(metadataInputPanel, "metaedit");

		NewDocumentPanel newPanel = new NewDocumentPanel(multiPanel, SWT.NONE);
		multiPanel.addSheet(newPanel, "newDocument");

		DocumentLister lister = new DocumentLister(multiPanel, SWT.NONE);
		multiPanel.addSheet(lister, "documents");

		AdvancedSearchPanel asp = new AdvancedSearchPanel(multiPanel, SWT.NONE);
		multiPanel.addSheet(asp, "advancedSearch");
		asp.addHomeSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("documents");
			}
			
		});
		
		multiPanel.selectSheet(lister);
	}
}
