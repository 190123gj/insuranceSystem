package com.born.insurance.ws.order.common;

import java.util.Date;

import com.born.insurance.ws.order.base.ValidateOrderBase;

/**
 * 子系统对接项目 order
 *
 * @author Ji
 * 
 * 2016年4月29日
 */
public class SubsystemDockProjectOrder extends ValidateOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1781343172681014309L;
	
	private Long id;
	
	private String projectCode;
	
	private String dockFormType;
	
	private Date rawAddTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getDockFormType() {
		return dockFormType;
	}
	
	public void setDockFormType(String dockFormType) {
		this.dockFormType = dockFormType;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
