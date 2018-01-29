package com.born.insurance.webui.control;

/**
 * <p>
 * Title:TableColumn
 * </p>
 * <p>
 * Description: Table Column
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author qch
 * @version 1.0
 */

public class TableColumn extends AbstractComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String horizontalAlign = "";

	protected String verticalAlign = "";

	protected boolean wrap = true;

	public TableColumn(String id) {
		super(id);
	}

	public String getComponentId() {
		return "";
	}

	/**
	 * �õ�ˮƽ����ʽ
	 * 
	 * @return String
	 */
	public String getHorizontalAlign() {
		return this.horizontalAlign;
	}

	/**
	 * ����ˮƽ����ʽ
	 * 
	 * @param horizontalAlign
	 */
	public void setHorizontalAlign(String horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
		this.sourceAttributes.put("align", horizontalAlign);
	}

	/**
	 * �õ���ֱ����ʽ
	 * 
	 * @param verticalAlign
	 */
	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
		this.sourceAttributes.put("valign", verticalAlign);
	}

	/**
	 * ���ô�ֱ����ʽ
	 * 
	 * @return String
	 */
	public String getVerticalAlign() {
		return this.verticalAlign;
	}

	/**
	 * ���õ�Ԫ���������Ƿ���
	 * 
	 * @param wrap
	 */
	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	/*
	 * �õ���Ԫ���������Ƿ��� @return boolean
	 */
	public boolean getWrap() {
		return this.wrap == wrap;
	}
}