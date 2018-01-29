package com.born.insurance.ws.info.businessBill;

import java.util.List;

import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.info.common.BaseToStringInfo;

public class BrokeFeeAndCommunityInfo extends BaseToStringInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8288817824486514349L;

	/**
	 * 经纪费
	 */
	private BrokerageFeeInfo brokerageFeeInfo ;
	
	/**
	 * 经纪费明细
	 */
	private List<BrokerageFeeDetailInfo> brokerageFeeDetails;
	
	
	/**
	 * 佣金
	 */
	private CommissionInfoInfo commissionInfoInfo;
	
	/**
	 * 佣金明细
	 */
	private List<CommissionInfoDetailInfo> commissionDetails;

	public BrokerageFeeInfo getBrokerageFeeInfo() {
		return brokerageFeeInfo;
	}

	public void setBrokerageFeeInfo(BrokerageFeeInfo brokerageFeeInfo) {
		this.brokerageFeeInfo = brokerageFeeInfo;
	}

	public List<BrokerageFeeDetailInfo> getBrokerageFeeDetails() {
		return brokerageFeeDetails;
	}

	public void setBrokerageFeeDetails(List<BrokerageFeeDetailInfo> brokerageFeeDetails) {
		this.brokerageFeeDetails = brokerageFeeDetails;
	}

	public CommissionInfoInfo getCommissionInfoInfo() {
		return commissionInfoInfo;
	}

	public void setCommissionInfoInfo(CommissionInfoInfo commissionInfoInfo) {
		this.commissionInfoInfo = commissionInfoInfo;
	}

	public List<CommissionInfoDetailInfo> getCommissionDetails() {
		return commissionDetails;
	}

	public void setCommissionDetails(List<CommissionInfoDetailInfo> commissionDetails) {
		this.commissionDetails = commissionDetails;
	}
	
	

}
