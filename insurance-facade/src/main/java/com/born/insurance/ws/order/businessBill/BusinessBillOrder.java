package com.born.insurance.ws.order.businessBill;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.FormOrderBase;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeOrder;
import com.born.insurance.ws.order.brokerageFeeDetail.BrokerageFeeDetailOrder;
import com.born.insurance.ws.order.businessBillBeneficiary.BusinessBillBeneficiaryOrder;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoOrder;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanOrder;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoOrder;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforOrder;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoOrder;
import com.born.insurance.ws.order.commissionInfoDetail.CommissionInfoDetailOrder;
import com.yjf.common.lang.util.money.Money;

public class BusinessBillOrder extends FormOrderBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1881634254268555048L;
	

	/**
	 * 业务类型（online 线上 offline 线下）
	 */
	private String insuranceOfType;
	
	/**
	 * 所属部门
	 */
	private String insuranceDepart;

	/**
	 * 团队
	 */
	private String insuranceTeam;

	/**
	 * 经纪人工号
	 */
	private String insuranceBrokerNo;

	/**
	 * 经纪人姓名
	 */
	private String insuranceBrokerName;
	
	/**
	 * 被保险人id
	 */
	private long billInsuredId;

	/**
	 * 被保险人姓名
	 */
	private String billInsuredName;

	/**
	 * 险种类别id
	 */
	private long insuranceTypeId;

	/**
	 * 险种类别名称
	 */
	private String insuranceTypeName;

	/**
	 * 险种的id
	 */
	private long insuranceCatalogId;

	/**
	 * 险种名称
	 */
	private String insuranceCatalogName;
	
	/**
	 * 保额
	 */
	private Money insuranceAmount = new Money(0, 0);
	/**
	* 保单号
	*/	
	private String insuranceNo;
 	/**
	* 状态
	*/	
	private String status;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 保险公司
	*/	
	private String insuranceCompanyName;
 	/**
	* 保险止期
	*/	
	private Date endDate;
 	/**
	* 原保单Id
	*/	
	private long sorceBusinessBillId;
 	/**
	* 保险起期
	*/	
	private Date beginDate;
 	/**
	* 
	*/	
	private Date rawUpdateTime;

 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 中介公司
	*/	
	private String agencyCompanyName;
 	/**
	* business_bill_id
	*/	
	private long businessBillId;
	
	/**
	 * 投保申请表的id
	 */
	private long letterId;
	
	/**
	 * 保费
	 */
	private Money premiumAmount = new Money(0,0);
	
	/**
	 * 投保人
	 */
	private long billCustomerId;
	
	/**
	 * 投保人名称
	 */
	private String billCustomerName;
 	/**
	* 保险公司
	*/	
	private long insuranceCompanyId;
 	/**
	* 询价单号
	*/	
	private String priceNo;
 	/**
	* 投标日期
	*/	
	private Date insuranceDate;
 	/**
	* 业务员
	*/	
	private String businessUserName;
	
	/**
	 * 业务员证件号码
	 */
	private String businessUserCertNo;
	
	/**
	 * 业务员手机号
	 */
	private String businessUserMobile;
	
	/**
	 *  业务员证件类型
	 */
	private String businessUserType;

 	/**
	* 续保单号
	*/	
	private String renewalNo;
 	/**
	* 业务单号
	*/	
	private String billNo;
 	/**
	* 发票号
	*/	
	private String invoiceNo;
 	/**
	* 批单号
	*/	
	private String batchNo;
	
	/**
	 * 车牌号
	 */
	private String plateNumber ;
	
	/**
	 * 期数
	 */
	private String periods;
 	/**
	* 中介公司
	*/	
	private long agencyCompanyId;
 	/**
	* 业务员
	*/	
	private long businessUserId;
	
	/**
	 * 经纪费
	 */
	private BrokerageFeeOrder brokerageFeeOrder;
	
	/**
	 * 经纪费明细
	 */
	private List<BrokerageFeeDetailOrder> brokerageFeeDetailOrder;
	
	/**
	 * 佣金明细
	 */
	private CommissionInfoOrder commissionInfoOrder;
	
	/**
	 * 佣金明细
	 */
	private List<CommissionInfoDetailOrder>  commissionInfoDetailOrder;
	
	/**
	 * 共保信息
	 */
	private List<BusinessBillCoinsInfoOrder> businessBillCoinsInfoOrders;
	
	/**
	 * 再保信息
	 */
	private List<BusinessBillReinsInfoOrder> businessBillReinsInfoOrders;
	
	/**
	 * 受益人信息
	 */
	private List<BusinessBillBeneficiaryOrder> businessBillBeneficiaryOrder;
	
	/**
	 * 费用信息
	 */
	private List<BusinessBillCostInfoOrder> businessBillCostInfoOrders;
	
	/**
	 * 保险产品
	 */
	private List<BusinessBillUnderInforOrder> businessBillUnderInforOrders;
	
	/**
	 * 缴费计划
	 */
	private List<BusinessBillPayPlanOrder> businessBillPayPlanOrders;
	
	/**
	 * 保险人与被保险人
	 */
	private List<BusinessBillCustomerOrder> businessBillCustomerOrder;
	
 
  	public String getInsuranceNo() {
        return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
	}
	public String getStatus() {
        return status;
	}

	public void setStatus(String status) {
        this.status = status;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public String getInsuranceCompanyName() {
        return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
	}
	public Date getEndDate() {
        return endDate;
	}

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
	}
	public long getSorceBusinessBillId() {
        return sorceBusinessBillId;
	}

	public void setSorceBusinessBillId(long sorceBusinessBillId) {
        this.sorceBusinessBillId = sorceBusinessBillId;
	}
	public Date getBeginDate() {
        return beginDate;
	}

	public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}

	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getAgencyCompanyName() {
        return agencyCompanyName;
	}

	public void setAgencyCompanyName(String agencyCompanyName) {
        this.agencyCompanyName = agencyCompanyName;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public long getInsuranceCompanyId() {
        return insuranceCompanyId;
	}

	public void setInsuranceCompanyId(long insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
	}
	public String getPriceNo() {
        return priceNo;
	}

	public void setPriceNo(String priceNo) {
        this.priceNo = priceNo;
	}
	public Date getInsuranceDate() {
        return insuranceDate;
	}

	public void setInsuranceDate(Date insuranceDate) {
        this.insuranceDate = insuranceDate;
	}
	public String getBusinessUserName() {
        return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
        this.businessUserName = businessUserName;
	}



	public String getRenewalNo() {
        return renewalNo;
	}

	public void setRenewalNo(String renewalNo) {
        this.renewalNo = renewalNo;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public String getInvoiceNo() {
        return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
	}
	public String getBatchNo() {
        return batchNo;
	}

	public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
	}
	public long getAgencyCompanyId() {
        return agencyCompanyId;
	}

	public void setAgencyCompanyId(long agencyCompanyId) {
        this.agencyCompanyId = agencyCompanyId;
	}
	public long getBusinessUserId() {
        return businessUserId;
	}

	public void setBusinessUserId(long businessUserId) {
        this.businessUserId = businessUserId;
	}
   
	public List<BusinessBillCoinsInfoOrder> getBusinessBillCoinsInfoOrders() {
		return businessBillCoinsInfoOrders;
	}

	public void setBusinessBillCoinsInfoOrders(List<BusinessBillCoinsInfoOrder> businessBillCoinsInfoOrders) {
		this.businessBillCoinsInfoOrders = businessBillCoinsInfoOrders;
	}

	public List<BusinessBillReinsInfoOrder> getBusinessBillReinsInfoOrders() {
		return businessBillReinsInfoOrders;
	}

	public void setBusinessBillReinsInfoOrders(List<BusinessBillReinsInfoOrder> businessBillReinsInfoOrders) {
		this.businessBillReinsInfoOrders = businessBillReinsInfoOrders;
	}
	
	public List<BusinessBillBeneficiaryOrder> getBusinessBillBeneficiaryOrder() {
		return businessBillBeneficiaryOrder;
	}

	public void setBusinessBillBeneficiaryOrder(List<BusinessBillBeneficiaryOrder> businessBillBeneficiaryOrder) {
		this.businessBillBeneficiaryOrder = businessBillBeneficiaryOrder;
	}

	public List<BusinessBillCostInfoOrder> getBusinessBillCostInfoOrders() {
		return businessBillCostInfoOrders;
	}

	public void setBusinessBillCostInfoOrders(List<BusinessBillCostInfoOrder> businessBillCostInfoOrders) {
		this.businessBillCostInfoOrders = businessBillCostInfoOrders;
	}

	public List<BusinessBillUnderInforOrder> getBusinessBillUnderInforOrders() {
		return businessBillUnderInforOrders;
	}

	public void setBusinessBillUnderInforOrders(List<BusinessBillUnderInforOrder> businessBillUnderInforOrders) {
		this.businessBillUnderInforOrders = businessBillUnderInforOrders;
	}

	public List<BusinessBillPayPlanOrder> getBusinessBillPayPlanOrders() {
		return businessBillPayPlanOrders;
	}

	public void setBusinessBillPayPlanOrders(List<BusinessBillPayPlanOrder> businessBillPayPlanOrders) {
		this.businessBillPayPlanOrders = businessBillPayPlanOrders;
	}
	public List<BusinessBillCustomerOrder> getBusinessBillCustomerOrder() {
		return businessBillCustomerOrder;
	}

	public void setBusinessBillCustomerOrder(List<BusinessBillCustomerOrder> businessBillCustomerOrder) {
		this.businessBillCustomerOrder = businessBillCustomerOrder;
	}
	
	public long getLetterId() {
		return letterId;
	}

	public void setLetterId(long letterId) {
		this.letterId = letterId;
	}
	
	public long getBillCustomerId() {
		return billCustomerId;
	}

	public void setBillCustomerId(long billCustomerId) {
		this.billCustomerId = billCustomerId;
	}

	public String getBillCustomerName() {
		return billCustomerName;
	}

	public void setBillCustomerName(String billCustomerName) {
		this.billCustomerName = billCustomerName;
	}
	
	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public BrokerageFeeOrder getBrokerageFeeOrder() {
		return brokerageFeeOrder;
	}

	public void setBrokerageFeeOrder(BrokerageFeeOrder brokerageFeeOrder) {
		this.brokerageFeeOrder = brokerageFeeOrder;
	}

	public List<BrokerageFeeDetailOrder> getBrokerageFeeDetailOrder() {
		return brokerageFeeDetailOrder;
	}

	public void setBrokerageFeeDetailOrder(List<BrokerageFeeDetailOrder> brokerageFeeDetailOrder) {
		this.brokerageFeeDetailOrder = brokerageFeeDetailOrder;
	}

	public CommissionInfoOrder getCommissionInfoOrder() {
		return commissionInfoOrder;
	}

	public void setCommissionInfoOrder(CommissionInfoOrder commissionInfoOrder) {
		this.commissionInfoOrder = commissionInfoOrder;
	}

	public List<CommissionInfoDetailOrder> getCommissionInfoDetailOrder() {
		return commissionInfoDetailOrder;
	}

	public void setCommissionInfoDetailOrder(List<CommissionInfoDetailOrder> commissionInfoDetailOrder) {
		this.commissionInfoDetailOrder = commissionInfoDetailOrder;
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

	public String getBusinessUserType() {
		return businessUserType;
	}

	public void setBusinessUserType(String businessUserType) {
		this.businessUserType = businessUserType;
	}
	

	public Long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getInsuranceOfType() {
		return insuranceOfType;
	}

	public void setInsuranceOfType(String insuranceOfType) {
		this.insuranceOfType = insuranceOfType;
	}

	public String getInsuranceDepart() {
		return insuranceDepart;
	}

	public void setInsuranceDepart(String insuranceDepart) {
		this.insuranceDepart = insuranceDepart;
	}

	public String getInsuranceTeam() {
		return insuranceTeam;
	}

	public void setInsuranceTeam(String insuranceTeam) {
		this.insuranceTeam = insuranceTeam;
	}

	public String getInsuranceBrokerNo() {
		return insuranceBrokerNo;
	}

	public void setInsuranceBrokerNo(String insuranceBrokerNo) {
		this.insuranceBrokerNo = insuranceBrokerNo;
	}

	public String getInsuranceBrokerName() {
		return insuranceBrokerName;
	}

	public void setInsuranceBrokerName(String insuranceBrokerName) {
		this.insuranceBrokerName = insuranceBrokerName;
	}

	public long getBillInsuredId() {
		return billInsuredId;
	}

	public void setBillInsuredId(long billInsuredId) {
		this.billInsuredId = billInsuredId;
	}

	public String getBillInsuredName() {
		return billInsuredName;
	}

	public void setBillInsuredName(String billInsuredName) {
		this.billInsuredName = billInsuredName;
	}

	public long getInsuranceTypeId() {
		return insuranceTypeId;
	}

	public void setInsuranceTypeId(long insuranceTypeId) {
		this.insuranceTypeId = insuranceTypeId;
	}

	public String getInsuranceTypeName() {
		return insuranceTypeName;
	}

	public void setInsuranceTypeName(String insuranceTypeName) {
		this.insuranceTypeName = insuranceTypeName;
	}

	public long getInsuranceCatalogId() {
		return insuranceCatalogId;
	}

	public void setInsuranceCatalogId(long insuranceCatalogId) {
		this.insuranceCatalogId = insuranceCatalogId;
	}

	public String getInsuranceCatalogName() {
		return insuranceCatalogName;
	}

	public void setInsuranceCatalogName(String insuranceCatalogName) {
		this.insuranceCatalogName = insuranceCatalogName;
	}

	public Money getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(Money insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
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