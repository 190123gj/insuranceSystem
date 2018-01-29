package com.born.insurance.webui.control;

/**
 * <p>
 * Title: the reset button control
 * </p>
 * <p>
 * Description: the reset button control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * 
 * @author: �ᴺ��
 * @version 1.0
 */

public class Reset extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Description: create a reset button control
	 * </p>
	 * 
	 * @param id:
	 *            the control id
	 */
	public Reset(String id) {
		super(id);
		this.name = id;
	}

	/**
	 * <p>
	 * Description: create a reset button control
	 * </p>
	 * 
	 * @param id
	 * @param Text
	 */
	public Reset(String id, String Text) {
		super(id);
		this.name = id;
		this.text = Text;
	}

	/**
	 * get the html code of the reset button
	 * 
	 * @return the html code of the reset button
	 */
	protected String getElementHtml() {
		if (("").equals(this.getText()))
		{
			return "<input type=\"reset\" " + Reset.pairAttribute("value", this.getText()) + super.getElementHtml() + " >";
		} else
		{
			return "<input type=\"reset\" " + super.getElementHtml() + " >";
		}
	}

}
