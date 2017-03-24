package teste.cache.externalInfos;

public class InfoOSName implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return "win10";
	}

}
