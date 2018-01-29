package com.born.insurance.util;

import java.util.Arrays;

import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 表的行数据
 * 
 * 
 * @author lirz
 * 
 * 2016-4-6 上午9:23:42
 */
public class DataRow {
	private String rowClass = ""; //该行的样式
	private String columnName; //列名(第一列各个名字)
	private String columnCode;
	private String[] datas; // 从第1列开始的列数据(列名从第0列算起)
	
	//	private String data1; //第1列数据(列名从第0列算起)
	//	private String data2; //第2列数据
	//	private String data3; //第3列数据
	//	private String data4; //第4列数据
	//	private String average; //平均值 根据datas计算得出
	
	public String getRowClass() {
		return this.rowClass;
	}
	
	public void setRowClass(String rowClass) {
		this.rowClass = rowClass;
	}
	
	public String getColumnName() {
		return this.columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnCode() {
		return this.columnCode;
	}
	
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	
	public String[] getDatas() {
		return this.datas;
	}
	
	public void setDatas(String[] datas) {
		this.datas = datas;
	}
	
	public String getAverage() {
		if (null != this.datas && this.datas.length > 0) {
			int k = 0;
			for (String s : this.datas) {
				if (StringUtil.isNotBlank(s)) {
					k++;
				}
			}
			return DataFinancialHelper.divide(DataFinancialHelper.add(this.datas), "" + k);
		} else {
			return "";
		}
	}
	
	//	public void setAverage(String average) {
	//		this.average = average;
	//	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataRow [rowClass=");
		builder.append(rowClass);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append(", columnCode=");
		builder.append(columnCode);
		builder.append(", datas=");
		builder.append(Arrays.toString(datas));
		builder.append("]");
		return builder.toString();
	}
	
}
