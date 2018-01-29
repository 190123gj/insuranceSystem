package com.born.insurance.util;

import java.io.File;

/**
 * 
 * 
 * @Filename ApplicationConstant.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author liujianping
 * 
 * @Email ljianping@yiji.com
 * 
 * @History <li>Author: liujianping</li> <li>Date: 2012-9-5</li> <li>Version:
 * 1.0</li> <li>Content: create</li>
 * 
 */
public class ApplicationConstant {
	public static String FILE_PATH_ROOT = "upload" + File.separator + "file" + File.separator; // 文件相对web目录路径
	public static String HTTP_PATH_ROOT = "upload/file/"; // http访问目录路径
	
	public final static int STORE_FILE_TYPE_ABSOLUTE_PATH = 1; // 文件存储 方式 绝对路径
	public final static int STORE_FILE_TYPE_RELATIVE_PATH = 0; // 文件存储 方式 相对路径
																// 相对web目录
	public static int CURRENT_STORE_FILE_TYPE = 0;
	public static String HTTP_DOMAIN_URL = "http://image.yiji.com"; // http访问域名
																	// 绝对路径方式使用
	
	public final static int UPLOAD_FILE_MAX_SIZE = 2 * 1024 * 1024; // 文件上传最大尺寸
	
	public static String ENVIRONMENT_TYPE_TEST = "TEST";
	public static String ENVIRONMENT_TYPE_DEV = "DEV";
	public static String ENVIRONMENT_TYPE_ONLINE_TEST = "ONLINE_TEST";
	public static String ENVIRONMENT_TYPE_PRODUCTION = "PRODUCTION";
	public final static String SYS_SERVICE_PHONE_KEY = "SYS_SERVICE_PHONE_KEY";
	
	public static final String SYS_PARAM_YRD_CS_EMAIL = "SYS_PARAM_YRD_CS_EMAIL";
	
	public static final String SYS_PARAM_YRD_CS_MOBILE = "SYS_PARAM_YRD_CS_MOBILE";
	
	/** 易极付商户id */
	public final static String SYS_PARAM_YJF_BUSINESS_USER_ID = "SYS_PARAM_YJF_BUSINESS_USER_ID";
	/** 安全验证key */
	public final static String SYS_PARAM_SECURITY_CHECK_KEY = "SYS_PARAM_SECURITY_CHECK_KEY";
	/** openApi访问地址 */
	public final static String SYS_PARAM_OPEN_API_URL_KEY = "SYS_PARAM_OPEN_API_URL_KEY";
	
	/** bornOpenApi访问地址 */
	public final static String SYS_PARAM_BORN_OPEN_API_URL_KEY = "SYS_PARAM_BORN_OPEN_API_URL_KEY";
	
	/** 回推地址访问地址 */
	public final static String SYS_PARAM_RETURN_URL_KEY = "RETURN_URL_KEY";
	
	/** 图片服务器地址Key */
	public final static String SYS_PARAM_IMAGE_URL_KEY = "IMAGE_URL_KEY";
	/** 产品key */
	public final static String SYS_PARAM_PRODUCT_KEY = "SYS_PARAM_PRODUCT_KEY";
	/** 平台名称 */
	public final static String SYS_PARAM_PLATFORM_NAME = "SYS_PARAM_PLATFORM_NAME";
	/** 产品名称 */
	public final static String SYS_PARAM_PRODUCT_NAME = "SYS_PARAM_PRODUCT_NAME";
	/** 外部订单号的（中间代码，不同产品不一样） */
	public final static String SYS_PARAM_OUT_BIZ_NUMBER = "SYS_PARAM_OUT_BIZ_NUMBER";
	/** 易周转冻结码 */
	public static final String TRANSFER_FREEZE_TYPE = "YZZ_GUARANTEE_FREEZE";
	/** 用户前缀 */
	public final static String SYS_YRD_PREFIXION = "SYS_YRD_PREFIXION";
	
	/** host配置 */
	public static final String SYS_PARAM_HOST = "SYS_PARAM_HOST";
	/** host通知类型 EMAIL|SMS */
	public static final String SYS_PARAM_NOTIFY_TYPE = "SYS_PARAM_NOTIFY_TYPE";
	
	/** 文件上传目录 */
	public static final String SYS_PARAM_YRD_UPLOAD_FOLDER = "SYS_PARAM_YRD_UPLOAD_FOLDER";
	
