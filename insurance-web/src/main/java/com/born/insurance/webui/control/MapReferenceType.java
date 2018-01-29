package com.born.insurance.webui.control;

import java.io.Serializable;

public class MapReferenceType implements Serializable {
 /**
  * 
  */
  private static final long serialVersionUID = 1L;
  public MapReferenceType()
  {
	type="add";  
  }
  private String type = null;
  public MapReferenceType(String typeName) {
    type = typeName;
  }

  public static MapReferenceType ADD = new MapReferenceType("add");

  public static MapReferenceType REMOVE = new MapReferenceType("remove");

  public static MapReferenceType CONTAINS_KEY = new MapReferenceType("containsKey");

  public static MapReferenceType CONTAINS_VALUE = new MapReferenceType("containsValue");

  public static MapReferenceType SET = new MapReferenceType("set");

  public static MapReferenceType GET = new MapReferenceType("get");

  public static MapReferenceType REMOVE_ALL = new MapReferenceType("removeAll");

  public static MapReferenceType REMOVE_AT = new MapReferenceType("removeAt");

  public static MapReferenceType INDEX_OF_KEY = new MapReferenceType("indexOfKey");

  public static MapReferenceType INDEX_OF_VALUE = new MapReferenceType("indexOfValue");

  public static MapReferenceType SET_BY_INDEX = new MapReferenceType("setByIndex");

  public static MapReferenceType GET_BY_INDEX = new MapReferenceType("getByIndex");

  public static MapReferenceType GET_KEY = new MapReferenceType("getKey");

  public static MapReferenceType GET_KEY_LIST = new MapReferenceType("getKeyList");

  public static MapReferenceType GET_VALUE_LIST = new MapReferenceType("getValueList");

  public String getType(){
    return type;
  }
}