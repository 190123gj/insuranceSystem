package com.born.insurance.ws.order.brokerageFeeDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BrokerageFeeDetailQueryOrder extends QueryPermissionPageBase {
				
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
	private double receivableAmount;
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