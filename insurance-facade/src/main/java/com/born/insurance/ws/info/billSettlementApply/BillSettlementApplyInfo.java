package com.born.insurance.ws.info.billSettlementApply;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class BillSettlementApplyInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 6963712717749437060L;
	/**
	* 保单结算申请单的id
	*/	
	private long id;
 	/**
	* 记录更新时间
	*/	
	private Date rowUpdateTime;
 	/**
	* 结算单号
	*/	
	private String settlementNo;
 	/**
	* 关联的保单的id
	*/	
	private String businessBillId;
	
	/**
	 * 保单号
	 */
	private String insuranceNo;

	/**
	 * 经纪人职级
	 */
	private String brokerRank ;
 	/**
	* 申请时间
	*/	
	private Date rowAddTime;
 	/**
	* 流程的id
	*/	
	private long formId;
 	/**
	* 保险类别
	*/	
	private String type;
 
  	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRowUpdateTime() {
        return rowUpdateTime;
	}

	public void setRowUpdateTime(Date rowUpdateTime) {
        this.rowUpdateTime = rowUpdateTime;
	}
	public String getSettlementNo() {
        return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
	}
	public String getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(String businessBillId) {
        this.businessBillId = businessBillId;
	}
	public Date getRowAddTime() {
        return rowAddTime;
	}

	public void setRowAddTime(Date rowAddTime) {
        this.rowAddTime = rowAddTime;
	}
	public long getFormId() {
        return formId;
	}

	public void setFormId(long formId) {
        this.formId = formId;
	}
	public String getType() {
        return type;
	}

	public void setType(String type) {
        this.type = type;
	}
    

    public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getBrokerRank() {
		return brokerRank;
	}

	public void setBrokerRank(String brokerRank) {
		this.brokerRank = brokerRank;
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