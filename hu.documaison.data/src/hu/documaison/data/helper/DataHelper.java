package hu.documaison.data.helper;

import java.io.File;

public class DataHelper {
	public static boolean isValidId(int id) {
		if (id > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static File createFileObject(String location) {
		File ret;
		try {
			ret = new File(location);
			return ret;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public static String fileExtension(String path) {
		int lastDot = path.lastIndexOf('.');
		return path.toString().substring(lastDot + 1);
	}
}
