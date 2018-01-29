/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.exception;

import com.born.insurance.ws.enums.base.InsuranceResultEnum;

/**
 * 
 * @Filename ExceptionFactory.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2013-2-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class ExceptionFactory {
	
	public static InsuranceBizException newFcsException(InsuranceResultEnum resultCode,
														String errorMsg) {
		return new InsuranceBizException(resultCode, errorMsg);
	}
}
