package com.born.insurance.ws.service.insuranceProtocolCharge;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProtocolCharge.InsuranceProtocolChargeInfo;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeOrder;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeQueryOrder;


public interface InsuranceProtocolChargeService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InsuranceProtocolChargeOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceProtocolChargeInfo findById(String id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceProtocolChargeInfo> queryList(InsuranceProtocolChargeQueryOrder queryOrder);


    /**
     *
     * @return
     */
    String queryProtocolCharge(InsuranceProtocolChargeQueryOrder queryOrder);

    /**
     * 查询投保人在该产品的缴费期限
     * @param productId
     * @param chargeType
     * @return
     */
	List<InsuranceProtocolChargeInfo> getInsuranceProductCharge(String productId, String chargeType);

	/**
	 * 获取投保人在该产品的费率
	 * @param productId
	 * @param chargeType
	 * @param certNo
	 * @param period
	 * @return
	 */
	InsuranceProtocolChargeInfo getInsuranceProductChargeRate(String productId, String chargeType, String certNo,String period);
	/**
	 * 获取保险公司对应寿险产品的经纪费比例
	 * @param insuranceProductInfo
	 * @param appserialPeriod
	 * @param appserialPeriod2 
	 * @return
	 */
	InsuranceProtocolChargeInfo getBrokerageRate(String type,InsuranceProductInfo insuranceProductInfo, String letterId, String appserialPeriod2);
	
	/**
	 * 获取产品缴费年限内的缴费期数
	 * @param insuranceProductInfo
	 * @param letterId
	 * @param period
	 * @return
	 */
	Integer getPeriodMax(InsuranceProductInfo insuranceProductInfo, String letterId);

	/**
	 * 查询某保险公司下产品的缴费期限
	 * @param companyUserId
	 * @param productId
	 * @param catalogId
	 * @return
	 */
	List<InsuranceProtocolChargeInfo> getInsuranceProductChargeInfo(long companyUserId,long catalogId, String productId);

	
}	