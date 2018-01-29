package com.born.insurance.ws.order.base;

public class FcsOrderBase extends ProcessOrder {
	private static final long serialVersionUID = 1154709032630062052L;
	
	/**
	 * 表单tab验证结果
	 */
	public Integer checkStatus;
	
	/**
	 * 表单tab名称
	 */
	public String checkName;
	
	/**
	 * 结果描述
	 */
	public String checkDesc;
	
	public Integer getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getCheckName() {
		return this.checkName;
	}
	
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	public String getCheckDesc() {
		return this.checkDesc;
	}
	
	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FcsOrderBase [checkStatus=");
		builder.append(checkStatus);
		builder.append(", checkName=");
		builder.append(checkName);
		builder.append(", checkDesc=");
		builder.append(checkDesc);
		builder.append("]");
		return builder.toString();
	}
	
}
