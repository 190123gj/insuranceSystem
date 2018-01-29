package com.born.insurance.integration.bpm.result;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.insurance.integration.bpm.flow.TaskEntity;
import com.born.insurance.ws.order.bpm.enums.RecoverStatusEnum;
import com.born.insurance.ws.order.bpm.enums.WorkflowStatusEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.yjf.common.lang.util.ListUtil;

public class WorkflowResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = -7146930756957596962L;
	
	long defId;
	String businessUrl;
	String creator;
	Date createtime;
	long creatorId;
	// 结束时间
	Date endTime;
	long startOrgId;
	WorkflowStatusEnum status;
	RecoverStatusEnum recover;
	String businessKey;
	String busDescp;
	String startOrgName;
	// 执行持续时间总长（毫秒)
	long duration;
	// 流程定义名称
	String processName;
	// 流程实例标题
	String subject;
	/**
	 * 父流程运行的ID。
	 */
	long parentId;
	
	List<TaskEntity> nextTaskList;
	private Map<String, Object> customizeMap = new HashMap<String, Object>();
	
	// 处理后状态
	protected WorkflowStatusEnum processAfterStatus;
	
	public boolean isStarting() {
		return status == WorkflowStatusEnum.STATUS_RUNNING;
	}
	
	public boolean isFinished() {
		return ListUtil.isEmpty(nextTaskList)
				&& processAfterStatus == WorkflowStatusEnum.STATUS_FINISH;
	}
	
	public Map<String, Object> getCustomizeMap() {
		return this.customizeMap;
	}
	
	public void setCustomizeMap(Map<String, Object> customizeMap) {
		this.customizeMap = customizeMap;
	}
	
	public void addCustomizeAttr(String key, String value) {
		customizeMap.put(key, value);
	}
	
	public Object getCustomizeAttr(String key) {
		return customizeMap.get(key);
	}
	
	/**
	 * 
	 * 异常终止
	 */
	public boolean isAborted() {
		return status == WorkflowStatusEnum.STATUS_MANUAL_FINISH;
	}
	
	public long getDefId() {
		return this.defId;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public String getBusinessUrl() {
		return this.businessUrl;
	}
	
	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}
	
	public String getCreator() {
		return this.creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public long getCreatorId() {
		return this.creatorId;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public long getStartOrgId() {
		return this.startOrgId;
	}
	
	public void setStartOrgId(long startOrgId) {
		this.startOrgId = startOrgId;
	}
	
	public WorkflowStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(WorkflowStatusEnum status) {
		this.status = status;
	}
	
	public String getBusinessKey() {
		return this.businessKey;
	}
	
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	public String getBusDescp() {
		return this.busDescp;
	}
	
	public void setBusDescp(String busDescp) {
		this.busDescp = busDescp;
	}
	
	public String getStartOrgName() {
		return this.startOrgName;
	}
	
	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public String getProcessName() {
		return this.processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public RecoverStatusEnum getRecover() {
		return this.recover;
	}
	
	public void setRecover(RecoverStatusEnum recover) {
		this.recover = recover;
	}
	
	public WorkflowStatusEnum getProcessAfterStatus() {
		return this.processAfterStatus;
	}
	
	public void setProcessAfterStatus(WorkflowStatusEnum processAfterStatus) {
		this.processAfterStatus = processAfterStatus;
	}
	
	public List<TaskEntity> getNextTaskList() {
		return this.nextTaskList;
	}
	
	public void setNextTaskList(List<TaskEntity> nextTaskList) {
		this.nextTaskList = nextTaskList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowResult [defId=");
		builder.append(defId);
		builder.append(", businessUrl=");
		builder.append(businessUrl);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", createtime=");
		builder.append(createtime);
		builder.append(", creatorId=");
		builder.append(creatorId);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", startOrgId=");
		builder.append(startOrgId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", businessKey=");
		builder.append(businessKey);
		builder.append(", busDescp=");
		builder.append(busDescp);
		builder.append(", startOrgName=");
		builder.append(startOrgName);
		builder.append(", duration=");
		builder.append(duration);
		builder.append("]");
		return builder.toString();
	}
}
