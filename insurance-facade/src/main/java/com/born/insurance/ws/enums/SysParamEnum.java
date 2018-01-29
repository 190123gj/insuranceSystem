package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统参数
 * 
 * @author wuzj
 * 
 */
public enum SysParamEnum {
	
	SYS_PARAM_FINANCIAL_PRODUCT_SHORT_TERM_RULE("SYS_PARAM_FINANCIAL_PRODUCT_SHORT_TERM_RULE",
												"短期理财产品规则", "如：15D 表示小于15天，2M表示小于2月，1Y表示小于1年"),
	
	SYS_PARAM_BUSI_MANAGER_ROLE_NAME("SYS_PARAM_BUSI_MANAGER_ROLE_NAME", "业务经理", "bpm配置的业务经理的角色代码"),
	
	SYS_PARAM_RISK_MANAGER_ROLE_NAME("SYS_PARAM_RISK_MANAGER_ROLE_NAME", "风险经理", "bpm配置的风险经理的角色代码"),
	SYS_PARAM_MANAGER_SECRETARY_ROLE_NAME("SYS_PARAM_MANAGER_SECRETARY_ROLE_NAME", "总经理秘书",
											"bpm配置的总经理秘书的角色代码"),
	SYS_PARAM_MANAGER_SECRETARY_XH_ROLE_NAME("SYS_PARAM_MANAGER_SECRETARY_XH_ROLE_NAME", "总经理秘书",
												"bpm配置的总经理秘书的角色代码"),
	
	SYS_PARAM_LEGAL_MANAGER_ROLE_NAME("SYS_PARAM_LEGAL_MANAGER_ROLE_NAME", "法务经理",
										"bpm配置的法务经理的角色代码"),
	SYS_PARAM_LEGAL_MANAGER_LD_ROLE_NAME("SYS_PARAM_LEGAL_MANAGER_LD_ROLE_NAME", "法务经理-领导",
											"bpm配置的法务经理的角色代码"),
	SYS_PARAM_FINANCIAL_MANAGER_ROLE_NAME("SYS_PARAM_FINANCIAL_MANAGER_ROLE_NAME", "财务经理",
											"bpm配置的财务经理的角色代码"),
	
	SYS_PARAM_FINANCIAL_PERSONNEL_ROLE_NAME("SYS_PARAM_FINANCIAL_PERSONNEL_ROLE_NAME", "财务人员",
											"bpm配置的财务经理的角色代码"),
	
	SYS_PARAM_FINANCIAL_ZJLC_ROLE_NAME("SYS_PARAM_FINANCIAL_ZJLC_ROLE_NAME", "资金理财角色",
										"bpm配置的财务资金理财角色代码"),
	
	SYS_PARAM_XINHUI_PERSONNEL_ROLE_NAME("SYS_PARAM_XINHUI_PERSONNEL_ROLE_NAME", "信惠人员",
											"bpm配置的财务经理的角色代码"),
	
	SYS_PARAM_XINHUI_BUSIMANAGER_ROLE_NAME("SYS_PARAM_XINHUI_BUSIMANAGER_ROLE_NAME", "信惠业务人员",
											"bpm配置的信惠业务人员角色代码"),
	
	SYS_PARAM_RISK_SECRETARY_ROLE_NAME("SYS_PARAM_RISK_SECRETARY_ROLE_NAME", "风控秘书",
										"bpm配置的风控秘书的角色代码"),
	SYS_PARAM_RISK_ZY_ROLE_NAME("SYS_PARAM_RISK_ZY_ROLE_NAME", "风险管理部-职员（风险经理）",
								"bpm配置的风险管理部-职员的角色代码"),
	SYS_PARAM_RISK_LD_ROLE_NAME("SYS_PARAM_RISK_LD_ROLE_NAME", "风险管理部-领导", "bpm配置的风险管理部-领导的角色代码"),
	
	SYS_PARAM_SYSTEM_ADMINISTRATOR_ROLE_NAME("SYS_PARAM_SYSTEM_ADMINISTRATOR_ROLE_NAME", "系统管理员",
												"bpm配置的系统管理员的角色代码"),
	
	SYS_PARAM_CREDIT_COMMISSIONER_ROLE_NAME("SYS_PARAM_CREDIT_COMMISSIONER_ROLE_NAME", "征信专员",
											"bpm配置的征信专员的角色代码"),
	SYS_PARAM_FILE_ADMINISTRATOR_ROLE_NAME("SYS_PARAM_FILE_ADMINISTRATOR_ROLE_NAME", "档案管理员",
											"bpm配置的档案管理员的角色代码"),
	SYS_PARAM_COMPANY_SPECIALPAPER_ADMINI_ROLE_NAME(
													"SYS_PARAM_COMPANY_SPECIALPAPER_ADMINI_ROLE_NAME",
													"公司特种纸管理员", "bpm配置的公司特种纸管理员的角色代码"),
	SYS_PARAM_DEPT_SPECIALPAPER_ADMIN_ROLE_NAME("SYS_PARAM_DEPT_SPECIALPAPER_ADMIN_ROLE_NAME",
												"部门特种纸管理员", "bpm配置的部门特种纸管理员的角色代码"),
	
