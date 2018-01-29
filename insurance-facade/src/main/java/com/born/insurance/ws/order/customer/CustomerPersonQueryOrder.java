/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.order.customer;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class CustomerPersonQueryOrder extends CustomerPersonDetailQueryOrder{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========
    
    private long userId;
    
    private String userIds;
    
    private String companys;

	private String removeCompanys;

	private String contactMobile;

	private String certType;
	
	private String customerName; 

	private String certNo;

	private Date certExpDate;

	private String countryCode;

	private String countryName;

	private String provinceCode;

	private String provinceName;

	private String cityCode;

	private String cityName;

	private String countyCode;

	private String countyName;

	private String address;

	private String email;

	private String mobile;

	private String telphone;

	private String fix;

	private String weixin;

	private String qq;

	private String status;

	private long creatorId;

	private String creatorName;
	
	private String customerType;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	private long relationId;

	private String parentName;

	private long parentId;

	private String childName;

	private long childId;

	private String memberNo;

	private long typeId;

	private String typeName;

	private String businessUserId;

	private String businessUserName;
	
	private String companyCustomer;
	

    //========== getters and setters ==========
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getContactMobile() {
		return contactMobile;
	}
	
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getCertType() {
		return certType;
	}
	
	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public Date getCertExpDate() {
		return certExpDate;
	}
	
	public void setCertExpDate(Date certExpDate) {
		this.certExpDate = certExpDate;
	}

	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyCode() {
		return countyCode;
	}
	
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}
	
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelphone() {
		return telphone;
	}
	
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getFix() {
		return fix;
	}
	
	public void setFix(String fix) {
		this.fix = fix;
	}

	public String getWeixin() {
		return weixin;
	}
	
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getQq() {
		return qq;
	}
	
	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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

	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

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

	public String getBusinessUserId() {
		return businessUserId;
	}

	public void setBusinessUserId(String businessUserId) {
		this.businessUserId = businessUserId;
	}

	public String getBusinessUserName() {
		return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}
	
	public String getCompanyCustomer() {
		return companyCustomer;
	}

	public void setCompanyCustomer(String companyCustomer) {
		this.companyCustomer = companyCustomer;
	}
	
	public String getCompanys() {
		return companys;
	}

	public void setCompanys(String companys) {
		this.companys = companys;
	}
	
	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getRemoveCompanys() {
		return removeCompanys;
	}

	public void setRemoveCompanys(String removeCompanys) {
		this.removeCompanys = removeCompanys;
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
