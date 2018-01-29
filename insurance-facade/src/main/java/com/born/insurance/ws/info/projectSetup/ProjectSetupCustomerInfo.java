package com.born.insurance.ws.info.projectSetup;

import com.born.insurance.ws.info.common.BaseToStringInfo;

public class ProjectSetupCustomerInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033075088541321105L;

	private long id;

	private long projectSetupId;

	private long customerId;

	private String customerName;

	private String certNo;

	private String certType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectSetupId() {
		return projectSetupId;
	}

	public void setProjectSetupId(long projectSetupId) {
		this.projectSetupId = projectSetupId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}
	
	

}
