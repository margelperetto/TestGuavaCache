package teste.cache.externalinfos;

public class InfoJavaArch implements ExternalInfo<String>{

	@Override
	public String retrieveExternalInfo(Object...params) throws Exception {
		return  System.getProperty("sun.arch.data.model");
	}

}
