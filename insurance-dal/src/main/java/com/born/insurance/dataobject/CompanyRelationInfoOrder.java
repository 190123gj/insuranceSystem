package com.born.insurance.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;
import com.born.insurance.ws.enums.CustomerTypeEnum;

public class CompanyRelationInfoOrder extends QueryPermissionPageBase implements Serializable {
	
	private static final long serialVersionUID = 196751580959047839L;
	
	private long userId;
	
	private long parentId;
	
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

	private long creatorId;

	private String creatorName;

	private String customerType;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	private long businessUserId;
	
	private String businessUserName;

	

	

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



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	

	public long getParentId() {
		return parentId;
	}



	public void setParentId(long parentId) {
		this.parentId = parentId;
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
