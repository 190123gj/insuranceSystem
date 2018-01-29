package com.born.insurance.ws.service.salesAreas;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.salesAreas.SalesAreasInfo;
import com.born.insurance.ws.order.salesAreas.SalesAreasOrder;
import com.born.insurance.ws.order.salesAreas.SalesAreasQueryOrder;

public interface SalesAreasService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(SalesAreasOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    SalesAreasInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<SalesAreasInfo> queryList(SalesAreasQueryOrder queryOrder);
	
}	