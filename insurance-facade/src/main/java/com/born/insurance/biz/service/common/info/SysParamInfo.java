package com.born.insurance.biz.service.common.info;

import java.io.Serializable;
import java.util.Date;

public class SysParamInfo implements Serializable {
	
	private static final long serialVersionUID = -1295836864172645577L;
	
	private String paramName;
	
	private String paramValue;
	
	private String extendAttributeOne;
	
	private String extendAttributeTwo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String description;
	
	public String getParamName() {
		return this.paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getParamValue() {
		return this.paramValue;
	}
	
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	public String getExtendAttributeOne() {
		return this.extendAttributeOne;
	}
	
	public void setExtendAttributeOne(String extendAttributeOne) {
		this.extendAttributeOne = extendAttributeOne;
	}
	
	public String getExtendAttributeTwo() {
		return this.extendAttributeTwo;
	}
	
	public void setExtendAttributeTwo(String extendAttributeTwo) {
		this.extendAttributeTwo = extendAttributeTwo;
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
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysParamInfo [paramName=");
		builder.append(paramName);
		builder.append(", paramValue=");
		builder.append(paramValue);
		builder.append(", extendAttributeOne=");
		builder.append(extendAttributeOne);
		builder.append(", extendAttributeTwo=");
		builder.append(extendAttributeTwo);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
}
