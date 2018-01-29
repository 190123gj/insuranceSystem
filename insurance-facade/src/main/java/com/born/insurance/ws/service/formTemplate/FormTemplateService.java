package com.born.insurance.ws.service.formTemplate;

import com.born.insurance.ws.info.formTemplate.FormTemplateInfo;
import com.born.insurance.ws.order.formTemplate.FormTemplateQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * Created by Administrator on 2017-1-3.
 */
public interface FormTemplateService {
    QueryBaseBatchResult<FormTemplateInfo> queryList(FormTemplateQueryOrder queryOrder);
    FormTemplateInfo queryInfoByFormTemplateId(long formTemplateId);
}
