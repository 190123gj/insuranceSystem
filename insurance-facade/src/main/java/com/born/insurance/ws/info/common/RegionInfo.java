package com.born.insurance.ws.info.common;

import java.util.Date;

import com.born.insurance.ws.enums.BooleanEnum;

public class RegionInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -8896517826732824978L;
	
	private int id;
	
	private String code;
	
	private String name;
	
	private String parentCode;
	
	private BooleanEnum hasChildren;
	
	private int level;
	
	private int sortOrder;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getParentCode() {
		return this.parentCode;
	}
	
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public BooleanEnum getHasChildren() {
		return this.hasChildren;
	}
	
	public void setHasChildren(BooleanEnum hasChildren) {
		this.hasChildren = hasChildren;
	}
	
}
