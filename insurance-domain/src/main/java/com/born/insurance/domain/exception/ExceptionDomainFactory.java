/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.domain.exception;

import com.born.insurance.domain.enums.InsuranceDomainResultEnum;

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
public class ExceptionDomainFactory {
	
	public static InsuranceDomainException newEstateDomainException(InsuranceDomainResultEnum resultCode,
																	String errorMsg) {
		return new InsuranceDomainException(resultCode, errorMsg);
	}
}
