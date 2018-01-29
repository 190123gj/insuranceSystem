package com.born.insurance.ws.enums;

import com.yjf.common.lang.util.StringUtil;

/**
 * 证件类型
 * */
public enum CertTypeEnum {
	DRIVING_LICENCE("DRIVING_LICENCE","驾驶证","PERSON",0),
	/** 身份证 */
	IDENTITY_CARD("IDENTITY_CARD", "身份证","PERSON",0),
	/** 军官证 */
	ARMY_IDENTITY_CARD("ARMY_IDENTITY_CARD", "军官证","PERSON",1),
	/** 学生证 */
	STUDENT_CARD("STUDENT_CARD", "户口","PERSON",2),
	/** 护照 */
	PASSPORT("PASSPORT", "护照","PERSON",3),

	UNIT_UNIFORM_CODE("UNIT_UNIFORM_CODE","统一社会代码证","COMPANYX",3),

	/**	营业执照*/
	BUSINESS_LICENSE("BUSINESS_LICENSE","营业执照","COMPANY" ,4),
	/**	税务登记证*/
	TAX_REGISTRATION_CERTIFICATE("TAX_REGISTRATION_CERTIFICATE","税务登记证","COMPANY",5),
	/**	组织机构代码证*/
	ORGANIZATION_CODE_CERTIFICATE("ORGANIZATION_CODE_CERTIFICATE","组织机构代码证","COMPANY",6),
	/**	业务许可证*/
	BUSINESS_PERMIT_CERTIFICATE("BUSINESS_PERMIT_CERTIFICATE","业务许可证","COMPANY",7),

	/** 其它证件 */
	OTHER("OTHER", "其它证件","ALL",100),
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;

	private final String  owner;

	private int order ;

	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private CertTypeEnum(String code, String message,String owner,int order) {
		this.code = code;
		this.message = message;
		this.owner = owner;
		this.order = order;
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

	public String getOwner() {
		return owner;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return CertTypeEnum
	 */
	public static CertTypeEnum getByCode(String code) {
		for (CertTypeEnum _enum : values()) {
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
	public static java.util.List<CertTypeEnum> getAllEnum() {
		java.util.List<CertTypeEnum> list = new java.util.ArrayList<CertTypeEnum>(values().length);
		for (CertTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	public static java.util.List<CertTypeEnum> getPersonAllEnum() {
	 	return getAllEnum("PERSON");
	}

	public static java.util.List<CertTypeEnum> getCompanyAllEnum() {
		return getAllEnum("COMPANY");
	}


	public static java.util.List<CertTypeEnum> getAllEnum(String owner) {
		java.util.List<CertTypeEnum> list = new java.util.ArrayList<CertTypeEnum>(values().length);
		for (CertTypeEnum _enum : values()) {
			if(StringUtil.equals(_enum.getOwner(),owner) || StringUtil.equals("ALL",_enum.getOwner())){
				list.add(_enum);
			}

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
		for (CertTypeEnum _enum : values()) {
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
		CertTypeEnum _enum = getByCode(code);
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