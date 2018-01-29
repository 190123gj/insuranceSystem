package com.born.insurance.biz.service.insuranceContactLetter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.dal.daointerface.BusinessBillBeneficiaryDAO;
import com.born.insurance.dal.daointerface.BusinessBillCustomerDAO;
import com.born.insurance.dal.daointerface.CustomerCertInfoDAO;
import com.born.insurance.dal.daointerface.CustomerContactWayDAO;
import com.born.insurance.dal.daointerface.InsuranceContactCertDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceProductLevelDAO;
import com.born.insurance.dal.daointerface.ValueTaxInfoDAO;
import com.born.insurance.dal.dataobject.BusinessBillBeneficiaryDO;
import com.born.insurance.dal.dataobject.BusinessBillCustomerDO;
import com.born.insurance.dal.dataobject.CustomerCertInfoDO;
import com.born.insurance.dal.dataobject.CustomerContactWayDO;
import com.born.insurance.dal.dataobject.InsuranceContactCertDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDetailDO;
import com.born.insurance.dal.dataobject.InsuranceProductLevelDO;
import com.born.insurance.dal.dataobject.ValueTaxInfoDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.InsuranceContactLetterExtraDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BiddingTypeEnum;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.ContactTypeEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.bpm.Org;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterFormInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.order.businessBillBeneficiary.BusinessBillBeneficiaryOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactCertOrder;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactLetterOrder;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactLetterQueryOrder;
import com.born.insurance.ws.order.insuranceContactLetterDetail.InsuranceContactLetterDetailOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceContactLetter.InsuranceContactLetterService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceContactLetterService")
public class InsuranceContactLetterServiceImpl extends BaseFormAutowiredDomainService implements
																					InsuranceContactLetterService {
	@Autowired
	protected InsuranceContactLetterDAO insuranceContactLetterDAO;
	
	@Autowired
	protected InsuranceContactLetterDetailDAO insuranceContactLetterDetailDAO;
	
	@Autowired
	protected ValueTaxInfoDAO valueTaxInfoDAO;
	
	@Autowired
	protected BusinessBillCustomerDAO businessBillCustomerDAO;
	
	@Autowired
	protected InsuranceContactCertDAO insuranceContactCertDAO;
	
	@Autowired
	protected BusinessBillBeneficiaryDAO businessBillBeneficiaryDAO;

	@Autowired
	protected InsuranceProductLevelDAO insuranceProductLevelDAO;
	
	@Autowired
	protected CustomerContactWayDAO customerContactWayDAO;
	
	@Autowired
	protected CustomerCertInfoDAO customerCertInfoDAO;
	
	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Override
	public FormBaseResult save(final InsuranceContactLetterOrder order) {
		return commonFormSaveProcess(order, "增加或修改投保申请信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"获取表单信息出错");
				}
				
				long id = order.getLetterId();
				if (id <= 0) {
					InsuranceContactLetterDO doObj = new InsuranceContactLetterDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setRawAddTime(now);
					doObj.setPremiumAmount(order.getPremiumAmount());
					doObj.setType(StringUtils.isBlank(order.getPriceContactName()) ? BiddingTypeEnum.DIRECT_BIDDING.getCode() :BiddingTypeEnum.ENQUIRY_BIDDING.getCode());
					doObj.setBillNo(genBillNo());
					doObj.setStatus("NO");
					doObj.setCreatorId(order.getCreatorId());
					doObj.setCreatorName(order.getCreatorName());
					doObj.setProductId(order.getProductId());
					doObj.setProductLevelId(order.getProductLevelId());
					//获取客户的手机号码
					CustomerContactWayDO wayDO = new CustomerContactWayDO();
					wayDO.setUserId(order.getCustomerUserId());
					wayDO.setType(ContactTypeEnum.MOBILE.getCode());
					List<CustomerContactWayDO> contactWayDOs = customerContactWayDAO.findByCondition(wayDO,null, null, 0, 0);
					if (ListUtil.isNotEmpty(contactWayDOs)) {
						StringBuffer sb = new StringBuffer();
						for (CustomerContactWayDO customerContactWayDO : contactWayDOs) {
							sb.append(customerContactWayDO.getWay()).append(",");
						}
						String phones = sb.toString();
						doObj.setCustomerUserPhone(phones.substring(0,phones.length()-1));
					}
					//当前人员是否为业务员，如果是，取当前人员id，如果不是，获取页面的上的业务员信息
					if (StringUtils.isBlank(order.getBusinessUserName())) {
						doObj.setBusinessUserId(order.getUserId());
						doObj.setBusinessUserName(order.getUserName());
						doObj.setDepartment(order.getDeptName());
					} else {
						UserDetailInfo userDetailInfo = bpmUserQueryService.findUserDetailByUserId(order.getBusinessUserId());
						if (null != userDetailInfo) {
							List<Org> deptList = userDetailInfo.getDeptList();
							if (ListUtil.isNotEmpty(deptList)) {
								doObj.setDepartment(deptList.get(0).getName());
							}
						}
					}
					Money totalAmount = new Money(0,0);
					//投保内容
					if (null != order.getInsuranceContactLetterDetailOrders() && order.getInsuranceContactLetterDetailOrders().size()>0) {
						for (InsuranceContactLetterDetailOrder insuranceContactLetterOrder : order.getInsuranceContactLetterDetailOrders()) {
							totalAmount = totalAmount.add(insuranceContactLetterOrder.getPremiumAmount());
						}
						doObj.setPremiumAmount(totalAmount);
					}
					insuranceContactLetterDAO.insert(doObj);
					id = doObj.getLetterId();
					List<ValueTaxInfoDO> list = valueTaxInfoDAO.findByCustomerUserId(order.getCustomerUserId());
					if (null != list && list.size() ==1) {
						ValueTaxInfoDO valueTaxInfoDO = list.get(0);
						//更新
						if (order.isCover()) {
							valueTaxInfoDO.setOrgName(order.getOrgName());
							valueTaxInfoDO.setIdentifyNumber(order.getIdentifyNumber());
							valueTaxInfoDO.setMobile(order.getContactMobile());
							valueTaxInfoDO.setBankCardNo(order.getBankCardNo());
							valueTaxInfoDO.setOpenBankName(order.getOpenBankName());
							valueTaxInfoDO.setBankCardNo(order.getBankCardNo());
							valueTaxInfoDAO.update(valueTaxInfoDO);
						}
					} else{
							//新增
							ValueTaxInfoDO valueTaxInfoDO = new ValueTaxInfoDO();
							valueTaxInfoDO.setCustomerUserId(order.getCustomerUserId());
							valueTaxInfoDO.setCustomerUserName(order.getCustomerUserName());
							valueTaxInfoDO.setOrgName(order.getOrgName());
							valueTaxInfoDO.setIdentifyNumber(order.getIdentifyNumber());
							valueTaxInfoDO.setMobile(order.getContactMobile());
							valueTaxInfoDO.setBankCardNo(order.getBankCardNo());
							valueTaxInfoDO.setOpenBankName(order.getOpenBankName());
							valueTaxInfoDO.setBankCardNo(order.getBankCardNo());
							valueTaxInfoDAO.insert(valueTaxInfoDO);
					}
					
					//需向保险公司提交的用户信息
					if (null != order.getCertOrders() && order.getCertOrders().size() > 0) {
						for (InsuranceContactCertOrder insuranceContactCertOrder : order.getCertOrders() ) {
							if (insuranceContactCertOrder.getSubmitVal().equals("1")) {
								InsuranceContactCertDO insuranceContactCertDO = new InsuranceContactCertDO();
								BeanCopier.staticCopy(insuranceContactCertOrder, insuranceContactCertDO);
								insuranceContactCertDO.setLetterId(doObj.getLetterId());
								insuranceContactCertDAO.insert(insuranceContactCertDO);
							}
						}
					}
					
					//投保内容
					if (null != order.getInsuranceContactLetterDetailOrders() && order.getInsuranceContactLetterDetailOrders().size()>0) {
						for (InsuranceContactLetterDetailOrder insuranceContactLetterOrder : order.getInsuranceContactLetterDetailOrders()) {
							InsuranceContactLetterDetailDO insuranceContactLetterDetailDO = new InsuranceContactLetterDetailDO();
							BeanCopier.staticCopy(insuranceContactLetterOrder, insuranceContactLetterDetailDO);
							insuranceContactLetterDetailDO.setLetterId(doObj.getLetterId());
							insuranceContactLetterDetailDAO.insert(insuranceContactLetterDetailDO);
						}
					}
					
					//保险人与被保险人之前的关系
					if (null !=order.getBusinessBillCustomerOrder() && order.getBusinessBillCustomerOrder().size()>0) {
						for (BusinessBillCustomerOrder businessBillCustomerOrder : order.getBusinessBillCustomerOrder()) {
							BusinessBillCustomerDO businessBillCustomerDO = new BusinessBillCustomerDO();
							BeanCopier.staticCopy(businessBillCustomerOrder, businessBillCustomerDO);
							businessBillCustomerDO.setBillNo(doObj.getBillNo());
							businessBillCustomerDAO.insert(businessBillCustomerDO);
						}
					}
					
					
					//受益方案
					if (null != order.getBusinessBillBeneficiaryOrder() && order.getBusinessBillBeneficiaryOrder().size()> 0 ) {
						for (BusinessBillBeneficiaryOrder businessBillBeneficiaryOrder : order.getBusinessBillBeneficiaryOrder()) {
							BusinessBillBeneficiaryDO  businessBillBeneficiaryDO = new BusinessBillBeneficiaryDO();
							BeanCopier.staticCopy(businessBillBeneficiaryOrder, businessBillBeneficiaryDO);
							businessBillBeneficiaryDO.setLetterId(doObj.getLetterId());
							businessBillBeneficiaryDAO.insert(businessBillBeneficiaryDO);
						}
					}
					
				} else {
					InsuranceContactLetterDO doObj = insuranceContactLetterDAO.findById(order
						.getLetterId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setPremiumAmount(order.getPremiumAmount());
					insuranceContactLetterDAO.update(doObj);
					insuranceContactLetterDetailDAO.deleteByLetterId(doObj.getLetterId());
					//投保内容
					if (null != order.getInsuranceContactLetterDetailOrders() && order.getInsuranceContactLetterDetailOrders().size()>0) {
						for (InsuranceContactLetterDetailOrder insuranceContactLetterOrder : order.getInsuranceContactLetterDetailOrders()) {
							InsuranceContactLetterDetailDO insuranceContactLetterDetailDO = new InsuranceContactLetterDetailDO();
							BeanCopier.staticCopy(insuranceContactLetterOrder, insuranceContactLetterDetailDO);
							insuranceContactLetterDetailDO.setLetterId(doObj.getLetterId());
							insuranceContactLetterDetailDAO.insert(insuranceContactLetterDetailDO);
						}
					}
					businessBillBeneficiaryDAO.deleteByLetterId(doObj.getLetterId());
					//受益方案
					if (null != order.getBusinessBillBeneficiaryOrder() && order.getBusinessBillBeneficiaryOrder().size()> 0 ) {
						for (BusinessBillBeneficiaryOrder businessBillBeneficiaryOrder : order.getBusinessBillBeneficiaryOrder()) {
							BusinessBillBeneficiaryDO  businessBillBeneficiaryDO = new BusinessBillBeneficiaryDO();
							BeanCopier.staticCopy(businessBillBeneficiaryOrder, businessBillBeneficiaryDO);
							businessBillBeneficiaryDO.setLetterId(doObj.getLetterId());
							businessBillBeneficiaryDAO.insert(businessBillBeneficiaryDO);
						}
					}
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
	public InsuranceContactLetterInfo findById(long id) {
		InsuranceContactLetterDO insuranceContactLetterDo = insuranceContactLetterDAO.findById(id);
		if (insuranceContactLetterDo != null) {
			InsuranceContactLetterInfo insuranceContactLetterInfo = new InsuranceContactLetterInfo();
			BeanCopier.staticCopy(insuranceContactLetterDo, insuranceContactLetterInfo);
			InsuranceProductLevelDO insuranceProductLevelDO = insuranceProductLevelDAO.findById(insuranceContactLetterInfo.getProductLevelId());
			if (null != insuranceProductLevelDO) {
				insuranceContactLetterInfo.setProductLevelLevel(insuranceProductLevelDO.getLevel());
			}
			List<ValueTaxInfoDO> valueTaxInfoDO = valueTaxInfoDAO.findByCustomerUserId(insuranceContactLetterDo.getCustomerUserId());
			if (valueTaxInfoDO.size() > 0 ) {
				ValueTaxInfo info = new ValueTaxInfo();
				ValueTaxInfoDO valueTaxInfoDO2 = valueTaxInfoDO.get(0);
				BeanCopier.staticCopy(valueTaxInfoDO2, info);
				insuranceContactLetterInfo.setValueTaxInfo(info);
			}
			return insuranceContactLetterInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceContactLetterFormInfo> queryList(	InsuranceContactLetterQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceContactLetterFormInfo> batchResult = new QueryBaseBatchResult<InsuranceContactLetterFormInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceContactLetterFormInfo> pageList = new ArrayList<InsuranceContactLetterFormInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceContactLetterExtraDO insuranceContactLetterExtraDO = new InsuranceContactLetterExtraDO();
			BeanCopier.staticCopy(queryOrder, insuranceContactLetterExtraDO);
			long totalCount = extraDAO
				.findInsuranceContactLetterByConditionCount(insuranceContactLetterExtraDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceContactLetterExtraDO> recordList = extraDAO.findInsuranceContactLetterByCondition(
					insuranceContactLetterExtraDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (InsuranceContactLetterExtraDO item : recordList) {
				InsuranceContactLetterFormInfo info = new InsuranceContactLetterFormInfo();
				BeanCopier.staticCopy(item, info);
				info.setFormCode(FormCodeEnum.getByCode(item.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				if (item.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode())) {
					info.setLifeInsuranceType("lifeInsurance");
				} else if (item.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && !StringUtils.isBlank(item.getIsQuota()) && item.getIsQuota().equals("YES")) {
					info.setLifeInsuranceType("notLifeInsuranceQuota");
				} else if (item.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && !StringUtils.isBlank(item.getIsQuota()) && item.getIsQuota().equals("NO")) {
					info.setLifeInsuranceType("notLifeInsurance");
				}
				//查询投保产品信息
				List<InsuranceContactLetterDetailDO> letterDetails = insuranceContactLetterDetailDAO.getInsuranceContactLetterDetails(item.getLetterId());
				StringBuffer sb = new StringBuffer();
				for (InsuranceContactLetterDetailDO insuranceContactLetterDetailDO : letterDetails) {
					sb.append(insuranceContactLetterDetailDO.getProductName()).append(",");
				}
				String str = sb.toString();
				info.setInsuranceProduct(letterDetails.size() == 0? item.getProductName(): str.substring(0,str.length() -1));
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
	public List<InsuranceContactLetterDetailInfo> getInsuranceContactLetterDetails(long letterId) {
		List<InsuranceContactLetterDetailInfo>  pageList = new ArrayList<InsuranceContactLetterDetailInfo>() ;
		List<InsuranceContactLetterDetailDO> list = null ;
		
		try {
			list = insuranceContactLetterDetailDAO.getInsuranceContactLetterDetails(letterId);
			for (InsuranceContactLetterDetailDO insuranceContactLetterDetailDO : list) {
				InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo =  new InsuranceContactLetterDetailInfo();
				BeanCopier.staticCopy(insuranceContactLetterDetailDO, insuranceContactLetterDetailInfo);
				pageList.add(insuranceContactLetterDetailInfo);
			}
		} catch (Exception e) {
			logger.error("投保内容查询：", e);
		}
		return pageList;
	}

	@Override
	public InsuranceContactLetterInfo findByFormId(long formId) {
		InsuranceContactLetterDO InsuranceContactLetterDO = insuranceContactLetterDAO.findByFormId(formId);
		if (null != InsuranceContactLetterDO) {
			InsuranceContactLetterInfo insuranceContactLetterInfo = new InsuranceContactLetterInfo();
			BeanCopier.staticCopy(InsuranceContactLetterDO, insuranceContactLetterInfo);
			InsuranceProductLevelDO insuranceProductLevelDO = insuranceProductLevelDAO.findById(insuranceContactLetterInfo.getProductLevelId());
			if (null != insuranceProductLevelDO) {
				insuranceContactLetterInfo.setProductLevelLevel(insuranceProductLevelDO.getLevel());
			}
			return insuranceContactLetterInfo;
		}
		return null;
	}



	@Override
	public InsuranceProductLevelInfo findProductLevelInfo(long productLevelId) {
		InsuranceProductLevelInfo info = new InsuranceProductLevelInfo() ;
		InsuranceProductLevelDO levelDo = null ;
		try {
			levelDo = insuranceProductLevelDAO.findById(productLevelId);
			if (null != levelDo) {
				BeanCopier.staticCopy(levelDo, info);
			}
		} catch (Exception e) {
		}
		return info;
	}



	@Override
	public List<CustomerCertInfo> findCustomerCertList(long customerUserId) {
		List<CustomerCertInfo> pageList = new ArrayList<CustomerCertInfo>();
		CustomerCertInfoDO customerCertInfo = new CustomerCertInfoDO();
		customerCertInfo.setUserId(customerUserId);
		List<CustomerCertInfoDO> list = customerCertInfoDAO.findByCondition(customerCertInfo, null, null, 0, 100);
		for (CustomerCertInfoDO customerCertInfoDO : list) {
			CustomerCertInfo info = new CustomerCertInfo();
			BeanCopier.staticCopy(customerCertInfoDO, info);
			info.setCertType(CertTypeEnum.getByCode(customerCertInfoDO.getCertType()));
			info.setCertTypeName(CertTypeEnum.getMsgByCode(customerCertInfoDO.getCertType()));
			pageList.add(info);
		}
		return pageList;
	}
}