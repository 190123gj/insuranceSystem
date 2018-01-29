package com.born.insurance.ws.order.common;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.Assert;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.MessageTypeEnum;
import com.born.insurance.ws.enums.MessageViewTypeEnum;
import com.born.insurance.ws.enums.NotificationTypeEnum;
import com.born.insurance.ws.enums.RemindTypeEnum;
import com.born.insurance.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.StringUtil;

public class MessageTemplateWayQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 7894407211660353363L;
	
	private long id;

	private long messageTemplateId;

	private String notifyWay;

	private String notifyObject;
	
	private long hour;

	private long minute;

    //========== getters and setters ==========	

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMessageTemplateId() {
		return messageTemplateId;
	}
	
	public void setMessageTemplateId(long messageTemplateId) {
		this.messageTemplateId = messageTemplateId;
	}

	public String getNotifyWay() {
		return notifyWay;
	}
	
	public void setNotifyWay(String notifyWay) {
		this.notifyWay = notifyWay;
	}

	public String getNotifyObject() {
		return notifyObject;
	}
	
	public void setNotifyObject(String notifyObject) {
		this.notifyObject = notifyObject;
	}

	public long getHour() {
		return hour;
	}
	
	public void setHour(long hour) {
		this.hour = hour;
	}

	public long getMinute() {
		return minute;
	}
	
	public void setMinute(long minute) {
		this.minute = minute;
	}



	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
}
