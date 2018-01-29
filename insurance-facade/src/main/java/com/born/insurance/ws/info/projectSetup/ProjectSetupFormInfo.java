package com.born.insurance.ws.info.projectSetup;

import java.util.Date;

import com.born.insurance.ws.info.common.FormVOInfo;

public class ProjectSetupFormInfo extends FormVOInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3351095202638882434L;
	
	/**
	 * 项目id
	 */
	private long projectSetupId;
	
	/**
	 * 项目名称
	 */
	private String projectSetupName;
	
	/**
	 * 状态
	 */
	private String status;
	
 	/**
	* 有效期至
	*/	
	private Date endDate;
 	
	/**
	* 生效时间
	*/	
	private Date beginDate;
	
 	/**
	* 创建时间
	*/	
	private Date rawAddTime;
	
	/**
	 * 更新时间
	 */
	private Date rawUpdateTime;

	public String getProjectSetupName() {
		return projectSetupName;
	}

	public void setProjectSetupName(String projectSetupName) {
		this.projectSetupName = projectSetupName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public long getProjectSetupId() {
		return projectSetupId;
	}

	public void setProjectSetupId(long projectSetupId) {
		this.projectSetupId = projectSetupId;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
 	
}

