package com.born.insurance.ws.service.personCommission;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.personCommission.PersonCommissionInfo;
import com.born.insurance.ws.info.personCommissionDetail.PersonCommissionDetailInfo;
import com.born.insurance.ws.order.personCommission.PersonCommissionOrder;
import com.born.insurance.ws.order.personCommission.PersonCommissionQueryOrder;

public interface PersonCommissionService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(PersonCommissionOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PersonCommissionInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<PersonCommissionInfo> queryList(PersonCommissionQueryOrder queryOrder);
    
    
    public InsuranceBaseResult update(PersonCommissionOrder personCommissionOrder);
    
    /**
     * 查询业务员的佣金明细
     * @param businessUserId
     * @return
     */
	List<PersonCommissionDetailInfo> getCommissionInfoDetails(long businessUserId);
	
}	