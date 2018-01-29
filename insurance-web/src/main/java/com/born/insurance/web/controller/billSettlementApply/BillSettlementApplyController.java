package com.born.insurance.web.controller.billSettlementApply;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.common.DateSeqService;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.StringUtil;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.InsuranceTypeEnum;
import com.born.insurance.ws.enums.ProjectStatusEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.billPayFeeDetail.BillPayFeeDetailInfo;
import com.born.insurance.ws.info.billSettlementApply.BillSettlementApplyFormInfo;
import com.born.insurance.ws.info.billSettlementApply.BillSettlementApplyInfo;
import com.born.insurance.ws.info.billSettlementApplyDetail.BillSettlementApplyDetailInfo;
import com.born.insurance.ws.info.bpm.Role;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyOrder;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.billPayFeeDetail.BillPayFeeDetailService;
import com.born.insurance.ws.service.billSettlementApply.BillSettlementApplyService;
import com.born.insurance.ws.service.billSettlementApplyDetail.BillSettlementApplyDetailService;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.chargeNotice.ChargeNoticeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/billSettlementApply")
public class BillSettlementApplyController extends WorkflowBaseController {
	@Autowired
	protected BillSettlementApplyService billSettlementApplyService;
	
	@Autowired
	protected BusinessBillService businessBillService;
	
	@Autowired
	private BillPayFeeDetailService billPayFeeDetailService;
	
	@Autowired
	protected BillSettlementApplyDetailService billSettlementApplyDetailService;
	
	@Autowired
	protected ChargeNoticeService chargeNoticeService;
	
	@Autowired
	protected DateSeqService dateSeqService;
	private final static String VM_PATH = "/insurance/billSettlementApply/";

