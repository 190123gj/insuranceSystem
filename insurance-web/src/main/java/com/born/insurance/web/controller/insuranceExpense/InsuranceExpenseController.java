package com.born.insurance.web.controller.insuranceExpense;

import javax.servlet.http.HttpServletRequest;

import com.born.insurance.ws.enums.InsuranceProtocolTypeEnum;
import com.born.insurance.ws.info.insuranceProtocolInfo.InsuranceProtocolInfoInfo;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoQueryOrder;
import com.born.insurance.ws.service.insuranceProtocolInfo.InsuranceProtocolInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceProtocol.InsuranceProtocolInfo;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolOrder;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceProtocol.InsuranceProtocolService;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/insuranceExpense")
public class InsuranceExpenseController extends BaseController {
	@Autowired
	protected InsuranceProtocolService insuranceProtocolService;

	@Autowired
	protected InsuranceProtocolInfoService insuranceProtocolInfoService;

	private final static String VM_PATH = "/insurance/insuranceExpense/";
	
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
			queryOrder.setType(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode());
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setQueryChargeInfo(false);
			QueryBaseBatchResult<InsuranceProtocolInfoInfo> batchResult = insuranceProtocolInfoService
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listInsuranceExpense.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "protocolId", required = false, defaultValue = "0") long protocolId,
						HttpServletRequest request, Model model) {
		InsuranceProtocolInfo info = null;
		if (protocolId > 0) {
			info = insuranceProtocolService.findById(protocolId);
			model.addAttribute("info", info);
		} else {
			info = new InsuranceProtocolInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceExpense.vm";
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceProtocolOrder order) {
		JSONObject json = new JSONObject();
		
		String tipPrefix = "费用政策";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceProtocolService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("view.htm")
	public String view(long protocolId, HttpServletRequest request, Model model) {
		InsuranceProtocolInfo insuranceProtocolInfo = insuranceProtocolService.findById(protocolId);
		model.addAttribute("info", insuranceProtocolInfo);
		return VM_PATH + "viewInsuranceExpense.vm";
	}
	
}
