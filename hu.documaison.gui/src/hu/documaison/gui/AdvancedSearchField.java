package hu.documaison.gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class AdvancedSearchField extends Composite {

	public AdvancedSearchField(Composite parent, int style, ISearchFieldProvider provider) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Combo fieldName = new Combo(this, SWT.NONE);
		for (SearchField field : provider.getSearchFields()) {
			fieldName.add(field.getName());
		}
		fieldName.select(0);
		SearchField selectedSF = provider.getSearchFields().get(0);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 3);
		data.bottom = new FormAttachment(100, -3);
		data.left = new FormAttachment(0, 10);
		fieldName.setLayoutData(data);
		
		Combo choices = new Combo(this, SWT.None);
		if (selectedSF.getType() == SearchFieldType.STRING) {
			choices.add("contains:");
			choices.add("not contains:");
			choices.add("equals:");
			choices.add("not equals:");
			choices.add("starts with:");
			choices.add("end with:");
		} else if (selectedSF.getType() == SearchFieldType.INTEGER) {
			choices.add("equals:");
			choices.add("not equals:");
			choices.add("less than:");
			choices.add("more than:");
			choices.add("in between:");
		}
		choices.select(0);
		data = new FormData();
		data.top = new FormAttachment(0, 3);
		data.bottom = new FormAttachment(100, -3);
		data.left = new FormAttachment(fieldName, 10);
		
		
		
	}

}
