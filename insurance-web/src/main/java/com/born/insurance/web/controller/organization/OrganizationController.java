package com.born.insurance.web.controller.organization;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.cooperation.CooperativeInstitutionService;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CompanyTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.SexEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.customer.CustomerPersonInfo;
import com.born.insurance.ws.info.customer.CustomerRelationInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;

@Controller
@RequestMapping("/insurance/organization")
public class OrganizationController extends BaseController {
	
	@Autowired
	private CustomerCompanyService customerCompanyService;
	
	@Autowired
	private CooperativeInstitutionService cooperativeInstitutionService;
	
	@Autowired
	private BaseDataInfoService baseDataInfoService;
	
	@Autowired
	private CustomerPersonService customerPersonService;
	
	private final String VM_PATH = "/insurance/organization/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "certExpDate" };
	}

	/**
	 * 查询所有的机构信息
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		CustomerCompanyQueryOrder customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
		// 从页面的request去除分页参数传到order里面
		WebUtil.setPoPropertyByRequest(customerCompanyQueryOrder, request);
		customerCompanyQueryOrder.setIsThirdParty("YES");
		String orgType = request.getParameter("orgType");
		customerCompanyQueryOrder.setCustomerType(CustomerTypeEnum.getByCode(orgType));
		QueryBaseBatchResult<CustomerCompanyInfo> baseResult = customerCompanyService
			.queryList(customerCompanyQueryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(baseResult));
		model.addAttribute("querykOrder", customerCompanyQueryOrder);
		return VM_PATH + "listOrganization.vm";
	}
	
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		return VM_PATH + "selectTypeAdd.vm";
	}
	
	@RequestMapping("selectType.htm")
	public String selectType(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = request.getParameter("orgType");
		model.addAttribute("certType", CertTypeEnum.getCompanyAllEnum());
		if (type.equals("INSURANCE_INSTITUTIONS")) {
			model.addAttribute("companyType", CompanyTypeEnum.getAllEnum());
			return VM_PATH + "addInsuranceOrganization.vm";
		} else {
			return VM_PATH + "addThirdOrganization.vm";
		}
	}
	
	/**
	 * 提交新增合作机构用户信息
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
		JSONObject jsonobj = new JSONObject();
		WebUtil.setPoPropertyByRequest(customerCompanyOrder, request);
		String certType = request.getParameter("certTypeText");
		setSessionLocalInfoCreatorOrder(customerCompanyOrder);
		customerCompanyOrder.setCertType(CertTypeEnum.getByCode(certType));
		String customerTypeText = request.getParameter("customerTypeText");
		customerCompanyOrder.setCustomerType(CustomerTypeEnum.getByCode(customerTypeText));
		InsuranceBaseResult result = customerCompanyService
			.addCustomerCompany(customerCompanyOrder);
		if (result.isSuccess()) {
			jsonobj.put("code", "1");
			jsonobj.put("message", "执行成功");
		} else {
			jsonobj.put("code", "0");
			jsonobj.put("message", "执行失败");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("addOrganizationMemberSubmit.json")
	public Object addOrganizationMemberSubmit(HttpServletRequest request,
												HttpServletResponse response, Model model,
												CustomerPersonOrder customerPersonOrder) {
		JSONObject jsonobj = new JSONObject();
		WebUtil.setPoPropertyByRequest(customerPersonOrder, request);
		setSessionLocalInfoCreatorOrder(customerPersonOrder);
		String id = request.getParameter("userId");
		String userName = request.getParameter("userName");
		long userId = NumberUtil.parseLong(id);
		String certType = request.getParameter("certType");
		String sex = request.getParameter("sex");
		customerPersonOrder.setCertType(CertTypeEnum.getByCode(certType));
		customerPersonOrder.setParentId(userId);
		customerPersonOrder.setParentName(userName);
		customerPersonOrder.setCustomerType(CustomerTypeEnum.INDIVIDUAL_CUSTOMER);
		InsuranceBaseResult result = customerPersonService.addCustomerPerson(customerPersonOrder);
		//InsuranceBaseResult result=customerPersonService.addRelationBaseInfo(companyRelationInfoOrder)
		if (result.isSuccess()) {
			jsonobj.put("code", "1");
			jsonobj.put("message", "执行成功");
		} else {
			jsonobj.put("code", "0");
			jsonobj.put("message", "执行失败");
		}
		return jsonobj;
	}
	
	/**
	 * 修改提交合作机构用户信息
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
		JSONObject jsonobj = new JSONObject();
		WebUtil.setPoPropertyByRequest(customerCompanyOrder, request);
		setSessionLocalInfoCreatorOrder(customerCompanyOrder);
		String certType = request.getParameter("certTypeText");
		customerCompanyOrder.setCertType(CertTypeEnum.getByCode(certType));
		InsuranceBaseResult result = customerCompanyService
			.updateCustomerCompany(customerCompanyOrder);
		if (result.isSuccess()) {
			jsonobj.put("code", "1");
			jsonobj.put("message", "执行成功");
		} else {
			jsonobj.put("code", "0");
			jsonobj.put("message", "执行失败");
		}
		return jsonobj;
	}
	
	/**
	 * 修改提交合作机构用户信息
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editPersonSubmit.json")
	public Object editPersonSubmit(HttpServletRequest request, HttpSession session, Model model,
									CustomerPersonOrder customerPersonOrder) {
		JSONObject jsonobj = new JSONObject();
		WebUtil.setPoPropertyByRequest(customerPersonOrder, request);
		String certType = request.getParameter("certType");
		String parId = request.getParameter("parentId");
		String parentName = request.getParameter("parentName");
		long parentId = NumberUtil.parseLong(parId);
		customerPersonOrder.setParentId(parentId);
		customerPersonOrder.setParentName(parentName);
		customerPersonOrder.setCertType(CertTypeEnum.getByCode(certType));
		customerPersonOrder.setCustomerType(CustomerTypeEnum.INDIVIDUAL_CUSTOMER);
		setSessionLocalInfoCreatorOrder(customerPersonOrder);
		InsuranceBaseResult res = null;
		res = customerPersonService.updateCustomerPerson(customerPersonOrder);
		if (res.isSuccess()) {
			jsonobj.put("code", "1");
			jsonobj.put("message", "执行成功");
		} else {
			jsonobj.put("code", "0");
			jsonobj.put("message", "执行失败");
		}
		return jsonobj;
	}

	/**
	 * 查询指定用户详细信息用于修改
	 * @param userId
	 * @param model
     * @return
     */
	@RequestMapping("edit.htm")
	public String edit(@RequestParam(value = "userId", required = false, defaultValue = "0") long userId,
						Model model) {
		try {
			CustomerCompanyInfo customerCompanyInfo = customerCompanyService
				.queryCustomerCompanyByUserId(userId);
			model.addAttribute("customerCompanyInfo", customerCompanyInfo);
			model.addAttribute("certType", CertTypeEnum.getAllEnum());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VM_PATH + "editThirdOrganization.vm";
	}

	/**
	 * 查询指定用户详细信息
	 * @param userId
	 * @param model
     * @return
     */
	@RequestMapping("info.htm")
	public String info(	@RequestParam(value = "userId", required = false, defaultValue = "0") long userId,
						Model model) {
		try {
			CustomerCompanyInfo info = customerCompanyService.queryCustomerCompanyByUserId(userId);
			model.addAttribute("info", info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VM_PATH + "infoOrganization.vm";
	}

	/**
	 * 查询指定机构的成员信息
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("organizationPersonnelManagement.htm")
	public String memberList(HttpServletRequest request, HttpServletResponse response, Model model) {
		CustomerPersonQueryOrder customerPersonQueryOrder = new CustomerPersonQueryOrder();
		String id = request.getParameter("userId");
		long userId = NumberUtil.parseLong(id);
		String userName = request.getParameter("userName");
		customerPersonQueryOrder.setUserId(userId);
		QueryBaseBatchResult<CustomerRelationInfo> reslut = cooperativeInstitutionService
			.querySubordinatePersonnelByCondition(customerPersonQueryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(reslut));
		model.addAttribute("userId", id);
		model.addAttribute("userName", userName);
		return VM_PATH + "organizationMemberList.vm";
	}
	
	private List<BaseDataInfoInfo> getBaseDataInfoInfos(BaseDataInfoTypeEnum typeEnum) {
		BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
		queryOrder.setPageSize(99);
		queryOrder.setType(typeEnum);
		return baseDataInfoService.queryList(queryOrder).getPageList();
	}
	
	@RequestMapping("addOrganizationMember.htm")
	public String addOrganizationMember(HttpServletRequest request, HttpServletResponse response,
										Model model) {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		model.addAttribute("userName", userName);
		model.addAttribute("userId", userId);
		model.addAttribute("certTypes", CertTypeEnum.getAllEnum());
		model.addAttribute("sexs", SexEnum.getAllEnum());
		model.addAttribute("mobileTypes",
			getBaseDataInfoInfos(BaseDataInfoTypeEnum.CONTACT_MOBILE_TYPE));
		return VM_PATH + "addOrganizationMember.vm";
	}
	
	/**
	 * 查询指定用户详细信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("personInfo.htm")
	public String personInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			CustomerPersonInfo info = customerPersonService
				.queryCustomerPersonByUserId(NumberUtil.parseLong(request.getParameter("userId")));
			model.addAttribute("certTypeList", CertTypeEnum.getAllEnum());
			model.addAttribute("mobileTypes",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CONTACT_MOBILE_TYPE));
			model.addAttribute("customerRelation",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			model.addAttribute("sexs", getBaseDataInfoInfos(BaseDataInfoTypeEnum.SEX));
			model.addAttribute("info", info);
		} catch (Exception e) {
			logger.error("查询指定个人客户信息失败", e);
		}
		return VM_PATH + "infoOrganizationPerson.vm";
	}
	
	/**
	 * 查询指定用户详细信息用于修改
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("personEdit.htm")
	public String personEdit(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String parentId = request.getParameter("parentId");
			String parentName = request.getParameter("parentName");
				CustomerPersonInfo info = customerPersonService
				.queryCustomerPersonByUserId(NumberUtil.parseLong(request.getParameter("userId")));
			//附件列表
			queryCommonAttachmentData(model, info.getUserId() + "",
				CommonAttachmentTypeEnum.PERSON_ATTACH);
			model.addAttribute("info", info);
			model.addAttribute("mobileTypes",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CONTACT_MOBILE_TYPE));
			model.addAttribute("customerRelation",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			model.addAttribute("sexs", getBaseDataInfoInfos(BaseDataInfoTypeEnum.SEX));
			model.addAttribute("certTypeList", CertTypeEnum.getAllEnum());
			model.addAttribute("parentId", parentId);
			model.addAttribute("parentName", parentName);
		} catch (Exception e) {
			logger.error("查询指定个人客户信息失败", e);
		}
		return VM_PATH + "editOrganizationMember.vm";
	}
	
}
