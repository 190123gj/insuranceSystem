package com.born.insurance.integration.result;


import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

public class LoginResult extends InsuranceBaseResult {
	private static final long serialVersionUID = 5977359625962122957L;
	
	private UserInfo userInfo;
	
	private long pwdErrorCount = 0;
	
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public long getPwdErrorCount() {
		return this.pwdErrorCount;
	}
	
	public void setPwdErrorCount(long pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResult [userInfo=");
		builder.append(userInfo);
		builder.append(", pwdErrorCount=");
		builder.append(pwdErrorCount);
		builder.append("]");
		return builder.toString();
	}
	
}
