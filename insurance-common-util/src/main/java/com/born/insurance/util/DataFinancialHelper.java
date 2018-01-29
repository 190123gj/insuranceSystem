package com.born.insurance.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.alibaba.fastjson.JSONObject;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 分析生成DataFinancial数据
 * 
 * 
 * @author lirz
 * 
 * 2016-4-6 上午9:40:13
 */
public class DataFinancialHelper {
	
	protected static final Logger logger = LoggerFactory.getLogger(DataFinancialHelper.class);
	
	/** 流动资产： */
	public static final String BALANCE_FLOW_CAPITAL = "balance_flow_capital";
	/** 货币资金 */
	public static final String BALANCE_MONETARY_CAPITAL = "balance_monetary_capital";
	/** 其他货币资金 */
	public static final String BALANCE_OTHER_MONETARY_CAPITAL = "balance_other_monetary_capital";
	/** 应收票据 */
	public static final String BALANCE_NOTES_RECEIVABLE = "balance_notes_receivable";
	/** 应收股利 */
	public static final String BALANCE_DIVIDENDS_RECEIVABLE = "balance_dividends_receivable";
	/** 应收利息 */
	public static final String BALANCE_INTEREST_RECEIVABLE = "balance_interest_receivable";
	/** 应收账款 */
	public static final String BALANCE_RECEIVABLES = "balance_receivables";
	/** 其他应收款 */
	public static final String BALANCE_OTHER_RECEIVABLES = "balance_other_receivables";
	/** 预付账款 */
	public static final String BALANCE_ADVANCE_PAYMENT = "balance_advance_payment";
	/** 应收补贴款 */
	public static final String BALANCE_ALLOWANCE_RECEIVABLE = "balance_allowance_receivable";
	/** 存货 */
	public static final String BALANCE_INVENTORY = "balance_inventory";
	/** 待摊费用 */
	public static final String BALANCE_UNAMORTIZED_EXPENSE = "balance_unamortized_expense";
	/** 一年内到期的长期债权投资 */
	public static final String BALANCE_LONGTERM_DEBT_INVESTMENT = "balance_longterm_debt_investment";
	/** 其他流动资产 */
	public static final String BALANCE_OTHER_FLOW_CAPITAL = "balance_other_flow_capital";
	/** 流动资产合计 */
	public static final String BALANCE_TOTAL_FLOW_CAPITAL = "balance_total_flow_capital";
	/** 长期投资： */
	public static final String BALANCE_LONGTERM_INVESTMENT = "balance_longterm_investment";
	/** 长期股权投资 */
	public static final String BALANCE_LONGTERM_STOCK_INVESTMENT = "balance_longterm_stock_investment";
	/** 投资性房地产 */
	public static final String BALANCE_INVESTMENT_HOUSING = "balance_investment_housing";
	/** 长期投资合计 */
	public static final String BALANCE_TOTAL_LONGTERM_INVESTMENT = "balance_total_longterm_investment";
	/** 固定资产： */
	public static final String BALANCE_FIXED_ASSETS = "balance_fixed_assets";
	/** 固定资产原价 */
	public static final String BALANCE_IMMOBILISATIONS = "balance_immobilisations";
	/** 减：累计折旧 */
	public static final String BALANCE_ACCUMULATED_DEPRECIATION = "balance_accumulated_depreciation";
	/** 固定资产净值 */
	public static final String BALANCE_NET_VALUE_OF_FIXED_ASSETS = "balance_net_value_of_fixed_assets";
	/** 减：固定资产减值准备 */
	public static final String BALANCE_FIXED_ASSETS_DEPRECIATION_RESERVES = "balance_fixed_assets_depreciation_reserves";
	/** 固定资产净额 */
	public static final String BALANCE_NET_FIXED_ASSETS = "balance_net_fixed_assets";
	/** 工程物资 */
	public static final String BALANCE_ENGINEERING_MATERIAL = "balance_engineering_material";
	/** 在建工程 */
	public static final String BALANCE_CONSTRUCTION_IN_PROCESS = "balance_construction_in_process";
	/** 固定资产清理 */
	public static final String BALANCE_DISPOSAL_OF_FIXED_ASSETS = "balance_disposal_of_fixed_assets";
	/** 固定资产合计 */
	public static final String BALANCE_TOTAL_FIXED_ASSETS = "balance_total_fixed_assets";
	/** 无形资产及其他资产： */
	public static final String BALANCE_INTANGIBLE_ASSETS_AND_OTHER_ASSETS = "balance_intangible_assets_and_other_assets";
	/** 无形资产 */
	public static final String BALANCE_NTANGIBLE_ASSETS = "balance_ntangible_assets";
	/** 长期待摊费用 */
	public static final String BALANCE_LONGTERM_UNAMORTIZED_EXPENSES = "balance_longterm_unamortized_expenses";
	/** 其他长期资产 */
	public static final String BALANCE_OTHER_LONGTERM_ASSETS = "balance_other_longterm_assets";
	/** 无形资产及其他资产合计 */
	public static final String BALANCE_TOTAL_NTANGIBLE_OTHER_ASSETS = "balance_total_ntangible_other_assets";
	/** 递延税项(借)： */
	public static final String BALANCE_DEFERRED_TAX_BORROW = "balance_deferred_tax_borrow";
	/** 递延税款借项 */
	public static final String BALANCE_DEFERRED_TAXES_DEBIT = "balance_deferred_taxes_debit";
	/** 非流动资产合计 */
	public static final String BALANCE_TOTAL_NON_CURRENT_ASSETS = "balance_total_non_current_assets";
	/** 资产总计 */
	public static final String BALANCE_TOTAL_CAPITAL = "balance_total_capital";
	/** 负债和股东权益 */
	public static final String BALANCE_LIABILITIES_SHAREHOLDERS_EQUITY = "balance_liabilities_shareholders_equity";
	/** 流动负债： */
	public static final String BALANCE_CURRENT_LIABILITIES = "balance_current_liabilities";
	/** 短期借款 */
	public static final String BALANCE_SHORT_TERM_BORROWING = "balance_short_term_borrowing";
	/** 应付票据 */
	public static final String BALANCE_NOTES_PAYABLE = "balance_notes_payable";
	/** 应付账款 */
	public static final String BALANCE_ACCOUNTS_PAYABLE = "balance_accounts_payable";
	/** 预收账款 */
	public static final String BALANCE_DEPOSIT_RECEIVED = "balance_deposit_received";
	/** 应付工资 */
	public static final String BALANCE_WAGES_PAYABLE = "balance_wages_payable";
	/** 应付利息 */
	public static final String BALANCE_INTEREST_PAYABLE = "balance_interest_payable";
	/** 应付股利 */
	public static final String BALANCE_DIVIDENDS_PAYABLE = "balance_dividends_payable";
	/** 应交税金 */
	public static final String BALANCE_TAX_PAYABLE = "balance_tax_payable";
	/** 其他应交款 */
	public static final String BALANCE_OTHER_FEES_PAYABLE = "balance_other_fees_payable";
	/** 其他应付款 */
	public static final String BALANCE_OTHER_AMOUNTS_PAYABLE = "balance_other_amounts_payable";
	/** 预提费用 */
	public static final String BALANCE_ACCRUED_EXPENSES = "balance_accrued_expenses";
	/** 预计负债 */
	public static final String BALANCE_ACCRUED_LIABILITIES = "balance_accrued_liabilities";
	/** 一年内到期的长期负债 */
	public static final String BALANCE_ONE_YEAR_DEBT = "balance_one_year_debt";
	/** 其他流动负债 */
	public static final String BALANCE_OTHER_CURRENT_LIABILITY = "balance_other_current_liability";
	/** 流动负债合计 */
	public static final String BALANCE_TOTAL_CURRENT_LIABILITY = "balance_total_current_liability";
	/** 长期负债： */
	public static final String BALANCE_LONGTERM_DEBT = "balance_longterm_debt";
	/** 长期借款 */
	public static final String BALANCE_LONGTERM_LOAN = "balance_longterm_loan";
	/** 应付债券 */
	public static final String BALANCE_BONDS_PAYABLE = "balance_bonds_payable";
	/** 长期应付款 */
	public static final String BALANCE_LONGTERM_PAYABLE = "balance_longterm_payable";
	/** 专项应付款 */
	public static final String BALANCE_ACCOUNT_PAYABLE_SPECIAL_FUNDS = "balance_account_payable_special_funds";
	/** 其他长期负债 */
	public static final String BALANCE_OTHER_LONGTERM_DEBT = "balance_other_longterm_debt";
	/** 长期负债合计 */
	public static final String BALANCE_TOTAL_LONGTERM_LIABILITIES = "balance_total_longterm_liabilities";
	/** 递延税项(贷)： */
	public static final String BALANCE_DEFERRED_TAX_LOAN = "balance_deferred_tax_loan";
	/** 递延税款贷项 */
	public static final String BALANCE_DEFERRED_TAX_CREDITS = "balance_deferred_tax_credits";
	/** 非流动负债合计 */
	public static final String BALANCE_TOTAL_NONCURRENT_LIABILITIES = "balance_total_noncurrent_liabilities";
	/** 负债合计 */
	public static final String BALANCE_TOTAL_LIABILITY = "balance_total_liability";
	/** 股东权益： */
	public static final String BALANCE_STOCKHOLDER_EQUITY = "balance_stockholder_equity";
	/** 少数股东权益 */
	public static final String BALANCE_MINORITY_EQUITY = "balance_minority_equity";
	/** 实收资本 */
	public static final String BALANCE_PAICLUP_CAPITAL = "balance_paiclup_capital";
	/** 减：已归还投资 */
	public static final String BALANCE_INVESTMENT_RETURNED = "balance_investment_returned";
	/** 实收资本净额 */
	public static final String BALANCE_NET_PAIDIN_CAPITAL = "balance_net_paidin_capital";
	/** 资本公积 */
	public static final String BALANCE_CAPITAL_RESERVE = "balance_capital_reserve";
	/** 盈余公积 */
	public static final String BALANCE_EARNED_SURPLUS = "balance_earned_surplus";
	/** 其中：法定公益金 */
	public static final String BALANCE_LEGAL_PUBLIC_WELFARE_FUND = "balance_legal_public_welfare_fund";
	/** 未分配利润 */
	public static final String BALANCE_UNDISTRIBUTED_PROFIT = "balance_undistributed_profit";
	/** 专项储备 */
	public static final String BALANCE_APPROPRIATIVE_RESERVE = "balance_appropriative_reserve";
	/** 股东权益合计 */
	public static final String BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL = "balance_total_shareholders_equitytotal";
	/** 负债和股东权益总计 */
	public static final String BALANCE_DEBT_SHAREHOLDER_EQUITY = "balance_debt_shareholder_equity";
	/** 一、主营业务收入 */
	public static final String PROFIT_MAIN_BUSINESS_INCOME = "profit_main_business_income";
	/** 减：主营业务成本 */
	public static final String PROFIT_MAIN_BUSINESS_COST = "profit_main_business_cost";
	/** 主营业务税金及附加 */
	public static final String PROFIT_TAX_AND_EXTRA_CHARGES_OF_MAIN_BUSINESS = "profit_tax_and_extra_charges_of_main_business";
	/** 二、主营业务利润 */
	public static final String PROFIT_INCOME_FROM_MAIN_OPERATION = "profit_income_from_main_operation";
	/** 加：其他业务利润 */
	public static final String PROFIT_INCOM_FROM_OTHER_OPERATION = "profit_incom_from_other_operation";
	/** 减：营业费用 */
	public static final String PROFIT_OPERATING_EXPENSES = "profit_operating_expenses";
	/** 管理费用 */
	public static final String PROFIT_ADMINISTRATION_EXPENSE = "profit_administration_expense";
	/** 财务费用 */
	public static final String PROFIT_FINANCIAL_COST = "profit_financial_cost";
	/** 资产减值损失 */
	public static final String PROFIT_ASSETS_IMPAIRMENT_LOSS = "profit_assets_impairment_loss";
	/** 三、营业利润 */
	public static final String PROFIT_OPERATING_PROFIT = "profit_operating_profit";
	/** 加：投资收益 */
	public static final String PROFIT_INCOME_FROM_INVESTMENT = "profit_income_from_investment";
	/** 补贴收入 */
	public static final String PROFIT_SUBSIDIZE_REVENUE = "profit_subsidize_revenue";
	/** 营业外收入 */
	public static final String PROFIT_NONBUSINESS_INCOME = "profit_nonbusiness_income";
	/** 减：营业外支出 */
	public static final String PROFIT_NONBUSINESS_EXPENDITURE = "profit_nonbusiness_expenditure";
	/** 四、利润总额 */
	public static final String PROFIT_TOTAL_PROFIT = "profit_total_profit";
	/** 减：所得税 */
	public static final String PROFIT_INCOME_TAX = "profit_income_tax";
	/** 五、净利润 */
	public static final String PROFIT_RETAINED_PROFITS = "profit_retained_profits";
	/** 加：年初未分配利润 */
	public static final String PROFIT_BEGINNING_RETAINED_EARNINGS = "profit_beginning_retained_earnings";
	/** 其他转入 */
	public static final String PROFIT_OTHER_TRANSFERIN = "profit_other_transferin";
	/** 六、可供分配的利润 */
	public static final String PROFIT_PROFIT_AVAILABLE_FOR_DISTRIBUTION = "profit_profit_available_for_distribution";
	/** 减：提取法定盈余公积 */
	public static final String PROFIT_WITHDRAWAL_LEGAL_SURPLUS = "profit_withdrawal_legal_surplus";
	/** 提取法定公益金 */
	public static final String PROFIT_TO_WITHDRAW_STATUTORY_WELFARE_RESERVE = "profit_to_withdraw_statutory_welfare_reserve";
	/** 提取职工奖励及福利基金 */
	public static final String PROFIT_WITHDRAWAL_STAFF_AND_WORKERS_BONUS_AND_WELFARE_FUND = "profit_withdrawal_staff_and_workers_bonus_and_welfare_fund";
	/** 提取储备基金 */
	public static final String PROFIT_WITHDRAWAL_RESERVE_FUND = "profit_withdrawal_reserve_fund";
	/** 提取企业发展基金 */
	public static final String PROFIT_APPROPRIATION_OF_ENTERPRISE_EXPANSION_FUND = "profit_appropriation_of_enterprise_expansion_fund";
	/** 利润归还投资 */
	public static final String PROFIT_CAPITAL_REDEMPTION = "profit_capital_redemption";
	/** 七、可供投资者分配的利润 */
	public static final String PROFIT_PROFIT_AVAILABLE_FOR_OWNERS_DISTRIBUTION = "profit_profit_available_for_owners_distribution";
	/** 减：应付优先股股利 */
	public static final String PROFIT_PREFERRED_STOCK_DIVIDENDS_PAYABLE = "profit_preferred_stock_dividends_payable";
	/** 提取任意盈余公积 */
	public static final String PROFIT_EXTRACT_FOR_DISCRETIONARY_EARNING_SURPLUS = "profit_extract_for_discretionary_earning_surplus";
	/** 应付普通股股利 */
	public static final String PROFIT_COMMON_STOCK_DIVIDENDS_PAYABLE = "profit_common_stock_dividends_payable";
	/** 转作股本的普通股股利 */
	public static final String PROFIT_DIVIDENDS_TRANSFERRED_TO_CAPITAL = "profit_dividends_transferred_to_capital";
	/** 八、未分配利润 */
	public static final String PROFIT_UNDISTRIBUTED_PROFIT = "profit_undistributed_profit";
	/** 一、经营活动产生的现金流量： */
	public static final String CASHFLOW_BUSINESS_ACTIVITIES = "cashflow_business_activities";
	/** 销售商品、提供劳务收到的现金 */
	public static final String CASHFLOW_SELLING_AND_SERVICE = "cashflow_selling_and_service";
	/** 收到的税费返还 */
	public static final String CASHFLOW_REFUND_OF_TAX_AND_LEVIES = "cashflow_refund_of_tax_and_levies";
	/** 收到的其他与经营活动有关的现金 */
	public static final String CASHFLOW_RECEIVE_OTHER_BUSINESS_ACTIVITIES = "cashflow_receive_other_business_activities";
	/** 现金流入小计(经营) */
	public static final String CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_BUSINESS_ACTIVITIES = "cashflow_subtotal_of_cash_inflow_business_activities";
	/** 购买商品、接受劳务支付的现金 */
	public static final String CASHFLOW_BUYING_ACCEPT_SERVICE = "cashflow_buying_accept_service";
	/** 支付给职工以及为职工支付的现金 */
	public static final String CASHFLOW_PAYMENT = "cashflow_payment";
	/** 支付的各项税费 */
	public static final String CASHFLOW_TAXES = "cashflow_taxes";
	/** 支付的其他与经营活动有关的现金 */
	public static final String CASHFLOW_PAY_OTHER_BUSINESS_ACTIVITIES = "cashflow_pay_other_business_activities";
	/** 现金流出小计(经营) */
	public static final String CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_BUSINESS_ACTIVITIES = "cashflow_subtotal_of_cash_out_flows_business_activities";
	/** 经营活动产生的现金流量净额 */
	public static final String CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES = "cashflow_net_amount_business_activities";
	/** 二、投资活动产生的现金流量： */
	public static final String CASHFLOW_INVESTMENT = "cashflow_investment";
	/** 收回投资所收到的现金 */
	public static final String CASHFLOW_CASH_RECEIVED_FORM_RETURN_OF = "cashflow_cash_received_form_return_of";
	/** 取得投资收益所收到的现金 */
	public static final String CASHFLOW_OBTAIN_INVESTMENT_INCOME_RECEIVED_IN_CASH = "cashflow_obtain_investment_income_received_in_cash";
	/** 处置固定资产、无形资产或其他长期资产收回的现金净额 */
	public static final String CASHFLOW_NET_CASH = "cashflow_net_cash";
	/** 收到的其他与投资活动有关的现金 */
	public static final String CASHFLOW_RECEIVED_OTHER_INVESTMENT_CASH = "cashflow_received_other_investment_cash";
	/** 现金流入小计(投资) */
	public static final String CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_INVESTMENT = "cashflow_subtotal_of_cash_inflow_investment";
	/** 购建固定资产、无形资产和其他长期资产支付的现金 */
	public static final String CASHFLOW_OTHER_PAYMENT_CASH = "cashflow_other_payment_cash";
	/** 投资所支付的现金 */
	public static final String CASHFLOW_INVESTMENT_PAYMENT_CASH = "cashflow_investment_payment_cash";
	/** 支付的其他与投资活动有关的现金 */
	public static final String CASHFLOW_OTHER_INVESTMENT_CASH = "cashflow_other_investment_cash";
	/** 现金流出小计(投资) */
	public static final String CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_INVESTMENT = "cashflow_subtotal_of_cash_out_flows_investment";
	/** 投资活动产生的现金流量净额 */
	public static final String CASHFLOW_NET_AMOUNT_INVESTMENT = "cashflow_net_amount_investment";
	/** 三、筹资活动产生的现金流量： */
	public static final String CASHFLOW_FINANCING = "cashflow_financing";
	/** 吸收投资所收到的现金 */
	public static final String CASHFLOW_CASH_RECEIVED_BY_INVESTORS = "cashflow_cash_received_by_investors";
	/** 借款收到的现金 */
	public static final String CASHFLOW_CASH_RECEIVED_FROM_BORROWINGS = "cashflow_cash_received_from_borrowings";
	/** 收到的其他与筹资活动有关的现金 */
	public static final String CASHFLOW_OTHER_FINANCING_CASH = "cashflow_other_financing_cash";
	/** 现金流入小计(筹资) */
	public static final String CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_FINANCING = "cashflow_subtotal_of_cash_inflow_financing";
	/** 偿还债务所支付的现金 */
	public static final String CASHFLOW_REPAYMENTS_OF_BORROWINGS = "cashflow_repayments_of_borrowings";
	/** 分配股利、利润或偿付利息所支付的现金 */
	public static final String CASHFLOW_SHARE_INTEREST = "cashflow_share_interest";
	/** 支付的其他与筹资活动有关的现金 */
	public static final String CASHFLOW_PAYMENT_OTHER_FINANCING = "cashflow_payment_other_financing";
	/** 现金流出小计(筹资) */
	public static final String CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_FINANCING = "cashflow_subtotal_of_cash_out_flows_financing";
	/** 筹资活动产生的现金流量净额 */
	public static final String CASHFLOW_NET_AMOUNT_FINANCING = "cashflow_net_amount_financing";
	/** 四、汇率变动对现金的影响 */
	public static final String CASHFLOW_THE_INFLUENCE_OF_EXCHANGE_RATE_CHANGES_FOR_CASH = "cashflow_the_influence_of_exchange_rate_changes_for_cash";
	/** 五、现金流量净额 */
	public static final String CASHFLOW_THE_NET_CASH_FLOW = "cashflow_the_net_cash_flow";
	/** 利息保障倍数 */
	public static final String TIMES_INTEREST_EARNED = "times_interest_earned";
	/** 资产负债率 */
	public static final String ASSETLIABILITY_RATIO = "assetliability_ratio";
	/** 或有负债=或有负债额/净资产 */
	public static final String CONTINGENT_LIABILITY = "contingent_liability";
	/** 流动比率 */
	public static final String IQUIDITY_RATIO = "iquidity_ratio";
	/** 速动比率 */
	public static final String QUICK_RATIO = "quick_ratio";
	/** 现金流量比率 */
	public static final String CASH_FLOW_RATIO = "cash_flow_ratio";
	/** 现金流量利息保障倍数 */
	public static final String THE_MULTIPLE_OF_INTEREST_SAFEGUARD_CASH_FLOW = "the_multiple_of_interest_safeguard_cash_flow";
	/** 存货周转天数 */
	public static final String DAYS_SALES_OF_INVENTORY = "days_sales_of_inventory";
	/** 应收账款周转天数 */
	public static final String DAYS_SALES_OUTSTANDING = "days_sales_outstanding";
	/** 应付账款周转天数 */
	public static final String DAYS_PAYABLE_OUTSTANDING = "days_payable_outstanding";
	/** 预收账款周转天数 */
	public static final String DEFERRED_REVENUE_TURNOVER_DAYS = "deferred_revenue_turnover_days";
	/** 营业收入（主营业务）增长率（%） */
	public static final String INCREASE_RATE_OF_BUSINESS_REVENUE = "increase_rate_of_business_revenue";
	/** 营业（主营业务）毛利润率（%） */
	public static final String BUSINESS_GROSS_MARGIN = "business_gross_margin";
	/** 主营业务净利润率（%） */
	public static final String MAIN_BUSINESS_PROFITABILITY = "main_business_profitability";
	/** 总资产报酬率（%） */
	public static final String RATE_OF_RETURN_ON_TOTAL_ASSETS = "rate_of_return_on_total_assets";
	/** 净资产收益率（ROE）（%） */
	public static final String RETURN_ON_EQUITY = "return_on_equity";
	/** 经营性现金流入 */
	public static final String OPERATING_CASH_INFLOWS = "operating_cash_inflows";
	/** 经营性现金流出 */
	public static final String OPERATING_CASH_OUTFLOWS = "operating_cash_outflows";
	/** 经营性净现金流 */
	public static final String OPERATING_CASH_FLOWS = "operating_cash_flows";
	/** 投资性现金流入 */
	public static final String THE_INVESTMENT_CASH_INFLOWS = "the_investment_cash_inflows";
	/** 投资性现金流出 */
	public static final String THE_INVESTMENT_CASH_OUTFLOWS = "the_investment_cash_outflows";
	/** 投资性净现金流 */
	public static final String THE_INVESTMENT_CASH_FLOWS = "the_investment_cash_flows";
	/** 筹资性现金流入 */
	public static final String RAISE_CASH_INFLOWS = "raise_cash_inflows";
	/** 筹资性现金流出 */
	public static final String RAISE_CASH_OUTFLOWS = "raise_cash_outflows";
	/** 筹资性净现金流 */
	public static final String RAISE_CASH_FLOWS = "raise_cash_flows";
	/** 净现金流 */
	public static final String NET_CASH_FLOW = "net_cash_flow";
	
