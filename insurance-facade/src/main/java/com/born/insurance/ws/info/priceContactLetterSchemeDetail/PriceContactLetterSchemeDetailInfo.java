package com.born.insurance.ws.info.priceContactLetterSchemeDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class PriceContactLetterSchemeDetailInfo extends BaseToStringInfo {


	private long id;

	private long letterSchemeId;

	private long parentCatalogId;

	private String parentCatalogName;

	private long catalogId;

	private String catalogName;

	private String responsibilityScope;

	private long liabilityNameId;

	private String propertyCategory;

	private String insuranceAmount;

	private Money insuranceAmountMoney;

	private String insuranceWay;

	private double deductibleRate;

	private double premiumRate;

	private String limitAmountType;

	private long limitAmount;

	private String expectPremiumAmount;

	private Money expectPremiumAmountMoney = new Money(0,0);

	private double intentionBrokerRate;

	private long nonDeductible;

	private String name;

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

	public long getLiabilityNameId() {
		return liabilityNameId;
	}

	public void setLiabilityNameId(long liabilityNameId) {
		this.liabilityNameId = liabilityNameId;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
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

	public double getDeductibleRate() {
		return deductibleRate;
	}

	public void setDeductibleRate(double deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	public String getExpectPremiumAmount() {
		return expectPremiumAmount;
	}

	public void setExpectPremiumAmount(String expectPremiumAmount) {
		this.expectPremiumAmount = expectPremiumAmount;
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

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
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

	public long getLetterSchemeId() {
		return letterSchemeId;
	}

	public void setLetterSchemeId(long letterSchemeId) {
		this.letterSchemeId = letterSchemeId;
	}

	public long getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(long limitAmount) {
		this.limitAmount = limitAmount;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Money getInsuranceAmountMoney() {
		return insuranceAmountMoney;
	}

	public void setInsuranceAmountMoney(Money insuranceAmountMoney) {
		this.insuranceAmountMoney = insuranceAmountMoney;
	}

	public Money getExpectPremiumAmountMoney() {
		return expectPremiumAmountMoney;
	}

	public void setExpectPremiumAmountMoney(Money expectPremiumAmountMoney) {
		this.expectPremiumAmountMoney = expectPremiumAmountMoney;
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