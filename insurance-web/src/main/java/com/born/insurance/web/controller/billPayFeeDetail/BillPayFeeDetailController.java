package com.born.insurance.web.controller.billPayFeeDetail;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.billPayFeeDetail.BillPayFeeDetailInfo;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.billPayFeeDetail.BillPayFeeDetailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/billPayFeeDetail")
public class BillPayFeeDetailController extends BaseController {
	@Autowired
	protected BillPayFeeDetailService billPayFeeDetailService;
	private final static String VM_PATH = "/insurance/billPayFeeDetail/";

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
			BillPayFeeDetailQueryOrder queryOrder = new BillPayFeeDetailQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BillPayFeeDetailInfo> batchResult = billPayFeeDetailService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBillPayFeeDetail.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		BillPayFeeDetailInfo info = null;
		if (id > 0) {
			info = billPayFeeDetailService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new BillPayFeeDetailInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBillPayFeeDetail.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BillPayFeeDetailOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "billPayFeeDetail";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = billPayFeeDetailService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		BillPayFeeDetailInfo billPayFeeDetailInfo = billPayFeeDetailService.findById(id);
		model.addAttribute("info",billPayFeeDetailInfo);
		return VM_PATH + "viewBillPayFeeDetail.vm";
    }


}
