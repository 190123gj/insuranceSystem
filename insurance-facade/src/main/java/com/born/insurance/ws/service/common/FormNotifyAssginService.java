package com.born.insurance.ws.service.common;

import java.util.Map;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.common.FormMessageTempleteInfo;
import com.born.insurance.ws.info.common.FormRelatedUserInfo;

/**
 * 通知交办人Service
 *
 *
 * @author wuzj
 *
 */
public interface FormNotifyAssginService {
	
	/**
	 * 通知交办人
	 * @param formInfo
	 * @param assignMan
	 * @param templete
	 * @param messageVars
	 */
	void notifyAssign(FormInfo formInfo, FormRelatedUserInfo assignMan,
					  FormMessageTempleteInfo templete, Map<String, String> messageVars);
}
