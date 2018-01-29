package com.born.insurance.webui.control;

/**
 * <p>
 * Title:the hidden control
 * </p>
 * <p>
 * Description: the hidden control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * 
 * @author:�ᴺ��
 * @version 1.0
 */

public class Hidden extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Description: create a hidden control
	 * </p>
	 * 
	 * @param id:the
	 *            control id
	 */
	public Hidden(String id) {
		super(id);
		this.name = id;
	}

	/**
	 * <p>
	 * Description: get the html code of the hidden control
	 * </p>
	 * 
	 * @return the html code of the hidden control
	 */
	protected String getElementHtml() {
		if (!("").equals(this.getText()))
		{
			return "<input type=\"hidden\" " + ListGrid.pairAttribute("value", this.getText()) + super.getElementHtml() + " >";
		} else
		{
			return "<input type=\"hidden\" " + super.getElementHtml() + " >";
		}
	}

}
