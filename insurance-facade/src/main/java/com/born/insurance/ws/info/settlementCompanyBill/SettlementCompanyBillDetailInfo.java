package com.born.insurance.ws.info.settlementCompanyBill;

import java.util.List;

public class SettlementCompanyBillDetailInfo {
	
	/**
	 * 结算单号
	 */
	private String billNo;
	
	List<SettlementCompanyBillInfo> datas;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public List<SettlementCompanyBillInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<SettlementCompanyBillInfo> datas) {
		this.datas = datas;
	}
	
	
}	