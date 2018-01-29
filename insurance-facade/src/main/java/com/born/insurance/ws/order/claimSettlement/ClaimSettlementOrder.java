package com.born.insurance.ws.order.claimSettlement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;

public class ClaimSettlementOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 7737744597803089027L;
	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 业务单id
	*/	
	private long businessBillId;
	
	/**
	 * 保单号
	 */
	private String insuranceNo;
	
	/**
	 * 理赔进度
	 */
	private String schedule;
 	/**
	* 保安描述
	*/	
	private String remark;
 	/**
	* id
	*/	
	private long claimSettlementId;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 出险时间
	*/	
	private Date dangerDate;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 创建者
	*/	
	private long creatorId;
 	/**
	* 报案人
	*/	
	private String informant;
 	/**
	* 出险地点
	*/	
	private String dangerPlace;
 	/**
	* 联系电话
	*/	
	private String mobile;
 
	private Date startTime;
	
	private Date endTime;
	
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public long getClaimSettlementId() {
        return claimSettlementId;
	}

	public void setClaimSettlementId(long claimSettlementId) {
        this.claimSettlementId = claimSettlementId;
	}

	public Date getDangerDate() {
        return dangerDate;
	}

	public void setDangerDate(Date dangerDate) {
        this.dangerDate = dangerDate;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}

	public String getInformant() {
        return informant;
	}

	public void setInformant(String informant) {
        this.informant = informant;
	}
	public String getDangerPlace() {
        return dangerPlace;
	}

	public void setDangerPlace(String dangerPlace) {
        this.dangerPlace = dangerPlace;
	}
	public String getMobile() {
        return mobile;
	}

	public void setMobile(String mobile) {
        this.mobile = mobile;
	}

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
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