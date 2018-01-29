/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.integration.bpm.user;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.born.insurance.integration.bpm.user.MobileManager;
import com.born.insurance.integration.common.impl.ClientBaseSevice;
import com.born.insurance.integration.enums.FunctionalModulesEnum;
import com.born.insurance.integration.util.MoblieSendData;
import com.born.insurance.integration.util.SessionConstant;
import com.born.insurance.integration.util.SessionMobileSend;
import com.born.insurance.util.ValidateCode;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.sms.SMSInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.sms.SMSService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yjf.common.lang.result.Result;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.ConstraintUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename MobileManagerImpl.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-27</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
@Service("mobileManager")
public class MobileManagerImpl extends ClientBaseSevice implements MobileManager {
	
	public static int VALIDATE_CODE_FAIL_MAX_TIMES = 5;
	public static ConstraintUtil SMS_OR_MAIL_CON = ConstraintUtil.newBuilder().count(10)
		.interval(60).build();
	//TODO 为了手机app间隔从60改为2
	public static ConstraintUtil DATA_CON = ConstraintUtil.newBuilder().count(100).interval(60)
		.build();
	public static ConstraintUtil DATA_CON_MAX = ConstraintUtil.newBuilder().count(100).interval(10)
		.build();
	
	public static ConstraintUtil DATA_CON_LOGIN = ConstraintUtil.newBuilder().count(100)
		.interval(10).build();
	
	@Autowired
	private SMSService smsService;
	
	Map<String, MoblieSendData> sendMobileMap = new HashMap<String, MoblieSendData>();
	
