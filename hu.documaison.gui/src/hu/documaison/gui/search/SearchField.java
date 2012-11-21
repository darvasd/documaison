package hu.documaison.gui.search;

import hu.documaison.data.entities.MetadataType;

import java.util.ArrayList;

public class SearchField {

	private final MetadataType type;
	private final String name;
	private ArrayList<String> values;

	public SearchField(String name, MetadataType type) {
		this.name = name;
		this.type = type;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public MetadataType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getValues() {
		return values;
	}

}
