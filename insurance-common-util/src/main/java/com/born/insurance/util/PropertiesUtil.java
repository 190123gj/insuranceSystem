package com.born.insurance.util;

import java.io.IOException;
import java.util.Properties;

import com.yjf.common.env.Env;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class PropertiesUtil {
	static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties p = null;
	private static Properties commonProps = null;
	/*
	public static String getProperty(String key) {
		if (p == null) {
			p = new Properties();
			try {
				System.out.println("当前环境为：" + Env.getEnv());
				p.load(PropertiesUtil.class.getResourceAsStream("/spring/sys." + Env.getEnv()
																+ ".config"));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return (String) p.get(key);
	}
	*/ 
	public static String getCommonConfigProperty(String key) {
		if (commonProps == null) {
			commonProps = new Properties();
			try {
				commonProps.load(PropertiesUtil.class.getResourceAsStream("/common-config-"
																			+ Env.getEnv()
																			+ ".properties"));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return (String) commonProps.get(key);
	}
	
/*	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "online");
		System.out.println(getCommonConfigProperty("u.yrd.mail.senderNickName"));
	}*/
}
