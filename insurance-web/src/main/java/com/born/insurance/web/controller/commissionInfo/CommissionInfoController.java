package com.born.insurance.web.controller.commissionInfo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.commissionInfo.CommissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/commissionInfo")
public class CommissionInfoController extends BaseController {
	@Autowired
	protected CommissionInfoService commissionInfoService;
	private final static String VM_PATH = "/insurance/commissionInfo/";

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
			CommissionInfoQueryOrder queryOrder = new CommissionInfoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<CommissionInfoInfo> batchResult = commissionInfoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listCommissionInfo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		CommissionInfoInfo info = null;
		if (id > 0) {
			info = commissionInfoService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new CommissionInfoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addCommissionInfo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, CommissionInfoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "commissionInfo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = commissionInfoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		CommissionInfoInfo commissionInfoInfo = commissionInfoService.findById(id);
		model.addAttribute("info",commissionInfoInfo);
		return VM_PATH + "viewCommissionInfo.vm";
    }


}
