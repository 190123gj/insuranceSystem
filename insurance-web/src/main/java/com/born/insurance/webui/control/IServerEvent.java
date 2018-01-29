/*
 * �������� 2003-10-8
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;

/**
 * <P>�������¼��ӿ�</P>
 * <P>�ͻ����¼����ͻ����¼����ɿͻ�������������������Զ�����
 * <P>��ӦJavaScript��̵��¼���<P>
 * <P>�������¼�:�ɿͻ�������������������Զ��ύָ����ݵ�</P>
 * <P>�������˲�������ݵ��ͻ��˵��¼�</P>
 * @author �ᴺ��
 */
public interface IServerEvent extends IEvent {
	/**
	 * ��ȡ�����¼�������
	 * @return ����
	 */
	public String getClassName();
	/**
	 * �����¼���ķ�����
	 * @return ������
	 */
	public String getMethodName();
	/**
	 * <P>���ô����¼���ķ�����</P>
	 * <P>���¼����д����VPƽ̨���¼���һ��</P>
	 * <P>��(HttpServeletRequest��HttpServeletResponse)����</P>
	 * @param methodName ��ķ���������
	 */
	public void setMethodName(String methodName);
	/**
	 * ���ô����¼������� 
	 * @param className
	 */
	public void setClassName(String className);
	/**
	 * ���÷������¼��ط��󽫵��õ�BeanShell��ʽ���ļ���(����·����)
	 * @param bshFile BeanShell�ļ���
	 */
	public void setBshFile(String bshFile);
	/**
	 * ��ȡBeanShell�ļ���(����·����)
	 * @return �ļ���
	 */
	public String getBshFile();
	/**
	 * ��ȡ�������¼��н��մ��������
	 * @return
	 */
	public String getServerRequest();
	/**
	 * ���÷������¼��н��մ�������
	 * һ�������������,����е�����������
	 * @param serverRequest ����������
	 */
	public void setServerRequest(String serverRequest);
}
