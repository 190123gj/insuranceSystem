package com.born.insurance.ws.order.base;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.Assert;

import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.Order;
import com.yjf.common.service.OrderBase;

public class ValidateOrderBase extends OrderBase {
	
	private static final long serialVersionUID = 1L;
	protected static final Money ZERO = Money.zero();
	
	protected void validateHasText(String validateField, String fieldDes) {
		Assert.hasText(validateField, fieldDes + "不能为空!");
	}
	
	protected void validateHasZore(long vlaue, String fieldDes) {
		Assert.isTrue(vlaue > 0, fieldDes + "不能为空!");
	}
	
	protected void validateNotNull(Object validateField, String fieldDes) {
		Assert.notNull(validateField, fieldDes + "不能为空!");
	}
	
	protected void validateGreaterThan(Number validateField, String fieldDes) {
		Assert.isTrue(validateField.doubleValue() > 0, fieldDes + "必须大于零!");
	}
	
	protected void validateGreaterThanEqualZero(Number validateField, String fieldDes) {
		Assert.isTrue(validateField.doubleValue() >= 0, fieldDes + "必须大于等于零!");
	}
	
	protected void validateMoneyGreaterThanZero(Money validateField, String fieldDes) {
		Assert.notNull(validateField, fieldDes + "不能为空!");
		Assert.isTrue(validateField.greaterThan(ZERO), fieldDes + "必须大于零!");
	}
	
	protected void validateNonNegative(Number validateField, String fieldDes) {
		Assert.isTrue(validateField.doubleValue() >= 0, fieldDes + "必须大于等于零!");
	}
	
	/**
	 * 
	 * @param validateField
	 * @param fieldDes
	 * @param maxLength 数据库中varchar字段长度
	 */
	protected void validateTextLength(String validateField, String fieldDes, int maxLength) {
		if (validateField != null) {
			Assert.isTrue(validateField.length() < maxLength, fieldDes + "长度超过最大限制（" + maxLength
																+ "）个字符!");
		}
		
	}
	
	@Override
	public boolean isCheckGid() {
		return false;
	}
	
	/**
	 * 
	 * 对于集合类型的参数进行check
	 * 
	 * @param orders
	 */
	protected void checkOrders(Collection<? extends ValidateOrderBase> orders) {
		if (null != orders && orders.size() > 0) {
			for (Order order : orders) {
				order.check();
			}
		}
	}
	
	protected boolean isNull(String str) {
		return (null == str || "".equals(str));
	}
	
	protected boolean isNull(Integer i) {
		return (null == i);
	}
	
	protected boolean isNull(Long l) {
		return (null == l);
	}
	
	protected boolean isNull(Date date) {
		return (null == date);
	}
	
	protected boolean isNull(Double d) {
		return (null == d);
	}
	
	protected boolean isNull(Money m) {
		return (null == m || m.equals(Money.zero()));
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
