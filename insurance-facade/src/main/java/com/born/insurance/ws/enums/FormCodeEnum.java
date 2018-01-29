package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单流程映射
 * 
 * @author wuzj
 */
public enum FormCodeEnum {
	
	DEFAULT("DEFAULT", "", "", "", "", "", "", "通用"),
	

	
	DEMO("demo", "demo", "demoProcessService",
					"/insurance/demo/view.htm", "/insurance/demo/edit.htm", "", "0",
					"demo"),
	CQXSQ("cqxsp", "cqxsp", "projectSetupProcessService",
			"/insurance/projectSetup/view.htm", "/insurance/demo/edit.htm", "", "0","超权限申请"),
	
	PRICESCHEME("priceScheme", "xjqt", "priceContactLetterProcessService",
			"/insurance/priceContactLetter/view.htm", "/insurance/priceContactLetter/edit.htm", "", "0","询价方案"),

	REPORTPRICE("reportPrice", "bjd", "reportPriceProcessService",
			"/insurance/priceContactLetter/view.htm", "/insurance/priceContactLetter/edit.htm", "", "0", "报价方案"),

	CONTACTLETTER("tbsq","xjqt_s","insuranceContactLetterProcessService",
			"/insurance/insuranceContactLetter/view.htm","/insurance/insuranceContactLetter/edit.htm","","0","投保申请"),
	
	BDSP("bdsp","bdsp","businessBillProcessService",
			"/insurance/businessBill/view.htm","/insurance/businessBill/view.htm","","0","保单上传审批"),
	
	JSSQ("jssq","jssq","billSettlementApplyProcessService",
			"/insurance/billSettlementApply/view.htm","/insurance/billSettlementApply/edit","","0","结算申请"),
	
	JSTZD("jstzd","jstzd","chargeNoticeProcessService",
			"/insurance/chargeNotice/view.htm","/insurance/chargeNotice/edit.htm","","0","结算通知单");
	
	/** 表单code */
	private final String code;
	
	/**
	 * 对应流程编号
	 */
	private final String flowCode;
	
	/**
	 * 流程业务处理的相关serviceName
	 */
	private final String processServiceName;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 默认的验证状态
	 */
	private final String defaultCheckStatus;
	
	/**
	 * 表单查看Url
	 */
	private final String viewUrl;
	
	/**
	 * 编辑页面
	 */
	private final String editUrl;
	
	/**
	 * 打印页面
	 */
	private final String printUrl;
	
	/**
	 * 构造一个<code>FormCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormCodeEnum(String code, String flowCode, String processServiceName, String viewUrl,
							String editUrl, String printUrl, String defaultCheckStatus,
							String message) {
		this.code = code;
		this.flowCode = flowCode;
		this.processServiceName = processServiceName;
		this.defaultCheckStatus = defaultCheckStatus;
		this.viewUrl = viewUrl;
		this.editUrl = editUrl;
		this.printUrl = printUrl;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getFlowCode() {
		return flowCode;
	}
	
	/**
	 * @return Returns the processServiceName.
	 */
	public String getProcessServiceName() {
		return processServiceName;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the viewUrl.
	 */
	public String getViewUrl() {
		return viewUrl;
	}
	
	/**
	 * @return Returns the editUrl.
	 */
	public String getEditUrl() {
		return editUrl;
	}
	
	/**
	 * @return Returns the printUrl.
	 */
	public String getPrintUrl() {
		return printUrl;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the flowCode.
	 */
	public String flowCode() {
		return flowCode;
	}
	
	/**
	 * @return Returns the processServiceName.
	 */
	public String processServiceName() {
		return processServiceName;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * @return Returns the viewUrl.
	 */
	public String viewUrl() {
		return viewUrl;
	}
	
	/**
	 * @return Returns the editUrl.
	 */
	public String editUrl() {
		return editUrl;
	}
	
	/**
	 * @return Returns the printUrl.
	 */
	public String printUrl() {
		return printUrl;
	}
	
	/**
	 * @return Returns the defaultCheckStatus.
	 */
	public String defaultCheckStatus() {
		return this.defaultCheckStatus;
	}
	
	/**
	 * @return Returns the defaultCheckStatus.
	 */
	public String getDefaultCheckStatus() {
		return this.defaultCheckStatus;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FormCodeEnum
	 */
	public static FormCodeEnum getByCode(String code) {
		for (FormCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormCodeEnum>
	 */
	public static List<FormCodeEnum> getAllEnum() {
		List<FormCodeEnum> list = new ArrayList<FormCodeEnum>();
		for (FormCodeEnum _enum : values()) {
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
		for (FormCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
