package com.born.insurance.ws.order.common;

import com.born.insurance.ws.base.QueryPageBase;

/**
 * 子系统对接项目信息 查询Order
 *
 * @author ji
 * 
 * 2016年8月27日
 */
public class SubsystemDockProjectQueryOrder extends QueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5238487344550789832L;
	
	private long id;
	
	private String projectCode;
	
	private String dockFormType;
	
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
	
	public String getDockFormType() {
		return dockFormType;
	}
	
	public void setDockFormType(String dockFormType) {
		this.dockFormType = dockFormType;
	}
	
}
