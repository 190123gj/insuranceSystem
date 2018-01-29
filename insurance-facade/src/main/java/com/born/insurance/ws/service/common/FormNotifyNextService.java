package com.born.insurance.ws.service.common;

import java.util.Map;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.common.FormMessageTempleteInfo;

/**
 * 通知下步执行人Service
 *
 *
 * @author wuzj
 *
 */
public interface FormNotifyNextService {
	
	/**
	 * 通知下步执行人
	 * @param formInfo 表单信息
	 * @param templete 消息模板
	 * @param messageVars 消息变量值
	 */
	void notifyNext(FormInfo formInfo, FormMessageTempleteInfo templete,
					Map<String, String> messageVars);
}
