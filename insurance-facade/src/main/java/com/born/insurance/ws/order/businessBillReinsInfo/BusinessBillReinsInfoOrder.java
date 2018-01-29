package com.born.insurance.ws.order.businessBillReinsInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class BusinessBillReinsInfoOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -3100159562240860670L;
	/**
	* 保险公司
	*/	
	private String insuraceCompanyName;
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 再保费
	*/	
	private Money premiumAmount = new Money(0,0);
 	/**
	* coins_info_id
	*/	
	private long reinsInfoId;
 	/**
	* 保险公司
	*/	
	private long insuraceCompanyId;
 	/**
	* 手续费
	*/	
	private Money  poundageAmount = new Money(0,0);
 
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
	public long getReinsInfoId() {
        return reinsInfoId;
	}

	public void setReinsInfoId(long reinsInfoId) {
        this.reinsInfoId = reinsInfoId;
	}
	public long getInsuraceCompanyId() {
        return insuraceCompanyId;
	}

	public void setInsuraceCompanyId(long insuraceCompanyId) {
        this.insuraceCompanyId = insuraceCompanyId;
	}
	
	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Money getPoundageAmount() {
		return poundageAmount;
	}

	public void setPoundageAmount(Money poundageAmount) {
		this.poundageAmount = poundageAmount;
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