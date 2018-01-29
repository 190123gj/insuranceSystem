package com.born.insurance.biz.service.businessBillCustomer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillBeneficiaryDO;
import com.born.insurance.dal.dataobject.BusinessBillCustomerDO;
import com.born.insurance.dal.daointerface.BusinessBillBeneficiaryDAO;
import com.born.insurance.dal.daointerface.BusinessBillCustomerDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBill.BusinessBillBeneficiaryInfo;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBillCustomer.BusinessBillCustomerService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("businessBillCustomerService")
public class BusinessBillCustomerServiceImpl extends BaseAutowiredDomainService implements
																				BusinessBillCustomerService {
	@Autowired
	protected BusinessBillCustomerDAO businessBillCustomerDAO;
	
	@Autowired
	protected BusinessBillBeneficiaryDAO businessBillBeneficiaryDAO;
	
	@Override
	public InsuranceBaseResult save(final BusinessBillCustomerOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getBillCustomerId();
				if (id <= 0) {
					BusinessBillCustomerDO doObj = new BusinessBillCustomerDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCustomerDAO.insert(doObj);
				} else {
					BusinessBillCustomerDO doObj = businessBillCustomerDAO.findById(order
						.getBillCustomerId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					businessBillCustomerDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BusinessBillCustomerInfo findById(long id) {
		BusinessBillCustomerDO businessBillCustomerDo = businessBillCustomerDAO.findById(id);
		if (businessBillCustomerDo != null) {
			BusinessBillCustomerInfo businessBillCustomerInfo = new BusinessBillCustomerInfo();
			BeanCopier.staticCopy(businessBillCustomerDo, businessBillCustomerInfo);
			return businessBillCustomerInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BusinessBillCustomerInfo> queryList(BusinessBillCustomerQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillCustomerInfo> batchResult = new QueryBaseBatchResult<BusinessBillCustomerInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillCustomerInfo> pageList = new ArrayList<BusinessBillCustomerInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillCustomerDO businessBillCustomerDO = new BusinessBillCustomerDO();
			BeanCopier.staticCopy(queryOrder, businessBillCustomerDO);
			long totalCount = businessBillCustomerDAO.findByConditionCount(businessBillCustomerDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillCustomerDO> recordList = businessBillCustomerDAO.findByCondition(
				businessBillCustomerDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
				for (BusinessBillCustomerDO item : recordList) {
					BusinessBillCustomerInfo info = new BusinessBillCustomerInfo();
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
	public List<BusinessBillCustomerInfo> findBusinessBillCustomers(String billNo) {
		List<BusinessBillCustomerInfo> pageList = new ArrayList<BusinessBillCustomerInfo>();
		List<BusinessBillCustomerDO> list = null ;
		
		try {
			list = businessBillCustomerDAO.findBusinessBillCustomers(billNo);
			for (BusinessBillCustomerDO item : list) {
				BusinessBillCustomerInfo info = new BusinessBillCustomerInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
		} catch (Exception e) {
			logger.error("投保人被保险人信息",  e.getMessage());
		}
		
		return pageList;
	}

	@Override
	public List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys(long letterId) {
		List<BusinessBillBeneficiaryInfo> pageList = new ArrayList<BusinessBillBeneficiaryInfo>();
		List<BusinessBillBeneficiaryDO> list = null ;
		
		try {
			list = businessBillBeneficiaryDAO.findBusinessBillBeneficiarys(letterId);
			for (BusinessBillBeneficiaryDO item : list) {
				BusinessBillBeneficiaryInfo info = new BusinessBillBeneficiaryInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
		} catch (Exception e) {
			logger.error("受益人信息",  e.getMessage());
		}
		return pageList;
	}
}