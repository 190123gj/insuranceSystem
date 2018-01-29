package com.born.insurance.ws.info.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.RemindContactTypeEnum;

public class MessageTemplateWayInfo extends BaseToStringInfo {
	
	private long id;

	private long messageTemplateId;
	
	private long sortOrder;

	private RemindContactTypeEnum notifyWay;

	private String notifyObject;

	private long hour;

	private long minute;

    //========== getters and setters ==========	
	
	
	
	

	public long getId() {
		return id;
	}
	
	public long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(long sortOrder) {
		this.sortOrder = sortOrder;
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

	public RemindContactTypeEnum getNotifyWay() {
		return notifyWay;
	}
	
	public void setNotifyWay(RemindContactTypeEnum notifyWay) {
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
