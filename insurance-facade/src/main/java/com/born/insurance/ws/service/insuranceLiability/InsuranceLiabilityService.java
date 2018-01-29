package com.born.insurance.ws.service.insuranceLiability;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceLiability.InsuranceLiabilityInfo;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityOrder;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityQueryOrder;

public interface InsuranceLiabilityService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InsuranceLiabilityOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceLiabilityInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceLiabilityInfo> queryList(InsuranceLiabilityQueryOrder queryOrder);

    /**
     * 删除保险责任
     * @param id
     * @return
     */
	InsuranceBaseResult deleteInsuranceLiability(InsuranceLiabilityOrder order);
	
}	