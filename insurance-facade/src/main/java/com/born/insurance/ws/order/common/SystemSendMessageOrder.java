package com.born.insurance.ws.order.common;

import com.born.insurance.ws.enums.SysSendMessageSubTypeEnum;
import com.born.insurance.ws.enums.SysSendMessageTypeEnum;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * @Filename MessageController.java
 *
 * @Description 系统自动发送站内消息order
 *
 * @Version 1.0
 *
 * @Author sxiaomeng
 *
 * @Email sxm@yiji.com
 *
 * @History <li>Author: sxiaomeng</li> <li>Date: 2015-3-12</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 *
 */
public class SystemSendMessageOrder {
	
	/**
	 * 类型
	 */
	private SysSendMessageTypeEnum type;
	
	/**
	 * 子类型 a/b/c/d
	 */
	private SysSendMessageSubTypeEnum subType;
	
	/**
	 * 用户id
	 */
	private long userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 金额
	 */
	private Money amount;
	
	/**
	 * 积分
	 */
	private long points;
	
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 项目名称
	 */
	private String loanName;
	
	/**
	 * 加息卷利率
	 */
	private double rateOfYear;
	
	public String getLoanName() {
		return loanName;
	}
	
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	
	public SysSendMessageTypeEnum getType() {
		return type;
	}
	
	public void setType(SysSendMessageTypeEnum type) {
		this.type = type;
	}
	
	public SysSendMessageSubTypeEnum getSubType() {
		return subType;
	}
	
	public void setSubType(SysSendMessageSubTypeEnum subType) {
		this.subType = subType;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public long getPoints() {
		return points;
	}
	
	public void setPoints(long points) {
		this.points = points;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public double getRateOfYear() {
		return this.rateOfYear;
	}
	
	public void setRateOfYear(double rateOfYear) {
		this.rateOfYear = rateOfYear;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemSendMessageOrder [type=");
		builder.append(type);
		builder.append(", subType=");
		builder.append(subType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", points=");
		builder.append(points);
		builder.append(", rateOfYear=");
		builder.append(rateOfYear);
		builder.append(", date=");
		builder.append(date);
		builder.append(", loanName=");
		builder.append(loanName);
		builder.append("]");
		return builder.toString();
	}
}
