package com.born.insurance.biz.service.customer;

import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dataobject.CompanyRelationInfoOrder;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.customer.CustomerPersonInfo;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface CustomerPersonService {

    public QueryBaseBatchResult<CustomerPersonInfo> queryCommonPersonByCondition(CustomerPersonQueryOrder queryOrder);

    public QueryBaseBatchResult<CustomerPersonInfo> queryCustomerPersonByCondition(CustomerPersonQueryOrder queryOrder);

    public InsuranceBaseResult updateCustomerPerson(CustomerPersonOrder customerBaseInfoOrder);


    public InsuranceBaseResult addCustomerPerson(CustomerPersonOrder customerBaseInfoOrder);

    public CustomerPersonInfo queryCustomerPersonByUserId(long UserId);

    public CustomerBaseInfo queryCustomerPersonByOwnerId(long UserId);

    public InsuranceBaseResult addRelationBaseInfo(CompanyRelationInfoOrder companyRelationInfoOrder);
    
    public CustomerBaseInfoDO findById(long userId);
    
    public InsuranceBaseResult deleteCustomerPersonInfo(CustomerPersonOrder customerBaseInfoOrder);

	public CustomerBaseInfo findByCustomerUserId(long customerUserId);

}