	/** 交易性金融资产 */
	public static final String BALANCE_TRADING_FINANCIAL_ASSETS = "balance_trading_financial_assets";
	/** 一年内到期的非流动资产 */
	public static final String BALANCE_YEAR_LIMITED_FIXED_ASSETS = "balance_year_limited_fixed_assets";
	/** 减：贷款损失准备 */
	public static final String BALANCE_LOAN_LOSS_RESERVES = "balance_loan_loss_reserves";
	/** 非流动资产： */
	public static final String BALANCE_NON_CURRENT_ASSETS = "balance_non_current_assets";
	/** 可供出售金融资产 */
	public static final String BALANCE_AVAILABLE_FOR_SALE_FINANCIAL_ASSETS = "balance_available_for_sale_financial_assets";
	/** 持有至到期投资 */
	public static final String BALANCE_HELD_TO_MATURITY_INVESTMENT = "balance_held_to_maturity_investment";
	/** 长期应收款 */
	public static final String BALANCE_LONGTERM_RECEIVABLES = "balance_longterm_receivables";
	/** 生产性生物资产 */
	public static final String BALANCE_PRODUCTIVE_BIOLOGICAL_ASSET = "balance_productive_biological_asset";
	/** 油气资产 */
	public static final String BALANCE_OIL_AND_GAS_ASSETS = "balance_oil_and_gas_assets";
	/** 开发支出 */
	public static final String BALANCE_DEVELOPMENT_EXPENDITURE = "balance_development_expenditure";
	/** 商誉 */
	public static final String BALANCE_BUSINESS_REPUTATION = "balance_business_reputation";
	/** 递延所得税资产 */
	public static final String BALANCE_DEFERRED_INCOME_TAX_ASSETS = "balance_deferred_income_tax_assets";
	/** 其他非流动资产 */
	public static final String BALANCE_OTHER_NON_CURRENT_ASSETS = "balance_other_non_current_assets";
	/** 交易性金融负债 */
	public static final String BALANCE_TRADING_FINANCIAL_LIABILITIES = "balance_trading_financial_liabilities";
	/** 一年内到期的非流动负债 */
	public static final String BALANCE_YEAR_LIMITED_FIXED_DEBT = "balance_year_limited_fixed_debt";
	/** 非流动负债： */
	public static final String BALANCE_NONCURRENT_LIABILITY = "balance_noncurrent_liability";
	/** 递延所得税负债 */
	public static final String BALANCE_DEFERRED_INCOME_TAX_LIABILITIES = "balance_deferred_income_tax_liabilities";
	/** 其他非流动负债 */
	public static final String BALANCE_OTHER_NONCURRENT_LIABILITIES = "balance_other_noncurrent_liabilities";
	/** 其中：特准储备基金 */
	public static final String BALANCE_SPECIAL_PERMISSION_RESERVE_FUND = "balance_special_permission_reserve_fund";
	/** 减：库存股 */
	public static final String BALANCE_TREASURY_STOCK = "balance_treasury_stock";
	/** 其中：法定公积金 */
	public static final String BALANCE_LEGAL_ACCUMULATION_FUND = "balance_legal_accumulation_fund";
	/** 任意公积金 */
	public static final String BALANCE_OPTIONAL_ACCUMULATION_FUND = "balance_optional_accumulation_fund";
	/** △一般风险准备 */
	public static final String BALANCE_GENERIC_RISK_RESERVE = "balance_generic_risk_reserve";
	/** 外币报表折算差额 */
	public static final String BALANCE_TRANSLATION_RESERVE = "balance_translation_reserve";
	/** 归属于母公司所有者权益合计 */
	public static final String BALANCE_HEADQUARTERS_STOCKHOLDER_EQUITY = "balance_headquarters_stockholder_equity";
	/** 加：公允价值变动收益（损失以“-”号填列） */
	public static final String PROFIT_GAINS_ON_THE_CHANGES_IN_THE_FAIR_VALUE = "profit_gains_on_the_changes_in_the_fair_value";
	/** 其中：对联营企业和合营企业的投资收益 */
	public static final String PROFIT_ENTERPRISE_INCOME_FROM_INVESTMENT = "profit_enterprise_income_from_investment";
	/** 收取贷款利息收到的现金 */
	public static final String CASHFLOW_CASH_FROM_LOAN_INTEREST = "cashflow_cash_from_loan_interest";
	
