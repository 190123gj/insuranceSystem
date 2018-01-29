package com.born.insurance.web.controller.projectSetup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.dal.daointerface.ProjectSetupCustomerDAO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.ProjectSetupCustomerDO;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.ProjectStatusEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCatalogInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCustomerInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupFormInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.projectSetup.ProjectSetupCatalogService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import com.born.insurance.ws.service.projectSetup.ProjectsetupCustomerService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/projectSetup")
public class ProjectSetupController extends WorkflowBaseController {
	@Autowired
	protected ProjectSetupService projectSetupService;
	
	@Autowired
	protected ProjectsetupCustomerService projectsetupCustomerService;
	
	@Autowired
	protected CustomerPersonService customerPersonService;
	
	@Autowired
	protected ProjectSetupCatalogService projectSetupCatalogService;
	
	private final static String VM_PATH = "/insurance/projectSetup/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "beginDate", "endDate" };
	}
	
	/**
	 * 超权限申请列表
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			ProjectSetupQueryOrder queryOrder = new ProjectSetupQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<ProjectSetupFormInfo> batchResult = projectSetupService
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("projectStatus", ProjectStatusEnum.getAllEnum());
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listProjectSetup.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
						HttpServletRequest request, Model model) {
		ProjectSetupInfo info = null;
		List<ProjectSetupCatalogInfo> catalogInfo = null;
		List<ProjectSetupCustomerInfo> lists = null;
		List<ProjectSetupCatalogInfo> findList = null;
		if (formId > 0) {
			info = projectSetupService.findByFormId(formId);
			if (null != info) {
				findList = projectSetupCatalogService.findList(info.getProjectSetupId());
				lists = projectsetupCustomerService.findList(info.getProjectSetupId());
				StringBuffer sb = new StringBuffer();
				if (null != lists && lists.size() > 0) {
					for (ProjectSetupCustomerInfo projectSetupCustomerInfo : lists) {
						sb.append(projectSetupCustomerInfo.getCustomerId()).append(",");
					}
					String strVal = sb.toString();
					info.setCustomerUserIds(strVal.substring(0, strVal.length() - 1));
				}
				info.setBankOrders(findList);
			}
			model.addAttribute("projectSetupCustomer", lists);
			model.addAttribute("infoJson", JSONObject.toJSONString(info).replace("\"", "\'"));
		} else {
			info = new ProjectSetupInfo();
			catalogInfo = new ArrayList<>();
			info.setBankOrders(catalogInfo);
		}
		if (StringUtils.isBlank(info.getCustomerUserName()) && StringUtils.isBlank(info.getChannelsUserName()) && (null == info.getBankOrders() || info.getBankOrders().size() == 0)) {
			info.setIsVal("-1");
		} else {
			info.setIsVal("1");
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			model.addAttribute("setupUserId", sessionLocal.getUserId());
			model.addAttribute("setupUserName", sessionLocal.getUserName());
		}
		//附件列表
		queryCommonAttachmentData(model, info.getProjectSetupId()+ "",CommonAttachmentTypeEnum.PROJECT_SET_UP);
		model.addAttribute("info", info);
		return VM_PATH + "addProjectSetup.vm";
	}
	
	/**
	 * 确认新增超权限申请
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, ProjectSetupOrder order) {
		JSONObject json = new JSONObject();
		
		String tipPrefix = "操作";
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setFormCode(FormCodeEnum.CQXSQ);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setSetupUserId(Long.valueOf(sessionLocal.getUserId()));;
				order.setSetupUserName(sessionLocal.getUserName());
			}
			InsuranceBaseResult result = projectSetupService.save(order);
			//添加附件
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.PROJECT_SET_UP);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	/**
	 * 查看超权限申请详情
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long formId, HttpServletRequest request, Model model) {
		ProjectSetupInfo projectSetupInfo = projectSetupService.findByFormId(formId);
		if (null != projectSetupInfo) {
			List<ProjectSetupCatalogInfo> catalogInfos = projectSetupCatalogService
				.findList(projectSetupInfo.getProjectSetupId());
			model.addAttribute("catalogInfos", catalogInfos);
			FormInfo form = formService.findByFormId(formId);
			model.addAttribute("form", form);
		}
		String customerUserNames = projectSetupInfo.getCustomerUserName();
		if (!StringUtils.isBlank(customerUserNames)) {
			List<ProjectSetupCustomerDO> list = JSONObject.parseArray(customerUserNames,
				ProjectSetupCustomerDO.class);
			model.addAttribute("customerList", list);
		}
		//附件列表
		queryCommonAttachmentData(model, projectSetupInfo.getProjectSetupId()+ "",CommonAttachmentTypeEnum.PROJECT_SET_UP);
		model.addAttribute("info", projectSetupInfo);
		return VM_PATH + "viewProjectSetup.vm";
	}
	
	/**
	 * 设置跟踪提醒
	 * @param projectSetupId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("set.htm")
	public String set(long projectSetupId, HttpServletRequest request, Model model) {
		ProjectSetupInfo projectSetupInfo = projectSetupService.findById(projectSetupId);
		model.addAttribute("info", projectSetupInfo);
		return VM_PATH + "followingWarn.vm";
	}
	
	/**
	 * 跟踪提醒确认
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setSubmit.json")
	public Object setSubmit(HttpServletRequest request, ProjectSetupOrder order) {
		JSONObject json = new JSONObject();
		
		return json;
	}
	
	
	/**
	 * 确认删除超权限申请
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteConfirm.json")
	public Object deleteConfirm(HttpServletRequest request, ProjectSetupOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "删除超权限申请";
		try {
			InsuranceBaseResult result = projectSetupService.deleteProjectSetup(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }
	
	
	/**
	 * 撤销超权限申请
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("revoke.json")
	public Object revoke(HttpServletRequest request, ProjectSetupOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "撤销超权限申请";
		try {
			InsuranceBaseResult result = projectSetupService.revoke(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }
	
	/**
	 * 超权限申请审批
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		FormInfo form = formService.findByFormId(formId);
		if (null != form) {
			ProjectSetupInfo setUpInfo = projectSetupService.findByFormId(formId);
			if (null != setUpInfo) {
				CustomerBaseInfoDO customerBaseInfoDO = customerPersonService.findById(setUpInfo
					.getCustomerUserId());
				if (null != customerBaseInfoDO) {
					setUpInfo.setCustomerType(CustomerTypeEnum.getMsgByCode(customerBaseInfoDO
						.getCustomerType()));
				}
			}
			
			List<ProjectSetupCatalogInfo> catalogInfos = projectSetupCatalogService
					.findList(setUpInfo.getProjectSetupId());
				model.addAttribute("catalogInfos", catalogInfos);
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				model.addAttribute("setupUserId", sessionLocal.getUserId());
				model.addAttribute("setupUserName", sessionLocal.getUserName());
			}
			
			String customerUserNames = setUpInfo.getCustomerUserName();
			if (!StringUtils.isBlank(customerUserNames)) {
				List<ProjectSetupCustomerDO> list = JSONObject.parseArray(customerUserNames,
					ProjectSetupCustomerDO.class);
				model.addAttribute("customerList", list);
			}
			//附件列表
			queryCommonAttachmentData(model, setUpInfo.getProjectSetupId()+ "",CommonAttachmentTypeEnum.PROJECT_SET_UP);
			model.addAttribute("info", setUpInfo);
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		return VM_PATH + "auditForm.vm";
	}
}
