package com.born.insurance.ws.order.common;

import com.born.insurance.ws.order.base.ValidateOrderBase;

public class CommonAttachmentDeleteOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 182454811694673022L;
	
	private long attachmentId;
	
	private String bizNo;
	
	private String filePhysicalPath;
	
	public long getAttachmentId() {
		return attachmentId;
	}
	
	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	public String getBizNo() {
		return bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public String getFilePhysicalPath() {
		return filePhysicalPath;
	}
	
	public void setFilePhysicalPath(String filePhysicalPath) {
		this.filePhysicalPath = filePhysicalPath;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateTradeAttachmentDeleteOrder [attachmentId=");
		builder.append(attachmentId);
		builder.append(", bizNo=");
		builder.append(bizNo);
		builder.append(", filePhysicalPath=");
		builder.append(filePhysicalPath);
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 
	 * @see com.yjf.common.service.Order#check()
	 */
	@Override
	public void check() {
		validateHasText(bizNo, "交易流水号");
	}
	
}
