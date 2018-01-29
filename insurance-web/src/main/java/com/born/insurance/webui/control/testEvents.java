/*
 * �������� 2003-11-10
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;
import java.util.Map;
import javax.servlet.http.* ;
/**
 * �¼�������
 * @author �ᴺ��
 */
public class testEvents {
	/**
	 * �¼�������ķ���
	 * @param request ��׼��HttpServletRequest
	 * @param response ��׼��HttpServletResponse
	 * @return "success"�Ƿ�ɹ�
	 */
	public static String callbackvalue(HttpServletRequest request,HttpServletResponse response){
		Map map=ServerEvent.getServerEventUserData(request);
 		String clientScript="window.parent.document.getElementById('txtOutPut').value=\"" + map.get("UserData1") + "\"";
 		
 		request.setAttribute(ServerEvent.CLIENT_SCRIPT,"<script>" + clientScript + "</script>");
		return "success";
	}
}
