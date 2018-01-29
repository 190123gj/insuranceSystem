package com.born.insurance.biz.service.priceContactLetterSchemeDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PriceContactLetterSchemeDetailDO;
import com.born.insurance.dal.daointerface.PriceContactLetterSchemeDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailOrder;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterSchemeDetailService")
public class PriceContactLetterSchemeDetailServiceImpl extends BaseAutowiredDomainService implements
																							PriceContactLetterSchemeDetailService {
	@Autowired
	protected PriceContactLetterSchemeDetailDAO priceContactLetterSchemeDetailDAO;
	
	@Override
	public InsuranceBaseResult save(final PriceContactLetterSchemeDetailOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterSchemeDetailDO doObj = new PriceContactLetterSchemeDetailDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterSchemeDetailDAO.insert(doObj);
				} else {
					PriceContactLetterSchemeDetailDO doObj = priceContactLetterSchemeDetailDAO
						.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterSchemeDetailDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PriceContactLetterSchemeDetailInfo findById(long id) {
		PriceContactLetterSchemeDetailDO priceContactLetterSchemeDetailDo = priceContactLetterSchemeDetailDAO
			.findById(id);
		if (priceContactLetterSchemeDetailDo != null) {
			PriceContactLetterSchemeDetailInfo priceContactLetterSchemeDetailInfo = new PriceContactLetterSchemeDetailInfo();
			BeanCopier.staticCopy(priceContactLetterSchemeDetailDo,
				priceContactLetterSchemeDetailInfo);
			return priceContactLetterSchemeDetailInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterSchemeDetailInfo> queryList(	PriceContactLetterSchemeDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterSchemeDetailInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterSchemeDetailInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterSchemeDetailInfo> pageList = new ArrayList<PriceContactLetterSchemeDetailInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterSchemeDetailDO priceContactLetterSchemeDetailDO = new PriceContactLetterSchemeDetailDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterSchemeDetailDO);
			long totalCount = priceContactLetterSchemeDetailDAO
				.findByConditionCount(priceContactLetterSchemeDetailDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterSchemeDetailDO> recordList = priceContactLetterSchemeDetailDAO
				.findByCondition(priceContactLetterSchemeDetailDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterSchemeDetailDO item : recordList) {
				PriceContactLetterSchemeDetailInfo info = new PriceContactLetterSchemeDetailInfo();
				BeanCopier.staticCopy(item, info);
				info.setInsuranceAmount(item.getInsuranceAmount().toStandardString());
				info.setExpectPremiumAmount(item.getExpectPremiumAmount().toStandardString());
				info.setInsuranceAmountMoney(item.getInsuranceAmount());
				info.setExpectPremiumAmountMoney(item.getExpectPremiumAmount());
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