	/**
	* 结算清单列表
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			boolean isRightOperate = false ;
			BillSettlementApplyQueryOrder queryOrder = new BillSettlementApplyQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("b.row_add_time");
			queryOrder.setSortOrder("DESC");
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (null != sessionLocal) {
				UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
				if (null != userDetailInfo) {
					List<Role> roleList = userDetailInfo.getRoleList();
					for (int i = 0; i < roleList.size(); i++) {
						if (roleList.get(i).getCode().equals("BusinessSys_fgscw") ) {
							isRightOperate = true ;
						}
					}
				}
			}
			QueryBaseBatchResult<BillSettlementApplyFormInfo> batchResult = billSettlementApplyService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("status", ProjectStatusEnum.getAllEnum());
			model.addAttribute("isRightOperate", isRightOperate);
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBillSettlementApply.vm";
	}
	
	
	public boolean condition(HttpServletRequest request,String businessId){
		boolean flag = false ;
		if (null == request.getSession().getAttribute("businessBillId") || !request.getSession().getAttribute("businessBillId").equals(businessId) ) {
			flag = !flag;
		}
		return flag;
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

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
    HttpServletRequest request, Model model) {
    	//取得表单信息
		FormInfo form = formService.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
					"获取表单信息出错");
		}
		BillSettlementApplyInfo info = billSettlementApplyService.findByFormId(formId);
		BillSettlementApplyDetailInfo billSettlementApplyDetailInfo = billSettlementApplyDetailService.findBySettlementApplyId(info.getId());
		//结算通知单详情
		ChargeNoticeInfo chargeNoticeInfo = chargeNoticeService.findByBusinessBillId(info.getBusinessBillId());
		BusinessBillInfo businessBillInfo = businessBillService.findById(Integer.parseInt(info.getBusinessBillId()));
		List<BillPayFeeDetailInfo> payFeeDetails = billPayFeeDetailService.findBySettlementApplyId(info.getId());
		model.addAttribute("info", info);
		model.addAttribute("billSettlementApplyDetailInfo", billSettlementApplyDetailInfo);
		model.addAttribute("form", form);
		model.addAttribute("businessBillInfo", businessBillInfo);
		model.addAttribute("edit", true);
		model.addAttribute("chargeNoticeInfo", chargeNoticeInfo);
		model.addAttribute("payFeeDetails", payFeeDetails);
		model.addAttribute("result",chargeNoticeInfo.getPayFee().add(chargeNoticeInfo.getPersistencyRateWard()).add(chargeNoticeInfo.getManageGrant()).add(chargeNoticeInfo.getPackageFee().add(chargeNoticeInfo.getInsuranceOther())));
		if (info.getType().equals(InsuranceTypeEnum.LIFEINSURANCE.getMessage())) {
			return VM_PATH + "addLifeBillSettlementApply.vm";
		} 
		
		if (info.getType().equals(InsuranceTypeEnum.CAREINSURANCE.getMessage())) {
			return VM_PATH + "addCarBillSettlementApply.vm";
		} 
		return VM_PATH + "addBillSettlementApply.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BillSettlementApplyOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "billSettlementApply";
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setBusinessBillId(order.getBusinessBillId());
			order.setSettlementNo(getBillNo());
			order.setFormCode(FormCodeEnum.JSSQ);
			InsuranceBaseResult result = billSettlementApplyService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

	/**
	 * 保单结算清单详情
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping("view.htm")
    public String view(@RequestParam(value = "formId", required = false, defaultValue = "0") long formId, HttpServletRequest request, Model model) {
    	FormInfo formInfo = formService.findByFormId(formId);
    	if (null != formInfo ) {
    		BillSettlementApplyInfo billSettlementApplyInfo = billSettlementApplyService.findByFormId(formId);
    		String businessBillId = billSettlementApplyInfo.getBusinessBillId();
    		if (!StringUtil.isBlank(businessBillId)) {
    			BusinessBillInfo businessBillInfo = businessBillService.findById(Integer.parseInt(businessBillId));
    			long id = billSettlementApplyInfo.getId();
    			BillSettlementApplyDetailInfo BillSettlementApplyDetailInfo = billSettlementApplyDetailService.findBySettlementApplyId(id);
    			List<BillPayFeeDetailInfo> payFeeDetails = billPayFeeDetailService.findBySettlementApplyId(id);
    			model.addAttribute("businessBillInfo",businessBillInfo);
    			model.addAttribute("form",formInfo);
    			//结算通知单详情
    			ChargeNoticeInfo chargeNoticeInfo = chargeNoticeService.findByBusinessBillId(businessBillId);
    			//结算清单详情
    			model.addAttribute("detailInfo", BillSettlementApplyDetailInfo);
    			//费用支付清单
    			model.addAttribute("payFeeDetails", payFeeDetails);
    			model.addAttribute("chargeNoticeInfo", chargeNoticeInfo);
    			model.addAttribute("result",null==chargeNoticeInfo ? 0 :(chargeNoticeInfo.getPayFee().add(chargeNoticeInfo.getPersistencyRateWard()).add(chargeNoticeInfo.getManageGrant()).add(chargeNoticeInfo.getPackageFee().add(chargeNoticeInfo.getInsuranceOther()))));
    			model.addAttribute("info",billSettlementApplyInfo);
    			if (businessBillInfo.getInsuranceTypeName().equals(InsuranceTypeEnum.CAREINSURANCE.getMessage())) {
    				return VM_PATH + "viewBillSettlementApply.vm";
    			}
    			if (businessBillInfo.getInsuranceTypeName().equals(InsuranceTypeEnum.LIFEINSURANCE.getMessage())) {
    				return VM_PATH + "viewBillSettlementApply3.vm";
    			}
    		}
    	}
		return VM_PATH + "viewBillSettlementApply2.vm";
    }


    
	/**
	 * 保单结算清单审核
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) throws Exception {
		FormInfo form = formService.findByFormId(formId);
		if (null != form) {
			BillSettlementApplyInfo bllSettlementApplyInfo = billSettlementApplyService.findByFormId(formId);
			String businessBillId = bllSettlementApplyInfo.getBusinessBillId();
			if (!StringUtils.isBlank(businessBillId)) {
				BusinessBillInfo businessBillInfo = businessBillService.findById(Integer.parseInt(businessBillId));
				model.addAttribute("businessBillInfo",businessBillInfo);
			}
			long id = bllSettlementApplyInfo.getId();
			BillSettlementApplyDetailInfo BillSettlementApplyDetailInfo = billSettlementApplyDetailService.findBySettlementApplyId(id);
			List<BillPayFeeDetailInfo> payFeeDetails = billPayFeeDetailService.findBySettlementApplyId(id);
			model.addAttribute("info", bllSettlementApplyInfo);
			//结算清单详情
			model.addAttribute("detailInfo", BillSettlementApplyDetailInfo);
			//结算通知单详情
			ChargeNoticeInfo chargeNoticeInfo = chargeNoticeService.findByBusinessBillId(bllSettlementApplyInfo.getBusinessBillId());
			//费用支付清单
			model.addAttribute("payFeeDetails", payFeeDetails);
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("chargeNoticeInfo", chargeNoticeInfo);// 表单信息
			model.addAttribute("result",chargeNoticeInfo.getPayFee().add(chargeNoticeInfo.getPersistencyRateWard()).add(chargeNoticeInfo.getManageGrant()).add(chargeNoticeInfo.getPackageFee().add(chargeNoticeInfo.getInsuranceOther())));
			initWorkflow(model, form, request.getParameter("taskId"));
			if (bllSettlementApplyInfo.getType().equals(InsuranceTypeEnum.CAREINSURANCE.getMessage())) {
				return VM_PATH + "auditForm.vm";
			}
			if (bllSettlementApplyInfo.getType().equals(InsuranceTypeEnum.LIFEINSURANCE.getMessage())) {
				return VM_PATH + "auditForm3.vm";
			}
		}
		return VM_PATH + "auditForm2.vm";
	}

}
