package hu.documaison.indexing;

import hu.documaison.indexing.interceptor.IndexerInterceptorDispatcher;

import java.util.ArrayList;
import java.util.Collection;

public class IndexerCollection {
	private Collection<IndexerInterface> indexers = new ArrayList<IndexerInterface>();
	private IndexerInterceptorDispatcher dispatcher = new IndexerInterceptorDispatcher();
	
	public void addIndexer(IndexerInterface indexer){
		this.indexers.add(indexer);
		indexer.setDispatcher(dispatcher);
	}

	public IndexerInterceptorDispatcher getDispatcher(){
		return this.dispatcher;
	}
	
	public void refreshAll(){
		for (IndexerInterface indexer : indexers){
			indexer.refresh();
		}
	}
}
