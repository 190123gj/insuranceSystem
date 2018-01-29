package com.born.insurance.ws.info.billPayFeeDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BillPayFeeDetailInfo extends BaseToStringInfo {
				
	/**
	 * 
	 */
	private static final long serialVersionUID = 3806231954976568185L;

	private long id;

	private long settlementApplyId;

	private long businessBillId;

	private String receiverName;
	
	private String insuranceBrokerNo;

	private Money generationFee = new Money(0, 0);

	private Money serviceFee = new Money(0, 0);

	private Money payFee = new Money(0, 0);

	private Money withholdTaxes = new Money(0, 0);

	private Money actualPayFee = new Money(0, 0);

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



	public String getReceiverName() {
		return receiverName;
	}



	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}



	public Money getGenerationFee() {
		return generationFee;
	}



	public void setGenerationFee(Money generationFee) {
		this.generationFee = generationFee;
	}



	public Money getServiceFee() {
		return serviceFee;
	}



	public void setServiceFee(Money serviceFee) {
		this.serviceFee = serviceFee;
	}



	public Money getPayFee() {
		return payFee;
	}



	public void setPayFee(Money payFee) {
		this.payFee = payFee;
	}



	public Money getWithholdTaxes() {
		return withholdTaxes;
	}



	public void setWithholdTaxes(Money withholdTaxes) {
		this.withholdTaxes = withholdTaxes;
	}



	public Money getActualPayFee() {
		return actualPayFee;
	}



	public void setActualPayFee(Money actualPayFee) {
		this.actualPayFee = actualPayFee;
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


	

	public String getInsuranceBrokerNo() {
		return insuranceBrokerNo;
	}



	public void setInsuranceBrokerNo(String insuranceBrokerNo) {
		this.insuranceBrokerNo = insuranceBrokerNo;
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