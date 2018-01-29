package com.born.insurance.web.controller.system;

import com.born.insurance.biz.service.common.info.SysParamInfo;
import com.born.insurance.ws.order.sysParam.SysParamOrder;
import com.born.insurance.ws.order.sysParam.SysParamQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;

/**
 * 系统参数controller
 * @author wuzj
 *
 */
@Controller
@RequestMapping("systemMg/sysparam")
public class SysParamController extends BaseController {
	
	private final String vm_path = "/systemMg/sysparam/";
	
	@RequestMapping("list.htm")
	public String list(SysParamQueryOrder order, Model model) {
		model.addAttribute("queryOrder", order);
		model.addAttribute("page",
			PageUtil.getCovertPage(sysParameterService.querySysPram(order)));
		return vm_path + "list.vm";
	}
	
	@RequestMapping("add.json")
	@ResponseBody
	public JSONObject add(SysParamOrder order) {
		SysParamInfo info = sysParameterService.getSysParameterValueDO(order.getParamName());
		InsuranceBaseResult result = null;
		if (info != null) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "参数已存在");
			return json;
		} else {
			result = sysParameterService.insertSysParameterValueDO(order);
		}
		return toJSONResult(result);
	}
	
	@RequestMapping("mod.json")
	@ResponseBody
	public JSONObject mod(SysParamOrder order) {
		SysParamInfo info = sysParameterService.getSysParameterValueDO(order.getParamName());
		InsuranceBaseResult result = null;
		if (info == null) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("message", "参数不存在");
			return json;
		} else {
			result = sysParameterService.updateSysParameterValueDO(order);
		}
		return toJSONResult(result);
	}
	
	@RequestMapping("delete.json")
	@ResponseBody
	public JSONObject delete(String paramName) {
		JSONObject result = new JSONObject();
		try {
			sysParameterService.deleteSysParameterValue(paramName);
			result.put("success", true);
			result.put("message", "删除成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "删除出错");
			logger.error("删除系统参数 {} 出错:{}", paramName, e);
		}
		return result;
	}
	
}
