
package com.born.insurance.ws.order.customer;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;


public class CustomerBaseOrder extends ProcessOrder{

    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========


	private String customerId;

	private String customerName;

	private String contactMobile;

	private CertTypeEnum certType;

	private String certNo;

	private Date certExpDate;

	private String certPic;

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

	private CustomerTypeEnum customerType;

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

	private String abbr1;

	private String abbr2;

	private String abbr3;

	private String remark;

	private List<CustomerContactWayOrder> mobileOrders;

	private List<CustomerContactWayOrder> emailOrders;

	private List<CustomerContactWayOrder> weixinOrders;

	private List<CustomerContactWayOrder> qqOrders;

	private List<CustomerBankInfoOrder> bankOrders;

	private List<CustomerRelationOrder> relationOrders;

	private List<CustomerContactWayOrder> addressOrders;

	List<CustomerCertOrder> certOrders;

	List<CustomerHisBusinessBillOrder> billOrders;

	List<CustomerContactWayOrder> corpOrders;

	private long ownerId;

	private String ownerUserName;

	private String outUser;

	private String refereeId;

	private String refereeName;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CustomerBankInfoOrder> getBankOrders() {
		return bankOrders;
	}

	public void setBankOrders(List<CustomerBankInfoOrder> bankOrders) {
		this.bankOrders = bankOrders;
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

	public Date getCertExpDate() {
		return certExpDate;
	}

	public void setCertExpDate(Date certExpDate) {
		this.certExpDate = certExpDate;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public List<CustomerCertOrder> getCertOrders() {
		return certOrders;
	}

	public void setCertOrders(List<CustomerCertOrder> certOrders) {
		this.certOrders = certOrders;
	}

	public CertTypeEnum getCertType() {
		return certType;
	}

	public void setCertType(CertTypeEnum certType) {
		this.certType = certType;
	}

	public long getChildId() {
		return childId;
	}

	public void setChildId(long childId) {
		this.childId = childId;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
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

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
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

	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CustomerContactWayOrder> getEmailOrders() {
		return emailOrders;
	}

	public void setEmailOrders(List<CustomerContactWayOrder> emailOrders) {
		this.emailOrders = emailOrders;
	}

	public String getFix() {
		return fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<CustomerContactWayOrder> getMobileOrders() {
		return mobileOrders;
	}

	public void setMobileOrders(List<CustomerContactWayOrder> mobileOrders) {
		this.mobileOrders = mobileOrders;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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

	public long getRelationId() {
		return relationId;
	}

	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}

	public List<CustomerRelationOrder> getRelationOrders() {
		return relationOrders;
	}

	public void setRelationOrders(List<CustomerRelationOrder> relationOrders) {
		this.relationOrders = relationOrders;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
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

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
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

	public String getOutUser() {
		return outUser;
	}

	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}

	public String getCertPic() {
		return certPic;
	}

	public void setCertPic(String certPic) {
		this.certPic = certPic;
	}

	public List<CustomerContactWayOrder> getQqOrders() {
		return qqOrders;
	}

	public void setQqOrders(List<CustomerContactWayOrder> qqOrders) {
		this.qqOrders = qqOrders;
	}

	public List<CustomerContactWayOrder> getWeixinOrders() {
		return weixinOrders;
	}

	public void setWeixinOrders(List<CustomerContactWayOrder> weixinOrders) {
		this.weixinOrders = weixinOrders;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CustomerContactWayOrder> getAddressOrders() {
		return addressOrders;
	}

	public void setAddressOrders(List<CustomerContactWayOrder> addressOrders) {
		this.addressOrders = addressOrders;
	}

	public List<CustomerHisBusinessBillOrder> getBillOrders() {
		return billOrders;
	}

	public void setBillOrders(List<CustomerHisBusinessBillOrder> billOrders) {
		this.billOrders = billOrders;
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

	public List<CustomerContactWayOrder> getCorpOrders() {
		return corpOrders;
	}

	public void setCorpOrders(List<CustomerContactWayOrder> corpOrders) {
		this.corpOrders = corpOrders;
	}

	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
