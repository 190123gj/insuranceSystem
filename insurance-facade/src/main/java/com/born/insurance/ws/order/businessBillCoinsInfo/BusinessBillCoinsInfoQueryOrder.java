package com.born.insurance.ws.order.businessBillCoinsInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BusinessBillCoinsInfoQueryOrder extends QueryPermissionPageBase {
				
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
	private long brokerAmount;
 	/**
	* 保险费
	*/	
	private long premiumAmount;
 	/**
	* 其他费
	*/	
	private long otherAmount;
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
	private long outAmount;
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
	public long getBrokerAmount() {
        return brokerAmount;
	}

	public void setBrokerAmount(long brokerAmount) {
        this.brokerAmount = brokerAmount;
	}
	public long getPremiumAmount() {
        return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
        this.premiumAmount = premiumAmount;
	}
	public long getOtherAmount() {
        return otherAmount;
	}

	public void setOtherAmount(long otherAmount) {
        this.otherAmount = otherAmount;
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
	public long getOutAmount() {
        return outAmount;
	}

	public void setOutAmount(long outAmount) {
        this.outAmount = outAmount;
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