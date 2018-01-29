package com.born.insurance.ws.info.businessBill;

import com.yjf.common.lang.util.money.Money;

public class CalcBrokeAndCommissionInfo {
	
	/**
	 * 每年寿险、附加险保费之和
	 */
	private Money amount;
	
	private Money yiciBrokeFee;
	
	private Money yiciCommissionInfoMoney;
	
	private Money attachBrokeFee;
	
	private Money attachCommissionInfo;
	
	private Money currentTotalAttachAmount;

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public Money getYiciBrokeFee() {
		return yiciBrokeFee;
	}

	public void setYiciBrokeFee(Money yiciBrokeFee) {
		this.yiciBrokeFee = yiciBrokeFee;
	}

	public Money getYiciCommissionInfoMoney() {
		return yiciCommissionInfoMoney;
	}

	public void setYiciCommissionInfoMoney(Money yiciCommissionInfoMoney) {
		this.yiciCommissionInfoMoney = yiciCommissionInfoMoney;
	}

	public Money getAttachBrokeFee() {
		return attachBrokeFee;
	}

	public void setAttachBrokeFee(Money attachBrokeFee) {
		this.attachBrokeFee = attachBrokeFee;
	}

	public Money getAttachCommissionInfo() {
		return attachCommissionInfo;
	}

	public void setAttachCommissionInfo(Money attachCommissionInfo) {
		this.attachCommissionInfo = attachCommissionInfo;
	}

	public Money getCurrentTotalAttachAmount() {
		return currentTotalAttachAmount;
	}

	public void setCurrentTotalAttachAmount(Money currentTotalAttachAmount) {
		this.currentTotalAttachAmount = currentTotalAttachAmount;
	}
	
	

}
