package com.born.insurance.biz.service.customer;

import com.born.insurance.dal.dataobject.CustomerProtocolDO;
import com.born.insurance.dataobject.CustomerProtocolVo;
import com.born.insurance.ws.order.customer.CustomerProtocolOrder;
import com.born.insurance.ws.order.customer.CustomerProtocolQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface CustomerProtocolService {
	
	/**
	 * 新增客户协议
	 * <p>Title: addCustomerProtocol</p>
	 * <p>Description: </p>
	 * @param customerProtocolDO
	 * @return 
	 */
	public InsuranceBaseResult addCustomerProtocol(CustomerProtocolOrder order);
	
	/**
	 * 查询所有客户协议
	 * <p>Title: queryCustomerProtocolList</p>
	 * <p>Description: </p>
	 * @param customerProtocolQueryOrder
	 * @return
	 */
	public QueryBaseBatchResult<CustomerProtocolVo> queryCustomerProtocolList(CustomerProtocolQueryOrder customerProtocolQueryOrder);

	/**
	 * 查询协议详情
	 * <p>Title: findById</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 */
	public CustomerProtocolDO findById(long id);
}
