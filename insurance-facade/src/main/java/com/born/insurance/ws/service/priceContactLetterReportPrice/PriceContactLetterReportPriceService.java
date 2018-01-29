package com.born.insurance.ws.service.priceContactLetterReportPrice;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceInfo;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceDetailInfo;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceQueryOrder;

public interface PriceContactLetterReportPriceService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(PriceContactLetterCompanyReportPriceOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PriceContactLetterCompanyReportPriceInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<PriceContactLetterCompanyReportPriceInfo> queryList(PriceContactLetterReportPriceQueryOrder queryOrder);


    QueryBaseBatchResult<PriceContactLetterCompanyReportPriceDetailInfo> queryList(PriceContactLetterReportPriceDetailQueryOrder queryOrder);
	
}	