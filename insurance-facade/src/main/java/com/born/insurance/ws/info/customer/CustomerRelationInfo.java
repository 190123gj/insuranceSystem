/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.info.customer;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class CustomerRelationInfo extends  CustomerBaseInfo {
	
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long relationId;
	
	private String parentName;
	
	private long parentId;
	
	private String childName;
	
	private long childId;
	
	private String memberNo;
	
	private long typeId;
	
	private String typeName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getRelationId() {
		return relationId;
	}
	
	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public String getChildName() {
		return childName;
	}
	
	public void setChildName(String childName) {
		this.childName = childName;
	}
	
	public long getChildId() {
		return childId;
	}
	
	public void setChildId(long childId) {
		this.childId = childId;
	}
	
	public String getMemberNo() {
		return memberNo;
	}
	
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	
	/**
	 * @return
	 *
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
