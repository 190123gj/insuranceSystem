package com.born.insurance.ws.order.settlementCompanyBillProcess;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class SettlementCompanyBillProcessOrder extends ProcessOrder {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1017055462625218805L;
	/**
	* 总笔数
	*/	
	private long total;
 	/**
	* 总费用
	*/	
	private Money totalAmount = new Money(0,0);
 	/**
	* id
	*/	
	private long id;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保险公司
	*/	
	private long insuranceCompanyId;
 	/**
	* 状态
	*/	
	private String status;
 	/**
	* 保险公司
	*/	
	private String insuranceCompanyName;
 	/**
	* 结算单号
	*/	
	private String billNo;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 
  	public long getTotal() {
        return total;
	}

	public void setTotal(long total) {
        this.total = total;
	}

	public Money getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getInsuranceCompanyId() {
        return insuranceCompanyId;
	}

	public void setInsuranceCompanyId(long insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
	}
	public String getStatus() {
        return status;
	}

	public void setStatus(String status) {
        this.status = status;
	}
	public String getInsuranceCompanyName() {
        return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
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