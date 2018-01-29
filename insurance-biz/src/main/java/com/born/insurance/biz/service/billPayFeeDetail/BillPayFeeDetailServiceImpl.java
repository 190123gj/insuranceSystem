package com.born.insurance.biz.service.billPayFeeDetail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BillPayFeeDetailDO;
import com.born.insurance.dal.daointerface.BillPayFeeDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.billPayFeeDetail.BillPayFeeDetailInfo;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailOrder;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.billPayFeeDetail.BillPayFeeDetailService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("billPayFeeDetailService")
public class BillPayFeeDetailServiceImpl extends BaseAutowiredDomainService implements BillPayFeeDetailService {
	@Autowired
	protected BillPayFeeDetailDAO billPayFeeDetailDAO;

	@Override
	public InsuranceBaseResult save(final BillPayFeeDetailOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getId();
				if (id <= 0) {
					BillPayFeeDetailDO doObj = new BillPayFeeDetailDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					billPayFeeDetailDAO.insert(doObj);
				} else {
					BillPayFeeDetailDO doObj = billPayFeeDetailDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					billPayFeeDetailDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BillPayFeeDetailInfo findById(long id) {
		BillPayFeeDetailDO billPayFeeDetailDo= billPayFeeDetailDAO.findById(id);
        if(billPayFeeDetailDo != null){
            BillPayFeeDetailInfo billPayFeeDetailInfo = new BillPayFeeDetailInfo();
            BeanCopier.staticCopy(billPayFeeDetailDo,billPayFeeDetailInfo);
            return billPayFeeDetailInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BillPayFeeDetailInfo> queryList(BillPayFeeDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<BillPayFeeDetailInfo> batchResult = new QueryBaseBatchResult<BillPayFeeDetailInfo>();
		
		try {
			queryOrder.check();
			List<BillPayFeeDetailInfo> pageList = new ArrayList<BillPayFeeDetailInfo>((int) queryOrder.getPageSize());
			
			BillPayFeeDetailDO billPayFeeDetailDO = new BillPayFeeDetailDO();
			BeanCopier.staticCopy(queryOrder, billPayFeeDetailDO);
			long totalCount = billPayFeeDetailDAO.findByConditionCount(billPayFeeDetailDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BillPayFeeDetailDO> recordList = billPayFeeDetailDAO.findByCondition(billPayFeeDetailDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (BillPayFeeDetailDO item : recordList) {
				BillPayFeeDetailInfo info = new BillPayFeeDetailInfo();
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
	public List<BillPayFeeDetailInfo> findBySettlementApplyId(long id) {
		
		List<BillPayFeeDetailInfo> pageList = new ArrayList<BillPayFeeDetailInfo>();
		List<BillPayFeeDetailDO>  list= billPayFeeDetailDAO.findBySettlementApplyId(id);
		for (BillPayFeeDetailDO billPayFeeDetailDO : list) {
			BillPayFeeDetailInfo info = new BillPayFeeDetailInfo();
			BeanCopier.staticCopy(billPayFeeDetailDO, info);
			pageList.add(info);
			
		}
		return pageList;
	}
}