package com.born.insurance.biz.service.businessBillCostInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillCostInfoDO;
import com.born.insurance.dal.daointerface.BusinessBillCostInfoDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBillCostInfo.BusinessBillCostInfoInfo;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoOrder;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillCostInfo.BusinessBillCostInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillCostInfoService")
public class BusinessBillCostInfoServiceImpl extends BaseAutowiredDomainService implements
																				BusinessBillCostInfoService {
	@Autowired
	protected BusinessBillCostInfoDAO businessBillCostInfoDAO;
	
	@Override
	public InsuranceBaseResult save(final BusinessBillCostInfoOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getCostInfoId();
				if (id <= 0) {
					BusinessBillCostInfoDO doObj = new BusinessBillCostInfoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCostInfoDAO.insert(doObj);
				} else {
					BusinessBillCostInfoDO doObj = businessBillCostInfoDAO.findById(order
						.getCostInfoId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCostInfoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillCostInfoInfo findById(long id) {
		BusinessBillCostInfoDO businessBillCostInfoDo = businessBillCostInfoDAO.findById(id);
		if (businessBillCostInfoDo != null) {
			BusinessBillCostInfoInfo businessBillCostInfoInfo = new BusinessBillCostInfoInfo();
			BeanCopier.staticCopy(businessBillCostInfoDo, businessBillCostInfoInfo);
			return businessBillCostInfoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillCostInfoInfo> queryList(BusinessBillCostInfoQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillCostInfoInfo> batchResult = new QueryBaseBatchResult<BusinessBillCostInfoInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillCostInfoInfo> pageList = new ArrayList<BusinessBillCostInfoInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillCostInfoDO businessBillCostInfoDO = new BusinessBillCostInfoDO();
			BeanCopier.staticCopy(queryOrder, businessBillCostInfoDO);
			long totalCount = businessBillCostInfoDAO.findByConditionCount(businessBillCostInfoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillCostInfoDO> recordList = businessBillCostInfoDAO.findByCondition(
				businessBillCostInfoDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (BusinessBillCostInfoDO item : recordList) {
				BusinessBillCostInfoInfo info = new BusinessBillCostInfoInfo();
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
}