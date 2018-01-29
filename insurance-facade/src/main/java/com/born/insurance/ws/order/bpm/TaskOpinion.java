package com.born.insurance.ws.order.bpm;

import java.util.List;

public class TaskOpinion {
	/**
	 * 初始化\尚未审批=-2
	 */
	public final static Short STATUS_INIT = (short) -2;
	/**
	 * 正在审批
	 */
	public final static Short STATUS_CHECKING = (short) -1;
	/**
	 * 弃权=0
	 */
	public final static Short STATUS_ABANDON = (short) 0;
	/**
	 * 同意=1
	 */
	public final static Short STATUS_AGREE = (short) 1;
	/**
	 * 反对=2
	 */
	public final static Short STATUS_REFUSE = (short) 2;
	/**
	 * 
	 * 驳回=3
	 */
	public final static Short STATUS_REJECT = (short) 3;
	/**
	 * 撤销=4
	 */
	public final static Short STATUS_RECOVER = (short) 4;
	
	/**
	 * 会签通过
	 */
	public final static Short STATUS_PASSED = (short) 5;
	
	/**
	 * 会签不通过。
	 */
	public final static Short STATUS_NOT_PASSED = (short) 6;
	
	/**
	 * 知会意见。
	 */
	public final static Short STATUS_NOTIFY = (short) 7;
	
	/**
	 * 更改执行路径
	 */
	public final static Short STATUS_CHANGEPATH = (short) 8;
	
	/**
	 * 终止
	 */
	public final static Short STATUS_ENDPROCESS = (short) 14;
	
	/**
	 * 沟通意见。
	 */
	public final static Short STATUS_COMMUNICATION = (short) 15;
	
	/**
	 * 沟通反馈。
	 */
	public final static Short STATUS_COMMUN_FEEDBACK = (short) 20;
	
	/**
	 * 办结转发
	 */
	public final static Short STATUS_FINISHDIVERT = (short) 16;
	
	/**
	 * 转办。
	 */
	public final static Short STATUS_DELEGATE = (short) 21;
	
	/**
	 * 转办取消。
	 */
	public final static Short STATUS_DELEGATE_CANCEL = (short) 22;
	
	/**
	 * 更改执行人。
	 */
	public final static Short STATUS_CHANGE_ASIGNEE = (short) 23;
	/**
	 * 驳回到发起人
	 */
	public final static Short STATUS_REJECT_TOSTART = (short) 24;
	
	/**
	 * 驳回到某一步骤
	 */
	public final static Short STATUS_REJECT_TO_TASK_NODE = (short) 50;
	/**
	 * 撤销到发起人
	 */
	public final static Short STATUS_RECOVER_TOSTART = (short) 25;
	
	/**
	 * 撤销
	 */
	public final static Short STATUS_REVOKED = (short) 17;
	
	/**
	 * 逻辑删除
	 */
	public final static Short STATUS_DELETE = (short) 18;
	
	/**
	 * 抄送
	 */
	public final static Short STATUS_NOTIFY_COPY = (short) 19;
	
	/**
	 * 代理。
	 */
	public final static Short STATUS_AGENT = (short) 26;
	/**
	 * 代理撤消。
	 */
	public final static Short STATUS_AGENT_CANCEL = (short) 27;
	
	/**
	 * 表单意见。
	 */
	public final static Short STATUS_OPINION = (short) 28;
	/**
	 * 驳回取消
	 */
	public final static Short STATUS_BACK_CANCEL = (short) 29;
	
	/**
	 * 撤销取消
	 */
	public final static Short STATUS_REVOKED_CANCEL = (short) 30;
	/**
	 * 通过取消
	 */
	public final static Short STATUS_PASS_CANCEL = (short) 31;
	/**
	 * 拒绝取消
	 */
	public final static Short STATUS_REFUSE_CANCEL = (short) 32;
	/**
	 * 提交
	 */
	public final static Short STATUS_SUBMIT = (short) 33;
	/**
	 * 重新提交
	 */
	public final static Short STATUS_RESUBMIT = (short) 34;
	
	/**
	 * 干预
	 */
	public final static Short STATUS_INTERVENE = (short) 35;
	
	/**
	 * 重启任务
	 */
	public final static Short STATUS_RESTART_TASK = (short) 36;
	
	/**
	 * 执行过，用于自动节点记录状态。
	 */
	public final static Short STATUS_EXECUTED = (short) 37;
	
	/**
	 * 抄送通知
	 */
	public final static String TASKKEY_NOTIFY = "NotifyTask";
	/**
	 * 办结转发
	 */
	public final static String TASKKEY_DIVERT = "DivertTask";
	/** 表示已读 =1 */
	public final static Short READ = 1;
	/** 表示未读=0 */
	public final static Short NOT_READ = 0;
	/**
	 * 流转
	 */
	public final static Short STATUS_TRANSTO = (short) 38;
	/**
	 * 流转中
	 */
	public final static Short STATUS_TRANSTO_ING = (short) 39;
	/**
	 * 代提交
	 */
	public final static Short STATUS_REPLACE_SUBMIT = (short) 40;
	/**
	 * 正常流转，即无别人干预
	 */
	public final static Short STATUS_COMMON_TRANSTO = (short) 41;
	/**
	 * 流转取消
	 */
	public final static Short STATUS_CANCLE_TRANSTO = (short) 42;
	/**
	 * 添加流转人
	 */
	public final static Short STATUS_ADD_TRANSTO = (short) 43;
	/**
	 * 补签
	 */
	public final static Short STATUS_RETROACTIVE = (short) 44;
	/**
	 * 追回
	 */
	public final static Short STATUS_RETRIEVE = (short) 45;
	
