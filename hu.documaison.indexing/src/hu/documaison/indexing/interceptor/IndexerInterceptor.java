package hu.documaison.indexing.interceptor;

import hu.documaison.data.entities.Document;

/**
 * Interceptor class for indexers.
 */
public abstract class IndexerInterceptor {
	public void beforeDocumentAdded(String path) {
		// no default implementation
	}

	public void beforeDocumentDeleted(Document document) {
	}

	public void onDocumentAdded(Document document) {
	}

	public void onDocumentDeleted(int deletedDocumentId) {
	}
	
	public void onDocumentAddingError(String path) {
	}
}
