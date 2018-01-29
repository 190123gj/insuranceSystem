package com.born.insurance.dal.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class OperatorInfoDO implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long identity;
	
	private String baseModuleName;
	
	private String permissionName;
	
	private String operationContent;
	
	private String memo;
	
	private long operatorId;
	
	private String operatorName;
	
	private String operatorIp;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getIdentity() {
		return identity;
	}
	
	public void setIdentity(long identity) {
		this.identity = identity;
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
	
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	/**
	 * @return
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
