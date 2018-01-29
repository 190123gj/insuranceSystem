package com.born.insurance.ws.order.settlementCompanyBillProcess;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;
import com.yjf.common.lang.util.money.Money;

public class SettlementCompanyBillProcessQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1930175909088124624L;

	/**
	 * 经纪费待结算记录
	 */
	private String settlementCompanyId;
	
	/**
	 * 结算单号
	 */
	private String billNo;
	
	/**
	 * 结算单记录状态
	 */
	private String status;
	
	/**
	 * 开始时间
	 */
	private String beginDate;
	
	/**
	 * 结束时间
	 */
	private String endDate;
	
	/**
	 * 保险公司
	 */
	private String insuranceCompanyId;
	
	/**
	 * 保险公司名称
	 */
	private String insuranceCompanyName;
	
	/**
	 * 结算金额
	 */
	private Money brokerAmount = new Money(0,0); 
	

	public String getInsuranceCompanyId() {
		return insuranceCompanyId;
	}


	public void setInsuranceCompanyId(String insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}

	
	public Money getBrokerAmount() {
		return brokerAmount;
	}


	public void setBrokerAmount(Money brokerAmount) {
		this.brokerAmount = brokerAmount;
	}


	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	

	public String getSettlementCompanyId() {
		return settlementCompanyId;
	}


	public void setSettlementCompanyId(String settlementCompanyId) {
		this.settlementCompanyId = settlementCompanyId;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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