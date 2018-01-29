package com.born.insurance.biz.service.priceTemplate;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.PriceTemplateDAO;
import com.born.insurance.dal.dataobject.PriceTemplateDO;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.priceTemplate.PriceTemplateInfo;
import com.born.insurance.ws.order.priceTemplate.PriceTemplateQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceTemplate.PriceTemplateService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-1-6.
 */
@Service("priceTemplateService")
public class PriceTemplateServiceImpl extends BaseAutowiredDomainService implements
																		PriceTemplateService {
	@Autowired
	PriceTemplateDAO priceTemplateDAO;
	
	@Override
	public PriceTemplateInfo findById(long id) {
		PriceTemplateDO priceTemplateDO = priceTemplateDAO.findById(id);
		if (priceTemplateDO != null) {
			PriceTemplateInfo salesAreasInfo = new PriceTemplateInfo();
			BeanCopier.staticCopy(priceTemplateDO, salesAreasInfo);
			return salesAreasInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceTemplateInfo> queryList(PriceTemplateQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceTemplateInfo> batchResult = new QueryBaseBatchResult<PriceTemplateInfo>();
		
		try {
			queryOrder.check();
			List<PriceTemplateInfo> pageList = new ArrayList<PriceTemplateInfo>(
				(int) queryOrder.getPageSize());
			
			PriceTemplateDO priceTemplateDO = new PriceTemplateDO();
			BeanCopier.staticCopy(queryOrder, priceTemplateDO);
			long totalCount = priceTemplateDAO.findByConditionCount(priceTemplateDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceTemplateDO> recordList = priceTemplateDAO.findByCondition(priceTemplateDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (PriceTemplateDO item : recordList) {
				PriceTemplateInfo info = new PriceTemplateInfo();
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
