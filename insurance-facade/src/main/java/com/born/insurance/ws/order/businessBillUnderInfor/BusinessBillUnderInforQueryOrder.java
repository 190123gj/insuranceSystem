package com.born.insurance.ws.order.businessBillUnderInfor;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BusinessBillUnderInforQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 保费
	*/	
	private String premiumAmount;
 	/**
	* 比例
	*/	
	private double permiumRate;
 	/**
	* under_info_id
	*/	
	private long underInfoId;
 	/**
	* 币种
	*/	
	private long moneyType;
 	/**
	* 保额
	*/	
	private long insuranceAmount;
 	/**
	* 险种
	*/	
	private long typeId;
 
  	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getPremiumAmount() {
        return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
        this.premiumAmount = premiumAmount;
	}
	public double getPermiumRate() {
        return permiumRate;
	}

	public void setPermiumRate(double permiumRate) {
        this.permiumRate = permiumRate;
	}
	public long getUnderInfoId() {
        return underInfoId;
	}

	public void setUnderInfoId(long underInfoId) {
        this.underInfoId = underInfoId;
	}
	public long getMoneyType() {
        return moneyType;
	}

	public void setMoneyType(long moneyType) {
        this.moneyType = moneyType;
	}
	public long getInsuranceAmount() {
        return insuranceAmount;
	}

	public void setInsuranceAmount(long insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
	}
	public long getTypeId() {
        return typeId;
	}

	public void setTypeId(long typeId) {
        this.typeId = typeId;
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