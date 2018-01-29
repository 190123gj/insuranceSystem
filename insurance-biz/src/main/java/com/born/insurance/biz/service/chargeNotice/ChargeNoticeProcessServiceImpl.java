package com.born.insurance.biz.service.chargeNotice;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.dal.dataobject.BillSettlementApplyDetailDO;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyOrder;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailOrder;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.billSettlementApply.BillSettlementApplyService;
import com.born.insurance.ws.service.billSettlementApplyDetail.BillSettlementApplyDetailService;
import com.born.insurance.ws.service.chargeNotice.ChargeNoticeService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.money.Money;

/**
 * Created by wqh on 2017-1-11.
 */
@Service("chargeNoticeProcessService")
public class ChargeNoticeProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private BillSettlementApplyService billSettlementApplyService;
	
	@Autowired
	private BillSettlementApplyDetailService billSettlementApplyDetailService;
	
	@Autowired
	private ChargeNoticeService chargeNoticeService;
	
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
		//结算通知审核通过之后，会在结算清单中显示数据
		//表单的id
		long formId = formInfo.getFormId();
		ChargeNoticeInfo chargeNoticeInfo = chargeNoticeService.findByFormId(formId);
		BillSettlementApplyOrder order = new BillSettlementApplyOrder();
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		order.setBusinessBillId(String.valueOf(chargeNoticeInfo.getBusinessBillId()));
		order.setInsuranceNo(chargeNoticeInfo.getInsuranceNo());
		order.setType(chargeNoticeInfo.getInsuranceTypeName());
		order.setBusinessBillId(order.getBusinessBillId());
		order.setSettlementNo(getBillNo());
		order.setFormCode(FormCodeEnum.JSSQ);
		billSettlementApplyService.save(order);
		//结算申请详情
		BillSettlementApplyDetailOrder billSettlementApplyDetailOrder = new BillSettlementApplyDetailOrder();
		billSettlementApplyDetailOrder.setSettlementApplyId(order.getId());
		billSettlementApplyDetailOrder.setBusinessBillId(chargeNoticeInfo.getBusinessBillId());
		billSettlementApplyDetailOrder.setActualPayFee(new Money(0));
		billSettlementApplyDetailOrder.setGenerationFee(new Money(0));
		billSettlementApplyDetailOrder.setIndividualTax(new Money(0));
		billSettlementApplyDetailOrder.setShouldPayFee(new Money(0));
		billSettlementApplyDetailOrder.setPayFee(chargeNoticeInfo.getPayFee());
		billSettlementApplyDetailOrder.setRecievableFee(chargeNoticeInfo.getRecievableFee());
		billSettlementApplyDetailOrder.setRecievedFee(chargeNoticeInfo.getRecievedFee());
		billSettlementApplyDetailOrder.setReservedScale(chargeNoticeInfo.getReservedScale());
		billSettlementApplyDetailOrder.setRowAddTime(new Date());
		billSettlementApplyDetailService.save(billSettlementApplyDetailOrder);
	}
	
	
	
	/**
	 * 业务单号
	 * @return
	 */
	private synchronized String getBillNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String billNo = "JJFJS";
		Pattern pattern = Pattern.compile("^[\\d]$");
		Matcher match = pattern.matcher(String.valueOf(month));
		Matcher match2 = pattern.matcher(String.valueOf(day));
		if (match.find()) {
			m = "0"+month;
		}
		if (match2.find()) {
			d = "0"+day;
		}
		String str= billNo+year+ m + d;
		String seqName = SysDateSeqNameEnum.INSURANCE_CONTACT_LETTER.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		str += StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return str;
	}
}
