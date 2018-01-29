package com.born.insurance.web.controller.insuranceLiability;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceLiability.InsuranceLiabilityInfo;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.insuranceLiability.InsuranceLiabilityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/insuranceLiability")
public class InsuranceLiabilityController extends BaseController {
	@Autowired
	protected InsuranceLiabilityService insuranceLiabilityService;
	private final static String VM_PATH = "/insurance/insuranceLiability/";

	/**
	* 保险责任
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			InsuranceLiabilityQueryOrder queryOrder = new InsuranceLiabilityQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<InsuranceLiabilityInfo> batchResult = insuranceLiabilityService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInsuranceLiability.vm";
	}

	/**
	 * 编辑、保存保险页面
	 * @param liabilityId
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "liabilityId", required = false, defaultValue = "0") long liabilityId,
    HttpServletRequest request, Model model) {
		InsuranceLiabilityInfo info = null;
		if (liabilityId > 0) {
			info = insuranceLiabilityService.findById(liabilityId);
			model.addAttribute("info", info);
	    }else{
			info = new InsuranceLiabilityInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceLiability.vm";
	}

    /**
     * 确认编辑保存
     * @param request
     * @param order
     * @return
     */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceLiabilityOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "操作";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceLiabilityService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }
	
	/**
	 * 确认删除保险责任
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteConfirm.json")
	public Object deleteConfirm(HttpServletRequest request, InsuranceLiabilityOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "删除保险责任";
		try {
			InsuranceBaseResult result = insuranceLiabilityService.deleteInsuranceLiability(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

	/**
	 * 查看保险责任详情
	 * @param liabilityId
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping("view.htm")
    public String view(long liabilityId, HttpServletRequest request, Model model) {
		InsuranceLiabilityInfo insuranceLiabilityInfo = insuranceLiabilityService.findById(liabilityId);
		model.addAttribute("info",insuranceLiabilityInfo);
		return VM_PATH + "viewInsuranceLiability.vm";
    }


}
