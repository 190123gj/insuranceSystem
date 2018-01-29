package com.born.insurance.web.controller.businessBillUnderInfor;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.businessBillUnderInfor.BusinessBillUnderInforInfo;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillUnderInfor.BusinessBillUnderInforService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillUnderInfor")
public class BusinessBillUnderInforController extends BaseController {
	@Autowired
	protected BusinessBillUnderInforService businessBillUnderInforService;
	private final static String VM_PATH = "/insurance/businessBillUnderInfor/";

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
			BusinessBillUnderInforQueryOrder queryOrder = new BusinessBillUnderInforQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillUnderInforInfo> batchResult = businessBillUnderInforService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillUnderInfor.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "underInfoId", required = false, defaultValue = "0") long underInfoId,
    HttpServletRequest request, Model model) {
		BusinessBillUnderInforInfo info = null;
		if (underInfoId > 0) {
			info = businessBillUnderInforService.findById(underInfoId);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillUnderInforInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillUnderInfor.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillUnderInforOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillUnderInfor";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillUnderInforService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long underInfoId, HttpServletRequest request, Model model) {
		BusinessBillUnderInforInfo businessBillUnderInforInfo = businessBillUnderInforService.findById(underInfoId);
		model.addAttribute("info",businessBillUnderInforInfo);
		return VM_PATH + "viewBusinessBillUnderInfor.vm";
    }


}
