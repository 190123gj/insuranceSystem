package com.born.insurance.util;

/**
 * 
 * 
 * @Filename YrdConstants.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-2</li> <li>Version: 1.0</li>
 * <li>Content: create</li> 常量
 */
public class FcsConstants {
	
	/**
	 * 交易状态常量
	 * 
	 * @Filename YrdConstants.java
	 * 
	 * @Description
	 * 
	 * @Version 1.0
	 * 
	 * @Author qichunhai
	 * 
	 * @Email qchunhai@yiji.com
	 * 
	 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-2</li> <li>Version:
	 * 1.0</li> <li>Content: create</li>
	 * 
	 */
	public static class CommonConfig {
		/**
		 * 实名认证通知类型
		 */
		public static String REAL_NAME_NOTIFY_TYPE = "sms";
		public static String INVEST_NOTIFY_TYPE = "sms";
		public static double AVAILABLE_INVEST_MULTIPLE = 1.0;
		public static double FESTIVAL = 1.0;
		/*
		public static String MAIL_MAILSERVER = "smtp.163.com";
		public static String MAIL_MAILSERVERPORT = "25";
		public static String MAIL_USERNAME = "yibajinrong@163.com";
		public static String MAIL_PASSWORD = "yibatouziwuyou";
		public static String MAIL_NICKNAME = AppConstantsUtil.getProductName() + "金融客服";
		public static String MAIL_SENDERNAME = "yibajinrong@163.com";
		*/
		/** 还款处理日期 */
		public static String REPAY_PROCESSDAYS = "3";
		
	}
	
	public static class TradeStatus {
		/**
		 * 待审核
		 */
		public final static short CHECK_PENDING = 0;
		/**
		 * 募集中-待成立
		 */
		public final static short TRADING = 1;
		/**
		 * 已成立
		 */
		public final static short REPAYING = 2;
		/**
		 * 交易完成
		 */
		public final static short REPAY_FINISH = 3;
		/**
		 * 交易失败
		 */
		public final static short FAILED = 4;
		/**
		 * 还款失败
		 */
		public final static short REPAYING_FAILD = 5;
		/**
		 * 担保公司审核中
		 */
		public final static short GUARANTEE_AUDITING = 6;
		/**
		 * 违约代偿完成
		 */
		public final static short COMPENSATORY_REPAY_FINISH = 7;
		/**
		 * 待还款
		 */
		public final static short DOREPAY = 8;
	}
	
	/**
	 * 
	 * 
	 * 
	 * @Filename YrdConstants.TransferComment.java
	 * 
	 * @Description
	 * 
	 * @Version 1.0
	 * 
	 * @Author yhl
	 * 
	 * @Email yhailong@yiji.com
	 * 
	 * @History <li>Author: yhl</li> <li>Date: 2013-6-18</li> <li>Version: 1.0</li>
	 * <li>Content: create</li> 转帐备注
	 */
	public static class TransferComment {
		public static String YRD_REREPAY = AppConstantsUtil.getProductName() + "补充还款";
		/**
		 * 投资无忧分润
		 */
		public static String YRD_DIVISION = AppConstantsUtil.getProductName() + "分润";
		/**
		 * 投资无忧投资
		 */
		public static String YRD_INVEST = AppConstantsUtil.getProductName() + "投资";
		/**
		 * 担保金转账
		 */
		public static String YRD_GUARANTEE_AMOUNT = AppConstantsUtil.getProductName() + "担保转帐";
		
		/**
		 * 投资无忧还款转账
		 */
		public static String YRD_REPAY_PRINCIPAL = AppConstantsUtil.getProductName() + "还款本金";
		
		public static String YRD_REPAY_INTEREST = AppConstantsUtil.getProductName() + "还款利息";

		public static String YRD_REPAY_PRINCIPAL_INTEREST = AppConstantsUtil.getProductName() + "还款本息";
		
		/**
		 * 投资无忧投资
		 */
		public static String YRD_PLATFORM_CHAEGE = AppConstantsUtil.getProductName() + "平台收费";
		
		public static String YRD_TRADE_CHAEGE = AppConstantsUtil.getProductName() + "交易手续费";
		
