package com.born.insurance.biz.service.insuranceProtocolInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.InsuranceProtocolInfoDO;
import com.born.insurance.dal.daointerface.InsuranceProtocolInfoDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceProtocolInfo.InsuranceProtocolInfoInfo;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoOrder;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceProtocolCharge.InsuranceProtocolChargeService;
import com.born.insurance.ws.service.insuranceProtocolInfo.InsuranceProtocolInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceProtocolInfoService")
public class InsuranceProtocolInfoServiceImpl extends BaseAutowiredDomainService implements
																				InsuranceProtocolInfoService {
	@Autowired
	protected InsuranceProtocolInfoDAO insuranceProtocolInfoDAO;

	@Autowired
	protected InsuranceProtocolChargeService insuranceProtocolChargeService;
	
	@Override
	public InsuranceBaseResult save(final InsuranceProtocolInfoOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getProtocolInfoId();
				if (id <= 0) {
					InsuranceProtocolInfoDO doObj = new InsuranceProtocolInfoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceProtocolInfoDAO.insert(doObj);
				} else {
					InsuranceProtocolInfoDO doObj = insuranceProtocolInfoDAO.findById(order
						.getProtocolInfoId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceProtocolInfoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceProtocolInfoInfo findById(long id) {
		InsuranceProtocolInfoDO insuranceProtocolInfoDo = insuranceProtocolInfoDAO.findById(id);
		if (insuranceProtocolInfoDo != null) {
			InsuranceProtocolInfoInfo insuranceProtocolInfoInfo = new InsuranceProtocolInfoInfo();
			BeanCopier.staticCopy(insuranceProtocolInfoDo, insuranceProtocolInfoInfo);
			return insuranceProtocolInfoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceProtocolInfoInfo> queryList(	InsuranceProtocolInfoQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceProtocolInfoInfo> batchResult = new QueryBaseBatchResult<InsuranceProtocolInfoInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceProtocolInfoInfo> pageList = new ArrayList<InsuranceProtocolInfoInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceProtocolInfoDO insuranceProtocolInfoDO = new InsuranceProtocolInfoDO();
			BeanCopier.staticCopy(queryOrder, insuranceProtocolInfoDO);
			long totalCount = insuranceProtocolInfoDAO
				.findByConditionCount(insuranceProtocolInfoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceProtocolInfoDO> recordList = insuranceProtocolInfoDAO.findByCondition(
				insuranceProtocolInfoDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (InsuranceProtocolInfoDO item : recordList) {
				InsuranceProtocolInfoInfo info = new InsuranceProtocolInfoInfo();
				BeanCopier.staticCopy(item, info);
				InsuranceProtocolChargeQueryOrder chargeQueryOrder = new InsuranceProtocolChargeQueryOrder();
				chargeQueryOrder.setProtocolInfoId(item.getProtocolInfoId());
				if(queryOrder.isQueryChargeInfo()){
					info.setChargeInfo(insuranceProtocolChargeService.queryProtocolCharge(chargeQueryOrder));
				}

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