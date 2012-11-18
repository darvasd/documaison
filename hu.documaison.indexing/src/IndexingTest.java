import hu.documaison.bll.interfaces.BllImplementation;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.indexing.IndexerFactory;
import hu.documaison.indexing.IndexerInterface;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

public class IndexingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SettingsData settings;
		try {
			settings = SettingsManager.getCurrentSettings();
		} catch (Exception e1) {
			settings = null;
		}

		String computerId = (settings != null ? settings.getComputerId() : "");

		System.out.println("Computer ID: " + computerId);

		IndexerInterface indexer;
		try {
			indexer = IndexerFactory.getIndexing("W:\\dmtemp", computerId, 
					new BllImplementation());
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			return;
		}

		indexer.refresh();
	}

}
