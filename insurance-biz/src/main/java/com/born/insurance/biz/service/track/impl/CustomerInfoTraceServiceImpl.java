package com.born.insurance.biz.service.track.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.track.CustomerInfoTraceService;
import com.born.insurance.dal.daointerface.CustomerInfoTraceDAO;
import com.born.insurance.dal.dataobject.CustomerInfoTraceDO;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.customer.CustomerInfoTraceInfo;
import com.born.insurance.ws.order.customer.CustomerInfoTraceOrder;
import com.born.insurance.ws.order.customer.CustomerInfoTraceQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.LoggerFactory;

@Service("customerInfoTraceService")
public class CustomerInfoTraceServiceImpl extends BaseAutowiredDomainService implements
																			CustomerInfoTraceService {
	
	private final com.yjf.common.log.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CustomerInfoTraceDAO customerInfoTraceDAO;
	
	@Override
	public QueryBaseBatchResult<CustomerInfoTraceInfo> querycustomerInfoTraceInfoByCondition(	CustomerInfoTraceQueryOrder customerInfoTraceQueryOrder) {
		logger.info("开始查询指定企业客户跟踪信息....customerInfoTraceQueryOrder=[{}]"
					+ customerInfoTraceQueryOrder.toString());
		QueryBaseBatchResult<CustomerInfoTraceInfo> baseBatchResult = new QueryBaseBatchResult<CustomerInfoTraceInfo>();
		customerInfoTraceQueryOrder.check();
		CustomerInfoTraceDO customerInfoTraceDO = new CustomerInfoTraceDO();
		BeanCopier.staticCopy(customerInfoTraceQueryOrder, customerInfoTraceDO);
		long totalCount = customerInfoTraceDAO.findByConditionCount(customerInfoTraceDO);
		PageComponent component = new PageComponent(customerInfoTraceQueryOrder, totalCount);
		List<CustomerInfoTraceDO> traceList = customerInfoTraceDAO.findByCondition(
			customerInfoTraceDO, customerInfoTraceQueryOrder.getSortCol(),
			customerInfoTraceQueryOrder.getSortOrder(), customerInfoTraceQueryOrder.getLimitStart(), customerInfoTraceQueryOrder.getPageSize());
		if (ListUtil.isNotEmpty(traceList)) {
			List<CustomerInfoTraceInfo> customerInfoTraceInfo = new ArrayList<CustomerInfoTraceInfo>();
			for (CustomerInfoTraceDO traceDo : traceList) {
				CustomerInfoTraceInfo traceInfo = new CustomerInfoTraceInfo();
				BeanCopier.staticCopy(traceDo, traceInfo);
				customerInfoTraceInfo.add(traceInfo);
			}
			baseBatchResult.setPageList(customerInfoTraceInfo);
			baseBatchResult.initPageParam(component);
			baseBatchResult.setSuccess(true);
		}
		return baseBatchResult;
	}
	
	@Override
	public InsuranceBaseResult addcustomerInfoTraceInfo(CustomerInfoTraceOrder customerInfoTraceOrder) {
		logger.info("开始添加指定企业客户跟踪信息....customerInfoTraceOrder=[{}]"
					+ customerInfoTraceOrder.toString());
		InsuranceBaseResult result = new InsuranceBaseResult();
		customerInfoTraceOrder.check();
		result.setSuccess(false);
		result.setMessage("添加失败");
		CustomerInfoTraceDO customerInfoTraceDO = new CustomerInfoTraceDO();
		BeanCopier.staticCopy(customerInfoTraceOrder, customerInfoTraceDO);
		customerInfoTraceDO.setRawAddTime(new Date());
		long insertId = customerInfoTraceDAO.insert(customerInfoTraceDO);
		if (insertId > 0) {
			result.setSuccess(true);
			result.setMessage("添加成功");
		}
		return result;
	}
}
