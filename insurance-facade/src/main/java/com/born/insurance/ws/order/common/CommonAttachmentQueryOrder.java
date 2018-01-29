/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.order.common;

import java.util.List;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;

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
 * <li>Content: create</li>
 * @History <li>Author: wuzj</li> <li>Date: 2016-10-21</li> <li>Version: 1.1</li>
 */
public class CommonAttachmentQueryOrder extends QueryPageBase {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 7654210862751673L;
	
	private String bizNo;
	
	private String childId;
	
	CommonAttachmentTypeEnum moduleType;
	
	private List<CommonAttachmentTypeEnum> moduleTypeList = null;
	
	private String fileName;
	
	private long isort;
	
	private long uploaderId;
	
	private String projectCode;
	
	private CheckStatusEnum checkStatus;
	
	private String uploaderName;
	
	private String remark;
	
	public String getBizNo() {
		return bizNo;
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
	
	public List<CommonAttachmentTypeEnum> getModuleTypeList() {
		return moduleTypeList;
	}
	
	public void setModuleTypeList(List<CommonAttachmentTypeEnum> moduleTypeList) {
		this.moduleTypeList = moduleTypeList;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getIsort() {
		return isort;
	}
	
	public void setIsort(long isort) {
		this.isort = isort;
	}
	
	public CommonAttachmentTypeEnum getModuleType() {
		return this.moduleType;
	}
	
	public void setModuleType(CommonAttachmentTypeEnum moduleType) {
		this.moduleType = moduleType;
	}
	
	public long getUploaderId() {
		return this.uploaderId;
	}
	
	public void setUploaderId(long uploaderId) {
		this.uploaderId = uploaderId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public CheckStatusEnum getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(CheckStatusEnum checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getUploaderName() {
		return this.uploaderName;
	}
	
	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public void check() {
	}
	
}
