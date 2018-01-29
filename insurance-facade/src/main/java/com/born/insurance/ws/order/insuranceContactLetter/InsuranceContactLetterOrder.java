package com.born.insurance.ws.order.insuranceContactLetter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.order.base.FormOrderBase;
import com.born.insurance.ws.order.businessBillBeneficiary.BusinessBillBeneficiaryOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import com.born.insurance.ws.order.insuranceContactLetterDetail.InsuranceContactLetterDetailOrder;
import com.yjf.common.lang.util.money.Money;

public class InsuranceContactLetterOrder extends FormOrderBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -3755450110544800760L;
	/**
	* 客户
	*/	
	private long customerUserId;
	
	/**
	 * 客户证件类型
	 */
	private String customerCertType;
	
	/**
	 * 客户证件号码
	 */
	private String customerCertNo;
	
	/**
	 * 客户类型
	 */
	private String customerUserType;
	
	/**
	 * 客户性别
	 */
	private String customerUserSex;
	
	/**
	 * 客户地址
	 */
	private String customerUserAddress;
	
	/**
	 * 客户生日
	 */
	private String customerUserBirth;
	
	/**
	 * 业务员id
	 */
	private long businessUserId;

	/**
	 * 业务员的名称
	 */
	private String businessUserName;

	/**
	 * 业务员所属部门
	 */
	private String department;

	/**
	 * 客户名称
	 */
	private String customerUserName;
	
	/**
	 * 保险公司id
	 */
	private long companyUserId;
	
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
	 * 超权限审批单名称
	 */
	private String projectSetupName;
	
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
	 * 增值税信息：单位名称
	 */
	private String orgName;
	
	/**
	 * 纳税人识别号
	 */
	private String identifyNumber;
	
	/**
	 * 电话
	 */
	private String contactMobile;
	
	/**
	 * 单位地址
	 */
	private String orgAddress;
	
	/**
	 * 开户行
	 */
	private String openBankName;
	
	/**
	 * 
	 */
	private String bankCardNo;
	
	/**
	 * 是否覆盖原有信息
	 */
	private boolean isCover;
	
	/**
	 * 是否定额
	 */
	private String isQuota;
	
	/**
	 * 投标类型：寿险、非寿险
	 */
	private String insuranceType;
	
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
	private String producLevelLevel;
	
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
	
	private List<InsuranceContactCertOrder> certOrders;
	/**
	 * 保险相关信息
	 */
	private List<InsuranceCatalogInfo> catalogOrders;
	
	/**
	 * 联系函详情 （投保内容）
	 */
	private List<InsuranceContactLetterDetailOrder> insuranceContactLetterDetailOrders;
	
	/**
	 * 受益人信息
	 */
	private List<BusinessBillBeneficiaryOrder> businessBillBeneficiaryOrder;
	
	/**
	 * 保险人与被保险人
	 */
	private List<BusinessBillCustomerOrder> businessBillCustomerOrder;
 
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

	public List<InsuranceCatalogInfo> getCatalogOrders() {
		return catalogOrders;
	}

	public void setCatalogOrders(List<InsuranceCatalogInfo> catalogOrders) {
		this.catalogOrders = catalogOrders;
	}

	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
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

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getCustomerUserType() {
		return customerUserType;
	}

	public void setCustomerUserType(String customerUserType) {
		this.customerUserType = customerUserType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public boolean isCover() {
		return isCover;
	}

	public void setCover(boolean isCover) {
		this.isCover = isCover;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public List<InsuranceContactCertOrder> getCertOrders() {
		return certOrders;
	}

	public void setCertOrders(List<InsuranceContactCertOrder> certOrders) {
		this.certOrders = certOrders;
	}

	public List<InsuranceContactLetterDetailOrder> getInsuranceContactLetterDetailOrders() {
		return insuranceContactLetterDetailOrders;
	}

	public void setInsuranceContactLetterDetailOrders(
			List<InsuranceContactLetterDetailOrder> insuranceContactLetterDetailOrders) {
		this.insuranceContactLetterDetailOrders = insuranceContactLetterDetailOrders;
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

	public String getProducLevelLevel() {
		return producLevelLevel;
	}

	public void setProducLevelLevel(String producLevelLevel) {
		this.producLevelLevel = producLevelLevel;
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

	public List<BusinessBillCustomerOrder> getBusinessBillCustomerOrder() {
		return businessBillCustomerOrder;
	}

	public void setBusinessBillCustomerOrder(List<BusinessBillCustomerOrder> businessBillCustomerOrder) {
		this.businessBillCustomerOrder = businessBillCustomerOrder;
	}

	public List<BusinessBillBeneficiaryOrder> getBusinessBillBeneficiaryOrder() {
		return businessBillBeneficiaryOrder;
	}

	public void setBusinessBillBeneficiaryOrder(List<BusinessBillBeneficiaryOrder> businessBillBeneficiaryOrder) {
		this.businessBillBeneficiaryOrder = businessBillBeneficiaryOrder;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
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

	public String getCustomerUserSex() {
		return customerUserSex;
	}

	public void setCustomerUserSex(String customerUserSex) {
		this.customerUserSex = customerUserSex;
	}

	public String getCustomerUserBirth() {
		return customerUserBirth;
	}

	public void setCustomerUserBirth(String customerUserBirth) {
		this.customerUserBirth = customerUserBirth;
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
	
	
	
}	