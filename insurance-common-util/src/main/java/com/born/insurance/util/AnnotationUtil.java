/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.springframework.util.Assert;

/**
 * 
 * @Filename AnnotationUtil.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-4-5</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public abstract class AnnotationUtil {
	
	private AnnotationUtil() {
	}
	
	/**
	 * 获取Class对象指定的Annotation对象
	 * @param element
	 * @param annotationType
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(AnnotatedElement element,
															Class<T> annotationClass) {
		Assert.notNull(element, "指定Class对象不能为空");
		
		T annotation = null;
		
		if (element.isAnnotationPresent(annotationClass)) {
			annotation = element.getAnnotation(annotationClass);
		}
		
		return annotation;
	}
	
	public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
		Assert.notNull(method, "指定Method对象不能为空");
		
		T annotation = null;
		if (method.isAnnotationPresent(annotationClass)) {
			annotation = method.getAnnotation(annotationClass);
		}
		return annotation;
	}
	
	public static Annotation[] getAnnotationsWithMethod(Method method) {
		return method.getAnnotations();
	}
	
}
