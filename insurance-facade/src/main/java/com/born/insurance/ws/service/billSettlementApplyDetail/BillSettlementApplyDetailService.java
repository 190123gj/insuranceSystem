package com.born.insurance.ws.service.billSettlementApplyDetail;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.billSettlementApplyDetail.BillSettlementApplyDetailInfo;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailOrder;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailQueryOrder;

public interface BillSettlementApplyDetailService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BillSettlementApplyDetailOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BillSettlementApplyDetailInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BillSettlementApplyDetailInfo> queryList(BillSettlementApplyDetailQueryOrder queryOrder);

    /**
     * 结算清单详情
     * @param id
     * @return
     */
	BillSettlementApplyDetailInfo findBySettlementApplyId(long id);
	
}	