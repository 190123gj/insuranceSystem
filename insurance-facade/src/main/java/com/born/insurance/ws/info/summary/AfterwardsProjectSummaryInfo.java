package com.born.insurance.ws.info.summary;

import java.util.Date;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class AfterwardsProjectSummaryInfo extends BaseToStringInfo {
	
	/**
	 * 保后汇总info
	 * @author jil
	 */
	private static final long serialVersionUID = -5338569184225793536L;
	
	private long summaryId;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String reportPeriod;
	
	private long submitManId;
	
	private String submitManName;
	
	private String submitManAccount;
	
	private int guaranteeHouseholds;
	
	private Money guaranteeReleasingAmount = new Money(0, 0);
	
	private int loanHouseholds;
	
	private Money loanReleasingAmount = new Money(0, 0);
	
	private String creditRisk;
	
	private String creditAfterCheck;
	
	private String creditAfterCheckProb;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getSummaryId() {
		return summaryId;
	}
	
	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getReportPeriod() {
		return reportPeriod;
	}
	
	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}
	
	public long getSubmitManId() {
		return submitManId;
	}
	
	public void setSubmitManId(long submitManId) {
		this.submitManId = submitManId;
	}
	
	public String getSubmitManName() {
		return submitManName;
	}
	
	public void setSubmitManName(String submitManName) {
		this.submitManName = submitManName;
	}
	
	public String getSubmitManAccount() {
		return submitManAccount;
	}
	
	public void setSubmitManAccount(String submitManAccount) {
		this.submitManAccount = submitManAccount;
	}
	
	public int getGuaranteeHouseholds() {
		return guaranteeHouseholds;
	}
	
	public void setGuaranteeHouseholds(int guaranteeHouseholds) {
		this.guaranteeHouseholds = guaranteeHouseholds;
	}
	
	public Money getGuaranteeReleasingAmount() {
		return guaranteeReleasingAmount;
	}
	
	public void setGuaranteeReleasingAmount(Money guaranteeReleasingAmount) {
		this.guaranteeReleasingAmount = guaranteeReleasingAmount;
	}
	
	public Money getLoanReleasingAmount() {
		return loanReleasingAmount;
	}
	
	public void setLoanReleasingAmount(Money loanReleasingAmount) {
		this.loanReleasingAmount = loanReleasingAmount;
	}
	
	public int getLoanHouseholds() {
		return loanHouseholds;
	}
	
	public void setLoanHouseholds(int loanHouseholds) {
		this.loanHouseholds = loanHouseholds;
	}
	
	public String getCreditRisk() {
		return creditRisk;
	}
	
	public void setCreditRisk(String creditRisk) {
		this.creditRisk = creditRisk;
	}
	
	public String getCreditAfterCheck() {
		return creditAfterCheck;
	}
	
	public void setCreditAfterCheck(String creditAfterCheck) {
		this.creditAfterCheck = creditAfterCheck;
	}
	
	public String getCreditAfterCheckProb() {
		return creditAfterCheckProb;
	}
	
	public void setCreditAfterCheckProb(String creditAfterCheckProb) {
		this.creditAfterCheckProb = creditAfterCheckProb;
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
	
}
