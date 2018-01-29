package com.born.insurance.web.controller.settlementCompanyBill;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.order.settlementCompanyBill.SettlementCompanyBillOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.SettlementCompanyStatusEnum;
import com.born.insurance.ws.enums.SettlementStatusEnum;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.settlementCompanyBill.SettlementCompanyBillInfo;
import com.born.insurance.ws.order.settlementCompanyBill.SettlementCompanyBillQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.settlementCompanyBill.SettlementCompanyBillService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/settlementCompanyBill")
public class SettlementCompanyBillController extends BaseController {
	@Autowired
	protected SettlementCompanyBillService settlementCompanyBillService;
	
	@Autowired
	protected CustomerCompanyService customerCompanyService;
	private final static String VM_PATH = "/insurance/settlementCompanyBill/";

	/**
	* 待经纪费结算列表
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			SettlementCompanyBillQueryOrder queryOrder = new SettlementCompanyBillQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<SettlementCompanyBillInfo> batchResult = settlementCompanyBillService.queryList(queryOrder);
			//保险公司
			CustomerCompanyQueryOrder customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
			customerCompanyQueryOrder.setCustomerType(CustomerTypeEnum.INSURANCE_INSTITUTIONS);
			customerCompanyQueryOrder.setPageSize(1000);
			QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult = customerCompanyService
				.queryList(customerCompanyQueryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("institutions", baseBatchResult.getPageList());
			model.addAttribute("settlementStatus",SettlementCompanyStatusEnum.getAllEnum());
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listSettlementCompanyBill.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "settlementCompanyId", required = false, defaultValue = "0") long settlementCompanyId,
    HttpServletRequest request, Model model) {
		SettlementCompanyBillInfo info = null;
		if (settlementCompanyId > 0) {
			info = settlementCompanyBillService.findById(settlementCompanyId);
			model.addAttribute("info", info);
	    }else{
			info = new SettlementCompanyBillInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addSettlementCompanyBill.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, SettlementCompanyBillOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "settlementCompanyBill";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = settlementCompanyBillService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long settlementCompanyId, HttpServletRequest request, Model model) {
		SettlementCompanyBillInfo settlementCompanyBillInfo = settlementCompanyBillService.findById(settlementCompanyId);
		model.addAttribute("info",settlementCompanyBillInfo);
		return VM_PATH + "viewSettlementCompanyBill.vm";
    }


}
