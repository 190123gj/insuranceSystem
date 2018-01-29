package com.born.insurance.ws.info.businessBill;

import java.util.List;

import com.born.insurance.ws.info.common.BaseToStringInfo;

public class BillPayPlanYear extends BaseToStringInfo  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7097506844597915561L;

	/**
	 * 缴费计划年度
	 */
	private String year;
	
	/**
	 * 缴费计划详情
	 */
	private List<BillPayPlanYearInfo> info;
	
	
	private int length;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<BillPayPlanYearInfo> getInfo() {
		return info;
	}

	public void setInfo(List<BillPayPlanYearInfo> info) {
		this.info = info;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	
	
	
	
	

}
