package com.born.insurance.ws.order.priceContactLetterScheme;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PriceContactLetterSchemeQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 标id
	*/	
	private long letterDemandId;
 	/**
	* id
	*/	
	private long id;
 	/**
	* 索赔形式
	*/	
	private String claimForm;
 	/**
	* 特别要求
	*/	
	private String specialReq;
 	/**
	* 询价期限
	*/	
	private Date askDate;
 	/**
	* 事故发生制
	*/	
	private String accidentSystem;
 	/**
	* 追溯期
	*/	
	private String retrospectivePeriod;
 	/**
	* 发现期
	*/	
	private String discoveryPeriod;
 	/**
	* 事故索赔制
	*/	
	private String accidentClaimSystem;
 	/**
	* 扩展字段
	*/	
	private String ext;
 	/**
	* 版本
	*/	
	private long version;
 
  	public long getLetterDemandId() {
        return letterDemandId;
	}

	public void setLetterDemandId(long letterDemandId) {
        this.letterDemandId = letterDemandId;
	}
	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public String getClaimForm() {
        return claimForm;
	}

	public void setClaimForm(String claimForm) {
        this.claimForm = claimForm;
	}
	public String getSpecialReq() {
        return specialReq;
	}

	public void setSpecialReq(String specialReq) {
        this.specialReq = specialReq;
	}
	public Date getAskDate() {
        return askDate;
	}

	public void setAskDate(Date askDate) {
        this.askDate = askDate;
	}
	public String getAccidentSystem() {
        return accidentSystem;
	}

	public void setAccidentSystem(String accidentSystem) {
        this.accidentSystem = accidentSystem;
	}
	public String getRetrospectivePeriod() {
        return retrospectivePeriod;
	}

	public void setRetrospectivePeriod(String retrospectivePeriod) {
        this.retrospectivePeriod = retrospectivePeriod;
	}
	public String getDiscoveryPeriod() {
        return discoveryPeriod;
	}

	public void setDiscoveryPeriod(String discoveryPeriod) {
        this.discoveryPeriod = discoveryPeriod;
	}
	public String getAccidentClaimSystem() {
        return accidentClaimSystem;
	}

	public void setAccidentClaimSystem(String accidentClaimSystem) {
        this.accidentClaimSystem = accidentClaimSystem;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
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