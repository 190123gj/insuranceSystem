package com.born.insurance.webui.control;
/**
 * <p>Title: the javascript code of the arraylist control</p>
 * <p>Description: the javascript code of the arraylist control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author:�ᴺ��
 * @version 1.0
 * 
 * ListManager_Add(object , canRedundance , hidden)<br>
 * ���ܣ���Hidden������һ������<br>
 * hidden��Hidden ����<br>
 * object��׼��д��hidden������<br>
 * canRedundance:�Ƿ�����hidden���ظ�������<br>
 * <br>
 * ListManager_Remove(Index , hidden)<br>
 * ���ܣ���Hidden��ָ��λ��ɾ��һ������<br>
 * Index:λ���±�<br>
 * hidden��Hidden ����<br>
 * <br>
 * ListManager_RemoveAll()<br>
 * ���ܣ�ɾ��Hidden�е���������<br>
 * <br>
 * ListManager_Set(index , object , hidden)<br>
 * ���ܣ�ͨ���±�index����Hidden��ָ�������<br>
 * Index:λ���±�<br>
 * object��׼��д��hidden������<br>
 * hidden��Hidden ����<br>
 * <br>
 * ListManager_Insert(index, value, hidden)<br>
 * ���ܣ���ָ���±�index��ǰ�����ָ�������<br>
 * Index:λ���±�<br>
 * value��׼��д��hidden������<br>
 * hidden��Hidden ����<br>
 */

public class ArrayListJS {

