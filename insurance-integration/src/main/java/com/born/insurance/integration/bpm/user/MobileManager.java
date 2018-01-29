/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.integration.bpm.user;


import com.born.insurance.integration.enums.FunctionalModulesEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.yjf.common.lang.result.Result;

/**
 * 
 * @Filename MobileManager.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-24</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public interface MobileManager {
	public Result sendMobileValidateCode(String mobileNumber,
										 FunctionalModulesEnum functionalModulesEnum);
	
	public InsuranceBaseResult equalValidateCodeUseResult(String validateCode,
														  FunctionalModulesEnum functionalModulesEnum);
	
	public boolean equalValidateCode(String validateCode,
									 FunctionalModulesEnum functionalModulesEnum);
	
	public InsuranceBaseResult equalValidateCode(String validateCode,
										   FunctionalModulesEnum functionalModulesEnum,
										   boolean isRemove);
	
	public InsuranceBaseResult clearValidateCode();
}
