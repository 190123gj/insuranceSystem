package com.born.insurance.ws.order.customer;

import java.util.Date;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.yjf.common.service.Order;

public class ValueTaxInfoQueryOrder extends QueryPageBase {
	private long id;

	private String orgName;

	private String identifyNumber;

	private long customerUserId;

	private String customerUserName;

	private String mobile;

	private String orgAddress;

	private String openBankName;

	private String bankCardNo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public long getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
		this.customerUserId = customerUserId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

}
