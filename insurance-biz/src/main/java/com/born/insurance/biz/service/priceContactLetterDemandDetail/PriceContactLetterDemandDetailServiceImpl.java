package com.born.insurance.biz.service.priceContactLetterDemandDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PriceContactLetterDemandDetailDO;
import com.born.insurance.dal.daointerface.PriceContactLetterDemandDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterDemandDetail.PriceContactLetterDemandDetailInfo;
import com.born.insurance.ws.order.priceContactLetterDemandDetail.PriceContactLetterDemandDetailOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetail.PriceContactLetterDemandDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetterDemandDetail.PriceContactLetterDemandDetailService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterDemandDetailService")
public class PriceContactLetterDemandDetailServiceImpl extends BaseAutowiredDomainService implements
																							PriceContactLetterDemandDetailService {
	@Autowired
	protected PriceContactLetterDemandDetailDAO priceContactLetterDemandDetailDAO;
	
	@Override
	public InsuranceBaseResult save(final PriceContactLetterDemandDetailOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterDemandDetailDO doObj = new PriceContactLetterDemandDetailDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDemandDetailDAO.insert(doObj);
				} else {
					PriceContactLetterDemandDetailDO doObj = priceContactLetterDemandDetailDAO
						.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDemandDetailDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PriceContactLetterDemandDetailInfo findById(long id) {
		PriceContactLetterDemandDetailDO priceContactLetterDemandDetailDo = priceContactLetterDemandDetailDAO
			.findById(id);
		if (priceContactLetterDemandDetailDo != null) {
			PriceContactLetterDemandDetailInfo priceContactLetterDemandDetailInfo = new PriceContactLetterDemandDetailInfo();
			BeanCopier.staticCopy(priceContactLetterDemandDetailDo,
				priceContactLetterDemandDetailInfo);
			return priceContactLetterDemandDetailInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterDemandDetailInfo> queryList(	PriceContactLetterDemandDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterDemandDetailInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterDemandDetailInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterDemandDetailInfo> pageList = new ArrayList<PriceContactLetterDemandDetailInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterDemandDetailDO priceContactLetterDemandDetailDO = new PriceContactLetterDemandDetailDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterDemandDetailDO);
			long totalCount = priceContactLetterDemandDetailDAO
				.findByConditionCount(priceContactLetterDemandDetailDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterDemandDetailDO> recordList = priceContactLetterDemandDetailDAO
				.findByCondition(priceContactLetterDemandDetailDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterDemandDetailDO item : recordList) {
				PriceContactLetterDemandDetailInfo info = new PriceContactLetterDemandDetailInfo();
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