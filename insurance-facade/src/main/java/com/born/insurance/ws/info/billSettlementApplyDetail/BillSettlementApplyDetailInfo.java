package com.born.insurance.ws.info.billSettlementApplyDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BillSettlementApplyDetailInfo extends BaseToStringInfo {
				
	/**
	 * 
	 */
	private static final long serialVersionUID = -6907143673231703788L;

	private long id;

	private long settlementApplyId;

	private long businessBillId;

	private Money recievableFee = new Money(0, 0);

	private Money recievedFee = new Money(0, 0);

	private Money shouldPayFee = new Money(0, 0);

	private Money actualPayFee = new Money(0, 0);

	private Money serviceFee = new Money(0, 0);

	private Money generationFee = new Money(0, 0);

	private Money individualTax = new Money(0, 0);

	private Money payFee = new Money(0, 0);

	private double reservedScale;

	private Date rowAddTime;

	private Date rowUpdateTime;
 
 

    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getSettlementApplyId() {
		return settlementApplyId;
	}



	public void setSettlementApplyId(long settlementApplyId) {
		this.settlementApplyId = settlementApplyId;
	}



	public long getBusinessBillId() {
		return businessBillId;
	}



	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
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



	public Money getShouldPayFee() {
		return shouldPayFee;
	}



	public void setShouldPayFee(Money shouldPayFee) {
		this.shouldPayFee = shouldPayFee;
	}



	public Money getActualPayFee() {
		return actualPayFee;
	}



	public void setActualPayFee(Money actualPayFee) {
		this.actualPayFee = actualPayFee;
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



	public Money getIndividualTax() {
		return individualTax;
	}



	public void setIndividualTax(Money individualTax) {
		this.individualTax = individualTax;
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