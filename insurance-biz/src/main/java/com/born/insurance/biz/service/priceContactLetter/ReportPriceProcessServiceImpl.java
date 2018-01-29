package com.born.insurance.biz.service.priceContactLetter;

import java.util.List;

import com.born.insurance.dal.daointerface.PriceContactLetterCompanyReportPriceDAO;
import com.born.insurance.dal.daointerface.PriceContactLetterDAO;
import com.born.insurance.dal.daointerface.PriceContactLetterReportPriceDAO;
import com.born.insurance.dal.dataobject.PriceContactLetterCompanyReportPriceDO;
import com.born.insurance.dal.dataobject.PriceContactLetterDO;
import com.born.insurance.dal.dataobject.PriceContactLetterReportPriceDO;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.order.bpm.enums.FlowVarTypeEnum;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;

/**
 * Created by wqh on 2017-1-11.
 */
@Service("reportPriceProcessService")
public class ReportPriceProcessServiceImpl extends BaseProcessService {
    @Autowired
    private PriceContactLetterReportPriceDAO priceContactLetterReportPriceDAO;

    @Autowired
    PriceContactLetterService priceContactLetterService;

    @Autowired
    PriceContactLetterCompanyReportPriceDAO priceContactLetterCompanyReportPriceDAO;

    @Override
    public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
        PriceContactLetterReportPriceDO priceContactLetterDO = priceContactLetterReportPriceDAO
                .findByFormId(formInfo.getFormId());

        PriceContactLetterCompanyReportPriceDO queryReportPriceDo = new PriceContactLetterCompanyReportPriceDO();
        queryReportPriceDo.setReportPriceId(priceContactLetterDO.getReportPriceId());
        List<PriceContactLetterCompanyReportPriceDO> priceDOList = priceContactLetterCompanyReportPriceDAO.findByCondition(queryReportPriceDo,null,null,0,100);
        Money reportInsuranceAmount = Money.zero();
        if(ListUtil.isNotEmpty(priceDOList)){
            for(PriceContactLetterCompanyReportPriceDO priceDO : priceDOList){
                reportInsuranceAmount.addTo(priceDO.getBrokerAmount());
            }
        }

        Money insuranceAmount = Money.zero();
        PriceContactLetterInfo letterInfo =  priceContactLetterService.findById(priceContactLetterDO.getContactLetterId());

        for(PriceContactLetterDemandInfo demandInfo:letterInfo.getDemandInfos()){
            List<PriceContactLetterSchemeDetailInfo> detailInfos = demandInfo.getSchemeInfo().getSchemeDetailInfoList();
            for(PriceContactLetterSchemeDetailInfo detailInfo : detailInfos){
                insuranceAmount.addTo(detailInfo.getExpectPremiumAmountMoney());
            }
        }

        List<FlowVarField> fields = Lists.newArrayList();
        FlowVarField riskManagerId = new FlowVarField();
        riskManagerId.setVarName("a");
        riskManagerId.setVarType(FlowVarTypeEnum.DOUBLE);
        riskManagerId.setVarVal(insuranceAmount.subtract(reportInsuranceAmount).toStandardString().replace(",", ""));
        fields.add(riskManagerId);
        return fields;
    }

    @Override
    public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        priceContactLetterService.updatePriceContactLetterStatus(formInfo);
    }
}
