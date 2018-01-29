package com.born.insurance.ws.info.brokerageFeeDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BrokerageFeeDetailInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 2209295169182865077L;
	/**
	* 经纪费详情id
	*/	
	private long brokerageFeeDetailId;
 	/**
	* 新增时间
	*/	
	private Date rawAddTime;
 	/**
	* 实收日期
	*/	
	private Date actualPayDate;
 	/**
	* 经纪费id
	*/	
	private long brokerageFeeId;
	
	/**
	 * 缴费期数
	 */
	private String appserialPeriod;
 	/**
	* 更新时间
	*/	
	private Date updateAddTime;
 	/**
	* 经纪费比例
	*/	
	private double borkerageScale;
 	/**
	* 应收金额
	*/	
	private Money receivableAmount = new Money(0,0);
 	/**
	* 应收日期
	*/	
	private Date planPayDate;
 
  	public long getBrokerageFeeDetailId() {
        return brokerageFeeDetailId;
	}

	public void setBrokerageFeeDetailId(long brokerageFeeDetailId) {
        this.brokerageFeeDetailId = brokerageFeeDetailId;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public Date getActualPayDate() {
        return actualPayDate;
	}

	public void setActualPayDate(Date actualPayDate) {
        this.actualPayDate = actualPayDate;
	}
	public long getBrokerageFeeId() {
        return brokerageFeeId;
	}

	public void setBrokerageFeeId(long brokerageFeeId) {
        this.brokerageFeeId = brokerageFeeId;
	}
	public Date getUpdateAddTime() {
        return updateAddTime;
	}

	public void setUpdateAddTime(Date updateAddTime) {
        this.updateAddTime = updateAddTime;
	}
	public double getBorkerageScale() {
        return borkerageScale;
	}

	public void setBorkerageScale(double borkerageScale) {
        this.borkerageScale = borkerageScale;
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