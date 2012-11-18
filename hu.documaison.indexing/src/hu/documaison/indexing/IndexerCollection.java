package hu.documaison.indexing;

import java.util.ArrayList;
import java.util.Collection;

public class IndexerCollection {
	private Collection<IndexerInterface> indexers = new ArrayList<IndexerInterface>();
	
	public void addIndexer(IndexerInterface indexer){
		this.indexers.add(indexer);
	}
	
	public void refreshAll(){
		for (IndexerInterface indexer : indexers){
			indexer.refresh();
		}
	}
}
