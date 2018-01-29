package com.born.insurance.ws.order.insuranceProtocolInfo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;

public class InsuranceProtocolInfoOrder extends ProcessOrder {
	private String chargeInfo;
	
	private long protocolInfoId;
	
	private String catalogName;
	
	private long catalogId;
	
	private String productName;
	
	private long productId;
	
	private long protocolId;

	private String firstPeriod;

	public String getChargeInfo() {
		return chargeInfo;
	}

	public void setChargeInfo(String chargeInfo) {
		this.chargeInfo = chargeInfo;
	}

	public long getProtocolInfoId() {
		return protocolInfoId;
	}

	public void setProtocolInfoId(long protocolInfoId) {
		this.protocolInfoId = protocolInfoId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(long catalogId) {
		this.catalogId = catalogId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(long protocolId) {
		this.protocolId = protocolId;
	}

	public String getFirstPeriod() {
		return firstPeriod;
	}

	public void setFirstPeriod(String firstPeriod) {
		this.firstPeriod = firstPeriod;
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