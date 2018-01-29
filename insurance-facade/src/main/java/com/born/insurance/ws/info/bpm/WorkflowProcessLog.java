package com.born.insurance.ws.info.bpm;

import com.born.insurance.ws.order.bpm.TaskOpinion;

import java.io.Serializable;


public class WorkflowProcessLog implements Serializable {
	private static final long serialVersionUID = -5378886853339489827L;
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
	protected String startTime;
	// 结束时间
	protected String endTime;
	// 持续时间
	protected Long durTime;
	// 执行人ID
	protected Long exeUserId;
	// 执行人名
	protected String exeFullname;
	// 审批意见
	protected String opinion;
	// 审批状态
	protected int checkStatus = TaskOpinion.STATUS_CHECKING;
	
	// 审批状态
	protected String checkStatusMessage = "正在审批";
	
	// 表单定义Id
	protected Long formDefId = 0L;
	// 意见表单名称
	protected String fieldName;
	// 流程定义ID
	protected String actDefId;
	
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
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(String endTime) {
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
	
	public int getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
		
		if (checkStatus == (int) TaskOpinion.STATUS_AGREE) {
			this.checkStatusMessage = "同意";
		} else if (checkStatus == (int) TaskOpinion.STATUS_REFUSE) {
			checkStatusMessage = "反对";
		} else if (checkStatus == (int) TaskOpinion.STATUS_REJECT
					|| checkStatus == (int) TaskOpinion.STATUS_REJECT_TO_TASK_NODE
					|| checkStatus == (int) TaskOpinion.STATUS_REJECT_TOSTART) {
			checkStatusMessage = "驳回";
		} else if (checkStatus == (int) TaskOpinion.STATUS_RECOVER
					|| checkStatus == (int) TaskOpinion.STATUS_RECOVER_TOSTART
					|| checkStatus == (int) TaskOpinion.STATUS_REVOKED
					|| checkStatus == (int) TaskOpinion.STATUS_RETRIEVE) {
			checkStatusMessage = "撤销";
		} else if (checkStatus == (int) TaskOpinion.STATUS_ENDPROCESS) {
			checkStatusMessage = "终止流程";
		} else if (checkStatus == (int) TaskOpinion.STATUS_RESUBMIT) {
			checkStatusMessage = "重新提交";
		} else if (checkStatus == (int) TaskOpinion.STATUS_DELEGATE) {
			checkStatusMessage = "转办";
		} else if (checkStatus == (int) TaskOpinion.STATUS_AGENT) {
			checkStatusMessage = "代理";
		}
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
	
	public String getCheckStatusMessage() {
		return this.checkStatusMessage;
	}
	
	public void setCheckStatusMessage(String checkStatusMessage) {
		this.checkStatusMessage = checkStatusMessage;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowProcessLog [opinionId=");
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
		builder.append("]");
		return builder.toString();
	}
	
}
