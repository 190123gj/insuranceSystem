package com.born.insurance.ws.order.insuranceConditions;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceConditionsQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 条件
	*/	
	private String businessConditions;
 	/**
	* id
	*/	
	private long liabilityId;
 	/**
	* 名称
	*/	
	private String name;
 	/**
	* 所属分类id或者所属险种id
	*/	
	private long ownerId;
 	/**
	* 模板
	*/	
	private String priceTemplate;
 	/**
	* catalog :分类，type:险种
	*/	
	private String type;
 
  	public String getBusinessConditions() {
        return businessConditions;
	}

	public void setBusinessConditions(String businessConditions) {
        this.businessConditions = businessConditions;
	}
	public long getLiabilityId() {
        return liabilityId;
	}

	public void setLiabilityId(long liabilityId) {
        this.liabilityId = liabilityId;
	}
	public String getName() {
        return name;
	}

	public void setName(String name) {
        this.name = name;
	}
	public long getOwnerId() {
        return ownerId;
	}

	public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
	}
	public String getPriceTemplate() {
        return priceTemplate;
	}

	public void setPriceTemplate(String priceTemplate) {
        this.priceTemplate = priceTemplate;
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