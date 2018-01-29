package com.born.insurance.ws.order.bpm;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class TaskNodeInfo implements Serializable {
	private static final long serialVersionUID = 5763200435012033522L;
	String nodeId;
	String nodeName;
	String nodeType;//exclusiveGateway 判断网关 ；userTask 用户任务；startEvent1 开始；endEvent1 开始
	String formUrl;
	List<TaskNodeInfo> nextFlowNode = Lists.newArrayList();
	boolean canSelectUser;
	String informType;
	
	public String getFormUrl() {
		return this.formUrl;
	}
	
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
	public List<TaskNodeInfo> getNextFlowNode() {
		return this.nextFlowNode;
	}
	
	public void setNextFlowNode(List<TaskNodeInfo> nextFlowNode) {
		this.nextFlowNode = nextFlowNode;
	}
	
	public String getNodeId() {
		return this.nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getNodeName() {
		return this.nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getNodeType() {
		return this.nodeType;
	}
	
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public boolean isCanSelectUser() {
		return this.canSelectUser;
	}
	
	public void setCanSelectUser(boolean canSelectUser) {
		this.canSelectUser = canSelectUser;
	}
	
	public String getInformType() {
		return this.informType;
	}
	
	public void setInformType(String informType) {
		this.informType = informType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskNodeInfo [nodeId=");
		builder.append(nodeId);
		builder.append(", nodeName=");
		builder.append(nodeName);
		builder.append(", nodeType=");
		builder.append(nodeType);
		builder.append(", formUrl=");
		builder.append(formUrl);
		builder.append(", nextFlowNode=");
		builder.append(nextFlowNode);
		builder.append(", canSelectUser=");
		builder.append(canSelectUser);
		builder.append(", informType=");
		builder.append(informType);
		builder.append("]");
		return builder.toString();
	}
	
}
