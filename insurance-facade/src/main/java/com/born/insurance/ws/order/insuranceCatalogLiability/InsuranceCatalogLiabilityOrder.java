package com.born.insurance.ws.order.insuranceCatalogLiability;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;

public class InsuranceCatalogLiabilityOrder extends ProcessOrder {

	private long id;

	private long liabilityId;

	private String liabilityName;

	private long ownerId;

	private String type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLiabilityId() {
		return liabilityId;
	}

	public void setLiabilityId(long liabilityId) {
		this.liabilityId = liabilityId;
	}

	public String getLiabilityName() {
		return liabilityName;
	}

	public void setLiabilityName(String liabilityName) {
		this.liabilityName = liabilityName;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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