	public static final Map<String, String> NAMES_KEY = new LinkedHashMap<>();
	static {
		NAMES_KEY.put("预付款项", BALANCE_ADVANCE_PAYMENT);
		NAMES_KEY.put("预收款项", BALANCE_DEPOSIT_RECEIVED);
		NAMES_KEY.put("应付职工薪酬", BALANCE_WAGES_PAYABLE);
		NAMES_KEY.put("应交税费", BALANCE_TAX_PAYABLE);
		NAMES_KEY.put("所有者权益（或股东权益）：", BALANCE_STOCKHOLDER_EQUITY);
		NAMES_KEY.put("实收资本（股本）", BALANCE_PAICLUP_CAPITAL);
		NAMES_KEY.put("*少数股东权益", BALANCE_MINORITY_EQUITY);
		NAMES_KEY.put("所有者权益合计", BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL);
		NAMES_KEY.put("负债和所有者权益总计", BALANCE_DEBT_SHAREHOLDER_EQUITY);
		NAMES_KEY.put("减：主营业务成本（融资成本）", PROFIT_MAIN_BUSINESS_COST);
		NAMES_KEY.put("投资收益（损失以“-”号填列）", PROFIT_INCOME_FROM_INVESTMENT);
		NAMES_KEY.put("交易性金融资产", BALANCE_TRADING_FINANCIAL_ASSETS);
		NAMES_KEY.put("一年内到期的非流动资产", BALANCE_YEAR_LIMITED_FIXED_ASSETS);
		NAMES_KEY.put("减：贷款损失准备", BALANCE_LOAN_LOSS_RESERVES);
		NAMES_KEY.put("非流动资产：", BALANCE_NON_CURRENT_ASSETS);
		NAMES_KEY.put("可供出售金融资产", BALANCE_AVAILABLE_FOR_SALE_FINANCIAL_ASSETS);
		NAMES_KEY.put("持有至到期投资", BALANCE_HELD_TO_MATURITY_INVESTMENT);
		NAMES_KEY.put("长期应收款", BALANCE_LONGTERM_RECEIVABLES);
		NAMES_KEY.put("生产性生物资产", BALANCE_PRODUCTIVE_BIOLOGICAL_ASSET);
		NAMES_KEY.put("油气资产", BALANCE_OIL_AND_GAS_ASSETS);
		NAMES_KEY.put("开发支出", BALANCE_DEVELOPMENT_EXPENDITURE);
		NAMES_KEY.put("商誉", BALANCE_BUSINESS_REPUTATION);
		NAMES_KEY.put("递延所得税资产", BALANCE_DEFERRED_INCOME_TAX_ASSETS);
		NAMES_KEY.put("其他非流动资产", BALANCE_OTHER_NON_CURRENT_ASSETS);
		NAMES_KEY.put("交易性金融负债", BALANCE_TRADING_FINANCIAL_LIABILITIES);
		NAMES_KEY.put("一年内到期的非流动负债", BALANCE_YEAR_LIMITED_FIXED_DEBT);
		NAMES_KEY.put("非流动负债：", BALANCE_NONCURRENT_LIABILITY);
		NAMES_KEY.put("递延所得税负债", BALANCE_DEFERRED_INCOME_TAX_LIABILITIES);
		NAMES_KEY.put("其他非流动负债", BALANCE_OTHER_NONCURRENT_LIABILITIES);
		NAMES_KEY.put("其中：特准储备基金", BALANCE_SPECIAL_PERMISSION_RESERVE_FUND);
		NAMES_KEY.put("减：库存股", BALANCE_TREASURY_STOCK);
		NAMES_KEY.put("其中：法定公积金", BALANCE_LEGAL_ACCUMULATION_FUND);
		NAMES_KEY.put("任意公积金", BALANCE_OPTIONAL_ACCUMULATION_FUND);
		NAMES_KEY.put("△一般风险准备", BALANCE_GENERIC_RISK_RESERVE);
		NAMES_KEY.put("外币报表折算差额", BALANCE_TRANSLATION_RESERVE);
		NAMES_KEY.put("归属于母公司所有者权益合计", BALANCE_HEADQUARTERS_STOCKHOLDER_EQUITY);
		NAMES_KEY.put("加：公允价值变动收益（损失以“-”号填列）", PROFIT_GAINS_ON_THE_CHANGES_IN_THE_FAIR_VALUE);
		NAMES_KEY.put("其中：对联营企业和合营企业的投资收益", PROFIT_ENTERPRISE_INCOME_FROM_INVESTMENT);
		NAMES_KEY.put("收取贷款利息收到的现金", CASHFLOW_CASH_FROM_LOAN_INTEREST);
		
		NAMES_KEY.put("流动资产：", BALANCE_FLOW_CAPITAL);
		NAMES_KEY.put("货币资金", BALANCE_MONETARY_CAPITAL);
		NAMES_KEY.put("其他货币资金", BALANCE_OTHER_MONETARY_CAPITAL);
		NAMES_KEY.put("应收票据", BALANCE_NOTES_RECEIVABLE);
		NAMES_KEY.put("应收股利", BALANCE_DIVIDENDS_RECEIVABLE);
		NAMES_KEY.put("应收利息", BALANCE_INTEREST_RECEIVABLE);
		NAMES_KEY.put("应收账款", BALANCE_RECEIVABLES);
		NAMES_KEY.put("其他应收款", BALANCE_OTHER_RECEIVABLES);
		NAMES_KEY.put("预付账款", BALANCE_ADVANCE_PAYMENT);
		NAMES_KEY.put("应收补贴款", BALANCE_ALLOWANCE_RECEIVABLE);
		NAMES_KEY.put("存货", BALANCE_INVENTORY);
		NAMES_KEY.put("待摊费用", BALANCE_UNAMORTIZED_EXPENSE);
		NAMES_KEY.put("一年内到期的长期债权投资", BALANCE_LONGTERM_DEBT_INVESTMENT);
		NAMES_KEY.put("其他流动资产", BALANCE_OTHER_FLOW_CAPITAL);
		NAMES_KEY.put("流动资产合计", BALANCE_TOTAL_FLOW_CAPITAL);
		NAMES_KEY.put("长期投资：", BALANCE_LONGTERM_INVESTMENT);
		NAMES_KEY.put("长期股权投资", BALANCE_LONGTERM_STOCK_INVESTMENT);
		NAMES_KEY.put("投资性房地产", BALANCE_INVESTMENT_HOUSING);
		NAMES_KEY.put("长期投资合计", BALANCE_TOTAL_LONGTERM_INVESTMENT);
		NAMES_KEY.put("固定资产：", BALANCE_FIXED_ASSETS);
		NAMES_KEY.put("固定资产原价", BALANCE_IMMOBILISATIONS);
		NAMES_KEY.put("减：累计折旧", BALANCE_ACCUMULATED_DEPRECIATION);
		NAMES_KEY.put("固定资产净值", BALANCE_NET_VALUE_OF_FIXED_ASSETS);
		NAMES_KEY.put("减：固定资产减值准备", BALANCE_FIXED_ASSETS_DEPRECIATION_RESERVES);
		NAMES_KEY.put("固定资产净额", BALANCE_NET_FIXED_ASSETS);
		NAMES_KEY.put("工程物资", BALANCE_ENGINEERING_MATERIAL);
		NAMES_KEY.put("在建工程", BALANCE_CONSTRUCTION_IN_PROCESS);
		NAMES_KEY.put("固定资产清理", BALANCE_DISPOSAL_OF_FIXED_ASSETS);
		NAMES_KEY.put("固定资产合计", BALANCE_TOTAL_FIXED_ASSETS);
		NAMES_KEY.put("无形资产及其他资产：", BALANCE_INTANGIBLE_ASSETS_AND_OTHER_ASSETS);
		NAMES_KEY.put("无形资产", BALANCE_NTANGIBLE_ASSETS);
		NAMES_KEY.put("长期待摊费用", BALANCE_LONGTERM_UNAMORTIZED_EXPENSES);
		NAMES_KEY.put("其他长期资产", BALANCE_OTHER_LONGTERM_ASSETS);
		NAMES_KEY.put("无形资产及其他资产合计", BALANCE_TOTAL_NTANGIBLE_OTHER_ASSETS);
		NAMES_KEY.put("递延税项(借)：", BALANCE_DEFERRED_TAX_BORROW);
		NAMES_KEY.put("递延税款借项", BALANCE_DEFERRED_TAXES_DEBIT);
		NAMES_KEY.put("非流动资产合计", BALANCE_TOTAL_NON_CURRENT_ASSETS);
		NAMES_KEY.put("资产总计", BALANCE_TOTAL_CAPITAL);
		NAMES_KEY.put("负债和股东权益", BALANCE_LIABILITIES_SHAREHOLDERS_EQUITY);
		NAMES_KEY.put("流动负债：", BALANCE_CURRENT_LIABILITIES);
		NAMES_KEY.put("短期借款", BALANCE_SHORT_TERM_BORROWING);
		NAMES_KEY.put("应付票据", BALANCE_NOTES_PAYABLE);
		NAMES_KEY.put("应付账款", BALANCE_ACCOUNTS_PAYABLE);
		NAMES_KEY.put("预收账款", BALANCE_DEPOSIT_RECEIVED);
		NAMES_KEY.put("应付工资", BALANCE_WAGES_PAYABLE);
		NAMES_KEY.put("应付利息", BALANCE_INTEREST_PAYABLE);
		NAMES_KEY.put("应付股利", BALANCE_DIVIDENDS_PAYABLE);
		NAMES_KEY.put("应交税金", BALANCE_TAX_PAYABLE);
		NAMES_KEY.put("其他应交款", BALANCE_OTHER_FEES_PAYABLE);
		NAMES_KEY.put("其他应付款", BALANCE_OTHER_AMOUNTS_PAYABLE);
		NAMES_KEY.put("预提费用", BALANCE_ACCRUED_EXPENSES);
		NAMES_KEY.put("预计负债", BALANCE_ACCRUED_LIABILITIES);
		NAMES_KEY.put("一年内到期的长期负债", BALANCE_ONE_YEAR_DEBT);
		NAMES_KEY.put("其他流动负债", BALANCE_OTHER_CURRENT_LIABILITY);
		NAMES_KEY.put("流动负债合计", BALANCE_TOTAL_CURRENT_LIABILITY);
		NAMES_KEY.put("长期负债：", BALANCE_LONGTERM_DEBT);
		NAMES_KEY.put("长期借款", BALANCE_LONGTERM_LOAN);
		NAMES_KEY.put("应付债券", BALANCE_BONDS_PAYABLE);
		NAMES_KEY.put("长期应付款", BALANCE_LONGTERM_PAYABLE);
		NAMES_KEY.put("专项应付款", BALANCE_ACCOUNT_PAYABLE_SPECIAL_FUNDS);
		NAMES_KEY.put("其他长期负债", BALANCE_OTHER_LONGTERM_DEBT);
		NAMES_KEY.put("长期负债合计", BALANCE_TOTAL_LONGTERM_LIABILITIES);
		NAMES_KEY.put("递延税项(贷)：", BALANCE_DEFERRED_TAX_LOAN);
		NAMES_KEY.put("递延税款贷项", BALANCE_DEFERRED_TAX_CREDITS);
		NAMES_KEY.put("非流动负债合计", BALANCE_TOTAL_NONCURRENT_LIABILITIES);
		NAMES_KEY.put("负债合计", BALANCE_TOTAL_LIABILITY);
		NAMES_KEY.put("股东权益：", BALANCE_STOCKHOLDER_EQUITY);
		NAMES_KEY.put("少数股东权益", BALANCE_MINORITY_EQUITY);
		NAMES_KEY.put("实收资本", BALANCE_PAICLUP_CAPITAL);
		NAMES_KEY.put("减：已归还投资", BALANCE_INVESTMENT_RETURNED);
		NAMES_KEY.put("实收资本净额", BALANCE_NET_PAIDIN_CAPITAL);
		NAMES_KEY.put("资本公积", BALANCE_CAPITAL_RESERVE);
		NAMES_KEY.put("盈余公积", BALANCE_EARNED_SURPLUS);
		NAMES_KEY.put("其中：法定公益金", BALANCE_LEGAL_PUBLIC_WELFARE_FUND);
		NAMES_KEY.put("未分配利润", BALANCE_UNDISTRIBUTED_PROFIT);
		NAMES_KEY.put("专项储备", BALANCE_APPROPRIATIVE_RESERVE);
		NAMES_KEY.put("股东权益合计", BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL);
		NAMES_KEY.put("负债和股东权益总计", BALANCE_DEBT_SHAREHOLDER_EQUITY);
		NAMES_KEY.put("一、主营业务收入", PROFIT_MAIN_BUSINESS_INCOME);
		NAMES_KEY.put("减：主营业务成本", PROFIT_MAIN_BUSINESS_COST);
		NAMES_KEY.put("主营业务税金及附加", PROFIT_TAX_AND_EXTRA_CHARGES_OF_MAIN_BUSINESS);
		NAMES_KEY.put("二、主营业务利润", PROFIT_INCOME_FROM_MAIN_OPERATION);
		NAMES_KEY.put("加：其他业务利润", PROFIT_INCOM_FROM_OTHER_OPERATION);
		NAMES_KEY.put("减：营业费用", PROFIT_OPERATING_EXPENSES);
		NAMES_KEY.put("管理费用", PROFIT_ADMINISTRATION_EXPENSE);
		NAMES_KEY.put("财务费用", PROFIT_FINANCIAL_COST);
		NAMES_KEY.put("资产减值损失", PROFIT_ASSETS_IMPAIRMENT_LOSS);
		NAMES_KEY.put("三、营业利润", PROFIT_OPERATING_PROFIT);
		NAMES_KEY.put("加：投资收益", PROFIT_INCOME_FROM_INVESTMENT);
		NAMES_KEY.put("补贴收入", PROFIT_SUBSIDIZE_REVENUE);
		NAMES_KEY.put("营业外收入", PROFIT_NONBUSINESS_INCOME);
		NAMES_KEY.put("减：营业外支出", PROFIT_NONBUSINESS_EXPENDITURE);
		NAMES_KEY.put("四、利润总额", PROFIT_TOTAL_PROFIT);
		NAMES_KEY.put("减：所得税", PROFIT_INCOME_TAX);
		NAMES_KEY.put("五、净利润", PROFIT_RETAINED_PROFITS);
		NAMES_KEY.put("加：年初未分配利润", PROFIT_BEGINNING_RETAINED_EARNINGS);
		NAMES_KEY.put("其他转入", PROFIT_OTHER_TRANSFERIN);
		NAMES_KEY.put("六、可供分配的利润", PROFIT_PROFIT_AVAILABLE_FOR_DISTRIBUTION);
		NAMES_KEY.put("减：提取法定盈余公积", PROFIT_WITHDRAWAL_LEGAL_SURPLUS);
		NAMES_KEY.put("提取法定公益金", PROFIT_TO_WITHDRAW_STATUTORY_WELFARE_RESERVE);
		NAMES_KEY.put("提取职工奖励及福利基金", PROFIT_WITHDRAWAL_STAFF_AND_WORKERS_BONUS_AND_WELFARE_FUND);
		NAMES_KEY.put("提取储备基金", PROFIT_WITHDRAWAL_RESERVE_FUND);
		NAMES_KEY.put("提取企业发展基金", PROFIT_APPROPRIATION_OF_ENTERPRISE_EXPANSION_FUND);
		NAMES_KEY.put("利润归还投资", PROFIT_CAPITAL_REDEMPTION);
		NAMES_KEY.put("七、可供投资者分配的利润", PROFIT_PROFIT_AVAILABLE_FOR_OWNERS_DISTRIBUTION);
		NAMES_KEY.put("减：应付优先股股利", PROFIT_PREFERRED_STOCK_DIVIDENDS_PAYABLE);
		NAMES_KEY.put("提取任意盈余公积", PROFIT_EXTRACT_FOR_DISCRETIONARY_EARNING_SURPLUS);
		NAMES_KEY.put("应付普通股股利", PROFIT_COMMON_STOCK_DIVIDENDS_PAYABLE);
		NAMES_KEY.put("转作股本的普通股股利", PROFIT_DIVIDENDS_TRANSFERRED_TO_CAPITAL);
		NAMES_KEY.put("八、未分配利润", PROFIT_UNDISTRIBUTED_PROFIT);
		NAMES_KEY.put("一、经营活动产生的现金流量：", CASHFLOW_BUSINESS_ACTIVITIES);
		NAMES_KEY.put("销售商品、提供劳务收到的现金", CASHFLOW_SELLING_AND_SERVICE);
		NAMES_KEY.put("收到的税费返还", CASHFLOW_REFUND_OF_TAX_AND_LEVIES);
		NAMES_KEY.put("收到的其他与经营活动有关的现金", CASHFLOW_RECEIVE_OTHER_BUSINESS_ACTIVITIES);
		NAMES_KEY.put("现金流入小计(经营)", CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_BUSINESS_ACTIVITIES);
		NAMES_KEY.put("购买商品、接受劳务支付的现金", CASHFLOW_BUYING_ACCEPT_SERVICE);
		NAMES_KEY.put("支付给职工以及为职工支付的现金", CASHFLOW_PAYMENT);
		NAMES_KEY.put("支付的各项税费", CASHFLOW_TAXES);
		NAMES_KEY.put("支付的其他与经营活动有关的现金", CASHFLOW_PAY_OTHER_BUSINESS_ACTIVITIES);
		NAMES_KEY.put("现金流出小计(经营)", CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_BUSINESS_ACTIVITIES);
		NAMES_KEY.put("经营活动产生的现金流量净额", CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES);
		NAMES_KEY.put("二、投资活动产生的现金流量：", CASHFLOW_INVESTMENT);
		NAMES_KEY.put("收回投资所收到的现金", CASHFLOW_CASH_RECEIVED_FORM_RETURN_OF);
		NAMES_KEY.put("取得投资收益所收到的现金", CASHFLOW_OBTAIN_INVESTMENT_INCOME_RECEIVED_IN_CASH);
		NAMES_KEY.put("处置固定资产、无形资产或其他长期资产收回的现金净额", CASHFLOW_NET_CASH);
		NAMES_KEY.put("收到的其他与投资活动有关的现金", CASHFLOW_RECEIVED_OTHER_INVESTMENT_CASH);
		NAMES_KEY.put("现金流入小计(投资)", CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_INVESTMENT);
		NAMES_KEY.put("购建固定资产、无形资产和其他长期资产支付的现金", CASHFLOW_OTHER_PAYMENT_CASH);
		NAMES_KEY.put("投资所支付的现金", CASHFLOW_INVESTMENT_PAYMENT_CASH);
		NAMES_KEY.put("支付的其他与投资活动有关的现金", CASHFLOW_OTHER_INVESTMENT_CASH);
		NAMES_KEY.put("现金流出小计(投资)", CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_INVESTMENT);
		NAMES_KEY.put("投资活动产生的现金流量净额", CASHFLOW_NET_AMOUNT_INVESTMENT);
		NAMES_KEY.put("三、筹资活动产生的现金流量：", CASHFLOW_FINANCING);
		NAMES_KEY.put("吸收投资所收到的现金", CASHFLOW_CASH_RECEIVED_BY_INVESTORS);
		NAMES_KEY.put("借款收到的现金", CASHFLOW_CASH_RECEIVED_FROM_BORROWINGS);
		NAMES_KEY.put("收到的其他与筹资活动有关的现金", CASHFLOW_OTHER_FINANCING_CASH);
		NAMES_KEY.put("现金流入小计(筹资)", CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_FINANCING);
		NAMES_KEY.put("偿还债务所支付的现金", CASHFLOW_REPAYMENTS_OF_BORROWINGS);
		NAMES_KEY.put("分配股利、利润或偿付利息所支付的现金", CASHFLOW_SHARE_INTEREST);
		NAMES_KEY.put("支付的其他与筹资活动有关的现金", CASHFLOW_PAYMENT_OTHER_FINANCING);
		NAMES_KEY.put("现金流出小计(筹资)", CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_FINANCING);
		NAMES_KEY.put("筹资活动产生的现金流量净额", CASHFLOW_NET_AMOUNT_FINANCING);
		NAMES_KEY.put("四、汇率变动对现金的影响", CASHFLOW_THE_INFLUENCE_OF_EXCHANGE_RATE_CHANGES_FOR_CASH);
		NAMES_KEY.put("五、现金流量净额", CASHFLOW_THE_NET_CASH_FLOW);
		NAMES_KEY.put("利息保障倍数", TIMES_INTEREST_EARNED);
		NAMES_KEY.put("资产负债率", ASSETLIABILITY_RATIO);
		NAMES_KEY.put("或有负债=或有负债额/净资产", CONTINGENT_LIABILITY);
		NAMES_KEY.put("流动比率", IQUIDITY_RATIO);
		NAMES_KEY.put("速动比率", QUICK_RATIO);
		NAMES_KEY.put("现金流量比率", CASH_FLOW_RATIO);
		NAMES_KEY.put("现金流量利息保障倍数", THE_MULTIPLE_OF_INTEREST_SAFEGUARD_CASH_FLOW);
		NAMES_KEY.put("存货周转天数", DAYS_SALES_OF_INVENTORY);
		NAMES_KEY.put("应收账款周转天数", DAYS_SALES_OUTSTANDING);
		NAMES_KEY.put("应付账款周转天数", DAYS_PAYABLE_OUTSTANDING);
		NAMES_KEY.put("预收账款周转天数", DEFERRED_REVENUE_TURNOVER_DAYS);
		NAMES_KEY.put("营业收入（主营业务）增长率（%）", INCREASE_RATE_OF_BUSINESS_REVENUE);
		NAMES_KEY.put("营业（主营业务）毛利润率（%）", BUSINESS_GROSS_MARGIN);
		NAMES_KEY.put("主营业务净利润率（%）", MAIN_BUSINESS_PROFITABILITY);
		NAMES_KEY.put("总资产报酬率（%）", RATE_OF_RETURN_ON_TOTAL_ASSETS);
		NAMES_KEY.put("净资产收益率（ROE）（%）", RETURN_ON_EQUITY);
		NAMES_KEY.put("经营性现金流入", OPERATING_CASH_INFLOWS);
		NAMES_KEY.put("经营性现金流出", OPERATING_CASH_OUTFLOWS);
		NAMES_KEY.put("经营性净现金流", OPERATING_CASH_FLOWS);
		NAMES_KEY.put("投资性现金流入", THE_INVESTMENT_CASH_INFLOWS);
		NAMES_KEY.put("投资性现金流出", THE_INVESTMENT_CASH_OUTFLOWS);
		NAMES_KEY.put("投资性净现金流", THE_INVESTMENT_CASH_FLOWS);
		NAMES_KEY.put("筹资性现金流入", RAISE_CASH_INFLOWS);
		NAMES_KEY.put("筹资性现金流出", RAISE_CASH_OUTFLOWS);
		NAMES_KEY.put("筹资性净现金流", RAISE_CASH_FLOWS);
		NAMES_KEY.put("净现金流", NET_CASH_FLOW);
	}
	
