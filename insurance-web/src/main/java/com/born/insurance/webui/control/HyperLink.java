package com.born.insurance.webui.control;

/**
 * <p>
 * Title: The HyperLink Control
 * </p>
 * <p>
 * Description:The HyperLink Control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * 
 * @author: qch
 * @version 1.0
 */

public class HyperLink extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String Href;

	/**
	 * Create a HyperLink: "<a href=*** onclick=*** >"
	 * 
	 * @param id
	 * @param requst
	 * 
	 * public HyperLink(String id,HttpServletRequest requst) { super(id,requst); }
	 */
	public HyperLink(String id) {
		super(id);
	}

	public HyperLink() {

	}

	/**
	 * Set Href of HyperLink
	 * 
	 * @param Href
	 */
	public void setHref(String Href) {
		this.Href = Href;
	}

	/**
	 * Get Href of HyperLink
	 * 
	 * @return
	 */
	public String getHref() {
		return this.Href;
	}

	/**
	 * getElementHtml
	 * 
	 * @return
	 */
	protected String getElementHtml() {
		this.sourceAttributes.put("href", this.getHref());
		return "<a  " + super.getElementHtml() + " >" + super.getText() + "</a>";
	}

}