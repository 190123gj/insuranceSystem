/**
 * Created on 2003-10-13
 *
 */
package com.born.insurance.webui.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: SearchText component
 * </p>
 * <p>
 * Description: ��SearchData���ʹ�ã�����ΪSearchData�ṩ��ѯ������SearchData����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003 11
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author He Kun
 * @since 1.0
 * 
 */
public class SearchText extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�����Ǹÿؼ��Ļ����
	private Table layoutTable; //���Ʋ��ֵ�table
	private final TextBox textBox;
	private final Hidden hidden;
	private final Image button;
	//������SearchText����Ҫ���ԣ����ڿͻ���Ҳ�ж�Ӧ�Ŀͻ������ԺͲ��ݷ���
	private String text = "";
	private String value = "";
	private boolean readonly = false;
	private boolean enabled = true;
	private String textBoxClass = "";
	private String buttonClass = "";
	private String pageUrl = "";
	private String hotKey = "F2";
	private int windowWidth = 290;
	private int windowHeight = 400;
	private boolean autoSearch = true;
	private String searchEntity = "";
	private String keyField = "";
	private String codeField = "";
	private String nameField = "";
	private String listTitle = "";
	private final HashMapManager captionMap;
	private String textFormula = "";
	protected static String disImage = "disgoldSearch.gif";
	protected static String enableImage = "goldSearch.gif";
	//private ArrayListManager dataFields;
	boolean debug = false;
	
	/**
	 * ���캯���һ��SearchText
	 * @param componentId Ҫ������SearchText��id
	 */
	public SearchText(String componentId) {
		super(componentId);
		this.OnChangeEventName = "searchTextOnChange";
		this.textBox = new TextBox(componentId + "_textbox");
		this.hidden = new Hidden(componentId + "_hidden");
		this.button = new Image(componentId + "_button");
		this.captionMap = new HashMapManager(componentId + "_captions");
		//	this.dataFields=new ArrayListManager(componentId+"_dataFields");
		init();
		initDefault();
	}
	
	/**
	 * ��ʼ���������
	 * 
	 */
	private void init() {
		this.layoutTable = new Table(getComponentId() + "_layoutTable", 2, 1);
		//this.setOnChangeEvent("document.getElementById('"+this.getComponentId()+"').setAttribute('fillBackFinished',false);alert('dfdf');");
		this.layoutTable.setCellPadding(0);
		this.layoutTable.setCellSpacing(0);
	}
	
	/**
	 * ��ʼ��Ĭ�ϵĲ���
	 * 
	 */
	private void initDefault() {
		ComponentUtil util = ComponentUtil.getInstance();
		String path = util.getSearchDataRequestPath();
		if (path != null && !"".equals(path))
			this.setPageUrl(path);
		String imageSrc = util.getImagesRequestPath() + "/" + enableImage;
		setButtonImageSrc(imageSrc);
		setWindowWidth(290);
		setWindowHeight(400);
		setButtonClass("buttonCss");
		setCssClass("labelCss");
		setTextBoxClass("inputText");
		setWidth("150");
		//this.layoutTable.getRow(0).setStyle("vertical-align","bottom");
		this.button.setStyle("BORDER-RIGHT", "#999999 0px solid;");
		this.button.setStyle("BORDER-TOP", " #999999 0px solid;");
		this.button.setStyle("BORDER-LEFT", " #999999 0px solid;");
		this.button.setStyle("BORDER-BOTTOM", " #999999 0px solid;");
		this.button.setStyle("CURSOR", "hand");
		
		this.setHotKey("F2");
		this.textBox.setEvent("onkeydown", "return SearchText_onkeyDown('" + this.componentID
											+ "')");
	}
	
	/**
	 * ������searchText��html����
	 * @return ���searchText��html����
	 */
	@Override
	protected String getElementHtml() {
		if (this.isVisible()) {
			if (!this.readonly) {
				this.button.addEvent("onClick", "SearchText_setFillBackFinished('"
												+ this.componentID
												+ "',false);SearchText_popupQueryList2('"
												+ this.componentID + "',false);");
			} else {
				ComponentUtil util = ComponentUtil.getInstance();
				String imageSrc = util.getImagesRequestPath() + "/" + disImage;
				setButtonImageSrc(imageSrc);
			}
			this.setAttribute("setValue", "SearchText_setValue");
			this.setAttribute("getValue", "SearchText_getValue");
			this.setAttribute("setFocus", "SearchText_setFocus");
			this.setAttribute("setText", "SearchText_setText");
			this.setAttribute("getText", "SearchText_getText");
			this.setAttribute("setProperty", "SearchText_setProperty");
			this.setAttribute("getProperty", "SearchText_getProperty");
			this.setAttribute("getFillBackFinished", "SearchText_getFillBackFinished");
			this.setAttribute("fillBackFinished", "true");
			this.setAttribute("autoSearch", String.valueOf(autoSearch));
			this.setAttribute("oldValue", this.text == null ? "" : this.text);
			//����Ĭ�ϵķ������¼�
			if (this.pageUrl != null && !("").equals(this.pageUrl) && this.autoSearch == true) {
				IServerEvent textBoxEvent = new ServerEvent("onblur");
				textBoxEvent.setFuncName("SearchText_prepareUserData('"
											+ this.textBox.getComponentId() + "','onblur','"
											+ this.getComponentId() + "');");
				textBoxEvent.setServerRequest(this.pageUrl);
				this.textBox.addEvent(textBoxEvent);
			}
			String html = "<span " + super.getElementHtml() + " >";
			this.layoutTable.getRow(0).getTableCell(0)
				.setText(this.textBox.getElementHtml() + this.hidden.getElementHtml());
			String html2 = button.getElementHtml() + captionMap.getElementHtml(); //+dataFields.getElementHtml();
			this.layoutTable.getRow(0).getTableCell(1).setText(html2);
			html += layoutTable.getElementHtml();
			html += "</span>";
			this.button.eventList.clear();
			return html;
		} else
			return "";
	}
	
	/**
	 * ��ȡSearchText���ı���
	 * @return �ı��������
	 */
	public TextBox getTextBox() {
		return textBox;
	}
	
	/**
	 * ��ȡSearchText��hidden
	 * @return hidden�ؼ�������
	 */
	public Hidden getHidden() {
		return hidden;
	}
	
	/**
	 * ��ȡSearchText��ͼƬ��ť
	 * @return ͼƬ��ť������
	 */
	public Image getButton() {
		return button;
	}
	
	/**
	 * ����SearchText��javascript�ļ��������ؽű��ļ���html����
	 * @return ���ؽű��ļ���html����
	 */
	@Override
	protected String getScriptHtml() {
		if (!this.isMakeScript) {
			return "";
		}
		String scriptReference = super.getScriptHtml() + SearchTextJS.getSearchTextJS()
									+ ServerEventJS.getServerEventJS() + HashMapJS.getHashMapJS()
									+ ArrayListJS.getArrayListJS();
		return scriptReference;
	}
	
	/**
	 * ��ȡ��ť��css��ʽ
	 * @return ��ť��css��ʽ
	 */
	public String getButtonClass() {
		return buttonClass;
	}
	
	/**
	 * ��ȡ��ť��ͼƬ�ļ�·��
	 * @return ��ť��ͼƬ�ļ�·��
	 */
	public String getButtonImageSrc() {
		return this.button.getSrc();
	}
	
	/**
	 * ��ȡSearchText�����ҳ�棬ͨ����SearchData����ҳ�������
	 * @return SearchText����ҳ���URL
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	
	/**
	 * ��ȡSearchText��ֻ������
	 * @return SearchText��ֻ������
	 */
	public boolean isReadonly() {
		return readonly;
	}
	
	/**
	 * ��ȡSearchText����ʾֵ
	 * @return SearchText����ʾֵ
	 */
	@Override
	public String getText() {
		return text;
	}
	
	/**
	 * ��ȡSearchText����ʵֵ(ʵ��ֵ)
	 * @return SearchText����ʵֵ(ʵ��ֵ)
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * ��ȡSearchTextҪ�򿪵�SearchData���ڵĸ߶�(����)
	 * @return SearchTextҪ�򿪵�SearchData���ڵĸ߶�
	 */
	public int getWindowHeight() {
		return windowHeight;
	}
	
	/**
	 * ��ȡSearchTextҪ�򿪵�SearchData���ڵĿ��(����)
	 * @return SearchTextҪ�򿪵�SearchData���ڵĿ��(����)
	 */
	public int getWindowWidth() {
		return windowWidth;
	}
	
	/**
	 * ���ð�ť��css���
	 * @param css class name
	 */
	public void setButtonClass(String string) {
		this.button.setAttribute("class", string);
		buttonClass = string;
	}
	
	/**
	 * ����ͼƬ��ť��ͼƬ
	 * @param ͼƬ��URL
	 */
	public void setButtonImageSrc(String string) {
		this.button.setSrc(string);
	}
	
	/**
	 * ����SearchTextҪ�����URL,ͨ����SearchData����ҳ�������
	 * @param string
	 */
	public void setPageUrl(String pageUrl) {
		this.setAttribute("pageUrl", pageUrl);
		this.pageUrl = pageUrl;
	}
	
	/**
	 * ����SearchText�Ƿ�ֻ��
	 * @param b true/false
	 */
	public void setReadOnly(boolean readOnly) {
		this.setAttribute("readonly", String.valueOf(readOnly));
		this.textBox.setReadOnly(readOnly);
		readonly = readOnly;
	}
	
	/**
	 * ����SearchText�ĳ�ʼ��ʾֵ
	 * @param string SearchText�ĳ�ʼ��ʾֵ
	 */
	@Override
	public void setText(String string) {
		//�ı��򱣴� Text, hidden ����ʵ��ֵ
		this.textBox.setText(string);
		this.text = string;
	}
	
	/**
	 * ����SearchText�ĳ�ʼ��ʵֵ
	 * @param string SearchText�ĳ�ʼ��ʵֵ
	 */
	public void setValue(String string) {
		//		�ı��򱣴� Text, hidden ����ʵ��ֵ
		this.hidden.setText(string);
		value = string;
	}
	
	/**
	 * ����SearchTextҪ�򿪵�SearchData���ڵĸ߶�(����)
	 * @param SearchData���ڵĸ߶�(����)
	 */
	public void setWindowHeight(int i) {
		this.setAttribute("windowHeight", "" + i);
		windowHeight = i;
	}
	
	/**
	 * ����SearchTextҪ�򿪵�SearchData���ڵĿ��(����)
	 * @param SearchData���ڵĿ��(����)
	 */
	public void setWindowWidth(int i) {
		this.setAttribute("windowWidth", "" + i);
		windowWidth = i;
	}
	
	/**
	 * ��ȡSearchText�ı����css��ʽ
	 * @return SearchText�ı����css��ʽ
	 */
	public String getTextBoxClass() {
		return textBoxClass;
	}
	
	/**
	 * ����SearchText�ı����css��ʽ
	 * @param string SearchText�ı����css��ʽ
	 */
	public void setTextBoxClass(String string) {
		this.textBox.setCssClass(string);
		textBoxClass = string;
	}
	
	/**
	 * �ͻ���onChange�¼�
	 */
	//	public void setOnChangeEvent(String funcStr)
	//	{//�ͻ����Զ����¼����������textBox��onchange�ϣ��ɳ���Աͨ�������ã�
	//		IEvent onChangeEvent=this.getOnChangeEvent();
	//		if(onChangeEvent!=null)
	//		{
	//			funcStr=onChangeEvent.getFuncName()+"; "+funcStr;
	//		}
	//		IEvent newEvent = new Event("onChangeEvent");
	//		newEvent.setFuncName(funcStr);
	//		this.setEvent(newEvent); //�ڱ��ؼ��Ϲ������¼�
	//	}
	//		/**
	//		 *���÷������¼������¼��ڻ�����Զ�����
	//		 * @param funcStr
	//		 * @param className
	//		 * @param methodName
	//		 */
	//	public void setOnChangeEvent(String funcStr ,String className, String methodName)
	//	{	//�������¼���Ҫ���棬ҲҪ������textBox��
	//		 IServerEvent event = new ServerEvent("onChangeServerEvent");
	//		 event.setFuncName(funcStr);
	//		 event.setClassName(className);
	//		 event.setMethodName(methodName);		 
	//		 this.setEvent(event);		 
	//	}
	//	public void setOnChangeEvent(String funcStr ,String bshName)
	//	{	//�������¼���Ҫ���棬ҲҪ������textBox��
	//		 IServerEvent event = new ServerEvent("onChangeServerEvent");
	//		 event.setFuncName(funcStr);
	//		 event.setBshFile(bshName);	 
	//		 this.setEvent(event);		 
	//	}
	//    /**
	//    *��ȡ�Զ���Ŀͻ����¼�����
	//    */
	//	public IEvent getOnChangeEvent()
	//	{
	//		return this.getEvent("onChangeEvent");
	//	}
	//	/**
	//		*��ȡ�Զ���Ŀͻ����¼�����
	//		*/
	//	public IEvent getOnChangeServerEvent()
	//	{
	//		return this.getEvent("onChangeServerEvent");
	//	}
	/**
	 * ���ؿؼ����Ƿ��������
	 * @return true/false
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * ���ÿؼ��Ŀ�������
	 * @param b true/false
	 */
	@Override
	public void setEnabled(boolean b) {
		this.textBox.setEnabled(b);
		//this.button.setEnabled(b);
		this.button.setSrc(ComponentUtil.getInstance().getImagesRequestPath() + "/" + disImage);
		enabled = b;
	}
	
	/**
	 * ���ÿؼ��Ŀ��
	 */
	@Override
	public void setWidth(String width) {
		this.setStyle("width", width);
		
		int intValue = getUnitValue(width);
		
		String unit = getUnitUnit(width);
		int intTextBoxWidth = intValue - 17;
		String strTextBoxWidth = intTextBoxWidth + unit;
		//setHeight("18px");
		//this.layoutTable.setStyle("width",width);
		this.textBox.setStyle("width", strTextBoxWidth);
		this.layoutTable.getRow(0).getTableCell(0).setStyle("width", strTextBoxWidth);
		this.layoutTable.setBorder(0);
		this.button.setStyle("width", "17px");
		this.button.setStyle("height", "17px");
		this.button.setStyle("width", "17");
		this.button.setStyle("height", "17");
		//this.layoutTable.getRow(0).getTableCell(1).setStyle("width", "20");
		super.width = width;
	}
	
	/**
	 * ���ÿؼ��߶�
	 */
	@Override
	public void setHeight(String height) {
		this.textBox.setStyle("height", height);
		this.button.setStyle("height", height);
		this.setStyle("height", height);
		super.height = height;
	}
	
	/**
	 * �����Զ���������
	 * @return �Զ���������
	 */
	public boolean getAutoSearch() {
		return autoSearch;
	}
	
	/**
	 * �����Զ��������ԣ��Զ�����Ϊfalseʱ�����ᵯ��SearchData�Ի����ڣ����ҿؼ�����ʾֵ��Ϊ��
	 * ��ֵ������ֻ����getText�������
	 * @param b �Ƿ��Զ�����
	 */
	public void setAutoSearch(boolean b) {
		autoSearch = b;
	}
	
	/**
	 * ����Ҫ��ѯ��ʵ�����
	 * @return Ҫ��ѯ��ʵ�����
	 */
	public String getSearchEntity() {
		return searchEntity;
	}
	
	/**
	 * ����Ҫ��ѯ��ʵ�����
	 * @param entityName Ҫ��ѯ��ʵ�����
	 */
	public void setSearchEntity(String entityName) {
		this.setAttribute("searchEntity", entityName);
		searchEntity = entityName;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�����ʾ�ı�(�������˵��ã�Ӧ����form�ύ)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�����ʾֵ
	 * @deprecated As of this version
	 */
	@Deprecated
	public static String getText(String controlId, HttpServletRequest request) {
		return getText(request, controlId);
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�����ʵֵ(�������˵��ã�Ӧ����form�ύ)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�����ʵֵ
	 * @deprecated As of this version
	 */
	@Deprecated
	public static String getValue(String controlId, HttpServletRequest request) {
		return getValue(request, controlId);
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�����ʾ�ı�(�������˵��ã�Ӧ����form�ύ)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�����ʾֵ
	 */
	public static String getText(HttpServletRequest request, String controlId) {
		String text = request.getParameter(controlId + "_textbox");
		return text;
	}
	
	public static String getText(RequestUtils request, String controlId) {
		String text = request.getString(controlId + "_textbox");
		return text;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�����ʵֵ(�������˵��ã�Ӧ����form�ύ)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�����ʵֵ
	 */
	public static String getValue(HttpServletRequest request, String controlId) {
		String value = request.getParameter(controlId + "_hidden");
		return value;
	}
	
	public static String getValue(RequestUtils request, String controlId) {
		String value = request.getString(controlId + "_hidden");
		return value;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�����ʾ�ı�(�������˵���)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�����ʾֵ
	 */
	public static String getText(HttpServletRequest request) {
		String text = request.getParameter("text");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			text = (String) userData.get("text");
		}
		if (text == null)
			text = "";
		return text;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�����ʵֵ(�������˵���)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�����ʵֵ
	 */
	public static String getValue(HttpServletRequest request) {
		String value = request.getParameter("value");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			value = (String) userData.get("value");
		}
		if (value == null)
			value = "";
		return value;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�Ҫ��ѯ��ʵ���key�ֶ���(�������˵���)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�Ҫ��ѯ��ʵ��key�ֶ���
	 * @deprecated
	 */
	@Deprecated
	public static String getKeyField(HttpServletRequest request) {
		String keyField = request.getParameter("keyField");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			keyField = (String) userData.get("keyField");
		}
		if (keyField == null || ("").equals(keyField))
			keyField = getCodeField(request);
		return keyField;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�Ҫ��ѯ��ʵ���codeField(�������˵���)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�Ҫ��ѯ��ʵ���codeField
	 * @deprecated
	 */
	@Deprecated
	public static String getCodeField(HttpServletRequest request) {
		String codeField = request.getParameter("codeField");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			codeField = (String) userData.get("codeField");
		}
		return codeField;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�Ҫ��ѯ��ʵ�������ֶ�(�������˵���)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�Ҫ��ѯ��ʵ�������ֶ�
	 * @deprecated
	 */
	@Deprecated
	public static String getNameField(HttpServletRequest request) {
		String nameField = request.getParameter("nameField");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			nameField = (String) userData.get("nameField");
		}
		return nameField;
	}
	
	/**
	 * ��ȡ�ͻ��˿ؼ�Ҫ��ѯ��ʵ�����(�������˵���)
	 * @param controlId
	 * @param request
	 * @return �ͻ��˿ؼ�Ҫ��ѯ��ʵ�����
	 */
	public static String getSearchEntity(HttpServletRequest request) {
		String searchEntity = request.getParameter("searchEntity");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			searchEntity = (String) userData.get("searchEntity");
		}
		return searchEntity;
	}
	
	/**
	 * ��ȡ��SearchText�����õı������SearchData�İ��ֶ����飨����ʱʹ�ã�
	 * @param request
	 * @return SearchData�İ��ֶ�����
	 */
	public static List getDataFields(HttpServletRequest request) {
		String dataFields[];
		List fields = new ArrayList();
		String dataFieldsString = request.getParameter("dataFields");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			dataFieldsString = (String) userData.get("dataFields");
		}
		if (dataFieldsString != null) {
			dataFields = StringUtils.split(dataFieldsString, ","); //dataFieldsString.split(",");
			for (int i = 0; i < dataFields.length; i++) {
				fields.add(dataFields[i]);
			}
			return fields;
		}
		return null;
	}
	
	/**
	 * ��ȡ��SearchText�����õı������SearchData���б���
	 * @param request
	 * @return ���б�����Ϣ��Map��
	 */
	public static Map getCaptions(HttpServletRequest request) {
		String captionString = request.getParameter("captions");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			captionString = (String) userData.get("captions");
		}
		if (captionString != null) {
			captionString = StringUtils.replace(captionString, ",", "`"); //captionString.replaceAll(",","`");		
			Map captions = HashMapManager.parseHidden(captionString);
			return captions;
		}
		return null;
	}
	
	/**
	 * ��ȡ��SearchData���������SearchText�����õĻ����ı��Ĺ�ʽ
	 * @param request
	 * @return
	 */
	public static String getTextFormula(HttpServletRequest request) {
		String textFormula = request.getParameter("textFormula");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null) {
			textFormula = (String) userData.get("textFormula");
		}
		return textFormula;
	}
	
	/**
	 * ��ȡSearchData�����Դ�󶨵Ĵ����ֶ�
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public String getCodeField() {
		return codeField;
	}
	
	/**
	 * ��ȡSearchData�����Դ�󶨵�key�ֶ�
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public String getKeyField() {
		return keyField;
	}
	
	/**
	 * ��ȡSearchData�����Դ�󶨵�����ֶ�
	 * @return
	 * 
	 */
	public String getNameField() {
		return nameField;
	}
	
	/**
	 * ����SearchData�����Դ�󶨵Ĵ����ֶ�
	 * @param string
	 * 
	 */
	public void setCodeField(String string) {
		this.setAttribute("codeField", string);
		codeField = string;
	}
	
	/**
	 * ����SearchData�����Դ�󶨵�key�ֶ�
	 * @param string
	 */
	public void setKeyField(String string) {
		this.setAttribute("keyField", string);
		keyField = string;
	}
	
	/**
	 * ����SearchData�����Դ�󶨵�����ֶ�
	 * @param string
	 * 
	 */
	public void setNameField(String string) {
		this.setAttribute("nameField", string);
		nameField = string;
	}
	
	/**
	 * ��ȡSearchData�ı���
	 * @return
	 */
	public String getListTitle() {
		return listTitle;
	}
	
	/**
	 * ����SearchData�ı���
	 * @param string
	 */
	public void setListTitle(String string) {
		this.setAttribute("listTitle", string);
		listTitle = string;
	}
	
	/**
	 * ����SearchTextĿ�������SearchData�İ��ֶ�
	 * @param keyField �����ֶΣ�����ʾ
	 * @param dataFields Ҫ��ʾ���ֶ���������Ƽ�ʹ�� setDataField(String
	 * keyField,List dataFields)
	 * 
	 */
	public void setDataField(String keyField, String[] dataFields) {
		List ds = new ArrayList();
		for (int i = 0; i < dataFields.length; i++) {
			ds.add(dataFields[i]);
		}
		setDataField(keyField, ds);
	}
	
	/**
	 * ����SearchTextĿ�������SearchData�İ��ֶ�
	 * @param keyField �����ֶΣ�����ʾ
	 * @param dataFields Ҫ��ʾ���ֶ��������
	 */
	public void setDataField(String keyField, List dataFields) {
		setKeyField(keyField);
		String str = "";
		for (int i = 0; i < dataFields.size(); i++) {
			if (i < dataFields.size() - 1)
				str += (String) dataFields.get(i) + ",";
			else
				str += (String) dataFields.get(i);
		}
		if (debug)
			System.out.println("************dataFields: " + str);
		this.setAttribute("dataFields", str);
	}
	
	/**
	 * ����SearchTextĿ�������SearchData���б���
	 * @param headMap �����ֶ���dataField���ͱ��⣨caption����Map
	 */
	public void setCaption(Map headMap) {
		Object[] keyArray = headMap.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			this.captionMap.put((String) keyArray[i], (String) headMap.get(keyArray[i]));
		}
	}
	
	/**
	 * ��ȡ���SearchText���ı��Ĺ�ʽ
	 * @return
	 */
	public String getTextFormula() {
		return textFormula;
	}
	
	/**
	 * ���û��SearchText���ı��Ĺ�ʽ ��ʽΪ��[�ֶ���1][�ֶ���2] �� [index1][index2]
	 * @param string
	 */
	public void setTextFormula(String string) {
		this.setAttribute("textFormula", string);
		textFormula = string;
	}
	
	/**
	 * ��ȡSearchText���ȼ�
	 * @return
	 */
	public String getHotKey() {
		return hotKey;
	}
	
	/**
	 * �����ȼ����ȼ�ʱ�Զ���������
	 * @param string
	 */
	public void setHotKey(String string) {
		this.setAttribute("hotKey", string);
		this.textBox.addEvent("onKeyDown", "SearchText_handleKeyDown('" + this.componentID
											+ "',event);");
		hotKey = string;
	}
	
	private static int getUnitValue(String stringUnit) {
		String value = "";
		String unit = "";
		int intValue = -1;
		int pos = -1;
		for (int i = 0; i < stringUnit.length(); i++) {
			pos = i;
			if (stringUnit.charAt(i) < '0' || stringUnit.charAt(i) > '9')
				break;
		}
		value = stringUnit.substring(0, pos + 1);
		unit = stringUnit.substring(pos, stringUnit.length());
		try {
			intValue = Integer.valueOf(value).intValue();
		} catch (Exception e) {
			intValue = 100;
		}
		return intValue;
	}
	
	private static String getUnitUnit(String stringUnit) {
		String value = "";
		String unit = "";
		int intValue = -1;
		int pos = -1;
		for (int i = 0; i < stringUnit.length(); i++) {
			pos = i;
			if (stringUnit.charAt(i) < '0' || stringUnit.charAt(i) > '9')
				break;
		}
		value = stringUnit.substring(0, pos);
		unit = stringUnit.substring(pos + 1, stringUnit.length());
		return unit;
	}
	
}