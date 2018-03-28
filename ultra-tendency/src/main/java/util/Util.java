package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
public class Util {

	//private constructor to avoid construction
	private Util(){}
	
	private static PropertyUtil propMgr = PropertyUtil.getInstance();
	

	public static Optional<String> getProperty(String propertyKey) {
		return Optional.ofNullable(propMgr.getPropertyAsString(propertyKey));
	}
	
	public static Properties getProperties(){ return propMgr.getProperties(); }
	
	
	private static class PropertyUtil{
		private Properties property;
		private static PropertyUtil instance;
		private PropertyUtil(){
			try (InputStream is = Util.class.getResourceAsStream("/application.properties");) {
				Properties prop = new Properties();
				prop.load(is);
				this.property=prop;
			} catch (IOException e) {
				//TODO
				e.printStackTrace();
			}
		}
		
		public Properties getProperties(){ return property; }
		public String getPropertyAsString(String propIdentifier){
			return property.getProperty(propIdentifier);
		}
		
		//TODO add synchronizationce
		public static PropertyUtil getInstance(){
			if(instance == null)
				instance = new PropertyUtil();
			return instance;
		}		
	}
}
