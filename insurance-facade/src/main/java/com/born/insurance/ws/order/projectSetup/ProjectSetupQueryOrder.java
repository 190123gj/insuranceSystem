package com.born.insurance.ws.order.projectSetup;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class ProjectSetupQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 7720181772059831488L;
	/**
	* 占费比例
	*/	
	private double proportion;
	
	/**
	 * 项目名称
	 */
	private String projectSetupName;
	
 	/**
	* 客户
	*/	
	private long customerUserId;
 	/**
	* 备注
	*/	
	private String remark;
 	/**
	* 客户
	*/	
	private long customerUserName;
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
	 * 项目状态
	 */
	private String status;


	private long setupUseId;
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
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 渠道信息
	*/	
	private String channelsUserName;
	
	/**
	 * 当前系统时间
	 */
	private Date currentSystemTime;

 
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
	public long getCustomerUserName() {
        return customerUserName;
	}

	public void setCustomerUserName(long customerUserName) {
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


	public long getSetupUseId() {
		return setupUseId;
	}

	public void setSetupUseId(long setupUseId) {
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
	
	public Date getCurrentSystemTime() {
		return currentSystemTime;
	}

	public void setCurrentSystemTime(Date currentSystemTime) {
		this.currentSystemTime = currentSystemTime;
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