package com.born.insurance.web.controller.claimSettlement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.claimSettlement.ClaimSettlementOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.claimSettlement.ClaimSettlementInfo;
import com.born.insurance.ws.order.claimSettlement.ClaimSettlementQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;

import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.claimSettlement.ClaimSettlementService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/claimSettlement")
public class ClaimSettlementController extends BaseController {
	
	@Autowired
	protected ClaimSettlementService claimSettlementService;
	
	@Autowired
	protected BusinessBillService businessBillService;
	
	private final static String VM_PATH = "/insurance/claimSettlement/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "dangerDate","startTime","endTime" };
	}

	/**
	* 理赔服务列表
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			ClaimSettlementQueryOrder queryOrder = new ClaimSettlementQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<ClaimSettlementInfo> batchResult = claimSettlementService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listClaimSettlement.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "claimSettlementId", required = false, defaultValue = "0") long claimSettlementId,
    HttpServletRequest request, Model model) {
		ClaimSettlementInfo info = null;
		if (claimSettlementId > 0) {
			info = claimSettlementService.findById(claimSettlementId);
			model.addAttribute("info", info);
	    }else{
			info = new ClaimSettlementInfo();
			model.addAttribute("info", info);
		}
		//附件列表
		queryCommonAttachmentData(model, claimSettlementId+ "",CommonAttachmentTypeEnum.CLAIMSETTLEMENT);
		return VM_PATH + "addClaimSettlement.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, ClaimSettlementOrder order) {
		JSONObject json = new JSONObject();
		String tipPrefix = "claimSettlement";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = claimSettlementService.save(order);
			//添加附件
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.CLAIMSETTLEMENT);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long claimSettlementId, HttpServletRequest request, Model model) {
		ClaimSettlementInfo claimSettlementInfo = claimSettlementService.findById(claimSettlementId);
		model.addAttribute("info",claimSettlementInfo);
		return VM_PATH + "viewClaimSettlement.vm";
    }
    
    
    
    @RequestMapping("info.htm")
    public String info(HttpServletRequest request,HttpServletResponse response, Model model){
    	try {
    		String id=request.getParameter("claimSettlementId");
    		long claimSettlementId=NumberUtil.parseLong(id);
    		ClaimSettlementInfo claimSettlementInfo = claimSettlementService.findById(claimSettlementId);
    		if (null != claimSettlementInfo) {
    			BusinessBillInfo businessBillInfo = businessBillService.findById(claimSettlementInfo.getBusinessBillId());
    			if (null != businessBillInfo) {
    				claimSettlementInfo.setInsuranceNo(businessBillInfo.getInsuranceNo());
    			}
    		}
    		//附件列表
    		queryCommonAttachmentData(model, id,CommonAttachmentTypeEnum.CLAIMSETTLEMENT);
    		model.addAttribute("claimSettlementInfo", claimSettlementInfo);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return VM_PATH + "infoClaimSettlement.vm";
    }
}
