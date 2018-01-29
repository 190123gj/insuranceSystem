package com.born.insurance.ws.order.settlementCompanyBill;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class SettlementCompanyBillOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1688992674558291839L;
	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保单号
	*/	
	private String insuranceNo;
 	/**
	* id
	*/	
	private long settlementCompanyId;
 	/**
	* 经纪费
	*/	
	private Money brokerAmount = new Money(0,0);
 	/**
	* 费率
	*/	
	private double rate;
 	/**
	* 保费
	*/	
	private Money premiumAmount = new Money(0,0); ;
 	/**
	* 业务单id
	*/	
	private long businessBillId;
 	/**
	* 保险公司
	*/	
	private long insuranceCompanyId;
 	/**
	* 保险公司
	*/	
	private String insuranceCompanyName;
 	/**
	* 结算单号
	*/	
	private String billNo;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getInsuranceNo() {
        return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
	}
	public long getSettlementCompanyId() {
        return settlementCompanyId;
	}

	public void setSettlementCompanyId(long settlementCompanyId) {
        this.settlementCompanyId = settlementCompanyId;
	}
	
	public Money getBrokerAmount() {
		return brokerAmount;
	}

	public void setBrokerAmount(Money brokerAmount) {
		this.brokerAmount = brokerAmount;
	}

	public double getRate() {
        return rate;
	}

	public void setRate(double rate) {
        this.rate = rate;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public long getInsuranceCompanyId() {
        return insuranceCompanyId;
	}

	public void setInsuranceCompanyId(long insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
	}
	public String getInsuranceCompanyName() {
        return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
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