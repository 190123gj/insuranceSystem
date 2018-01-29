package com.born.insurance.ws.order.bpm;

import java.io.Serializable;
import java.util.Date;

public class WorkflowTaskInfo implements Serializable {
	
	private static final long serialVersionUID = 6136774225419441219L;
	private String id;
	private String name;
	private String subject;
	private String subjectView;
	private String parentTaskId;
	private String description;
	private String priority;
	private Date createTime;
	private String owner;
	/**
	 * 执行人
	 */
	private String assignee;
	private String delegationState;
	private String executionId;
	private String processInstanceId;
	private String processDefinitionId;
	private String taskDefinitionKey;
	private Date dueDate;
	private long revision;
	private String processName;
	private String taskUrl;
	private String status;
	private String type;
	private long allowDivert;
	private long ischeck;
	private long defId;
	private long allowBatchApprove;
	private long runId;
	private long typeId;
	private String typeName;
	private String orgName;
	private String tagIds;
	private long creatorId;
	private String creator;
	private Boolean isAgent = Boolean.valueOf(false);
	
	private Boolean isDivert = Boolean.valueOf(false);
	private Short taskStatus;
	private String codebefore;
	private long hasRead = Integer.valueOf(0);
	private long reminderLv;
	private Date createDate;
	private String createDateStr;
	private String globalFlowNo;
	
	protected String initialAssignee;
	
	protected String category;
	
	protected boolean isIdentityLinksInitialized = false;
	
	protected boolean isDeleted;
	
	protected String eventName;
	
