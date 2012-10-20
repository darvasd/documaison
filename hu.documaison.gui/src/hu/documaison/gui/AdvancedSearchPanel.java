package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class AdvancedSearchPanel extends InnerPanel {
	public AdvancedSearchPanel(Composite parent, int style) {
		super(parent, style, "Advanced search");
	}



	@Override
	protected void createComposite() {
		Label operator1 = new Label(this, SWT.None);
		operator1.setText("The following filters will be used, with an");
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 10);
		operator1.setLayoutData(data);
		
		Combo operatorCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		data = new FormData();
		data.top = new FormAttachment(operator1, 0, SWT.CENTER);
		data.left = new FormAttachment(operator1, 5);
		operatorCombo.setLayoutData(data);
		operatorCombo.add("AND");
		operatorCombo.add("OR");
		operatorCombo.select(0);
		
		Label operator2 = new Label(this, SWT.None);
		operator2.setText("operator:");
		data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(operatorCombo, 5);
		operator2.setLayoutData(data);
		
		addEventListeners();
	}
	

	
	private void clearAll() {
		
	}



	
	private void addEventListeners() {
		item.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				clearAll();
			}
			
		});
	}
	

}
