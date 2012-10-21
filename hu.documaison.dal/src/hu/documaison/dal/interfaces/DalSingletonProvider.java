package hu.documaison.dal.interfaces;

public class DalSingletonProvider {
	private static DalImplementation impl = null;
	
	public static DalInterface getDalImplementation()
	{
		if (impl == null)
		{
			impl = new DalImplementation();
		}
		return impl;
	}
	
	private DalSingletonProvider() {}
}
