package common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;



/**
 * 
 * 读取properties文件中的value
 *
 */
public class PropertiesUtils {
	
	public static String getProperty(String proName){
		String proValue = "";
		Properties prop =  new  Properties();
		try {
	         InputStream in = new BufferedInputStream (new FileInputStream("settings.properties"));
	         prop.load(in);
	         proValue = prop.getProperty (proName);
	            return proValue;
	        } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	        }
	    }

}
