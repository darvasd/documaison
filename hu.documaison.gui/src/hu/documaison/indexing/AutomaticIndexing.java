package hu.documaison.indexing;

import hu.documaison.settings.SettingsManager;

import java.util.Timer;
import java.util.TimerTask;

public class AutomaticIndexing {
	
	private static IndexerCollection indexers;
	private static Timer timer;
	private static TimerTask task;
	
	
	public static void setIndexers(IndexerCollection ic) {
		indexers = ic;
	}
	
	public static IndexerCollection getIndexers() {
		return indexers;
	}
	
	public static void enableAutomaticIndexing() {
		timer = new Timer(true);
		task = new TimerTask() {
			
			@Override
			public void run() {
				indexers.refreshAll();
			}
		};
		timer.schedule(task, 300000, 300000);
	}
	
	public static void disableAutomaticIndexing() {
		if (timer != null) {
			timer.cancel();
		}
	}
	
	public static void initialize() {
		try {
			if (SettingsManager.getCurrentSettings().isIndexingEnabled()) {
				enableAutomaticIndexing();
			}
		} catch (Exception e) {
			//Do nothing.
		}
	}
	

}
