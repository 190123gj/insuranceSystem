package com.born.insurance.biz.service.businessBillCoinsInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillCoinsInfoDO;
import com.born.insurance.dal.daointerface.BusinessBillCoinsInfoDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBillCoinsInfo.BusinessBillCoinsInfoInfo;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoOrder;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillCoinsInfo.BusinessBillCoinsInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillCoinsInfoService")
public class BusinessBillCoinsInfoServiceImpl extends BaseAutowiredDomainService implements
																				BusinessBillCoinsInfoService {
	@Autowired
	protected BusinessBillCoinsInfoDAO businessBillCoinsInfoDAO;
	
	@Override
	public InsuranceBaseResult save(final BusinessBillCoinsInfoOrder order) {
		return commonProcess(order, "共保增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getCoinsInfoId();
				if (id <= 0) {
					BusinessBillCoinsInfoDO doObj = new BusinessBillCoinsInfoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCoinsInfoDAO.insert(doObj);
				} else {
					BusinessBillCoinsInfoDO doObj = businessBillCoinsInfoDAO.findById(order
						.getCoinsInfoId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCoinsInfoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillCoinsInfoInfo findById(long id) {
		BusinessBillCoinsInfoDO businessBillCoinsInfoDo = businessBillCoinsInfoDAO.findById(id);
		if (businessBillCoinsInfoDo != null) {
			BusinessBillCoinsInfoInfo businessBillCoinsInfoInfo = new BusinessBillCoinsInfoInfo();
			BeanCopier.staticCopy(businessBillCoinsInfoDo, businessBillCoinsInfoInfo);
			return businessBillCoinsInfoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillCoinsInfoInfo> queryList(	BusinessBillCoinsInfoQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillCoinsInfoInfo> batchResult = new QueryBaseBatchResult<BusinessBillCoinsInfoInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillCoinsInfoInfo> pageList = new ArrayList<BusinessBillCoinsInfoInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillCoinsInfoDO businessBillCoinsInfoDO = new BusinessBillCoinsInfoDO();
			BeanCopier.staticCopy(queryOrder, businessBillCoinsInfoDO);
			long totalCount = businessBillCoinsInfoDAO
				.findByConditionCount(businessBillCoinsInfoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillCoinsInfoDO> recordList = businessBillCoinsInfoDAO.findByCondition(
				businessBillCoinsInfoDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (BusinessBillCoinsInfoDO item : recordList) {
				BusinessBillCoinsInfoInfo info = new BusinessBillCoinsInfoInfo();
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
	public List<BusinessBillCoinsInfoInfo> findBusinessBillCoinsInfo(long businessBillId) {
		List<BusinessBillCoinsInfoInfo> pageList = new ArrayList<BusinessBillCoinsInfoInfo>();
		List<BusinessBillCoinsInfoDO> list = null;
		try {
			list = businessBillCoinsInfoDAO.findBusinessBillCoinsInfo(businessBillId);
			for (BusinessBillCoinsInfoDO item : list) {
				BusinessBillCoinsInfoInfo info = new BusinessBillCoinsInfoInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
		} catch (Exception e) {
		}
		return pageList;
	}
}