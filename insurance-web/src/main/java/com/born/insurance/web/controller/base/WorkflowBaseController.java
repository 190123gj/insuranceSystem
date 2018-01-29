package com.born.insurance.web.controller.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.info.bpm.BpmButtonInfo;
import com.born.insurance.ws.info.bpm.WebNodeInfo;
import com.born.insurance.ws.info.common.FormExecuteInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.TaskNodeInfo;
import com.born.insurance.ws.order.bpm.TaskOpinion;
import com.born.insurance.ws.order.common.SimpleFormAuditOrder;
import com.born.insurance.ws.order.common.SimpleUserInfo;
import com.born.insurance.ws.order.common.TaskAssignFormOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.common.FormService;
import org.springframework.ui.Model;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.bean.ProjectBean;
import com.born.insurance.web.util.WebUtil;

import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

public class WorkflowBaseController extends BaseController {
	

	
	protected WebNodeInfo initWorkflow(Model model, FormInfo formInfo, String taskId, String sysName) {
		model.addAttribute("_SYSNAME", sysName);
		return initWorkflow(model, formInfo, taskId);
	}
	
	protected WebNodeInfo initWorkflow(Model model, FormInfo formInfo, String taskId) {
		
		WebNodeInfo nodeInfo = workflowEngineClient.getTaskNode(taskId);
		boolean isCanWorkflowFinished = false;
		if (ListUtil.isNotEmpty(nodeInfo.getBpmButtonInfos())) {
			for (BpmButtonInfo buttonInfo : nodeInfo.getBpmButtonInfos()) {
				if (BpmButtonInfo.NODE_BUTTON_TYPE_SAVEFORM == buttonInfo.getOperatortype()) {
					model.addAttribute("isSaveForm", true);
				}
				if (BpmButtonInfo.NODE_BUTTON_TYPE_COMPLETE_END == buttonInfo.getOperatortype()) {
					isCanWorkflowFinished = true;
					model.addAttribute("isCanWorkflowFinished", isCanWorkflowFinished);
				}
			}
			
			//判断是否当前执行人，当前表单还可操作
			List<FormExecuteInfo> exeInfos = formInfo.getFormExecuteInfo();
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			boolean isCurrentExecutor = false;
			if (ListUtil.isNotEmpty(exeInfos)) {
				for (FormExecuteInfo info : exeInfos) {
					if (info.isExecute() || info.isSetAgent())
						continue;
					if (taskId.equals(info.getTaskId())) {//当前任务
						if (info.getUserId() > 0 && info.getUserId() == sessionLocal.getUserId()) {
							isCurrentExecutor = true;
							break;
						} else if (info.getUserId() == 0
									&& ListUtil.isNotEmpty(info.getCandidateUserList())) {
							for (SimpleUserInfo user : info.getCandidateUserList()) {
								if (user.getUserId() == sessionLocal.getUserId()) {
									isCurrentExecutor = true;
									break;
								}
							}
						}
					}
				}
			}
			if (!isCurrentExecutor) {
				//不是当前执行人再去bpm查询实时的执行人
				isCurrentExecutor = false;
				List<Long> nodeExcutor = getNodeExcutor(formInfo, nodeInfo.getNodeId());
				for (Long userId : nodeExcutor) {
					if (userId == sessionLocal.getUserId()) {
						isCurrentExecutor = true;
						break;
					}
				}
				//不是当前执行人清除按钮
				if (!isCurrentExecutor)
					nodeInfo.getBpmButtonInfos().clear(); //清空按钮就行了
			}
		}
		boolean hasNexNode = false;
		List<TaskNodeInfo> nodeInfos = Lists.newArrayList();
		if (isCanWorkflowFinished && ListUtil.isNotEmpty(nodeInfo.getNextFlowNode())
			&& nodeInfo.getNextFlowNode().size() >= 1) {
			hasNexNode = true;
			model.addAttribute("selectNodeCount", nodeInfo.getNextFlowNode().size() + 1);
			
		} else if (ListUtil.isNotEmpty(nodeInfo.getNextFlowNode())
					&& nodeInfo.getNextFlowNode().size() >= 2) {
			hasNexNode = true;
			model.addAttribute("selectNodeCount", nodeInfo.getNextFlowNode().size());
			
		}
		model.addAttribute("hasSelectNode", hasNexNode);
		if (hasNexNode) {
			model.addAttribute("firstNode", nodeInfo.getNextFlowNode().get(0));
			nodeInfos.addAll(nodeInfo.getNextFlowNode());
			nodeInfos.remove(0);
			model.addAttribute("secondNodes", nodeInfos);
		}
		
		model.addAttribute("webNodeInfo", nodeInfo);
		model.addAttribute("taskId", taskId);
		return nodeInfo;
	}
	
