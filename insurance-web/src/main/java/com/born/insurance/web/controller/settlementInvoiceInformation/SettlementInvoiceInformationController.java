package com.born.insurance.web.controller.settlementInvoiceInformation;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.settlementInvoiceInformation.SettlementInvoiceInformationOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.settlementInvoiceInformation.SettlementInvoiceInformationInfo;
import com.born.insurance.ws.order.settlementInvoiceInformation.SettlementInvoiceInformationQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.settlementInvoiceInformation.SettlementInvoiceInformationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/settlementInvoiceInformation")
public class SettlementInvoiceInformationController extends BaseController {
	@Autowired
	protected SettlementInvoiceInformationService settlementInvoiceInformationService;
	private final static String VM_PATH = "/insurance/settlementInvoiceInformation/";
	
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "invoiceIssuingTime"};
	}

	/**
	* 结算单对应的发票信息
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			SettlementInvoiceInformationQueryOrder queryOrder = new SettlementInvoiceInformationQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<SettlementInvoiceInformationInfo> batchResult = settlementInvoiceInformationService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listSettlementInvoiceInformation.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		SettlementInvoiceInformationInfo info = null;
		if (id > 0) {
			info = settlementInvoiceInformationService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new SettlementInvoiceInformationInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addSettlementInvoiceInformation.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, SettlementInvoiceInformationOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "发票确认";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = settlementInvoiceInformationService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		SettlementInvoiceInformationInfo settlementInvoiceInformationInfo = settlementInvoiceInformationService.findById(id);
		model.addAttribute("info",settlementInvoiceInformationInfo);
		return VM_PATH + "viewSettlementInvoiceInformation.vm";
    }


}
