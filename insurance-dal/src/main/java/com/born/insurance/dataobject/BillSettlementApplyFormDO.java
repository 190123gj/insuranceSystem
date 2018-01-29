package com.born.insurance.dataobject;

import java.util.Date;

public class BillSettlementApplyFormDO extends SimpleFormDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4756166058344789530L;
	
	private long id;

	private String businessBillId;

	private String insuranceNo;

	private String brokerRank;

	private String settlementNo;

	private String type;
	
	private String status ;

	private Date rowAddTime;

	private Date rowUpdateTime;

	public long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBusinessBillId() {
		return businessBillId;
	}

	public void setBusinessBillId(String businessBillId) {
		this.businessBillId = businessBillId;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getBrokerRank() {
		return brokerRank;
	}

	public void setBrokerRank(String brokerRank) {
		this.brokerRank = brokerRank;
	}

	public String getSettlementNo() {
		return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getRowAddTime() {
		return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}

	public Date getRowUpdateTime() {
		return rowUpdateTime;
	}

	public void setRowUpdateTime(Date rowUpdateTime) {
		this.rowUpdateTime = rowUpdateTime;
	}
	
	

}