  /**
   * <p>Description: get the javascript code of the Arraylist control</p>
   * @param path: the path of the javasrcipt code file
   * @return the referece of the javascript code file
   */
  public static String getArrayListJS(){
	  JSTool jstool=new JSTool("ArrayListJS.js");
	  if(!jstool.util.isUpdateJsEveryTime())
	  {
		  return jstool.getComponentJS() ;
	  }
	  StringBuffer js = new StringBuffer();
          js.append(" function ListManager_Add(object , canRedundance , hidden)\r\n");
     js.append(" {\r\n");
     js.append("		var strArray = ListManager_Analysis(hidden);\r\n");
     js.append("		if(!canRedundance)\r\n");
     js.append("		{\r\n");
     js.append(" 		for(index = 0; index < strArray.length; index++)\r\n");
     js.append(" 		{\r\n");
     js.append("				if(object == strArray[index])\r\n");
     js.append("				{\r\n");
     js.append("					return null;\r\n");
     js.append("				}\r\n");
     js.append("			}\r\n");
     js.append("		}\r\n");
     js.append("                hidden.value = hidden.value.substring(0, hidden.value.length - 9);\r\n");
     js.append("		hidden.value += \"~\";\r\n");
     js.append("		hidden.value += object;\r\n");
     js.append("                hidden.value +=\"~listend;\"\r\n");
     js.append(" }\r\n");
     js.append("function ListManager_Remove(Index , hidden)\r\n");
     js.append(" {\r\n");
     js.append(" 	var strArray = ListManager_Analysis(hidden);\r\n");
     js.append(" 	hidden.value = \"listmanager;\"\r\n");
     js.append(" 	for(index = 0; index < strArray.length; index++)\r\n");
     js.append(" 	{\r\n");
     js.append(" 		if(Index != index)\r\n");
     js.append(" 		{\r\n");
     js.append(" 			hidden.value += \"~\";\r\n");
     js.append("			hidden.value += strArray[index];\r\n");
     js.append(" 		}\r\n");
     js.append(" 	}\r\n");
     js.append("        hidden.value+=\"~listend;\"\r\n");
     js.append(" }\r\n");
     js.append(" function ListManager_RemoveAll(hidden)\r\n");
     js.append(" {\r\n");
     js.append("	hidden.value = \"listmanager;~listend;\";\r\n");
     js.append(" }\r\n");
     js.append(" function ListManager_Analysis(hidden)\r\n");
     js.append(" {\r\n");
     js.append(" 	var strArray;\r\n");
     js.append(" 	if(hidden.value.indexOf(\"listmanager;\") == 0)\r\n");
     js.append(" 	{\r\n");
     js.append(" 		if(hidden.value == \"listmanager;~listend;\")\r\n");
     js.append(" 		{\r\n");
     js.append(" 			return new Array();\r\n");
     js.append(" 		}\r\n");
     js.append(" 		var arrayString = hidden.value.substring(13, hidden.value.length)\r\n");
     js.append("       		var arrayString1 = arrayString.substring(0, arrayString.length - 9);\r\n");
     js.append(" 		strArray = arrayString1.split(\"~\");\r\n");
     js.append(" 	}\r\n");
     js.append(" 	return strArray;\r\n");
     js.append(" }\r\n");
     js.append(" function ListManager_Set(index , object , hidden)\r\n");
     js.append(" {\r\n");
     js.append(" 	\r\n");
     js.append(" 	var strArray = ListManager_Analysis(hidden);\r\n");
     js.append(" 	\r\n");
     js.append(" 	if(strArray[index] == null)\r\n");
     js.append(" 	{\r\n");
     js.append(" 		return;\r\n");
     js.append(" 	}\r\n");
     js.append(" 	hidden.value=\"listmanager;\"\r\n");     
     js.append(" 	strArray[index] = object;\r\n");
     js.append(" 	for(Index = 0; Index<strArray.length; Index++)\r\n");
     js.append(" 	{\r\n");
     js.append("		hidden.value += \"~\"\r\n");
     js.append(" 		hidden.value += strArray[Index];\r\n");
     js.append(" 	}\r\n");
     js.append("        hidden.value+=\"~listend;\"\r\n");
     js.append(" }\r\n");
     js.append(" function ListManager_Get(index , hidden)\r\n");
     js.append(" {\r\n");
     js.append("		var strArray = ListManager_Analysis(hidden);\r\n");
     js.append("		return strArray[index];\r\n");
     js.append(" }\r\n");
     js.append("\r\n");
     js.append(" function ListManager_Size(hidden)\r\n");
     js.append(" {\r\n");
     js.append("        \r\n");
     js.append("        return hidden.value.split(\"~\").length - 2;\r\n");
     js.append(" }\r\n");
     js.append(" function ListManager_IndexOf(value, hidden)\r\n");
     js.append(" {\r\n");
     js.append("        var strArray = ListManager_Analysis(hidden);\r\n");
     js.append("        var nIndex = -1;\r\n");
     js.append("        for(index = 0; index < strArray.length; index++)\r\n");
     js.append("        {\r\n");
     js.append("                if(strArray[index] == value)\r\n");
     js.append("                {\r\n");
     js.append("                        nIndex = index;\r\n");
     js.append("                        break;\r\n");
     js.append("                }\r\n");
     js.append("        }\r\n");
     js.append("        return nIndex;\r\n");
     js.append(" }\r\n");
     js.append(" function ListManager_Insert(index, value, hidden)\r\n");
     js.append(" {\r\n");
     js.append(" 	var strArray = ListManager_Analysis(hidden);\r\n");
     js.append("	if(index == null || index === \"\" || parseInt(index) == NaN)\r\n");
     js.append("	{\r\n");
     js.append("		return;\r\n");
     js.append("	}\r\n");
     js.append("        if(index > strArray.length)\r\n");
     js.append("        {\r\n");
     js.append("               return;\r\n");
     js.append("        }\r\n");
     js.append(" 	for(var i = strArray.length - 1; i >= index; i--)\r\n");
     js.append(" 	{\r\n");
     js.append(" 	        strArray[i + 1]	 = strArray[i];\r\n");
     js.append(" 	}\r\n");
     js.append(" 	strArray[index] = value;	\r\n");
     js.append(" 	hidden.value=\"listmanager;\";\r\n");
     js.append(" 	for(i = 0; i<strArray.length; i++)\r\n");
     js.append(" 	{\r\n");
     js.append(" 		hidden.value += \"~\";\r\n");
     js.append(" 		hidden.value += strArray[i];\r\n");
     js.append(" 	}	\r\n");
     js.append("        hidden.value+=\"~listend;\";\r\n");
     js.append(" }");
     js.append(" function ListManager_getHiddenId(controlId)\r\n");
     js.append(" {\r\n");
     js.append("	return controlId + \"_hidden\";\r\n");
     js.append(" }");

      jstool.js=js;
      return jstool.getComponentJS();
  }

}