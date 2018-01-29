package com.born.insurance.ws.order.businessBillPayPlan;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class BusinessBillPayPlanOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 2194598646893916220L;
	/**
	* 应交日期
	*/	
	private String planPayDate;
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 保费
	*/	
	private Money premiumAmount = new Money(0,0);
	
	/**
	 * 当前第几期
	 */
	private long serial;
	
	/**
	 * 当前第一年
	 */
	private String currentYear;
	
	/**
	 * 保单年度
	 */
	private String year;
	/**
	 * 缴费期数
	 */
	private String appserialPeriod;
	
	/**
	 * 缴费状态
	 */
	private String paymentStatus;
	
	/**
	 * 缴费信息
	 */
	private String insuranceBillRenewal;
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
	private String actualPayDate;
 
  	public String getPlanPayDate() {
        return planPayDate;
	}

	public void setPlanPayDate(String planPayDate) {
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
	public String getActualPayDate() {
        return actualPayDate;
	}

	public void setActualPayDate(String actualPayDate) {
        this.actualPayDate = actualPayDate;
	}
    public String getAppserialPeriod() {
		return appserialPeriod;
	}

	public void setAppserialPeriod(String appserialPeriod) {
		this.appserialPeriod = appserialPeriod;
	}
	
	public String getInsuranceBillRenewal() {
		return insuranceBillRenewal;
	}

	public void setInsuranceBillRenewal(String insuranceBillRenewal) {
		this.insuranceBillRenewal = insuranceBillRenewal;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public long getSerial() {
		return serial;
	}

	public void setSerial(long serial) {
		this.serial = serial;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
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