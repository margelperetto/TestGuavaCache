package teste.cache.externalinfos;

import java.net.InetAddress;

public class InfoHost implements ExternalInfo<String>{

	@Override
	public String retrieveExternalInfo(Object...params) throws Throwable {
		return InetAddress.getLocalHost().getHostName();
	}

}
