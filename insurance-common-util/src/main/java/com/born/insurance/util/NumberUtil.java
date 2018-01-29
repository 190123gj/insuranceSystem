package com.born.insurance.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class NumberUtil {
	static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);
	
	static final String EMPTY = "";
	
	/**
	 * 格式化数字
	 * @param num
	 * @param format
	 * @return
	 */
	public static String format(Number num, String format) {
		DecimalFormat nf = new DecimalFormat(format);
		return nf.format(num);
	}
	
	/**
	 * 格式化数字
	 * @param num
	 * @param format
	 * @param zero2Null
	 * @return
	 */
	public static String format(Number num, String format, boolean zero2Empty) {
		if (zero2Empty && num.doubleValue() == 0)
			return EMPTY;
		DecimalFormat nf = new DecimalFormat(format);
		return nf.format(num);
	}
	
	public static int parseInt(String strInt) {
		return (int) parseLong(strInt, 0);
	}
	
	public static long parseLong(String strLong, long defaultValue) {
		if (StringUtil.isEmpty(strLong))
			return defaultValue;
		try {
			return Long.parseLong(strLong);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("parseLong error", e);
		}
		return defaultValue;
	}
	
	public static double parseDouble(String strDouble, double defaultValue) {
		if (StringUtil.isEmpty(strDouble))
			return defaultValue;
		try {
			return Double.parseDouble(strDouble);
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("parseLong error", e);
		}
		return defaultValue;
	}
	
	public static double parseDouble(String strDouble) {
		return parseDouble(strDouble, 0.0);
	}
	
	public static long parseLong(String strLong) {
		return parseLong(strLong, 0);
	}
	
	public static String formatMoney(double num) {
		double tempNum = num / 100.0;
		DecimalFormat nf = new DecimalFormat("###,###,##0.00");
		return nf.format(tempNum);
	}
	
	public static String parseMoneyToString(String strMoney) {
		double tempNum = parseDouble(strMoney) * 100;
		long value = new Double(tempNum).longValue();
		return String.valueOf(value);
	}
	
	public static long parseMoneyToLong(String strMoney) {
		double tempNum = parseDouble(strMoney) * 100;
		long value = new Double(tempNum).longValue();
		return value;
	}
	
	/**
	 * 如果有小数，保留一位小数；没有小数，只显示整数部分
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d) {
		if (d % 1.0d == 0) {
			long dl = (long) d;
			//			if (dl == 0) {
			//				return "";
			//			}
			return String.valueOf(dl);
		} else {
			return format(d, "#0.0");
		}
	}
	
	/**
	 * 如果有小数，保留一位小数；没有小数，只显示整数部分
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDouble2(double d) {
		if (d % 1.0d == 0) {
			long dl = (long) d;
			//			if (dl == 0) {
			//				return "";
			//			}
			return String.valueOf(dl);
		} else {
			return format(d, "#0.00");
		}
	}
	
	/***
	 * double 浮点数运算
	 * @param val1
	 * @param val2
	 * @param operator 运算符 + - * /
	 * @return
	 */
	public static double doubleCaculate(double val1, double val2, String operator) {
		BigDecimal dval1 = new BigDecimal(Double.toString(val1));
		BigDecimal dval2 = new BigDecimal(Double.toString(val2));
		if ("+".equals(operator)) {
			return dval1.add(dval2).doubleValue();
		} else if ("-".equals(operator)) {
			return dval1.subtract(dval2).doubleValue();
		} else if ("*".equals(operator)) {
			return dval1.multiply(dval2).doubleValue();
		} else if ("/".equals(operator)) {
			return dval1.divide(dval2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		} else {
			throw new IllegalArgumentException("运算符不正确");
		}
	}
	
	/**
	 * 将阿拉伯数字转换成中文
	 *
	 * @param d
	 * @return
	 */
	public static String parseNumToChinese(long d) {
		String[] str = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String ss[] = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿" };
		String s = String.valueOf(d);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			String index = String.valueOf(s.charAt(i));
			sb = sb.append(str[Integer.parseInt(index)]);
		}
		String sss = String.valueOf(sb);
		int i = 0;
		for (int j = sss.length(); j > 0; j--) {
			sb = sb.insert(j, ss[i++]);
		}
		String chi = sb.toString();
		if (chi.endsWith("零"))
			chi = chi.substring(0, chi.length() - 1);
		return chi;
	}
	
	public static void main(String[] args) {
		String s = formatDouble(0.0);
		System.out.println(s);
		
	}
}
