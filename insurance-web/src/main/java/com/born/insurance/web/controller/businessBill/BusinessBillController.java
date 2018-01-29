package com.born.insurance.web.controller.businessBill;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.token.TokenProccessor;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.web.util.ExcelUtil;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.InsuranceOfTypeEnum;
import com.born.insurance.ws.enums.InsuranceProtocolTypeEnum;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.PaymentTypeEnum;
import com.born.insurance.ws.enums.ProjectStatusEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.bpm.Role;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.info.businessBill.BillPayPlanYear;
import com.born.insurance.ws.info.businessBill.BrokeFeeAndCommunityInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillBeneficiaryInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillFormInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.businessBill.ShowMsgInfo;
import com.born.insurance.ws.info.businessBillCoinsInfo.BusinessBillCoinsInfoInfo;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.info.businessBillReinsInfo.BusinessBillReinsInfoInfo;
import com.born.insurance.ws.info.businessBillUnderInfor.BusinessBillUnderInforInfo;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.insuranceBillRenewal.InsuranceBillRenewalInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.info.insuranceProtocolCharge.InsuranceProtocolChargeInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillQueryOrder;
import com.born.insurance.ws.order.common.SimpleFormOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import com.born.insurance.ws.service.brokerageFee.BrokerageFeeService;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.businessBillCoinsInfo.BusinessBillCoinsInfoService;
import com.born.insurance.ws.service.businessBillCustomer.BusinessBillCustomerService;
import com.born.insurance.ws.service.businessBillPayPlan.BusinessBillPayPlanService;
import com.born.insurance.ws.service.businessBillReinsInfo.BusinessBillReinsInfoService;
import com.born.insurance.ws.service.businessBillUnderInfor.BusinessBillUnderInforService;
import com.born.insurance.ws.service.commissionInfo.CommissionInfoService;
import com.born.insurance.ws.service.insuranceBillRenewal.InsuranceBillRenewalService;
import com.born.insurance.ws.service.insuranceContactLetter.InsuranceContactLetterService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.born.insurance.ws.service.insuranceProtocolCharge.InsuranceProtocolChargeService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/businessBill")
public class BusinessBillController extends WorkflowBaseController {

	@Autowired
	private BaseDataInfoService baseDataInfoService;

	@Autowired
	protected BusinessBillService businessBillService;

	@Autowired
	protected BusinessBillCustomerService businessBillCustomerService;

	@Autowired
	protected BusinessBillCoinsInfoService businessBillCoinsInfoService;

	@Autowired
	protected BusinessBillReinsInfoService businessBillReinsInfoService;

	@Autowired
	protected BusinessBillPayPlanService businessBillPayPlanService;
	
	@Autowired
	protected BusinessBillUnderInforService  businessBillUnderInforService;
	
	@Autowired
	protected InsuranceContactLetterService insuranceContactLetterService;
	
	@Autowired
	protected CustomerPersonService customerPersonService;
	
	@Autowired
	protected CustomerCompanyService customerCompanyService;
	
	@Autowired
	protected InsuranceBillRenewalService insuranceBillRenewalService;
	
	@Autowired
	protected BrokerageFeeService brokerageFeeService;
	
	@Autowired
	protected CommissionInfoService commissionInfoService;
	
	@Autowired
	protected ProjectSetupService projectSetupService;
	
	@Autowired
	protected InsuranceProductService insuranceProductService;
	
	@Autowired
	protected InsuranceProtocolChargeService insuranceProtocolChargeService;

