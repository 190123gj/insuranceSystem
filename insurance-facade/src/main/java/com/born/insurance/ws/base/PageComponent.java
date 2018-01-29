package com.born.insurance.ws.base;

import com.born.insurance.ws.order.base.QueryOrderInterface;

public class PageComponent {
	public final static int Page_Count = 15;
	long pages;
	long pageSize = Page_Count;
	long curPage;
	long pageCount;
	long firstRecord = 0;
	long lastRecord = Page_Count;
	long rowCount;
	long firstPage;
	String sortCol;
	String sortOrder;
	
	public long getPages() {
		return pages;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public long getCurPage() {
		return curPage;
	}
	
	public long getPageCount() {
		return pageCount;
	}
	
	public long getFirstRecord() {
		return firstRecord;
	}
	
	public long getLastRecord() {
		return lastRecord;
	}
	
	public long getRowCount() {
		return rowCount;
	}
	
	public long getFirstPage() {
		return firstPage;
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
	
	public void setPages(long pages) {
		this.pages = pages;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
	
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	public void setFirstRecord(long firstRecord) {
		this.firstRecord = firstRecord;
	}
	
	public void setLastRecord(long lastRecord) {
		this.lastRecord = lastRecord;
	}
	
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}
	
	public void setFirstPage(long firstPage) {
		this.firstPage = firstPage;
	}
	
	public PageComponent(QueryOrderInterface queryOrder, long rowCount) {
		init(queryOrder.getSortCol(), queryOrder.getSortOrder(), queryOrder.getPageSize(),
			queryOrder.getPageNumber(), rowCount);
	}
	
	public PageComponent(int iPages, int rowCount) {
		init(pageSize, iPages, rowCount);
	}
	
	public PageComponent(int iPageSize, int iPages, int rowCount) {
		init(iPageSize, iPages, rowCount);
	}
	
	void init(long iPageSize, long iPages, long rowCount) {
		init(null, null, iPageSize, iPages, rowCount);
	}
	
	void init(String sortCol, String sortOrder, long iPageSize, long iPages, long rowCount) {
		this.sortCol = sortCol;
		this.sortOrder = sortOrder;
		if (iPageSize == 0) {
			return;
		}
		
		pageSize = iPageSize;
		
		curPage = iPages;
		this.rowCount = rowCount;
		this.pageCount = rowCount % iPageSize == 0 ? rowCount / iPageSize : rowCount / iPageSize
																			+ 1;
		if (this.pageCount == 0) {
			this.pageCount = 1;
		}
		this.curPage = iPages;
		
		if (this.curPage < this.pageCount) {
			this.firstRecord = (this.curPage - 1) * pageSize;
			this.lastRecord = (this.curPage) * pageSize;
		} else {
			this.firstRecord = (this.pageCount - 1) * pageSize;
			this.lastRecord = rowCount;
			this.curPage = this.pageCount;
		}
	}
}