		/**
		 * 投资无忧债权转让
		 */
		public static String YRD_DEBT_TRANSFER = AppConstantsUtil.getProductName() + "债权转让";
		/**
		 * 投资无忧债权转让
		 */
		public static String YRD_DEBT_TRANSFER_CHARGE = AppConstantsUtil.getProductName()
														+ "债权转让收费";
	}
	
	public static class SesssionKey {
		/**
		 * 签名银行key
		 */
		public static String SIGN_BANK_KEY = "SIGN_BANK_KEY";
		
	}
	
	public static class ChargeDetail {
		/**
		 * 服务性收费
		 */
		public static String CHARGE_DETAIL_NOTE = AppConstantsUtil.getProductName() + "服务性收费";
	}
	
	/**
	 * 消息通知常量
	 * @author CHARLEY
	 * 
	 */
	public static class MessageNotifyConstants {
		
		public static String INVEST_NOTIFY_TYPE = CommonConfig.INVEST_NOTIFY_TYPE;
		public static String GREETING_CONTENT = "尊敬的" + AppConstantsUtil.getProductName() + "会员：";
		public static final String ISNOTIFIED_NO = "NO";
		public static final String ISNOTIFIED_YES = "YES";
		public static String NOTIFY_TYPE_EMAIL = "EMAIL";
		public static String NOTIFY_TYPE_SMS = "sms";
		public static String LOCAL_CLIENT = "yijiu";
		
		public static String PAYlOAN_FAILD_CONTENT = SmsConstantsProperty
			.getContent("PAYlOAN_FAILD_CONTENT");
		public static String UNFULL_LOANDEMAND_OVERDUE_CONTENT = SmsConstantsProperty
			.getContent("UNFULL_LOANDEMAND_OVERDUE_CONTENT"); //"您有一笔借款融资日期已达到,但未满标,借款名称：var1,申请借款金额：var2元,已融资金额：var3元,系统将自动处理交易为未成立状态,请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理!";
		public static String UNFULL_INVESTEDlOAND_OVERDUE_CONTENT = SmsConstantsProperty
			.getContent("UNFULL_INVESTEDlOAND_OVERDUE_CONTENT"); //"您在"+ AppConstantsUtil.getProductName()+ "网贷平台的一笔投资,交易名称：var1,投资金额：var2元,融资期限已达到,但该笔融资未满标,系统将自动处理交易为未成立状态,该笔借款投资冻结金额将自动返回原有帐户，请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理!";
		public static String AUTO_CHANGE_EXPIRELOANTODOREPAY_CONTENT = SmsConstantsProperty
			.getContent("AUTO_CHANGE_EXPIRELOANTODOREPAY_CONTENT"); //"您有一笔借款已到期，借款名称：var1,实际借款金额：var2元,系统已自动为您转入待还款中，请及时处理还款，请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理！";
		public static String AUTO_PAYLOAN_CONTENT = SmsConstantsProperty
			.getContent("AUTO_PAYLOAN_CONTENT"); //"您有一笔借款自动还款失败,借款名称：var1,实际借款金额：var2元,原因：var3,请登录: hostLink 进行核实,如有任何疑问，请联系我公司客服进行处理！ ";
		public static String LOAN_DEMAND_INVESTED_CONTENT = SmsConstantsProperty
			.getContent("LOAN_DEMAND_INVESTED_CONTENT"); //"您在"+ AppConstantsUtil.getProductName()+ "网贷平台的一笔借款已满额,该笔借款名称：var1,申请融资金额：var2元,已获融资：var3元,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String LOAN_DEMAND_INVESTED_PLATFORM = "var1项目已经满标，请通知审核人员尽快完成审核，详情请登录平台查询。";
		public static String REPAY_NOTIFY_LOANER_CONTENT = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_LOANER_CONTENT"); //"您在"+ AppConstantsUtil.getProductName()+ "网贷平台的一笔借款还款处理成功,借款名称：var1,实际借款金额：var2元,还款金额：var3元,还款时间：var4,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REPAY_NOTIFY_INVESTOR_CONTENT = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT"); //"您在"+ AppConstantsUtil.getProductName()+ "网贷平台的一笔投资已收到还款,交易名称：var1,投资金额：var2元, 本次还款：var4元,还款时间:var3,具体到账时间以实际到账时间为准,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String PREPAY_NOTIFY_INVESTOR_CONTENT = SmsConstantsProperty
			.getContent("PREPAY_NOTIFY_INVESTOR_CONTENT"); //您在#ProductName#网贷平台的一笔投资已收到提前还款,交易名称：var1,还款金额：var4元,还款时间:var3,具体到账时间以实际到账时间为准,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！
		
