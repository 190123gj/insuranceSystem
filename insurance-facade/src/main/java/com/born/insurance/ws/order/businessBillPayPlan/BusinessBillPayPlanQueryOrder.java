package com.born.insurance.ws.order.businessBillPayPlan;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BusinessBillPayPlanQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 应交日期
	*/	
	private Date planPayDate;
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 保费
	*/	
	private String premiumAmount;
 	/**
	* pay_plan_id
	*/	
	private long payPlanId;
 	/**
	* 险种
	*/	
	private long typeId;
 	/**
	* 实交日期
	*/	
	private Date actualPayDate;
 
  	public Date getPlanPayDate() {
        return planPayDate;
	}

	public void setPlanPayDate(Date planPayDate) {
        this.planPayDate = planPayDate;
	}
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
	public long getPayPlanId() {
        return payPlanId;
	}

	public void setPayPlanId(long payPlanId) {
        this.payPlanId = payPlanId;
	}
	public long getTypeId() {
        return typeId;
	}

	public void setTypeId(long typeId) {
        this.typeId = typeId;
	}
	public Date getActualPayDate() {
        return actualPayDate;
	}

	public void setActualPayDate(Date actualPayDate) {
        this.actualPayDate = actualPayDate;
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