	/**
	 * 根据任务ID验证当前登陆是是否该任务执行人
	 * @param formInfo
	 * @param nodeId
	 * @return
	 */
	private List<Long> getNodeExcutor(FormInfo formInfo, String nodeId) {
		List<Long> nodeExcutor = Lists.newArrayList();
		try {
			//查询执行人
			List<TaskOpinion> taskOpinions = workflowEngineClient.getTaskUsers(
				String.valueOf(formInfo.getActInstId()), nodeId);
			if (ListUtil.isNotEmpty(taskOpinions)) {
				for (TaskOpinion taskOpinion : taskOpinions) {
					//正在审批
					if (taskOpinion.getCheckStatus() == (long) TaskOpinion.STATUS_CHECKING) {
						long userId = taskOpinion.getExeUserId();
						if (userId > 0) {
							nodeExcutor.add(userId);
						} else if (ListUtil.isNotEmpty(taskOpinion.getCandidateUserList())) { //查询候选人列表
							for (Long uid : taskOpinion.getCandidateUserList()) {
								nodeExcutor.add(uid);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("实时获取任务执行人出错 {}", e);
		}
		return nodeExcutor;
	}
	
	protected void doNext(HttpServletRequest request, HttpServletResponse response,
							Short intTaskOpinion) {
		long formId = NumberUtil.parseLong(request.getParameter("hddFormId"));
		long taskId = NumberUtil.parseLong(request.getParameter("taskId"));
		SimpleFormAuditOrder auditOrder = makeAuditOrder(request, formId, taskId);
		auditOrder.setVoteAgree(String.valueOf(intTaskOpinion));
		if (intTaskOpinion == TaskOpinion.STATUS_REJECT_TOSTART) {
			auditOrder.setIsBack("2");
		} else if (intTaskOpinion == TaskOpinion.STATUS_REJECT_TO_TASK_NODE) {
			auditOrder.setIsBack("3");
		} else if (intTaskOpinion == TaskOpinion.STATUS_ENDPROCESS) {
			auditOrder.setNextNodeId("workflowFinished");
		}
		JSONObject jsonObject = new JSONObject();
		if ("workflowFinished".equals(auditOrder.getNextNodeId())) {
			
			InsuranceBaseResult baseResult = formService.endWorkflow(auditOrder);
			if (baseResult.isSuccess()) {
				
				jsonObject.put("code", "1");
				jsonObject.put("success", true);
				
				jsonObject.put("message", "流程处理完成");
				jsonObject.put("nextAuditor", "");
				jsonObject.put("nextNode", "");
				
				//上传附件
				if ("YES".equals(request.getParameter("uploadAttach"))) {
					addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
						request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
				}
			} else {
				jsonObject.put("code", "0");
				jsonObject.put("success", false);
				if (StringUtil.isNotBlank(baseResult.getMessage())) {
					jsonObject.put("message", baseResult.getMessage());
				} else {
					jsonObject.put("message", "处理失败");
				}
			}
			
		} else {
			InsuranceBaseResult baseResult = formService
				.auditProcess(auditOrder);
			if (baseResult.isSuccess()) {
				jsonObject.put("code", "1");
				jsonObject.put("success", true);
				
				String nextAuditor = "";
				String nextNode = "";
				String nextInfo = baseResult.getUrl();
				if (StringUtil.isNotBlank(nextInfo)) {
					String[] next = nextInfo.split(";");
					if (next.length > 0)
						nextNode = next[0];
					if (next.length > 1)
						nextAuditor = next[1];
				}
				
				String message = "处理成功 ";
				if ("FLOW_FINISH".equals(nextNode)) {
					message = "流程处理完成";
				} else {
					if (StringUtil.isNotBlank(nextNode)) {
						message += "[ " + nextNode + " ]";
					}
					if (StringUtil.isNotBlank(nextAuditor)) {
						message += "[ 待执行人：" + nextAuditor + " ]";
					}
				}
				
				jsonObject.put("message", message);
				jsonObject.put("nextAuditor", nextAuditor);
				jsonObject.put("nextNode", nextNode);
				//上传附件
				if ("YES".equals(request.getParameter("uploadAttach"))) {
					addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
						request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
				}
			} else {
				jsonObject.put("code", "0");
				jsonObject.put("success", false);
				if (StringUtil.isNotBlank(baseResult.getMessage())) {
					jsonObject.put("message", baseResult.getMessage());
				} else {
					jsonObject.put("message", "流程处理失败");
				}
			}
		}
		printHttpResponse(response, jsonObject);
	}
	
	protected void doTaskAssign(HttpServletRequest request, HttpServletResponse response) {
		long formId = NumberUtil.parseLong(request.getParameter("hddFormId"));
		long taskId = NumberUtil.parseLong(request.getParameter("taskId"));
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		TaskAssignFormOrder auditOrder = new TaskAssignFormOrder();
		auditOrder.setFormId(formId);
		auditOrder.setAssigneeId(request.getParameter("fnAssignManId"));
		auditOrder.setAssigneeName(request.getParameter("fnAssignManName"));
		auditOrder.setMemo(request.getParameter("assignMemo"));
		auditOrder.setTaskId(String.valueOf(taskId));
		auditOrder.setUserId(sessionLocal.getUserId());
		auditOrder.setUserAccount(sessionLocal.getUserName());
		auditOrder.setUserName(sessionLocal.getRealName());
		InsuranceBaseResult baseResult = formService.taskAssign(auditOrder);
		JSONObject jsonObject = new JSONObject();
		if (baseResult.isSuccess()) {
			
			jsonObject.put("code", "1");
			jsonObject.put("success", true);
			
			String nextAuditor = "";
			String nextNode = "";
			String nextInfo = baseResult.getUrl();
			if (StringUtil.isNotBlank(nextInfo)) {
				String[] next = nextInfo.split(";");
				if (next.length > 0)
					nextNode = next[0];
				if (next.length > 1)
					nextAuditor = next[1];
			}
			
			String message = "处理成功 ";
			if ("FLOW_FINISH".equals(nextNode)) {
				message = "流程处理完成";
			} else {
				if (StringUtil.isNotBlank(nextNode)) {
					message += "[ " + nextNode + " ]";
				}
				if (StringUtil.isNotBlank(nextAuditor)) {
					message += "[ 待执行人：" + nextAuditor + " ]";
				}
			}
			
			jsonObject.put("message", message);
			jsonObject.put("nextAuditor", nextAuditor);
			jsonObject.put("nextNode", nextNode);
			
			//上传附件
			if ("YES".equals(request.getParameter("uploadAttach"))) {
				addAttachfile(String.valueOf(formId), String.valueOf(auditOrder.getUserId()),
					request, null, null, CommonAttachmentTypeEnum.FORM_ATTACH);
			}
		} else {
			jsonObject.put("code", "0");
			jsonObject.put("success", false);
			if (StringUtil.isNotBlank(baseResult.getMessage())) {
				jsonObject.put("message", baseResult.getMessage());
			} else {
				jsonObject.put("message", "流程处理失败");
			}
		}
		printHttpResponse(response, jsonObject);
	}
	
	protected SimpleFormAuditOrder makeAuditOrder(HttpServletRequest request, long formId,
												  long taskId) {
		SimpleFormAuditOrder auditOrder = new SimpleFormAuditOrder();
		
		setSessionLocalInfo2Order(auditOrder);
		
		auditOrder.setFormId(formId);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		auditOrder.setUserId(sessionLocal.getUserId());
		auditOrder.setUserAccount(sessionLocal.getUserName());
		auditOrder.setUserName(sessionLocal.getRealName());
		auditOrder.setNextNodeId(request.getParameter("selectNodeId"));
		auditOrder.setNextUser(request.getParameter("selectNodeNextUser"));
		auditOrder.setTaskId(taskId);
		auditOrder.setVoteContent(request.getParameter("workflowVoteContent"));
		auditOrder.setUserIp(sessionLocal.getRemoteAddr());
		
		UserInfo userInfo = sessionLocal.getUserInfo();
		if (userInfo != null) {
			auditOrder.setUserEmail(userInfo.getEmail());
			auditOrder.setUserMobile(userInfo.getMoblie());
		}
		
		//serviceName 直接写死在 FormCodeEnum中
		//auditOrder.setServiceName(request.getParameter("workflowServiceName"));
		auditOrder.setNextNodeId(request.getParameter("radNextNodeId"));
		Map<String, String> reqMap = WebUtil.getRequestMap(request, "selectNodeId",
			"selectNodeNextUser", "workflowVoteContent", "radNextNodeId", "assignMemo",
			"fnAssignManName", "fnAssignManId", "taskId", "hddFormId", "hddFormActDefId", "_time",
			"hddFormActInstId", "pathName_FORM_ATTACH");
		
		Map<String, Object> customizeMap = auditOrder.getCustomizeMap();
		String brokerScale = request.getParameter("brokerScale");
		String commonScale = request.getParameter("commonScale");
		if (customizeMap == null)
			customizeMap = Maps.newHashMap();
		
		customizeMap.put("brokerScale", brokerScale);
		customizeMap.put("commonScale", commonScale);
		for (String key : reqMap.keySet()) {
			customizeMap.put(key, reqMap.get(key));
		}
		
		auditOrder.setCustomizeMap(customizeMap);
		
		return auditOrder;
	}

}
