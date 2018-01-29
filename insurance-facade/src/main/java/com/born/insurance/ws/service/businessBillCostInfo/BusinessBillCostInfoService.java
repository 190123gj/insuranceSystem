package com.born.insurance.ws.service.businessBillCostInfo;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.businessBillCostInfo.BusinessBillCostInfoInfo;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoOrder;
import com.born.insurance.ws.order.businessBillCostInfo.BusinessBillCostInfoQueryOrder;

public interface BusinessBillCostInfoService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillCostInfoOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillCostInfoInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillCostInfoInfo> queryList(BusinessBillCostInfoQueryOrder queryOrder);
	
}	