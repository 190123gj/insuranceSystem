package com.born.insurance.ws.info.common;

import java.util.List;

import com.born.insurance.ws.order.common.SimpleUserInfo;

/**
 * 表单当前执行信息
 *
 * @author wuzj
 */
public class FormExecuteInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 4456962752866079838L;
	
	/**
	 * 当前执行人ID
	 */
	private long userId;
	
	/**
	 * 当前执行人名称
	 */
	private String userName;
	
	/**
	 * 当前执行人账号
	 */
	private String userAccount;
	
	/**
	 * 当前执行人email
	 */
	private String email;
	
	/**
	 * 当前执行人手机
	 */
	private String mobile;
	
	/**
	 * 当前任务ID
	 */
	private String taskId;
	
	/**
	 * 当前任务名称
	 */
	private String taskName;
	
	/**
	 * 当前任务URL
	 */
	private String taskUrl;
	
	/**
	 * 当前任务跳转
	 */
	private String redirectTaskUrl;
	
	/**
	 * 是否已执行
	 */
	private boolean isExecute = false;
	
	/**
	 * 候选人列表
	 */
	private List<SimpleUserInfo> candidateUserList;
	
	/**
	 * 是否设置代理
	 */
	private boolean isSetAgent;
	
	/**
	 * 审批意见
	 */
	private String opinion;
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskUrl() {
		return this.taskUrl;
	}
	
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
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
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getRedirectTaskUrl() {
		return this.redirectTaskUrl;
	}
	
	public void setRedirectTaskUrl(String redirectTaskUrl) {
		this.redirectTaskUrl = redirectTaskUrl;
	}
	
	public boolean isExecute() {
		return this.isExecute;
	}
	
	public void setExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}
	
	public List<SimpleUserInfo> getCandidateUserList() {
		return this.candidateUserList;
	}
	
	public void setCandidateUserList(List<SimpleUserInfo> candidateUserList) {
		this.candidateUserList = candidateUserList;
	}
	
	public boolean isSetAgent() {
		return this.isSetAgent;
	}
	
	public void setSetAgent(boolean isSetAgent) {
		this.isSetAgent = isSetAgent;
	}
	
	public String getOpinion() {
		return this.opinion;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
}
