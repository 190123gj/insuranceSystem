package com.born.insurance.web.controller.businessBillReinsInfo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.businessBillReinsInfo.BusinessBillReinsInfoInfo;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillReinsInfo.BusinessBillReinsInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillReinsInfo")
public class BusinessBillReinsInfoController extends BaseController {
	@Autowired
	protected BusinessBillReinsInfoService businessBillReinsInfoService;
	private final static String VM_PATH = "/insurance/businessBillReinsInfo/";

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
			BusinessBillReinsInfoQueryOrder queryOrder = new BusinessBillReinsInfoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillReinsInfoInfo> batchResult = businessBillReinsInfoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillReinsInfo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "reinsInfoId", required = false, defaultValue = "0") long reinsInfoId,
    HttpServletRequest request, Model model) {
		BusinessBillReinsInfoInfo info = null;
		if (reinsInfoId > 0) {
			info = businessBillReinsInfoService.findById(reinsInfoId);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillReinsInfoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillReinsInfo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillReinsInfoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillReinsInfo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillReinsInfoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long reinsInfoId, HttpServletRequest request, Model model) {
		BusinessBillReinsInfoInfo businessBillReinsInfoInfo = businessBillReinsInfoService.findById(reinsInfoId);
		model.addAttribute("info",businessBillReinsInfoInfo);
		return VM_PATH + "viewBusinessBillReinsInfo.vm";
    }


}
