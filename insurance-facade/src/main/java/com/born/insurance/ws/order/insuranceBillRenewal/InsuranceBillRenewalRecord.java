package com.born.insurance.ws.order.insuranceBillRenewal;

import java.io.Serializable;
import java.util.Date;

import com.yjf.common.lang.util.money.Money;

public class InsuranceBillRenewalRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6695699582431952240L;

	//单据号
	private String billNo;
	
	//缴费计划的id
	private long payPlanId;
	
	//保单的id
	private long businessBillId;
	
	//保险费
	private Money premiumAmount = new Money(0,0);
	
	//期数
	private String period; 
	
	//缴费状态
	private int paymentStatus;
	
	//是否有缴费信息
	private boolean haveBillRenevalRecord;
	
	//应收日期
	private Date planPayDate;
	
	//实收日期
	private Date actualPayDate;
	
	//录入时间
	private Date rowAddTime ;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

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

	public Date getActualPayDate() {
		return actualPayDate;
	}

	public void setActualPayDate(Date actualPayDate) {
		this.actualPayDate = actualPayDate;
	}

	public Date getRowAddTime() {
		return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}

	public long getBusinessBillId() {
		return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
	}


	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean isHaveBillRenevalRecord() {
		return haveBillRenevalRecord;
	}

	public void setHaveBillRenevalRecord(boolean haveBillRenevalRecord) {
		this.haveBillRenevalRecord = haveBillRenevalRecord;
	}

	public long getPayPlanId() {
		return payPlanId;
	}

	public void setPayPlanId(long payPlanId) {
		this.payPlanId = payPlanId;
	}
	
	
	
	

}
