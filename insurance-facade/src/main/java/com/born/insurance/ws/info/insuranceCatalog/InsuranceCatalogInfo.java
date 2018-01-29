package com.born.insurance.ws.info.insuranceCatalog;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.InsuranceCatalogTypeEnum;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;

public class InsuranceCatalogInfo extends BaseToStringInfo {
				
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
	private BooleanEnum lastDepth;
	
	/**
	 * 是否有子节点
	 */
	private String haschildren;
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
	* 创建者
	*/	
	private long creatorId;
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
	public BooleanEnum getLastDepth() {
        return lastDepth;
	}

	public void setLastDepth(BooleanEnum lastDepth) {
        this.lastDepth = lastDepth;
	}
	public String getNodePath() {
        return nodePath;
	}

	public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
	}
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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
	public long getCreatorId() {
        return creatorId;
	}

	public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
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

	public long getPriceTemplate() {
		return priceTemplate;
	}

	public void setPriceTemplate(long priceTemplate) {
		this.priceTemplate = priceTemplate;
	}
	
	public String getHaschildren() {
		return haschildren;
	}

	public void setHaschildren(String haschildren) {
		this.haschildren = haschildren;
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