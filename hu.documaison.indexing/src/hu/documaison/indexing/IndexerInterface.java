package hu.documaison.indexing;

import hu.documaison.indexing.interceptor.IndexerInterceptorDispatcher;

public interface IndexerInterface {
	public void refresh();

	public void setDispatcher(IndexerInterceptorDispatcher dispatcher);
}
