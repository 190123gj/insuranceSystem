package com.born.insurance.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @Filename MoneyUtil.java
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
public class MoneyUtil {
	//金额显示格式
	private static final String FORMATTEXT = "###,##0";
	//显示精度
	private static int PRECISION = 2;
	//显示零
	private static String ZERO = "0.00";
	
	public static String getMoney(long m) {
		return new Money((m / 100), (int) (m % 100)).toString();
	}
	
	public static Money getMoneyByLong(long m) {
		Money money = new Money();
		money.setCent(m);
		return money;
	}
	
	/**
	 * 还需N万， 求N
	 * @param amount
	 * @param loanedamount
	 * @return
	 */
	
	public static double getMoneyByw(Money amount, Money loanedamount) {
		if (amount == null || loanedamount == null)
			return 0;
		return amount.subtract(loanedamount).getAmount().longValue() / 10000.00;
	}
	
	/**
	 * 还需N， 求N
	 * @param amount
	 * @param loanedamount
	 * @return
	 */
	
	public static Money getNeedMoney(Money amount, Money loanedamount) {
		return amount.subtract(loanedamount);
	}
	
	/**
	 * wan
	 * @param amount
	 * @return
	 */
	public static double getMoneyByw(Money amount) {
		if (amount == null)
			return 0;
		return (amount.getCent() / 1000000.00);
	}
	
	/**
	 * 
	 * 单位：万，保留两位小数
	 * 
	 * @param amount
	 * @return
	 */
	public static String getMoneyByw2(Money amount) {
		if (amount == null)
			return "0";
		double d = amount.getCent() / 1000000.00d;
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(d);
	}
	
	/**
	 * 
	 * 单位：亿，保留两位小数
	 * 
	 * @param amount
	 * @return
	 */
	public static String getMoneyByy2(Money amount) {
		if (amount == null)
			return "0";
		double d = amount.getCent() / 10000000000.00d;
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(d);
	}
	
	/**
	 * 不要小数
	 * @param amount
	 * @return
	 */
	public static int getMoneyInt(Money amount) {
		if (amount == null)
			return 0;
		return (int) (amount.getCent() / 100);
	}
	
	/**
	 * 字符串转为long
	 * @param s
	 * @return
	 */
	public static long parseLong(String s) {
		return Long.parseLong(s);
	}
	
	/**
	 * 计算百分百
	 * @param molecular 分子
	 * @param denominator 分母
	 * @return
	 */
	public static String getPercentage(Money molecular, Money denominator, Money leastAmount) {
		//		if((denominator - molecular) < leastAmount){
		//			return "100%";
		//		}
		if (molecular == null || denominator == null || leastAmount == null)
			return "";
		if (denominator.getCent() == 0) {
			return "0.00%";
		}
		BigDecimal percent = molecular.getAmount()
			.divide(denominator.getAmount(), 4, BigDecimal.ROUND_HALF_EVEN)
			.multiply(new BigDecimal(100));
		
		float f = percent.setScale(PRECISION, BigDecimal.ROUND_HALF_UP).floatValue();
		return f + "%";
		
	}
	
	/**
	 * 计算
	 * @param money1
	 * @param money2
	 * @return
	 */
	public static Money addMoneyByStr(String money1, String money2) {
		Money a = new Money(money1);
		Money b = new Money(money2);
		return a.add(b);
	}
	
	/**
	 * 计算进度
	 * @param molecular 分子
	 * @param denominator 分母
	 * @return
	 */
	public static float getPercentageNew(Money molecular, Money denominator) {
		//		if((denominator - molecular) < leastAmount){
		//			return "100%";
		//		}
		if (molecular == null || denominator == null)
			return 0;
		if (denominator.getCent() == 0) {
			return 0;
		}
		BigDecimal percent = molecular.getAmount().divide(denominator.getAmount(), 4,
			BigDecimal.ROUND_HALF_EVEN);
		float f = percent.setScale(PRECISION, BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}
	
	public static int getPercentageToInt(float percentage) {
		int f = (int) percentage;
		return f;
	}
	
	/**
	 * 格式化金额 input long
	 */
	
	public static String getFormatAmount(long formatAmount) {
		double fromAmount = formatAmount;
		double fenAmount = new BigDecimal(fromAmount / 100).setScale(PRECISION,
			BigDecimal.ROUND_HALF_UP).doubleValue();
		String amount = String.valueOf(fenAmount);
		if (amount == null || amount.length() < 1) {
			return ZERO;
		}
		NumberFormat formater = null;
		double number = Double.parseDouble(amount);
		if (PRECISION > 0) {
			StringBuffer buff = new StringBuffer();
			buff.append(FORMATTEXT).append(".");
			for (int i = 0; i < PRECISION; i++) {
				buff.append("0");
			}
			formater = new DecimalFormat(buff.toString());
		} else {
			formater = new DecimalFormat(FORMATTEXT);
		}
		return formater.format(number);
	}
	
	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String digitUppercase(long formatAmount) {
		double fromAmount = formatAmount;
		double n = new BigDecimal(fromAmount / 100).setScale(PRECISION, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };
		String head = n < 0 ? "负" : "";
		n = Math.abs(n);
		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
				.replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);
		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零")
					.replaceAll("^整$", "零元整");
	}
	
