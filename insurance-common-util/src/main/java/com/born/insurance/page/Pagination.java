/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @Filename Page.java
 * 
 * @Description 分页返回对象
 * 
 * @Version 1.0
 * 
 * @Author xuyin
 * 
 * @Email xuyin@yiji.com
 * 
 * @History <li>Author: xuyin</li> <li>Date: 2012-7-11</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class Pagination<T> {
	private static int DEFAULT_PAGE_SIZE = 20;
	
	private int pageSize = DEFAULT_PAGE_SIZE; //// 每页的记录数，<=0表示只有一页。
	
	private long start; // 当前页第一条数据在"数据集"中的位置,从0开始
	
	private List<T> result; // 当前页中存放的记录,类型一般为List
	
	private long totalCount; // 总记录数
	
	@SuppressWarnings("unused")
	private long totalPageCount;
	
	@SuppressWarnings("unused")
	private long currentPageNo;
	
	@SuppressWarnings("unused")
	private long previosPageNo;
	
	@SuppressWarnings("unused")
	private long nexPagetNo;
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setTotalPageCount(long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	public void setCurrentPageNo(long currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
	/**
	 * 构造方法，只构造空页.
	 */
	public Pagination() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}
	
	/**
	 * 默认构造方法.
	 * 
	 * @param start 本页数据在数据库中的起始位置，从0开始。
	 * @param totalSize 数据库中总记录条数，必须>=0。
	 * @param pageSize 本页容量，<=0表示只有一页。
	 * @param data 本页包含的数据，不能为 null 。
	 */
	public Pagination(long start, long totalSize, int pageSize, List<T> result) {
		
		//保存参数。
		this.start = start;
		this.totalCount = totalSize;
		this.pageSize = pageSize;
		this.result = result;
		this.previosPageNo = getPreviosPageNo();
		this.nexPagetNo = getNexPagetNo();
		this.totalPageCount = getTotalPageCount();
	}
	
	/**
	 * 取总记录数.
	 */
	public long getTotalCount() {
		return this.totalCount;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 取总页数。如果totalSize为0或pageSize<=0，返回1。
	 */
	public long getTotalPageCount() {
		if (totalCount == 0 || pageSize <= 0) {
			return 1;
		} else {
			if (totalCount % pageSize == 0)
				return totalCount / pageSize;
			else
				return totalCount / pageSize + 1;
		}
	}
	
	/**
	 * 取当前页中指定记录
	 * 
	 * @param index 以0为起始值
	 * @return
	 */
	public T getEntity(int index) {
		if (result != null && result.size() > index) {
			return result.get(index);
		}
		return null;
	}
	
	/**
	 * 获取结果
	 */
	public List<T> getResult() {
		return result;
	}
	
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	/**
	 * 取该页当前页码，页码从1开始。如果pageSize<=0，返回1。
	 */
	public long getCurrentPageNo() {
		if (pageSize <= 0) {
			return 1;
		} else {
			return start / pageSize + 1;
		}
	}
	
	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}
	
	/**
	 * 是否有前一页
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}
	
	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 * 
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 获取任一页第一条数据在数据集的位置（这个返回的startIndex是从0开始的）.
	 * 
	 * @param pageNo 从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		if (pageSize <= 0) {
			return 0;
		} else {
			return (pageNo - 1) * pageSize;
		}
	}
	
	public long getPreviosPageNo() {
		
		return getCurrentPageNo() - 1 < 1 ? 1 : getCurrentPageNo() - 1;
		
	}
	
	public long getNexPagetNo() {
		return getCurrentPageNo() + 1 > getTotalPageCount() ? getTotalPageCount()
			: getCurrentPageNo() + 1;
	}
	
	public long getStart() {
		return start;
	}
	
	public void setStart(long start) {
		this.start = start;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setPreviosPageNo(long previosPageNo) {
		this.previosPageNo = previosPageNo;
	}
	
	public void setNexPagetNo(long nexPagetNo) {
		this.nexPagetNo = nexPagetNo;
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
