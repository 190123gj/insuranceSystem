package com.born.insurance.ws.service.chargeNotice;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeFormInfo;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeInfo;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeOrder;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeQueryOrder;

public interface ChargeNoticeService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(ChargeNoticeOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    ChargeNoticeInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<ChargeNoticeInfo> queryList(ChargeNoticeQueryOrder queryOrder);
    
    
    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<ChargeNoticeFormInfo> queryFormList(ChargeNoticeQueryOrder queryOrder);

    /**
     * 结算通知单详情
     * @param formId
     * @return
     */
	ChargeNoticeInfo findByFormId(long formId);

	/**
	 * 根据保单的id查看该保单的结算通知单
	 * @param businessBillId
	 * @return
	 */
	ChargeNoticeInfo findByBusinessBillId(String businessBillId);
	
}	