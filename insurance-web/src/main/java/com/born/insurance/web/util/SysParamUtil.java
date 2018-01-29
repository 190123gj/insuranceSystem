package com.born.insurance.web.util;


import com.born.insurance.biz.service.common.SysParameterService;
import com.born.insurance.ws.enums.SysParamEnum;

/**
 * 
 * 获取系统参数
 * 
 * @author lirz
 * 
 * 2016-4-30 下午3:56:54
 */
public class SysParamUtil {
	
	private static SysParameterService sysParameterService;
	
	static {
		sysParameterService = SpringUtil.getBean("sysParameterService");
	}
	
	public static String getSysParamValue(String paramName) {
		return sysParameterService.getSysParameterValue(paramName);
	}
	
	public static String getSysParamValueByEnum(SysParamEnum sysParamEnum) {
		return (null == sysParamEnum) ? null : getSysParamValue(sysParamEnum.code());
	}
	
	public static int getSysParamIntValue(String paramName) {
		String value = getSysParamValue(paramName);
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static int getSysParamIntValue(SysParamEnum sysParamEnum) {
		return getSysParamIntValue(getSysParamValueByEnum(sysParamEnum));
	}
	
	public static double getSysParamDoubleValue(String paramName) {
		String value = getSysParamValue(paramName);
		try {
			return Double.valueOf(value);
		} catch (Exception e) {
			return 0d;
		}
	}
	
	public static double getSysParamDoubleValue(SysParamEnum sysParamEnum) {
		return getSysParamDoubleValue(getSysParamValueByEnum(sysParamEnum));
	}
}
