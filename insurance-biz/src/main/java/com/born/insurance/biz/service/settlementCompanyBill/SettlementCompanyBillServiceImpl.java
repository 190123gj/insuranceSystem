package com.born.insurance.biz.service.settlementCompanyBill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BusinessBillDO;
import com.born.insurance.dal.dataobject.CustomerCompanyDetailDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDetailDO;
import com.born.insurance.dal.dataobject.InsuranceProductDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillProcessDO;
import com.born.insurance.dal.daointerface.BrokerageFeeDAO;
import com.born.insurance.dal.daointerface.BusinessBillDAO;
import com.born.insurance.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceProductDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillProcessDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.SettlementCompanyStatusEnum;
import com.born.insurance.ws.enums.SettlementProcessEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.settlementCompanyBill.SettlementCompanyBillInfo;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductOrder;
import com.born.insurance.ws.order.settlementCompanyBill.SettlementCompanyBillOrder;
import com.born.insurance.ws.order.settlementCompanyBill.SettlementCompanyBillQueryOrder;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.settlementCompanyBill.SettlementCompanyBillService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.util.PinyinUtil;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("settlementCompanyBillService")
public class SettlementCompanyBillServiceImpl extends BaseAutowiredDomainService implements
																				SettlementCompanyBillService {
	@Autowired
	protected SettlementCompanyBillDAO settlementCompanyBillDAO;
	
	@Autowired
	protected CustomerCompanyDetailDAO customerCompanyDetailDAO;
	
	@Autowired
	protected SettlementCompanyBillProcessDAO settlementCompanyBillProcessDAO;
	
	@Autowired
	protected BusinessBillDAO businessBillDAO;
	
	@Autowired
	protected InsuranceContactLetterDAO insuranceContactLetterDAO;
	
	@Autowired
	protected InsuranceContactLetterDetailDAO insuranceContactLetterDetailDAO;
	
	@Autowired
	protected InsuranceProductDAO insuranceProductDAO;
	
	@Override
	public InsuranceBaseResult save(final SettlementCompanyBillOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getSettlementCompanyId();
				if (id <= 0) {
					SettlementCompanyBillDO doObj = new SettlementCompanyBillDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					settlementCompanyBillDAO.insert(doObj);
				} else {
					SettlementCompanyBillDO doObj = settlementCompanyBillDAO.findById(order
						.getSettlementCompanyId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					settlementCompanyBillDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public SettlementCompanyBillInfo findById(long id) {
		SettlementCompanyBillDO settlementCompanyBillDo = settlementCompanyBillDAO.findById(id);
		if (settlementCompanyBillDo != null) {
			SettlementCompanyBillInfo settlementCompanyBillInfo = new SettlementCompanyBillInfo();
			BeanCopier.staticCopy(settlementCompanyBillDo, settlementCompanyBillInfo);
			return settlementCompanyBillInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<SettlementCompanyBillInfo> queryList(	SettlementCompanyBillQueryOrder queryOrder) {
		QueryBaseBatchResult<SettlementCompanyBillInfo> batchResult = new QueryBaseBatchResult<SettlementCompanyBillInfo>();
		
		try {
			queryOrder.check();
			List<SettlementCompanyBillInfo> pageList = new ArrayList<SettlementCompanyBillInfo>(
				(int) queryOrder.getPageSize());
			
			SettlementCompanyBillDO settlementCompanyBillDO = new SettlementCompanyBillDO();
			BeanCopier.staticCopy(queryOrder, settlementCompanyBillDO);
			long totalCount = settlementCompanyBillDAO
				.findByConditionCount(settlementCompanyBillDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<SettlementCompanyBillDO> recordList = settlementCompanyBillDAO.findByCondition(
				settlementCompanyBillDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (SettlementCompanyBillDO item : recordList) {
				SettlementCompanyBillInfo info = new SettlementCompanyBillInfo();
				BeanCopier.staticCopy(item, info);
				long businessBillId = item.getBusinessBillId();
				BusinessBillDO businessBillDO = businessBillDAO.findById(businessBillId);
				if (null == businessBillDO) continue;
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
				}
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
	public void dealSettlementCompanyBill(SettlementCompanyBillProcessQueryOrder queryOrder) {
		String settlementCompanyIds = queryOrder.getSettlementCompanyId();
		try {
			settlementCompanyBillDAO.updateSettlementCompanyBill(SettlementCompanyStatusEnum.PROCESSING.getCode(),queryOrder.getSettlementCompanyId());
			String[] str = settlementCompanyIds.split(",");
			long total = str.length;
			SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = new SettlementCompanyBillProcessDO();
			settlementCompanyBillProcessDO.setBillNo(getBillNo());
			settlementCompanyBillProcessDO.setInsuranceCompanyId(StringUtils.isBlank(queryOrder.getInsuranceCompanyId()) ? 0 : Long.valueOf(queryOrder.getInsuranceCompanyId()));
			CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO.findById(queryOrder.getInsuranceCompanyId());
			if (null != customerCompanyDetailDO) {
				settlementCompanyBillProcessDO.setInsuranceCompanyName(customerCompanyDetailDO.getCustomerName());
			}
			settlementCompanyBillProcessDO.setSettlementCompanyIds(queryOrder.getSettlementCompanyId());
			settlementCompanyBillProcessDO.setRawAddTime(new Date());
			settlementCompanyBillProcessDO.setTotal(total);
			settlementCompanyBillProcessDO.setStatus(SettlementProcessEnum.PENDINGINVOICE.getCode());
			settlementCompanyBillProcessDO.setTotalAmount(queryOrder.getBrokerAmount());
			settlementCompanyBillProcessDAO.insert(settlementCompanyBillProcessDO);
		} catch (Exception e) {
			logger.error("经纪费待结算记录状态更新失败:", e.getMessage());
		}
	}
	
	
	/**
	 * 业务单号
	 * @return
	 */
	private synchronized String getBillNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String billNo = "JJFJS";
		Pattern pattern = Pattern.compile("^[\\d]$");
		Matcher match = pattern.matcher(String.valueOf(month));
		Matcher match2 = pattern.matcher(String.valueOf(day));
		if (match.find()) {
			m = "0"+month;
		}
		if (match2.find()) {
			d = "0"+day;
		}
		String str= billNo+year+ m + d;
		String seqName = SysDateSeqNameEnum.INSURANCE_CONTACT_LETTER.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		str += StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return str;
	}

	@Override
	public List<SettlementCompanyBillInfo> findSettlementCompanyBills(String settlementCompanyIds) {
		List<SettlementCompanyBillInfo>  pageList = new ArrayList<SettlementCompanyBillInfo>();
		List<SettlementCompanyBillDO>  settlementCompanyBills = settlementCompanyBillDAO.findSettlementCompanyBills(settlementCompanyIds);
		for (SettlementCompanyBillDO item : settlementCompanyBills) {
			SettlementCompanyBillInfo info = new SettlementCompanyBillInfo();
			BeanCopier.staticCopy(item, info);
			pageList.add(info);
		}
		return pageList;
	}
}