package com.born.insurance.biz.service.priceContactLetterScheme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PriceContactLetterSchemeDO;
import com.born.insurance.dal.daointerface.PriceContactLetterSchemeDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterScheme.PriceContactLetterSchemeInfo;
import com.born.insurance.ws.order.priceContactLetterScheme.PriceContactLetterSchemeOrder;
import com.born.insurance.ws.order.priceContactLetterScheme.PriceContactLetterSchemeQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetterScheme.PriceContactLetterSchemeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterSchemeService")
public class PriceContactLetterSchemeServiceImpl extends BaseAutowiredDomainService implements
																					PriceContactLetterSchemeService {
	@Autowired
	protected PriceContactLetterSchemeDAO priceContactLetterSchemeDAO;
	
	@Override
	public InsuranceBaseResult save(final PriceContactLetterSchemeOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterSchemeDO doObj = new PriceContactLetterSchemeDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterSchemeDAO.insert(doObj);
				} else {
					PriceContactLetterSchemeDO doObj = priceContactLetterSchemeDAO.findById(order
						.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterSchemeDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PriceContactLetterSchemeInfo findById(long id) {
		PriceContactLetterSchemeDO priceContactLetterSchemeDo = priceContactLetterSchemeDAO
			.findById(id);
		if (priceContactLetterSchemeDo != null) {
			PriceContactLetterSchemeInfo priceContactLetterSchemeInfo = new PriceContactLetterSchemeInfo();
			BeanCopier.staticCopy(priceContactLetterSchemeDo, priceContactLetterSchemeInfo);
			return priceContactLetterSchemeInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterSchemeInfo> queryList(PriceContactLetterSchemeQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterSchemeInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterSchemeInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterSchemeInfo> pageList = new ArrayList<PriceContactLetterSchemeInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterSchemeDO priceContactLetterSchemeDO = new PriceContactLetterSchemeDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterSchemeDO);
			long totalCount = priceContactLetterSchemeDAO
				.findByConditionCount(priceContactLetterSchemeDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterSchemeDO> recordList = priceContactLetterSchemeDAO
				.findByCondition(priceContactLetterSchemeDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterSchemeDO item : recordList) {
				PriceContactLetterSchemeInfo info = new PriceContactLetterSchemeInfo();
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