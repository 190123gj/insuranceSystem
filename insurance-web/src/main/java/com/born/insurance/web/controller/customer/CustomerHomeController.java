package com.born.insurance.web.controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;


@Controller
@RequestMapping("/customer")
public class CustomerHomeController extends BaseController {
	

	final static String vm_path = "/insurance/customer/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
}
