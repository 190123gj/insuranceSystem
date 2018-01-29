package com.born.insurance.ws.info.common;

import java.util.Date;

import com.born.insurance.ws.enums.SubsystemDockFormTypeEnum;

/**
 * 
 * @author Ji
 *
 * 子系统对接项目信息 info
 */

public class SubsystemDockProjectInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1564414022744137708L;
	
	private long id;
	
	private String projectCode;
	
	private SubsystemDockFormTypeEnum dockFormType;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public SubsystemDockFormTypeEnum getDockFormType() {
		return dockFormType;
	}
	
	public void setDockFormType(SubsystemDockFormTypeEnum dockFormType) {
		this.dockFormType = dockFormType;
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
