package com.born.insurance.ws.service.businessBillCoinsInfo;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.businessBillCoinsInfo.BusinessBillCoinsInfoInfo;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoOrder;
import com.born.insurance.ws.order.businessBillCoinsInfo.BusinessBillCoinsInfoQueryOrder;

public interface BusinessBillCoinsInfoService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillCoinsInfoOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillCoinsInfoInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillCoinsInfoInfo> queryList(BusinessBillCoinsInfoQueryOrder queryOrder);

    /**
     * 共保信息查询
     * @param businessBillId
     * @return
     */
	List<BusinessBillCoinsInfoInfo> findBusinessBillCoinsInfo(long businessBillId);
	
}	