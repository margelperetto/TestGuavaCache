package teste.cache.externalinfos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;

public class ExternalInfoCacheManager<T> {

	private Map<Object, LoadingCache<Class<? extends ExternalInfo<T>>, T>> mapCacheByContext = new HashMap<>();
	private Class<? extends ExternalInfo<T>> externalInfoClass;
	private Long cacheDurationMillis;
	private boolean cacheByContext;

	public ExternalInfoCacheManager(Class<? extends ExternalInfo<T>> externalInfoClass) {
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
	
	public boolean isEternalCache(){
		return cacheDurationMillis == null;
	}
	
	public void setCacheByContext(boolean cacheByContext){
		this.cacheByContext = cacheByContext;
	}
	
	public Class<? extends ExternalInfo<T>> getExternalInfoClass() {
		return externalInfoClass;
	}

	public T getCachedInfo() throws RuntimeException{
		try {
			return  getLoadingCache().get(externalInfoClass);
		} catch (InvalidCacheLoadException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Erro to get cached info!",e);
		}
	}

	public T getInfoFromRefreshedCache() throws RuntimeException{
		getLoadingCache().refresh(externalInfoClass);
		return getCachedInfo();
	}

	private synchronized LoadingCache<Class<? extends ExternalInfo<T>>, T> getLoadingCache(){
		Object context;

		if( cacheByContext ) {
			context = getContext();
		} else {
			context = "GLOBAL_CACHE_CONTEXT";
		}

		if( !mapCacheByContext.containsKey(context) ) {
			CacheBuilder<Object,Object> builder = CacheBuilder.newBuilder();
			if(cacheDurationMillis!=null){
				builder.expireAfterWrite(cacheDurationMillis, TimeUnit.MILLISECONDS);
			}
			LoadingCache<Class<? extends ExternalInfo<T>>, T> newLoadingCache = builder.build(new ExternalInfoCacheLoader());
			mapCacheByContext.put(context, newLoadingCache);
		}

		return mapCacheByContext.get(context);
	}

	//mock
	protected Object getContext() {
//		return ResourceManager.get().get(com.softexpert.platform.resource.ResourceType.SAAS_CONTEXT);
		return "CONTEXT";
	}

	class ExternalInfoCacheLoader extends CacheLoader<Class<? extends ExternalInfo<T>>, T> {
		@Override
		public T load(Class<? extends ExternalInfo<T>> externalInfoClass) throws RuntimeException{
			try {
				return externalInfoClass.newInstance().retrieveExternalInfo();
			} catch (Throwable e) {
				throw new RuntimeException("Erro to load external info!", e);
			}
		}
	}

}
