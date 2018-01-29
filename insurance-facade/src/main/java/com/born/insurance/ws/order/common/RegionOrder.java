package com.born.insurance.ws.order.common;


import com.born.insurance.ws.order.base.ProcessOrder;

/**
 * 行政区域划分
 *
 * @author wuzj
 */
public class RegionOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -5543587824810458427L;
	
	private int id;
	
	private String code;
	
	private String name;
	
	private String parentCode;
	
	private String hasChildren;
	
	private int level;
	
	private int sortOrder;
	
	private String remark;
	
	@Override
	public void check() {
		validateNotNull(code, "编码");
		validateNotNull(name, "名称");
		validateNotNull(parentCode, "上级编码");
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentCode() {
		return this.parentCode;
	}
	
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	public String getHasChildren() {
		return this.hasChildren;
	}
	
	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
