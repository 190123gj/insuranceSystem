package com.born.insurance.ws.service.insuranceBillRenewal;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.insuranceBillRenewal.InsuranceBillRenewalInfo;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewal;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalQueryOrder;
import com.born.insurance.ws.order.insuranceBillRenewal.InsuranceBillRenewalRecord;

public interface InsuranceBillRenewalService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InsuranceBillRenewalOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceBillRenewalInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceBillRenewalInfo> queryList(InsuranceBillRenewalQueryOrder queryOrder);

    /**
     * 根据业务单id 和 期数查询对应缴费信息
     * @param businessBillId
     * @param period
     * @return
     */
	InsuranceBillRenewalInfo findInsuranceBillRenewal(long businessBillId, long period);

	/**
	 * 根据业务单的id查询保单缴费计划详情信息
	 * @param businessBillId
	 * @return
	 */
	List<InsuranceBillRenewal> findInsuranceBillPayPlan(long businessBillId);

	/**
	 * 保单续期
	 * @param order
	 * @return
	 */
	InsuranceBaseResult insuranceBillRenewal(BusinessBillOrder order);
	
}	