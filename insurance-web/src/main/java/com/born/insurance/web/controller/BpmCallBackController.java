package com.born.insurance.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.controller.base.FormController;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("bpm/callback/")
public class BpmCallBackController extends FormController {
	
	@Autowired
	CustomerPersonService customerPersonService;
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("registerUser.json")
	@ResponseBody
	public Object registerUser(HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			String phone = request.getParameter("mobile");
			String sex = request.getParameter("sex");
			String email = request.getParameter("email");
			String userId = request.getParameter("userId");
			String fullname = request.getParameter("fullname");
			String mobile = request.getParameter("mobile");
			CustomerPersonOrder personOrder = new CustomerPersonOrder();
			personOrder.setContactMobile(mobile);
			personOrder.setMemberNo(phone);
			personOrder.setOwnerId(NumberUtil.parseLong(userId));
			personOrder.setOwnerUserName(fullname);
			personOrder.setCustomerName(fullname);
			personOrder.setSex("1".equals(sex) ? "男" : "女");
			personOrder.setCustomerType(CustomerTypeEnum.INDIVIDUAL_CUSTOMER);
			CustomerBaseInfo baseInfo = customerPersonService.queryCustomerPersonByOwnerId(personOrder.getOwnerId());
			if(baseInfo != null){
				personOrder.setUserId(baseInfo.getUserId());
				customerPersonService.updateCustomerPerson(personOrder);
			}else{
				customerPersonService.addCustomerPerson(personOrder);
			}
			jsonObject.put("code", "1");
			jsonObject.put("success", true);
			jsonObject.put("message", "处理成功");
			
		} catch (Exception e) {
			jsonObject.put("code", "0");
			jsonObject.put("message", "BPM回调处理出错");
			logger.error("BPM-registerUser-回调处理出错", e);
		}
		
		return jsonObject;
	}
}
