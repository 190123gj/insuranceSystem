package com.born.insurance.ws.info.common;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.RemindTypeEnum;

public class MessageTemplateInfo extends BaseToStringInfo{


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
	
	private Date startTime;
	
	private Date endTime;
	
	private List<MessageTemplateWayInfo> wayInfos;

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
	
	
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	

	public List<MessageTemplateWayInfo> getWayInfos() {
		return wayInfos;
	}

	public void setWayInfos(List<MessageTemplateWayInfo> wayInfos) {
		this.wayInfos = wayInfos;
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
