package hu.documaison.indexing;

import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.SettingsAccessException;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

public class IndexerFactory {
	public static IndexerInterface getIndexing(String folderPath,
			String currentComputerId, BllInterface bll)
			throws InvalidFolderException, InvalidParameterException {
		IndexerImpl impl = new IndexerImpl(folderPath, currentComputerId, bll);
		return impl;
	}
	
	public static IndexerCollection getAllIndexers(BllInterface bll) throws SettingsAccessException, InvalidFolderException, InvalidParameterException{
		// load settings
		SettingsData settings;
		try {
			settings = SettingsManager.getCurrentSettings();
		} catch (Exception e) {
			throw new SettingsAccessException();
		}
		String computerId = (settings != null ? settings.getComputerId() : "");

		// create indexer collection
		IndexerCollection indexerColl = new IndexerCollection();
		for (String folder : settings.getIndexedFolders()){
			indexerColl.addIndexer(getIndexing(folder, computerId, bll));
		}
		return indexerColl;
	}
}
