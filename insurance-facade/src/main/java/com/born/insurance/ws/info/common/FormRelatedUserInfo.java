package com.born.insurance.ws.info.common;

import java.util.Date;

import com.born.insurance.ws.enums.ExeStatusEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormRelatedUserTypeEnum;
import com.born.insurance.ws.order.bpm.TaskOpinion;
import com.born.insurance.ws.order.common.SimpleUserInfo;

/**
 * 表单相关人员Info
 *
 * @author wuzj
 * 
 * 2016年8月6日
 */
public class FormRelatedUserInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 6717718582071593982L;
	
	private long relatedId;
	
	/** 表单ID */
	private long formId;
	
	/** 表单类型 */
	private FormCodeEnum formCode;
	
	/** 任务ID */
	private long taskId;
	
	/** 任务名称 */
	private String taskName;
	
	/**
	 * 任务意见
	 * @see TaskOpinion
	 */
	private String taskOpinion;
	
	/** 执行状态 */
	private ExeStatusEnum exeStatus;
	
	/** 相关项目编号 */
	private String projectCode;
	
	/** 用户类型 */
	private FormRelatedUserTypeEnum userType;
	
	/** 用户ID */
	private long userId;
	
	/** 用户账号 */
	private String userAccount;
	
	/** 用户名称 */
	private String userName;
	
	/** 用户手机 */
	private String userMobile;
	
	/** 用户邮箱 */
	private String userEmail;
	
	/** 用户部门 */
	private long deptId;
	
	/** 用户部门 编号 */
	private String deptCode;
	
	/** 用户名称 */
	private String deptName;
	
	/** 用户部门路径 */
	private String deptPath;
	
	/** 用户部门 路径名称 */
	private String deptPathName;
	
	/** 备注 */
	private String remark;
	
	/** 任务开始时间 */
	private Date taskStartTime;
	
	/** 任务结束时间 */
	private Date taskEndTime;
	
	/** 新增时间 */
	private Date rawAddTime;
	
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public SimpleUserInfo toSimpleUserInfo() {
		SimpleUserInfo user = new SimpleUserInfo();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserAccount(userAccount);
		user.setEmail(userEmail);
		user.setDeptId(deptId);
		user.setDeptCode(deptCode);
		user.setDeptName(deptName);
		user.setDeptPath(deptPath);
		user.setDeptPathName(deptPathName);
		return user;
	}
	
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
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskOpinion() {
		return this.taskOpinion;
	}
	
	public void setTaskOpinion(String taskOpinion) {
		this.taskOpinion = taskOpinion;
	}
	
	public ExeStatusEnum getExeStatus() {
		return this.exeStatus;
	}
	
	public void setExeStatus(ExeStatusEnum exeStatus) {
		this.exeStatus = exeStatus;
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
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
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
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getTaskStartTime() {
		return this.taskStartTime;
	}
	
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	
	public Date getTaskEndTime() {
		return this.taskEndTime;
	}
	
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
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
	
}
