package com.born.insurance.ws.order.commissionInfoDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class CommissionInfoDetailQueryOrder extends QueryPermissionPageBase {
				
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
	* 佣金明细id
	*/	
	private long commissionInfoDetailId;
 	/**
	* 应收金额
	*/	
	private double receivableAmount;
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
	public double getReceivableAmount() {
        return receivableAmount;
	}

	public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
	}
	public Date getPlanPayDate() {
        return planPayDate;
	}

	public void setPlanPayDate(Date planPayDate) {
        this.planPayDate = planPayDate;
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