	public static final String SYS_PARAM_YRD_TRANSFER_LIMIT = "SYS_PARAM_YRD_TRANSFER_LIMIT";
	/** 投资无忧分润账户 */
	public static final String SYS_PARAM_PROFIT_SHARING_ACCOUNT = "SYS_PARAM_PROFIT_SHARING_ACCOUNT";
	
	/** 收银台的url */
	public static final String SYS_PARAM_CASHIER_HOST = "SYS_PARAM_CASHIER_HOST";
	
	/** 投资无忧在易极付平台中的用户ID:用于存储中转资金 */
	public static final String SYS_PARAM_EXCHANGE_ACCOUNT = "SYS_PARAM_EXCHANGE_ACCOUNT";
	/** 收费编码 */
	public static final String SYS_PARAM_TRADE_BIZPRODUCTCODE = "SYS_PARAM_TRADE_BIZPRODUCTCODE";
	/** 默认经济人编码 */
	public static final String SYS_PARAM_YRD_PBROKER_USER_NAME = "SYS_PARAM_YRD_PBROKER_USER_NAME";
	
	/** 企付通投资，默认经济人编码 */
	public static final String SYS_PARAM_QFT_PBROKER_USER_NAME = "SYS_PARAM_QFT_PBROKER_USER_NAME";
	/** 易结汇投资，默认经济人编码 */
	public static final String SYS_PARAM_YJH_PBROKER_USER_NAME = "SYS_PARAM_YJH_PBROKER_USER_NAME";
	
	public static final String SYS_PARAM_BORN_PBROKER_USER_NAME = "SYS_PARAM_BORN_PBROKER_USER_NAME";
	/** fop 字体库 */
	public static final String SYS_PARAM_YRD_FOP_FONT = "SYS_PARAM_YRD_FOP_FONT";
	
	/** 提现收费code */
	public static final String SYS_PARAM_WITHDRAW_CHARGE_CODE = "SYS_PARAM_WITHDRAW_CHARGE_CODE";
	/** 网银充值code */
	public static final String SYS_PARAM_BANKB2C_CHARGE_CODE = "SYS_PARAM_BANKB2C_CHARGE_CODE";
	/** 平台地址 */
	public static final String SYS_PARAM_PLATFORM_ADDRESS = "SYS_PARAM_PLATFORM_ADDRESS";
	/** 平台代偿账号 */
	public static final String SYS_PARAM_PLATFORM_REYPAY_USER_NAME = "SYS_PARAM_PLATFORM_REYPAY_USER_NAME";
	
	public final static String JJRAGENTPREFIX = "K"; // 经纪人下的投资人前缀??????
	
	public final static long EMAIL_TEMPLATE_ID = 19L;
	
	public final static double AVAILABLE_INVEST_MULTIPLE = 1;
	/** 投资上限人数 */
	public static final String MAX_INVEST_COUNT = "MAX_INVEST_COUNT";
	
	/** 数字认证服务地址 */
	public static final String SYS_PARAM_CERTIFICATE_SERVICE_URL = "SYS_PARAM_CERTIFICATE_SERVICE_URL";
	/** 数字认证Key */
	public static final String SYS_PARAM_CERTIFICATE_APP_KEY = "SYS_PARAM_CERTIFICATE_APP_KEY";
	/** 数字认证密钥Key */
	public static final String SYS_PARAM_CERTIFICATE_APP_SECRET = "SYS_PARAM_CERTIFICATE_APP_SECRET";
	
	/** 邮件发送服务器 */
	public static final String SYS_PARAM_MAIL_SERVER = "SYS_PARAM_MAIL_SERVER";
	/** 邮件发送服务端口 */
	public static final String SYS_PARAM_MAIL_SERVERPORT = "SYS_PARAM_MAIL_SERVERPORT";
	/** 发件人地址 */
	public static final String SYS_PARAM_MAIL_USERNAME = "SYS_PARAM_MAIL_USERNAME";
	/** 发件人邮箱密码 */
	public static final String SYS_PARAM_MAIL_PASSWORD = "SYS_PARAM_MAIL_PASSWORD";
	/** 发件人别名 */
	public static final String SYS_PARAM_MAIL_NICKNAME = "SYS_PARAM_MAIL_NICKNAME";
	/** 发件人地址 */
	public static final String SYS_PARAM_MAIL_SENDERNAME = "SYS_PARAM_MAIL_SENDERNAME";
	
