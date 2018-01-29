/*
 * �������� 2003-10-8
 *
 *
 */
package com.born.insurance.webui.control;
import java.util.List;
/**
 * @author �ᴺ��
 * <p>Title:IComponent </p>
 * <p>Description:������ӿ�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:���첩��������޹�˾ </p>
 * @version 1.0
 */
public interface IComponent {
	/**
	 * <p>��ȡ���ID.</p>
	 */
	public String getComponentId();
	/**
	 * <p>��ȡ��������Դ.</p>
	 * @see #setDataSource
	 */
	public Object getDataSource();
	/**
	 * <p>������������Դ.</p>
	 * @param dataSource ���Դ����ΪMap��ArrayList���ͣ�
	 * @see #getDataSource
	 */
	public void setDataSource(Object dataSource);
	/**
	 * <p>�������������.</p>
	 * @param key ������
	 * @param value ����ֵ
	 * @see #setAttribute(String key, String value)
	 * @see #removeAttribute(String key)
	 */
	public void addAttribute(String key, String value);
	/**
	 * <p>�������������.</p>
	 * @param key ������
	 * @param value ����ֵ
	 * @see #addAttribute(String key, String value)
	 * @see #removeAttribute(String key)
	 */
	public void setAttribute(String key, String value);
	/**
	 * <p>�Ƴ�ֵΪKey��Attribute.</p>
	 * @param key ������
	 * @see #addAttribute(String key, String value)
	 * @see #setAttribute(String key, String value)
	 */
	public void removeAttribute(String key);

	/**
	 * <p>�����������ʾ����</p>
	 * @see #isVisible
	 */
	public void setVisible(boolean visible);
	/**
	 * <p>��ȡ�������ʾ����</p>
	 * @see #setVisible(boolean visible)
	 */
	public boolean isVisible();
	/**
	 * <p>��ȡ������¼�����</p>
	 */
	public List getEventList();
	/**
	 * ��ȡָ���¼�����¼�����
	 * @param eventName �¼���
	 * @see #setEvent(IEvent event)
	 * @see #addEvent(IEvent event)
	 * @see #addEvent(String eventName, String funcName)
	 * @see #addEvent(String eventName, String funcName, String className, String methodName)
	 * @see #getEventList()
	 */
	public IEvent getEvent(String eventName);
	/**
	 * <p>�����¼�</p>
	 * @param event �¼�
	 * @see #addEvent(IEvent event)
	 * @see #addEvent(String eventName, String funcName)
	 * @see #addEvent(String eventName, String funcName, String className, String methodName)
	 * @see #getEvent(String eventName)
	 * @see #getEventList()
	 */
	public void setEvent(IEvent event);
	/**
	 * <p>�����¼�</p>
	 * @param event �¼�
	 * @see #setEvent(IEvent event)
	 * @see #addEvent(String eventName, String funcName)
	 * @see #addEvent(String eventName, String funcName, String className, String methodName)
	 * @see #getEvent(String eventName)
	 * @see #getEventList()
	 */
	public void addEvent(IEvent event);
	/**
	 * <p>�����¼�</p>
	 * @param eventName �¼���
	 * @param funcName ���ù����
	 * @see #setEvent(IEvent event)
	 * @see #addEvent(IEvent event)
	 * @see #addEvent(String eventName, String funcName, String className, String methodName)
	 * @see #getEvent(String eventName)
	 * @see #getEventList()
	 */
	public void addEvent(String eventName, String funcName);
	/**
	 * <p>�����¼�</p>
	 * @param event �¼�
	 * @param funcName ���ù����
	 * @param className �������¼���������
	 * @param methodName �������¼����÷�����
	 * @see #setEvent(IEvent event)
	 * @see #addEvent(String eventName, String funcName)
	 * @see #addEvent(IEvent event)
	 * @see #getEvent(String eventName)
	 * @see #getEventList()
	 */
	public void addEvent(String eventName, String funcName, String className, String methodName);

	/**
	 * <p>�Ƿ�ӵ���ӿؼ�</p>
	 */
	public boolean hasComponent();

	/**
	 * <p>��ȡָ��ID�������</p>
	 * @param ID �����ID
	 */
	public IComponent findComponent(String ID);

    /**
     * <p>��ȡָ������������</p>
     * @param index ���������
     */
    public IComponent getComponent(int index);
	/**
	 * <p>���������</p>
	 * @param IComponent �����
	 */
    public void addComponent(IComponent component);

