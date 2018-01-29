package com.born.insurance.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class RateUtil {
	public static String getRate(double value) {
		BigDecimal bg = new BigDecimal(value * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d + "%";
	}
	
	
	public static String getRateOnly(double value){
		BigDecimal bg = new BigDecimal(value * 100);
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d+"";
	}
	
	public static String formatRate(Double value) {
		if (null == value) {
			return "";
		}
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(value);
	}
	
	public static String formatRateStr(String value) {
		if (null == value || "".equals(value.trim())) {
			return "";
		}
		try {
			Double d = Double.parseDouble(value);
			DecimalFormat format = new DecimalFormat("#0.00");
			return format.format(d);
		} catch (Exception e) {
			return "";
		}
	}
}
