package com.born.insurance.web.controller.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.insurance.web.controller.base.BaseController;


@Controller
@RequestMapping("/business")
public class BusinessHomeController extends BaseController {
	

	final static String vm_path = "/insurance/business/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
}
