/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.util;

import java.util.HashMap;
import java.util.Map;

import com.yjf.common.lang.security.DigestUtil;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename SignUtil.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-3-4</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class SignUtil {
	/**
	 * @param paramMap
	 * @param openApiContext
	 */
	public static boolean validateSign(Map<String, String> paramMap, String securityCheckKey,
										DigestALGEnum algEnum) {
		Map<String, Object> validatMap = new HashMap<String, Object>(paramMap);
		String signValue = (String) validatMap.remove(DigestUtil.SIGN_KEY);
		String sign = DigestUtil.digest(validatMap, securityCheckKey, algEnum);
		return StringUtil.equalsIgnoreCase(signValue, sign);
	}
}
