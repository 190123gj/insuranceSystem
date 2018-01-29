package com.born.insurance.ws.info.common;

import java.util.Map;

/**
 * 
 * 用于返回集合对象
 * 
 * @author lirz
 * 
 * 2016-6-15 上午10:50:28
 */
public class CollectionInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> map;
	
	public Object get(String key) {
		if (null != map) {
			return map.get(key);
		}
		
		return null;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
