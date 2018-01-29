package com.born.insurance.ws.order.billSettlementApplyDetail;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class BillSettlementApplyDetailOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -6638736693035588781L;
	/**
	* 实际付款金额
	*/	
	private Money actualPayFee = new Money(0,0);
 	/**
	* 
	*/	
	private long id;
 	/**
	* 记录更新时间
	*/	
	private Date rowUpdateTime;
 	/**
	* 已收费用
	*/	
	private Money recievedFee = new Money(0,0);
 	/**
	* 应收费用
	*/	
	private Money recievableFee = new Money(0,0);
 	/**
	* 保单的id
	*/	
	private long businessBillId;
 	/**
	* 费用预留比例
	*/	
	private double reservedScale;
 	/**
	* 服务费
	*/	
	private Money serviceFee = new Money(0,0);
 	/**
	* 经代费
	*/	
	private Money generationFee = new Money(0,0);
 	/**
	* 支付金额
	*/	
	private Money payFee = new Money(0,0);
 	/**
	* 
	*/	
	private Date rowAddTime;
 	/**
	* 应付金额
	*/	
	private Money shouldPayFee = new Money(0,0);
 	/**
	* 个税
	*/	
	private Money individualTax = new Money(0,0);
 	/**
	* 保单结算申请的id
	*/	
	private long settlementApplyId;
	
	

    public Money getActualPayFee() {
		return actualPayFee;
	}




	public void setActualPayFee(Money actualPayFee) {
		this.actualPayFee = actualPayFee;
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public Date getRowUpdateTime() {
		return rowUpdateTime;
	}




	public void setRowUpdateTime(Date rowUpdateTime) {
		this.rowUpdateTime = rowUpdateTime;
	}




	public Money getRecievedFee() {
		return recievedFee;
	}




	public void setRecievedFee(Money recievedFee) {
		this.recievedFee = recievedFee;
	}




	public Money getRecievableFee() {
		return recievableFee;
	}




	public void setRecievableFee(Money recievableFee) {
		this.recievableFee = recievableFee;
	}




	public long getBusinessBillId() {
		return businessBillId;
	}




	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
	}




	public double getReservedScale() {
		return reservedScale;
	}




	public void setReservedScale(double reservedScale) {
		this.reservedScale = reservedScale;
	}




	public Money getServiceFee() {
		return serviceFee;
	}




	public void setServiceFee(Money serviceFee) {
		this.serviceFee = serviceFee;
	}




	public Money getGenerationFee() {
		return generationFee;
	}




	public void setGenerationFee(Money generationFee) {
		this.generationFee = generationFee;
	}




	public Money getPayFee() {
		return payFee;
	}




	public void setPayFee(Money payFee) {
		this.payFee = payFee;
	}




	public Date getRowAddTime() {
		return rowAddTime;
	}




	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}




	public Money getShouldPayFee() {
		return shouldPayFee;
	}




	public void setShouldPayFee(Money shouldPayFee) {
		this.shouldPayFee = shouldPayFee;
	}




	public Money getIndividualTax() {
		return individualTax;
	}




	public void setIndividualTax(Money individualTax) {
		this.individualTax = individualTax;
	}




	public long getSettlementApplyId() {
		return settlementApplyId;
	}




	public void setSettlementApplyId(long settlementApplyId) {
		this.settlementApplyId = settlementApplyId;
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