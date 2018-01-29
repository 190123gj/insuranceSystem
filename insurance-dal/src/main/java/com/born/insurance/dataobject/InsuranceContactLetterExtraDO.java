/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.dataobject;

// auto generated imports
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author jenny
 *
 */
public class InsuranceContactLetterExtraDO extends SimpleFormDO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3700973498530971596L;

	private long letterId;

	private long customerUserId;

	private String customerUserName;

	private long companyUserId;

	private String companyUserName;
	
	private String customerUserPhone;

	private long priceContactId;

	private String priceContactName;

	private String priceContactNo;
	
	private String isQuota;
	
	private String insuranceType;

	private String project;
	
	private long productId;
	
	private String productName;

	private Date beginDate;

	private Date endDate;
	
	private String status;
	
	private String letterStatus;

	private long formId;

	private String isInvoice;

	private String remark;

	private String recipients;

	private String company;

	private String address;

	private String mobile;

	private String type;

	private long creatorId;

	private String creatorName;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getLetterId() {
		return letterId;
	}
	
	public void setLetterId(long letterId) {
		this.letterId = letterId;
	}

	public long getCustomerUserId() {
		return customerUserId;
	}
	
	public void setCustomerUserId(long customerUserId) {
		this.customerUserId = customerUserId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}
	
	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public long getCompanyUserId() {
		return companyUserId;
	}
	
	public void setCompanyUserId(long companyUserId) {
		this.companyUserId = companyUserId;
	}

	public String getCompanyUserName() {
		return companyUserName;
	}
	
	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public long getPriceContactId() {
		return priceContactId;
	}
	
	public void setPriceContactId(long priceContactId) {
		this.priceContactId = priceContactId;
	}

	public String getPriceContactName() {
		return priceContactName;
	}
	
	public void setPriceContactName(String priceContactName) {
		this.priceContactName = priceContactName;
	}

	public String getPriceContactNo() {
		return priceContactNo;
	}
	
	public void setPriceContactNo(String priceContactNo) {
		this.priceContactNo = priceContactNo;
	}

	public String getProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getIsInvoice() {
		return isInvoice;
	}
	
	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRecipients() {
		return recipients;
	}
	
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public long getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getCustomerUserPhone() {
		return customerUserPhone;
	}

	public void setCustomerUserPhone(String customerUserPhone) {
		this.customerUserPhone = customerUserPhone;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getLetterStatus() {
		return letterStatus;
	}

	public void setLetterStatus(String letterStatus) {
		this.letterStatus = letterStatus;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
