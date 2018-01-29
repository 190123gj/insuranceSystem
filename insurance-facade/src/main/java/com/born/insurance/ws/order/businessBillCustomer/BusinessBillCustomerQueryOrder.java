package com.born.insurance.ws.order.businessBillCustomer;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BusinessBillCustomerQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 所属业务单
	*/	
	private long businessBillId;
 	/**
	* 证件类型
	*/	
	private String customerCertType;
 	/**
	* 客户
	*/	
	private long customerUserId;
 	/**
	* business_bill_id
	*/	
	private long billCustomerId;
 	/**
	* 证件号码
	*/	
	private String customerCertNo;
 	/**
	* 客户名称
	*/	
	private String customerUserName;
 	/**
	* 关系
	*/	
	private String relation;
 	/**
	* 类型
	*/	
	private String type;
 
  	public long getBusinessBillId() {
        return businessBillId;
	}

	public void setBusinessBillId(long businessBillId) {
        this.businessBillId = businessBillId;
	}
	public String getCustomerCertType() {
        return customerCertType;
	}

	public void setCustomerCertType(String customerCertType) {
        this.customerCertType = customerCertType;
	}
	public long getCustomerUserId() {
        return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
        this.customerUserId = customerUserId;
	}
	public long getBillCustomerId() {
        return billCustomerId;
	}

	public void setBillCustomerId(long billCustomerId) {
        this.billCustomerId = billCustomerId;
	}
	public String getCustomerCertNo() {
        return customerCertNo;
	}

	public void setCustomerCertNo(String customerCertNo) {
        this.customerCertNo = customerCertNo;
	}
	public String getCustomerUserName() {
        return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
	}
	public String getRelation() {
        return relation;
	}

	public void setRelation(String relation) {
        this.relation = relation;
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