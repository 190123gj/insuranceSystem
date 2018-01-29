package com.born.insurance.biz.service.settlementCompanyBillProcess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BrokerageFeeDetailDO;
import com.born.insurance.dal.dataobject.InvoiceRequisitionDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillProcessDO;
import com.born.insurance.dal.dataobject.SettlementInvoiceInformationDO;
import com.born.insurance.dal.daointerface.BrokerageFeeDetailDAO;
import com.born.insurance.dal.daointerface.InvoiceRequisitionDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillProcessDAO;
import com.born.insurance.dal.daointerface.SettlementInvoiceInformationDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.SettlementCompanyStatusEnum;
import com.born.insurance.ws.enums.SettlementProcessEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.invoiceRequisition.InvoiceRequisitionInfo;
import com.born.insurance.ws.info.settlementCompanyBillProcess.SettlementCompanyBillProcessInfo;
import com.born.insurance.ws.info.settlementInvoiceInformation.SettlementInvoiceInformationInfo;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessOrder;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.settlementCompanyBillProcess.SettlementCompanyBillProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("settlementCompanyBillProcessService")
public class SettlementCompanyBillProcessServiceImpl extends BaseAutowiredDomainService implements
																						SettlementCompanyBillProcessService {
	@Autowired
	protected SettlementCompanyBillProcessDAO settlementCompanyBillProcessDAO;
	
	@Autowired
	protected SettlementCompanyBillDAO settlementCompanyBillDAO;
	
	@Autowired
	protected InvoiceRequisitionDAO invoiceRequisitionDAO;
	
	@Autowired
	protected BrokerageFeeDetailDAO brokerageFeeDetailDAO;

	@Autowired
	protected SettlementInvoiceInformationDAO settlementInvoiceInformationDAO;
	@Override
	public InsuranceBaseResult save(final SettlementCompanyBillProcessOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					SettlementCompanyBillProcessDO doObj = new SettlementCompanyBillProcessDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					settlementCompanyBillProcessDAO.insert(doObj);
				} else {
					SettlementCompanyBillProcessDO doObj = settlementCompanyBillProcessDAO
						.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					settlementCompanyBillProcessDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public SettlementCompanyBillProcessInfo findById(long id) {
		SettlementCompanyBillProcessDO settlementCompanyBillProcessDo = settlementCompanyBillProcessDAO
			.findById(id);
		if (settlementCompanyBillProcessDo != null) {
			SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo = new SettlementCompanyBillProcessInfo();
			BeanCopier.staticCopy(settlementCompanyBillProcessDo, settlementCompanyBillProcessInfo);
			return settlementCompanyBillProcessInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<SettlementCompanyBillProcessInfo> queryList(SettlementCompanyBillProcessQueryOrder queryOrder) {
		QueryBaseBatchResult<SettlementCompanyBillProcessInfo> batchResult = new QueryBaseBatchResult<SettlementCompanyBillProcessInfo>();
		
		try {
			queryOrder.check();
			List<SettlementCompanyBillProcessInfo> pageList = new ArrayList<SettlementCompanyBillProcessInfo>(
				(int) queryOrder.getPageSize());
			
			SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = new SettlementCompanyBillProcessDO();
			BeanCopier.staticCopy(queryOrder, settlementCompanyBillProcessDO);
			settlementCompanyBillProcessDO.setInsuranceCompanyId(StringUtils.isBlank(queryOrder.getInsuranceCompanyId()) ? 0 :Long.valueOf(queryOrder.getInsuranceCompanyId()));
			long totalCount = settlementCompanyBillProcessDAO
				.findByConditionCount(settlementCompanyBillProcessDO,DateUtil.parse(queryOrder.getBeginDate()),DateUtil.parse(queryOrder.getEndDate()));
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<SettlementCompanyBillProcessDO> recordList = settlementCompanyBillProcessDAO
				.findByCondition(settlementCompanyBillProcessDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize(),DateUtil.parse(queryOrder.getBeginDate()),DateUtil.parse(queryOrder.getEndDate()));
			for (SettlementCompanyBillProcessDO item : recordList) {
				SettlementCompanyBillProcessInfo info = new SettlementCompanyBillProcessInfo();
				String billNo = item.getBillNo();
				SettlementInvoiceInformationInfo settlementInvoiceInformationInfo = new SettlementInvoiceInformationInfo();
				SettlementInvoiceInformationDO settlementInvoiceInformationDO = 	settlementInvoiceInformationDAO.getSettlementInvoiceInformation(billNo);
				if (null != settlementInvoiceInformationDO){
					BeanCopier.staticCopy(settlementInvoiceInformationDO, settlementInvoiceInformationInfo);
				}
				InvoiceRequisitionDO invoiceRequisitionDO = invoiceRequisitionDAO.getInvoiceRequisitionInfo(billNo);
				if (null != invoiceRequisitionDO) {
					info.setInvoiceRequisitionInfo(true);
				} else {
					info.setInvoiceRequisitionInfo(false);
				}
				BeanCopier.staticCopy(item, info);
				info.setSettlementInvoiceInformationInfo(settlementInvoiceInformationInfo);
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
	public void packet(SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo) throws Exception {
		try {
			SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = settlementCompanyBillProcessDAO.findById(settlementCompanyBillProcessInfo.getId());
			settlementCompanyBillProcessDO.setStatus(SettlementProcessEnum.UNPACKING.getCode());
			settlementCompanyBillProcessDAO.update(settlementCompanyBillProcessDO);
			String settlementCompanyBillIds = settlementCompanyBillProcessInfo.getSettlementCompanyIds();
			settlementCompanyBillDAO.updateSettlementCompanyBill(SettlementCompanyStatusEnum.UNTREATED.getCode(), settlementCompanyBillIds);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void recieve(String id) {
		try {
			//更改结算单状态为已收款
			SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = settlementCompanyBillProcessDAO.findById(Long.valueOf(id));
			settlementCompanyBillProcessDO.setStatus(SettlementProcessEnum.RECEIVABLES.getCode());
			settlementCompanyBillProcessDAO.update(settlementCompanyBillProcessDO);
			
			//更改待结算单为已结算
			settlementCompanyBillDAO.updateSettlementCompanyBill(SettlementCompanyStatusEnum.ALREADYSETTLED.getCode(), settlementCompanyBillProcessDO.getSettlementCompanyIds());
			
			List<SettlementCompanyBillDO> findSettlementCompanyBills = settlementCompanyBillDAO.findSettlementCompanyBills(settlementCompanyBillProcessDO.getSettlementCompanyIds());
			if (ListUtil.isNotEmpty(findSettlementCompanyBills)) {
				//更改保单信息中的经纪费的实收日期
				for (SettlementCompanyBillDO settlementCompanyBillDO : findSettlementCompanyBills) {
					long businessBillId = settlementCompanyBillDO.getBusinessBillId();
					String appserialPeriod = settlementCompanyBillDO.getAppserialPeriod();
					BrokerageFeeDetailDO brokerageFeeDetailDO = brokerageFeeDetailDAO.getBrokerageFeeDetail(businessBillId,appserialPeriod);
					if (null != brokerageFeeDetailDO) {
						BrokerageFeeDetailDO brokerageFeeDetail = brokerageFeeDetailDAO.findById(brokerageFeeDetailDO.getBrokerageFeeDetailId());
						brokerageFeeDetail.setActualPayDate(new Date());
						brokerageFeeDetailDAO.update(brokerageFeeDetail);
					}
				}
			}
		} catch (DataAccessException e) {
			logger.error("确认收款异常：", e.getMessage());
		} catch (NumberFormatException e) {
			logger.error("确认收款异常：", e.getMessage());
		}
	}
}