/*
 * �������� 2003-10-8
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;

/**
 * @author �ᴺ��
 *
 * ������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
public class Event implements IEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Event()
	{
		
	}
	/**
	 * ���캯���ÿͻ����¼���
	 * @param eventName �¼���
	 */
	public Event(String eventName)
	{
		this.eventName=eventName;
	}
	/**
	 * ��ȡ�ͻ����¼���
	 */
	public String getEventName()
	{
		return this.eventName;
	}
	/**
	 * ���ÿͻ����¼����ù����
	 */
	public void setFuncName(String funcName)
	{
		this.funcName=funcName;
	}
	
	/**
	 * ��ȡ�ͻ����¼����ù����
	 */
	public String getFuncName()
	{
		return this.funcName;
	}
	
	
	//////////////////
	protected String eventName;
	protected String funcName;
	//////////////////
}
