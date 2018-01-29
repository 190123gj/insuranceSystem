package com.born.insurance.web.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

public class BeanUtil {
	
	public static Map<String, String> beanToMap(Object bean) {
		Map<String, String> resMap = new HashMap<>();
		Map<String, String> map = BeanMap.create(bean);
		Object sMap[] = map.keySet().toArray();
		for (int i = 0; i < map.size(); i++) {
			String key = (String) sMap[i];
			Object value = map.get(sMap[i]);
			if (value != null) {
				String class0 = String.valueOf(value.getClass());
				if (class0.toLowerCase().indexOf("date") > -1) {
					resMap.put(key, DateUtil.dtSimpleFormat((Date) value));
				} else if (class0.toLowerCase().indexOf("money") > -1) {
					resMap.put(key, ((Money) value).toStandardString());
				} else {
					resMap.put(key, String.valueOf(value));
				}
				
			} else {
				resMap.put(key, "");
			}
			
		}
		return resMap;
		
	}
	
	//格式换数据
	public static Map<String, String> transData(Map<String, Object> map0) {
		Map<String, String> resMap = new HashMap<>();
		Object sMap[] = map0.keySet().toArray();
		for (int i = 0; i < map0.size(); i++) {
			String key = (String) sMap[i];
			Object value = map0.get(sMap[i]);
			if (value != null) {
				String class0 = String.valueOf(value.getClass());
				if (class0.toLowerCase().indexOf("date") > -1) {
					resMap.put(key, DateUtil.dtSimpleFormat((Date) value));
				} else if (class0.toLowerCase().indexOf("money") > -1) {
					resMap.put(key, ((Money) value).toStandardString());
				} else {
					resMap.put(key, String.valueOf(value));
				}
				
			} else {
				resMap.put(key, "");
			}
			
		}
		return resMap;
	}
}
