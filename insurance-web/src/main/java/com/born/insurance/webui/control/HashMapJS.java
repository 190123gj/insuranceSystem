package com.born.insurance.webui.control;


/**
 * <p>Title: the javascript code of the hashmap manager control</p>
 * <p>Description: the javascript code of the hashmap manager control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author: �ᴺ��
 * @version 1.0
 */

public class HashMapJS {

  /**
   * <p>Description: get the javasrcipt code of the HashMap control</p>
   * @param path: the path of the javascript code file.
   * @return the reference of the javascript code file.
   * <p><b>HashtableManager_Add��</b>��Ӷ���hidden</p>
   * <b>param:key, </b> <b>paramType:String, </b><b>description:��ӵĶ����keyֵ</b>
   * <b>param:object, </b> <b>paramType:String, </b><b>description:��ӵĶ����ֵ</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:��, </b>
   * <p><b>HashtableManager_Remove</b>��hiddenɾ�����</p>
   * <b>param:key, </b> <b>paramType:String, </b><b>description:ɾ��Ķ����keyֵ</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:��, </b>
   * <p><b>HashtableManager_Set</b>���ö���hidden</p>
   * <b>param:key, </b> <b>paramType:String, </b><b>description:���õĶ����keyֵ</b>
   * <b>param:object, </b> <b>paramType:String, </b><b>description:���õĶ����ֵ</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:��, </b>
   * <p><b>HashtableManager_ContainsKey</b>�ж��Ƿ��ĳ��keyֵ</p>
   * <b>param:key, </b> <b>paramType:String, </b><b>description:�����keyֵ</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:bool, </b> <b>description:�Ƿ���key</b>
   * <p><b>HashtableManager_Get</b>��hidden��ö���</p>
   * <b>param:key, </b> <b>paramType:String, </b><b>description:Ҫ��õĶ����keyֵ</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:string, </b> <b>description:���ö����value</b>
   * <p><b>HashtableManager_RemoveAll</b>ɾ�����ж���</p>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:��, </b>
   * <p><b>HashtableManager_RemoveAt</b>���index��hidden��ɾ��һ������</p>
   * <b>param:index, </b> <b>paramType:int, </b><b>description:Ҫɾ��Ķ����indexֵ</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:��, </b>
   * <p><b>HashtableManager_IndexOfKey</b>���key���ĳ�������index</p>
   * <b>param:key, </b> <b>paramType:string, </b><b>description:Ҫ���index�Ķ����key</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:int, </b> <b>description:�����index</b>
   * <p><b>HashtableManager_IndexOfValue</b>���value���ĳ�������index</p>
   * <b>param:object, </b> <b>paramType:string, </b><b>description:Ҫ���index�Ķ����value</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:int, </b> <b>description:�����index</b>
   * <p><b>HashtableManager_SetByIndex</b>���index����ĳ�������ֵ</p>
   * <b>param:index, </b> <b>paramType:string, </b><b>description:Ҫ����ֵ��index</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:��, </b> 
   * <p><b>HashtableManager_GetByIndex</b>���index���ĳ�������ֵ</p>
   * <b>param:index, </b> <b>paramType:string, </b><b>description:Ҫ���ֵ�Ķ����index</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:string, </b> <b>description:�����value</b>     
   * <p><b>HashtableManager_GetKey</b>���index���ĳ�������key</p>
   * <b>param:index, </b> <b>paramType:string, </b><b>description:Ҫ���key�Ķ����index</b>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:string, </b> <b>description:�����key</b>      
   * <p><b>HashtableManager_GetKeyList</b>������ж����key�ļ���</p>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:array, </b> <b>description:���ж����key�ļ���</b>   
   * <p><b>HashtableManager_GetValueList</b>������ж����value</p>
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:array, </b> <b>description:���ж����value�ļ���</b> 
   * <p><b>HashtableManager_ContainsValue</b>���value�ж��Ƿ��ĳ������</p>
   * <b>param:object, </b> <b>paramType:string, </b><b>description:�Ƿ���value</b>   
   * <b>param:hidden, </b> <b>paramType:html hidden object, </b><b>description:�洢��ݵĿͻ���hidden����</b>
   * <b>return:bool, </b> <b>description:�Ƿ���value</b>     
   */
	public static String getHashMapJS(){
		JSTool jstool=new JSTool("HashMapJS.js");
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS() ;
		 }
		StringBuffer jsSB = new StringBuffer();
		jsSB.append(" function HashtableManager_Add(key , object , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append("		array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append("		index = HashtableManager_IndexOfKey(key,hidden);\r\n");
		jsSB.append("		if(index == -1)\r\n");
		jsSB.append("		{\r\n");
		jsSB.append("			if(hidden.value == \"\")\r\n");
		jsSB.append("			{\r\n");
		jsSB.append("				hidden.value += key;\r\n");
		jsSB.append("				hidden.value += \"`\";\r\n");
		jsSB.append("				hidden.value += object;\r\n");
		jsSB.append("			}\r\n");
		jsSB.append("			else\r\n");
		jsSB.append("			{\r\n");
		jsSB.append("				hidden.value += \"~\"\r\n");
		jsSB.append("				hidden.value += key;\r\n");
		jsSB.append("				hidden.value += \"`\";\r\n");
		jsSB.append("				hidden.value += object;\r\n");
		jsSB.append("			}\r\n");
		jsSB.append("		}\r\n");
		jsSB.append("		else\r\n");
		jsSB.append("		{\r\n");
		jsSB.append("			return;\r\n");
		jsSB.append("		}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_Remove(key , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden)\r\n");
		jsSB.append(" 	index = HashtableManager_IndexOfKey(key,hidden);\r\n");
		jsSB.append(" 	if(index == -1)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		return;\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	else\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		HashtableManager_Delete(index , array , hidden);\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_SetHidden(hidden , array)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	hidden.value = array[0][0];\r\n");
		jsSB.append(" 	hidden.value += \"`\";\r\n");
		jsSB.append(" 	hidden.value += array[0][1];\r\n");
		jsSB.append(" 	for(index = 1; index<array.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		hidden.value += \"~\";\r\n");
		jsSB.append(" 		hidden.value += array[index][0];\r\n");
		jsSB.append(" 		hidden.value += \"`\";\r\n");
		jsSB.append(" 		hidden.value += array[index][1];\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_ContainsKey(key , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	for(index = 0; index<array.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		if(key == array[index][0])\r\n");
		jsSB.append(" 		{\r\n");
		jsSB.append(" 			return true;\r\n");
		jsSB.append(" 		}\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	return false;\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_Set(key , object , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append("		array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append("		index = HashtableManager_IndexOfKey(key,hidden);\r\n");
		jsSB.append(" 	if(index == -1)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		return;\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	else\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		array[index][1] = object;\r\n");
		jsSB.append(" 		HashtableManager_SetHidden(hidden,array);\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append("  }\r\n");
		jsSB.append(" function HashtableManager_Anaysis(hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	var arr = new Array();\r\n");
		jsSB.append("		if(hidden.value == null || hidden.value == \"\")\r\n");
		jsSB.append("		{\r\n");
		jsSB.append("			return -1\r\n");
		jsSB.append("		}\r\n");
		jsSB.append(" 	strArray = hidden.value.split(\"~\");\r\n");
		jsSB.append(" 	for(index = 0; index< strArray.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		subArray = strArray[index].split(\"`\");\r\n");
		jsSB.append(" 		arr[index] = subArray;\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	return arr;\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_Get(key , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append("		array = HashtableManager_Anaysis(hidden)\r\n");
		jsSB.append("		index = HashtableManager_IndexOfKey(key,hidden);\r\n");
		jsSB.append("		if(index != -1)\r\n");
		jsSB.append("		{\r\n");
		jsSB.append("			return array[index][1];\r\n");
		jsSB.append("		}\r\n");
		jsSB.append("		else\r\n");
		jsSB.append("		{\r\n");
		jsSB.append("			return null;\r\n");
		jsSB.append("		}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_RemoveAll(hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append("		hidden.value = \"\";\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_RemoveAt(index , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	nIndex = 0;\r\n");
		jsSB.append(" 	if(index>=0 && index<array.length)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		HashtableManager_Delete(index , array , hidden);\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_Delete(index , array , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	nIndex = 0;\r\n");
		jsSB.append("		hidden.value = \"\";\r\n");
		jsSB.append(" 	for(Index = 0 ;Index<array.length ;Index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		if(Index == index)\r\n");
		jsSB.append(" 		{\r\n");
		jsSB.append(" 			continue;\r\n");
		jsSB.append(" 		}\r\n");
		jsSB.append(" 		else\r\n");
		jsSB.append(" 		{\r\n");
		jsSB.append(" 			if(nIndex == 0)		\r\n");
		jsSB.append(" 			{\r\n");
		jsSB.append(" 				hidden.value = array[Index][0];\r\n");
		jsSB.append(" 				hidden.value += \"`\";\r\n");
		jsSB.append(" 				hidden.value += array[Index][1];\r\n");
		jsSB.append(" 				nIndex++;\r\n");
		jsSB.append(" 			}\r\n");
		jsSB.append(" 			else\r\n");
		jsSB.append(" 			{\r\n");
		jsSB.append(" 				hidden.value += \"~\"\r\n");
		jsSB.append(" 				hidden.value += array[Index][0];\r\n");
		jsSB.append(" 				hidden.value += \"`\";\r\n");
		jsSB.append(" 				hidden.value += array[Index][1]\r\n");
		jsSB.append(" 			}\r\n");
		jsSB.append(" 		}\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_IndexOfKey(key , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	for(index = 0; index<array.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		if(key == array[index][0])\r\n");
		jsSB.append(" 		{\r\n");
		jsSB.append(" 			return index;\r\n");
		jsSB.append(" 		}\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	return -1;\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_IndexOfValue(object , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	for(index = 0; index<array.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		if(object == array[index][1])\r\n");
		jsSB.append(" 		{\r\n");
		jsSB.append(" 			return index;\r\n");
		jsSB.append(" 		}\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	return -1;\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_SetByIndex(index , object , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	if(index == null || index == \"\" || parseInt(index) == isNaN)\r\n");
 		jsSB.append(" 	{\r\n");
 		jsSB.append(" 		return;\r\n");
 		jsSB.append(" 	}\r\n");
		jsSB.append(" 	if(index>=0 && index <array.length)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		array[index][1] = object;\r\n");
		jsSB.append(" 		HashtableManager_SetHidden(hidden , array);\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_GetByIndex(index , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	if(index>=0 && index <array.length)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		return array[index][1];\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	else\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		return null;\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_GetKey(index , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden)\r\n");
		jsSB.append(" 	if(index>=0 && index <array.length)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		return array[index][0];\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	else\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		return null;\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_GetKeyList(hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	return  HashtableManager_GetFromArray(array , 0);\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_GetValueList(hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	return  HashtableManager_GetFromArray(array , 1);\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_GetFromArray(array , nIndex)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	var arr = new Array();\r\n");
		jsSB.append(" 	for(index = 0; index<array.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		arr[index] = array[index][nIndex];\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	return arr;\r\n");
		jsSB.append(" }\r\n");
		jsSB.append(" function HashtableManager_ContainsValue(object , hidden)\r\n");
		jsSB.append(" {\r\n");
		jsSB.append(" 	array = HashtableManager_Anaysis(hidden);\r\n");
		jsSB.append(" 	for(index = 0; index<array.length; index++)\r\n");
		jsSB.append(" 	{\r\n");
		jsSB.append(" 		if(object == array[index][1])\r\n");
		jsSB.append(" 		{\r\n");
		jsSB.append(" 			return true;\r\n");
		jsSB.append(" 		}\r\n");
		jsSB.append(" 	}\r\n");
		jsSB.append(" 	return false;\r\n");
		jsSB.append(" }\r\n");
		jsSB.append("\r\n");
		jsSB.append("");
		jstool.js=jsSB;
		return jstool.getComponentJS();
  }
}