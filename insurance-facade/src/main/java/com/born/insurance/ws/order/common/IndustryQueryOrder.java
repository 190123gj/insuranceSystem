package com.born.insurance.ws.order.common;


import com.born.insurance.ws.base.QueryPageBase;

/**
 * 国民经济行业分类查询
 *
 * @author wuzj
 */
public class IndustryQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -3683619576665602120L;
	
	private int id;
	
	private String type;
	
	private String typeBig;
	
	private String typeMedium;
	
	private String typeSmall;
	
	private int level;
	
	private String parentCode;
	
	private String code;
	
	private String name;
	
	private String reviewTemplate;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTypeBig() {
		return this.typeBig;
	}
	
	public void setTypeBig(String typeBig) {
		this.typeBig = typeBig;
	}
	
	public String getTypeMedium() {
		return this.typeMedium;
	}
	
	public void setTypeMedium(String typeMedium) {
		this.typeMedium = typeMedium;
	}
	
	public String getTypeSmall() {
		return this.typeSmall;
	}
	
	public void setTypeSmall(String typeSmall) {
		this.typeSmall = typeSmall;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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
	
	public String getReviewTemplate() {
		return this.reviewTemplate;
	}
	
	public void setReviewTemplate(String reviewTemplate) {
		this.reviewTemplate = reviewTemplate;
	}
}
