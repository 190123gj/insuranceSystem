package com.born.insurance.ws.service.personCommissionProcess;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.personCommissionProcess.PersonCommissionProcessInfo;
import com.born.insurance.ws.order.personCommissionProcess.PersonCommissionProcessOrder;
import com.born.insurance.ws.order.personCommissionProcess.PersonCommissionProcessQueryOrder;

public interface PersonCommissionProcessService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(PersonCommissionProcessOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PersonCommissionProcessInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<PersonCommissionProcessInfo> queryList(PersonCommissionProcessQueryOrder queryOrder);
	
}	