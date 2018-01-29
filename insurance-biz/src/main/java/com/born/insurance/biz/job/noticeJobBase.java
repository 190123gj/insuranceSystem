/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:01:42 创建
 */
package com.born.insurance.biz.job;

import com.born.insurance.biz.service.common.MailService;
import com.born.insurance.biz.service.common.SiteMessageService;
import com.born.insurance.biz.service.common.SysParameterService;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.ws.enums.SysParamEnum;
import com.born.insurance.ws.order.common.SimpleUserInfo;
import com.born.insurance.ws.order.common.SysWebAccessTokenOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.common.SysWebAccessTokenService;
import com.born.insurance.ws.service.sms.SMSService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public abstract class noticeJobBase extends JobBase {
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService smsService;
	
	@Autowired
	protected SysParameterService sysParameterService;
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	protected String getAccessToken(SimpleUserInfo userInfo) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(userInfo, tokenOrder);
		tokenOrder.setRemark("站外访问链接");
		InsuranceBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取web访问地址
	 * @return
	 */
	protected String getFaceWebUrl() {
		String faceUrl = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
		if (faceUrl != null && faceUrl.endsWith("/")) {
			faceUrl = faceUrl.substring(0, faceUrl.length() - 1);
		}
		return faceUrl;
	}
}
