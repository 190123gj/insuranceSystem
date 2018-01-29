/*
 * �������� 2003-10-8
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;

import java.io.Serializable;

/**
 * �¼��ӿ�
 * @author �ᴺ��
 */
public interface IEvent extends Serializable{
	/*
	 * ��ȡ�ͻ����¼���
	 */
	public String getEventName();
	/*
	 * ���ÿͻ����¼����ù����
	 */
	public void setFuncName(String funcName);
	/*
	 * ��ȡ�ͻ����¼����ù����
	 */
	public String getFuncName();
}
