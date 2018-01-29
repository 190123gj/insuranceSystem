package com.born.insurance.ws.order.brokerageFee;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BrokerageFeeQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 9002809758517245940L;
	/**
	* 新增时间
	*/	
	private Date rawAddTime;
 	/**
	* 金额
	*/	
	private double brokerageAmount;
 	/**
	* 更新时间
	*/	
	private Date updateAddTime;
 	/**
	* 保单的id
	*/	
	private long businessBillId;
 	/**
	* 经纪费比例
	*/	
	private double brokerageScale;
 	/**
	* 经纪费id
	*/	
	private long brokerageId;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public double getBrokerageAmount() {
        return brokerageAmount;
	}

	public void setBrokerageAmount(double brokerageAmount) {
        this.brokerageAmount = brokerageAmount;
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
	public double getBrokerageScale() {
        return brokerageScale;
	}

	public void setBrokerageScale(double brokerageScale) {
        this.brokerageScale = brokerageScale;
	}
	public long getBrokerageId() {
        return brokerageId;
	}

	public void setBrokerageId(long brokerageId) {
        this.brokerageId = brokerageId;
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