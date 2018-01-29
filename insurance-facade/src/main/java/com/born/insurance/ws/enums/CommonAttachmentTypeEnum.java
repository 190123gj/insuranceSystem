/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Filename EstateTradeAttachmentTypeEnum.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-4-10</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public enum CommonAttachmentTypeEnum {
	
	FORM_ATTACH("FORM_ATTACH", "表单附件"),
	
	/** 表单附件 */
	PERSON_ATTACH("PERSON_ATTACH", "个人附件"),

	CERT_ATTACH("CERT_ATTACH", "证件附件"),

	CUSTOMER_PROTOCOL("CUSTOMER_PROTOCOL","客户协议"),

	INSURANCE_PROTOCOL("INSURANCE_PROTOCOL", "保险协议附件"),
	
	PROJECT_SET_UP("PROJECT_SET_UP","超权限附件"),
	
	INSURANCE_PRODUCT__LIABILITY("INSURANCE_PRODUCT__LIABILITY","产品中保险责任附件"),
	
	INSURANCE_PRODUCT__CHARGE("INSURANCE_PRODUCT__CHARGE","产品中的费率表"),
	
	INSURANCE_PRODUCT__OTHER("INSURANCE_PRODUCT__OTHER","产品中其他附件"),
	
	LIFEINSURANCE_BILL_POLICY_RECEIPT("LIFEINSURANCE_BILL_POLICY_RECEIPT","寿险保单回执"), 
	
	NOLIFEINSURANCE_QUATEBILL_POLICY_RECEIPT("NOLIFEINSURANCE_QUATEBILL_POLICY_RECEIPT","非寿险定额保单回执"),
	
	NOLIFEINSURANCE_POLICY_RECEIPT("NOLIFEINSURANCE_POLICY_RECEIPT","非寿险非定额保单回执"),
	
	NOLIFEINSURANCEQUOTA_DATA("NOLIFEINSURANCEQUOTA_DATA","非寿险定额投保申请附件"),
	
	NOLIFEINSURANCEQUOTA_OTHER_DATA("NOLIFEINSURANCEQUOTA_OTHER_DATA","非寿险定额投保申请其他附件"),
	
	NOLIFEINSURANCE_DATA("NOLIFEINSURANCE_DATA","非寿险非定额投保申请附件"),
	
	NOLIFEINSURANCE_OTHER_DATA("NOLIFEINSURANCE_OTHER_DATA","非寿险非定额投保申请其他附件"),
	
	LIFEINSURANCE_DATA("LIFEINSURANCE_DATA","寿险投保申请附件"),
	
	LIFEINSURANCE_OTHER_DATA("LIFEINSURANCE_OTHER_DATA","寿险投保申请其他附件"),

	OTHER("OTHER", "其它附件"), //默认，一般不使用
	
	SETTLEMENT_COMPANY_BILL_RECIEVE("SETTLEMENT_COMPANY_BILL_RECIEVE","结算单确认收款"),


	REPORT_ANALYSE("REPORT_ANALYSE","报价分析"),
	
	CLAIMSETTLEMENT("CLAIMSETTLEMENT","理赔服务条款");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * CommonAttachmentTypeEnum
	 * @param code 编码
	 * @param message 描述
	 */
	private CommonAttachmentTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return CommonAttachmentTypeEnum
	 */
	public static CommonAttachmentTypeEnum getByCode(String code) {
		for (CommonAttachmentTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	public List<CommonAttachmentTypeEnum> getAllEnum() {
		List<CommonAttachmentTypeEnum> list = new ArrayList<CommonAttachmentTypeEnum>();
		for (CommonAttachmentTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (CommonAttachmentTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
