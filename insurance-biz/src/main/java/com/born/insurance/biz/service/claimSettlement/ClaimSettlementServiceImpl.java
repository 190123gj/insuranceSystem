package com.born.insurance.biz.service.claimSettlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.ClaimSettlementDO;
import com.born.insurance.dal.dataobject.ClaimSettlementProcessDO;
import com.born.insurance.dal.daointerface.ClaimSettlementDAO;
import com.born.insurance.dal.daointerface.ClaimSettlementProcessDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.claimSettlement.ClaimSettlementInfo;
import com.born.insurance.ws.order.claimSettlement.ClaimSettlementOrder;
import com.born.insurance.ws.order.claimSettlement.ClaimSettlementQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.claimSettlement.ClaimSettlementService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("claimSettlementService")
public class ClaimSettlementServiceImpl extends BaseAutowiredDomainService implements
																			ClaimSettlementService {
	@Autowired
	protected ClaimSettlementDAO claimSettlementDAO;
	
	@Autowired
	protected ClaimSettlementProcessDAO claimSettlementProcessDAO;
	
	@Autowired
	protected BusinessBillService businessBillService;
	
	@Override
	public InsuranceBaseResult save(final ClaimSettlementOrder order) {
		return commonProcess(order, "增加或修改理赔信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getClaimSettlementId();
				if (id <= 0) {
					ClaimSettlementDO doObj = new ClaimSettlementDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					claimSettlementDAO.insert(doObj);
					id = doObj.getClaimSettlementId();
				} else {
					ClaimSettlementDO doObj = claimSettlementDAO.findById(order
						.getClaimSettlementId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					claimSettlementDAO.update(doObj);
				}
				
				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(id);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ClaimSettlementInfo findById(long id) {
		ClaimSettlementDO claimSettlementDo = claimSettlementDAO.findById(id);
		if (claimSettlementDo != null) {
			ClaimSettlementInfo claimSettlementInfo = new ClaimSettlementInfo();
			BeanCopier.staticCopy(claimSettlementDo, claimSettlementInfo);
			return claimSettlementInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ClaimSettlementInfo> queryList(ClaimSettlementQueryOrder queryOrder) {
		QueryBaseBatchResult<ClaimSettlementInfo> batchResult = new QueryBaseBatchResult<ClaimSettlementInfo>();
		
		try {
			queryOrder.check();
			List<ClaimSettlementInfo> pageList = new ArrayList<ClaimSettlementInfo>(
				(int) queryOrder.getPageSize());
			
			ClaimSettlementDO claimSettlementDO = new ClaimSettlementDO();
			BeanCopier.staticCopy(queryOrder, claimSettlementDO);
			long totalCount = claimSettlementDAO.findByConditionCount(claimSettlementDO,queryOrder.getStartTime(),queryOrder.getEndTime());
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ClaimSettlementDO> recordList = claimSettlementDAO.findByCondition(
				claimSettlementDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize(),queryOrder.getStartTime(),queryOrder.getEndTime());
			for (ClaimSettlementDO item : recordList) {
				ClaimSettlementInfo info = new ClaimSettlementInfo();
				BeanCopier.staticCopy(item, info);
				BusinessBillInfo businessBillInfo = businessBillService.findById(info.getBusinessBillId());
				if (null != businessBillInfo) {
					info.setInsuranceNo(businessBillInfo.getInsuranceNo());
				}
				ClaimSettlementProcessDO claimSettlementProcess = new ClaimSettlementProcessDO();
				claimSettlementProcess.setClaimSettlementId(item.getClaimSettlementId());
				List<ClaimSettlementProcessDO> lists = claimSettlementProcessDAO.findByCondition(claimSettlementProcess, "raw_add_time", "DESC", 0, 1);
				if (ListUtil.isNotEmpty(lists)) {
					ClaimSettlementProcessDO claimSettlementProcessDO = lists.get(0);
					info.setSchedule(claimSettlementProcessDO.getType());
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