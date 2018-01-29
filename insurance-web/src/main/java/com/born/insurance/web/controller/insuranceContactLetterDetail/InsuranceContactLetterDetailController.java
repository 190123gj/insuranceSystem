package com.born.insurance.web.controller.insuranceContactLetterDetail;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.insuranceContactLetterDetail.InsuranceContactLetterDetailOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.order.insuranceContactLetterDetail.InsuranceContactLetterDetailQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.insuranceContactLetterDetail.InsuranceContactLetterDetailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/insuranceContactLetterDetail")
public class InsuranceContactLetterDetailController extends BaseController {
	@Autowired
	protected InsuranceContactLetterDetailService insuranceContactLetterDetailService;
	private final static String VM_PATH = "/insurance/insuranceContactLetterDetail/";

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
			InsuranceContactLetterDetailQueryOrder queryOrder = new InsuranceContactLetterDetailQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InsuranceContactLetterDetailInfo> batchResult = insuranceContactLetterDetailService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInsuranceContactLetterDetail.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "letterDetailId", required = false, defaultValue = "0") long letterDetailId,
    HttpServletRequest request, Model model) {
		InsuranceContactLetterDetailInfo info = null;
		if (letterDetailId > 0) {
			info = insuranceContactLetterDetailService.findById(letterDetailId);
			model.addAttribute("info", info);
	    }else{
			info = new InsuranceContactLetterDetailInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceContactLetterDetail.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceContactLetterDetailOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "insuranceContactLetterDetail";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceContactLetterDetailService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long letterDetailId, HttpServletRequest request, Model model) {
		InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo = insuranceContactLetterDetailService.findById(letterDetailId);
		model.addAttribute("info",insuranceContactLetterDetailInfo);
		return VM_PATH + "viewInsuranceContactLetterDetail.vm";
    }


}
