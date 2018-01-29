package com.born.insurance.ws.order.common;

import java.util.List;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.ExeStatusEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormRelatedUserTypeEnum;

/**
 * 表单相关人员查询Order
 *
 * @author wuzj
 * 
 * 2016年8月6日
 */
public class FormRelatedUserQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 1025030113292157261L;
	
	private long relatedId; //主键
	
	private long formId; //表单ID
	
	private long taskId; //任务ID
	
	private String projectCode; //项目编号
	
	private FormRelatedUserTypeEnum userType; //人员类型
	
	private ExeStatusEnum exeStatus; //任务执行状态
	
	private List<String> exeStatusList; //任务执行状态
	
	private List<Long> excludeList; //不包含的数据
	
	private FormCodeEnum formCode; //表单类型
	
	/**
	 * 当前人员
	 */
	private long userId;
	
	private String userAccount;
	
	/**
	 * 部门ID
	 */
	private List<Long> deptIdList;
	
	/** 部门编号 */
	private String deptCode;
	
	/** 部门路径 */
	private String deptPath;
	
	/** 部门名称 */
	private String deptName;
	
	public long getRelatedId() {
		return this.relatedId;
	}
	
	public void setRelatedId(long relatedId) {
		this.relatedId = relatedId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public FormRelatedUserTypeEnum getUserType() {
		return this.userType;
	}
	
	public void setUserType(FormRelatedUserTypeEnum userType) {
		this.userType = userType;
	}
	
	public ExeStatusEnum getExeStatus() {
		return this.exeStatus;
	}
	
	public void setExeStatus(ExeStatusEnum exeStatus) {
		this.exeStatus = exeStatus;
	}
	
	public List<String> getExeStatusList() {
		return this.exeStatusList;
	}
	
	public void setExeStatusList(List<String> exeStatusList) {
		this.exeStatusList = exeStatusList;
	}
	
	public List<Long> getExcludeList() {
		return this.excludeList;
	}
	
	public void setExcludeList(List<Long> excludeList) {
		this.excludeList = excludeList;
	}
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
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
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
