package com.born.insurance.web.controller.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.insurance.ws.info.customer.CustomerInfoTraceInfo;
import com.born.insurance.ws.order.customer.CustomerInfoTraceQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.customer.CustomerPersonInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;

@Controller
@RequestMapping("/insurance/customer/person")
public class CustomerPersonController extends CustomerBaseController {
	@Autowired
	CustomerPersonService customerPersonService;
	private final String VM_PATH = "/insurance/customerManage/person/";
	
	@Autowired
	private BaseDataInfoService baseDataInfoService;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "certExpDate", "birthDay" };
	}
	
	/**
	 * 查询所有的个人客户信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			CustomerPersonQueryOrder customerPersonQueryOrder = new CustomerPersonQueryOrder();
			//从页面的request去除分页参数传到order里面
			WebUtil.setPoPropertyByRequest(customerPersonQueryOrder, request);
			customerPersonQueryOrder
				.setCustomerType(CustomerTypeEnum.INDIVIDUAL_CUSTOMER.getCode());
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			customerPersonQueryOrder.setBusinessUserId(sessionLocal.getUserId() + "");
			QueryBaseBatchResult<CustomerPersonInfo> baseBatchResult = customerPersonService
				.queryCustomerPersonByCondition(customerPersonQueryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(baseBatchResult));
			model.addAttribute("queryOrder", customerPersonQueryOrder);
			model.addAttribute("certTypeList", CertTypeEnum.getPersonAllEnum());
		} catch (Exception e) {
			logger.error("查询指定个人客户信息失败", e);
		}
		return VM_PATH + "listPerson.vm";
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
			CustomerPersonInfo info = customerPersonService.queryCustomerPersonByUserId(userId);
			model.addAttribute("certTypeList", CertTypeEnum.getPersonAllEnum());
			model.addAttribute("mobileTypes",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CONTACT_MOBILE_TYPE));
			model.addAttribute("customerRelation",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			model.addAttribute("sexs", getBaseDataInfoInfos(BaseDataInfoTypeEnum.SEX));
			CustomerInfoTraceQueryOrder customerInfoTraceQueryOrder = new CustomerInfoTraceQueryOrder();
			customerInfoTraceQueryOrder.setCustomerUserId(userId);
			customerInfoTraceQueryOrder.setPageSize(2);
			customerInfoTraceQueryOrder.setSortOrder("desc");
			customerInfoTraceQueryOrder.setSortCol("raw_add_time");
			QueryBaseBatchResult<CustomerInfoTraceInfo> traceInfo= customerInfoTraceService
					.querycustomerInfoTraceInfoByCondition(customerInfoTraceQueryOrder);
			model.addAttribute("traceInfo",traceInfo.getPageList());

			model.addAttribute("info", info);
		} catch (Exception e) {
			logger.error("查询指定个人客户信息失败", e);
		}
		return VM_PATH + "infoPerson.vm";
	}
	
	/**
	 * 查询指定用户详细信息用于修改
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("certTypeList", CertTypeEnum.getPersonAllEnum());
		model.addAttribute("mobileTypes",
			getBaseDataInfoInfos(BaseDataInfoTypeEnum.CONTACT_MOBILE_TYPE));
		model.addAttribute("customerRelation",
			getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
		model.addAttribute("sexs", getBaseDataInfoInfos(BaseDataInfoTypeEnum.SEX));
		CustomerPersonInfo info = new CustomerPersonInfo();
		model.addAttribute("info", info);
		return VM_PATH + "addPerson.vm";
	}
	
	private List<BaseDataInfoInfo> getBaseDataInfoInfos(BaseDataInfoTypeEnum typeEnum) {
		BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
		queryOrder.setPageSize(99);
		queryOrder.setType(typeEnum);
		return baseDataInfoService.queryList(queryOrder).getPageList();
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
			CustomerPersonInfo info = customerPersonService.queryCustomerPersonByUserId(userId);
			//附件列表
			queryCommonAttachmentData(model, info.getUserId() + "",
				CommonAttachmentTypeEnum.PERSON_ATTACH);
			model.addAttribute("info", info);
			model.addAttribute("mobileTypes",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CONTACT_MOBILE_TYPE));
			model.addAttribute("customerRelation",
				getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
			model.addAttribute("sexs", getBaseDataInfoInfos(BaseDataInfoTypeEnum.SEX));
			model.addAttribute("certTypeList", CertTypeEnum.getPersonAllEnum());
		} catch (Exception e) {
			logger.error("查询指定个人客户信息失败", e);
		}
		return VM_PATH + "addPerson.vm";
	}
	
	/**
	 * 提交修改个人用户信息
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, HttpSession session, Model model) {
		JSONObject jsonobj = new JSONObject();
		CustomerPersonOrder customerPersonOrder = new CustomerPersonOrder();
		WebUtil.setPoPropertyByRequest(customerPersonOrder, request);
		setSessionLocalInfo2Order(customerPersonOrder);
		InsuranceBaseResult res = customerPersonService.updateCustomerPerson(customerPersonOrder);
		toJSONResult(jsonobj, res, "保存成功", null);
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public Object save(HttpServletRequest request, CustomerPersonOrder customerPersonOrder,
						HttpSession session, Model model) {
		JSONObject jsonobj = new JSONObject();
		try {
			WebUtil.setPoPropertyByRequest(customerPersonOrder, request);
			customerPersonOrder.setCustomerType(CustomerTypeEnum.INDIVIDUAL_CUSTOMER);
			InsuranceBaseResult res = null;
			if (customerPersonOrder.getUserId() > 0) {
				res = customerPersonService.updateCustomerPerson(customerPersonOrder);
			} else {
				res = customerPersonService.addCustomerPerson(customerPersonOrder);
			}
			toJSONResult(jsonobj, res, "保存成功", null);
		} catch (Exception e) {
			logger.error("保存客户信息失败",e);
			jsonobj.put("success", false);
			jsonobj.put("message", "保存成功失败");
		}
		
		return jsonobj;
	}
	
	/**
	 * 查询指定企业客户跟踪信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("listPersonTrace.htm")
	public String listPersonTrace(HttpServletRequest request, Model model) {
		queryCustomerTrack(request, model);
		return VM_PATH + "listPersonTrace.vm";
	}
	
	@ResponseBody
	@RequestMapping("addCustomerInfoTraceSubmit.json")
	public Object addCustomerInfoTraceSubmit(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		return addCustomerTrace(request);
	}
	
	@ResponseBody
	@RequestMapping("personDelete.json")
	public Object personDelete(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject jsonobj = new JSONObject();
		try {
			CustomerPersonOrder customerPersonOrder = new CustomerPersonOrder();
			WebUtil.setPoPropertyByRequest(customerPersonOrder, request);
			setSessionLocalInfoCreatorOrder(customerPersonOrder);
			String customerId = request.getParameter("customerId");
			String uId = request.getParameter("userId");
			long userId = NumberUtil.parseLong(uId);
			customerPersonOrder.setCustomerId(customerId);
			customerPersonOrder.setUserId(userId);
			InsuranceBaseResult result = customerPersonService
				.deleteCustomerPersonInfo(customerPersonOrder);
			toJSONResult(jsonobj, result, "删除用户", null);
		} catch (Exception e) {
			logger.error("删除用户失败", e);
			jsonobj.put("success", false);
			jsonobj.put("message", "删除用户失败");
		}
		return jsonobj;
	}
}
