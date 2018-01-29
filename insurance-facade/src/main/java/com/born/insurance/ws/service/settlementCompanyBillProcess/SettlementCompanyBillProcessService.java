package com.born.insurance.ws.service.settlementCompanyBillProcess;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.settlementCompanyBillProcess.SettlementCompanyBillProcessInfo;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessOrder;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessQueryOrder;

public interface SettlementCompanyBillProcessService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(SettlementCompanyBillProcessOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    SettlementCompanyBillProcessInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<SettlementCompanyBillProcessInfo> queryList(SettlementCompanyBillProcessQueryOrder queryOrder);

    /**
     * 对结算单进行拆包操作
     * @param settlementCompanyBillProcessInfo
     */
	void packet(SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo) throws Exception;

	/**
	 * 更改结算单状态为已收款
	 * @param id
	 */
	void recieve(String id);
	
}	