package teste.cache.manager;

import teste.cache.externalInfos.ExternalInfo;

public class ExternalInfoCacheManagerBuilder {

	private final ExternalInfoCacheManager cacheManager;
	
	public ExternalInfoCacheManagerBuilder(Class<? extends ExternalInfo> externalInfoClass) {
		cacheManager = new ExternalInfoCacheManager(externalInfoClass);
	}
	
	public ExternalInfoCacheManagerBuilder cacheDurationMillis(Long cacheDurationMillis){
		cacheManager.setCacheDurationMillis(cacheDurationMillis);
		return this;
	}
	
	public ExternalInfoCacheManagerBuilder cacheByContext(boolean cacheByContext){
		cacheManager.setCacheByContext(cacheByContext);
		return this;
	}
	
	public ExternalInfoCacheManager build(){
		return cacheManager;
	}
}
