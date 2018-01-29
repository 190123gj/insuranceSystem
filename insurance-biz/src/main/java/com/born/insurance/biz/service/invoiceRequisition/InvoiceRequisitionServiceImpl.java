package com.born.insurance.biz.service.invoiceRequisition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BrokerageFeeDO;
import com.born.insurance.dal.dataobject.BusinessBillDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDetailDO;
import com.born.insurance.dal.dataobject.InsuranceProductDO;
import com.born.insurance.dal.dataobject.InvoiceRequisitionDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillProcessDO;
import com.born.insurance.dal.daointerface.BrokerageFeeDAO;
import com.born.insurance.dal.daointerface.BusinessBillDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceProductDAO;
import com.born.insurance.dal.daointerface.InvoiceRequisitionDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillProcessDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.invoiceRequisition.InvoiceRequisitionInfo;
import com.born.insurance.ws.info.invoiceRequisition.SettlementBillInfo;
import com.born.insurance.ws.order.invoiceRequisition.InvoiceRequisitionOrder;
import com.born.insurance.ws.order.invoiceRequisition.InvoiceRequisitionQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.invoiceRequisition.InvoiceRequisitionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("invoiceRequisitionService")
public class InvoiceRequisitionServiceImpl extends BaseAutowiredDomainService implements InvoiceRequisitionService {
	@Autowired
	protected InvoiceRequisitionDAO invoiceRequisitionDAO;
	
	@Autowired
	protected SettlementCompanyBillProcessDAO settlementCompanyBillProcessDAO;
	
	@Autowired
	protected SettlementCompanyBillDAO settlementCompanyBillDAO;
	
	@Autowired
	protected BusinessBillDAO businessBillDAO;
	
	@Autowired
	protected BrokerageFeeDAO brokerageFeeDAO;
	
	@Autowired
	protected InsuranceContactLetterDAO insuranceContactLetterDAO;
	
	@Autowired
	protected InsuranceContactLetterDetailDAO insuranceContactLetterDetailDAO;
	
	@Autowired
	protected InsuranceProductDAO insuranceProductDAO;

