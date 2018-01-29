package com.born.insurance.ws.order.common;

import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.order.base.ProcessOrder;

public class SimpleFormOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 6393467714208099943L;
	
	//表单ID
	private Long formId;
	
	//表单编码
	private FormCodeEnum formCode;
	
	//手机
	protected String userMobile;
	
	//email
	protected String userEmail;
	
	//部门ID
	protected Long deptId;
	
	//部门编号
	protected String deptCode;
	
	//部门名称
	protected String deptName;
	
	//部门路径 deptId.deptId. 
	protected String deptPath;
	
	//部门路径名称 deptPathName/deptPathName/
	protected String deptPathName;
	
	//相关项目编号
	protected String relatedProjectCode;
	
	@Override
	public void check() {
		validateNotNull(formId, "表单ID");
		validateHasZore(formId, "表单ID");
		validateNotNull(userId, "用户ID");
		validateHasZore(userId, "用户ID");
	}
	
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
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
