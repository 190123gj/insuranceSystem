package com.born.insurance.biz.service.insuranceContactLetter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.insurance.ws.service.insuranceContactLetter.InsuranceContactLetterService;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceContactLetterProcessService")
public class InsuranceContactLetterProcessServiceImpl extends BaseProcessService {
	@Autowired
    private InsuranceContactLetterService insuranceContactLetterService;

    @Override
    public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
    	 List<FlowVarField> fields = Lists.newArrayList();
         InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findByFormId(formInfo.getFormId());
         if (null != insuranceContactLetterInfo) {
             FlowVarField riskManagerId = new FlowVarField();
             riskManagerId.setVarName("priceNo");
             riskManagerId.setVarType(FlowVarTypeEnum.STRING);
             riskManagerId.setVarVal(!StringUtils.isBlank(insuranceContactLetterInfo.getPriceContactNo()) ? "YES":"NO");
             
             
             FlowVarField riskManagerAmount = new FlowVarField();
             riskManagerAmount.setVarName("premiumAmount");
             riskManagerAmount.setVarType(FlowVarTypeEnum.DOUBLE);
             riskManagerAmount.setVarVal(String.valueOf(insuranceContactLetterInfo.getPremiumAmount()));
            
             fields.add(riskManagerId);
             fields.add(riskManagerAmount);
             
         }
         return fields;	
    }
}