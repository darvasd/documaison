package hu.documaison.gui;

import java.util.ArrayList;

public interface ISearchFieldProvider {

	public ArrayList<SearchField> getSearchFields();
	
	public SearchField getField(String name);
	
}
