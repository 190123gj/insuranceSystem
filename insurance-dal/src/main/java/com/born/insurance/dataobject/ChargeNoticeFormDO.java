/**
 * 
 */
package com.born.insurance.dataobject;

import java.util.Date;

import com.yjf.common.lang.util.money.Money;

/**
 * @author guoj
 *
 * 2018年1月9日下午3:19:20
 */
public class ChargeNoticeFormDO extends SimpleFormDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8329838288992900928L;
	
	private long id;

	private long businessBillId;

	private String depart;

	private String team;

	private Date noticeDate;

	private String insuranceNo;

	private String insuranceTypeName;

	private String billCustomerName;

	private Money premiumAmount = new Money(0, 0);

	private Money recievableFee = new Money(0, 0);

	private Money recievedFee = new Money(0, 0);

	private Money payFee = new Money(0, 0);

	private double reservedScale;

	private String noticeNo;

	private String billNo;

	private String insuranceBrokerNo;

	private String insuranceBrokerName;

	private long commissionType ;

	private Money persistencyRateWard = new Money(0, 0);

	private Money manageGrant = new Money(0, 0);

	private Money packageFee = new Money(0, 0);

	private Money insuranceOther = new Money(0, 0);

	private Date rowAddTime;

	private Date rowUpdateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBusinessBillId() {
		return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceTypeName() {
		return insuranceTypeName;
	}

	public void setInsuranceTypeName(String insuranceTypeName) {
		this.insuranceTypeName = insuranceTypeName;
	}

	public String getBillCustomerName() {
		return billCustomerName;
	}

	public void setBillCustomerName(String billCustomerName) {
		this.billCustomerName = billCustomerName;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Money getRecievableFee() {
		return recievableFee;
	}

	public void setRecievableFee(Money recievableFee) {
		this.recievableFee = recievableFee;
	}

	public Money getRecievedFee() {
		return recievedFee;
	}

	public void setRecievedFee(Money recievedFee) {
		this.recievedFee = recievedFee;
	}

	public Money getPayFee() {
		return payFee;
	}

	public void setPayFee(Money payFee) {
		this.payFee = payFee;
	}

	public double getReservedScale() {
		return reservedScale;
	}

	public void setReservedScale(double reservedScale) {
		this.reservedScale = reservedScale;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getInsuranceBrokerNo() {
		return insuranceBrokerNo;
	}

	public void setInsuranceBrokerNo(String insuranceBrokerNo) {
		this.insuranceBrokerNo = insuranceBrokerNo;
	}

	public String getInsuranceBrokerName() {
		return insuranceBrokerName;
	}

	public void setInsuranceBrokerName(String insuranceBrokerName) {
		this.insuranceBrokerName = insuranceBrokerName;
	}

	
	public long getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(long commissionType) {
		this.commissionType = commissionType;
	}

	public Money getPersistencyRateWard() {
		return persistencyRateWard;
	}

	public void setPersistencyRateWard(Money persistencyRateWard) {
		this.persistencyRateWard = persistencyRateWard;
	}

	public Money getManageGrant() {
		return manageGrant;
	}

	public void setManageGrant(Money manageGrant) {
		this.manageGrant = manageGrant;
	}

	public Money getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(Money packageFee) {
		this.packageFee = packageFee;
	}

	public Money getInsuranceOther() {
		return insuranceOther;
	}

	public void setInsuranceOther(Money insuranceOther) {
		this.insuranceOther = insuranceOther;
	}

	public Date getRowAddTime() {
		return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}

	public Date getRowUpdateTime() {
		return rowUpdateTime;
	}

	public void setRowUpdateTime(Date rowUpdateTime) {
		this.rowUpdateTime = rowUpdateTime;
	}
	
	

}