	private static final Map<String, String> CLASS_MAP = new HashMap<>();
	static {
		CLASS_MAP.put(BALANCE_TOTAL_FLOW_CAPITAL, "bac1");
		CLASS_MAP.put(BALANCE_TOTAL_NON_CURRENT_ASSETS, "bac1");
		CLASS_MAP.put(BALANCE_TOTAL_CAPITAL, "bac2");
		CLASS_MAP.put(BALANCE_TOTAL_CURRENT_LIABILITY, "bac1");
		CLASS_MAP.put(BALANCE_TOTAL_NONCURRENT_LIABILITIES, "bac1");
		CLASS_MAP.put(BALANCE_TOTAL_LIABILITY, "bac1");
		CLASS_MAP.put(BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL, "bac2");
		CLASS_MAP.put(BALANCE_DEBT_SHAREHOLDER_EQUITY, "bac3");
		
		CLASS_MAP.put(PROFIT_INCOME_FROM_MAIN_OPERATION, "bac1");
		CLASS_MAP.put(PROFIT_RETAINED_PROFITS, "bac1");
		
		CLASS_MAP.put(CASHFLOW_BUSINESS_ACTIVITIES, "bac1");
		CLASS_MAP.put(CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_BUSINESS_ACTIVITIES, "bac4");
		CLASS_MAP.put(CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_BUSINESS_ACTIVITIES, "bac2");
		CLASS_MAP.put(CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES, "bac5");
		CLASS_MAP.put(CASHFLOW_INVESTMENT, "bac1");
		CLASS_MAP.put(CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_INVESTMENT, "bac4");
		CLASS_MAP.put(CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_INVESTMENT, "bac2");
		CLASS_MAP.put(CASHFLOW_NET_AMOUNT_INVESTMENT, "bac5");
		CLASS_MAP.put(CASHFLOW_FINANCING, "bac1");
		CLASS_MAP.put(CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_FINANCING, "bac4");
		CLASS_MAP.put(CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_FINANCING, "bac2");
		CLASS_MAP.put(CASHFLOW_NET_AMOUNT_FINANCING, "bac5");
		CLASS_MAP.put(CASHFLOW_THE_INFLUENCE_OF_EXCHANGE_RATE_CHANGES_FOR_CASH, "bac1");
		CLASS_MAP.put(CASHFLOW_THE_NET_CASH_FLOW, "bac3");
	}
	
