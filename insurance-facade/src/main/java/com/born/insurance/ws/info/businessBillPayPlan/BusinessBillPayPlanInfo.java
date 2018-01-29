package com.born.insurance.ws.info.businessBillPayPlan;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BusinessBillPayPlanInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 3489121917406364930L;
	/**
	* 应交日期
	*/	
	private Date planPayDate;
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 保费
	*/	
	private Money premiumAmount = new Money(0,0);
	
	/**
	 * 缴费状态
	 */
	private int paymentStatus;
	
	/**
	 * 保单年度
	 */
	private String year ;
	
	/**
	 * 缴费期数
	 */
	private String appserialPeriod;
 	/**
	* pay_plan_id
	*/	
	private long payPlanId;
 	/**
	* 险种
	*/	
	private long typeId;
 	/**
	* 实交日期
	*/	
	private Date actualPayDate;
 
  	public Date getPlanPayDate() {
        return planPayDate;
	}

	public void setPlanPayDate(Date planPayDate) {
        this.planPayDate = planPayDate;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public long getPayPlanId() {
        return payPlanId;
	}

	public void setPayPlanId(long payPlanId) {
        this.payPlanId = payPlanId;
	}
	public long getTypeId() {
        return typeId;
	}

	public void setTypeId(long typeId) {
        this.typeId = typeId;
	}
	public Date getActualPayDate() {
        return actualPayDate;
	}

	public void setActualPayDate(Date actualPayDate) {
        this.actualPayDate = actualPayDate;
	}
	
    public String getAppserialPeriod() {
		return appserialPeriod;
	}

	public void setAppserialPeriod(String appserialPeriod) {
		this.appserialPeriod = appserialPeriod;
	}
	
	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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