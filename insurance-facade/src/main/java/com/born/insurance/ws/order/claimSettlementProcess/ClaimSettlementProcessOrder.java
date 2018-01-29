package com.born.insurance.ws.order.claimSettlementProcess;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;

public class ClaimSettlementProcessOrder extends ProcessOrder {
				
 	/**
	* 处理时间
	*/	
	private Date processDate;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* id
	*/	
	private long settlementProcessId;
 	/**
	* 描述
	*/	
	private String remark;
 	/**
	* 理赔服务id
	*/	
	private long claimSettlementId;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 处理类型
	*/	
	private String type;
 	/**
	* 
	*/	
	private Date rawUpdateTime;

	private String contactorName;

	private String contactorMobile1;

	private String contactorMobile2;
	
	private double estimateAmount;

	private double damageAmount;

	private double paymentAmount;
	
	
	
  	public Date getProcessDate() {
        return processDate;
	}

	public void setProcessDate(Date processDate) {
        this.processDate = processDate;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getSettlementProcessId() {
        return settlementProcessId;
	}

	public void setSettlementProcessId(long settlementProcessId) {
        this.settlementProcessId = settlementProcessId;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public long getClaimSettlementId() {
        return claimSettlementId;
	}

	public void setClaimSettlementId(long claimSettlementId) {
        this.claimSettlementId = claimSettlementId;
	}

	public String getType() {
        return type;
	}

	public void setType(String type) {
        this.type = type;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}

    public double getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	public double getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(double damageAmount) {
		this.damageAmount = damageAmount;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getContactorMobile1() {
		return contactorMobile1;
	}

	public void setContactorMobile1(String contactorMobile1) {
		this.contactorMobile1 = contactorMobile1;
	}

	public String getContactorMobile2() {
		return contactorMobile2;
	}

	public void setContactorMobile2(String contactorMobile2) {
		this.contactorMobile2 = contactorMobile2;
	}
	
	public String getContactorName() {
		return contactorName;
	}

	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
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