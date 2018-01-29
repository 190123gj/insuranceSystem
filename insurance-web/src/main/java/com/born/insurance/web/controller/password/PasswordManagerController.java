package com.born.insurance.web.controller.password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.ws.order.UpdatePasswordOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.user.PasswordManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.FrontAutowiredBaseController;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("userHome")
public class PasswordManagerController extends FrontAutowiredBaseController {
	
	private static String vm_path = "/mainPage/";
	@Autowired
	PasswordManagerService passwordManagerService;
	
	@RequestMapping("modifyPassword.htm")
	public String modifyPassword(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		
		return vm_path + "modifyPassword.vm";
	}
	
	@RequestMapping("modifyPasswordSubmit.htm")
	public String modifyPasswordSubmit(HttpServletRequest request, HttpServletResponse response,
										Model model) {
		JSONObject jsonObject = new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		String relpassword = request.getParameter("relpassword");
		if (StringUtil.equals(oldpassword, ShiroSessionUtils.getSessionLocal().getPassword())) {
			UpdatePasswordOrder passwordOrder = new UpdatePasswordOrder();
			passwordOrder.setNewPassword(newpassword);
			passwordOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
			InsuranceBaseResult result = passwordManagerService.updateUserPassword(passwordOrder);
			if (result.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "密码修改成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", result.getMessage());
			}
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "原密码输入不正确");
		}
		printHttpResponse(response, jsonObject);
		return null;
	}
}
