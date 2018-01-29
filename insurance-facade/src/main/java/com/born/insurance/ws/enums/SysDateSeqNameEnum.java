package com.born.insurance.ws.enums;

public enum SysDateSeqNameEnum {
	
	PRICE_CONTACT_LETTER("PRICE_CONTACT_LETTER", "询价单编号"),

	INSURANCE_PRODUCT("INSURANCE_PRODUCT", "保险产品编号"),
	
	INSURANCE_CONTACT_LETTER("INSURANCE_CONTACT_LETTER", "业务单编号"),
	
	BUSINESS_BILL_NOTICE_NO("BUSINESS_BILL_NOTICE_NO","业务结算通知单号"),
	
	BUSINESS_BILL_CORRECTING("BUSINESS_BILL_CORRECTING","保单批改单号"),
	
	PAYMENT_BILL_NUMBER("PAYMENT_BILL_NUMBER","缴费编号")
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private SysDateSeqNameEnum(String code, String message) {
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
	 * @return SysDateSeqNameEnum
	 */
	public static SysDateSeqNameEnum getByCode(String code) {
		for (SysDateSeqNameEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SysDateSeqNameEnum>
	 */
	public static java.util.List<SysDateSeqNameEnum> getAllEnum() {
		java.util.List<SysDateSeqNameEnum> list = new java.util.ArrayList<SysDateSeqNameEnum>(
			values().length);
		for (SysDateSeqNameEnum _enum : values()) {
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
		for (SysDateSeqNameEnum _enum : values()) {
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
		SysDateSeqNameEnum _enum = getByCode(code);
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
	public static String getCode(SysDateSeqNameEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
