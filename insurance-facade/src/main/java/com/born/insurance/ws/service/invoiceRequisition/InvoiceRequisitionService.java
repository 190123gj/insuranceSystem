package com.born.insurance.ws.service.invoiceRequisition;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.invoiceRequisition.InvoiceRequisitionInfo;
import com.born.insurance.ws.info.invoiceRequisition.SettlementBillInfo;
import com.born.insurance.ws.order.invoiceRequisition.InvoiceRequisitionOrder;
import com.born.insurance.ws.order.invoiceRequisition.InvoiceRequisitionQueryOrder;

public interface InvoiceRequisitionService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InvoiceRequisitionOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InvoiceRequisitionInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InvoiceRequisitionInfo> queryList(InvoiceRequisitionQueryOrder queryOrder);

    /**
     * 计算保单号的笔数
     * @param billNo
     * @return
     */
	String getBillInfos(String billNo);

	/**
	 * 结算单清单信息
	 * @param billNo
	 * @return
	 */
	List<SettlementBillInfo> findSettlementBillInfo(String billNo);
	
}	