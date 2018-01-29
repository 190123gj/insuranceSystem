package com.born.insurance.ws.result.common;

import com.born.insurance.ws.info.common.RelatedUserInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

/**
 * 设置相关人员结果
 *
 *
 * @author wuzj
 *
 */
public class RelatedUserResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = -2431611369815479085L;
	
	private RelatedUserInfo relatedUser;
	
	public RelatedUserInfo getRelatedUser() {
		return this.relatedUser;
	}
	
	public void setRelatedUser(RelatedUserInfo relatedUser) {
		this.relatedUser = relatedUser;
	}
	
}
