package com.born.insurance.ws.order.insuranceCatalog;
import com.born.insurance.ws.enums.InsuranceCatalogTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceCatalogQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 分类名称
	*/	
	private String catalogName;
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


	private InsuranceCatalogTypeEnum type;

	private String certainIds;
 
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

	public String getCertainIds() {
		return certainIds;
	}

	public void setCertainIds(String certainIds) {
		this.certainIds = certainIds;
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