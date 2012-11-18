package hu.documaison.data.helper;

import java.io.File;
import java.nio.file.Path;

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
	
	public static String fileExtension(Path path) {
		int lastDot = path.toString().lastIndexOf('.');
		return path.toString().substring(lastDot + 1);
	}
}