	@Override
	public InsuranceBaseResult save(final InvoiceRequisitionOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getInvoiceRequisitionId();
				if (id <= 0) {
					InvoiceRequisitionDO doObj = new InvoiceRequisitionDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					invoiceRequisitionDAO.insert(doObj);
				} else {
					InvoiceRequisitionDO doObj = invoiceRequisitionDAO.findById(order.getInvoiceRequisitionId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					invoiceRequisitionDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InvoiceRequisitionInfo findById(long id) {
		InvoiceRequisitionDO invoiceRequisitionDo= invoiceRequisitionDAO.findById(id);
        if(invoiceRequisitionDo != null){
            InvoiceRequisitionInfo invoiceRequisitionInfo = new InvoiceRequisitionInfo();
            BeanCopier.staticCopy(invoiceRequisitionDo,invoiceRequisitionInfo);
            return invoiceRequisitionInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InvoiceRequisitionInfo> queryList(InvoiceRequisitionQueryOrder queryOrder) {
		QueryBaseBatchResult<InvoiceRequisitionInfo> batchResult = new QueryBaseBatchResult<InvoiceRequisitionInfo>();
		
		try {
			queryOrder.check();
			List<InvoiceRequisitionInfo> pageList = new ArrayList<InvoiceRequisitionInfo>((int) queryOrder.getPageSize());
			
			InvoiceRequisitionDO invoiceRequisitionDO = new InvoiceRequisitionDO();
			BeanCopier.staticCopy(queryOrder, invoiceRequisitionDO);
			long totalCount = invoiceRequisitionDAO.findByConditionCount(invoiceRequisitionDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InvoiceRequisitionDO> recordList = invoiceRequisitionDAO.findByCondition(invoiceRequisitionDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (InvoiceRequisitionDO item : recordList) {
				InvoiceRequisitionInfo info = new InvoiceRequisitionInfo();
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
	public String getBillInfos(String billNo) {
		StringBuffer sb = new StringBuffer();
		List<String> lists = new ArrayList<String>();
		SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = settlementCompanyBillProcessDAO.findSettlementCompanyBillProcess(billNo);
		if (null != settlementCompanyBillProcessDO) {
			String settlementCompanyIds = settlementCompanyBillProcessDO.getSettlementCompanyIds();
			if (!StringUtils.isBlank(settlementCompanyIds)) {
				List<SettlementCompanyBillDO> findSettlementCompanyBills = settlementCompanyBillDAO.findSettlementCompanyBills(settlementCompanyIds);
				if (ListUtil.isNotEmpty(findSettlementCompanyBills)) {
					for (SettlementCompanyBillDO settlementCompanyBillDO : findSettlementCompanyBills) {
						String insuranceNo = settlementCompanyBillDO.getInsuranceNo();
						if (!lists.contains(insuranceNo)) {
							lists.add(insuranceNo);
						}
					}
				}
			}
		}
		for (int i = 0; i <lists.size(); i++) {
			if ( i<=2) {
				sb.append(lists.get(i)).append(",");
			}
		}
		if (lists.size() > 3) {
			sb.append("等"+lists.size()+"笔");
		} else {
			sb.append("共计"+lists.size()+"笔");
		}
		return sb.toString();
	}

	@Override
	public List<SettlementBillInfo> findSettlementBillInfo(String billNo) {
		List<SettlementBillInfo> settlementBillInfoList = new ArrayList<SettlementBillInfo>();
		SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = settlementCompanyBillProcessDAO.findSettlementCompanyBillProcess(billNo);
		//结算单对应的保单的id
		List<Long> businessBillIds = new ArrayList<Long>();
		if (null != settlementCompanyBillProcessDO ) {
			String settlementCompanyIds = settlementCompanyBillProcessDO.getSettlementCompanyIds();
			if (!StringUtils.isBlank(settlementCompanyIds)) {
				List<SettlementCompanyBillDO> findSettlementCompanyBills = settlementCompanyBillDAO.findSettlementCompanyBills(settlementCompanyIds);
				if (ListUtil.isNotEmpty(findSettlementCompanyBills)) {
					for (SettlementCompanyBillDO settlementCompanyBillDO : findSettlementCompanyBills) {
						long businessBillId = settlementCompanyBillDO.getBusinessBillId();
						if (!businessBillIds.contains(businessBillId)) {
							businessBillIds.add(businessBillId);
						}
					}
				}
			}
		}
		if (!ListUtil.isEmpty(businessBillIds)) {
			for (Long lo : businessBillIds) {
				BusinessBillDO businessBillDO = businessBillDAO.findById(lo);
				SettlementBillInfo info = new SettlementBillInfo();
				info.setBillCustomerName(businessBillDO.getBillCustomerName());
				info.setInsuranceNo(businessBillDO.getInsuranceNo());
				info.setInsuranceDate(DateUtil.format(businessBillDO.getInsuranceDate(), DateUtil.dtSimple));
				info.setPremiumAmount(businessBillDO.getPremiumAmount());
				//经纪费信息
				BrokerageFeeDO brokerageFeeDO = brokerageFeeDAO.findBrokerageFeeByBusinessBillId(lo);
				if (null != brokerageFeeDO) {
					info.setBorkerageRate(brokerageFeeDO.getBrokerageScale());
					info.setBrokerageFee(brokerageFeeDO.getBrokerageAmount());
				}
				//查询保单险种的信息
				//查询保单的产品信息
				InsuranceContactLetterDO insuranceContactLetterDO = insuranceContactLetterDAO.findById(businessBillDO.getLetterId());
				if (insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && insuranceContactLetterDO.getIsQuota().equals("YES")) {
					long productId = insuranceContactLetterDO.getProductId();
					InsuranceProductDO insuranceProductDO = insuranceProductDAO.findById(productId);
					if (null != insuranceProductDO ) {
						info.setCatalogNames(insuranceProductDO.getCatalogName());
					}
				} else {
					List<InsuranceContactLetterDetailDO> insuranceContactLetterDetails = insuranceContactLetterDetailDAO.getInsuranceContactLetterDetails(businessBillDO.getLetterId());
					if (ListUtil.isNotEmpty(insuranceContactLetterDetails)) {
						StringBuffer sb = new StringBuffer();
						for (InsuranceContactLetterDetailDO insuranceContactLetterDetailDO : insuranceContactLetterDetails) {
							long productId = insuranceContactLetterDetailDO.getProductId();
							InsuranceProductDO insuranceProductDO = insuranceProductDAO.findById(productId);
							if (null != insuranceProductDO ) {
								sb.append(insuranceProductDO.getCatalogName()).append(",");
							}
						}
						String catalogNames = sb.toString();
						info.setCatalogNames(catalogNames.substring(0,catalogNames.length()-1));
					}
					settlementBillInfoList.add(info);
				}
			}
		}
		return settlementBillInfoList;
	}
}