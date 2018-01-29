package com.born.insurance.web.controller.businessBill;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillBeneficiaryInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.businessBillCoinsInfo.BusinessBillCoinsInfoInfo;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.info.businessBillReinsInfo.BusinessBillReinsInfoInfo;
import com.born.insurance.ws.info.businessBillUnderInfor.BusinessBillUnderInforInfo;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.insuranceBillRenewal.InsuranceBillRenewalInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillQueryOrder;
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
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/businessBillModify")
public class BusinessBillModifyController extends BaseController {

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

	private final static String VM_PATH = "/insurance/businessBillModify/";

	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "insuranceDate", "beginDate", "endDate", "planPayDate", "actualPayDate" };
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
			BusinessBillQueryOrder queryOrder = new BusinessBillQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillInfo> batchResult = businessBillService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
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
			@RequestParam(value = "businessBillId", required = false, defaultValue = "0") long businessBillId,@RequestParam(value = "letterId", required = false, defaultValue = "0") long letterId,String type,
			HttpServletRequest request, Model model) {
		BusinessBillInfo info = null;
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
				//缴费计划查询
			    List<BusinessBillPayPlanInfo> billPayPlanInfos = businessBillPayPlanService.findAllNormalBusinessBillPayPlan(businessBillId);
			    model.addAttribute("billPayPlanInfos", billPayPlanInfos);
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
			//投保确认
			info = new BusinessBillInfo();
			//中介渠道查询（第三方机构信息）
			List<CustomerBaseInfo>  agencyCompanys = customerCompanyService.findAgencyCompany(CustomerTypeEnum.THIRD_PARTY_MECHANISM.getCode());
			//投保申请信息
			InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findById(letterId);
			if (null != insuranceContactLetterInfo) {
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
					BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
					if (businessBillBeneficiaryInfo.getType().equals("0")) {
						model.addAttribute("plan_order", 0);
					} else {
						model.addAttribute("plan_order", 1);
					}
				}
				model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
				//投保内容
				List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(letterId);
				Money totalMoney = new Money(0,0);
				//获取保费
				if (ListUtil.isNotEmpty(insuranceContactLetterDetails)) {
					for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
						totalMoney = totalMoney.add(insuranceContactLetterDetailInfo.getPremiumAmount());
					}
				}
				model.addAttribute("amount", totalMoney);
				model.addAttribute("insuranceContactLetterDetails", insuranceContactLetterDetails);
			}
			model.addAttribute("insuranceContactLetterInfo", insuranceContactLetterInfo);
			model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			model.addAttribute("info", info);
			model.addAttribute("agencyCompanys", agencyCompanys);
			
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
			InsuranceBaseResult result = businessBillService.save(order);
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
	public String view(long businessBillId, HttpServletRequest request, Model model) {
		BusinessBillInfo businessBillInfo = businessBillService.findById(businessBillId);
		if (null != businessBillInfo) {
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
	     List<BusinessBillPayPlanInfo> billPayPlanInfos = businessBillPayPlanService.findAllNormalBusinessBillPayPlan(businessBillId);
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
		if (!StringUtil.isBlank(businessBillInfo.getPriceNo())) {
			return "insurance/priceContactLetter/add.htm";
		} else {
			return "/insurance/insuranceContactLetter/addInsuranceContactLetter.vm";
		}
	}
}