	/**
	 * <p>�����������ʽ��</p>
	 * @param cssClassName ��ʽ����
	 */
	public void setCssClass(String cssClassName);

	/**
	 * <p>��ȡ�������ʽ����</p>
	 */
	public String getCssClass();

	/**
	 * <p>����������</p>
	 * @param width ���
	 */
	public void setWidth(String width);

	/**
	 * <p>��ȡ������</p>
	 */
	public String getWidth();

	/**
	 * <p>��������߶�</p>
	 * @param height �߶�
	 */
	public void setHeight(String height);
	/**
	 * <p>��ȡ����߶�</p>
	 */
	public String getHeight();
	/**
	 * <p>����ǰ�����Դ�󶨵����</p>
	 * �����ú����Դ�����Ҫ������ɶ�������ô˷���.
	 * @see #setDataSource(Object dataSource)
	 */
	public void dataBind();
	/**
	 * <p>�����������ʽ</p>
	 * @param key ��ʽkey
	 * @param value ��ʽֵ
	 * @see #setStyle(String style)
	 * @see #setStyle(String key,String value)
	 * @see #getStyle()
	 * @see #getStyle(String key)
	 */
	public void addStyle(String key,String value);
	/**
	 * <p>�����������ʽ</p>
	 * @param style ��ʽ
	 * @see #addStyle(String key,String value)
	 * @see #setStyle(String key,String value)
	 * @see #getStyle()
	 * @see #getStyle(String key)
	 */
	public void setStyle(String style);
	/**
	 * <p>�����������ʽ</p>
	 * @param key ��ʽkey
	 * @param value ��ʽvalue
	 * @see #addStyle(String key,String value)
	 * @see #setStyle(String style)
	 * @see #getStyle()
	 * @see #getStyle(String key)
	 */
	public void setStyle(String key,String value);

	/**
	 * <p>��ȡ�������ʽ</p>
	 * @see #addStyle(String key,String value)
	 * @see #setStyle(String style)
	 * @see #setStyle(String key,String value)
	 * @see #getStyle(String key)
	 */
	public String getStyle();
	/**
	 * <p>��ȡָ��key���������ʽ</p>
	 * @param key �����ʽkey
	 * @see #addStyle(String key,String value)
	 * @see #setStyle(String style)
	 * @see #setStyle(String key,String value)
	 * @see #getStyle()
	 */
	public String getStyle(String key);
	/**
	 * <p>���������ʾ</p>
	 * @param toolTip �����ʾ
	 */
	public void setToolTip(String toolTip);
	/**
	 * <p>��ȡ�����ʾ</p>
	 */
	public String getToolTip();
	/**
	 * <p>����Ƿ����</p>
	 * @see #setEnabled(boolean enabled)
	 */
	public boolean isEnabled();
	/**
	 * <p>��������Ƿ����</p>
	 * @param enabled �Ƿ����
	 * @see #isEnabled()
	 */
	public void setEnabled(boolean enabled);
	/**
	 * <p>���������Tab˳��</p>
	 * @param tabIndex Tab˳��
	 * @see #getTabIndex()
	 */
	public void setTabIndex(int tabIndex);
	/**
	 * <p>��ȡ�����Tab˳��</p>
	 * @see #setTabIndex(int tabIndex)
	 */
	public int getTabIndex();
	/**
	 * ������ʾ����
	 * @param text ��ʾ����
	 * @see #getText()
	 */
	public void setText(String text);
	/**
	 * ��ȡ��ʾ����
	 * @see #setText(String text)
	 */
	public String getText();
	/**
	 * <p>��ȡ�ı��¼�����</p>
	 * @see #setOnChangeEvent(IEvent event)
	 */
	public IEvent getOnChangeEvent();
	/**
	 * <p>���øı��¼�����</p>
	 * @param event �¼�����
	 * @see #getOnChangeEvent()
	 */
        public void setOnChangeEvent(String funcName);
        /**
         * <p>���øı��¼�����</p>
         * @param Serverevent �¼�����
         * @see #getOnChangeEvent()
         */
        public void setOnChangeEvent(String funcName, String className,
                                     String methedName);

	/**
	 * <p>���������Ӧ��html�ű�</p>
	 */
	public String getInnerHtml();

}
