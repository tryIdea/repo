package com.kolong.tongji.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Params {
	private static Logger logger = Logger.getLogger(Params.class); 
	private static Properties p = new Properties();
	static {
		try {
			InputStream is = Params.class.getClassLoader().getResourceAsStream("params.properties");
			p.load(is);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static double get(String name) {
		return Double.parseDouble(p.getProperty(name));
	}
	
}
