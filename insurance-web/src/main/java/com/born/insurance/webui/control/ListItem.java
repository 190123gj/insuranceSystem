package com.born.insurance.webui.control;

import java.io.Serializable;

/**
 * 
 * <p>
 * Title: the item of the dropdownlist
 * </p>
 * <p>
 * Description: the item of the dropdownlist
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
public class ListItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String text = null;

	public String value = null;

	public String filterId = null;

	public ListItem() {

	}

	/**
	 * <p>
	 * Description: create a list item object
	 * </p>
	 * 
	 * @param filterType:
	 *            the filter type
	 * @param itemText:
	 *            text of the item
	 * @param itemValue:
	 *            value of the item
	 */
	public ListItem(String filterType, String itemText, String itemValue) {
		this.filterId = filterType;
		this.text = itemText;
		this.value = itemValue;
	}

	/**
	 * <p>
	 * Description: create a list item object
	 * </p>
	 * 
	 * @param itemText:
	 *            text of the item
	 * @param itemValue:
	 *            value of the item
	 */
	public ListItem(String itemText, String itemValue) {
		this.text = itemText;
		this.value = itemValue;
	}

	/**
	 * <p>
	 * Description: set filter of the item
	 * </p>
	 * 
	 * @param filter:
	 *            filter value
	 */
	public void setFilterId(String filter) {
		this.filterId = filter;
	}

	/**
	 * <p>
	 * Description: set text of the item
	 * </p>
	 * 
	 * @param itemText:
	 *            text value;
	 */
	public void setText(String itemText) {
		this.text = itemText;
	}

	/**
	 * <p>
	 * Description: set value of the item
	 * </p>
	 * 
	 * @param itemValue:
	 *            value of the item.
	 */
	public void setValue(String itemValue) {
		this.value = itemValue;
	}

	/**
	 * <p>
	 * Description: get value of the item
	 * </p>
	 * 
	 * @return value of the item
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * <p>
	 * Description: get filter of the item
	 * </p>
	 * 
	 * @return filter of the item
	 */
	public String getFilterId() {
		return this.filterId;
	}

	/**
	 * <p>
	 * Description: get text of the item
	 * </p>
	 * 
	 * @return text of the item
	 */
	public String getText() {
		return this.text;
	}
}