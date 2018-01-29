package com.born.insurance.biz.service.businessBill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseProcessService;
import com.born.insurance.biz.util.PinyinUtil;
import com.born.insurance.dal.daointerface.BusinessBillDAO;
import com.born.insurance.dal.dataobject.BusinessBillDO;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeOrder;
import com.born.insurance.ws.order.common.SimpleFormOrder;
import com.born.insurance.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.chargeNotice.ChargeNoticeService;
import com.born.insurance.ws.service.common.FormService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.money.Money;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by wqh on 2017-1-11.
 */
@Service("businessBillProcessService")
public class BusinessBillProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private ChargeNoticeService chargeNoticeService;
	
	@Autowired
	protected BusinessBillDAO businessBillDAO;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		long formId = formInfo.getFormId();
		BusinessBillDO businessBillDO = businessBillDAO.findByFormId(formId);
		if (null != businessBillDO) {
			FlowVarField field = new FlowVarField();
			field.setVarName("Reservation");
			field.setVarType(FlowVarTypeEnum.DOUBLE);
			field.setVarVal(String.valueOf(businessBillDO.getGrossRate()));
			fields.add(field);
		}
		return fields;
	}
	
	
	@Override
	public InsuranceBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		InsuranceBaseResult result = createResult();
		Double brokerScale = 0.0 ;
		Double commonScale = 0.0 ;
		Map<String, Object> customizeMap = order.getCustomizeMap();
		if (customizeMap.containsKey("brokerScale")) {
			String broStr = (String)customizeMap.get("brokerScale");
			String comStr = (String)customizeMap.get("commonScale");
			if (!StringUtils.isBlank(broStr) && !StringUtils.isBlank(comStr)) {
				brokerScale = Double.valueOf(broStr);
				commonScale = Double.valueOf(comStr);
				FormInfo formInfo = order.getFormInfo();
				long formId = formInfo.getFormId();
				BusinessBillDO businessBillDO = businessBillDAO.findByFormId(formId);
				businessBillDO.setBrokerScale(brokerScale);
				businessBillDO.setCommonScale(commonScale);
				BigDecimal broker = new BigDecimal(brokerScale);
				BigDecimal common = new BigDecimal(commonScale);
				BigDecimal one = new BigDecimal(1);
				//计算公式：公式：1-佣金比例/经纪费率，保留2位小数点
				BigDecimal grossRate = (one.subtract(common.divide(broker,2,RoundingMode.HALF_UP)));
				businessBillDO.setGrossRate(grossRate.doubleValue());
				businessBillDAO.update(businessBillDO);
			}
		}
		
		result.setSuccess(true);
		return result;
	}
	
	
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
		long formId = formInfo.getFormId();
		BusinessBillDO businessBillDO = businessBillDAO.findByFormId(formId);
		if (null == businessBillDO) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA, "数据不存在");
		}
		//原保费
		Money premiumAmount = businessBillDO.getPremiumAmount();
		//经纪费率
		double brokerScale = businessBillDO.getBrokerScale();
		//佣金比例
		double commonScale = businessBillDO.getCommonScale();
		//预留比率
		double grossRate = businessBillDO.getGrossRate();
		
		Money brokerFee = premiumAmount.multiply(brokerScale).multiply(0.01);
		Money commonFee = premiumAmount.multiply(commonScale).multiply(0.01);
		Money grossProfit = premiumAmount.multiply(grossRate).multiply(0.01);
		businessBillDO.setBrokerVal(brokerFee);
		businessBillDO.setCommonVal(commonFee);
		businessBillDO.setGrossProfit(grossProfit);
		businessBillDAO.update(businessBillDO);
		
		//添加保单结算通知单
		ChargeNoticeOrder order = new ChargeNoticeOrder();
		order.setBusinessBillId(businessBillDO.getBusinessBillId());
		order.setDepart(businessBillDO.getInsuranceDepart());
		order.setTeam(businessBillDO.getInsuranceTeam());
		order.setNoticeDate(businessBillDO.getInsuranceDate());
		order.setInsuranceNo(businessBillDO.getInsuranceNo());
		order.setInsuranceTypeName(businessBillDO.getInsuranceTypeName());
		order.setBillCustomerName(businessBillDO.getInsuranceCompanyName());
		order.setPremiumAmount(premiumAmount);
		order.setRecievableFee(businessBillDO.getBrokerVal());
		order.setReservedScale(businessBillDO.getGrossRate());
		order.setPayFee(businessBillDO.getCommonVal());
		order.setBillNo(businessBillDO.getBillNo());
		order.setNoticeNo(this.genNoticeNo(order));
		order.setInsuranceBrokerNo(businessBillDO.getInsuranceBrokerNo());
		order.setInsuranceBrokerName(businessBillDO.getInsuranceBrokerName());
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		order.setFormCode(FormCodeEnum.JSTZD);
		chargeNoticeService.save(order);
	}
	

	/**
	 * 编号
	 * @return
	 */
	private synchronized String genNoticeNo(ChargeNoticeOrder order) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String priceCode = PinyinUtil.getPinYinHeadChar(order.getInsuranceTypeName()) + year;
		String seqName = SysDateSeqNameEnum.BUSINESS_BILL_NOTICE_NO.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		priceCode += StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return priceCode;
	}
}
