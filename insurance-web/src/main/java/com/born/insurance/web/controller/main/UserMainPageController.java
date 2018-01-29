package com.born.insurance.web.controller.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.info.bpm.TaskTypeViewInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.BpmFinishTaskInfo;
import com.born.insurance.ws.order.bpm.TaskSearchOrder;
import com.born.insurance.ws.order.bpm.WorkflowTaskInfo;
import com.born.insurance.ws.order.common.FormQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rop.thirdparty.com.google.common.collect.Lists;



import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("userHome")
public class UserMainPageController extends BaseController {
	
	final static String vm_path = "/mainPage/";
	

	/**
	 * 工作台、首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("mainIndex.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		//明细代办任务
		taskList(null, model);
		//代办任务分类
		taskGroup(null, model);
		
		//被驳回的任务
		List<WorkflowTaskInfo> backTaskList = Lists.newArrayList();
		
		//项目管理被驳回单据
		List<WorkflowTaskInfo> pmList = formService.backTaskList(sessionLocal.getUserId());
		if (ListUtil.isNotEmpty(pmList))
			backTaskList.addAll(pmList);
		
		model.addAttribute("backTaskList", backTaskList);
		

		return vm_path + "mainIndex.vm";
	}
	

	

	/**
	 * 代办任务列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("taskList.htm")
	public String taskList(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineClient
			.getTasksByAccount(taskSearchOrder);
		model.addAttribute("page", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "taskList.vm";
	}
	
	/**
	 * 代办任务分类列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("taskGroup.htm")
	public String taskGroup(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		QueryBaseBatchResult<TaskTypeViewInfo> baseBatchResult = workflowEngineClient
			.getTaskTypeView(taskSearchOrder);
		if (baseBatchResult != null && baseBatchResult.getTotalCount() > 0) {
			Collections.sort(baseBatchResult.getPageList(), new Comparator<TaskTypeViewInfo>() {
				@Override
				public int compare(TaskTypeViewInfo o1, TaskTypeViewInfo o2) {
					return o2.getProcessName().compareTo(o1.getProcessName());
				}
			});
		}
		model.addAttribute("pageTaskGroup", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "taskGroupList.vm";
	}
	
	/**
	 * 代办任务列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("taskGroupList.htm")
	public String taskGroupList(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
		QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineClient
			.getTasksByAccount(taskSearchOrder);
		model.addAttribute("processName", taskSearchOrder.getProcessName());
		model.addAttribute("taskNodeName", taskSearchOrder.getTaskNodeName());
		model.addAttribute("pageGroupTask", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "taskGroupList.vm";
	}
	
	/**
	 * 已办任务列表
	 * @param taskSearchOrder
	 * @param model
	 * @return
	 */
	@RequestMapping("doneTaskList.htm")
	public String doneTaskList(TaskSearchOrder taskSearchOrder, Model model) {
		if (taskSearchOrder == null)
			taskSearchOrder = new TaskSearchOrder();
		taskSearchOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		QueryBaseBatchResult<BpmFinishTaskInfo> baseBatchResult = workflowEngineClient
			.getFinishTask(taskSearchOrder);
		model.addAttribute("pageDoneTask", PageUtil.getCovertPage(baseBatchResult));
		return vm_path + "doneTaskList.vm";
	}
	

	/**
	 * 处理任务
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("taskDistributor.htm")
	public String taskDistributor(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		FormQueryOrder formQueryOrder = new FormQueryOrder();
		formQueryOrder.setActDefId(request.getParameter("processDefinitionId"));
		formQueryOrder
			.setActInstId(NumberUtil.parseLong(request.getParameter("processInstanceId")));
		String module = "projectMg";
		String url = request.getParameter("taskUrl");
		if (StringUtil.isNotEmpty(url)) {
			String[] stringSplit = url.split("/");
			if (stringSplit.length > 0)
				module = stringSplit[1];
		}
		QueryBaseBatchResult<FormInfo> batchResult = formService
			.queryPage(formQueryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			FormInfo formInfo = batchResult.getPageList().get(0);
			String taskId = request.getParameter("taskId");
			model.addAttribute("systemNameDefautUrl", url);
			model.addAttribute("formId", formInfo.getFormId());
			model.addAttribute("taskId", taskId);
		} else {
			logger.error("taskDistributor.htm调用结果", batchResult);
		}
		
		return "redirect:/" + module + "/index.htm";
	}
	
}
