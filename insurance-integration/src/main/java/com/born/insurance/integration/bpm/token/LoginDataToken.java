package com.born.insurance.integration.bpm.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.ws.order.LoginOrder;


/**
 * 带验证码 登录
 * 
 * @Filename LoginDataToken.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author chenxu
 * 
 * @Email chenxu@yiji.com
 * 
 * @History <li>Author: chenxu</li> <li>Date: 2012-7-19</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class LoginDataToken extends UsernamePasswordToken {
	
	private static final long serialVersionUID = 6295199072699314520L;
	
	private LoginOrder loginOrder;
	
	private boolean isValidate = true;
	
	UserInfo info;
	
	public LoginOrder getLoginOrder() {
		return this.loginOrder;
	}
	
	public void setLoginOrder(LoginOrder loginOrder) {
		this.loginOrder = loginOrder;
	}
	
	public boolean isValidate() {
		return this.isValidate;
	}
	
	public UserInfo getInfo() {
		return this.info;
	}
	
	public void setInfo(UserInfo info) {
		this.info = info;
	}
	
	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
	
	public LoginDataToken() {
	}
	
	public LoginDataToken(final LoginOrder loginData) {
		this(loginData, false, null);
	}
	
	public LoginDataToken(final LoginOrder loginData, final String host) {
		this(loginData, false, host);
	}
	
	public LoginDataToken(final LoginOrder loginData, final boolean rememberMe) {
		this(loginData, rememberMe, null);
	}
	
	public LoginDataToken(final LoginOrder loginData, final boolean rememberMe, final String host) {
		super(loginData.getUserName(), loginData.getPassword() != null ? loginData.getPassword()
			.toCharArray() : null, rememberMe, host);
		this.loginOrder = loginData;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginDataToken [loginOrder=");
		builder.append(loginOrder);
		builder.append(", isValidate=");
		builder.append(isValidate);
		builder.append(", info=");
		builder.append(info);
		builder.append("]");
		return builder.toString();
	}
	
}
