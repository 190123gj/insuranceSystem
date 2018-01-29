package com.born.insurance.ws.order.bpm;

import java.util.List;

import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.base.ProcessOrder;

public class WorkflowStartOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 6071643458977735479L;
	FormCodeEnum formCodeEnum;
	long processId;
	String processUserName;
	String processRealName;
	FormInfo formInfo;
	String customTaskName;//自定义代办任务名称 
	List<FlowVarField> fields;
	
	@Override
	public void check() {
		validateGreaterThan(processId, "处理人id");
		validateHasText(processUserName, "处理人userName");
		validateHasText(processRealName, "处理人名称");
		validateNotNull(formCodeEnum, "表单类型不能为空");
		validateNotNull(formInfo, "表单信息");
	}
	
	public FormCodeEnum getFormCodeEnum() {
		return this.formCodeEnum;
	}
	
	public void setFormCodeEnum(FormCodeEnum formCodeEnum) {
		this.formCodeEnum = formCodeEnum;
	}
	
	public long getProcessId() {
		return this.processId;
	}
	
	public void setProcessId(long processId) {
		this.processId = processId;
	}
	
	public String getProcessUserName() {
		return this.processUserName;
	}
	
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}
	
	public String getProcessRealName() {
		return this.processRealName;
	}
	
	public void setProcessRealName(String processRealName) {
		this.processRealName = processRealName;
	}
	
	public FormInfo getFormInfo() {
		return this.formInfo;
	}
	
	public void setFormInfo(FormInfo formInfo) {
		this.formInfo = formInfo;
	}
	
	public List<FlowVarField> getFields() {
		return this.fields;
	}
	
	public void setFields(List<FlowVarField> fields) {
		this.fields = fields;
	}
	
	public String getCustomTaskName() {
		return this.customTaskName;
	}
	
	public void setCustomTaskName(String customTaskName) {
		this.customTaskName = customTaskName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowStartOrder [formCodeEnum=");
		builder.append(formCodeEnum);
		builder.append(", processId=");
		builder.append(processId);
		builder.append(", processUserName=");
		builder.append(processUserName);
		builder.append(", processRealName=");
		builder.append(processRealName);
		builder.append(", customTaskName=");
		builder.append(customTaskName);
		builder.append(", formInfo=");
		builder.append(formInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
