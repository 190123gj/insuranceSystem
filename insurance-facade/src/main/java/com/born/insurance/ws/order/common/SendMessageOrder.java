package com.born.insurance.ws.order.common;

import com.born.insurance.ws.enums.BooleanEnum;
import org.springframework.util.Assert;

import com.born.insurance.ws.enums.MessageTypeEnum;

public class SendMessageOrder extends MessageOrder {
	
	private static final long serialVersionUID = -13115344582471140L;
	
	private String userAccount;
	
	private SimpleUserInfo sendUser;
	
	@Override
	public void check() {
		super.check();
		Assert
			.isTrue((userAccount != null && !"".equals(userAccount) || (sendUser != null)), "收件人");
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public SimpleUserInfo getSendUser() {
		return this.sendUser;
	}
	
	public void setSendUser(SimpleUserInfo sendUser) {
		this.sendUser = sendUser;
	}
	
	public static SendMessageOrder createSystemMessageOrder() {
		SendMessageOrder messageOrder = new SendMessageOrder();
		messageOrder.setIsSendMessage(BooleanEnum.IS);
		messageOrder.setMessageId(-1);
		messageOrder.setMessageType(MessageTypeEnum.SYSTEM);
		messageOrder.setMessageSenderId(-1);
		messageOrder.setMessageSenderName("系统消息");
		messageOrder.setNotificationObject("定向消息");
		return messageOrder;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendMessageOrder [userAccount=");
		builder.append(userAccount);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
