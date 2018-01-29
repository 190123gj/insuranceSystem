package com.born.insurance.ws.order.commissionInfoDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class CommissionInfoDetailOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -3118723541206036572L;
	/**
	* 应收金额
	*/	
	private Money receivableAmount = new Money(0,0);
	
 	/**
	* 应收日期
	*/	
	private String planPayDate;
	
	/**
	 * 实收日期
	 */
	private String extactTime;
	
	/**
	 * 缴费期数
	 */
	private String appserialPeriod;
 


	public Money getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(Money receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public String getPlanPayDate() {
        return planPayDate;
	}

	public void setPlanPayDate(String planPayDate) {
        this.planPayDate = planPayDate;
	}
    
    public String getAppserialPeriod() {
		return appserialPeriod;
	}

	public void setAppserialPeriod(String appserialPeriod) {
		this.appserialPeriod = appserialPeriod;
	}
	

	public String getExtactTime() {
		return extactTime;
	}

	public void setExtactTime(String extactTime) {
		this.extactTime = extactTime;
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