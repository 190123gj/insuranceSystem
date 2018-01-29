package com.born.insurance.webui.control;

public class ListReferenceType {
  private String type = null;

  public ListReferenceType(String typeName){
    type = typeName;
  }

  public static ListReferenceType ADD = new ListReferenceType("add");

  public static ListReferenceType SET = new ListReferenceType("insert");

  public static ListReferenceType REMOVE =  new ListReferenceType("remove");

  public static ListReferenceType REMOVE_ALL =  new ListReferenceType("removeAll");

  public static ListReferenceType GET =  new ListReferenceType("get");

  public String getType(){
    return type;
  }
}