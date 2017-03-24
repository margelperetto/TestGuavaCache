package teste.cache.externalInfos;

public class InfoIpAddress implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return "192.168.2.1";
	}

}
