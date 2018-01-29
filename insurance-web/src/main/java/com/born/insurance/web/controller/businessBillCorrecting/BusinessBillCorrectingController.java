package com.born.insurance.web.controller.businessBillCorrecting;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.common.DateSeqService;
import com.born.insurance.util.StringUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.order.businessBillCorrecting.BusinessBillCorrectingOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.ProjectStatusEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.info.businessBillCorrecting.BusinessBillCorrectingInfo;
import com.born.insurance.ws.order.businessBillCorrecting.BusinessBillCorrectingQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.businessBillCorrecting.BusinessBillCorrectingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/businessBillCorrecting")
public class BusinessBillCorrectingController extends BaseController {
	@Autowired
	protected BusinessBillCorrectingService businessBillCorrectingService;
	
	@Autowired
	protected DateSeqService dateSeqService;
	private final static String VM_PATH = "/insurance/businessBillCorrecting/";

	/**
	* 保单批改
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			BusinessBillCorrectingQueryOrder queryOrder = new BusinessBillCorrectingQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<BusinessBillCorrectingInfo> batchResult = businessBillCorrectingService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("status", ProjectStatusEnum.getAllEnum());
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listBusinessBillCorrecting.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
    HttpServletRequest request, Model model) {
		BusinessBillCorrectingInfo info = null;
		if (id > 0) {
			info = businessBillCorrectingService.findById(id);
			model.addAttribute("info", info);
	    }else{
			info = new BusinessBillCorrectingInfo();
			info.setBusinessBillCorrectingNo(getBusinessBillCorrectingNo());
			model.addAttribute("info", info);
		}
		return VM_PATH + "addBusinessBillCorrecting.vm";
	}
    
    
	/**
	 * 业务单号
	 * @return
	 */
	private synchronized String getBusinessBillCorrectingNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String billNo = "PD";
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
		String seqName = SysDateSeqNameEnum.BUSINESS_BILL_CORRECTING.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		str += StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return str;
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, BusinessBillCorrectingOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "businessBillCorrecting";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = businessBillCorrectingService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }
	
	

    @RequestMapping("view.htm")
    public String view(long id, HttpServletRequest request, Model model) {
		BusinessBillCorrectingInfo businessBillCorrectingInfo = businessBillCorrectingService.findById(id);
		model.addAttribute("info",businessBillCorrectingInfo);
		return VM_PATH + "viewBusinessBillCorrecting.vm";
    }


}
