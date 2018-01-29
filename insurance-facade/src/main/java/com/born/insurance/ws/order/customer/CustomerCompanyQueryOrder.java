
package com.born.insurance.ws.order.customer;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;




public class CustomerCompanyQueryOrder extends CustomerCompanyDetailQueryOrder{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long userId;

	private String contactMobile;

	private CertTypeEnum certType;

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

	private CustomerTypeEnum customerType;

	private String companyNature;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	private String businessUserId;
	
	private String businessUserName;
	
	private String isThirdParty;

	private String abbr;
	
	private List<ValueTaxInfoOrder> taxOrder;
	
	private List<CustomerBankInfoOrder> bankOrder;

	private  long parentId;
	private String firstLevel;

	public String getBusinessUserId() {
		return businessUserId;
	}

	public List<ValueTaxInfoOrder> getTaxOrder() {
		return taxOrder;
	}

	public void setTaxOrder(List<ValueTaxInfoOrder> taxOrder) {
		this.taxOrder = taxOrder;
	}

	public List<CustomerBankInfoOrder> getBankOrder() {
		return bankOrder;
	}

	public void setBankOrder(List<CustomerBankInfoOrder> bankOrder) {
		this.bankOrder = bankOrder;
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

	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}

	public String getIsThirdParty() {
		return isThirdParty;
	}

	public void setIsThirdParty(String isThirdParty) {
		this.isThirdParty = isThirdParty;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getFirstLevel() {
		return firstLevel;
	}

	public void setFirstLevel(String firstLevel) {
		this.firstLevel = firstLevel;
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
