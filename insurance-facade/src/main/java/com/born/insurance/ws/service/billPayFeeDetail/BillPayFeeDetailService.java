package com.born.insurance.ws.service.billPayFeeDetail;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.billPayFeeDetail.BillPayFeeDetailInfo;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailOrder;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailQueryOrder;

public interface BillPayFeeDetailService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BillPayFeeDetailOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BillPayFeeDetailInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BillPayFeeDetailInfo> queryList(BillPayFeeDetailQueryOrder queryOrder);

    
    /**
     * 查询费用支付清单
     * @param id
     * @return
     */
	List<BillPayFeeDetailInfo> findBySettlementApplyId(long id);
	
}	