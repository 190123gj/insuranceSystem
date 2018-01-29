package com.born.insurance.ws.service.billSettlementApply;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.billSettlementApply.BillSettlementApplyFormInfo;
import com.born.insurance.ws.info.billSettlementApply.BillSettlementApplyInfo;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyOrder;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyQueryOrder;

public interface BillSettlementApplyService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BillSettlementApplyOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BillSettlementApplyInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BillSettlementApplyFormInfo> queryList(BillSettlementApplyQueryOrder queryOrder);

    /**
     * 查询结算清单信息
     * @param formId
     * @return
     */
	BillSettlementApplyInfo findByFormId(long formId);
	
}	