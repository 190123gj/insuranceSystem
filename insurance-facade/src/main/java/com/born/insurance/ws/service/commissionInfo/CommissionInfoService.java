package com.born.insurance.ws.service.commissionInfo;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.commissionInfo.CommissionInfoInfo;
import com.born.insurance.ws.info.commissionInfoDetail.CommissionInfoDetailInfo;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoOrder;
import com.born.insurance.ws.order.commissionInfo.CommissionInfoQueryOrder;

public interface CommissionInfoService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(CommissionInfoOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    CommissionInfoInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<CommissionInfoInfo> queryList(CommissionInfoQueryOrder queryOrder);

    /**
     * 查询保单佣金信息
     * @param businessBillId
     * @return
     */
	CommissionInfoInfo findCommissionInfoByBusinessBillId(long businessBillId);

	/**
	 * 佣金明细查询
	 * @param commissionInfoId
	 * @return
	 */
	List<CommissionInfoDetailInfo> findCommissionInfoDetails(long commissionInfoId);
	
}	