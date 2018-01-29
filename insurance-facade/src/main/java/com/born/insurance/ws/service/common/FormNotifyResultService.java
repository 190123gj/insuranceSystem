package com.born.insurance.ws.service.common;

import java.util.List;
import java.util.Map;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.common.FormMessageTempleteInfo;
import com.born.insurance.ws.order.common.SimpleUserInfo;

/**
 * 通知结果Service
 *
 *
 * @author wuzj
 *
 */
public interface FormNotifyResultService {
	
	/**
	 * 通知结果
	 * @param formInfo 表单信息
	 * @param templete 消息模板
	 * @param messageVars 消息变量值
	 * @param notifyUserList 通知用户
	 */
	void notifyResult(FormInfo formInfo, FormMessageTempleteInfo templete,
						Map<String, String> messageVars, List<SimpleUserInfo> notifyUserList);
}
