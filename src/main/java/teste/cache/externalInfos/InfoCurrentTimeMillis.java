package teste.cache.externalInfos;

public class InfoCurrentTimeMillis implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return System.currentTimeMillis();
	}

}
