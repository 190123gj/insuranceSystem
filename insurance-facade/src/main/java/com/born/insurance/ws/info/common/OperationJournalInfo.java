/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.info.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Filename OperationJournalInfo.java
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
public class OperationJournalInfo implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 自增主键
	 */
	private long identity;
	
	/** 主模块，父模块名 */
	private String baseModuleName;
	
	/** 子模块名 */
	private String permissionName;
	
	/** 操作员ID */
	private long operatorId;
	
	/** 操作内容(如'注册','修改','审核','下载'等) */
	private String operationContent;
	
	/** 操作员真实姓名 */
	private String operatorName;
	
	/** 客户端IP */
	private String operatorIp;
	
	/** 操作信息(详细操作内容) */
	private String memo;
	
	/** 操作时间 */
	private Date operatorTime;
	
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	
	public long getIdentity() {
		return identity;
	}
	
	public void setIdentity(long identity) {
		this.identity = identity;
	}
	
	public String getBaseModuleName() {
		return this.baseModuleName;
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
	
	public Date getOperatorTime() {
		return operatorTime;
	}
	
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperationJournalInfo [identity=");
		builder.append(identity);
		builder.append(", baseModuleName=");
		builder.append(baseModuleName);
		builder.append(", permissionName=");
		builder.append(permissionName);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append(", operationContent=");
		builder.append(operationContent);
		builder.append(", operatorName=");
		builder.append(operatorName);
		builder.append(", operatorIp=");
		builder.append(operatorIp);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", operatorTime=");
		builder.append(operatorTime);
		builder.append("]");
		return builder.toString();
	}
	
}
