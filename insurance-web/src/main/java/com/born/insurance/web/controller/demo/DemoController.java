package com.born.insurance.web.controller.demo;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.demo.DemoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.demo.DemoInfo;
import com.born.insurance.ws.order.demo.DemoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/demo")
public class DemoController extends WorkflowBaseController {
	@Autowired
	protected DemoService demoService;
	private final static String VM_PATH = "/insurance/demo/";

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
			DemoQueryOrder queryOrder = new DemoQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<DemoInfo> batchResult = demoService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listDemo.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		DemoInfo info = null;
		if (id > 0) {
			info = demoService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new DemoInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addDemo.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, DemoOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "demo";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = demoService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		DemoInfo demoInfo = demoService.findById(id);
		model.addAttribute("info",demoInfo);
		return VM_PATH + "viewDemo.vm";
    }


	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formService.findByFormId(formId);
		if (null != form) {
			DemoInfo demoInfo = demoService.findByFormId(formId);
			model.addAttribute("info",demoInfo);
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		return VM_PATH + "auditForm.vm";
	}


}
