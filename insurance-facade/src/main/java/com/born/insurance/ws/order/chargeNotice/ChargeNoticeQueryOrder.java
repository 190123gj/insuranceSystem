package com.born.insurance.ws.order.chargeNotice;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class ChargeNoticeQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 5265789393003948092L;
	/**
	* 保单号
	*/	
	private String insuranceNo;
 	/**
	* 管理津贴
	*/	
	private long manageGrant;
 	/**
	* 原保费
	*/	
	private long premiumAmount;
 	/**
	* 团险佣金
	*/	
	private long groupInsuranceCommission;
 	/**
	* 部门
	*/	
	private String depart;
 	/**
	* 经纪人编号
	*/	
	private String insuranceBrokerNo;
 	/**
	* 费用支付
	*/	
	private long payFee;
 	/**
	* 记录新增时间
	*/	
	private Date rowAddTime;
 	/**
	* 寿险佣金
	*/	
	private long lifeInsuranceCommission;
 	/**
	* 继续率奖金
	*/	
	private long persistencyRateWard;
 	/**
	* 
	*/	
	private long id;
 	/**
	* 记录更新时间
	*/	
	private Date rowUpdateTime;
 	/**
	* 应收金额
	*/	
	private long recievableFee;
 	/**
	* 经纪人姓名
	*/	
	private String insuranceBrokerName;
 	/**
	* 流程的formId
	*/	
	private long formId;
 	/**
	* 业务编号
	*/	
	private String billNo;
 	/**
	* 险种类别名称
	*/	
	private String insuranceTypeName;
 	/**
	* 团队
	*/	
	private String team;
 	/**
	* 日期
	*/	
	private Date noticeDate;
 	/**
	* 已收
	*/	
	private long recievedFee;
 	/**
	* 关联的保单的id
	*/	
	private long businessBillId;
 	/**
	* 费用预留比率
	*/	
	private String reservedScale;
 	/**
	* 方案费
	*/	
	private long packageFee;
 	/**
	* 单号
	*/	
	private String noticeNo;
 	/**
	* 其他
	*/	
	private String insuranceOther;
 	/**
	* 承包人简称
	*/	
	private String billCustomerName;
	/**
	 * 审批状态
	 */
	private String formStatus;
 
  	public String getInsuranceNo() {
        return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
	}
	public long getManageGrant() {
        return manageGrant;
	}

	public void setManageGrant(long manageGrant) {
        this.manageGrant = manageGrant;
	}
	public long getPremiumAmount() {
        return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
        this.premiumAmount = premiumAmount;
	}
	public long getGroupInsuranceCommission() {
        return groupInsuranceCommission;
	}

	public void setGroupInsuranceCommission(long groupInsuranceCommission) {
        this.groupInsuranceCommission = groupInsuranceCommission;
	}
	public String getDepart() {
        return depart;
	}

	public void setDepart(String depart) {
        this.depart = depart;
	}
	public String getInsuranceBrokerNo() {
        return insuranceBrokerNo;
	}

	public void setInsuranceBrokerNo(String insuranceBrokerNo) {
        this.insuranceBrokerNo = insuranceBrokerNo;
	}
	public long getPayFee() {
        return payFee;
	}

	public void setPayFee(long payFee) {
        this.payFee = payFee;
	}
	public Date getRowAddTime() {
        return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
        this.rowAddTime = rowAddTime;
	}
	public long getLifeInsuranceCommission() {
        return lifeInsuranceCommission;
	}

	public void setLifeInsuranceCommission(long lifeInsuranceCommission) {
        this.lifeInsuranceCommission = lifeInsuranceCommission;
	}
	public long getPersistencyRateWard() {
        return persistencyRateWard;
	}

	public void setPersistencyRateWard(long persistencyRateWard) {
        this.persistencyRateWard = persistencyRateWard;
	}
	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRowUpdateTime() {
        return rowUpdateTime;
	}

	public void setRowUpdateTime(Date rowUpdateTime) {
        this.rowUpdateTime = rowUpdateTime;
	}
	public long getRecievableFee() {
        return recievableFee;
	}

	public void setRecievableFee(long recievableFee) {
        this.recievableFee = recievableFee;
	}
	public String getInsuranceBrokerName() {
        return insuranceBrokerName;
	}

	public void setInsuranceBrokerName(String insuranceBrokerName) {
        this.insuranceBrokerName = insuranceBrokerName;
	}
	public long getFormId() {
        return formId;
	}

	public void setFormId(long formId) {
        this.formId = formId;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public String getInsuranceTypeName() {
        return insuranceTypeName;
	}

	public void setInsuranceTypeName(String insuranceTypeName) {
        this.insuranceTypeName = insuranceTypeName;
	}
	public String getTeam() {
        return team;
	}

	public void setTeam(String team) {
        this.team = team;
	}
	public Date getNoticeDate() {
        return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
	}
	public long getRecievedFee() {
        return recievedFee;
	}

	public void setRecievedFee(long recievedFee) {
        this.recievedFee = recievedFee;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getReservedScale() {
        return reservedScale;
	}

	public void setReservedScale(String reservedScale) {
        this.reservedScale = reservedScale;
	}
	public long getPackageFee() {
        return packageFee;
	}

	public void setPackageFee(long packageFee) {
        this.packageFee = packageFee;
	}
	public String getNoticeNo() {
        return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
	}
	public String getInsuranceOther() {
        return insuranceOther;
	}

	public void setInsuranceOther(String insuranceOther) {
        this.insuranceOther = insuranceOther;
	}
	public String getBillCustomerName() {
        return billCustomerName;
	}

	public void setBillCustomerName(String billCustomerName) {
        this.billCustomerName = billCustomerName;
	}


	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
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