package com.born.insurance.biz.service.insuranceBillRenewal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.BrokerageFeeDAO;
import com.born.insurance.dal.daointerface.BrokerageFeeDetailDAO;
import com.born.insurance.dal.daointerface.BusinessBillPayPlanDAO;
import com.born.insurance.dal.daointerface.CommissionInfoDAO;
import com.born.insurance.dal.daointerface.CommissionInfoDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceBillRenewalDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDetailDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillDAO;
import com.born.insurance.dal.dataobject.BrokerageFeeDetailDO;
import com.born.insurance.dal.dataobject.BusinessBillPayPlanDO;
import com.born.insurance.dal.dataobject.CommissionInfoDO;
import com.born.insurance.dal.dataobject.CommissionInfoDetailDO;
import com.born.insurance.dal.dataobject.InsuranceBillRenewalDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDO;
import com.born.insurance.dal.dataobject.PersonCommissionDO;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CommissionTypeEnum;
import com.born.insurance.ws.enums.PaymentStatusEnum;
import com.born.insurance.ws.enums.SettlementCompanyStatusEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.businessBill.BillPayPlanYear;
import com.born.insurance.ws.info.businessBill.BillPayPlanYearInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.info.insuranceBillRenewal.InsuranceBillRenewalInfo;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewal;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalQueryOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalRecord;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.businessBillPayPlan.BusinessBillPayPlanService;
import com.born.insurance.ws.service.insuranceBillRenewal.InsuranceBillRenewalService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.com.alibaba.fastjson.JSON;
import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceBillRenewalService")
public class InsuranceBillRenewalServiceImpl extends BaseAutowiredDomainService implements
																				InsuranceBillRenewalService {
	@Autowired
	protected InsuranceBillRenewalDAO insuranceBillRenewalDAO;
	
	@Autowired
	protected BusinessBillPayPlanDAO businessBillPayPlanDAO;
	
	@Autowired
	protected BrokerageFeeDAO  brokerageFeeDAO;
	
	@Autowired
	protected BrokerageFeeDetailDAO brokerageFeeDetailDAO;
	
	@Autowired
	protected CommissionInfoDAO commissionInfoDAO;
	
	@Autowired
	protected PersonCommissionDAO personCommissionDAO;
	
	@Autowired
	protected PersonCommissionDetailDAO personCommissionDetailDAO;
	
	@Autowired
	protected CommissionInfoDetailDAO commissionInfoDetailDAO;
	
	@Autowired
	protected SettlementCompanyBillDAO settlementCompanyBillDAO;
	
	@Autowired
	protected BusinessBillService businessBillService;
	
	@Autowired
	protected BusinessBillPayPlanService businessBillPayPlanService;
	
	@Autowired
	protected InsuranceContactLetterDAO insuranceContactLetterDAO;
	
	
	@Override
	public InsuranceBaseResult save(final InsuranceBillRenewalOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getBillRenewalId();
				if (id <= 0) {
					InsuranceBillRenewalDO doObj = new InsuranceBillRenewalDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setBusinessBillId(Integer.parseInt(order.getBusinessBillId()));
					doObj.setMoney(order.getMoney());
					doObj.setPaymentNumber(genPayMentNumber());
					doObj.setRawAddTime(now);
					//更改该保单期数的缴费状态
					BusinessBillPayPlanDO businessBillPayPlanDO = businessBillPayPlanDAO.findById(Integer.parseInt(order.getPayPlanId()));
					if (order.getMoney().compareTo(businessBillPayPlanDO.getPremiumAmount()) != 0) {
					    throw ExceptionFactory.newFcsException(InsuranceResultEnum.FORM_CHECK_ERROR,"续期费用与缴费计划中金额不符");
					}
					
					doObj.setPremiumAmount(businessBillPayPlanDO.getPremiumAmount());
					doObj.setPeriod(Integer.parseInt(businessBillPayPlanDO.getAppserialPeriod()));
					insuranceBillRenewalDAO.insert(doObj);
					//更新缴费时间
					businessBillPayPlanDO.setPaymentStatus(Integer.parseInt(PaymentStatusEnum.ALREADYPAID.getCode()));
					businessBillPayPlanDO.setActualPayDate(order.getTradingTime());
					businessBillPayPlanDAO.update(businessBillPayPlanDO);
					//保单
					BusinessBillInfo businessBillInfo = businessBillService.findById(doObj.getBusinessBillId());
					//更新对应期数佣金信息 和 经纪费信息
					BrokerageFeeDetailDO brokerageFeeDetailDO =  brokerageFeeDetailDAO.getBrokerageFee(doObj.getBusinessBillId(),businessBillPayPlanDO.getAppserialPeriod());
					if (null != brokerageFeeDetailDO) {
						brokerageFeeDetailDO.setPlanPayDate(order.getTradingTime());
						brokerageFeeDetailDAO.update(brokerageFeeDetailDO);
						InsuranceBillRenewalDO insuranceBillRenewalDO = insuranceBillRenewalDAO.findInsuranceBillRenewal(doObj.getBusinessBillId(), Integer.parseInt(brokerageFeeDetailDO.getAppserialPeriod()));
						//新增经纪费结算信息
						SettlementCompanyBillDO settlementCompanyBillDO = new SettlementCompanyBillDO();
						settlementCompanyBillDO.setBusinessBillId(doObj.getBusinessBillId());
						settlementCompanyBillDO.setBillNo(insuranceBillRenewalDO.getPaymentNumber());
						settlementCompanyBillDO.setCustomerUserId(businessBillInfo.getBillCustomerId());
						settlementCompanyBillDO.setCustomerUserName(businessBillInfo.getBillCustomerName());
						settlementCompanyBillDO.setInsuranceNo(businessBillInfo.getInsuranceNo());
						settlementCompanyBillDO.setInsuranceCompanyId(businessBillInfo.getInsuranceCompanyId());
						settlementCompanyBillDO.setInsuranceCompanyName(businessBillInfo.getInsuranceCompanyName());
						settlementCompanyBillDO.setStatus(SettlementCompanyStatusEnum.UNTREATED.getCode());
						settlementCompanyBillDO.setBrokerAmount(brokerageFeeDetailDO.getReceivableAmount());
						settlementCompanyBillDO.setRate(brokerageFeeDetailDO.getBorkerageScale());
						settlementCompanyBillDO.setPlanPayDate(order.getTradingTime());
						settlementCompanyBillDO.setRawAddTime(new Date());
						//保费（从投保确认单缴费计划部分的保险费加总获得）
						Money premiumAmount = new Money(0,0);
					   List<BusinessBillPayPlanInfo> payPlanInfos = businessBillPayPlanService.findAllNormalBusinessBillPayPlan(doObj.getBusinessBillId());
						if (ListUtil.isNotEmpty(payPlanInfos)) {
							for (BusinessBillPayPlanInfo payPlanInfo : payPlanInfos) {
								premiumAmount = premiumAmount.add(payPlanInfo.getPremiumAmount());
							}
						}
						settlementCompanyBillDO.setPremiumAmount(premiumAmount);
						settlementCompanyBillDAO.insert(settlementCompanyBillDO);
					}
					
					CommissionInfoDO commissionInfoDO = commissionInfoDAO.findCommissionInfoByBusinessBillId(doObj.getBusinessBillId());
					CommissionInfoDetailDO commissionInfoDetailDO = commissionInfoDetailDAO.getCommissionInfo(doObj.getBusinessBillId(),businessBillPayPlanDO.getAppserialPeriod());
					if (null != commissionInfoDetailDO) {
						commissionInfoDetailDO.setPlanPayDate(order.getTradingTime());
					    commissionInfoDetailDAO.update(commissionInfoDetailDO);
					    //更新业务员佣金信息
					    PersonCommissionDO businessUserCommissionDo = personCommissionDAO.findByUserId(commissionInfoDO.getBusinessUserId());
						PersonCommissionDO personCommissionDO = new PersonCommissionDO();
						if (null == businessUserCommissionDo) {
							//新增佣金结算信息
							personCommissionDO.setBusinessUserId(commissionInfoDO.getBusinessUserId());
							personCommissionDO.setBusinessUserName(commissionInfoDO.getBusinessUserName());
							personCommissionDO.setApplyingAmount(new Money(0,0));
							personCommissionDO.setRawAddTime(new Date());
							personCommissionDO.setTotalAmount(commissionInfoDetailDO.getReceivableAmount());
							personCommissionDO.setDrawAmount(new Money(0,0));
							personCommissionDAO.insert(personCommissionDO);
						} else {
							businessUserCommissionDo.setTotalAmount(businessUserCommissionDo.getTotalAmount().add(commissionInfoDetailDO.getReceivableAmount()));
							personCommissionDAO.update(businessUserCommissionDo);
						}
						//新增业务员佣金明细
						PersonCommissionDetailDO  personCommissionDetailDO = new PersonCommissionDetailDO();
						personCommissionDetailDO.setCommissionTime(commissionInfoDetailDO.getPlanPayDate());
						personCommissionDetailDO.setBalance(null == businessUserCommissionDo ? commissionInfoDetailDO.getReceivableAmount() :businessUserCommissionDo.getTotalAmount().subtract(businessUserCommissionDo.getDrawAmount()));
						personCommissionDetailDO.setBusinessUserId(commissionInfoDO.getBusinessUserId());
						personCommissionDetailDO.setBusinessUserName(commissionInfoDO.getBusinessUserName());
						personCommissionDetailDO.setCommissionType(CommissionTypeEnum.INCOME.getCode());
						personCommissionDetailDO.setCommissionAmount(commissionInfoDetailDO.getReceivableAmount());
						personCommissionDetailDO.setSerialNumber("");
						personCommissionDetailDO.setRemark("保单号："+businessBillInfo.getInsuranceNo()+"-"+"费用类型"+commissionInfoDO.getCommissionCatalog());
						personCommissionDetailDO.setRawAddTime(new Date());
						personCommissionDetailDAO.insert(personCommissionDetailDO);
					}
				} else {
					InsuranceBillRenewalDO doObj = insuranceBillRenewalDAO.findById(order
						.getBillRenewalId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceBillRenewalDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	
	/**
	 * 保单缴费编号
	 * @return
	 */
	private synchronized String genPayMentNumber() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String billNo = "PAYMENTBILLNO";
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
		String seqName = SysDateSeqNameEnum.PAYMENT_BILL_NUMBER.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		str += com.born.insurance.util.StringUtil.leftPad(String.valueOf(seq), 4, "0");
		
		return str;
	}
	
	@Override
	public InsuranceBillRenewalInfo findById(long id) {
		InsuranceBillRenewalDO insuranceBillRenewalDo = insuranceBillRenewalDAO.findById(id);
		if (insuranceBillRenewalDo != null) {
			InsuranceBillRenewalInfo insuranceBillRenewalInfo = new InsuranceBillRenewalInfo();
			BeanCopier.staticCopy(insuranceBillRenewalDo, insuranceBillRenewalInfo);
			return insuranceBillRenewalInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceBillRenewalInfo> queryList(InsuranceBillRenewalQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceBillRenewalInfo> batchResult = new QueryBaseBatchResult<InsuranceBillRenewalInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceBillRenewalInfo> pageList = new ArrayList<InsuranceBillRenewalInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceBillRenewalDO insuranceBillRenewalDO = new InsuranceBillRenewalDO();
			BeanCopier.staticCopy(queryOrder, insuranceBillRenewalDO);
			long totalCount = insuranceBillRenewalDAO.findByConditionCount(insuranceBillRenewalDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceBillRenewalDO> recordList = insuranceBillRenewalDAO.findByCondition(insuranceBillRenewalDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (InsuranceBillRenewalDO item : recordList) {
				InsuranceBillRenewalInfo info = new InsuranceBillRenewalInfo();
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
	public InsuranceBillRenewalInfo findInsuranceBillRenewal(long businessBillId, long period) {
		InsuranceBillRenewalInfo  info = new InsuranceBillRenewalInfo();
		InsuranceBillRenewalDO insuranceBillRenewalDO = insuranceBillRenewalDAO.findInsuranceBillRenewal(businessBillId,period);
		if (null != insuranceBillRenewalDO) {
			BeanCopier.staticCopy(insuranceBillRenewalDO, info);
			return info;
		}
		return null;
	}


	@Override
	public List<InsuranceBillRenewal> findInsuranceBillPayPlan(long businessBillId) {
		
		List<InsuranceBillRenewal> pageList = new ArrayList<InsuranceBillRenewal>();
		List<BusinessBillPayPlanDO> list = null;
		Map<String, List<BusinessBillPayPlanDO>> maps = new LinkedHashMap<String, List<BusinessBillPayPlanDO>>();
		try {
			list = businessBillPayPlanDAO.findAllBusinessBillPayPlan(businessBillId);
			if (ListUtil.isNotEmpty(list)) {
				for (BusinessBillPayPlanDO businessBillPayPlanDO : list) {
					if (!maps.containsKey(businessBillPayPlanDO.getYear())) {
						 List<BusinessBillPayPlanDO> arrayList = new ArrayList<BusinessBillPayPlanDO>();
						 arrayList.add(businessBillPayPlanDO);
						maps.put(businessBillPayPlanDO.getYear(), arrayList);
					} else {
						List<BusinessBillPayPlanDO> list2 = maps.get(businessBillPayPlanDO.getYear());
						list2.add(businessBillPayPlanDO);
					}
				}
			}
			if (!maps.isEmpty()) {
				for (String s : maps.keySet()) {
					InsuranceBillRenewal insuranceBillRenewal = new InsuranceBillRenewal();
					List<InsuranceBillRenewalRecord> info = new ArrayList<InsuranceBillRenewalRecord>();
					insuranceBillRenewal.setYear(s);
					List<BusinessBillPayPlanDO> list2 = maps.get(s);
					for (BusinessBillPayPlanDO businessBillPayPlanDO : list2) {
						InsuranceBillRenewalRecord insuranceBillRenewalRecord = new InsuranceBillRenewalRecord();
						insuranceBillRenewalRecord.setActualPayDate(businessBillPayPlanDO.getActualPayDate());
						insuranceBillRenewalRecord.setPeriod(businessBillPayPlanDO.getAppserialPeriod());
						insuranceBillRenewalRecord.setPlanPayDate(businessBillPayPlanDO.getPlanPayDate());
						insuranceBillRenewalRecord.setPayPlanId(businessBillPayPlanDO.getPayPlanId());
						insuranceBillRenewalRecord.setPaymentStatus(businessBillPayPlanDO.getPaymentStatus());
						insuranceBillRenewalRecord.setBusinessBillId(businessBillPayPlanDO.getBusinessBillId());
						insuranceBillRenewalRecord.setRowAddTime(businessBillPayPlanDO.getDataAddTime());
						insuranceBillRenewalRecord.setPremiumAmount(businessBillPayPlanDO.getPremiumAmount());
						InsuranceBillRenewalDO insuranceBillRenewalDO = insuranceBillRenewalDAO.findInsuranceBillRenewal(businessBillId, Long.valueOf(businessBillPayPlanDO.getAppserialPeriod()));
						if (null != insuranceBillRenewalDO) {
							insuranceBillRenewalRecord.setBillNo(insuranceBillRenewalDO.getBillNo());
							insuranceBillRenewalRecord.setHaveBillRenevalRecord(true);
						}
						info.add(insuranceBillRenewalRecord);
					}
					insuranceBillRenewal.setList(info);
					insuranceBillRenewal.setLength(info.size());
					pageList.add(insuranceBillRenewal);
				}
			}
		} catch (Exception e) {
			logger.error("缴费计划查询:", e.getMessage());
		}
		return pageList;
	}


	@Override
	public InsuranceBaseResult insuranceBillRenewal(final BusinessBillOrder order) {
		return commonProcess(order, "保单续期", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				long businessBillId = order.getBusinessBillId();
				BusinessBillInfo doObj = businessBillService.findById(businessBillId);
				InsuranceContactLetterDO insuranceContactLetterDO = insuranceContactLetterDAO.findById(doObj.getLetterId());
				List<BusinessBillPayPlanOrder> businessBillPayPlanOrders = order.getBusinessBillPayPlanOrders();
				if (ListUtil.isNotEmpty(businessBillPayPlanOrders)) {
					for (BusinessBillPayPlanOrder businessBillPayPlanOrder : businessBillPayPlanOrders) {
						if (businessBillPayPlanOrder.getPaymentStatus().equals(PaymentStatusEnum.UNPAY.getCode())) {
							
							if (!StringUtils.isBlank(businessBillPayPlanOrder.getActualPayDate())) {
								//更改状态为已缴费 更新缴费计划当前期数的实收日期
								long payPlanId = businessBillPayPlanOrder.getPayPlanId();
								BusinessBillPayPlanDO billPayPlanDO = businessBillPayPlanDAO.findById(payPlanId);
								billPayPlanDO.setPaymentStatus(Integer.parseInt(PaymentStatusEnum.ALREADYPAID.getCode()));
								billPayPlanDO.setActualPayDate(DateUtil.parse(businessBillPayPlanOrder.getActualPayDate()));
								billPayPlanDO.setDataAddTime(now);
								businessBillPayPlanDAO.update(billPayPlanDO);
								
								
								//新增缴费信息
								String insuranceBillRenewal = businessBillPayPlanOrder.getInsuranceBillRenewal();
								InsuranceBillRenewalDO InsuranceBillRenewalDO = new InsuranceBillRenewalDO();
								if (!StringUtils.isBlank(insuranceBillRenewal)) {
									//新增缴费信息
									Map map = (Map)JSON.parse(insuranceBillRenewal);
									MiscUtil.setInfoPropertyByMap(map,InsuranceBillRenewalDO);
									InsuranceBillRenewalDO.setMoney(new Money((String)map.get("money")));
									InsuranceBillRenewalDO.setTradingTime(DateUtil.parse((String)map.get("tradingTime")));
									InsuranceBillRenewalDO.setPaymentNumber(genPayMentNumber());
									InsuranceBillRenewalDO.setBusinessBillId(businessBillPayPlanOrder.getBusinessBillId());
									InsuranceBillRenewalDO.setPremiumAmount(businessBillPayPlanOrder.getPremiumAmount());
									InsuranceBillRenewalDO.setPeriod(Integer.parseInt(businessBillPayPlanOrder.getAppserialPeriod()));
									InsuranceBillRenewalDO.setBillNo(doObj.getBillNo());
									insuranceBillRenewalDAO.insert(InsuranceBillRenewalDO);
								}	
								
								if (businessBillPayPlanOrder.getSerial() == 1 || (businessBillPayPlanOrder.getCurrentYear().equals("1") && !StringUtils.isBlank(insuranceContactLetterDO.getIsQuota()) && insuranceContactLetterDO.getIsQuota().equals("NO"))) {
									//更新对应期数佣金信息 和 经纪费信息
									BrokerageFeeDetailDO brokerageFeeDetailDO =  brokerageFeeDetailDAO.getBrokerageFee(doObj.getBusinessBillId(),businessBillPayPlanOrder.getAppserialPeriod());
									if (null != brokerageFeeDetailDO) {
										brokerageFeeDetailDO.setPlanPayDate(DateUtil.parse(businessBillPayPlanOrder.getActualPayDate()));
										brokerageFeeDetailDAO.update(brokerageFeeDetailDO);
										//更新保单的经纪费信息
										//新增经纪费结算信息
										SettlementCompanyBillDO settlementCompanyBillDO = new SettlementCompanyBillDO();
										settlementCompanyBillDO.setBusinessBillId(doObj.getBusinessBillId());
										settlementCompanyBillDO.setBillNo(InsuranceBillRenewalDO.getPaymentNumber());
										settlementCompanyBillDO.setInsuranceNo(doObj.getInsuranceNo());
										settlementCompanyBillDO.setCustomerUserId(doObj.getBillCustomerId());
										settlementCompanyBillDO.setCustomerUserName(doObj.getBillCustomerName());
										settlementCompanyBillDO.setInsuranceCompanyId(doObj.getInsuranceCompanyId());
										
										settlementCompanyBillDO.setInsuranceCompanyName(doObj.getInsuranceCompanyName());
										settlementCompanyBillDO.setStatus(SettlementCompanyStatusEnum.UNTREATED.getCode());
										settlementCompanyBillDO.setBrokerAmount(brokerageFeeDetailDO.getReceivableAmount());
										settlementCompanyBillDO.setRate(brokerageFeeDetailDO.getBorkerageScale());
										settlementCompanyBillDO.setPlanPayDate(DateUtil.parse(businessBillPayPlanOrder.getActualPayDate()));
										settlementCompanyBillDO.setRawAddTime(now);
										//保费（从投保确认单缴费计划部分的保险费加总获得）
										Money premiumAmount = new Money(0,0);
										List<BusinessBillPayPlanOrder> payPlanOrders = order.getBusinessBillPayPlanOrders();
										if (ListUtil.isNotEmpty(payPlanOrders)) {
											for (BusinessBillPayPlanOrder payPlanOrder : businessBillPayPlanOrders) {
												premiumAmount = premiumAmount.add(payPlanOrder.getPremiumAmount());
											}
										}
										settlementCompanyBillDO.setPremiumAmount(premiumAmount);
										settlementCompanyBillDAO.insert(settlementCompanyBillDO);
									}
									
									
									CommissionInfoDO commissionInfoDO = commissionInfoDAO.findCommissionInfoByBusinessBillId(doObj.getBusinessBillId());
									CommissionInfoDetailDO commissionInfoDetailDO = commissionInfoDetailDAO.getCommissionInfo(doObj.getBusinessBillId(),businessBillPayPlanOrder.getAppserialPeriod());
									if (null != commissionInfoDetailDO) {
										commissionInfoDetailDO.setPlanPayDate(DateUtil.parse(businessBillPayPlanOrder.getActualPayDate()));
										commissionInfoDetailDAO.update(commissionInfoDetailDO);
										//更新业务员佣金信息
										PersonCommissionDO businessUserCommissionDo = personCommissionDAO.findByUserId(doObj.getBusinessUserId());
										PersonCommissionDO personCommissionDO = new PersonCommissionDO();
										if (null == businessUserCommissionDo) {
											//新增佣金结算信息
											personCommissionDO.setBusinessUserId(doObj.getBusinessUserId());
											personCommissionDO.setBusinessUserName(doObj.getBusinessUserName());
											personCommissionDO.setApplyingAmount(new Money(0,0));
											personCommissionDO.setRawAddTime(now);
											personCommissionDO.setTotalAmount(commissionInfoDetailDO.getReceivableAmount());
											personCommissionDO.setBusinessUserCertNo(order.getBusinessUserCertNo());
											personCommissionDO.setBusinessUserMobile(order.getBusinessUserMobile());
											personCommissionDO.setBusinessUserType(order.getBusinessUserType());
											personCommissionDO.setDrawAmount(new Money(0,0));
											personCommissionDAO.insert(personCommissionDO);
										} else {
											businessUserCommissionDo.setTotalAmount(businessUserCommissionDo.getTotalAmount().add(commissionInfoDetailDO.getReceivableAmount()));
											personCommissionDAO.update(businessUserCommissionDo);
										}
										//新增业务员佣金明细
										PersonCommissionDetailDO  personCommissionDetailDO = new PersonCommissionDetailDO();
										personCommissionDetailDO.setCommissionTime(commissionInfoDetailDO.getPlanPayDate());
										personCommissionDetailDO.setBalance(null == businessUserCommissionDo ? commissionInfoDetailDO.getReceivableAmount() :businessUserCommissionDo.getTotalAmount().subtract(businessUserCommissionDo.getDrawAmount()));
										personCommissionDetailDO.setBusinessUserId(doObj.getBusinessUserId());
										personCommissionDetailDO.setBusinessUserName(doObj.getBusinessUserName());
										personCommissionDetailDO.setBusinessUserType(null == businessUserCommissionDo ? personCommissionDO.getBusinessUserType() : businessUserCommissionDo.getBusinessUserType());
										personCommissionDetailDO.setCommissionType(CommissionTypeEnum.INCOME.getCode());
										personCommissionDetailDO.setCommissionAmount(commissionInfoDetailDO.getReceivableAmount());
										personCommissionDetailDO.setSerialNumber("");
										personCommissionDetailDO.setRemark("保单号："+doObj.getInsuranceNo()+"-"+"费用类型"+commissionInfoDO.getCommissionCatalog());
										personCommissionDetailDO.setRawAddTime(now);
										personCommissionDetailDAO.insert(personCommissionDetailDO);
									}
								}
							}
						} else { 
							InsuranceBillRenewalDO  billRenewal = insuranceBillRenewalDAO.findInsuranceBillRenewal(doObj.getBusinessBillId(), Long.valueOf(businessBillPayPlanOrder.getAppserialPeriod()));
							if (null == billRenewal) {
								//新增缴费信息
								String insuranceBillRenewal = businessBillPayPlanOrder.getInsuranceBillRenewal();
								if (!StringUtils.isBlank(insuranceBillRenewal)) {
									//新增缴费信息
									Map map = (Map)JSON.parse(insuranceBillRenewal);
									InsuranceBillRenewalDO InsuranceBillRenewalDO = new InsuranceBillRenewalDO();
									MiscUtil.setInfoPropertyByMap(map,InsuranceBillRenewalDO);
									InsuranceBillRenewalDO.setMoney(new Money((String)map.get("money")));
									InsuranceBillRenewalDO.setTradingTime(DateUtil.parse((String)map.get("tradingTime")));
									InsuranceBillRenewalDO.setPaymentNumber(genPayMentNumber());
									InsuranceBillRenewalDO.setBusinessBillId(doObj.getBusinessBillId());
									InsuranceBillRenewalDO.setPremiumAmount(businessBillPayPlanOrder.getPremiumAmount());
									InsuranceBillRenewalDO.setPeriod(Integer.parseInt(businessBillPayPlanOrder.getAppserialPeriod()));
									InsuranceBillRenewalDO.setBillNo(doObj.getBillNo());
									insuranceBillRenewalDAO.insert(InsuranceBillRenewalDO);
									//更新经纪费结算中的保单续交编号
									SettlementCompanyBillDO settlementCompanyBillDO = settlementCompanyBillDAO.findSettlementCompanyBill(doObj.getBusinessBillId(),Integer.parseInt(businessBillPayPlanOrder.getAppserialPeriod()));
									settlementCompanyBillDO.setBillNo(InsuranceBillRenewalDO.getPaymentNumber());
									settlementCompanyBillDAO.update(settlementCompanyBillDO);
								}	
							}
						}
					}
				}
		
				return null;
			}
		}, null, null);
	}
}