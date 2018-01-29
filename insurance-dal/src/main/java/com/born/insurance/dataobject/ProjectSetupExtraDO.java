package com.born.insurance.dataobject;

import java.util.Date;

public class ProjectSetupExtraDO extends SimpleFormDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8851261108479211273L;
	
	private long projectSetupId;

	private String projectSetupName;

	private long customerUserId;

	private String customerUserName;

	private long insuranceCatalogId;

	private long channelsUserId;

	private String channelsUserName;

	private double proportion;

	private String proportionType;

	private long setupUserId;

	private String setupUserName;

	private long setupUseType;

	private String setupUseName;

	private long setupUseId;

	private Date beginDate;

	private Date endDate;

	private String remark;

	private String status;
	
	private long creatorId;

	private String creatorName;

	private Date rawAddTime;

	private Date rawUpdateTime;

	public long getProjectSetupId() {
		return projectSetupId;
	}

	public void setProjectSetupId(long projectSetupId) {
		this.projectSetupId = projectSetupId;
	}

	public String getProjectSetupName() {
		return projectSetupName;
	}

	public void setProjectSetupName(String projectSetupName) {
		this.projectSetupName = projectSetupName;
	}

	public long getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
		this.customerUserId = customerUserId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public long getInsuranceCatalogId() {
		return insuranceCatalogId;
	}

	public void setInsuranceCatalogId(long insuranceCatalogId) {
		this.insuranceCatalogId = insuranceCatalogId;
	}

	public long getChannelsUserId() {
		return channelsUserId;
	}

	public void setChannelsUserId(long channelsUserId) {
		this.channelsUserId = channelsUserId;
	}

	public String getChannelsUserName() {
		return channelsUserName;
	}

	public void setChannelsUserName(String channelsUserName) {
		this.channelsUserName = channelsUserName;
	}

	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}

	public String getProportionType() {
		return proportionType;
	}

	public void setProportionType(String proportionType) {
		this.proportionType = proportionType;
	}

	public long getSetupUserId() {
		return setupUserId;
	}

	public void setSetupUserId(long setupUserId) {
		this.setupUserId = setupUserId;
	}

	public String getSetupUserName() {
		return setupUserName;
	}

	public void setSetupUserName(String setupUserName) {
		this.setupUserName = setupUserName;
	}

	public long getSetupUseType() {
		return setupUseType;
	}

	public void setSetupUseType(long setupUseType) {
		this.setupUseType = setupUseType;
	}

	public String getSetupUseName() {
		return setupUseName;
	}

	public void setSetupUseName(String setupUseName) {
		this.setupUseName = setupUseName;
	}

	public long getSetupUseId() {
		return setupUseId;
	}

	public void setSetupUseId(long setupUseId) {
		this.setupUseId = setupUseId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	

}
