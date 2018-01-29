package com.born.insurance.util;

import java.util.List;

/**
 * 数据表
 * 
 * 
 * @author lirz
 * 
 * 2016-4-6 上午9:21:51
 */
public class DataSheet {
	private String title; //表名：资产负债表
	private String [] years; //列名
	private DataRow header; //行头(第一行)
	private List<DataRow> dataRows; //行数据
	private int row; //总行数
	private int column; //总列数
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getYears() {
		return this.years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}

	public DataRow getHeader() {
		return header;
	}

	public void setHeader(DataRow header) {
		this.header = header;
	}

	public List<DataRow> getDataRows() {
		return this.dataRows;
	}

	public void setDataRows(List<DataRow> dataRows) {
		this.dataRows = dataRows;
	}

	public int getRow() {
		if (null == dataRows || dataRows.size() <= 0) {
			return 0;
		}
		return this.dataRows.size();
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		if (null == dataRows || dataRows.size() <= 0) {
			return 0;
		}
		String ss [] = dataRows.get(0).getDatas();
		if (null != ss) {
			return ss.length+1;
		}
		return 0;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
}
