package hu.documaison.data.helper;

public class DataHelper {
	public static boolean isValidId(int id) {
		if (id > 0) {
			return true;
		} else {
			return false;
		}
	}
}
