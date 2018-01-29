package com.born.insurance.ws.service.businessBill;
import java.util.List;

import com.born.insurance.ws.info.businessBill.BusinessBillFormInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.businessBill.ShowMsgInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.order.businessBill.BusinessBillOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface BusinessBillService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillInfo> queryList(BusinessBillQueryOrder queryOrder);

    /**
     * 新增缴费计划
     * @param insuranceContactLetterInfo
     * @param insuranceContactLetterDetails
     * @return 
     */
	ShowMsgInfo addPayBillPlan(InsuranceContactLetterInfo insuranceContactLetterInfo,List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails);

	/**
	 * 新增保单
	 * @param order
	 */
	InsuranceBaseResult addBusinessBill(BusinessBillOrder order);

	/**
	 * 查询保单列表
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<BusinessBillFormInfo> queryFormList(BusinessBillQueryOrder queryOrder);

	/**
	 * 根据formid查询保单的信息
	 * @param formId
	 * @return
	 */
	BusinessBillInfo findByFormId(long formId);

	/**
	 * 根据保单号查询保单信息
	 * @param insuranceNo
	 * @return
	 */
	BusinessBillFormInfo findBusinessBillByNo(String insuranceNo);
	/**
	 * 更改保单结算的状态
	 * @param businessBillInfo
	 * @return
	 */
	int updateBusinessBillStatus(BusinessBillInfo businessBillInfo);

	/**
	 * 删除保单
	 * @param order
	 * @return
	 */
	InsuranceBaseResult deleteBusinessBill(BusinessBillOrder order);
	
}	