package com.born.insurance.web.controller.salesAreas;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.salesAreas.SalesAreasOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.salesAreas.SalesAreasInfo;
import com.born.insurance.ws.order.salesAreas.SalesAreasQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.salesAreas.SalesAreasService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/salesAreas")
public class SalesAreasController extends BaseController {
	@Autowired
	protected SalesAreasService salesAreasService;
	private final static String VM_PATH = "/insurance/salesAreas/";

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
			SalesAreasQueryOrder queryOrder = new SalesAreasQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<SalesAreasInfo> batchResult = salesAreasService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listSalesAreas.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		SalesAreasInfo info = null;
		if (id > 0) {
			info = salesAreasService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new SalesAreasInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addSalesAreas.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, SalesAreasOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "salesAreas";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = salesAreasService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		SalesAreasInfo salesAreasInfo = salesAreasService.findById(id);
		model.addAttribute("info",salesAreasInfo);
		return VM_PATH + "viewSalesAreas.vm";
    }


}
