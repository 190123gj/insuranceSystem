package com.born.insurance.web.controller.invoiceRequisition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.ValueTaxInfoService;
import com.born.insurance.dal.daointerface.SettlementCompanyBillProcessDAO;
import com.born.insurance.dal.dataobject.SettlementCompanyBillProcessDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.token.TokenProccessor;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.invoiceRequisition.InvoiceRequisitionOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.info.invoiceRequisition.InvoiceRequisitionInfo;
import com.born.insurance.ws.info.invoiceRequisition.SettlementBillInfo;
import com.born.insurance.ws.order.invoiceRequisition.InvoiceRequisitionQueryOrder;
import com.born.insurance.ws.order.settlementCompanyBill.SettlementCompanyBillQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.invoiceRequisition.InvoiceRequisitionService;
import com.yjf.common.lang.util.money.Money;

import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/invoiceRequisition")
public class InvoiceRequisitionController extends BaseController {
	@Autowired
	protected InvoiceRequisitionService invoiceRequisitionService;
	
	@Autowired
	protected SettlementCompanyBillProcessDAO settlementCompanyBillProcessDAO;
	
	@Autowired
	protected ValueTaxInfoService valueTaxInfoService;
	private final static String VM_PATH = "/insurance/invoiceRequisition/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "applyTime"};
	}


	/**
	* 发票申请单
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			InvoiceRequisitionQueryOrder queryOrder = new InvoiceRequisitionQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InvoiceRequisitionInfo> batchResult = invoiceRequisitionService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInvoiceRequisition.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		InvoiceRequisitionInfo info = null;
		if (id > 0) {
			info = invoiceRequisitionService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new InvoiceRequisitionInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInvoiceRequisition.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InvoiceRequisitionOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "发票申请";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = invoiceRequisitionService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		InvoiceRequisitionInfo invoiceRequisitionInfo = invoiceRequisitionService.findById(id);
		model.addAttribute("info",invoiceRequisitionInfo);
		return VM_PATH + "viewInvoiceRequisition.vm";
    }
    
    /**
     * 
     * @param billNo
     * @param insuranceCompanyName
     * @return
     */
    @ResponseBody
	@RequestMapping("getInvoiceInfo.json")
    public Map<String, Object> getInvoiceInfo(String billNo,String insuranceCompanyId,String insuranceCompanyName ){
    	Map<String, Object> maps = new HashMap<String, Object>();
    	SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			maps.put("applyId", sessionLocal.getUserId());
			maps.put("applyName", sessionLocal.getUserName());
			maps.put("deptName", "");
		}
		//查询保险公司增值税信息
		ValueTaxInfo valueTaxInfo = valueTaxInfoService.findValueTaxInfo(insuranceCompanyId);
		
		SettlementCompanyBillProcessDO settlementCompanyBillProcessDO = settlementCompanyBillProcessDAO.findSettlementCompanyBillProcess(billNo);
		Money totalAmount = settlementCompanyBillProcessDO.getTotalAmount();
		//查询结算的保单数据
    	maps.put("success", true);
    	maps.put("capitalAmount", totalAmount);
    	maps.put("lowercaseAmount", totalAmount);
    	maps.put("billNos", invoiceRequisitionService.getBillInfos(billNo));
    	maps.put("valueTaxInfo", valueTaxInfo);
    	maps.put("insuranceCompanyId", insuranceCompanyId);
    	maps.put("insuranceCompanyName", insuranceCompanyName);
    	maps.put("applyTime", DateUtil.format(new Date(), DateUtil.dtSimpleYmdhms));
    	//查询结算清单
    	List<SettlementBillInfo> settlementBillInfo = invoiceRequisitionService.findSettlementBillInfo(billNo);
    	maps.put("data",settlementBillInfo);
    	
    	return maps;
    }


}
