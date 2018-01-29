package com.born.insurance.webui.control;

/**
 * <p>
 * Title: The CheckBox Control
 * </p>
 * <p>
 * Description:The CheckBox Control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author: QingJian
 * 
 * @version 1.0
 */

public class OfbizCheckBox extends CheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String TextChecked = "";
	protected String TextUnChecked = "";
	protected String ValueChecked="";
	protected String ValueUnChecked="";
	protected String isNeedValue="false";
	/**
	 * Create a checkbox only by id
	 * 
	 * @param id
	 */
	public OfbizCheckBox(String id) {
		super(id);
	}
	public void setTextChecked(String TextChecked) {
		this.TextChecked = TextChecked;
	}
	public String getTextChecked() {
		return this.TextChecked;
	}
	public void setTextUnChecked(String TextUnChecked) {
		this.TextUnChecked = TextUnChecked;
	}
	public String getTextUnChecked() {
		return this.TextUnChecked;
	}

	/**
	 * ���� �ͻ��˷�������֮���ٵ��ø���ķ�����ȡhtml
	 * 
	 * @return html
	 */
	protected String getElementHtml() {
		
		this.sourceAttributes.put("setValue", "OfbizCheckBox_setChecked");
		this.sourceAttributes.put("getValue", "OfbizCheckBox_getChecked");
		if(!this.getTextChecked().equals("")||!this.getTextUnChecked().equals("")){
			this.sourceAttributes.put("TextChecked", this.getTextChecked());
			this.sourceAttributes.put("TextUnChecked", this.getTextUnChecked());
			
		}
		if(!this.getValueChecked().equals("")||!this.getValueUnChecked().equals("")){
			this.sourceAttributes.put("ValueChecked",this.getValueChecked());
			this.sourceAttributes.put("ValueUnChecked",this.getValueUnChecked());
		}
		
		if(("true").equals(this.isNeedValue))
		{
			this.sourceAttributes.put("setText", "OfbizCheckBox_setText");
			this.sourceAttributes.put("getText", "OfbizCheckBox_getText");
		}
		this.sourceAttributes.put("isNeedValue",isNeedValue);
		return super.getElementHtml();
	}

	/**
	 * gacheckbox's script
	 * 
	 * @return script
	 */
	protected String getScriptHtml() {

		if(!this.isMakeScript)
		{
			return "";
		}
		String html = super.getScriptHtml() + OfbizCheckBoxJS.getOfbizCheckBoxJS();
		return html;

	}

	/**
	 * @return Returns the valueChecked.
	 */
	public String getValueChecked() {
		return ValueChecked;
	}
	public void setNeedValue(boolean isNeedValue) {
		if(isNeedValue)
		{
			this.isNeedValue="true";
		}			
		else
		{
			this.isNeedValue="false";
		}
	}
	public boolean getNeedValue() {
		return "true".equals(this.isNeedValue);
	}
	/**
	 * @param valueChecked The valueChecked to set.
	 */
	public void setValueChecked(String valueChecked) {
		ValueChecked = valueChecked;
	}
	/**
	 * @return Returns the valueUnChecked.
	 */
	public String getValueUnChecked() {
		return ValueUnChecked;
	}
	/**
	 * @param valueUnChecked The valueUnChecked to set.
	 */
	public void setValueUnChecked(String valueUnChecked) {
		ValueUnChecked = valueUnChecked;
	}
}
