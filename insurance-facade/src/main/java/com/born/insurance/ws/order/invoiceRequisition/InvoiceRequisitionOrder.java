package com.born.insurance.ws.order.invoiceRequisition;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;

public class InvoiceRequisitionOrder extends ProcessOrder {
				
 	/**
	* 大写金额
	*/	
	private String capitalAmount;
 	/**
	* 业务部门分管领导
	*/	
	private String leader;
 	/**
	* 保险公司id
	*/	
	private long insuranceCompanyId;
 	/**
	* 结算单编号
	*/	
	private String settlementNo;
 	/**
	* 保单号
	*/	
	private String insuranceNo;
 	/**
	* 发票申请单编号
	*/	
	private String applyNo;
 	/**
	* 发票申请单id
	*/	
	private long invoiceRequisitionId;
 	/**
	* 保险公司名称
	*/	
	private String insuranceCompanyName;
 	/**
	* 电话
	*/	
	private String mobile;
 	/**
	* 申请日期
	*/	
	private Date applyTime;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 账号
	*/	
	private String bankCardNo;
 	/**
	* 申请人名称
	*/	
	private String applyUserName;
 	/**
	* 部门负责人
	*/	
	private String departmentCharger;
 	/**
	* 地址
	*/	
	private String orgAddress;
 	/**
	* 申请人的id
	*/	
	private long applyUserId;
 	/**
	* 开户行
	*/	
	private String openBankName;
 	/**
	* 
	*/	
	private String smallAmount;
 	/**
	* 纳税人识别号
	*/	
	private String identifyNumber;
 	/**
	* 申请部门
	*/	
	private String applyDeptment;
 
  	public String getCapitalAmount() {
        return capitalAmount;
	}

	public void setCapitalAmount(String capitalAmount) {
        this.capitalAmount = capitalAmount;
	}
	public String getLeader() {
        return leader;
	}

	public void setLeader(String leader) {
        this.leader = leader;
	}
	public long getInsuranceCompanyId() {
        return insuranceCompanyId;
	}

	public void setInsuranceCompanyId(long insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
	}
	public String getSettlementNo() {
        return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
	}
	public String getInsuranceNo() {
        return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
	}
	public String getApplyNo() {
        return applyNo;
	}

	public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
	}
	public long getInvoiceRequisitionId() {
        return invoiceRequisitionId;
	}

	public void setInvoiceRequisitionId(long invoiceRequisitionId) {
        this.invoiceRequisitionId = invoiceRequisitionId;
	}
	public String getInsuranceCompanyName() {
        return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getMobile() {
        return mobile;
	}

	public void setMobile(String mobile) {
        this.mobile = mobile;
	}
	public Date getApplyTime() {
        return applyTime;
	}

	public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public String getBankCardNo() {
        return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
	}
	public String getApplyUserName() {
        return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
	}
	public String getDepartmentCharger() {
        return departmentCharger;
	}

	public void setDepartmentCharger(String departmentCharger) {
        this.departmentCharger = departmentCharger;
	}
	public String getOrgAddress() {
        return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
	}
	public long getApplyUserId() {
        return applyUserId;
	}

	public void setApplyUserId(long applyUserId) {
        this.applyUserId = applyUserId;
	}
	public String getOpenBankName() {
        return openBankName;
	}

	public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName;
	}
	public String getSmallAmount() {
        return smallAmount;
	}

	public void setSmallAmount(String smallAmount) {
        this.smallAmount = smallAmount;
	}
	public String getIdentifyNumber() {
        return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
	}
	public String getApplyDeptment() {
        return applyDeptment;
	}

	public void setApplyDeptment(String applyDeptment) {
        this.applyDeptment = applyDeptment;
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