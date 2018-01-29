package com.born.insurance.biz.service.customer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.customer.ValueTaxInfoService;
import com.born.insurance.dal.daointerface.ValueTaxInfoDAO;
import com.born.insurance.dal.dataobject.ValueTaxInfoDO;
import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.order.customer.ValueTaxInfoQueryOrder;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.log.LoggerFactory;

@Service("valueTaxInfoService")
public class ValueTaxInfoServiceImpl extends BaseAutowiredDomainService
		implements ValueTaxInfoService {

	private final com.yjf.common.log.Logger logger = LoggerFactory
			.getLogger(this.getClass());

	@Autowired
	private ValueTaxInfoDAO valueTaxInfoDAO;

	@Override
	public List<ValueTaxInfo> queryValueTaxInfoById(
			ValueTaxInfoQueryOrder valueTaxInfoQueryOrder) {
		logger.info("开始查询指定企业客户增值税发票列息....valueTaxInfoQueryOrder=[{}]"
				+ valueTaxInfoQueryOrder.toString());
		List<ValueTaxInfo> list=new ArrayList<ValueTaxInfo>();
		ValueTaxInfoDO valueTaxInfoDO = new ValueTaxInfoDO();
		BeanCopier.staticCopy(valueTaxInfoQueryOrder, valueTaxInfoDO);
		List<ValueTaxInfoDO> valueDo=(List<ValueTaxInfoDO>) valueTaxInfoDAO.findById(valueTaxInfoDO
				.getCustomerUserId());
		for(ValueTaxInfoDO Do:valueDo){
			ValueTaxInfo valueTaxInfo = new ValueTaxInfo();
			BeanCopier.staticCopy(Do, valueTaxInfo);
			list.add(valueTaxInfo);
		}
		return list;
	}

	@Override
	public ValueTaxInfo findValueTaxInfo(String insuranceCompanyId) {
		ValueTaxInfo valueTaxInfo = new ValueTaxInfo();
		ValueTaxInfoDO valueTaxInfoDO = valueTaxInfoDAO.findValueTaxInfo(Long.valueOf(insuranceCompanyId));
		if (null != valueTaxInfoDO) {
			BeanCopier.staticCopy(valueTaxInfoDO, valueTaxInfo);
		}
		return valueTaxInfo;
	}
}
