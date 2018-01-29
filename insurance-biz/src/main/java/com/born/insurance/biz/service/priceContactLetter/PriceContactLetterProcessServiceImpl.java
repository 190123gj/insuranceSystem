package com.born.insurance.biz.service.priceContactLetter;

import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wqh on 2017-1-11.
 */
@Service("priceContactLetterProcessService")
public class PriceContactLetterProcessServiceImpl extends BaseProcessService {
	@Autowired
	private PriceContactLetterService priceContactLetterService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		PriceContactLetterInfo letterInfo = priceContactLetterService.findByFormId(formInfo.getFormId());
		Money insuranceAmount = Money.zero();
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
		riskManagerId.setVarVal(insuranceAmount.toStandardString().replace(",", ""));
		fields.add(riskManagerId);
		return fields;
	}
}
