/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.order.common;

import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.order.base.ValidateOrderBase;
import com.yjf.common.service.Order;

/**
 *
 * @author wuzj
 *
 */
public class CommonAttachmentBatchOrder extends ValidateOrderBase implements Order {
	
	private static final long serialVersionUID = 613899559277650510L;
	
	private String bizNo;
	
	private String childId;
	
	private CommonAttachmentTypeEnum moduleType = CommonAttachmentTypeEnum.OTHER;
	
	/***
	 * fileName = path[0]; filePhysicalPath = path[1]; requestPath = path[2];
	 */
	private String path;
	
	/***
	 * @see CheckStatusEnum
	 */
	private String checkStatus;
	
	private long uploaderId;
	private String uploaderAccount;
	private String uploaderName;
	
	private String projectCode;
	
	private String remark;
	
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
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
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
		validateHasText(path, "文件路径");
	}
}
