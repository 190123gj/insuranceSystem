package com.born.insurance.ws.order;

import java.io.Serializable;

public class LoginOrder implements Serializable {
	private static final long serialVersionUID = 9190151302997776303L;
	String userName;
	String password;
	String loginIp;
	String agent;
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLoginIp() {
		return this.loginIp;
	}
	
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public String getAgent() {
		return this.agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginOrder [userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", loginIp=");
		builder.append(loginIp);
		builder.append(", agent=");
		builder.append(agent);
		builder.append("]");
		return builder.toString();
	}
	
}
