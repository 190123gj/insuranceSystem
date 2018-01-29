package com.born.insurance.web.controller.priceContactLetterDemandDetailTwo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoInfo;
import com.born.insurance.ws.order.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/priceContactLetterDemandDetailTwo")
public class PriceContactLetterDemandDetailTwoController extends BaseController {
	@Autowired
	protected PriceContactLetterDemandDetailTwoService priceContactLetterDemandDetailTwoService;
	private final static String VM_PATH = "/insurance/priceContactLetterDemandDetailTwo/";

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
			PriceContactLetterDemandDetailTwoQueryOrder queryOrder = new PriceContactLetterDemandDetailTwoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<PriceContactLetterDemandDetailTwoInfo> batchResult = priceContactLetterDemandDetailTwoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPriceContactLetterDemandDetailTwo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PriceContactLetterDemandDetailTwoInfo info = null;
		if (id > 0) {
			info = priceContactLetterDemandDetailTwoService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PriceContactLetterDemandDetailTwoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPriceContactLetterDemandDetailTwo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PriceContactLetterDemandDetailTwoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "priceContactLetterDemandDetailTwo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = priceContactLetterDemandDetailTwoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PriceContactLetterDemandDetailTwoInfo priceContactLetterDemandDetailTwoInfo = priceContactLetterDemandDetailTwoService.findById(id);
		model.addAttribute("info",priceContactLetterDemandDetailTwoInfo);
		return VM_PATH + "viewPriceContactLetterDemandDetailTwo.vm";
    }


}
