package com.born.insurance.ws.info.common;

import java.util.Date;

import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;

public class CommonAttachmentInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5872639604372914170L;
	
	private long attachmentId;
	
	private String bizNo;
	
	private String childId;
	
	private CommonAttachmentTypeEnum moduleType = CommonAttachmentTypeEnum.OTHER;
	
	private String fileName;
	
	private long isort;
	
	private String filePhysicalPath;
	
	private String requestPath;
	
	private long uploaderId;
	
	private String uploaderAccount;
	
	private String uploaderName;
	
	private String projectCode;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private CheckStatusEnum checkstatus;
	
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
	
	public String getChildId() {
		return this.childId;
	}
	
	public void setChildId(String childId) {
		this.childId = childId;
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
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public CheckStatusEnum getCheckstatus() {
		return this.checkstatus;
	}
	
	public void setCheckstatus(CheckStatusEnum checkstatus) {
		this.checkstatus = checkstatus;
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
}
