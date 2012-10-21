package hu.documaison;

import hu.documaison.bll.interfaces.BllImplementation;
import hu.documaison.bll.interfaces.BllInterface;

public class Application {

	private static BllInterface bllInstance = null;
	
	public static BllInterface getBll() {
		if (bllInstance == null) {
			bllInstance = new BllImplementation();
		}
		return bllInstance;
	}
	
}
