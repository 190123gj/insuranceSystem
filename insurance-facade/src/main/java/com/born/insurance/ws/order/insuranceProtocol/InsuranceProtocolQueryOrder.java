package com.born.insurance.ws.order.insuranceProtocol;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceProtocolQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 用户备注
	*/	
	private String remark;
 	/**
	* 协议号
	*/	
	private String no;
 	/**
	* 协议期结束时间
	*/	
	private Date endDate;
 	/**
	* 协议期开始时间
	*/	
	private Date beginDate;
 	/**
	* 类型： 保险协议，保险佣金
	*/	
	private String type;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 创建者
	*/	
	private long creatorId;
 	/**
	* id
	*/	
	private long protocolId;
 	/**
	* 保险公司
	*/	
	private long compayUserId;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 协议名称
	*/	
	private String name;
 	/**
	* 签约机构
	*/	
	private String contractingAgencyId;
 	/**
	* 创建者名称
	*/	
	private String creatorName;

	private long protocolUserId;

	private String protocolUserName;
 
  	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public String getNo() {
        return no;
	}

	public void setNo(String no) {
        this.no = no;
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
	public long getProtocolId() {
        return protocolId;
	}

	public void setProtocolId(long protocolId) {
        this.protocolId = protocolId;
	}
	public long getCompayUserId() {
        return compayUserId;
	}

	public void setCompayUserId(long compayUserId) {
        this.compayUserId = compayUserId;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getName() {
        return name;
	}

	public void setName(String name) {
        this.name = name;
	}
	public String getContractingAgencyId() {
        return contractingAgencyId;
	}

	public void setContractingAgencyId(String contractingAgencyId) {
        this.contractingAgencyId = contractingAgencyId;
	}
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
	}

	public long getProtocolUserId() {
		return protocolUserId;
	}

	public void setProtocolUserId(long protocolUserId) {
		this.protocolUserId = protocolUserId;
	}

	public String getProtocolUserName() {
		return protocolUserName;
	}

	public void setProtocolUserName(String protocolUserName) {
		this.protocolUserName = protocolUserName;
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