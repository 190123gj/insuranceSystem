package com.born.insurance.biz.service.customer;


import java.util.List;

import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.order.customer.ValueTaxInfoQueryOrder;

public interface ValueTaxInfoService {
	
	public List<ValueTaxInfo> queryValueTaxInfoById(ValueTaxInfoQueryOrder valueTaxInfoQueryOrder);

	public ValueTaxInfo findValueTaxInfo(String insuranceCompanyId);

}
