import hu.documaison.bll.interfaces.BllImplementation;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.indexing.IndexingFactory;
import hu.documaison.indexing.IndexingInterface;

public class IndexingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IndexingInterface indexer;
		try {
			indexer = IndexingFactory.getIndexing("W:\\dmtemp",
					new BllImplementation());
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			return;
		}

		indexer.refresh();
	}

}
