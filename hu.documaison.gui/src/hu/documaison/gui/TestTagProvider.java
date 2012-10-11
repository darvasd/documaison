package hu.documaison.gui;

public class TestTagProvider implements ITagProvider {

	@Override
	public int getTagCount() {
		return 100;
	}

	@Override
	public String getTag(int index) {
		return "Tag no. " + index;
	}

}
