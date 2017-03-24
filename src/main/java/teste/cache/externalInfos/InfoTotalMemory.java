package teste.cache.externalInfos;

public class InfoTotalMemory implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return "8 GB";
	}

}
