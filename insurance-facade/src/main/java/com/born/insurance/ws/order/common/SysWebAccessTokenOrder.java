package com.born.insurance.ws.order.common;

import java.util.Date;

import javax.jws.WebService;

import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.order.base.ValidateOrderBase;

@WebService
public class SysWebAccessTokenOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 7520179591012789241L;
	
	private long id;
	
	private long userId;
	
	private String userName;
	
	private String userAccount;
	
	private String accessToken;
	
	private BooleanEnum isValid;
	
	private String remark;
	
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
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public BooleanEnum getIsValid() {
		return this.isValid;
	}
	
	public void setIsValid(BooleanEnum isValid) {
		this.isValid = isValid;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
