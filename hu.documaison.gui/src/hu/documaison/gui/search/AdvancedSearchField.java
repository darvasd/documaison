package hu.documaison.gui.search;

import hu.documaison.gui.ISearchFieldProvider;
import hu.documaison.gui.NotifactionWindow;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class AdvancedSearchField extends Composite {

	private Combo choices;
	private HashMap<String, SearchField> searchFieldCache = new HashMap<String, SearchField>();
	private ArrayList<SearchField> searchFields;
	private Combo fieldName;
	private Button removeBtn;
	private Button addBtn;
	private Control input1 = null;
	private Control input2 = null;

	public AdvancedSearchField(Composite parent, int style, ISearchFieldProvider provider) {
		super(parent, style);
		setLayout(new FormLayout());
		
		fieldName = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		int count = loadSearchFields(new TestSearchFieldProvider());
		if (count == 0) {
			NotifactionWindow.showError("Metadata error", "No metadata entries found in the database.");
		} else {
			fieldName.select(0);
		}
		
		SearchField selectedSF = searchFields.get(0);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 3);
		data.bottom = new FormAttachment(100, -3);
		data.left = new FormAttachment(0, 10);
		fieldName.setLayoutData(data);
		
		choices = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		if (selectedSF != null) {
			loadChoices(selectedSF.getType());
			choices.select(0);
		}
		data = new FormData();
		data.top = new FormAttachment(0, 3);
		data.bottom = new FormAttachment(100, -3);
		data.left = new FormAttachment(fieldName, 10);
		choices.setLayoutData(data);
		
		addBtn = new Button(this, SWT.PUSH);
		addBtn.setText("+");
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, -10);
		addBtn.setLayoutData(data);
		
		removeBtn = new Button(this, SWT.PUSH);
		removeBtn.setText("-");
		removeBtn.setVisible(false);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(addBtn, 0);
		removeBtn.setLayoutData(data);
		
		loadInputFields(false, selectedSF.getType());
		
		
		createEventHandlers();
	}
	
	private void loadChoices(SearchFieldType type) {
		choices.removeAll();
		if (type == SearchFieldType.STRING) {
			choices.add("contains:");
			choices.add("not contains:");
			choices.add("equals:");
			choices.add("not equals:");
			choices.add("starts with:");
			choices.add("end with:");
		} else if (type  == SearchFieldType.INTEGER) {
			choices.add("equals:");
			choices.add("not equals:");
			choices.add("less than:");
			choices.add("more than:");
			choices.add("in between:");
		} else if (type  == SearchFieldType.DATE) {
			choices.add("equals");
			choices.add("not equals");
			choices.add("in between");
		}
		choices.select(0);
		loadInputsForSelection();
	}
	
	private int loadSearchFields(ISearchFieldProvider provider) {
		searchFields = provider.getSearchFields();
		for (SearchField sf : searchFields) {
			searchFieldCache.put(sf.getName(), sf);
			fieldName.add(sf.getName());
		}
		return searchFields.size();
	}
	//tüpptürüpp
	private void loadInputFields(boolean isMultiple, SearchFieldType type) {
		if (input1 != null) {
			input1.dispose();
		}
		if (input2 != null) {
			input2.dispose();
		}
		if (isMultiple == false) {
			input1 = createOneField(type);
			FormData data = new FormData();
			data.top = new FormAttachment(0, 3);
			data.bottom = new FormAttachment(100, -3);
			data.left = new FormAttachment(choices, 10);
			data.right = new FormAttachment(removeBtn, -15);
			input1.setLayoutData(data);
		} else {
			input1 = createOneField(type);
			FormData data = new FormData();
			data.top = new FormAttachment(0, 3);
			data.bottom = new FormAttachment(100, -3);
			data.left = new FormAttachment(choices, 10);
			data.width = 150;
			input1.setLayoutData(data);
			input2 = createOneField(type);
			data = new FormData();
			data.top = new FormAttachment(0, 3);
			data.bottom = new FormAttachment(100, -3);
			data.left = new FormAttachment(input1, 10);
			data.width = 150;
			input2.setLayoutData(data);			
		}
		layout();
	}
	
	private Control createOneField(SearchFieldType type) {
		if (type == SearchFieldType.STRING) {
			Text textField = new Text(this, SWT.BORDER);
			return textField;
		} else if (type == SearchFieldType.INTEGER) {
			Text textField = new Text(this, SWT.BORDER);
			return textField;
		} else if (type == SearchFieldType.DATE) {
			DateTime dateField = new DateTime(this, SWT.DATE | SWT.DROP_DOWN | SWT.MEDIUM);
			return dateField;
		} else {
			NotifactionWindow.showError("Internal error", "Unknown metadata type, can't create search field");
			Text textField = new Text(this, SWT.BORDER);
			return textField;
		}
	}
	
	private void createEventHandlers() {
		fieldName.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (fieldName.getSelectionIndex() < 0) {
					choices.removeAll();
				} else {
					SearchField selected = searchFieldCache.get(fieldName.getItem(fieldName.getSelectionIndex()));
					loadChoices(selected.getType());
				}
			}
		});
		choices.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (choices.getSelectionIndex() >= 0) {
					loadInputsForSelection();
				}
			}
		});
	}
	
	public void addPlusListener(SelectionListener listener) {
		addBtn.addSelectionListener(listener);
	}
	
	public void setRemoveVisibility(boolean visibility) {
		removeBtn.setVisible(visibility);
	}
	
	public void addRemoveListener(SelectionListener listener) {
		removeBtn.addSelectionListener(listener);
	}
	
	private void loadInputsForSelection() {
		if (choices.getItem(choices.getSelectionIndex()).equalsIgnoreCase("in between:")) {
			SearchField selected = searchFieldCache.get(fieldName.getItem(fieldName.getSelectionIndex()));
			loadInputFields(true, selected.getType());
		} else {
			SearchField selected = searchFieldCache.get(fieldName.getItem(fieldName.getSelectionIndex()));
			loadInputFields(false, selected.getType());
		}
	}
	
	

}
