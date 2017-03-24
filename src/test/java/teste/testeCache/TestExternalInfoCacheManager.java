package teste.testeCache;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import teste.cache.externalInfos.InfoCurrentTimeMillis;
import teste.cache.manager.ExternalInfoCacheManager;
import teste.cache.manager.ExternalInfoCacheManagerBuilder;

public class TestExternalInfoCacheManager {

	@Test
	public void testCacheDuration() throws Exception{
		
		ExternalInfoCacheManager manager = new ExternalInfoCacheManagerBuilder(InfoCurrentTimeMillis.class)
				.cacheDurationMillis(3_000l)
				.build();
		
		Object info = manager.getCachedInfo();
		
		Thread.sleep(1000);
		
		MatcherAssert.assertThat(info, Matchers.equalTo(manager.getCachedInfo()));

		Thread.sleep(2000);
		
		MatcherAssert.assertThat(info, Matchers.not(manager.getCachedInfo()));
		
	}
	
	@Test
	public void testCacheForceRealInfo() throws Exception{
		
		ExternalInfoCacheManager manager = new ExternalInfoCacheManagerBuilder(InfoCurrentTimeMillis.class)
				.cacheDurationMillis(3_000l)
				.build();
		
		Object info = manager.getCachedInfo();
		
		Thread.sleep(1000);
		
		Object realInfo = manager.getInfoFromRefreshedCache();
		
		MatcherAssert.assertThat(info, Matchers.not(realInfo));
		
		Thread.sleep(2000);
		
		MatcherAssert.assertThat(realInfo, Matchers.equalTo(manager.getCachedInfo()));
		
	}
	
	@Test
	public void testCacheWithoutDuration() throws Exception{
		
		ExternalInfoCacheManager manager = new ExternalInfoCacheManagerBuilder(InfoCurrentTimeMillis.class)
				.build();
		
		Object info = manager.getCachedInfo();
		
		Thread.sleep(1000);
		
		MatcherAssert.assertThat(info, Matchers.equalTo(manager.getCachedInfo()));
		
		Thread.sleep(1000);
		
		Object realInfo = manager.getInfoFromRefreshedCache();
		
		MatcherAssert.assertThat(info, Matchers.not(realInfo));
		
		Thread.sleep(1000);
		
		MatcherAssert.assertThat(realInfo, Matchers.equalTo(manager.getCachedInfo()));
	}
	
}