package com.born.insurance.biz.service.businessBillCorrecting;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillCorrectingDO;
import com.born.insurance.dal.daointerface.BusinessBillCorrectingDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBillCorrecting.BusinessBillCorrectingInfo;
import com.born.insurance.ws.order.businessBillCorrecting.BusinessBillCorrectingOrder;
import com.born.insurance.ws.order.businessBillCorrecting.BusinessBillCorrectingQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillCorrecting.BusinessBillCorrectingService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillCorrectingService")
public class BusinessBillCorrectingServiceImpl extends BaseAutowiredDomainService implements BusinessBillCorrectingService {
	@Autowired
	protected BusinessBillCorrectingDAO businessBillCorrectingDAO;

	@Override
	public InsuranceBaseResult save(final BusinessBillCorrectingOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getBusinessBillCorrectingId();
				if (id <= 0) {
					BusinessBillCorrectingDO doObj = new BusinessBillCorrectingDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					businessBillCorrectingDAO.insert(doObj);
				} else {
					BusinessBillCorrectingDO doObj = businessBillCorrectingDAO.findById(order.getBusinessBillCorrectingId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCorrectingDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillCorrectingInfo findById(long id) {
		BusinessBillCorrectingDO businessBillCorrectingDo= businessBillCorrectingDAO.findById(id);
        if(businessBillCorrectingDo != null){
            BusinessBillCorrectingInfo businessBillCorrectingInfo = new BusinessBillCorrectingInfo();
            BeanCopier.staticCopy(businessBillCorrectingDo,businessBillCorrectingInfo);
            return businessBillCorrectingInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillCorrectingInfo> queryList(BusinessBillCorrectingQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillCorrectingInfo> batchResult = new QueryBaseBatchResult<BusinessBillCorrectingInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillCorrectingInfo> pageList = new ArrayList<BusinessBillCorrectingInfo>((int) queryOrder.getPageSize());
			
			BusinessBillCorrectingDO businessBillCorrectingDO = new BusinessBillCorrectingDO();
			BeanCopier.staticCopy(queryOrder, businessBillCorrectingDO);
			long totalCount = businessBillCorrectingDAO.findByConditionCount(businessBillCorrectingDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillCorrectingDO> recordList = businessBillCorrectingDAO.findByCondition(businessBillCorrectingDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (BusinessBillCorrectingDO item : recordList) {
				BusinessBillCorrectingInfo info = new BusinessBillCorrectingInfo();
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