	/**
	 * 获取年华利率
	 * @param value
	 * @return
	 */
	public static double getPercentage(double value) {
		BigDecimal bg = new BigDecimal(value * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}
	
	/**
	 * 将格式化后的金额转成double类型
	 * @param value
	 * @return
	 */
	public static double money2Double(String value) {
		if (StringUtil.isEmpty(value))
			return 0;
		String str = value.replaceAll(",", "");
		return Double.parseDouble(str);
	}
	
	public static Money getJCBMoney(String lValue, String m1, String m2) {
		double l = Double.parseDouble(lValue);
		Money a = new Money(m1);
		Money b = new Money(m2);
		Money money = a.multiply(l);
		return money.add(b);
	}
	
	/**
	 * 获取money对象
	 * @param str
	 * @return
	 */
	public static Money getMoney(String str) {
		if (StringUtil.isBlank(str)) {
			return new Money(0L);
		}
		return Money.amout(str);
	}
	
	/**
	 * 获取money对象的万元展示，当不为money对象时输出原值
	 * @param str
	 * @return
	 */
	public static String getMoneyStrW(String str) {
		if (StringUtil.isBlank(str)) {
			return new Money(0L).toStandardString();
		}
		try {
			return Money.amout(str).toStandardString();
		} catch (Exception e) {
			return str;
		}
	}
	
	/**
	 * 获取万元的money对象
	 * @param str
	 * @return
	 */
	public static Money getMoneyW(String str) {
		if (StringUtil.isBlank(str)) {
			return new Money(0L);
		}
		return Money.amout(str).multiply(10000);
	}
	
	/**
	 * 获取亿元的money对象
	 * @param str
	 * @return
	 */
	public static Money getMoneyY(String str) {
		if (StringUtil.isBlank(str)) {
			return new Money(0L);
		}
		return Money.amout(str).multiply(100000000);
	}
	
	/**
	 * 获取money对象
	 * @param str
	 * @return
	 */
	public static Money getMoney(double yuan) {
		return Money.amout(String.valueOf(yuan));
	}
	
	/**
	 * 获取万元的money对象
	 * @param str
	 * @return
	 */
	public static Money getMoneyW(double wyuan) {
		return Money.amout(String.valueOf(wyuan)).multiply(10000);
	}
	
	/**
	 * 获取亿元的money对象
	 * @param str
	 * @return
	 */
	public static Money getMoneyY(double yyuan) {
		return Money.amout(String.valueOf(yyuan)).multiply(100000000);
	}
	
	/**
	 * 格式化金额为1,000,001.65的形式
	 * @param money
	 * @return
	 */
	public static String format(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("金额不能为null");
		}
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,###.00");
		String result = fmt.format(money.getAmount().doubleValue());
		if (result.indexOf(".") == 0) {
			result = "0" + result;
		}
		return result;
	}
	
	/**
	 * 格式化金额 W
	 * @param money
	 * @return
	 */
	public static String formatW(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("金额不能为null");
		}
		return format(money.divide(10000));
	}
	
	/**
	 * 格式化金额Y亿元
	 * @param money
	 * @return
	 */
	public static String formatY(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("金额不能为null");
		}
		return format(money.divide(100000000d));
	}
	
	/**
	 * 格式化金额为1,000,001.65的形式
	 * @param money
	 * @return
	 */
	public static String formatWithUnit(Money money) {
		return format(money) + "元";
	}
	
	/**
	 * 格式化金额 W
	 * @param money
	 * @return
	 */
	public static String formatWWithUnit(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("金额不能为null");
		}
		return format(money.divide(10000)) + "万元";
	}
	
	/**
	 * 格式化金额Y亿元
	 * @param money
	 * @return
	 */
	public static String formatYWithUnit(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("金额不能为null");
		}
		return format(money.divide(100000000d)) + "亿元";
	}
	
}
