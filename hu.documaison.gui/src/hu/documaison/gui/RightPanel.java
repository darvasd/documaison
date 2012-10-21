package hu.documaison.gui;

import hu.documaison.gui.search.AdvancedSearchPanel;
import hu.documaison.gui.settings.SettingsPanel;

import javax.swing.plaf.multi.MultiPopupMenuUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class RightPanel extends Composite{

	public RightPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		final MultiPanel multiPanel = new MultiPanel(this, SWT.NONE);
		ViewManager.getDefault().setViewer(multiPanel);
		Composite tab1Composite = new Composite(multiPanel, SWT.NONE);
		multiPanel.addSheet(tab1Composite, "documents");
		tab1Composite.setLayout(new FormLayout());
		DocumentItem doc = new DocumentItem(tab1Composite, SWT.BORDER);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(0, 123);
		doc.setLayoutData(data);
		
		DocumentItem doc2 = new DocumentItem(tab1Composite, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(doc, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.height = 123;
		doc2.setLayoutData(data);
		
		AdvancedSearchPanel asp = new AdvancedSearchPanel(multiPanel, SWT.NONE);
		multiPanel.addSheet(asp, "advancedSearch");
		asp.addHomeSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("documents");
			}
			
		});
		
		SettingsPanel sp = new SettingsPanel(multiPanel, SWT.None);
		multiPanel.addSheet(sp, "settings");
		sp.addHomeSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("documents");
			}
			
		});
		
		
	}

}
