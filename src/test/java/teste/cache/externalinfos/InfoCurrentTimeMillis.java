package teste.cache.externalinfos;

import teste.cache.externalinfos.ExternalInfo;

public class InfoCurrentTimeMillis implements ExternalInfo<Long>{

	@Override
	public Long retrieveExternalInfo(Object...params) {
		return System.currentTimeMillis();
	}

}
