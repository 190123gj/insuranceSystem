package com.born.insurance.web.controller.login;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.integration.bpm.user.LoginService;
import com.born.insurance.integration.bpm.user.SysUser;
import com.born.insurance.integration.bpm.user.UserDetailsServiceProxy;
import com.born.insurance.integration.enums.FunctionalModulesEnum;
import com.born.insurance.integration.result.LoginResult;
import com.born.insurance.integration.util.SessionConstant;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.MD5Util;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.SysParamEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.enums.bpm.UserStateEnum;
import com.born.insurance.ws.order.LoginOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.WebUtil;
import com.google.common.collect.Maps;
import com.yjf.common.env.Env;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.result.Result;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	final static String vm_path = "/platform/login/";
	
	private static String LOGIN_MESSAGE = "用户名或者密码错误";
	
	private static String LOGIN_MESSAGE_DISABLE = "该用户不可用...";
	/**
	 * 账户已被锁定
	 */
	private static String LOGIN_MESSAGE_LOCKED = "此账户已被锁定，请等待解锁";
	/**
	 * 账户不存在
	 */
	private static String LOGIN_USER_UNKNOWN = "账户不存在";
	
	@Autowired
	LoginService loginService;
	
	static String path = "login/";
	
	@RequestMapping("toLogin.htm")
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//TODO
		return path + "login.vm";
	}
	
	@RequestMapping("login.htm")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model,
						String redirect) {
		
		redirect = checkRedirect(redirect);
		if (StringUtil.isBlank(redirect)) {
			redirect = "/userHome/mainIndex.htm";
		}
		if (StringUtil.isBlank(request.getParameter("userName"))
			|| StringUtil.isBlank(request.getParameter("password"))) {
			//构建跳转用的参数
			model.addAttribute("redirectParamMap", makeRedirectParam(request));
			logger.info(request.getParameter("code"), request.getParameter("code"));
			logger.info(request.getParameter("message"), request.getParameter("message"));
			model.addAttribute("code", request.getParameter("code"));
			model.addAttribute("message", request.getParameter("message"));
			
			//登出
			Subject subject = SecurityUtils.getSubject();
			try {
				if (null != subject) {
					ShiroSessionUtils.clear();
					subject.logout();
				}
				request.getSession().invalidate();
			} catch (Exception e) {
				logger.error("登出报错", e);
			}
			
			return path + "login.vm";
		}
		// 判断图片验证码是否正确
		String checkCaptcha = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_NOT_CHECK_CAPTCHA_NAME.code());
		if (StringUtil.isNotBlank(checkCaptcha) && BooleanEnum.YES.code().equals(checkCaptcha)) {
			// 不为空且标记为无需check就不做任何操作
			// donothing
		} else if (Env.isDev()
		//				|| Env.isTest()
		) {
			// dev 和text的时候不校验验证码
			// donothing
		} else {
			String captcha = request.getParameter("captcha");
			if (StringUtil.isEmpty(captcha)) {
				model.addAttribute("code", 0);
				model.addAttribute("message", "请填写图片验证码！");
				return path + "login.vm";
			}
			Session session = SecurityUtils.getSubject().getSession();
			String captchaSession = (String) session
				.getAttribute(SessionConstant.SESSION_KAPTCHA_KEY);
			if (!StringUtil.equalsIgnoreCase(captcha, captchaSession)) {
				model.addAttribute("code", 0);
				model.addAttribute("message", "图片验证码不正确");
				return path + "login.vm";
			}
			
		}
		
		LoginOrder loginOrder = new LoginOrder();
		WebUtil.setPoPropertyByRequest(loginOrder, request);
		loginOrder.setLoginIp(IPUtil.getIpAddr(request));
		LoginResult loginResult = loginService.login(loginOrder);
		
		if (loginResult.isSuccess()) {
			return sendUrl(response, redirect, makeRedirectParam(request));
		} else if (loginResult.getInsuranceResultEnum() == InsuranceResultEnum.HAVE_NOT_DATA) {
			model.addAttribute("code", 0);
			model.addAttribute("message", "账户名或密码错误");
			return path + "login.vm";
		} else if (loginResult.getInsuranceResultEnum() == InsuranceResultEnum.USER_DISABLE) {
			model.addAttribute("code", 0);
			//			model.addAttribute("message", LOGIN_MESSAGE_DISABLE);
			model.addAttribute("message", "此账户已锁定，请联系管理员。");
			return path + "login.vm";
		} else if (InsuranceResultEnum.FUNCTION_NOT_OPEN == loginResult.getInsuranceResultEnum()) {
			//  return 第一次登录修改密码页面
			//			model.addAttribute("userName", request.getParameter("userName"));
			//			return path + "activation.vm";
			// 先置为自动激活
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			String key = "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			String account = loginOrder.getUserName();
			String password = account + "active" + key;
			password = MD5Util.getMD5_32(password);
			
			try {
				// 激活后默认无问题，直接登录并跳转
				String str = serviceProxy.login(account, password, null, null);
				LoginResult result2 = loginService.login(loginOrder);
				if (result2.isSuccess()) {
					return sendUrl(response, redirect, makeRedirectParam(request));
				} else {
					model.addAttribute("code", 0);
					model.addAttribute("message", result2.getMessage());
					return path + "login.vm";
				}
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("code", 0);
				model.addAttribute("message", e.getMessage());
				return path + "login.vm";
			}
			//return sendUrl(response, redirect, makeRedirectParam(request));
		} else {
			model.addAttribute("code", 0);
			model.addAttribute("message", loginResult.getMessage());
			return path + "login.vm";
		}
	}
	
	private Map<String, String> makeRedirectParam(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		Map<String, String> redirectParamMap = Maps.newHashMap();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			if ("userName".equals(paramName) || "password".equals(paramName)
				|| "redirect".equals(paramName) || "accessToken".equals(paramName)
				|| "code".equals(paramName) || "message".equals(paramName)) {
				continue;
			}
			redirectParamMap.put(paramName, request.getParameter(paramName));
		}
		return redirectParamMap;
	}
	
	private String checkRedirect(String redirect) {
		if (redirect != null) {
			//			if (redirect.startsWith(AppConstantsUtil.getHostHttpUrl())) {
			//				return redirect;
			//			} else
			if (redirect.contains("http://") || redirect.contains("https://")) {
				return "";
			} else {
				return redirect;
			}
		}
		return redirect;
	}
	
	@RequestMapping(value = "logout.htm")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return logout(request, response, "/login/login.htm");
	}
	
	private String logout(HttpServletRequest request, HttpServletResponse response, String url) {
		Subject subject = SecurityUtils.getSubject();
		try {
			if (null != subject) {
				ShiroSessionUtils.clear();
				subject.logout();
			}
			request.getSession().invalidate();
		} catch (Exception e) {
			logger.error("登出报错", e);
		}
		sendUrl(response, url);
		return null;
	}
	
	/**
	 * 激活
	 * @return
	 */
	@RequestMapping("toActive.htm")
	public String toActive(Model model) {
		return path + "activation.vm";
	}
	
	/**
	 * 忘记密码
	 * @return
	 */
	@RequestMapping("forgetPassword.htm")
	public String forgetPassword(ModelMap model) {
		model.addAttribute("forget", "forget");
		return path + "activation.vm";
	}
	
	/**
	 * 验证用户名和手机是否匹配，并验证改用户是否已激活
	 * @param userName
	 * @param mobile
	 * @return
	 */
	@RequestMapping("checkUserAndMobile.json")
	@ResponseBody
	public Boolean checkUserAndMobile(String userName, String mobile, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			SysUser userInfo = serviceProxy.loadUserByUsername(userName);
			//SysUser userInfo = userDetailsService.loadUserByUsername(userName);
			if (StringUtil.isBlank(mobile) || StringUtil.notEquals(mobile, userInfo.getMobile())) {
				json.put("success", false);
				//json.put("message", "您输入的手机号码和系统数据不匹配，请重新输入或联系管理员!");
				//return json;
				return false;
			}
			if (UserStateEnum.INACTIVE.code().equals(userInfo.getUserStatus())) {
				json.put("success", false);
				//json.put("message", "该账户不存在或未处于待激活状态!");
				//return json;
				return false;
			}
			request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER,
				userInfo);
			CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
			if (checkStatus == null) {
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.NOT_APPLY);
			}
			json.put("success", true);
			//json.put("message", "验证成功!");
			//return json;
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//return json;
		return false;
	}
	
	/**
	 * 激活用户
	 * @param password
	 * @param checkKey
	 * @return
	 */
	@RequestMapping("activeUser.json")
	@ResponseBody
	public JSONObject activeUser(String password, String checkKey, String forget,
									HttpServletRequest request) {
		JSONObject json = new JSONObject();
		SysUser user = (SysUser) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER);
		if (user == null) {
			json.put("success", false);
			json.put("message", "验证信息已过期，请刷新页面后重试!");
			return json;
		}
		String userName = request.getParameter("userName");
		if (StringUtil.isBlank(userName)) {
			json.put("success", false);
			json.put("message", "用户名不能为空");
			return json;
		}
		String account = user.getAccount();
		if (StringUtil.notEquals(userName, user.getAccount())) {
			json.put("success", false);
			json.put("message", "发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			return json;
		}
		// 判断验证码是否已校验通过
		CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
		if (CheckStatusEnum.CHECK_PASS != checkStatus) {
			json.put("success", false);
			json.put("message", "请先获取或校验验证码!");
			return json;
		}
		// 调用激活方法
		// 修改密码(如果激活方法自带修改密码这一步就不需要)
		try {
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			String str = serviceProxy.updatePwd(account, password);
			String key = "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			password = account + "active" + key;
			password = MD5Util.getMD5_32(password);
			// 忘记密码时无需登录
			if (!"forget".equals(forget)) {
				LoginOrder loginOrder = new LoginOrder();
				WebUtil.setPoPropertyByRequest(loginOrder, request);
				loginOrder.setLoginIp(IPUtil.getIpAddr(request));
				loginOrder.setUserName(account);
				LoginResult loginResult = loginService.login(loginOrder);
				
				if (!loginResult.isSuccess()
					&& InsuranceResultEnum.FUNCTION_NOT_OPEN == loginResult.getInsuranceResultEnum()) {
					serviceProxy.login(account, password, null, null);
				}
			}
			json.put("success", true);
			json.put("message", "激活成功!");
			mobileManager.clearValidateCode();
			request.getSession().removeAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
		} catch (RemoteException e) {
			logger.error("激活失败：" + e.getMessage(), e);
			json.put("success", false);
			json.put("message", "激活失败：" + e.getMessage());
		}
		return json;
	}

	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
     * @throws IOException
     */
	@RequestMapping("sendMobileValidateCode.json")
	@ResponseBody
	public JSONObject sendMobileValidateCode(HttpServletRequest request,
												HttpServletResponse response, ModelMap model)
																								throws IOException {
		String tipPrefix = "发送验证码";
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("utf-8");
		String userName = request.getParameter("userName");
		if (StringUtil.isBlank(userName)) {
			json.put("success", false);
			json.put("message", "用户名不能为空!");
			logger.info("用户名不能为空!");
			return json;
			//return false;
		}
		SysUser user = (SysUser) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER);
		if (user == null) {
			json.put("success", false);
			json.put("message", "请先输入用户名和手机号!");
			logger.info("请先输入用户名和手机号!");
			return json;
			//return false;
		}
		if (StringUtil.notEquals(userName, user.getAccount())) {
			json.put("success", false);
			json.put("message", "发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			logger.info("发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			return json;
			//return false;
		}
		String mobileNumber = user.getMobile();
		try {
			//			String code = ValidateCode.getCode(6, 0);
			//			String smsContent = "第一次登录的测试验证码测试验证码来了：" + code + "结束";
			//			logger.info(smsContent);
			//			SMSInfo smsInfo = sMSService.sendSMS(mobileNumber, smsContent, null);
			//			json.put("data", smsInfo);
			//			json = toStandardResult(json, tipPrefix);
			Result result = mobileManager.sendMobileValidateCode(mobileNumber,
				FunctionalModulesEnum.ACCOUNT_ACTIVATION);
			//			json.put("data", result);
			//			json = toStandardResult(json, tipPrefix);
			if (result.isSuccess()) {
				json.put("success", true);
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.APPLYING);
			}
			json.put("message", result.getMessage());
		} catch (Exception e) {
			logger.error(tipPrefix + "出错：" + e.getMessage(), e);
			json.put("success", false);
			json.put("message", e.getMessage());
			//return false;
		}
		return json;
		//return true;
	}

	/**
	 * 校验买家手机验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
     */
	@RequestMapping("equalActiveValidateCode.json")
	@ResponseBody
	public Boolean equalValidateCodeForBuyer(HttpServletRequest request,
												HttpServletResponse response, ModelMap model)
																								throws IOException {
		response.setCharacterEncoding("utf-8");
		String checkCode = request.getParameter("code");
		CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
		JSONObject json = new JSONObject();
		if (CheckStatusEnum.APPLYING == checkStatus
			|| CheckStatusEnum.CHECK_NOT_PASS == checkStatus
			|| CheckStatusEnum.CHECK_PASS == checkStatus) {
			
			InsuranceBaseResult InsuranceBaseResult = mobileManager.equalValidateCode(checkCode,
				FunctionalModulesEnum.ACCOUNT_ACTIVATION, false);
			if (InsuranceBaseResult.isSuccess()) {
				json.put("success", true);
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.CHECK_PASS);
			} else {
				json.put("success", false);
				json.put("message", InsuranceBaseResult.getMessage());
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.CHECK_NOT_PASS);
				return false;
			}
			
		} else {
			json.put("success", false);
			json.put("message", "请先获取校验码");
			logger.error("未发起激活用户手机验证申请");
			request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
				CheckStatusEnum.NOT_APPLY);
			return false;
		}
		return true;
	}
}