	private final static String VM_PATH = "/insurance/businessBill/";

	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "insuranceDate", "planPayDate", "actualPayDate" };
	}
	@Override
	protected String[] getDateInput2NameArray() {
		return new String[] {"beginDate", "endDate"};
	}

	/**
	 * 保单信息管理
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			boolean isRightOperate = false ;
			BusinessBillQueryOrder queryOrder = new BusinessBillQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (null != sessionLocal) {
				UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
				if (null != userDetailInfo) {
					List<Role> roleList = userDetailInfo.getRoleList();
					for (int i = 0; i < roleList.size(); i++) {
						if (roleList.get(i).getCode().equals("BusinessSys_nq") ) {
							isRightOperate = true ;
						}
					}
				}
			}
			queryOrder.setSortCol("b.raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<BusinessBillFormInfo> batchResult = businessBillService.queryFormList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("status", ProjectStatusEnum.getAllEnum());
			model.addAttribute("isRightOperate", isRightOperate);
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listBusinessBill.vm";
	}

	private List<BaseDataInfoInfo> getBaseDataInfoInfos(BaseDataInfoTypeEnum typeEnum) {
		BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
		queryOrder.setPageSize(99);
		queryOrder.setType(typeEnum);
		return baseDataInfoService.queryList(queryOrder).getPageList();
	}

	/**
	 * 编辑新增维护保单信息
	 * 
	 * @param businessBillId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(
			@RequestParam(value = "businessBillId", required = false, defaultValue = "0") long businessBillId,@RequestParam(value = "letterId", required = false, defaultValue = "0") long letterId,String type,@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
			HttpServletRequest request, Model model) {
		BusinessBillInfo info = null;
		InsuranceContactLetterInfo infos = null;
		//保单信息维护
		if (businessBillId > 0) {
			info = businessBillService.findById(businessBillId);
			if (null != info) {
				// 投保人 和 被保险人人
				List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(info.getBillNo());
				if (null != billCustomerInfos && billCustomerInfos.size()==2) {
					BusinessBillCustomerInfo businessBillCustomerInfo1= billCustomerInfos.get(0);
					BusinessBillCustomerInfo businessBillCustomerInfo2= billCustomerInfos.get(1);
					//投保人
					if (businessBillCustomerInfo1.getType().equals("0")) {
						model.addAttribute("policyHolder", businessBillCustomerInfo1);
						model.addAttribute("insuredPerson", businessBillCustomerInfo2);
					} else {
						model.addAttribute("policyHolder", businessBillCustomerInfo2);
						model.addAttribute("insuredPerson", businessBillCustomerInfo1);
					}
				}
				InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findById(info.getLetterId());
				if (null != insuranceContactLetterInfo) {
					long projectSetupId = insuranceContactLetterInfo.getProjectSetupId();
					insuranceContactLetterInfo.setCustomerCertType(CertTypeEnum.getMsgByCode(insuranceContactLetterInfo.getCustomerCertType()));
					ProjectSetupInfo projectSetupInfo = projectSetupService.findById(projectSetupId);
					if (null != projectSetupInfo) {
						insuranceContactLetterInfo.setProportionType(projectSetupInfo.getProportionType());
						insuranceContactLetterInfo.setProportion(projectSetupInfo.getProportion());
						//查询该超权限对应的渠道信息
						info.setAgencyCompanyId(projectSetupInfo.getChannelsUserId());
						info.setAgencyCompanyName(projectSetupInfo.getChannelsUserName());
						
					}
				}
				//受益人信息
				List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(insuranceContactLetterInfo.getLetterId());
				if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
					for (BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo : findBusinessBillBeneficiarys) {
						businessBillBeneficiaryInfo.setBeneficiaryCertType(CertTypeEnum.getMsgByCode(businessBillBeneficiaryInfo.getBeneficiaryCertType()));
					}
					BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
					if (businessBillBeneficiaryInfo.getType().equals("0")) {
						model.addAttribute("plan_order", 0);
					} else {
						model.addAttribute("plan_order", 1);
					}
				}
				model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
				//投保内容查看
				List<InsuranceContactLetterDetailInfo> contactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(info.getLetterId());
				Money totalMoney = new Money(0,0);
				//获取保费
				if (ListUtil.isNotEmpty(contactLetterDetails)) {
					for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : contactLetterDetails) {
						totalMoney = totalMoney.add(insuranceContactLetterDetailInfo.getPremiumAmount());
					}
				}
				model.addAttribute("amount", totalMoney);
				model.addAttribute("contactLetterDetails", contactLetterDetails);
				if (insuranceContactLetterInfo.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode())) {
					//缴费计划查询
					List<BillPayPlanYear>  billPayPlanInfos = businessBillPayPlanService.findAllBusinessBillPayPlan(businessBillId);
					model.addAttribute("planList", billPayPlanInfos);
				} else {
					List<BusinessBillPayPlanInfo> billPayPlanInfos = businessBillPayPlanService.findAllNormalBusinessBillPayPlan(businessBillId);
					model.addAttribute("billPayPlanInfos", billPayPlanInfos);
				}
			    //共保信息
			    List<BusinessBillCoinsInfoInfo> billCoins = businessBillCoinsInfoService.findBusinessBillCoinsInfo(businessBillId);
			    model.addAttribute("billCoins", billCoins);
			    //再保信息
				 List<BusinessBillReinsInfoInfo> billReins = businessBillReinsInfoService.findBusinessBillReinsInfo(businessBillId);
				model.addAttribute("billReins", billReins);
				model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
				model.addAttribute("insuranceContactLetterInfo", insuranceContactLetterInfo);
				model.addAttribute("info", info);
				//经纪费信息 和 佣金信息
				BrokerageFeeInfo brokerageFeeInfo = brokerageFeeService.findBrokerageFeeByBusinessBillId(businessBillId);
				if (null != brokerageFeeInfo) {
					//经纪费信息明细 和  佣金明细查询
					List<BrokerageFeeDetailInfo> brokerageFeeDetails = brokerageFeeService.findBrokerageFeeDetails(brokerageFeeInfo.getBrokerageId());
					model.addAttribute("brokerageFeeDetails", brokerageFeeDetails);
				}
				CommissionInfoInfo commissionInfoInfo = commissionInfoService.findCommissionInfoByBusinessBillId(businessBillId);
				if (null != commissionInfoInfo) {
				    List<CommissionInfoDetailInfo> commissionInfoDetails = commissionInfoService.findCommissionInfoDetails(commissionInfoInfo.getCommissionInfoId());
				    model.addAttribute("commissionInfoDetails", commissionInfoDetails);
				}
				model.addAttribute("brokerageFeeInfo", brokerageFeeInfo);
				model.addAttribute("commissionInfoInfo", commissionInfoInfo);
				queryCommonAttachmentData(model, businessBillId + "",CommonAttachmentTypeEnum.LIFEINSURANCE_BILL_POLICY_RECEIPT);
				queryCommonAttachmentData(model, businessBillId + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_QUATEBILL_POLICY_RECEIPT);
				queryCommonAttachmentData(model, businessBillId + "",CommonAttachmentTypeEnum.LIFEINSURANCE_BILL_POLICY_RECEIPT);
				//寿险投保确认
				if (insuranceContactLetterInfo.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode())) {
					return VM_PATH + "addLifeInsuranceBusinessBill.vm";
					//非寿险非定额投保确认
				} else if (insuranceContactLetterInfo.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && insuranceContactLetterInfo.getIsQuota().equals("NO")) {
					return VM_PATH + "addNotLifeInsuranceBusinessBill.vm";
					//非寿险定额投保确认
				} else if (insuranceContactLetterInfo.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && insuranceContactLetterInfo.getIsQuota().equals("YES")) {
					return VM_PATH + "addNotLifeInsuranceQuotaBusinessBill.vm";
				}
			}
		} else {
			
			//续保中的暂存按钮
			if (formId > 0) {
				infos = insuranceContactLetterService.findByFormId(formId);
				if (null != infos) {
					//查询产品的档次信息
					long productLevelId = infos.getProductLevelId();
					long customerUserId = infos.getCustomerUserId();
					if (productLevelId > 0  && infos.getProductId() > 0) {
						InsuranceProductLevelInfo insuranceProductLevelInfo = insuranceContactLetterService.findProductLevelInfo(productLevelId);
						if (null != insuranceProductLevelInfo) {
							infos.setProductLevelLevel(insuranceProductLevelInfo.getLevel());
							infos.setPremiumAmount(insuranceProductLevelInfo.getPremiumAmount());
						}
					}
					List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(infos.getLetterId());
					List<CustomerCertInfo> findCustomerCertList = insuranceContactLetterService.findCustomerCertList(customerUserId);
					//客户的证件信息
					model.addAttribute("findCustomerCertList", findCustomerCertList);
					//投保内容
					model.addAttribute("insuranceContactLetterDetails", insuranceContactLetterDetails);
					// 投保人 和 被保险人人 
					//TODO 传入业务单号
					List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(infos.getBillNo());
					if (null != billCustomerInfos && billCustomerInfos.size()==2) {
						BusinessBillCustomerInfo businessBillCustomerInfo1= billCustomerInfos.get(0);
						BusinessBillCustomerInfo businessBillCustomerInfo2= billCustomerInfos.get(1);
						BusinessBillCustomerInfo policyHolder = businessBillCustomerInfo1.getType().equals("0")?businessBillCustomerInfo1:businessBillCustomerInfo2;
						BusinessBillCustomerInfo insuredPerson = businessBillCustomerInfo1.getType().equals("0")?businessBillCustomerInfo2:businessBillCustomerInfo1;
						model.addAttribute("policyHolder", policyHolder);
						model.addAttribute("insuredPerson", insuredPerson);
						List<CustomerCertInfo> customerCertInfo = insuranceProductService.getCustomerCertInfo(insuredPerson.getCustomerUserId());
						if (ListUtil.isNotEmpty(customerCertInfo)) {
							for (CustomerCertInfo customerCertInfo2 : customerCertInfo) {
								if (customerCertInfo2.getCertType().getCode().equals(CertTypeEnum.IDENTITY_CARD.getCode())) {
									String certNo = customerCertInfo2.getCertNo();
									Integer lastCap =Integer.parseInt(certNo.substring(certNo.length()-1));
									String sex = lastCap % 2 == 0?"woman":"man";
									model.addAttribute("certNo",certNo );
									model.addAttribute("sex",sex );
									for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
										long productId = insuranceContactLetterDetailInfo.getProductId();
										InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
										List<InsuranceProtocolChargeInfo> insuranceProductCharge = insuranceProtocolChargeService.getInsuranceProductCharge(String.valueOf(insuranceContactLetterDetailInfo.getProductId()), sex);
										insuranceContactLetterDetailInfo.setInsuranceProductCharges(insuranceProductCharge);
										InsuranceProtocolChargeInfo insuranceProductChargeRate = insuranceProtocolChargeService.getInsuranceProductChargeRate(String.valueOf(insuranceContactLetterDetailInfo.getProductId()), sex, certNo, insuranceContactLetterDetailInfo.getPeriod());
										if (null != insuranceProductChargeRate) {
											insuranceContactLetterDetailInfo.setPeriodRate(insuranceProductChargeRate.getVal());
										}
										if (null != insuranceProductInfo) {
											insuranceContactLetterDetailInfo.setPayType(PaymentTypeEnum.getMsgByCode(String.valueOf(insuranceProductInfo.getPayType())));
										}
									}
									break;
								}
							}
						}
					} else {
						for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
							long productId = insuranceContactLetterDetailInfo.getProductId();
							InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
							List<InsuranceProtocolChargeInfo> insuranceProductCharge = insuranceProtocolChargeService.getInsuranceProductChargeInfo(insuranceProductInfo.getCompanyUserId(), insuranceProductInfo.getCatalogId(), String.valueOf(productId));
							insuranceContactLetterDetailInfo.setInsuranceProductCharges(insuranceProductCharge);
							
						}
					}
					//受益顺序
					List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(infos.getLetterId());
					if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
						BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
						if (businessBillBeneficiaryInfo.getType().equals("0")) {
							model.addAttribute("plan_order", 0);
						} else {
							model.addAttribute("plan_order", 1);
						}
					}
					model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
					//查询保险非寿险定额产品对应的档次信息
					List<InsuranceProductLevelInfo> productLevels = insuranceProductService.getInsuranceProductLevel(infos.getProductId());
					model.addAttribute("productLevels", productLevels);
				}
				queryCommonAttachmentData(model, infos.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_DATA);
				queryCommonAttachmentData(model, infos.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_OTHER_DATA);
				queryCommonAttachmentData(model, infos.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_DATA);
				queryCommonAttachmentData(model, infos.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_OTHER_DATA);
				queryCommonAttachmentData(model, infos.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_DATA);
				queryCommonAttachmentData(model, infos.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_OTHER_DATA);
				model.addAttribute("info", infos);
				model.addAttribute("infoJson", JSONObject.toJSONString(info));
				model.addAttribute("type", type);
				model.addAttribute("certType", CertTypeEnum.getAllEnum());
				model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
				
				//寿险续保
				if (infos.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode())) {
					return VM_PATH + "renewalLifeInsuranceBusinessBill.vm";
					//非寿险非定额续保
				} else if (infos.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && infos.getIsQuota().equals("NO")) {
					return VM_PATH + "renewalNotLifeInsuranceBusinessBill.vm";
					//非寿险定额续保
				} else if (infos.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && infos.getIsQuota().equals("YES")) {
					return VM_PATH + "renewalNotLifeInsuranceQuotaBusinessBill.vm";
				}
			}
			//投保确认
			info = new BusinessBillInfo();
			//中介渠道查询（第三方机构信息）
			List<CustomerBaseInfo>  agencyCompanys = customerCompanyService.findAgencyCompany(CustomerTypeEnum.SYSTEM_ORGANIZATION.getCode());
			//投保申请信息
			InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findById(letterId);
			if (null != insuranceContactLetterInfo) {
				insuranceContactLetterInfo.setCustomerCertType(CertTypeEnum.getMsgByCode(insuranceContactLetterInfo.getCustomerCertType()));
				long projectSetupId = insuranceContactLetterInfo.getProjectSetupId();
				ProjectSetupInfo projectSetupInfo = projectSetupService.findById(projectSetupId);
				if (null != projectSetupInfo) {
					insuranceContactLetterInfo.setProportionType(projectSetupInfo.getProportionType());
					insuranceContactLetterInfo.setProportion(projectSetupInfo.getProportion());
					//查询该超权限对应的渠道信息
					info.setAgencyCompanyId(projectSetupInfo.getChannelsUserId());
					info.setAgencyCompanyName(projectSetupInfo.getChannelsUserName());
					
				}
				// 投保人 和 被保险人人
				List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(insuranceContactLetterInfo.getBillNo());
				if (null != billCustomerInfos && billCustomerInfos.size()==2) {
					BusinessBillCustomerInfo businessBillCustomerInfo1= billCustomerInfos.get(0);
					BusinessBillCustomerInfo businessBillCustomerInfo2= billCustomerInfos.get(1);
					//投保人
					if (businessBillCustomerInfo1.getType().equals("0")) {
						model.addAttribute("policyHolder", businessBillCustomerInfo1);
						model.addAttribute("insuredPerson", businessBillCustomerInfo2);
					} else {
						model.addAttribute("policyHolder", businessBillCustomerInfo2);
						model.addAttribute("insuredPerson", businessBillCustomerInfo1);
					}
				}
				//受益人信息
				List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(insuranceContactLetterInfo.getLetterId());
				if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
					for (BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo : findBusinessBillBeneficiarys) {
						businessBillBeneficiaryInfo.setBeneficiaryCertType(CertTypeEnum.getMsgByCode(businessBillBeneficiaryInfo.getBeneficiaryCertType()));
					}
					BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
					if (businessBillBeneficiaryInfo.getType().equals("0")) {
						model.addAttribute("plan_order", 0);
					} else {
						model.addAttribute("plan_order", 1);
					}
				}
				model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
				//TODO  生成非寿险定额的缴费计划
		        if (!StringUtils.isBlank(insuranceContactLetterInfo.getIsQuota()) && insuranceContactLetterInfo.getIsQuota().equals("YES")) {
					Money premiumAmount = insuranceContactLetterInfo.getPremiumAmount();
					Money communityInfoAmount = new Money(0,0) ;
					long productId = insuranceContactLetterInfo.getProductId();
					InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
					InsuranceProtocolChargeInfo BrokeChargeInfo = insuranceProtocolChargeService.getBrokerageRate(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),insuranceProductInfo,String.valueOf(letterId),null);
					Double brokeScale = BrokeChargeInfo == null ? 0 : Double.valueOf(BrokeChargeInfo.getVal());
					InsuranceProtocolChargeInfo CommunityProtocolChargeInfo = insuranceProtocolChargeService.getBrokerageRate(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),insuranceProductInfo,String.valueOf(letterId),null);
					Double communityScale = null !=projectSetupInfo? projectSetupInfo.getProportion()/100 : (  CommunityProtocolChargeInfo == null ? 0 :Double.valueOf(CommunityProtocolChargeInfo.getVal()));
					if (null !=projectSetupInfo) {
						String proportionType = projectSetupInfo.getProportionType();
						//占经纪费比例
						if (proportionType.equals("0")) {
							communityInfoAmount = premiumAmount.multiply(brokeScale).multiply(communityScale);
						//占保费比例
						} else {
							communityInfoAmount = premiumAmount.multiply(communityScale);
						}
					} else {
						communityInfoAmount = premiumAmount.multiply(communityScale);
					}
					model.addAttribute("brokeFee", premiumAmount.multiply(brokeScale).divide(BigDecimal.valueOf(100)));
					model.addAttribute("communityFee", communityInfoAmount.divide(BigDecimal.valueOf(100)));
					model.addAttribute("communityScale",communityScale);
					model.addAttribute("brokeFeeScale", brokeScale);
				} 
				//投保内容
				List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(letterId);
				//总的保费
				Money totalMoney = new Money(0,0);
				//总的经纪费 
				Money brokeFee = new Money(0,0);
				//总的佣金金额
				Money communityInfo = new Money(0,0);
				//获取保费
				if (ListUtil.isNotEmpty(insuranceContactLetterDetails)) {
					for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
						totalMoney = totalMoney.add(insuranceContactLetterDetailInfo.getPremiumAmount());
						brokeFee = brokeFee.add(insuranceContactLetterDetailInfo.getBrokerAmount());
					}
					//生成寿险的缴费计划
					if (insuranceContactLetterInfo.getInsuranceType().equals("isLifeInsurance")) {
						 ShowMsgInfo list = businessBillService.addPayBillPlan(insuranceContactLetterInfo,insuranceContactLetterDetails);
						model.addAttribute("planList",list.getBillPayPlanYear());
						model.addAttribute("payPlanLen", list.getBillPayPlanYear().size());
						BrokeFeeAndCommunityInfo brokeFeeAndCommunityInfo = list.getBrokeFeeAndCommunityInfo();
						model.addAttribute("brokerageFeeInfo",brokeFeeAndCommunityInfo.getBrokerageFeeInfo());
						model.addAttribute("brokerageFeeDetails", brokeFeeAndCommunityInfo.getBrokerageFeeDetails());
						model.addAttribute("commissionInfo",brokeFeeAndCommunityInfo.getCommissionInfoInfo());
						model.addAttribute("amount",totalMoney.getAmount());
						model.addAttribute("commissionInfoDetails", brokeFeeAndCommunityInfo.getCommissionDetails());
					} else if (!StringUtils.isBlank(insuranceContactLetterInfo.getIsQuota()) && insuranceContactLetterInfo.getIsQuota().equals("NO")) {
						//TODO 生成非寿险非定额的缴费计划
						//自动计算出佣金金额 和  佣金比例
						for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
							long productId = insuranceContactLetterDetailInfo.getProductId();
							Money premiumAmount = insuranceContactLetterDetailInfo.getPremiumAmount();
							Money brokerAmount = insuranceContactLetterDetailInfo.getBrokerAmount();
							InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
							Money communityInfoAmount = new Money(0.0) ;
							InsuranceProtocolChargeInfo insuranceProtocolChargeInfo = insuranceProtocolChargeService.getBrokerageRate(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),insuranceProductInfo,String.valueOf(insuranceContactLetterDetailInfo.getLetterId()),null);
							if (null != insuranceProtocolChargeInfo) {
								//佣金比例
								String val = insuranceProtocolChargeInfo.getVal();
								val = null !=projectSetupInfo ? String.valueOf(projectSetupInfo.getProportion()) : val ;
								if (null !=projectSetupInfo) {
									String proportionType = projectSetupInfo.getProportionType();
									//占经纪费比例
									if (proportionType.equals("0")) {
										communityInfoAmount = brokerAmount.multiply(BigDecimal.valueOf(Double.valueOf(val)/100));
									//占保费比例
									} else {
										communityInfoAmount = premiumAmount.multiply(BigDecimal.valueOf(Double.valueOf(val)/100));
									}
								} else {
									communityInfoAmount = premiumAmount.multiply(BigDecimal.valueOf(Double.valueOf(val)/100));
								}
								communityInfo = communityInfo.add(communityInfoAmount);
							}
						}
						BrokerageFeeInfo brokerageFeeInfo = new BrokerageFeeInfo();
						brokerageFeeInfo.setBrokerageAmount(brokeFee);
						brokerageFeeInfo.setBrokerageScale(brokeFee.getAmount().divide(totalMoney.getAmount(),4,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
						CommissionInfoInfo commissionInfoInfo = new CommissionInfoInfo();
						commissionInfoInfo.setCommissionAmount(communityInfo);
						if (null ==projectSetupInfo || projectSetupInfo.getProportionType().equals("0")) {
							commissionInfoInfo.setCommissionScale(communityInfo.getAmount().divide(totalMoney.getAmount(),4,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).doubleValue());
						} else {
							commissionInfoInfo.setCommissionScale(projectSetupInfo.getProportion());
						}
						model.addAttribute("amount", totalMoney);
						model.addAttribute("brokerageFeeInfo", brokerageFeeInfo);
						model.addAttribute("commissionInfoInfo", commissionInfoInfo);
					}
				}
				model.addAttribute("insuranceContactLetterDetails", insuranceContactLetterDetails);
			}
			model.addAttribute("insuranceContactLetterInfo", insuranceContactLetterInfo);
			model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			model.addAttribute("info", info);
			model.addAttribute("agencyCompanys", agencyCompanys);
			
			if (!StringUtils.isBlank(type)) {
				//寿险投保确认
				if (type.equals("lifeInsurance")) {
					return VM_PATH + "addLifeInsuranceBusinessBill.vm";
					//非寿险非定额投保确认
				} else if (type.equals("notLifeInsurance")) {
					return VM_PATH + "addNotLifeInsuranceBusinessBill.vm";
					//非寿险定额投保确认
				} else if (type.equals("notLifeInsuranceQuota")) {
					return VM_PATH + "addNotLifeInsuranceQuotaBusinessBill.vm";
				}
			}
		}
		return null;
	}
	
	
	
	

	/**
	 * 新增保单信息
	 * 
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillOrder order, HttpSession session, Model model) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBill";
		try {
			WebUtil.setPoPropertyByRequest(order, request);
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			InsuranceBaseResult result = businessBillService.save(order);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.LIFEINSURANCE_BILL_POLICY_RECEIPT);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.NOLIFEINSURANCE_QUATEBILL_POLICY_RECEIPT);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.LIFEINSURANCE_BILL_POLICY_RECEIPT);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}

	/**
	 * 提醒设置
	 * 
	 * @param projectSetupId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("set.htm")
	public String set(HttpServletRequest request, Model model) {
		model.addAttribute("info", null);
		return VM_PATH + "followingWarn.vm";
	}

	/**
	 * 提醒设置确认
	 * 
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setSubmit.json")
	public Object setSubmit(HttpServletRequest request, BusinessBillOrder order) {
		JSONObject json = new JSONObject();

		return json;
	}

	/**
	 * 查看保单详情
	 * 
	 * @param businessBillId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(@RequestParam(value = "businessBillId", required = false, defaultValue = "0")long businessBillId,@RequestParam(value = "formId", required = false, defaultValue = "0") long formId, HttpServletRequest request, Model model) {
		BusinessBillInfo businessBillInfo =  null ;
		
		if (formId > 0) {
			businessBillInfo = businessBillService.findByFormId(formId);
		} else {
			businessBillInfo = 	businessBillService.findById(businessBillId);
		}
		
		if (null != businessBillInfo ) {
			if (!StringUtils.isBlank(businessBillInfo.getInsuranceOfType())) {
				if (businessBillInfo.getInsuranceOfType().equals(InsuranceOfTypeEnum.OFFLINE.getCode())) {
					FormInfo formInfo = formService.findByFormId(businessBillInfo.getFormId());
					//附件列表
					queryCommonAttachmentData(model, businessBillInfo.getBusinessBillId()+ "",CommonAttachmentTypeEnum.FORM_ATTACH);
					model.addAttribute("form", formInfo);
					model.addAttribute("info", businessBillInfo);
					return VM_PATH + "viewFormBusinessBill.vm";
				}
			}
			InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findById(businessBillInfo.getLetterId());
			model.addAttribute("insuranceContactLetterInfo", insuranceContactLetterInfo);
			//投保内容
			List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(insuranceContactLetterInfo.getLetterId());
			model.addAttribute("insuranceContactLetterDetails", insuranceContactLetterDetails);
		}
		model.addAttribute("info", businessBillInfo);
		// 投保人 和 被保险人人
		List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(businessBillInfo.getBillNo());
		
		//承保信息
		List<BusinessBillUnderInforInfo> billUnderInforInfos = businessBillUnderInforService.findBusinessBillUnderInfo(businessBillId);

		//共保信息
	    List<BusinessBillCoinsInfoInfo> billCoins = businessBillCoinsInfoService.findBusinessBillCoinsInfo(businessBillId);
	
		//再保信息
		 List<BusinessBillReinsInfoInfo> billReins = businessBillReinsInfoService.findBusinessBillReinsInfo(businessBillId);
		//缴费计划
		 List<BusinessBillPayPlanInfo>  billPayPlanInfos = businessBillPayPlanService.findAllNormalBusinessBillPayPlan(businessBillId);
		model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
		//经纪费信息 和 佣金信息
		BrokerageFeeInfo brokerageFeeInfo = brokerageFeeService.findBrokerageFeeByBusinessBillId(businessBillId);
		if (null != brokerageFeeInfo) {
			//经纪费信息明细 和  佣金明细查询
			List<BrokerageFeeDetailInfo> brokerageFeeDetails = brokerageFeeService.findBrokerageFeeDetails(brokerageFeeInfo.getBrokerageId());
			model.addAttribute("brokerageFeeDetails", brokerageFeeDetails);
		}
		CommissionInfoInfo commissionInfoInfo = commissionInfoService.findCommissionInfoByBusinessBillId(businessBillId);
		if (null != commissionInfoInfo) {
		    List<CommissionInfoDetailInfo> commissionInfoDetails = commissionInfoService.findCommissionInfoDetails(commissionInfoInfo.getCommissionInfoId());
		    model.addAttribute("commissionInfoDetails", commissionInfoDetails);
		}
		model.addAttribute("brokerageFeeInfo", brokerageFeeInfo);
		model.addAttribute("commissionInfoInfo", commissionInfoInfo);
		model.addAttribute("billCustomers", billCustomerInfos);
		model.addAttribute("billCoins", billCoins);
		model.addAttribute("billReins", billReins);
		model.addAttribute("billPayPlanInfos", billPayPlanInfos);
		model.addAttribute("billUnderInforInfos", billUnderInforInfos);
		return VM_PATH + "viewBusinessBill.vm";
	}

	/**
	 * 获取该保单该期数的缴费信息
	 * @return
	 */
	@RequestMapping("getInsuranceBillRenewal.json")
	@ResponseBody
	public InsuranceBaseResult getinsuranceBillRenewal(String businessBillId,String period){
		InsuranceBaseResult result = new InsuranceBaseResult();
		InsuranceBillRenewalInfo insuranceBillRenewalInfo = null;
		try {
			insuranceBillRenewalInfo = insuranceBillRenewalService.findInsuranceBillRenewal(Integer.parseInt(businessBillId), Integer.parseInt(period));
			result.setSuccess(true);
			result.setReturnObject(insuranceBillRenewalInfo);
		} catch (NumberFormatException e) {
			result.setSuccess(false);
			result.setMessage("查询保单缴费信息失败");
			logger.error("查询保单缴费信息异常", e);
		}
		return result;
	}
	
	/**
	 * 保单续保
	 * 
	 * @param projectSetupId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("renewalOfInsurance.htm")
	public String renewalOfInsurance(@RequestParam(value = "businessBillId", required = false, defaultValue = "0") long businessBillId,HttpServletRequest request, Model model) {
		BusinessBillInfo businessBillInfo = businessBillService.findById(businessBillId);
		//投保申请的id
		long letterId = businessBillInfo.getLetterId();
		InsuranceContactLetterInfo info = insuranceContactLetterService.findById(letterId);
		if (!StringUtil.isBlank(info.getPriceContactNo())) {
			long priceContactId = info.getPriceContactId();
			model.addAttribute("priceContactId", priceContactId);
			return "redirect:/insurance/priceContactLetter/forwardAdd.htm";
		} else {
			if (null != info) {
				//查询产品的档次信息
				long productLevelId = info.getProductLevelId();
				long customerUserId = info.getCustomerUserId();
				if (productLevelId > 0  && info.getProductId() > 0) {
					InsuranceProductLevelInfo insuranceProductLevelInfo = insuranceContactLetterService.findProductLevelInfo(productLevelId);
					if (null != insuranceProductLevelInfo) {
						info.setProductLevelLevel(insuranceProductLevelInfo.getLevel());
						info.setPremiumAmount(insuranceProductLevelInfo.getPremiumAmount());
					}
				}
				List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(info.getLetterId());
				List<CustomerCertInfo> findCustomerCertList = insuranceContactLetterService.findCustomerCertList(customerUserId);
				//客户的证件信息
				model.addAttribute("findCustomerCertList", findCustomerCertList);
				//投保内容
				model.addAttribute("insuranceContactLetterDetails", insuranceContactLetterDetails);
				// 投保人 和 被保险人人 
				//TODO 传入业务单号
				List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(info.getBillNo());
				if (null != billCustomerInfos && billCustomerInfos.size()==2) {
					BusinessBillCustomerInfo businessBillCustomerInfo1= billCustomerInfos.get(0);
					BusinessBillCustomerInfo businessBillCustomerInfo2= billCustomerInfos.get(1);
					BusinessBillCustomerInfo policyHolder = businessBillCustomerInfo1.getType().equals("0")?businessBillCustomerInfo1:businessBillCustomerInfo2;
					BusinessBillCustomerInfo insuredPerson = businessBillCustomerInfo1.getType().equals("0")?businessBillCustomerInfo2:businessBillCustomerInfo1;
					model.addAttribute("policyHolder", policyHolder);
					model.addAttribute("insuredPerson", insuredPerson);
					List<CustomerCertInfo> customerCertInfo = insuranceProductService.getCustomerCertInfo(insuredPerson.getCustomerUserId());
					if (ListUtil.isNotEmpty(customerCertInfo)) {
						for (CustomerCertInfo customerCertInfo2 : customerCertInfo) {
							if (customerCertInfo2.getCertType().getCode().equals(CertTypeEnum.IDENTITY_CARD.getCode())) {
								String certNo = customerCertInfo2.getCertNo();
								Integer lastCap =Integer.parseInt(certNo.substring(certNo.length()-1));
								String sex = lastCap % 2 == 0?"woman":"man";
								model.addAttribute("certNo",certNo );
								model.addAttribute("sex",sex );
								for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
									List<InsuranceProtocolChargeInfo> insuranceProductCharge = insuranceProtocolChargeService.getInsuranceProductCharge(String.valueOf(insuranceContactLetterDetailInfo.getProductId()), sex);
									insuranceContactLetterDetailInfo.setInsuranceProductCharges(insuranceProductCharge);
									InsuranceProtocolChargeInfo insuranceProductChargeRate = insuranceProtocolChargeService.getInsuranceProductChargeRate(String.valueOf(insuranceContactLetterDetailInfo.getProductId()), sex, certNo, insuranceContactLetterDetailInfo.getPeriod());
									if (null != insuranceProductChargeRate) {
										insuranceContactLetterDetailInfo.setPeriodRate(insuranceProductChargeRate.getVal());
									}
									InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(insuranceContactLetterDetailInfo.getProductId());
									if (null != insuranceProductInfo) {
										insuranceContactLetterDetailInfo.setPayType(PaymentTypeEnum.getMsgByCode(String.valueOf(insuranceProductInfo.getPayType())));
									}
								}
								break;
							}
						}
					}
				} else {
					for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
						long productId = insuranceContactLetterDetailInfo.getProductId();
						InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
						List<InsuranceProtocolChargeInfo> insuranceProductCharge = insuranceProtocolChargeService.getInsuranceProductChargeInfo(insuranceProductInfo.getCompanyUserId(), insuranceProductInfo.getCatalogId(), String.valueOf(productId));
						insuranceContactLetterDetailInfo.setInsuranceProductCharges(insuranceProductCharge);
						
					}
				}
				//受益顺序
				List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(info.getLetterId());
				if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
					BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
					if (businessBillBeneficiaryInfo.getType().equals("0")) {
						model.addAttribute("plan_order", 0);
					} else {
						model.addAttribute("plan_order", 1);
					}
				}
				String makeToken = TokenProccessor.getInstance().makeToken();
				request.getSession().setAttribute("token", makeToken);
				model.addAttribute("httpRequest", request);
				
				queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_DATA);
				queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_OTHER_DATA);
				queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_DATA);
				queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_OTHER_DATA);
				queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_DATA);
				queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_OTHER_DATA);
				model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
				//查询保险非寿险定额产品对应的档次信息
				List<InsuranceProductLevelInfo> productLevels = insuranceProductService.getInsuranceProductLevel(info.getProductId());
				info.setFormId(0);
				info.setLetterId(0);
				model.addAttribute("info", info);
				model.addAttribute("productLevels", productLevels);
				model.addAttribute("certType", CertTypeEnum.getAllEnum());
				model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			}
			
			
			//寿险续保
			if (info.getInsuranceType().equals(LifeInsuranceTypeEnum.LIFEINSURANCE.getCode())) {
				return VM_PATH + "renewalLifeInsuranceBusinessBill.vm";
				//非寿险非定额续保
			} else if (info.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && info.getIsQuota().equals("NO")) {
				return VM_PATH + "renewalNotLifeInsuranceBusinessBill.vm";
				//非寿险定额续保
			} else if (info.getInsuranceType().equals(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode()) && info.getIsQuota().equals("YES")) {
				return VM_PATH + "renewalNotLifeInsuranceQuotaBusinessBill.vm";
			}
			return null;
		}
	}
	
	
	//上传excel数据
		@RequestMapping("upLoadExcel.htm")
		public void uploadExcel(HttpServletRequest request, HttpServletResponse response, String type) throws Exception {
			JSONObject json = new JSONObject();
			try {
				//导入时存在的异常数据
			    Map<String,Object> map=new HashMap<String, Object>();
				//解析excel考勤文件
				List<BusinessBillInfo> list = readExcel(request,map);
				if (ListUtil.isNotEmpty(list)) {
					for (BusinessBillInfo businessBillInfo : list) {
					BusinessBillOrder order = new BusinessBillOrder();
					BeanCopier.staticCopy(businessBillInfo, order);
					setSessionLocalInfo2Order(order);
					order.setCheckIndex(0);
					order.setCheckStatus(1);
					order.setFormCode(FormCodeEnum.BDSP);
					businessBillService.addBusinessBill(order);
					}
				}
				json.put("success", true);
				json.put("message", "导入成功");
				returnJson(response, true, json);
				if (map.isEmpty()) {
					request.getSession().removeAttribute("businessBillBad");
				} else {
					request.getSession().setAttribute("businessBillBad", map.get("businessBillBad"));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				json.put("success", false);
				json.put("message", "导入异常" + e.getMessage());
			}
		}
		
		
		
		/**
		 * 修改保单
		 * @param request
		 * @param order
		 * @return
		 */
		@ResponseBody
		@RequestMapping("editSubmitBusinessBill.json")
		public Object editSubmitBusinessBill(HttpServletRequest request, BusinessBillOrder order) {
			JSONObject json = new JSONObject();
			
			String tipPrefix = "操作";
			try {
				setSessionLocalInfo2Order(order);
				order.setCheckIndex(0);
				order.setCheckStatus(1);
				order.setFormCode(FormCodeEnum.BDSP);
				InsuranceBaseResult result = businessBillService.addBusinessBill(order);
				//添加附件
				addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.FORM_ATTACH);
				json = toJSONResult(result, tipPrefix);
			} catch (Exception e) {
				logger.error(tipPrefix, e);
				json = toJSONResult(tipPrefix, e);
			}
			return json;
		}
		

		private List<BusinessBillInfo> readExcel(HttpServletRequest request, Map<String, Object> map) throws Exception {
			 List<String[]> dataList = new ArrayList<>();
				List<BusinessBillInfo> pageList = new ArrayList<>();
				//错误数据集合返回
				List<BusinessBillInfo> businessBillBad=new ArrayList<BusinessBillInfo>();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
				ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
				fileUpload.setHeaderEncoding("utf-8");
				List<FileItem> fileList = null;
				try {
					fileList = fileUpload.parseRequest(request);
				} catch (FileUploadException ex) {
					logger.error(ex.getMessage(), ex);
				}
				Iterator<FileItem> it = fileList.iterator();
				InputStream is = null;
				while (it.hasNext()) {
					FileItem item = it.next();
					if (!item.isFormField()) {
						is = item.getInputStream();
						break;
					}
				}
				
				if (null != is) {
					org.apache.poi.ss.usermodel.Workbook model = null ;
					if (fileList.get(0).getName().endsWith(".xls")) {
						model = new HSSFWorkbook(is);
					} else {
						model = new XSSFWorkbook(is);
					}
					org.apache.poi.ss.usermodel.Sheet sheet = model.getSheetAt(0);
				      //行数(表头的目录不需要，从1开始)  
			        for(int i=0; i<sheet.getLastRowNum(); i++){  
			        	Row row = sheet.getRow(i);
			        	if (null == row) {
			        		break ;
			        	}
			        	
			        	if(i >= 2 && CheckRowNull(row) > 4){
			        		continue ;
			        	}
		        		//总的列
		        		int columns =row.getLastCellNum();
		        		String[] str = new String[columns];  	
		        		for(int j=0; j< columns; j++){  
		        			//获取第i行，第j列的值  
		        			org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
		        			if (null != cell) {
		        				str[j] = getValue(cell,j);
		        			}
		        		}  
		        		dataList.add(str);
			        }  
				}
				if (ListUtil.isNotEmpty(dataList)) {
					int num = 0 ;
					Map<String, Object> maps = new HashMap<>();
				for (String[] s : dataList) {
						num ++ ;
						if (num < 3 ) {
							continue;
						}
						 StringBuffer sb = new StringBuffer();
					    for(int i = 0; i < s.length; i++){
					    	if (null != s[i]) {
					    		sb. append(s[i]);
					    	}
					    }
						    String ns = sb.toString();
						    if (StringUtils.isBlank(ns)) {
						    	continue ;
						    }
						BusinessBillInfo info = new BusinessBillInfo();
						
						for (int i = 0; i < s.length; i++) {
							String data = s[i];
							switch (i) {
							case 0:
								info.setInsuranceDepart(data);
								break;
							case 1:
								info.setInsuranceTeam(data);
								break;
							case 2:
								info.setInsuranceBrokerNo(data);
								break;
							case 3:
								info.setInsuranceBrokerName(data);
								break;
							case 4:
								//查询该保险公司是否存在
								info.setInsuranceCompanyName(data);
								break;
							case 5:
								//查询该保险公司是否存在
								info.setBillCustomerName(data);
								break;
							case 6:
								info.setBillInsuredName(data);
								break;
							case 7:
								info.setInsuranceTypeName(data);
								break;
							case 8:
								info.setInsuranceCatalogName(data);
								break;	
							case 9:
								info.setInsuranceNo(data);
								break;	
							case 10:
								info.setBatchNo(data);
								break;	
							case 11:
								info.setPlateNumber(data);
								break;	
							case 12:
								info.setPeriods(data);
								break;	
							case 13:
								data = data == null ? "0" : data;
								info.setInsuranceAmount(new Money(Double.valueOf(data)));
								break;	
							case 14:
								if (!StringUtil.isBlank(data)) {
									info.setInsuranceDate(sf.parse(data));
								}
								break;	
							case 15:
								if (!StringUtil.isBlank(data)) {
								     info.setBeginDate(sdf.parse(data));
								}
								break;	
							case 16:
								if (!StringUtil.isBlank(data)) {
									info.setEndDate(sdf.parse(data));
								}
								break;
							case 17:
								data = StringUtils.isBlank(data) ? "0" : data;
								info.setPremiumAmount(new Money(Double.valueOf(data)));
								break;
							default:
								info.setInsuranceOfType(InsuranceOfTypeEnum.OFFLINE.getCode());
								break;
							}
						}

						if (!isValidParam(info)) {
							info.setErrorMsg(info.getErrorMsg().substring(0, info.getErrorMsg().length()-1));
							businessBillBad.add(info);
							continue;
						}
						if (!maps.containsKey(info.getInsuranceNo())) {
							pageList.add(info);
							maps.put(info.getInsuranceNo(), info);
						}
						}
				}
				if (ListUtil.isNotEmpty(businessBillBad)) {
					map.put("businessBillBad", businessBillBad);
				}
				return pageList;
		}
		
		
		// 判断行为空
		public static int CheckRowNull(Row hssfRow) {
			int num = 0;
			Iterator<Cell> cellItr = hssfRow.iterator();
			while (cellItr.hasNext()) {
				Cell c = cellItr.next();
				if (c.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					num++;
				}
			}
			return num;
		}



	public boolean isValidParam(BusinessBillInfo info){

		boolean lastFlag=true;
		info.setErrorMsg("");
		if (StringUtils.isBlank(info.getInsuranceDepart())) {
			info.setErrorMsg(info.getErrorMsg()+"部门名称不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getInsuranceTeam())) {
			info.setErrorMsg(info.getErrorMsg()+"团队不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getInsuranceBrokerNo())) {
			info.setErrorMsg(info.getErrorMsg()+"经纪人工号不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getInsuranceBrokerName())) {
			info.setErrorMsg(info.getErrorMsg()+"经纪人姓名不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getInsuranceCompanyName())) {
			info.setErrorMsg(info.getErrorMsg()+"保险公司不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getBillCustomerName())) {
			info.setErrorMsg(info.getErrorMsg()+"投保人不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getBillInsuredName())) {
			info.setErrorMsg(info.getErrorMsg()+"被保险人不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getInsuranceTypeName())) {
			info.setErrorMsg(info.getErrorMsg()+"险种类别不能为空,");
			lastFlag = false ;
		}

		if (StringUtils.isBlank(info.getInsuranceCatalogName())) {
			info.setErrorMsg(info.getErrorMsg()+"险种名称不能为空,");
			lastFlag = false ;
		}


		if (StringUtils.isBlank(info.getInsuranceNo())) {
			info.setErrorMsg(info.getErrorMsg()+"保单号不能为空,");
			lastFlag = false ;
		} else {
				String insuranceNo = info.getInsuranceNo();
				BusinessBillFormInfo formInfo = businessBillService.findBusinessBillByNo(insuranceNo);
				if (null != formInfo) {
					info.setErrorMsg(info.getErrorMsg()+"该保单号已存在，请更改,");
					lastFlag = false ;
			}
		}

		if (null == info.getInsuranceDate()) {
			info.setErrorMsg(info.getErrorMsg()+"签单日期不能为空,");
			lastFlag = false ;
		}

		if (null == info.getBeginDate()) {
			info.setErrorMsg(info.getErrorMsg()+"起保日期不能为空,");
			lastFlag = false ;
		}

		if (null == info.getEndDate()) {
			info.setErrorMsg(info.getErrorMsg()+"起保截止日期不能为空,");
			lastFlag = false ;
		}


		return lastFlag;
	}
		
		
		/**
		 * 返回excel数据
		 * @param cell
		 * @return
		 */
		private  String getValue(org.apache.poi.ss.usermodel.Cell cell,int j) {
			 String cellValue = "";
			if(null==cell)return "";
			if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
				// 返回布尔类型的值
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) { //数值型
				if (HSSFDateUtil.isCellDateFormatted(cell) ) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					if (j==15 || j==16) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					}
	                if(cell.getDateCellValue()!=null){
	                    try {
							cellValue = sdf.format(cell.getDateCellValue());
						} catch (Exception e) {
							logger.error("转换日期格式异常", cell.getStringCellValue());
						}
	                }
				} else { // 纯数字     
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				return cellValue;
			}else {
				return cell.getRichStringCellValue().getString();
			}
		}

		/**
		 * 分公司财务对保单上传审批
		 * @param formId
		 * @param request
		 * @param model
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("audit2.htm")
		public String audit2(long formId, HttpServletRequest request, Model model) throws Exception {
			FormInfo form = formService.findByFormId(formId);
			if (null != form) {
				BusinessBillInfo businessBillInfo = businessBillService.findByFormId(formId);
				//附件列表
				queryCommonAttachmentData(model, businessBillInfo.getBusinessBillId()+ "",CommonAttachmentTypeEnum.FORM_ATTACH);
				model.addAttribute("info", businessBillInfo);
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
			}
			return VM_PATH + "auditForm2.vm";
		}
		
		

		/**
		 * 保单上传审批
		 * @param formId
		 * @param request
		 * @param model
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("audit.htm")
		public String audit(long formId, HttpServletRequest request, Model model) throws Exception {
			FormInfo form = formService.findByFormId(formId);
			if (null != form) {
				BusinessBillInfo businessBillInfo = businessBillService.findByFormId(formId);
				//附件列表
				queryCommonAttachmentData(model, businessBillInfo.getBusinessBillId()+ "",CommonAttachmentTypeEnum.FORM_ATTACH);
				model.addAttribute("info", businessBillInfo);
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
			}
			return VM_PATH + "auditForm.vm";
		}
		
		
		/**
		 * 下载保单的模板
		 * @param request
		 * @param response
		 * @param type
		 */
		@RequestMapping("downloadExcelModel.htm")
		public void downLoadExcel(HttpServletRequest request, HttpServletResponse response) {
			BufferedOutputStream output = null;
			BufferedInputStream input = null;
			
			try {	
					String fileName = "保单模板";
					String filePath = request.getServletContext().getRealPath("/WEB-INF/xsl/excelModel/")+ File.separator+"保单模板.xlsx";
					File file = new File(filePath);
					response.setHeader("Content-disposition",
						"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1")
								+ ".xlsx");
					response.setContentType("application/msexcel");
					response.setContentLength((int) file.length());
					byte[] buffer = new byte[4096];// 缓冲区
					output = new BufferedOutputStream(response.getOutputStream());
					input = new BufferedInputStream(new FileInputStream(file));
					int n = -1;
					//遍历，开始下载
					while ((n = input.read(buffer, 0, 4096)) > -1) {
						output.write(buffer, 0, n);
					}
					output.flush(); //不可少
					response.flushBuffer();//不可少
			} catch (Exception e) {
				//异常自己捕捉
				logger.error("读取excel模板异常：" + e);
			} finally {
				//关闭流，不可少
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		
		
		/**
		 * 
		 * @Description:导出异常数据
		 * @param response
		 * @param request
		 * @param data
		 */
		@RequestMapping("downloadBadData.htm")
		@ResponseBody
		public void downloadBadData(HttpServletResponse response,HttpServletRequest request){
			try {
				
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				
				Object businessBillBad= request.getSession().getAttribute("businessBillBad");
				
				//模板
				String templatePath =request.getSession().getServletContext().getRealPath("/WEB-INF/xsl/excelModel/badDataTemplate.xls");
				//生成excel地址
				String resultPath =request.getSession().getServletContext().getRealPath("/WEB-INF/xsl/excelModel/businessBillBadData.xls");
				File file = new File(resultPath);
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.setHeader("Content-Disposition","attachment;filename=".concat(URLEncoder.encode(file.getName(), "UTF-8")));
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("list", businessBillBad);
				
				byte[] content = ExcelUtil.createExcelFileByte(templatePath, map, resultPath);
				
				OutputStream output = response.getOutputStream();
				output.write(content);
				output.flush();
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("导出异常数据出错：" + e.getMessage());
			}
		}
		
		
		
		/**
		 * 编辑新增维护保单信息
		 * 
		 * @param businessBillId
		 * @param request
		 * @param model
		 * @return
		 */
		@RequestMapping("editBusinessBill.htm")
		public String editBusinessBill(@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
				HttpServletRequest request, Model model) {
			FormInfo form = formService.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
						"获取表单信息出错");
			}
			BusinessBillInfo info = businessBillService.findByFormId(formId);
			//附件列表
			queryCommonAttachmentData(model, info.getBusinessBillId()+ "",CommonAttachmentTypeEnum.FORM_ATTACH);
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("info", info);// 表单信息
			return  VM_PATH + "addFormBusinessBill.vm";
		}



    /**
     * 确认删除保单
     * @param request
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteConfirm.json")
    public Object deleteConfirm(HttpServletRequest request, BusinessBillOrder order) {
        JSONObject json = new JSONObject();

        String tipPrefix = "删除保单";
        try {
            InsuranceBaseResult result = businessBillService.deleteBusinessBill(order);
            json = toJSONResult(result, tipPrefix);
        } catch (Exception e) {
            logger.error(tipPrefix, e);
            json = toJSONResult(tipPrefix, e);
        }
        return json;
    }
}
