package com.born.insurance.ws.info.insuranceProtocolInfo;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class InsuranceProtocolInfoInfo extends BaseToStringInfo {
				
 	/**
	* 协议id
	*/	
	private long protocolId;
 	/**
	* id
	*/	
	private long protocolInfoId;

	private long productId;

	private String productName;
 	/**
	* 分类id
	*/	
	private long catalogId;

	private String catalogName;

	private String chargeInfo;

	private String protocolUserName;

	private String contractingAgencyName;

	private String firstPeriod;


  	public long getProtocolId() {
        return protocolId;
	}

	public void setProtocolId(long protocolId) {
        this.protocolId = protocolId;
	}
	public long getProtocolInfoId() {
        return protocolInfoId;
	}

	public void setProtocolInfoId(long protocolInfoId) {
        this.protocolInfoId = protocolInfoId;
	}

	public long getCatalogId() {
        return catalogId;
	}

	public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
	}

	public String getChargeInfo() {
		return chargeInfo;
	}

	public void setChargeInfo(String chargeInfo) {
		this.chargeInfo = chargeInfo;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getContractingAgencyName() {
		return contractingAgencyName;
	}

	public void setContractingAgencyName(String contractingAgencyName) {
		this.contractingAgencyName = contractingAgencyName;
	}

	public String getProtocolUserName() {
		return protocolUserName;
	}

	public void setProtocolUserName(String protocolUserName) {
		this.protocolUserName = protocolUserName;
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