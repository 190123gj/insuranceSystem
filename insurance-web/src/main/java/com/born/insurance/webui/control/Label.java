package com.born.insurance.webui.control;

/**
 * <p>
 * Title: the label control
 * </p>
 * <p>
 * Description: the label control
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

public class Label extends AbstractComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Description: create the label control
	 * </p>
	 * 
	 * @param id:
	 *            the control id
	 */
	public Label(String id) {
		super(id);
	}

	/**
	 * <p>
	 * Description: create the label control
	 * </p>
	 * 
	 * @param id
	 * @param Text
	 */
	public Label(String id, String Text) {
		super(id);
		this.setText(Text);
	}

	/**
	 * <p>
	 * Description: get the html code of the label control
	 * </p>
	 * 
	 * @return the html code of the label control
	 */
	protected String getElementHtml() {
		StringBuffer innerHTML = new StringBuffer();
		innerHTML.append("<span ");
		innerHTML.append(super.getElementHtml() + " >");
		if (this.getText() != null && !("").equals(this.getText()))
		{
			innerHTML.append(this.getText());
		}
		for (int index = 0; index < this.components.size(); index++)
		{
			innerHTML.append(((AbstractComponent) this.components.get(index)).getInnerHtml());
		}
		innerHTML.append("</span>");
		return innerHTML.toString();
	}

}
