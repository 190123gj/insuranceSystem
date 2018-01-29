package com.born.insurance.web.controller.basedata;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.born.insurance.biz.service.bpm.WorkflowEngineClient;

import com.born.insurance.ws.order.bpm.WorkflowProcessLog;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.born.insurance.web.controller.base.FrontAutowiredBaseController;


@Controller
@RequestMapping("baseDataLoad")
public class WorkflowBaseDataController extends FrontAutowiredBaseController {
	
	@Autowired
	WorkflowEngineClient workflowEngineClient;
	
	@ResponseBody
	@RequestMapping("loadWorkflowProcessList.json")
	public JSONObject loadWorkflowProcessList(HttpServletRequest request, String actInstId) {
		JSONObject jsonObject = new JSONObject();
		QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineClient
			.getProcessOpinionByActInstId(actInstId);
		jsonObject.put("success", true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", batchResult.getPageList());
		jsonObject.put("data", map);
		return jsonObject;
	}
}
