package com.born.insurance.web.controller.billSettlementApplyDetail;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.billSettlementApplyDetail.BillSettlementApplyDetailInfo;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.billSettlementApplyDetail.BillSettlementApplyDetailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/billSettlementApplyDetail")
public class BillSettlementApplyDetailController extends BaseController {
	@Autowired
	protected BillSettlementApplyDetailService billSettlementApplyDetailService;
	private final static String VM_PATH = "/insurance/billSettlementApplyDetail/";

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
			BillSettlementApplyDetailQueryOrder queryOrder = new BillSettlementApplyDetailQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BillSettlementApplyDetailInfo> batchResult = billSettlementApplyDetailService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBillSettlementApplyDetail.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		BillSettlementApplyDetailInfo info = null;
		if (id > 0) {
			info = billSettlementApplyDetailService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new BillSettlementApplyDetailInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBillSettlementApplyDetail.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BillSettlementApplyDetailOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "billSettlementApplyDetail";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = billSettlementApplyDetailService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		BillSettlementApplyDetailInfo billSettlementApplyDetailInfo = billSettlementApplyDetailService.findById(id);
		model.addAttribute("info",billSettlementApplyDetailInfo);
		return VM_PATH + "viewBillSettlementApplyDetail.vm";
    }


}
