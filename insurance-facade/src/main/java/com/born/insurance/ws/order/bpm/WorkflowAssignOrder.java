package com.born.insurance.ws.order.bpm;

import com.born.insurance.ws.order.base.ProcessOrder;

public class WorkflowAssignOrder extends ProcessOrder {
	private static final long serialVersionUID = 9164861031420987022L;
	String taskId;
	String informType = "3";//1邮件,2短信,3站内信
	String memo;//意见
	String assigneeId;
	String assigneeName;
	
	@Override
	public void check() {
		validateHasZore(userId, "执行人");
		validateHasText(assigneeId, "转办人");
		validateHasText(memo, "意见");
	}
	
	public String getInformType() {
		return this.informType;
	}
	
	public void setInformType(String informType) {
		this.informType = informType;
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getAssigneeId() {
		return this.assigneeId;
	}
	
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
	
	public String getAssigneeName() {
		return this.assigneeName;
	}
	
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowAssignOrder [userId=");
		builder.append(userId);
		builder.append(", taskId=");
		builder.append(taskId);
		builder.append(", informType=");
		builder.append(informType);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", assigneeId=");
		builder.append(assigneeId);
		builder.append(", assigneeName=");
		builder.append(assigneeName);
		builder.append("]");
		return builder.toString();
	}
	
}
