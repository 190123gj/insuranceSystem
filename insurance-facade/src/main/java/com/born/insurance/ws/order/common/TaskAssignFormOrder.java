package com.born.insurance.ws.order.common;

public class TaskAssignFormOrder extends SimpleFormOrder {
	
	private static final long serialVersionUID = -7865860545317269471L;
	String memo;//意见
	String assigneeId;
	String assigneeName;
	String taskId;
	
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
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskAssignFormOrder [memo=");
		builder.append(memo);
		builder.append(", assigneeId=");
		builder.append(assigneeId);
		builder.append(", assigneeName=");
		builder.append(assigneeName);
		builder.append(", taskId=");
		builder.append(taskId);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
