package com.born.insurance.ws.info.common;

import com.born.insurance.ws.enums.MessageReceivedStatusEnum;

public class MessageReceivedInfo extends MessageInfo {
	
	private static final long serialVersionUID = 5411715943125523570L;
	private long receivedId;
	private String messageReceivedName;
	private String messageReceivedAccount;
	private long messageReceivedId;
	
	private MessageReceivedStatusEnum messageReceivedStatus;
	
	public String getMessageReceivedName() {
		return this.messageReceivedName;
	}
	
	public void setMessageReceivedName(String messageReceivedName) {
		this.messageReceivedName = messageReceivedName;
	}
	
	public long getMessageReceivedId() {
		return this.messageReceivedId;
	}
	
	public void setMessageReceivedId(long messageReceivedId) {
		this.messageReceivedId = messageReceivedId;
	}
	
	public MessageReceivedStatusEnum getMessageReceivedStatus() {
		return this.messageReceivedStatus;
	}
	
	public void setMessageReceivedStatus(MessageReceivedStatusEnum messageReceivedStatus) {
		this.messageReceivedStatus = messageReceivedStatus;
	}
	
	public long getReceivedId() {
		return this.receivedId;
	}
	
	public void setReceivedId(long receivedId) {
		this.receivedId = receivedId;
	}
	
	public String getMessageReceivedAccount() {
		return this.messageReceivedAccount;
	}
	
	public void setMessageReceivedAccount(String messageReceivedAccount) {
		this.messageReceivedAccount = messageReceivedAccount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageReceivedInfo [receivedId=");
		builder.append(receivedId);
		builder.append(", messageReceivedName=");
		builder.append(messageReceivedName);
		builder.append(", messageReceivedAccount=");
		builder.append(messageReceivedAccount);
		builder.append(", messageReceivedId=");
		builder.append(messageReceivedId);
		builder.append(", messageReceivedStatus=");
		builder.append(messageReceivedStatus);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
