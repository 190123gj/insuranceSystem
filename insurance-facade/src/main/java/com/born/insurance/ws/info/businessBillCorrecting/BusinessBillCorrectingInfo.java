package com.born.insurance.ws.info.businessBillCorrecting;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BusinessBillCorrectingInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 5947826540713916967L;
	/**
	* 投保人名称
	*/	
	private String billCustomerName;
 	/**
	* 批改意见
	*/	
	private String correctingOpinion;
	/**
	 * 批改单号
	 */
	private String businessBillCorrectingNo;
 	/**
	* 新增时间
	*/	
	private Date rowAddTime;
 	/**
	* 批改状态
	*/	
	private String correctingStatus;
 	/**
	* 保单号
	*/	
	private String insuranceNo;
 	/**
	* 保单的id
	*/	
	private long businessBillId;
 	/**
	* 批改类型
	*/	
	private String correctingType;
 	/**
	* 更改时间
	*/	
	private Date rowUpdateTime;
 	/**
	* 保单批改表的id
	*/	
	private long businessBillCorrectingId;
 	/**
	* 投保人id
	*/	
	private long billCustomerId;
 
  	public String getBillCustomerName() {
        return billCustomerName;
	}

	public void setBillCustomerName(String billCustomerName) {
        this.billCustomerName = billCustomerName;
	}
	public String getCorrectingOpinion() {
        return correctingOpinion;
	}

	public void setCorrectingOpinion(String correctingOpinion) {
        this.correctingOpinion = correctingOpinion;
	}
	public Date getRowAddTime() {
        return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
        this.rowAddTime = rowAddTime;
	}
	public String getCorrectingStatus() {
        return correctingStatus;
	}

	public void setCorrectingStatus(String correctingStatus) {
        this.correctingStatus = correctingStatus;
	}
	public String getInsuranceNo() {
        return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
	}
	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getCorrectingType() {
        return correctingType;
	}

	public void setCorrectingType(String correctingType) {
        this.correctingType = correctingType;
	}
	public Date getRowUpdateTime() {
        return rowUpdateTime;
	}

	public void setRowUpdateTime(Date rowUpdateTime) {
        this.rowUpdateTime = rowUpdateTime;
	}
	public long getBusinessBillCorrectingId() {
        return businessBillCorrectingId;
	}

	public void setBusinessBillCorrectingId(long businessBillCorrectingId) {
        this.businessBillCorrectingId = businessBillCorrectingId;
	}
	public long getBillCustomerId() {
        return billCustomerId;
	}

	public void setBillCustomerId(long billCustomerId) {
        this.billCustomerId = billCustomerId;
	}

    public String getBusinessBillCorrectingNo() {
		return businessBillCorrectingNo;
	}

	public void setBusinessBillCorrectingNo(String businessBillCorrectingNo) {
		this.businessBillCorrectingNo = businessBillCorrectingNo;
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