package com.born.insurance.ws.service.priceContactLetter;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterFormInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceDetailInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceInfo;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterOrder;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterQueryOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.ReportPriceDetailQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

import java.util.List;

public interface PriceContactLetterService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    FormBaseResult save(PriceContactLetterOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PriceContactLetterInfo findById(long id);


    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PriceContactLetterInfo forwardFindById(long id);



    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    PriceContactLetterInfo findByFormId(long id);


    String  priceSchemeCode(PriceContactLetterInfo letterInfo);

    void updatePriceContactLetterStatus(FormInfo formInfo);


    FormBaseResult saveReportPrice(PriceContactLetterReportPriceOrder priceOrder);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<PriceContactLetterInfo> queryList(PriceContactLetterQueryOrder queryOrder);


    QueryBaseBatchResult<PriceContactLetterFormInfo> queryFormList(PriceContactLetterQueryOrder queryOrder);

    public List<PriceContactLetterCompanyReportPriceDetailInfo> queryReportPriceDetail(long priceContactLetterId, long customerUserId);

    public List<PriceContactLetterCompanyReportPriceDetailInfo>  queryReportPriceDetail(ReportPriceDetailQueryOrder reportPriceDetailQueryOrder);

    public List<PriceContactLetterCompanyReportPriceInfo> queryReportPriceCustomer(long priceContactLetterId);

    public void initReportPriceInfo(PriceContactLetterInfo letterInfo);
}	