	/** 当前所属行业特性 */
	public static final String SYS_PARAM_CURRENT_INDUSTRY = "SYS_PARAM_CURRENT_INDUSTRY";
	
	/** EZMONEY访问地址 */
	public static final String SYS_PARAM_EZMONEY_HTTP_URL = "SYS_PARAM_EZMONEY_HTTP_URL";
	
	/** 交易是否使用交易密码 */
	public static final String SYS_PARAM_TRADE_USER_PAY_PASSWORD = "SYS_PARAM_TRADE_USER_PAY_PASSWORD";
	
	public static final String SYS_PARAM_CONTRACT_NAME = "SYS_PARAM_CONTRACT_NAME";
	/** YJF平台地址 */
	public static final String SYS_PARAM_YJF_URL = "SYS_PARAM_YJF_URL";
	/** YJF平台地址 */
	public static final String CQP2PMD5KEY = "CQP2PMD5KEY";
	/** YJF平台地址 */
	public static final String SYS_PARAM_SMS_BY_POSTMAN = "SYS_PARAM_SMS_BY_POSTMAN";
	
	/** 易八平台和理财平台对接的冻结码 */
	public static final String YJF_EIGHT_FREEZE = "YJF_EIGHT_FREEZE";// "YJF_EIGHT_FREEZE"
																		// "TRANSFER_FREEZE";
	
	public static EnvironmentConfig ENV_CONFIG = new EnvironmentConfig();
	
	public int getCurrentStoreFileType() {
		return CURRENT_STORE_FILE_TYPE;
	}
	
	/** 经纪人 */
	public final static long ROLE_ID_BROKER = 11L;
	/** 投资人 */
	public final static long ROLE_ID_INVESTOR = 12L;
	/** 平台账户 */
	public final static long ROLE_ID_PLATFORM = 7L;
	/** ICP */
	public static final String SYS_PARAM_PRODUCT_ICP = "SYS_PARAM_PRODUCT_ICP";
	
	public static final String SYS_PARAM_ALLCOMMON = "SYS_PARAM_ALLCOMMON";
	
	/** 接收借款请求邮箱地址 */
	public static final String SYS_PARAM_MAIL_LOAN_REQUEST = "SYS_PARAM_MAIL_LOAN_REQUEST";
	/** 修改支付密码URL */
	public static final String SYS_PARAM_MODIFYPASSWORDHTTPURL = "SYS_PARAM_MODIFYPASSWORDHTTPURL";
	/** QQ号 */
	public static final String SYS_PARAM_PRODUCT_QQ = "SYS_PARAM_PRODUCT_QQ";
	
	public static final String SYS_PARAM_INCREASINGAMOUNT = "SYS_PARAM_INCREASINGAMOUNT";
	/** 投资积分率 */
	public static final String SYS_PARAM_INVSET_POINTS_RATE = "SYS_PARAM_INVSET_POINTS_RATE";
	/** 推荐人积分 */
	public static final String SYS_PARAM_REFEREE_POINTS = "SYS_PARAM_REFEREE_POINTS";
	
	/** 开放后台添加借款人个人信息功能 */
	public static final String SYS_PARAM_ADD_LOANER_BASE_INFO = "SYS_PARAM_ADD_LOANER_BASE_INFO";
	/** 添加app版本更新功能 */
	public static final String SYS_APP_UPDATE_IOS = "SYS_APP_UPDATE_IOS";
	public static final String SYS_APP_UPDATE_ANDROID = "SYS_APP_UPDATE_ANDROID";
	/** 登陆密码框是否需要控件 */
	public static final String SYS_PARAM_HAVE_LOGPWD_CONTROLLER = "SYS_PARAM_HAVE_LOGPWD_CONTROLLER";
	/** app审核账号配置 */
	public static final String SYS_PARAM_TEST_ACCOUNT = "SYS_PARAM_TEST_ACCOUNT";
	
	/** 红包账号 */
	public static final String SYS_PARAM_GIFT_MONEY_ACCOUNT = "SYS_PARAM_GIFT_MONEY_ACCOUNT";
	/** 登陆不要验证码 */
	public static final String SYS_PARAM_LOGIN_WITHOUT_CHECKCODE = "SYS_PARAM_LOGIN_WITHOUT_CHECKCODE";
	
