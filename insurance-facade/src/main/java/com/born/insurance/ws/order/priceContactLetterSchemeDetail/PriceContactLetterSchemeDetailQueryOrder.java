package com.born.insurance.ws.order.priceContactLetterSchemeDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PriceContactLetterSchemeDetailQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 保额确定方式
	*/	
	private String insuranceWay;
 	/**
	* 方案建议id
	*/	
	private long letterSchemeId;
 	/**
	* 免赔额/率
	*/	
	private double deductibleRate;
 	/**
	* 品名
	*/	
	private String productName;
 	/**
	* 货物
	*/	
	private String goods;
 	/**
	* 保额
	*/	
	private long insuranceAmount;
 	/**
	* 职业
	*/	
	private String job;
 	/**
	* 财产类别
	*/	
	private String propertyCategory;
 	/**
	* 扩展字段
	*/	
	private String ext;
 	/**
	* 包装及数量
	*/	
	private String packingQuantity;
 	/**
	* 险种
	*/	
	private long typeId;
 	/**
	* 版本
	*/	
	private long version;
 	/**
	* 标记
	*/	
	private String sign;
 	/**
	* id
	*/	
	private long id;
 	/**
	* 方案
	*/	
	private String programme;
 	/**
	* 人数
	*/	
	private String personNum;
 	/**
	* 年龄段
	*/	
	private String ageGroup;
 	/**
	* 保费/费率
	*/	
	private double premiumRate;
 	/**
	* 不计免赔
	*/	
	private long nonDeductible;
 	/**
	* 赔偿限额类型
	*/	
	private long limitAmount;
 	/**
	* 责任范围
	*/	
	private String responsibilityScope;
 
  	public String getInsuranceWay() {
        return insuranceWay;
	}

	public void setInsuranceWay(String insuranceWay) {
        this.insuranceWay = insuranceWay;
	}
	public long getLetterSchemeId() {
        return letterSchemeId;
	}

	public void setLetterSchemeId(long letterSchemeId) {
        this.letterSchemeId = letterSchemeId;
	}
	public double getDeductibleRate() {
        return deductibleRate;
	}

	public void setDeductibleRate(double deductibleRate) {
        this.deductibleRate = deductibleRate;
	}
	public String getProductName() {
        return productName;
	}

	public void setProductName(String productName) {
        this.productName = productName;
	}
	public String getGoods() {
        return goods;
	}

	public void setGoods(String goods) {
        this.goods = goods;
	}
	public long getInsuranceAmount() {
        return insuranceAmount;
	}

	public void setInsuranceAmount(long insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
	}
	public String getJob() {
        return job;
	}

	public void setJob(String job) {
        this.job = job;
	}
	public String getPropertyCategory() {
        return propertyCategory;
	}

	public void setPropertyCategory(String propertyCategory) {
        this.propertyCategory = propertyCategory;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public String getPackingQuantity() {
        return packingQuantity;
	}

	public void setPackingQuantity(String packingQuantity) {
        this.packingQuantity = packingQuantity;
	}
	public long getTypeId() {
        return typeId;
	}

	public void setTypeId(long typeId) {
        this.typeId = typeId;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
	}
	public String getSign() {
        return sign;
	}

	public void setSign(String sign) {
        this.sign = sign;
	}
	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public String getProgramme() {
        return programme;
	}

	public void setProgramme(String programme) {
        this.programme = programme;
	}
	public String getPersonNum() {
        return personNum;
	}

	public void setPersonNum(String personNum) {
        this.personNum = personNum;
	}
	public String getAgeGroup() {
        return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
	}
	public double getPremiumRate() {
        return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
        this.premiumRate = premiumRate;
	}
	public long getNonDeductible() {
        return nonDeductible;
	}

	public void setNonDeductible(long nonDeductible) {
        this.nonDeductible = nonDeductible;
	}
	public long getLimitAmount() {
        return limitAmount;
	}

	public void setLimitAmount(long limitAmount) {
        this.limitAmount = limitAmount;
	}
	public String getResponsibilityScope() {
        return responsibilityScope;
	}

	public void setResponsibilityScope(String responsibilityScope) {
        this.responsibilityScope = responsibilityScope;
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