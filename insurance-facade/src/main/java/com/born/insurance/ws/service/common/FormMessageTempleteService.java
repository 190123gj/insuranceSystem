package com.born.insurance.ws.service.common;

import javax.jws.WebService;

import com.born.insurance.biz.service.common.SysClearCacheService;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormMessageTypeEnum;
import com.born.insurance.ws.info.common.FormMessageTempleteInfo;
import com.born.insurance.ws.order.common.FormMessageTempleteOrder;
import com.born.insurance.ws.order.common.FormMessageTempleteQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * 表单通知模板
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FormMessageTempleteService extends SysClearCacheService {
	
	/**
	 * 分页查询表单通知模板
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormMessageTempleteInfo> queryPage(FormMessageTempleteQueryOrder order);
	
	/**
	 * 根据表单和类型查询模板
	 * @param formCode
	 * @param type
	 * @return
	 */
	FormMessageTempleteInfo findByFormCodeAndType(FormCodeEnum formCode, FormMessageTypeEnum type);
	
	/**
	 * 根据模板ID查询
	 * @param tempelteId
	 * @return
	 */
	FormMessageTempleteInfo findByTempleteId(long templeteId);
	
	/**
	 * 保存模板
	 * @param order
	 * @return
	 */
	InsuranceBaseResult saveTemplete(FormMessageTempleteOrder order);
	
	/**
	 * 根据模板ID删除
	 * @param templeteId
	 * @return
	 */
	InsuranceBaseResult deleteByTempleteId(long templeteId);
	
}
