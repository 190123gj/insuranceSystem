package com.born.insurance.biz.service.businessBillUnderInfor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.BusinessBillUnderInforDAO;
import com.born.insurance.dal.dataobject.BusinessBillUnderInforDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.businessBillUnderInfor.BusinessBillUnderInforInfo;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforOrder;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillUnderInfor.BusinessBillUnderInforService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillUnderInforService")
public class BusinessBillUnderInforServiceImpl extends BaseAutowiredDomainService implements
																					BusinessBillUnderInforService {
	@Autowired
	protected BusinessBillUnderInforDAO businessBillUnderInforDAO;
	
	@Override
	public InsuranceBaseResult save(final BusinessBillUnderInforOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getUnderInfoId();
				if (id <= 0) {
					BusinessBillUnderInforDO doObj = new BusinessBillUnderInforDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillUnderInforDAO.insert(doObj);
				} else {
					BusinessBillUnderInforDO doObj = businessBillUnderInforDAO.findById(order
						.getUnderInfoId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillUnderInforDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillUnderInforInfo findById(long id) {
		BusinessBillUnderInforDO businessBillUnderInforDo = businessBillUnderInforDAO.findById(id);
		if (businessBillUnderInforDo != null) {
			BusinessBillUnderInforInfo businessBillUnderInforInfo = new BusinessBillUnderInforInfo();
			BeanCopier.staticCopy(businessBillUnderInforDo, businessBillUnderInforInfo);
			return businessBillUnderInforInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillUnderInforInfo> queryList(	BusinessBillUnderInforQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillUnderInforInfo> batchResult = new QueryBaseBatchResult<BusinessBillUnderInforInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillUnderInforInfo> pageList = new ArrayList<BusinessBillUnderInforInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillUnderInforDO businessBillUnderInforDO = new BusinessBillUnderInforDO();
			BeanCopier.staticCopy(queryOrder, businessBillUnderInforDO);
			long totalCount = businessBillUnderInforDAO
				.findByConditionCount(businessBillUnderInforDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillUnderInforDO> recordList = businessBillUnderInforDAO.findByCondition(
				businessBillUnderInforDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (BusinessBillUnderInforDO item : recordList) {
				BusinessBillUnderInforInfo info = new BusinessBillUnderInforInfo();
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
	public List<BusinessBillUnderInforInfo> findBusinessBillUnderInfo(long businessBillId) {
		List<BusinessBillUnderInforInfo> pageList = new ArrayList<BusinessBillUnderInforInfo>();
		List<BusinessBillUnderInforDO> list = null;
		try {
			list = businessBillUnderInforDAO.findBusinessBillUnderInfo(businessBillId);
			for (BusinessBillUnderInforDO businessBillUnderInforDO : list) {
				BusinessBillUnderInforInfo info = new BusinessBillUnderInforInfo();
				BeanCopier.staticCopy(businessBillUnderInforDO, info);
				pageList.add(info);
			}
		} catch (Exception e) {
			logger.error("获取保单承保信息:",e.getMessage());
		}
		return pageList;
	}
}