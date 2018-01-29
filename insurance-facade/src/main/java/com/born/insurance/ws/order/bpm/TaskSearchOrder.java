package com.born.insurance.ws.order.bpm;

import com.born.insurance.ws.base.QueryPageBase;
import org.springframework.util.Assert;



public class TaskSearchOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -4777384038062334377L;
	String taskNodeName;
	String subject;
	String processName;
	String orderField;
	String orderSeq;
	String userName;
	long userId;
	
	@Override
	public void check() {
		Assert.isTrue(userId > 0 || (userName != null && !"".equals(userName.trim())), "用户不能为空");
	}
	
	public String getTaskNodeName() {
		return this.taskNodeName;
	}
	
	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getProcessName() {
		return this.processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getOrderField() {
		return this.orderField;
	}
	
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	
	public String getOrderSeq() {
		return this.orderSeq;
	}
	
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskSearchOrder [taskNodeName=");
		builder.append(taskNodeName);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", processName=");
		builder.append(processName);
		builder.append(", orderField=");
		builder.append(orderField);
		builder.append(", orderSeq=");
		builder.append(orderSeq);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}
	
}
