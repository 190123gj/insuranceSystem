package com.born.insurance.ws.info.projectSetup;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.BaseToStringInfo;

public class ProjectSetupInfo extends BaseToStringInfo {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 2426438231641594121L;
	/**
	* 占费比例
	*/	
	private double proportion;
 	/**
	* 客户
	*/	
	private long customerUserId;
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	/**
	 * 表单标识formId
	 */
	private long formId;
	
	/**
	 * 项目名称
	 */
	private String projectSetupName;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 该特殊权限对应的客户id
	 */
	private String customerUserIds;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 客户
	*/	
	private String customerUserName;
 	/**
	* project_setup_id
	*/	
	private long projectSetupId;
 	/**
	* 有效期至
	*/	
	private Date endDate;
 	/**
	* 比例类型
	*/	
	private String proportionType;
 	/**
	* 生效时间
	*/	
	private Date beginDate;
 	/**
	* 立项人
	*/	
	private long setupUserId;
 	/**
	* 项目使用人
	*/	
	private long setupUseType;
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
	* 保险信息
	*/	
	private long insuranceCatalogId;
 	/**
	* 渠道信息
	*/	
	private long channelsUserId;
 	/**
	* 立项人
	*/	
	private String setupUserName;
	
	/**
	 * 项目使用人
	 */
	private String setupUseName;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 渠道信息
	*/	
	private String channelsUserName;
	
	private String isVal;
	
	/**
	 * 保险相关信息
	 */
	private List<ProjectSetupCatalogInfo> bankOrders;
	
	
  	public String getProjectSetupName() {
		return projectSetupName;
	}

	public void setProjectSetupName(String projectSetupName) {
		this.projectSetupName = projectSetupName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getProportion() {
        return proportion;
	}

	public void setProportion(double proportion) {
        this.proportion = proportion;
	}
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
	public String getCustomerUserName() {
        return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
	}
	public long getProjectSetupId() {
        return projectSetupId;
	}

	public void setProjectSetupId(long projectSetupId) {
        this.projectSetupId = projectSetupId;
	}
	public Date getEndDate() {
        return endDate;
	}

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
	}
	public String getProportionType() {
        return proportionType;
	}

	public void setProportionType(String proportionType) {
        this.proportionType = proportionType;
	}
	public Date getBeginDate() {
        return beginDate;
	}

	public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
	}
	public long getSetupUserId() {
        return setupUserId;
	}

	public void setSetupUserId(long setupUserId) {
        this.setupUserId = setupUserId;
	}
	public long getSetupUseType() {
        return setupUseType;
	}

	public void setSetupUseType(long setupUseType) {
        this.setupUseType = setupUseType;
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
	public long getInsuranceCatalogId() {
        return insuranceCatalogId;
	}

	public void setInsuranceCatalogId(long insuranceCatalogId) {
        this.insuranceCatalogId = insuranceCatalogId;
	}
	public long getChannelsUserId() {
        return channelsUserId;
	}

	public void setChannelsUserId(long channelsUserId) {
        this.channelsUserId = channelsUserId;
	}
	public String getSetupUserName() {
        return setupUserName;
	}

	public void setSetupUserName(String setupUserName) {
        this.setupUserName = setupUserName;
	}
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
	}
	public String getChannelsUserName() {
        return channelsUserName;
	}

	public void setChannelsUserName(String channelsUserName) {
        this.channelsUserName = channelsUserName;
	}

    public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public List<ProjectSetupCatalogInfo> getBankOrders() {
		return bankOrders;
	}

	public void setBankOrders(List<ProjectSetupCatalogInfo> bankOrders) {
		this.bankOrders = bankOrders;
	}
	
	public String getCustomerUserIds() {
		return customerUserIds;
	}

	public void setCustomerUserIds(String customerUserIds) {
		this.customerUserIds = customerUserIds;
	}
	
	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}
	public String getIsVal() {
		return isVal;
	}

	public void setIsVal(String isVal) {
		this.isVal = isVal;
	}
	
	public String getSetupUseName() {
		return setupUseName;
	}

	public void setSetupUseName(String setupUseName) {
		this.setupUseName = setupUseName;
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