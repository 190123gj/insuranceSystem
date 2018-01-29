package com.born.insurance.ws.info.insuranceConditions;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class InsuranceConditionsInfo extends BaseToStringInfo {

	private long conditionId;

	private String businessConditions;

	private long priceTemplate;

	private String priceTemplateName;

	private long ownerId;

	private String type;

	public long getConditionId() {
		return conditionId;
	}

	public void setConditionId(long conditionId) {
		this.conditionId = conditionId;
	}

	public String getBusinessConditions() {
		return businessConditions;
	}

	public void setBusinessConditions(String businessConditions) {
		this.businessConditions = businessConditions;
	}

	public long getPriceTemplate() {
		return priceTemplate;
	}

	public void setPriceTemplate(long priceTemplate) {
		this.priceTemplate = priceTemplate;
	}

	public String getPriceTemplateName() {
		return priceTemplateName;
	}

	public void setPriceTemplateName(String priceTemplateName) {
		this.priceTemplateName = priceTemplateName;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	
}	