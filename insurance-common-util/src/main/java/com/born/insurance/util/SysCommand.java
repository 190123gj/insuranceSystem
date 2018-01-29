package com.born.insurance.util;

public class SysCommand {
	
	/*
	 * 代扣、提现参数
	 */
	/*小于500万提现接口参数名*/
	public static final String	WITHDRAWAL_SERVICE			= "yzzApplyWithdraw";
	/*大于500万提现接口参数名*/
	public static final String	LARGEWITHDRAWAL_SERVICE		= "yzzApplyWithdrawAudit";
	/*代扣接口参数名*/
	public static final String	DEDUCT_SERVICE				= "deductDeposit.apply";
	/*代扣接口参数名*/
	public static final String	TRANSFER_SERVICE			= "yzzTradeTransferBankAccount";
	public static final double	QUOTA						= 5000000.00;
	//	private double QUOTA = 20.00;
	/*对私提现*/
	public static final String	PRIVATETAG					= "N";								//对私
	/*对公提现*/
	public static final String	PUBLICTAG					= "Y";								//对公
	public static final String	BIZIDENTITY					= "EASY_LOAN";
	public static final String	OWNER						= "EASY_LOAN";
	public static final String	SUBOWNER					= "19finance";
	/*对私提现收费参数*/
	public static final String	PRIVATETRADEPRODUCTCODE		= "20131010-financingyjWd";
	/*对公提现收费参数*/
	public static final String	PUBLICTRADEPRODUCTCODE		= "20131018-financingyjWithdraw";
	/*转账到卡收费参数*/
	public static final String	TRANSFERTRADEPRODUCTCODE	= "20131011-yizhouzhuandealfree";
	/*提现bizNo*/
	public static final String	WD_BIZNO					= "720";
	/*代扣bizNo*/
	public static final String	DE_BIZNO					= "710";
	/*借记卡*/
	public static final String	DEBITCARD					= "DEBIT_CARD";
	/*信用卡*/
	public static final String	CREDITCARD					= "CREDIT_CARD";
	/*同步*/
	public static final String	SYNC						= "sync";
	/*异步*/
	public static final String	ASYNC						= "async";
	
	/*
	 * 用户类别
	 */
	/*个人用户*/
	public static final String	GR							= "GR";
	/*机构用户*/
	public static final String	JG							= "JG";
	
	/*
	 * openpai返回结果参数
	 */
	/*返回Map结果中取dataMap的key值*/
	
	/*返回Map中判断返回信息key值*/
	public static final String	ISSUCCESS					= "isSuccess";
	/*失败标识*/
	public static final String	FALSE						= "false";
	/*成功标识*/
	public static final String	TRUE						= "true";
	
	/**
	 * 实名认证接口名
	 */
	
}
