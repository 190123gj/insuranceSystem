package com.born.insurance.ws.info.businessBillCoinsInfo;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BusinessBillCoinsInfoInfo extends BaseToStringInfo {
				
 	/**
	* 保险公司
	*/	
	private String insuraceCompanyName;
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 经纪费
	*/	
	private Money brokerAmount = new Money(0,0);
 	/**
	* 保险费
	*/	
	private Money premiumAmount = new Money(0,0);
 	/**
	* 其他费
	*/	
	private Money otherAmount = new Money(0,0);
 	/**
	* 保险公司
	*/	
	private long insuraceCompanyId;
 	/**
	* coins_info_id
	*/	
	private long coinsInfoId;
 	/**
	* 出单费
	*/	
	private Money outAmount = new Money(0,0);
 	/**
	* 首席
	*/	
	private String chief;
 	/**
	* 份额
	*/	
	private double portion;
 
  	public String getInsuraceCompanyName() {
        return insuraceCompanyName;
	}

	public void setInsuraceCompanyName(String insuraceCompanyName) {
        this.insuraceCompanyName = insuraceCompanyName;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	
	public long getInsuraceCompanyId() {
        return insuraceCompanyId;
	}

	public void setInsuraceCompanyId(long insuraceCompanyId) {
        this.insuraceCompanyId = insuraceCompanyId;
	}
	public long getCoinsInfoId() {
        return coinsInfoId;
	}

	public void setCoinsInfoId(long coinsInfoId) {
        this.coinsInfoId = coinsInfoId;
	}
	
	public String getChief() {
        return chief;
	}

	public void setChief(String chief) {
        this.chief = chief;
	}
	public double getPortion() {
        return portion;
	}

	public void setPortion(double portion) {
        this.portion = portion;
	}
    

    public Money getBrokerAmount() {
		return brokerAmount;
	}

	public void setBrokerAmount(Money brokerAmount) {
		this.brokerAmount = brokerAmount;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Money getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(Money otherAmount) {
		this.otherAmount = otherAmount;
	}

	public Money getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Money outAmount) {
		this.outAmount = outAmount;
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