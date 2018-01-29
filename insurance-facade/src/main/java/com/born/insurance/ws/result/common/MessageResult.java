package com.born.insurance.ws.result.common;

import com.born.insurance.ws.info.common.MessageReceivedInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

public class MessageResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = -6573704269130355064L;
	MessageReceivedInfo messageReceivedInfo;
	
	public MessageReceivedInfo getMessageReceivedInfo() {
		return this.messageReceivedInfo;
	}
	
	public void setMessageReceivedInfo(MessageReceivedInfo messageReceivedInfo) {
		this.messageReceivedInfo = messageReceivedInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageResult [messageReceivedInfo=");
		builder.append(messageReceivedInfo);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
