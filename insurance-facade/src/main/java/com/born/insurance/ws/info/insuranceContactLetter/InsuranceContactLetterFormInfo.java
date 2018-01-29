package com.born.insurance.ws.info.insuranceContactLetter;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.FormVOInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;

public class InsuranceContactLetterFormInfo extends FormVOInfo {
				
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
	 * 客户手机号
	 */
	private String customerUserPhone;
	
	/**
	 * 该投保申请单是不是已经完成承保(YES NO)
	 */
	private String letterStatus ;
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
	 * 保险公司名称
	 */
	private String companyUserName;
	
	/**
	 * 保险产品id
	 */
	private long productId;
	
	/**
	 * 保险产品
	 */
	private String productName;
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
	 * 投保类型
	 */
	private String lifeInsuranceType;
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
	 * 所保险的产品信息
	 */
	private String insuranceProduct;
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
	 * 保险
	 */
	private List<InsuranceCatalogInfo> catalogOrders;
 
 
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

	public List<InsuranceCatalogInfo> getCatalogOrders() {
		return catalogOrders;
	}

	public void setCatalogOrders(List<InsuranceCatalogInfo> catalogOrders) {
		this.catalogOrders = catalogOrders;
	}
	
	public String getLifeInsuranceType() {
		return lifeInsuranceType;
	}

	public void setLifeInsuranceType(String lifeInsuranceType) {
		this.lifeInsuranceType = lifeInsuranceType;
	}
	
	public String getInsuranceProduct() {
		return insuranceProduct;
	}

	public void setInsuranceProduct(String insuranceProduct) {
		this.insuranceProduct = insuranceProduct;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getCustomerUserPhone() {
		return customerUserPhone;
	}

	public void setCustomerUserPhone(String customerUserPhone) {
		this.customerUserPhone = customerUserPhone;
	}
	
	public String getLetterStatus() {
		return letterStatus;
	}

	public void setLetterStatus(String letterStatus) {
		this.letterStatus = letterStatus;
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