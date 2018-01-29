package com.born.insurance.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;
import com.born.insurance.ws.enums.CustomerTypeEnum;

public class CompanyInfoVo  implements Serializable {
	
	private static final long serialVersionUID = 196751580959047839L;
	
	private long userId;

	private String customerId;

	private String customerName;

	private String contactMobile;

	private String certType;

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

	private long businessUserId;

	private String businessUserName;

	private long creatorId;

	private String creatorName;

	private String customerType;

	private String enterpriseType;

	private long registerCapital;

	private long actualCapital;

	private Date establishedTime;

	private String scale;

	private long staffNum;

	private String contactMan;

	private String busiScope;

	private String websit;

	private String legalPersion;

	private String legalPersionCertNo;

	private String legalPersionAddress;

	private String actualControlMan;

	private String actualControlManCertNo;

	private String actualControlManCertType;

	private String actualControlManAddress;

	private Date rawAddTime;

	private Date rawUpdateTime;

	
	
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



	public long getBusinessUserId() {
		return businessUserId;
	}



	public void setBusinessUserId(long businessUserId) {
		this.businessUserId = businessUserId;
	}



	public String getBusinessUserName() {
		return businessUserName;
	}



	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
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



	public String getEnterpriseType() {
		return enterpriseType;
	}



	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}



	public long getRegisterCapital() {
		return registerCapital;
	}



	public void setRegisterCapital(long registerCapital) {
		this.registerCapital = registerCapital;
	}



	public long getActualCapital() {
		return actualCapital;
	}



	public void setActualCapital(long actualCapital) {
		this.actualCapital = actualCapital;
	}



	public Date getEstablishedTime() {
		return establishedTime;
	}



	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}



	public String getScale() {
		return scale;
	}



	public void setScale(String scale) {
		this.scale = scale;
	}



	public long getStaffNum() {
		return staffNum;
	}



	public void setStaffNum(long staffNum) {
		this.staffNum = staffNum;
	}



	public String getContactMan() {
		return contactMan;
	}



	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}



	public String getBusiScope() {
		return busiScope;
	}



	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}



	public String getWebsit() {
		return websit;
	}



	public void setWebsit(String websit) {
		this.websit = websit;
	}



	public String getLegalPersion() {
		return legalPersion;
	}



	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}



	public String getLegalPersionCertNo() {
		return legalPersionCertNo;
	}



	public void setLegalPersionCertNo(String legalPersionCertNo) {
		this.legalPersionCertNo = legalPersionCertNo;
	}



	public String getLegalPersionAddress() {
		return legalPersionAddress;
	}



	public void setLegalPersionAddress(String legalPersionAddress) {
		this.legalPersionAddress = legalPersionAddress;
	}



	public String getActualControlMan() {
		return actualControlMan;
	}



	public void setActualControlMan(String actualControlMan) {
		this.actualControlMan = actualControlMan;
	}



	public String getActualControlManCertNo() {
		return actualControlManCertNo;
	}



	public void setActualControlManCertNo(String actualControlManCertNo) {
		this.actualControlManCertNo = actualControlManCertNo;
	}



	public String getActualControlManCertType() {
		return actualControlManCertType;
	}



	public void setActualControlManCertType(String actualControlManCertType) {
		this.actualControlManCertType = actualControlManCertType;
	}



	public String getActualControlManAddress() {
		return actualControlManAddress;
	}



	public void setActualControlManAddress(String actualControlManAddress) {
		this.actualControlManAddress = actualControlManAddress;
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
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
