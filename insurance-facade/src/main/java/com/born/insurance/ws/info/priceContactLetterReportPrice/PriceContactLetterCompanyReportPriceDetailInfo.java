package com.born.insurance.ws.info.priceContactLetterReportPrice;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class PriceContactLetterCompanyReportPriceDetailInfo extends BaseToStringInfo {

	private long id;

	private long reportPriceId;

	private long parentCatalogId;

	private String parentCatalogName;

	private long catalogId;

	private String catalogName;

	private long insuranceProductId;

	private String insuranceProductName;

	private String responsibilityScope;

	private String propertyCategory;


	private double chargeRate;

	private double borkerAmountRate;

	private String insuranceWay;

	private double deductibleRate;

	private double premiumRate;

	private String limitAmountType;


	private double intentionBrokerRate;

	private long nonDeductible;

	private String productName;

	private long productId;

	private String packingQuantity;

	private String sign;

	private String job;

	private String ageGroup;

	private String personNum;

	private String programme;

	private String goods;

	private String target;

	private long version;

	private String ext;

	private String premiumAmount;
	private String limitAmount;
	private String borkerAmount ;
	private String insuranceAmount;
	private String expectPremiumAmount;

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public double getBorkerAmountRate() {
		return borkerAmountRate;
	}

	public void setBorkerAmountRate(double borkerAmountRate) {
		this.borkerAmountRate = borkerAmountRate;
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

	public double getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}

	public double getDeductibleRate() {
		return deductibleRate;
	}

	public void setDeductibleRate(double deductibleRate) {
		this.deductibleRate = deductibleRate;
	}


	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public long getInsuranceProductId() {
		return insuranceProductId;
	}

	public void setInsuranceProductId(long insuranceProductId) {
		this.insuranceProductId = insuranceProductId;
	}

	public String getInsuranceProductName() {
		return insuranceProductName;
	}

	public void setInsuranceProductName(String insuranceProductName) {
		this.insuranceProductName = insuranceProductName;
	}

	public String getInsuranceWay() {
		return insuranceWay;
	}

	public void setInsuranceWay(String insuranceWay) {
		this.insuranceWay = insuranceWay;
	}

	public double getIntentionBrokerRate() {
		return intentionBrokerRate;
	}

	public void setIntentionBrokerRate(double intentionBrokerRate) {
		this.intentionBrokerRate = intentionBrokerRate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}


	public String getLimitAmountType() {
		return limitAmountType;
	}

	public void setLimitAmountType(String limitAmountType) {
		this.limitAmountType = limitAmountType;
	}

	public long getNonDeductible() {
		return nonDeductible;
	}

	public void setNonDeductible(long nonDeductible) {
		this.nonDeductible = nonDeductible;
	}

	public String getPackingQuantity() {
		return packingQuantity;
	}

	public void setPackingQuantity(String packingQuantity) {
		this.packingQuantity = packingQuantity;
	}

	public long getParentCatalogId() {
		return parentCatalogId;
	}

	public void setParentCatalogId(long parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}

	public String getParentCatalogName() {
		return parentCatalogName;
	}

	public void setParentCatalogName(String parentCatalogName) {
		this.parentCatalogName = parentCatalogName;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

	public String getPropertyCategory() {
		return propertyCategory;
	}

	public void setPropertyCategory(String propertyCategory) {
		this.propertyCategory = propertyCategory;
	}

	public long getReportPriceId() {
		return reportPriceId;
	}

	public void setReportPriceId(long reportPriceId) {
		this.reportPriceId = reportPriceId;
	}

	public String getResponsibilityScope() {
		return responsibilityScope;
	}

	public void setResponsibilityScope(String responsibilityScope) {
		this.responsibilityScope = responsibilityScope;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getBorkerAmount() {
		return borkerAmount;
	}

	public void setBorkerAmount(String borkerAmount) {
		this.borkerAmount = borkerAmount;
	}

	public String getExpectPremiumAmount() {
		return expectPremiumAmount;
	}

	public void setExpectPremiumAmount(String expectPremiumAmount) {
		this.expectPremiumAmount = expectPremiumAmount;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
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