	/**
	 * @param validateCode
	 * @param functionalModulesEnum
	 * @return
	 */
	@Override
	public InsuranceBaseResult equalValidateCode(String validateCode,
												 FunctionalModulesEnum functionalModulesEnum,
												 boolean isRemove) {
		InsuranceBaseResult InsuranceBaseResult = new InsuranceBaseResult();
		//TODO 处理functionalModulesEnum
		logger.info("validateCode=={}", validateCode);
		boolean result = false;
		Subject subject = SecurityUtils.getSubject();
		if (StringUtil.isNotEmpty(validateCode)) {
			SessionMobileSend sessionMobileSend = (SessionMobileSend) subject.getSession()
				.getAttribute(SessionConstant.SESSION_VALIDATE_CODE);
			if (sessionMobileSend == null || StringUtil.isBlank(sessionMobileSend.getCode())) {
				InsuranceBaseResult.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
				InsuranceBaseResult.setSuccess(false);
				InsuranceBaseResult.setMessage("请先获取校验码");
			} else {
				if (sessionMobileSend.getEqualCount() < VALIDATE_CODE_FAIL_MAX_TIMES) {
					result = StringUtil.equalsIgnoreCase(validateCode, sessionMobileSend.getCode());
					if (result) {
						if (isRemove) {
							subject.getSession().removeAttribute(
								SessionConstant.SESSION_VALIDATE_CODE);
						}
						InsuranceBaseResult.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
						InsuranceBaseResult.setSuccess(true);
						InsuranceBaseResult.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
					} else {
						InsuranceBaseResult.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_FAILURE);
						InsuranceBaseResult.setSuccess(false);
						InsuranceBaseResult.setMessage("校验码错误");
					}
					
					if (!result) {
						sessionMobileSend.setEqualCount(sessionMobileSend.getEqualCount() + 1);
						
					}
				} else {
					InsuranceBaseResult.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_FAILURE);
					InsuranceBaseResult.setSuccess(false);
					InsuranceBaseResult.setMessage("连续错误" + VALIDATE_CODE_FAIL_MAX_TIMES + "次，请重新获取验证码");
				}
			}
			
		} else {
			InsuranceBaseResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			InsuranceBaseResult.setSuccess(false);
			InsuranceBaseResult.setMessage("校验码不能为空");
		}
		return InsuranceBaseResult;
	}
	
	/**
	 * @param mobileNumber
	 * @param functionalModulesEnum
	 * @return
	 */
	@Override
	public Result sendMobileValidateCode(String mobileNumber,
											FunctionalModulesEnum functionalModulesEnum) {
		//TODO 处理functionalModulesEnum
		ResultBase resultBase = new ResultBase();
		if (StringUtil.isEmpty(mobileNumber))
			return resultBase;
		//if (ApplicationConstant.ENV_CONFIG.isOnline()) {
		try {
			if (functionalModulesEnum.getSendCount() > 10) {
				DATA_CON.check(mobileNumber + functionalModulesEnum.code());
			} else {
				SMS_OR_MAIL_CON.check(mobileNumber + functionalModulesEnum.code());
			}
			DATA_CON_MAX.check(mobileNumber);
		} catch (RuntimeException e) {
			logger.error("Constraints check:" + e.getMessage(), e);
			if ("超过间隔限制".equals(e.getMessage())) {
				resultBase.setMessage("不能频繁发送，请30秒后再试");
			} else {
				resultBase.setMessage(e.getMessage());
			}
			
			return resultBase;
		}
		//	}
		//		clearYesterdaySendData();
		//		
		//		if (!validateSendTime()) {
		//			resultBase.setMessage("发送时间间隔太短");
		//			return resultBase;
		//		}
		
		boolean isPass = validateSendMoblie(mobileNumber, functionalModulesEnum, resultBase);
		if (!isPass) {
			
			return resultBase;
		}
		String code = ValidateCode.getCode(6, 0);
		Subject subject = SecurityUtils.getSubject();
		SessionMobileSend preSessionMobileSend = (SessionMobileSend) subject.getSession()
			.getAttribute(SessionConstant.SESSION_VALIDATE_CODE);
		if (preSessionMobileSend != null) {
			if (StringUtil.isNotEmpty(preSessionMobileSend.getCode())
				&& StringUtil.equals(mobileNumber, preSessionMobileSend.getMoblie())
				&& functionalModulesEnum == preSessionMobileSend.getFunctionalModulesEnum()) {
				code = preSessionMobileSend.getCode();
			}
		}
		//		if (!ApplicationConstant.ENV_CONFIG.isOnline()) {
		//			code = "888888";
		//		}
		logger.info("sendCode=={}", code);
		
		SessionMobileSend sessionMobileSend = new SessionMobileSend();
		sessionMobileSend.setCode(code);
		sessionMobileSend.setLastSendDate(new Date());
		sessionMobileSend.setMoblie(mobileNumber);
		sessionMobileSend.setFunctionalModulesEnum(functionalModulesEnum);
		subject.getSession().setAttribute(SessionConstant.SESSION_VALIDATE_CODE, sessionMobileSend);
		
		// 获取短信内容，发送验证码
		String smsContent = "短信验证码为：" + code;
		if (functionalModulesEnum == FunctionalModulesEnum.ACCOUNT_ACTIVATION) {
			smsContent = "第一次登录的测试验证码测试验证码来了：" + code + "结束";
		}
		SMSInfo smsInfo = smsService.sendSMS(mobileNumber, smsContent, null);
		resultBase.setSuccess(true);
		resultBase.setMessage(smsInfo == null ? "" : smsInfo.getMessage());
		return resultBase;
	}
	
	/**
	 * @param mobileNumber
	 * @param functionalModulesEnum
	 * @param resultBase
	 * @return
	 */
	private boolean validateSendMoblie(String mobileNumber,
										FunctionalModulesEnum functionalModulesEnum,
										ResultBase resultBase) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			
			//			UserInfo userInfo = (UserInfo) subject.getSession().getAttribute(
			//				SessionConstant.SESSION_CURR_USER);
			
			MoblieSendData moblieSendData = new MoblieSendData(mobileNumber, functionalModulesEnum);
			//			if (userInfo != null) {
			//				moblieSendData.setUserName(userInfo.getUserName());
			//			}
			MoblieSendData sendData = sendMobileMap.get(moblieSendData.getSendMobileNumber());
			if (sendData != null) {
				sendData.addCount();
				//				if (!validateOneMinute(sendData.getLastSendDate())) {
				//					resultBase.setMessage("发送时间间隔太短");
				//					return false;
				//				}
				if (sendData.getSendCount() > MoblieSendData.MAX_SEND_TIMES) {
					resultBase.setMessage("发送次数超限");
					return false;
				}
			} else {
				sendMobileMap.put(moblieSendData.getSendMobileNumber(), new MoblieSendData(
					mobileNumber));
			}
			MoblieSendData moduleSendData = sendMobileMap.get(moblieSendData.getSendDataKey());
			if (moduleSendData != null) {
				moduleSendData.addCount();
				if (!validateOneMinute(sendData.getLastSendDate())) {
					resultBase.setMessage("发送时间间隔太短");
					return false;
				}
				if (sendData.getSendCount() > MoblieSendData.MAX_SEND_TIMES) {
					resultBase.setMessage("发送次数超限");
					return false;
				}
			} else {
				sendMobileMap.put(moblieSendData.getSendMobileNumber(), moblieSendData);
			}
			
		}
		return true;
	}
	
	/**
	 * @param lastSendDate
	 */
	private boolean validateOneMinute(Date lastSendDate) {
		Calendar calLastSendDate = Calendar.getInstance();
		calLastSendDate.setTime(lastSendDate);
		calLastSendDate.add(Calendar.MINUTE, 1);
		if (calLastSendDate.after(Calendar.getInstance())) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param validateCode
	 * @param functionalModulesEnum
	 * @return
	 */
	@Override
	public InsuranceBaseResult equalValidateCodeUseResult(String validateCode,
													FunctionalModulesEnum functionalModulesEnum) {
		return equalValidateCode(validateCode, functionalModulesEnum, true);
	}
	
	@Override
	public InsuranceBaseResult clearValidateCode() {
		InsuranceBaseResult result = new InsuranceBaseResult();
		try {
			Subject subject = SecurityUtils.getSubject();
			SessionMobileSend sessionMobileSend = (SessionMobileSend) subject.getSession()
				.removeAttribute(SessionConstant.SESSION_VALIDATE_CODE);
			if (sessionMobileSend != null) {
				sessionMobileSend.setCode("");
				subject.getSession().setAttribute(SessionConstant.SESSION_VALIDATE_CODE,
					sessionMobileSend);
			}
			result.setSuccess(true);
			result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
			result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
		} catch (InvalidSessionException e) {
			logger.error("清除验证码出错!", e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_FAILURE);
			result.setMessage("清除验证码失败");
		}
		return result;
	}
	
	@Override
	public boolean equalValidateCode(String validateCode,
										FunctionalModulesEnum functionalModulesEnum) {
		InsuranceBaseResult result = equalValidateCodeUseResult(validateCode, functionalModulesEnum);
		return result.isSuccess() && result.isExecuted();
	}
}
