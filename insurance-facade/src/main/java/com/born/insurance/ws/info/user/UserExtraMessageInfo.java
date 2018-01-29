/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午9:58:53 创建
 */
package com.born.insurance.ws.info.user;

import java.util.Date;

import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class UserExtraMessageInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private long userId;
	
	private String userName;
	
	private String userAccount;
	
	private String userJudgeKey;
	
	private String oaSystemId;
	
	private String bankName;
	
	private String bankAccountNo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getOaSystemId() {
		return this.oaSystemId;
	}
	
	public void setOaSystemId(String oaSystemId) {
		this.oaSystemId = oaSystemId;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankAccountNo() {
		return this.bankAccountNo;
	}
	
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserJudgeKey() {
		return this.userJudgeKey;
	}
	
	public void setUserJudgeKey(String userJudgeKey) {
		this.userJudgeKey = userJudgeKey;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
