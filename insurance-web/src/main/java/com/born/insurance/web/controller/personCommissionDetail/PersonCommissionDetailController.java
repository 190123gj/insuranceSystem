package com.born.insurance.web.controller.personCommissionDetail;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceBillRenewal.InsuranceBillRenewalInfo;
import com.born.insurance.ws.info.personCommissionDetail.PersonCommissionDetailInfo;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.personCommissionDetail.PersonCommissionDetailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/personCommissionDetail")
public class PersonCommissionDetailController extends BaseController {
	@Autowired
	protected PersonCommissionDetailService personCommissionDetailService;
	private final static String VM_PATH = "/insurance/personCommissionDetail/";

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
			PersonCommissionDetailQueryOrder queryOrder = new PersonCommissionDetailQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<PersonCommissionDetailInfo> batchResult = personCommissionDetailService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPersonCommissionDetail.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		PersonCommissionDetailInfo info = null;
		if (id > 0) {
			info = personCommissionDetailService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new PersonCommissionDetailInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPersonCommissionDetail.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PersonCommissionDetailOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "personCommissionDetail";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = personCommissionDetailService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		PersonCommissionDetailInfo personCommissionDetailInfo = personCommissionDetailService.findById(id);
		model.addAttribute("info",personCommissionDetailInfo);
		return VM_PATH + "viewPersonCommissionDetail.vm";
    }

	/**
	 * 获取佣金明细
	 * @return
	 */
	@RequestMapping("getPersonCommissionDetail.json")
	@ResponseBody
	public InsuranceBaseResult getPersonCommissionDetail(PersonCommissionDetailOrder personCommissionDetailOrder){
		InsuranceBaseResult result = new InsuranceBaseResult();
		List<PersonCommissionDetailInfo> personCommissionDetailInfo = null ;
		try {
			personCommissionDetailInfo = personCommissionDetailService.getPersonCommissionDetail(personCommissionDetailOrder);
			result.setSuccess(true);
			result.setReturnObject(personCommissionDetailInfo);
		} catch (NumberFormatException e) {
			result.setSuccess(false);
			result.setMessage("查询佣金明细信息失败");
			logger.error("查询佣金明细信息异常", e);
		}
		return result;
	}
}
