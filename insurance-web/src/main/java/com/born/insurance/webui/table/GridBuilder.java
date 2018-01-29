package com.born.insurance.webui.table;

import com.born.insurance.webui.control.ListGrid;
import com.born.insurance.webui.control.Table;
import com.born.insurance.webui.control.TableCell;
import com.born.insurance.webui.control.TableRow;
import com.yjf.common.lang.util.StringUtil;

public class GridBuilder {
	
	Table pageFooter = null;
	ListGrid listControl = null;
	public int defaultColumnWidth = 150;
	public boolean table_layout_fixed = true;
	final static int sortOrder = 0;
	
	public GridBuilder() {
		
	}
	
	PaginationObject page = null;
	
	public static void MergHeadTable(Table dt, int startIndex, int cellNum, int begin) {
		for (int i = startIndex; i < cellNum; i++)
			mergHeadRowTableOne(dt, i, begin);
	}
	
	public static void mergHeadRowTableOne(Table dt, int cellNum, int begin) {
		int i = begin, rowSpanNum = 1;
		while (i < dt.rowSize() - 1) {
			TableRow gvr = dt.getRow(i);
			for (++i; i < dt.rowSize(); i++) {
				TableRow gvrNext = dt.getRow(i);
				
				if (gvr.getTableCell(cellNum).getText()
					.equals(gvrNext.getTableCell(cellNum).getText())) {
					gvrNext.getTableCell(cellNum).setVisible(false);
					rowSpanNum++;
				} else {
					gvr.getTableCell(cellNum).setRowSpan(rowSpanNum);
					rowSpanNum = 1;
					break;
				}
				if (i == dt.rowSize() - 1) {
					gvr.getTableCell(cellNum).setRowSpan(rowSpanNum);
				}
			}
		}
	}
	
	public static void MergTable(Table dt, int startIndex, int cellNum, int begin) {
		for (int i = startIndex; i < cellNum; i++)
			mergRowTableOne(dt, i, begin);
	}
	
	public static void MergColTable(Table dt, int rowNum, int begin) {
		for (int i = 0; i < rowNum; i++)
			mergColTableOne(dt, i, begin);
	}
	
	public static void mergColTableOne(Table dt, int rowNum, int begin) {
		int i = begin, colSpanNum = 1;
		int width = 0;
		while (i < dt.columnSize() - 1) {
			TableCell gvr = dt.getCell(rowNum, i);
			for (++i; i < dt.columnSize(); i++) {
				TableCell nextCell = dt.getCell(rowNum, i);
				if (gvr.getText().equals(nextCell.getText())) {
					if (rowNum - 1 > 0) {
						if (!dt.getCell(rowNum - 1, i).isVisible()) {
							nextCell.setVisible(false);
							String strWidth = nextCell.getWidth();
							if (StringUtil.isNotEmpty(strWidth)) {
								//width+=UtilFunction.parseWidth(strWidth)+dt.getCellSpacing();
								width += ReportUtil.parseWidth(strWidth);
							}
							colSpanNum++;
						} else {
							if (colSpanNum > 1) {
								gvr.setColumnSpan(colSpanNum);
								if (width > 0) {
									width += ReportUtil.parseWidth(gvr.getWidth());
									gvr.setWidth(String.valueOf(width));
								}
							}
							colSpanNum = 1;
							width = 0;
							break;
						}
						
					} else {
						nextCell.setVisible(false);
						String strWidth = nextCell.getWidth();
						if (StringUtil.isNotEmpty(strWidth)) {
							//width+=UtilFunction.parseWidth(strWidth)+dt.getCellSpacing();
							width += ReportUtil.parseWidth(strWidth);
						}
						colSpanNum++;
						
					}
					
				} else {
					if (colSpanNum > 1) {
						gvr.setColumnSpan(colSpanNum);
						if (width > 0) {
							width += ReportUtil.parseWidth(gvr.getWidth());
							gvr.setWidth(String.valueOf(width));
						}
					}
					colSpanNum = 1;
					width = 0;
					break;
				}
				if (i == dt.columnSize() - 1) {
					if (colSpanNum > 1) {
						gvr.setColumnSpan(colSpanNum);
						if (width > 0) {
							width += ReportUtil.parseWidth(gvr.getWidth());
							gvr.setWidth(String.valueOf(width));
						}
					}
					
				}
			}
		}
	}
	
	public static void MergTable(Table dt, int cellNum, int begin) {
		MergTable(dt, 0, cellNum, begin);
	}
	
	public static void mergRowTableOne(Table dt, int cellNum, int begin) {
		int i = begin, rowSpanNum = 1;
		while (i < dt.rowSize() - 1) {
			TableRow gvr = dt.getRow(i);
			for (++i; i < dt.rowSize(); i++) {
				TableRow gvrNext = dt.getRow(i);
				
				if (gvr.getTableCell(cellNum).getText()
					.equals(gvrNext.getTableCell(cellNum).getText())) {
					if (cellNum > 0) {
						if (!gvrNext.getTableCell(cellNum - 1).isVisible()) {
							gvrNext.getTableCell(cellNum).setVisible(false);
							rowSpanNum++;
						} else {
							gvr.getTableCell(cellNum).setRowSpan(rowSpanNum);
							rowSpanNum = 1;
							break;
						}
					} else {
						gvrNext.getTableCell(cellNum).setVisible(false);
						rowSpanNum++;
					}
					
				} else {
					gvr.getTableCell(cellNum).setRowSpan(rowSpanNum);
					rowSpanNum = 1;
					break;
				}
				if (i == dt.rowSize() - 1) {
					gvr.getTableCell(cellNum).setRowSpan(rowSpanNum);
				}
			}
		}
	}
}
