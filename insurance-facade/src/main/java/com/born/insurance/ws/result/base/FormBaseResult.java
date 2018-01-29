package com.born.insurance.ws.result.base;

import com.born.insurance.ws.info.common.FormInfo;

public class FormBaseResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = 5565388893810134298L;
	
	FormInfo formInfo;
	
	public FormInfo getFormInfo() {
		return this.formInfo;
	}
	
	public void setFormInfo(FormInfo formInfo) {
		this.formInfo = formInfo;
	}
	
}
