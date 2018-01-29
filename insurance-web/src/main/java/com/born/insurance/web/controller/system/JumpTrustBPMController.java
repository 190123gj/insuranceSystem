package com.born.insurance.web.controller.system;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.integration.common.PropertyConfigService;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import com.born.insurance.web.controller.base.FrontAutowiredBaseController;


@Controller
@RequestMapping("JumpTrust")
public class JumpTrustBPMController extends FrontAutowiredBaseController {
	@Autowired
	PropertyConfigService propertyConfigService;
	
	private final String loginPrivateKey = "b@o_r2^n-b#p0M";
	
	@RequestMapping("gotobpm.htm")
	public String gotoBpm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String url = request.getParameter("url");
		String userName = ShiroSessionUtils.getSessionLocal().getUserName();
		long timestamp = new Date().getTime();
		String sign = MD5Util.getMD5_32(userName + String.valueOf(timestamp) + loginPrivateKey);
		String realUrl = propertyConfigService.getBmpRootUrl() + "/bornsso/login.do?userName="
							+ userName + "&sign=" + sign + "&timestamp="
							+ String.valueOf(timestamp) + "&targetUrl=" + url;
		//		String realUrl = "http://127.0.0.1:29440/bornbpm/bornsso/login.do?userName=" + userName
		//							+ "&sign=" + sign + "&timestamp=" + String.valueOf(timestamp)
		//							+ "&targetUrl=" + url;
		sendUrl(response, realUrl);
		return null;
	}
	
	@RequestMapping("makeLoginUrl.htm")
	public String makeLoginUrl(HttpServletRequest request, HttpServletResponse response, Model model) {
		String url = request.getParameter("url");
		String userName = ShiroSessionUtils.getSessionLocal().getUserName();
		long timestamp = new Date().getTime();
		String sign = MD5Util.getMD5_32(userName + String.valueOf(timestamp) + loginPrivateKey);
		String realUrl = propertyConfigService.getBmpRootUrl() + "/bornsso/login.do?userName="
							+ userName + "&sign=" + sign + "&timestamp="
							+ String.valueOf(timestamp);
		//		String realUrl = "http://127.0.0.1:29440/bornbpm/bornsso/login.do?userName=" + userName
		//							+ "&sign=" + sign + "&timestamp=" + String.valueOf(timestamp);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("loginUrl", realUrl);
		jsonObject.put("code", "1");
		printHttpResponse(response, jsonObject);
		return null;
	}
}
