package com.born.insurance.ws.order.personCommission;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PersonCommissionQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -3419281190179219737L;
	/**
	* 总佣金
	*/	
	private long totalAmount;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 结算佣金
	*/	
	private long drawAmount;
	
	/**
	 * 业务员手机号码
	 */
	private String businessUserMobile ;
	
	/**
	 * 证件号码
	 */
	private String businessUserCertNo;
 	/**
	* 业务员类型
	*/	
	private String businessUserType;
 	/**
	* 业务员
	*/	
	private String businessUserName;
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
 
  	public long getTotalAmount() {
        return totalAmount;
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



	public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getDrawAmount() {
        return drawAmount;
	}

	public void setDrawAmount(long drawAmount) {
        this.drawAmount = drawAmount;
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