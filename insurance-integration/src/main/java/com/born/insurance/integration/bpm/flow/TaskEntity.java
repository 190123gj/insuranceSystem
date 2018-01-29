package com.born.insurance.integration.bpm.flow;

import java.util.Date;

public class TaskEntity {
	String id;
	protected int revision;
	
	protected String owner;
	protected String assignee;
	protected String initialAssignee;
	
	protected String parentTaskId;
	
	protected String name;
	protected String description;
	protected int priority = 0;
	protected Date createTime; // The time when the task has been created
	protected Date dueDate;;
	protected String category;
	
	protected boolean isIdentityLinksInitialized = false;
	
	protected String executionId;
	
	protected String processInstanceId;
	
	protected String processDefinitionId;
	
	protected String taskDefinitionKey;
	
	protected boolean isDeleted;
	
	protected String eventName;
	
	protected String tenantId = "";
	
	protected boolean forcedUpdate;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getRevision() {
		return this.revision;
	}
	
	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getAssignee() {
		return this.assignee;
	}
	
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	public String getInitialAssignee() {
		return this.initialAssignee;
	}
	
	public void setInitialAssignee(String initialAssignee) {
		this.initialAssignee = initialAssignee;
	}
	
	public String getParentTaskId() {
		return this.parentTaskId;
	}
	
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getDueDate() {
		return this.dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isIdentityLinksInitialized() {
		return this.isIdentityLinksInitialized;
	}
	
	public void setIdentityLinksInitialized(boolean isIdentityLinksInitialized) {
		this.isIdentityLinksInitialized = isIdentityLinksInitialized;
	}
	
	public String getExecutionId() {
		return this.executionId;
	}
	
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}
	
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	public String getTaskDefinitionKey() {
		return this.taskDefinitionKey;
	}
	
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	
	public boolean isDeleted() {
		return this.isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getTenantId() {
		return this.tenantId;
	}
	
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public boolean isForcedUpdate() {
		return this.forcedUpdate;
	}
	
	public void setForcedUpdate(boolean forcedUpdate) {
		this.forcedUpdate = forcedUpdate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskEntity [revision=");
		builder.append(revision);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", assignee=");
		builder.append(assignee);
		builder.append(", initialAssignee=");
		builder.append(initialAssignee);
		builder.append(", parentTaskId=");
		builder.append(parentTaskId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", category=");
		builder.append(category);
		builder.append(", isIdentityLinksInitialized=");
		builder.append(isIdentityLinksInitialized);
		builder.append(", executionId=");
		builder.append(executionId);
		builder.append(", processInstanceId=");
		builder.append(processInstanceId);
		builder.append(", processDefinitionId=");
		builder.append(processDefinitionId);
		builder.append(", taskDefinitionKey=");
		builder.append(taskDefinitionKey);
		builder.append(", isDeleted=");
		builder.append(isDeleted);
		builder.append(", eventName=");
		builder.append(eventName);
		builder.append(", tenantId=");
		builder.append(tenantId);
		builder.append(", forcedUpdate=");
		builder.append(forcedUpdate);
		builder.append("]");
		return builder.toString();
	}
	
}
