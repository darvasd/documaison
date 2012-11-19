package hu.documaison.indexing.interceptor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Dispatcher for Interceptor pattern.
 */
public class IndexerInterceptorDispatcher {
	private Collection<IndexerInterceptor> interceptors = new ArrayList<IndexerInterceptor>();
	
	public void register(IndexerInterceptor interceptor){
		this.interceptors.add(interceptor);
	}
	
	public void unregister(IndexerInterceptor interceptor){
		this.interceptors.remove(interceptor);
	}
	
	public Collection<IndexerInterceptor> getInterceptors(){
		return this.interceptors;
	}
}
