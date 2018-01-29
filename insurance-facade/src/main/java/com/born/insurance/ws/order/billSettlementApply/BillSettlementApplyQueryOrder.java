package com.born.insurance.ws.order.billSettlementApply;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BillSettlementApplyQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -5397020899402402684L;
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
	 * 结算单状态
	 */
	private String status ;
 	/**
	* 关联的保单的id
	*/	
	private String businessBillId;
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
	/**
	 * 审核状态
	 */
	private String formStatus;
 
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

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
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