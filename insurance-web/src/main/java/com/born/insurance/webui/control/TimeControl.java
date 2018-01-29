/*
 * Created on 2005-7-6
 *
 */
package com.born.insurance.webui.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * TimeControl
 * 
 * @author liwei
 */
public class TimeControl extends AbstractComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Table timeTable = null;
	private ListBox hourListBox;
	private TextBox readOnlyHour;
	private TextBox minuteTextBox;
	private boolean readOnly = false;
	private String OnBlurEventName = "onBlur";
	private static List hourList = new ArrayList(24);
	private String hourWidth = null;
	private String minuteWidth = null;

	static {
		for (int i = 0; i < 24; i++)
		{
			String option = null;
			if (i < 10)
			{
				option ="0" +String.valueOf(i);
			} else
			{
				option = String.valueOf(i);
			}
			hourList.add(option);
		}
	}
	/**
	 * <p>Description: create a Time control</p>
	 * @param id: the control id.
	 */
	public TimeControl(String id)
	{
		super(id);
		this.timeTable = new Table("timeTable", 4, 1);
		this.hourListBox = new ListBox(id + "_ListBox");
		this.readOnlyHour = new OfbizTextBox(id + "_ReadOnlyTextBox");
		this.minuteTextBox = new OfbizTextBox(id + "_TextBox");
		minuteTextBox.setMaxLength(2);
		minuteTextBox.setWidth("40");
		hourListBox.setWidth("70");
		readOnlyHour.setWidth("70");
		setOnBlurEvent("validateTime('"+id+"')");
		for (int i = 0; i < 24; i++)
		{
			String option = (String)hourList.get(i);
			hourListBox.addOptions(new Option(option, option));
		}
	}

	public void setText(String timeText)
	{
		String[] timeArray = null;

		if (timeText != null && timeText.length() >0)
		{
			timeArray = StringUtils.split(timeText, ":");
			hourListBox.setSelectedOption(timeArray[0]);
			readOnlyHour.setText(timeArray[0]);
			minuteTextBox.setText(timeArray[1]);
		} else
		{
			hourListBox.setSelectedOption("00");
			readOnlyHour.setText("00");
			minuteTextBox.setText("00");
		}
	}

	public void setReadOnly(boolean isReadOnly)
	{
		if (isReadOnly)
		{
			this.readOnly = isReadOnly;
		} else
		{
			this.readOnly = false;
		}
	}

	public static String getText(HttpServletRequest request, String controlId)
	{
		String time = null;
		String hour = request.getParameter(controlId + "_ListBox");
		String hourReadOnly = request.getParameter(controlId + "_ReadOnlyTextBox");
		String minute = request.getParameter(controlId + "_TextBox");

		if (hourReadOnly == null)
		{
			time = hour + ":" + minute;
		} else
		{
			time = hourReadOnly + ":" + minute;
		}

		return time;
	}

	public static String getText(RequestUtils req, String controlId)
	{
		String time = null;
		String hour = req.getString(controlId + "_ListBox");
		String hourReadOnly = req.getString(controlId + "_ReadOnlyTextBox");
		String minute = req.getString(controlId + "_TextBox");

		if (hourReadOnly == null)
		{
			time = hour + ":" + minute;
		} else
		{
			time = hourReadOnly + ":" + minute;
		}

		return time;
	}

	private void setOnBlurEvent(String funcName) {    	
	    IEvent event=new Event(this.OnBlurEventName);
	    event.setFuncName(funcName);
	    this.minuteTextBox.setEvent(event);
	}

	protected String getElementHtml()
	{
		if(null != hourWidth){
			readOnlyHour.setWidth(hourWidth);
			hourListBox.setWidth(hourWidth);
		}
		if(null != minuteWidth){
			minuteTextBox.setWidth(minuteWidth);
		}
		if (this.readOnly == true)
		{
			this.readOnlyHour.setReadOnly(true);
			this.minuteTextBox.setReadOnly(true);
			timeTable.getCell(0, 0).addComponent(readOnlyHour);
		} else
		{
			this.readOnlyHour.setReadOnly(false);
			this.minuteTextBox.setReadOnly(false);
			timeTable.getCell(0, 0).addComponent(hourListBox);
		}
		timeTable.getCell(0, 1).setText("ʱ");
		timeTable.getCell(0, 2).addComponent(minuteTextBox);
		timeTable.getCell(0, 3).setText("��");

		String result = timeTable.getInnerHtml();
		return result;
	}
	protected String getScriptHtml()
	{
		String scriptReference = TimeControlJS.getTimeControlJS();
		return scriptReference;
	}
	/**
	 * @return
	 */
	public ListBox getHourListBox()
	{
		return hourListBox;
	}

	/**
	 * @return
	 */
	public TextBox getMinuteTextBox()
	{
		return minuteTextBox;
	}

	/**
	 * @return
	 */
	public Table getTimeTable()
	{
		return timeTable;
	}

	public String getHourWidth() {
		return hourWidth;
	}

	public void setHourWidth(String hourWidth) {
		this.hourWidth = hourWidth;
	}

	public String getMinuteWidth() {
		return minuteWidth;
	}

	public void setMinuteWidth(String minuteWidth) {
		this.minuteWidth = minuteWidth;
	}
	
}
