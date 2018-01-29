package com.born.insurance.ws.service.insuranceCatalog;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogLiabilityInfo;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogOrder;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogQueryOrder;

public interface InsuranceCatalogService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InsuranceCatalogOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceCatalogInfo findById(long id);

    InsuranceCatalogInfo findPriceTemplateById(long id);


    InsuranceBaseResult deleteById(long id);
    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceCatalogInfo> queryList(InsuranceCatalogQueryOrder queryOrder);
    
    
    /**
     * 根据险种的id查询对应的保险责任
     * @param catalogId
     * @return
     */
    List<InsuranceCatalogLiabilityInfo> findInsuranceCatalogLiabilitys(long catalogId);
	
}	