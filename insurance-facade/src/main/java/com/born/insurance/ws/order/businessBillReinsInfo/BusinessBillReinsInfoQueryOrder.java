package com.born.insurance.ws.order.businessBillReinsInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BusinessBillReinsInfoQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 保险公司
	*/	
	private String insuraceCompanyName;
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 再保费
	*/	
	private Date premiumAmount;
 	/**
	* coins_info_id
	*/	
	private long reinsInfoId;
 	/**
	* 保险公司
	*/	
	private long insuraceCompanyId;
 	/**
	* 手续费
	*/	
	private Date poundageAmount;
 
  	public String getInsuraceCompanyName() {
        return insuraceCompanyName;
	}

	public void setInsuraceCompanyName(String insuraceCompanyName) {
        this.insuraceCompanyName = insuraceCompanyName;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public Date getPremiumAmount() {
        return premiumAmount;
	}

	public void setPremiumAmount(Date premiumAmount) {
        this.premiumAmount = premiumAmount;
	}
	public long getReinsInfoId() {
        return reinsInfoId;
	}

	public void setReinsInfoId(long reinsInfoId) {
        this.reinsInfoId = reinsInfoId;
	}
	public long getInsuraceCompanyId() {
        return insuraceCompanyId;
	}

	public void setInsuraceCompanyId(long insuraceCompanyId) {
        this.insuraceCompanyId = insuraceCompanyId;
	}
	public Date getPoundageAmount() {
        return poundageAmount;
	}

	public void setPoundageAmount(Date poundageAmount) {
        this.poundageAmount = poundageAmount;
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