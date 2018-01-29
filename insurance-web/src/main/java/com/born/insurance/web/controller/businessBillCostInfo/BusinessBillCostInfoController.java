package com.born.insurance.web.controller.businessBillCostInfo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.businessBillCostInfo.BusinessBillCostInfoInfo;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillCostInfo.BusinessBillCostInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillCostInfo")
public class BusinessBillCostInfoController extends BaseController {
	@Autowired
	protected BusinessBillCostInfoService businessBillCostInfoService;
	private final static String VM_PATH = "/insurance/businessBillCostInfo/";

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
			BusinessBillCostInfoQueryOrder queryOrder = new BusinessBillCostInfoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillCostInfoInfo> batchResult = businessBillCostInfoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillCostInfo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "costInfoId", required = false, defaultValue = "0") long costInfoId,
    HttpServletRequest request, Model model) {
		BusinessBillCostInfoInfo info = null;
		if (costInfoId > 0) {
			info = businessBillCostInfoService.findById(costInfoId);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillCostInfoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillCostInfo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillCostInfoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillCostInfo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillCostInfoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long costInfoId, HttpServletRequest request, Model model) {
		BusinessBillCostInfoInfo businessBillCostInfoInfo = businessBillCostInfoService.findById(costInfoId);
		model.addAttribute("info",businessBillCostInfoInfo);
		return VM_PATH + "viewBusinessBillCostInfo.vm";
    }


}
