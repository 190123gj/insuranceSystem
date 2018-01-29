package com.born.insurance.web.controller.businessBillCoinsInfo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.businessBillCoinsInfo.BusinessBillCoinsInfoInfo;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillCoinsInfo.BusinessBillCoinsInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillCoinsInfo")
public class BusinessBillCoinsInfoController extends BaseController {
	@Autowired
	protected BusinessBillCoinsInfoService businessBillCoinsInfoService;
	private final static String VM_PATH = "/insurance/businessBillCoinsInfo/";

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
			BusinessBillCoinsInfoQueryOrder queryOrder = new BusinessBillCoinsInfoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillCoinsInfoInfo> batchResult = businessBillCoinsInfoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillCoinsInfo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "coinsInfoId", required = false, defaultValue = "0") long coinsInfoId,
    HttpServletRequest request, Model model) {
		BusinessBillCoinsInfoInfo info = null;
		if (coinsInfoId > 0) {
			info = businessBillCoinsInfoService.findById(coinsInfoId);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillCoinsInfoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillCoinsInfo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillCoinsInfoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillCoinsInfo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillCoinsInfoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long coinsInfoId, HttpServletRequest request, Model model) {
		BusinessBillCoinsInfoInfo businessBillCoinsInfoInfo = businessBillCoinsInfoService.findById(coinsInfoId);
		model.addAttribute("info",businessBillCoinsInfoInfo);
		return VM_PATH + "viewBusinessBillCoinsInfo.vm";
    }


}
