package com.born.insurance.ws.info.personCommissionDetail;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class PersonCommissionDetailInfo extends BaseToStringInfo {
				
	/**
	 * 
	 */
	private static final long serialVersionUID = -7245513534656155985L;

	private long settlementPersonId;

	private String serialNumber;

	private long businessUserId;

	private String businessUserName;

	private String businessUserType;

	private Money commissionAmount = new Money(0,0);

	private String commissionTime;
	
	private String insuranceNo;

	private String commissionType;

	private Money balance = new Money(0,0) ;

	private String remark;

	private Date rawAddTime;

	private Date rawUpdateTime;
 
  
    public long getSettlementPersonId() {
		return settlementPersonId;
	}


	public void setSettlementPersonId(long settlementPersonId) {
		this.settlementPersonId = settlementPersonId;
	}


	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public long getBusinessUserId() {
		return businessUserId;
	}


	public void setBusinessUserId(long businessUserId) {
		this.businessUserId = businessUserId;
	}


	public String getBusinessUserName() {
		return businessUserName;
	}


	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}


	public String getBusinessUserType() {
		return businessUserType;
	}


	public void setBusinessUserType(String businessUserType) {
		this.businessUserType = businessUserType;
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getRawAddTime() {
		return rawAddTime;
	}


	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}


	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}


	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	


	public Money getCommissionAmount() {
		return commissionAmount;
	}


	public void setCommissionAmount(Money commissionAmount) {
		this.commissionAmount = commissionAmount;
	}


	public Money getBalance() {
		return balance;
	}


	public void setBalance(Money balance) {
		this.balance = balance;
	}


	public String getInsuranceNo() {
		return insuranceNo;
	}


	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
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