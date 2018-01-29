package com.born.insurance.ws.info.businessBill;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class BusinessBillFormInfo extends FormVOInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1718990234394493864L;
	
	/**
	 * 流程表单的id
	 */
	private long formId;

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
	 * 保单的状态
	 */
	private String billStatus;
	
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
	 * 经纪费比例
	 */
	private double brokerScale;
	
	/**
	 * 佣金比例
	 */
	private double commonScale;
	
	/**
	 * 毛利率
	 */
	private double grossRate;

	/**
	 * 毛利润
	 */
	private Money grossprofit = new Money(0,0);
	
	/**
	 * 经纪费
	 */
	private Money brokerVal = new Money(0,0);

	/**
	 * 佣金
	 */
	private Money common_val = new Money(0,0);
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 保险公司
	*/	
	private String insuranceCompanyName;
	
	/**
	 * 投保申请的id
	 */
	private long letterId;
	
	/**
	 * 保险产品
	 */
	private String insuranceProduct;
	
	/**
	 * 保险险种id
	 */
	private String catalogId;
	
	/**
	 * 保险险种
	 */
	private String catalogName;
	
	
	/**
	 * 寿险产品最大的缴费期数
	 */
	private Integer maxVal;
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
	* 创建者
	*/	
	private long creatorId;
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
	* 创建者名称
	*/	
	private String creatorName;
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
	 * 投保人的id
	 */
	private int billCustomerId;
	
	/**
	 * 投保人名称
	 */
	private String billCustomerName;
	
	/**
	 * 保费
	 */
	private Money premiumAmount = new Money(0,0);
 	/**
	* 批单号
	*/	
	private String batchNo;
 	/**
	* 中介公司
	*/	
	private long agencyCompanyId;
 	/**
	* 业务员
	*/	
	private long businessUserId;
 
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
	public long getCreatorId() {
        return creatorId;
	}

	public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
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
	
	public long getLetterId() {
		return letterId;
	}

	public void setLetterId(long letterId) {
		this.letterId = letterId;
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
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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
	
    public long getBillCustomerId() {
		return billCustomerId;
	}

	public void setBillCustomerId(int billCustomerId) {
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

	public String getInsuranceProduct() {
		return insuranceProduct;
	}

	public void setInsuranceProduct(String insuranceProduct) {
		this.insuranceProduct = insuranceProduct;
	}
	
	public Integer getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(Integer maxVal) {
		this.maxVal = maxVal;
	}
	
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	

	public long getFormId() {
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

	public double getBrokerScale() {
		return brokerScale;
	}

	public void setBrokerScale(double brokerScale) {
		this.brokerScale = brokerScale;
	}

	public double getCommonScale() {
		return commonScale;
	}

	public void setCommonScale(double commonScale) {
		this.commonScale = commonScale;
	}
	
	

	public double getGrossRate() {
		return grossRate;
	}

	public void setGrossRate(double grossRate) {
		this.grossRate = grossRate;
	}



	public Money getGrossprofit() {
		return grossprofit;
	}

	public void setGrossprofit(Money grossprofit) {
		this.grossprofit = grossprofit;
	}

	public Money getBrokerVal() {
		return brokerVal;
	}

	public void setBrokerVal(Money brokerVal) {
		this.brokerVal = brokerVal;
	}

	public Money getCommon_val() {
		return common_val;
	}

	public void setCommon_val(Money common_val) {
		this.common_val = common_val;
	}

	
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
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