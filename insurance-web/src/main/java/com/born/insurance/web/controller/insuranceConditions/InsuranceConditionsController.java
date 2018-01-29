package com.born.insurance.web.controller.insuranceConditions;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceConditions.InsuranceConditionsInfo;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.insuranceConditions.InsuranceConditionsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/insuranceConditions")
public class InsuranceConditionsController extends BaseController {
	@Autowired
	protected InsuranceConditionsService insuranceConditionsService;
	private final static String VM_PATH = "/insurance/insuranceConditions/";

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
			InsuranceConditionsQueryOrder queryOrder = new InsuranceConditionsQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InsuranceConditionsInfo> batchResult = insuranceConditionsService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInsuranceConditions.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "liabilityId", required = false, defaultValue = "0") long liabilityId,
    HttpServletRequest request, Model model) {
		InsuranceConditionsInfo info = null;
		if (liabilityId > 0) {
			info = insuranceConditionsService.findById(liabilityId);
			model.addAttribute("info", info);
	    }else{
			info = new InsuranceConditionsInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceConditions.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceConditionsOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "insuranceConditions";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceConditionsService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long liabilityId, HttpServletRequest request, Model model) {
		InsuranceConditionsInfo insuranceConditionsInfo = insuranceConditionsService.findById(liabilityId);
		model.addAttribute("info",insuranceConditionsInfo);
		return VM_PATH + "viewInsuranceConditions.vm";
    }


}
