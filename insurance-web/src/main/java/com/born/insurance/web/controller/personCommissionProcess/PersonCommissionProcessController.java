package com.born.insurance.web.controller.personCommissionProcess;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.personCommissionProcess.PersonCommissionProcessOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.SettlementStatusEnum;
import com.born.insurance.ws.info.personCommissionProcess.PersonCommissionProcessInfo;
import com.born.insurance.ws.order.personCommissionProcess.PersonCommissionProcessQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.personCommissionProcess.PersonCommissionProcessService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/personCommissionProcess")
public class PersonCommissionProcessController extends BaseController {
	@Autowired
	protected PersonCommissionProcessService personCommissionProcessService;
	private final static String VM_PATH = "/insurance/personCommissionProcess/";

	/**
	* 佣金结算申请处理
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			PersonCommissionProcessQueryOrder queryOrder = new PersonCommissionProcessQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<PersonCommissionProcessInfo> batchResult = personCommissionProcessService.queryList(queryOrder);
			List<SettlementStatusEnum> settlementStatus = SettlementStatusEnum.getAllEnum();
			model.addAttribute("settlementStatus", settlementStatus);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPersonCommissionProcess.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PersonCommissionProcessInfo info = null;
		if (id > 0) {
			info = personCommissionProcessService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PersonCommissionProcessInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPersonCommissionProcess.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PersonCommissionProcessOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "结算";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = personCommissionProcessService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PersonCommissionProcessInfo personCommissionProcessInfo = personCommissionProcessService.findById(id);
		model.addAttribute("info",personCommissionProcessInfo);
		return VM_PATH + "viewPersonCommissionProcess.vm";
    }


}
