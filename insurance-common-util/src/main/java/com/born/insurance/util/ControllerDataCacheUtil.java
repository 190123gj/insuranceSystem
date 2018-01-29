package com.born.insurance.util;

import java.util.HashMap;
import java.util.Map;

public class ControllerDataCacheUtil {
	
	private static Map<String, Object> staticMap = new HashMap<String, Object>();
	
	/**
	 * 更新缓存中的值
	 * @param key
	 * @param value
	 */
	public synchronized static void setValue(String key, Object value) {
		staticMap.put(key, value);
		staticMap.put(key + "_lastTime", System.currentTimeMillis());
	}
	
	/**
	 * 从缓存中取值
	 * @param key
	 * @return
	 */
	public synchronized static Object getValue(String key) {
		return staticMap.get(key);
	}
	
	/**
	 * 距离上次更新时间
	 * @param key
	 * @return
	 */
	public synchronized static long getTimeSinceUpdate(String key) {
		Long lastTime = (Long) staticMap.get(key + "_lastTime");
		if (lastTime == null) {
			lastTime = (long) 0;
		}
		long intervalTime = System.currentTimeMillis() - lastTime;
		return intervalTime;
	}
	
	/**
	 * 对应KEY对应的值，是否已经超过规定的秒数，需要重新更新该值
	 * @param key
	 * @param secends 具体超过多少秒后需要更新
	 * @return true：已经到时间，需要更新；false：暂不需更新
	 */
	public synchronized static boolean needUpdateNow(String key, long secends) {
		long intervalTime = ControllerDataCacheUtil.getTimeSinceUpdate(key);
		Object lastValue = ControllerDataCacheUtil.getValue(key);
		if (intervalTime <= 1000 * secends && lastValue != null) {
			return false;
		} else {
			staticMap.put(key + "_lastTime", System.currentTimeMillis());
			return true;
		}
		
	}
}
