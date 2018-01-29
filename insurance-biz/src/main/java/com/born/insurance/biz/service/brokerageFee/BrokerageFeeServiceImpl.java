package com.born.insurance.biz.service.brokerageFee;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BrokerageFeeDO;
import com.born.insurance.dal.dataobject.BrokerageFeeDetailDO;
import com.born.insurance.dal.daointerface.BrokerageFeeDAO;
import com.born.insurance.dal.daointerface.BrokerageFeeDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeOrder;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.brokerageFee.BrokerageFeeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("brokerageFeeService")
public class BrokerageFeeServiceImpl extends BaseAutowiredDomainService implements BrokerageFeeService {
	@Autowired
	protected BrokerageFeeDAO brokerageFeeDAO;
	
	@Autowired
	protected BrokerageFeeDetailDAO  brokerageFeeDetailDAO;

	@Override
	public InsuranceBaseResult save(final BrokerageFeeOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getBrokerageId();
				if (id <= 0) {
					BrokerageFeeDO doObj = new BrokerageFeeDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					brokerageFeeDAO.insert(doObj);
				} else {
					BrokerageFeeDO doObj = brokerageFeeDAO.findById(order.getBrokerageId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					brokerageFeeDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BrokerageFeeInfo findById(long id) {
		BrokerageFeeDO brokerageFeeDo= brokerageFeeDAO.findById(id);
        if(brokerageFeeDo != null){
            BrokerageFeeInfo brokerageFeeInfo = new BrokerageFeeInfo();
            BeanCopier.staticCopy(brokerageFeeDo,brokerageFeeInfo);
            return brokerageFeeInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BrokerageFeeInfo> queryList(BrokerageFeeQueryOrder queryOrder) {
		QueryBaseBatchResult<BrokerageFeeInfo> batchResult = new QueryBaseBatchResult<BrokerageFeeInfo>();
		
		try {
			queryOrder.check();
			List<BrokerageFeeInfo> pageList = new ArrayList<BrokerageFeeInfo>((int) queryOrder.getPageSize());
			
			BrokerageFeeDO brokerageFeeDO = new BrokerageFeeDO();
			BeanCopier.staticCopy(queryOrder, brokerageFeeDO);
			long totalCount = brokerageFeeDAO.findByConditionCount(brokerageFeeDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BrokerageFeeDO> recordList = brokerageFeeDAO.findByCondition(brokerageFeeDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (BrokerageFeeDO item : recordList) {
				BrokerageFeeInfo info = new BrokerageFeeInfo();
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
	public BrokerageFeeInfo findBrokerageFeeByBusinessBillId(long businessBillId) {
		BrokerageFeeInfo brokerageFeeInfo = new BrokerageFeeInfo() ;
		BrokerageFeeDO brokerageFeeDO = null ;
		try {
			brokerageFeeDO = brokerageFeeDAO.findBrokerageFeeByBusinessBillId(businessBillId);
			if (null != brokerageFeeDO) {
				BeanCopier.staticCopy(brokerageFeeDO, brokerageFeeInfo);
			}
		} catch (Exception e) {
			logger.error("查询保单经纪费失败:",e.getMessage());
		}
		return brokerageFeeInfo;
	}

	@Override
	public List<BrokerageFeeDetailInfo> findBrokerageFeeDetails(long brokerageId) {
		List<BrokerageFeeDetailInfo> details = new ArrayList<BrokerageFeeDetailInfo>();
		List<BrokerageFeeDetailDO> recordList = brokerageFeeDetailDAO.findBrokerageFeeDetails(brokerageId);
		for (BrokerageFeeDetailDO item : recordList) {
			BrokerageFeeDetailInfo info = new BrokerageFeeDetailInfo();
			BeanCopier.staticCopy(item, info);
			details.add(info);
		}
		return details;
	}
}