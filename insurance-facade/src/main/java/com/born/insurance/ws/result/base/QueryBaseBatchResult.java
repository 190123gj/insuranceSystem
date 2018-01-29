package com.born.insurance.ws.result.base;

import java.util.List;

import com.born.insurance.ws.base.PageComponent;
import com.google.common.collect.Lists;

public class QueryBaseBatchResult<T> extends InsuranceBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 420002915574977408L;
	long totalCount = 0;
	long pageSize = 10;
	long pageNumber = 1;
	long pageCount = 1;
	String sortCol;
	String sortOrder;
	List<T> pageList = Lists.newArrayList();
	
	public void initPageParam(PageComponent component) {
		this.setTotalCount(component.getRowCount());
		this.setPageSize(component.getPageSize());
		this.setPageNumber(component.getCurPage());
		this.pageCount = component.getPageCount();
		this.sortCol = component.getSortCol();
		this.sortOrder = component.getSortOrder();
	}
	
	public List<T> getPageList() {
		return pageList;
	}
	
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public long getPageCount() {
		return this.pageCount;
	}
	
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryBaseBatchResult [totalCount=");
		builder.append(totalCount);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", sortCol=");
		builder.append(sortCol);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append(", pageList=");
		builder.append(pageList);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
