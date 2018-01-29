package com.born.insurance.web.controller.base;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 
 * @Filename WebParamBinder.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-23</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public class WebParamBinder implements WebBindingInitializer {
	
	public static final String[] DATEPATTERN = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
																"yyyy年MM月dd日 HH时mm分ss秒",
																"yyyy年MM月dd日" };
	
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		DateFormat dateFormat = new SimpleDateFormat(DATEPATTERN[0]);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Calendar.class, new CustomCalendarEditor(true, DATEPATTERN));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}