package com.born.insurance.biz.service.insuranceContactLetterDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDetailDO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.order.insuranceContactLetterDetail.InsuranceContactLetterDetailOrder;
import com.born.insurance.ws.order.insuranceContactLetterDetail.InsuranceContactLetterDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceContactLetterDetail.InsuranceContactLetterDetailService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceContactLetterDetailService")
public class InsuranceContactLetterDetailServiceImpl extends BaseAutowiredDomainService implements
																						InsuranceContactLetterDetailService {
	@Autowired
	protected InsuranceContactLetterDetailDAO insuranceContactLetterDetailDAO;
	
	@Override
	public InsuranceBaseResult save(final InsuranceContactLetterDetailOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getLetterDetailId();
				if (id <= 0) {
					InsuranceContactLetterDetailDO doObj = new InsuranceContactLetterDetailDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceContactLetterDetailDAO.insert(doObj);
				} else {
					InsuranceContactLetterDetailDO doObj = insuranceContactLetterDetailDAO
						.findById(order.getLetterDetailId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceContactLetterDetailDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceContactLetterDetailInfo findById(long id) {
		InsuranceContactLetterDetailDO insuranceContactLetterDetailDo = insuranceContactLetterDetailDAO
			.findById(id);
		if (insuranceContactLetterDetailDo != null) {
			InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo = new InsuranceContactLetterDetailInfo();
			BeanCopier.staticCopy(insuranceContactLetterDetailDo, insuranceContactLetterDetailInfo);
			return insuranceContactLetterDetailInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceContactLetterDetailInfo> queryList(InsuranceContactLetterDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceContactLetterDetailInfo> batchResult = new QueryBaseBatchResult<InsuranceContactLetterDetailInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceContactLetterDetailInfo> pageList = new ArrayList<InsuranceContactLetterDetailInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceContactLetterDetailDO insuranceContactLetterDetailDO = new InsuranceContactLetterDetailDO();
			BeanCopier.staticCopy(queryOrder, insuranceContactLetterDetailDO);
			long totalCount = insuranceContactLetterDetailDAO
				.findByConditionCount(insuranceContactLetterDetailDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceContactLetterDetailDO> recordList = insuranceContactLetterDetailDAO
				.findByCondition(insuranceContactLetterDetailDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (InsuranceContactLetterDetailDO item : recordList) {
				InsuranceContactLetterDetailInfo info = new InsuranceContactLetterDetailInfo();
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