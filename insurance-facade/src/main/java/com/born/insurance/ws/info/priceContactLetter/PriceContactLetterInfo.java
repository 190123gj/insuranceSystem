package com.born.insurance.ws.info.priceContactLetter;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterReportPriceInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import java.util.List;

public class PriceContactLetterInfo extends BaseToStringInfo {
				
 	/**
	* id
	*/	
	private long id;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 客户
	*/	
	private long customerUserId;
 	/**
	* 客户
	*/	
	private String customerUserName;
 	/**
	* 询价方案名称
	*/	
	private String name;
 	/**
	* 询价方案编码
	*/	
	private String billNo;
 	/**
	* 扩展字段
	*/	
	private String ext;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 版本
	*/	
	private long version;


	private String priceType;

	private String priceTemplate;

	private long insuranceCatalogId;

	private String insuranceCatalogName;

	private long businessConditionId;

	private String businessConditions;

	private String status;


	private long projectSetupId;

	private String projectSetupName;

	private String customerCertType;

	private String customerCertNo;

	private long catalogId;

	private String catalogName;

	private long formId;
	private String specialReq;

	private String askDate;

	private PriceContactLetterReportPriceInfo reportPriceInfo;


	private List<PriceContactLetterDemandInfo> demandInfos;
	private String customerType;

	private String businessUserId;

	private String businessUserName;
 
  	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
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
	public String getName() {
        return name;
	}

	public void setName(String name) {
        this.name = name;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
	}


	public List<PriceContactLetterDemandInfo> getDemandInfos() {
		return demandInfos;
	}

	public void setDemandInfos(List<PriceContactLetterDemandInfo> demandInfos) {
		this.demandInfos = demandInfos;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getPriceTemplate() {
		return priceTemplate;
	}

	public void setPriceTemplate(String priceTemplate) {
		this.priceTemplate = priceTemplate;
	}

	public long getInsuranceCatalogId() {
		return insuranceCatalogId;
	}

	public void setInsuranceCatalogId(long insuranceCatalogId) {
		this.insuranceCatalogId = insuranceCatalogId;
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

	public String getInsuranceCatalogName() {
		return insuranceCatalogName;
	}

	public void setInsuranceCatalogName(String insuranceCatalogName) {
		this.insuranceCatalogName = insuranceCatalogName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
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

	public PriceContactLetterReportPriceInfo getReportPriceInfo() {
		return reportPriceInfo;
	}

	public void setReportPriceInfo(PriceContactLetterReportPriceInfo reportPriceInfo) {
		this.reportPriceInfo = reportPriceInfo;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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