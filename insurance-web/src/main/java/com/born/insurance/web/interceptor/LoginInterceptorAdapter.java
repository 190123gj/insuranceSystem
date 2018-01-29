package com.born.insurance.web.interceptor;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.integration.bpm.user.LoginService;
import com.born.insurance.integration.bpm.user.PermissionService;
import com.born.insurance.integration.bpm.user.PermissionUtil;
import com.born.insurance.integration.result.LoginResult;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.MD5Util;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.SysWebAccessTokenInfo;
import com.born.insurance.ws.order.LoginOrder;
import com.born.insurance.ws.service.common.SysWebAccessTokenService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import com.born.insurance.web.util.SpringUtil;
import com.born.insurance.web.util.WebSessionUtil;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class LoginInterceptorAdapter implements HandlerInterceptor {
	
	String ignoreUrlStr = "";
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static SysWebAccessTokenService sysWebAccessTokenService;
	
	private static LoginService loginService;
	
	private static PermissionService permissionService;
	
	static {
		sysWebAccessTokenService = SpringUtil.getBean("sysWebAccessTokenService");
		loginService = SpringUtil.getBean("loginService");
		permissionService = SpringUtil.getBean("permissionService");
	}
	
	/**
	 * 正则${:}
	 */
	protected static Pattern pattern = Pattern
		.compile("\\$\\{[a-zA-Z0-9.]{1,}:[a-zA-Z0-9.]{1,}\\}");
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
								Object handler) throws Exception {
		
		String uri = request.getServletPath();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		//不需要权限检查的访问密钥
		SysWebAccessTokenInfo token = (SysWebAccessTokenInfo) request.getAttribute("accessToken");
		if (token != null) {
			
			//当前登陆用户不是访问用户
			if (sessionLocal != null && token.getUserId() != sessionLocal.getUserId()) {
				if (isAjaxRequest(request)) {
					JSONObject json = new JSONObject();
					json.put("success", false);
					json.put("message", "无权访问");
					printHttpResponse(response, json);
				} else {
					String redirect = "/login/login.htm?code=0&message="
										+ URLEncoder.encode("您无权访问当前数据，请重新登陆", "utf-8")
										+ "&redirect=" + request.getRequestURI();
					response.sendRedirect(redirect);
				}
				return false;
			}
			
			if (sessionLocal == null && token.getIsValid() == BooleanEnum.IS) {
				try {
					//自动登陆
					LoginOrder loginData = new LoginOrder();
					loginData.setUserName(token.getUserAccount());
					String password = token.getUserAccount() + "trust"
										+ "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
					password = MD5Util.getMD5_32(password);
					loginData.setPassword(password);
					LoginResult loginResult = loginService.login(loginData);
					if (loginResult.isSuccess()) {
						sysWebAccessTokenService.use(token.getAccessToken());
						String loadMenu = request.getParameter("loadMenu");
						if (loadMenu == null || !loadMenu.equals("NO")) {
							permissionService.loadSystemPermission(uri.split("/")[1]);
						}
					}
				} catch (Exception e) {
					logger.error("自动登陆出错：{}", e);
				}
				
				return true;
			} else if (sessionLocal == null && token.getIsValid() == BooleanEnum.NO) {
				if (isAjaxRequest(request)) {
					JSONObject json = new JSONObject();
					json.put("success", false);
					json.put("message", "访问已失效");
					printHttpResponse(response, json);
				} else {
					String redirect = "/login/login.htm?code=0&message="
										+ URLEncoder.encode("访问已失效，请重新登陆", "utf-8") + "&redirect="
										+ request.getRequestURI();
					Enumeration<String> params = request.getParameterNames();
					while (params.hasMoreElements()) {
						String paramName = params.nextElement();
						redirect += "&" + paramName + "=" + request.getParameter(paramName);
					}
					response.sendRedirect(redirect);
				}
				return false;
			} else {
				return true;
			}
		} else {
			if (PermissionUtil.checkPermission(uri)) {
				return true;
			} else {
				if (isAjaxRequest(request)) {
					JSONObject json = new JSONObject();
					json.put("success", false);
					json.put("message", "未授权的资源：" + uri);
					printHttpResponse(response, json);
					return false;
				} else {
					throw ExceptionFactory
						.newFcsException(InsuranceResultEnum.NO_ACCESS, "未授权的资源：" + uri);
				}
			}
		}
	}
	
	/**
	 * 是否ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		//是否签报的请求
		String isFormChangeApply = request.getParameter("isFormChangeApply");
		if (StringUtil.isNotBlank(requestType) || "IS".equals(isFormChangeApply))
			return true;
		return false;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
							Object handler, ModelAndView modelAndView) throws Exception {
		try {
			if (modelAndView != null && modelAndView.getModelMap() != null) {
				modelAndView.getModelMap().put("sessionLocal", ShiroSessionUtils.getSessionLocal());
				modelAndView.getModelMap().put("sessionID", request.getSession().getId());
				modelAndView.getModelMap().put("webSessionUtil",
					new WebSessionUtil(ShiroSessionUtils.getSessionLocal()));
				modelAndView.getModelMap().put("sessionScope", ShiroSessionUtils.getSessionLocal());
			}
			
			// response.setHeader("Pragma", "no-cache");
			// response.addHeader("Cache-Control", "must-revalidate");
			// response.addHeader("Cache-Control", "no-cache");
			// response.addHeader("Cache-Control", "no-store");
			// response.setDateHeader("Expires", 0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception ex) throws Exception {
		
	}
	
	public void setIgnoreUrlStr(String ignoreUrlStr) {
		this.ignoreUrlStr = ignoreUrlStr;
	}
	
	protected void printHttpResponse(HttpServletResponse response,
										com.alibaba.fastjson.JSONAware json) {
		try {
			
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json.toJSONString());
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
	}
}
