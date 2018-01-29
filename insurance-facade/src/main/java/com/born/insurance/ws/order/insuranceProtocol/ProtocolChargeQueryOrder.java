package com.born.insurance.ws.order.insuranceProtocol;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;

public class ProtocolChargeQueryOrder extends QueryPermissionPageBase {

	private String companyUserIds;
	private String catalogIds;

	public String getCatalogIds() {
		return catalogIds;
	}

	public void setCatalogIds(String catalogIds) {
		this.catalogIds = catalogIds;
	}

	public String getCompanyUserIds() {
		return companyUserIds;
	}

	public void setCompanyUserIds(String companyUserIds) {
		this.companyUserIds = companyUserIds;
	}

	/**
     * @return
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	
}	