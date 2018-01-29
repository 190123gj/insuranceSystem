package com.born.insurance.biz.service.settlementInvoiceInformation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.SettlementCompanyBillProcessDO;
import com.born.insurance.dal.dataobject.SettlementInvoiceInformationDO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillProcessDAO;
import com.born.insurance.dal.daointerface.SettlementInvoiceInformationDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.SettlementProcessEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.settlementInvoiceInformation.SettlementInvoiceInformationInfo;
import com.born.insurance.ws.order.settlementInvoiceInformation.SettlementInvoiceInformationOrder;
import com.born.insurance.ws.order.settlementInvoiceInformation.SettlementInvoiceInformationQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.settlementInvoiceInformation.SettlementInvoiceInformationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("settlementInvoiceInformationService")
public class SettlementInvoiceInformationServiceImpl extends BaseAutowiredDomainService implements SettlementInvoiceInformationService {
	@Autowired
	protected SettlementInvoiceInformationDAO settlementInvoiceInformationDAO;
	
	@Autowired
	protected SettlementCompanyBillProcessDAO settlementCompanyBillProcessDAO;

	@Override
	public InsuranceBaseResult save(final SettlementInvoiceInformationOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getInvoiceId();
				if (id <= 0) {
					SettlementInvoiceInformationDO doObj = new SettlementInvoiceInformationDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setRawAddTime(now);
					settlementInvoiceInformationDAO.insert(doObj);
					//变更记录为待收款
					String billNo = doObj.getBillNo();
					SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = 	settlementCompanyBillProcessDAO.findSettlementCompanyBillProcess(billNo);
					settlementCompanyBillProcessDO.setStatus(SettlementProcessEnum.ACCOUNTRECEIVABLE.getCode());
					settlementCompanyBillProcessDAO.update(settlementCompanyBillProcessDO);
				} else {
					SettlementInvoiceInformationDO doObj = settlementInvoiceInformationDAO.findById(order.getInvoiceId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					settlementInvoiceInformationDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public SettlementInvoiceInformationInfo findById(long id) {
		SettlementInvoiceInformationDO settlementInvoiceInformationDo= settlementInvoiceInformationDAO.findById(id);
        if(settlementInvoiceInformationDo != null){
            SettlementInvoiceInformationInfo settlementInvoiceInformationInfo = new SettlementInvoiceInformationInfo();
            BeanCopier.staticCopy(settlementInvoiceInformationDo,settlementInvoiceInformationInfo);
            return settlementInvoiceInformationInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<SettlementInvoiceInformationInfo> queryList(SettlementInvoiceInformationQueryOrder queryOrder) {
		QueryBaseBatchResult<SettlementInvoiceInformationInfo> batchResult = new QueryBaseBatchResult<SettlementInvoiceInformationInfo>();
		
		try {
			queryOrder.check();
			List<SettlementInvoiceInformationInfo> pageList = new ArrayList<SettlementInvoiceInformationInfo>((int) queryOrder.getPageSize());
			
			SettlementInvoiceInformationDO settlementInvoiceInformationDO = new SettlementInvoiceInformationDO();
			BeanCopier.staticCopy(queryOrder, settlementInvoiceInformationDO);
			long totalCount = settlementInvoiceInformationDAO.findByConditionCount(settlementInvoiceInformationDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<SettlementInvoiceInformationDO> recordList = settlementInvoiceInformationDAO.findByCondition(settlementInvoiceInformationDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (SettlementInvoiceInformationDO item : recordList) {
				SettlementInvoiceInformationInfo info = new SettlementInvoiceInformationInfo();
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