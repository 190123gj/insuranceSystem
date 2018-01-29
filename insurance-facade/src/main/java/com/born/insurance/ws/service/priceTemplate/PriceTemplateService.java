package com.born.insurance.ws.service.priceTemplate;

import com.born.insurance.ws.info.priceTemplate.PriceTemplateInfo;
import com.born.insurance.ws.order.priceTemplate.PriceTemplateQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * Created by wqh on 2017-1-6.
 */
public interface PriceTemplateService {
	/**
	 * 查询信息
	 *
	 * @param id 单据编号
	 * @return
	 */
	PriceTemplateInfo findById(long id);
	
	/**
	 * 根据条件查询列表
	 * @param queryOrder 查询条件
	 * @return 结果集
	 */
	QueryBaseBatchResult<PriceTemplateInfo> queryList(PriceTemplateQueryOrder queryOrder);
	
}
