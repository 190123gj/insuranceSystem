package com.born.insurance.util;

import java.math.BigDecimal;

/**
 * 
 * 
 * @Filename LoanUtil.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author yhl
 * 
 * @Email yhailong@yiji.com
 * 
 * @History <li>Author: yhl</li> <li>Date: 2013-7-1</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class LoanUtil {
	/**
	 * 天
	 */
	public static final String LOAN_TIME_UNIT_DAY = "D";
	/**
	 * 月
	 */
	public static final String LOAN_TIME_UNIT_MONTH = "M";
	/**
	 * 固定期限
	 */
	public static final String LOAN_TIME_UNIT_PEIRIOD = "W";
	/**
	 * 年
	 */
	public static final String LOAN_TIME_UNIT_YEAR = "Y";
	
	/**
	 * 构造借款时间
	 * @param n 数量
	 * @param unit 计量单位
	 * @return
	 */
	public static String getLoanTime(int n, String unit) {
		if (unit.equals(LOAN_TIME_UNIT_DAY)) {
			return n + "天";
		} else if (unit.equals(LOAN_TIME_UNIT_MONTH)) {
			return n + "个月";
		} else if (unit.equals(LOAN_TIME_UNIT_PEIRIOD)) {
			return n + "月期";
		} else {
			return n + "年";
		}
	}
	
	public static String getRate(String value) {
		BigDecimal bg = new BigDecimal(Float.parseFloat(value) * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d + "%";
	}
	
	public static String getRate_1(String value) {
		BigDecimal bg = new BigDecimal(Float.parseFloat(value) * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}
	
}
