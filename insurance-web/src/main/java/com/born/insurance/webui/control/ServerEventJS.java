/*
 * �������� 2003-10-16
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;
/**
 * @author �ᴺ��
 *
 * �������¼��ű������
 */
public class ServerEventJS
{
	/**
	 * ���캯��
	 */
	protected ServerEventJS()
	{
	}
	/**
	 * <B>��ȡ�������¼��ű����ô�</B>
	 * <BR><B>�ͻ��˵���JavaScript˵��</B>
	 * <BR>�ù��Ϊ�¼������Զ�����,�¼����ƽ�ֱ�ӵ���,����Բ�����н���
	 * <BR>�¼����ƽ��ṩ��������:componentId ��ǰ�����¼������ID
	 * <BR>                     eventName ��ǰ�����¼������
	 * <BR>�û����ø�Ĳ�����,���������Ը��
	 * <BR>����û���Ҫ�����������д�������Attribute������
	 * <BR>Ȼ�����ѵĹ�̸��componentId�������ȡ�� 
	 * <BR>function:getUserDataHidden(componentId,eventName)
	 * <BR>param componentId ���ID
	 * <BR>param eventName   �¼���
	 * <BR>��̶�������:
	 * <BR>function myServerFunc (componentId,eventName)
	 * <BR>{
	 * <BR>    var objData=getUserDataHidden(componentId,eventName);
	 * <BR>    //�������¼����Զ����JavaScript���getUserDataHidden(componentId,eventName),ͨ��˹�̿ɵõ������ύ��ݵ�����
	 * <BR>    HashtableManager_Add("UserData1","Data",objData);
	 * <BR>}
	 * <BR>//ֱ�ص���HashTableManager�ķ�������ݼ���
	 * <BR>�¼��������ӣ�
	 * <BR>IServerEvent event=new ServerEvent(��Onclick()��);
	 * <BR>event.setFuncName(��myServerFunc(componentId,eventName)��);
	 * <BR>//myServerFuncΪJavaScript����Ĺ��
	 * <BR>//�ڷ��������Զ��������֡�󣬽��Զ����øù�̣�������п��ޣ���������̶�:componentId��eventName��Сдһ��
	 * <BR><B>��ȡ�û���ݴ洢����(Hidden)<B>
	 * <BR><B>function:getUserDataHidden(componentId,eventName)</B>
	 * <BR><B>param componentId ���ID</B>
	 * <BR><B>param eventName   �¼���</B>
	 * @param path �ű�·��
	 * @return  �������¼����ýű���
	 */
	public static String getServerEventJS()
	{
		JSTool jstool=new JSTool("ServerEvent.js");
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS() + HashMapJS.getHashMapJS();
		 }
		StringBuffer js = new StringBuffer();
        js.append("function ServerEventFunc(componentId,eventName)\r\n");
        js.append("{\r\n");
        js.append(" \r\n");
        js.append("	var clientFunc=document.getElementById(componentId).getAttribute(\"SF\" + eventName);\r\n");
        js.append("	if(null==clientFunc)\r\n");
        js.append("		return;\r\n");
        js.append("	var sr=document.getElementById(componentId).getAttribute(\"SR\" + eventName);\r\n");
        js.append("	var sc=document.getElementById(componentId).getAttribute(\"SC\" + eventName);\r\n");
        js.append("	var bsh=document.getElementById(componentId).getAttribute(\"BSH\" + eventName);\r\n");
        js.append("	var sm=document.getElementById(componentId).getAttribute(\"SM\" + eventName);\r\n");
        js.append("	\r\n");
        js.append("	var divName=componentId + eventName +\"_DIV\";\r\n");
        js.append("	var iframeName=componentId + eventName + \"_IFRAME\";\r\n");
        js.append("	var iformName=iframeName + \"_FORM\";\r\n");
        js.append("	var ihiddenName=\"COMPONENT_DATA_SYSTEM\";\r\n");
        js.append("	var ihiddenUserName=\"COMPONENT_DATA_USER\";\r\n");
        js.append("	\r\n");
        js.append("	var objdiv=document.getElementById(divName);\r\n");
        js.append("	var objInput=null;\r\n");
        js.append("	if(null!=objdiv&&!document.all)\r\n");
        js.append("	{		\r\n");
        js.append("		var doc=eval(\"window.\" + iframeName).document;\r\n");
        js.append("		objInput=doc.getElementById(ihiddenName);		\r\n");
        js.append("	}\r\n");
        js.append("	else\r\n");
        js.append("	{\r\n");
        js.append("		if(objdiv==null)	\r\n");
        js.append("		{	     \r\n");
        js.append("			objdiv=window.document.createElement(\"Div\");\r\n");
        js.append("		    objdiv.id=divName;\r\n");
        js.append("		    objdiv.name=divName;\r\n");
        js.append("		    document.body.appendChild(objdiv);\r\n");
        js.append("		 }\r\n");
        js.append("		else 	\r\n");
        js.append("		{\r\n");
        js.append("			objdiv.innerHTML=\"\";	\r\n");
        js.append("		}			\r\n");
        js.append("		var outString=\"<IFrame id='\" + iframeName + \"' name='\" + iframeName + \"'  style='width:0; height:0;visibility:hidden'></IFrame>\"	\r\n");
        js.append("		objdiv.innerHTML=outString;\r\n");
        js.append("		var strHtml=\"<html><body><form method='post' action='\" + sr +\"' name='\" + iformName + \"' id='\" + iformName + \"'>\";\r\n");
        js.append("		strHtml+=\"<input type='hidden' id='\" + ihiddenName + \"' name='\" + ihiddenName + \"'>\";\r\n");
        js.append("		strHtml+=\"<input type='hidden' id='\" + ihiddenUserName + \"' name='\" + ihiddenUserName + \"'></form></body></html>\"				\r\n");
        js.append("		eval(\"window.\" + iframeName).document.write(strHtml);				\r\n");
        js.append("	}\r\n");
        js.append("	if(!document.all)\r\n");
        js.append("	{\r\n");
        js.append("		eval(\"window.\" + iframeName).document.getElementById(iformName).action=sr+\"?iformName=\"+iformName;\r\n");
        js.append("	}\r\n");
        js.append("	objInput=eval(\"window.\" + iframeName).document.getElementById(ihiddenName);\r\n");
        js.append("	if(objInput==null) alert('Dont Create Input');	\r\n");
        js.append("	HashtableManager_Add(\"ID\",componentId,objInput);\r\n");
        js.append("	if(bsh!=\"null\");\r\n");
        js.append("	{\r\n");
        js.append("	    HashtableManager_Add(\"BSH\",bsh,objInput);\r\n");
        js.append("	}\r\n");
        js.append("	if(sc!=\"null\");\r\n");
        js.append("	{\r\n");
        js.append("	    HashtableManager_Add(\"SC\",sc,objInput);\r\n");
        js.append("	}\r\n");
        js.append("	if(sm!=\"null\");\r\n");
        js.append("	{\r\n");
        js.append("	    HashtableManager_Add(\"SM\",sm,objInput);\r\n");
        js.append("	}\r\n");
	  js.append("      var isBreak=eval(clientFunc);\r\n");	
	  js.append("      if(isBreak==false)\r\n");	
	  js.append("       {\r\n");	
	  js.append("       	document.body.removeChild(objdiv);\r\n");
	  js.append("       	 objdiv.innerHTML=\"\";\r\n");		 
	  js.append("       	return;\r\n");
	  js.append("        }\r\n");			  
	  js.append("        var objhidden=getUserDataHidden(componentId,eventName);\r\n");
	  js.append("         var objSystemhidden= eval(\"window.\" + iframeName).document.getElementById(ihiddenUserName);\r\n");
	  js.append("         if(sr.indexOf(\"?\")>=0)\r\n");
	  js.append("          {\r\n");
	  js.append("           document.getElementById(iframeName).src=sr+\"&\"+objhidden.name+\"=\"+objhidden.value+\"&\"+objSystemhidden.name+\"=\"+objSystemhidden.value;\r\n");
	  js.append("          }\r\n");	 
	  js.append("         else\r\n");	
	  js.append("         {\r\n");	
	  js.append("         		 document.getElementById(iframeName).src=sr+\"?\"+objhidden.name+\"=\"+objhidden.value+\"&\"+objSystemhidden.name+\"=\"+objSystemhidden.value;\r\n");	
	  js.append("          }\r\n");	 
        js.append("}\r\n");
        js.append("function getUserDataHidden(componentId,eventName)\r\n");
        js.append("{\r\n");
        js.append("		var iframeName=componentId + eventName + \"_IFRAME\";\r\n");
        js.append("		var iformName=iframeName + \"_FORM\";\r\n");
        js.append("	    	var ihiddenUserName=\"COMPONENT_DATA_USER\";\r\n");
        js.append("		return eval(\"window.\" + iframeName).document.getElementById(ihiddenUserName);\r\n");
        js.append("}\r\n");
        js.append("");
		jstool.js=js;
		return jstool.getComponentJS() + HashMapJS.getHashMapJS();
	}



}
