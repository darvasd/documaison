package hu.documaison.gui.search;

import hu.documaison.Application;
import hu.documaison.data.entities.Tag;
import hu.documaison.gui.ISearchFieldProvider;

import java.util.ArrayList;

public class TestSearchFieldProvider implements ISearchFieldProvider {

	private final ArrayList<SearchField> fields = new ArrayList<SearchField>();

	public TestSearchFieldProvider() {
		SearchField field = new SearchField("Title", SearchFieldType.STRING);
		fields.add(field);
		field = new SearchField("Description", SearchFieldType.STRING);
		fields.add(field);
		field = new SearchField("Year", SearchFieldType.INTEGER);
		fields.add(field);
		field = new SearchField("Tag", SearchFieldType.STRING);
		ArrayList<String> tags = new ArrayList<String>();
		for (Tag tag : Application.getBll().getTags()) {
			tags.add(tag.getName());
		}
		field.setValues(tags);
		fields.add(field);
	}

	@Override
	public ArrayList<SearchField> getSearchFields() {
		return fields;
	}

	@Override
	public SearchField getField(String name) {
		for (SearchField field : fields) {
			if (field.getName().equalsIgnoreCase(name)) {
				return field;
			}
		}
		return null;
	}

}
