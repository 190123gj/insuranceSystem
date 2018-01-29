package com.born.insurance.web.controller.priceContactLetterSchemeDetail;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/priceContactLetterSchemeDetail")
public class PriceContactLetterSchemeDetailController extends BaseController {
	@Autowired
	protected PriceContactLetterSchemeDetailService priceContactLetterSchemeDetailService;
	private final static String VM_PATH = "/insurance/priceContactLetterSchemeDetail/";

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
			PriceContactLetterSchemeDetailQueryOrder queryOrder = new PriceContactLetterSchemeDetailQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<PriceContactLetterSchemeDetailInfo> batchResult = priceContactLetterSchemeDetailService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPriceContactLetterSchemeDetail.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PriceContactLetterSchemeDetailInfo info = null;
		if (id > 0) {
			info = priceContactLetterSchemeDetailService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PriceContactLetterSchemeDetailInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPriceContactLetterSchemeDetail.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PriceContactLetterSchemeDetailOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "priceContactLetterSchemeDetail";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = priceContactLetterSchemeDetailService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PriceContactLetterSchemeDetailInfo priceContactLetterSchemeDetailInfo = priceContactLetterSchemeDetailService.findById(id);
		model.addAttribute("info",priceContactLetterSchemeDetailInfo);
		return VM_PATH + "viewPriceContactLetterSchemeDetail.vm";
    }


}
