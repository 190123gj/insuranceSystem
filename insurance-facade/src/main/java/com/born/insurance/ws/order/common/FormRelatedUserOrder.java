package com.born.insurance.ws.order.common;

import java.util.Date;

import com.born.insurance.ws.enums.ExeStatusEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormRelatedUserTypeEnum;
import com.born.insurance.ws.order.base.ValidateOrderBase;

/**
 * 表单相关人员Info
 *
 * @author wuzj
 * 
 * 2016年8月6日
 */
public class FormRelatedUserOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -7743582002171051695L;
	
	private long relatedId; //主键
	
	private long formId; //表单ID
	
	private long taskId; //任务ID
	
	private String taskName; //任务名称
	
	private String taskOpinion; //任务意见 
	
	private String projectCode; //项目编号
	
	private FormRelatedUserTypeEnum userType; //人员类型
	
	private ExeStatusEnum exeStatus; //任务执行状态
	
	private FormCodeEnum formCode; //表单类型
	
	/**
	 * 当前人员
	 */
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private String userMobile;
	
	private String userEmail;
	
	//相关部门
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	private String remark;
	
	//人员替换时是否删除原来的人员
	private boolean delOrinigal;
	
	/** 任务开始时间 */
	private Date taskStartTime;
	
	/** 任务结束时间 */
	private Date taskEndTime;
	
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
	
	public boolean isDelOrinigal() {
		return this.delOrinigal;
	}
	
	public void setDelOrinigal(boolean delOrinigal) {
		this.delOrinigal = delOrinigal;
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
	
}
