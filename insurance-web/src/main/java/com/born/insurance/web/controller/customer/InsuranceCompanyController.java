package com.born.insurance.web.controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.order.customer.CustomerCompanyOrder;

import java.util.List;

@Controller
@RequestMapping("/insurance/insuranceCompany")
public class InsuranceCompanyController extends CompanyBaseController {

	
	private final String VM_PATH = "/insurance/customerManage/insuranceCompany/";
	
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
			queryList(request, model, CustomerTypeEnum.INSURANCE_INSTITUTIONS);
		} catch (Exception e) {
			logger.error("企业客户", e);
		}
		return VM_PATH + "listInsuranceCompany.vm";
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
		return VM_PATH + "infoInsuranceCompany.vm";
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
		return VM_PATH + "addInsuranceCompany.vm";
	}
	
	/**
	 * 提交修改企业用户信息
	 * 
	 * @param request
	 * @param customerCompanyOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, Model model,
								CustomerCompanyOrder customerCompanyOrder) {
		return editSubmit(request, customerCompanyOrder);
	}
	
	/**
	 * 提交新增/修改企业用户信息
	 * 
	 * @param request
	 * @param customerCompanyOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addSubmit.json")
	public Object addSubmit(HttpServletRequest request,Model model,
							CustomerCompanyOrder customerCompanyOrder) {
		return addSubmit(request, customerCompanyOrder);
	}
	
	/**
	 * 跳转新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	public String add(Model model) {
		model.addAttribute("certType", CertTypeEnum.getCompanyAllEnum());
		CustomerCompanyInfo info = new CustomerCompanyInfo();
		model.addAttribute("info", info);
		return VM_PATH + "addInsuranceCompany.vm";
	}
	
	/**
	 * 查询指定企业客户跟踪信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("track.htm")
	public String traceInfo(HttpServletRequest request, Model model) {
		queryCustomerTrack(request, model);
		return VM_PATH + "listCompanyTrace.vm";
	}


	/**
	 * 树形数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tree.json")
	@ResponseBody
	public JSONArray tree(HttpServletRequest request, Model model) {
		JSONArray finalData = new JSONArray();
		try {
			CustomerCompanyQueryOrder customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
			setSessionLocalInfo2Order(customerCompanyQueryOrder);
			customerCompanyQueryOrder.setPageSize(999);
			customerCompanyQueryOrder.setParentId(NumberUtil.parseLong(request.getParameter("parentId")));
			customerCompanyQueryOrder.setCustomerType(CustomerTypeEnum.INSURANCE_INSTITUTIONS);
			if(customerCompanyQueryOrder.getParentId() == 0){
				customerCompanyQueryOrder.setFirstLevel("YES");
			}
			QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult = customerCompanyService
					.queryList(customerCompanyQueryOrder);
			for (CustomerCompanyInfo info : baseBatchResult.getPageList()) {
				JSONObject json = new JSONObject();
				json.put("id", info.getUserId());
				json.put("name", info.getCustomerName());
				customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
				setSessionLocalInfo2Order(customerCompanyQueryOrder);
				customerCompanyQueryOrder.setPageSize(999);
				customerCompanyQueryOrder.setParentId(info.getUserId());
				customerCompanyQueryOrder.setCustomerType(CustomerTypeEnum.INSURANCE_INSTITUTIONS);
				QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult1 = customerCompanyService
						.queryList(customerCompanyQueryOrder);
				json.put("isParent", ListUtil.isNotEmpty(baseBatchResult1.getPageList()));
				finalData.add(json);
			}
		} catch (Exception e) {
			logger.error("加载数据出错", e);
		}
		return finalData;
	}



	/**
	 * 添加指定企业客户跟踪信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addCustomerTrace.json")
	public Object addCustomerTrace(HttpServletRequest request,Model model
											) {
		 return addCustomerTrace(request);
	}


}
