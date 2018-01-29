package com.born.insurance.ws.base;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.Assert;

import com.born.insurance.ws.order.base.QueryOrderInterface;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.Order;

public class QueryPageBase implements Order, QueryOrderInterface {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 3152587109070129487L;
	
	long pageSize = 10;
	long pageNumber = 1;
	
	long limitStart = 0;
	
	String sortCol;
	
	String sortOrder;
	
	public long getLimitStart() {
		return (pageNumber - 1) * pageSize;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
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
	
	@Override
	public void check() {
		Assert.isTrue(pageNumber > 0, "无效分页参数");
		Assert.isTrue(pageNumber < 100000, "无效分页参数");
		Assert.isTrue(pageSize >= 0, "无效分页参数");
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	@Override
	public String getGid() {
		return null;
	}
	
	@Override
	public void setGid(String gid) {
	}
	
	public String getSortCol() {
		return sortCol;
	}
	
	public void setSortCol(String sortCol) {
		if (sortCol != null && sortCol.contains(";")) {
			throw new IllegalArgumentException("非法排序列");
		}
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		if (StringUtil.isNotBlank(sortCol) && StringUtil.isBlank(sortOrder)) {
			sortOrder = "DESC";
		}
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		if (sortOrder != null && !"".equals(sortOrder) && !"asc".equalsIgnoreCase(sortOrder)
			&& !"desc".equalsIgnoreCase(sortOrder)) {
			throw new IllegalArgumentException("无效排序");
		}
		this.sortOrder = sortOrder;
	}
	
	protected boolean isNull(String str) {
		return (null == str || "".equals(str));
	}
	
	protected boolean isNull(Integer i) {
		return (null == i);
	}
	
	protected boolean isNull(Long l) {
		return (null == l);
	}
	
	protected boolean isNull(Date date) {
		return (null == date);
	}
	
	protected boolean isNull(Double d) {
		return (null == d);
	}
	
	protected boolean isNull(Money m) {
		return (null == m || m.equals(Money.zero()));
	}
}
