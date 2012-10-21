package hu.documaison.gui.search;

import java.util.ArrayList;

public class SearchField {

	private SearchFieldType type;
	private String name;
	private ArrayList<String> values;
	
	public SearchField(String name, SearchFieldType type) {
		this.name = name;
		this.type = type;
	}
	
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public SearchFieldType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getValues() {
		return values;
	}
	
	
	
}
