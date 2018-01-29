package com.born.insurance.ws.order.common;

import com.born.insurance.ws.order.base.ValidateOrderBase;

public class MyMessageOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1386232973535374205L;
	private long userId;
	private long receivedId;
	
	/***
	 * 消息接受状态
	 */
	private String messageReceivedStatus;
	
	@Override
	public void check() {
		validateHasZore(userId, "用户id");
		validateHasZore(receivedId, "接收消息id");
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getReceivedId() {
		return this.receivedId;
	}
	
	public void setReceivedId(long receivedId) {
		this.receivedId = receivedId;
	}
	
	public String getMessageReceivedStatus() {
		return messageReceivedStatus;
	}
	
	public void setMessageReceivedStatus(String messageReceivedStatus) {
		this.messageReceivedStatus = messageReceivedStatus;
	}
	
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("MyMessageOrder{");
		sb.append("userId=").append(userId);
		sb.append(", receivedId=").append(receivedId);
		sb.append(", messageReceivedStatus='").append(messageReceivedStatus).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
