package com.born.insurance.ws.info.insuranceCatalog;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.BaseToStringInfo;

public class InsuranceCatalogRelationInfo extends BaseToStringInfo {
	private long relationId;
	
	private String parentName;
	
	private long parentId;
	
	private String childName;
	
	private long childId;
	
	private String type;
	
	public long getChildId() {
		return childId;
	}
	
	public void setChildId(long childId) {
		this.childId = childId;
	}
	
	public String getChildName() {
		return childName;
	}
	
	public void setChildName(String childName) {
		this.childName = childName;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public long getRelationId() {
		return relationId;
	}
	
	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}