package teste.cache.externalInfos;

public class InfoHDTotalSpace implements ExternalInfo {

	@Override
	public Object retrieveExternalInfo() {
		return "1 TB";
	}

}
