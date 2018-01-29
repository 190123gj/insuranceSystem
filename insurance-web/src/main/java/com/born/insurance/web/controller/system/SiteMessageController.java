package com.born.insurance.web.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.MessageReceivedStatusEnum;
import com.born.insurance.ws.enums.NotificationTypeEnum;
import com.born.insurance.ws.info.common.MessageInfo;
import com.born.insurance.ws.info.common.MessageReceivedInfo;
import com.born.insurance.ws.order.common.MessageOrder;
import com.born.insurance.ws.order.common.MyMessageOrder;
import com.born.insurance.ws.order.common.QueryMessageOrder;
import com.born.insurance.ws.order.common.QueryReceviedMessageOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.common.MessageResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 *
 * @Description 站内消息管理
 *
 */
@Controller
@RequestMapping("/systemMg/message")
public class SiteMessageController extends BaseController {
	
	private final String vm_path = "/systemMg/message/";
	
	/*
	 * 管理员列表
	 */
	@RequestMapping("admin/list.htm")
	public String messageList(QueryMessageOrder order, HttpServletRequest request, Model model) {
		if (order == null)
			order = new QueryMessageOrder();
		QueryBaseBatchResult<MessageInfo> messageInfoList = siteMessageService
			.findMessage(order);
		model.addAttribute("page", PageUtil.getCovertPage(messageInfoList));
		model.addAttribute("queryMessageOrder", order);
		return vm_path + "managerList.vm";
	}
	
