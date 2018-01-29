package com.born.insurance.web.controller.personCommission;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.util.DateUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BusinessUserTypeEnum;
import com.born.insurance.ws.info.personCommission.PersonCommissionInfo;
import com.born.insurance.ws.info.personCommissionDetail.PersonCommissionDetailInfo;
import com.born.insurance.ws.order.personCommission.PersonCommissionOrder;
import com.born.insurance.ws.order.personCommission.PersonCommissionQueryOrder;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.personCommission.PersonCommissionService;
import com.born.insurance.ws.service.personCommissionDetail.PersonCommissionDetailService;
import com.yjf.common.lang.util.money.Money;

import rop.thirdparty.com.alibaba.fastjson.JSON;
import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/personCommission")
public class PersonCommissionController extends BaseController {
	@Autowired
	protected PersonCommissionService personCommissionService;
	
	@Autowired
	protected PersonCommissionDetailService personCommissionDetailService;
	
	private final static String VM_PATH = "/insurance/personCommission/";

	/**
	* 业务员佣金结算列表
	*
	* @param request
	* @param model
	* @return
	*/
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			PersonCommissionQueryOrder queryOrder = new PersonCommissionQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<PersonCommissionInfo> batchResult = personCommissionService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("businessUserType", BusinessUserTypeEnum.getAllEnum());
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listPersonCommission.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "commissionId", required = false, defaultValue = "0") long commissionId,
    HttpServletRequest request, Model model) {
		PersonCommissionInfo info = null;
		if (commissionId > 0) {
			info = personCommissionService.findById(commissionId);
			model.addAttribute("info", info);
	    }else{
			info = new PersonCommissionInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addPersonCommission.vm";
	}
    
    
    
    

	@ResponseBody
	@RequestMapping("edit.json")
	public Object editSubmit(HttpServletRequest request, PersonCommissionOrder order) {
		JSONObject json = new JSONObject();
		String tipPrefix = "结算金额申请";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = personCommissionService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

	public String getTime(String times){
		String pattern = "\\d";
		// 创建 Pattern 对象
	    Pattern r = Pattern.compile(pattern);
		String[] str = times.split("-");
		String year = str[0];
		String month = str[1];
		String day = str[2];
		StringBuffer sb = new StringBuffer(year).append("-");
		 Matcher m = r.matcher(month);
		 Matcher m1 = r.matcher(day);
		 if (!m.matches()) {
			 sb.append("0").append(month).append("-");
		 } else {
			 sb.append(month).append("-");
		 }
		 if (!m1.matches()) {
			 sb.append("0").append(day);
		 } else {
			 sb.append(day);
		 }
		 return sb.toString();
	}
	/**
	 * 查看佣金详情
	 * @param commissionId
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping("view.htm")
    public String view(HttpServletRequest request, Model model) throws Exception {
		PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder = new PersonCommissionDetailQueryOrder();
		//如果起始时间为null，默认当前月份
		//查询业务员的佣金明细
		WebUtil.setPoPropertyByRequest(personCommissionDetailQueryOrder, request);
		setSessionLocalInfo2Order(personCommissionDetailQueryOrder);
		if (StringUtils.isBlank(personCommissionDetailQueryOrder.getBeginDate())) {
			personCommissionDetailQueryOrder.setBeginDate(DateUtil.getCurrMonthFirstDay(new Date()));
			personCommissionDetailQueryOrder.setEndDate(DateUtil.getCurrentMonthEndDay());
		}
		PersonCommissionInfo personCommissionInfo = personCommissionService.findById(personCommissionDetailQueryOrder.getCommissionId());
		QueryBaseBatchResult<PersonCommissionDetailInfo> batchResult = personCommissionDetailService.queryList(personCommissionDetailQueryOrder);
		//佣金收入
		Money incomeTotal = personCommissionDetailService.getInComeTotalSum(personCommissionDetailQueryOrder);
		//佣金提取
		Money extractTotal = personCommissionDetailService.getExtractTotalSum(personCommissionDetailQueryOrder);
		//上期转结 查询区间段数据的前一天的数据
		Money lastTotal = personCommissionDetailService.getLastTotal(personCommissionDetailQueryOrder);;
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("info",personCommissionInfo);
		model.addAttribute("incomeTotal", incomeTotal.getAmount());
		model.addAttribute("extractTotal",extractTotal.getAmount());
		model.addAttribute("lastTotal",lastTotal.getAmount());
	/*	List<String> xAxisData = personCommissionDetailService.getXAxisData(personCommissionDetailQueryOrder);
		List<BigDecimal> positiveData = personCommissionDetailService.getPositiveData(personCommissionDetailQueryOrder,xAxisData);
		List<BigDecimal> negativeData = personCommissionDetailService.getNegativexData(personCommissionDetailQueryOrder,xAxisData);
		model.addAttribute("xAxisData",JSON.toJSONString(xAxisData).replace("\"", "'"));
		model.addAttribute("positiveData",JSON.toJSONString(positiveData).replace("\"", "'"));
		model.addAttribute("negativeData",JSON.toJSONString(negativeData).replace("\"", "'"));*/
		model.addAttribute("personCommissionDetailQueryOrder",personCommissionDetailQueryOrder);
		return VM_PATH + "viewPersonCommission.vm";
    }
}
