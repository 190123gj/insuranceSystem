package com.born.insurance.webui.table;

import com.born.insurance.ws.enums.BooleanEnum;
import org.apache.poi.hssf.usermodel.HSSFCell;



public class TableCellBean {
	int row;
	int cell;
	BooleanEnum isMeger = BooleanEnum.NO;
	
	public TableCellBean(HSSFCell cell) {
		row = cell.getRowIndex();
		this.cell = cell.getColumnIndex();
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCell() {
		return this.cell;
	}
	
	public void setCell(int cell) {
		this.cell = cell;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TableCellBean) {
			return ((TableCellBean) obj).cell == this.cell && ((TableCellBean) obj).row == this.row;
		} else {
			return false;
		}
	}
	
	public BooleanEnum getIsMeger() {
		return this.isMeger;
	}
	
	public void setIsMeger(BooleanEnum isMeger) {
		this.isMeger = isMeger;
	}
	
	@Override
	public int hashCode() {
		return new Integer(cell).hashCode() * 100000 + new Integer(row).hashCode();
	}
	
}
