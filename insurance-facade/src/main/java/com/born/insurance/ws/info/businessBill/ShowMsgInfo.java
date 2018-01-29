package com.born.insurance.ws.info.businessBill;

import java.util.List;

public class ShowMsgInfo {
	
	private  BrokeFeeAndCommunityInfo brokeFeeAndCommunityInfo;
	
	private List<BillPayPlanYear> billPayPlanYear;

	public BrokeFeeAndCommunityInfo getBrokeFeeAndCommunityInfo() {
		return brokeFeeAndCommunityInfo;
	}

	public void setBrokeFeeAndCommunityInfo(BrokeFeeAndCommunityInfo brokeFeeAndCommunityInfo) {
		this.brokeFeeAndCommunityInfo = brokeFeeAndCommunityInfo;
	}

	public List<BillPayPlanYear> getBillPayPlanYear() {
		return billPayPlanYear;
	}

	public void setBillPayPlanYear(List<BillPayPlanYear> billPayPlanYear) {
		this.billPayPlanYear = billPayPlanYear;
	}


	

}
