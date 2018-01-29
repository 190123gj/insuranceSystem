package com.born.insurance.web.controller.brokerageFee;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.brokerageFee.BrokerageFeeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/brokerageFee")
public class BrokerageFeeController extends BaseController {
	@Autowired
	protected BrokerageFeeService brokerageFeeService;
	private final static String VM_PATH = "/insurance/brokerageFee/";

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
			BrokerageFeeQueryOrder queryOrder = new BrokerageFeeQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BrokerageFeeInfo> batchResult = brokerageFeeService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBrokerageFee.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		BrokerageFeeInfo info = null;
		if (id > 0) {
			info = brokerageFeeService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new BrokerageFeeInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBrokerageFee.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BrokerageFeeOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "brokerageFee";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = brokerageFeeService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		BrokerageFeeInfo brokerageFeeInfo = brokerageFeeService.findById(id);
		model.addAttribute("info",brokerageFeeInfo);
		return VM_PATH + "viewBrokerageFee.vm";
    }


}
