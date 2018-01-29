package com.born.insurance.util;



import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jxl.common.Logger;

/**
 * @author liujunwei (liujunwei.cq@163.com) company:重庆市博恩软件公司 duty:project
 * manager name:刘军伟 time:2011-4-4 系统统一常量配置获取器。通过JAVA
 * Properties,我们可以将系统常量放到外部property文件中，如果常量值需要变动，则不需要改源码
 */
public class PropertyFactory {
	private static Logger logger = Logger.getLogger(PropertyFactory.class);
	
	public static Properties defaultProperty;
	public static Properties errorProperty;
	
	public static String defPropertyLoc = "default_property.properties";
	public static String errorPropertyLoc = "error_property.properties";
	
	private static Map<String, Properties> cacheMap = new HashMap<String, Properties>(); // 缓冲
	
	/**
	 * 读取默认文件
	 * @return
	 */
	//	public static Properties getDefaultPropertyPP() 
	//	{
	//		if(defaultProperty == null)
	//		{
	//			try 
	//			{
	//				logger.info("......根据文件名load属性defPro对象");
	//				defaultProperty = new Properties();
	////				defaultProperty.load(PropertyFactory.class.getClassLoader().getResourceAsStream(defPropertyLoc));
	//				defaultProperty.load(Resources.getResourceAsStream(defPropertyLoc));
	//			} 
	//			catch (Exception e) 
	//			{
	//				// TODO Auto-generated catch block
	//				logger.error("读取默认属性文件失败："+e);
	//				logger.error(e.getLocalizedMessage()+e);
	//				System.exit(0);
	//			}
	//		}
	//		
	//		return defaultProperty;
	//	}
	
	/**
	 * 捕获页面错误的如404、500的property文件
	 * @return
	 */
	//	public static Properties getErrorPropertiesPP()
	//	{
	//		if(errorProperty == null)
	//		{
	//			try 
	//			{
	//				logger.info("......根据文件名load属性errorPro对象");
	//				errorProperty = new Properties();
	//				errorProperty.load(Resources.getResourceAsStream(errorPropertyLoc));
	//			}
	//			catch (Exception e) 
	//			{
	//				// TODO Auto-generated catch block
	//				logger.error("读取错误属性文件失败："+e);
	//				logger.error(e.getLocalizedMessage()+e);
	//				System.exit(0);
	//			}
	//		}
	//		
	//		return errorProperty;
	//	}
	
	/**
	 * Get a properties by file name
	 * @param fileName
	 * @return
	 */
	//	public static Properties getPropertiesByFileNamePP(String fileName) 
	//	{
	//		Properties ret=cacheMap.get(fileName.toLowerCase());
	//		if(ret == null)
	//		{
	//			try 
	//			{
	//				logger.info("......根据文件名load属性对象>>"+fileName);
	//				ret = new Properties();
	//				ret.load(Resources.getResourceAsStream(fileName));
	//			}
	//			catch (Exception e) 
	//			{
	//				logger.error("根据文件名load属性对象失败："+e);
	//				logger.error(e.getLocalizedMessage()+e);
	//			}
	//			cacheMap.put(fileName.toLowerCase(), ret);
	//		}
	//		
	//		return ret;
	//	}
	
	/**
	 * get property
	 * @param fileName
	 * @param key
	 * @return
	 */
	//	public static String getPropertyPP(String fileName, String key) 
	//	{
	//		return getPropertiesByFileName(fileName).getProperty(key);
	//	}
	//	
	//
	//	public static Properties getSecurityPropertiesPP() 
	//	{
	//		return getPropertiesByFileName("security.properties");
	//	}
	
	//////////////
	//default_property.properties存储对象
	public static Map defaultPropertyMap = new HashMap();
	
	//security.properties
	public static Map securityPropertyMap = new HashMap();
	
	//member.permission.properties
	public static Map memberPermissionPropertyMap = new HashMap();
	//member.properties
	public static Map memberPropertyMap = new HashMap();
	
	//error_property.properties
	public static Map errorPropertyMap = new HashMap();
	
	//exceptmsg.properties
	public static Map excepMsgPropertyMap = new HashMap();
	
	//tiles.map.properties
	public static Map tilesMapPropertyMap = new HashMap();
	
	public static String cqncpPtype = null;
	
}
