package com.born.insurance.util;

import com.yjf.common.lang.security.DigestUtil;

/**
 * 
 *                       
 * @Filename Constants.java
 *
 * @Description openapi常量类 
 *
 * @Version 1.0
 *
 * @Author bohr.qiu
 *
 * @Email qzhanbo@yiji.com
 *       
 * @History
 *<li>Author: bohr.qiu</li>
 *<li>Date: 2012-9-21</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class Constants {
	
	/**
	 * 接入渠道字符集key
	 */
	public static final String	CHARSET_KEY				= "inputCharset";
	
	/**
	 * 接入渠道默认字符集
	 */
	public static final String	DEFAULT_CHARSET			= "UTF-8";
	
	/**
	 * 接入渠道签名key
	 */
	public static final String	SIGN_KEY				= "sign";
	
	/**
	 * 请求处理错误后调用地址key
	 */
	public static final String	ERROR_NOTIFY_URL_KEY	= "errorNotifyUrl";
	
	/**
	 * 接入渠道服务类型key
	 */
	public static final String	SERVICE_TYPE_KEY		= "service";
	
	/**
	 * 页面跳转同步通知页面路径key。
	 * 易极付处理完请求后,当前页面自动跳转到商户网站里指定页面的http 路径。 
	 */
	public static final String	RETURN_URL_KEY			= "returnUrl";
	
	/**
	 * 服务器异步通知页面路径key。
	 * 易极付服务器主动通知商户网站里指定的页面 http 路径。 
	 */
	public static final String	NOTIFY_URL_KEY			= "notifyUrl";
	
	/**
	 *  处理失败后，通知渠道方的错误码key
	 */
	public static final String	NOTIFY_ERROR_CODE_KEY	= "errorCode";
	
	/**
	 *  接入渠道订单标识号
	 */
	public static final String	ORDER_NO_KEY			= "orderNo";
	
	/**
	 *   交易号KEY
	 */
	public static final String	TRADE_NO_KEY			= "tradeNo";
	
	/**
	 * 合作伙伴id
	 */
	public static final String	PARTNER_ID_KEY			= "partnerId";
	
	/**
	 * 应答码
	 */
	public static final String	RESULT_CODE				= "resultCode";
	
	/**
	 * 返回信息
	 */
	public static final String	RESULT_MESSAGE			= "resultMessage";
	
	/**
	 * 摘要算法类型key
	 */
	public static final String	SIGN_TYPE				= DigestUtil.SIGN_TYPE_KEY;
	
	public static final String	MERCHANT_INFO			= "merchant_info";
	
	/** 数据列表集*/
	public static final String	DATALIST				= "dataList";
	
	/** 跳转action*/
	public static final String	REDIRECT_ACTION			= "redirectAction";
	
	/** 通知*/
	public static final String	NOTIFY					= "notify";
	
	/** 跳转*/
	public static final String	JUMP					= "jump";
	
	/** 成功标志*/
	public static final String	EXECUTE_SUCCESS			= "EXECUTE_SUCCESS";
	public static final String	DEPOSIT_SUCCESS			= "DEPOSIT_SUCCESS";
	public static final String	WITHDRAW_SUCCESS		= "WITHDRAW_SUCCESS";
}
