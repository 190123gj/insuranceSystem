package com.born.insurance.ws.order.personCommissionProcess;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class PersonCommissionProcessOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -2591541012384232553L;
	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 申请结算佣金
	*/	
	private Money applyDrawAmount = new Money(0,0);
 	/**
	* id
	*/	
	private long commissionProcessId;
	
	/**
	 * 批量的id
	 */
	private String commissionProcessIds;
 	/**
	* 状态
	*/	
	private String status;
	
	/**
	 * 结算失败的原因
	 */
	private String reason ;
 	/**
	* 业务员
	*/	
	private String businessUserName;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 业务员
	*/	
	private String businessUserId;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	
	public Money getApplyDrawAmount() {
		return applyDrawAmount;
	}

	public void setApplyDrawAmount(Money applyDrawAmount) {
		this.applyDrawAmount = applyDrawAmount;
	}

	public long getCommissionProcessId() {
        return commissionProcessId;
	}

	public void setCommissionProcessId(long commissionProcessId) {
        this.commissionProcessId = commissionProcessId;
	}
	public String getStatus() {
        return status;
	}

	public void setStatus(String status) {
        this.status = status;
	}
	public String getBusinessUserName() {
        return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
        this.businessUserName = businessUserName;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public String getBusinessUserId() {
        return businessUserId;
	}

	public void setBusinessUserId(String businessUserId) {
        this.businessUserId = businessUserId;
	}
    
    public String getCommissionProcessIds() {
		return commissionProcessIds;
	}

	public void setCommissionProcessIds(String commissionProcessIds) {
		this.commissionProcessIds = commissionProcessIds;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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