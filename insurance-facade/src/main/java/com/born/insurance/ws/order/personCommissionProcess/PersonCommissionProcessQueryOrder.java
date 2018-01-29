package com.born.insurance.ws.order.personCommissionProcess;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PersonCommissionProcessQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 申请结算佣金
	*/	
	private long applyDrawAmount;
 	/**
	* id
	*/	
	private long commissionProcessId;
 	/**
	* 状态
	*/	
	private String status;
 	/**
	* 业务员
	*/	
	private String businessUserName;
	
	/**
	 * 证件号码
	 */
	private String businessUserCertNo;
	
	/**
	 * 业务员手机号码
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
	
	/**
	 * 申请中金额
	 */
	private long applyingAmount;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getApplyDrawAmount() {
        return applyDrawAmount;
	}

	public void setApplyDrawAmount(long applyDrawAmount) {
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
    

    public long getApplyingAmount() {
		return applyingAmount;
	}

	public void setApplyingAmount(long applyingAmount) {
		this.applyingAmount = applyingAmount;
	}

	
	public String getBusinessUserCertNo() {
		return businessUserCertNo;
	}

	public void setBusinessUserCertNo(String businessUserCertNo) {
		this.businessUserCertNo = businessUserCertNo;
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