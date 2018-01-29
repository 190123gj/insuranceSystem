package com.born.insurance.ws.order.priceContactLetterReportPrice;

import java.util.List;

import com.yjf.common.lang.util.money.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;

public class PriceContactLetterCompanyReportPriceOrder extends ProcessOrder {

	private long id;

	private long customerUserId;

	private String customerUserName;

	private long reportPriceId;

	private Money premiumAmount = new Money(0, 0);

	private Money expenseAmount = new Money(0, 0);

	private Money brokerAmount = new Money(0, 0);

	private double brokerAmountRate;

	private String createDate;

	private String differenceContent;

	private String specialAgreement;

	private String quotationPrerequisite;

	private String expiryDate;

	private long contactUserId;

	private String contactUserName;

	private String contactUserMobile;

	private String contactUserEmail;


	private String remark;

	private long version;

	private String ext;

	private String askCompanyOrdersData;

	public long getReportPriceId() {
		return reportPriceId;
	}

	public void setReportPriceId(long reportPriceId) {
		this.reportPriceId = reportPriceId;
	}

	private List<PriceContactLetterCompanyReportPriceDetailOrder> detailOrders;



	public double getBrokerAmountRate() {
		return brokerAmountRate;
	}

	public void setBrokerAmountRate(double brokerAmountRate) {
		this.brokerAmountRate = brokerAmountRate;
	}


	public String getContactUserEmail() {
		return contactUserEmail;
	}

	public void setContactUserEmail(String contactUserEmail) {
		this.contactUserEmail = contactUserEmail;
	}

	public long getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(long contactUserId) {
		this.contactUserId = contactUserId;
	}

	public String getContactUserMobile() {
		return contactUserMobile;
	}

	public void setContactUserMobile(String contactUserMobile) {
		this.contactUserMobile = contactUserMobile;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
		this.customerUserId = customerUserId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public List<PriceContactLetterCompanyReportPriceDetailOrder> getDetailOrders() {
		return detailOrders;
	}

	public void setDetailOrders(List<PriceContactLetterCompanyReportPriceDetailOrder> detailOrders) {
		this.detailOrders = detailOrders;
	}

	public String getDifferenceContent() {
		return differenceContent;
	}

	public void setDifferenceContent(String differenceContent) {
		this.differenceContent = differenceContent;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getQuotationPrerequisite() {
		return quotationPrerequisite;
	}

	public void setQuotationPrerequisite(String quotationPrerequisite) {
		this.quotationPrerequisite = quotationPrerequisite;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpecialAgreement() {
		return specialAgreement;
	}

	public void setSpecialAgreement(String specialAgreement) {
		this.specialAgreement = specialAgreement;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getAskCompanyOrdersData() {
		return askCompanyOrdersData;
	}

	public void setAskCompanyOrdersData(String askCompanyOrdersData) {
		this.askCompanyOrdersData = askCompanyOrdersData;
	}

	public Money getBrokerAmount() {
		return brokerAmount;
	}

	public void setBrokerAmount(Money brokerAmount) {
		this.brokerAmount = brokerAmount;
	}

	public Money getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public Money getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Money expenseAmount) {
		this.expenseAmount = expenseAmount;
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