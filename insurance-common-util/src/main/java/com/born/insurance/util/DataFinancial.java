package com.born.insurance.util;

import java.util.Map;

/**
 * 
 * 财务评价整页各表的数据
 * 
 * 
 * @author lirz
 * 
 * 2016-4-6 上午9:30:39
 */
public class DataFinancial {
	//导入的数据
	private Map<String, String> data;
	//直接可以从excel导入的数据
	private DataSheet balance; //资产负债表
	private DataSheet profit; //利润及利润分配表
	private DataSheet cashFlow; //现金流量表
	//分析数据表(由上面三张表计算得出)
	private DataSheet dpaAnalyze; //偿债能力分析(debt paying ability)
	private DataSheet ocAnalyze; //运营能力分析(operating capability)
	private DataSheet ecAnalyze; //盈利能力分析(earning capacity)
	private DataSheet cfAnalyze; //现金流分析(cash flow)
	
	public Map<String, String> getData() {
		return this.data;
	}
	
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	public DataSheet getBalance() {
		return this.balance;
	}
	
	public void setBalance(DataSheet balance) {
		this.balance = balance;
	}
	
	public DataSheet getProfit() {
		return this.profit;
	}
	
	public void setProfit(DataSheet profit) {
		this.profit = profit;
	}
	
	public DataSheet getCashFlow() {
		return this.cashFlow;
	}
	
	public void setCashFlow(DataSheet cashFlow) {
		this.cashFlow = cashFlow;
	}
	
	public DataSheet getDpaAnalyze() {
		return this.dpaAnalyze;
	}
	
	public void setDpaAnalyze(DataSheet dpaAnalyze) {
		this.dpaAnalyze = dpaAnalyze;
	}
	
	public DataSheet getOcAnalyze() {
		return this.ocAnalyze;
	}
	
	public void setOcAnalyze(DataSheet ocAnalyze) {
		this.ocAnalyze = ocAnalyze;
	}
	
	public DataSheet getEcAnalyze() {
		return this.ecAnalyze;
	}
	
	public void setEcAnalyze(DataSheet ecAnalyze) {
		this.ecAnalyze = ecAnalyze;
	}
	
	public DataSheet getCfAnalyze() {
		return this.cfAnalyze;
	}
	
	public void setCfAnalyze(DataSheet cfAnalyze) {
		this.cfAnalyze = cfAnalyze;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataFinancial [data=");
		builder.append(data);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", profit=");
		builder.append(profit);
		builder.append(", cashFlow=");
		builder.append(cashFlow);
		builder.append(", dpaAnalyze=");
		builder.append(dpaAnalyze);
		builder.append(", ocAnalyze=");
		builder.append(ocAnalyze);
		builder.append(", ecAnalyze=");
		builder.append(ecAnalyze);
		builder.append(", cfAnalyze=");
		builder.append(cfAnalyze);
		builder.append("]");
		return builder.toString();
	}
	
}
