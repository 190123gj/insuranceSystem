package com.born.insurance.web.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.insurance.util.StringUtil;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.born.insurance.ws.order.common.CommonAttachmentQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("insurance")
public class ProjectHomeController extends WorkflowBaseController {
	
	final static String vm_path = "/insurance/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	/**
	 * 项目附件
	 * @param order
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("projectAttach.htm")
	public String projectAttach(CommonAttachmentQueryOrder order, HttpServletRequest request,
								Model model) {
		//项目附件
		if (order == null)
			order = new CommonAttachmentQueryOrder();
		if (StringUtil.isNotBlank(order.getProjectCode())) {
			QueryBaseBatchResult<CommonAttachmentInfo> attachement = commonAttachmentService
				.queryPage(order);
			model.addAttribute("pageAttach", PageUtil.getCovertPage(attachement));
			model.addAttribute("hasAttach", attachement != null && attachement.getTotalCount() > 0);
			model.addAttribute("projectCode", order.getProjectCode());
		}
		return vm_path + "project/projectAttachList.vm";
	}
	
}
