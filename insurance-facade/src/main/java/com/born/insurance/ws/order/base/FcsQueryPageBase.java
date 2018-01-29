package com.born.insurance.ws.order.base;

import org.springframework.util.Assert;

import com.yjf.common.service.Order;

public class FcsQueryPageBase implements Order, QueryOrderInterface {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 3152587109070129487L;
	
	long pageSize = 10;
	long pageNumber = 1;
	
	long limitStart = 0;
	
	String sortCol;
	
	String sortOrder = "ASC";
	
	public long getLimitStart() {
		return (pageNumber - 1) * pageSize;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	@Override
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	public long getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	@Override
	public void check() {
		Assert.isTrue(pageNumber > 0, "无效分页参数");
		Assert.isTrue(pageNumber < 100000, "无效分页参数");
		Assert.isTrue(pageSize > 0, "无效分页参数");
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryBaseOrder [pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", sortCol=");
		builder.append(sortCol);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public String getGid() {
		return null;
	}
	
	@Override
	public void setGid(String gid) {
	}
	
	@Override
	public String getSortCol() {
		return sortCol;
	}
	
	@Override
	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
