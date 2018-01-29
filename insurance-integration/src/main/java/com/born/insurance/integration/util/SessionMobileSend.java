/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.integration.util;

import com.born.insurance.integration.enums.FunctionalModulesEnum;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @Filename SessionMobileSend.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-3-11</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public class SessionMobileSend implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -1449591466737944826L;
	Date lastSendDate;
	String code;
	int equalCount = 0;
	String moblie;
	FunctionalModulesEnum functionalModulesEnum;
	
	public Date getLastSendDate() {
		return lastSendDate;
	}
	
	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getEqualCount() {
		return equalCount;
	}
	
	public void setEqualCount(int equalCount) {
		this.equalCount = equalCount;
	}
	
	public String getMoblie() {
		return moblie;
	}
	
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
	public FunctionalModulesEnum getFunctionalModulesEnum() {
		return functionalModulesEnum;
	}
	
	public void setFunctionalModulesEnum(FunctionalModulesEnum functionalModulesEnum) {
		this.functionalModulesEnum = functionalModulesEnum;
	}
	
}
