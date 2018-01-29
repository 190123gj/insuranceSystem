package com.born.insurance.ws.order.common;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.MessageReceivedStatusEnum;
import com.born.insurance.ws.enums.MessageTypeEnum;
import com.born.insurance.ws.enums.MessageViewTypeEnum;

public class QueryReceviedMessageOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -1378947995532159138L;
	/**
	 * 接收用户的userId
	 */
	private long messageReceivedId;
	/**
	 * 接收用户的名称
	 */
	private String messageReceivedName;
	/**
	 * 接收用户的账号
	 */
	private String messageReceivedAccount;
	/**
	 * 主消息id
	 */
	private long messageId;
	/**
	 * 消息发送人
	 */
	private long messageSenderId;
	/**
	 * 显示方式
	 */
	private MessageViewTypeEnum viewType = MessageViewTypeEnum.LIST;
	/**
	 * 接收时间，发送时间 开始时间
	 */
	private Date beginReceivedDate;
	/**
	 * 接收时间，发送时间 结束时间
	 */
	private Date endReceivedDate;
	/**
	 * 消息类型
	 */
	private MessageTypeEnum messageType;
	/**
	 * 消息状态
	 */
	private List<MessageReceivedStatusEnum> statusList;
	
	/**
	 * 消息内容
	 */
	private String messageContent;
	
	public long getMessageReceivedId() {
		return this.messageReceivedId;
	}
	
	public void setMessageReceivedId(long messageReceivedId) {
		this.messageReceivedId = messageReceivedId;
	}
	
	public String getMessageReceivedName() {
		return this.messageReceivedName;
	}
	
	public void setMessageReceivedName(String messageReceivedName) {
		this.messageReceivedName = messageReceivedName;
	}
	
	public long getMessageId() {
		return this.messageId;
	}
	
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	public long getMessageSenderId() {
		return this.messageSenderId;
	}
	
	public void setMessageSenderId(long messageSenderId) {
		this.messageSenderId = messageSenderId;
	}
	
	public Date getBeginReceivedDate() {
		return this.beginReceivedDate;
	}
	
	public void setBeginReceivedDate(Date beginReceivedDate) {
		this.beginReceivedDate = beginReceivedDate;
	}
	
	public Date getEndReceivedDate() {
		return this.endReceivedDate;
	}
	
	public void setEndReceivedDate(Date endReceivedDate) {
		this.endReceivedDate = endReceivedDate;
	}
	
	public MessageTypeEnum getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}
	
	public List<MessageReceivedStatusEnum> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<MessageReceivedStatusEnum> statusList) {
		this.statusList = statusList;
	}
	
	public MessageViewTypeEnum getViewType() {
		return this.viewType;
	}
	
	public void setViewType(MessageViewTypeEnum viewType) {
		this.viewType = viewType;
	}
	
	public String getMessageReceivedAccount() {
		return this.messageReceivedAccount;
	}
	
	public void setMessageReceivedAccount(String messageReceivedAccount) {
		this.messageReceivedAccount = messageReceivedAccount;
	}
	
	public String getMessageContent() {
		return this.messageContent;
	}
	
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryMessageOrder [messageReceivedId=");
		builder.append(messageReceivedId);
		builder.append(", messageReceivedName=");
		builder.append(messageReceivedName);
		builder.append(", messageReceivedAccount=");
		builder.append(messageReceivedAccount);
		builder.append(", messageId=");
		builder.append(messageId);
		builder.append(", messageSenderId=");
		builder.append(messageSenderId);
		builder.append(", viewType=");
		builder.append(viewType);
		builder.append(", beginReceivedDate=");
		builder.append(beginReceivedDate);
		builder.append(", endReceivedDate=");
		builder.append(endReceivedDate);
		builder.append(", messageType=");
		builder.append(messageType);
		builder.append(", statusList=");
		builder.append(statusList);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
