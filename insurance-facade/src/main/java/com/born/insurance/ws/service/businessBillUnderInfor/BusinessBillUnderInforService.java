package com.born.insurance.ws.service.businessBillUnderInfor;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.businessBillUnderInfor.BusinessBillUnderInforInfo;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforOrder;
import com.born.insurance.ws.order.businessBillUnderInfor.BusinessBillUnderInforQueryOrder;

public interface BusinessBillUnderInforService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillUnderInforOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillUnderInforInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillUnderInforInfo> queryList(BusinessBillUnderInforQueryOrder queryOrder);

    /**
     * 保单承保信息查询
     * @param businessBillId
     * @return
     */
	List<BusinessBillUnderInforInfo> findBusinessBillUnderInfo(long businessBillId);
	
}	