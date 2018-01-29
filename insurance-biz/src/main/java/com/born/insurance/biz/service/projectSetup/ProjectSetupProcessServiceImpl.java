package com.born.insurance.biz.service.projectSetup;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseProcessService;
import rop.thirdparty.com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("projectSetupProcessService")
public class ProjectSetupProcessServiceImpl extends BaseProcessService {
    @Autowired
    ProjectSetupService projectSetupService;

    @Override
    public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
        List<FlowVarField> fields = Lists.newArrayList();
        ProjectSetupInfo setupInfo = projectSetupService.findByFormId(formInfo.getFormId());
        if (null != setupInfo) {
            FlowVarField riskManagerId = new FlowVarField();
            riskManagerId.setVarName("proportion");
            riskManagerId.setVarType(FlowVarTypeEnum.DOUBLE);
            riskManagerId.setVarVal(String.valueOf((setupInfo.getProportion())/100));
            fields.add(riskManagerId);
        }
        return fields;
    }
}
