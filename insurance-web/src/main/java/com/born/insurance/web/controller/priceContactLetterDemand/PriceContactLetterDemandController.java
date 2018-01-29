package com.born.insurance.web.controller.priceContactLetterDemand;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.priceContactLetterDemand.PriceContactLetterDemandService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/priceContactLetterDemand")
public class PriceContactLetterDemandController extends BaseController {
	@Autowired
	protected PriceContactLetterDemandService priceContactLetterDemandService;
	private final static String VM_PATH = "/insurance/priceContactLetterDemand/";

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
			PriceContactLetterDemandQueryOrder queryOrder = new PriceContactLetterDemandQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<PriceContactLetterDemandInfo> batchResult = priceContactLetterDemandService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPriceContactLetterDemand.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PriceContactLetterDemandInfo info = null;
		if (id > 0) {
			info = priceContactLetterDemandService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PriceContactLetterDemandInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPriceContactLetterDemand.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PriceContactLetterDemandOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "priceContactLetterDemand";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = priceContactLetterDemandService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PriceContactLetterDemandInfo priceContactLetterDemandInfo = priceContactLetterDemandService.findById(id);
		model.addAttribute("info",priceContactLetterDemandInfo);
		return VM_PATH + "viewPriceContactLetterDemand.vm";
    }


}
