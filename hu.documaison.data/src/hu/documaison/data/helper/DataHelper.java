package hu.documaison.data.helper;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;

public class DataHelper {
	public static boolean isValidId(int id) {
		if (id > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isURL(String string) {
		if (string != null
				&& (string.startsWith("http://") || string
						.startsWith("https://"))) {
			return true;
		}
		return false;
	}

	public static String createBibTex(Document doc) {
		String ret = "@Article{article" + doc.getId() + ",\n";
		Metadata title = doc.getMetadata("title");
		if (title == null) {
			title = doc.getMetadata("Title");
		}
		if (title != null) {
			ret = ret.concat("\ttitle = \"" + title.getValue() + "\",\n");
		}
		Metadata author = doc.getMetadata("author");
		if (author == null) {
			author = doc.getMetadata("Author");
		}
		if (author != null) {
			ret = ret.concat("\tauthor = \"" + author.getValue() + "\",\n");
		}
		Metadata location = doc.getMetadata("location");
		if (location == null) {
			location = doc.getMetadata("Location");
		}
		if (location != null && isURL(location.getValue())) {
			ret = ret.concat("\turl = \"" + location.getValue() + "\",\n");
		}
		return ret.concat("}");
	}
}