	public static DataSheet installDataSheet(Sheet sheet, int rowStart) {
		if (null == sheet) {
			logger.error("财务报表解析失败：没有表单数据");
			return null;
		}
		
		DataSheet dataSheet = new DataSheet();
		dataSheet.setTitle(sheet.getName());
		int rows = sheet.getRows();//得到行数
		int columns = sheet.getColumns();//得到列数
		logger.info(sheet.getName() + "：共有" + columns + "列，" + rows + "行");
		
		int len = columns - 1;
		dataSheet.setRow(rows);
		dataSheet.setColumn(columns);
		
		if (rows < rowStart) {
			logger.error("财务报表解析失败：没有数据");
			return dataSheet;
		}
		
		if (columns > 0 && rows > 0) {
			List<DataRow> dataRows = new ArrayList<>(rows - rowStart + 1);
			DataRow header = new DataRow();
			String[] datas = new String[len];
			for (int column = 1; column < columns; column++) {
				datas[column - 1] = sheet.getCell(column, rowStart - 1).getContents();
			}
			header.setColumnName(sheet.getCell(0, rowStart - 1).getContents());
			header.setDatas(datas);
			dataSheet.setHeader(header);
			dataRows.add(header);
			
			for (int row = rowStart; row < rows; row++) {
				DataRow dataRow = new DataRow();
				datas = new String[len];
				for (int column = 1; column < columns; column++) {
					datas[column - 1] = sheet.getCell(column, row).getContents();
				}
				dataRow.setColumnName(sheet.getCell(0, row).getContents());
				if (StringUtil.isNotBlank(dataRow.getColumnName())) {
					dataRow
						.setRowClass(CLASS_MAP.get(NAMES_KEY.get(dataRow.getColumnName().trim())));
				}
				dataRow.setDatas(datas);
				dataRows.add(dataRow);
			}
			dataSheet.setDataRows(dataRows);
		}
		
		return dataSheet;
	}
	
	public static DataSheet installDataSheet(Sheet sheet, Map<String, String> data) {
		if (null == sheet) {
			logger.error("财务报表解析失败：没有表单数据");
			return null;
		}
		
		DataSheet dataSheet = new DataSheet();
		dataSheet.setTitle(sheet.getName());
		int columns = sheet.getColumns();//得到列数
		int rows = sheet.getRows();//得到行数
		int len = columns - 1;
		logger.info(sheet.getName() + "：共有" + columns + "列，" + rows + "行");
		dataSheet.setRow(rows);
		dataSheet.setColumn(columns);
		if (columns > 0 && rows > 0) {
			List<DataRow> dataRows = new ArrayList<>(rows - 2);
			int i = 2;
			int j = 0;
			DataRow firstRow = installDataRow(sheet, i, j, len, columns);
			String[] keys = new String[len];
			if (null != firstRow && null != firstRow.getDatas() && firstRow.getDatas().length > 0) {
				for (int k = 0; k < firstRow.getDatas().length; k++) {
					keys[k] = firstRow.getDatas()[k];
				}
			} else {
				logger.error("财务报表解析失败：数据格式不正确");
				return null;
			}
			dataRows.add(firstRow);
			
			for (i += 1; i < rows; i++) {
				j = 0;
				DataRow dataRow = installDataRow(sheet, i, j, len, columns, keys, data);
				dataRows.add(dataRow);
			}
			dataSheet.setDataRows(dataRows);
			dataSheet.setYears(keys);
		}
		
		return dataSheet;
	}
	
	public static DataRow installDataRow(Sheet sheet, int i, int j, int len, int columns) {
		DataRow dataRow = new DataRow();
		String cellContent = sheet.getCell(j, i).getContents();
		if (StringUtil.isNotBlank(cellContent)) {
			cellContent = cellContent.trim();
			dataRow.setColumnName(cellContent);
		} else {
			dataRow.setColumnName("");
		}
		
		String firstDatas[] = new String[len];
		int index = 0;
		for (j += 1; j < columns; j++, index++) {
			cellContent = sheet.getCell(j, i).getContents();
			if (StringUtil.isNotBlank(cellContent)) {
				cellContent = cellContent.trim();
			} else {
				cellContent = "";
			}
			firstDatas[index] = cellContent;
		}
		dataRow.setDatas(firstDatas);
		return dataRow;
	}
	
	public static DataRow installDataRow(Sheet sheet, int i, int j, int len, int columns,
											String[] keys, Map<String, String> data) {
		DataRow dataRow = new DataRow();
		String cellContent = sheet.getCell(j, i).getContents();
		if (StringUtil.isNotBlank(cellContent)) {
			cellContent = cellContent.trim();
			dataRow.setColumnName(cellContent);
		} else {
			dataRow.setColumnName("");
		}
		String key = NAMES_KEY.get(cellContent);
		String firstDatas[] = new String[len];
		int index = 0;
		for (j += 1; j < columns; j++, index++) {
			cellContent = sheet.getCell(j, i).getContents();
			if (StringUtil.isNotBlank(cellContent)) {
				cellContent = cellContent.trim();
			} else {
				cellContent = "";
			}
			firstDatas[index] = cellContent;
			if (StringUtil.isNotBlank(key)) {
				data.put(keys[index] + "." + key, cellContent);
			} else {
				logger.info("未找到对应的key：" + cellContent);
			}
		}
		dataRow.setDatas(firstDatas);
		dataRow.setColumnCode(key);
		dataRow.setRowClass(CLASS_MAP.get(key));
		return dataRow;
	}
	
