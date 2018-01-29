package com.born.insurance.ws.order.billPayFeeDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class BillPayFeeDetailOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -8941556761257544679L;
	/**
	* 实付金额
	*/	
	private Money actualPayFee = new Money(0,0);
	
	
	private Money generationFee = new Money(0, 0);
	
	
	private String insuranceBrokerNo;
 	/**
	* 费用性质
	*/	
	private String feeProperty;
 	/**
	* 
	*/	
	private long id;
 	/**
	* 
	*/	
	private Date rowUpdateTime;
 	/**
	* 关联的保单的id
	*/	
	private long businessBillId;
 	/**
	* 应付金额
	*/	
	private Money payFee = new Money(0,0);
 	/**
	* 记录新增时间
	*/	
	private Date rowAddTime;
 	/**
	* 保单申请表id
	*/	
	private long settlementApplyId;
 	/**
	* 代扣税金
	*/	
	private Money withholdTaxes = new Money(0,0);
	
	private Money serviceFee = new Money(0, 0);

 	/**
	* 收款人姓名
	*/	
	private String receiverName;
 
	public String getFeeProperty() {
        return feeProperty;
	}

	public void setFeeProperty(String feeProperty) {
        this.feeProperty = feeProperty;
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
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public Date getRowAddTime() {
        return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
        this.rowAddTime = rowAddTime;
	}
	public long getSettlementApplyId() {
        return settlementApplyId;
	}

	public void setSettlementApplyId(long settlementApplyId) {
        this.settlementApplyId = settlementApplyId;
	}
	public String getReceiverName() {
        return receiverName;
	}

	public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
	}
	
    public Money getActualPayFee() {
		return actualPayFee;
	}

	public void setActualPayFee(Money actualPayFee) {
		this.actualPayFee = actualPayFee;
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