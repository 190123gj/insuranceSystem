
package com.born.insurance.ws.order.customer;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class CustomerCompanyOrder extends CustomerBaseOrder {
	
	private static final long serialVersionUID = -4282603875229233564L;
	
	private String customerId;
	
	private String customerName;
	
	private String enterpriseType;
	
	private long registerCapital;
	
	private long actualCapital;
	
	private Date establishedTime;
	
	private String scale;
	
	private long staffNum;
	
	private String contactMan;

	private long contactManId;

	private String contactEmail;
	
	private String busiScope;
	
	private String websit;
	
	private String legalPersion;
	
	private String legalPersionCertNo;
	
	private String legalPersionAddress;
	
	private String actualControlMan;
	
	private String actualControlManCertNo;
	
	private String actualControlManCertType;
	
	private String actualControlManAddress;
	

	private long parentId;

	private String parentName;

	private String kind;

	private String companyType;

	private String companyNature;
	
	private List<ValueTaxInfoOrder> taxOrders;
	
	public long getActualCapital() {
		return actualCapital;
	}
	
	public void setActualCapital(long actualCapital) {
		this.actualCapital = actualCapital;
	}
	
	public String getActualControlMan() {
		return actualControlMan;
	}
	
	public void setActualControlMan(String actualControlMan) {
		this.actualControlMan = actualControlMan;
	}
	
	public String getActualControlManAddress() {
		return actualControlManAddress;
	}
	
	public void setActualControlManAddress(String actualControlManAddress) {
		this.actualControlManAddress = actualControlManAddress;
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
	
	public String getBusiScope() {
		return busiScope;
	}
	
	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}
	
	public String getContactMan() {
		return contactMan;
	}
	
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	
	@Override
	public String getCustomerId() {
		return customerId;
	}
	
	@Override
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public String getCustomerName() {
		return customerName;
	}
	
	@Override
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public Date getEstablishedTime() {
		return establishedTime;
	}
	
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	

	public String getLegalPersion() {
		return legalPersion;
	}
	
	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}
	
	public String getLegalPersionAddress() {
		return legalPersionAddress;
	}
	
	public void setLegalPersionAddress(String legalPersionAddress) {
		this.legalPersionAddress = legalPersionAddress;
	}
	
	public String getLegalPersionCertNo() {
		return legalPersionCertNo;
	}
	
	public void setLegalPersionCertNo(String legalPersionCertNo) {
		this.legalPersionCertNo = legalPersionCertNo;
	}
	
	public long getRegisterCapital() {
		return registerCapital;
	}
	
	public void setRegisterCapital(long registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	public String getScale() {
		return scale;
	}
	
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public long getStaffNum() {
		return staffNum;
	}
	
	public void setStaffNum(long staffNum) {
		this.staffNum = staffNum;
	}
	
	public List<ValueTaxInfoOrder> getTaxOrders() {
		return taxOrders;
	}
	
	public void setTaxOrders(List<ValueTaxInfoOrder> taxOrders) {
		this.taxOrders =  taxOrders;
	}
	
	public String getWebsit() {
		return websit;
	}
	
	public void setWebsit(String websit) {
		this.websit = websit;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Override
	public long getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getParentName() {
		return parentName;
	}

	@Override
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public long getContactManId() {
		return contactManId;
	}

	public void setContactManId(long contactManId) {
		this.contactManId = contactManId;
	}

	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