	SYS_PARAM_ALL_DATA_PERMISSION_ROLE_NAME("SYS_PARAM_ALL_DATA_PERMISSION_ROLE_NAME", "拥有所有数据权限",
											"bpm配置拥有所有数据权限的角色代码"),
	
	SYS_PARAM_PRINCIPAL_DATA_PERMISSION_ROLE_NAME("SYS_PARAM_PRINCIPAL_DATA_PERMISSION_ROLE_NAME",
													"只拥有所负责的数据权限", "bpm配置拥有所负责的数据的角色代码"),
	
	SYS_PARAM_INTERNAL_AUDITOR_ROLE_NAME("SYS_PARAM_INTERNAL_AUDITOR_ROLE_NAME", "内审专员",
											"bpm配置的内审专员角色代码"),
	SYS_PARAM_CWYSG_ROLE_NAME("SYS_PARAM_CWYSG_ROLE_NAME", "财务应收岗", "bpm配置的财务应收岗角色代码"),
	
	SYS_PARAM_CWYFG_ROLE_NAME("SYS_PARAM_CWYFG_ROLE_NAME", "财务应付岗", "bpm配置的财务应付岗角色代码"),
	
	SYS_PARAM_ZJL_ROLE_NAME("SYS_PARAM_ZJL_ROLE_NAME", "总经理", "bpm配置的总经理角色代码"),
	
	SYS_PARAM_CHAIRMAN_ROLE_NAME("SYS_PARAM_CHAIRMAN_ROLE_NAME", "董事长", "bpm配置的董事长角色代码"),
	
	SYS_PARAM_FGFZ_ROLE_NAME("SYS_PARAM_FGFZ_ROLE_NAME", "分管副总", "bpm配置的分管副总角色代码"),
	
	SYS_PARAM_IMAGE_SERVER_URL("SYS_PARAM_IMAGE_SERVER_URL", "图片服务器URL", "图片服务器URL"),
	
	SYS_PARAM_UPLOAD_FOLDER("SYS_PARAM_UPLOAD_FOLDER", "上传文件服器路径", "上传文件服器路径"),
	
	SYS_PARAM_WORD_DOWNLOAD_FOLDER("SYS_PARAM_WORD_DOWNLOAD_FOLDER", "导出文件服器路径", "出文件服器路径"),
	
	SYS_PARAM_FACE_WEB_URL("SYS_PARAM_FACE_WEB_URL", "web端外网地址", "web端外网地址"),
	
	SYS_PARAM_FACE_INTRANET_URL("SYS_PARAM_FACE_INTRANET_URL", "web端内网地址", "web端内网地址"),
	
	SYS_PARAM_RISK_COVER_RATE("SYS_PARAM_RISK_COVER_RATE", "风险覆盖率", "风险覆盖率"),
	
	SYS_PARAM_RISK_DEPT_CODE("SYS_PARAM_RISK_DEPT_CODE", "风险部门编号", "风险部门编号"),
	
	SYS_PARAM_NKHGB_DEPT_CODE("SYS_PARAM_NKHGB_DEPT_CODE", "内控合规部门编码", "内控合规部门编码"),
	
	SYS_PARAM_FW_DEPT_CODE("SYS_PARAM_FW_DEPT_CODE", "法律事务部门编码", "法律事务部门编码"),
	
	SYS_PARAM_DIRECTOR_POSITION("SYS_PARAM_DIRECTOR_POSITION", "总监", "总监职位编码"),
	
	SYS_PARAM_FGFZ_POSITION("SYS_PARAM_FGFZ_POSITION", "分管副总", "分管副总职位编号"),
	
	SYS_PARAM_MANAGER_POSITION("SYS_PARAM_MANAGER_POSITION", "总经理", "总经理职位编号"),
	
	SYS_PARAM_BUSINESS_DEPARTMENT("SYS_PARAM_BUSINESS_DEPARTMENT", "业务部", "业务部编号"),
	
	SYS_PARAM_BMFZR_ROLE_NAME("SYS_PARAM_BMFZR_ROLE_NAME", "部门负责人", "部门负责人编号"),
	
	SYS_PARAM_COMPANY("SYS_PARAM_COMPANY", "公司", "公司"),
	
