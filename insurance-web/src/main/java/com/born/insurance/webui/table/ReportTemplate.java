package com.born.insurance.webui.table;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReportTemplate {
	int colHeadCount = 0;
	int colCount;
	int[] colHeadOrderArray;
	int[] colOrderArray;
	String[][] colHeadString;
	List<String> colOrderList = new LinkedList<String>();
	List<String> headOrderList = null;
	boolean isOrderBy = false;
	String reportName;
	Map<String, String> captionProperty = new HashMap<String, String>();
	boolean isMergeRow = false;
	boolean isExsitFooter = false;
	int mergeColCount = -1;
	
	public int getColHeadCount() {
		return this.colHeadCount;
	}
	
	public void setColHeadCount(int colHeadCount) {
		this.colHeadCount = colHeadCount;
	}
	
	public int getColCount() {
		return this.colCount;
	}
	
	public void setColCount(int colCount) {
		this.colCount = colCount;
	}
	
	public int[] getColHeadOrderArray() {
		return this.colHeadOrderArray;
	}
	
	public void setColHeadOrderArray(int[] colHeadOrderArray) {
		this.colHeadOrderArray = colHeadOrderArray;
	}
	
	public int[] getColOrderArray() {
		return this.colOrderArray;
	}
	
	public void setColOrderArray(int[] colOrderArray) {
		this.colOrderArray = colOrderArray;
	}
	
	public List<String> getColOrderList() {
		return this.colOrderList;
	}
	
	public void setColOrderList(List<String> colOrderList) {
		this.colOrderList = colOrderList;
	}
	
	public List<String> getHeadOrderList() {
		return this.headOrderList;
	}
	
	public void setHeadOrderList(List<String> headOrderList) {
		this.headOrderList = headOrderList;
	}
	
	public boolean isOrderBy() {
		return this.isOrderBy;
	}
	
	public void setOrderBy(boolean isOrderBy) {
		this.isOrderBy = isOrderBy;
	}
	
	public String getReportName() {
		return this.reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public Map<String, String> getCaptionProperty() {
		return this.captionProperty;
	}
	
	public void setCaptionProperty(Map<String, String> captionProperty) {
		this.captionProperty = captionProperty;
	}
	
	public void setColNameByIndex(int i, String name) {
		this.getCaptionProperty().put("col" + i + "Name", name);
	}
	
	public String getColNameByIndex(int i) {
		return this.getCaptionProperty().get("col" + i + "Name");
	}
	
	public void setCenterCaptionByIndex(int i, String name) {
		this.getCaptionProperty().put("center" + i + "Caption", name);
	}
	
	public void setCenterValueByIndex(int i, String name) {
		this.getCaptionProperty().put("center" + i + "Value", name);
	}
	
	public void setLeftCaptionByIndex(int i, String name) {
		this.getCaptionProperty().put("left" + i + "Caption", name);
	}
	
	public void setLeftValueByIndex(int i, String name) {
		this.getCaptionProperty().put("left" + i + "Value", name);
	}
	
	public void setRightCaptionByIndex(int i, String name) {
		this.getCaptionProperty().put("right" + i + "Caption", name);
	}
	
	public void setRightValueByIndex(int i, String name) {
		this.getCaptionProperty().put("right" + i + "Value", name);
	}
	
	public boolean isMergeRow() {
		return this.isMergeRow;
	}
	
	public void setMergeRow(boolean isMergeRow) {
		this.isMergeRow = isMergeRow;
	}
	
	public boolean isExsitFooter() {
		return this.isExsitFooter;
	}
	
	public void setExsitFooter(boolean isExsitFooter) {
		this.isExsitFooter = isExsitFooter;
	}
	
	public String[][] getColHeadString() {
		return this.colHeadString;
	}
	
	public void setColHeadString(String[][] colHeadString) {
		this.colHeadString = colHeadString;
	}
	
	public int getMergeColCount() {
		return this.mergeColCount;
	}
	
	public void setMergeColCount(int mergeColCount) {
		this.mergeColCount = mergeColCount;
	}
	
}