	//偿债能力分析
	public static void installDPAanalyze(DataFinancial dataFinancial) {
		installDPAanalyze(dataFinancial, false);
	}
	
	//偿债能力分析
	public static void installDPAanalyze(DataFinancial dataFinancial, boolean init) {
		if (null == dataFinancial) {
			return;
		}
		
		DataSheet dpaAnalyze = new DataSheet();
		dpaAnalyze.setTitle("偿债能力分析");
		DataRow firstRow = new DataRow();
		String[] balanceYears = dataFinancial.getBalance().getYears();
		String[] years = new String[balanceYears.length];
		int i = 0;
		for (; i < balanceYears.length; i++) {
			years[i] = balanceYears[i];
		}
		firstRow.setColumnName("项目");
		dpaAnalyze.setYears(years);
		
		String datas[] = new String[years.length];
		i = 0;
		for (i = 0; i < years.length; i++) {
			datas[i] = years[i];
		}
		firstRow.setDatas(datas);
		
		List<DataRow> rows = new ArrayList<>();
		rows.add(firstRow);
		
		Map<String, String> map = dataFinancial.getData();
		int dataLength = datas.length;
		DataRow row1 = new DataRow();
		DataRow row2 = new DataRow();
		DataRow row3 = new DataRow();
		DataRow row4 = new DataRow();
		DataRow row5 = new DataRow();
		DataRow row6 = new DataRow();
		DataRow row7 = new DataRow();
		row1.setColumnName("利息保障倍数");
		row1.setRowClass(CLASS_MAP.get(row1.getColumnName()));
		row2.setColumnName("资产负债率");
		row2.setRowClass(CLASS_MAP.get(row2.getColumnName()));
		row3.setColumnName("或有负债=或有负债额/净资产");
		row3.setRowClass(CLASS_MAP.get(row3.getColumnName()));
		row4.setColumnName("流动比率");
		row4.setRowClass(CLASS_MAP.get(row4.getColumnName()));
		row5.setColumnName("速动比率");
		row5.setRowClass(CLASS_MAP.get(row5.getColumnName()));
		row6.setColumnName("现金流量比率");
		row6.setRowClass(CLASS_MAP.get(row6.getColumnName()));
		row7.setColumnName("现金流量利息保障倍数");
		row7.setRowClass(CLASS_MAP.get(row7.getColumnName()));
		String data1[] = new String[dataLength];
		String data2[] = new String[dataLength];
		String data3[] = new String[dataLength];
		String data4[] = new String[dataLength];
		String data5[] = new String[dataLength];
		String data6[] = new String[dataLength];
		String data7[] = new String[dataLength];
		//		data1[0] = row1.getColumnName();
		if (!init) {
			for (i = 0; i < years.length; i++) {
				data1[i] = getTimesInterestEarned(map, i, years);
				data2[i] = getAssetliabilityRatio(map, i, years);
				data3[i] = getContingentLiability(map, i, years);
				data4[i] = getIquidityRatio(map, i, years);
				data5[i] = getQuickRatio(map, i, years);
				data6[i] = getCashFlowRatio(map, i, years);
				data7[i] = getTheMultipleOfInterestSafeguardCashFlow(map, i, years);
			}
		}
		row1.setDatas(data1);
		row2.setDatas(data2);
		row3.setDatas(data3);
		row4.setDatas(data4);
		row5.setDatas(data5);
		row6.setDatas(data6);
		row7.setDatas(data7);
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		rows.add(row4);
		rows.add(row5);
		rows.add(row6);
		rows.add(row7);
		dpaAnalyze.setDataRows(rows);
		dataFinancial.setDpaAnalyze(dpaAnalyze);
	}
	
	//利息保障倍数 = （利润表中利润总额+利润表中财务费用）/利润表中财务费用
	public static String getTimesInterestEarned(Map<String, String> map, int i, String[] keys) {
		String profit_financial_cost = map.get(keys[i] + "." + PROFIT_FINANCIAL_COST);
		String v1 = add(map.get(keys[i] + "." + PROFIT_TOTAL_PROFIT), profit_financial_cost);
		return divide(v1, profit_financial_cost);
	}
	
	//资产负债率 = 负债合计/资产总计
	public static String getAssetliabilityRatio(Map<String, String> map, int i, String[] keys) {
		return divide(map.get(keys[i] + "." + BALANCE_TOTAL_LIABILITY),
			map.get(keys[i] + "." + BALANCE_TOTAL_CAPITAL));
	}
	
	//或有负债=或有负债额/净资产 = 预计负债/（资产总计-负债合计）
	public static String getContingentLiability(Map<String, String> map, int i, String[] keys) {
		return divide(
			map.get(keys[i] + "." + BALANCE_ACCRUED_LIABILITIES),
			subtract(map.get(keys[i] + "." + BALANCE_TOTAL_CAPITAL),
				map.get(keys[i] + "." + BALANCE_TOTAL_LIABILITY)));
	}
	
	//流动比率=流动资产合计/流动负债合计
	public static String getIquidityRatio(Map<String, String> map, int i, String[] keys) {
		return divide(map.get(keys[i] + "." + BALANCE_TOTAL_FLOW_CAPITAL),
			map.get(keys[i] + "." + BALANCE_TOTAL_CURRENT_LIABILITY));
	}
	
	//速动比率=（流动资产合计-待摊费用-存货）/流动负债合计
	public static String getQuickRatio(Map<String, String> map, int i, String[] keys) {
		return divide(
			subtract(
				map.get(keys[i] + "." + BALANCE_TOTAL_FLOW_CAPITAL),
				add(map.get(keys[i] + "." + BALANCE_UNAMORTIZED_EXPENSE),
					map.get(keys[i] + "." + BALANCE_INVENTORY))),
			map.get(keys[i] + "." + BALANCE_TOTAL_CURRENT_LIABILITY));
	}
	
	//现金流量比率	现金流量表、资产负债表	现金流量表中的经营性活动产生的现金流量净额/资产负债表中流动负债合计
	public static String getCashFlowRatio(Map<String, String> map, int i, String[] keys) {
		return divide(map.get(keys[i] + "." + CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES),
			map.get(keys[i] + "." + BALANCE_TOTAL_CURRENT_LIABILITY));
	}
	
	//现金流量利息保障倍数		现金流量表中“经营活动产生的现金流量净额”/利润表中“财务费用”
	public static String getTheMultipleOfInterestSafeguardCashFlow(Map<String, String> map, int i,
																	String[] keys) {
		return divide(map.get(keys[i] + "." + CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES),
			map.get(keys[i] + "." + PROFIT_FINANCIAL_COST));
	}
	
	//运营能力分析
	public static void instalLOCanalyze(DataFinancial dataFinancial) {
		instalLOCanalyze(dataFinancial, false);
	}
	
	//运营能力分析
	public static void instalLOCanalyze(DataFinancial dataFinancial, boolean init) {
		if (null == dataFinancial) {
			return;
		}
		
		DataSheet ocaAnalyze = new DataSheet();
		ocaAnalyze.setTitle("运营能力分析");
		DataRow firstRow = new DataRow();
		String[] balanceYears = dataFinancial.getBalance().getYears();
		String[] years = new String[balanceYears.length];
		int i = 0;
		for (; i < balanceYears.length; i++) {
			years[i] = balanceYears[i];
		}
		firstRow.setColumnName("项目");
		ocaAnalyze.setYears(years);
		
		String datas[] = new String[years.length];
		i = 0;
		for (i = 0; i < years.length; i++) {
			datas[i] = years[i];
		}
		firstRow.setDatas(datas);
		
		List<DataRow> rows = new ArrayList<>();
		rows.add(firstRow);
		
		Map<String, String> map = dataFinancial.getData();
		int dataLength = datas.length;
		DataRow row1 = new DataRow();
		DataRow row2 = new DataRow();
		DataRow row3 = new DataRow();
		DataRow row4 = new DataRow();
		row1.setColumnName("存货周转天数");
		row1.setRowClass(CLASS_MAP.get(row1.getColumnName()));
		row2.setColumnName("应收账款周转天数");
		row2.setRowClass(CLASS_MAP.get(row2.getColumnName()));
		row3.setColumnName("应付账款周转天数");
		row3.setRowClass(CLASS_MAP.get(row3.getColumnName()));
		row4.setColumnName("预收账款周转天数");
		row4.setRowClass(CLASS_MAP.get(row4.getColumnName()));
		String data1[] = new String[dataLength];
		String data2[] = new String[dataLength];
		String data3[] = new String[dataLength];
		String data4[] = new String[dataLength];
		if (!init) {
			for (i = 0; i < years.length - 1; i++) {
				data1[i] = getDaysSalesOfInventory(map, i, years);
				data2[i] = getDaysSalesOutstanding(map, i, years);
				data3[i] = getDaysPayableOutstanding(map, i, years);
				data4[i] = getDeferredRevenueTurnoverDays(map, i, years);
			}
		}
		row1.setDatas(data1);
		row2.setDatas(data2);
		row3.setDatas(data3);
		row4.setDatas(data4);
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		rows.add(row4);
		ocaAnalyze.setDataRows(rows);
		dataFinancial.setOcAnalyze(ocaAnalyze);
	}
	
