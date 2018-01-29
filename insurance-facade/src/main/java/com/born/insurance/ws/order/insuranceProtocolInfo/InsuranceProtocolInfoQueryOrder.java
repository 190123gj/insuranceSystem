package com.born.insurance.ws.order.insuranceProtocolInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceProtocolInfoQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 协议id
	*/	
	private long protocolId;
 	/**
	* id
	*/	
	private long protocolInfoId;
 	/**
	* 险种id
	*/	
	private long typeId;
 	/**
	* 分类id
	*/	
	private long catalogId;

	private String type ;

	private boolean isQueryChargeInfo =true;
 
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
	public long getTypeId() {
        return typeId;
	}

	public void setTypeId(long typeId) {
        this.typeId = typeId;
	}
	public long getCatalogId() {
        return catalogId;
	}

	public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isQueryChargeInfo() {
		return isQueryChargeInfo;
	}

	public void setQueryChargeInfo(boolean queryChargeInfo) {
		isQueryChargeInfo = queryChargeInfo;
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