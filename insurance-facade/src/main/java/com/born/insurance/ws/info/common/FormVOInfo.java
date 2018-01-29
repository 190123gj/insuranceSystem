package com.born.insurance.ws.info.common;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;

/**
 * 对应SimpleFormDO.java
 * 
 * @author lirz
 *
 * 2016-5-19 下午2:25:17
 */
public class FormVOInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1760480809563618793L;
	
	private long formId;
	
	private FormCodeEnum formCode;
	
	private String formName;
	
	private String formUrl;
	
	private FormStatusEnum formStatus;
	
	private String detailStatus;
	
	private String actDefId;
	
	private long actInstId; //流程实例ID
	
	private long taskId;
	
	private long runId;
	
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
	
	private String relatedProjectCode;
	
	private String taskUserData;
	
	List<FormExecuteInfo> formExecuteInfo;
	
	private Date submitTime;
	
	private Date finishTime;
	
	private Date formAddTime;
	
	private Date formUpdateTime;
	
	private String remark;
	
	private String trace;
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public FormCodeEnum getFormCode() {
		return formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public String getFormName() {
		return formName;
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public String getFormUrl() {
		return formUrl;
	}
	
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
	public FormStatusEnum getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getDetailStatus() {
		return detailStatus;
	}
	
	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}
	
	public String getActDefId() {
		return actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public long getActInstId() {
		return actInstId;
	}
	
	public void setActInstId(long actInstId) {
		this.actInstId = actInstId;
	}
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public long getRunId() {
		return runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getDefId() {
		return defId;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public long getFormUserId() {
		return formUserId;
	}
	
	public void setFormUserId(long formUserId) {
		this.formUserId = formUserId;
	}
	
	public String getFormUserAccount() {
		return formUserAccount;
	}
	
	public void setFormUserAccount(String formUserAccount) {
		this.formUserAccount = formUserAccount;
	}
	
	public String getFormUserName() {
		return formUserName;
	}
	
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
	
	public long getFormDeptId() {
		return formDeptId;
	}
	
	public void setFormDeptId(long formDeptId) {
		this.formDeptId = formDeptId;
	}
	
	public String getFormDeptName() {
		return formDeptName;
	}
	
	public void setFormDeptName(String formDeptName) {
		this.formDeptName = formDeptName;
	}
	
	public String getFormDeptCode() {
		return formDeptCode;
	}
	
	public void setFormDeptCode(String formDeptCode) {
		this.formDeptCode = formDeptCode;
	}
	
	public String getFormDeptPath() {
		return formDeptPath;
	}
	
	public void setFormDeptPath(String formDeptPath) {
		this.formDeptPath = formDeptPath;
	}
	
	public String getFormDeptPathName() {
		return formDeptPathName;
	}
	
	public void setFormDeptPathName(String formDeptPathName) {
		this.formDeptPathName = formDeptPathName;
	}
	
	public String getCheckStatus() {
		return checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getRelatedProjectCode() {
		return relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
	public Date getSubmitTime() {
		return submitTime;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public Date getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public Date getFormAddTime() {
		return formAddTime;
	}
	
	public void setFormAddTime(Date formAddTime) {
		this.formAddTime = formAddTime;
	}
	
	public Date getFormUpdateTime() {
		return formUpdateTime;
	}
	
	public void setFormUpdateTime(Date formUpdateTime) {
		this.formUpdateTime = formUpdateTime;
	}
	
	public String getTaskUserData() {
		return this.taskUserData;
	}
	
	public void setTaskUserData(String taskUserData) {
		this.taskUserData = taskUserData;
	}
	
	public List<FormExecuteInfo> getFormExecuteInfo() {
		return this.formExecuteInfo;
	}
	
	public void setFormExecuteInfo(List<FormExecuteInfo> formExecuteInfo) {
		this.formExecuteInfo = formExecuteInfo;
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
	
}
