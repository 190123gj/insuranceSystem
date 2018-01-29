package com.born.insurance.ws.order.insuranceBillRenewal;

import java.util.List;

import com.born.insurance.ws.order.base.ProcessOrder;

public class InsuranceBillRenewal  extends ProcessOrder{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8373854232647433585L;

	/**
	 * 缴费计划年度
	 */
	private String year;
	
	private List<InsuranceBillRenewalRecord> list ;
	
	private int length ;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<InsuranceBillRenewalRecord> getList() {
		return list;
	}

	public void setList(List<InsuranceBillRenewalRecord> list) {
		this.list = list;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	
	
	
	
	

}
