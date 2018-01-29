/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.order.common;

import org.springframework.util.Assert;

import com.yjf.common.service.Order;

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
public class OperationJournalAddOrder implements Order {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	
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
	
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
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
	
	public String getPermissionName() {
		return permissionName;
	}
	
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public String getBaseModuleName() {
		return this.baseModuleName;
	}
	
	public void setBaseModuleName(String baseModuleName) {
		this.baseModuleName = baseModuleName;
	}
	
	@Override
	public void check() {
		Assert.hasText(permissionName, "permissionName不能为空");
		Assert.hasText(operationContent, "操作内容(如'注册','修改','审核','下载'等)operationContent不能为空");
		Assert.notNull(operatorId, "操作员IDoperatorId不能为空");
		Assert.hasText(operatorName, "操作员真实姓名operatorName不能为空");
		Assert.hasText(operatorIp, "客户端IPoperatorIp不能为空");
		Assert.hasText(memo, "操作信息(详细操作内容)memo不能为空");
	}
	
	@Override
	public String getGid() {
		return null;
	}
	
	@Override
	public void setGid(String gid) {
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperationJournalAddOrder [baseModuleName=");
		builder.append(baseModuleName);
		builder.append(", permissionName=");
		builder.append(permissionName);
		builder.append(", operationContent=");
		builder.append(operationContent);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append(", operatorName=");
		builder.append(operatorName);
		builder.append(", operatorIp=");
		builder.append(operatorIp);
		builder.append(", memo=");
		builder.append(memo);
		builder.append("]");
		return builder.toString();
	}
	
}
