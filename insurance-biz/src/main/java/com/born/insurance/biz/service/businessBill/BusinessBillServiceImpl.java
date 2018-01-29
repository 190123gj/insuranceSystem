package com.born.insurance.biz.service.businessBill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.biz.service.insuranceProtocolCharge.InsuranceProtocolChargeServiceImpl;
import com.born.insurance.dal.daointerface.BrokerageFeeDAO;
import com.born.insurance.dal.daointerface.BrokerageFeeDetailDAO;
import com.born.insurance.dal.daointerface.BusinessBillBeneficiaryDAO;
import com.born.insurance.dal.daointerface.BusinessBillCoinsInfoDAO;
import com.born.insurance.dal.daointerface.BusinessBillCustomerDAO;
import com.born.insurance.dal.daointerface.BusinessBillDAO;
import com.born.insurance.dal.daointerface.BusinessBillPayPlanDAO;
import com.born.insurance.dal.daointerface.BusinessBillReinsInfoDAO;
import com.born.insurance.dal.daointerface.BusinessBillUnderInforDAO;
import com.born.insurance.dal.daointerface.CommissionInfoDAO;
import com.born.insurance.dal.daointerface.CommissionInfoDetailDAO;
import com.born.insurance.dal.daointerface.CustomerBaseInfoDAO;
import com.born.insurance.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceBillRenewalDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceProtocolChargeDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDetailDAO;
import com.born.insurance.dal.daointerface.SettlementCompanyBillDAO;
import com.born.insurance.dal.dataobject.BrokerageFeeDO;
import com.born.insurance.dal.dataobject.BrokerageFeeDetailDO;
import com.born.insurance.dal.dataobject.BusinessBillBeneficiaryDO;
import com.born.insurance.dal.dataobject.BusinessBillCoinsInfoDO;
import com.born.insurance.dal.dataobject.BusinessBillCustomerDO;
import com.born.insurance.dal.dataobject.BusinessBillDO;
import com.born.insurance.dal.dataobject.BusinessBillPayPlanDO;
import com.born.insurance.dal.dataobject.BusinessBillReinsInfoDO;
import com.born.insurance.dal.dataobject.BusinessBillUnderInforDO;
import com.born.insurance.dal.dataobject.CommissionInfoDO;
import com.born.insurance.dal.dataobject.CommissionInfoDetailDO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.InsuranceBillRenewalDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDetailDO;
import com.born.insurance.dal.dataobject.InsuranceProtocolChargeDO;
import com.born.insurance.dal.dataobject.PersonCommissionDO;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.BusinessBillFormDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.integration.bpm.user.SysUser;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BillSettlementStatusEnum;
import com.born.insurance.ws.enums.CommissionTypeEnum;
import com.born.insurance.ws.enums.ContactLetterTypeEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.InsuranceOfTypeEnum;
import com.born.insurance.ws.enums.InsuranceProtocolTypeEnum;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.PaymentStatusEnum;
import com.born.insurance.ws.enums.SettlementCompanyStatusEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.info.businessBill.BillPayPlanYear;
import com.born.insurance.ws.info.businessBill.BillPayPlanYearInfo;
import com.born.insurance.ws.info.businessBill.BrokeFeeAndCommunityInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillFormInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.businessBill.CalcBrokeAndCommissionInfo;
import com.born.insurance.ws.info.businessBill.ShowMsgInfo;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeOrder;
import com.born.insurance.ws.order.brokerageFeeDetail.BrokerageFeeDetailOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillQueryOrder;
import com.born.insurance.ws.order.businessBillBeneficiary.BusinessBillBeneficiaryOrder;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanOrder;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoOrder;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforOrder;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoOrder;
import com.born.insurance.ws.order.commissionInfoDetail.CommissionInfoDetailOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
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
@Service("businessBillService")
public class BusinessBillServiceImpl extends BaseFormAutowiredDomainService implements
																		BusinessBillService {
	@Autowired
	protected BusinessBillDAO businessBillDAO;
	
	@Autowired
	protected BusinessBillCustomerDAO businessBillCustomerDAO;
	
	@Autowired
	protected BusinessBillPayPlanDAO businessBillPayPlanDAO;
	
	@Autowired
	protected InsuranceBillRenewalDAO insuranceBillRenewalDAO;
	
	@Autowired
	protected BusinessBillUnderInforDAO  BusinessBillUnderInforDAO;
	
	@Autowired
	protected BusinessBillBeneficiaryDAO businessBillBeneficiaryDAO;
	
	@Autowired
	protected BusinessBillCoinsInfoDAO businessBillCoinsInfoDAO;
	
	@Autowired
	protected BusinessBillReinsInfoDAO businessBillReinsInfoDAO;
	
	@Autowired
	protected InsuranceContactLetterDAO  insuranceContactLetterDAO;
	
	@Autowired
	protected InsuranceProductService insuranceProductService;
	
	@Autowired
	protected InsuranceContactLetterDetailDAO insuranceContactLetterDetailDAO;
	
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
	protected CustomerCompanyDetailDAO customerCompanyDetailDAO;
	
	@Autowired
	protected CustomerBaseInfoDAO customerBaseInfoDAO;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected InsuranceCatalogService insuranceCatalogService;
	
	@Autowired
	protected ExtraDAO  extraDAO;
	
	@Autowired
	protected InsuranceProtocolChargeDAO insuranceProtocolChargeDAO;
	
	
	@Override
	public InsuranceBaseResult save(final BusinessBillOrder order) {
		return commonProcess(order, "保单增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getBusinessBillId();
				if (id <= 0) {
					BusinessBillDO doObj = new BusinessBillDO();
					BusinessBillCustomerDO customerUserDO  = new BusinessBillCustomerDO();
					BusinessBillCustomerDO customerPersonDO  = new BusinessBillCustomerDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					if (order.getAgencyCompanyId() > 0) {
						CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(String.valueOf(order.getAgencyCompanyId()));
						if (null == customerBaseInfoDO) {
							customerBaseInfoDO = customerBaseInfoDAO.findById(order.getAgencyCompanyId());
						} 
						doObj.setAgencyCompanyName(customerBaseInfoDO.getCustomerName());
					}
					doObj.setRawAddTime(now);
					doObj.setInsuranceOfType(InsuranceOfTypeEnum.ONLINE.getCode());
					doObj.setBillStatus("0");
					businessBillDAO.insert(doObj);
					id = doObj.getBusinessBillId();
					//更改投保申请为完成承保状态
					InsuranceContactLetterDO insuranceContactLetterDO = insuranceContactLetterDAO.findById(order.getLetterId());
					insuranceContactLetterDO.setStatus("YES");
					insuranceContactLetterDAO.update(insuranceContactLetterDO);
					
					//保险人与被保险人之前的关系
					if (ListUtil.isNotEmpty(order.getBusinessBillCustomerOrder())) {
						for (BusinessBillCustomerOrder businessBillCustomerOrder : order.getBusinessBillCustomerOrder()) {
							BusinessBillCustomerDO businessBillCustomerDO = new BusinessBillCustomerDO();
							BeanCopier.staticCopy(businessBillCustomerOrder, businessBillCustomerDO);
							businessBillCustomerDO.setBusinessBillId(doObj.getBusinessBillId());
							businessBillCustomerDAO.insert(businessBillCustomerDO);
						}
					}
					
					//受益方案
					if (ListUtil.isNotEmpty(order.getBusinessBillBeneficiaryOrder()) ) {
						for (BusinessBillBeneficiaryOrder businessBillBeneficiaryOrder : order.getBusinessBillBeneficiaryOrder()) {
							BusinessBillBeneficiaryDO  businessBillBeneficiaryDO = new BusinessBillBeneficiaryDO();
							BeanCopier.staticCopy(businessBillBeneficiaryOrder, businessBillBeneficiaryDO);
							businessBillBeneficiaryDO.setLetterId(doObj.getLetterId());
							businessBillBeneficiaryDAO.insert(businessBillBeneficiaryDO);
						}
					}
					
					//承保信息
					if (ListUtil.isNotEmpty(order.getBusinessBillUnderInforOrders())) {
						for(BusinessBillUnderInforOrder businessBillUnderInforOrder:order.getBusinessBillUnderInforOrders()){
							BusinessBillUnderInforDO businessBillUnderInforDO = new BusinessBillUnderInforDO();
							BeanCopier.staticCopy(businessBillUnderInforOrder, businessBillUnderInforDO);
							businessBillUnderInforDO.setBusinessBillId(doObj.getBusinessBillId());
							BusinessBillUnderInforDAO.insert(businessBillUnderInforDO);
						}
					}
					//缴费计划
					if (ListUtil.isNotEmpty(order.getBusinessBillPayPlanOrders())) {
						for(BusinessBillPayPlanOrder businessBillPayPlanOrder: order.getBusinessBillPayPlanOrders()){
							BusinessBillPayPlanDO  businessBillPayPlanDO = new BusinessBillPayPlanDO();
							BeanCopier.staticCopy(businessBillPayPlanOrder, businessBillPayPlanDO);
							businessBillPayPlanDO.setPlanPayDate(DateUtil.parse(businessBillPayPlanOrder.getPlanPayDate()));
							businessBillPayPlanDO.setActualPayDate(DateUtil.parse(businessBillPayPlanOrder.getActualPayDate()));
							businessBillPayPlanDO.setBusinessBillId(doObj.getBusinessBillId());
							if (null != businessBillPayPlanDO.getActualPayDate()) {
								businessBillPayPlanDO.setDataAddTime(now);
							}
							businessBillPayPlanDO.setRawAddTime(now);
							businessBillPayPlanDAO.insert(businessBillPayPlanDO);
							
							if (!StringUtils.isBlank(businessBillPayPlanOrder.getActualPayDate())) {
								//更改状态为已缴费
								long payPlanId = businessBillPayPlanDO.getPayPlanId();
								BusinessBillPayPlanDO billPayPlanDO = businessBillPayPlanDAO.findById(payPlanId);
								billPayPlanDO.setPaymentStatus(Integer.parseInt(PaymentStatusEnum.ALREADYPAID.getCode()));
								businessBillPayPlanDAO.update(billPayPlanDO);
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
									InsuranceBillRenewalDO.setPremiumAmount(businessBillPayPlanDO.getPremiumAmount());
									InsuranceBillRenewalDO.setPeriod(Integer.parseInt(businessBillPayPlanOrder.getAppserialPeriod()));
									InsuranceBillRenewalDO.setBillNo(doObj.getBillNo());
									insuranceBillRenewalDAO.insert(InsuranceBillRenewalDO);
								}
							}
						}
					}
					
					//如果是寿险，查询投保人的相关信息
					if (insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode())) {
						customerUserDO = businessBillCustomerDAO.findBillCustomerInfo(doObj.getBusinessBillId(),ContactLetterTypeEnum.POLICYHOLDER.getCode());
						customerPersonDO=businessBillCustomerDAO.findBillCustomerInfo(doObj.getBusinessBillId(),ContactLetterTypeEnum.INSUREDPERSON.getCode());
					}
					
					//新增经纪费 
					if (ListUtil.isNotEmpty(order.getBrokerageFeeDetailOrder())) {
						List<BrokerageFeeDetailOrder> brokerageFeeDetailOrders = order.getBrokerageFeeDetailOrder();
						BrokerageFeeOrder brokerageFeeOrder = order.getBrokerageFeeOrder();
						
						if (null != brokerageFeeOrder) {
							BrokerageFeeDO brokerageFeeDO = new BrokerageFeeDO();
							brokerageFeeDO.setBusinessBillId(doObj.getBusinessBillId());
							brokerageFeeDO.setRawAddTime(now);
							BrokerageFeeOrder brokerageFeeOrder2 = order.getBrokerageFeeOrder();
							if (null != brokerageFeeOrder2) {
								brokerageFeeDO.setBrokerageAmount(brokerageFeeOrder2.getBrokerageAmount());
								brokerageFeeDO.setBrokerageScale(!StringUtils.isBlank(brokerageFeeOrder2.getBrokerageScale())?Double.valueOf(brokerageFeeOrder2.getBrokerageScale()):0);
							}
							brokerageFeeDAO.insert(brokerageFeeDO);
							for (BrokerageFeeDetailOrder brokerageFeeDetailOrder : brokerageFeeDetailOrders) {
								BrokerageFeeDetailDO brokerageFeeDetailDO = new BrokerageFeeDetailDO();
								brokerageFeeDetailDO.setActualPayDate(DateUtil.parse(brokerageFeeDetailOrder.getActualPayDate()));
								brokerageFeeDetailDO.setPlanPayDate(DateUtil.parse(brokerageFeeDetailOrder.getPlanPayDate()));
								brokerageFeeDetailDO.setAppserialPeriod(brokerageFeeDetailOrder.getAppserialPeriod());
								brokerageFeeDetailDO.setRawAddTime(now);
								brokerageFeeDetailDO.setReceivableAmount(brokerageFeeDetailOrder.getReceivableAmount());
								brokerageFeeDetailDO.setBorkerageScale(!StringUtils.isBlank(brokerageFeeDetailOrder.getBorkerageScale())?Double.valueOf(brokerageFeeDetailOrder.getBorkerageScale()):0);
								brokerageFeeDetailDO.setBrokerageFeeId(brokerageFeeDO.getBrokerageId());
								brokerageFeeDetailDAO.insert(brokerageFeeDetailDO);
								//查询对应的缴费信息，获取缴费编号
								InsuranceBillRenewalDO insuranceBillRenewalDO = insuranceBillRenewalDAO.findInsuranceBillRenewal(doObj.getBusinessBillId(), Integer.parseInt(brokerageFeeDetailOrder.getAppserialPeriod()));
								if (!StringUtils.isBlank(brokerageFeeDetailOrder.getExtactTime())) {
									//新增经纪费结算信息
									SettlementCompanyBillDO settlementCompanyBillDO = new SettlementCompanyBillDO();
									settlementCompanyBillDO.setBusinessBillId(doObj.getBusinessBillId());
									if (null != insuranceBillRenewalDO) {
										settlementCompanyBillDO.setBillNo(insuranceBillRenewalDO.getPaymentNumber());
									}
									settlementCompanyBillDO.setAppserialPeriod(brokerageFeeDetailOrder.getAppserialPeriod());
									settlementCompanyBillDO.setCustomerUserId(insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) ? insuranceContactLetterDO.getCustomerUserId() :customerUserDO.getCustomerUserId());
									settlementCompanyBillDO.setCustomerUserName(insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) ? insuranceContactLetterDO.getCustomerUserName() : customerUserDO.getCustomerUserName());
									settlementCompanyBillDO.setInsuancePersonId(insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) ? insuranceContactLetterDO.getCustomerUserId() :customerPersonDO.getCustomerUserId());
									settlementCompanyBillDO.setInsuancePersonName(insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) ? insuranceContactLetterDO.getCustomerUserName() :customerPersonDO.getCustomerUserName());
									settlementCompanyBillDO.setInsuranceNo(doObj.getInsuranceNo());
									settlementCompanyBillDO.setInsuranceCompanyId(doObj.getInsuranceCompanyId());
									settlementCompanyBillDO.setInsuranceCompanyName(doObj.getInsuranceCompanyName());
									settlementCompanyBillDO.setStatus(SettlementCompanyStatusEnum.UNTREATED.getCode());
									settlementCompanyBillDO.setBrokerAmount(brokerageFeeDetailOrder.getReceivableAmount());
									settlementCompanyBillDO.setRate(!StringUtils.isBlank(brokerageFeeDetailOrder.getBorkerageScale())?Double.valueOf(brokerageFeeDetailOrder.getBorkerageScale()):0);
									settlementCompanyBillDO.setPlanPayDate(DateUtil.parse(brokerageFeeDetailOrder.getPlanPayDate()));
									settlementCompanyBillDO.setRawAddTime(now);
									//保费（从投保确认单缴费计划部分的保险费加总获得）
									Money premiumAmount = new Money(0,0);
									List<BusinessBillPayPlanOrder> businessBillPayPlanOrders = order.getBusinessBillPayPlanOrders();
									if (ListUtil.isNotEmpty(businessBillPayPlanOrders)) {
										for (BusinessBillPayPlanOrder businessBillPayPlanOrder : businessBillPayPlanOrders) {
											premiumAmount = premiumAmount.add(businessBillPayPlanOrder.getPremiumAmount());
										}
									}
									settlementCompanyBillDO.setPremiumAmount(premiumAmount);
									settlementCompanyBillDAO.insert(settlementCompanyBillDO);
								}
							}
						}
					}
					
					//新增 佣金信息
					if (ListUtil.isNotEmpty(order.getCommissionInfoDetailOrder())) {
						List<CommissionInfoDetailOrder> commissionInfoDetailOrders = order.getCommissionInfoDetailOrder();
						CommissionInfoOrder commissionInfoOrder = order.getCommissionInfoOrder();
						if (null != commissionInfoOrder) {
							CommissionInfoDO  commissionInfoDO = new CommissionInfoDO(); 
							commissionInfoDO.setBusinessBillId(doObj.getBusinessBillId());
							commissionInfoDO.setRawAddTime(now);
							commissionInfoDO.setCommissionAmount(commissionInfoOrder.getCommissionAmount());
							commissionInfoDO.setCommissionCatalog(commissionInfoOrder.getCommissionCatalog());
							commissionInfoDO.setCommissionScale(!StringUtils.isBlank(commissionInfoOrder.getCommissionScale())?Double.valueOf(commissionInfoOrder.getCommissionScale()):0);
							commissionInfoDAO.insert(commissionInfoDO);
							
							Money balance = new Money(0,0);
							for (CommissionInfoDetailOrder commissionInfoDetailOrder : commissionInfoDetailOrders) {
								CommissionInfoDetailDO  commissionInfoDetailDO = new CommissionInfoDetailDO();
								commissionInfoDetailDO.setCommissionInfoId((int)commissionInfoDO.getCommissionInfoId());
								commissionInfoDetailDO.setAppserialPeriod(commissionInfoDetailOrder.getAppserialPeriod());
								commissionInfoDetailDO.setPlanPayDate(DateUtil.parse(commissionInfoDetailOrder.getPlanPayDate()));
								commissionInfoDetailDO.setReceivableAmount(commissionInfoDetailOrder.getReceivableAmount());
								commissionInfoDetailDO.setRawAddTime(now);
								commissionInfoDetailDAO.insert(commissionInfoDetailDO);
								
								if (!StringUtils.isBlank(commissionInfoDetailOrder.getExtactTime())) {
									SysUser sysUser = bpmUserQueryService.findUserByUserId(order.getBusinessUserId());
									PersonCommissionDO businessUserCommissionDo = personCommissionDAO.findByUserId(order.getBusinessUserId());
									PersonCommissionDO personCommissionDO = new PersonCommissionDO();
									balance = balance.add(commissionInfoDetailOrder.getReceivableAmount());
									if (null == businessUserCommissionDo) {
										//新增佣金结算信息
										personCommissionDO.setBusinessUserId(order.getBusinessUserId());
										personCommissionDO.setBusinessUserName(order.getBusinessUserName());
										//查询业务员的证件号码、证件类型
										personCommissionDO.setBusinessUserMobile(sysUser.getMobile());
										personCommissionDO.setApplyingAmount(new Money(0,0));
										personCommissionDO.setRawAddTime(new Date());
										personCommissionDO.setTotalAmount(commissionInfoDetailOrder.getReceivableAmount());
										personCommissionDO.setBusinessUserCertNo(order.getBusinessUserCertNo());
										personCommissionDO.setBusinessUserType(order.getBusinessUserType());
										personCommissionDO.setDrawAmount(new Money(0,0));
										personCommissionDAO.insert(personCommissionDO);
									} else {
										businessUserCommissionDo.setTotalAmount(businessUserCommissionDo.getTotalAmount().add(commissionInfoDetailOrder.getReceivableAmount()));
										businessUserCommissionDo.setBusinessUserCertNo(order.getBusinessUserCertNo());
										businessUserCommissionDo.setBusinessUserType(order.getBusinessUserType());
										businessUserCommissionDo.setBusinessUserMobile(sysUser.getMobile());
										personCommissionDAO.update(businessUserCommissionDo);
									}
									//新增业务员佣金明细
									PersonCommissionDetailDO  personCommissionDetailDO = new PersonCommissionDetailDO();
									personCommissionDetailDO.setCommissionTime(DateUtil.parse(commissionInfoDetailOrder.getPlanPayDate()));
									personCommissionDetailDO.setBalance(null == businessUserCommissionDo ? balance :businessUserCommissionDo.getTotalAmount().subtract(businessUserCommissionDo.getDrawAmount()));
									personCommissionDetailDO.setBusinessUserId(order.getBusinessUserId());
									personCommissionDetailDO.setBusinessUserName(order.getBusinessUserName());
									personCommissionDetailDO.setBusinessUserType(order.getBusinessUserType());
									personCommissionDetailDO.setCommissionType(CommissionTypeEnum.INCOME.getCode());
									personCommissionDetailDO.setCommissionAmount(commissionInfoDetailOrder.getReceivableAmount());
									personCommissionDetailDO.setSerialNumber("");
									personCommissionDetailDO.setBusinessBillId(doObj.getBusinessBillId());
									personCommissionDetailDO.setInsuranceNo(doObj.getInsuranceNo());
									personCommissionDetailDO.setRemark("保单号："+doObj.getInsuranceNo()+"-"+"费用类型"+commissionInfoDO.getCommissionCatalog());
									personCommissionDetailDO.setRawAddTime(now);
									personCommissionDetailDAO.insert(personCommissionDetailDO);
								}
							}
						}
					}
					//新增共保信息
					if (ListUtil.isNotEmpty(order.getBusinessBillCoinsInfoOrders())) {
						for (BusinessBillCoinsInfoOrder businessBillCoinsInfoOrder : order.getBusinessBillCoinsInfoOrders()) {
							BusinessBillCoinsInfoDO  businessBillCoinsInfoDO = new BusinessBillCoinsInfoDO();
							BeanCopier.staticCopy(businessBillCoinsInfoOrder, businessBillCoinsInfoDO);
							businessBillCoinsInfoDO.setBusinessBillId(doObj.getBusinessBillId());
							businessBillCoinsInfoDAO.insert(businessBillCoinsInfoDO);
						}
						
					}
					
					//新增再保信息
					if (ListUtil.isNotEmpty(order.getBusinessBillReinsInfoOrders())) {
						for (BusinessBillReinsInfoOrder businessBillReinsInfoOrder : order.getBusinessBillReinsInfoOrders()) {
							BusinessBillReinsInfoDO  businessBillReinsInfoDO = new BusinessBillReinsInfoDO();
							BeanCopier.staticCopy(businessBillReinsInfoOrder, businessBillReinsInfoDO);
							businessBillReinsInfoDO.setBusinessBillId(doObj.getBusinessBillId());
							businessBillReinsInfoDAO.insert(businessBillReinsInfoDO);
						}
						
					}
				} else {
					//保单信息维护只修改共保再保信息以及缴费计划信息
					BusinessBillDO doObj = businessBillDAO.findById(order.getBusinessBillId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					
					//修改共保再保信息
					updateBusinessBillCoinsAndReins(order, doObj);
					
					//更改缴费计划
					updateBusinessBillPayPlan(order, doObj);
				}
				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(id);
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
	public BusinessBillInfo findById(long id) {
		BusinessBillDO businessBillDo = businessBillDAO.findById(id);
		if (businessBillDo != null) {
			BusinessBillInfo businessBillInfo = new BusinessBillInfo();
			BeanCopier.staticCopy(businessBillDo, businessBillInfo);
			return businessBillInfo;
		}
		return null;
	}
	
	
	
	/**
	 * 更改保单的共保再保信息
	 * @param order
	 * @param doObj
	 */
	public void updateBusinessBillCoinsAndReins(BusinessBillOrder order,BusinessBillDO doObj){
		//查询所有该保单的共保信息
		List<BusinessBillCoinsInfoDO> businessBillCoinsInfoDOs = businessBillCoinsInfoDAO.findBusinessBillCoinsInfo(doObj.getBusinessBillId());
		if (ListUtil.isNotEmpty(businessBillCoinsInfoDOs)){
			for (BusinessBillCoinsInfoDO businessBillCoinsInfoDO : businessBillCoinsInfoDOs) {
				boolean exist = false;
				if (ListUtil.isNotEmpty(order.getBusinessBillCoinsInfoOrders())){
					for (BusinessBillCoinsInfoOrder businessBillCoinsInfoOrder : order.getBusinessBillCoinsInfoOrders()) {
						if (businessBillCoinsInfoDO.getInsuraceCompanyName().trim().equals(businessBillCoinsInfoOrder.getInsuraceCompanyName().trim())) {
							exist = true;
							break;
						}
					}
					if (!exist) {
						businessBillCoinsInfoDAO.deleteById(businessBillCoinsInfoDO.getCoinsInfoId());
					}
				} else {
					businessBillCoinsInfoDAO.deleteById(businessBillCoinsInfoDO.getCoinsInfoId());
				}
			}
		}
		if (ListUtil.isNotEmpty(order.getBusinessBillCoinsInfoOrders())){
			for (BusinessBillCoinsInfoOrder businessBillCoinsInfoOrder : order.getBusinessBillCoinsInfoOrders()) {
				if (ListUtil.isNotEmpty(businessBillCoinsInfoDOs)){
					boolean flag = false;
					for (BusinessBillCoinsInfoDO businessBillCoinsInfoDO : businessBillCoinsInfoDOs) {
						//同一家保险公司 修改
						if (businessBillCoinsInfoDO.getInsuraceCompanyName().trim().equals(businessBillCoinsInfoOrder.getInsuraceCompanyName().trim())) {
							businessBillCoinsInfoDO.setBrokerAmount(businessBillCoinsInfoOrder.getBrokerAmount());
							businessBillCoinsInfoDO.setChief(businessBillCoinsInfoOrder.getChief());
							businessBillCoinsInfoDO.setOtherAmount(businessBillCoinsInfoOrder.getOtherAmount());
							businessBillCoinsInfoDO.setOutAmount(businessBillCoinsInfoOrder.getOutAmount());
							businessBillCoinsInfoDO.setPortion(businessBillCoinsInfoOrder.getPortion());
							businessBillCoinsInfoDO.setPremiumAmount(businessBillCoinsInfoOrder.getPremiumAmount());
							businessBillCoinsInfoDAO.update(businessBillCoinsInfoDO);
							flag = true;
							break;
						}
					}
					if (!flag) {
						BusinessBillCoinsInfoDO billCoinsInfoDO = new BusinessBillCoinsInfoDO();
						BeanCopier.staticCopy(businessBillCoinsInfoOrder, billCoinsInfoDO);
						billCoinsInfoDO.setBusinessBillId(doObj.getBusinessBillId());
						businessBillCoinsInfoDAO.insert(billCoinsInfoDO);
					}
				} else {
					//新增共保信息
					if (ListUtil.isNotEmpty(order.getBusinessBillCoinsInfoOrders())){
						for (BusinessBillCoinsInfoOrder billCoinsInfoOrder : order.getBusinessBillCoinsInfoOrders()) {
							BusinessBillCoinsInfoDO  businessBillCoinsInfoDO = new BusinessBillCoinsInfoDO();
							BeanCopier.staticCopy(billCoinsInfoOrder, businessBillCoinsInfoDO);
							businessBillCoinsInfoDO.setBusinessBillId(doObj.getBusinessBillId());
							businessBillCoinsInfoDAO.insert(businessBillCoinsInfoDO);
						}
						break;
					}
				}
			}
		} 
		
		
		//查询所有该保单的再保信息
		List<BusinessBillReinsInfoDO> billReinsInfoDOs = businessBillReinsInfoDAO.findBusinessBillReinsInfo(doObj.getBusinessBillId());
		if (ListUtil.isNotEmpty(billReinsInfoDOs)){
			for (BusinessBillReinsInfoDO businessBillReinsInfoDO : billReinsInfoDOs) {
				boolean exist = false;
				if (ListUtil.isNotEmpty(order.getBusinessBillReinsInfoOrders())){
					for (BusinessBillReinsInfoOrder businessBillReinsInfoOrder : order.getBusinessBillReinsInfoOrders()) {
						if (businessBillReinsInfoDO.getInsuraceCompanyName().trim().equals(businessBillReinsInfoOrder.getInsuraceCompanyName().trim())) {
							exist = true;
							break;
						}
					}
					if (!exist) {
						businessBillReinsInfoDAO.deleteById(businessBillReinsInfoDO.getReinsInfoId());
					}
				} else {
					businessBillReinsInfoDAO.deleteById(businessBillReinsInfoDO.getReinsInfoId());
				}
			}
		}
		//新增再保信息
		if (ListUtil.isNotEmpty(order.getBusinessBillReinsInfoOrders())){
			for (BusinessBillReinsInfoOrder businessBillReinsInfoOrder : order.getBusinessBillReinsInfoOrders()) {
				if (ListUtil.isNotEmpty(billReinsInfoDOs)){
					boolean flag = false;
					for (BusinessBillReinsInfoDO businessBillReinsInfoDO : billReinsInfoDOs) {
						if (businessBillReinsInfoOrder.getInsuraceCompanyName().trim().equals(businessBillReinsInfoDO.getInsuraceCompanyName().trim())) {
							businessBillReinsInfoDO.setPoundageAmount(businessBillReinsInfoOrder.getPoundageAmount());
							businessBillReinsInfoDO.setPremiumAmount(businessBillReinsInfoOrder.getPremiumAmount());
							businessBillReinsInfoDAO.update(businessBillReinsInfoDO);
							flag = true;
							break;
						}
					}
					if (!flag) {
						BusinessBillReinsInfoDO billReinsInfoDO = new BusinessBillReinsInfoDO();
						BeanCopier.staticCopy(businessBillReinsInfoOrder, billReinsInfoDO);
						billReinsInfoDO.setBusinessBillId(doObj.getBusinessBillId());
						businessBillReinsInfoDAO.insert(billReinsInfoDO);
					}
			} else {
				if (ListUtil.isNotEmpty(order.getBusinessBillReinsInfoOrders())){
					for (BusinessBillReinsInfoOrder billReinsInfoOrder : order.getBusinessBillReinsInfoOrders()) {
						BusinessBillReinsInfoDO  businessBillReinsInfoDO = new BusinessBillReinsInfoDO();
						BeanCopier.staticCopy(billReinsInfoOrder, businessBillReinsInfoDO);
						businessBillReinsInfoDO.setBusinessBillId(doObj.getBusinessBillId());
						businessBillReinsInfoDAO.insert(businessBillReinsInfoDO);
					}
					break;
				}
			}
		}
		} 
	}
	
	
	/**
	 * 更改保单的缴费计划
	 * @param order
	 * @param doObj
	 */
	public void updateBusinessBillPayPlan(BusinessBillOrder order,BusinessBillDO doObj){
		//如果没有对应的缴费信息则新增
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
						billPayPlanDO.setDataAddTime(new Date());
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
							InsuranceBillRenewalDO.setBusinessBillId(doObj.getBusinessBillId());
							InsuranceBillRenewalDO.setPremiumAmount(businessBillPayPlanOrder.getPremiumAmount());
							InsuranceBillRenewalDO.setPeriod(Integer.parseInt(businessBillPayPlanOrder.getAppserialPeriod()));
							InsuranceBillRenewalDO.setBillNo(doObj.getBillNo());
							insuranceBillRenewalDAO.insert(InsuranceBillRenewalDO);
						}
						
						//每个年度的第一期 缴费之后，结算经纪费 和 佣金信息
						if (businessBillPayPlanOrder.getSerial() == 1) {
							//更新对应期数佣金信息 和 经纪费信息
							BrokerageFeeDetailDO brokerageFeeDetailDO =  brokerageFeeDetailDAO.getBrokerageFee(doObj.getBusinessBillId(),businessBillPayPlanOrder.getCurrentYear());
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
								settlementCompanyBillDO.setRawAddTime(new Date());
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
							CommissionInfoDetailDO commissionInfoDetailDO = commissionInfoDetailDAO.getCommissionInfo(doObj.getBusinessBillId(),businessBillPayPlanOrder.getCurrentYear());
							if (null != commissionInfoDetailDO) {
								commissionInfoDetailDO.setPlanPayDate(DateUtil.parse(businessBillPayPlanOrder.getActualPayDate()));
							    commissionInfoDetailDAO.update(commissionInfoDetailDO);
							    //更新业务员佣金信息
							    PersonCommissionDO businessUserCommissionDo = personCommissionDAO.findByUserId(doObj.getBusinessUserId());
							    SysUser sysUser = bpmUserQueryService.findUserByUserId(order.getBusinessUserId());
								PersonCommissionDO personCommissionDO = new PersonCommissionDO();
								if (null == businessUserCommissionDo) {
									//新增佣金结算信息
									personCommissionDO.setBusinessUserId(doObj.getBusinessUserId());
									personCommissionDO.setBusinessUserName(doObj.getBusinessUserName());
									personCommissionDO.setApplyingAmount(new Money(0,0));
									personCommissionDO.setRawAddTime(new Date());
									personCommissionDO.setTotalAmount(commissionInfoDetailDO.getReceivableAmount());
									personCommissionDO.setBusinessUserCertNo(order.getBusinessUserCertNo());
									personCommissionDO.setBusinessUserMobile(sysUser.getMobile());
									personCommissionDO.setBusinessUserType(order.getBusinessUserType());
									personCommissionDO.setDrawAmount(new Money(0,0));
									personCommissionDAO.insert(personCommissionDO);
								} else {
									businessUserCommissionDo.setTotalAmount(businessUserCommissionDo.getTotalAmount().add(commissionInfoDetailDO.getReceivableAmount()));
									businessUserCommissionDo.setBusinessUserCertNo(order.getBusinessUserCertNo());
									businessUserCommissionDo.setBusinessUserType(order.getBusinessUserType());
									businessUserCommissionDo.setBusinessUserMobile(sysUser.getMobile());
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
								personCommissionDetailDO.setRawAddTime(new Date());
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

	}
	
	
	
	@Override
	public QueryBaseBatchResult<BusinessBillInfo> queryList(BusinessBillQueryOrder queryOrder) {
		QueryBaseBatchResult<BusinessBillInfo> batchResult = new QueryBaseBatchResult<BusinessBillInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillInfo> pageList = new ArrayList<BusinessBillInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillDO businessBillDO = new BusinessBillDO();
			BeanCopier.staticCopy(queryOrder, businessBillDO);
			long totalCount = businessBillDAO.findByConditionCount(businessBillDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillDO> recordList = businessBillDAO.findByCondition(businessBillDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (BusinessBillDO item : recordList) {
				BusinessBillInfo info = new BusinessBillInfo();
				BeanCopier.staticCopy(item, info);
				//查询保险产品
				InsuranceContactLetterDO insuranceContactLetterDO = insuranceContactLetterDAO.findById(item.getLetterId());
				if (insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode()) || 
						(insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && insuranceContactLetterDO.getIsQuota().equals("NO"))) {
					List<InsuranceContactLetterDetailDO> insuranceContactLetterDetails = insuranceContactLetterDetailDAO.getInsuranceContactLetterDetails(item.getLetterId());
					StringBuffer sb = new StringBuffer();
					StringBuffer catalogId = new StringBuffer();
					StringBuffer catalogName = new StringBuffer();
					for (InsuranceContactLetterDetailDO insuranceContactLetterDetailDO : insuranceContactLetterDetails) {
						sb.append(insuranceContactLetterDetailDO.getProductName()).append(",");
						InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(insuranceContactLetterDetailDO.getProductId());
						if (null !=insuranceProductInfo ) {
							catalogId.append(String.valueOf(insuranceProductInfo.getCatalogId())).append(",");
							catalogName.append(insuranceProductInfo.getCatalogName()).append(",");
						}
					}
					String productStr = sb.toString();
					String catalogIdStr = catalogId.toString();
					String catalogNameStr = catalogName.toString();
					info.setInsuranceProduct(productStr.substring(0,productStr.length() - 1));
					info.setCatalogId(catalogIdStr.substring(0,catalogIdStr.length()-1));
					info.setCatalogName(catalogNameStr.substring(0,catalogNameStr.length()-1));	
				} else {
					long productId = insuranceContactLetterDO.getProductId();
					if (productId > 0) {
						InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
						if (null != insuranceProductInfo) {
							info.setCatalogId(String.valueOf(insuranceProductInfo.getCatalogId()));
							info.setCatalogName(insuranceProductInfo.getCatalogName());
							info.setInsuranceProduct(insuranceContactLetterDO.getProductName());
						}
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
	
	/**
	 * 获取佣金和 经纪费比例
	 * @param type
	 * @param productId
	 * @param companyUserId
	 * @param catalogId
	 * @param period
	 * @param appserialPeriod
	 * @return
	 */
	public Double getScale (String type, long productId, long companyUserId, String catalogId, String period, String appserialPeriod) {
		InsuranceProtocolChargeDO protocolChargeDO = insuranceProtocolChargeDAO.getBrokerageRate(type, productId, companyUserId, null, String.valueOf(period), appserialPeriod, new Date());
		if (null == protocolChargeDO) {
			protocolChargeDO = insuranceProtocolChargeDAO.getBrokerageRate(type, productId, companyUserId,catalogId, String.valueOf(period), appserialPeriod, new Date());
		}
		return null == protocolChargeDO ? 0 : Double.valueOf(protocolChargeDO.getVal())/100;
	}


	@Override
	public ShowMsgInfo addPayBillPlan(InsuranceContactLetterInfo insuranceContactLetterInfo,
			List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails) {
		List<BusinessBillCustomerDO> findBusinessBillCustomers = businessBillCustomerDAO.findBusinessBillCustomers(insuranceContactLetterInfo.getBillNo());
		BusinessBillCustomerDO businessBillCustomerDO1 = findBusinessBillCustomers.get(0);
		BusinessBillCustomerDO businessBillCustomerDO2 = findBusinessBillCustomers.get(1);
		//被保险人人的身份证号码
		String certNo = businessBillCustomerDO1.getType().equals("0")?businessBillCustomerDO2.getCustomerCertNo() : businessBillCustomerDO1.getCustomerCertNo();
		//被保险人的年龄
		int age = InsuranceProtocolChargeServiceImpl.getFullAge(certNo);
		//被保险人人性别
		String sex = Integer.parseInt(certNo.substring(certNo.length()-2,certNo.length()-1))%2 == 0 ? "woman" : "man";
	
		Map<String, List<InsuranceContactLetterDetailInfo>>  maps  = new HashMap<String,List<InsuranceContactLetterDetailInfo>>();
		
		//主险产品
		List<InsuranceContactLetterDetailInfo> mainList = new ArrayList<InsuranceContactLetterDetailInfo>();
		//附加险产品
		List<InsuranceContactLetterDetailInfo> attachList = new ArrayList<InsuranceContactLetterDetailInfo>();
		
		Money totalAmount = new Money(0,0);
		for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
			//投保内容的产品
			long productId = insuranceContactLetterDetailInfo.getProductId();
			InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
			if (null != insuranceProductInfo) {
				InsuranceCatalogInfo InsuranceCatalogInfo = insuranceCatalogService.findById(insuranceProductInfo.getCatalogId());
				//该产品是主险
				String isMain = InsuranceCatalogInfo.getIsMain();
				if (isMain.equals("YES")) {
					mainList.add(insuranceContactLetterDetailInfo);
					maps.put("main", insuranceContactLetterDetails);
				} else {
					attachList.add(insuranceContactLetterDetailInfo);
				}
			}
			totalAmount = totalAmount.add(insuranceContactLetterDetailInfo.getPremiumAmount());
		}
		maps.put("attach", attachList);
		//主险产品
		InsuranceContactLetterDetailInfo mainProduct = maps.get("main").get(0);
		//主险产品的缴费期限 
		Integer period = Integer.parseInt(mainProduct.getPeriod());
		//主险产品对应的费率表
		Map<String, String> queryInsuranceProtocolCharge = extraDAO.queryInsuranceProtocolCharge(mainProduct.getProductId(), sex, period);
		//被保险人人当前年龄所对应的基础数据(主险都是取的当前年龄)
		String baseData = queryInsuranceProtocolCharge.get(String.valueOf(age));
		       baseData = StringUtils.isBlank(baseData) ? "0" :baseData;
		InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(mainProduct.getProductId());
		//主险的缴费类型
		long payType = insuranceProductInfo.getPayType();
		//基础数据比例
		BigDecimal val = mainProduct.getInsuranceAmount().getAmount().divide(insuranceProductInfo.getUnitPrice().getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
		//主险的年交保费
		Money yearPremiumAmount = new Money();
		
		//分期比例系数
		String periodRate = insuranceProductInfo.getPeriodRate();
		//投保起始日期,不分期，在起始日期加一年
		Date beginDate = insuranceContactLetterInfo.getBeginDate();
		Calendar calendar = Calendar.getInstance();
		
		//附加险产品
		List<InsuranceContactLetterDetailInfo> attachProduct = maps.get("attach");
		
		
		int totalMonthPeroid = 0;
		int totalQuarterPeriod = 0;
		int halfYearPerioid = 0;
		//半年交 和 季度交期数
		int halfPeriod = 0;
		int quarterPeriod = 0;
		ShowMsgInfo showMsgInfo = new ShowMsgInfo();
		List<BillPayPlanYear> recordList = new ArrayList<BillPayPlanYear>();
		BrokeFeeAndCommunityInfo BrokeFeeAndCommunityInfo = new BrokeFeeAndCommunityInfo();
		
		/**
		 * 经纪费
		 */
		BrokerageFeeInfo brokerageFeeInfo = new BrokerageFeeInfo() ;
		//主险经纪费
		Money mainBrokeFee = new Money(0,0);
		//附加险经纪费
		Money attachBrokeFee = null;
		
		Money yiciBrokeFee = new Money(0,0);
		Money fenqiBrokeFee = new Money(0,0);
		/**
		 * 经纪费明细
		 */
	   List<BrokerageFeeDetailInfo> brokerageFeeDetails = new ArrayList<BrokerageFeeDetailInfo>();
	   
	   /**
	    * 佣金
	    */
	   CommissionInfoInfo commissionInfoInfo = new CommissionInfoInfo();
   		//主险佣金费用
 		Money mainCommissionInfo = new Money(0,0);
 		//附加佣金费用
 		Money attachCommissionInfo = null ;
 		Money yiciCommissionInfo = new Money(0,0);
		Money fenqiCommissionInfo = new Money(0,0);
		
		/**
		 * 佣金明细
		 */
	List<CommissionInfoDetailInfo> commmissionInfoDetails = new ArrayList<CommissionInfoDetailInfo>();
		
		for (int i = 0; i < period; i++) {
			//经纪费明细
			BrokerageFeeDetailInfo brokerageFeeDetailInfo = new BrokerageFeeDetailInfo();
			//佣金明细
			CommissionInfoDetailInfo commissionInfoDetailInfo = new CommissionInfoDetailInfo();
			//主险的经纪费比例
			Double brokeFeeScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(), mainProduct.getProductId(), insuranceProductInfo.getCompanyBaseUserId(), null, String.valueOf(period), String.valueOf(i+1));
			
			//主险的佣金比例
			Double commissionInfoScale =getScale(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(), mainProduct.getProductId(), insuranceProductInfo.getCompanyBaseUserId(), null, String.valueOf(period), String.valueOf(i+1));
			
			BillPayPlanYear billPayPlanYear = new BillPayPlanYear();
			//初始化每一个的金额
			yearPremiumAmount = new Money(val).multiply(Double.valueOf(baseData));
			attachBrokeFee = new Money(0,0);
			attachCommissionInfo = new Money(0,0);
			
			 List<BillPayPlanYearInfo> infoList = new ArrayList<BillPayPlanYearInfo>();
			 if (i == 0) {
			 	billPayPlanYear.setYear("首年");
			 } else {
				billPayPlanYear.setYear("第"+(i+1)+"年");
			 }
		
			//主险缴费类型为年交 或者 一次性交费，不同分期
			if (payType == 19) {
				BillPayPlanYearInfo info = new BillPayPlanYearInfo();
				//每年主险保费之和
				Money currentTotalAmount = new Money();
				Money currentTotalAttachAmount = new Money();
				//期数
				info.setPeriod(String.valueOf(i+1));
				
				Date date = new Date(beginDate.getTime());
				calendar.setTime(date);
		        calendar.add(Calendar.YEAR, i);
				info.setPlanPayDate(calendar.getTime());
				
				/**
				 * 主险每一年经纪费计算
				 */
				String everyAge = queryInsuranceProtocolCharge.get(String.valueOf(age));
				Money currentYearBrokeFee = new Money(null == everyAge ? "0" : everyAge).multiply(val).multiply(BigDecimal.valueOf(Double.valueOf(brokeFeeScale)));
				/**
				 * 主险每一年佣金的计算
				 */
				Money currentYearCommissionInfo = new Money(null == everyAge ? "0" : everyAge).multiply(val).multiply(BigDecimal.valueOf(Double.valueOf(commissionInfoScale)));
				
				//如果不分期，将附加险的年交保费加到第一期上面
				if (i == 0) {
					
					/**附加险**/
					if (ListUtil.isNotEmpty(attachProduct)) {
						for (InsuranceContactLetterDetailInfo attachProductDetailInfo : attachProduct) {
							long productId = attachProductDetailInfo.getProductId();
							//附加险的保额
							Money insuranceAmount = attachProductDetailInfo.getInsuranceAmount();
							//附加险的缴费期限（不能大于主险的缴费期限，如果大于，将以主险的缴费期限为准）
							Integer attachPeriod = Integer.parseInt(attachProductDetailInfo.getPeriod());
							//附加险产品
							InsuranceProductInfo attachProductInfo = insuranceProductService.findById(productId);
							
							//附加险的基本保额
							Money attachUnitPrice = attachProductInfo.getUnitPrice();
							
							
							//基础数据（年交保费）
							BigDecimal attachVal =insuranceAmount.getAmount().divide(attachUnitPrice.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
							attachPeriod = attachPeriod > period ?  period : attachPeriod;
							//附加险产品对应的费率表
							Map<String, String> attachQueryInsuranceProtocolCharge = extraDAO.queryInsuranceProtocolCharge(attachProductInfo.getProductId(), sex, attachPeriod);
							for (int j = 0; j < attachPeriod; j++) {
								String attachBaseData = attachQueryInsuranceProtocolCharge.get(String.valueOf(age+j));
								attachBaseData = StringUtils.isBlank(attachBaseData) ? "0" :attachBaseData;
								Money resultBasedata = new  Money(attachBaseData).multiply(attachVal);
								yearPremiumAmount = yearPremiumAmount.add(resultBasedata);
								currentTotalAttachAmount = currentTotalAttachAmount.add(resultBasedata);
								/**
								 * 附加险经纪费计算
								 */
								Double attachBrokeFeeScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(j+1));
								attachBrokeFee = attachBrokeFee.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachBrokeFeeScale))));
								/**
								 * 附加险佣金的计算
								 */
								Double attachCommissionScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(j+1));
								attachCommissionInfo = attachCommissionInfo.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachCommissionScale))));
							}
							
						}
					}
				}
				//每年的主险保费+附加险保费
				currentTotalAmount = currentTotalAmount.add(new Money(null == everyAge ? "0" : everyAge).multiply(val).add(currentTotalAttachAmount));
				//每年的经纪费
				Money currentBrokeFee = currentYearBrokeFee.add(attachBrokeFee);
				//每年的拥金
				Money currentCommissionInfo = currentYearCommissionInfo.add(attachCommissionInfo);
				//年交保费
				info.setPremiumAmount(yearPremiumAmount);
				infoList.add(info);
				billPayPlanYear.setInfo(infoList);
				
				brokerageFeeDetailInfo.setAppserialPeriod(String.valueOf(i+1));
				brokerageFeeDetailInfo.setReceivableAmount(currentBrokeFee);
				brokerageFeeDetailInfo.setBorkerageScale(currentBrokeFee.getAmount().divide(currentTotalAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
				commissionInfoDetailInfo.setAppserialPeriod(String.valueOf(i+1));
				commissionInfoDetailInfo.setReceivableAmount(currentCommissionInfo);
				
				/**
				 * 每一年总的经纪费
				 */
				mainBrokeFee = mainBrokeFee.add(currentBrokeFee);
				/**
				 * 每一年总的佣金
				 */
				mainCommissionInfo = mainCommissionInfo.add(currentCommissionInfo);
				
			} else {
				//按年分期
				if (payType == 18){
				
					BillPayPlanYearInfo info = new BillPayPlanYearInfo();
					//期数
					info.setPeriod(String.valueOf(i+1));
					
					Date date = new Date(beginDate.getTime());
					calendar.setTime(date);
			        calendar.add(Calendar.YEAR, i);
					info.setPlanPayDate(calendar.getTime());
					
					/**
					 * 主险每一年经纪费计算
					 */
					String everyAge = queryInsuranceProtocolCharge.get(String.valueOf(age));
					Money currentYearBrokeFee = new Money(null == everyAge ? "0" : everyAge).multiply(val).multiply(BigDecimal.valueOf(Double.valueOf(brokeFeeScale)));
					/**
					 * 主险每一年佣金的计算
					 */
					Money currentYearCommissionInfo = new Money(null == everyAge ? "0" : everyAge).multiply(val).multiply(BigDecimal.valueOf(Double.valueOf(commissionInfoScale)));
					
					/**附加险**/
					if (ListUtil.isNotEmpty(attachProduct)) {
						for (InsuranceContactLetterDetailInfo attachProductDetailInfo : attachProduct) {
							long productId = attachProductDetailInfo.getProductId();
						
							//附加险产品
							InsuranceProductInfo attachProductInfo = insuranceProductService.findById(productId);
							
							//如果附加险产品的缴费类型 和 主险的缴费类型一致，按主险的类，不一致则不分期，将数据新增到主险的第一期上面
							if (payType == attachProductInfo.getPayType()) {
								//附加险的基本保额
								Money attachUnitPrice = attachProductInfo.getUnitPrice();
								//附加险的缴费期限（不能大于主险的缴费期限，如果大于，将以主险的缴费期限为准）
								Integer attachPeriod = Integer.parseInt(attachProductDetailInfo.getPeriod()) > period ? period : Integer.parseInt(attachProductDetailInfo.getPeriod());
								//附加险的保额
								Money insuranceAmount = attachProductDetailInfo.getInsuranceAmount();
								//附加险产品对应的费率表
								Map<String, String> attachQueryInsuranceProtocolCharge = extraDAO.queryInsuranceProtocolCharge(attachProductInfo.getProductId(), sex, attachPeriod);
								
								//基础数据（年交保费）
								BigDecimal attachVal =insuranceAmount.getAmount().divide(attachUnitPrice.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
								//分期保费的计算
								String attachBaseData = attachQueryInsuranceProtocolCharge.get(String.valueOf(age+i));
								attachBaseData = StringUtils.isBlank(attachBaseData) ? "0" :attachBaseData;
								Money resultBasedata = new  Money(attachBaseData).multiply(attachVal);
								yearPremiumAmount = yearPremiumAmount.add(resultBasedata);
								
								/**
								 * 分期经纪费的计算
								 */
								Double attachBrokeFeeScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(i+1));
								attachBrokeFee = attachBrokeFee.add(resultBasedata.multiply(BigDecimal.valueOf(Double.valueOf(attachBrokeFeeScale))));
								
								/**
								 * 附加险佣金的计算
								 */
								Double attachCommissionScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(i+1));
								attachCommissionInfo = attachCommissionInfo.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachCommissionScale))));
							} else {
								//一次性不分期
								if (i == 0) {
									//附加险的基本保额
									Money attachUnitPrice = attachProductInfo.getUnitPrice();
									//附加险的缴费期限（不能大于主险的缴费期限，如果大于，将以主险的缴费期限为准）
									Integer attachPeriod = Integer.parseInt(attachProductDetailInfo.getPeriod());
									//附加险的保额
									Money insuranceAmount = attachProductDetailInfo.getInsuranceAmount();
									//基础数据（年交保费）
									BigDecimal attachVal =insuranceAmount.getAmount().divide(attachUnitPrice.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
									attachPeriod = attachPeriod > period ?  period : attachPeriod;
									//附加险产品对应的费率表
									Map<String, String> attachQueryInsuranceProtocolCharge = extraDAO.queryInsuranceProtocolCharge(attachProductInfo.getProductId(), sex, attachPeriod);
									for (int  k= 0; k < attachPeriod; k++) {
										String attachBaseData = attachQueryInsuranceProtocolCharge.get(String.valueOf(age+k));
										attachBaseData = StringUtils.isBlank(attachBaseData) ? "0" :attachBaseData;
										Money resultBasedata = new  Money(attachBaseData).multiply(attachVal);
										yearPremiumAmount = yearPremiumAmount.add(resultBasedata);
										
										/**
										 * 附加险经纪费计算
										 */
										Double attachBrokeFeeScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(k+1));
										attachBrokeFee = attachBrokeFee.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachBrokeFeeScale))));
										/**
										 * 附加险佣金的计算
										 */
										Double attachCommissionScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(k+1));
										attachCommissionInfo = attachCommissionInfo.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachCommissionScale))));
									}
								} 
							}
						}
					}
					
					//每年的经纪费
					Money currentBrokeFee = currentYearBrokeFee.add(attachBrokeFee);
					//每年的拥金
					Money currentCommissionInfo = currentYearCommissionInfo.add(attachCommissionInfo);
					
					//年交保费
					info.setPremiumAmount(yearPremiumAmount);
					infoList.add(info);
					billPayPlanYear.setInfo(infoList);
					
					brokerageFeeDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					brokerageFeeDetailInfo.setReceivableAmount(currentBrokeFee);
					brokerageFeeDetailInfo.setBorkerageScale(currentBrokeFee.getAmount().divide(yearPremiumAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
					commissionInfoDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					commissionInfoDetailInfo.setReceivableAmount(currentCommissionInfo);
					/**
					 * 总的经纪费
					 */
					mainBrokeFee = mainBrokeFee.add(currentBrokeFee);
					/**
					 * 总的佣金
					 */
					mainCommissionInfo = mainCommissionInfo.add(currentCommissionInfo);
					
				}
				
				//按照季度、月份、半年分期
				//月份
				if (payType == 15) {
					//每年主险保费和 + 每年附加险保费之和
					Money currentTotalAmount = new Money();
					//分期月份之和(主险)
					Money totalMonthAmount = new Money(0,0);
					//附加险分期保费之和(附加险)
					Money currentTotalAttachAmount = new Money(0,0);
					for (int j = 0; j < 12; j++) {
						BillPayPlanYearInfo info = new BillPayPlanYearInfo();
						info.setPeriod(String.valueOf(totalMonthPeroid+1));
						Money amount = yearPremiumAmount.multiply(Double.valueOf(periodRate));
						totalMonthAmount = totalMonthAmount.add(amount);
						/**附加险**/
						 CalcBrokeAndCommissionInfo attachAmountCalc = attachAmountCalc(age, sex, period, payType, attachProduct, i, j, amount,yiciBrokeFee,yiciCommissionInfo,fenqiBrokeFee,fenqiCommissionInfo,currentTotalAttachAmount);
						 amount = attachAmountCalc.getAmount() == null ? amount : attachAmountCalc.getAmount();
						 currentTotalAttachAmount = attachAmountCalc.getCurrentTotalAttachAmount() == null ? new Money(0) : attachAmountCalc.getCurrentTotalAttachAmount();
						attachBrokeFee = attachBrokeFee.add(attachAmountCalc.getAttachBrokeFee() == null ? new Money(0) : attachAmountCalc.getAttachBrokeFee());
						attachCommissionInfo = attachCommissionInfo.add(attachAmountCalc.getAttachCommissionInfo() == null ? new Money(0) : attachAmountCalc.getAttachCommissionInfo());
						//如果附加险产品不分期，默认将年交保费计算到主险保费首期，所以附加险的经纪费只计算一次，不能重复累加
						if (i== 0 & j == 0) {
							attachBrokeFee = attachBrokeFee.add(attachAmountCalc.getYiciBrokeFee() == null ? new Money(0) : attachAmountCalc.getYiciBrokeFee());
							attachCommissionInfo = attachCommissionInfo.add(attachAmountCalc.getYiciCommissionInfoMoney() == null ? new Money(0) : attachAmountCalc.getYiciCommissionInfoMoney());
						}
						
						info.setPremiumAmount(amount);
						
						Date date = new Date(beginDate.getTime());
						calendar.setTime(date);
						 calendar.add(Calendar.MONTH, totalMonthPeroid);
					    info.setPlanPayDate(calendar.getTime());
					    
						infoList.add(info);
						totalMonthPeroid++;
					}
					
					//每年的主险保费+附加险保费
					currentTotalAmount = currentTotalAmount.add(totalMonthAmount.add(currentTotalAttachAmount));
					//每年的经纪费(分期保费之和 * 经纪费比例)
					Money currentBrokeFee = totalMonthAmount.multiply(BigDecimal.valueOf(Double.valueOf(brokeFeeScale))).add(attachBrokeFee);
					//每年的拥金
					Money currentCommissionInfo = totalMonthAmount.multiply(BigDecimal.valueOf(Double.valueOf(commissionInfoScale))).add(attachCommissionInfo);
					//经纪费明细
					brokerageFeeDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					brokerageFeeDetailInfo.setBorkerageScale(currentBrokeFee.getAmount().divide(currentTotalAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
					brokerageFeeDetailInfo.setReceivableAmount(currentBrokeFee);
					//佣金明细
					commissionInfoDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					commissionInfoDetailInfo.setReceivableAmount(currentCommissionInfo);
					//当前年的经纪费
					mainBrokeFee = mainBrokeFee.add(currentBrokeFee);
					//当前年的佣金
					mainCommissionInfo = mainCommissionInfo.add(currentCommissionInfo);
				}
				//季度
				if (payType == 16) {
					//每年主险保费之和+每年附加险保费之和
					Money currentTotalAmount = new Money();
					//每年主险保费之和(分期季度之和)
					Money totalYearAmount = new Money(0,0);
					//附加险分期保费之和(附加险)
					Money currentTotalAttachAmount = new Money(0,0);
					for (int j = 0; j < 4; j++) {
						BillPayPlanYearInfo info = new BillPayPlanYearInfo();
						info.setPeriod(String.valueOf(quarterPeriod+1));
						Money amount = yearPremiumAmount.multiply(Double.valueOf(periodRate));
						totalYearAmount = totalYearAmount.add(amount);
						/**附加险**/
						CalcBrokeAndCommissionInfo attachAmountCalc = attachAmountCalc(age, sex, period, payType, attachProduct, i, j, amount,yiciBrokeFee,yiciCommissionInfo,fenqiBrokeFee,fenqiCommissionInfo,currentTotalAttachAmount);
						amount = attachAmountCalc.getAmount() == null ? amount : attachAmountCalc.getAmount();
						currentTotalAttachAmount = attachAmountCalc.getCurrentTotalAttachAmount() == null ? new Money(0) : attachAmountCalc.getCurrentTotalAttachAmount();
						attachBrokeFee = attachBrokeFee.add(attachAmountCalc.getAttachBrokeFee());
						attachCommissionInfo = attachCommissionInfo.add(attachAmountCalc.getAttachCommissionInfo());
						//如果附加险产品不分期，默认将年交保费计算到主险保费首期，所以附加险的经纪费只计算一次，不能重复累加
						if (i== 0 & j == 0) {
							attachBrokeFee = attachBrokeFee.add(attachAmountCalc.getYiciBrokeFee() == null ? new Money(0) : attachAmountCalc.getYiciBrokeFee());
							attachCommissionInfo = attachCommissionInfo.add(attachAmountCalc.getYiciCommissionInfoMoney() == null ? new Money(0) : attachAmountCalc.getYiciCommissionInfoMoney());
						}
						info.setPremiumAmount(amount);
					
						Date date = new Date(beginDate.getTime());
						calendar.setTime(date);
						 calendar.add(Calendar.MONTH, totalQuarterPeriod);
					    info.setPlanPayDate(calendar.getTime());
					    
						infoList.add(info);
						quarterPeriod++;
						totalQuarterPeriod +=3;
					}
					
					//每年的主险保费+附加险保费
					currentTotalAmount = currentTotalAmount.add(totalYearAmount).add(currentTotalAttachAmount);
					//每年的经纪费(分期保费之和 * 经纪费比例)
					Money currentBrokeFee = totalYearAmount.multiply(BigDecimal.valueOf(Double.valueOf(brokeFeeScale))).add(attachBrokeFee);
					//每年的拥金
					Money currentCommissionInfo = totalYearAmount.multiply(BigDecimal.valueOf(Double.valueOf(commissionInfoScale))).add(attachCommissionInfo);
					//经纪费明细
					brokerageFeeDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					brokerageFeeDetailInfo.setBorkerageScale(currentBrokeFee.getAmount().divide(currentTotalAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
					brokerageFeeDetailInfo.setReceivableAmount(currentBrokeFee);
					//佣金明细
					commissionInfoDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					commissionInfoDetailInfo.setReceivableAmount(currentCommissionInfo);
					//当前年的经纪费
					mainBrokeFee = mainBrokeFee.add(currentBrokeFee);
					//当前年的佣金
					mainCommissionInfo = mainCommissionInfo.add(currentCommissionInfo);
				}
				//半年
				if (payType == 17) {
					//每年主险保费之和
					Money currentTotalAmount = new Money();
					//分期半年之和
					Money totalYearAmount = new Money(0,0);
					//附加险分期保费之和(附加险)
					Money currentTotalAttachAmount = new Money(0,0);
					for (int j = 0; j <2; j++) {
						BillPayPlanYearInfo info = new BillPayPlanYearInfo();
						info.setPeriod(String.valueOf(halfPeriod+1));
						Money amount = yearPremiumAmount.multiply(Double.valueOf(periodRate));
						totalYearAmount = totalYearAmount.add(amount);
						/**附加险**/
					    CalcBrokeAndCommissionInfo attachAmountCalc = attachAmountCalc(age, sex, period, payType, attachProduct, i, j, amount,yiciBrokeFee,yiciCommissionInfo,fenqiBrokeFee,fenqiCommissionInfo,currentTotalAttachAmount);
						amount = attachAmountCalc.getAmount() == null ? amount : attachAmountCalc.getAmount();
						currentTotalAttachAmount = attachAmountCalc.getCurrentTotalAttachAmount() == null ? new Money(0) : attachAmountCalc.getCurrentTotalAttachAmount();
						attachBrokeFee = attachBrokeFee.add(attachAmountCalc.getAttachBrokeFee());
						attachCommissionInfo = attachCommissionInfo.add(attachAmountCalc.getAttachCommissionInfo());
						//如果附加险产品不分期，默认将年交保费计算到主险保费首期，所以附加险的经纪费只计算一次，不能重复累加
						if (i== 0 & j == 0) {
							attachBrokeFee = attachBrokeFee.add(attachAmountCalc.getYiciBrokeFee() == null ? new Money(0) : attachAmountCalc.getYiciBrokeFee());
							attachCommissionInfo = attachCommissionInfo.add(attachAmountCalc.getYiciCommissionInfoMoney() ==  null ? new Money(0) : attachAmountCalc.getYiciCommissionInfoMoney());
						}
						
						info.setPremiumAmount(amount);
						Date date = new Date(beginDate.getTime());
						calendar.setTime(date);
						calendar.add(Calendar.MONTH, halfYearPerioid);
					    info.setPlanPayDate(calendar.getTime());
						infoList.add(info);
						halfPeriod++;
						halfYearPerioid += 6;
					}
					//每年的主险保费+附加险保费
					currentTotalAmount = currentTotalAmount.add(totalYearAmount).add(currentTotalAttachAmount);
					//每年的经纪费(分期保费之和 * 经纪费比例)
					Money currentBrokeFee = totalYearAmount.multiply(BigDecimal.valueOf(Double.valueOf(brokeFeeScale))).add(attachBrokeFee);
					//每年的拥金
					Money currentCommissionInfo = totalYearAmount.multiply(BigDecimal.valueOf(Double.valueOf(commissionInfoScale))).add(attachCommissionInfo);
					//经纪费明细
					brokerageFeeDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					brokerageFeeDetailInfo.setBorkerageScale(currentBrokeFee.getAmount().divide(currentTotalAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
					brokerageFeeDetailInfo.setReceivableAmount(currentBrokeFee);
					//佣金明细
					commissionInfoDetailInfo.setAppserialPeriod(String.valueOf(i+1));
					commissionInfoDetailInfo.setReceivableAmount(currentCommissionInfo);
					//当前年的经纪费
					mainBrokeFee = mainBrokeFee.add(currentBrokeFee);
					//当前年的佣金
					mainCommissionInfo = mainCommissionInfo.add(currentCommissionInfo);
				}
				billPayPlanYear.setInfo(infoList);
			}
			billPayPlanYear.setLength(infoList.size());
			recordList.add(billPayPlanYear);
			brokerageFeeDetails.add(brokerageFeeDetailInfo);
			commmissionInfoDetails.add(commissionInfoDetailInfo);
		}
		//主险的经纪费 + 附加险的经纪费(汇总)
		brokerageFeeInfo.setBrokerageAmount(mainBrokeFee);
		BigDecimal scale = (mainBrokeFee).getAmount().multiply(BigDecimal.valueOf(100)).divide(totalAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
		brokerageFeeInfo.setBrokerageScale(scale.doubleValue());
		BrokeFeeAndCommunityInfo.setBrokerageFeeInfo(brokerageFeeInfo);
		
		//主险的佣金+附加险的佣金(汇总)
		commissionInfoInfo.setCommissionAmount(mainCommissionInfo);
		BigDecimal commissionScale = (mainCommissionInfo).getAmount().multiply(BigDecimal.valueOf(100)).divide(totalAmount.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
		commissionInfoInfo.setCommissionScale(commissionScale.doubleValue());
		BrokeFeeAndCommunityInfo.setCommissionInfoInfo(commissionInfoInfo);
		
		BrokeFeeAndCommunityInfo.setBrokerageFeeDetails(brokerageFeeDetails);
		BrokeFeeAndCommunityInfo.setCommissionDetails(commmissionInfoDetails);
		showMsgInfo.setBrokeFeeAndCommunityInfo(BrokeFeeAndCommunityInfo);
		showMsgInfo.setBillPayPlanYear(recordList);
		return showMsgInfo;	
	}

	/**
	 * 附加险的计算
	 * @param age
	 * @param sex
	 * @param period
	 * @param payType
	 * @param attachProduct
	 * @param i
	 * @param j
	 * @param amount
	 * @param attachBrokeFee
	 * @return
	 */
	private CalcBrokeAndCommissionInfo attachAmountCalc(int age, String sex, Integer period, long payType,
			List<InsuranceContactLetterDetailInfo> attachProduct, int i, int j, Money amount,Money yiciBrokeFee,Money yiciCommissionInfoMoney,Money attachBrokeFee,Money attachCommissionInfo,Money currentTotalAttachAmount) {
		CalcBrokeAndCommissionInfo calcBrokeAndCommissionInfo = new CalcBrokeAndCommissionInfo();
		if (ListUtil.isNotEmpty(attachProduct)) {
			for (InsuranceContactLetterDetailInfo attachProductDetailInfo : attachProduct) {
				long productId = attachProductDetailInfo.getProductId();
				//附加险产品
				InsuranceProductInfo attachProductInfo = insuranceProductService.findById(productId);
				
				
				//如果附加险产品的缴费类型 和 主险的缴费类型一致，按主险的类，不一致则不分期，将数据新增到主险的第一期上面
				if (payType == attachProductInfo.getPayType()) {
					//附加险的基本保额
					Money attachUnitPrice = attachProductInfo.getUnitPrice();
					//附加险的缴费期限（不能大于主险的缴费期限，如果大于，将以主险的缴费期限为准）
					Integer attachPeriod = Integer.parseInt(attachProductDetailInfo.getPeriod());
					//附加险的保额
					Money insuranceAmount = attachProductDetailInfo.getInsuranceAmount();
					//附加险产品对应的费率表
					Map<String, String> attachQueryInsuranceProtocolCharge = extraDAO.queryInsuranceProtocolCharge(attachProductInfo.getProductId(), sex, attachPeriod);
					//附加险产品分期的比例
					String attachPeriodRate= attachProductInfo.getPeriodRate();
					//基础数据（年交保费）
					BigDecimal attachVal =insuranceAmount.getAmount().divide(attachUnitPrice.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
					//分期保费的计算
						String attachBaseData = attachQueryInsuranceProtocolCharge.get(String.valueOf(age+i));
						attachBaseData = StringUtils.isBlank(attachBaseData) ? "0" :attachBaseData;
						Money resultBasedata = new  Money(attachBaseData).multiply(attachVal).multiply(Double.valueOf(attachPeriodRate));
						currentTotalAttachAmount = currentTotalAttachAmount.add(resultBasedata);
						amount = amount.add(resultBasedata);
						calcBrokeAndCommissionInfo.setAmount(amount);
						calcBrokeAndCommissionInfo.setCurrentTotalAttachAmount(currentTotalAttachAmount);
						/**
						 * 分期经纪费的计算
						 */
						Double attachBrokeFeeScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(i+1));
						attachBrokeFee = attachBrokeFee.add(resultBasedata.multiply(BigDecimal.valueOf(Double.valueOf(attachBrokeFeeScale))));
						calcBrokeAndCommissionInfo.setAttachBrokeFee(attachBrokeFee);
						/**
						 * 附加险佣金的计算
						 */
						Double attachCommissionScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(j+1));
						attachCommissionInfo = attachCommissionInfo.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachCommissionScale))));
						calcBrokeAndCommissionInfo.setAttachCommissionInfo(attachCommissionInfo);
				} else {
					//一次性不分期
					if (i == 0 && j == 0) {
						//附加险的基本保额
						Money attachUnitPrice = attachProductInfo.getUnitPrice();
						//附加险的缴费期限（不能大于主险的缴费期限，如果大于，将以主险的缴费期限为准）
						Integer attachPeriod = Integer.parseInt(attachProductDetailInfo.getPeriod());
						//附加险的保额
						Money insuranceAmount = attachProductDetailInfo.getInsuranceAmount();
						//基础数据（年交保费）
						BigDecimal attachVal =insuranceAmount.getAmount().divide(attachUnitPrice.getAmount(),2,BigDecimal.ROUND_HALF_EVEN);
						attachPeriod = attachPeriod > period ?  period : attachPeriod;
						//附加险产品对应的费率表
						Map<String, String> attachQueryInsuranceProtocolCharge = extraDAO.queryInsuranceProtocolCharge(attachProductInfo.getProductId(), sex, attachPeriod);
						for (int  k= 0; k < attachPeriod; k++) {
							String attachBaseData = attachQueryInsuranceProtocolCharge.get(String.valueOf(age+k));
							attachBaseData = StringUtils.isBlank(attachBaseData) ? "0" :attachBaseData;
							Money resultBasedata = new  Money(attachBaseData).multiply(attachVal);
							currentTotalAttachAmount = currentTotalAttachAmount.add(resultBasedata);
							amount = amount.add(resultBasedata);
							calcBrokeAndCommissionInfo.setAmount(amount);
							/**
							 * 附加险经纪费计算
							 */
							Double attachBrokeFeeScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(k+1));
							yiciBrokeFee = yiciBrokeFee.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachBrokeFeeScale))));
							calcBrokeAndCommissionInfo.setYiciBrokeFee(yiciBrokeFee);
							/**
							 * 附加险佣金的计算
							 */
							Double attachCommissionScale = getScale(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),productId, attachProductInfo.getCompanyBaseUserId(), String.valueOf(attachProductInfo.getCatalogId()), String.valueOf(attachPeriod), String.valueOf(j+1));
							yiciCommissionInfoMoney = attachBrokeFee.add(new Money(attachBaseData).multiply(attachVal).multiply(BigDecimal.valueOf(Double.valueOf(attachCommissionScale))));
							calcBrokeAndCommissionInfo.setYiciCommissionInfoMoney(yiciCommissionInfoMoney);
						}
					} 
				}
			}
		}
		return calcBrokeAndCommissionInfo;
	}


	@Override
	public InsuranceBaseResult addBusinessBill(final BusinessBillOrder order) {
		
		return commonFormSaveProcess(order, "保单增加或修改信息", new BeforeProcessInvokeService() {
			
			
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"获取表单信息出错");
				}
				long id = order.getBusinessBillId();
				final Date now = InsuranceDomainHolder.get().getSysDate();
				if (id <= 0) {
					
					BusinessBillDO doObj = new BusinessBillDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					if (order.getAgencyCompanyId() > 0) {
						CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(String.valueOf(order.getAgencyCompanyId()));
						if (null == customerBaseInfoDO) {
							customerBaseInfoDO = customerBaseInfoDAO.findById(order.getAgencyCompanyId());
						} 
						doObj.setAgencyCompanyName(customerBaseInfoDO.getCustomerName());
					}
					doObj.setRawAddTime(now);
					doObj.setInsuranceOfType(InsuranceOfTypeEnum.OFFLINE.getCode());
					doObj.setBillStatus("0");
					doObj.setFormId(form.getFormId());
					doObj.setBillNo(genBillNo());
					businessBillDAO.insert(doObj);
					order.setFormId(form.getFormId());
				} else {
					//保单信息维护只修改共保再保信息以及缴费计划信息
					BusinessBillDO doObj = businessBillDAO.findById(order.getBusinessBillId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj);
					businessBillDAO.update(doObj);
				}
				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(id);
				return null;
			}
		}, null, null);
	}
	
	
	/**
	 * 业务单号
	 * @return
	 */
	private synchronized String genBillNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String billNo = "YWD";
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
	public QueryBaseBatchResult<BusinessBillFormInfo> queryFormList(BusinessBillQueryOrder queryOrder) {
	QueryBaseBatchResult<BusinessBillFormInfo> batchResult = new QueryBaseBatchResult<BusinessBillFormInfo>();
		
		try {
			queryOrder.check();
			List<BusinessBillFormInfo> pageList = new ArrayList<BusinessBillFormInfo>(
				(int) queryOrder.getPageSize());
			
			BusinessBillFormDO businessBillFormDO = new BusinessBillFormDO();
			BeanCopier.staticCopy(queryOrder, businessBillFormDO);
			long totalCount = extraDAO.findByConditionCount(businessBillFormDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BusinessBillFormDO> recordList = extraDAO.findByCondition(businessBillFormDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (BusinessBillFormDO item : recordList) {
				BusinessBillFormInfo info = new BusinessBillFormInfo();
				info.setFormCode(FormCodeEnum.getByCode(item.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				BeanCopier.staticCopy(item, info);
				//查询保险产品
				InsuranceContactLetterDO insuranceContactLetterDO = insuranceContactLetterDAO.findById(item.getLetterId());
				if (null != insuranceContactLetterDO) {
					if (insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode()) || 
							(insuranceContactLetterDO.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && insuranceContactLetterDO.getIsQuota().equals("NO"))) {
						List<InsuranceContactLetterDetailDO> insuranceContactLetterDetails = insuranceContactLetterDetailDAO.getInsuranceContactLetterDetails(item.getLetterId());
						StringBuffer sb = new StringBuffer();
						StringBuffer catalogId = new StringBuffer();
						StringBuffer catalogName = new StringBuffer();
						for (InsuranceContactLetterDetailDO insuranceContactLetterDetailDO : insuranceContactLetterDetails) {
							sb.append(insuranceContactLetterDetailDO.getProductName()).append(",");
							InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(insuranceContactLetterDetailDO.getProductId());
							if (null !=insuranceProductInfo ) {
								catalogId.append(String.valueOf(insuranceProductInfo.getCatalogId())).append(",");
								catalogName.append(insuranceProductInfo.getCatalogName()).append(",");
							}
						}
						String productStr = sb.toString();
						String catalogIdStr = catalogId.toString();
						String catalogNameStr = catalogName.toString();
						info.setInsuranceProduct(productStr.substring(0,productStr.length() - 1));
						info.setCatalogId(catalogIdStr.substring(0,catalogIdStr.length()-1));
						info.setCatalogName(catalogNameStr.substring(0,catalogNameStr.length()-1));	
					} else {
						long productId = insuranceContactLetterDO.getProductId();
						if (productId > 0) {
							InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
							if (null != insuranceProductInfo) {
								info.setCatalogId(String.valueOf(insuranceProductInfo.getCatalogId()));
								info.setCatalogName(insuranceProductInfo.getCatalogName());
								info.setInsuranceProduct(insuranceContactLetterDO.getProductName());
							}
						}
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
	public BusinessBillInfo findByFormId(long formId) {
		BusinessBillInfo info = new BusinessBillInfo();
		BusinessBillDO businessBillDO = businessBillDAO.findByFormId(formId);
		if (null != businessBillDO) {
			BeanCopier.staticCopy(businessBillDO, info);
			return info;
		}
		return null;
	}


	@Override
	public BusinessBillFormInfo findBusinessBillByNo(String insuranceNo) {
		BusinessBillFormInfo info = new BusinessBillFormInfo();
		BusinessBillFormDO businessBillFormDO = extraDAO.findBusinessBillByNo(insuranceNo);
		if (null != businessBillFormDO) {
			BeanCopier.staticCopy(businessBillFormDO, info);
			return info;
		}
		return null;
	}


	@Override
	public int updateBusinessBillStatus(BusinessBillInfo businessBillInfo) {
		if (null != businessBillInfo) {
			BusinessBillDO businessBillDO = businessBillDAO.findById(businessBillInfo.getBusinessBillId());
			businessBillDO.setBillStatus(BillSettlementStatusEnum.ON.getCode());
			businessBillDAO.update(businessBillDO);
		}
		return 0;
	}

	@Override
	public InsuranceBaseResult deleteBusinessBill(BusinessBillOrder order) {
		InsuranceBaseResult batchResult = new InsuranceBaseResult();
		try {
			businessBillDAO.deleteById(order.getBusinessBillId());
			batchResult.setSuccess(true);
			batchResult.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (DataAccessException e) {
			batchResult.setSuccess(true);
			batchResult.setMessage(InsuranceResultEnum.EXECUTE_FAIL.getMessage());
			e.printStackTrace();
		}
		return batchResult;
	}
}