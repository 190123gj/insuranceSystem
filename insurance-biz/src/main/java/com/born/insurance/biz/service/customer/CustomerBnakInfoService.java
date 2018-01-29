package com.born.insurance.biz.service.customer;

import com.born.insurance.ws.info.customer.CustomerBankInfo;
import com.born.insurance.ws.order.customer.CustomerBankInfoOrder;
import com.born.insurance.ws.order.customer.CustomerBankInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

public interface CustomerBnakInfoService {
	public CustomerBankInfo queryCustomerBankInfoByID(CustomerBankInfoQueryOrder customerBankInfoQueryOrder);
	
	//public InsuranceBaseResult updateCustomerBankInfoByCondition(CustomerBankInfoOrder customerBankInfoOrder);
}