	// opinionId
	protected Long opinionId;
	// actInstId
	protected String actInstId;
	// 任务名称
	protected String taskName;
	// 任务Key
	protected String taskKey;
	// 任务令牌
	protected String taskToken;
	// taskId
	protected Long taskId;
	// 执行开始时间
	protected java.util.Date startTime;
	// 结束时间
	protected java.util.Date endTime;
	// 持续时间
	protected Long durTime;
	// 执行人ID
	protected Long exeUserId;
	// 执行人名
	protected String exeFullname;
	//候选人列表
	protected List<Long> candidateUserList;
	// 审批意见
	protected String opinion;
	// 审批状态
	protected Long checkStatus = (long) STATUS_CHECKING;
	// 表单定义Id
	protected Long formDefId = 0L;
	// 意见表单名称
	protected String fieldName;
	// 流程定义ID
	protected String actDefId;
	// 父流程实例ID
	protected String superExecution;
	
	protected String candidateUser = "";
	
	/**
	 * 抄送表的中状态，0为未读，1为已读
	 * 
	 * protected Short status=0;
	 */
	protected String status;
	
	protected String startTimeStr;
	protected String endTimeStr;
	protected String durTimeStr;
	protected boolean read;
	
	public TaskOpinion() {
		
	}
	
	public Long getOpinionId() {
		return this.opinionId;
	}
	
	public void setOpinionId(Long opinionId) {
		this.opinionId = opinionId;
	}
	
	public String getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskKey() {
		return this.taskKey;
	}
	
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	
	public String getTaskToken() {
		return this.taskToken;
	}
	
	public void setTaskToken(String taskToken) {
		this.taskToken = taskToken;
	}
	
	public Long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	public Long getDurTime() {
		return this.durTime;
	}
	
	public void setDurTime(Long durTime) {
		this.durTime = durTime;
	}
	
	public Long getExeUserId() {
		return this.exeUserId;
	}
	
	public void setExeUserId(Long exeUserId) {
		this.exeUserId = exeUserId;
	}
	
	public String getExeFullname() {
		return this.exeFullname;
	}
	
	public void setExeFullname(String exeFullname) {
		this.exeFullname = exeFullname;
	}
	
	public String getOpinion() {
		return this.opinion;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public Long getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(Long checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public Long getFormDefId() {
		return this.formDefId;
	}
	
	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getActDefId() {
		return this.actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public String getSuperExecution() {
		return this.superExecution;
	}
	
	public void setSuperExecution(String superExecution) {
		this.superExecution = superExecution;
	}
	
	public String getCandidateUser() {
		return this.candidateUser;
	}
	
	public void setCandidateUser(String candidateUser) {
		this.candidateUser = candidateUser;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStartTimeStr() {
		return this.startTimeStr;
	}
	
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public String getEndTimeStr() {
		return this.endTimeStr;
	}
	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	public String getDurTimeStr() {
		return this.durTimeStr;
	}
	
	public void setDurTimeStr(String durTimeStr) {
		this.durTimeStr = durTimeStr;
	}
	
	public boolean isRead() {
		return this.read;
	}
	
	public void setRead(boolean read) {
		this.read = read;
	}
	
	public List<Long> getCandidateUserList() {
		return this.candidateUserList;
	}
	
	public void setCandidateUserList(List<Long> candidateUserList) {
		this.candidateUserList = candidateUserList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskOpinion [opinionId=");
		builder.append(opinionId);
		builder.append(", actInstId=");
		builder.append(actInstId);
		builder.append(", taskName=");
		builder.append(taskName);
		builder.append(", taskKey=");
		builder.append(taskKey);
		builder.append(", taskToken=");
		builder.append(taskToken);
		builder.append(", taskId=");
		builder.append(taskId);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", durTime=");
		builder.append(durTime);
		builder.append(", exeUserId=");
		builder.append(exeUserId);
		builder.append(", exeFullname=");
		builder.append(exeFullname);
		builder.append(", opinion=");
		builder.append(opinion);
		builder.append(", checkStatus=");
		builder.append(checkStatus);
		builder.append(", formDefId=");
		builder.append(formDefId);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", actDefId=");
		builder.append(actDefId);
		builder.append(", superExecution=");
		builder.append(superExecution);
		builder.append(", candidateUser=");
		builder.append(candidateUser);
		builder.append(", status=");
		builder.append(status);
		builder.append(", startTimeStr=");
		builder.append(startTimeStr);
		builder.append(", endTimeStr=");
		builder.append(endTimeStr);
		builder.append(", durTimeStr=");
		builder.append(durTimeStr);
		builder.append(", read=");
		builder.append(read);
		builder.append("]");
		return builder.toString();
	}
	
}
