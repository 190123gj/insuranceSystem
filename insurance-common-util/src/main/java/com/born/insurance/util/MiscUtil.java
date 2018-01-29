package com.born.insurance.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ReflectionUtils;

import com.born.insurance.util.json.linkedJSONParser;
import com.yjf.common.lang.util.BeanCopyUtils;
import com.yjf.common.lang.util.Converter;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class MiscUtil {
	private static final Logger logger = LoggerFactory.getLogger(MiscUtil.class);
	
	private static Converter c = BeanCopyUtils
		.newIgnorePropertiesConverter(new String[] { "bizNo,biz_no" });
	
	public static void copyPoObject(Object po, Object srcPo) {
		copyPoObject(po, srcPo, false);
	}
	
	public static void copyPoObject(Object po, Object srcPo, boolean isNotCopyNull) {
		if (srcPo == null || po == null)
			return;
		//		BeanCopyUtils.copy(srcPo, po, c);
		
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(srcPo);
		for (int i = 0; i < pds.length; i++) {
			PropertyDescriptor pd = pds[i];
			
			PropertyDescriptor[] pds1 = PropertyUtils.getPropertyDescriptors(po);
			for (int j = 0; j < pds1.length; j++) {
				if (pds1[j].getName().equals(pd.getName())) {
					if (pd.getPropertyType().equals(pds1[j].getPropertyType())
						&& pds1[j].getWriteMethod() != null) {
						try {
							Object temp = PropertyUtils.getProperty(srcPo, pd.getName());
							if (isNotCopyNull && temp != null) {
								PropertyUtils.setProperty(po, pd.getName(), temp);
							} else if (!isNotCopyNull)
								PropertyUtils.setProperty(po, pd.getName(), temp);
							
						} catch (IllegalAccessException e) {
							logger.error("IllegalAccessException err", e);
						} catch (InvocationTargetException e) {
							logger.error("InvocationTargetException err", e);
						} catch (NoSuchMethodException e) {
							logger.error("NoSuchMethodException err", e);
						}
					}
					continue;
					
				}
				
			}
		}
		
	}
	
	public static <T, V> void convertList(List<T> listFrom, List<V> listTo, Class<V> classz) {
		if (listFrom != null) {
			for (T item : listFrom) {
				V vItem = null;
				try {
					vItem = classz.newInstance();
				} catch (InstantiationException e) {
					logger.error(e.getLocalizedMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
				if (vItem != null) {
					MiscUtil.copyPoObject(vItem, item);
					listTo.add(vItem);
				}
				
			}
		}
	}
	
	public static HashMap<String, Object> parseJSON(String json) {
		if (logger.isInfoEnabled()) {
			if (json != null && json.length() <= 500) {
				logger.info("..........parseJSON....json:" + json);
			} else if (json != null) {
				logger.info("..........parseJSON....json:" + json.substring(0, 500));
			}
			
		}
		
		try {
			if (json == null)
				return null;
			@SuppressWarnings("unchecked")
			HashMap<String, Object> o = (HashMap<String, Object>) new JSONParser().parse(json);
			return o;
		} catch (ParseException e) {
			logger.error("JSONParser json is Error", e);
		}
		return null;
	}
	
	public static com.alibaba.fastjson.JSONObject getJsonObjByParseJSON(String json) {
		if (logger.isInfoEnabled()) {
			if (json != null && json.length() <= 500) {
				logger.info("..........parseJSON....json:" + json);
			} else if (json != null) {
				logger.info("..........parseJSON....json:" + json.substring(0, 500));
			}
			
		}
		
		try {
			if (json == null)
				return null;
			@SuppressWarnings("unchecked")
			com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
				.parse(json);
			return o;
		} catch (Exception e) {
			logger.error("JSONParser json is Error", e);
		}
		return null;
	}
	
	public static LinkedHashMap<String, Object> parseJSONWithOrder(String json) {
		if (logger.isInfoEnabled())
			logger.info("..........parseJSON....json:" + json);
		try {
			if (json == null)
				return null;
			LinkedHashMap<String, Object> o = (LinkedHashMap<String, Object>) new linkedJSONParser()
				.parse(json);
			return o;
		} catch (ParseException e) {
			logger.error("JSONParser json is Error", e);
		}
		return null;
	}
	
	public static HashMap<String, Object> parseJSONWithException(String json) throws ParseException {
		if (logger.isInfoEnabled())
			logger.info("..........parseJSON....json:" + json);
		try {
			if (json == null)
				return null;
			@SuppressWarnings("unchecked")
			HashMap<String, Object> o = (HashMap<String, Object>) new JSONParser().parse(json);
			return o;
		} catch (ParseException e) {
			logger.error("JSONParser json is Error", e);
			throw new IllegalArgumentException("json转换失败： " + e.getMessage());
		}
	}
	
	public static JSONArray parseJSONArray(String json) {
		if (logger.isInfoEnabled())
			logger.info("..........parseJSON....json:" + json);
		try {
			if (json == null)
				return null;
			@SuppressWarnings("unchecked")
			JSONArray o = (JSONArray) new JSONParser().parse(json);
			return o;
		} catch (ParseException e) {
			logger.error("JSONParser json is Error", e);
		}
		return null;
	}
	
	public static String escape(String src) {
		if (StringUtil.isEmpty(src))
			return "";
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	public static Map<String, Object> covertPoToMapNoNull(Object po) {
		String propertyName = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					Object object = PropertyUtils.getProperty(po, propertyName);
					if (object != null) {
						dataMap.put(propertyName, object);
					}
					
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	public static String addJsonString(String memo, String memoKey, String memoValue) {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(memo))
			jsonMap = parseJSON(memo);
		
		jsonMap.put(memoKey, memoValue);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(jsonMap);
		return jsonObject.toJSONString();
	}
	
	public static <T> List<T> toList(T obj) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1, T obj2) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		temp.add(obj2);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1, T obj2, T obj3) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		temp.add(obj2);
		temp.add(obj3);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1, T obj2, T obj3, T obj4) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		temp.add(obj2);
		temp.add(obj3);
		temp.add(obj4);
		return temp;
	}
	
	public static List<String> toList(String obj1, String obj2) {
		List<String> temp = new ArrayList<String>();
		temp.add(obj1);
		temp.add(obj2);
		return temp;
	}
	
	public static List<String> toList(String obj1, String obj2, String obj3) {
		List<String> temp = new ArrayList<String>();
		temp.add(obj1);
		temp.add(obj2);
		temp.add(obj3);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj, String key1, Object obj1) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		temp.put(key1, obj1);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj, String key1, Object obj1,
											String key2, Object obj2) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		temp.put(key1, obj1);
		temp.put(key2, obj2);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj, String key1, Object obj1,
											String key2, Object obj2, String key3, Object obj3) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		temp.put(key1, obj1);
		temp.put(key2, obj2);
		temp.put(key3, obj3);
		return temp;
	}
	
	public static List<Map<String, Object>> getMapListByPoList(List<?> poList) {
		List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
		if (ListUtil.isEmpty(poList))
			return mapList;
		for (Iterator<?> it = poList.iterator(); it.hasNext();) {
			Object po = it.next();
			Map<String, Object> itemMap = covertPoToMap(po);
			mapList.add(itemMap);
		}
		return mapList;
	}
	
	public static Map<String, Object> covertPoToMap(Object po) {
		String propertyName = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					
					dataMap.put(propertyName, PropertyUtils.getProperty(po, propertyName));
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	public static Map<String, String> covertPoToMap(Object po, String... exclude) {
		Set<String> sets = new HashSet<>();
		if (null != exclude && exclude.length > 0) {
			for (String s : exclude) {
				if (StringUtil.isNotBlank(s)) {
					sets.add(s.trim());
				}
			}
		}
		
		return covertPoToMap(po, "", sets);
	}
	
	public static Map<String, String> covertPoToMap(Object po, String prefix, Set<String> sets) {
		String propertyName = null;
		Map<String, String> dataMap = new HashMap<>();
		try {
			if (po == null) {
				return dataMap;
			}
			
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (sets.contains(propertyName)) {
					continue;
				}
				if (pd.getWriteMethod() != null) {
					Object value = PropertyUtils.getProperty(po, propertyName);
					if (null == value) {
						dataMap.put(StringUtil.isBlank(prefix) ? propertyName
							: (prefix + "." + propertyName), "");
					} else if (value instanceof java.util.List) {
						List list1 = (List) value;
						if (list1.size() > 0) {
							if (StringUtil.isNotBlank(prefix)) {
								dataMap.put(prefix + "." + propertyName + ".size", list1.size()
																					+ "");
							} else {
								dataMap.put(propertyName + ".size", list1.size() + "");
							}
							int index = 0;
							for (Object po1 : (List) value) {
								if (StringUtil.isNotBlank(prefix)) {
									dataMap.putAll(covertPoToMap(po1, prefix + "." + propertyName
																		+ "." + index, sets));
								} else {
									dataMap.putAll(covertPoToMap(po1, propertyName + "." + index,
										sets));
								}
								index++;
							}
						}
					} else {
						dataMap.put(StringUtil.isBlank(prefix) ? propertyName
							: (prefix + "." + propertyName), value.toString());
					}
				}
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		
		return dataMap;
	}
	
	public static Map<String, String> comparePo(Object po1, Object po2, String... exclude) {
		if (null != po1 && null != po2) {
			Map<String, String> map1 = covertPoToMap(po1, exclude);
			Map<String, String> map2 = covertPoToMap(po2, exclude);
			
			Map<String, String> map = new HashMap<>();
			for (String key : map1.keySet()) {
				String parentKey = getParentKey(key);
				if (map.containsKey(parentKey)) {
					continue;
				}
				
				if (StringUtil.notEquals(map1.get(key), map2.get(key))) {
					map.put(parentKey, map1.get(key));
				}
			}
			return map;
		}
		return null;
	}
	
	private static String getParentKey(String key) {
		return (null != key) ? (key.replace("_obj.", "_").split("\\.")[0]) : key;
	}
	
	public static Map<String, String> covertPoToMapNoNullValue(Object po) {
		String propertyName = null;
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					Object object = PropertyUtils.getProperty(po, propertyName);
					if (object != null) {
						dataMap.put(propertyName, object.toString());
					}
					
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> covertPoToMapJson(Object po) {
		String propertyName = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					Object tempValue = PropertyUtils.getProperty(po, propertyName);
					if (tempValue instanceof Enum) {
						dataMap.put(propertyName, ((Enum) tempValue).name());
					} else {
						dataMap.put(propertyName, PropertyUtils.getProperty(po, propertyName));
					}
					
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	public static void setDOPropertyByDbMap(Map<String, Object> dbMap, Object entityDO) {
		
		Iterator<Map.Entry<String, Object>> iterator = dbMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String propertyName = StringUtil.toCamelCase(entry.getKey());
			try {
				Object temp = entry.getValue();
				if (entry.getValue() != null) {
					PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(entityDO,
						propertyName);
					if (pd == null)
						continue;
					if (pd.getPropertyType().equals(String.class)) {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					} else if (pd.getPropertyType() == Integer.class
								|| pd.getPropertyType() == int.class
								|| pd.getPropertyType() == Long.class
								|| pd.getPropertyType() == long.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
					} else if (pd.getPropertyType() == Double.class
								|| pd.getPropertyType() == double.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
						
					} else if (pd.getPropertyType() == Money.class) {
						Money money = new Money();
						money.setCent(((BigDecimal) temp).longValue());
						
						BeanUtils.setProperty(entityDO, pd.getName(), money);
						
					} else {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					}
				}
				
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException err", e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException err", e);
			} catch (NoSuchMethodException e) {
				logger.error("NoSuchMethodException err", e);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}
		
	}
	
	public static void setInfoPropertyByMap(Map<String, Object> dataMap, Object entityDO) {
		
		Iterator<Map.Entry<String, Object>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String propertyName = entry.getKey();
			try {
				Object temp = entry.getValue();
				if (entry.getValue() != null) {
					PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(entityDO,
						propertyName);
					if (pd == null)
						continue;
					if (pd.getPropertyType().equals(String.class)) {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					} else if (pd.getPropertyType() == Integer.class
								|| pd.getPropertyType() == int.class
								|| pd.getPropertyType() == Long.class
								|| pd.getPropertyType() == long.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
					} else if (pd.getPropertyType() == Double.class
								|| pd.getPropertyType() == double.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
						
					} else if (pd.getPropertyType() == Money.class) {
						Money money = new Money();
						if (temp instanceof BigDecimal) {
							money.setCent(((BigDecimal) temp).longValue());
						} else if (temp instanceof Number) {
							money.setAmount(new BigDecimal(((Number) temp).doubleValue()));
						} else if (temp instanceof String) {
							money.setAmount(new BigDecimal((String) ((String) temp).replaceAll(",","")));
						}
						BeanUtils.setProperty(entityDO, pd.getName(), money);
						
					} else if (pd.getPropertyType() == Date.class) {
						
					} else if (Enum.class.isAssignableFrom(pd.getPropertyType())) {
						
						Method method = ReflectionUtils.findMethod(pd.getPropertyType().getClass(),
							"getByCode", new Class[] { String.class });
						if (method != null) {
							Object enumValue = method.invoke(null, temp);
							if (enumValue != null) {
								BeanUtils.setProperty(entityDO, pd.getName(), enumValue);
							}
						}
						//不处理Enum类型的属性
						logger.debug("Property :" + pd.getName() + "is sub class of Enum !");
					} else {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					}
				}
				
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException err  propertyName={}", propertyName, e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException err propertyName={}", propertyName, e);
			} catch (NoSuchMethodException e) {
				logger.error("NoSuchMethodException err propertyName={}", propertyName, e);
			} catch (Exception e) {
				logger.error(" propertyName={} is error", propertyName, e);
			}
		}
		
	}
	
	public static List<Map<String, Object>> sortMaps(List<Map<String, Object>> listOfMaps,
														List sortKeys) {
		if (listOfMaps == null || sortKeys == null)
			return null;
		
		List<Map<String, Object>> toSort = new ArrayList<Map<String, Object>>();
		toSort.addAll(listOfMaps);
		try {
			MapComparator mc = new MapComparator(sortKeys);
			Collections.sort(toSort, mc);
		} catch (Exception e) {
			return null;
		}
		return toSort;
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] arg) {
		String aa = "{\"errCodeCtx\":{\"code\":\"DATA_ACCESS_EXCEPTION\",\"memo\":\"数据库异常\",\"message\":\"\"},\"instructionInfo\":{\"accountName\":\"AD\",\"accountNo\":\"20130106010000006668\",\"accountType\":\"PERSONAL\",\"apiAccess\":\"DEDUCT\",\"bankAccountName\":\"4\",\"bankAccountNo\":\"1235456435\",\"bankCode\":\"ICBC\",\"bankName\":\"工商银行\",\"bizIdentity\":\"C\",\"depositId\":\"2013030412212121000065371\",\"depositType\":\"DEPOSIT_ONLY\",\"freezeType\":\"DEDUCT_FREEZE\",\"memo\":\"1\",\"outBizContext\":{},\"outBizNo\":\"1111362390276500\",\"payAmount\":3.00,\"payAmountCurrency\":\"CNY\",\"payAmountIn\":3.00,\"rawAddTime\":1362390276000,\"status\":\"INITIAL\",\"subContractCode\":\"ASYNC_DEDUCT_DEPOSIT_CONTRACT\"},\"message\":\"[处理失败->数据库异常->//]\",\"resultCode\":\"EXECUTE_FAILURE\",\"success\":false}";
		System.out.println("=========="
							+ ((Map) parseJSON(aa).get("instructionInfo")).get("depositId"));
	}
}