	/** 是否启用融资信息模块 */
	public static final String SYS_PARAM_LOAN_INFO_ITEM = "SYS_PARAM_LOAN_INFO_ITEM";
	/** 密码错误几次后需要验证码 */
	public static final String SYS_PARAM_ALLOW_ERROR_TIMES = "SYS_PARAM_ALLOW_ERROR_TIMES";
	/** APP端控制是否显示代扣充值按钮 */
	public static final String SYS_PARAM_APP_CLOSE_DKCHARGE = "SYS_PARAM_APP_CLOSE_DKCHARGE";
	
	/** 是否仅支持易极付登录 */
	public static final String SYS_PARAM_ONLY_YJF_LOGIN = "SYS_PARAM_ONLY_YJF_LOGIN";
	
	/** app用控制yjf修改密码按钮的颜色 */
	public static final String SYS_PARAM_YJF_MODIFYPWD_BTNCOLOR = "SYS_PARAM_YJF_MODIFYPWD_BTNCOLOR";
	
	/** 二级经济人标志 */
	public static final String SYS_PARAM_TWO_LEVEL_BROKER = "SYS_PARAM_TWO_LEVEL_BROKER";
	
	/** 经纪人有效期 */
	public static final String SYS_PARAM_BROKER_VALIDITY = "SYS_PARAM_BROKER_VALIDITY";
	
	/** 是否使用用户等级 */
	public static final String SYS_PARAM_USE_USER_LEVEL = "SYS_PARAM_USE_USER_LEVEL";
	
	public static final String SYS_PARAM_VIP_USER_LEVEL = "SYS_PARAM_VIP_USER_LEVEL";
	
	/** 是否支持企付通投资 */
	public static final String SYS_PARAM_QFT_INVEST = "SYS_PARAM_QFT_INVEST";
	
	/** 是否支持担保金 */
	public static final String SYS_PARAM_PAY_GUARANTEE_AMOUNT = "SYS_PARAM_PAY_GUARANTEE_AMOUNT";
	/** 业务扩展城市 */
	public static final String SYS_PARAM_LOAN_USER_AREA_CITYS = "SYS_PARAM_LOAN_USER_AREA_CITYS";
	/** 平台系统缩写名 */
	public static final String SYS_PARAM_SYSTEM_NAME = "SYS_PARAM_SYSTEM_NAME";
	/** 商户联盟 */
	public static final String SYS_PARAM_YJF_MERCHANT_UNION = "SYS_PARAM_YJF_MERCHANT_UNION";
	/** APP充值或提现收费规则 */
	public static final String SYS_APP_CHARGE_RULES = "SYS_APP_CHARGE_RULES";
	/** 提现是否跳到born_api */
	public static final String SYS_PARAM_WITHdRAW_GO_BORN_URL = "SYS_PARAM_WITHdRAW_GO_BORN_URL";
	/** 长金宝验证支付密码 */
	public static final String SYS_PARAM_CHECK_INVEST_PWD = "SYS_PARAM_CHECK_INVEST_PWD";
	/** 是否是投资无忧平台 */
	public static final String SYS_PARAM_IS_TZWY = "SYS_PARAM_IS_TZWY";
	/** 外部签名key */
	public static final String SYS_PARAM_OUTER_SING_KEY = "SYS_PARAM_OUTER_SING_KEY";
	
	/** 系统指定充值收费模式 */
	public static final String SYS_PARAM_DEPOSIT_CHARGE_MODE = "SYS_PARAM_DEPOSIT_CHARGE_MODE";
	
	/** 系统指定充值收费模式 */
	public static final String SYS_PARAM_WITHDRAW_CHARGE_MODE = "SYS_PARAM_WITHDRAW_CHARGE_MODE";
	
	/** 配置获取支付令牌成功跳转地址 */
	public static final String SYS_PARAM_SUCCESS_RETURN_URL = "SYS_PARAM_SUCCESS_RETURN_URL";
	/** 配置获取支付令牌失败跳转地址 */
	public static final String SYS_PARAM_FAIL_RETURN_URL = "SYS_PARAM_FAIL_RETURN_URL";
	
	/** 免费提现的次数 */
	public static final String SYS_PARAM_MONTH_FREE_WITHDRAW_COUNT = "SYS_PARAM_MONTH_FREE_WITHDRAW_COUNT";
	
	/** 固定提现卡 */
	public static final String SYS_PARAM_LOADER_FIXED_BANK_CARD = "SYS_PARAM_LOADER_FIXED_BANK_CARD";
	
