package com.born.insurance.ws.order.insuranceProduct;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.insuranceCatalogLiability.InsuranceCatalogLiabilityOrder;
import com.yjf.common.lang.util.money.Money;

public class InsuranceProductOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -6569495639844266221L;
	
 	/**
	* 停售时间
	*/	
	private String stopSaleDate;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 创建者
	*/	
	private long creatorId;
 	/**
	* id
	*/	
	private long typeId;
 	/**
	* 销售状态
	*/	
	private String saleStatus;
	
	/**
	 * 销售区域
	 */
	private String salesAreas;

 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保险公司
	*/	
	private long companyUserId;

	private long companyBaseUserId;
	
	/**
	 * 保险公司名称
	 */
	private String companyUserName;
 	/**
	* 缴费类型
	*/	
	private long payType;
 	/**
	* 险种名称
	*/	
	private String productName;
 	/**
	* 缴费期限
	*/	
	private String payPeriod;
	
	/**
	 * 分期维护系数
	 */
	private String periodRate;
	
	/**
	 * 产品费率表中的基本保额维度
	 */
	private Money unitPrice = new Money(0,0);
 	/**
	* 费率表id
	*/	
	private long rateId;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 备案号
	*/	
	private String recordNumber;
 	/**
	* 所属保险分类
	*/	
	private String catalogId;
	
	/**
	 * 所属保险分类名称
	 */
	private String catalogName;
	
	/**
	 * 父险种id
	 */			
	private String parentProductId;
	
	/**
	 * 是否定额
	 */
	private String isQuota;

	/**
	 * 是否为寿险
	 */
	private String isLifeInsurance;

	/**
	 * 简称一
	 */
	private String abbr1;

	/**
	 * 简称二
	 */
	private String abbr2;

	/**
	 * 简称三
	 */
	private String abbr3;

	/** 
	 * 保险期限类型     
	 */
	private String insurancePeriodType;

	/**
	 * 保险期限
	 */
	private String insurancePeriod;

    private String salesAreasCollect;;
	
	 /**
	  * 产品档次信息
	  */
	private List<InsuranceProductLevelOrder> insuranceProductLevelOrders;
	
	/**
	 * 产品管理的保险责任
	 */
	private List<InsuranceCatalogLiabilityOrder> insuranceCatalogLiabilityOrders;
	
	private long risk;

	private String charge;
	
 
	public String getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}

	public String getStopSaleDate() {
        return stopSaleDate;
	}

	public void setStopSaleDate(String stopSaleDate) {
        this.stopSaleDate = stopSaleDate;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getCreatorId() {
        return creatorId;
	}

	public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
	}
	public long getTypeId() {
        return typeId;
	}

	public void setTypeId(long typeId) {
        this.typeId = typeId;
	}
	public String getSaleStatus() {
        return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
	}

	public long getRisk() {
		return risk;
	}

	public void setRisk(long risk) {
		this.risk = risk;
	}

	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getCompanyUserId() {
        return companyUserId;
	}

	public void setCompanyUserId(long companyUserId) {
        this.companyUserId = companyUserId;
	}
	public long getPayType() {
        return payType;
	}

	public void setPayType(long payType) {
        this.payType = payType;
	}
	public String getProductName() {
        return productName;
	}

	public void setProductName(String productName) {
        this.productName = productName;
	}
	public String getPayPeriod() {
        return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
	}
	public long getRateId() {
        return rateId;
	}

	public void setRateId(long rateId) {
        this.rateId = rateId;
	}


	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
	}
	public String getRecordNumber() {
        return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
	}
	public String getCatalogId() {
        return catalogId;
	}

	public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
	}
    public String getCreatorName() {
		return creatorName;
	}
    
	public String getSalesAreas() {
		return salesAreas;
	}

	public void setSalesAreas(String salesAreas) {
		this.salesAreas = salesAreas;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}

	public String getIsLifeInsurance() {
		return isLifeInsurance;
	}

	public void setIsLifeInsurance(String isLifeInsurance) {
		this.isLifeInsurance = isLifeInsurance;
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

	public String getInsurancePeriodType() {
		return insurancePeriodType;
	}

	public void setInsurancePeriodType(String insurancePeriodType) {
		this.insurancePeriodType = insurancePeriodType;
	}

	public String getInsurancePeriod() {
		return insurancePeriod;
	}

	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}
	
	public List<InsuranceProductLevelOrder> getInsuranceProductLevelOrders() {
		return insuranceProductLevelOrders;
	}

	public void setInsuranceProductLevelOrders(List<InsuranceProductLevelOrder> insuranceProductLevelOrders) {
		this.insuranceProductLevelOrders = insuranceProductLevelOrders;
	}
	
	public List<InsuranceCatalogLiabilityOrder> getInsuranceCatalogLiabilityOrders() {
		return insuranceCatalogLiabilityOrders;
	}
	
	public void setInsuranceCatalogLiabilityOrders(List<InsuranceCatalogLiabilityOrder> insuranceCatalogLiabilityOrders) {
		this.insuranceCatalogLiabilityOrders = insuranceCatalogLiabilityOrders;
	}

	public String getSalesAreasCollect() {
		return salesAreasCollect;
	}

	public void setSalesAreasCollect(String salesAreasCollect) {
		this.salesAreasCollect = salesAreasCollect;
	}


	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public long getCompanyBaseUserId() {
		return companyBaseUserId;
	}

	public void setCompanyBaseUserId(long companyBaseUserId) {
		this.companyBaseUserId = companyBaseUserId;
	}
	

	public String getPeriodRate() {
		return periodRate;
	}

	public void setPeriodRate(String periodRate) {
		this.periodRate = periodRate;
	}

	public Money getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Money unitPrice) {
		this.unitPrice = unitPrice;
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
    
    

	@Override
    public void check() {
    	validateHasText(productName, "产品名称");
    	validateHasZore(companyUserId, "保险公司");
    }
	
	
}	