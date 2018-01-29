package com.born.insurance.ws.service.insuranceProduct;
import java.util.List;
import java.util.Map;

import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogLiabilityInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface InsuranceProductService {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InsuranceProductOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceProductInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceProductInfo> queryList(InsuranceProductQueryOrder queryOrder);
    
    
    /**
     * 根据险种id查询对应的保险责任
     * @param ownerId
     * @return
     */
    List<InsuranceCatalogLiabilityInfo> queryList(long ownerId);
    
    /**
     * 根据产品id查询档次
     * @param productId
     * @return
     */
    List<InsuranceProductLevelInfo> queryProductLevelInfoList(long productId);
    
    /**
     * 根据用户id获取证件类型
     * @param customerUserid
     * @return
     */
    List<CustomerCertInfo> getCustomerCertInfo(long customerUserid);

    /**
     * 根据产品id查询产品档次信息
     * @param productId
     * @return
     */
	List<InsuranceProductLevelInfo> getInsuranceProductLevel(long productId);

	/**
	 * 更改产品的状态
	 */
	InsuranceBaseResult runUpdateSaleStatus();
	
	/**
	 * 查询产品的费率表
	 * @param productId
	 * @param chargeType
	 * @param year
	 * @return
	 */
	public Map<String,String> queryInsuranceProtocolCharge(long productId,String chargeType,long year);

	
}	