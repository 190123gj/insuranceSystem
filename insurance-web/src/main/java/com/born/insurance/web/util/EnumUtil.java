/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */

package com.born.insurance.web.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename EnumUtil.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2012-5-30</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@SuppressWarnings("unchecked")
public class EnumUtil {
	
	private static final Logger logger = Logger.getLogger(EnumUtil.class);
	private static final String GET_METHOD_PREFIX = "get";
	final static Map<String, Class<Enum>> classMap = new HashMap<String, Class<Enum>>();
	/** 定义被扫描的enum,填到包名即可 */
	static String[] enumPackages = { "com.yjf.common.lang.enums.", "com.yjf.common.service.enums.",
									"com.born.fcs.pm.ws.enums." };
	
	/**
	 * 获取枚举Class，支持传入参数enum全路径，用来防止多个enum同个名字。<br>
	 * 
	 * @param className
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class<Enum> getJDKEnumClass(String className) {
		Class<Enum> clazz = null;
		clazz = classMap.get(className);
		if (clazz != null)
			return clazz;
		for (String enumPackage : enumPackages) {
			try {
				if (StringUtil.contains(className, ".")) {// 针对传全路径类名
					clazz = (Class<Enum>) Class.forName(className);
					if (clazz != null && clazz.isInstance(Enum.class)) {
						return clazz;
					}
				} else {// 针对类名
					clazz = (Class<Enum>) Class.forName(enumPackage + className);
					if (clazz != null && clazz.isInstance(Enum.class)) {
						return clazz;
					}
				}
			} catch (Exception e) {
				
			}
		}
		if (clazz != null) {
			synchronized (classMap) {
				Class<Enum> temp = classMap.get(className);
				if (temp == null) {
					classMap.put(className, clazz);
				}
			}
		}
		return clazz;
	}
	
	/**
	 * 获取枚举中的对象列表
	 * @param enumClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getEnumList(Class enumClass) {
		return new ArrayList(EnumSet.allOf(enumClass));
	}
	
	/**
	 * 获取枚举中的对象列表
	 * 
	 * @param enumName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getEnumList(String enumName) {
		return new ArrayList(EnumSet.allOf(getJDKEnumClass(enumName)));
	}
	
	@SuppressWarnings("rawtypes")
	public static Map getEnumMap(Class enumClass) {
		HashMap map = new HashMap();
		Iterator itr = EnumUtils.iterator(enumClass);
		while (itr.hasNext()) {
			Enum enm = (Enum) itr.next();
			map.put(enm.name(), enm);
		}
		return map;
	}
	
	/**
	 * 获取JDK ENUM 信息
	 * @param className 类名
	 * @param compareProperty 比较属性名称
	 * @param messageProperty 输出信息属性名称
	 * @param compareValue 比较值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getJDKEnumMessage(String className, String compareProperty,
											String messageProperty, String compareValue) {
		Class<Enum> enumClass = getJDKEnumClass(className);
		return getJDKEnumMessage(enumClass, compareProperty, messageProperty, compareValue);
	}
	
	public static <E extends Enum<E>> String getJDKEnumMessage(Class<E> enumClass,
																String compareProperty,
																String messageProperty,
																String compareValue) {
		if (enumClass == null || StringUtil.isBlank(messageProperty)) {
			return null;
		}
		
		Method getCompareProperty = ReflectionUtils.findMethod(enumClass,
			propertyGetName(compareProperty), new Class[] {});
		
		Method getMessageProperty = ReflectionUtils.findMethod(enumClass,
			propertyGetName(messageProperty), new Class[] {});
		
		if (getCompareProperty == null || getMessageProperty == null) {
			return null;
		}
		
		// 遍历比较enum值
		try {
			E[] enumValues = enumClass.getEnumConstants();
			for (E enumValue : enumValues) {
				String comparePropertyValue = (String) getCompareProperty.invoke(enumValue,
					new Object[] {});
				
				if (StringUtil.equals(comparePropertyValue, compareValue)) {
					return (String) getMessageProperty.invoke(enumValue, new Object[] {});
				}
			}
		} catch (Exception e) {
			// do nothing
			logger.warn("获取枚举信息异常！" + e);
		}
		
		return null;
	}
	
	public static String getEnumByCode(String className, String code) {
		
		return getJDKEnumMessage(className, "code", "message", code);
	}
	
	public static String getEnumByMessage(String className, String message) {
		
		return getJDKEnumMessage(className, "message", "code", message);
	}
	
	/**
	 * 获取属性方法名
	 * @param propertyName
	 * @return
	 */
	private static String propertyGetName(String propertyName) {
		return GET_METHOD_PREFIX + StringUtil.substring(propertyName, 0, 1).toUpperCase()
				+ StringUtil.substring(propertyName, 1, propertyName.length());
	}
	
	public static boolean isNull(Enum<?> enumObject) {
		if (enumObject == null)
			return true;
		else
			return false;
	}
}
