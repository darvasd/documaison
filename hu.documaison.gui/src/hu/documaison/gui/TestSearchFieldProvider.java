package hu.documaison.gui;

import java.util.ArrayList;

public class TestSearchFieldProvider implements ISearchFieldProvider {

	private ArrayList<SearchField> fields = new ArrayList<SearchField>();
	
	public TestSearchFieldProvider() {
		SearchField field = new SearchField("Title", SearchFieldType.STRING);
		fields.add(field);
		field = new SearchField("Description", SearchFieldType.STRING);
		fields.add(field);
		field = new SearchField("Year", SearchFieldType.INTEGER);
		fields.add(field);
		field = new SearchField("Tag", SearchFieldType.STRING);
		ArrayList<String> tags = new ArrayList<String>();
		TestTagProvider tp = new TestTagProvider();
		for (int i = 0; i < tp.getTagCount(); i++) {
			tags.add(tp.getTag(i));
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
