/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.born.insurance.domain.context;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.yjf.common.domain.api.Domain;
import com.yjf.common.service.Order;

/**
 * @Filename EstateContext.java
 * 
 * @Description 系统参数上下文定义.
 * 
 * @Version 1.0
 * 
 * @Author hasulee
 * 
 * @Email ligen@yiji.com
 * 
 * @History <li>Author: hasulee</li> <li>Date: 2012-7-2 下午6:13:46</li> <li>
 * Version: 1.0</li> <li>Content: create</li>
 */
public class InsuranceDomainContext<T extends Order> implements Serializable {
	
	private static final long serialVersionUID = -2822996616803487728L;
	
	/** 系统(数据库)当前时间 */
	private final Date sysDate;
	
	/** 全部属于Order(使用泛型，代替语法糖)一类的数据传输对象 */
	private final T target;
	
	/** 冗余系统流转参数 */
	private final Map<String, Object> attribute = new HashMap<String, Object>();
	
	/** 动态资源置与线程中 <code>reloadableMsgSources</code> */
	private final ReloadableResourceBundleMessageSource reloadableMsgSources;
	private Domain domain;
	
	public InsuranceDomainContext(Date sysDate, T target,
								  ReloadableResourceBundleMessageSource reloadableMsgSources) {
		this.sysDate = sysDate;
		this.target = target;
		this.reloadableMsgSources = reloadableMsgSources;
	}
	
	public String getReloadableProperty(String key, Object[] params, Locale locale) {
		return reloadableMsgSources.getMessage(key, params, locale);
	}
	
	public String getReloadableProperty(String key, Object[] params) {
		return getReloadableProperty(key, params, Locale.CHINA);
	}
	
	public String getReloadableProperty(String key) {
		return getReloadableProperty(key, null);
	}
	
	public void addAttribute(String key, Object value) {
		attribute.put(key, value);
	}
	
	public Object getAttribute(String key) {
		Object obj = null;
		if (attribute.containsKey(key)) {
			obj = attribute.get(key);
		}
		return obj;
	}
	
	public Date getSysDate() {
		return sysDate;
	}
	
	public T getTarget() {
		return target;
	}
	
	public Domain getDomain() {
		return this.domain;
	}
	
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateContext [sysDate=");
		builder.append(sysDate);
		builder.append(", target=");
		builder.append(target);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append(", reloadableMsgSources=");
		builder.append(reloadableMsgSources);
		builder.append("]");
		return builder.toString();
	}
	
}
