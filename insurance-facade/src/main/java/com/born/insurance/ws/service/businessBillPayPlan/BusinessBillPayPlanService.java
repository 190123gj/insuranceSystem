package com.born.insurance.ws.service.businessBillPayPlan;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.businessBill.BillPayPlanYear;
import com.born.insurance.ws.info.businessBillPayPlan.BusinessBillPayPlanInfo;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanOrder;
import com.born.insurance.ws.order.businessBillPayPlan.BusinessBillPayPlanQueryOrder;

public interface BusinessBillPayPlanService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillPayPlanOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillPayPlanInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillPayPlanInfo> queryList(BusinessBillPayPlanQueryOrder queryOrder);

    /**
     * 未缴费的缴费计划查询
     * @param businessBillId
     * @return
     */
	List<BusinessBillPayPlanInfo> findBusinessBillPayPlan(long businessBillId);

	/**
	 * 查询所有的缴费计划
	 * @param businessBillId
	 * @return
	 */
	List<BillPayPlanYear> findAllBusinessBillPayPlan(long businessBillId);
	
	/**
	 * 查询所有的缴费计划
	 * @param businessBillId
	 * @return
	 */
	List<BusinessBillPayPlanInfo> findAllNormalBusinessBillPayPlan(long businessBillId);
	
}	