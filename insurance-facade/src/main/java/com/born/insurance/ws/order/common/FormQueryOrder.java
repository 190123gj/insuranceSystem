package com.born.insurance.ws.order.common;

import java.util.List;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;

public class FormQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 4174870445539568116L;
	
	//表单ID
	private Long formId;
	//表单编码
	private FormCodeEnum formCode;
	//流程实例
	private Long actInstId;
	
	private Long runId;
	
	private Long taskId;
	
	private Long defId;
	
	private String actDefId;
	//表单状态
	private FormStatusEnum status;
	//状态列表
	private List<FormStatusEnum> statusList;
	//提交人
	private Long userId;
	private String userName;
	
	private String userAccount;
	
	private Long deptId;
	
	private String deptCode;
	
	private String deptPath;
	
	private String deptPathName;
	
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
	
	public List<FormStatusEnum> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<FormStatusEnum> statusList) {
		this.statusList = statusList;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
}
