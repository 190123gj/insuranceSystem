package com.born.insurance.webui.control;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * <p>
 * Title: the class to store all list items
 * </p>
 * <p>
 * Description: the class to store all list items
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
public class ListItemCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList items = new ArrayList();

	private ArrayList filterIdList = new ArrayList();

	public ListItemCollection() {
	}

	/**
	 * <p>
	 * Description: add a list item to collection
	 * </p>
	 * 
	 * @param item:list
	 *            item
	 */
	public void addItem(ListItem item) {
		if (item.filterId != null && !filterIdList.contains(item.filterId))
		{
			filterIdList.add(item.filterId);
		}
		items.add(item);
	}

	/**
	 * <p>
	 * Description: remove a list item
	 * </p>
	 * 
	 * @param item
	 *            list item
	 */
	public void removeItem(ListItem item) {
		items.remove(item);
		ArrayList filterList = getByFilterId(item.filterId);
		if (filterList == null || filterIdList.size() == 0)
		{
			filterIdList.remove(item.filterId);
		}
	}

	/**
	 * <p>
	 * Description: remove a list item according as index
	 * </p>
	 * 
	 * @param index:
	 *            index of the list item
	 */
	public void removeItem(int index) {
		String filterId = ((ListItem) items.get(index)).filterId;
		items.remove(index);
		ArrayList filterList = getByFilterId(filterId);
		if (filterList == null || filterIdList.size() == 0)
		{
			filterIdList.remove(filterId);
		}

	}

	/**
	 * <p>
	 * Description: remove all list items
	 * </p>
	 */
	public void removeAll() {
		filterIdList.clear();
		items.clear();
	}

	/**
	 * <p>
	 * Description: insert a list item to collection
	 * </p>
	 * 
	 * @param index:
	 *            index of the item
	 * @param item:
	 *            the list item
	 */
	public void insertItem(int index, ListItem item) {
		if (!items.contains(item))
		{
			if (item.filterId != null && filterIdList.contains(item.filterId))
			{
				filterIdList.add(item.filterId);
			}
			items.add(index, item);
		}
	}

	/**
	 * get collection by the filter id
	 * 
	 * @param filterId:
	 *            the filter id
	 * @return: the list items' collection
	 */
	public ArrayList getByFilterId(String filterId) {
		if (!filterIdList.contains(filterId))
		{
			return null;
		}
		ArrayList filterList = new ArrayList();
		for (int index = 0; index < items.size(); index++)
		{
			if (filterId.equals(((ListItem) items.get(index)).filterId))
			{
				filterList.add(items.get(index));
			}
		}
		return filterList;
	}

	/**
	 * <p>
	 * Description: get all list items
	 * </p>
	 * 
	 * @return: all list items collection
	 */
	public ArrayList getAllItems() {
		return items;
	}

	/**
	 * <p>
	 * Description: get the filter id collection
	 * </p>
	 * 
	 * @return: all filter id collection.
	 */
	public ArrayList getFilterIdList() {
		return this.filterIdList;
	}
}