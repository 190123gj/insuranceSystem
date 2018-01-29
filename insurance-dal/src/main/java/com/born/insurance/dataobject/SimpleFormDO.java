/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.dataobject;

// auto generated imports
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 表单项目信息
 *
 * @author wuzj
 */
public class SimpleFormDO implements Serializable {
	
	private static final long serialVersionUID = -2807760887382553654L;
	
	/*
	 * 表单信息
	 */
	private long formId;
	
	private String formCode;
	
	private String formName;
	
	private String formUrl;
	
	private String formStatus;
	
	private String detailStatus; //流程步骤状态
	
	private long actInstId; //流程实例ID
	
	private String actDefId;
	
	private long runId;
	
	private long taskId;
	
	private long defId;
	
	private long formUserId;
	
	private String formUserAccount;
	
	private String formUserName;
	
	private long formDeptId;
	
	private String formDeptName;
	
	private String formDeptCode;
	
	private String formDeptPath;
	
	private String formDeptPathName;
	
	private String checkStatus;
	
	private String checkName;
	
	private Date formAddTime;
	
	private Date formUpdateTime;
	
	private String relatedProjectCode;
	
	private String taskUserData;
	
	private Date submitTime;
	
	private Date finishTime;
	
	private String remark;
	
	private String trace;
	
	private long draftUserId;
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	
	public String getFormName() {
		return this.formName;
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public String getFormUrl() {
		return this.formUrl;
	}
	
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getDetailStatus() {
		return this.detailStatus;
	}
	
	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}
	
	public long getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(long actInstId) {
		this.actInstId = actInstId;
	}
	
	public long getRunId() {
		return this.runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public long getFormUserId() {
		return this.formUserId;
	}
	
	public void setFormUserId(long formUserId) {
		this.formUserId = formUserId;
	}
	
	public String getFormUserAccount() {
		return this.formUserAccount;
	}
	
	public void setFormUserAccount(String formUserAccount) {
		this.formUserAccount = formUserAccount;
	}
	
	public String getFormUserName() {
		return this.formUserName;
	}
	
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
	
	public long getFormDeptId() {
		return this.formDeptId;
	}
	
	public void setFormDeptId(long formDeptId) {
		this.formDeptId = formDeptId;
	}
	
	public String getFormDeptName() {
		return this.formDeptName;
	}
	
	public void setFormDeptName(String formDeptName) {
		this.formDeptName = formDeptName;
	}
	
	public String getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getCheckName() {
		return this.checkName;
	}
	
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	public Date getFormAddTime() {
		return this.formAddTime;
	}
	
	public void setFormAddTime(Date formAddTime) {
		this.formAddTime = formAddTime;
	}
	
	public Date getFormUpdateTime() {
		return this.formUpdateTime;
	}
	
	public void setFormUpdateTime(Date formUpdateTime) {
		this.formUpdateTime = formUpdateTime;
	}
	
	public Date getSubmitTime() {
		return this.submitTime;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public Date getFinishTime() {
		return this.finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getFormDeptCode() {
		return this.formDeptCode;
	}
	
	public void setFormDeptCode(String formDeptCode) {
		this.formDeptCode = formDeptCode;
	}
	
	public String getFormDeptPath() {
		return this.formDeptPath;
	}
	
	public void setFormDeptPath(String formDeptPath) {
		this.formDeptPath = formDeptPath;
	}
	
	public String getFormDeptPathName() {
		return this.formDeptPathName;
	}
	
	public void setFormDeptPathName(String formDeptPathName) {
		this.formDeptPathName = formDeptPathName;
	}
	
	public String getActDefId() {
		return this.actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public long getDefId() {
		return this.defId;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
	public String getTaskUserData() {
		return this.taskUserData;
	}
	
	public void setTaskUserData(String taskUserData) {
		this.taskUserData = taskUserData;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTrace() {
		return this.trace;
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}
	
	public long getDraftUserId() {
		return this.draftUserId;
	}
	
	public void setDraftUserId(long draftUserId) {
		this.draftUserId = draftUserId;
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
