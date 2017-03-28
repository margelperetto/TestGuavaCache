package teste.cache.externalinfos;

public interface ExternalInfo<T> {

	T retrieveExternalInfo(Object... params) throws Throwable;
	
}