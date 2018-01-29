/*
 * �������� 2003-10-8
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;

import java.lang.reflect.*;
import java.util.Map;

import javax.servlet.http.* ;
//import org.ofbiz.core.util.*;

/**
 * <P>�����������������¼�</P>
 * @author qch
 */
public class ComponentEvents {
	/**
	 * �����������������¼�
	 * @param request ����
	 * @param response ��Ӧ
	 * @return success or error
	 */
	public static String processRequest(HttpServletRequest request,HttpServletResponse response){
		String componentdata=request.getParameter(ServerEvent.SERVEREVENT_HIDDEN_SYSTEM)==null?"":request.getParameter(ServerEvent.SERVEREVENT_HIDDEN_SYSTEM);
		Map componentMap=ServerEvent.getSetverEventSystemData(request);
		String className = "";
		String methodName="";
		if(componentMap.containsKey("SC"))
		{
			className=componentMap.get("SC").toString();
		}
		if(componentMap.containsKey("SM"))
		{
			methodName=componentMap.get("SM").toString();
		}
		if(className.toLowerCase().equals("null")) className = "";
		if(methodName.toLowerCase().equals("null")) methodName = "";
		if(!className.equals("") && !methodName.equals("")){
		  try{
			  ClassLoader loader = Thread.currentThread().getContextClassLoader();
			  Class businessClass = loader.loadClass(className);
			  //�в��������
			  Class[] paramTypes = new Class[]{ };
			  Object[] params = new Object[]{ };
			  Constructor cn = businessClass.getConstructor(paramTypes);
			  Object object = cn.newInstance(params);
			  //ȱʡ���캯���޲������ֱ�ӵõ�
			  //Object object = businessClass.newInstance();
			  paramTypes = new Class[]{HttpServletRequest.class,HttpServletResponse.class};
			  params = new Object[]{request,response};
			  //�õ�ָ������
			  Method me = businessClass.getMethod(methodName,paramTypes);
			  String returnValue = (String) me.invoke(object,params);
			  String  iformName=request.getParameter("iformName");
			  if(iformName!=null)
				request.setAttribute("FormName",iformName);
			  else
			  {
				request.setAttribute("FormName","");
			  }
			 //��ݷ��ؽ����֯�ͻ������
			 return returnValue;
		}catch(Exception e){
			request.setAttribute(ServerEvent.CLIENT_SCRIPT,"");
			System.out.println("�������¼�'" + className + methodName + "'����:" + e.toString());
			return "error";
		  }
		}
		return "success";
	}

}