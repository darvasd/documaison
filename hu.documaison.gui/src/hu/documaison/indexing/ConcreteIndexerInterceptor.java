package hu.documaison.indexing;

import org.eclipse.swt.widgets.Display;

import hu.documaison.data.entities.Document;
import hu.documaison.gui.document.DocumentObserver;
import hu.documaison.indexing.interceptor.IndexerInterceptor;



public class ConcreteIndexerInterceptor extends IndexerInterceptor {

	@Override
	public void onDocumentAdded(Document document) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				DocumentObserver.notifyLister();
			}
		});
	}

	@Override
	public void onDocumentDeleted(int deletedDocumentId) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				DocumentObserver.notifyLister();
			}
		});
	}

}
