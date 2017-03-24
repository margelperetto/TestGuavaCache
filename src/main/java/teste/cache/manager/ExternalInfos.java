package teste.cache.manager;

import teste.cache.externalInfos.ExternalInfo;
import teste.cache.externalInfos.InfoCPUName;
import teste.cache.externalInfos.InfoFakeContextName;
import teste.cache.externalInfos.InfoHDTotalSpace;
import teste.cache.externalInfos.InfoIpAddress;
import teste.cache.externalInfos.InfoJavaVersion;
import teste.cache.externalInfos.InfoMacAddress;
import teste.cache.externalInfos.InfoOSName;
import teste.cache.externalInfos.InfoTotalMemory;
import teste.cache.externalInfos.InfoCurrentTimeMillis;

public enum ExternalInfos {

	OS_NAME			(InfoOSName.class, 3_000), 
	TOTAL_MEM		(InfoTotalMemory.class, 5_000), 
	JAVA_VERSION	(InfoJavaVersion.class, 8_000), 
	IP_ADDRESS		(InfoIpAddress.class, 9_000), 
	MAC_ADDRESS		(InfoMacAddress.class, true), 
	HD_TOTAL_SPACE	(InfoHDTotalSpace.class, 11_000, true),
	CPU_NAME		(InfoCPUName.class),
	CONTEXT_NAME	(InfoFakeContextName.class, 1_000, true),
	SYSTEM_TIME		(InfoCurrentTimeMillis.class, 0);

	private ExternalInfoCacheManager cacheManager;

	private ExternalInfos(Class<? extends ExternalInfo> externalInfoClass) {
		cacheManager = new ExternalInfoCacheManagerBuilder(externalInfoClass).build();
	}
	
	private ExternalInfos(Class<? extends ExternalInfo> externalInfoClass, long cacheDurationMillis) {
		cacheManager = new ExternalInfoCacheManagerBuilder(externalInfoClass).cacheDurationMillis(cacheDurationMillis).build();
	}
	
	private ExternalInfos(Class<? extends ExternalInfo> externalInfoClass, boolean cacheByContext) {
		cacheManager = new ExternalInfoCacheManagerBuilder(externalInfoClass).cacheByContext(cacheByContext).build();
	}
	
	private ExternalInfos(Class<? extends ExternalInfo> externalInfoClass, long cacheDurationMillis, boolean cacheByContext) {
		cacheManager = new ExternalInfoCacheManagerBuilder(externalInfoClass).cacheDurationMillis(cacheDurationMillis).cacheByContext(cacheByContext).build();
	}
	
	private ExternalInfos(ExternalInfoCacheManagerBuilder builder) {
		cacheManager = builder.build();
	}
	
	public ExternalInfoCacheManager getCacheManager() {
		return cacheManager;
	}

}