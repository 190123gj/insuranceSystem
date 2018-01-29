package com.born.insurance.ws.order.insuranceCatalog;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.enums.InsuranceCatalogTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.insuranceCatalogLiability.InsuranceCatalogLiabilityOrder;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsOrder;

public class InsuranceCatalogOrder extends ProcessOrder {
	
	/**
	 * 分类名称
	 */
	private String catalogName;


	private String catalogCode;

	/**
	* 
	*/
	private Date rawAddTime;
	/**
	 * 用户备注
	 */
	private String remark;
	/**
	 * YES,NO
	 */
	private String lastDepth;
	/**
	 * 路径
	 */
	private String nodePath;
	/**
	 * 创建者名称
	 */
	private String creatorName;
	/**
	 * 父分类
	 */
	private long parentCatalogId;
	/**
	* 
	*/
	private Date rawUpdateTime;

	/**
	 * 级别
	 */
	private long depth;
	/**
	 * id
	 */
	private long catalogId;

	private long priceTemplate;
	
	private InsuranceCatalogTypeEnum type;


	private String isLifeInsurance;

	private String isMain;

	private String abbr1;

	private String abbr2;

	private String abbr3;

	List<InsuranceCatalogLiabilityOrder> liabilityOrders;
	
	List<InsuranceConditionsOrder> conditionOrders;

	private String parentIds;

	private String parentNames;

	private String childrenIds;

	private String childrenNames;


	public String getCatalogName() {
		return catalogName;
	}
	
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getLastDepth() {
		return lastDepth;
	}
	
	public void setLastDepth(String lastDepth) {
		this.lastDepth = lastDepth;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	

	
	public long getParentCatalogId() {
		return parentCatalogId;
	}
	
	public void setParentCatalogId(long parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	

	public long getDepth() {
		return depth;
	}
	
	public void setDepth(long depth) {
		this.depth = depth;
	}
	
	public long getCatalogId() {
		return catalogId;
	}
	
	public void setCatalogId(long catalogId) {
		this.catalogId = catalogId;
	}
	
	public InsuranceCatalogTypeEnum getType() {
		return type;
	}
	
	public void setType(InsuranceCatalogTypeEnum type) {
		this.type = type;
	}
	
	public List<InsuranceCatalogLiabilityOrder> getLiabilityOrders() {
		return liabilityOrders;
	}
	
	public void setLiabilityOrders(List<InsuranceCatalogLiabilityOrder> liabilityOrders) {
		this.liabilityOrders = liabilityOrders;
	}
	
	public List<InsuranceConditionsOrder> getConditionOrders() {
		return conditionOrders;
	}
	
	public void setConditionOrders(List<InsuranceConditionsOrder> conditionOrders) {
		this.conditionOrders = conditionOrders;
	}

	@Override
	public String getCreatorName() {
		return creatorName;
	}

	@Override
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public long getPriceTemplate() {
		return priceTemplate;
	}

	public void setPriceTemplate(long priceTemplate) {
		this.priceTemplate = priceTemplate;
	}

	public String getAbbr1() {
		return abbr1;
	}

	public void setAbbr1(String abbr1) {
		this.abbr1 = abbr1;
	}

	public String getAbbr2() {
		return abbr2;
	}

	public void setAbbr2(String abbr2) {
		this.abbr2 = abbr2;
	}

	public String getAbbr3() {
		return abbr3;
	}

	public void setAbbr3(String abbr3) {
		this.abbr3 = abbr3;
	}

	public String getIsLifeInsurance() {
		return isLifeInsurance;
	}

	public void setIsLifeInsurance(String isLifeInsurance) {
		this.isLifeInsurance = isLifeInsurance;
	}

	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getParentNames() {
		return parentNames;
	}

	public void setParentNames(String parentNames) {
		this.parentNames = parentNames;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public String getChildrenIds() {
		return childrenIds;
	}

	public void setChildrenIds(String childrenIds) {
		this.childrenIds = childrenIds;
	}

	public String getChildrenNames() {
		return childrenNames;
	}

	public void setChildrenNames(String childrenNames) {
		this.childrenNames = childrenNames;
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