package com.born.insurance.biz.service.priceContactLetterDemand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PriceContactLetterDemandDO;
import com.born.insurance.dal.daointerface.PriceContactLetterDemandDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandOrder;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetterDemand.PriceContactLetterDemandService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterDemandService")
public class PriceContactLetterDemandServiceImpl extends BaseAutowiredDomainService implements
																					PriceContactLetterDemandService {
	@Autowired
	protected PriceContactLetterDemandDAO priceContactLetterDemandDAO;
	
	@Override
	public InsuranceBaseResult save(final PriceContactLetterDemandOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterDemandDO doObj = new PriceContactLetterDemandDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDemandDAO.insert(doObj);
				} else {
					PriceContactLetterDemandDO doObj = priceContactLetterDemandDAO.findById(order
						.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDemandDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PriceContactLetterDemandInfo findById(long id) {
		PriceContactLetterDemandDO priceContactLetterDemandDo = priceContactLetterDemandDAO
			.findById(id);
		if (priceContactLetterDemandDo != null) {
			PriceContactLetterDemandInfo priceContactLetterDemandInfo = new PriceContactLetterDemandInfo();
			BeanCopier.staticCopy(priceContactLetterDemandDo, priceContactLetterDemandInfo);
			return priceContactLetterDemandInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterDemandInfo> queryList(PriceContactLetterDemandQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterDemandInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterDemandInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterDemandInfo> pageList = new ArrayList<PriceContactLetterDemandInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterDemandDO priceContactLetterDemandDO = new PriceContactLetterDemandDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterDemandDO);
			long totalCount = priceContactLetterDemandDAO
				.findByConditionCount(priceContactLetterDemandDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterDemandDO> recordList = priceContactLetterDemandDAO
				.findByCondition(priceContactLetterDemandDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterDemandDO item : recordList) {
				PriceContactLetterDemandInfo info = new PriceContactLetterDemandInfo();
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