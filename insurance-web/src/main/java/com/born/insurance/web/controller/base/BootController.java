package com.born.insurance.web.controller.base;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BootController extends BaseController {
	
	@RequestMapping("index.htm")
	public void pageIndex(HttpServletResponse response, HttpSession session) throws IOException {
		sendUrl(response, "/login/login.htm");
	}
}
