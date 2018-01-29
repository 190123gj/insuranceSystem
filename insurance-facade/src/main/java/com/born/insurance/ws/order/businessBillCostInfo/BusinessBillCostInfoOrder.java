package com.born.insurance.ws.order.businessBillCostInfo;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;

public class BusinessBillCostInfoOrder extends ProcessOrder {
				
 	/**
	* 应收费用
	*/	
	private long chargeAmount;
 	/**
	* 比例
	*/	
	private double proportion;
 	/**
	* 出单费
	*/	
	private long actualCollectDate;
 	/**
	* 应收日期
	*/	
	private Date collectDate;
 	/**
	* cost_info_id
	*/	
	private long costInfoId;
 
  	public long getChargeAmount() {
        return chargeAmount;
	}

	public void setChargeAmount(long chargeAmount) {
        this.chargeAmount = chargeAmount;
	}
	public double getProportion() {
        return proportion;
	}

	public void setProportion(double proportion) {
        this.proportion = proportion;
	}
	public long getActualCollectDate() {
        return actualCollectDate;
	}

	public void setActualCollectDate(long actualCollectDate) {
        this.actualCollectDate = actualCollectDate;
	}
	public Date getCollectDate() {
        return collectDate;
	}

	public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
	}
	public long getCostInfoId() {
        return costInfoId;
	}

	public void setCostInfoId(long costInfoId) {
        this.costInfoId = costInfoId;
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