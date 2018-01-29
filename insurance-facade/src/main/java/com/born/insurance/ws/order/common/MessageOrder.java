package com.born.insurance.ws.order.common;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.MessageTypeEnum;
import com.born.insurance.ws.enums.MessageViewTypeEnum;
import com.born.insurance.ws.enums.NotificationTypeEnum;
import com.born.insurance.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.StringUtil;

public class MessageOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 7894407211660353363L;
	
	public static MessageOrder newSystemMessageOrder() {
		MessageOrder order = new MessageOrder();
		order.setIsSendMessage(BooleanEnum.YES);
		order.setMessageSenderId(-1);
		order.setMessageSenderAccount("system");
		order.setMessageSenderName("系统消息");
		order.setMessageTitle("系统通知");
		order.setMessageSubject("系统通知");
		order.setMessageType(MessageTypeEnum.SYSTEM);
		return order;
	}
	
	/**
	 * 消息id 修改不能为空
	 */
	private long messageId;
	/**
	 * 消息标题
	 */
	private String messageTitle;
	/**
	 * 消息类型
	 */
	private MessageTypeEnum messageType = MessageTypeEnum.DEFAULT;
	
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
	private MessageViewTypeEnum viewType = MessageViewTypeEnum.LIST;
	
	/**
	 * 消息链接
	 */
	private String linkUrl;
	/**
	 * 通知对象类型
	 */
	private NotificationTypeEnum notificationType = NotificationTypeEnum.APPOINT;
	
	private Date messagePlanSendDate;
	
	private Date messageSendDate;
	
	private long messageSenderId;
	
	private String messageSenderName;
	
	private String messageSenderAccount;
	
	private BooleanEnum isSendMessage = BooleanEnum.YES;
	
	private String[] sendUserAcounts;
	
	private SimpleUserInfo[] sendUsers;
	
	//是否加上收件人的名字
	private boolean withSenderName;
	/**
	 * 消息接收角色名
	 */
	private String messageSenderRole;
	
	@Override
	public void check() {
		validateHasText(messageTitle, "标题不能为空");
		validateNotNull(messageType, "消息类型");
		validateHasText(messageContent, "消息内容不能为空");
		validateNotNull(viewType, "显示方式");
		validateNotNull(notificationType, "通知对象");
		validateHasText(messageSenderName, "消息发送人");
		if (notificationType == NotificationTypeEnum.APPOINT) {
			Assert.isTrue(
				(sendUserAcounts != null && sendUserAcounts.length > 0)
						|| (sendUsers != null && sendUsers.length > 0)
						|| StringUtil.isNotBlank(messageSenderRole), "指定通知人不能为空");
		}
	}
	
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
	
	public Date getMessageSendDate() {
		return this.messageSendDate;
	}
	
	public void setMessageSendDate(Date messageSendDate) {
		this.messageSendDate = messageSendDate;
	}
	
	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
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
	
	public BooleanEnum getIsSendMessage() {
		return this.isSendMessage;
	}
	
	public void setIsSendMessage(BooleanEnum isSendMessage) {
		this.isSendMessage = isSendMessage;
	}
	
	public String getLinkUrl() {
		return this.linkUrl;
	}
	
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public String getMessageSenderAccount() {
		return this.messageSenderAccount;
	}
	
	public void setMessageSenderAccount(String messageSenderAccount) {
		this.messageSenderAccount = messageSenderAccount;
	}
	
	public String[] getSendUserAcounts() {
		return this.sendUserAcounts;
	}
	
	public void setSendUserAcounts(String[] sendUserAcounts) {
		this.sendUserAcounts = sendUserAcounts;
	}
	
	public SimpleUserInfo[] getSendUsers() {
		return this.sendUsers;
	}
	
	public void setSendUsers(SimpleUserInfo[] sendUsers) {
		this.sendUsers = sendUsers;
	}
	
	public boolean isWithSenderName() {
		return this.withSenderName;
	}
	
	public void setWithSenderName(boolean withSenderName) {
		this.withSenderName = withSenderName;
	}
	
	public String getMessageSenderRole() {
		return messageSenderRole;
	}
	
	public void setMessageSenderRole(String messageSenderRole) {
		this.messageSenderRole = messageSenderRole;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageOrder [messageId=");
		builder.append(messageId);
		builder.append(", messageTitle=");
		builder.append(messageTitle);
		builder.append(", messageType=");
		builder.append(messageType);
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
		builder.append(", messagePlanSendDate=");
		builder.append(messagePlanSendDate);
		builder.append(", messageSendDate=");
		builder.append(messageSendDate);
		builder.append(", messageSenderId=");
		builder.append(messageSenderId);
		builder.append(", messageSenderName=");
		builder.append(messageSenderName);
		builder.append(", messageSenderAccount=");
		builder.append(messageSenderAccount);
		builder.append(", isSendMessage=");
		builder.append(isSendMessage);
		builder.append(", sendUserAcounts=");
		builder.append(sendUserAcounts);
		builder.append(", messageSenderRole=");
		builder.append(messageSenderRole);
		builder.append("]");
		return builder.toString();
	}
	
}
