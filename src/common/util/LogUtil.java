package common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */
public class LogUtil {
	static {
		String properties = LogUtil.class.getClassLoader().getResource("log4j.properties").getPath();
		PropertyConfigurator.configure(properties);
	}
	
	public static void fatal(Class<?> cl, Object msg){
		Logger.getLogger(cl).fatal(msg);
	}
	
	public static void trace(Class<?> cl, Object msg){
		Logger.getLogger(cl).trace(msg);
	}
	
	public static void info(Class<?> cl, Object msg){
		Logger.getLogger(cl).info(msg);
	}
	
	public static void debug(Class<?> cl, Object msg){
		Logger.getLogger(cl).debug(msg);
	}
	
	public static void err(Class<?> cl, Object msg){
		Logger.getLogger(cl).error(msg);
	}
	
	public static void warn(Class<?> cl, Object msg){
		Logger.getLogger(cl).warn(msg);
	}
	
	public static void console(Class<?> cl, Object msg){
		System.out.println(msg);
	}

//	public static void main(String[] args) {
//		LogUtil.info(LogUtil.class, "--------");
//	}
}
