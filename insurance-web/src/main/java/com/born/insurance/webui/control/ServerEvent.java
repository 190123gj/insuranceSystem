/*
 * �������� 2003-10-8
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * @author �ᴺ��
 * @created    2003-10-26
 * @edit  2003-11-30
 * @edit  2003-12-07
 * <P>Description:�������¼�</P>
 * ���������¼��Ĵ���������component.xml����
 */
public class ServerEvent extends Event implements IServerEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ServerEvent(String eventName)
	{
		super(eventName);
		this.serverRequest=ComponentUtil.getInstance().getServerEventRequestPath();
	}
	/* ���� Javadoc��
	 * @see com.bornsoft.core.component.IServerEvent#getClassName()
	 */
	public String getClassName() {
		return this.className;
	}
	/* ���� Javadoc��
	 * @see com.bornsoft.core.component.IServerEvent#setClassName(java.lang.String)
	 */
	public void setClassName(String className) {
		this.className=className;

	}

	/* ���� Javadoc��
	 * @see com.bornsoft.core.component.IServerEvent#getMethodName()
	 */
	public String getMethodName() {
		return this.methodName;
	}

	/* ���� Javadoc��
	 * @see com.bornsoft.core.component.IServerEvent#setMethodName(java.lang.String)
	 */
	public void setMethodName(String methodName) {
		this.methodName=methodName;

	}
	public String getServerRequest()
	{
		return this.serverRequest;
	}
	public void setServerRequest(String serverRequest)
	{
		this.serverRequest=serverRequest;
	}
		
	public void setBshFile(String bshFile) {
		this.bshFile=bshFile;
	}
		
	public String getBshFile()
	{
		return this.bshFile;
	}
	//////////////////////////////
	protected String className="";
	protected String methodName="";
	protected String bshFile="";
	/**
	 * ͨ������
	 */
	protected String serverRequest = "GenericRequest";
	/**
	 * ����û����hidden��
	 */
	public final static String SERVEREVENT_HIDDEN_USER = "COMPONENT_DATA_USER";
	/**
	 * ���ϵͳ���hidden��
	 */
	public final static String SERVEREVENT_HIDDEN_SYSTEM = "COMPONENT_DATA_SYSTEM";
	/**
	 * ���ؽű����key
	 * ҳ���ڳ�ʼ��ʱҪ����
	 */
	public final static String CLIENT_SCRIPT = "CLIENT_SCRIPT";
	//////////////////////////////
	/**
	 * <P>�ӷ������˻�ȡ�û��ύ���</P>
	 * @param request
	 * @return Map
	 */
	public static Map getServerEventUserData(HttpServletRequest request)
	{
		
		return HashMapManager.parseToMap(SERVEREVENT_HIDDEN_USER,request); 
	}
	/**
	 * <P>�ӷ������˻�ȡϵͳ�ύ���</P>
	 * @param request
	 * @return Map
	 */
	public static Map getSetverEventSystemData(HttpServletRequest request)
	{
		return HashMapManager.parseToMap(SERVEREVENT_HIDDEN_SYSTEM,request);
	}
}
