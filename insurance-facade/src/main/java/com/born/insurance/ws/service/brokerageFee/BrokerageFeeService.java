package com.born.insurance.ws.service.brokerageFee;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.brokerageFee.BrokerageFeeInfo;
import com.born.insurance.ws.info.brokerageFeeDetail.BrokerageFeeDetailInfo;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeOrder;
import com.born.insurance.ws.order.brokerageFee.BrokerageFeeQueryOrder;

public interface BrokerageFeeService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BrokerageFeeOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BrokerageFeeInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BrokerageFeeInfo> queryList(BrokerageFeeQueryOrder queryOrder);

    /**
     * 查询保单经纪费信息
     * @param businessBillId
     * @return
     */
	BrokerageFeeInfo findBrokerageFeeByBusinessBillId(long businessBillId);

	/**
	 * 查询经纪费明细
	 * @param brokerageId
	 * @return
	 */
	List<BrokerageFeeDetailInfo> findBrokerageFeeDetails(long brokerageId);
	
}	