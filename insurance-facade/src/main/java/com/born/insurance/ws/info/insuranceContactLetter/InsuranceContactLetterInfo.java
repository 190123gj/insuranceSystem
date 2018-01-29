package com.born.insurance.ws.info.insuranceContactLetter;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import java.util.List;

public class InsuranceContactLetterInfo extends BaseToStringInfo {
				
	/**
	 * 
	 */
	private static final long serialVersionUID = -8706252538475499566L;
	/**
	* 客户
	*/	
	private long customerUserId;
	/**
	 * 客户名称
	 */
	private String customerUserName;
	
	/**
	 * 客户证件类型
	 */
	private String customerCertType;
	
	/**
	 * 客户证件号码
	 */
	private String customerCertNo;
	
	/**
	 * 客户的类型
	 */
	private String customerUserType;
	
	/**
	 * 客户生日
	 */
	private String customerUserBirth;
	
	/**
	 * 客户性别
	 */
	private String customerUserSex;
	
	/**
	 * 客户所属地址
	 */
	private String customerUserAddress;
	
	/**
	 * 业务员的id
	 */
	private long businessUserId;
	
	/**
	 * 业务员
	 */
	private String businessUserName;
	
	/**
	 * 业务员所属部门
	 */
	private String department;
	
	/**
	 * 保险公司id
	 */
	private long companyUserId;
	
	/**
	 * 表单的id
	 */
	private long formId;
	
	/**
	 * 询价单id
	 */
	private long priceContactId;
	
	/**
	 * 询价方案名称
	 */
	private String priceContactName;
	
	/**
	 * 询价单编号
	 */
	private String priceContactNo;
	
	/**
	 * 超权限审批id
	 */
	private long projectSetupId;
	
	/**
	 * 超权限审批名称
	 */
	private String projectSetupName;
	
	/**
	 * 比例类型
	 */
	private String proportionType;
	
	/**
	 * 占费比例
	 */
	private Double proportion;
	
	/**
	 * 保险公司名称
	 */
	private String companyUserName;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 收件人
	*/	
	private String recipients;
 	/**
	* 结束时间
	*/	
	private Date endDate;
 	/**
	* 开始时间
	*/	
	private Date beginDate;
 	/**
	* 类型：直接投标、询价投标
	*/	
	private String type;
 	/**
	* 
	*/	
	private Date rawUpdateTime;

 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保险项目
	*/	
	private String project;
 	/**
	* 地址
	*/	
	private String address;
 	/**
	* 单位
	*/	
	private String company;
 	/**
	* id
	*/	
	private long letterId;
 	/**
	* 是否开发票
	*/	
	private String isInvoice;

 	/**
	* 联系电话
	*/	
	private String mobile;
	
	/**
	 * 是否定额产品投保
	 */
	private String isQuota;
	
	/**
	 * 业务单号
	 */
	private String billNo;
	
	/**
	 * 投标类型
	 */
	private String insuranceType;
	
	/**
	 * 保险
	 */
	private List<InsuranceCatalogInfo> catalogOrders;
	
	/**
	 * 保险产品id
	 */
	private long productId;
	
	/**
	 * 保险产品名称
	 */
	private String productName;
	
	/**
	 * 档次id
	 */
	private long productLevelId;	
	
	/**
	 * 档次名称
	 */
	private String productLevelLevel;
	
	/**
	 * 保费
	 */
	private Money premiumAmount = new Money(0,0);
	
	/**
	 * 是否随车销售
	 */
	private String isCarSales;
	
	/**
	 * 车牌号
	 */
	private String plateNumber;
	
	/**
	 * 保障期限
	 */
	private String guaranteePeriod;
	
	/**
	 * 增值税相关信息
	 */
	private ValueTaxInfo valueTaxInfo;
 
 
  	public long getCustomerUserId() {
        return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
        this.customerUserId = customerUserId;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public String getRecipients() {
        return recipients;
	}

	public void setRecipients(String recipients) {
        this.recipients = recipients;
	}
	public Date getEndDate() {
        return endDate;
	}

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
	}
	public Date getBeginDate() {
        return beginDate;
	}

