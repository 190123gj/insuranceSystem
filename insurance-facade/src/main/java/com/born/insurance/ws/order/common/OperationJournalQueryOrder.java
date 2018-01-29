/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.order.common;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.FcsQueryPageBase;

/**
 * 
 * @Filename OperationJournalAddOrder.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author jiajie
 * 
 * @Email hjiajie@yiji.com
 * 
 * @History <li>Author: jiajie</li> <li>Date: 2013-1-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class OperationJournalQueryOrder extends FcsQueryPageBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	
	/** 主模块名，父模块名 */
	private String baseModuleName;
	
	/** 子模块名 */
	private String permissionName;
	
	/** 操作内容(如'注册','修改','审核','下载'等) */
	private String operationContent;
	
	/** 操作员ID */
	private long operatorId;
	
	/** 操作员真实姓名 */
	private String operatorName;
	
	/** 客户端IP */
	private String operatorIp;
	
	/** 操作信息(详细操作内容) */
	private String memo;
	
	/** 执行操作时间开始 */
	private Date operatorTimeStart;
	
	/** 执行操作时间结束 */
	private Date operatorTimeEnd;
	
	/** 当前页 */
	private int currPage = 1;
	
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	
	public String getBaseModuleName() {
		return baseModuleName;
	}
	
	public void setBaseModuleName(String baseModuleName) {
		this.baseModuleName = baseModuleName;
	}
	
	public String getPermissionName() {
		return permissionName;
	}
	
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public long getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getOperatorIp() {
		return operatorIp;
	}
	
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Date getOperatorTimeStart() {
		return operatorTimeStart;
	}
	
	public void setOperatorTimeStart(Date operatorTimeStart) {
		this.operatorTimeStart = operatorTimeStart;
	}
	
	public Date getOperatorTimeEnd() {
		return operatorTimeEnd;
	}
	
	public void setOperatorTimeEnd(Date operatorTimeEnd) {
		this.operatorTimeEnd = operatorTimeEnd;
	}
	
	
	public int getCurrPage() {
		return this.currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	@Override
	public void check() {
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
}
