package com.born.insurance.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import com.born.insurance.ws.info.common.SysDbBackupConfigInfo;
import com.born.insurance.ws.info.common.SysDbBackupLogInfo;
import com.born.insurance.ws.order.common.SysDbBackupConfigOrder;
import com.born.insurance.ws.order.common.SysDbBackupConfigQueryOrder;
import com.born.insurance.ws.order.common.SysDbBackupLogQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;

@Controller
@RequestMapping("systemMg/dbbackup")
public class SysDbBackupController extends BaseController {
	
	private static final String vm_path = "/systemMg/dbbackup/";
	
	/**
	 * 填写配置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("config/form.htm")
	public String configForm(Long configId, HttpServletRequest request, Model model) {
		if (configId != null && configId > 0) {
			SysDbBackupConfigQueryOrder order = new SysDbBackupConfigQueryOrder();
			order.setConfigId(configId);
			QueryBaseBatchResult<SysDbBackupConfigInfo> configInfo = sysDbBackupService
				.queryConfig(order);
			if (configInfo != null && configInfo.getTotalCount() > 0) {
				model.addAttribute("config", configInfo.getPageList().get(0));
			}
		}
		return vm_path + "configAdd.vm";
	}
	
	/**
	 * 配置列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("config/list.htm")
	public String configList(HttpServletRequest request, Model model) {
		SysDbBackupConfigQueryOrder order = new SysDbBackupConfigQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		QueryBaseBatchResult<SysDbBackupConfigInfo> configInfo = sysDbBackupService
			.queryConfig(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("page", PageUtil.getCovertPage(configInfo));
		return vm_path + "configList.vm";
	}

	/**
	 * 根据配置手动备份
	 * @param configId
	 * @param model
     * @return
     */
	@RequestMapping("manual.htm")
	@ResponseBody
	public JSONObject manual(long configId, Model model) {
		JSONObject json = new JSONObject();
		try {
			InsuranceBaseResult result = sysDbBackupService.runBackupByConfigId(configId);
			toJSONResult(json, result, "备份成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "备份出错");
			logger.error("手动备份出错{}", e);
		}
		return json;
	}
	
	/**
	 * 保存配置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("config/save.htm")
	@ResponseBody
	public JSONObject saveConfig(HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			SysDbBackupConfigOrder order = new SysDbBackupConfigOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = sysDbBackupService.saveConfig(order);
			toJSONResult(json, result, null, null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存配置出错");
			logger.error("保存配置出错{}", e);
		}
		return json;
	}

	/**
	 * 启用/禁用配置
	 * @param configId
	 * @param model
     * @return
     */
	@RequestMapping("config/changeInUse.htm")
	@ResponseBody
	public JSONObject changeInUse(long configId, Model model) {
		JSONObject json = new JSONObject();
		try {
			InsuranceBaseResult result = sysDbBackupService.changeInUse(configId);
			toJSONResult(json, result, null, null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "更新配置出错");
			logger.error("更新配置出错{}", e);
		}
		return json;
	}

	/**
	 * 删除配置
	 * @param configId
	 * @param model
     * @return
     */
	@RequestMapping("config/delete.htm")
	@ResponseBody
	public JSONObject saveConfig(long configId, Model model) {
		JSONObject json = new JSONObject();
		try {
			InsuranceBaseResult result = sysDbBackupService.delConfig(configId);
			toJSONResult(json, result, null, null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "删除配置出错");
			logger.error("删除配置出错{}", e);
		}
		return json;
	}
	
	/**
	 * 备份日志列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("log/list.htm")
	public String logList(HttpServletRequest request, Model model) {
		SysDbBackupLogQueryOrder order = new SysDbBackupLogQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		QueryBaseBatchResult<SysDbBackupLogInfo> logInfo = sysDbBackupService.queryLog(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("page", PageUtil.getCovertPage(logInfo));
		return vm_path + "logList.vm";
	}

	/**
	 * 删除备份文件
	 * @param logId
	 * @param model
     * @return
     */
	@RequestMapping("log/delFile.htm")
	@ResponseBody
	public JSONObject delFile(long logId, Model model) {
		JSONObject json = new JSONObject();
		try {
			InsuranceBaseResult result = sysDbBackupService.delBackupFile(logId);
			toJSONResult(json, result, null, null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "删除备份文件出错");
			logger.error("删除备份文件出错{}", e);
		}
		return json;
	}
}
