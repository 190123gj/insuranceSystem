package com.born.insurance.webui.control;

/**
 * <p>
 * Title: The Option Control
 * </p>
 * <p>
 * Description:The Option Control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * 
 * @author: QingJian
 * @version 1.0
 */

public class Option extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String value = null;

	protected boolean Selected;

	/**
	 * Create a Option control
	 */
	public Option() {

	}

	/**
	 * Create a Option control
	 * 
	 * @param Text
	 */
	public Option(String Text) {
		this.setText(Text);
	}

	/**
	 * Create a Option control
	 * 
	 * @param Text
	 * @param Value
	 */
	public Option(String Text, String Value) {
		this.setText(Text);
		this.setValue(Value);
	}

	/**
	 * Create a Option control
	 * 
	 * @param Text
	 * @param Value
	 * @param Selected
	 */
	public Option(String Text, String Value, boolean Selected) {
		this.setText(Text);
		this.setValue(Value);
		this.setSelected(Selected);
	}

	/**
	 * Set the Option's value
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Get the Option's value
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Set it's selected
	 * 
	 * @param Selected
	 */
	protected void setSelected(boolean Selected) {
		this.Selected = Selected;
	}

	protected boolean getSelected() {
		return this.Selected;
	}

	/**
	 * Get the control html
	 * 
	 * @return html
	 */
	protected String getElementHtml() {
		String htmlOption;
		htmlOption = "<option ";		
		this.sourceAttributes.put("value", this.getValue());
		if (this.Selected == true)
		{
			this.sourceAttributes.put("Selected", String.valueOf(this.getSelected()));
		} else
		{
			this.sourceAttributes.remove("Selected");
		}
		htmlOption = htmlOption + " " + super.getElementHtml() + ">" + this.getText() + "</option>";
		return htmlOption;
	}
}
