package com.born.insurance.ws.info.settlementCompanyBillProcess;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.invoiceRequisition.InvoiceRequisitionInfo;
import com.born.insurance.ws.info.settlementInvoiceInformation.SettlementInvoiceInformationInfo;
import com.yjf.common.lang.util.money.Money;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class SettlementCompanyBillProcessInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -7776816128707242924L;
	
	private long id;

	private String settlementCompanyIds;

	private String billNo;

	private long insuranceCompanyId;

	private String insuranceCompanyName;

	private  Money totalAmount = new Money(0,0); ;

	private long total;

	private String status;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	private boolean invoiceRequisitionInfo;
	
	private SettlementInvoiceInformationInfo settlementInvoiceInformationInfo;
	

    public SettlementInvoiceInformationInfo getSettlementInvoiceInformationInfo() {
		return settlementInvoiceInformationInfo;
	}

	public void setSettlementInvoiceInformationInfo(SettlementInvoiceInformationInfo settlementInvoiceInformationInfo) {
		this.settlementInvoiceInformationInfo = settlementInvoiceInformationInfo;
	}


	public boolean isInvoiceRequisitionInfo() {
		return invoiceRequisitionInfo;
	}

	public void setInvoiceRequisitionInfo(boolean invoiceRequisitionInfo) {
		this.invoiceRequisitionInfo = invoiceRequisitionInfo;
	}

	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getSettlementCompanyIds() {
		return settlementCompanyIds;
	}




	public void setSettlementCompanyIds(String settlementCompanyIds) {
		this.settlementCompanyIds = settlementCompanyIds;
	}




	public String getBillNo() {
		return billNo;
	}




	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}




	public long getInsuranceCompanyId() {
		return insuranceCompanyId;
	}




	public void setInsuranceCompanyId(long insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}




	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}




	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}


	public Money getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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