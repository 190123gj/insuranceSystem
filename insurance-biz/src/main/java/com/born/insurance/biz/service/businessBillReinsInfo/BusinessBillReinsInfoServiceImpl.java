package com.born.insurance.biz.service.businessBillReinsInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillReinsInfoDO;
import com.born.insurance.dal.daointerface.BusinessBillReinsInfoDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBillReinsInfo.BusinessBillReinsInfoInfo;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoOrder;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillReinsInfo.BusinessBillReinsInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillReinsInfoService")
public class BusinessBillReinsInfoServiceImpl extends BaseAutowiredDomainService implements
																				BusinessBillReinsInfoService {
	@Autowired
	protected BusinessBillReinsInfoDAO businessBillReinsInfoDAO;
	
	@Override
	public InsuranceBaseResult save(final BusinessBillReinsInfoOrder order) {
		return commonProcess(order, "增加或修改再保信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getReinsInfoId();
				if (id <= 0) {
					BusinessBillReinsInfoDO doObj = new BusinessBillReinsInfoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillReinsInfoDAO.insert(doObj);
				} else {
					BusinessBillReinsInfoDO doObj = businessBillReinsInfoDAO.findById(order
						.getReinsInfoId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillReinsInfoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillReinsInfoInfo findById(long id) {
		BusinessBillReinsInfoDO businessBillReinsInfoDo = businessBillReinsInfoDAO.findById(id);
		if (businessBillReinsInfoDo != null) {
			BusinessBillReinsInfoInfo businessBillReinsInfoInfo = new BusinessBillReinsInfoInfo();
			BeanCopier.staticCopy(businessBillReinsInfoDo, businessBillReinsInfoInfo);
			return businessBillReinsInfoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillReinsInfoInfo> queryList(	BusinessBillReinsInfoQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillReinsInfoInfo> batchResult = new QueryBaseBatchResult<BusinessBillReinsInfoInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillReinsInfoInfo> pageList = new ArrayList<BusinessBillReinsInfoInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillReinsInfoDO businessBillReinsInfoDO = new BusinessBillReinsInfoDO();
			BeanCopier.staticCopy(queryOrder, businessBillReinsInfoDO);
			long totalCount = businessBillReinsInfoDAO
				.findByConditionCount(businessBillReinsInfoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillReinsInfoDO> recordList = businessBillReinsInfoDAO.findByCondition(
				businessBillReinsInfoDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (BusinessBillReinsInfoDO item : recordList) {
				BusinessBillReinsInfoInfo info = new BusinessBillReinsInfoInfo();
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
	public List<BusinessBillReinsInfoInfo> findBusinessBillReinsInfo(long businessBillId) {
		List<BusinessBillReinsInfoInfo> pageList = new ArrayList<BusinessBillReinsInfoInfo>();
		List<BusinessBillReinsInfoDO> list = null ;
		try {
			list =businessBillReinsInfoDAO.findBusinessBillReinsInfo(businessBillId); 
			for (BusinessBillReinsInfoDO item : list) {
				BusinessBillReinsInfoInfo info = new BusinessBillReinsInfoInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
		} catch (Exception e) {
			logger.error("查询保单再保信息",e);
		}
		
		return pageList;
	}
}