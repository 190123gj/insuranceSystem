package com.born.insurance.biz.service.commissionInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.CommissionInfoDO;
import com.born.insurance.dal.dataobject.CommissionInfoDetailDO;
import com.born.insurance.dal.daointerface.CommissionInfoDAO;
import com.born.insurance.dal.daointerface.CommissionInfoDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoOrder;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.commissionInfo.CommissionInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("commissionInfoService")
public class CommissionInfoServiceImpl extends BaseAutowiredDomainService implements CommissionInfoService {
	@Autowired
	protected CommissionInfoDAO commissionInfoDAO;
	
	@Autowired
	protected CommissionInfoDetailDAO commissionInfoDetailDAO;

	@Override
	public InsuranceBaseResult save(final CommissionInfoOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getCommissionInfoId();
				if (id <= 0) {
					CommissionInfoDO doObj = new CommissionInfoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					commissionInfoDAO.insert(doObj);
				} else {
					CommissionInfoDO doObj = commissionInfoDAO.findById(order.getCommissionInfoId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					commissionInfoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public CommissionInfoInfo findById(long id) {
		CommissionInfoDO commissionInfoDo= commissionInfoDAO.findById(id);
        if(commissionInfoDo != null){
            CommissionInfoInfo commissionInfoInfo = new CommissionInfoInfo();
            BeanCopier.staticCopy(commissionInfoDo,commissionInfoInfo);
            return commissionInfoInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<CommissionInfoInfo> queryList(CommissionInfoQueryOrder queryOrder) {
		QueryBaseBatchResult<CommissionInfoInfo> batchResult = new QueryBaseBatchResult<CommissionInfoInfo>();
		
		try {
			queryOrder.check();
			List<CommissionInfoInfo> pageList = new ArrayList<CommissionInfoInfo>((int) queryOrder.getPageSize());
			
			CommissionInfoDO commissionInfoDO = new CommissionInfoDO();
			BeanCopier.staticCopy(queryOrder, commissionInfoDO);
			long totalCount = commissionInfoDAO.findByConditionCount(commissionInfoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<CommissionInfoDO> recordList = commissionInfoDAO.findByCondition(commissionInfoDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (CommissionInfoDO item : recordList) {
				CommissionInfoInfo info = new CommissionInfoInfo();
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
	public CommissionInfoInfo findCommissionInfoByBusinessBillId(long businessBillId) {
		CommissionInfoInfo commissionInfoInfo = new CommissionInfoInfo();
		CommissionInfoDO commissionInfoDO = null ;
		try {
			commissionInfoDO = commissionInfoDAO.findCommissionInfoByBusinessBillId(businessBillId);
			if (null != commissionInfoDO) {
				BeanCopier.staticCopy(commissionInfoDO, commissionInfoInfo);
			}
		} catch (Exception e) {
			logger.error("保单佣金信息查询失败：", e.getMessage());
		}
		return commissionInfoInfo;
	}

	@Override
	public List<CommissionInfoDetailInfo> findCommissionInfoDetails(long commissionInfoId) {
		List<CommissionInfoDetailInfo> details = new ArrayList<CommissionInfoDetailInfo>();
		List<CommissionInfoDetailDO> recordList = commissionInfoDetailDAO.findCommissionInfoDetails(commissionInfoId);
		for (CommissionInfoDetailDO commissionInfoDetailDO : recordList) {
			CommissionInfoDetailInfo info = new CommissionInfoDetailInfo();
			BeanCopier.staticCopy(commissionInfoDetailDO, info);
			details.add(info);
		}
		return details;
	}
}