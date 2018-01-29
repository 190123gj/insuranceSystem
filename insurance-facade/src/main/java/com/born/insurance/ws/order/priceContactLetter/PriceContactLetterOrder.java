package com.born.insurance.ws.order.priceContactLetter;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.FormOrderBase;

public class PriceContactLetterOrder extends FormOrderBase {

	private long id;

	private String priceType;

	private String priceTemplate;

	private long catalogId;

	private String catalogName;

	private long businessConditionId;

	private String businessConditions;

	private String businessUserId;

	private String businessUserName;

	private long projectSetupId;

	private String projectSetupName;

	private String name;

	private String billNo;

	private String customerType;

	private long customerUserId;

	private String customerUserName;

	private String customerCertType;

	private String customerCertNo;

	private long creatorId;

	private String creatorName;

	private String status;

	private Date rawAddTime;

	private Date rawUpdateTime;

	private long version;

	private String ext;
	private String specialReq;

	private String askDate;



	List<PriceContactLetterDemandOrder> demandOrders;


	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public long getBusinessConditionId() {
		return businessConditionId;
	}

	public void setBusinessConditionId(long businessConditionId) {
		this.businessConditionId = businessConditionId;
	}

	public String getBusinessConditions() {
		return businessConditions;
	}

	public void setBusinessConditions(String businessConditions) {
		this.businessConditions = businessConditions;
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

	public long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(long catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	@Override
	public long getCreatorId() {
		return creatorId;
	}

	@Override
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String getCreatorName() {
		return creatorName;
	}

	@Override
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCustomerCertNo() {
		return customerCertNo;
	}

	public void setCustomerCertNo(String customerCertNo) {
		this.customerCertNo = customerCertNo;
	}

	public String getCustomerCertType() {
		return customerCertType;
	}

	public void setCustomerCertType(String customerCertType) {
		this.customerCertType = customerCertType;
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

	public List<PriceContactLetterDemandOrder> getDemandOrders() {
		return demandOrders;
	}

	public void setDemandOrders(List<PriceContactLetterDemandOrder> demandOrders) {
		this.demandOrders = demandOrders;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPriceTemplate() {
		return priceTemplate;
	}

	public void setPriceTemplate(String priceTemplate) {
		this.priceTemplate = priceTemplate;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public long getProjectSetupId() {
		return projectSetupId;
	}

	public void setProjectSetupId(long projectSetupId) {
		this.projectSetupId = projectSetupId;
	}

	public String getProjectSetupName() {
		return projectSetupName;
	}

	public void setProjectSetupName(String projectSetupName) {
		this.projectSetupName = projectSetupName;
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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	public String getAskDate() {
		return askDate;
	}

	public void setAskDate(String askDate) {
		this.askDate = askDate;
	}

	public String getSpecialReq() {
		return specialReq;
	}

	public void setSpecialReq(String specialReq) {
		this.specialReq = specialReq;
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