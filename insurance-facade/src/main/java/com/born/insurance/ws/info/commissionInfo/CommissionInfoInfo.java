package com.born.insurance.ws.info.commissionInfo;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class CommissionInfoInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -4563741150190095550L;
	/**
	* 新增时间
	*/	
	private Date rawAddTime;
 	/**
	* 佣金信息id
	*/	
	private long commissionInfoId;
 	/**
	* 佣金类型
	*/	
	private String commissionCatalog;
 	/**
	* 佣金金额
	*/	
	private Money commissionAmount = new Money(0,0);
 	/**
	* 更新时间
	*/	
	private Date updateAddTime;
 	/**
	* 保单的id
	*/	
	private long businessBillId;
 	/**
	* 佣金比例
	*/	
	private double commissionScale;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getCommissionInfoId() {
        return commissionInfoId;
	}

	public void setCommissionInfoId(long commissionInfoId) {
        this.commissionInfoId = commissionInfoId;
	}
	public String getCommissionCatalog() {
        return commissionCatalog;
	}

	public void setCommissionCatalog(String commissionCatalog) {
        this.commissionCatalog = commissionCatalog;
	}

	
	public Money getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(Money commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public Date getUpdateAddTime() {
        return updateAddTime;
	}

	public void setUpdateAddTime(Date updateAddTime) {
        this.updateAddTime = updateAddTime;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public double getCommissionScale() {
        return commissionScale;
	}

	public void setCommissionScale(double commissionScale) {
        this.commissionScale = commissionScale;
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