package com.born.insurance.ws.order.billPayFeeDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BillPayFeeDetailQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 实付金额
	*/	
	private long actualPayFee;
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
	private long payFee;
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
	private long withholdTaxes;
 	/**
	* 收款人姓名
	*/	
	private String receiverName;
 
  	public long getActualPayFee() {
        return actualPayFee;
	}

	public void setActualPayFee(long actualPayFee) {
        this.actualPayFee = actualPayFee;
	}
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
	public long getSettlementApplyId() {
        return settlementApplyId;
	}

	public void setSettlementApplyId(long settlementApplyId) {
        this.settlementApplyId = settlementApplyId;
	}
	public long getWithholdTaxes() {
        return withholdTaxes;
	}

	public void setWithholdTaxes(long withholdTaxes) {
        this.withholdTaxes = withholdTaxes;
	}
	public String getReceiverName() {
        return receiverName;
	}

	public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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