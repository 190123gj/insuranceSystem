package com.born.insurance.web.controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.order.customer.CustomerCompanyOrder;

@Controller
@RequestMapping("/insurance/systemOrganization")
public class SystemOrganizationController extends CompanyBaseController {
	

	private final String VM_PATH = "/insurance/customerManage/systemOrganization/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "certExpDate", "birthDay" };
	}
	
	/**
	 * 查询所有的企业客户信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			queryList(request, model, CustomerTypeEnum.SYSTEM_ORGANIZATION);
		} catch (Exception e) {
			logger.error("企业客户", e);
		}
		return VM_PATH + "listSystemOrganization.vm";
	}
	
	/**
	 * 查询指定用户详细信息
	 * @param userId
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("info.htm")
	public String info(	@RequestParam(value = "userId", required = false, defaultValue = "0") long userId,
						HttpServletResponse response, Model model) {
		try {
			queryInfo(userId, model);
		} catch (Exception e) {
			logger.error("企业客户详情", e);
		}
		return VM_PATH + "infoSystemOrganization.vm";
	}
	
	/**
	 * 查询指定用户详细信息用于修改
	 * @param userId
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "userId", required = false, defaultValue = "0") long userId,
						HttpServletResponse response, Model model) {
		try {
			queryInfo(userId, model);
		} catch (Exception e) {
			logger.error("企业客户详情", e);
		}
		return VM_PATH + "addSystemOrganization.vm";
	}
	
	/**
	 * 提交修改企业用户信息
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, HttpSession session, Model model,
								CustomerCompanyOrder customerCompanyOrder) {
		return editSubmit(request, customerCompanyOrder);
	}
	
	/**
	 * 提交新增/修改企业用户信息
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addSubmit.json")
	public Object addSubmit(HttpServletRequest request, HttpSession session, Model model,
							CustomerCompanyOrder customerCompanyOrder) {
		return addSubmit(request, customerCompanyOrder);
	}
	
	/**
	 * 跳转新增页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("certType", CertTypeEnum.getCompanyAllEnum());
		CustomerCompanyInfo info = new CustomerCompanyInfo();
		model.addAttribute("info",info);
		return VM_PATH + "addSystemOrganization.vm";
	}
	
	/**
	 * 查询指定企业客户跟踪信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("track.htm")
	public String traceInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		queryCustomerTrack(request, model);
		return VM_PATH + "listCompanyTrace.vm";
	}
	
	/**
	 * 添加指定企业客户跟踪信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addCustomerTrace.json")
	public Object addCustomerTrace(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		return addCustomerTrace(request);
	}
}
