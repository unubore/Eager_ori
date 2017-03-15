package com.eager.core.constract;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridProperties {
	private static Logger logger = LoggerFactory
			.getLogger(GridProperties.class);
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(GridProperties.class.getClassLoader()
					.getResourceAsStream("grid.properties"));
		} catch (IOException e) {
			logger.error("加载grid.properties出错！");
		}
	}

	public static String getKey(String key) {
		return properties.getProperty(key);
	}
	
	public static final long SESSION_TIME_OUT = Long
			.valueOf(properties.getProperty("sessionTimeOut"));
}
