package com.born.insurance.ws.info.personCommissionProcess;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class PersonCommissionProcessInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 6491763531030889958L;
	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 申请结算佣金
	*/	
	private Money applyDrawAmount = new Money(0,0);
	
	/**
	 * 结算单号
	 */
	private String settlementNumber;
 	/**
	* id
	*/	
	private long commissionProcessId;
 	/**
	* 状态
	*/	
	private String status;
	
	/**
	 * 结算失败原因
	 */
	private String reason ;
 	/**
	* 业务员
	*/	
	private String businessUserName;
	
	/**
	 * 业务员的手机号
	 */
	private String businessUserMobile;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 业务员
	*/	
	private long businessUserId;
 
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
	public long getBusinessUserId() {
        return businessUserId;
	}

	public void setBusinessUserId(long businessUserId) {
        this.businessUserId = businessUserId;
	}
    
    public String getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(String settlementNumber) {
		this.settlementNumber = settlementNumber;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

	public String getBusinessUserMobile() {
		return businessUserMobile;
	}

	public void setBusinessUserMobile(String businessUserMobile) {
		this.businessUserMobile = businessUserMobile;
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