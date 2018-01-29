package com.born.insurance.ws.info.common;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

public class BaseToStringInfo implements Serializable {
	
	private static final long serialVersionUID = -290471646591360603L;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/////// 为合同打印添加momey的万元转换方法，雷同com.born.fcs.pm.util.MoneyUtil
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
}
