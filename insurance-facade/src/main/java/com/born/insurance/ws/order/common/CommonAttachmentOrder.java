/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.order.common;

import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.Order;

/**
 * 
 * @Filename EstateTradeAttachmentInfo.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-4-10</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public class CommonAttachmentOrder extends ValidateOrderBase implements Order {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 7654210862751673L;
	
	private long attachmentId;
	
	private String bizNo;
	
	private String childId;
	
	private CommonAttachmentTypeEnum moduleType = CommonAttachmentTypeEnum.OTHER;
	
	private String fileName;
	
	private long isort;
	
	private String filePhysicalPath;
	
	private String requestPath;
	
	/***
	 * @see CheckStatusEnum
	 */
	private String checkStatus;
	
	private long uploaderId;
	private String uploaderAccount;
	private String uploaderName;
	
	private String projectCode;
	
	private String remark;
	
	/**
	 * 作为更新条件是否全部为空
	 * @return
	 */
	public boolean isConditionNull() {
		return StringUtil.isBlank(bizNo) && StringUtil.isBlank(childId) && moduleType == null
				&& StringUtil.isBlank(fileName) && StringUtil.isBlank(checkStatus)
				&& StringUtil.isBlank(projectCode) && StringUtil.isBlank(remark);
	}
	
	public long getAttachmentId() {
		return this.attachmentId;
	}
	
	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	public String getBizNo() {
		return this.bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public String getChildId() {
		return this.childId;
	}
	
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public CommonAttachmentTypeEnum getModuleType() {
		return this.moduleType;
	}
	
	public void setModuleType(CommonAttachmentTypeEnum moduleType) {
		this.moduleType = moduleType;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getIsort() {
		return this.isort;
	}
	
	public void setIsort(long isort) {
		this.isort = isort;
	}
	
	public String getFilePhysicalPath() {
		return this.filePhysicalPath;
	}
	
	public void setFilePhysicalPath(String filePhysicalPath) {
		this.filePhysicalPath = filePhysicalPath;
	}
	
	public String getRequestPath() {
		return this.requestPath;
	}
	
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	public String getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public long getUploaderId() {
		return this.uploaderId;
	}
	
	public void setUploaderId(long uploaderId) {
		this.uploaderId = uploaderId;
	}
	
	public String getUploaderAccount() {
		return this.uploaderAccount;
	}
	
	public void setUploaderAccount(String uploaderAccount) {
		this.uploaderAccount = uploaderAccount;
	}
	
	public String getUploaderName() {
		return this.uploaderName;
	}
	
	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 
	 * @see com.yjf.common.service.Order#check()
	 */
	@Override
	public void check() {
		validateHasText(bizNo, "bizNo");
		validateNotNull(moduleType, "模块类型");
		validateHasText(fileName, "文件名称");
		validateHasText(filePhysicalPath, "文件路径");
		validateHasText(requestPath, "访问路径");
	}
}
