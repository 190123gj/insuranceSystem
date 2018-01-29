package com.born.insurance.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if(null == context) {
			throw new NullPointerException("spring 上下文对象未初始化，无法完成bean的查找！");
		}
		if(null != context) {
			Object obj = context.getBean(beanName);
			if(null != obj) {
				return (T)obj;
			}
		}
		
		return null;
	}
	
}
