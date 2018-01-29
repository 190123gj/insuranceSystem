package com.born.insurance.ws.info.common;

import java.util.Date;

public class IndustryInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1448418612555150519L;
	
	private int id;
	
	private String type;
	
	private String typeBig;
	
	private String typeMedium;
	
	private String typeSmall;
	
	private int level;
	
	private String parentCode;
	
	private String code;
	
	private String name;
	
	private String remark;
	
	private String reviewTemplate;
	
	private String fullName;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTypeBig() {
		return this.typeBig;
	}
	
	public void setTypeBig(String typeBig) {
		this.typeBig = typeBig;
	}
	
	public String getTypeMedium() {
		return this.typeMedium;
	}
	
	public void setTypeMedium(String typeMedium) {
		this.typeMedium = typeMedium;
	}
	
	public String getTypeSmall() {
		return this.typeSmall;
	}
	
	public void setTypeSmall(String typeSmall) {
		this.typeSmall = typeSmall;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getParentCode() {
		return this.parentCode;
	}
	
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getReviewTemplate() {
		return this.reviewTemplate;
	}
	
	public void setReviewTemplate(String reviewTemplate) {
		this.reviewTemplate = reviewTemplate;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
