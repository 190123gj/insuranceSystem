package com.born.insurance.web.controller.insuranceProtocolInfo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceProtocolInfo.InsuranceProtocolInfoInfo;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.insuranceProtocolInfo.InsuranceProtocolInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/insuranceProtocolInfo")
public class InsuranceProtocolInfoController extends BaseController {
	@Autowired
	protected InsuranceProtocolInfoService insuranceProtocolInfoService;
	private final static String VM_PATH = "/insurance/insuranceProtocolInfo/";

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
			InsuranceProtocolInfoQueryOrder queryOrder = new InsuranceProtocolInfoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InsuranceProtocolInfoInfo> batchResult = insuranceProtocolInfoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInsuranceProtocolInfo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "protocolInfoId", required = false, defaultValue = "0") long protocolInfoId,
    HttpServletRequest request, Model model) {
		InsuranceProtocolInfoInfo info = null;
		if (protocolInfoId > 0) {
			info = insuranceProtocolInfoService.findById(protocolInfoId);
			model.addAttribute("info", info);
	    }else{
			info = new InsuranceProtocolInfoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceProtocolInfo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceProtocolInfoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "insuranceProtocolInfo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceProtocolInfoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long protocolInfoId, HttpServletRequest request, Model model) {
		InsuranceProtocolInfoInfo insuranceProtocolInfoInfo = insuranceProtocolInfoService.findById(protocolInfoId);
		model.addAttribute("info",insuranceProtocolInfoInfo);
		return VM_PATH + "viewInsuranceProtocolInfo.vm";
    }


}
