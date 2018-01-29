/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午9:55:36 创建
 */
package com.born.insurance.ws.result.user;

import com.born.insurance.ws.info.user.UserExtraMessageInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class UserExtraMessageResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private UserExtraMessageInfo userExtraMessageInfo;
	
	public UserExtraMessageInfo getUserExtraMessageInfo() {
		return this.userExtraMessageInfo;
	}
	
	public void setUserExtraMessageInfo(UserExtraMessageInfo userExtraMessageInfo) {
		this.userExtraMessageInfo = userExtraMessageInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserExtraMessageResult [userExtraMessageInfo=");
		builder.append(userExtraMessageInfo);
		builder.append(", getInsuranceResultEnum()=");
		builder.append(getInsuranceResultEnum());
		builder.append("]");
		return builder.toString();
	}
	
}
