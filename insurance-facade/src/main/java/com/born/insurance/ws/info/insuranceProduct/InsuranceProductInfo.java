package com.born.insurance.ws.info.insuranceProduct;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONArray;
import com.born.insurance.ws.info.insuranceProtocol.SalesAreaCommInfo;
import com.yjf.common.lang.util.money.Money;

public class InsuranceProductInfo extends SalesAreaCommInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -7884567304309453119L;
	/**
	* 用户备注
	*/	
	private String remark;
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
	
 	/**
	* 停售时间
	*/	
	private Date stopSaleDate;
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
	private long productId;
 	/**
	* 销售状态
	*/	
	private String saleStatus;
 	/**
	* 父险种
	*/	
	private long parentProductId;
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
	private String companyUserName ;
	
	private long payType;
	
	/**
	 * 分期缴费系数
	 */
	private String periodRate;
	
	/**
	 * 产品费率表中的保额的基本维度
	 */
	private Money unitPrice = new Money(0.0);

	private String payTypeName;

	private String payPeriod;

	private String payPeriodName;
 
 	/**
	* 险种名称
	*/	
	private String productName;

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
	private long catalogId;

	private String productNo;
	
	/**
	 * 分类名称
	 */
	private String catalogName;

	private JSONArray charge;
 
  	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public Date getStopSaleDate() {
        return stopSaleDate;
	}

	public void setStopSaleDate(Date stopSaleDate) {
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
	
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getSaleStatus() {
        return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
	}

	public long getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(long parentProductId) {
		this.parentProductId = parentProductId;
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
	public long getRateId() {
        return rateId;
	}

	public void setRateId(long rateId) {
        this.rateId = rateId;
	}
	public String getCreatorName() {
        return creatorName;
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
	public long getCatalogId() {
        return catalogId;
	}

	public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
	}

    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getCompanyUserName() {
		return companyUserName;
	}


	public long getPayType() {
		return payType;
	}

	public void setPayType(long payType) {
		this.payType = payType;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	public String getPayPeriodName() {
		return payPeriodName;
	}

	public void setPayPeriodName(String payPeriodName) {
		this.payPeriodName = payPeriodName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
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

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public JSONArray getCharge() {
		return charge;
	}

	public void setCharge(JSONArray charge) {
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
	
	
}	