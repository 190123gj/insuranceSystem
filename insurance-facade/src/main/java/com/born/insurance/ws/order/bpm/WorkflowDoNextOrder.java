package com.born.insurance.ws.order.bpm;

import java.util.List;

import com.born.insurance.ws.order.base.ProcessOrder;

public class WorkflowDoNextOrder extends ProcessOrder {
	private static final long serialVersionUID = -8997549404284738908L;
	String taskId;
	String voteAgree;
	String voteContent;
	String nextNodeId;
	String nextUser;
	String businessKey;
	String isBack = "0";// 0正常, 1驳回（撤销),2驳回到发起人（撤销)
	List<FlowVarField> fields;
	long processId;
	
	String processRealName;
	
	@Override
	public void check() {
		validateHasText(taskId, "任务id");
		validateHasText(userAccount, "执行人");
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getVoteAgree() {
		return this.voteAgree;
	}
	
	public void setVoteAgree(String voteAgree) {
		this.voteAgree = voteAgree;
	}
	
	public String getVoteContent() {
		return this.voteContent;
	}
	
	public void setVoteContent(String voteContent) {
		this.voteContent = voteContent;
	}
	
	public String getNextNodeId() {
		return this.nextNodeId;
	}
	
	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}
	
	public String getNextUser() {
		return this.nextUser;
	}
	
	public void setNextUser(String nextUser) {
		this.nextUser = nextUser;
	}
	
	public String getBusinessKey() {
		return this.businessKey;
	}
	
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	public String getIsBack() {
		return this.isBack;
	}
	
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
	public List<FlowVarField> getFields() {
		return this.fields;
	}
	
	public void setFields(List<FlowVarField> fields) {
		this.fields = fields;
	}
	
	public long getProcessId() {
		return this.processId;
	}
	
	public void setProcessId(long processId) {
		this.processId = processId;
	}
	
	public String getProcessRealName() {
		return this.processRealName;
	}
	
	public void setProcessRealName(String processRealName) {
		this.processRealName = processRealName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowDoNextOrder [taskId=");
		builder.append(taskId);
		builder.append(", userAccount=");
		builder.append(userAccount);
		builder.append(", voteAgree=");
		builder.append(voteAgree);
		builder.append(", voteContent=");
		builder.append(voteContent);
		builder.append(", nextNodeId=");
		builder.append(nextNodeId);
		builder.append(", nextUser=");
		builder.append(nextUser);
		builder.append(", businessKey=");
		builder.append(businessKey);
		builder.append(", isBack=");
		builder.append(isBack);
		builder.append(", fields=");
		builder.append(fields);
		builder.append(", processId=");
		builder.append(processId);
		builder.append(", processRealName=");
		builder.append(processRealName);
		builder.append("]");
		return builder.toString();
	}
	
}
