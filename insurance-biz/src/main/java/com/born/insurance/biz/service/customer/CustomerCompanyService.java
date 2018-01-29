package com.born.insurance.biz.service.customer;


import java.util.List;

import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.order.customer.CustomerCompanyOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface CustomerCompanyService {
	
	public QueryBaseBatchResult<CustomerCompanyInfo> queryList(CustomerCompanyQueryOrder customerCompanyQueryOrder);
	
	public InsuranceBaseResult updateCustomerCompany(CustomerCompanyOrder customerCompanyOrder);
	
	public InsuranceBaseResult addCustomerCompany(CustomerCompanyOrder customerCompanyOrder);
	
	public CustomerCompanyInfo queryCustomerCompanyByUserId(long userId);
	
	public List<CustomerBaseInfo> findAgencyCompany(String customerType);
 	
}
