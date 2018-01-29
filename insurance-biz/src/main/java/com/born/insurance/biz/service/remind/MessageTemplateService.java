package com.born.insurance.biz.service.remind;

import com.born.insurance.ws.info.common.MessageTemplateInfo;
import com.born.insurance.ws.order.common.MessageTemplateOrder;
import com.born.insurance.ws.order.common.MessageTemplateQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface MessageTemplateService {
	
	public QueryBaseBatchResult<MessageTemplateInfo> queryMessageTemplateInfoByCondition(MessageTemplateQueryOrder messageTemplateQueryOrder);
	
	public MessageTemplateInfo queryMessageTemplateInfoByID(MessageTemplateQueryOrder messageTemplateQueryOrder);
	
	public InsuranceBaseResult updateMessageTemplateInfoByID(MessageTemplateOrder messageTemplateOrder);

}
