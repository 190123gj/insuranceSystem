package com.born.insurance.ws.info.insuranceBillRenewal;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class InsuranceBillRenewalInfo extends BaseToStringInfo {
				
	/**
	 * 
	 */
	private static final long serialVersionUID = 2431026062580328785L;

	private long billRenewalId;

	private long businessBillId;

	private String billNo;
	
	private Date planPayDate;
	
	private Date actualPayDate;

	private String paymentMethod;

	private String payer;

	private String payee;

	private Money money = new Money(0,0);

	private Date tradingTime;

	private String agent;

	private String postNo;

	private String merchantId;

	private String tradingCode;

	private String operater;

	private String transferSerialNumber;

	private String payBank;

	private String payAccount;

	private String receivablesBank;

	private String receivablesAccount;

	private String checkNumber;

	private String platformName;

	private long period;

	private Money premiumAmount = new Money(0,0) ;

	private long creatorId;

	private String creatorName;

	private Date rawAddTime;

	private Date rawUpdateTime;
    

    public long getBillRenewalId() {
		return billRenewalId;
	}


	public void setBillRenewalId(long billRenewalId) {
		this.billRenewalId = billRenewalId;
	}


	public long getBusinessBillId() {
		return businessBillId;
	}


	public void setBusinessBillId(long businessBillId) {
		this.businessBillId = businessBillId;
	}


	public String getBillNo() {
		return billNo;
	}


	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public String getPayer() {
		return payer;
	}


	public void setPayer(String payer) {
		this.payer = payer;
	}


	public String getPayee() {
		return payee;
	}


	public void setPayee(String payee) {
		this.payee = payee;
	}


	public Date getTradingTime() {
		return tradingTime;
	}


	public void setTradingTime(Date tradingTime) {
		this.tradingTime = tradingTime;
	}


	public String getAgent() {
		return agent;
	}


	public void setAgent(String agent) {
		this.agent = agent;
	}


	public String getPostNo() {
		return postNo;
	}


	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	public String getTradingCode() {
		return tradingCode;
	}


	public void setTradingCode(String tradingCode) {
		this.tradingCode = tradingCode;
	}


	public String getOperater() {
		return operater;
	}


	public void setOperater(String operater) {
		this.operater = operater;
	}


	public String getTransferSerialNumber() {
		return transferSerialNumber;
	}


	public void setTransferSerialNumber(String transferSerialNumber) {
		this.transferSerialNumber = transferSerialNumber;
	}


	public String getPayBank() {
		return payBank;
	}


	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}


	public String getPayAccount() {
		return payAccount;
	}


	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}


	public String getReceivablesBank() {
		return receivablesBank;
	}


	public void setReceivablesBank(String receivablesBank) {
		this.receivablesBank = receivablesBank;
	}


	public String getReceivablesAccount() {
		return receivablesAccount;
	}


	public void setReceivablesAccount(String receivablesAccount) {
		this.receivablesAccount = receivablesAccount;
	}


	public String getCheckNumber() {
		return checkNumber;
	}


	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}


	public String getPlatformName() {
		return platformName;
	}


	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}


	public long getPeriod() {
		return period;
	}


	public void setPeriod(long period) {
		this.period = period;
	}


	public long getCreatorId() {
		return creatorId;
	}


	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}


	public String getCreatorName() {
		return creatorName;
	}


	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}


	public Date getRawAddTime() {
		return rawAddTime;
	}


	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}


	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}


	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	

	public Money getMoney() {
		return money;
	}


	public void setMoney(Money money) {
		this.money = money;
	}


	public Money getPremiumAmount() {
		return premiumAmount;
	}


	public void setPremiumAmount(Money premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
	

	public Date getPlanPayDate() {
		return planPayDate;
	}


	public void setPlanPayDate(Date planPayDate) {
		this.planPayDate = planPayDate;
	}


	public Date getActualPayDate() {
		return actualPayDate;
	}


	public void setActualPayDate(Date actualPayDate) {
		this.actualPayDate = actualPayDate;
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