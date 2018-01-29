package com.born.insurance.ws.order;


import com.born.insurance.ws.order.base.ValidateOrderBase;

public class UpdatePasswordOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4794120383774866170L;
	String userName;
	String newPassword;
	
	@Override
	public void check() {
		validateHasText(userName, "用户名");
		validateHasText(newPassword, "密码");
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNewPassword() {
		return this.newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdatePasswordOrder [userName=");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}
	
}
