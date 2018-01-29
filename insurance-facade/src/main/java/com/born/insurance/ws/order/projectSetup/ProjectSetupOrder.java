package com.born.insurance.ws.order.projectSetup;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import java.util.List;

import com.born.insurance.ws.order.base.FormOrderBase;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogOrder;

public class ProjectSetupOrder extends FormOrderBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = -5489996995707622468L;
	/**
	* 占费比例
	*/	
	private String proportion;
	
	/**
	 * 项目名称
	 */
	private String projectSetupName;
 	/**
	* 客户
	*/	
	private String customerUserId;
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
	private String projectSetupId;
 	/**
	* 有效期至
	*/	
	private String endDate;
	
	/**
	 * 超权限申请状态
	 */
	private String status;
 	/**
	* 比例类型
	*/	
	private String proportionType;
 	/**
	* 生效时间
	*/	
	private String beginDate;
 	/**
	* 立项人
	*/	
	private long setupUserId;
 	/**
	* 项目使用人
	*/	
	private String setupUseType;

	/**
	 * 权益人的id
	 */
	private String setupUseId;
 	/**
	* 
	*/	
	private Date rawUpdateTime;

 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保险信息
	*/	
	private String insuranceCatalogId;
 	/**
	* 渠道信息
	*/	
	private long channelsUserId;
	
 	/**
	* 立项人
	*/	
	private String setupUserName;
	
	/**
	 * 项目权益人
	 */
	private String setupUseName;

 	/**
	* 渠道信息
	*/	
	private String channelsUserName;
	
	/**
	 * 保险相关信息
	 */
	private List<InsuranceCatalogOrder> bankOrders;
 
  
	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	
	public String getProportionType() {
        return proportionType;
	}

	public void setProportionType(String proportionType) {
        this.proportionType = proportionType;
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
	public String getInsuranceCatalogId() {
        return insuranceCatalogId;
	}

	public void setInsuranceCatalogId(String insuranceCatalogId) {
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

	public String getChannelsUserName() {
        return channelsUserName;
	}

	public void setChannelsUserName(String channelsUserName) {
        this.channelsUserName = channelsUserName;
	}
	
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	public String getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(String customerUserId) {
		this.customerUserId = customerUserId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public String getProjectSetupId() {
		return projectSetupId;
	}

	public void setProjectSetupId(String projectSetupId) {
		this.projectSetupId = projectSetupId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public long getSetupUserId() {
		return setupUserId;
	}

	public void setSetupUserId(long setupUserId) {
		this.setupUserId = setupUserId;
	}

	public String getSetupUseType() {
		return setupUseType;
	}

	public void setSetupUseType(String setupUseType) {
		this.setupUseType = setupUseType;
	}

	public String getSetupUseId() {
		return setupUseId;
	}

	public void setSetupUseId(String setupUseId) {
		this.setupUseId = setupUseId;
	}
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
	
	public List<InsuranceCatalogOrder> getBankOrders() {
		return bankOrders;
	}

	public void setBankOrders(List<InsuranceCatalogOrder> bankOrders) {
		this.bankOrders = bankOrders;
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