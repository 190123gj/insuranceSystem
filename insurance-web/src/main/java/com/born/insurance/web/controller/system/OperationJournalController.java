package com.born.insurance.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import com.born.insurance.biz.service.common.OperationJournalService;
import com.born.insurance.integration.bpm.user.UserDetailsService;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.order.common.OperationJournalQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.yjf.common.lang.util.StringUtil;

/**
 * 操作日志controller
 * @author ji
 * 
 */
@Controller
@RequestMapping("systemMg/operationJournal")
public class OperationJournalController extends BaseController {

	@Autowired
	OperationJournalService operationJournalService;
	
	private final String vm_path = "/systemMg/operationJournal/";
	
	@RequestMapping("list.htm")
	public String list(OperationJournalQueryOrder order, Model model) {
		model.addAttribute("queryOrder", order);
		model.addAttribute("page",
			PageUtil.getCovertPage(operationJournalService.queryOperationJournalInfo(order)));
		return vm_path + "operationJournalList.vm";
	}
	

}
