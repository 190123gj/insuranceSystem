package com.born.insurance.ws.order.personCommissionDetail;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;

public class PersonCommissionDetailOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 673463723198076565L;
	
	/**
	 * 明细id
	 */
	private long settlementPersonId;
 
 	/**
	* 业务员
	*/	
	private String businessUserName;
 	/**
	* 
	*/	
	private String commissionTime;
	
	/**
	* 佣金类型
	*/	
	private String commissionType;
	
	
 	/**
	* 业务员
	*/	
	private long businessUserId;
 
	
	public String getBusinessUserName() {
        return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
        this.businessUserName = businessUserName;
	}

	public long getBusinessUserId() {
        return businessUserId;
	}

	public void setBusinessUserId(long businessUserId) {
        this.businessUserId = businessUserId;
	}
    

    public String getCommissionTime() {
		return commissionTime;
	}

	public void setCommissionTime(String commissionTime) {
		this.commissionTime = commissionTime;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	
	public long getSettlementPersonId() {
		return settlementPersonId;
	}

	public void setSettlementPersonId(long settlementPersonId) {
		this.settlementPersonId = settlementPersonId;
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