	package com.born.insurance.web.controller.remind;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.remind.MessageTemplateService;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.RemindContactTypeEnum;
import com.born.insurance.ws.enums.RemindTypeEnum;
import com.born.insurance.ws.info.common.MessageTemplateInfo;
import com.born.insurance.ws.order.common.MessageTemplateOrder;
import com.born.insurance.ws.order.common.MessageTemplateQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("/insurance/remind")
public class MessageTemplateController extends BaseController {
	
	@Autowired
	private MessageTemplateService messageTemplateService;
	
	private final String VM_PATH ="/insurance/remind/";
	
	
	/**
	 * 查询所有的提醒模板信息
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			MessageTemplateQueryOrder messageTemplateQueryOrder = new MessageTemplateQueryOrder();
			// 从页面的request去除分页参数传到order里面
			WebUtil.setPoPropertyByRequest(messageTemplateQueryOrder, request);
			String notifyType=request.getParameter("notifyType");
			messageTemplateQueryOrder.setNotifyType(RemindTypeEnum.getByCode(notifyType));
			QueryBaseBatchResult<MessageTemplateInfo> baseBatchResult = messageTemplateService.queryMessageTemplateInfoByCondition(messageTemplateQueryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(baseBatchResult));
			model.addAttribute("notifyTypes", RemindTypeEnum.getAllEnum());
			model.addAttribute("querykOrder", messageTemplateQueryOrder);
		} catch (Exception e) {
			logger.error("提醒模板", e);
		}
		return VM_PATH + "listRemind.vm";
	}
	
	
	/**
	 * 查询指定的提醒模板信息
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("info.htm")
	public String info(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
			MessageTemplateQueryOrder messageTemplateQueryOrder = new MessageTemplateQueryOrder();
			String id=request.getParameter("id");
			long messageId=NumberUtil.parseLong(id);
			messageTemplateQueryOrder.setId(messageId);
			MessageTemplateInfo messageTemplateInfo = messageTemplateService.queryMessageTemplateInfoByID(messageTemplateQueryOrder);
			model.addAttribute("messageTemplateInfo", messageTemplateInfo);
		} catch (Exception e) {
			logger.error("指定提醒详情", e);
		}
				return VM_PATH + "infoMessageTemplate.vm";
		
	}
	
	
	
	/**
	 * 查询指定的提醒模板信息
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model){
		try {
			MessageTemplateQueryOrder messageTemplateQueryOrder = new MessageTemplateQueryOrder();
			String id=request.getParameter("id");
			long messageId=NumberUtil.parseLong(id);
			messageTemplateQueryOrder.setId(messageId);
			MessageTemplateInfo messageTemplateInfo = messageTemplateService.queryMessageTemplateInfoByID(messageTemplateQueryOrder);
			model.addAttribute("messageTemplateInfo", messageTemplateInfo);
			model.addAttribute("remindTypes", RemindTypeEnum.getAllEnum());
			model.addAttribute("remindContactTypes", RemindContactTypeEnum.getAllEnum());
			model.addAttribute("id", id);
		} catch (Exception e) {
			logger.error("指定提醒详情", e);
		}
				return VM_PATH + "editMessageTemplate.vm";
		
	}
	
	
	/**
	 * 修改指定的提醒模板信息提交
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, HttpSession session,
			Model model,MessageTemplateOrder messageTemplateOrder){
		JSONObject jsonobj = new JSONObject();
		try {
			WebUtil.setPoPropertyByRequest(messageTemplateOrder, request);
			String id=request.getParameter("id");
			long messageId=NumberUtil.parseLong(id);
			String notifyType=request.getParameter("notifyTypeText");
			String day=request.getParameter("endDay");
			if(day.equals("OTHERS")){
				String days=request.getParameter("endDays");
				long endDay=NumberUtil.parseLong(days);
				messageTemplateOrder.setEndDay(endDay);
			}else{
				long otherDay=NumberUtil.parseLong(day);
				messageTemplateOrder.setEndDay(otherDay);
			}
			messageTemplateOrder.setId(messageId);
			messageTemplateOrder.setNotifyType(RemindTypeEnum.getByCode(notifyType));
			InsuranceBaseResult result = messageTemplateService.updateMessageTemplateInfoByID(messageTemplateOrder);
			if(result.isSuccess()){
				jsonobj.put("code", "1");
				jsonobj.put("message", "执行成功");
			}else{
				jsonobj.put("code", "0");
				jsonobj.put("message", "执行失败");
			}
		} catch (Exception e) {
			logger.error("编辑提交", e);
		}
		return jsonobj;
	}	
}
