package com.born.insurance.webui.control;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TableRow extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int rowIndex = 0;

	protected Table parentTable = null;

	public TableRow(String id) {
		super(id);
		this.setElementType("tr");
	}

	/**
	 * �õ�������
	 * 
	 * @return
	 */
	public int getRowIndex() {
		return this.rowIndex;
	}

	public String getComponentId() {
		return super.getComponentId() + String.valueOf(rowIndex);
	}

	/**
	 * �õ���Ԫ�����
	 * 
	 * @param index
	 *            ��Ԫ������
	 * @return
	 */
	public TableCell getTableCell(int index) {
		return (TableCell) this.components.get(index);
	}

	public void addComponent(IComponent control) {
	}

	public void addTableCell(TableCell cell) {
		cell.parentRow = this;
		this.components.add(cell);
	}

	public void addTableCell(int index, TableCell cell) {
		cell.parentRow = this;
		this.components.add(index, cell);
	}

	public TableCell addTableCell() {
		TableCell cell = getNewTableCell(this.components.size());
		this.components.add(cell);
		TableColumn column = this.parentTable.getColumn(this.components.size() - 1);
		if (null != column)
		{
			for (int i = 0; i < column.components.size(); i++)
			{
				AbstractComponent component = (AbstractComponent) column.components.get(i);
				IComponent o = null;
				try
				{
					o = (IComponent) component.clone();
				} catch (Exception e)
				{

				}
				cell.components.add(o);
			}
			Map columnCustom = new HashMap();
			columnCustom.putAll(column.customAttributes);
			columnCustom.putAll(this.customAttributes);
			cell.customAttributes.clear();
			cell.customAttributes.putAll(columnCustom);
			columnCustom.clear();
			columnCustom.putAll(column.sourceAttributes);
			columnCustom.putAll(cell.sourceAttributes);
			cell.sourceAttributes.clear();
			cell.sourceAttributes = columnCustom;
			cell.setEnabled(column.enabled);
			if (column.cssClass != null && column.cssClass.length() == 0)
			{
				cell.cssClass = column.cssClass;
			}
			cell.width = column.width;
			cell.visible = column.visible;
			cell.horizontalAlign = column.horizontalAlign;
			cell.wrap = !this.parentTable.isNoWrap();
			cell.verticalAlign = column.verticalAlign;
			cell.style.style.putAll(column.style.style);
		}
		return cell;
	}

	/*
	 * protected TableCell insertTableCell(int index) { TableCell cell=getNewTableCell(index); this.controls.add(index,cell); return cell; }
	 */
	private TableCell getNewTableCell(int index) {
		TableCell cell = new TableCell(this.getComponentId() + "_");
		cell.setOutID(this.isOutID);
		cell.cellIndex = index;
		cell.parentRow = this;
		return cell;
	}

	/**
	 * �õ���Ԫ������
	 * 
	 * @return
	 */
	public int cellSize() {
		return this.components.size();
	}

	protected String getElementHtml() {
		if (this.isVisible())
		{
			StringBuffer html = new StringBuffer(255);
			html.append("<" + this.getElementType() + super.getElementHtml() + ">");
			html.append(this.getText());
			for (int i = 0; i < this.cellSize(); i++)
			{
				html.append(this.getTableCell(i).getInnerHtml());
			}
			html.append(this.assembleEndTag());
			return html.toString();
		} else
		{
			return "";
		}
	}

	/**
	 * �õ�table����
	 * 
	 * @return
	 */
	public Table getParentTable() {
		return this.parentTable;
	}
}
