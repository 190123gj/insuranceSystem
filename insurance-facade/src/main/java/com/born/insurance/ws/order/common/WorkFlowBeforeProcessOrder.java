package com.born.insurance.ws.order.common;

import java.util.Map;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.base.ProcessOrder;

public class WorkFlowBeforeProcessOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 4176350270127956761L;
	
	FormInfo formInfo;
	
	Map<String, Object> customizeMap;
	
	public FormInfo getFormInfo() {
		return this.formInfo;
	}
	
	public void setFormInfo(FormInfo formInfo) {
		this.formInfo = formInfo;
	}
	
	public Map<String, Object> getCustomizeMap() {
		return this.customizeMap;
	}
	
	public void setCustomizeMap(Map<String, Object> customizeMap) {
		this.customizeMap = customizeMap;
	}
	
}
