package com.born.insurance.web.controller.priceContactLetterReportPrice;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceInfo;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.priceContactLetterReportPrice.PriceContactLetterReportPriceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/priceContactLetterReportPrice")
public class PriceContactLetterReportPriceController extends BaseController {
	@Autowired
	protected PriceContactLetterReportPriceService priceContactLetterReportPriceService;
	private final static String VM_PATH = "/insurance/priceContactLetterReportPrice/";

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
			PriceContactLetterReportPriceQueryOrder queryOrder = new PriceContactLetterReportPriceQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<PriceContactLetterCompanyReportPriceInfo> batchResult = priceContactLetterReportPriceService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPriceContactLetterReportPrice.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PriceContactLetterCompanyReportPriceInfo info = null;
		if (id > 0) {
			info = priceContactLetterReportPriceService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PriceContactLetterCompanyReportPriceInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPriceContactLetterReportPrice.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PriceContactLetterCompanyReportPriceOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "priceContactLetterReportPrice";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = priceContactLetterReportPriceService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PriceContactLetterCompanyReportPriceInfo priceContactLetterReportPriceInfo = priceContactLetterReportPriceService.findById(id);
		model.addAttribute("info",priceContactLetterReportPriceInfo);
		return VM_PATH + "viewPriceContactLetterReportPrice.vm";
    }


}
