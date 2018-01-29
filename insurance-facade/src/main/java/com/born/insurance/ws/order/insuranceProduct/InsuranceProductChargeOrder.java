package com.born.insurance.ws.order.insuranceProduct;

import com.born.insurance.ws.order.base.ProcessOrder;

public class InsuranceProductChargeOrder extends ProcessOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4481504662304196264L;
	//产品id
	private String productId;
	//主险产品缴费类型
	private String mainPayType;
	//主险产品对应缴费类型比例系数
	private String periodRate;
	//附加险产品缴费类型
	private String payType;
	//缴费期限
	private String period;
	//性别
	private String chargeType;
	//身份证
	private String certNo;
	//该产品的基本保额 
	private String unitPrice;
	//保额
	private String insuranceAmount;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMainPayType() {
		return mainPayType;
	}
	public void setMainPayType(String mainPayType) {
		this.mainPayType = mainPayType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public String getPeriodRate() {
		return periodRate;
	}
	public void setPeriodRate(String periodRate) {
		this.periodRate = periodRate;
	}
	
	

}
