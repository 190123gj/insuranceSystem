package com.born.insurance.web.controller.baseDataInfo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/baseDataInfo")
public class BaseDataInfoController extends BaseController {
	@Autowired
	protected BaseDataInfoService baseDataInfoService;
	private final static String VM_PATH = "/insurance/baseDataInfo/";

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
			BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BaseDataInfoInfo> batchResult = baseDataInfoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBaseDataInfo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		BaseDataInfoInfo info = null;
		if (id > 0) {
			info = baseDataInfoService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new BaseDataInfoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBaseDataInfo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BaseDataInfoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "baseDataInfo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = baseDataInfoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		BaseDataInfoInfo baseDataInfoInfo = baseDataInfoService.findById(id);
		model.addAttribute("info",baseDataInfoInfo);
		return VM_PATH + "viewBaseDataInfo.vm";
    }


}
