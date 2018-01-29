package com.born.insurance.biz.service.billSettlementApply;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.google.common.collect.Lists;

/**
 * Created by wqh on 2017-1-11.
 */
@Service("billSettlementApplyProcessService")
public class BillSettlementApplyProcessServiceImpl extends BaseProcessService {
	
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		return fields;
	}
	
	
	@Override
	public InsuranceBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		InsuranceBaseResult result = createResult();
		result.setSuccess(true);
		return result;
	}



	
	
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
	}
	
	
}
