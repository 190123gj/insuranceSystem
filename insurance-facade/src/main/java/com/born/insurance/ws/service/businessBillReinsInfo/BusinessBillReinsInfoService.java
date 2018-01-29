package com.born.insurance.ws.service.businessBillReinsInfo;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.businessBillReinsInfo.BusinessBillReinsInfoInfo;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoOrder;
import com.born.insurance.ws.order.businessBillReinsInfo.BusinessBillReinsInfoQueryOrder;

public interface BusinessBillReinsInfoService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillReinsInfoOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillReinsInfoInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillReinsInfoInfo> queryList(BusinessBillReinsInfoQueryOrder queryOrder);

    /**
     * 查询保单再保信息
     * @param businessBillId
     * @return
     */
	List<BusinessBillReinsInfoInfo> findBusinessBillReinsInfo(long businessBillId);
	
}	