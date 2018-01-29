package com.born.insurance.web.controller.businessBillCustomer;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillCustomer.BusinessBillCustomerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillCustomer")
public class BusinessBillCustomerController extends BaseController {
	@Autowired
	protected BusinessBillCustomerService businessBillCustomerService;
	private final static String VM_PATH = "/insurance/businessBillCustomer/";

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
			BusinessBillCustomerQueryOrder queryOrder = new BusinessBillCustomerQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillCustomerInfo> batchResult = businessBillCustomerService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillCustomer.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "billCustomerId", required = false, defaultValue = "0") long billCustomerId,
    HttpServletRequest request, Model model) {
		BusinessBillCustomerInfo info = null;
		if (billCustomerId > 0) {
			info = businessBillCustomerService.findById(billCustomerId);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillCustomerInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillCustomer.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillCustomerOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillCustomer";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillCustomerService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long billCustomerId, HttpServletRequest request, Model model) {
		BusinessBillCustomerInfo businessBillCustomerInfo = businessBillCustomerService.findById(billCustomerId);
		model.addAttribute("info",businessBillCustomerInfo);
		return VM_PATH + "viewBusinessBillCustomer.vm";
    }


}
