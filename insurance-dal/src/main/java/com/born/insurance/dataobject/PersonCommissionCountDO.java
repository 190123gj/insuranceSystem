package com.born.insurance.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PersonCommissionCountDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8126781148056814443L;
	
	/**
	 * 佣金收入金额
	 */
	private Double inComeSum;
	
	/**
	 * 佣金提取金额
	 */
	private Double extractTotalSum;
	
	/**
	 * 上期结转
	 */
	private Double lastTotal;

	public Double getInComeSum() {
		return inComeSum;
	}

	public void setInComeSum(Double inComeSum) {
		this.inComeSum = inComeSum;
	}

	public Double getExtractTotalSum() {
		return extractTotalSum;
	}

	public void setExtractTotalSum(Double extractTotalSum) {
		this.extractTotalSum = extractTotalSum;
	}

	public Double getLastTotal() {
		return lastTotal;
	}

	public void setLastTotal(Double lastTotal) {
		this.lastTotal = lastTotal;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
