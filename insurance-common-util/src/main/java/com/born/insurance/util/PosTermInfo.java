package com.born.insurance.util;

import java.io.Serializable;

public class PosTermInfo implements Serializable {
	
	private static final long serialVersionUID = 6382271056135121128L;
	
	String termInfoId;
	
	String termInfoName;
	
	public String getTermInfoId() {
		return this.termInfoId;
	}
	
	public void setTermInfoId(String termInfoId) {
		this.termInfoId = termInfoId;
	}
	
	public String getTermInfoName() {
		return this.termInfoName;
	}
	
	public void setTermInfoName(String termInfoName) {
		this.termInfoName = termInfoName;
	}
	
}
