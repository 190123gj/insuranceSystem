package com.born.insurance.ws.enums;
/**
 * 客户类型
 * */
public enum CustomerTypeEnum {
	/**个人客户*/
	INDIVIDUAL_CUSTOMER("INDIVIDUAL_CUSTOMER","个人客户"),
	/**企业客户*/
	COMPANY_CUSTOMER("COMPANY_CUSTOMER","团体客户"),
	/**保险机构*/
	INSURANCE_INSTITUTIONS("INSURANCE_INSTITUTIONS","保险机构"),
	/**第三方机构*/
	THIRD_PARTY_MECHANISM("THIRD_PARTY_MECHANISM","第三方机构"),

	SYSTEM_ORGANIZATION("SYSTEM_ORGANIZATION","组织机构"),

	SYSTEM_USER("SYSTEM_USER","用户"),

	/**经纪人*/
	BROKER("BROKER","经纪人"),
	/**合作机构员工*/
	COOPERATIVE_ORGANIZATION_EMPLOYEE("COOPERATIVE_ORGANIZATION_EMPLOYEE","合作机构员工");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private CustomerTypeEnum(String code, String message) {
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
	 * @return CustomerTypeEnum
	 */
	public static CustomerTypeEnum getByCode(String code) {
		for (CustomerTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CustomerTypeEnum>
	 */
	public static java.util.List<CustomerTypeEnum> getAllEnum() {
		java.util.List<CustomerTypeEnum> list = new java.util.ArrayList<CustomerTypeEnum>(values().length);
		for (CustomerTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (CustomerTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		CustomerTypeEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(CustomerTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
