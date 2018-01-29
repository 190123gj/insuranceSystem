/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.util;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @Filename SystemConfig.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-4</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class SystemConfig {
	String platformName;
	String productName;
	String outBizNumber;
	String yrdPrefixion;
	String yrdUploadFolder;
	String imageServerUrl;
	String customerServicePhone;
	String yrdTransferLimit;
	String hostUrl;
	String exchangeAccount;
	String profitSharingAccount;
	String platformRepayUserName;// 平台代偿账户用户
	String tradeBizProductCode;
	String hostHttpUrl;
	String defaultBrokerUserName;
	String qftBrokerUserName;
	String yjhBrokerUserName;
	String bornBrokerUserName;
	String yrdFopFontFolder; // fop 字体库
	String withdrawChargeCode;
	String bankB2CChargeCode;
	String platformAddress;
	String productICP;
	String productQQ; // QQ号
	String allCommon;
	String customerServiceEmail;
	String customerServiceMobile;
	
	long increasingAmount = 100000;// 激增金额
	int maxInvestCount = 200;// 投资人数上限
	
	String mailServer; // 邮件发送服务器
	String mailServerport; // 邮件发送服务端口
	String mailUsername; // 发件人地址
	String mailPassword; // 发件人邮箱密码
	String mailNickName; // 发件人别名
	String mailSenderName; // 发件人地址
	
	String certificateServiceUrl; // 数字认证服务地址
	String certificateAppKey; // 数字认证Key
	String certificateAppSecret; // 数字认证密钥
	
	String loanRequestMail; //接收借款请求邮箱地址
	
	String tradeUsePayPassword; // 是否是使用交易密码操作业务
	
	String contractName;
	
	String bornApiUrl;
	
	String allowErrorTimes;// 密码错误几次后需要验证码
	
	String testAccount; // app审核账号
	
	String appUpdateIOS;// ios版本更新
	
	String appUpdateANDROID;// ANDROID版本更新
	
	String closeDkCharge;// app端控制充值按钮是否显示
	
	String useUserLevel;//用户分级
	
	String loanUserAreaCitys;//业务扩展城市
	
	String checkInvestPwd;//长金宝投资密码校验
	
	String outerSingKey;//外部用签名key
	
	//短信是否直接从Postman短信平台发送
	String smsByPostman;
	
	String[] giveGiftMoneyRoles;
	
	/**
	 * 是否验证承诺函编号唯一性(发布融资时)
	 */
	String guaranteeLicenceNoUnique;
	
	/**
	 * 手机黑名单IP地址
	 */
	List<String> phoneBlackList;
	/**
	 * 发送短信手机白名单IP地址
	 */
	List<String> smsIpWhiteList;
	
	/** 友盟推送 android Key */
	String androidYmKey;
	
	/** 友盟推送ios Key */
	String iosYmKey;
	
	/** 手机防火墙 */
	String isMoblieFirewall;
	/** 手机防火墙 */
	String requestHeadName;
	
	String requestHeadVallue;
	
	String wxAppID;
	
	String wxAppSecret;
	
	String wxToken;
	
	public String getPlatformName() {
		return this.platformName;
	}
	
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getOutBizNumber() {
		return this.outBizNumber;
	}
	
	public void setOutBizNumber(String outBizNumber) {
		this.outBizNumber = outBizNumber;
	}
	
	public String getYrdPrefixion() {
		return this.yrdPrefixion;
	}
	
	public void setYrdPrefixion(String yrdPrefixion) {
		this.yrdPrefixion = yrdPrefixion;
	}
	
	public String getYrdUploadFolder() {
		return this.yrdUploadFolder;
	}
	
	public void setYrdUploadFolder(String yrdUploadFolder) {
		this.yrdUploadFolder = yrdUploadFolder;
	}
	
	public String getImageServerUrl() {
		return this.imageServerUrl;
	}
	
	public void setImageServerUrl(String imageServerUrl) {
		this.imageServerUrl = imageServerUrl;
	}
	
	public String getCustomerServicePhone() {
		return this.customerServicePhone;
	}
	
	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}
	
	public String getYrdTransferLimit() {
		return this.yrdTransferLimit;
	}
	
	public void setYrdTransferLimit(String yrdTransferLimit) {
		this.yrdTransferLimit = yrdTransferLimit;
	}
	
	public String getHostUrl() {
		return this.hostUrl;
	}
	
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	public String getExchangeAccount() {
		return this.exchangeAccount;
	}
	
	public void setExchangeAccount(String exchangeAccount) {
		this.exchangeAccount = exchangeAccount;
	}
	
	public String getProfitSharingAccount() {
		return this.profitSharingAccount;
	}
	
	public void setProfitSharingAccount(String profitSharingAccount) {
		this.profitSharingAccount = profitSharingAccount;
	}
	
	public String getPlatformRepayUserName() {
		return this.platformRepayUserName;
	}
	
	public void setPlatformRepayUserName(String platformRepayUserName) {
		this.platformRepayUserName = platformRepayUserName;
	}
	
	public String getTradeBizProductCode() {
		return this.tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public String getHostHttpUrl() {
		return this.hostHttpUrl;
	}
	
	public void setHostHttpUrl(String hostHttpUrl) {
		this.hostHttpUrl = hostHttpUrl;
	}
	
	public String getDefaultBrokerUserName() {
		return this.defaultBrokerUserName;
	}
	
	public void setDefaultBrokerUserName(String defaultBrokerUserName) {
		this.defaultBrokerUserName = defaultBrokerUserName;
	}
	
	public String getQftBrokerUserName() {
		return this.qftBrokerUserName;
	}
	
	public void setQftBrokerUserName(String qftBrokerUserName) {
		this.qftBrokerUserName = qftBrokerUserName;
	}
	
	public String getYjhBrokerUserName() {
		return this.yjhBrokerUserName;
	}
	
	public void setYjhBrokerUserName(String yjhBrokerUserName) {
		this.yjhBrokerUserName = yjhBrokerUserName;
	}
	
	public String getBornBrokerUserName() {
		return this.bornBrokerUserName;
	}
	
	public void setBornBrokerUserName(String bornBrokerUserName) {
		this.bornBrokerUserName = bornBrokerUserName;
	}
	
	public String getYrdFopFontFolder() {
		return this.yrdFopFontFolder;
	}
	
	public void setYrdFopFontFolder(String yrdFopFontFolder) {
		this.yrdFopFontFolder = yrdFopFontFolder;
	}
	
	public String getWithdrawChargeCode() {
		return this.withdrawChargeCode;
	}
	
	public void setWithdrawChargeCode(String withdrawChargeCode) {
		this.withdrawChargeCode = withdrawChargeCode;
	}
	
	public String getBankB2CChargeCode() {
		return this.bankB2CChargeCode;
	}
	
	public void setBankB2CChargeCode(String bankB2CChargeCode) {
		this.bankB2CChargeCode = bankB2CChargeCode;
	}
	
	public String getPlatformAddress() {
		return this.platformAddress;
	}
	
	public void setPlatformAddress(String platformAddress) {
		this.platformAddress = platformAddress;
	}
	
	public String getProductICP() {
		return this.productICP;
	}
	
	public void setProductICP(String productICP) {
		this.productICP = productICP;
	}
	
	public String getProductQQ() {
		return this.productQQ;
	}
	
	public void setProductQQ(String productQQ) {
		this.productQQ = productQQ;
	}
	
	public String getAllCommon() {
		return this.allCommon;
	}
	
	public void setAllCommon(String allCommon) {
		this.allCommon = allCommon;
	}
	
	public String getCustomerServiceEmail() {
		return this.customerServiceEmail;
	}
	
	public void setCustomerServiceEmail(String customerServiceEmail) {
		this.customerServiceEmail = customerServiceEmail;
	}
	
	public String getCustomerServiceMobile() {
		return this.customerServiceMobile;
	}
	
	public void setCustomerServiceMobile(String customerServiceMobile) {
		this.customerServiceMobile = customerServiceMobile;
	}
	
	public long getIncreasingAmount() {
		return this.increasingAmount;
	}
	
	public void setIncreasingAmount(long increasingAmount) {
		this.increasingAmount = increasingAmount;
	}
	
	public int getMaxInvestCount() {
		return this.maxInvestCount;
	}
	
	public void setMaxInvestCount(int maxInvestCount) {
		this.maxInvestCount = maxInvestCount;
	}
	
	public String getMailServer() {
		return this.mailServer;
	}
	
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
	
	public String getMailServerport() {
		return this.mailServerport;
	}
	
	public void setMailServerport(String mailServerport) {
		this.mailServerport = mailServerport;
	}
	
	public String getMailUsername() {
		return this.mailUsername;
	}
	
	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}
	
	public String getMailPassword() {
		return this.mailPassword;
	}
	
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	
	public String getMailNickName() {
		return this.mailNickName;
	}
	
	public void setMailNickName(String mailNickName) {
		this.mailNickName = mailNickName;
	}
	
	public String getMailSenderName() {
		return this.mailSenderName;
	}
	
	public void setMailSenderName(String mailSenderName) {
		this.mailSenderName = mailSenderName;
	}
	
	public String getCertificateServiceUrl() {
		return this.certificateServiceUrl;
	}
	
	public void setCertificateServiceUrl(String certificateServiceUrl) {
		this.certificateServiceUrl = certificateServiceUrl;
	}
	
	public String getCertificateAppKey() {
		return this.certificateAppKey;
	}
	
	public void setCertificateAppKey(String certificateAppKey) {
		this.certificateAppKey = certificateAppKey;
	}
	
	public String getCertificateAppSecret() {
		return this.certificateAppSecret;
	}
	
	public void setCertificateAppSecret(String certificateAppSecret) {
		this.certificateAppSecret = certificateAppSecret;
	}
	
	public String getLoanRequestMail() {
		return this.loanRequestMail;
	}
	
	public void setLoanRequestMail(String loanRequestMail) {
		this.loanRequestMail = loanRequestMail;
	}
	
	public String getTradeUsePayPassword() {
		return this.tradeUsePayPassword;
	}
	
	public void setTradeUsePayPassword(String tradeUsePayPassword) {
		this.tradeUsePayPassword = tradeUsePayPassword;
	}
	
	public String getContractName() {
		return this.contractName;
	}
	
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	public String getBornApiUrl() {
		return this.bornApiUrl;
	}
	
	public void setBornApiUrl(String bornApiUrl) {
		this.bornApiUrl = bornApiUrl;
	}
	
	public String getAllowErrorTimes() {
		return this.allowErrorTimes;
	}
	
	public void setAllowErrorTimes(String allowErrorTimes) {
		this.allowErrorTimes = allowErrorTimes;
	}
	
	public String getTestAccount() {
		return this.testAccount;
	}
	
	public void setTestAccount(String testAccount) {
		this.testAccount = testAccount;
	}
	
	public String getAppUpdateIOS() {
		return this.appUpdateIOS;
	}
	
	public void setAppUpdateIOS(String appUpdateIOS) {
		this.appUpdateIOS = appUpdateIOS;
	}
	
	public String getAppUpdateANDROID() {
		return this.appUpdateANDROID;
	}
	
	public void setAppUpdateANDROID(String appUpdateANDROID) {
		this.appUpdateANDROID = appUpdateANDROID;
	}
	
	public String getCloseDkCharge() {
		return this.closeDkCharge;
	}
	
	public void setCloseDkCharge(String closeDkCharge) {
		this.closeDkCharge = closeDkCharge;
	}
	
	public String getUseUserLevel() {
		return this.useUserLevel;
	}
	
	public void setUseUserLevel(String useUserLevel) {
		this.useUserLevel = useUserLevel;
	}
	
	public String getLoanUserAreaCitys() {
		return this.loanUserAreaCitys;
	}
	
	public void setLoanUserAreaCitys(String loanUserAreaCitys) {
		this.loanUserAreaCitys = loanUserAreaCitys;
	}
	
	public String getCheckInvestPwd() {
		return this.checkInvestPwd;
	}
	
	public void setCheckInvestPwd(String checkInvestPwd) {
		this.checkInvestPwd = checkInvestPwd;
	}
	
	public String getOuterSingKey() {
		return this.outerSingKey;
	}
	
	public void setOuterSingKey(String outerSingKey) {
		this.outerSingKey = outerSingKey;
	}
	
	public String getSmsByPostman() {
		return this.smsByPostman;
	}
	
	public void setSmsByPostman(String smsByPostman) {
		this.smsByPostman = smsByPostman;
	}
	
	public String[] getGiveGiftMoneyRoles() {
		return this.giveGiftMoneyRoles;
	}
	
	public void setGiveGiftMoneyRoles(String[] giveGiftMoneyRoles) {
		this.giveGiftMoneyRoles = giveGiftMoneyRoles;
	}
	
	public String getGuaranteeLicenceNoUnique() {
		return this.guaranteeLicenceNoUnique;
	}
	
	public void setGuaranteeLicenceNoUnique(String guaranteeLicenceNoUnique) {
		this.guaranteeLicenceNoUnique = guaranteeLicenceNoUnique;
	}
	
	public List<String> getPhoneBlackList() {
		return this.phoneBlackList;
	}
	
	public void setPhoneBlackList(List<String> phoneBlackList) {
		this.phoneBlackList = phoneBlackList;
	}
	
	public List<String> getSmsIpWhiteList() {
		return this.smsIpWhiteList;
	}
	
	public void setSmsIpWhiteList(List<String> smsIpWhiteList) {
		this.smsIpWhiteList = smsIpWhiteList;
	}
	
	public String getAndroidYmKey() {
		return this.androidYmKey;
	}
	
	public void setAndroidYmKey(String androidYmKey) {
		this.androidYmKey = androidYmKey;
	}
	
	public String getIosYmKey() {
		return this.iosYmKey;
	}
	
	public void setIosYmKey(String iosYmKey) {
		this.iosYmKey = iosYmKey;
	}
	
	public String getIsMoblieFirewall() {
		return this.isMoblieFirewall;
	}
	
	public void setIsMoblieFirewall(String isMoblieFirewall) {
		this.isMoblieFirewall = isMoblieFirewall;
	}
	
	public String getRequestHeadName() {
		return this.requestHeadName;
	}
	
	public void setRequestHeadName(String requestHeadName) {
		this.requestHeadName = requestHeadName;
	}
	
	public String getRequestHeadVallue() {
		return this.requestHeadVallue;
	}
	
	public void setRequestHeadVallue(String requestHeadVallue) {
		this.requestHeadVallue = requestHeadVallue;
	}
	
	public String getWxAppID() {
		return this.wxAppID;
	}
	
	public void setWxAppID(String wxAppID) {
		this.wxAppID = wxAppID;
	}
	
	public String getWxAppSecret() {
		return this.wxAppSecret;
	}
	
	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}
	
	public String getWxToken() {
		return this.wxToken;
	}
	
	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemConfig [platformName=");
		builder.append(platformName);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", outBizNumber=");
		builder.append(outBizNumber);
		builder.append(", yrdPrefixion=");
		builder.append(yrdPrefixion);
		builder.append(", yrdUploadFolder=");
		builder.append(yrdUploadFolder);
		builder.append(", imageServerUrl=");
		builder.append(imageServerUrl);
		builder.append(", customerServicePhone=");
		builder.append(customerServicePhone);
		builder.append(", yrdTransferLimit=");
		builder.append(yrdTransferLimit);
		builder.append(", hostUrl=");
		builder.append(hostUrl);
		builder.append(", exchangeAccount=");
		builder.append(exchangeAccount);
		builder.append(", profitSharingAccount=");
		builder.append(profitSharingAccount);
		builder.append(", platformRepayUserName=");
		builder.append(platformRepayUserName);
		builder.append(", tradeBizProductCode=");
		builder.append(tradeBizProductCode);
		builder.append(", hostHttpUrl=");
		builder.append(hostHttpUrl);
		builder.append(", defaultBrokerUserName=");
		builder.append(defaultBrokerUserName);
		builder.append(", qftBrokerUserName=");
		builder.append(qftBrokerUserName);
		builder.append(", yjhBrokerUserName=");
		builder.append(yjhBrokerUserName);
		builder.append(", bornBrokerUserName=");
		builder.append(bornBrokerUserName);
		builder.append(", yrdFopFontFolder=");
		builder.append(yrdFopFontFolder);
		builder.append(", withdrawChargeCode=");
		builder.append(withdrawChargeCode);
		builder.append(", bankB2CChargeCode=");
		builder.append(bankB2CChargeCode);
		builder.append(", platformAddress=");
		builder.append(platformAddress);
		builder.append(", productICP=");
		builder.append(productICP);
		builder.append(", productQQ=");
		builder.append(productQQ);
		builder.append(", allCommon=");
		builder.append(allCommon);
		builder.append(", customerServiceEmail=");
		builder.append(customerServiceEmail);
		builder.append(", customerServiceMobile=");
		builder.append(customerServiceMobile);
		builder.append(", increasingAmount=");
		builder.append(increasingAmount);
		builder.append(", maxInvestCount=");
		builder.append(maxInvestCount);
		builder.append(", mailServer=");
		builder.append(mailServer);
		builder.append(", mailServerport=");
		builder.append(mailServerport);
		builder.append(", mailUsername=");
		builder.append(mailUsername);
		builder.append(", mailPassword=");
		builder.append(mailPassword);
		builder.append(", mailNickName=");
		builder.append(mailNickName);
		builder.append(", mailSenderName=");
		builder.append(mailSenderName);
		builder.append(", certificateServiceUrl=");
		builder.append(certificateServiceUrl);
		builder.append(", certificateAppKey=");
		builder.append(certificateAppKey);
		builder.append(", certificateAppSecret=");
		builder.append(certificateAppSecret);
		builder.append(", loanRequestMail=");
		builder.append(loanRequestMail);
		builder.append(", tradeUsePayPassword=");
		builder.append(tradeUsePayPassword);
		builder.append(", contractName=");
		builder.append(contractName);
		builder.append(", bornApiUrl=");
		builder.append(bornApiUrl);
		builder.append(", allowErrorTimes=");
		builder.append(allowErrorTimes);
		builder.append(", testAccount=");
		builder.append(testAccount);
		builder.append(", appUpdateIOS=");
		builder.append(appUpdateIOS);
		builder.append(", appUpdateANDROID=");
		builder.append(appUpdateANDROID);
		builder.append(", closeDkCharge=");
		builder.append(closeDkCharge);
		builder.append(", useUserLevel=");
		builder.append(useUserLevel);
		builder.append(", loanUserAreaCitys=");
		builder.append(loanUserAreaCitys);
		builder.append(", checkInvestPwd=");
		builder.append(checkInvestPwd);
		builder.append(", outerSingKey=");
		builder.append(outerSingKey);
		builder.append(", smsByPostman=");
		builder.append(smsByPostman);
		builder.append(", giveGiftMoneyRoles=");
		builder.append(Arrays.toString(giveGiftMoneyRoles));
		builder.append(", guaranteeLicenceNoUnique=");
		builder.append(guaranteeLicenceNoUnique);
		builder.append(", phoneBlackList=");
		builder.append(phoneBlackList);
		builder.append(", smsIpWhiteList=");
		builder.append(smsIpWhiteList);
		builder.append(", androidYmKey=");
		builder.append(androidYmKey);
		builder.append(", iosYmKey=");
		builder.append(iosYmKey);
		builder.append(", isMoblieFirewall=");
		builder.append(isMoblieFirewall);
		builder.append(", requestHeadName=");
		builder.append(requestHeadName);
		builder.append(", requestHeadVallue=");
		builder.append(requestHeadVallue);
		builder.append(", wxAppID=");
		builder.append(wxAppID);
		builder.append(", wxAppSecret=");
		builder.append(wxAppSecret);
		builder.append(", wxToken=");
		builder.append(wxToken);
		builder.append("]");
		return builder.toString();
	}
	
}
