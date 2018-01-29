package com.born.insurance.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;


@Controller
@RequestMapping("systemMg")
public class SystemHomeController extends BaseController {
	

	
	final static String vm_path = "/systemMg/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	@RequestMapping("clearCache.json")
	@ResponseBody
	public JSONObject clearCache() {
		JSONObject result = new JSONObject();
		try {
//			sysClearCacheServicet.clearCache();
			result.put("success", true);
			result.put("message", "清除缓存成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "清除缓存出错");
			logger.error("清除缓存出错:{}", e);
		}
		return result;
	}
	


}
