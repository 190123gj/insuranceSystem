package com.born.insurance.web.controller.chargeNotice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.InsuranceTypeEnum;
import com.born.insurance.ws.enums.ProjectStatusEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.bpm.Role;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeFormInfo;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeOrder;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.chargeNotice.ChargeNoticeService;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/chargeNotice")
public class ChargeNoticeController extends WorkflowBaseController {
	@Autowired
	protected ChargeNoticeService chargeNoticeService;
	private final static String VM_PATH = "/insurance/chargeNotice/";

	/**
	* 车险 非车险 寿险结算通知单
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			boolean isRightOperate = false ;
			ChargeNoticeQueryOrder queryOrder = new ChargeNoticeQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (null != sessionLocal) {
				UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
				if (null != userDetailInfo) {
					List<Role> roleList = userDetailInfo.getRoleList();
					for (int i = 0; i < roleList.size(); i++) {
						if (roleList.get(i).getCode().equals("BusinessSys_fgsywglg") ) {
							isRightOperate = true ;
						}
					}
				}
			}
			QueryBaseBatchResult<ChargeNoticeFormInfo> batchResult = chargeNoticeService.queryFormList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("status", ProjectStatusEnum.getAllEnum());
			model.addAttribute("isRightOperate",isRightOperate);
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listChargeNotice.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
    HttpServletRequest request, Model model) {
		ChargeNoticeInfo info = chargeNoticeService.findByFormId(formId);
		FormInfo form = formService.findByFormId(formId);
		model.addAttribute("info", info);
		model.addAttribute("form", form);
		model.addAttribute("edit", true);
		model.addAttribute("result",info.getPayFee().add(info.getPersistencyRateWard()).add(info.getManageGrant()).add(info.getPackageFee().add(info.getInsuranceOther())));
		if (InsuranceTypeEnum.LIFEINSURANCE.getMessage().equals(info.getInsuranceTypeName())) {
			return VM_PATH + "editLifeViewChargeNotice.vm";
		} 
		
		if (InsuranceTypeEnum.CAREINSURANCE.getMessage().equals(info.getInsuranceTypeName())) {
			return VM_PATH + "editCarViewChargeNotice.vm";
		}
	    
		return VM_PATH + "editViewChargeNotice.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, ChargeNoticeOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "chargeNotice";
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setFormCode(FormCodeEnum.JSTZD);
			InsuranceBaseResult result = chargeNoticeService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(@RequestParam(value = "formId", required = false, defaultValue = "0") long formId, HttpServletRequest request, Model model) {
    	FormInfo form = formService.findByFormId(formId);
    	if (form == null) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
					"获取表单信息出错");
		}
    	ChargeNoticeInfo chargeNoticeInfo = chargeNoticeService.findByFormId(formId);
		model.addAttribute("info",chargeNoticeInfo);
		model.addAttribute("form",form);
		model.addAttribute("result",chargeNoticeInfo.getPayFee().add(chargeNoticeInfo.getPersistencyRateWard()).add(chargeNoticeInfo.getManageGrant()).add(chargeNoticeInfo.getPackageFee().add(chargeNoticeInfo.getInsuranceOther())));
		if (InsuranceTypeEnum.LIFEINSURANCE.getMessage().equals(chargeNoticeInfo.getInsuranceTypeName())) {
			return VM_PATH + "lifeViewChargeNotice.vm";
		} 
		
		if (InsuranceTypeEnum.CAREINSURANCE.getMessage().equals(chargeNoticeInfo.getInsuranceTypeName())) {
			return VM_PATH + "carViewChargeNotice.vm";
		}
		
		return VM_PATH + "viewChargeNotice.vm";
    }

    
    

	/**
	 * 结算通知单审批
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
			ChargeNoticeInfo chargeNoticeInfo = chargeNoticeService.findByFormId(formId);
			model.addAttribute("info", chargeNoticeInfo);
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("result",chargeNoticeInfo.getPayFee().add(chargeNoticeInfo.getPersistencyRateWard()).add(chargeNoticeInfo.getManageGrant()).add(chargeNoticeInfo.getPackageFee().add(chargeNoticeInfo.getInsuranceOther())));
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		return VM_PATH + "auditForm.vm";
	}

}