	/*
	 * 站内信詳情
	 */
	@RequestMapping("admin/detail.htm")
	public String messageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		QueryMessageOrder queryMessageOrder = new QueryMessageOrder();
		WebUtil.setPoPropertyByRequest(queryMessageOrder, request);
		QueryBaseBatchResult<MessageInfo> messageInfoList = siteMessageService
			.findMessage(queryMessageOrder);
		QueryReceviedMessageOrder queryMessageReceviedOrder = new QueryReceviedMessageOrder();
		queryMessageReceviedOrder.setMessageId(queryMessageOrder.getMessageId());
		QueryBaseBatchResult<MessageReceivedInfo> messageReceivedInfoList = siteMessageService
			.findReceviedMessage(queryMessageReceviedOrder);
		if (messageInfoList != null) {
			model.addAttribute("info", messageInfoList.getPageList().get(0));
			model.addAttribute("receivedNum", messageReceivedInfoList.getTotalCount());
			if (ListUtil.isNotEmpty(messageReceivedInfoList.getPageList())) {
				model.addAttribute("messageReceivedInfo", messageReceivedInfoList.getPageList()
					.get(0));
			}
		}
		return vm_path + "messageInfo.vm";
	}
	
	@RequestMapping("admin/form.htm")
	public String addMessage(HttpServletRequest request, Model model, String flag) {
		model.addAttribute("flag", flag);
		return vm_path + "addMessage.vm";
	}
	
	/*
	 * 添加站内消息
	 */
	@ResponseBody
	@RequestMapping("admin/send.json")
	public JSONObject addMessageInfo(HttpSession session, HttpServletRequest request) {
		MessageOrder messageOrder = new MessageOrder();
		WebUtil.setPoPropertyByRequest(messageOrder, request);
		JSONObject jsonObject = new JSONObject();
		if (StringUtil.hasBlank(messageOrder.getMessageTitle())) {
			jsonObject.put("success", false);
			jsonObject.put("message", "信息录入不全");
			return jsonObject;
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		messageOrder.setIsSendMessage(BooleanEnum.YES);
		messageOrder.setMessageSenderName(sessionLocal.getRealName());
		if (StringUtil.isBlank(messageOrder.getMessageSenderName())) {
			messageOrder.setMessageSenderName(sessionLocal.getRealName());
		}
		messageOrder.setMessageSenderId(sessionLocal.getUserId());
		messageOrder.setMessageSenderAccount(sessionLocal.getUserName());
		messageOrder.setNotificationType(NotificationTypeEnum.getByCode(request
			.getParameter("notificationType")));
		messageOrder.setMessageSubject(messageOrder.getMessageTitle());
		String sendUserAccount = request.getParameter("sendUserAccount");
		if (sendUserAccount != null) {
			sendUserAccount = sendUserAccount.replaceAll("，", ",");
			String[] sendUserAccountSplit = sendUserAccount.split(",");
			messageOrder.setSendUserAcounts(sendUserAccountSplit);
			messageOrder.setNotificationObject(sendUserAccount);
		}
		String sendUserRole = request.getParameter("sendUserRole");
		messageOrder.setMessageSenderRole(sendUserRole);
		InsuranceBaseResult result = siteMessageService.addMessageInfo(messageOrder);
		return toJSONResult(result, "发送站内信");
	}
	
	/*
	 * 用户消息列表
	 */
	@RequestMapping("user/list.htm")
	public String messageInfoList(HttpServletRequest request,
									QueryReceviedMessageOrder queryMessageOrder, Model model,
									String status) {
		long userId = ShiroSessionUtils.getSessionLocal().getUserId();
		if (queryMessageOrder == null)
			queryMessageOrder = new QueryReceviedMessageOrder();
		queryMessageOrder.setMessageReceivedId(userId);
		List<MessageReceivedStatusEnum> statusList = new ArrayList<>();
		if (StringUtil.isEmpty(status)) {
			statusList.add(MessageReceivedStatusEnum.UNREAD);
			statusList.add(MessageReceivedStatusEnum.READ);
			statusList.add(MessageReceivedStatusEnum.COLLECT);
		} else {
			statusList.add(MessageReceivedStatusEnum.getByCode(status));
		}
		queryMessageOrder.setStatusList(statusList);
		QueryBaseBatchResult<MessageReceivedInfo> messageInfoList = siteMessageService
			.findReceviedMessage(queryMessageOrder);
		model.addAttribute("page", PageUtil.getCovertPage(messageInfoList));
		model.addAttribute("status", status);
		return vm_path + "userList.vm";
	}
	
	/*
	 * 查詢消息数量
	 */
	@ResponseBody
	@RequestMapping("user/unRead.json")
	public JSONObject ajaxLoadUnReadData(HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		if (ShiroSessionUtils.getSessionLocal() != null) {
			long userId = ShiroSessionUtils.getSessionLocal().getUserId();
			try {
				long count = siteMessageService.loadUnReadMyMessageCount(userId);
				jsonObject.put("success", true);
				jsonObject.put("message", "查询成");
				jsonObject.put("count", count);
			} catch (Exception e) {
				jsonObject.put("success", false);
				jsonObject.put("message", "查询未读消息出错");
			}
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "您未登陆或已掉线");
		}
		return jsonObject;
	}
	
	/*
	 * 修改消息
	 */
	@RequestMapping("user/update.htm")
	public ModelAndView updateMessageInfo(HttpSession session, HttpServletRequest request,
											HttpServletResponse response, Model model, String status) {
		
		MyMessageOrder myMessageOrder = new MyMessageOrder();
		String ids = request.getParameter("receivedId");
		String[] receivedIds = ids == null ? null : ids.split(",");
		String type = request.getParameter("type");
		WebUtil.setPoPropertyByRequest(myMessageOrder, request);
		if (type.equals(MessageReceivedStatusEnum.COLLECT.code())) {
			myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.COLLECT.code());
		} else if (type.equals(MessageReceivedStatusEnum.READ.code())) {
			myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.READ.code());
		}
		myMessageOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		for (String receivedId : receivedIds) {
			myMessageOrder.setReceivedId(NumberUtil.parseLong(receivedId));
			siteMessageService.readMessageInfo(myMessageOrder);
		}
		if (StringUtil.isNotEmpty(status)) {
			return new ModelAndView(new RedirectView("/message/userList.htm?status=" + status));
		} else {
			return new ModelAndView(new RedirectView("/message/userList.htm"));
		}
	}
	
	/*
	* 修改消息
	*/
	@ResponseBody
	@RequestMapping("user/update.json")
	public JSONObject ajaxUpdateMessageInfo(HttpSession session, HttpServletRequest request,
											HttpServletResponse response, Model model, String status) {
		JSONObject json = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal == null) {
			json.put("success", false);
			json.put("message", "登陆失效");
			return json;
		}
		
		try {
			InsuranceBaseResult result = null;
			if ("YES".equals(request.getParameter("readAll"))) {
				result = siteMessageService.updateReceivedMessageStatus(
					sessionLocal.getUserId(), MessageReceivedStatusEnum.READ);
			} else {
				MyMessageOrder myMessageOrder = new MyMessageOrder();
				String ids = request.getParameter("receivedId");
				String[] receivedIds = ids == null ? null : ids.split(",");
				String type = request.getParameter("type");
				//WebUtil.setPoPropertyByRequest(myMessageOrder, request);
				if (type.equals(MessageReceivedStatusEnum.COLLECT.code())) {
					myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.COLLECT
						.code());
				} else if (type.equals(MessageReceivedStatusEnum.READ.code())) {
					myMessageOrder.setMessageReceivedStatus(MessageReceivedStatusEnum.READ.code());
				}
				myMessageOrder.setUserId(sessionLocal.getUserId());
				for (String receivedId : receivedIds) {
					myMessageOrder.setReceivedId(NumberUtil.parseLong(receivedId));
					result = siteMessageService.readMessageInfo(myMessageOrder);
				}
			}
			
			if (result != null && result.isSuccess()) {
				json.put("success", true);
				json.put("message", "操作成功");
			} else {
				json.put("success", false);
				json.put("message", "操作失败");
			}
			
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "更新消息出错");
			logger.error("更新消息出错 {}", e);
		}
		return json;
	}
	
	/*
	 * 删除收到的消息
	 */
	@RequestMapping("user/delete.htm")
	public void deleteReceivedMessageInfo(HttpSession session, HttpServletRequest request,
											HttpServletResponse response, Model model, String status) {
		MyMessageOrder myMessageOrder = new MyMessageOrder();
		MessageResult messageResult = null;
		String ids = request.getParameter("receivedId");
		String[] receivedIds = ids == null ? null : ids.split(",");
		myMessageOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
		for (String receivedId : receivedIds) {
			myMessageOrder.setReceivedId(NumberUtil.parseLong(receivedId));
			messageResult = siteMessageService.deleteReceivedMessageInfo(myMessageOrder);
		}
		model.addAttribute("messageResult", messageResult);
		try {
			response.sendRedirect("/message/user/list.htm");
		} catch (IOException e) {
			logger.error("删除消息出错 {}", e);
		}
	}
	
	/*
	 * 删除收到的消息
	 */
	@RequestMapping("user/delete.json")
	@ResponseBody
	public JSONObject ajaxDelete(HttpSession session, HttpServletRequest request,
									HttpServletResponse response, Model model, String status) {
		JSONObject result = new JSONObject();
		try {
			MyMessageOrder myMessageOrder = new MyMessageOrder();
			String ids = request.getParameter("receivedId");
			String[] receivedIds = ids == null ? null : ids.split(",");
			myMessageOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
			for (String receivedId : receivedIds) {
				myMessageOrder.setReceivedId(NumberUtil.parseLong(receivedId));
				siteMessageService.deleteReceivedMessageInfo(myMessageOrder);
			}
			result.put("success", true);
			result.put("message", "删除成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "删除消息出错");
			logger.error("删除消息出错 {}", e);
		}
		return result;
	}
	
}
