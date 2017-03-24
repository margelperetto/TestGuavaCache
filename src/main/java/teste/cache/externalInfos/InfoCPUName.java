package teste.cache.externalInfos;

public class InfoCPUName implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return "Intel Core i7";
	}

}
