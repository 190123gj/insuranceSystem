package com.born.insurance.ws.info.businessBillUnderInfor;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BusinessBillUnderInforInfo extends BaseToStringInfo {
				

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698725662003260863L;

	private long underInfoId;

	private long productId;

	private String productName;

	private long insuranceAmount;

	private double permiumRate;

	private String premiumAmount;

	private long moneyType;

	private long businessBillId;

 
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