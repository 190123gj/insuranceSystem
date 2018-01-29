package com.born.insurance.ws.service.personCommissionDetail;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.math.BigDecimal;
import java.util.List;

import com.born.insurance.ws.info.personCommissionDetail.PersonCommissionDetailInfo;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailOrder;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailQueryOrder;

public interface PersonCommissionDetailService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(PersonCommissionDetailOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PersonCommissionDetailInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<PersonCommissionDetailInfo> queryList(PersonCommissionDetailQueryOrder queryOrder);
    /**
     * 获取佣金收入
     * @param personCommissionDetailQueryOrder
     * @return
     * @throws Exception 
     */
	Money getInComeTotalSum(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) throws Exception;

	/**
	 * 佣金提取
	 * @param personCommissionDetailQueryOrder
	 * @return
	 * @throws Exception 
	 */
	Money getExtractTotalSum(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) throws Exception;

	/**
	 * 获取上期结转的金额
	 * @param personCommissionDetailQueryOrder
	 * @return
	 * @throws Exception 
	 */
	Money getLastTotal(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) throws Exception;
	/**
	 * 获取时间
	 * @param personCommissionDetailQueryOrder
	 * @return
	 */
	List<String> getXAxisData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder);
	/**
	 * 获取佣金收入明细
	 * @param personCommissionDetailQueryOrder
	 * @param xAxisData 
	 * @return
	 */
	List<BigDecimal> getPositiveData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder, List<String> xAxisData);

	/**
	 * 获取佣金提取明细
	 * @param personCommissionDetailQueryOrder
	 * @param xAxisData 
	 * @return
	 */
	List<BigDecimal> getNegativexData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder, List<String> xAxisData);

	/**
	 * 查询佣金明细详情
	 * @param personCommissionDetailOrder
	 * @return
	 */
	List<PersonCommissionDetailInfo> getPersonCommissionDetail(PersonCommissionDetailOrder personCommissionDetailOrder);
	
}	