package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 消息常用变量
 * 
 * @author wuzj
 *
 */
public enum FormMessageVarEnum {
	
	单据号("单据号", "${单据号}"),
	单据名称("单据名称", "${单据名称}"),
	发起人("发起人", "${发起人}"),
	发起部门("发起部门", "${发起部门}"),
	发起时间("发起时间", "${发起时间}"),
	操作时间("操作时间", "${操作时间}"),
	操作人("操作人", "${操作人}"),
	收件人("收件人", "${收件人}"),
	操作部门("操作部门", "${操作部门}"),
	项目编号("项目编号", "${项目编号}"),
	项目名称("项目名称", "${项目名称}"),
	客户名称("客户名称", "${客户名称}"),
	审核结果("审核结果", "${审核结果}"),
	项目金额("项目金额", "${项目金额}"),
	业务类型("业务类型", "${业务类型}"),
	项目期限("项目期限", "${项目期限}"),
	审核地址("审核地址", "${审核地址}"),
	单据地址("单据地址", "${单据地址}"),
	系统地址("系统地址", "${系统地址}"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FormMessageVarEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormMessageVarEnum(String code, String message) {
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
	 * @return FormMessageVarEnum
	 */
	public static FormMessageVarEnum getByCode(String code) {
		for (FormMessageVarEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormMessageVarEnum>
	 */
	public static List<FormMessageVarEnum> getAllEnum() {
		List<FormMessageVarEnum> list = new ArrayList<FormMessageVarEnum>();
		for (FormMessageVarEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (FormMessageVarEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
