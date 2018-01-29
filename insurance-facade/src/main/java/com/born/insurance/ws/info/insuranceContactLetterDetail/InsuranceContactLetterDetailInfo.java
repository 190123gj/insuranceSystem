package com.born.insurance.ws.info.insuranceContactLetterDetail;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.insuranceProtocolCharge.InsuranceProtocolChargeInfo;
import com.yjf.common.lang.util.money.Money;

public class InsuranceContactLetterDetailInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 6695947821651440081L;
	/**
	* id
	*/	
	private long letterDetailId;
 	/**
	* 经纪费
	*/	
	private Money brokerAmount = new Money(0,0);
 	/**
	* 保费
	*/	
	private Money premiumAmount = new Money(0,0) ;
 	/**
	* 保额
	*/	
	private Money insuranceAmount = new Money(0,0) ;
	
	/**
	 * 首期保费
	 */
	private Money firstPremiumAmount = new Money(0,0)  ;
	
	/**
	 * 缴费期限
	 */
	private String period;
	
	/**
	 * 缴费类型
	 */
	private String payType;
	
	/**
	 * 该产品在这个缴费期限下对应的费率
	 */
	private String periodRate;
 	/**
	* 联系函id
	*/	
	private long letterId;
 	
	/**
	 * 产品的id
	 */
	private long productId;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 产品对应对保险期限
	 */
	private List<InsuranceProtocolChargeInfo> insuranceProductCharges;
 
  	public long getLetterDetailId() {
        return letterDetailId;
	}

	public void setLetterDetailId(long letterDetailId) {
        this.letterDetailId = letterDetailId;
	}
	
	public long getLetterId() {
        return letterId;
	}

	public void setLetterId(long letterId) {
        this.letterId = letterId;
	}
	
    public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Money getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(Money insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public Money getFirstPremiumAmount() {
		return firstPremiumAmount;
	}

	public void setFirstPremiumAmount(Money firstPremiumAmount) {
		this.firstPremiumAmount = firstPremiumAmount;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public List<InsuranceProtocolChargeInfo> getInsuranceProductCharges() {
		return insuranceProductCharges;
	}

	public void setInsuranceProductCharges(List<InsuranceProtocolChargeInfo> insuranceProductCharges) {
		this.insuranceProductCharges = insuranceProductCharges;
	}
	
	public String getPeriodRate() {
		return periodRate;
	}

	public void setPeriodRate(String periodRate) {
		this.periodRate = periodRate;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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