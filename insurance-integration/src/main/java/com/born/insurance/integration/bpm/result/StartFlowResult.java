package com.born.insurance.integration.bpm.result;

public class StartFlowResult extends WorkflowResult {
	
	private static final long serialVersionUID = 8668906432402047545L;
	long runId;
	// ACT流程实例ID
	long actInstId;
	// ACT流程定义ID
	String actDefId;
	
	public long getRunId() {
		return this.runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(long actInstId) {
		this.actInstId = actInstId;
	}
	
	public String getActDefId() {
		return this.actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
}