	protected boolean forcedUpdate;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getParentTaskId() {
		return this.parentTaskId;
	}
	
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPriority() {
		return this.priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
	public String getDelegationState() {
		return this.delegationState;
	}
	
	public void setDelegationState(String delegationState) {
		this.delegationState = delegationState;
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
	
	public Date getDueDate() {
		return this.dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public long getRevision() {
		return this.revision;
	}
	
	public void setRevision(Integer revision) {
		this.revision = revision;
	}
	
	public String getProcessName() {
		return this.processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getTaskUrl() {
		return this.taskUrl;
	}
	
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getAllowDivert() {
		return this.allowDivert;
	}
	
	public void setAllowDivert(Integer allowDivert) {
		this.allowDivert = allowDivert;
	}
	
	public long getIscheck() {
		return this.ischeck;
	}
	
	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}
	
	public Long getDefId() {
		return this.defId;
	}
	
	public void setDefId(Long defId) {
		this.defId = defId;
	}
	
	public long getAllowBatchApprove() {
		return this.allowBatchApprove;
	}
	
	public void setAllowBatchApprove(Integer allowBatchApprove) {
		this.allowBatchApprove = allowBatchApprove;
	}
	
	public Long getRunId() {
		return this.runId;
	}
	
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	
	public Long getTypeId() {
		return this.typeId;
	}
	
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getOrgName() {
		return this.orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getTagIds() {
		return this.tagIds;
	}
	
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getCreator() {
		return this.creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Boolean getIsAgent() {
		return this.isAgent;
	}
	
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}
	
	public Boolean getIsDivert() {
		return this.isDivert;
	}
	
	public void setIsDivert(Boolean isDivert) {
		this.isDivert = isDivert;
	}
	
	public Short getTaskStatus() {
		return this.taskStatus;
	}
	
	public void setTaskStatus(Short taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getCodebefore() {
		return this.codebefore;
	}
	
	public void setCodebefore(String codebefore) {
		this.codebefore = codebefore;
	}
	
	public long getReminderLv() {
		return this.reminderLv;
	}
	
	public void setReminderLv(Integer reminderLv) {
		this.reminderLv = reminderLv;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getGlobalFlowNo() {
		return this.globalFlowNo;
	}
	
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	
	public long getHasRead() {
		return this.hasRead;
	}
	
	public void setRevision(long revision) {
		this.revision = revision;
	}
	
	public void setAllowDivert(long allowDivert) {
		this.allowDivert = allowDivert;
	}
	
	public void setIscheck(long ischeck) {
		this.ischeck = ischeck;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public void setAllowBatchApprove(long allowBatchApprove) {
		this.allowBatchApprove = allowBatchApprove;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	
	public void setHasRead(long hasRead) {
		this.hasRead = hasRead;
	}
	
	public void setReminderLv(long reminderLv) {
		this.reminderLv = reminderLv;
	}
	
	public String getInitialAssignee() {
		return this.initialAssignee;
	}
	
	public void setInitialAssignee(String initialAssignee) {
		this.initialAssignee = initialAssignee;
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
	
	public boolean isForcedUpdate() {
		return this.forcedUpdate;
	}
	
	public void setForcedUpdate(boolean forcedUpdate) {
		this.forcedUpdate = forcedUpdate;
	}
	
	public String getSubjectView() {
		if (subject != null && subject.indexOf("|") > 0) {
			String[] s = subject.split("\\|");
			String subjectView = "";
			for (int i = 0; i < s.length - 1; i++) {
				subjectView += s[i];
			}
			return subjectView;
		}
		return this.subject;
	}
	
	public void setSubjectView(String subjectView) {
		this.subjectView = subjectView;
	}
	
	public String getCreateDateStr() {
		if (subject != null && subject.indexOf("|") > 0) {
			String[] s = subject.split("\\|");
			return s[s.length - 1];
		}
		return this.createDateStr;
	}
	
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowTaskInfo [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", parentTaskId=");
		builder.append(parentTaskId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", assignee=");
		builder.append(assignee);
		builder.append(", delegationState=");
		builder.append(delegationState);
		builder.append(", executionId=");
		builder.append(executionId);
		builder.append(", processInstanceId=");
		builder.append(processInstanceId);
		builder.append(", processDefinitionId=");
		builder.append(processDefinitionId);
		builder.append(", taskDefinitionKey=");
		builder.append(taskDefinitionKey);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", revision=");
		builder.append(revision);
		builder.append(", processName=");
		builder.append(processName);
		builder.append(", taskUrl=");
		builder.append(taskUrl);
		builder.append(", status=");
		builder.append(status);
		builder.append(", type=");
		builder.append(type);
		builder.append(", allowDivert=");
		builder.append(allowDivert);
		builder.append(", ischeck=");
		builder.append(ischeck);
		builder.append(", defId=");
		builder.append(defId);
		builder.append(", allowBatchApprove=");
		builder.append(allowBatchApprove);
		builder.append(", runId=");
		builder.append(runId);
		builder.append(", typeId=");
		builder.append(typeId);
		builder.append(", typeName=");
		builder.append(typeName);
		builder.append(", orgName=");
		builder.append(orgName);
		builder.append(", tagIds=");
		builder.append(tagIds);
		builder.append(", creatorId=");
		builder.append(creatorId);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", isAgent=");
		builder.append(isAgent);
		builder.append(", isDivert=");
		builder.append(isDivert);
		builder.append(", taskStatus=");
		builder.append(taskStatus);
		builder.append(", codebefore=");
		builder.append(codebefore);
		builder.append(", hasRead=");
		builder.append(hasRead);
		builder.append(", reminderLv=");
		builder.append(reminderLv);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", globalFlowNo=");
		builder.append(globalFlowNo);
		builder.append(", initialAssignee=");
		builder.append(initialAssignee);
		builder.append(", category=");
		builder.append(category);
		builder.append(", isIdentityLinksInitialized=");
		builder.append(isIdentityLinksInitialized);
		builder.append(", isDeleted=");
		builder.append(isDeleted);
		builder.append(", eventName=");
		builder.append(eventName);
		builder.append(", forcedUpdate=");
		builder.append(forcedUpdate);
		builder.append("]");
		return builder.toString();
	}
	
}
