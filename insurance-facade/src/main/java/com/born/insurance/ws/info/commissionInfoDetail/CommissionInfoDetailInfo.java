package com.born.insurance.ws.info.commissionInfoDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class CommissionInfoDetailInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1784441567147857435L;
	/**
	* 添加时间
	*/	
	private Date rawAddTime;
 	/**
	* 佣金id
	*/	
	private String commissionInfoId;
 	/**
	* 更新时间
	*/	
	private Date updateAddTime;
	
	/**
	 * 缴费期数
	 */
	private String appserialPeriod;
 	/**
	* 佣金明细id
	*/	
	private long commissionInfoDetailId;
 	/**
	* 应收金额
	*/	
	private Money receivableAmount = new Money(0,0);
 	/**
	* 应收日期
	*/	
	private Date planPayDate;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getCommissionInfoId() {
        return commissionInfoId;
	}

	public void setCommissionInfoId(String commissionInfoId) {
        this.commissionInfoId = commissionInfoId;
	}
	public Date getUpdateAddTime() {
        return updateAddTime;
	}

	public void setUpdateAddTime(Date updateAddTime) {
        this.updateAddTime = updateAddTime;
	}
	public long getCommissionInfoDetailId() {
        return commissionInfoDetailId;
	}

	public void setCommissionInfoDetailId(long commissionInfoDetailId) {
        this.commissionInfoDetailId = commissionInfoDetailId;
	}
	
	public Money getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(Money receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public Date getPlanPayDate() {
        return planPayDate;
	}

	public void setPlanPayDate(Date planPayDate) {
        this.planPayDate = planPayDate;
	}
    
    public String getAppserialPeriod() {
		return appserialPeriod;
	}

	public void setAppserialPeriod(String appserialPeriod) {
		this.appserialPeriod = appserialPeriod;
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