	public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
	}
	public String getType() {
        return type;
	}

	public void setType(String type) {
        this.type = type;
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
	public String getProject() {
        return project;
	}

	public void setProject(String project) {
        this.project = project;
	}
	public String getAddress() {
        return address;
	}

	public void setAddress(String address) {
        this.address = address;
	}
	public String getCompany() {
        return company;
	}

	public void setCompany(String company) {
        this.company = company;
	}
	public long getLetterId() {
        return letterId;
	}

	public void setLetterId(long letterId) {
        this.letterId = letterId;
	}
	public String getIsInvoice() {
        return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
	}
	
	public String getMobile() {
        return mobile;
	}

	public void setMobile(String mobile) {
        this.mobile = mobile;
	}
	
    public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public long getCompanyUserId() {
		return companyUserId;
	}

	public void setCompanyUserId(long companyUserId) {
		this.companyUserId = companyUserId;
	}

	public long getPriceContactId() {
		return priceContactId;
	}

	public void setPriceContactId(long priceContactId) {
		this.priceContactId = priceContactId;
	}

	public String getPriceContactName() {
		return priceContactName;
	}

	public void setPriceContactName(String priceContactName) {
		this.priceContactName = priceContactName;
	}

	public String getPriceContactNo() {
		return priceContactNo;
	}

	public void setPriceContactNo(String priceContactNo) {
		this.priceContactNo = priceContactNo;
	}

	public String getCompanyUserName() {
		return companyUserName;
	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}
	
	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public List<InsuranceCatalogInfo> getCatalogOrders() {
		return catalogOrders;
	}

	public void setCatalogOrders(List<InsuranceCatalogInfo> catalogOrders) {
		this.catalogOrders = catalogOrders;
	}
	
	public ValueTaxInfo getValueTaxInfo() {
		return valueTaxInfo;
	}

	public void setValueTaxInfo(ValueTaxInfo valueTaxInfo) {
		this.valueTaxInfo = valueTaxInfo;
	}
	
	public String getCustomerUserType() {
		return customerUserType;
	}

	public void setCustomerUserType(String customerUserType) {
		this.customerUserType = customerUserType;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getProductLevelId() {
		return productLevelId;
	}

	public void setProductLevelId(long productLevelId) {
		this.productLevelId = productLevelId;
	}

	

	public String getProductLevelLevel() {
		return productLevelLevel;
	}

	public void setProductLevelLevel(String productLevelLevel) {
		this.productLevelLevel = productLevelLevel;
	}


	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getIsCarSales() {
		return isCarSales;
	}

	public void setIsCarSales(String isCarSales) {
		this.isCarSales = isCarSales;
	}

	public String getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(String guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	public long getProjectSetupId() {
		return projectSetupId;
	}

	public void setProjectSetupId(long projectSetupId) {
		this.projectSetupId = projectSetupId;
	}

	public String getProjectSetupName() {
		return projectSetupName;
	}

	public void setProjectSetupName(String projectSetupName) {
		this.projectSetupName = projectSetupName;
	}
	

	public long getBusinessUserId() {
		return businessUserId;
	}

	public void setBusinessUserId(long businessUserId) {
		this.businessUserId = businessUserId;
	}

	public String getBusinessUserName() {
		return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	

	public String getProportionType() {
		return proportionType;
	}

	public void setProportionType(String proportionType) {
		this.proportionType = proportionType;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	

	public String getCustomerUserBirth() {
		return customerUserBirth;
	}

	public void setCustomerUserBirth(String customerUserBirth) {
		this.customerUserBirth = customerUserBirth;
	}

	public String getCustomerUserSex() {
		return customerUserSex;
	}

	public void setCustomerUserSex(String customerUserSex) {
		this.customerUserSex = customerUserSex;
	}
	
	public String getCustomerUserAddress() {
		return customerUserAddress;
	}

	public void setCustomerUserAddress(String customerUserAddress) {
		this.customerUserAddress = customerUserAddress;
	}
	

	public String getCustomerCertType() {
		return customerCertType;
	}

	public void setCustomerCertType(String customerCertType) {
		this.customerCertType = customerCertType;
	}

	public String getCustomerCertNo() {
		return customerCertNo;
	}

	public void setCustomerCertNo(String customerCertNo) {
		this.customerCertNo = customerCertNo;
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