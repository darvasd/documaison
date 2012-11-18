package hu.documaison.indexing;
import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;

public class IndexingFactory {
	public static IndexingInterface getIndexing(String folderPath,
			BllInterface bll) throws InvalidFolderException, InvalidParameterException {
		IndexingImpl impl = new IndexingImpl(folderPath, bll);
		return impl;
	}
}
