package com.born.insurance.webui.control;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * <p>
 * Title: the calendar control
 * </p>
 * <p>
 * Description: the calendar control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author: �ᴺ��
 * @version 1.0
 */
public class OFBIZCalendar extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date selectedDate = null;
	private Date defaultDate = new Date();
	private Date currentDate = new Date();
	private boolean readOnly = false;
	private boolean necessary = false;
	private int textBoxWidth = 120;
	private int buttonWidth = 17;
	private int buttonHeight = 17;
	private String textBoxClass = "inputText";
	private String buttonClass = "inputText";
	private String buttonSrc = ComponentUtil.getInstance().getImagesRequestPath() + "/date.gif";
	private String disButtonSrc = ComponentUtil.getInstance().getImagesRequestPath()
									+ "/datedisabled.gif";
	private String textBoxId = null;
	private String buttonId = null;
	private String spanId = null;
	private String popDivId = null;
	private Label calendarLab = null;
	private TextBox calendarText = null;
	private boolean dateIsNull = false;
	private Image calendarImg = null;
	private HtmlDiv popDiv = null;
	private boolean isIEBrowser = true;
	private int popHeight = 180;
	private int popWidth = 173;
	private boolean isViewTime = false;
	
	public boolean isViewTime() {
		return isViewTime;
	}
	
	public void setViewTime(boolean isViewTime) {
		this.isViewTime = isViewTime;
	}
	
	/**
	 * <p>
	 * Description: create the calendar control
	 * </p>
	 * @param id: component id
	 * @deprecated
	 * ����ʹ��OFBIZCalendar(id,request)����,�����������Ϊֻ��IE��������
	 */
	@Deprecated
	public OFBIZCalendar(String id) {
		super(id);
		this.OnChangeEventName = "ondatechange";
		this.name = id;
		addSubControl(id);
		this.setWidth("150");
	}
	
	public OFBIZCalendar(String id, HttpServletRequest request) {
		super(id);
		if (ComponentUtil.isIEBrowser(request)) {
			this.isIEBrowser = true;
		} else {
			this.isIEBrowser = false;
			this.popHeight = 210;
			this.popWidth = 188;
		}
		this.setWidth("150");
		this.OnChangeEventName = "ondatechange";
		this.name = id;
		addSubControl(id);
	}
	
	private void addSubControl(String id) {
		textBoxId = id + "_textBox";
		spanId = id + "_span";
		buttonId = id + "_img";
		popDivId = id + "_div";
		calendarLab = new Label(spanId);
		calendarText = new TextBox(textBoxId);
		calendarText.setCssClass(this.textBoxClass);
		calendarImg = new Image(buttonId);
		popDiv = new HtmlDiv(popDivId);
		calendarLab.components.add(calendarText);
		calendarLab.components.add(calendarImg);
	}
	
	public void setPopHeight(int pHeight) {
		this.popHeight = pHeight;
	}
	
	public int getPopHeight() {
		return this.popHeight;
	}
	
	public void setPopWidth(int pWidth) {
		this.popWidth = pWidth;
	}
	
	public int getPopWidth() {
		return this.popWidth;
	}
	
	/**
	 * <p>
	 * Description: get selectedDate attribute
	 * </p>
	 * @return: selected date
	 */
	public Date getSelectedDate() {
		return this.selectedDate;
	}
	
	public void setSelectedDate(Date sDate) {
		this.selectedDate = sDate;
	}
	
	public void setSelectedDateNull(boolean isNull) {
		this.dateIsNull = isNull;
	}
	
	public boolean getSelectedDateNull() {
		return this.dateIsNull;
	}
	
	/**
	 * <p>
	 * Description: get defaultDate attribute
	 * </p>
	 * @return: default date
	 */
	public Date getDefaultDate() {
		return this.defaultDate;
	}
	
	public void setDefaultDate(Date dDate) {
		this.defaultDate = dDate;
	}
	
	/**
	 * <p>
	 * Description: get currentDate attribute
	 * </p>
	 * @return: the date of today
	 */
	public Date getCurrentDate() {
		return this.currentDate;
	}
	
	public void setCurrentDate(Date cDate) {
		this.currentDate = cDate;
	}
	
	/**
	 * <p>
	 * Description: set readOnly attribute, if the control is readOnly,then we
	 * can't cahnge date
	 * </p>
	 * @param isReadOnly: the control is readOnly or not
	 */
	public void setReadOnly(boolean isReadOnly) {
		this.readOnly = isReadOnly;
	}
	
	/**
	 * <p>
	 * Description: get the readOnly attribute
	 * </p>
	 * @return: readOnly attribute;
	 */
	public boolean getReadOnly() {
		return this.readOnly;
	}
	
	/**
	 * <p>
	 * Description: set necessary attribute,if the control is necessary, then
	 * the date mustn't null
	 * </p>
	 * @param isNecessary: the control is necessary or not
	 */
	public void setNecessary(boolean isNecessary) {
		this.necessary = isNecessary;
	}
	
	/**
	 * <p>
	 * Description: get necessary attribute
	 * </p>
	 * @return: necessary attribute;
	 */
	public boolean getNecessary() {
		return this.necessary;
	}
	
	/**
	 * <p>
	 * Description: set width of the textbox
	 * </p>
	 * @param width: width of textbox
	 */
	public void setTextBoxWidth(int width) {
		this.textBoxWidth = width;
	}
	
	@Override
	public void setWidth(String width) {
		if (StringUtil.isNotEmpty(width)) {
			this.textBoxWidth = ListGrid.parseWidth(width) - 17;
		}
	}
	
	/**
	 * <p>
	 * Description: get width of the textbox
	 * </p>
	 * @return: width of textbox
	 */
	public int getTextBoxWidth() {
		return this.textBoxWidth;
	}
	
	/**
	 * <p>
	 * Description: set style class of textbox
	 * </p>
	 * @param style: style class of textbox
	 */
	public void setTextBoxClass(String style) {
		this.textBoxClass = style;
		calendarText.setCssClass(this.textBoxClass);
	}
	
	/**
	 * <p>
	 * Description: get style class of textbox
	 * </p>
	 * @return: style class of textbox
	 */
	public String getTextBoxClass() {
		return this.textBoxClass;
	}
	
	/**
	 * <p>
	 * Description: set width of image button
	 * </p>
	 * @param width: width of image button
	 */
	public void setButtonWidth(int width) {
		this.buttonWidth = width;
	}
	
	/**
	 * <p>
	 * Description: get width of image button
	 * </p>
	 * @return: width of image button
	 */
	public int getButtonWidth() {
		return this.buttonWidth;
	}
	
	/**
	 * <p>
	 * Description: set height of image button
	 * </p>
	 * @param height: height of image button
	 */
	public void setButtonHeight(int height) {
		this.buttonHeight = height;
	}
	
	/**
	 * <p>
	 * Description: get height of image button
	 * </p>
	 * @return: height of image button
	 */
	public int getButtonHeight() {
		return this.buttonHeight;
	}
	
	/**
	 * <p>
	 * Description: set style class of image button
	 * </p>
	 * @param style: style class of image button
	 */
	public void setButtonClass(String style) {
		this.buttonClass = style;
	}
	
	/**
	 * <p>
	 * Description: get style class of image button
	 * </p>
	 * @return style class of image button
	 */
	public String getButtonClass() {
		return this.buttonClass;
	}
	
	/**
	 * <p>
	 * Description: set pictrue path of image button
	 * </p>
	 * @param src: pictrue path of image button
	 */
	public void setButtonSrc(String src) {
		this.buttonSrc = src;
	}
	
	/**
	 * <p>
	 * Description: get pictrue path of image button
	 * </p>
	 * @return pictrue path of image button
	 */
	public String getButtonSrc() {
		return this.buttonSrc;
	}
	
	/**
	 * <p>
	 * Description: set pictrue path of image button when button is disabled
	 * </p>
	 * @param src: pictrue path of image button when button is disabled
	 */
	public void setDisButtonSrc(String src) {
		this.disButtonSrc = src;
	}
	
	/**
	 * <p>
	 * Description: get pictrue path of image button when button is disabled
	 * </p>
	 * @return pictrue path of image button when button is disabled
	 */
	public String getDisButtonSrc() {
		return this.disButtonSrc;
	}
	
	/**
	 * <p>
	 * Description: set client event when date has changed
	 * </p>
	 * @param eventStr: the javascript function when the client event is
	 * happened.
	 * 
	 * public void setOnChangeEvent(String functionStr){ IEvent event = new
	 * Event("ondatechange"); event.setFuncName(functionStr);
	 * this.addEvent(event); }
	 */
	/**
	 * public IEvent getOnChangeEvent(){ return this.getEvent("ondatechange"); }
	 */
	/**
	 * public void setOnChangeEvent(String functionStr, String className, String
	 * methodName){ IServerEvent event = new ServerEvent("ondatechange");
	 * event.setFuncName(functionStr); event.setClassName(className);
	 * event.setMethodName(methodName); this.addEvent(event); }
	 */
	/**
	 * public void setOnChangeEvent(String functionStr, String fileName){
	 * IServerEvent event = new ServerEvent("ondatechange");
	 * event.setFuncName(functionStr); event.setBshFile(fileName);
	 * this.addEvent(event); }
	 */
	/**
	 * <p>
	 * Description: get client event when date has changed
	 * </p>
	 * @return the javascript function when the client event is happened.
	 */
	public IEvent getDateChanged() {
		return this.getOnChangeEvent();
	}
	
	/**
	 * <p>
	 * Description: convert string to date
	 * </p>
	 * @param dateStr: the string which get from client.
	 * @return: the date after changed.
	 */
	public static Date getChangedDate(String dateStr) {
		String[] dateStrs = StringUtils.splitArray(dateStr, "-");
		if (dateStrs.length != 3) {
			return null;
		} else {
			int year = Integer.parseInt(dateStrs[0]) - 1900;
			int month = Integer.parseInt(dateStrs[1]);
			int date = Integer.parseInt(dateStrs[2]);
			Date newDate = new Date();
			newDate.setYear(year);
			newDate.setMonth(month - 1);
			newDate.setDate(date);
			return newDate;
		}
	}
	
	/**
	 * <p>
	 * Description: get innerHTML of the control
	 * </p>
	 * @return innerHTML of the control
	 */
	@Override
	protected String getElementHtml() {
		this.setTextBoxAttribute();
		this.setImageAttribute();
		this.setLabelAttribute();
		this.setHtmlDivAttribute();
		StringBuffer innerHtml = new StringBuffer();
		innerHtml.append("<span " + super.getElementHtml() + "><nobr>");
		innerHtml.append(this.calendarLab.getElementHtml() + "</nobr>");
		innerHtml.append(this.popDiv.getElementHtml());
		innerHtml.append("</span>");
		return innerHtml.toString();
	}
	
	@Override
	protected String getScriptHtml() {
		if (!this.isMakeScript) {
			return "";
		}
		if (this.isIEBrowser) {
			return super.getScriptHtml() + CalendarJS.getCalendarJS();
		} else {
			return super.getScriptHtml() + CalendarJsForMozila.getCalendarJS();
		}
	}
	
	/**
	 * <p>
	 * Description: get textbox
	 * </p>
	 * @return textbox
	 */
	public TextBox getTextBox() {
		return this.calendarText;
	}
	
	/**
	 * <p>
	 * Description: get label
	 * </p>
	 * @return label
	 */
	public Label getLabel() {
		return this.calendarLab;
	}
	
	/**
	 * <p>
	 * Description: get image button
	 * </p>
	 * @return image button
	 */
	public Image getImage() {
		return this.calendarImg;
	}
	
	/**
	 * <p>
	 * Description: get pop calendar div
	 * </p>
	 * @return pop calendar div
	 */
	public HtmlDiv getPopDiv() {
		return this.popDiv;
	}
	
	/**
	 * <p>
	 * Description: set attribute of the parent of textbox and image button
	 * </p>
	 */
	private void setLabelAttribute() {
		return;
	}
	
	/**
	 * <p>
	 * Description: set attribute of the textbox
	 * </p>
	 */
	private void setTextBoxAttribute() {
		if (this.necessary) {
			if (this.selectedDate == null) {
				this.selectedDate = new Date();
			}
		}
		String selectedDateStr = this.parseDateToStr(this.selectedDate);
		
		StringBuffer style = new StringBuffer();
		style.append("width:" + String.valueOf(this.textBoxWidth) + ";");
		this.calendarText.setAttribute("style", style.toString());
		if (this.necessary) {
			this.setAttribute("necessary", "true");
		} else {
			this.setAttribute("necessary", "false");
		}
		if (this.readOnly) {
			this.setAttribute("readonly", "true");
			this.calendarText.setAttribute("readonly", "true");
		}
		if (this.necessary) {
			this.setAttribute("defaultDate", this.parseDateToStr(this.defaultDate));
		} else {
			this.setAttribute("defaultDate", " ");
		}
		if (!this.isIEBrowser) {
			this.setAttribute("isSelected", "false");
		}
		this.setAttribute("selectedDate", selectedDateStr);
		this.setAttribute("curDate", this.parseDateToStr(this.currentDate));
		this.setAttribute("popdiv", this.popDivId);
		this.setAttribute("dissrc", this.disButtonSrc);
		this.setAttribute("src", this.buttonSrc);
		this.calendarText.setText(selectedDateStr);
		this.calendarText.setAttribute("title", selectedDateStr);
		this.calendarText.setAttribute("maxlength", "10");
		this.setAttribute("popHeight", String.valueOf(this.popHeight));
		this.setAttribute("popWidth", String.valueOf(this.popWidth));
		this.setAttribute("setValue", "OFBIZCalendar_setValue");
		this.setAttribute("getValue", "OFBIZCalendar_getValue");
		this.setAttribute("setFocus", "OFBIZCalendar_setFocus");
		this.setAttribute("setProperty", "OFBIZCalendar_setProperty");
		if (this.isViewTime) {
			this.setAttribute("isViewTime", "true");
		}
		if (this.isIEBrowser) {
			this.calendarText
				.setAttribute("onkeydown", "OFBIZJudgeKey('" + this.componentID + "')");
			this.calendarText.setAttribute("onkeypress", "return OFBIZJudgeKeyPress()");
		} else {
			this.calendarText.setAttribute("onkeydown", "OFBIZJudgeKey(event)");
			this.calendarText.setAttribute("onclick", "OFBIZCalendar_closePop('" + this.componentID
														+ "')");
			this.calendarText.setAttribute("onkeypress", "return OFBIZJudgeKeyPress(event)");
		}
		this.calendarText.setAttribute("onblur", "OFBIZJudgeBlur('" + this.componentID + "');");
	}
	
	/**
	 * <p>
	 * Description: set attribute of the image button
	 * </p>
	 */
	private void setImageAttribute() {
		StringBuffer style = new StringBuffer();
		style.append("CURSOR: hand;");
		style.append("width:" + String.valueOf(this.buttonWidth) + ";");
		style.append("height:" + String.valueOf(this.buttonHeight) + ";");
		style.append("BORDER-RIGHT: #999999 0px solid;");
		style.append("BORDER-TOP: #999999 0px solid;");
		style.append("BORDER-LEFT: #999999 0px solid;");
		style.append("BORDER-BOTTOM: #999999 0px solid;");
		this.calendarImg.setAttribute("style", style.toString());
		this.calendarImg.setAttribute("class", this.buttonClass);
		if (this.buttonSrc != null && !this.readOnly) {
			this.calendarImg.setAttribute("src", this.buttonSrc);
		}
		if (this.readOnly) {
			this.calendarImg.setAttribute("disabled", "true");
			if (this.disButtonSrc != null) {
				this.calendarImg.setAttribute("src", this.disButtonSrc);
			}
		}
		this.calendarImg.setAttribute("onclick", "OFBIZCalendar_showPop('" + this.componentID
													+ "')");
	}
	
	/**
	 * <p>
	 * Description: set attribute of the pop calendar div
	 * </p>
	 */
	private void setHtmlDivAttribute() {
		this.popDiv.setAttribute("style",
			"position:absolute; width:0px; height:0px; top:0px; left:0px; background-color:white");
		this.popDiv.setAttribute("type", "calendar_pop");
		this.popDiv.setAttribute("popShow", "false");
		if (this.isIEBrowser) {
			this.popDiv.setAttribute("onblur", "Calendar_popDivBlur('" + this.componentID + "')");
		}
	}
	
	/**
	 * <p>
	 * Description: parse date object to string object
	 * </p>
	 * @param curDate: the date will be converted
	 * @return: the string of date
	 */
	private String parseDateToStr(Date curDate) {
		if (curDate == null) {
			return "";
		}
		String dateStr = "";
		if (!this.isViewTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = sdf.format(curDate);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateStr = sdf.format(curDate);
		}
		return dateStr;
	}
	
	/**
	 * @deprecated As of this version
	 */
	@Deprecated
	public static String getResultDate(HttpServletRequest request, String id) {
		String textId = id + "_textBox";
		String textValue = request.getParameter(textId);
		if (StringUtil.isEmpty(textValue)) {
			return textValue;
		}
		return formatDate(textValue);
	}
	
	public static String formatDate(String value) {
		if (value == null) {
			return "";
		}
		String[] temp = StringUtils.split(value, "-");
		if (temp.length != 3) {
			return "";
		} else {
			if (temp[1].length() == 1) {
				temp[1] = "0" + temp[1];
			}
			if (temp[2].length() == 1) {
				temp[2] = "0" + temp[2];
			}
			return temp[0] + "-" + temp[1] + "-" + temp[2];
		}
	}
	
	public static String getText(HttpServletRequest request, String id) {
		return OFBIZCalendar.getResultDate(request, id);
	}
	
	public static String getText(RequestUtils request, String id) {
		return OFBIZCalendar.getResultDate(request, id);
	}
	
	public static String getResultDate(RequestUtils request, String id) {
		String textId = id + "_textBox";
		String textValue = request.getString(textId);
		if (StringUtil.isEmpty(textValue)) {
			return "";
		}
		return formatDate(textValue);
	}
	
	public static boolean isDateNull(RequestUtils request, String id) {
		String textId = id + "_textBox";
		String textValue = request.getString(textId);
		if (textValue == null) {
			return true;
		}
		return false;
	}
	
	public String getElementInnerHtml() {
		return getElementHtml();
	}
	
}