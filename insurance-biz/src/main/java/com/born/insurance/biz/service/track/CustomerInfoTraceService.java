package com.born.insurance.biz.service.track;

import com.born.insurance.ws.info.customer.CustomerInfoTraceInfo;
import com.born.insurance.ws.order.customer.CustomerInfoTraceOrder;
import com.born.insurance.ws.order.customer.CustomerInfoTraceQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface CustomerInfoTraceService {

	public QueryBaseBatchResult<CustomerInfoTraceInfo> querycustomerInfoTraceInfoByCondition(CustomerInfoTraceQueryOrder customerInfoTraceQueryOrder);
	
	public InsuranceBaseResult addcustomerInfoTraceInfo(CustomerInfoTraceOrder customerInfoTraceOrder);
	
}
