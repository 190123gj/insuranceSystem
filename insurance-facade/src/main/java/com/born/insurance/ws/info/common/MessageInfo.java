package com.born.insurance.ws.info.common;

import java.io.Serializable;
import java.util.Date;

import com.born.insurance.ws.enums.MessageReceivedStatusEnum;
import com.born.insurance.ws.enums.MessageStatusEnum;
import com.born.insurance.ws.enums.MessageTypeEnum;
import com.born.insurance.ws.enums.MessageViewTypeEnum;
import com.born.insurance.ws.enums.NotificationTypeEnum;

public class MessageInfo implements Serializable {
	
	private static final long serialVersionUID = -6950679207346988675L;
	/**
	 * 消息id
	 */
	private long messageId;
	/**
	 * 消息标题
	 */
	private String messageTitle;
	/**
	 * 消息类型
	 */
	private MessageTypeEnum messageType;
	/**
	 * 消息状态
	 */
	private MessageStatusEnum messageStatus;
	/**
	 * 消息接收状态
	 */
	private MessageReceivedStatusEnum messageReceivedStatus;
	/**
	 * 消息主题
	 */
	private String messageSubject;
	/**
	 * 消息内容
	 */
	private String messageContent;
	/**
	 * 通知对象描述
	 */
	private String notificationObject;
	/**
	 * 显示方式
	 */
	private MessageViewTypeEnum viewType;
	
	/**
	 * 消息链接
	 */
	private String linkUrl;
	/**
	 * 通知对象类型
	 */
	private NotificationTypeEnum notificationType;
	/**
	 * 发送时间
	 */
	private Date messageSendDate;
	/**
	 * 计划发送时间
	 */
	private Date messagePlanSendDate;
	/**
	 * 发送人
	 */
	private long messageSenderId;
	/**
	 * 发送人名称
	 */
	private String messageSenderName;
	
	/**
	 * 发送人账号
	 */
	private String messageSenderAccount;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getMessageId() {
		return this.messageId;
	}
	
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	public String getMessageTitle() {
		return this.messageTitle;
	}
	
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	public MessageTypeEnum getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}
	
	public MessageStatusEnum getMessageStatus() {
		return this.messageStatus;
	}
	
	public void setMessageStatus(MessageStatusEnum messageStatus) {
		this.messageStatus = messageStatus;
	}
	
	public String getMessageSubject() {
		return this.messageSubject;
	}
	
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}
	
	public String getMessageContent() {
		return this.messageContent;
	}
	
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public String getNotificationObject() {
		return this.notificationObject;
	}
	
	public void setNotificationObject(String notificationObject) {
		this.notificationObject = notificationObject;
	}
	
	public MessageViewTypeEnum getViewType() {
		return this.viewType;
	}
	
	public void setViewType(MessageViewTypeEnum viewType) {
		this.viewType = viewType;
	}
	
	public NotificationTypeEnum getNotificationType() {
		return this.notificationType;
	}
	
	public void setNotificationType(NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
	}
	
	public Date getMessageSendDate() {
		return this.messageSendDate;
	}
	
	public void setMessageSendDate(Date messageSendDate) {
		this.messageSendDate = messageSendDate;
	}
	
	public Date getMessagePlanSendDate() {
		return this.messagePlanSendDate;
	}
	
	public void setMessagePlanSendDate(Date messagePlanSendDate) {
		this.messagePlanSendDate = messagePlanSendDate;
	}
	
	public long getMessageSenderId() {
		return this.messageSenderId;
	}
	
	public void setMessageSenderId(long messageSenderId) {
		this.messageSenderId = messageSenderId;
	}
	
	public String getMessageSenderName() {
		return this.messageSenderName;
	}
	
	public void setMessageSenderName(String messageSenderName) {
		this.messageSenderName = messageSenderName;
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
	
	public String getLinkUrl() {
		return this.linkUrl;
	}
	
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public MessageReceivedStatusEnum getMessageReceivedStatus() {
		return this.messageReceivedStatus;
	}
	
	public void setMessageReceivedStatus(MessageReceivedStatusEnum messageReceivedStatus) {
		this.messageReceivedStatus = messageReceivedStatus;
	}
	
	public String getMessageSenderAccount() {
		return this.messageSenderAccount;
	}
	
	public void setMessageSenderAccount(String messageSenderAccount) {
		this.messageSenderAccount = messageSenderAccount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageInfo [messageId=");
		builder.append(messageId);
		builder.append(", messageTitle=");
		builder.append(messageTitle);
		builder.append(", messageType=");
		builder.append(messageType);
		builder.append(", messageStatus=");
		builder.append(messageStatus);
		builder.append(", messageReceivedStatus=");
		builder.append(messageReceivedStatus);
		builder.append(", messageSubject=");
		builder.append(messageSubject);
		builder.append(", messageContent=");
		builder.append(messageContent);
		builder.append(", notificationObject=");
		builder.append(notificationObject);
		builder.append(", viewType=");
		builder.append(viewType);
		builder.append(", linkUrl=");
		builder.append(linkUrl);
		builder.append(", notificationType=");
		builder.append(notificationType);
		builder.append(", messageSendDate=");
		builder.append(messageSendDate);
		builder.append(", messagePlanSendDate=");
		builder.append(messagePlanSendDate);
		builder.append(", messageSenderId=");
		builder.append(messageSenderId);
		builder.append(", messageSenderName=");
		builder.append(messageSenderName);
		builder.append(", messageSenderAccount=");
		builder.append(messageSenderAccount);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append("]");
		return builder.toString();
	}
	
}
