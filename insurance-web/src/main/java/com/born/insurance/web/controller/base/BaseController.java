package com.born.insurance.web.controller.base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.ws.base.QueryFormBase;
import com.born.insurance.ws.base.QueryPermissionPageBase;
import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.bpm.Org;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.base.FormOrderBase;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.bpm.WorkflowProcessLog;
import com.born.insurance.ws.order.common.CommonAttachmentOrder;
import com.born.insurance.ws.order.common.CommonAttachmentQueryOrder;
import com.born.insurance.ws.order.common.SimpleFormOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.FormService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.util.DataPermissionUtil;
import com.born.insurance.web.util.WebUtil;

import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class BaseController extends BaseAutowiredController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		String[] nameArray = getDateInputNameArray();
		if (nameArray != null && nameArray.length > 0) {
			for (int i = 0; i < nameArray.length; i++) {
				binder.registerCustomEditor(Date.class, nameArray[i], new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
				
			}
		}
		
		String[] nameArray2 = getDateInput2NameArray();
		if (nameArray2 != null && nameArray2.length > 0) {
			for (int i = 0; i < nameArray2.length; i++) {
				binder.registerCustomEditor(Date.class, nameArray2[i], new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
				
			}
		}
		
		String[] dateDayNameArray = getDateInputDayNameArray();
		if (dateDayNameArray != null && dateDayNameArray.length > 0) {
			for (int i = 0; i < dateDayNameArray.length; i++) {
				binder.registerCustomEditor(Date.class, dateDayNameArray[i], new CustomDateEditor(
					new SimpleDateFormat("yyyy-MM-dd"), true));
				
			}
		}
		String[] moneyNameArray = getMoneyInputNameArray();
		if (dateDayNameArray != null && moneyNameArray.length > 0) {
			for (int i = 0; i < moneyNameArray.length; i++) {
				binder.registerCustomEditor(Money.class, moneyNameArray[i],
					new CommonBindingInitializer());
			}
		}
	}
	
	protected String[] getDateInputNameArray() {
		return new String[0];
	}
	
	protected String[] getDateInput2NameArray() {
		return new String[0];
	}
	
	protected String[] getDateInputDayNameArray() {
		return new String[0];
	}
	
	protected String[] getMoneyInputNameArray() {
		return new String[0];
	}
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected String sendUrl(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	protected String sendUrl(HttpServletResponse response, String url, Map<String, String> params) {
		try {
			if (params != null && !params.isEmpty()) {
				int i = 0;
				for (String key : params.keySet()) {
					if (i == 0) {
						url += "?" + key + "=" + params.get(key);
					} else {
						url += "&" + key + "=" + params.get(key);
					}
					i++;
				}
			}
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 页面审核历史
	 * @param form
	 * @param model
	 */
	protected void setAuditHistory2Page(FormInfo form, Model model) {
		if (form != null && form.getActInstId() > 0) {
			QueryBaseBatchResult<WorkflowProcessLog> batchResult = workflowEngineClient
				.getProcessOpinionByActInstId(String.valueOf(form.getActInstId()));
			if (batchResult != null) {
				List<WorkflowProcessLog> list = batchResult.getPageList();
				Collections.reverse(list);
				model.addAttribute("auditHisList", list);
			}
		}
	}
	


	
	protected JSONObject toJSONResult(JSONObject json, ResultBase baseResult, String extraTrueMsg,
										String extraFalseMsg) {
		if (baseResult != null && baseResult.isSuccess()) {
			json.put("success", true);
			if (StringUtil.isNotEmpty(extraTrueMsg)) {
				json.put("message", extraTrueMsg);
			} else {
				json.put("message", baseResult.getMessage());
			}
			if (baseResult instanceof FormBaseResult) {
				FormInfo form = ((FormBaseResult) baseResult).getFormInfo();
				if (form != null)
					json.put("form", form.toJson());
			}
		} else {
			json.put("success", false);
			if (StringUtil.isNotEmpty(extraFalseMsg)) {
				json.put("message", extraFalseMsg);
			} else {
				json.put("message", baseResult.getMessage());
			}
		}
		return json;
	}
	
	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @param extraTrueMsg 格外指定成功消息
	 * @param extraFalseMsg 格外指定失败消息
	 * @return
	 */
	protected JSONObject toJSONResult(ResultBase baseResult, String extraTrueMsg,
										String extraFalseMsg) {
		JSONObject result = new JSONObject();
		if (baseResult.isSuccess()) {
			result.put("success", true);
			if (StringUtil.isNotEmpty(extraTrueMsg)) {
				result.put("message", extraTrueMsg);
			} else {
				result.put("message", baseResult.getMessage());
			}
			
		} else {
			result.put("success", false);
			if (StringUtil.isNotEmpty(extraFalseMsg)) {
				result.put("message", extraFalseMsg);
			} else {
				result.put("message", baseResult.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @return
	 */
	protected JSONObject toJSONResult(ResultBase baseResult) {
		return toJSONResult(baseResult, null, null);
	}
	
	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @return
	 */
	protected JSONObject toJSONResult(JSONObject json, ResultBase baseResult) {
		return toJSONResult(json, baseResult, null, null);
	}
	
	/**
	 * 处理保存结果
	 * 
	 * @param result 保存结果
	 * @param tipPrefix 提示信息前缀
	 * @return
	 */
	protected JSONObject toJSONResult(InsuranceBaseResult result, String tipPrefix) {
		JSONObject json = new JSONObject();
		if (null != result && result.isSuccess()) {
			json.put("success", true);
			json.put("message", tipPrefix + "成功");
			if (result instanceof FormBaseResult) {
				FormInfo form = ((FormBaseResult) result).getFormInfo();
				if (form != null)
					json.put("form", form.toJson());
			}
			json.put("id", result.getKeyId());
		} else {
			json.put("success", false);
			json.put("message", tipPrefix + "失败 :" + result.getMessage());
		}
		return json;
	}
	
	
	/**
	 * 处理保存结果
	 * 
	 * @param result 保存结果
	 * @param tipPrefix 提示信息前缀
	 * @return
	 */
	protected JSONObject toJSONResult(FormBaseResult submitResult) {
		JSONObject result = new JSONObject();
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
		return result;
	}
	
	/**
	 * 标准化返回处理保存结果
	 * @param data null表示处理失败
	 * @param tipPrefix
	 * @return
	 */
	protected JSONObject toStandardResult(JSON data, String tipPrefix) {
		JSONObject json = new JSONObject();
		if (null != data) {
			json.put("success", true);
			json.put("message", tipPrefix + "成功");
			json.put("data", data);
			
		} else {
			json.put("success", false);
			json.put("message", tipPrefix + "失败");
		}
		return json;
	}
	
	/**
	 * 处理保存异常信息
	 * 
	 * @param tipPrefix 提示信息前缀
	 * @param e 异常消息
	 * @return
	 */
	protected JSONObject toJSONResult(String tipPrefix, Exception e) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", tipPrefix + "出错[" + e.getMessage() + "]");
		return json;
	}
	
	protected void printHttpResponse(HttpServletResponse response,
										com.alibaba.fastjson.JSONAware json) {
		try {
			
			response.setContentType("json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json.toJSONString());
		} catch (IOException e) {
			logger.error("response. getWriter print error ", e);
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(FormOrderBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			order.setCreatorId(sessionLocal.getUserId());
			order.setCreatorName(sessionLocal.getUserName());
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMobile());
				order.setUserEmail(userInfo.getEmail());
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(SimpleFormOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			
			UserDetailInfo userInfo = sessionLocal.getUserDetailInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMobile());
				order.setUserEmail(userInfo.getEmail());
				Org dept = userInfo.getPrimaryOrg();
				if (dept != null) {
					order.setDeptId(dept.getId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getPathName());
				}
			}
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(ProcessOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			order.setCreatorId(sessionLocal.getUserId());
			order.setCreatorName(sessionLocal.getRealName());
		}
	}


	protected void setSessionLocalInfoCreatorOrder(ProcessOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setCreatorId(sessionLocal.getUserId());
			order.setCreatorName(sessionLocal.getRealName());
		}
	}
	

	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryFormBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryPermissionPageBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {//拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { //按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
			//查看草稿的人员
			order.setDraftUserId(sessionLocal.getUserId());
		}
	}
	

	


	
	/**
	 * 不显示作废侧边栏
	 * @param model
	 */
	protected void showNoEndForm(Model model) {
		model.addAttribute("showNoEndForm", true);
	}
	

	


	

	


	



	

	/**
	 * 是否拥有所有数据权限
	 * @return
	 */
	protected boolean hasAllDataPermission() {
		return DataPermissionUtil.hasAllDataPermission();
	}
	
	/**
	 * 是拥有所负责数据权限
	 * @return
	 */
	protected boolean hasPrincipalDataPermission() {
		return DataPermissionUtil.hasPrincipalDataPermission();
	}
	

	


	/**
	 * 添加附件
	 * @param keyId
	 * @param request
	 * @param types
	 * @return
	 */
	protected InsuranceBaseResult addAttachfile(String keyId, HttpServletRequest request,
											String projectCode, String remark,
											CommonAttachmentTypeEnum... types) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentService.deleteByBizNoModuleType(keyId, types);
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = request.getParameter("pathName_" + type.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("projectCode");
						}
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("relatedProjectCode");
						}
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentService.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}
	
	/**
	 * 添加附件
	 * @param keyId
	 * @param childId
	 * @param request
	 * @param types
	 * @return
	 */
	protected InsuranceBaseResult addAttachfile(String keyId, String childId, HttpServletRequest request,
											String projectCode, String remark,
											CommonAttachmentTypeEnum... types) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
		
		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}
		
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentService.deleteByBizNoAndChildIdModuleType(keyId, childId, types);
		
		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			String pathValues = request.getParameter("pathName_" + type.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setChildId(childId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("projectCode");
						}
						if (StringUtil.isBlank(projectCode)) {
							projectCode = request.getParameter("relatedProjectCode");
						}
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}
		
		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentService.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}
	
	/**
	 * 查询附件列表
	 * 
	 * @param model
	 * @param bizNo 交易(引用外部数据id)流水号
	 * @param type 模块
	 * @param childId 长度 0 或者 1
	 */
	protected void queryCommonAttachmentData(Model model, String bizNo,
												CommonAttachmentTypeEnum type, String... childId) {
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		if (childId != null && childId.length > 0) {
			attachQueryOrder.setBizNo(childId[0]);
		}
		attachQueryOrder.setBizNo(bizNo);
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(type);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentService
			.queryCommonAttachment(attachQueryOrder);
		if (null != attaches && ListUtil.isNotEmpty(attaches.getPageList())) {
			model.addAttribute("attaches_" + type.code(), attaches.getPageList());
			StringBuilder urls = new StringBuilder();
			for (CommonAttachmentInfo attach : attaches.getPageList()) {
				urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
					.append(",").append(attach.getRequestPath()).append(";");
			}
			urls.deleteCharAt(urls.length() - 1);
			model.addAttribute("hiddenUrls_" + type.code(), urls.toString());
			
			//所有附件
			model.addAttribute("commonAttachementList", attaches.getPageList());
		}
	}
	
	protected String queryCommonAttachmentUrls(String bizNo, CommonAttachmentTypeEnum type,
												String... childId) {
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		if (childId != null && childId.length > 0) {
			attachQueryOrder.setBizNo(childId[0]);
		}
		attachQueryOrder.setBizNo(bizNo);
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(type);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentService
			.queryCommonAttachment(attachQueryOrder);
		if (null != attaches && ListUtil.isNotEmpty(attaches.getPageList())) {
			StringBuilder urls = new StringBuilder();
			for (CommonAttachmentInfo attach : attaches.getPageList()) {
				urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
					.append(",").append(attach.getRequestPath()).append(";");
			}
			urls.deleteCharAt(urls.length() - 1);
			return urls.toString();
		}
		
		return "";
	}
	
	/**
	 * 构建跳转地址
	 * @param request
	 * @param model
	 */
	protected void buildSystemNameDefaultUrl(HttpServletRequest request, Model model) {
		Map<String, String> params = WebUtil.getRequestMap(request);
		if (params.containsKey("systemNameDefautUrl")) {
			int index = 0;
			String systemNameDefautUrl = params.get("systemNameDefautUrl");
			if (StringUtil.isNotBlank(systemNameDefautUrl)) {
				for (String pName : params.keySet()) {
					if ("systemNameDefautUrl".equals(pName))
						continue;
					String pValue = params.get(pName);
					if (StringUtil.isNotBlank(pValue)) {
						if (index == 0 && systemNameDefautUrl.indexOf("?") == -1) {
							systemNameDefautUrl += "?" + pName + "=" + pValue;
						} else {
							systemNameDefautUrl += "&" + pName + "=" + pValue;
						}
						index++;
					}
				}
			}
			params.put("systemNameDefautUrl", systemNameDefautUrl);
		}
		model.addAllAttributes(params);
	}
	
	/**
	 * 验证登录状态
	 * 
	 * @param json 失效提示信息
	 * @return 未登录或失效返回true
	 */
	protected boolean isLoginExpire(JSONObject json) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (null == sessionLocal) {
			json.put("success", false);
			json.put("message", "您未登陆或登陆已失效");
			return true;
		}
		return false;
	}
	
	/**
	 * 验证重复提交
	 * 
	 * @param json 失效提示信息
	 * @return 重复提交返回true
	 */
	protected boolean isRepeatSubmit(JSONObject json, HttpSession session, String token) {
		return false;
		//去掉防重复提交
		//		String getToken = (String) session.getAttribute("token");
		//		if (StringUtil.equals(token, getToken)) {
		//			return false;
		//		} else {
		//			json.put("success", false);
		//			json.put("message", "请不要重复提交");
		//			return true;
		//		}
	}
	

	
	/**
	 * 
	 * 提交表单到流程
	 * 
	 * @param order
	 * @param formId
	 * @param json 保存结果
	 * @return
	 */
	protected void formSubmit(FormOrderBase order, long formId, JSONObject json, String sysName) {
		//提交审核
		if (null != order.getCheckStatus() && order.getCheckStatus() == 1) {
			UserInfo userInfo = ShiroSessionUtils.getSessionLocal().getUserInfo();
			if (userInfo != null) {
				order.setUserEmail(userInfo.getEmail());
				order.setUserMobile(userInfo.getMoblie());
			}
			SimpleFormOrder formOrder = new SimpleFormOrder();
			BeanCopier.staticCopy(order, formOrder);
			formOrder.setFormId(formId);
			FormBaseResult submitResult = formService.submit(formOrder);
			if (submitResult.isSuccess()) {
				json.put("success", true);
				json.put("message", submitResult.getMessage());
				json.put("form", submitResult.getFormInfo().toJson());
			} else {
				json.put("success", false);
				json.put("message", submitResult.getMessage());
				json.put("form", submitResult.getFormInfo());
			}
		}
	}
	

	
	/**
	 * 
	 * 获取近几年的年份值:2016,2015...
	 * 
	 * @param n
	 * @return
	 */
	protected int[] getYears(int n) {
		int[] years = new int[n];
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int currentYear = c.get(Calendar.YEAR);
		for (int y = currentYear, i = 0; i < n; i++) {
			years[i] = y--;
		}
		return years;
	}
	
	protected void returnJson(HttpServletResponse response, boolean isIE, JSONObject jsonobj)
																								throws IOException {
		response.reset();
		if (isIE) {
			response.setHeader("ContentType", "text/html");
			response.setContentType("text/html");
		} else {
			response.setHeader("ContentType", "application/json");
			response.setContentType("application/json");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonobj.toJSONString());
	}
	

	


	/**
	 * siteurl
	 * @param request
	 * @return
	 */
	protected String getRequestUrl(HttpServletRequest request) {
		String str = request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort();
		return str;
	}
}
