package com.born.insurance.ws.order.common;

import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.order.base.ProcessOrder;

public class FormOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -2844042241590219925L;
	
	private Long formId;
	
	private FormCodeEnum formCode;
	
	private String formName;
	
	private String formUrl;
	
	private Long actInstId;
	
	private Long runId;
	
	private Long taskId;
	
	private Long defId;
	
	private String actDefId;
	
	private FormStatusEnum status;
	
	private String detailStatus;
	
	private Long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String checkStatus;
	
	private String checkName;
	
	private String relatedProjectCode;
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
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
	
	public Long getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(Long actInstId) {
		this.actInstId = actInstId;
	}
	
	public Long getRunId() {
		return this.runId;
	}
	
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	
	public Long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public Long getDefId() {
		return this.defId;
	}
	
	public void setDefId(Long defId) {
		this.defId = defId;
	}
	
	public String getActDefId() {
		return this.actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public FormStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(FormStatusEnum status) {
		this.status = status;
	}
	
	public String getDetailStatus() {
		return this.detailStatus;
	}
	
	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}
	
	public Long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
}
