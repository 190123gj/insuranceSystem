package com.born.insurance.ws.service.insuranceProtocol;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.order.insuranceProtocol.ProtocolChargeQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceProtocol.InsuranceProtocolInfo;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolOrder;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolQueryOrder;

public interface InsuranceProtocolService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(InsuranceProtocolOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceProtocolInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceProtocolInfo> queryList(InsuranceProtocolQueryOrder queryOrder);

    String queryInsuranceProtocolCharge(ProtocolChargeQueryOrder queryOrder);


    String queryInsuranceProtocolCharge(InsuranceProductInfo productInfo);
}	