package com.born.insurance.ws.order.common;

import java.util.Date;
import java.util.List;

import javax.print.DocFlavor.STRING;

import com.born.insurance.ws.enums.RemindTypeEnum;
import com.born.insurance.ws.info.common.MessageTemplateWayInfo;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.base.ValidateOrderBase;
import com.yjf.common.service.Order;

public class MessageTemplateOrder extends MessageTemplateWayOrder {
	
	private static final long serialVersionUID = 7894407211660353363L;
	
	private long id;

	private String notifyName;

	private RemindTypeEnum notifyType;

	private long endDay;

	private String notifyContent;

	private long creatorId;

	private String creatorName;

	private String type;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	private List<MessageTemplateWayOrder> wayOrders;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotifyName() {
		return notifyName;
	}

	public void setNotifyName(String notifyName) {
		this.notifyName = notifyName;
	}

	public RemindTypeEnum getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(RemindTypeEnum notifyType) {
		this.notifyType = notifyType;
	}

	public long getEndDay() {
		return endDay;
	}

	public void setEndDay(long endDay) {
		this.endDay = endDay;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public List<MessageTemplateWayOrder> getWayOrders() {
		return wayOrders;
	}

	public void setWayOrders(List<MessageTemplateWayOrder> wayOrders) {
		this.wayOrders = wayOrders;
	}


	
	
	
}
