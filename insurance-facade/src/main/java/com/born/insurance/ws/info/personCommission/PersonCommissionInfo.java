package com.born.insurance.ws.info.personCommission;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class PersonCommissionInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 2575481017562036889L;
	/**
	* 总佣金
	*/	
	private Money totalAmount = new Money(0,0);
 	/**
	* 
	*/	
	private Date rawAddTime;
	
	/**
	 * 申请中金额
	 */
	private  Money applyingAmount = new Money(0,0) ;
	
	 
 	/**
	* 结算佣金
	*/	
	private  Money drawAmount = new Money(0,0) ;
 	/**
	* 业务员类型
	*/	
	private String businessUserType;
 	/**
	* 业务员
	*/	
	private String businessUserName;
	
	/**
	 * 业务员手机号码
	 */
	private String businessUserMobile ;
	
	/**
	 * 证件号码
	 */
	private String businessUserCertNo;
 	/**
	* id
	*/	
	private long commissionId;
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
	
	public String getBusinessUserType() {
        return businessUserType;
	}

	public void setBusinessUserType(String businessUserType) {
        this.businessUserType = businessUserType;
	}
	public String getBusinessUserName() {
        return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
        this.businessUserName = businessUserName;
	}
	public long getCommissionId() {
        return commissionId;
	}

	public void setCommissionId(long commissionId) {
        this.commissionId = commissionId;
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
	

	public Money getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Money getApplyingAmount() {
		return applyingAmount;
	}

	public void setApplyingAmount(Money applyingAmount) {
		this.applyingAmount = applyingAmount;
	}

	public Money getDrawAmount() {
		return drawAmount;
	}

	public void setDrawAmount(Money drawAmount) {
		this.drawAmount = drawAmount;
	}

	public String getBusinessUserMobile() {
		return businessUserMobile;
	}

	public void setBusinessUserMobile(String businessUserMobile) {
		this.businessUserMobile = businessUserMobile;
	}

	public String getBusinessUserCertNo() {
		return businessUserCertNo;
	}

	public void setBusinessUserCertNo(String businessUserCertNo) {
		this.businessUserCertNo = businessUserCertNo;
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