package com.born.insurance.ws.order.insuranceBillRenewal;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceBillRenewalQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保费
	*/	
	private long premiumAmount;
 	/**
	* 业务单id
	*/	
	private long businessBillId;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* id
	*/	
	private long billRenewalId;
 	/**
	* 期数
	*/	
	private long period;
 	/**
	* 单据编号
	*/	
	private String billNo;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 创建者
	*/	
	private long creatorId;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getPremiumAmount() {
        return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
        this.premiumAmount = premiumAmount;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
	}
	public long getBillRenewalId() {
        return billRenewalId;
	}

	public void setBillRenewalId(long billRenewalId) {
        this.billRenewalId = billRenewalId;
	}
	public long getPeriod() {
        return period;
	}

	public void setPeriod(long period) {
        this.period = period;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getCreatorId() {
        return creatorId;
	}

	public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
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