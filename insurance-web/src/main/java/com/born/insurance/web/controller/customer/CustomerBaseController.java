package com.born.insurance.web.controller.customer;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.track.CustomerInfoTraceService;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.customer.CustomerInfoTraceInfo;
import com.born.insurance.ws.order.customer.CustomerInfoTraceOrder;
import com.born.insurance.ws.order.customer.CustomerInfoTraceQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-1-23.
 */
public class CustomerBaseController extends BaseController {
	
	@Autowired
	protected CustomerInfoTraceService customerInfoTraceService;
	
	protected void queryCustomerTrack(HttpServletRequest request, Model model) {
		try {
			CustomerInfoTraceQueryOrder customerInfoTraceQueryOrder = new CustomerInfoTraceQueryOrder();
			WebUtil.setPoPropertyByRequest(customerInfoTraceQueryOrder, request);
			String id = request.getParameter("userId");
			String userName = request.getParameter("customerUserName");
			long userId = NumberUtil.parseLong(id);
			customerInfoTraceQueryOrder.setCustomerUserId(userId);
			QueryBaseBatchResult<CustomerInfoTraceInfo> info = customerInfoTraceService
				.querycustomerInfoTraceInfoByCondition(customerInfoTraceQueryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(info));
			model.addAttribute("userName", userName);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error("查询指定企业客户跟踪信息", e);
		}
	}
	
	protected JSONObject addCustomerTrace(HttpServletRequest request) {
		JSONObject jsonobj = new JSONObject();
		try {
			CustomerInfoTraceOrder customerInfoTraceOrder = new CustomerInfoTraceOrder();
			WebUtil.setPoPropertyByRequest(customerInfoTraceOrder, request);
			setSessionLocalInfoCreatorOrder(customerInfoTraceOrder);
			String id = request.getParameter("userId");
			String userName = request.getParameter("userName");
			long userId = NumberUtil.parseLong(id);
			customerInfoTraceOrder.setCustomerUserId(userId);
			customerInfoTraceOrder.setCustomerUserName(userName);
			InsuranceBaseResult result = null;
			result = customerInfoTraceService.addcustomerInfoTraceInfo(customerInfoTraceOrder);
			toJSONResult(jsonobj, result,"添加客户跟踪成功",null);
		} catch (Exception e) {
			logger.error("添加客户跟踪信息失败", e);
            jsonobj.put("success", false);
            jsonobj.put("message", "保存失败");
		}
		
		return jsonobj;
	}
}
