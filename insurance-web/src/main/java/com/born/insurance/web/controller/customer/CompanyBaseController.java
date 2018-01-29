package com.born.insurance.web.controller.customer;

import javax.servlet.http.HttpServletRequest;

import com.born.insurance.ws.info.customer.CustomerInfoTraceInfo;
import com.born.insurance.ws.order.customer.CustomerInfoTraceQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.order.customer.CustomerCompanyOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * Created by Administrator on 2017-1-23.
 */
public class CompanyBaseController extends CustomerBaseController {
	@Autowired
	protected CustomerCompanyService customerCompanyService;
	
	protected void queryList(HttpServletRequest request, Model model,CustomerTypeEnum customerTypeEnum) {
		CustomerCompanyQueryOrder customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
		// 从页面的request去除分页参数传到order里面
		WebUtil.setPoPropertyByRequest(customerCompanyQueryOrder, request);
		String certType = request.getParameter("certType");
		customerCompanyQueryOrder.setCertType(CertTypeEnum.getByCode(certType));
		customerCompanyQueryOrder.setCustomerType(customerTypeEnum);
		QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult = customerCompanyService
			.queryList(customerCompanyQueryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(baseBatchResult));
		model.addAttribute("querykOrder", customerCompanyQueryOrder);
		model.addAttribute("certTypeList", CertTypeEnum.getCompanyAllEnum());
	}
	
	protected void queryInfo(long userId, Model model) {
		CustomerCompanyInfo info = customerCompanyService.queryCustomerCompanyByUserId(userId);
		model.addAttribute("info", info);
		model.addAttribute("certType", CertTypeEnum.getCompanyAllEnum());
		CustomerInfoTraceQueryOrder customerInfoTraceQueryOrder = new CustomerInfoTraceQueryOrder();
		customerInfoTraceQueryOrder.setCustomerUserId(userId);
		customerInfoTraceQueryOrder.setPageSize(2);
		customerInfoTraceQueryOrder.setSortOrder("desc");
		customerInfoTraceQueryOrder.setSortCol("raw_add_time");
		QueryBaseBatchResult<CustomerInfoTraceInfo> traceInfo= customerInfoTraceService
				.querycustomerInfoTraceInfoByCondition(customerInfoTraceQueryOrder);
		model.addAttribute("traceInfo",traceInfo.getPageList());

	}
	
	protected JSONObject editSubmit(HttpServletRequest request,
									CustomerCompanyOrder customerCompanyOrder) {
		JSONObject jsonobj = new JSONObject();
		try {
			WebUtil.setPoPropertyByRequest(customerCompanyOrder, request);
			InsuranceBaseResult res = customerCompanyService
				.updateCustomerCompany(customerCompanyOrder);
			toJSONResult(jsonobj, res,"编辑成功",null);
		} catch (Exception e) {
			logger.error("编辑企业客户", e);
			jsonobj.put("success", false);
			jsonobj.put("message", "编辑企业客户失败");
		}
		return jsonobj;
	}
	
	protected JSONObject addSubmit(HttpServletRequest request,
									CustomerCompanyOrder customerCompanyOrder) {
		JSONObject jsonobj = new JSONObject();
		try {
			WebUtil.setPoPropertyByRequest(customerCompanyOrder, request);
			String certType = request.getParameter("certTypeText");
			String customerType = request.getParameter("customerTypeText");
			setSessionLocalInfoCreatorOrder(customerCompanyOrder);
			customerCompanyOrder.setCertType(CertTypeEnum.getByCode(certType));
			customerCompanyOrder.setCustomerType(CustomerTypeEnum.getByCode(customerType));
			InsuranceBaseResult result = null;
			if (customerCompanyOrder.getUserId() > 0) {
				result = customerCompanyService.updateCustomerCompany(customerCompanyOrder);
			} else {
				result = customerCompanyService.addCustomerCompany(customerCompanyOrder);
			}
			toJSONResult(jsonobj, result,"操作成功",null);
		} catch (Exception e) {
			logger.error("新增企业客户", e);
			jsonobj.put("success", false);
			jsonobj.put("message", "新增企业客户失败");
		}
		
		return jsonobj;
	}
	
}
