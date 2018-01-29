package com.born.insurance.ws.enums;

/**
 * 提醒类型
 * */
public enum RemindTypeEnum {
	
	/**超权限申请提醒*/
	SUPER_POWER_APPLY_REMIND ("SUPER_POWER_APPLY_REMIND","超权限申请提醒"),
	/**询报价提醒*/
	INQUIRY_QUTATION_REMIND("INQUIRY_QUTATION_REMIND","询报价提醒"),
	/**投保申请提醒*/
	INSURE_APPLY_REMIND("INQUIRY_QUTATION_REMIND","投保申请提醒"),
	/**续保提醒*/
	RENEWALB_REMIND("RENEWAL_REMIND","续保提醒"),
	/**续期提醒*/
	RENEWALQ_REMIND("RENEWALQ_REMIND","续期提醒"),
	/**证件到期提醒*/
	CERT_EXPIRE_REMIND("CERT_EXPIRE_REMIND","证件到期提醒"),
	/**协议到期提醒*/
	PROTOCOL_EXPIRE_REMIND("PROTOCOL_EXPIRE_REMIND","协议到期提醒");
	
	
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private RemindTypeEnum(String code, String message) {
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
	 * @return CertTypeEnum
	 */
	public static RemindTypeEnum getByCode(String code) {
		for (RemindTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CertTypeEnum>
	 */
	public static java.util.List<RemindTypeEnum> getAllEnum() {
		java.util.List<RemindTypeEnum> list = new java.util.ArrayList<RemindTypeEnum>(values().length);
		for (RemindTypeEnum _enum : values()) {
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
		for (RemindTypeEnum _enum : values()) {
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
		RemindTypeEnum _enum = getByCode(code);
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
	public static String getCode(CertTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
