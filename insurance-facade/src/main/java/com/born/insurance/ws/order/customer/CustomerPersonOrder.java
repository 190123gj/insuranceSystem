package com.born.insurance.ws.order.customer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class CustomerPersonOrder extends CustomerBaseOrder{

    private static final long serialVersionUID = -4282603875229233564L;

	private String customerId;

	private String customerName;

	private String sex;

	private long orgId;

	private String nation;

	private String birthDay;

	private String maritalStatus;

	private String company;

	private String dept;

	private String job;


	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String getCustomerId() {
		return customerId;
	}

	@Override
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String getCustomerName() {
		return customerName;
	}

	@Override
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public void check(){
		
	}
	


	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
