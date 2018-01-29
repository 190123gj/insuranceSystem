package com.born.insurance.ws.order.chargeNotice;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

public class ChargeNoticeOrder extends FormOrderBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 719049161469602254L;
	/**
	* 保单号
	*/	
	private String insuranceNo;
 	/**
	* 管理津贴
	*/	
	private Money manageGrant = new Money(0,0);
 	/**
	* 原保费
	*/	
	private Money premiumAmount = new Money(0,0);
 	
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
	private Money payFee = new Money(0,0);
	
	/**
	 * 佣金类型
	 */
	private long commissionType ;
 	/**
	* 记录新增时间
	*/	
	private Date rowAddTime;
 
 	/**
	* 继续率奖金
	*/	
	private Money persistencyRateWard = new Money(0,0);
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
	private Money recievableFee= new Money(0,0);
 	/**
	* 经纪人姓名
	*/	
	private String insuranceBrokerName;
 	
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
	private Money recievedFee = new Money(0,0);
 	/**
	* 关联的保单的id
	*/	
	private long businessBillId;
 	/**
	* 费用预留比率
	*/	
	private double reservedScale;
 	/**
	* 方案费
	*/	
	private Money packageFee = new Money(0,0);
 	/**
	* 单号
	*/	
	private String noticeNo;
 	/**
	* 其他
	*/	
	private Money insuranceOther = new Money(0,0);
 	/**
	* 承包人简称
	*/	
	private String billCustomerName;
 
  
	

    public String getInsuranceNo() {
		return insuranceNo;
	}




	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}




	public Money getManageGrant() {
		return manageGrant;
	}




	public void setManageGrant(Money manageGrant) {
		this.manageGrant = manageGrant;
	}




	public Money getPremiumAmount() {
		return premiumAmount;
	}




	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
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

	public Money getPayFee() {
		return payFee;
	}

	public void setPayFee(Money payFee) {
		this.payFee = payFee;
	}

	public Date getRowAddTime() {
		return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}

	public Money getPersistencyRateWard() {
		return persistencyRateWard;
	}

	public void setPersistencyRateWard(Money persistencyRateWard) {
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


	public Money getRecievableFee() {
		return recievableFee;
	}




	public void setRecievableFee(Money recievableFee) {
		this.recievableFee = recievableFee;
	}




	public String getInsuranceBrokerName() {
		return insuranceBrokerName;
	}




	public void setInsuranceBrokerName(String insuranceBrokerName) {
		this.insuranceBrokerName = insuranceBrokerName;
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




	public Money getRecievedFee() {
		return recievedFee;
	}




	public void setRecievedFee(Money recievedFee) {
		this.recievedFee = recievedFee;
	}




	public long getBusinessBillId() {
		return businessBillId;
	}




	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
	}




	public double getReservedScale() {
		return reservedScale;
	}




	public void setReservedScale(double reservedScale) {
		this.reservedScale = reservedScale;
	}




	public Money getPackageFee() {
		return packageFee;
	}




	public void setPackageFee(Money packageFee) {
		this.packageFee = packageFee;
	}




	public String getNoticeNo() {
		return noticeNo;
	}




	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}




	public Money getInsuranceOther() {
		return insuranceOther;
	}




	public void setInsuranceOther(Money insuranceOther) {
		this.insuranceOther = insuranceOther;
	}




	public String getBillCustomerName() {
		return billCustomerName;
	}




	public void setBillCustomerName(String billCustomerName) {
		this.billCustomerName = billCustomerName;
	}




	public long getCommissionType() {
		return commissionType;
	}




	public void setCommissionType(long commissionType) {
		this.commissionType = commissionType;
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