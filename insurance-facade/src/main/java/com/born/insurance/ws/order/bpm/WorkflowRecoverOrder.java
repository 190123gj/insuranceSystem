package com.born.insurance.ws.order.bpm;

import com.born.insurance.ws.order.base.ProcessOrder;

public class WorkflowRecoverOrder extends ProcessOrder {
	private static final long serialVersionUID = 9164861031420987022L;
	long runId;
	String informType = "3";//1邮件,2短信,3站内信
	String opinion;//意见
	String backToStart;//1撤销 0追回;
	
	@Override
	public void check() {
		validateHasZore(runId, "runId");
		validateHasZore(userId, "执行人");
		validateHasText(opinion, "意见");
	}
	
	public long getRunId() {
		return this.runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public String getInformType() {
		return this.informType;
	}
	
	public void setInformType(String informType) {
		this.informType = informType;
	}
	
	public String getOpinion() {
		return this.opinion;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getBackToStart() {
		return this.backToStart;
	}
	
	public void setBackToStart(String backToStart) {
		this.backToStart = backToStart;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowRecoverOrder [userId=");
		builder.append(userId);
		builder.append(", runId=");
		builder.append(runId);
		builder.append(", informType=");
		builder.append(informType);
		builder.append(", opinion=");
		builder.append(opinion);
		builder.append(", backToStart=");
		builder.append(backToStart);
		builder.append("]");
		return builder.toString();
	}
	
}