		public static String REPAY_NOTIFY_BROKER_CONTENT = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_BROKER_CONTENT"); //您的投资人:var1已收到#ProductName#平台的一笔还款var3元，该项目已还完本息,登录:hostLink查看详情。
		public static String REPAY_NOTIFY_BROKER_CONTENT_4PLAN = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_BROKER_CONTENT_4PLAN");//您的投资人:var1在#ProductName#平台的一笔投资已收到本月利息var2元，登录:hostLink查看详情。
		public static String INVEST_SUCCESS_BROKER_CONTENT = SmsConstantsProperty
			.getContent("INVEST_SUCCESS_BROKER_CONTENT");//您的投资人:var1于var2投资var3元，登录:hostLink查看详情。
		public static String REPAY_NOTIFY_BROKER_CONTENT_PRINCIPAL = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_BROKER_CONTENT_PRINCIPAL");//您的投资人:var1在#ProductName#平台的一笔投资已收到本月等额本息还款var2元，登录:hostLink查看详情。
		public static String REPAY_NOTIFY_BROKER_CONTENT_PRINCIPAL_END = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_BROKER_CONTENT_PRINCIPAL_END");//您的投资人:var1在#ProductName#平台的一笔投资已收到本月等额本息还款var2元，该项目已还完本息,登录:hostLink查看详情。
		
		public static String REPAY_NOTIFY_INVESTOR_CONTENT_4PLAN = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT_4PLAN");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔投资已收到第var5期还款,交易名称：var1, 本期还款：var4元,还款时间:var3,具体到账时间以实际到账时间为准,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REPAY_NOTIFY_INVESTOR_CONTENT_PRINCIPAL = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT_PRINCIPAL");//您在#ProductName#平台的一笔投资已收到本月等额本息还款var2元，登录:hostLink查看详情。
		public static String REPAY_NOTIFY_INVESTOR_CONTENT_PRINCIPAL_END = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT_PRINCIPAL_END");//您在#ProductName#平台的一笔投资已收到本月等额本息还款var2元，该项目已还完本息,登录:hostLink查看详情。
		public static String LOAN_SUCCESS_LOANER_CONTENT = SmsConstantsProperty
			.getContent("LOAN_SUCCESS_LOANER_CONTENT"); //"您在"+ AppConstantsUtil.getProductName()+ "网贷平台的一笔借款承诺机构审核通过现已放款,交易名称：var1,实际借款金额：var2元,实际到账金额：var3元,放款时间:var4,具体到账时间以实际到账时间为准,请登录:hostLink进行核实,如有任何疑问,请联系我公司客服进行处理！";
		
		public static String LOAN_SUCCESS_INVESTOR_CONTENT = SmsConstantsProperty
			.getContent("LOAN_SUCCESS_INVESTOR_CONTENT");//您在"+ AppConstantsUtil.getProductName()+ "金融投资的项目"var1" 已于var3 成立 ,投资金额 var2元，合同到期日:var4,请登录:hostLink进行核实,如有任何疑问,请联系我公司客服进行处理！。
		
