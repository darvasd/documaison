package hu.documaison.gui;

import hu.documaison.gui.search.SearchField;

import java.util.ArrayList;

public interface ISearchFieldProvider {

	public ArrayList<SearchField> getSearchFields();
	
	public SearchField getField(String name);
	
}
