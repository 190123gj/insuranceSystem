package com.born.insurance.webui.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yjf.common.lang.util.StringUtil;

/**
 * <p>
 * Title: the hash data manager
 * </p>
 * <p>
 * Description: the hash data manager
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * 
 * @author: �ᴺ��
 * @version 1.0
 */

public class HashMapManager extends Hidden {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap dataManager = new HashMap();
	
	private final ArrayList keyList = new ArrayList();
	
	/**
	 * <p>
	 * Description: create a hashmap manager control
	 * </p>
	 * 
	 * @param id: the control id
	 */
	public HashMapManager(String id) {
		super(id);
		this.name = id;
	}
	
	/**
	 * <p>
	 * Description: initial the attributes of the control
	 * </p>
	 * 
	 * @param params: the value's collection
	 */
	public void init(HashMap params) {
		for (Iterator i = params.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			if (("text").equals(e.getKey())) {
				this.setText((String) e.getValue());
			}
			if (("map").equals(e.getKey())) {
				this.dataManager = (HashMap) e.getValue();
			}
		}
	}
	
	/**
	 * <p>
	 * Description: put a String into dataManager
	 * </p>
	 * 
	 * @param key: the key of the String object
	 * @param value: the string object
	 */
	public void put(String key, String value) {
		if (StringUtil.isNotEmpty(key)) {
			dataManager.put(key, value);
			if (this.keyList.indexOf(key) == -1) {
				this.keyList.add(key);
			}
		}
	}
	
	/**
	 * <p>
	 * Description: delete a string object from dataManager
	 * </p>
	 * 
	 * @param key: key of the string object
	 */
	public void remove(String key) {
		if (key != null && !("").equals(key)) {
			dataManager.remove(key);
			this.keyList.remove(key);
		}
	}
	
	/**
	 * <p>
	 * Description: judge whether the key exists in dataManager
	 * </p>
	 * 
	 * @param key: the key .
	 * @return: the key exists in dataManager or not.
	 */
	public boolean contains(String key) {
		if (key != null && !("").equals(key)) {
			return dataManager.containsKey(key);
		} else {
			return false;
		}
	}
	
	/**
	 * <p>
	 * Description: judge whether the value exists in dataManager
	 * </p>
	 * 
	 * @param value: the value
	 * @return the value exists in dataManager or not
	 */
	public boolean containsValue(String value) {
		return dataManager.containsValue(value);
	}
	
	/**
	 * <p>
	 * Description: get collection of keys
	 * </p>
	 * 
	 * @return the collection of keys
	 */
	public ArrayList getKeys() {
		if (this.keyList.size() > 0) {
			return this.keyList;
		}
		ArrayList al = new ArrayList();
		for (int index = 0; index < dataManager.keySet().toArray().length; index++) {
			al.add(dataManager.keySet().toArray()[index]);
		}
		return al;
	}
	
	/**
	 * <p>
	 * Description: get collection of values
	 * </p>
	 * 
	 * @return the collection of values
	 */
	public ArrayList getValues() {
		ArrayList al = new ArrayList();
		for (int index = 0; index < dataManager.values().toArray().length; index++) {
			al.add(dataManager.values().toArray()[index]);
		}
		return al;
	}
	
	/**
	 * <p>
	 * Description: get the html code of the control
	 * </p>
	 * 
	 * @return the html of the control
	 */
	@Override
	protected String getElementHtml() {
		StringBuffer innerHtml = new StringBuffer();
		setText(parseHash());
		innerHtml.append(super.getElementHtml());
		return innerHtml.toString();
	}
	
	@Override
	protected String getScriptHtml() {
		return super.getScriptHtml() + HashMapJS.getHashMapJS();
	}
	
