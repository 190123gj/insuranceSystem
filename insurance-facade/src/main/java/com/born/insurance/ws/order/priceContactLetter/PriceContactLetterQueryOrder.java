package com.born.insurance.ws.order.priceContactLetter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PriceContactLetterQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* id
	*/	
	private long id;
 	/**
	* 
	*/	
	private Date rawAddTime;

	private String customerType;

 	/**
	* 客户
	*/	
	private long customerUserId;
 	/**
	* 客户
	*/	
	private String customerUserName;
 	/**
	* 询价方案名称
	*/	
	private String name;
 	/**
	* 询价方案编码
	*/	
	private String billNo;
 	/**
	* 扩展字段
	*/	
	private String ext;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 版本
	*/	
	private long version;

	private String formStatus;

	private String status;
 
  	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getCustomerUserId() {
        return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
        this.customerUserId = customerUserId;
	}
	public String getCustomerUserName() {
        return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
	}
	public String getName() {
        return name;
	}

	public void setName(String name) {
        this.name = name;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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