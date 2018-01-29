package com.born.insurance.ws.order.settlementInvoiceInformation;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.order.base.ProcessOrder;

public class SettlementInvoiceInformationOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -7909562822044975219L;
	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 发票号
	*/	
	private String invoiceNo;
 	/**
	* 
	*/	
	private Date rowUpdateTime;
 	/**
	* 结算单对应的发票信息id
	*/	
	private long invoiceId;
 	/**
	* 结算单号
	*/	
	private String billNo;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 发票开具时间
	*/	
	private Date invoiceIssuingTime;
 	/**
	* 物流公司名称
	*/	
	private String logisticsCompanyName;
 	/**
	* 物流单号
	*/	
	private String logisticsNumber;
 
  	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getInvoiceNo() {
        return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
	}
	public Date getRowUpdateTime() {
        return rowUpdateTime;
	}

	public void setRowUpdateTime(Date rowUpdateTime) {
        this.rowUpdateTime = rowUpdateTime;
	}
	public long getInvoiceId() {
        return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public Date getInvoiceIssuingTime() {
        return invoiceIssuingTime;
	}

	public void setInvoiceIssuingTime(Date invoiceIssuingTime) {
        this.invoiceIssuingTime = invoiceIssuingTime;
	}
	public String getLogisticsCompanyName() {
        return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
	}
	public String getLogisticsNumber() {
        return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
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