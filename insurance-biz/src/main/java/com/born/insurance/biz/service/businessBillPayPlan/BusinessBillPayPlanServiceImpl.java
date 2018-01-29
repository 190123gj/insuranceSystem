package com.born.insurance.biz.service.businessBillPayPlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillPayPlanDO;
import com.born.insurance.dal.daointerface.BusinessBillPayPlanDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBill.BillPayPlanYear;
import com.born.insurance.ws.info.businessBill.BillPayPlanYearInfo;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanOrder;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillPayPlan.BusinessBillPayPlanService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillPayPlanService")
public class BusinessBillPayPlanServiceImpl extends BaseAutowiredDomainService implements
																				BusinessBillPayPlanService {
	@Autowired
	protected BusinessBillPayPlanDAO businessBillPayPlanDAO;
	
	@Override
	public InsuranceBaseResult save(final BusinessBillPayPlanOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getPayPlanId();
				if (id <= 0) {
					BusinessBillPayPlanDO doObj = new BusinessBillPayPlanDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillPayPlanDAO.insert(doObj);
				} else {
					BusinessBillPayPlanDO doObj = businessBillPayPlanDAO.findById(order
						.getPayPlanId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillPayPlanDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillPayPlanInfo findById(long id) {
		BusinessBillPayPlanDO businessBillPayPlanDo = businessBillPayPlanDAO.findById(id);
		if (businessBillPayPlanDo != null) {
			BusinessBillPayPlanInfo businessBillPayPlanInfo = new BusinessBillPayPlanInfo();
			BeanCopier.staticCopy(businessBillPayPlanDo, businessBillPayPlanInfo);
			return businessBillPayPlanInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillPayPlanInfo> queryList(	BusinessBillPayPlanQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillPayPlanInfo> batchResult = new QueryBaseBatchResult<BusinessBillPayPlanInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillPayPlanInfo> pageList = new ArrayList<BusinessBillPayPlanInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillPayPlanDO businessBillPayPlanDO = new BusinessBillPayPlanDO();
			BeanCopier.staticCopy(queryOrder, businessBillPayPlanDO);
			long totalCount = businessBillPayPlanDAO.findByConditionCount(businessBillPayPlanDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillPayPlanDO> recordList = businessBillPayPlanDAO.findByCondition(
				businessBillPayPlanDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (BusinessBillPayPlanDO item : recordList) {
				BusinessBillPayPlanInfo info = new BusinessBillPayPlanInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}

	@Override
	public List<BusinessBillPayPlanInfo> findBusinessBillPayPlan(long businessBillId) {
		List<BusinessBillPayPlanInfo> pageList = new ArrayList<BusinessBillPayPlanInfo>();
		List<BusinessBillPayPlanDO> list = null;
		try {
			list = businessBillPayPlanDAO.findBusinessBillPayPlan(businessBillId);
			for (BusinessBillPayPlanDO businessBillPayPlanDO : list) {
				BusinessBillPayPlanInfo info = new BusinessBillPayPlanInfo();
				BeanCopier.staticCopy(businessBillPayPlanDO, info);
				pageList.add(info);
				
			}
		} catch (Exception e) {
			logger.error("缴费计划查询:", e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<BillPayPlanYear> findAllBusinessBillPayPlan(long businessBillId) {
		List<BillPayPlanYear> pageList = new ArrayList<BillPayPlanYear>();
		List<BusinessBillPayPlanDO> list = null;
		Map<String, List<BusinessBillPayPlanDO>> maps = new LinkedHashMap<String, List<BusinessBillPayPlanDO>>();
		try {
			list = businessBillPayPlanDAO.findAllBusinessBillPayPlan(businessBillId);
			if (ListUtil.isNotEmpty(list)) {
				for (BusinessBillPayPlanDO businessBillPayPlanDO : list) {
					if (!maps.containsKey(businessBillPayPlanDO.getYear())) {
						 List<BusinessBillPayPlanDO> arrayList = new ArrayList<BusinessBillPayPlanDO>();
						 arrayList.add(businessBillPayPlanDO);
						maps.put(businessBillPayPlanDO.getYear(), arrayList);
					} else {
						List<BusinessBillPayPlanDO> list2 = maps.get(businessBillPayPlanDO.getYear());
						list2.add(businessBillPayPlanDO);
					}
				}
			}
			if (!maps.isEmpty()) {
				for (String s : maps.keySet()) {
					BillPayPlanYear billPayPlanYear = new BillPayPlanYear();
					List<BillPayPlanYearInfo> info = new ArrayList<BillPayPlanYearInfo>();
					billPayPlanYear.setYear(s);
					List<BusinessBillPayPlanDO> list2 = maps.get(s);
					for (BusinessBillPayPlanDO businessBillPayPlanDO : list2) {
						BillPayPlanYearInfo billPayPlanYearInfo = new BillPayPlanYearInfo();
						billPayPlanYearInfo.setActualPayDate(businessBillPayPlanDO.getActualPayDate());
						billPayPlanYearInfo.setPeriod(businessBillPayPlanDO.getAppserialPeriod());
						billPayPlanYearInfo.setPlanPayDate(businessBillPayPlanDO.getPlanPayDate());
						billPayPlanYearInfo.setPayPlanId(businessBillPayPlanDO.getPayPlanId());
						billPayPlanYearInfo.setPaymentStatus(businessBillPayPlanDO.getPaymentStatus());
						billPayPlanYearInfo.setBusinessBillId(businessBillPayPlanDO.getBusinessBillId());
						billPayPlanYearInfo.setPremiumAmount(businessBillPayPlanDO.getPremiumAmount());
						info.add(billPayPlanYearInfo);
					}
					billPayPlanYear.setInfo(info);
					billPayPlanYear.setLength(info.size());
					pageList.add(billPayPlanYear);
				}
			}
		} catch (Exception e) {
			logger.error("缴费计划查询:", e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<BusinessBillPayPlanInfo> findAllNormalBusinessBillPayPlan(long businessBillId) {
		List<BusinessBillPayPlanInfo> pageList = new ArrayList<BusinessBillPayPlanInfo>();
		List<BusinessBillPayPlanDO> list = null;
		try {
			list = businessBillPayPlanDAO.findAllBusinessBillPayPlan(businessBillId);
			for (BusinessBillPayPlanDO businessBillPayPlanDO : list) {
				BusinessBillPayPlanInfo info  = new BusinessBillPayPlanInfo();
				BeanCopier.staticCopy(businessBillPayPlanDO, info);
				pageList.add(info);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return pageList;
	}
}