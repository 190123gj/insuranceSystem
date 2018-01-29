package com.born.insurance.web.controller.insuranceBillRenewal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.info.businessBill.BillPayPlanYear;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.businessBillCoinsInfo.BusinessBillCoinsInfoInfo;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.info.businessBillReinsInfo.BusinessBillReinsInfoInfo;
import com.born.insurance.ws.info.businessBillUnderInfor.BusinessBillUnderInforInfo;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.info.insuranceBillRenewal.InsuranceBillRenewalInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewal;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
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

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/insuranceBillRenewal")
public class InsuranceBillRenewalController extends BaseController {
	
	@Autowired
	private BaseDataInfoService baseDataInfoService;
	
	@Autowired
	protected InsuranceBillRenewalService insuranceBillRenewalService;
	
	@Autowired
	protected BusinessBillCustomerService businessBillCustomerService;
	
	@Autowired
	protected InsuranceContactLetterService insuranceContactLetterService;
	
	@Autowired
	protected BusinessBillUnderInforService  businessBillUnderInforService;
	
	@Autowired
	protected BusinessBillCoinsInfoService businessBillCoinsInfoService;
	
	@Autowired
	protected BusinessBillReinsInfoService businessBillReinsInfoService;
	
	@Autowired
	protected BrokerageFeeService brokerageFeeService;
	
	@Autowired
	protected CommissionInfoService commissionInfoService;
	
	
	@Autowired
	protected BusinessBillPayPlanService  businessBillPayPlanService;
	
	@Autowired
	protected BusinessBillService businessBillService;
	private final static String VM_PATH = "/insurance/insuranceBillRenewal/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "tradingTime"};
	}

	/**
	* 保单续期
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model,@RequestParam(value = "businessBillId", required = false, defaultValue = "0") long businessBillId) {
		try {
			if (businessBillId > 0) {
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
					//缴费计划查询
				 List<BillPayPlanYear>  billPayPlanInfos = businessBillPayPlanService.findAllBusinessBillPayPlan(businessBillId);
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
				//缴费计划查询
				List<BillPayPlanYear>  businessBillPayPlanInfos = businessBillPayPlanService.findAllBusinessBillPayPlan(businessBillId);
			    model.addAttribute("payPlanInfos", billPayPlanInfos);
				model.addAttribute("brokerageFeeInfo", brokerageFeeInfo);
				model.addAttribute("commissionInfoInfo", commissionInfoInfo);
				model.addAttribute("billCustomers", billCustomerInfos);
				model.addAttribute("billCoins", billCoins);
				model.addAttribute("billReins", billReins);
				model.addAttribute("billPayPlanInfos", billPayPlanInfos);
				model.addAttribute("billUnderInforInfos", billUnderInforInfos);
				model.addAttribute("businessBillPayPlanInfos", businessBillPayPlanInfos);
				
			}
			List<InsuranceBillRenewal> batchResult = insuranceBillRenewalService.findInsuranceBillPayPlan(businessBillId);
			model.addAttribute("planList", batchResult);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInsuranceBillRenewal.vm";
	}
	
	private List<BaseDataInfoInfo> getBaseDataInfoInfos(BaseDataInfoTypeEnum typeEnum) {
		BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
		queryOrder.setPageSize(99);
		queryOrder.setType(typeEnum);
		return baseDataInfoService.queryList(queryOrder).getPageList();
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "billRenewalId", required = false, defaultValue = "0") long billRenewalId,
    HttpServletRequest request, Model model) {
		InsuranceBillRenewalInfo info = null;
		if (billRenewalId > 0) {
			info = insuranceBillRenewalService.findById(billRenewalId);
			model.addAttribute("info", info);
	    }else{
			info = new InsuranceBillRenewalInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceBillRenewal.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceBillRenewalOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "insuranceBillRenewal";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceBillRenewalService.save(order);
			json = toJSONResult(result, tipPrefix);
			if (result.isSuccess()) {
				InsuranceBillRenewalInfo insuranceBillRenewalInfo = insuranceBillRenewalService.findInsuranceBillRenewal(Integer.parseInt(order.getBusinessBillId()),Integer.parseInt(order.getPeriod()));
				json.put("insuranceBillRenewalInfo", insuranceBillRenewalInfo);
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long billRenewalId, HttpServletRequest request, Model model) {
		InsuranceBillRenewalInfo insuranceBillRenewalInfo = insuranceBillRenewalService.findById(billRenewalId);
		model.addAttribute("info",insuranceBillRenewalInfo);
		return VM_PATH + "viewInsuranceBillRenewal.vm";
    }
    
	@ResponseBody
	@RequestMapping("insuranceBillRenewal.json")
	public Object insuranceBillRenewal(HttpServletRequest request, BusinessBillOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "续期";
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			InsuranceBaseResult result = insuranceBillRenewalService.insuranceBillRenewal(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }


}
