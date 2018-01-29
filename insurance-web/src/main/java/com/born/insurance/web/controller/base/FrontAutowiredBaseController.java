package com.born.insurance.web.controller.base;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class FrontAutowiredBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void printHttpResponse(HttpServletResponse response,
			JSONAware json) {
		try {

			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json.toJSONString());
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
	}

	protected void printHttpResponseJson(HttpServletResponse response,
			String jsonString) {
		try {

			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
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

	protected void printHttpResponse(HttpServletResponse response,
			String printStr) {
		try {

			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(printStr);
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
	}

	protected void sendUrl(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected static String getWebRootPath(HttpServletRequest request) {
		WebApplicationContext wac = (WebApplicationContext) request
				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		ServletContext context = wac.getServletContext();
		return context.getRealPath("");
	}

}
