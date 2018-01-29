package com.born.insurance.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class ConfigParamProperty extends PropertyPlaceholderConfigurer {
	
	protected static final Logger logger = LoggerFactory.getLogger(ConfigParamProperty.class);
	
	private static Map<String, Object> ctxPropertiesMap;
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
										Properties props) throws BeansException {
		try {
			super.processProperties(beanFactoryToProcess, props);
			ctxPropertiesMap = new HashMap<String, Object>();
			for (Object key : props.keySet()) {
				String keyStr = key.toString();
				String value = props.getProperty(keyStr);
				ctxPropertiesMap.put(keyStr, value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	/**
	 * 获取配置值
	 * @param key
	 * @return
	 */
	public static String getParamValue(String key) {
		
		try {
			if (ctxPropertiesMap.get(key) == null) {
				return "";
			}
			String value = (String) ctxPropertiesMap.get(key);
			return value;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "";
	}
	
	/**
	 * 获取当前系统名称
	 * @return
	 */
	public static String systemName() {
		return getParamValue("SYSTEM_NAME");
	}
	
	/**
	 * 是否同步项目相关人员到PM
	 * @return
	 */
	public static boolean syncProjectRelatedUser2Pm() {
		try {
			return "YES".equals(getParamValue("syncProjectRelatedUser2Pm")) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 是否同步项目相关人员到FM
	 * @return
	 */
	public static boolean syncProjectRelatedUser2Fm() {
		try {
			return "YES".equals(getParamValue("syncProjectRelatedUser2Fm")) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 是否同步项目相关人员到AM
	 * @return
	 */
	public static boolean syncProjectRelatedUser2Am() {
		try {
			return "YES".equals(getParamValue("syncProjectRelatedUser2Am")) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
