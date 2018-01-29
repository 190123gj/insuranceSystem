package com.born.insurance.biz.service.claimSettlementProcess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.ClaimSettlementProcessDO;
import com.born.insurance.dal.daointerface.ClaimSettlementProcessDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.claimSettlementProcess.ClaimSettlementProcessInfo;
import com.born.insurance.ws.order.claimSettlementProcess.ClaimSettlementProcessOrder;
import com.born.insurance.ws.order.claimSettlementProcess.ClaimSettlementProcessQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.claimSettlementProcess.ClaimSettlementProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("claimSettlementProcessService")
public class ClaimSettlementProcessServiceImpl extends BaseAutowiredDomainService implements
																					ClaimSettlementProcessService {
	@Autowired
	protected ClaimSettlementProcessDAO claimSettlementProcessDAO;
	
	@Override
	public InsuranceBaseResult save(final ClaimSettlementProcessOrder order) {
		return commonProcess(order, "理赔进度维护信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getSettlementProcessId();
				if (id <= 0) {
					ClaimSettlementProcessDO doObj = new ClaimSettlementProcessDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					claimSettlementProcessDAO.insert(doObj);
				} else {
					ClaimSettlementProcessDO doObj = claimSettlementProcessDAO.findById(order
						.getSettlementProcessId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					claimSettlementProcessDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ClaimSettlementProcessInfo findById(long id) {
		ClaimSettlementProcessDO claimSettlementProcessDo = claimSettlementProcessDAO.findById(id);
		if (claimSettlementProcessDo != null) {
			ClaimSettlementProcessInfo claimSettlementProcessInfo = new ClaimSettlementProcessInfo();
			BeanCopier.staticCopy(claimSettlementProcessDo, claimSettlementProcessInfo);
			return claimSettlementProcessInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ClaimSettlementProcessInfo> queryList(	ClaimSettlementProcessQueryOrder queryOrder) {
		QueryBaseBatchResult<ClaimSettlementProcessInfo> batchResult = new QueryBaseBatchResult<ClaimSettlementProcessInfo>();
		
		try {
			queryOrder.check();
			List<ClaimSettlementProcessInfo> pageList = new ArrayList<ClaimSettlementProcessInfo>(
				(int) queryOrder.getPageSize());
			ClaimSettlementProcessDO claimSettlementProcessDO = new ClaimSettlementProcessDO();
			BeanCopier.staticCopy(queryOrder, claimSettlementProcessDO);
			long totalCount = claimSettlementProcessDAO.findByConditionCount(claimSettlementProcessDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ClaimSettlementProcessDO> recordList = claimSettlementProcessDAO.findByCondition(
				claimSettlementProcessDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (ClaimSettlementProcessDO item : recordList) {
				ClaimSettlementProcessInfo info = new ClaimSettlementProcessInfo();
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