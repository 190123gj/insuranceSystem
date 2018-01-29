package com.born.insurance.web.controller.businessBillPayPlan;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillPayPlan.BusinessBillPayPlanService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillPayPlan")
public class BusinessBillPayPlanController extends BaseController {
	@Autowired
	protected BusinessBillPayPlanService businessBillPayPlanService;
	private final static String VM_PATH = "/insurance/businessBillPayPlan/";

	/**
	* 风险预警处理表
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			BusinessBillPayPlanQueryOrder queryOrder = new BusinessBillPayPlanQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillPayPlanInfo> batchResult = businessBillPayPlanService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillPayPlan.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "payPlanId", required = false, defaultValue = "0") long payPlanId,
    HttpServletRequest request, Model model) {
		BusinessBillPayPlanInfo info = null;
		if (payPlanId > 0) {
			info = businessBillPayPlanService.findById(payPlanId);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillPayPlanInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillPayPlan.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillPayPlanOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillPayPlan";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillPayPlanService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long payPlanId, HttpServletRequest request, Model model) {
		BusinessBillPayPlanInfo businessBillPayPlanInfo = businessBillPayPlanService.findById(payPlanId);
		model.addAttribute("info",businessBillPayPlanInfo);
		return VM_PATH + "viewBusinessBillPayPlan.vm";
    }


}
