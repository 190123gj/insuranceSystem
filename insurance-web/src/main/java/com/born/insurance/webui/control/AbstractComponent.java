/*
 * �������� 2003-10-8
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.yjf.common.lang.util.StringUtil;

/**
 * @author qch
 * <P>
 * Description:����������
 * </P>
 * ���������̳д˻���
 */
public abstract class AbstractComponent implements IComponent, Cloneable, Serializable {
	
	protected boolean isOutID = true;
	
	/**
	 * <p>
	 * ���캯��
	 * </p>
	 */
	public AbstractComponent() {
		
	}
	
	/**
	 * <P>
	 * ���캯��
	 * </P>
	 * ������ID����һ�����
	 * @param componentId ���ID
	 */
	
	protected AbstractComponent(String componentId) {
		this.componentID = componentId;
		this.browseType = "";
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component.IComponent#getComponentId()
	 */
	@Override
	public String getComponentId() {
		return this.componentID;
	}
	
	public void setComponentId(String componetId) {
		this.componentID = componetId;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getDataSource()
	 */
	@Override
	public Object getDataSource() {
		return this.dataSource;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setDataSource(java.lang.Object)
	 */
	@Override
	public void setDataSource(Object dataSource) {
		this.dataSource = dataSource;
		
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#isVisible()
	 */
	@Override
	public boolean isVisible() {
		return this.visible;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getAttribute(String name)
	 */
	public String getAttribute(String name) {
		if (customAttributes.containsKey(name)) {
			return (String) customAttributes.get(name);
		} else {
			return (String) sourceAttributes.get(name);
		}
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setAttribute(String name, String value)
	 */
	@Override
	public void setAttribute(String name, String value) {
		customAttributes.put(name, value);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#addAttribute(String name, String value)
	 */
	@Override
	public void addAttribute(String name, String value) {
		if (customAttributes.containsKey(name)) {
			customAttributes.put(name, (String) customAttributes.get(name) + value);
		} else {
			customAttributes.put(name, value);
		}
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#removeAttribute(String name)
	 */
	@Override
	public void removeAttribute(String name) {
		customAttributes.remove(name);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getEventList()
	 */
	@Override
	public List getEventList() {
		return this.eventList;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getEvent(java.lang.String)
	 */
	@Override
	public IEvent getEvent(String eventName) {
		IEvent event = null;
		for (int i = 0; i < this.eventList.size(); i++) {
			event = (IEvent) this.eventList.get(i);
			if (event.getEventName().equalsIgnoreCase(eventName)) {
				return event;
			}
		}
		event = null;
		return null;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setEvent(java.lang.String)
	 */
	@Override
	public void setEvent(IEvent event) {
		IEvent temp = null;
		for (int i = 0; i < this.eventList.size(); i++) {
			temp = (IEvent) this.eventList.get(i);
			if (temp.getEventName().equalsIgnoreCase(event.getEventName())) {
				this.eventList.set(i, event);
				return;
			}
		}
		this.addEvent(event);
	}
	
	public void setEvent(String eventName, String functionName) {
		IEvent event = new Event(eventName);
		event.setFuncName(functionName);
		IEvent temp = null;
		for (int i = 0; i < this.eventList.size(); i++) {
			temp = (IEvent) this.eventList.get(i);
			if (temp.getEventName().toLowerCase().equals(event.getEventName().toLowerCase())) {
				this.eventList.set(i, event);
				return;
			}
		}
		this.addEvent(event);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#addEvent(com.bornsoft.core.gaui.component.IEvent)
	 */
	@Override
	public void addEvent(IEvent event) {
		this.eventList.add(event);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#addEvent(java.lang.String, java.lang.String)
	 */
	@Override
	public void addEvent(String eventName, String functionName) {
		Event event = new Event(eventName);
		event.setFuncName(functionName);
		IEvent temp;
		for (int i = 0; i < this.eventList.size(); i++) {
			temp = (IEvent) this.eventList.get(i);
			if (temp.getEventName().toLowerCase().equals(event.getEventName().toLowerCase())) {
				String tempEventName = "";
				if (temp.getFuncName().indexOf(";") < 0) {
					tempEventName = temp.getFuncName() + ";";
				} else {
					tempEventName = temp.getFuncName();
				}
				functionName = tempEventName + functionName;
				event.setFuncName(functionName);
				this.eventList.set(i, event);
				return;
			}
		}
		
		this.addEvent(event);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#addEvent(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addEvent(String eventName, String funcName, String className, String methodName) {
		ServerEvent event = new ServerEvent(eventName);
		event.setFuncName(funcName);
		event.setClassName(className);
		event.setMethodName(methodName);
		this.addEvent(event);
		
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#addEvent(evnentName, funcName, bshFile)
	 */
	public void addEvent(String eventName, String funcName, String bshFile) {
		
		ServerEvent event = new ServerEvent(eventName);
		event.setFuncName(funcName);
		event.setBshFile(bshFile);
		this.addEvent(event);
		
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#hasComponent()
	 */
	@Override
	public boolean hasComponent() {
		if (null == components || components.isEmpty() || 0 == components.size()) {
			return false;
		} else {
			return true;
		}
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#findComponent(java.lang.String)
	 */
	@Override
	public IComponent findComponent(String ID) {
		IComponent component = null;
		for (int i = 0; i < this.components.size(); i++) {
			component = (IComponent) this.components.get(i);
			if (component.getComponentId().equals(ID)) {
				return component;
			}
		}
		component = null;
		return null;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getComponent(int index)
	 */
	@Override
	public IComponent getComponent(int index) {
		if (this.components.size() > index) {
			return (IComponent) this.components.get(index);
		} else {
			return null;
		}
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#addComponent(IComponent component)
	 */
	@Override
	public void addComponent(IComponent component) {
		this.components.add(component);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setCssClass(java.lang.String)
	 */
	@Override
	public void setCssClass(String cssClassName) {
		this.cssClass = cssClassName;
		this.sourceAttributes.put(AbstractComponent.CLASS_ATTRIBUTE, this.cssClass);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getCssClass()
	 */
	@Override
	public String getCssClass() {
		return this.cssClass;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setWidth(String width)
	 */
	@Override
	public void setWidth(String width) {
		this.width = width;
		this.sourceAttributes.put(AbstractComponent.WIDTH_ATTRIBUTE, this.width);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.width;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setHeight(String)
	 */
	@Override
	public void setHeight(String height) {
		this.height = height;
		this.sourceAttributes.put(AbstractComponent.HEIGHT_ATTRIBUTE, this.height);
		
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getHeight()
	 */
	@Override
	public String getHeight() {
		return this.height;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#dataBind()
	 */
	@Override
	public void dataBind() {
		return;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setStyle(java.lang.String)
	 */
	@Override
	public void addStyle(String key, String value) {
		this.style.setStyle(key, value);
		
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setStyle(java.lang.String)
	 */
	@Override
	public void setStyle(String style) {
		this.style.setStyle(style);
		
	}
	
	@Override
	public void setStyle(String key, String value) {
		this.style.setStyle(key, value);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getStyle()
	 */
	@Override
	public String getStyle() {
		return this.style.getInnerHtml();
	}
	
	/*
	 *  ���� Javadoc��
	 * @see com.bornsoft.core.gaui.component.IComponent#getStyle(java.lang.String)
	 */
	@Override
	public String getStyle(String key) {
		return this.style.getStyle(key);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setToolTip(java.lang.String)
	 */
	@Override
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
		this.sourceAttributes.put(AbstractComponent.TOOLTIP_ATTRIBUTE, this.toolTip);
		
	}
	
	/**
	 * @see com.bornsoft.core.gaui.component#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return this.toolTip;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
		this.sourceAttributes.put(AbstractComponent.TABINDEX_ATTRIBUTE,
			String.valueOf(this.tabIndex));
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getTabIndex()
	 */
	@Override
	public int getTabIndex() {
		return this.tabIndex;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setText(String text)
	 */
	@Override
	public void setText(String text) {
		this.text = text;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getText()
	 */
	@Override
	public String getText() {
		return this.text;
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#getOnChangeEvent()
	 */
	@Override
	public final IEvent getOnChangeEvent() {
		return this.getEvent(this.OnChangeEventName);
	}
	
	/*
	 * @see com.bornsoft.core.gaui.component#setOnChangeEvent(com.bornsoft.core.component.IEvent)
	 */
	@Override
	public final void setOnChangeEvent(String funcName) {
		IEvent event = new Event(this.OnChangeEventName);
		event.setFuncName(funcName);
		this.setEvent(event);
	}
	
	/*
	 *  ���� Javadoc��
	 * @see com.bornsoft.core.gaui.component.IComponent#setOnChangeEvent(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public final void setOnChangeEvent(String funcName, String className, String methedName) {
		this.addEvent(this.OnChangeEventName, funcName, className, methedName);
	}
	
	/**
	 * <p>
	 * ����OnChageEvent�ķ�����
	 * </p>
	 * �÷���Ϊһ�������¼�,�������ڻط����Զ�����BeanShell�ļ��еĴ���
	 * @param bshFileName BeanShell����
	 */
	public final void setOnChangeEvent(String funcName, String bshFileName) {
		this.addEvent(this.OnChangeEventName, funcName, bshFileName);
	}
	
	/**
	 * @see com.bornsoft.core.gaui.component#getInnerHtml()
	 */
	@Override
	public final String getInnerHtml() {
		if (this.isVisible()) {
			StringBuffer htmlBody = new StringBuffer(100);
			htmlBody.append(this.getScriptHtml());
			htmlBody.append(this.getElementHtml());
			return htmlBody.toString();
		} else
			return "";
	}
	
	protected String getScriptHtml() {
		return this.getEventsHtml();
	}
	
	protected String getElementHtml() {
		StringBuffer htmlBody = new StringBuffer(100);
		if (StringUtil.isNotEmpty(this.getComponentId()) && this.isOutID) {
			htmlBody.append(" "
							+ AbstractComponent.pairAttribute(ID_ATTRIBUTE, this.getComponentId()));
		}
		if (this.enabled == false) {
			htmlBody.append(" disabled");
		}
		if (this.name != null && this.name.length() != 0) {
			htmlBody.append(" " + AbstractComponent.pairAttribute(NAME_ATTRIBUTE, this.name));
			
		}
		for (Iterator i = this.sourceAttributes.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			if (!("").equals(e.getValue()) && e.getValue() != null) {
				htmlBody.append(" "
								+ AbstractComponent.pairAttribute((String) e.getKey(),
									(String) e.getValue()));
			} else if ("value".equalsIgnoreCase((String) e.getKey()) && e.getValue() != null) {
				htmlBody.append(" "
								+ AbstractComponent.pairAttribute((String) e.getKey(),
									(String) e.getValue()));
			}
			
		}
		for (Iterator i = this.customAttributes.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Map.Entry) i.next();
			if (!("").equals(e.getValue()))
				htmlBody.append(" "
								+ AbstractComponent.pairAttribute((String) e.getKey(),
									(String) e.getValue()));
		}
		for (int i = 0; i < this.eventList.size(); i++) {
			IEvent event = (IEvent) this.eventList.get(i);
			if (event.getClass() == ServerEvent.class) {
				htmlBody.append(" " + event.getEventName() + "=\"ServerEventFunc" + "('"
								+ this.componentID + "','" + event.getEventName() + "')\"");
				htmlBody.append(" SF" + event.getEventName() + "=\"" + event.getFuncName() + "\"");
				String className = ((ServerEvent) event).getClassName().toString();
				String methodName = ((ServerEvent) event).getMethodName().toString();
				String bshFile = ((ServerEvent) event).getBshFile().toString();
				if (!className.equals("")) {
					htmlBody.append(" SC" + event.getEventName() + "=\"" + className + "\"");
				}
				if (!methodName.equals("")) {
					htmlBody.append(" SM" + event.getEventName() + "=\"" + methodName + "\"");
				}
				htmlBody.append(" SR" + event.getEventName() + "=\""
								+ ((ServerEvent) event).getServerRequest() + "\"");
				if (!bshFile.equals("")) {
					htmlBody.append(" BSH" + event.getEventName() + "=\"" + bshFile + "\"");
				}
			} else {
				htmlBody.append(" " + event.getEventName() + "=\"" + event.getFuncName() + "\"");
			}
		}
		htmlBody.append(this.style.getInnerHtml());
		return htmlBody.toString();
	}
	
	protected boolean isHaveServerEvent() {
		for (int i = 0; i < this.eventList.size(); i++) {
			IEvent event = (IEvent) this.eventList.get(i);
			if (event.getClass() == ServerEvent.class) {
				return true;
			}
		}
		return false;
	}
	
	//////////////////////////��������
	protected static String pairAttribute(String name, String value) {
		if (value == null) {
			value = "null";
		}
		int qPos = value.indexOf('"');
		if (qPos >= 0) {
			StringBuffer buf = new StringBuffer(value.length() * 2);
			int startPos = 0;
			do {
				buf.append(value.substring(startPos, qPos));
				buf.append(QUOTE_ENTITY);
				startPos = qPos + 1;
				qPos = value.indexOf('"', startPos);
			} while (qPos >= 0);
			buf.append(value.substring(startPos));
			value = buf.toString();
		}
		return name + "=\"" + value + "\"";
	}
	
	protected String assembleEndTag() {
		return "</" + getElementType() + ">";
	}
	
	protected String getElementType() {
		return this.elementType;
	}
	
	protected void setElementType(String elementType) {
		this.elementType = elementType;
	}
	
	protected String getEventsHtml() {
		if (this.isHaveServerEvent()) {
			return ServerEventJS.getServerEventJS();
		}
		return "";
	}
	
	/**
	 * <p>
	 * Clones, and keeps track of original instance.
	 * </p>
	 * 
	 * @return Shallow clone of object.
	 * @throws CloneNotSupportedException
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		AbstractComponent ret = (AbstractComponent) super.clone();
		ret.customAttributes = new HashMap();
		ret.customAttributes.putAll(this.customAttributes);
		ret.sourceAttributes = new HashMap();
		ret.sourceAttributes.putAll(this.sourceAttributes);
		ret.components = new ArrayList();
		ret.components.addAll(this.components);
		ret.style = new Style();
		ret.style.style.putAll(this.style.style);
		ret.eventList = new ArrayList();
		ret.eventList.addAll(this.eventList);
		return ret;
	}
	
	/////////////////////��������
	protected String componentID = "";
	protected String name = "";
	protected Object dataSource = null;
	protected List eventList = new ArrayList();
	protected boolean visible = true;
	protected boolean enabled = true;
	protected String cssClass = "";
	protected List components = Lists.newArrayList();
	protected String width = "";
	protected String height = "";
	protected String toolTip = "";
	protected int tabIndex;
	protected String text = "";
	protected String realPath = "";
	protected boolean isMakeScript = true;
	/***
	 * <p>
	 * Tag attributes read from the source template markup.
	 * </p>
	 */
	protected Map sourceAttributes = new HashMap();
	/***
	 * <p>
	 * Tag attributes set in the component.
	 * </p>
	 */
	protected Map customAttributes = new HashMap();
	protected Style style = new Style();
	
	protected String OnChangeEventName = "onchange";
	
	//////////////////////
	
	private String browseType = "";
	
	private String elementType = "";
	/***
	 * <p>
	 * Entity representing a double quote, used when an attribute value contains
	 * double quotes.
	 * </p>
	 */
	private static final String QUOTE_ENTITY = "&#34;";
	/***
	 * Name of ID attribute, used to uniquely identify an element in the page
	 */
	private static final String ID_ATTRIBUTE = "id";
	/***
	 * <p>
	 * Name of CSS (Cascading Style Sheet) CLASS attribute, used to specify a
	 * style from the style sheet to use with the element.
	 * </p>
	 */
	private static final String CLASS_ATTRIBUTE = "class";
	
	private static final String NAME_ATTRIBUTE = "name";
	
	private static final String STYLE_ATTRIBUTE = "style";
	
	private static final String HEIGHT_ATTRIBUTE = "height";
	
	private static final String WIDTH_ATTRIBUTE = "width";
	
	private static final String ENABLED_ATTRIBUTE = "disabled";
	
	private static final String TABINDEX_ATTRIBUTE = "tabIndex";
	
	private static final String TOOLTIP_ATTRIBUTE = "title";
	
	public boolean isMakeScript() {
		return isMakeScript;
	}
	
	public void setMakeScript(boolean isMakeScript) {
		this.isMakeScript = isMakeScript;
	}
	
	public boolean isOutID() {
		return isOutID;
	}
	
	public void setOutID(boolean isOutID) {
		this.isOutID = isOutID;
	}
	
	@Override
	public String toString() {
		return this.getInnerHtml();
	}
}
