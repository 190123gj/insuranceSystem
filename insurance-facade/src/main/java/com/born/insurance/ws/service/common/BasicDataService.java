package com.born.insurance.ws.service.common;

import com.born.insurance.ws.info.common.IndustryInfo;
import com.born.insurance.ws.info.common.RegionInfo;
import com.born.insurance.ws.order.common.IndustryQueryOrder;
import com.born.insurance.ws.order.common.RegionOrder;
import com.born.insurance.ws.order.common.RegionQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
/**
 * 基础数据服务
 *
 * @author wuzj
 */

public interface BasicDataService {


	/**
	 * 查询国民经济行业分类
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<IndustryInfo> queryIndustry(IndustryQueryOrder order);
	

	
	/**
	 * 查询行政区域划分
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<RegionInfo> queryRegion(RegionQueryOrder order);
	
	/**
	 * 保存行政区域
	 * @param order
	 * @return
	 */
	InsuranceBaseResult saveRegion(RegionOrder order);
	
	/**
	 * 删除行政区域
	 * @param id
	 * @return
	 */
	InsuranceBaseResult delRegion(int id);
	

}
