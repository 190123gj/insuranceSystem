package com.born.insurance.web.controller.claimSettlementProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.claimSettlementProcess.ClaimSettlementProcessOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.claimSettlement.ClaimSettlementInfo;
import com.born.insurance.ws.info.claimSettlementProcess.ClaimSettlementProcessInfo;
import com.born.insurance.ws.order.claimSettlementProcess.ClaimSettlementProcessQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;

import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.claimSettlement.ClaimSettlementService;
import com.born.insurance.ws.service.claimSettlementProcess.ClaimSettlementProcessService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/claimSettlementProcess")
public class ClaimSettlementProcessController extends BaseController {
	@Autowired
	protected ClaimSettlementProcessService claimSettlementProcessService;
	
	@Autowired
	protected ClaimSettlementService claimSettlementService;
	
	@Autowired
	protected BusinessBillService businessBillService;
	
	private final static String VM_PATH = "/insurance/claimSettlementProcess/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "processDate" };
	}
	/**
	* 风险预警处理表
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("scheduleMaintenance.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			ClaimSettlementProcessQueryOrder queryOrder = new ClaimSettlementProcessQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			String id=request.getParameter("claimSettlementId");
    		long claimSettlementId=NumberUtil.parseLong(id);
    		queryOrder.setSettlementProcessId(claimSettlementId);
    		ClaimSettlementInfo claimSettlementInfo = claimSettlementService.findById(claimSettlementId);
    		if (null != claimSettlementInfo) {
    			BusinessBillInfo businessBillInfo = businessBillService.findById(claimSettlementInfo.getBusinessBillId());
    			if (null != businessBillInfo) {
    				claimSettlementInfo.setInsuranceNo(businessBillInfo.getInsuranceNo());
    			}
    		}
    		//附件列表
    		queryCommonAttachmentData(model, id,CommonAttachmentTypeEnum.CLAIMSETTLEMENT);
    		QueryBaseBatchResult<ClaimSettlementProcessInfo> batchResult = claimSettlementProcessService.queryList(queryOrder);
    		model.addAttribute("claimSettlementInfo", claimSettlementInfo);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
			model.addAttribute("claimSettlementId", claimSettlementId);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "scheduleMaintenance.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "settlementProcessId", required = false, defaultValue = "0") long settlementProcessId,
    HttpServletRequest request, Model model) {
		ClaimSettlementProcessInfo info = null;
		if (settlementProcessId > 0) {
			info = claimSettlementProcessService.findById(settlementProcessId);
			model.addAttribute("info", info);
	    }else{
			info = new ClaimSettlementProcessInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addClaimSettlementProcess.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, ClaimSettlementProcessOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "claimSettlementProcess";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = claimSettlementProcessService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long settlementProcessId, HttpServletRequest request, Model model) {
		ClaimSettlementProcessInfo claimSettlementProcessInfo = claimSettlementProcessService.findById(settlementProcessId);
		model.addAttribute("info",claimSettlementProcessInfo);
		return VM_PATH + "viewClaimSettlementProcess.vm";
    }
    
    
    @ResponseBody
    @RequestMapping("addCheduleMaintenance.json")
    public Object editsCheduleMaintenance(HttpServletRequest request,
			HttpServletResponse response, Model model,ClaimSettlementProcessOrder claimSettlementProcessOrder){
    	JSONObject jsonobj = new JSONObject();
		WebUtil.setPoPropertyByRequest(claimSettlementProcessOrder, request);
		String id=request.getParameter("claimSettlementId");
		long claimSettlementId = NumberUtil.parseLong(id);
		claimSettlementProcessOrder.setClaimSettlementId(claimSettlementId);
		InsuranceBaseResult result=null;
		result=claimSettlementProcessService.save(claimSettlementProcessOrder);
		if(result.isSuccess()){
			jsonobj.put("success", true);
			jsonobj.put("message", "执行成功");
		}else{
			jsonobj.put("success", false);
			jsonobj.put("message", "执行失败");
		}
		return jsonobj;
    	
    }
}
