package com.born.insurance.web.controller.priceContactLetterScheme;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.priceContactLetterScheme.PriceContactLetterSchemeOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.priceContactLetterScheme.PriceContactLetterSchemeInfo;
import com.born.insurance.ws.order.priceContactLetterScheme.PriceContactLetterSchemeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.priceContactLetterScheme.PriceContactLetterSchemeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/priceContactLetterScheme")
public class PriceContactLetterSchemeController extends BaseController {
	@Autowired
	protected PriceContactLetterSchemeService priceContactLetterSchemeService;
	private final static String VM_PATH = "/insurance/priceContactLetterScheme/";

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
			PriceContactLetterSchemeQueryOrder queryOrder = new PriceContactLetterSchemeQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<PriceContactLetterSchemeInfo> batchResult = priceContactLetterSchemeService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPriceContactLetterScheme.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PriceContactLetterSchemeInfo info = null;
		if (id > 0) {
			info = priceContactLetterSchemeService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PriceContactLetterSchemeInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPriceContactLetterScheme.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PriceContactLetterSchemeOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "priceContactLetterScheme";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = priceContactLetterSchemeService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PriceContactLetterSchemeInfo priceContactLetterSchemeInfo = priceContactLetterSchemeService.findById(id);
		model.addAttribute("info",priceContactLetterSchemeInfo);
		return VM_PATH + "viewPriceContactLetterScheme.vm";
    }


}
