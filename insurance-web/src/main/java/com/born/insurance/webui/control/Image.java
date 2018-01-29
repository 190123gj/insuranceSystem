package com.born.insurance.webui.control;

/**
 * <p>
 * Title: The Image Control
 * </p>
 * <p>
 * Description:The Image Control
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

public class Image extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String Src = "";

	/**
	 * <p>
	 * Description: Create a Image Control
	 * </p>
	 * 
	 * @param id:
	 *            the Control ID
	 * @param request:
	 *            the http servlet request
	 * 
	 * 
	 * public Image(String id,HttpServletRequest requst) { super(id,requst); }
	 */
	public Image(String id) {
		super(id);
	}

	/**
	 * <p>
	 * Description:Set the image control source file path
	 * 
	 * @param Src
	 */
	public void setSrc(String Src) {
		this.Src = Src;
	}

	/**
	 * <p>
	 * Description:get the image control source file path
	 * 
	 * @return Src
	 */
	public String getSrc() {
		return this.Src;
	}

	/**
	 * <p>
	 * Description: get the html code of the control
	 * </p>
	 * 
	 * @return the html code of the control
	 */

	protected String getElementHtml() {
		String Html = "<img ";
		if (!this.getText().equals(""))
		{
			this.sourceAttributes.put("value", this.getText());
		}
		if (!this.getSrc().equals(""))
		{
			this.sourceAttributes.put("src", this.getSrc());
		}
		Html = Html + super.getElementHtml() + " >";
		return Html;
	}
}
