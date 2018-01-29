package com.born.insurance.ws.info.invoiceRequisition;

import java.util.Date;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class SettlementBillInfo extends BaseToStringInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1426065749816180545L;
	
	/**
	 * 保单号
	 */
	private String insuranceNo;
	
	/**
	 * 险种
	 */
	private String catalogNames;
	
	/**
	 * 投保日期
	 */
	private String insuranceDate;
	
	/**
	 * 投保人
	 */
	private String billCustomerName;
	
	/**
	 * 保费
	 */
	private Money premiumAmount = new Money(0,0);
	
	/**
	 * 经纪费率
	 */
	private Double borkerageRate;
	
	/**
	 * 经纪费
	 */
	private Money brokerageFee = new Money(0,0);

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getCatalogNames() {
		return catalogNames;
	}

	public void setCatalogNames(String catalogNames) {
		this.catalogNames = catalogNames;
	}

	public String getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(String insuranceDate) {
		this.insuranceDate = insuranceDate;
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

	public Double getBorkerageRate() {
		return borkerageRate;
	}

	public void setBorkerageRate(Double borkerageRate) {
		this.borkerageRate = borkerageRate;
	}

	public Money getBrokerageFee() {
		return brokerageFee;
	}

	public void setBrokerageFee(Money brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	
	

}
