package teste.cache.externalInfos;

import teste.context.FakeContextManager;

public class InfoFakeContextName implements ExternalInfo{

	@Override
	public Object retrieveExternalInfo() {
		return FakeContextManager.getContextName();
	}

}
