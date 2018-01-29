package com.born.insurance.ws.order.base;

public interface QueryOrderInterface {
	public long getPageSize();
	
	public long getPageNumber();
	
	public String getSortCol();
	
	public String getSortOrder();
	
}