	/**
	 * <p>
	 * Description: parse the string to hashmap
	 * </p>
	 * 
	 * @param value: the string object
	 * @return the hashmap object
	 */
	public static HashMap parseHidden(String hiddenValue) {
		String value = HashMapManager.getChinese(hiddenValue);
		if (value == null || value.equals("")) {
			return null;
		}
		HashMap result = new HashMap();
		String[] values = StringUtils.split(value, "~"); // value.split("~");
		for (int index = 0; index < values.length; index++) {
			String keyAndValue = values[index];
			String[] strings = StringUtils.split(keyAndValue, "`"); // keyAndValue.split("`");
			if (strings.length == 0) {
				result.put("", "");
			} else if (strings.length == 1) {
				result.put(strings[0], "");
			} else {
				result.put(strings[0], strings[1]);
			}
			
		}
		return result;
	}
	
	private static String getChinese(String in) {
		String s = null;
		byte temp[];
		if (in == null) {
			return null;
		}
		try {
			temp = in.getBytes("iso-8859-1");
			s = new String(temp);
		} catch (Exception e) {
			// System.out.println(e.toString());
		}
		return in;
	}
	
	public static HashMap parseToMap(String id, HttpServletRequest request) {
		HashMap result = new HashMap();
		String value = HashMapManager.getChinese(request.getParameter(id));
		if (value == null) {
			return null;
		}
		String[] values = StringUtils.split(value, "~");// value.split("~");;
		
		for (int index = 0; index < values.length; index++) {
			String keyAndValue = values[index];
			String[] strings = StringUtils.split(keyAndValue, "`");// keyAndValue.split("`");
			
			if (strings.length == 0) {
				result.put("", "");
			} else if (strings.length == 1) {
				result.put(strings[0], "");
			} else {
				result.put(strings[0], strings[1]);
			}
			
		}
		return result;
	}
	
	public static HashMap parseToMap(String id, RequestUtils request) {
		HashMap result = new HashMap();
		String value = HashMapManager.getChinese(request.getString(id));
		if (value == null) {
			return null;
		}
		String[] values = StringUtils.split(value, "~");// value.split("~");;
		
		for (int index = 0; index < values.length; index++) {
			String keyAndValue = values[index];
			String[] strings = StringUtils.split(keyAndValue, "`");// keyAndValue.split("`");
			
			if (strings.length == 0) {
				result.put("", "");
			} else if (strings.length == 1) {
				result.put(strings[0], "");
			} else {
				result.put(strings[0], strings[1]);
			}
			
		}
		return result;
	}
	
	public static ArrayList getKeyList(String id, HttpServletRequest request) {
		ArrayList keyList = new ArrayList();
		String value = HashMapManager.getChinese(request.getParameter(id));
		if (value == null || value.equals("")) {
			return null;
		}
		String[] values = StringUtils.split(value, "~");// value.split("~");;
		for (int index = 0; index < values.length; index++) {
			String keyAndValue = values[index];
			String[] strings = StringUtils.split(keyAndValue, "`");// keyAndValue.split("`");
			keyList.add(strings[0]);
		}
		return keyList;
	}
	
	public static ArrayList getKeyList(String id, RequestUtils request) {
		ArrayList keyList = new ArrayList();
		String value = HashMapManager.getChinese(request.getString(id));
		if (value == null || value.equals("")) {
			return null;
		}
		String[] values = StringUtils.split(value, "~");// value.split("~");;
		for (int index = 0; index < values.length; index++) {
			String keyAndValue = values[index];
			String[] strings = StringUtils.split(keyAndValue, "`");// keyAndValue.split("`");
			keyList.add(strings[0]);
		}
		return keyList;
	}
	
