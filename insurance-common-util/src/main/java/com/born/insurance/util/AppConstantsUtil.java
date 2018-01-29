/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.util;

import java.util.ArrayList;
import java.util.List;

import com.yjf.common.lang.util.ArrayUtil;
import com.yjf.common.lang.util.ListUtil;

/**
 * 
 * @Filename AppConstantsUtil.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class AppConstantsUtil {
	private static SystemConfig staticConfig;
	
	public synchronized static void init(SystemConfig config) {
		if (staticConfig == null) {
			staticConfig = config;
		}
	}
	
	public synchronized static void clear() {
		staticConfig = null;
	}
	
	public static boolean transferIsOpen() {
		return true;
	}
	
	public static String getYrdPrefixion() {
		return StringUtil.defaultIfBlank(staticConfig.getYrdPrefixion(), "");
	}
	
	public static String getPlatformName() {
		return staticConfig.getPlatformName();
	}
	
	public static String getProductName() {
		return staticConfig.getProductName();
	}
	
	public static String getAllCommon() {
		return staticConfig.getAllCommon();
	}
	
	public static String getProductICP() {
		return staticConfig.getProductICP();
	}
	
	public static String getOutBizNumber() {
		return staticConfig.getOutBizNumber();
	}
	
	public static long getIncreasingAmount() {
		return staticConfig.getIncreasingAmount();
	}
	
	public static int getMaxInvestCount() {
		return staticConfig.getMaxInvestCount();
	}
	
	public static boolean canGiveGiftMoney(String role) {
		String[] roles = staticConfig.getGiveGiftMoneyRoles();
		return ArrayUtil.contains(roles, role);
	}
	
	public static String[] canGiveGiftMoneyRoles() {
		String[] roles = staticConfig.getGiveGiftMoneyRoles();
		return roles;
	}
	
	/**
	 * QQ
	 * 
	 * @return
	 */
	public static String getProductQQ() {
		return staticConfig.getProductQQ();
	}
	
	/**
	 * 上传文件服器路径
	 * 
	 * @return
	 */
	public static String getYrdUploadFolder() {
		return staticConfig.getYrdUploadFolder();
//		return "/opt/apache-tomcat-7.0.39-yrd2/webapps/ROOT";
	}
	
	/**
	 * fop 字体库配置文件（userconfig.xml）路径
	 * 
	 * @return
	 */
	public static String getYrdFopFontFolder() {
		return staticConfig.getYrdFopFontFolder();
	}
	
	/**
	 * 图片服务器url
	 * 
	 * @return
	 */
	public static String getImageServerUrl() {
		return staticConfig.getImageServerUrl();
//		return "http://192.169.2.245:30000";
	}
	
	/**
	 * yrd的客服电话
	 * 
	 * @return
	 */
	public static String getCustomerServicePhone() {
		return staticConfig.getCustomerServicePhone();
	}
	
	/**
	 * yrd的客服手机
	 * 
	 * @return
	 */
	public static String getCustomerServiceMobile() {
		return staticConfig.getCustomerServiceMobile();
	}
	
	/**
	 * yrd的客服邮箱
	 * 
	 * @return
	 */
	public static String getCustomerServiceEmail() {
		return staticConfig.getCustomerServiceEmail();
	}
	
	/**
	 * yrd有转账权限的特殊用户
	 * 
	 * @return
	 */
	public static String getYrdTransferLimit() {
		return staticConfig.getYrdTransferLimit();
	}
	
	/**
	 * host配置
	 * 
	 * @return
	 */
	public static String getHostUrl() {
		return staticConfig.getHostUrl();
	}
	
	/**
	 * yjf中间结算账户
	 * 
	 * @return
	 */
	public static String getExchangeAccount() {
		return staticConfig.getExchangeAccount();
	}
	
	/**
	 * yjf分润账户
	 * 
	 * @return
	 */
	public static String getProfitSharingAccount() {
		return staticConfig.getProfitSharingAccount();
	}
	
	/**
	 * 收费产品编码
	 * 
	 * @return
	 */
	public static String getTradeBizProductCode() {
		return staticConfig.getTradeBizProductCode();
	}
	
	/**
	 * 获取当前应用的url
	 * 
	 * @return
	 */
	public static String getHostHttpUrl() {
		return staticConfig.getHostHttpUrl();
	}
	
	/**
	 * 默认经济人账户用户名
	 * 
	 * @return
	 */
	public static String getDefaultBrokerUserName() {
		return staticConfig.getDefaultBrokerUserName();
	}
	
	/**
	 * 企付通投资，默认经济人编码
	 * 
	 * @return
	 */
	public static String getQftBrokerUserName() {
		return staticConfig.getQftBrokerUserName();
	}
	
	/**
	 * 易结汇投资，默认经济人编码
	 * @return
	 */
	public static String getYjhBrokerUserName() {
		return staticConfig.getYjhBrokerUserName();
	}
	
	/**
	 * 易结汇投资，默认经济人编码
	 * @return
	 */
	public static String getBornBrokerUserName() {
		return staticConfig.getBornBrokerUserName();
	}
	
	/**
	 * 提现收费code
	 * 
	 * @return
	 */
	public static String getWithdrawChargeCode() {
		return staticConfig.getWithdrawChargeCode();
	}
	
	/**
	 * 网银收费code
	 * 
	 * @return
	 */
	public static String getBankB2CChargeCode() {
		return staticConfig.getBankB2CChargeCode();
	}
	
	/**
	 * 公司地址
	 * 
	 * @return
	 */
	public static String getPlatformAddress() {
		return staticConfig.getPlatformAddress();
	}
	
	/**
	 * 邮件发送服务器配置
	 * 
	 * @return
	 */
	public static String getMailServer() {
		return staticConfig.mailServer;
	}
	
	/**
	 * 邮件发送服务器配置
	 * 
	 * @return
	 */
	public static String getMailServerport() {
		return staticConfig.mailServerport;
	}
	
	/**
	 * 邮件发送服务器配置
	 * 
	 * @return
	 */
	public static String getMailUserName() {
		return staticConfig.mailUsername;
	}
	
	/**
	 * 邮件发送服务器配置
	 * 
	 * @return
	 */
	public static String getMailPassword() {
		return staticConfig.mailPassword;
	}
	
	/**
	 * 邮件发送服务器配置
	 * 
	 * @return
	 */
	public static String getMailNickName() {
		return staticConfig.mailNickName;
	}
	
	/**
	 * 邮件发送服务器配置
	 * 
	 * @return
	 */
	public static String getMailSenderName() {
		return staticConfig.mailSenderName;
	}
	
	/**
	 * 数字认证服务地址
	 * 
	 * @return
	 */
	public static String getCertificateServiceUrl() {
		return staticConfig.certificateServiceUrl;
	}
	
	/**
	 * 数字认证Key
	 * 
	 * @return
	 */
	public static String getCertificateAppKey() {
		return staticConfig.certificateAppKey;
	}
	
	/**
	 * 数字认证密钥
	 * 
	 * @return
	 */
	public static String getCertificateAppSecret() {
		return staticConfig.certificateAppSecret;
	}
	
	/**
	 * 是否使用支付密码，交易
	 * 
	 * @return
	 */
	public static boolean canTradeUsePayPassword() {
		return StringUtil.equalsIgnoreCase(
			StringUtil.defaultIfBlank(staticConfig.getTradeUsePayPassword(), "N"), "Y");
	}
	
	public static String getBornApiUrl() {
		return StringUtil.defaultIfEmpty(staticConfig.bornApiUrl,
			"http://bornapi.yiji.com/gateway.html");
	}
	
	public static String getAppUpdateANDROID() {
		return staticConfig.appUpdateANDROID;
	}
	
	public static String getAppUpdateIOS() {
		return staticConfig.appUpdateIOS;
	}
	
	/**
	 * app审核账号
	 * 
	 * @return
	 */
	public static String getTestAccount() {
		return staticConfig.getTestAccount();
	}
	
	/**
	 * 密码输入错误allowErrorTimes次后需要验证码
	 * 
	 * @return
	 */
	public static String getAllowErrorTimes() {
		
		return staticConfig.getAllowErrorTimes();
	}
	
	/**
	 * app端控制充值按钮是否显示
	 * 
	 * @return
	 */
	public static String getCloseDkCharge() {
		
		return staticConfig.getCloseDkCharge();
	}
	
	/**
	 * 平台代还款账户
	 * 
	 * @return
	 */
	public static String getPlatformRepayUserName() {
		return staticConfig.getPlatformRepayUserName();
	}
	
	/**
	 * 是否使用用户等级
	 * 
	 * @return
	 */
	public static boolean isUseUserLevel() {
		return "Y".equals(staticConfig.getUseUserLevel());
	}
	
	/**
	 * 接收融资请求邮箱地址
	 * @return
	 */
	public static String getLoanRequestMail() {
		
		return staticConfig.getLoanRequestMail();
	}
	
	/**
	 * 房融通业务扩展城市
	 * @return
	 */
	public static List<String> getLoanUserAreaCitys() {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(staticConfig.getLoanUserAreaCitys())) {
			String[] str = staticConfig.getLoanUserAreaCitys().split(",");
			for (int i = 0; i < str.length; i++) {
				list.add(str[i]);
			}
		}
		return list;
	}
	
	/**
	 * 长金宝投资密码校验
	 * @return
	 */
	public static String getCheckInvestPwd() {
		return staticConfig.getCheckInvestPwd();
	}
	
	/**
	 * 短信是否直接从Postman短信平台发送
	 * @return
	 */
	public static String getSmsByPostman() {
		return staticConfig.getSmsByPostman();
	}
	
	/**
	 * 外部用签名key
	 * @return
	 */
	public static String getOuterSingKey() {
		return staticConfig.getOuterSingKey();
	}
	
	public static boolean isloaderFixedBankCard() {
		return true;//"Y".equals(staticConfig.);
	}
	
	/**
	 * 是否验证承诺函编号唯一性，默认验证(Y)，不验证请配置 SYS_PARAM_GUARANTEE_LICENCE_NO_UNIQUE = N
	 */
	public static String validateGuaranteeLicenceNoUnique() {
		return staticConfig.getGuaranteeLicenceNoUnique();
	}
	
	/**
	 * ip地址黑明单
	 * @param ipAddress
	 * @return
	 */
	public static boolean isExsitPhoneBlackList(String ipAddress) {
		if (ListUtil.isEmpty(staticConfig.phoneBlackList) || StringUtil.isEmpty(ipAddress))
			return false;
		if (staticConfig.phoneBlackList.indexOf(ipAddress) > -1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ip地址黑明单
	 * @param ipAddress
	 * @return
	 */
	public static boolean isExsitSmsIpWhiteList(String ipAddress) {
		if (ListUtil.isEmpty(staticConfig.smsIpWhiteList) || StringUtil.isEmpty(ipAddress))
			return false;
		if (staticConfig.smsIpWhiteList.indexOf(ipAddress) > -1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 友盟推送 android Key
	 */
	public static String getAndroidYmKey() {
		return staticConfig.getAndroidYmKey();
	}
	
	/**
	 * 友盟推送 ios Key
	 */
	public static String getIosYmKey() {
		return staticConfig.getIosYmKey();
	}
	
	/**
	 * 手机防火墙
	 */
	public static boolean isMoblieFirewall() {
		return "Y".equalsIgnoreCase(staticConfig.getIsMoblieFirewall());
	}
	
	/**
	 * 手机防火墙 请求head
	 */
	public static String getRequestHeadName() {
		return staticConfig.getRequestHeadName();
	}
	
	/**
	 * 请求头的关键字
	 * @return
	 */
	public static String getRequestHeadVallue() {
		return staticConfig.getRequestHeadVallue();
	}
	
	/**
	 * 微信公众平台开发者应用ID
	 * @return
	 */
	public static String getWxAppID() {
		return staticConfig.getWxAppID();
	}
	
	/**
	 * 微信公众平台开发者应用密钥
	 * @return
	 */
	public static String getWxAppSecret() {
		return staticConfig.getWxAppSecret();
	}
	
	/**
	 * 微信公众平台 Token
	 * @return
	 */
	public static String getWxToken() {
		return staticConfig.getWxToken();
	}
	
}
