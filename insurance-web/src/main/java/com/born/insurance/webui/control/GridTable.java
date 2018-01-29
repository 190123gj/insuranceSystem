package com.born.insurance.webui.control;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: GridTable
 * </p>
 * <p>
 * Description: GridTable
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

public class GridTable extends Table {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridTable(String id) {
		super(id);
	}

	public GridTable(String id, HttpServletRequest requst, int columnSize) {
		this(id);
		for (int i = 0; i < columnSize; i++)
		{
			TableColumn newColumn = this.getNewColumn();
			this.tableColumns.add(newColumn);
		}
	}

	protected TableColumn getNewColumn() {
		return new GridColumn(this.getComponentId() + "_Column");
	}

	public GridColumn getGridColumn(int index) {
		return (GridColumn) this.tableColumns.get(index);
	}

	protected TableRow getNewRow(int index) {
		TableRow row = new TableRow(this.getComponentId() + "_Row_");
		row.setOutID(this.isOutID);
		row.parentTable = this;
		row.rowIndex = index;
		return row;
	}

	public GridColumn getGridColumn(String dataField) {
		for (int i = 0; i < this.columnSize(); i++)
		{
			if (((GridColumn) this.tableColumns.get(i)).getDataField().equals(dataField))
			{
				return (GridColumn) this.tableColumns.get(i);
			}
		}
		return null;
	}

}