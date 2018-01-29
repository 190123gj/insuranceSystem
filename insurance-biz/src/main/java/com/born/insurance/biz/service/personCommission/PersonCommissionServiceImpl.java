package com.born.insurance.biz.service.personCommission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PersonCommissionDO;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.dal.dataobject.PersonCommissionProcessDO;
import com.born.insurance.dal.daointerface.PersonCommissionDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDetailDAO;
import com.born.insurance.dal.daointerface.PersonCommissionProcessDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.SettlementStatusEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.personCommission.PersonCommissionInfo;
import com.born.insurance.ws.info.personCommissionDetail.PersonCommissionDetailInfo;
import com.born.insurance.ws.order.personCommission.PersonCommissionOrder;
import com.born.insurance.ws.order.personCommission.PersonCommissionQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.personCommission.PersonCommissionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("personCommissionService")
public class PersonCommissionServiceImpl extends BaseAutowiredDomainService implements
																			PersonCommissionService {
	@Autowired
	protected PersonCommissionDAO personCommissionDAO;
	
	@Autowired
	protected PersonCommissionDetailDAO personCommissionDetailDAO;
	
	@Autowired
	protected PersonCommissionProcessDAO personCommissionProcessDAO;
	
	@Override
	public InsuranceBaseResult save(final PersonCommissionOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getCommissionId();
				if (id <= 0) {
					PersonCommissionDO doObj = new PersonCommissionDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					personCommissionDAO.insert(doObj);
				} else {
					PersonCommissionDO doObj = personCommissionDAO.findById(order.getCommissionId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					Money applicationSettlementAmount=order.getApplicationSettlementAmount();
					if (doObj.getApplyingAmount().add(applicationSettlementAmount).greaterThan(doObj.getTotalAmount().subtract(doObj.getDrawAmount()))) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.NO_BALANCE,"余额不足");
					}
					//新增结算申请
					PersonCommissionProcessDO personCommissionProcessDO = new PersonCommissionProcessDO();
					personCommissionProcessDO.setApplyDrawAmount(applicationSettlementAmount);
					personCommissionProcessDO.setBusinessUserId(doObj.getBusinessUserId());
					personCommissionProcessDO.setBusinessUserName(doObj.getBusinessUserName());
					personCommissionProcessDO.setBusinessUserMobile(doObj.getBusinessUserMobile());
					personCommissionProcessDO.setRawAddTime(now);
					personCommissionProcessDO.setStatus(SettlementStatusEnum.PROCESSING.getCode());
					personCommissionProcessDO.setSettlementNumber("");
					personCommissionProcessDAO.insert(personCommissionProcessDO);
					
					doObj.setApplyingAmount(applicationSettlementAmount.add(doObj.getApplyingAmount()));
					personCommissionDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	
	
	
	
	
	
	@Override
	public PersonCommissionInfo findById(long id) {
		PersonCommissionDO personCommissionDo = personCommissionDAO.findById(id);
		if (personCommissionDo != null) {
			PersonCommissionInfo personCommissionInfo = new PersonCommissionInfo();
			BeanCopier.staticCopy(personCommissionDo, personCommissionInfo);
			return personCommissionInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PersonCommissionInfo> queryList(PersonCommissionQueryOrder queryOrder) {
		QueryBaseBatchResult<PersonCommissionInfo> batchResult = new QueryBaseBatchResult<PersonCommissionInfo>();
		
		try {
			queryOrder.check();
			List<PersonCommissionInfo> pageList = new ArrayList<PersonCommissionInfo>(
				(int) queryOrder.getPageSize());
			
			PersonCommissionDO personCommissionDO = new PersonCommissionDO();
			BeanCopier.staticCopy(queryOrder, personCommissionDO);
			long totalCount = personCommissionDAO.findByConditionCount(personCommissionDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PersonCommissionDO> recordList = personCommissionDAO.findByCondition(
				personCommissionDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (PersonCommissionDO item : recordList) {
				PersonCommissionInfo info = new PersonCommissionInfo();
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
	public InsuranceBaseResult update(final PersonCommissionOrder personCommissionOrder) {
		return commonProcess(personCommissionOrder, "修改业务员佣金结算", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				PersonCommissionDO personCommissionDO=personCommissionDAO.findById(personCommissionOrder.getCommissionId());
				return null;
			}
		}, null, null);
	}


	@Override
	public List<PersonCommissionDetailInfo> getCommissionInfoDetails(long businessUserId) {
		List<PersonCommissionDetailInfo> details = new ArrayList<PersonCommissionDetailInfo>();
		List<PersonCommissionDetailDO> recordList = null ;
		try {
			recordList = personCommissionDetailDAO.getCommissionInfoDetails(businessUserId);
			for (PersonCommissionDetailDO item : recordList) {
				PersonCommissionDetailInfo info = new PersonCommissionDetailInfo();
				BeanCopier.staticCopy(item, info);
				details.add(info);
			}
		} catch (Exception e) {
			logger.error("查询业务员佣金明细失败:", e.getMessage());
		}
		return details;
	}
}