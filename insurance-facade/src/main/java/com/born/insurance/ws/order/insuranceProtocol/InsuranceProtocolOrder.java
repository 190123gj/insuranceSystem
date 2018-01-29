package com.born.insurance.ws.order.insuranceProtocol;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoOrder;

public class InsuranceProtocolOrder extends ProcessOrder {
	
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
	 * id
	 */
	private long protocolId;
	/**
	 * 保险公司
	 */
	private String protocolUserId;
	private String protocolUserName;
	
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
	
	private String contractingAgencyName;

	private String salesAreasCollect;


	private Date signDate;

	private String isMain;

	private String parentId;

	private String parentName;
	
	private List<InsuranceProtocolInfoOrder> infoOrders;
	
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


	public String getProtocolUserId() {
		return protocolUserId;
	}

	public void setProtocolUserId(String protocolUserId) {
		this.protocolUserId = protocolUserId;
	}

	public String getProtocolUserName() {
		return protocolUserName;
	}

	public void setProtocolUserName(String protocolUserName) {
		this.protocolUserName = protocolUserName;
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
	

	public String getContractingAgencyName() {
		return contractingAgencyName;
	}
	
	public void setContractingAgencyName(String contractingAgencyName) {
		this.contractingAgencyName = contractingAgencyName;
	}
	
	public List<InsuranceProtocolInfoOrder> getInfoOrders() {
		return infoOrders;
	}
	
	public void setInfoOrders(List<InsuranceProtocolInfoOrder> infoOrders) {
		this.infoOrders = infoOrders;
	}

	public String getSalesAreasCollect() {
		return salesAreasCollect;
	}

	public void setSalesAreasCollect(String salesAreasCollect) {
		this.salesAreasCollect = salesAreasCollect;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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