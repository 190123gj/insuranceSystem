package com.born.insurance.ws.order.billSettlementApplyDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BillSettlementApplyDetailQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 实际付款金额
	*/	
	private long actualPayFee;
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
	private long recievedFee;
 	/**
	* 应收费用
	*/	
	private long recievableFee;
 	/**
	* 保单的id
	*/	
	private long businessBillId;
 	/**
	* 费用预留比例
	*/	
	private String reservedScale;
 	/**
	* 服务费
	*/	
	private long serviceFee;
 	/**
	* 经代费
	*/	
	private long generationFee;
 	/**
	* 支付金额
	*/	
	private long payFee;
 	/**
	* 
	*/	
	private Date rowAddTime;
 	/**
	* 应付金额
	*/	
	private long shouldPayFee;
 	/**
	* 个税
	*/	
	private long individualTax;
 	/**
	* 保单结算申请的id
	*/	
	private long settlementApplyId;
 
  	public long getActualPayFee() {
        return actualPayFee;
	}

	public void setActualPayFee(long actualPayFee) {
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
	public long getRecievedFee() {
        return recievedFee;
	}

	public void setRecievedFee(long recievedFee) {
        this.recievedFee = recievedFee;
	}
	public long getRecievableFee() {
        return recievableFee;
	}

	public void setRecievableFee(long recievableFee) {
        this.recievableFee = recievableFee;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getReservedScale() {
        return reservedScale;
	}

	public void setReservedScale(String reservedScale) {
        this.reservedScale = reservedScale;
	}
	public long getServiceFee() {
        return serviceFee;
	}

	public void setServiceFee(long serviceFee) {
        this.serviceFee = serviceFee;
	}
	public long getGenerationFee() {
        return generationFee;
	}

	public void setGenerationFee(long generationFee) {
        this.generationFee = generationFee;
	}
	public long getPayFee() {
        return payFee;
	}

	public void setPayFee(long payFee) {
        this.payFee = payFee;
	}
	public Date getRowAddTime() {
        return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
        this.rowAddTime = rowAddTime;
	}
	public long getShouldPayFee() {
        return shouldPayFee;
	}

	public void setShouldPayFee(long shouldPayFee) {
        this.shouldPayFee = shouldPayFee;
	}
	public long getIndividualTax() {
        return individualTax;
	}

	public void setIndividualTax(long individualTax) {
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