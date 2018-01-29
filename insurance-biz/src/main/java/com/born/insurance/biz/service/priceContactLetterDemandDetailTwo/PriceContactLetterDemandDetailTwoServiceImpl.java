package com.born.insurance.biz.service.priceContactLetterDemandDetailTwo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PriceContactLetterDemandDetailTwoDO;
import com.born.insurance.dal.daointerface.PriceContactLetterDemandDetailTwoDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoInfo;
import com.born.insurance.ws.order.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterDemandDetailTwoService")
public class PriceContactLetterDemandDetailTwoServiceImpl extends BaseAutowiredDomainService
																							implements
																							PriceContactLetterDemandDetailTwoService {
	@Autowired
	protected PriceContactLetterDemandDetailTwoDAO priceContactLetterDemandDetailTwoDAO;
	
	@Override
	public InsuranceBaseResult save(final PriceContactLetterDemandDetailTwoOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterDemandDetailTwoDO doObj = new PriceContactLetterDemandDetailTwoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDemandDetailTwoDAO.insert(doObj);
				} else {
					PriceContactLetterDemandDetailTwoDO doObj = priceContactLetterDemandDetailTwoDAO
						.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDemandDetailTwoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PriceContactLetterDemandDetailTwoInfo findById(long id) {
		PriceContactLetterDemandDetailTwoDO priceContactLetterDemandDetailTwoDo = priceContactLetterDemandDetailTwoDAO
			.findById(id);
		if (priceContactLetterDemandDetailTwoDo != null) {
			PriceContactLetterDemandDetailTwoInfo priceContactLetterDemandDetailTwoInfo = new PriceContactLetterDemandDetailTwoInfo();
			BeanCopier.staticCopy(priceContactLetterDemandDetailTwoDo,
				priceContactLetterDemandDetailTwoInfo);
			return priceContactLetterDemandDetailTwoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterDemandDetailTwoInfo> queryList(	PriceContactLetterDemandDetailTwoQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterDemandDetailTwoInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterDemandDetailTwoInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterDemandDetailTwoInfo> pageList = new ArrayList<PriceContactLetterDemandDetailTwoInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterDemandDetailTwoDO priceContactLetterDemandDetailTwoDO = new PriceContactLetterDemandDetailTwoDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterDemandDetailTwoDO);
			long totalCount = priceContactLetterDemandDetailTwoDAO
				.findByConditionCount(priceContactLetterDemandDetailTwoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterDemandDetailTwoDO> recordList = priceContactLetterDemandDetailTwoDAO
				.findByCondition(priceContactLetterDemandDetailTwoDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterDemandDetailTwoDO item : recordList) {
				PriceContactLetterDemandDetailTwoInfo info = new PriceContactLetterDemandDetailTwoInfo();
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