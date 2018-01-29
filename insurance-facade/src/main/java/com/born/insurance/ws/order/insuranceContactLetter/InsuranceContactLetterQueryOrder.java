package com.born.insurance.ws.order.insuranceContactLetter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceContactLetterQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -3666280190604210230L;
	/**
	* 客户
	*/	
	private long customerUserId;
	
	/**
	 * 客户名称
	 */
	private String customerUserName;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 收件人
	*/	
	private String recipients;
 	/**
	* 结束时间
	*/	
	private Date endDate;
 	/**
	* 开始时间
	*/	
	private Date beginDate;
 	/**
	* 类型：直接投标、询价投标
	*/	
	private String type;
	
	/**
	 * 状态
	 */
	private String status;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 创建者
	*/	
	private long creatorId;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保险项目
	*/	
	private String project;
 	/**
	* 地址
	*/	
	private String address;
 	/**
	* 单位
	*/	
	private String company;
 	/**
	* id
	*/	
	private long letterId;
 	/**
	* 是否开发票
	*/	
	private String isInvoice;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 联系电话
	*/	
	private String mobile;
 
  	public long getCustomerUserId() {
        return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
        this.customerUserId = customerUserId;
	}
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public String getRecipients() {
        return recipients;
	}

	public void setRecipients(String recipients) {
        this.recipients = recipients;
	}
	public Date getEndDate() {
        return endDate;
	}

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
	}
	public Date getBeginDate() {
        return beginDate;
	}

	public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
	}
	public String getType() {
        return type;
	}

	public void setType(String type) {
        this.type = type;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getCreatorId() {
        return creatorId;
	}

	public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getProject() {
        return project;
	}

	public void setProject(String project) {
        this.project = project;
	}
	public String getAddress() {
        return address;
	}

	public void setAddress(String address) {
        this.address = address;
	}
	public String getCompany() {
        return company;
	}

	public void setCompany(String company) {
        this.company = company;
	}
	public long getLetterId() {
        return letterId;
	}

	public void setLetterId(long letterId) {
        this.letterId = letterId;
	}
	public String getIsInvoice() {
        return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
	}
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
	}
	public String getMobile() {
        return mobile;
	}

	public void setMobile(String mobile) {
        this.mobile = mobile;
	}

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
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