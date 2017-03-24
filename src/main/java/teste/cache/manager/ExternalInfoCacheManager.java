package teste.cache.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import teste.cache.externalInfos.ExternalInfo;
import teste.context.FakeContextManager;

public class ExternalInfoCacheManager {

	private Map<String, LoadingCache<Class<? extends ExternalInfo>, Object>> mapCacheByContext = new HashMap<>();
	private Class<? extends ExternalInfo> externalInfoClass;
	private Long cacheDurationMillis;
	private boolean cacheByContext;

	public ExternalInfoCacheManager(Class<? extends ExternalInfo> externalInfoClass) {
		this.externalInfoClass = externalInfoClass;
	}

	public Long getCacheDurationMillis(){
		return cacheDurationMillis;
	}
	
	public void setCacheDurationMillis(Long cacheDurationMillis) {
		this.cacheDurationMillis = cacheDurationMillis;	
	}

	public boolean isCacheByContext(){
		return cacheByContext;
	}
	
	public void setCacheByContext(boolean cacheByContext){
		this.cacheByContext = cacheByContext;
	}

	public Object getCachedInfo() throws Exception{
		Object cachedInfo = null;
		cachedInfo =  getLoadingCache().get(externalInfoClass);
		return cachedInfo;
	}

	public Object getInfoFromRefreshedCache() throws Exception{
		getLoadingCache().refresh(externalInfoClass);
		return getCachedInfo();
	}

	private synchronized LoadingCache<Class<? extends ExternalInfo>, Object> getLoadingCache(){
		String contextName;

		if( cacheByContext ) {
			contextName = FakeContextManager.getContextName();
		} else {
			contextName = "GLOBAL";
		}

		if( !mapCacheByContext.containsKey(contextName) ) {
			CacheBuilder<Object,Object> builder = CacheBuilder.newBuilder();
			if(cacheDurationMillis!=null){
				builder.expireAfterWrite(cacheDurationMillis, TimeUnit.MILLISECONDS);
			}
			LoadingCache<Class<? extends ExternalInfo>, Object> newLoadingCache = builder.build(new ExternalInfoCacheLoader());
			mapCacheByContext.put(contextName, newLoadingCache);
		}

		return mapCacheByContext.get(contextName);
	}

	class ExternalInfoCacheLoader extends CacheLoader<Class<? extends ExternalInfo>, Object> {
		@Override
		public Object load(Class<? extends ExternalInfo> externalInfoClass) throws Exception {
			return externalInfoClass.newInstance().retrieveExternalInfo();
		}
	}

}
