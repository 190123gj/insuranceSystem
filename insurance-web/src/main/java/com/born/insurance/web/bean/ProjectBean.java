package com.born.insurance.web.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectBean implements Serializable {
	
	private static final long serialVersionUID = -4331782852566937069L;
	String capitalSubChannelName;
	String guaranteeRate;
	double dblGuaranteeRate;
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public String getGuaranteeRate() {
		return this.guaranteeRate;
	}
	
	public void setGuaranteeRate(String guaranteeRate) {
		this.guaranteeRate = guaranteeRate;
	}
	
	public double getDblGuaranteeRate() {
		return this.dblGuaranteeRate;
	}
	
	public void setDblGuaranteeRate(double dblGuaranteeRate) {
		this.dblGuaranteeRate = dblGuaranteeRate;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
