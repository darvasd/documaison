import hu.documaison.bll.interfaces.BllImplementation;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.indexing.IndexerFactory;
import hu.documaison.indexing.IndexerInterface;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

public class IndexingTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		SettingsData settings;
		try {
			settings = SettingsManager.getCurrentSettings();
		} catch (Exception e1) {
			settings = null;
		}

		String computerId = (settings != null ? settings.getComputerId() : "");

		System.out.println("Computer ID: " + computerId);

		BllImplementation bll = new BllImplementation();
		DocumentType documentType = bll.createDocumentType();
		documentType.setDefaultExt("txt");
		documentType.setTypeName("Text File");
		bll.updateDocumentType(documentType);
		
		IndexerInterface indexer;
		try {
			indexer = IndexerFactory.getIndexing("W:\\dmtemp", computerId, 
					new BllImplementation());
		} catch (InvalidParameterException e) {
			e.printStackTrace();
			return;
		}

		indexer.refresh();
		
		// get all docs
		System.out.println();
		System.out.println("Documents in database:");
		for (Document doc : bll.getDocuments()){
			System.out.println(doc.getLocation());
		}
	}

}
