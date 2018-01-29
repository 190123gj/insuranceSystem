/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.info.customer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.SexEnum;

// auto generated imports


public class CustomerBaseInfo implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long userId;

	private String customerId;

	private String customerName;

	private String contactMobile;

	private CertTypeEnum certType;

	private String certNo;
	
	private String sex;
	
	private String birth;
	
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

	private CustomerTypeEnum customerType;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	private String businessUserId;
	
	private String businessUserName;

	private String remark;

	private List<CustomerContactWayInfo> contactMobileInfos;

	private List<CustomerContactWayInfo> contactEmailInfos;

	private List<CustomerContactWayInfo> contactQQInfos;

	private List<CustomerContactWayInfo> contactWeiXinInfos;

	private List<CustomerContactWayInfo> corpInfos;

	private List<CustomerBankInfo> bankInfos;

	private List<CustomerRelationInfo> relationInfos;

	private List<CustomerCertInfo> certInfos;

	private List<CustomerHisBusinessBillInfo> billInfos;

	private List<CustomerContactWayInfo> addressInfos;

	private String abbr1;

	private String abbr2;

	private String abbr3;


	private long ownerId;

	private String ownerUserName;

	private String outUser;

	private String refereeId;

	private String refereeName;


    //========== getters and setters ==========

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

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactMobile() {
		return contactMobile;
	}
	
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}



	public CertTypeEnum getCertType() {
		return certType;
	}
	
	public void setCertType(CertTypeEnum certType) {
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

	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
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


	public List<CustomerContactWayInfo> getContactMobileInfos() {
		return contactMobileInfos;
	}

	public void setContactMobileInfos(List<CustomerContactWayInfo> contactMobileInfos) {
		this.contactMobileInfos = contactMobileInfos;
	}

	public List<CustomerContactWayInfo> getContactEmailInfos() {
		return contactEmailInfos;
	}

	public void setContactEmailInfos(List<CustomerContactWayInfo> contactEmailInfos) {
		this.contactEmailInfos = contactEmailInfos;
	}


	public List<CustomerBankInfo> getBankInfos() {
		return bankInfos;
	}

	public void setBankInfos(List<CustomerBankInfo> bankInfos) {
		this.bankInfos = bankInfos;
	}

	public List<CustomerRelationInfo> getRelationInfos() {
		return relationInfos;
	}

	public void setRelationInfos(List<CustomerRelationInfo> relationInfos) {
		this.relationInfos = relationInfos;
	}

	public List<CustomerCertInfo> getCertInfos() {
		return certInfos;
	}

	public void setCertInfos(List<CustomerCertInfo> certInfos) {
		this.certInfos = certInfos;
	}

	public String getAbbr1() {
		return abbr1;
	}

	public void setAbbr1(String abbr1) {
		this.abbr1 = abbr1;
	}

	public String getAbbr2() {
		return abbr2;
	}

	public void setAbbr2(String abbr2) {
		this.abbr2 = abbr2;
	}

	public String getAbbr3() {
		return abbr3;
	}

	public void setAbbr3(String abbr3) {
		this.abbr3 = abbr3;
	}
	

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getOutUser() {
		return outUser;
	}

	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<CustomerContactWayInfo> getContactQQInfos() {
		return contactQQInfos;
	}

	public void setContactQQInfos(List<CustomerContactWayInfo> contactQQInfos) {
		this.contactQQInfos = contactQQInfos;
	}

	public List<CustomerContactWayInfo> getContactWeiXinInfos() {
		return contactWeiXinInfos;
	}

	public void setContactWeiXinInfos(List<CustomerContactWayInfo> contactWeiXinInfos) {
		this.contactWeiXinInfos = contactWeiXinInfos;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CustomerHisBusinessBillInfo> getBillInfos() {
		return billInfos;
	}

	public void setBillInfos(List<CustomerHisBusinessBillInfo> billInfos) {
		this.billInfos = billInfos;
	}

	public List<CustomerContactWayInfo> getAddressInfos() {
		return addressInfos;
	}

	public void setAddressInfos(List<CustomerContactWayInfo> addressInfos) {
		this.addressInfos = addressInfos;
	}

	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}

	public String getRefereeName() {
		return refereeName;
	}

	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}

	public List<CustomerContactWayInfo> getCorpInfos() {
		return corpInfos;
	}

	public void setCorpInfos(List<CustomerContactWayInfo> corpInfos) {
		this.corpInfos = corpInfos;
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