		public static String REAL_NAME_AUTH_NOTIFY = SmsConstantsProperty
			.getContent("REAL_NAME_AUTH_NOTIFY"); //"请查看您在"+ AppConstantsUtil.getProductName()+"网贷平台提交的实名认证结果,账户名：var1, 认证状态：var2, 请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String DEMAND_CONFIRMED_NOTIFY = SmsConstantsProperty
			.getContent("DEMAND_CONFIRMED_NOTIFY"); //"借款人已确认一笔新的资金需求，名称：var1,融资金额：var2元，确认金额：var3元，请做好相关准备便于资金流转!";
		public static String REPAY_ADVANCED_NOTIFY = SmsConstantsProperty
			.getContent("REPAY_ADVANCED_NOTIFY"); //"一笔新的交易即将进入还款期，名称：var1, 借款金额：var2元，还款时间：var3，请做好相关准备便于资金流转!";
		public static String PDF_OK_TO_INVESTOR = SmsConstantsProperty
			.getContent("PDF_OK_TO_INVESTOR"); //"您在"+ AppConstantsUtil.getProductName()+ "网贷平台的一笔投资, 交易名称：var1,承诺机构已上传合同与承诺函，请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String SUCCESS_INVEST_NOTIFY = SmsConstantsProperty
			.getContent("SUCCESS_INVEST_NOTIFY"); //"您在"+ AppConstantsUtil.getProductName()+ "金融的一笔投资操作已成功，借款名称：var1，投资金额：var2元，如有任何疑问,请联系我公司客服进行处理！";
		public static String SUCCESS_DEBT_TRANSFER_SELLER = SmsConstantsProperty
			.getContent("SUCCESS_DEBT_TRANSFER_SELLER"); //"您在"+ AppConstantsUtil.getProductName()+ "金融的一笔债权转让操作已成功，借款名称：var1，转让金额：var2元，如有任何疑问,请联系我公司客服进行处理！";
		public static String SUCCESS_DEBT_TRANSFER_BUYER = SmsConstantsProperty
			.getContent("SUCCESS_DEBT_TRANSFER_BUYER"); //"您在"+ AppConstantsUtil.getProductName()+ "金融的一笔债权转让已经成功转让，借款名称：var1，转让金额：var2元，转让方(var3)  如有任何疑问,请联系我公司客服进行处理！";
		public static String NOTIFY_GUARANTEE_AUDIT = SmsConstantsProperty
			.getContent("NOTIFY_GUARANTEE_AUDIT");//"您有一笔借款融资需要审核,借款名称：var1,请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理!";
		public static String SUCCESS_OPEN_ACCOUNT_NOTIFY = "您在"
															+ AppConstantsUtil.getProductName()
															+ "平台提交的融资申请，已为你开通融资用户，用户名:var1，密码:var2，认证状态:var3，请登录您的邮箱进行激活，如有任何疑问，请联系客服进行处理！";
		
		public static String REPAY_NOTIFY_INVESTOR_CONTENT_EQUITY = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT_EQUITY");//您在#ProductName#金融平台的一笔投资已收到分润,交易名称：var1, 本期分润：var2元,分润时间:var3,具体到账时间以实际到账时间为准,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！	
		
		public static String REREPAY_NOTIFY_PLATFORM_CONTENT = "您在"
																+ AppConstantsUtil.getProductName()
																+ "网贷平台的垫付的一笔还款，融资人现已补还款,借款名称：var1,补还款金额：var2元,还款时间：var3,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		
		public static String REGIST_NOTIFY_CONTENT = SmsConstantsProperty
			.getContent("REGIST_NOTIFY_CONTENT"); //非常高兴你能成为我们的用户!感谢你在"+ AppConstantsUtil.getProductName()+ "创建了账户，现在就开始体验简单，安全，好收益的互联网金融投资吧！ 
		
		public static String LOANED_NOTIFY_INVERTOR_CONTENT = SmsConstantsProperty
			.getContent("LOANED_NOTIFY_INVERTOR_CONTENT");
		//				"您好，您之前投资的融资人var1又有一笔新的融资需求，借款名称：var2，如有投资意愿请前往首页hostLink查看标的信息，祝您投资愉快。";
		
		//以下为通过http://smsops.yiji.com/sms/template/list?page=1 设置短信内容时，对应的“模板名”，后续可能添加其他的：
		
