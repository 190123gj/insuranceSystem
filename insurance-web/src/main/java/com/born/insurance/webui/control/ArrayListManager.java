package com.born.insurance.webui.control;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;



/**
 * <p>Title: the array list manager control</p>
 * <p>Description: the array list manager control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author: �ᴺ��
 * @version 1.0
 */

public class ArrayListManager extends AbstractComponent{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private ArrayList dataManager = new ArrayList();
  private String listHidden = null;
  private boolean canRedundance = false;

  /**
   *<p>Description: create a arraylist control</p>
   * @param id: the id of the control
   */
  public ArrayListManager(String id){
    super(id);
    this.name = id;
    listHidden = id + "_hidden";
    this.components.add(new Hidden(listHidden));
  }

  public void setComponentId(String id){
    this.componentID = id;
    this.name = id;
    listHidden = id + "_hidden";
    if(this.components.size() == 1){
      Hidden valueHidden = (Hidden)this.components.get(0);
      valueHidden.componentID = listHidden;
      valueHidden.name = listHidden;
    }
  }

  /**
   * <p>Description: initial attributes of the control</p>
   * @param params: value collection
   */
  public void init(HashMap params){
    for (Iterator i =  params.entrySet().iterator(); i.hasNext(); ){
     Map.Entry e = (Map.Entry) i.next();
     if(("text").equals(e.getKey())){
       this.setText((String)e.getValue());
     }
     if(("list").equals(e.getKey())){
       this.dataManager = (ArrayList)e.getValue();
     }
   }
  }

  /**
   * <p>Description: get the html code of the control</p>
   * @return the html code of the control
   */
  protected String getElementHtml(){
    StringBuffer innerHtml = new StringBuffer();
    AbstractComponent ac = (AbstractComponent)this.findComponent(listHidden);
    ac.setText("listmanager;" + parseList() + "~listend;");
    innerHtml.append(ac.getInnerHtml());
    return innerHtml.toString();
  }
  protected String getScriptHtml()
  {
	  return super.getScriptHtml() + ArrayListJS.getArrayListJS();
  }

  /**
   * <p>Description: parse arraylist to a string object</p>
   * @return a string object
   */
  public String parseList(){
    StringBuffer values = new StringBuffer();
    for(int index = 0; index < dataManager.size(); index++){
        values.append("~" + dataManager.get(index).toString());
    }
    return values.toString();
  }

  /**
   * <p>Description: parse a string object to a arraylist</p>
   * @param value: the string object
   * @return: the arraylist
   */
  public static ArrayList parseHidden(String id, HttpServletRequest request){
    String hiddenName = id + "_hidden";
    String value = ArrayListManager.getChinese(request.getParameter(hiddenName));   
    if(value == null || value.equals("listmanager;~listend;") || value.equals("") ){
      return null;
    }   
    ArrayList results = new ArrayList();   
    String[] values =StringUtils.splitArray(value,"~");// value.split("~");    
    for(int index = 1; index < values.length - 1; index++){
      results.add(values[index]);
    }
    String pppp = value.substring(value.length() - 1, value.length());
    if(value.substring(value.length() - 1, value.length()).equals("~")){
      results.add("");
    }
    return results;
  }
  public static ArrayList parseHidden(String id, RequestUtils request){
	  String hiddenName = id + "_hidden";
	  String value = ArrayListManager.getChinese(request.getString(hiddenName));   
	  if(value == null || value.equals("listmanager;~listend;") || value.equals("") ){
		return null;
	  }   
	  ArrayList results = new ArrayList();   
	  String[] values =StringUtils.splitArray(value,"~");// value.split("~");    
	  for(int index = 1; index < values.length - 1; index++){
		results.add(values[index]);
	  }
	  String pppp = value.substring(value.length() - 1, value.length());
	  if(value.substring(value.length() - 1, value.length()).equals("~")){
		results.add("");
	  }
	  return results;
	}

  private static String getChinese(String in)
  {
        String result=null;
        byte temp[];
        if(in==null)
        {
          return null;
        }
        try
        {
          temp=in.getBytes("iso-8859-1");
          result=new String(temp);
        }
        catch(Exception e)
        {         
        }
        return in;
      }


  public static ArrayList parseHidden(String Hiddenvalue){
    String value = ArrayListManager.getChinese(Hiddenvalue);
    if(value == null || value.equals("listmanager;~listend;") || value.equals("")){
      return null;
    }
    ArrayList results = new ArrayList();
    String[] values =StringUtils.splitArray(value,"~");// value.split("~");
    for(int index = 1; index < values.length; index++){
      results.add(values[index]);
    }
    return results;
  }

  /**
   * <p>Description: get id of the hidden</p>
   * @return id of the hidden
   */
  public String getHidden(){
    return listHidden;
  }

  /**
  * <p>Description: get size of datamanager</p>
  * @return size
  */
  public int size(){
    return dataManager.size();
  }
	
  /**
   * <p>Description: add a string object into datamanager</p>
   * @param value: the string object
   */
  public void add(String value){
    dataManager.add(value);
  }

  /**
   * <p>Description: insert a string object into datamanager</p>
   * @param index: index of the value
   * @param value: the string object
   */
  public void insert(int index, String value){
    dataManager.add(index, value);
  }

  /**
  * <p>Description: set a string object into datamanager</p>
  * @param index: index of the value
  * @param value: the string object
  */
  public void set(int index, String value){
    dataManager.set(index, value);
  }

  /**
   *<p>Description: delete all objects in datamanager</p>
   */
  public void removeAll(){
    dataManager.clear();
  }

  /**
   * <p>Description: delete one object from datamanager</p>
   * @param index: index of the object
   */
  public void remove(int index){
    dataManager.remove(index);
  }

  /**
   * <p>Description: get a object from datamanager</p>
   * @param index: index of the object
   * @return the string object
   */
  public String get(int index){
  	if(dataManager.get(index)==null)
		return null;
	else
    	return dataManager.get(index).toString();	
  }

  /**
   * <p>Description: get datamanager</p>
   * @return datamanager
   */
  public ArrayList getArrayList(){
    return dataManager;
  }
   
  /**
   * <p>Description: set datamanager</p>
   */
  public void setArrayList(ArrayList list){
    dataManager = list;
  }

  public String getReference(ListReferenceType operationType, HashMap params){
    StringBuffer reference = new StringBuffer();
    String redundance = "false";
    if(canRedundance){
      redundance = "true";
    }
    if(operationType.getType().equals("add")){
      reference.append("ListManager_Add(\"");
      reference.append(params.get("value").toString() + "\",");
      reference.append(redundance + ",");
    }
    else if(operationType.getType().equals("get")){
      reference.append("ListManager_Get(");
      reference.append(params.get("index").toString() + ",");
    }
    else if(operationType.getType().equals("remove")){
      reference.append("ListManager_Remove(");
      reference.append(params.get("index").toString() + ",");
    }
    else if(operationType.getType().equals("removeAll")){
      reference.append("ListManager_RemoveAll(");
    }
    else if(operationType.getType().equals("insert")){
      reference.append("ListManager_Set(");
      reference.append(params.get("index").toString());
      reference.append(",\"" + params.get("value").toString() + "\",");
    }
    reference.append("document.getElementById(\"" + listHidden + "\"))");
    return reference.toString();
  }
}