	/** 发放红包角色 */
	public static final String SYS_PARAM_GIVE_GIFT_MONEY_ROLES = "SYS_PARAM_GIVE_GIFT_MONEY_ROLES";
	
	/** 收取债权手续费 默认收取(Y)不用配置，如果平台不收取，请配置(N) */
	public static final String SYS_PARAM_DEBT_CHARGE = "SYS_PARAM_DEBT_CHARGE";
	
	/** 流标退不退奖励 */
	public static final String SYS_PARAM_UN_FULL_GIVE_UP_MONEY = "SYS_PARAM_UN_FULL_GIVE_UP_MONEY";
	
	/** 自动签约，传给签约平台的还款类型 */
	public static final String SYS_PARAM_PLATFORM_REPAY_TYPE = "SYS_PARAM_PLATFORM_REPAY_TYPE";
	
	/** 是否使用融资人最高授信额度，默认不使用(N)，使用请配置Y */
	public static final String SYS_PARAM_MAX_LOAN_AMOUNT_LIMIT = "SYS_PARAM_MAX_LOAN_AMOUNT_LIMIT";
	/** 是否验证承诺函编号唯一性，默认验证(Y)，不验证请配置N */
	public static final String SYS_PARAM_GUARANTEE_LICENCE_NO_UNIQUE = "SYS_PARAM_GUARANTEE_LICENCE_NO_UNIQUE";
	
	/** 手机黑名单IP地址 */
	public static final String SYS_PARAM_PHONE_BLACK_LIST = "SYS_PARAM_PHONE_BLACK_LIST";
	
	/** 手机黑名单IP地址 */
	public static final String SYS_PARAM_SMS_IP_WHITE_LIST = "SYS_PARAM_SMS_IP_WHITE_LIST";
	
	/** 手机防火墙 */
	public static final String SYS_PARAM_MOBLIE_FIREWALL = "SYS_PARAM_MOBLIE_FIREWALL";
	
	/** 不使用弱实名的平台 **/
	public static final String SYS_PARAM_NO_USE_WEAK_REAL_NAME = "SYS_PARAM_NO_USE_WEAK_REAL_NAME";
	/** 友盟推送 android Key */
	public static final String SYS_PARAM_YOUMENG_KEY_ANDROID = "SYS_PARAM_YOUMENG_KEY_ANDROID";
	/** 友盟推送 ios Key */
	public static final String SYS_PARAM_YOUMENG_KEY_IOS = "SYS_PARAM_YOUMENG_KEY_IOS";
	
	/** pos设备列表 id:别号;id:别号;id:别号; */
	public static final String SYS_PARAM_POS_TERM_LIST = "SYS_PARAM_POS_TERM_LIST";
	/** pos商户垫支的yjfId */
	public static final String SYS_PARAM_POS_MERCHANT_YJF_ACCOUNT_ID = "SYS_PARAM_POS_MERCHANT_YJF_ACCOUNT_ID";
	
	/** 微信对接三要素appId */
	public static final String SYS_PARAM_WX_APP_ID = "SYS_PARAM_WX_APP_ID";
	/** 微信对接三要素AppSecret */
	public static final String SYS_PARAM_WX_APP_SECRET = "SYS_PARAM_WX_APP_SECRET";
	/** 微信对接三要素Token */
	public static final String SYS_PARAM_WX_TOKEN = "SYS_PARAM_WX_TOKEN";
	
	/** 申请债权转让笔数 */
	public static final String SYS_PARAM_DEBT_TRANSFER_COUNT = "SYS_PARAM_DEBT_TRANSFER_COUNT";
	
	/** Y:实名认证时，一个身份证号只能对应一个用户 */
	public static final String SYS_PARAM_PERSONAL_CERTNO_UNIQUE = "SYS_PARAM_PERSONAL_CERTNO_UNIQUE";
	/** 借款申请 未审核达到多少数量就不允许再申请借款 */
	public static final String SYS_PARAM_LOAN_APPLY_NO_CHECK_LIMIT = "SYS_PARAM_LOAN_APPLY_NO_CHECK_LIMIT";
	/** Y：债权转让无限次 */
	public static final String SYS_PARAM_DEBT_TRANSFER_NO_LIMIT = "SYS_PARAM_DEBT_TRANSFER_NO_LIMIT";
	
	public static final String SYS_PARAM_SAME_CARD_ONE_REGISTER_USER = "SYS_PARAM_SAME_CARD_ONE_REGISTER_USER";
	
}
