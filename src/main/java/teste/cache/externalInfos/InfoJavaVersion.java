package teste.cache.externalInfos;

public class InfoJavaVersion implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return "JVM 1.8";
	}

}