		//1 通用校验码   
		public static String TEMPLATE_NAME_COMMON_VALIDATE_CODE = SmsConstantsProperty
			.getContent("TEMPLATE_NAME_COMMON_VALIDATE_CODE"); //模板名 = 验证码2_53
		
	}
	
	/**
	 * 合同状态
	 * @author CHARLEY
	 * 
	 */
	
	public static class GuaranteeTradeStatus {
		/**
		 * 待审核
		 */
		public final static String GUARANTEE_CREATE = "合约创建";
		/**
		 * 募集中
		 */
		public final static String GUARANTEE_SIGN = "合约签发";
		/**
		 * 还款中
		 */
		public final static String GUARANTEE_IMPLEMENT = "合约履行中";
		/**
		 * 交易完成
		 */
		public final static String GUARANTEE_FINISH = "合约完毕";
		/**
		 * 交易失败
		 */
		public final static String GUARANTEE_FAILD = "合约失效";
		/**
		 * 合约违约
		 */
		public final static String GUARANTEE_BROKEN = "合约违约";
		/**
		 * 担保机构审核中
		 */
		public final static String GUARANTEE_AUDITING = "承诺机构审核中";
		/**
		 * 违约代偿完成
		 */
		public final static String COMPENSATORY_REPAY_FINISH = "违约代偿完成";
	}
	
	/**
	 * 时间相关
	 * @author CHARLEY
	 * 
	 */
	public static class TimeRelativeConstants {
		public final static int DAYSOFAYEAR = 360;
	}
	
	/**
	 * 转让只能是实际金额的正负比率（当前估算12%）
	 * 
	 * 
	 * @author Administrator
	 * 
	 */
	public static class DeptTransferConstants {
		public final static double DEPT_TRANSFER_RATE = 0.12;
	}
	
	/**
	 * 充值相关
	 * @author CHARLEY
	 * 
	 */
	public static class CoreEngineConstants {
		public final static String BUSINESSNO = "710";
		public final static String RECHARGEFROM = "EASY_LOAN";
	}
	
	public static class RoleId {
		/**
		 * 操作员
		 */
		public static final String OPERATOR = "14";
		/**
		 * 投资人
		 */
		public static String INVESTOR = "12";
		/**
		 * 借款人
		 */
		public static String LOANER = "13";
		/**
		 * 经纪人
		 */
		public static String BROKER = "11";
		/**
		 * 运营机构
		 */
		public static String MARKETING = "10";
		/**
		 * 保荐机构
		 */
		public static String SPONSOR = "9";
		/**
		 * 担保机构
		 */
		public static String GUARANTEE = "8";
		/**
		 * 投资无忧金融
		 */
		public static String YRD = "7";
	}
	
	/**
	 * 用户相关
	 * @author CHARLEY
	 * 
	 */
	public static class UserInfoConstants {
		/**
		 * 易极付账户yrd前缀
		 */
		public static String YRDPREFIXION = "yrd_";
		/**
		 * 易极付账户yrd经纪人
		 */
		public static String YRDPBROKERUSERNAME = "yrdfinance_broker";
	}
	
	public static class TradeViewCanstants {
		public static String TRADE_DEFAULT = "状态暂无";
		public static String TRADE_TRADING = "待成立";
		public static String TRADE_FAILD = "未成立";
		public static String TRADE_REPAYING = "已成立";
		public static String TRADE_FINISH = "已还款";
		public static String TRADE_REPAY_FAILD = "到期未还款";
		public static String TRADE_PAID = "已收款";
		public static String TRADE_NOT_PAID = "待收款";
		public static String GUARANTEE_AUDITING = "担保公司审核中";
		public static String DOREPAY = "待还款";
		public static String COMPENSATORY_REPAY_FINISH = "违约代偿完成";
	}
	
	public static class RepayConfigureCanstants {
		static String processDays = CommonConfig.REPAY_PROCESSDAYS;
		/**
		 * 还款处理日期
		 */
		public static int PROCESSIONREPAYDAYS = Integer.parseInt(processDays == null ? "1"
			: processDays);
	}
	
//	public static class GuaranteeAuthFilterCanstants {
//		public static String FILTEREDGUARANTEES = "";
//		public static String GUARANTEE_EDU = "重庆市教育担保有限责任公司";
//	}
	
	public static class BankCodes {
		/**
		 * ABC,BOC,COMM,CCB,CEB,CIB,CMBC,CITIC,CQRCB,ICBC,PSBC,SPDB,UNION,CQCB,
		 * GDB,SDB,HXB,CQTGB,PINGANBANK,BANKSH
		 */
		public static String sychBankCodes = "ABC,BOC,COMM,CCB,CEB,CIB,CMBC,CQRCB,ICBC,PSBC,SPDB,UNION,CQCB,GDB,SDB,HXB,CQTGB,PINGANBANK,BANKSH";
		/**
		 * CMB
		 */
		public static String asychBankCodes = "CMB,CITIC";
		/**
		 * 绑卡验证过滤 邮储
		 */
		public static String filteredBankCodes = "PSBC,PINGANBK";
	}
	
}
