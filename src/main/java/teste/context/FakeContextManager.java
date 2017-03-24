package teste.context;

public class FakeContextManager {

	private static final ThreadLocal<String> tr = new ThreadLocal<>();
	
	public static String getContextName(){
		String contextName = tr.get();
		if(contextName == null){
			contextName = "Context"+(int)(Math.random()*1000);
			tr.set(contextName);
		}
		return contextName;
	}
	
}