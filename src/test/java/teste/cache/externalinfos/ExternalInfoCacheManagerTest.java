package teste.cache.externalinfos;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import teste.cache.externalinfos.ExternalInfoCacheManager;

public class ExternalInfoCacheManagerTest {

	@Before
	public void startUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCachedInfo() throws Exception{
		
		long cacheDurationMillis = 500;
		
		ExternalInfoCacheManager<Long> manager = new ExternalInfoCacheManager<>(InfoCurrentTimeMillis.class);
		manager.setCacheDurationMillis(cacheDurationMillis);
		
		Long info = manager.getCachedInfo();
		
		Thread.sleep(cacheDurationMillis / 2);
		
		MatcherAssert.assertThat(info, Matchers.equalTo(manager.getCachedInfo()));
	}
	
	@Test
	public void testCacheDurationExpired() throws Exception{
		
		long cacheDurationMillis = 500;
		
		ExternalInfoCacheManager<Long> manager = new ExternalInfoCacheManager<>(InfoCurrentTimeMillis.class);
		manager.setCacheDurationMillis(cacheDurationMillis);
		
		Long info = manager.getCachedInfo();
		
		Thread.sleep(cacheDurationMillis + 1);
		
		MatcherAssert.assertThat(info, Matchers.not(manager.getCachedInfo()));
	}
	
	@Test
	public void testInfoFromRefreshedCacheIsDifferentOfPreviousInfoAndUpdateCachedInfo() throws Exception{
		
		ExternalInfoCacheManager<Long> manager = new ExternalInfoCacheManager<>(InfoCurrentTimeMillis.class);
		
		Long previousInfo = manager.getCachedInfo();
		Thread.sleep(100);
		Long refreshedInfo = manager.getInfoFromRefreshedCache();
		Thread.sleep(100);
		Long currentCachedInfo = manager.getCachedInfo();
		
		MatcherAssert.assertThat(previousInfo, Matchers.not(refreshedInfo));
		MatcherAssert.assertThat(currentCachedInfo, Matchers.equalTo(refreshedInfo));
	}
	
	@Test
	public void testCacheWithoutDuration() throws Exception{
		
		ExternalInfoCacheManager<Long> manager = new ExternalInfoCacheManager<>(InfoCurrentTimeMillis.class);
		
		Long info = manager.getCachedInfo();
		
		Thread.sleep(500);
		
		MatcherAssert.assertThat(info, Matchers.equalTo(manager.getCachedInfo()));
	}
	
	@Test
	public void testCacheByContextTrueIsNotNull() throws Exception{
		
		Object[] infos = getArrayOfTwoCachedInfos(true);
		
		MatcherAssert.assertThat(infos[0], Matchers.notNullValue());
		MatcherAssert.assertThat(infos[1], Matchers.notNullValue());
	}
	
	@Test
	public void testCacheByContextFalseIsNotNull() throws Exception{
		
		Object[] infos = getArrayOfTwoCachedInfos(false);
		
		MatcherAssert.assertThat(infos[0], Matchers.notNullValue());
		MatcherAssert.assertThat(infos[1], Matchers.notNullValue());
	}
	
	@Test
	public void testCacheByContextTrueContainsDifferentValues() throws Exception{
		
		Object[] infos = getArrayOfTwoCachedInfos(true);
		
		MatcherAssert.assertThat(infos[0], Matchers.not(infos[1]));
	}
	
	@Test
	public void testCacheByContextFalseContainsTheSameValue() throws Exception{
		
		Object[] infos = getArrayOfTwoCachedInfos(false);
		
		MatcherAssert.assertThat(infos[0], Matchers.equalTo(infos[1]));
	}
	
	private Object[] getArrayOfTwoCachedInfos(boolean cacheByContext) throws Exception{
		
		ExternalInfoCacheManager<Long> cacheManager = Mockito.spy(new ExternalInfoCacheManager<>(InfoCurrentTimeMillis.class));
		cacheManager.setCacheByContext(cacheByContext);
		
		Object[] infos = new Object[2];
		
		Mockito.doReturn("CONTEXTO1").when(cacheManager).getContext();
		infos[0] = cacheManager.getCachedInfo();
		Thread.sleep(200);
		Mockito.doReturn("CONTEXTO2").when(cacheManager).getContext();
		infos[1] = cacheManager.getCachedInfo();
		
		return infos;
	}
	
}