	//存货周转天数	利润表、资产负债表	360/(利润表中“减：主营业务成本（融资成本）”/（（资产负债表中本年存货+上一年存货）/2))
	public static String getDaysSalesOfInventory(Map<String, String> map, int i, String[] keys) {
		//		String v2 = divide(add(map.get(keys[i] + "." + BALANCE_INVENTORY), map.get(keys[i+1] + "." + BALANCE_INVENTORY)), "2");
		//		String v3 = divide(map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST), v2);
		//		return divide("360", v3);
		return divide(
			multiply(
				"360",
				add(map.get(keys[i] + "." + BALANCE_INVENTORY),
					map.get(keys[i + 1] + "." + BALANCE_INVENTORY))),
			multiply(map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST), "2"));
	}
	
	//应收账款周转天数	利润表、资产负债表	360/（利润表中“主营业务收入”/（（资产负债表中本期应收账款+上一年应收账款）/2））
	public static String getDaysSalesOutstanding(Map<String, String> map, int i, String[] keys) {
		//		String v1 = divide("360", map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_INCOME));
		//		String v2 = divide(add(map.get(keys[i] + "." + BALANCE_RECEIVABLES), map.get(keys[i+1] + "." + BALANCE_RECEIVABLES)), "2");
		//		return divide(v1, v2);
		return divide(
			multiply(
				"360",
				add(map.get(keys[i] + "." + BALANCE_RECEIVABLES),
					map.get(keys[i + 1] + "." + BALANCE_RECEIVABLES))),
			multiply(map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_INCOME), "2"));
	}
	
	//应付账款周转天数	利润表、资产负债表	360/（利润表中“减：主营业务成本（融资成本）”/（（资产负债表中本年应付账款+上一年应付账款）/2））
	public static String getDaysPayableOutstanding(Map<String, String> map, int i, String[] keys) {
		//		String v1 = divide("360", map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST));
		//		String v2 = divide(add(map.get(keys[i] + "." + BALANCE_ACCOUNTS_PAYABLE), map.get(keys[i+1] + "." + BALANCE_ACCOUNTS_PAYABLE)), "2");
		//		return divide(v1, v2);
		return divide(
			multiply(
				"360",
				add(map.get(keys[i] + "." + BALANCE_ACCOUNTS_PAYABLE),
					map.get(keys[i + 1] + "." + BALANCE_ACCOUNTS_PAYABLE))),
			multiply(map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST), "2"));
	}
	
	//预收账款周转天数	利润表、资产负债表	360/（利润表中“减：主营业务成本（融资成本）”/（（资产负债表中本年预付账款+上一年预付账款）/2））
	public static String getDeferredRevenueTurnoverDays(Map<String, String> map, int i,
														String[] keys) {
		//		String v1 = divide("360", map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST));
		//		String v2 = divide(add(map.get(keys[i] + "." + BALANCE_ADVANCE_PAYMENT), map.get(keys[i+1] + "." + BALANCE_ADVANCE_PAYMENT)), "2");
		//		return divide(v1, v2);
		return divide(
			multiply(
				"360",
				add(map.get(keys[i] + "." + BALANCE_ADVANCE_PAYMENT),
					map.get(keys[i + 1] + "." + BALANCE_ADVANCE_PAYMENT))),
			multiply(map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST), "2"));
	}
	
	//盈利能力分析
	public static void instalECanalyze(DataFinancial dataFinancial) {
		instalECanalyze(dataFinancial, false);
	}
	
	//盈利能力分析
	public static void instalECanalyze(DataFinancial dataFinancial, boolean init) {
		if (null == dataFinancial) {
			return;
		}
		
		DataSheet ecAnalyze = new DataSheet();
		ecAnalyze.setTitle("盈利能力分析");
		DataRow firstRow = new DataRow();
		String[] balanceYears = dataFinancial.getBalance().getYears();
		String[] years = new String[balanceYears.length];
		int i = 0;
		for (; i < balanceYears.length; i++) {
			years[i] = balanceYears[i];
		}
		firstRow.setColumnName("项目");
		ecAnalyze.setYears(years);
		
		String datas[] = new String[years.length];
		i = 0;
		for (i = 0; i < years.length; i++) {
			datas[i] = years[i];
		}
		firstRow.setDatas(datas);
		
		List<DataRow> rows = new ArrayList<>();
		rows.add(firstRow);
		
		Map<String, String> map = dataFinancial.getData();
		int dataLength = datas.length;
		DataRow row1 = new DataRow();
		DataRow row2 = new DataRow();
		DataRow row3 = new DataRow();
		DataRow row4 = new DataRow();
		DataRow row5 = new DataRow();
		row1.setColumnName("营业收入（主营业务）增长率（%）");
		row1.setRowClass(CLASS_MAP.get(row1.getColumnName()));
		row2.setColumnName("营业（主营业务）毛利润率（%）");
		row2.setRowClass(CLASS_MAP.get(row2.getColumnName()));
		row3.setColumnName("主营业务净利润率（%）");
		row3.setRowClass(CLASS_MAP.get(row3.getColumnName()));
		row4.setColumnName("总资产报酬率（%）");
		row4.setRowClass(CLASS_MAP.get(row4.getColumnName()));
		row5.setColumnName("净资产收益率（ROE）（%）");
		row5.setRowClass(CLASS_MAP.get(row5.getColumnName()));
		String data1[] = new String[dataLength];
		String data2[] = new String[dataLength];
		String data3[] = new String[dataLength];
		String data4[] = new String[dataLength];
		String data5[] = new String[dataLength];
		if (!init) {
			for (i = 0; i < years.length; i++) {
				data1[i] = getIncreaseRateOfBusinessRevenue(map, i, years);
				data2[i] = getBusinessGrossMargin(map, i, years);
				data3[i] = getMainBusinessProfitability(map, i, years);
				data4[i] = getRateOfReturnOnTotalAssets(map, i, years);
				data5[i] = getReturnOnEquity(map, i, years);
			}
		}
		row1.setDatas(data1);
		row2.setDatas(data2);
		row3.setDatas(data3);
		row4.setDatas(data4);
		row5.setDatas(data5);
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		rows.add(row4);
		rows.add(row5);
		ecAnalyze.setDataRows(rows);
		dataFinancial.setEcAnalyze(ecAnalyze);
	}
	
	//营业收入（主营业务）增长率（%）	利润表	（本年主营业务收入-上一年主营业务收入）/上一年主营业务收入
	public static String getIncreaseRateOfBusinessRevenue(Map<String, String> map, int i,
															String[] keys) {
		if ((i + 1) >= keys.length) {
			return "";
		}
		String v2 = map.get(keys[i + 1] + "." + PROFIT_MAIN_BUSINESS_INCOME);
		return divide(subtract(map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_INCOME), v2), v2);
	}
	
	//营业（主营业务）毛利润率（%）	利润表	（利润表主营业务收入-(减：主营业务成本（融资成本）)）/利润表中“主营业务收入”
	public static String getBusinessGrossMargin(Map<String, String> map, int i, String[] keys) {
		String v2 = map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_INCOME);
		return divide(subtract(v2, map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_COST)), v2);
	}
	
	//主营业务净利润率（%）	利润表	利润表中“净利润/主营业务收入”
	public static String getMainBusinessProfitability(Map<String, String> map, int i, String[] keys) {
		return divide(map.get(keys[i] + "." + PROFIT_RETAINED_PROFITS),
			map.get(keys[i] + "." + PROFIT_MAIN_BUSINESS_INCOME));
	}
	
	//总资产报酬率（%）	利润表、资产负债表	利润表中“净利润”+利润表中“财务费用”/（（资产负债表中本年“总资产”+上一年总资产）/2）
	public static String getRateOfReturnOnTotalAssets(Map<String, String> map, int i, String[] keys) {
		//		String v1 = add(map.get(keys[i] + "." + PROFIT_RETAINED_PROFITS), map.get(keys[i] + "." + PROFIT_FINANCIAL_COST));
		//		String v2 = divide(add(map.get(keys[i] + "." + BALANCE_TOTAL_CAPITAL), map.get(keys[i+1] + "." + BALANCE_TOTAL_CAPITAL)), "2");
		//		return divide(v1, v2);
		if ((i + 1) >= keys.length) {
			return "";
		}
		return divide(
			multiply(
				add(map.get(keys[i] + "." + PROFIT_RETAINED_PROFITS),
					map.get(keys[i] + "." + PROFIT_FINANCIAL_COST)), "2"),
			add(map.get(keys[i] + "." + BALANCE_TOTAL_CAPITAL),
				map.get(keys[i + 1] + "." + BALANCE_TOTAL_CAPITAL)));
	}
	
	//净资产收益率（ROE）（%）	利润表、资产负债表	利润表中“净利润”/（（资产负债表中本年“所有者权益合计”+上一年所有者权益）/2）
	public static String getReturnOnEquity(Map<String, String> map, int i, String[] keys) {
		//		String v2 = divide(add(map.get(keys[i] + "." + BALANCE_STOCKHOLDER_EQUITY), map.get(keys[i+1] + "." + BALANCE_STOCKHOLDER_EQUITY)), "2");
		//		return divide(map.get(keys[i] + "." + PROFIT_RETAINED_PROFITS), v2);
		if ((i + 1) >= keys.length) {
			return "";
		}
		return divide(
			multiply("2", map.get(keys[i] + "." + PROFIT_RETAINED_PROFITS)),
			add(map.get(keys[i] + "." + BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL),
				map.get(keys[i + 1] + "." + BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL)));
	}
	
	//现金流分析
	public static void installCFAanalyze(DataFinancial dataFinancial) {
		installCFAanalyze(dataFinancial, false);
	}
	
	//现金流分析
	public static void installCFAanalyze(DataFinancial dataFinancial, boolean init) {
		if (null == dataFinancial) {
			return;
		}
		
		DataSheet dpaAnalyze = new DataSheet();
		dpaAnalyze.setTitle("现金流分析");
		DataRow firstRow = new DataRow();
		String[] balanceYears = dataFinancial.getBalance().getYears();
		String[] years = new String[balanceYears.length];
		int i = 0;
		for (; i < balanceYears.length; i++) {
			years[i] = balanceYears[i];
		}
		//		years[i] = "行业平均值";
		firstRow.setColumnName("项目");
		dpaAnalyze.setYears(years);
		
		String datas[] = new String[years.length];
		i = 0;
		for (i = 0; i < years.length; i++) {
			datas[i] = years[i];
		}
		firstRow.setDatas(datas);
		
		List<DataRow> rows = new ArrayList<>();
		rows.add(firstRow);
		
		Map<String, String> map = dataFinancial.getData();
		int dataLength = datas.length;
		DataRow row1 = new DataRow();
		DataRow row2 = new DataRow();
		DataRow row3 = new DataRow();
		DataRow row4 = new DataRow();
		DataRow row5 = new DataRow();
		DataRow row6 = new DataRow();
		DataRow row7 = new DataRow();
		DataRow row8 = new DataRow();
		DataRow row9 = new DataRow();
		DataRow row10 = new DataRow();
		row1.setColumnName("经营性现金流入");
		row1.setRowClass(CLASS_MAP.get(row1.getColumnName()));
		row2.setColumnName("经营性现金流出");
		row2.setRowClass(CLASS_MAP.get(row2.getColumnName()));
		row3.setColumnName("经营性净现金流");
		row3.setRowClass(CLASS_MAP.get(row3.getColumnName()));
		row4.setColumnName("投资性现金流入");
		row4.setRowClass(CLASS_MAP.get(row4.getColumnName()));
		row5.setColumnName("投资性现金流出");
		row5.setRowClass(CLASS_MAP.get(row5.getColumnName()));
		row6.setColumnName("投资性净现金流");
		row6.setRowClass(CLASS_MAP.get(row6.getColumnName()));
		row7.setColumnName("筹资性现金流入");
		row7.setRowClass(CLASS_MAP.get(row7.getColumnName()));
		row8.setColumnName("筹资性现金流出");
		row8.setRowClass(CLASS_MAP.get(row8.getColumnName()));
		row9.setColumnName("筹资性净现金流");
		row9.setRowClass(CLASS_MAP.get(row9.getColumnName()));
		row10.setColumnName("净现金流");
		row10.setRowClass(CLASS_MAP.get(row10.getColumnName()));
		String data1[] = new String[dataLength];
		String data2[] = new String[dataLength];
		String data3[] = new String[dataLength];
		String data4[] = new String[dataLength];
		String data5[] = new String[dataLength];
		String data6[] = new String[dataLength];
		String data7[] = new String[dataLength];
		String data8[] = new String[dataLength];
		String data9[] = new String[dataLength];
		String data10[] = new String[dataLength];
		if (!init) {
			for (i = 0; i < years.length; i++) {
				data1[i] = getcashFlowSubtotalOfCashInflowBusinessActivities(map, i, years);
				data2[i] = getCashflowSubtotalOfCashOutFlowsBusinessActivities(map, i, years);
				data3[i] = getOperatingCashFlows(map, i, years);
				data4[i] = getTheInvestmentCashInflows(map, i, years);
				data5[i] = getTheInvestmentCashOutflows(map, i, years);
				data6[i] = getTheInvestmentCashFlows(map, i, years);
				data7[i] = getRaiseCashInflows(map, i, years);
				data8[i] = getRaiseCashOutflows(map, i, years);
				data9[i] = getRaiseCashFlows(map, i, years);
				data10[i] = getNetCashFlow(map, i, years);
			}
		}
		row1.setDatas(data1);
		row2.setDatas(data2);
		row3.setDatas(data3);
		row4.setDatas(data4);
		row5.setDatas(data5);
		row6.setDatas(data6);
		row7.setDatas(data7);
		row8.setDatas(data8);
		row9.setDatas(data9);
		row10.setDatas(data10);
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		rows.add(row4);
		rows.add(row5);
		rows.add(row6);
		rows.add(row7);
		rows.add(row8);
		rows.add(row9);
		rows.add(row10);
		dpaAnalyze.setDataRows(rows);
		dataFinancial.setCfAnalyze(dpaAnalyze);
	}
	
	//经营性现金流入	现金流量表	取现金流量表对应的大项合计
	public static String getcashFlowSubtotalOfCashInflowBusinessActivities(Map<String, String> map,
																			int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_BUSINESS_ACTIVITIES);
	}
	
	//经营性现金流出	现金流量表	取现金流量表对应的大项合计
	public static String getCashflowSubtotalOfCashOutFlowsBusinessActivities(	Map<String, String> map,
																				int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_BUSINESS_ACTIVITIES);
	}
	
	//经营性净现金流	现金流量表	取现金流量表对应的大项合计
	public static String getOperatingCashFlows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES);
	}
	
	//投资性现金流入	现金流量表	取现金流量表对应的大项合计
	public static String getTheInvestmentCashInflows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_INVESTMENT);
	}
	
	//投资性现金流出	现金流量表	取现金流量表对应的大项合计
	public static String getTheInvestmentCashOutflows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_INVESTMENT);
	}
	
	//投资性净现金流	现金流量表	取现金流量表对应的大项合计
	public static String getTheInvestmentCashFlows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_NET_AMOUNT_INVESTMENT);
	}
	
	//筹资性现金流入	现金流量表	取现金流量表对应的大项合计
	public static String getRaiseCashInflows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_FINANCING);
	}
	
	//筹资性现金流出	现金流量表	取现金流量表对应的大项合计
	public static String getRaiseCashOutflows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_FINANCING);
	}
	
	//筹资性净现金流	现金流量表	取现金流量表对应的大项合计
	public static String getRaiseCashFlows(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_NET_AMOUNT_FINANCING);
	}
	
	//净现金流	现金流量表	取现金流量表对应的大项合计
	public static String getNetCashFlow(Map<String, String> map, int i, String[] keys) {
		return map.get(keys[i] + "." + CASHFLOW_THE_NET_CASH_FLOW);
	}
	
	public static String add(String... strs) {
		try {
			if (null == strs) {
				return "";
			}
			Money m = new Money(0L);
			for (String s : strs) {
				if (null != s && !"".equals(s.trim())) {
					m.addTo(Money.amout(s));
				}
			}
			return m.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("相加出错：", strs, e);
			return "";
		}
	}
	
	public static String subtract(String str1, String str2) {
		try {
			Money m1 = new Money(0L);
			if (StringUtil.isNotEmpty(str1)) {
				m1 = Money.amout(str1);
			}
			Money m2 = new Money(0L);
			if (StringUtil.isNotEmpty(str2)) {
				m2 = Money.amout(str2);
			}
			return m1.subtract(m2).toString();
		} catch (Exception e) {
			logger.error("相减出错：", str1, str2, e);
			return "";
		}
	}
	
	public static String multiply(String str1, String str2) {
		try {
			Money m1 = new Money(0L);
			if (StringUtil.isNotEmpty(str1)) {
				m1 = Money.amout(str1);
			}
			double d2 = 0d;
			if (StringUtil.isNotEmpty(str2)) {
				d2 = Double.parseDouble(str2.replaceAll(",", ""));
			}
			return m1.multiply(d2).toString();
		} catch (Exception e) {
			logger.error("相乘出错：", str1, str2, e);
			return "";
		}
	}
	
	public static String divide(String str1, String str2) {
		try {
			Money m1 = new Money(0L);
			if (StringUtil.isNotEmpty(str1)) {
				m1 = Money.amout(str1);
			}
			double d2 = 0d;
			if (StringUtil.isNotEmpty(str2)) {
				d2 = Double.parseDouble(str2.replaceAll(",", ""));
			}
			if (Math.abs(d2) <= 0.000001d) {
				logger.error("相除出错：除数为0", str1, str2);
				return "";
			}
			return m1.divide(d2).toString();
		} catch (Exception e) {
			logger.error("相除出错：", str1, str2, e);
			return "";
		}
	}
	
	public static void main(String[] args) {
		String path = "F:\\test\\一般企业企业报表标准格式-模板.xls";
		try {
			JSONObject json = parseExcel(path);
			System.out.println("json is null:" + (null == json));
		} catch (BiffException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static JSONObject parseExcel(String excelPath) throws BiffException, IOException {
		Workbook model = Workbook.getWorkbook(new File(excelPath));
		DataFinancial dataFinancial = new DataFinancial();
		Map<String, String> data = new HashMap<String, String>();
		//		Workbook model = Workbook.getWorkbook(is);
		Sheet balanceSheet = model.getSheet(0);
		dataFinancial.setBalance(installDataSheet(balanceSheet, data));
		Sheet profitSheet = model.getSheet(1);
		dataFinancial.setProfit(installDataSheet(profitSheet, data));
		Sheet cashFlowSheet = model.getSheet(2);
		dataFinancial.setCashFlow(installDataSheet(cashFlowSheet, data));
		
		dataFinancial.setData(data);
		
		installDPAanalyze(dataFinancial, true);
		instalLOCanalyze(dataFinancial, true);
		instalECanalyze(dataFinancial, true);
		installCFAanalyze(dataFinancial, true);
		//		Sheet sheet = model.getSheet(0);
		//		int columns = sheet.getColumns();//得到列数
		//		int rows = sheet.getRows();//得到行数
		//		logger.info(excelPath + "：共有" + columns + "列，" + rows + "行");
		//		if (columns > 0 && rows > 0) {
		//			String[][] datas = new String[rows][columns];
		//			for (int i = 0; i < rows; i++) {
		//				for (int j = 0; j < columns; j++) {
		//					datas[i][j] = sheet.getCell(j, i).getContents();
		//				}
		//			}
		//			
		//			return new ExcelData(columns, rows, datas);
		//		}
		return parseToJson(dataFinancial, null);
	}
	
	public static JSONObject parseToJson(DataFinancial dataFinancial, String type) {
		if (null == dataFinancial) {
			return null;
		}
		
		JSONObject json = new JSONObject();
		DataSheet balance = dataFinancial.getBalance();
		if (null != balance) {
			json.put("years", balance.getYears());
		}
		
		String[][] d1 = installDatas(balance, type);
		if (null != d1) {
			json.put("balance", d1);
		}
		
		DataSheet profit = dataFinancial.getProfit();
		String[][] d2 = installDatas(profit, type);
		if (null != d2) {
			json.put("profit", d2);
		}
		
		DataSheet cashFlow = dataFinancial.getCashFlow();
		String[][] d3 = installDatas(cashFlow, type);
		if (null != d3) {
			json.put("cashFlow", d3);
		}
		
		DataSheet dpaAnalyze = dataFinancial.getDpaAnalyze();
		String[][] d4 = installDatas(dpaAnalyze, true, type);
		if (null != d4) {
			json.put("dpaAnalyze", d4);
		}
		
		DataSheet ocAnalyze = dataFinancial.getOcAnalyze();
		String[][] d5 = installDatas(ocAnalyze, true, type);
		if (null != d5) {
			json.put("ocAnalyze", d5);
		}
		
		DataSheet ecAnalyze = dataFinancial.getEcAnalyze();
		String[][] d6 = installDatas(ecAnalyze, true, type);
		if (null != d6) {
			json.put("ecAnalyze", d6);
		}
		
		DataSheet cfAnalyze = dataFinancial.getCfAnalyze();
		String[][] d7 = installDatas(cfAnalyze, type);
		if (null != d7) {
			json.put("cfAnalyze", d7);
		}
		
		return json;
	}
	
	public static String[][] installDatas(DataSheet dataSheet, String type) {
		return installDatas(dataSheet, false, type);
	}
	
	public static String[][] installDatas(DataSheet dataSheet, boolean needAverage, String type) {
		Map<String, String[]> map = new HashMap<>();
		if (null != dataSheet) {
			int row = dataSheet.getRow();
			int column = dataSheet.getColumn() + 1;
			List<DataRow> dataRows = dataSheet.getDataRows();
			String datas[][] = null;
			if (needAverage) {
				datas = new String[row][column + 5];
			} else {
				datas = new String[row][column + 4];
			}
			
			for (int i = 0; i < row; i++) {
				DataRow dataRow = dataRows.get(i);
				datas[i][0] = dataRow.getRowClass();
				datas[i][1] = dataRow.getColumnName();
				map.put(StringUtil.trimStart(dataRow.getColumnName()), dataRow.getDatas());
				int j = 2;
				for (; j < column; j++) {
					datas[i][j] = dataRow.getDatas()[j - 2];
				}
				
				//校验计算数据
				String[] erMes = FinaciClaCheck.checkdata(map, dataRow.getColumnName(),
					dataRow.getDatas(), type);
				if (erMes != null) {
					int mesIndex = column;
					for (String s : erMes) {
						datas[i][mesIndex] = s;
						mesIndex++;
					}
				}
				
				if (needAverage) {
					if (i == 0) {
						datas[i][j] = "行业平均值";
					} else {
						datas[i][j] = dataRow.getAverage();
					}
				}
			}
			
			return datas;
		}
		
		return null;
	}
	
	public static String getKpiClass(String kpiName) {
		String key = NAMES_KEY.get(kpiName);
		return CLASS_MAP.get(key);
	}
	
	public static String getKpiClassByCode(String kpiCode) {
		return CLASS_MAP.get(kpiCode);
	}
	
	public static void main0(String[] args) {
		File file = new File("F:\\works\\进出口担保项目\\jb.txt");
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束  
			Map<String, Integer> map = new LinkedHashMap<>();
			while ((tempString = reader.readLine()) != null) {
				// 显示行号  
				if (null != tempString && !tempString.trim().equals("")) {
					Integer count = map.get(tempString.trim());
					if (null == count) {
						count = 1;
					} else {
						count++;
					}
					map.put(tempString.trim(), count);
				}
				line++;
			}
			reader.close();
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (NAMES_KEY.get(entry.getKey()) == null) {
					//					System.out.println(entry.getKey());
					System.out.println("NAMES_KEY.put(\"" + entry.getKey() + "\", \"\");");
				}
				//				if (entry.getValue() > 1) {
				//					System.out.println(entry.getKey() + " 重复数据：" + entry.getValue());
				//				} else {
				//					// 加：公允价值变动收益（损失以“-”号填列）
				//					//            		System.out.println(entry.getKey() + "====" + entry.getValue());
				////					System.out.println("NAMES_KEY.put(\"" + entry.getKey() + "\", \"\");");
				//				}
			}
			System.out.println("行数:" + line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void main1(String[] args) {
		File file = new File("F:\\works\\进出口担保项目\\jb.txt");
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束  
			Map<String, Integer> map = new LinkedHashMap<>();
			while ((tempString = reader.readLine()) != null) {
				// 显示行号  
				if (null != tempString && !tempString.trim().equals("")) {
					Integer count = map.get(tempString.trim());
					if (null == count) {
						count = 1;
					} else {
						count++;
					}
					map.put(tempString.trim(), count);
				}
				line++;
			}
			reader.close();
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 1) {
					System.out.println(entry.getKey() + " 重复数据：" + entry.getValue());
				} else {
					// 加：公允价值变动收益（损失以“-”号填列）
					//            		System.out.println(entry.getKey() + "====" + entry.getValue());
					System.out.println("NAMES_KEY.put(\"" + entry.getKey() + "\", \"\");");
				}
			}
			System.out.println("行数:" + line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void main3(String[] args) {
		Set<String> sets = new HashSet<>();
		for (Map.Entry<String, String> entry : NAMES_KEY.entrySet()) {
			String value = entry.getValue();
			value = value.replaceAll("\\s+", "_").toLowerCase().replaceAll("-", "");
			System.out.println("NAMES_KEY.put(\"" + entry.getKey() + "\", \"" + value + "\");");
			if (sets.contains(value)) {
				System.out.println("---------------------------" + value);
			}
			sets.add(value);
		}
		
	}
	
	public static void main4(String[] args) {
		for (Map.Entry<String, String> entry : NAMES_KEY.entrySet()) {
			System.out.println("/** " + entry.getKey() + " */");
			System.out.println("public static final String " + entry.getValue().toUpperCase()
								+ " = " + "\"" + entry.getValue() + "\";");
		}
	}
	
	public static void main5(String[] args) {
		for (Map.Entry<String, String> entry : NAMES_KEY.entrySet()) {
			System.out.println("NAMES_KEY.put(\"" + entry.getKey() + "\", "
								+ entry.getValue().toUpperCase() + ");");
		}
	}
	
}
