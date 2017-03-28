package teste.cache.externalinfos;

public class ExternalInfos {

	public static final ExternalInfoCacheManager<String> HOST = build(InfoHost.class, 1_000);
	public static final ExternalInfoCacheManager<String> JAVA_ARCH = build(InfoJavaArch.class, 5_000, true);
	public static final ExternalInfoCacheManager<String> OS_NAME = build(InfoOSName.class, true);
	
	public static final ExternalInfoCacheManager<?>[] INFOS = new ExternalInfoCacheManager[]{
			HOST, JAVA_ARCH, OS_NAME
	};

	private static <T> ExternalInfoCacheManager<T> build(Class<? extends ExternalInfo<T>> externalInfoClass){
		return new ExternalInfoCacheManager<T>(externalInfoClass);
	}
	
	private static <T> ExternalInfoCacheManager<T> build(Class<? extends ExternalInfo<T>> externalInfoClass, long cacheDurationMillis){
		ExternalInfoCacheManager<T> manager = build(externalInfoClass);
		manager.setCacheDurationMillis(cacheDurationMillis);
		return manager;
	}
	
	private static <T> ExternalInfoCacheManager<T> build(Class<? extends ExternalInfo<T>> externalInfoClass, long cacheDurationMillis, boolean cacheByContext){
		ExternalInfoCacheManager<T> manager = build(externalInfoClass);
		manager.setCacheDurationMillis(cacheDurationMillis);
		manager.setCacheByContext(cacheByContext);
		return manager;
	}
	
	private static <T> ExternalInfoCacheManager<T> build(Class<? extends ExternalInfo<T>> externalInfoClass, boolean cacheByContext){
		ExternalInfoCacheManager<T> manager = build(externalInfoClass);
		manager.setCacheByContext(cacheByContext);
		return manager;
	}
	
}