package com.born.insurance.biz.service.insuranceConditions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.InsuranceConditionDO;
import com.born.insurance.dal.daointerface.InsuranceConditionDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceConditions.InsuranceConditionsInfo;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsOrder;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceConditions.InsuranceConditionsService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceConditionsService")
public class InsuranceConditionsServiceImpl extends BaseAutowiredDomainService implements
																				InsuranceConditionsService {
	@Autowired
	protected InsuranceConditionDAO insuranceConditionsDAO;
	
	@Override
	public InsuranceBaseResult save(final InsuranceConditionsOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getConditionId();
				if (id <= 0) {
					InsuranceConditionDO doObj = new InsuranceConditionDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceConditionsDAO.insert(doObj);
				} else {
					InsuranceConditionDO doObj = insuranceConditionsDAO.findById(order
						.getConditionId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceConditionsDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceConditionsInfo findById(long id) {
		InsuranceConditionDO insuranceConditionsDo = insuranceConditionsDAO.findById(id);
		if (insuranceConditionsDo != null) {
			InsuranceConditionsInfo insuranceConditionsInfo = new InsuranceConditionsInfo();
			BeanCopier.staticCopy(insuranceConditionsDo, insuranceConditionsInfo);
			return insuranceConditionsInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceConditionsInfo> queryList(	InsuranceConditionsQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceConditionsInfo> batchResult = new QueryBaseBatchResult<InsuranceConditionsInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceConditionsInfo> pageList = new ArrayList<InsuranceConditionsInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceConditionDO insuranceConditionsDO = new InsuranceConditionDO();
			BeanCopier.staticCopy(queryOrder, insuranceConditionsDO);
			long totalCount = insuranceConditionsDAO.findByConditionCount(insuranceConditionsDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceConditionDO> recordList = insuranceConditionsDAO.findByCondition(
				insuranceConditionsDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (InsuranceConditionDO item : recordList) {
				InsuranceConditionsInfo info = new InsuranceConditionsInfo();
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