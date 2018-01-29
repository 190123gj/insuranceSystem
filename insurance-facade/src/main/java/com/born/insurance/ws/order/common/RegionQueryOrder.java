package com.born.insurance.ws.order.common;
import com.born.insurance.ws.base.QueryPageBase;

/**
 * 行政区域划分
 *
 * @author wuzj
 */
public class RegionQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -3683619576665602120L;
	
	private int id;
	
	private String code;
	
	private String name;
	
	private String parentCode;
	
	private int level;
	
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
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
