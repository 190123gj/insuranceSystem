package com.born.insurance.web.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.integration.bpm.user.SysOrg;
import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.integration.result.WorkflowBatchProcessResult;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.WorkflowBatchProcessOrder;
import com.born.insurance.ws.order.bpm.TaskOpinion;
import com.born.insurance.ws.order.common.CancelFormOrder;
import com.born.insurance.ws.order.common.FormQueryOrder;
import com.born.insurance.ws.order.common.SimpleFormAuditOrder;
import com.born.insurance.ws.order.common.SimpleFormOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("insurance/form")
public class FormController extends WorkflowBaseController {
	
	@RequestMapping("submit.json")
	@ResponseBody
	public JSONObject submit(SimpleFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("code", "0");
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			
			setSessionLocalInfo2Order(order);
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			
			FormBaseResult submitResult = formService.submit(order);
			
			if (submitResult.isSuccess()) {
				result.put("code", "1");
				result.put("success", true);
				
				String nextAuditor = "";
				String nextNode = "";
				String nextInfo = submitResult.getUrl();
				if (StringUtil.isNotBlank(nextInfo)) {
					String[] next = nextInfo.split(";");
					if (next.length > 0)
						nextNode = next[0];
					if (next.length > 1)
						nextAuditor = next[1];
				}
				
				String message = "提交成功 ";
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
				result.put("message", message);
				result.put("nextAuditor", nextAuditor);
				result.put("nextNode", nextNode);
				result.put("form", submitResult.getFormInfo().toJson());
			} else {
				result.put("code", "0");
				result.put("success", false);
				result.put("message", submitResult.getMessage());
				result.put("form", submitResult.getFormInfo().toJson());
			}
			
		} catch (Exception e) {
			logger.error("提交表单出错", e);
		}
		return result;
	}
	
	@RequestMapping("cancel.json")
	@ResponseBody
	public JSONObject cancel(CancelFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			
			setSessionLocalInfo2Order(order);
			
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			
			InsuranceBaseResult cancelResult = formService.cancel(order);
			if (cancelResult.isSuccess()) {
				result.put("code", "1");
				result.put("success", true);
				result.put("message", "撤回成功");
			} else {
				result.put("code", "0");
				result.put("success", false);
				result.put("message", cancelResult.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("撤回表单出错", e);
		}
		return result;
	}
	
	@RequestMapping("workflow/processs/donext.json")
	public String workflowProcesssDoNext(HttpServletRequest request, HttpServletResponse response,
											Model model) {
		doNext(request, response, TaskOpinion.STATUS_AGREE);
		return null;
	}
	
	@RequestMapping("workflow/processs/dorefuse.json")
	public String workflowProcesssDoFefuse(HttpServletRequest request,
											HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REFUSE);
		return null;
	}
	
	@RequestMapping("workflow/processs/doback.json")
	public String workflowProcesssDoBack(HttpServletRequest request, HttpServletResponse response,
											Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TOSTART);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBack.json")
	public String workflowProcesssDoGoToBack(HttpServletRequest request,
												HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TOSTART);
		return null;
	}
	
	@RequestMapping("workflow/processs/doGoToBackNode.json")
	public String workflowProcesssDoGoToBackNode(HttpServletRequest request,
													HttpServletResponse response, Model model) {
		doNext(request, response, TaskOpinion.STATUS_REJECT_TO_TASK_NODE);
		return null;
	}
	
	@RequestMapping("workflow/processs/doTaskAssign.json")
	public String workflowProcesssDoTaskAss(HttpServletRequest request,
											HttpServletResponse response, Model model) {
		doTaskAssign(request, response);
		return null;
	}
	
	@RequestMapping("workflow/processs/doEndWorkflow.json")
	public String workflowProcesssDoEnd(HttpServletRequest request, HttpServletResponse response,
										Model model) {
		doNext(request, response, TaskOpinion.STATUS_ENDPROCESS);
		return null;
	}
	
	@RequestMapping("end.json")
	@ResponseBody
	public JSONObject end(long formId, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			FormInfo formInfo = null;
			if (formId > 0)
				formInfo = formService.findByFormId(formId);
			if (formInfo == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "表单不存在");
				return jsonObject;
			} else if (formInfo.getStatus() != FormStatusEnum.BACK
						&& formInfo.getStatus() != FormStatusEnum.CANCEL) {
				jsonObject.put("success", false);
				jsonObject.put("message", "当前状态不允许作废");
				return jsonObject;
			}
			SimpleFormAuditOrder auditOrder = makeAuditOrder(request, formId, formInfo.getTaskId());
			auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_ENDPROCESS));
			auditOrder.setNextNodeId("workflowFinished"); //终止
			InsuranceBaseResult baseResult = formService.endWorkflow(auditOrder);
			if (baseResult.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "作废成功");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "作废失败[" + baseResult.getMessage() + "]");
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "作废出错");
		}
		return jsonObject;
	}
	
	@RequestMapping("batchProcess.json")
	@ResponseBody
	public JSONObject batchProcess(WorkflowBatchProcessOrder order, HttpServletRequest request,
								   Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setUserId(sessionLocal.getUserId());
				order.setUserAccount(sessionLocal.getUserName());
				order.setUserName(sessionLocal.getRealName());
				order.setUserIp(sessionLocal.getRemoteAddr());
				
				UserInfo userInfo = sessionLocal.getUserInfo();
				if (userInfo != null) {
					order.setUserMobile(userInfo.getMoblie());
					order.setUserEmail(userInfo.getEmail());
					SysOrg dept = sessionLocal.getUserInfo().getDept();
					if (dept != null) {
						order.setDeptId(dept.getOrgId());
						order.setDeptCode(dept.getCode());
						order.setDeptName(dept.getOrgName());
						order.setDeptPath(dept.getPath());
						order.setDeptPathName(dept.getOrgPathname());
					}
				}
			}
			WorkflowBatchProcessResult result = workflowEngineClient.batchProcess(order);
			if (result.isSuccess()) {
				jsonObject.put("success", true);
				jsonObject.put("message", "批量处理成功");
				jsonObject.put("result", result.toJson());
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "批量处理失败");
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "批量处理异常");
		}
		return jsonObject;
	}
	
	@RequestMapping("delete.json")
	@ResponseBody
	public JSONObject delete(SimpleFormOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				result.put("code", 0);
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult cancelResult = formService.delete(order);
			if (cancelResult.isSuccess()) {
				result.put("code", 1);
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("code", 0);
				result.put("success", false);
				result.put("message", cancelResult.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("删除表单出错", e);
		}
		return result;
	}
	
	/**
	 * 根据 actInstId 或者 formId 和 sysName 查看表单
	 * @param actInstId
	 * @param formId
	 * @param sysName
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(String actInstId, Long formId, String sysName, Model model) {
		
		if (StringUtil.isNotEmpty(sysName) && formId != null && formId > 0) {

			FormInfo formInfo = formService.findByFormId(formId);
			if (formInfo == null) {
				throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			model.addAttribute("formId", formId);
			return "redirect:" + getRedirectUrl(formInfo.getFormUrl(), model);
		} else if (StringUtil.isNotEmpty(actInstId)) {
			long insId = NumberUtil.parseLong(actInstId);
			if (insId > 0) {
				FormQueryOrder queryOrder = new FormQueryOrder();
				queryOrder.setActInstId(insId);
				QueryBaseBatchResult<FormInfo> formResult = formService.queryPage(queryOrder);
					FormInfo formInfo = formResult.getPageList().get(0);
				model.addAttribute("formId", formInfo.getFormId());
				return "redirect:" + getRedirectUrl(formInfo.getFormUrl(), model);
			}
		} else {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.INCOMPLETE_REQ_PARAM, "请求参数不完整");
		}
		return null;
	}
	
	/**
	 * 获取跳转地址
	 * @param url
	 * @param model
	 * @return
	 */
	private String getRedirectUrl(String url, Model model) {
		String module = "projectMg";
		String[] stringSplit = url.split("/");
		if (stringSplit.length > 0)
			module = stringSplit[1];
		model.addAttribute("systemNameDefautUrl", url);
		return "/" + module + "/index.htm";
	}
}
