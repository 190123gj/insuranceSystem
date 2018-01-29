package com.born.insurance.util;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 校验计算公式模板
 * */
public class FinaciClaCheck {
	
	protected static ScriptEngine calculatingStr = new ScriptEngineManager()
		.getEngineByName("JavaScript");
	
	protected static final Logger logger = LoggerFactory.getLogger(DataFinancialHelper.class);
	
	//校验和
	public static String[] checkdata(Map<String, String[]> map, String columnName, String[] datas,
										String type) {
		Map<String, String[]> checkMap = null;
		if (StringUtil.equals(type, "financial_tables_J_d")) {
			checkMap = checkMapJR;
		} else if (StringUtil.equals(type, "financial_tables_d")) {
			checkMap = checkMapYB;
		} else if (StringUtil.equals(type, "acr_b")) {
			checkMap = checkMapBHYB;
		} else if (StringUtil.equals(type, "acr_b_j")) {
			checkMap = checkMapBHJR;
		} else {
			return null;
		}
		
		if (checkMap.containsKey(columnName)) {
			String[][] data = new String[datas.length][checkMap.get(columnName).length];
			for (int i = 0; i < datas.length; i++) {
				int index = 0;
				for (String s : checkMap.get(columnName)) {
					String claSymble = "";
					if (s.indexOf("- ") > -1) {//包含减号
						claSymble = "-";
						s = s.replace("- ", "");
					}
					String[] d0 = map.get(s);
					if (d0 != null && StringUtil.isNotBlank(d0[i])) {
						data[i][index] = claSymble + d0[i];
					}
					index++;
				}
				
			}//计算
			String[] calRes = new String[datas.length];
			for (int i = 0; i < data.length; i++) {
				String calculating = "";
				for (String s : data[i]) {
					if (StringUtil.isNotBlank(s)) {
						if (calculating.length() > 0) {
							calculating += "+" + s;
						} else {
							calculating += s;
						}
					}
				}
				if (StringUtil.isNotBlank(calculating)) {
					try {
						double thisCls = (double) calculatingStr.eval(calculating.replaceAll(",",
							"").replace("+-", "-"));
						if (StringUtil.isNotBlank(datas[i])) {
							if (Math.round(thisCls * 100) / 100.00 != Double.parseDouble(datas[i]
								.replaceAll(",", "")))
								calRes[i] = "与计算的值不相等：" + (new Money(thisCls)).toStandardString();
							logger.error(
								"财务数据校验有不相等的情况出现columnName={}，data={}，index={},thisCls={}",
								columnName, datas[i], i, columnName);
						}
					} catch (ScriptException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			return calRes;
		}
		return null;
		
	}
	
	//尽职一般企业模板
	public static Map<String, String[]> checkMapYB = new HashMap<>();
	static {
		//资产负债表
		checkMapYB.put("流动资产合计", new String[] { "货币资金", "其他货币资金", "应收票据", "应收股利", "应收利息", "应收账款",
												"其他应收款", "预付账款", "应收补贴款", "存货", "待摊费用",
												"一年内到期的长期债权投资", "其他流动资产" });
		checkMapYB.put("非流动资产合计", new String[] { "长期投资合计", "固定资产合计", "无形资产及其他资产合计", "递延税款借项" });
		checkMapYB.put("固定资产合计", new String[] { "固定资产净额", "工程物资", "在建工程", "固定资产清理" });
		
		checkMapYB.put("资产总计", new String[] { "流动资产合计", "非流动资产合计" });
		checkMapYB.put("无形资产及其他资产合计", new String[] { "无形资产及其他资产：", "无形资产", "长期待摊费用", "其他长期资产" });
		
		checkMapYB.put("流动负债合计", new String[] { "短期借款", "应付票据", "应付账款", "预收账款", "应付工资", "应付利息",
												"应付股利", "应交税金", "其他应交款", "其他应付款", "预提费用", "预计负债",
												"一年内到期的长期负债", "其他流动负债" });
		
		checkMapYB.put("非流动负债合计", new String[] { "长期负债合计", "递延税款贷项" });
		
		checkMapYB.put("负债合计", new String[] { "流动负债合计", "非流动负债合计" });
		checkMapYB.put("股东权益合计",
			new String[] { "实收资本净额", "资本公积", "盈余公积", "未分配利润", "少数股东权益", "专项储备" });
		
		checkMapYB.put("负债和股东权益总计", new String[] { "负债合计", "股东权益合计" });
		
		checkMapYB.put("长期投资合计", new String[] { "长期股权投资", "投资性房地产" });
		checkMapYB.put("固定资产净值", new String[] { "固定资产原价", "- 减：累计折旧" });
		checkMapYB.put("长期负债合计", new String[] { "长期借款", "应付债券", "长期应付款", "专项应付款", "其他长期负债" });
		checkMapYB.put("实收资本净额", new String[] { "实收资本", "- 减：已归还投资" });
		
		checkMapYB.put("长期投资合计", new String[] { "长期股权投资", "投资性房地产" });
		checkMapYB.put("固定资产净值", new String[] { "固定资产原价", "- 减：累计折旧" });
		checkMapYB.put("固定资产净额", new String[] { "固定资产净值", "- 减：固定资产减值准备" });
		checkMapYB.put("长期负债合计", new String[] { "长期借款", "应付债券", "长期应付款", "专项应付款", "其他长期负债" });
		checkMapYB.put("实收资本净额", new String[] { "实收资本", "- 减：已归还投资" });
		
		//利润及利润分配表
		checkMapYB.put("二、主营业务利润", new String[] { "一、主营业务收入", "- 减：主营业务成本", "- 主营业务税金及附加" });
		checkMapYB.put("三、营业利润", new String[] { "二、主营业务利润", "加：其他业务利润", "- 减：营业费用", "- 管理费用",
												"- 财务费用", "- 资产减值损失" });
		checkMapYB.put("四、利润总额", new String[] { "三、营业利润", "加：投资收益", "补贴收入", "营业外收入", "- 减：营业外支出" });
		checkMapYB.put("五、净利润", new String[] { "四、利润总额", "- 减：所得税" });
		checkMapYB.put("六、可供分配的利润", new String[] { "五、净利润", "加：年初未分配利润", "其他转入" });
		checkMapYB.put("七、可供投资者分配的利润", new String[] { "六、可供分配的利润", "- 减：提取法定盈余公积", "- 提取法定公益金",
														"- 提取职工奖励及福利基金", "- 提取储备基金", "- 提取企业发展基金",
														"- 利润归还投资" });
		checkMapYB.put("八、未分配利润", new String[] { "七、可供投资者分配的利润", "- 减：应付优先股股利", "- 提取任意盈余公积",
												"- 应付普通股股利", "- 转作股本的普通股股利" });
		
		//现金流量表
		checkMapYB.put("现金流入小计(经营)",
			new String[] { "销售商品、提供劳务收到的现金", "收到的税费返还", "收到的其他与经营活动有关的现金" });
		checkMapYB.put("现金流出小计(经营)", new String[] { "购买商品、接受劳务支付的现金", "支付给职工以及为职工支付的现金", "支付的各项税费",
													"支付的其他与经营活动有关的现金" });
		checkMapYB.put("现金流入小计(投资)",
			new String[] { "收回投资所收到的现金", "取得投资收益所收到的现金", "处置固定资产、无形资产或其他长期资产收回的现金净额",
							"收到的其他与投资活动有关的现金" });
		checkMapYB.put("现金流出小计(投资)", new String[] { "购建固定资产、无形资产和其他长期资产支付的现金", "投资所支付的现金",
													"支付的其他与投资活动有关的现金" });
		
		checkMapYB.put("现金流入小计(筹资)", new String[] { "吸收投资所收到的现金", "借款收到的现金", "收到的其他与筹资活动有关的现金" });
		checkMapYB.put("现金流出小计(筹资)", new String[] { "偿还债务所支付的现金", "分配股利、利润或偿付利息所支付的现金",
													"支付的其他与筹资活动有关的现金" });
		checkMapYB.put("五、现金流量净额", new String[] { "经营活动产生的现金流量净额", "投资活动产生的现金流量净额",
													"筹资活动产生的现金流量净额", "四、汇率变动对现金的影响" });
		
		checkMapYB.put("经营活动产生的现金流量净额", new String[] { "现金流入小计(经营)", "- 现金流出小计(经营)" });
		checkMapYB.put("投资活动产生的现金流量净额", new String[] { "现金流入小计(投资)", "- 现金流出小计(投资)" });
		checkMapYB.put("筹资活动产生的现金流量净额", new String[] { "现金流入小计(筹资)", "- 现金流出小计(筹资)" });
		
	}
	
	//保后一般企业模板
	public static Map<String, String[]> checkMapBHYB = new HashMap<>();
	static {
		//资产负债表
		checkMapBHYB.put("流动资产合计", new String[] { "货币资金", "其他货币资金", "应收票据", "应收股利", "应收利息", "应收账款",
													"其他应收款", "预付账款", "应收补贴款", "存货", "待摊费用",
													"一年内到期的长期债权投资", "其他流动资产" });
		checkMapBHYB.put("非流动资产合计", new String[] { "长期投资合计", "固定资产合计", "无形资产及其他资产合计", "递延税款借项" });
		checkMapBHYB.put("资产总计", new String[] { "非流动资产合计", "流动资产合计" });
		checkMapBHYB.put("流动负债合计", new String[] { "短期借款", "应付票据", "应付账款", "预收账款", "应付工资", "应付利息",
													"应付股利", "应交税金", "其他应交款", "其他应付款", "预提费用",
													"预计负债", "一年内到期的长期负债", "其他流动负债" });
		checkMapBHYB.put("非流动负债合计", new String[] { "长期负债合计", "递延税款贷项" });
		checkMapBHYB.put("股东权益合计", new String[] { "实收资本净额", "资本公积", "盈余公积", "未分配利润", "专项储备",
													"少数股东权益" });
		checkMapBHYB.put("负债和股东权益总计", new String[] { "股东权益合计", "负债合计" });
		
		//利润及利润分配表
		checkMapBHYB.put("二、主营业务利润", new String[] { "一、主营业务收入", "- 减：主营业务成本", "- 主营业务税金及附加" });
		checkMapBHYB.put("三、营业利润", new String[] { "二、主营业务利润", "加：其他业务利润", "- 减：营业费用", "- 管理费用",
													"- 财务费用", "- 资产减值损失" });
		checkMapBHYB.put("四、利润总额",
			new String[] { "三、营业利润", "加：投资收益", "补贴收入", "营业外收入", "- 减：营业外支出" });
		checkMapBHYB.put("五、净利润", new String[] { "四、利润总额", "- 减：所得税" });
		checkMapBHYB.put("六、可供分配的利润", new String[] { "五、净利润", "加：年初未分配利润", "其他转入" });
		checkMapBHYB.put("七、可供投资者分配的利润", new String[] { "六、可供分配的利润", "- 减：提取法定盈余公积", "- 提取法定公益金",
														"- 提取职工奖励及福利基金", "- 提取储备基金", "- 提取企业发展基金",
														"- 利润归还投资" });
		checkMapBHYB.put("八、未分配利润", new String[] { "七、可供投资者分配的利润", "- 减：应付优先股股利", "- 提取任意盈余公积",
													"- 应付普通股股利", "- 转作股本的普通股股利" });
		
		//现金流量
		checkMapBHYB.put("现金流入小计(经营)", new String[] { "销售商品、提供劳务收到的现金", "收到的税费返还",
														"收到的其他与经营活动有关的现金" });
		checkMapBHYB.put("现金流出小计(经营)", new String[] { "购买商品、接受劳务支付的现金", "支付给职工以及为职工支付的现金",
														"支付的各项税费", "支付的其他与经营活动有关的现金" });
		checkMapBHYB.put("现金流入小计(投资)", new String[] { "收回投资所收到的现金", "取得投资收益所收到的现金",
														"处置固定资产、无形资产或其他长期资产收回的现金净额",
														"收到的其他与投资活动有关的现金" });
		checkMapBHYB.put("现金流出小计(投资)", new String[] { "购建固定资产、无形资产和其他长期资产支付的现金", "投资所支付的现金",
														"支付的其他与投资活动有关的现金" });
		checkMapBHYB.put("现金流入小计(筹资)", new String[] { "吸收投资所收到的现金", "借款收到的现金", "收到的其他与筹资活动有关的现金" });
		checkMapBHYB.put("现金流出小计(筹资)", new String[] { "偿还债务所支付的现金", "分配股利、利润或偿付利息所支付的现金",
														"支付的其他与筹资活动有关的现金" });
		checkMapBHYB.put("五、现金流量净额", new String[] { "经营活动产生的现金流量净额", "投资活动产生的现金流量净额",
													"筹资活动产生的现金流量净额", "四、汇率变动对现金的影响" });
		checkMapBHYB.put("经营活动产生的现金流量净额", new String[] { "现金流入小计(经营)", "- 现金流出小计(经营)" });
		checkMapBHYB.put("投资活动产生的现金流量净额", new String[] { "现金流入小计(投资)", "- 现金流出小计(投资)" });
		checkMapBHYB.put("筹资活动产生的现金流量净额", new String[] { "现金流入小计(筹资)", "- 现金流出小计(筹资)" });
		
	}
	
	//尽职金融企业模板
	public static Map<String, String[]> checkMapJR = new HashMap<>();
	static {
		checkMapJR.put("非流动资产合计", new String[] { "可供出售金融资产", "持有至到期投资", "长期应收款", "长期股权投资",
												"投资性房地产", "固定资产净额", "在建工程", "工程物资", "固定资产清理",
												"生产性生物资产", "油气资产", "无形资产", "开发支出", "商誉", "长期待摊费用",
												"递延所得税资产", "其他非流动资产" });
		
		checkMapJR.put("资产总计", new String[] { "流动资产合计", "非流动资产合计" });
		checkMapJR.put("流动负债合计", new String[] { "短期借款", "交易性金融负债", "应付票据", "应付账款", "预收款项",
												"应付职工薪酬", "应交税费", "应付利息", "应付股利", "其他应付款",
												"一年内到期的非流动负债", "其他流动负债" });
		checkMapJR.put("非流动负债合计", new String[] { "长期借款", "应付债券", "长期应付款", "专项应付款", "预计负债",
												"递延所得税负债", "其他非流动负债", "其中：特准储备基金" });
		checkMapJR.put("负债合计", new String[] { "流动负债合计", "非流动负债合计" });
		checkMapJR.put("负债和所有者权益总计", new String[] { "所有者权益合计", "负债合计" });
		
		checkMapJR.put("流动资产合计", new String[] { "货币资金", "交易性金融资产", "应收票据", "应收账款", "预付款项", "应收利息",
												"应收股利", "其他应收款", "存货", "一年内到期的非流动资产", "其他流动资产",
												"- 减：贷款损失准备", "待摊费用" });
		checkMapJR.put("固定资产净值", new String[] { "固定资产原价", "- 减：累计折旧" });
		checkMapJR.put("固定资产净额", new String[] { "固定资产净值", "- 减：固定资产减值准备" });
		checkMapJR.put("所有者权益合计", new String[] { "实收资本（股本）", "资本公积", "- 减：库存股", "盈余公积", "一般风险准备",
												"未分配利润", "外币报表折算差额", "少数股东权益" });
		
		checkMapJR.put("二、主营业务利润", new String[] { "一、主营业务收入", "- 减：主营业务成本（融资成本）", "- 主营业务税金及附加" });
		checkMapJR.put("三、营业利润", new String[] { "二、主营业务利润", "加：其他业务利润", "- 减：营业费用", "- 管理费用",
												"- 财务费用", "- 资产减值损失", "加：公允价值变动收益（损失以“-”号填列）",
												"投资收益（损失以“-”号填列）" });
		checkMapJR.put("四、利润总额", new String[] { "三、营业利润", "加：投资收益", "补贴收入", "营业外收入", "- 减：营业外支出" });
		checkMapJR.put("五、净利润", new String[] { "四、利润总额", "- 减：所得税" });
		checkMapJR.put("六、可供分配的利润", new String[] { "五、净利润", "加：年初未分配利润", "其他转入" });
		checkMapJR.put("七、可供投资者分配的利润", new String[] { "六、可供分配的利润", "- 减：提取法定盈余公积", "- 提取法定公益金",
														"- 提取职工奖励及福利基金", "- 提取储备基金", "- 提取企业发展基金",
														"- 利润归还投资" });
		checkMapJR.put("八、未分配利润", new String[] { "七、可供投资者分配的利润", "- 减：应付优先股股利", "- 提取任意盈余公积",
												"- 应付普通股股利", "- 转作股本的普通股股利" });
		
		//现金流量表
		checkMapJR.put("现金流入小计(经营)", new String[] { "销售商品、提供劳务收到的现金", "收取贷款利息收到的现金",
													"收到的其他与经营活动有关的现金" });
		checkMapJR.put("现金流出小计(经营)", new String[] { "购买商品、接受劳务支付的现金", "支付给职工以及为职工支付的现金", "支付的各项税费",
													"支付的其他与经营活动有关的现金" });
		checkMapJR.put("经营活动产生的现金流量净额", new String[] { "现金流入小计(经营)", "- 现金流出小计(经营)" });
		checkMapJR.put("现金流入小计(投资)",
			new String[] { "收回投资所收到的现金", "取得投资收益所收到的现金", "处置固定资产、无形资产或其他长期资产收回的现金净额",
							"收到的其他与投资活动有关的现金" });
		checkMapJR.put("现金流出小计(投资)", new String[] { "购建固定资产、无形资产和其他长期资产支付的现金", "投资所支付的现金",
													"支付的其他与投资活动有关的现金" });
		
		checkMapJR.put("投资活动产生的现金流量净额", new String[] { "现金流入小计(投资)", "- 现金流出小计(投资)" });
		checkMapJR.put("现金流入小计(筹资)", new String[] { "吸收投资所收到的现金", "借款收到的现金", "收到的其他与筹资活动有关的现金" });
		checkMapJR.put("现金流出小计(筹资)", new String[] { "偿还债务所支付的现金", "分配股利、利润或偿付利息所支付的现金",
													"支付的其他与筹资活动有关的现金" });
		checkMapJR.put("筹资活动产生的现金流量净额", new String[] { "现金流入小计(筹资)", "- 现金流出小计(筹资)" });
		checkMapJR.put("五、现金流量净额", new String[] { "经营活动产生的现金流量净额", "投资活动产生的现金流量净额",
													"筹资活动产生的现金流量净额", "四、汇率变动对现金的影响" });
		
	}
	
	//保后金融企业模板
	public static Map<String, String[]> checkMapBHJR = new HashMap<>();
	static {
		//资产负债表
		checkMapBHJR.put("非流动资产合计", new String[] { "可供出售金融资产", "持有至到期投资", "长期应收款", "长期股权投资",
													"投资性房地产", "固定资产净额", "在建工程", "工程物资", "固定资产清理",
													"生产性生物资产", "油气资产", "无形资产", "开发支出", "商誉",
													"长期待摊费用", "递延所得税资产", "其他非流动资产" });
		checkMapBHJR.put("资产总计", new String[] { "非流动资产合计", "流动资产合计" });
		
		checkMapBHJR.put("流动负债合计", new String[] { "短期借款", "交易性金融负债", "应付票据", "应付账款", "预收款项",
													"应付职工薪酬", "应交税费", "应付利息", "应付股利", "其他应付款",
													"一年内到期的非流动负债", "其他流动负债" });
		checkMapBHJR.put("非流动负债合计", new String[] { "长期借款", "应付债券", "长期应付款", "专项应付款", "预计负债",
													"递延所得税负债", "其他非流动负债", "其中：特准储备基金" });
		checkMapBHJR.put("负债合计", new String[] { "非流动负债合计", "流动负债合计" });
		checkMapBHJR.put("所有者权益合计", new String[] { "实收资本（股本）", "资本公积", "- 减：库存股", "盈余公积", "一般风险准备",
													"未分配利润", "外币报表折算差额", "少数股东权益" });
		checkMapBHJR.put("负债和所有者权益总计", new String[] { "所有者权益合计", "负债合计" });
		//现金流量
		checkMapBHJR.put("现金流入小计(经营)", new String[] { "销售商品、提供劳务收到的现金", "收取贷款利息收到的现金",
														"收到的其他与经营活动有关的现金" });
		checkMapBHJR.put("现金流出小计(经营)", new String[] { "购买商品、接受劳务支付的现金", "支付给职工以及为职工支付的现金",
														"支付的各项税费", "支付的其他与经营活动有关的现金" });
		checkMapBHJR.put("现金流入小计(投资)", new String[] { "收回投资所收到的现金", "取得投资收益所收到的现金",
														"处置固定资产、无形资产或其他长期资产收回的现金净额",
														"收到的其他与投资活动有关的现金" });
		checkMapBHJR.put("现金流出小计(投资)", new String[] { "支付的其他与投资活动有关的现金", "投资所支付的现金",
														"购建固定资产、无形资产和其他长期资产支付的现金" });
		checkMapBHJR.put("现金流入小计(筹资)", new String[] { "吸收投资所收到的现金", "借款收到的现金", "收到的其他与筹资活动有关的现金" });
		checkMapBHJR.put("现金流出小计(筹资)", new String[] { "偿还债务所支付的现金", "分配股利、利润或偿付利息所支付的现金",
														"支付的其他与筹资活动有关的现金" });
		checkMapBHJR.put("五、现金流量净额", new String[] { "经营活动产生的现金流量净额", "投资活动产生的现金流量净额",
													"筹资活动产生的现金流量净额", "四、汇率变动对现金的影响" });
		checkMapBHJR.put("流动资产合计", new String[] { "货币资金", "交易性金融资产", "应收票据", "应收账款", "预付款项",
													"应收利息", "应收股利", "其他应收款", "存货", "一年内到期的非流动资产",
													"其他流动资产", "- 减：贷款损失准备", "待摊费用" });
		checkMapBHJR.put("固定资产净额", new String[] { "固定资产净值", "- 减：固定资产减值准备" });
		checkMapBHJR.put("所有者权益合计", new String[] { "实收资本（股本）", "资本公积", "- 减：库存股", "盈余公积", "一般风险准备",
													"未分配利润", "外币报表折算差额", "少数股东权益" });
		checkMapBHJR
			.put("二、主营业务利润", new String[] { "一、主营业务收入", "- 减：主营业务成本（融资成本）", "- 主营业务税金及附加" });
		checkMapBHJR.put("三、营业利润", new String[] { "二、主营业务利润", "加：其他业务利润", "- 减：营业费用", "- 管理费用",
													"- 财务费用", "- 资产减值损失", "加：公允价值变动收益（损失以“-”号填列）",
													"投资收益（损失以“-”号填列）" });
		checkMapBHJR.put("四、利润总额",
			new String[] { "三、营业利润", "加：投资收益", "补贴收入", "营业外收入", "- 减：营业外支出" });
		checkMapBHJR.put("五、净利润", new String[] { "四、利润总额", "- 减：所得税" });
		checkMapBHJR.put("六、可供分配的利润", new String[] { "五、净利润", "加：年初未分配利润", "其他转入" });
		checkMapBHJR.put("七、可供投资者分配的利润", new String[] { "六、可供分配的利润", "- 减：提取法定盈余公积", "- 提取法定公益金",
														"- 提取职工奖励及福利基金", "- 提取储备基金", "- 提取企业发展基金",
														"- 利润归还投资" });
		checkMapBHJR.put("八、未分配利润", new String[] { "七、可供投资者分配的利润", "- 减：应付优先股股利", "- 提取任意盈余公积",
													"- 应付普通股股利", "- 转作股本的普通股股利" });
		
		checkMapBHJR.put("经营活动产生的现金流量净额", new String[] { "现金流入小计(经营)", "- 现金流出小计(经营)" });
		checkMapBHJR.put("投资活动产生的现金流量净额", new String[] { "现金流入小计(投资)", "- 现金流出小计(投资)" });
		checkMapBHJR.put("筹资活动产生的现金流量净额", new String[] { "现金流入小计(筹资)", "- 现金流出小计(筹资)" });
		
	}
	
}
