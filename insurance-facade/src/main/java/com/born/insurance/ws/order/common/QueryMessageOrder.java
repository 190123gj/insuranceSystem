package com.born.insurance.ws.order.common;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.MessageStatusEnum;
import com.born.insurance.ws.enums.MessageTypeEnum;
import com.born.insurance.ws.enums.MessageViewTypeEnum;

public class QueryMessageOrder extends QueryPageBase {
	
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
	private Date beginMessageSendDate;
	private Date endMessageSendDate;
	private Date beginMessageCreateDate;
	private Date endMessageCreateDate;
	/**
	 * 消息类型
	 */
	private MessageTypeEnum messageType;
	/**
	 * 消息状态
	 */
	private List<MessageStatusEnum> statusList;
	
	/**
	 * 消息标题
	 * @return
	 */
	private String messageTitle;
	
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
	
	public Date getBeginMessageSendDate() {
		return this.beginMessageSendDate;
	}
	
	public void setBeginMessageSendDate(Date beginMessageSendDate) {
		this.beginMessageSendDate = beginMessageSendDate;
	}
	
	public Date getEndMessageSendDate() {
		return this.endMessageSendDate;
	}
	
	public void setEndMessageSendDate(Date endMessageSendDate) {
		this.endMessageSendDate = endMessageSendDate;
	}
	
	public Date getBeginMessageCreateDate() {
		return this.beginMessageCreateDate;
	}
	
	public void setBeginMessageCreateDate(Date beginMessageCreateDate) {
		this.beginMessageCreateDate = beginMessageCreateDate;
	}
	
	public Date getEndMessageCreateDate() {
		return this.endMessageCreateDate;
	}
	
	public void setEndMessageCreateDate(Date endMessageCreateDate) {
		this.endMessageCreateDate = endMessageCreateDate;
	}
	
	public MessageTypeEnum getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}
	
	public List<MessageStatusEnum> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<MessageStatusEnum> statusList) {
		this.statusList = statusList;
	}
	
	public MessageViewTypeEnum getViewType() {
		return this.viewType;
	}
	
	public void setViewType(MessageViewTypeEnum viewType) {
		this.viewType = viewType;
	}
	
	public String getMessageTitle() {
		return messageTitle;
	}
	
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("QueryMessageOrder{");
		sb.append("messageReceivedId=").append(messageReceivedId);
		sb.append(", messageReceivedName='").append(messageReceivedName).append('\'');
		sb.append(", messageId=").append(messageId);
		sb.append(", messageSenderId=").append(messageSenderId);
		sb.append(", viewType=").append(viewType);
		sb.append(", beginMessageSendDate=").append(beginMessageSendDate);
		sb.append(", endMessageSendDate=").append(endMessageSendDate);
		sb.append(", beginMessageCreateDate=").append(beginMessageCreateDate);
		sb.append(", endMessageCreateDate=").append(endMessageCreateDate);
		sb.append(", messageType=").append(messageType);
		sb.append(", statusList=").append(statusList);
		sb.append(", messageTitle='").append(messageTitle).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
