package com.born.insurance.ws.info.businessBill;

import java.util.Date;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class BillPayPlanYearInfo extends BaseToStringInfo  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3738829394906600085L;
	
	/**
	 * id
	 */
	private long payPlanId;
	
	/**
	 * 期数
	 */
	private String period;
	
	/**
	 * 缴费状态
	 */
	private int paymentStatus;
	
	/**
	 * 应收日期
	 */
	private Date planPayDate;
	
	/**
	 * 保险费
	 */
	private Money premiumAmount;
	
	/**
	 * 实收日期
	 */
	private Date actualPayDate;
	
	/**
	 * 缴费信息
	 */
	private String billRenewal;
	
	/**
	 * 保单的id
	 */
	private long businessBillId;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getPlanPayDate() {
		return planPayDate;
	}

	public void setPlanPayDate(Date planPayDate) {
		this.planPayDate = planPayDate;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Date getActualPayDate() {
		return actualPayDate;
	}

	public void setActualPayDate(Date actualPayDate) {
		this.actualPayDate = actualPayDate;
	}

	public String getBillRenewal() {
		return billRenewal;
	}

	public void setBillRenewal(String billRenewal) {
		this.billRenewal = billRenewal;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public long getPayPlanId() {
		return payPlanId;
	}

	public void setPayPlanId(long payPlanId) {
		this.payPlanId = payPlanId;
	}

	public long getBusinessBillId() {
		return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
	}
	
	
	

}
