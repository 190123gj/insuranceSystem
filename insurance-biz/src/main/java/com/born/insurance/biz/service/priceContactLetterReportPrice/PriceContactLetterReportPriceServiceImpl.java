package com.born.insurance.biz.service.priceContactLetterReportPrice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.insurance.dal.daointerface.PriceContactLetterCompanyReportPriceDAO;
import com.born.insurance.dal.daointerface.PriceContactLetterCompanyReportPriceDetailDAO;
import com.born.insurance.dal.dataobject.PriceContactLetterCompanyReportPriceDetailDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.PriceContactLetterReportPriceDAO;
import com.born.insurance.dal.dataobject.PriceContactLetterReportPriceDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceDetailInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceInfo;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceDetailQueryOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetterReportPrice.PriceContactLetterReportPriceService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;


/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterReportPriceService")
public class PriceContactLetterReportPriceServiceImpl extends BaseAutowiredDomainService implements
																						PriceContactLetterReportPriceService {
	@Autowired
	protected PriceContactLetterReportPriceDAO priceContactLetterReportPriceDAO;

	@Autowired
	protected PriceContactLetterCompanyReportPriceDAO priceContactLetterCompanyReportPriceDAO;

	@Autowired
	protected PriceContactLetterCompanyReportPriceDetailDAO priceContactLetterCompanyReportPriceDetailDAO;
	
	@Override
	public InsuranceBaseResult save(final PriceContactLetterCompanyReportPriceOrder order) {
		return commonProcess(order, "报价增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterReportPriceDO doObj = new PriceContactLetterReportPriceDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterReportPriceDAO.insert(doObj);
				} else {
					PriceContactLetterReportPriceDO doObj = priceContactLetterReportPriceDAO
						.findByReportPriceId(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterReportPriceDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PriceContactLetterCompanyReportPriceInfo findById(long id) {
		PriceContactLetterReportPriceDO priceContactLetterReportPriceDo = priceContactLetterReportPriceDAO
			.findByReportPriceId(id);
		if (priceContactLetterReportPriceDo != null) {
			priceContactLetterCompanyReportPriceDAO.findById(priceContactLetterReportPriceDo.getReportPriceId());
			PriceContactLetterCompanyReportPriceInfo priceContactLetterReportPriceInfo = new PriceContactLetterCompanyReportPriceInfo();
			BeanCopier.staticCopy(priceContactLetterReportPriceDo,
					priceContactLetterReportPriceInfo);
			return priceContactLetterReportPriceInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterCompanyReportPriceInfo> queryList(PriceContactLetterReportPriceQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterCompanyReportPriceInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterCompanyReportPriceInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterCompanyReportPriceInfo> pageList = new ArrayList<PriceContactLetterCompanyReportPriceInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterReportPriceDO priceContactLetterReportPriceDO = new PriceContactLetterReportPriceDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterReportPriceDO);
			long totalCount = priceContactLetterReportPriceDAO
				.findByConditionCount(priceContactLetterReportPriceDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterReportPriceDO> recordList = priceContactLetterReportPriceDAO
				.findByCondition(priceContactLetterReportPriceDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterReportPriceDO item : recordList) {
				PriceContactLetterCompanyReportPriceInfo info = new PriceContactLetterCompanyReportPriceInfo();
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
	public QueryBaseBatchResult<PriceContactLetterCompanyReportPriceDetailInfo> queryList(PriceContactLetterReportPriceDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterCompanyReportPriceDetailInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterCompanyReportPriceDetailInfo>();

		try {
			queryOrder.check();
			List<PriceContactLetterCompanyReportPriceDetailInfo> pageList = new ArrayList<PriceContactLetterCompanyReportPriceDetailInfo>(
					(int) queryOrder.getPageSize());

			PriceContactLetterCompanyReportPriceDetailDO priceContactLetterReportPriceDetailDO = new PriceContactLetterCompanyReportPriceDetailDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterReportPriceDetailDO);
			long totalCount = priceContactLetterCompanyReportPriceDetailDAO
					.findByConditionCount(priceContactLetterReportPriceDetailDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterCompanyReportPriceDetailDO> recordList = priceContactLetterCompanyReportPriceDetailDAO
					.findByCondition(priceContactLetterReportPriceDetailDO, queryOrder.getSortCol(),
							queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterCompanyReportPriceDetailDO item : recordList) {
				PriceContactLetterCompanyReportPriceDetailInfo info = new PriceContactLetterCompanyReportPriceDetailInfo();
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