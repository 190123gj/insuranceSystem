package com.born.insurance.ws.service.priceContactLetterDemandDetail;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterDemandDetail.PriceContactLetterDemandDetailInfo;
import com.born.insurance.ws.order.priceContactLetterDemandDetail.PriceContactLetterDemandDetailOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetail.PriceContactLetterDemandDetailQueryOrder;

public interface PriceContactLetterDemandDetailService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(PriceContactLetterDemandDetailOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PriceContactLetterDemandDetailInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<PriceContactLetterDemandDetailInfo> queryList(PriceContactLetterDemandDetailQueryOrder queryOrder);
	
}	