	public static String parseHash(Map map) {
		StringBuffer hashSB = new StringBuffer();
		int index = 0;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			if (index == 0) {
				hashSB.append(e.getKey().toString() + "`" + e.getValue().toString());
			} else {
				hashSB.append("~" + e.getKey().toString() + "`" + e.getValue().toString());
			}
			index++;
		}
		return hashSB.toString();
	}
	
	/**
	 * <p>
	 * Description: parse hashmap to a string object
	 * </p>
	 * 
	 * @return a string object
	 */
	private String parseHash() {
		StringBuffer hashSB = new StringBuffer();
		if (this.keyList.size() > 0) {
			for (int index = 0; index < this.keyList.size(); index++) {
				String key = this.keyList.get(index).toString();
				if (index == 0) {
					hashSB.append(key + "`" + String.valueOf(this.dataManager.get(key)));
				} else {
					hashSB.append("~" + key + "`" + objectToString(this.dataManager.get(key)));
				}
			}
			return hashSB.toString();
		}
		int index = 0;
		for (Iterator i = this.dataManager.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			if (index == 0) {
				hashSB.append(objectToString(e.getKey()) + "`" + objectToString(e.getValue()));
			} else {
				hashSB
					.append("~" + objectToString(e.getKey()) + "`" + objectToString(e.getValue()));
			}
			index++;
		}
		return hashSB.toString();
	}
	
	protected String objectToString(Object object) {
		if (object == null)
			return "";
		return String.valueOf(object);
	}
	
	/**
	 * <p>
	 * Description: get the dataManager object</>
	 * 
	 * @return dataManager
	 */
	public HashMap getHashMap() {
		return dataManager;
	}
	
	public void setHashMap(Map map) {
		this.dataManager = (HashMap) map;
	}
	
	public String getReference(MapReferenceType operationType, HashMap params) {
		StringBuffer reference = new StringBuffer();
		if (("add").equals(operationType.getType())) {
			reference.append("HashtableManager_Add(\"");
			reference.append(params.get("key").toString() + "\",\"");
			reference.append(params.get("value").toString() + "\",");
		} else if (("remove").equals(operationType.getType())) {
			reference.append("HashtableManager_Remove(\"");
			reference.append(params.get("key").toString() + "\",");
		} else if (("removeAll").equals(operationType.getType())) {
			reference.append("HashtableManager_RemoveAll(");
		} else if (("removeAt").equals(operationType.getType())) {
			reference.append("HashtableManager_RemoveAt(");
			reference.append(params.get("index").toString() + ",");
		} else if (("set").equals(operationType.getType())) {
			reference.append("HashtableManager_Set(\"");
			reference.append(params.get("key").toString() + "\",\"");
			reference.append(params.get("value").toString() + "\",");
		} else if (("get").equals(operationType.getType())) {
			reference.append("HashtableManager_Get(\"");
			reference.append(params.get("key").toString() + "\",");
		} else if (("containsKey").equals(operationType.getType())) {
			reference.append("HashtableManager_ContainsKey(\"");
			reference.append(params.get("key").toString() + "\",");
		} else if (("containsValue").equals(operationType.getType())) {
			reference.append("HashtableManager_ContainsValue(\"");
			reference.append(params.get("value").toString() + "\",");
		} else if (("indexOfKey").equals(operationType.getType())) {
			reference.append("HashtableManager_IndexOfKey(\"");
			reference.append(params.get("key").toString() + "\",");
		} else if (("indexOfValue").equals(operationType.getType())) {
			reference.append("HashtableManager_IndexOfValue(\"");
			reference.append(params.get("value").toString() + "\",");
		} else if (("setByIndex").equals(operationType.getType())) {
			reference.append("HashtableManager_SetByIndex(");
			reference.append(params.get("index").toString() + ",\"");
			reference.append(params.get("value").toString() + "\",");
		} else if (("getByIndex").equals(operationType.getType())) {
			reference.append("HashtableManager_GetByIndex(");
			reference.append(params.get("index").toString() + ",");
		} else if (("getKey").equals(operationType.getType())) {
			reference.append("HashtableManager_GetKey(");
			reference.append(params.get("index").toString() + ",");
		} else if (("getKeyList").equals(operationType.getType())) {
			reference.append("HashtableManager_GetKeyList(");
		} else if (("getValueList").equals(operationType.getType())) {
			reference.append("HashtableManager_GetValueList(");
		}
		reference.append("document.getElementById(\"" + this.componentID + "\"))");
		return reference.toString();
	}
}
