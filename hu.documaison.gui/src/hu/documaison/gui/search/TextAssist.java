package hu.documaison.gui.search;

import java.util.ArrayList;
import java.util.List;

import org.mihalis.opal.textAssist.TextAssistContentProvider;

public class TextAssist extends TextAssistContentProvider {

	private ArrayList<String> contents = new ArrayList<String>();
	
	public void setContents(ArrayList<String> newContents) {
		contents = newContents;
	}
	
	@Override
	public List<String> getContent(String arg0) {
		ArrayList<String> ret = new ArrayList<String>();
		if (arg0.length() > 0) {
			for (String s : contents) {
				if (s.startsWith(arg0)) {
					ret.add(s);
				}
			}
		}
		return ret;
	}

}
