package teste.cache.externalinfos;

public class InfoOSName implements ExternalInfo<String>{

	@Override
	public String retrieveExternalInfo(Object...params) {
		return System.getProperty("os.name");
	}

}
