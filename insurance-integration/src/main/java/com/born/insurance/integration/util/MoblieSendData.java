/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.integration.util;

import java.io.Serializable;
import java.util.Date;


import com.born.insurance.integration.enums.FunctionalModulesEnum;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename MoblieSendData.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-3-9</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class MoblieSendData implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -9052899375070718812L;
	Date lastSendDate;
	Date createDate;
	String sendMobileNumber;
	long sendCount;
	FunctionalModulesEnum modulesEnum;
	String userName;
	public final static long MAX_SEND_TIMES = 50;
	
	public MoblieSendData(String sendMobileNumber) {
		lastSendDate = new Date();
		sendCount = 1;
		this.sendMobileNumber = sendMobileNumber;
		
	}
	
	public MoblieSendData(String sendMobileNumber, FunctionalModulesEnum modulesEnum) {
		lastSendDate = new Date();
		sendCount = 1;
		this.sendMobileNumber = sendMobileNumber;
		this.modulesEnum = modulesEnum;
	}
	
	public String getSendDataKey() {
		String key = sendMobileNumber + modulesEnum.code();
		return key;
	}
	
	public String getSendDataUserKey() {
		String key = null;
		if (StringUtil.isNotEmpty(userName))
			key = sendMobileNumber + modulesEnum.code() + userName;
		else
			key = getSendDataKey();
		return key;
	}
	
	public void addCount() {
		sendCount++;
	}
	
	public Date getLastSendDate() {
		return lastSendDate;
	}
	
	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	
	public String getSendMobileNumber() {
		return sendMobileNumber;
	}
	
	public void setSendMobileNumber(String sendMobileNumber) {
		this.sendMobileNumber = sendMobileNumber;
	}
	
	public long getSendCount() {
		return sendCount;
	}
	
	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
	}
	
	public FunctionalModulesEnum getModulesEnum() {
		return modulesEnum;
	}
	
	public void setModulesEnum(FunctionalModulesEnum modulesEnum) {
		this.modulesEnum = modulesEnum;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