	SYS_PARAM_GZ_ROLE_NAME("SYS_PARAM_GZ_ROLE_NAME", "公章管理员", "公章管理员编号"),
	
	SYS_PARAM_FRZ_ROLE_NAME("SYS_PARAM_FRZ_ROLE_NAME", "法人章管理员", "法人章管理员编号"),
	
	SYS_PARAM_GZFRZ_ROLE_NAME("SYS_PARAM_GZFRZ_ROLE_NAME", "公章法人章管理员", "管理公章和法人章两个章的人员"),
	
	SYS_PARAM_QB_INVOLVE_DEPT("SYS_PARAM_QB_INVOLVE_DEPT", "签报涉及部门", "签报涉及部门"),
	
	/** 系统通知邮箱配置 */
	SYS_PARAM_MAIL_SERVER("SYS_PARAM_MAIL_SERVER", "邮件服务器地址", "如 : smtp.163.com"),
	SYS_PARAM_MAIL_SERVERPORT("SYS_PARAM_MAIL_SERVERPORT", "邮件服务器端口", "邮件服务器端口"),
	SYS_PARAM_MAIL_USERNAME("SYS_PARAM_MAIL_USERNAME", "发件人邮箱", "发件人邮箱"),
	SYS_PARAM_MAIL_PASSWORD("SYS_PARAM_MAIL_PASSWORD", "发件人邮箱密码", "邮箱密码"),
	SYS_PARAM_MAIL_NICKNAME("SYS_PARAM_MAIL_NICKNAME", "发件人名称", "发件人名称"),
	
	/*** 是否进行评委key校验 YES/NO */
	SYS_PARAM_JUDGE_KEY_CHECK_SIGN("SYS_PARAM_JUDGE_KEY_CHECK_SIGN", "是否进行评委key校验- YES/NO",
									"是否进行评委key校验- YES/NO"),
	
	/*** 报表系统年度报表配置需要上报的部门编号 */
	SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE("SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE",
											"报表系统年度报表配置需要上报的部门编号", "报表系统年度报表配置需要上报的部门编号"),
	
	SYS_PARAM_XINHUI_DEPT_CODE("SYS_PARAM_XINHUI_DEPT_CODE", "信惠编号", "信惠组织编号"),
	
	APP_SECRITY_KEY("APP_SECRITY_KEY", "APP 加密 KEY", "APP 加密 KEY"),
	
	COMPANY_LEADER_DEPT_CODE("COMPANY_LEADER_DEPT_CODE", "公司领导", "公司领导部门编号"),
	
	FORM_CHANGE_SIGN_DEPARTMENT("FORM_CHANGE_SIGN_DEPARTMENT", "签报会签部门", "签报会签部门 编号,名称 ;编号,名称"),
	
	FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID("FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID",
												"签报会签部门负责人审核节点ID", "签报会签部门负责人审核节点ID"),
	FORM_CHANGE_LEADER_TASK_NODE_ID("FORM_CHANGE_LEADER_TASK_NODE_ID", "签报相关领导审核节点ID",
									"签报相关领导审核节点ID"),
	
	ENTRUSTED_DEFAULT_CHANNEL("ENTRUSTED_DEFAULT_CHANNEL", "委贷项目默认资金渠道", "委贷项目默认资金渠道编号"),
	
	SYS_PARAM_CWYS_ROLE_NAME("SYS_PARAM_CWYS_ROLE_NAME", "财务应收岗", "财务应收岗角色代码"),
	SYS_PARAM_CWYF_ROLE_NAME("SYS_PARAM_CWYF_ROLE_NAME", "财务应付岗", "财务应付岗角色代码"),
	SYS_PARAM_SCYXG_ROLE_NAME("SYS_PARAM_SCYXG_ROLE_NAME", "市场营销岗人员", "市场营销岗人员"),
	SYS_PARAM_NOT_CHECK_CAPTCHA_NAME("SYS_PARAM_NOT_CHECK_CAPTCHA_NAME", "是否不验证首页验证码", "是否不验证首页验证码"), ;
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 描述 */
	private final String desc;
	
	/**
	 * 构造一个<code>CompareEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SysParamEnum(String code, String message, String desc) {
		this.code = code;
		this.message = message;
		this.desc = desc;
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
	 * @return Returns the desc.
	 */
	public String getDesc() {
		return desc;
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
	 * @return Returns the desc.
	 */
	public String desc() {
		return desc;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return CompareEnum
	 */
	public static SysParamEnum getByCode(String code) {
		for (SysParamEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CompareEnum>
	 */
	public static List<SysParamEnum> getAllEnum() {
		List<SysParamEnum> list = new ArrayList<SysParamEnum>();
		for (SysParamEnum _enum : values()) {
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
		for (SysParamEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
