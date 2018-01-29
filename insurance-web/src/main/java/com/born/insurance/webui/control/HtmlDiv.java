package com.born.insurance.webui.control;

/**
 * <p>
 * Title: the htmldiv control
 * </p>
 * <p>
 * Description: the htmldiv control
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

public class HtmlDiv extends AbstractComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Description: create a htmldiv control
	 * </p>
	 * 
	 * @param id:
	 *            the control id
	 */
	public HtmlDiv(String id) {
		super(id);
		this.name = null;
	}

	/**
	 * <p>
	 * Description: get the html code of the htmldiv control
	 * </p>
	 * 
	 * @return the html code of the htmldiv control
	 */
	protected String getElementHtml() {
		StringBuffer innerHTML = new StringBuffer();
		innerHTML.append("<div ");
		innerHTML.append(super.getElementHtml() + " >");
		if (this.getText() != null && !("").equals(this.getText()))
		{
			innerHTML.append(this.getText());
		}
		for (int index = 0; index < this.components.size(); index++)
		{
			innerHTML.append(((AbstractComponent) this.components.get(index)).getInnerHtml());
		}
		innerHTML.append("</div>");
		return innerHTML.toString();
	}
}
