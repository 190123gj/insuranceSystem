package com.born.insurance.ws.order.common;

import java.util.HashMap;
import java.util.Map;

public class SimpleFormAuditOrder extends SimpleFormOrder {
	private static final long serialVersionUID = 7904681778454514542L;
	long taskId;
	private Map<String, Object> customizeMap = new HashMap<String, Object>();
	
	String voteAgree;//TASK  描述在TaskOpinion
	String voteContent;
	String nextNodeId;
	String nextUser;
	String businessKey;
	String isBack = "0";// 0正常, 1驳回（撤销),2驳回到发起人（撤销) 3驳回到指定步骤
	boolean reSubmit;
	
	public void addCustomizeAttr(String key, String value) {
		customizeMap.put(key, value);
	}
	
	public Object getCustomizeAttr(String key) {
		return customizeMap.get(key);
	}
	
	public void setCustomizeMap(Map<String, Object> customizeMap) {
		this.customizeMap = customizeMap;
	}
	
	public Map<String, Object> getCustomizeMap() {
		return customizeMap;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
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
	
	public boolean isReSubmit() {
		return this.reSubmit;
	}
	
	public void setReSubmit(boolean reSubmit) {
		this.reSubmit = reSubmit;
	}
	
}
