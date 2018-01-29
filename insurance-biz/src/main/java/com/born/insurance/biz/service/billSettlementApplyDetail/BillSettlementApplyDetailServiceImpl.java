package com.born.insurance.biz.service.billSettlementApplyDetail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BillSettlementApplyDetailDO;
import com.born.insurance.dal.daointerface.BillSettlementApplyDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.billSettlementApplyDetail.BillSettlementApplyDetailInfo;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailOrder;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.billSettlementApplyDetail.BillSettlementApplyDetailService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("billSettlementApplyDetailService")
public class BillSettlementApplyDetailServiceImpl extends BaseAutowiredDomainService implements BillSettlementApplyDetailService {
	@Autowired
	protected BillSettlementApplyDetailDAO billSettlementApplyDetailDAO;

	@Override
	public InsuranceBaseResult save(final BillSettlementApplyDetailOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getId();
				if (id <= 0) {
					BillSettlementApplyDetailDO doObj = new BillSettlementApplyDetailDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					billSettlementApplyDetailDAO.insert(doObj);
				} else {
					BillSettlementApplyDetailDO doObj = billSettlementApplyDetailDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					billSettlementApplyDetailDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BillSettlementApplyDetailInfo findById(long id) {
		BillSettlementApplyDetailDO billSettlementApplyDetailDo= billSettlementApplyDetailDAO.findById(id);
        if(billSettlementApplyDetailDo != null){
            BillSettlementApplyDetailInfo billSettlementApplyDetailInfo = new BillSettlementApplyDetailInfo();
            BeanCopier.staticCopy(billSettlementApplyDetailDo,billSettlementApplyDetailInfo);
            return billSettlementApplyDetailInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BillSettlementApplyDetailInfo> queryList(BillSettlementApplyDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<BillSettlementApplyDetailInfo> batchResult = new QueryBaseBatchResult<BillSettlementApplyDetailInfo>();
		
		try {
			queryOrder.check();
			List<BillSettlementApplyDetailInfo> pageList = new ArrayList<BillSettlementApplyDetailInfo>((int) queryOrder.getPageSize());
			
			BillSettlementApplyDetailDO billSettlementApplyDetailDO = new BillSettlementApplyDetailDO();
			BeanCopier.staticCopy(queryOrder, billSettlementApplyDetailDO);
			long totalCount = billSettlementApplyDetailDAO.findByConditionCount(billSettlementApplyDetailDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BillSettlementApplyDetailDO> recordList = billSettlementApplyDetailDAO.findByCondition(billSettlementApplyDetailDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (BillSettlementApplyDetailDO item : recordList) {
				BillSettlementApplyDetailInfo info = new BillSettlementApplyDetailInfo();
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
	public BillSettlementApplyDetailInfo findBySettlementApplyId(long id) {
		
		BillSettlementApplyDetailInfo info = new BillSettlementApplyDetailInfo();
		BillSettlementApplyDetailDO billSettlementApplyDetailDO = billSettlementApplyDetailDAO.findBySettlementApplyId(id);
		if (null != billSettlementApplyDetailDO) {
			BeanCopier.staticCopy(billSettlementApplyDetailDO, info);
			return info;
		}
		return null;
	}
}