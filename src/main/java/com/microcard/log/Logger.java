/**
 * 
 */
package com.microcard.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author wuwei
 *
 */
public class Logger {
	
	private Log log;

	private Logger(Log log) {
		
		this.log = log;
		
	}
	
	static {
		
		try {
			InputStream in = Logger.class.getClassLoader().getResourceAsStream("log4j.properties");
			Properties p = new Properties();
			p.load(in);
			PropertyConfigurator.configure(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static HashMap<String,Logger> logMap = new HashMap<String,Logger>();
	
	public static Logger getOperLogger() {
		
		
		if(logMap.get("microcard.oper") == null) {
			synchronized(Logger.class) {
				if(logMap.get("microcard.oper") != null)
					return logMap.get("microcard.oper");
				
				Log log = LogFactory.getLog("microcard.oper");
				Logger logger = new Logger(log);	
				logMap.put("microcard.oper", logger);
				
			}
	
		}
		
		return logMap.get("microcard.oper"); 
		
	}
	
	public static Logger getMsgLogger() {
		if(logMap.get("microcard.msg") == null) {
			synchronized(Logger.class) {
				if(logMap.get("microcard.msg") != null)
					return logMap.get("microcard.msg");
				
				Log log = LogFactory.getLog("microcard.msg");
				Logger logger = new Logger(log);	
				logMap.put("microcard.msg", logger);
				
			}
	
		}
		
		return logMap.get("microcard.msg"); 
	}
	
	
	
	public void debug(String msg) {
		
		log.debug(msg);
		
	}
	
	public void warn(String msg) {
		
		log.warn(msg);
		
	}
	
	public void info(String msg) {
		
		log.info(msg);
		
	}
	
	public void error(Throwable e , String msg) {
		
		log.error(msg, e);
		
	}
	
	public void error(String msg) {
		
		log.error(msg);
	}
	
}
