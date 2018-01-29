package com.born.insurance.biz.service.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import rop.thirdparty.com.google.common.collect.Lists;

import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.demo.DemoInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.insurance.ws.service.demo.DemoService;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("demoProcessService")
public class DemoProcessServiceImpl extends BaseProcessService {
	@Autowired
	DemoService demoService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		DemoInfo demoInfo = demoService.findByFormId(formInfo.getFormId());
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("amount");
		riskManagerId.setVarType(FlowVarTypeEnum.DOUBLE);
		riskManagerId.setVarVal(demoInfo.getAmount().getCent() + "");
		fields.add(riskManagerId);
		return fields;
	}
}
