package com.born.insurance.ws.order.businessBill;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BusinessBillQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -5714887860417936652L;
	/**
	* 保单号
	*/	
	private String insuranceNo;
	
	/**
	 * 被保险人姓名
	 */
	private String billInsuredName;
	
	/**
	 * 投保人名称
	 */
	private String billCustomerName;
	
	/**
	 * 经纪人姓名
	 */
	private String insuranceBrokerName;
	
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
    

    public String getBillInsuredName() {
		return billInsuredName;
	}

	public void setBillInsuredName(String billInsuredName) {
		this.billInsuredName = billInsuredName;
	}

	public String getBillCustomerName() {
		return billCustomerName;
	}

	public void setBillCustomerName(String billCustomerName) {
		this.billCustomerName = billCustomerName;
	}

	public String getInsuranceBrokerName() {
		return insuranceBrokerName;
	}

	public void setInsuranceBrokerName(String insuranceBrokerName) {
		this.insuranceBrokerName = insuranceBrokerName;
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