package com.born.insurance.web.controller.settlementCompanyBillProcess;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.settlementCompanyBill.SettlementCompanyBillDetailInfo;
import com.born.insurance.ws.info.settlementCompanyBill.SettlementCompanyBillInfo;
import com.born.insurance.ws.info.settlementCompanyBillProcess.SettlementCompanyBillProcessInfo;
import com.born.insurance.ws.order.settlementCompanyBillProcess.SettlementCompanyBillProcessQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;

import com.born.insurance.ws.service.settlementCompanyBill.SettlementCompanyBillService;
import com.born.insurance.ws.service.settlementCompanyBillProcess.SettlementCompanyBillProcessService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/settlementCompanyBillProcess")
public class SettlementCompanyBillProcessController extends BaseController {
	
	@Autowired
	protected SettlementCompanyBillService settlementCompanyBillService;
	
	@Autowired
	protected SettlementCompanyBillProcessService settlementCompanyBillProcessService;
	
	@Autowired
	protected CustomerCompanyService customerCompanyService;
	private final static String VM_PATH = "/insurance/settlementCompanyBillProcess/";

	/**
	* 经纪费结算单
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			SettlementCompanyBillProcessQueryOrder queryOrder = new SettlementCompanyBillProcessQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");

			QueryBaseBatchResult<SettlementCompanyBillProcessInfo> batchResult = settlementCompanyBillProcessService.queryList(queryOrder);
			//保险公司
			CustomerCompanyQueryOrder customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
			customerCompanyQueryOrder.setCustomerType(CustomerTypeEnum.INSURANCE_INSTITUTIONS);
			customerCompanyQueryOrder.setPageSize(1000);
			QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult = customerCompanyService
				.queryList(customerCompanyQueryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("institutions", baseBatchResult.getPageList());
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listSettlementCompanyBillProcess.vm";
	}

	/**
	 * 新增结算单
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("addSettle.htm")
	public String addSettle(HttpServletRequest request, Model model) {
			SettlementCompanyBillProcessQueryOrder queryOrder = new SettlementCompanyBillProcessQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			//更改经纪费待结算记录状态和新增经纪费结算单
			settlementCompanyBillService.dealSettlementCompanyBill(queryOrder);
			return "redirect:/insurance/settlementCompanyBillProcess/list.htm";

	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		SettlementCompanyBillProcessInfo info = null;
		if (id > 0) {
			info = settlementCompanyBillProcessService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new SettlementCompanyBillProcessInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addSettlementCompanyBillProcess.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, SettlementCompanyBillProcessOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "settlementCompanyBillProcess";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = settlementCompanyBillProcessService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo = settlementCompanyBillProcessService.findById(id);
		model.addAttribute("info",settlementCompanyBillProcessInfo);
		return VM_PATH + "viewSettlementCompanyBillProcess.vm";
    }

    /**
     * 对结算记录进行拆包操作
     * @param id
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("packet.json")
    public Object packet(String id, HttpServletRequest request, Model model) {
    	JSONObject json = new JSONObject();
    	if (StringUtils.isBlank(id)) {
    		json.put("success", false);
    		json.put("message", "参数不能为空");
    		return json;
    	}
    	SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo = settlementCompanyBillProcessService.findById(Long.valueOf(id));
    	if (null == settlementCompanyBillProcessInfo) {
    		json.put("success", false);
    		json.put("message", "该结算单信息不存在");
    		return json;
    	}
    	try {
    		settlementCompanyBillProcessService.packet(settlementCompanyBillProcessInfo);
			json.put("success",true);
			json.put("message","拆包成功");
		} catch (Exception e) {
			json.put("success",false);
			json.put("message","拆包失败");
		}
    	return json;
    }
    
    
    /**
     * 确认收款操作
     * @param id
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("recieve.json")
    public Object recieve(String id, HttpServletRequest request, Model model) {
    	JSONObject json = new JSONObject();
    	if (StringUtils.isBlank(id)) {
    		json.put("success", false);
    		json.put("message", "参数不能为空");
    		return json;
    	}
    	SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo = settlementCompanyBillProcessService.findById(Long.valueOf(id));
    	if (null == settlementCompanyBillProcessInfo) {
    		json.put("success", false);
    		json.put("message", "该结算单信息不存在");
    		return json;
    	}
    	try {
    		settlementCompanyBillProcessService.recieve(id);
    		//添加附件
			addAttachfile(id + "", request, id+"", null,CommonAttachmentTypeEnum.SETTLEMENT_COMPANY_BILL_RECIEVE);
			json.put("success",true);
			json.put("message","收款成功");
		} catch (Exception e) {
			json.put("success",false);
			json.put("message","收款失败");
		}
    	return json;
    }
    

    /**
     * 对结算记录进行拆包操作
     * @param id
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("detail.json")
    public Object detail(String id, HttpServletRequest request, Model model) {
    	JSONObject json = new JSONObject();
    	SettlementCompanyBillDetailInfo info = new SettlementCompanyBillDetailInfo();
    	List<SettlementCompanyBillInfo> settlementCompanyBillList = null ;
    	if (StringUtils.isBlank(id)) {
    		json.put("success", false);
    		json.put("message", "参数不能为空");
    		return json;
    	}
    	SettlementCompanyBillProcessInfo settlementCompanyBillProcessInfo = settlementCompanyBillProcessService.findById(Long.valueOf(id));
    	if (null == settlementCompanyBillProcessInfo) {
    		json.put("success", false);
    		json.put("message", "该结算单信息不存在");
    		return json;
    	}
    	try {
    		settlementCompanyBillList = settlementCompanyBillService.findSettlementCompanyBills(settlementCompanyBillProcessInfo.getSettlementCompanyIds());
    		info.setBillNo(settlementCompanyBillProcessInfo.getBillNo());
    		info.setDatas(settlementCompanyBillList);
    		json.put("success",true);
			json.put("message","结算单详情查询成功");
			json.put("data", info);
		} catch (Exception e) {
			json.put("success",false);
			json.put("message","结算单详情查询失败");
		}
    	return json;
    }
    

}
