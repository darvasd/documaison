package hu.documaison.dal.interfaces;

import hu.documaison.dal.database.DatabaseUtils;

public class DalSingletonProvider {
	private static DalImplementation impl = null;
	
	public static DalInterface getDalImplementation()
	{
		if (impl == null)
		{
			// creating tables if not exists
			DatabaseUtils.createTablesBestEffort();
			
			impl = new DalImplementation();
		}
		return impl;
	}
	
	private DalSingletonProvider() {}
}
