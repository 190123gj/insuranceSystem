/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.page;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @Filename PageParam.java
 *
 * @Description 分页参数
 *
 * @Version 1.0
 *
 * @Author tanyongfu
 *
 * @Email tyongfu@yiji.com
 * 
 * @History 
 * <li>Author: tanyongfu</li> <li>Date: 2012-6-2</li> <li>Version: 1.0</li> <li>Content: create</li> 
 * <li>Author: wuzj</li> <li>Date: 2016-3-1</li> <li> Version: 1.1</li> <li>Content: 添加排序列 排序顺序</li>
 */
public class PageParam {
	//第几页
	int pageNo = 1;
	//页面大小
	int pageSize = 10;
	//排序列
	private String sortCol;
	//排序顺序  asc/desc
	private String sortOrder;
	
	public PageParam() {
	}
	
	public PageParam(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public PageParam(int pageNo, int pageSize, String sortCol, String sortOrder) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortCol = sortCol;
		this.sortOrder = sortOrder;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
