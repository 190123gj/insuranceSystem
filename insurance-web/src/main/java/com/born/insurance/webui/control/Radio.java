package com.born.insurance.webui.control;

/**
 * <p>
 * Title: The Radio Control
 * </p>
 * <p>
 * Description:The Radio Control
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
public class Radio extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean Checked;
	/**
	 * <p>
	 * Description: Create a Radio Control
	 * </p>
	 * 
	 * @param id:
	 *            the Control ID
	 * @param request:
	 *            the http servlet request
	 * 
	 * public Radio(String id,HttpServletRequest requst) { super(id, requst); }
	 */
	public Radio(String id) {
		super(id);
		this.name = id;
	}
	public void setchecked(boolean checked) {
		this.Checked = checked;
	}
	public boolean getChecked() {
		return this.Checked;
	}
	protected String getElementHtml() {
		this.sourceAttributes.put("value", this.getText());
		if (this.Checked) {
			return "<input type=\"radio\" " + " checked"
					+ super.getElementHtml() + " >";
		} else {
			return "<input type=\"radio\" " + super.getElementHtml() + " >";
		}
	}
}
