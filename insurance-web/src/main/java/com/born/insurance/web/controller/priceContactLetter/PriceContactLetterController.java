package com.born.insurance.web.controller.priceContactLetter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.ListUI;

import com.born.insurance.dal.daointerface.CustomerCertInfoDAO;
import com.born.insurance.dal.daointerface.PriceContactLetterReportPriceDAO;
import com.born.insurance.dal.dataobject.CustomerCertInfoDO;
import com.born.insurance.dal.dataobject.PriceContactLetterReportPriceDO;
import com.born.insurance.dal.dataobject.ProjectSetupCustomerDO;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.insuranceConditions.InsuranceConditionsInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.info.priceContactLetterDemandDetail.PriceContactLetterDemandDetailInfo;
import com.born.insurance.ws.info.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoInfo;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCatalogInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import com.born.insurance.ws.service.insuranceConditions.InsuranceConditionsService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupCatalogService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.PriceWayEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterFormInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.info.priceTemplate.PriceTemplateInfo;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogQueryOrder;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterOrder;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterQueryOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterReportPriceOrder;
import com.born.insurance.ws.order.priceTemplate.PriceTemplateQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;
import com.born.insurance.ws.service.priceTemplate.PriceTemplateService;
import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/priceContactLetter")
public class PriceContactLetterController extends WorkflowBaseController {
	@Autowired
	protected PriceContactLetterService priceContactLetterService;
	
	@Autowired
	protected PriceTemplateService priceTemplateService;
	
	@Autowired
	private InsuranceCatalogService insuranceCatalogService;
	
	@Autowired
	private InsuranceConditionsService insuranceConditionsService;
	
	@Autowired
	private BaseDataInfoService baseDataInfoService;
	
	@Autowired
	private CustomerCertInfoDAO customerCertInfoDAO;
	
	@Autowired
	private PriceContactLetterReportPriceDAO priceContactLetterReportPriceDAO;

	@Autowired
	private ProjectSetupService projectSetupService;

	@Autowired
	private ProjectSetupCatalogService projectSetupCatalogService;
	
	private final static String VM_PATH = "/insurance/priceContactLetter/";
	
	/**
	 * 风险预警处理表
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			PriceContactLetterQueryOrder queryOrder = new PriceContactLetterQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<PriceContactLetterFormInfo> batchResult = priceContactLetterService
				.queryFormList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listPriceContactLetter.vm";
	}
	
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, Model model) {
		PriceTemplateQueryOrder queryOrder = new PriceTemplateQueryOrder();
		queryOrder.setPageSize(30);
		queryOrder.setSortCol("ID");
		queryOrder.setSortOrder("DESC");
		model.addAllAttributes(WebUtil.getRequestMap(request));
		List<PriceTemplateInfo> templateInfos = priceTemplateService.queryList(queryOrder)
			.getPageList();
		model.addAttribute("templateInfos", templateInfos);
		model.addAttribute("ways", PriceWayEnum.getAllEnum());
		//查询保险分类
		InsuranceCatalogQueryOrder insuranceCatalogQueryOrder = new InsuranceCatalogQueryOrder();
		QueryBaseBatchResult<InsuranceCatalogInfo> catalogInfoList = insuranceCatalogService
			.queryList(insuranceCatalogQueryOrder);
		model.addAttribute("catalogInfoList", catalogInfoList.getPageList());
		String code = request.getParameter("errCode");
		if (StringUtil.equalsIgnoreCase(code, "1")) {
			model.addAttribute("errorMessage1", "未找到对应询价模板");
		}
		return VM_PATH + "addPrice.vm";
	}
	
	@RequestMapping("addNext.htm")
	public String addNext(HttpServletRequest request, Model model) {
		model.addAllAttributes(WebUtil.getRequestMap(request));
		String catalogId = StringUtil.defaultIfEmpty(request.getParameter("catalogId"), "0");
		String businessConditionId = StringUtil.defaultIfEmpty(
			request.getParameter("businessConditionId"), "0");
		model.addAttribute("projectSetupId",
			NumberUtil.parseLong(request.getParameter("projectSetupId")));
		String priceTemplate = request.getParameter("priceTemplate");
		if (StringUtil.isEmpty(priceTemplate)) {
			InsuranceConditionsInfo insuranceConditionsInfo = insuranceConditionsService
				.findById(NumberUtil.parseLong(request.getParameter("businessConditionId")));
			if (insuranceConditionsInfo != null) {
				BaseDataInfoInfo baseDataInfoInfo = baseDataInfoService
					.findById(insuranceConditionsInfo.getPriceTemplate());
				if (baseDataInfoInfo != null) {
					priceTemplate = baseDataInfoInfo.getCode();
					model.addAttribute("priceTemplate", priceTemplate);
				}
			} else {
				InsuranceCatalogInfo catalogInfo = insuranceCatalogService.findPriceTemplateById(NumberUtil
					.parseLong(catalogId));
				if (catalogInfo != null) {
					BaseDataInfoInfo baseDataInfoInfo = baseDataInfoService.findById(catalogInfo
						.getPriceTemplate());
					if (baseDataInfoInfo != null) {
						priceTemplate = baseDataInfoInfo.getCode();
						model.addAttribute("priceTemplate", priceTemplate);
					}
				}
			}
		}

		long projectSetupId =  NumberUtil.parseLong(request.getParameter("projectSetupId"));
		if(StringUtil.equalsIgnoreCase(catalogId,"0")){
			ProjectSetupInfo projectSetupInfo = projectSetupService.findById(projectSetupId);
			if (null != projectSetupInfo) {
				List<ProjectSetupCatalogInfo> catalogInfos = projectSetupCatalogService
						.findList(projectSetupInfo.getProjectSetupId());
				StringBuilder catalogIds = new StringBuilder();
				for(ProjectSetupCatalogInfo catalogInfo : catalogInfos){
					catalogIds.append(catalogInfo.getCatalogId()).append(",");
				}
				if(StringUtil.isNotEmpty(catalogIds.toString())){
					model.addAttribute("certainIds",catalogIds.toString().substring(0,catalogIds.length()-1));
				}

			}
		}else{
			model.addAttribute("certainIds",catalogId);
		}

//		String customerUserNames = projectSetupInfo.getCustomerUserName();
//		if (!StringUtils.isBlank(customerUserNames)) {
//			List<ProjectSetupCustomerDO> list = JSONObject.parseArray(customerUserNames,
//					ProjectSetupCustomerDO.class);
//			StringBuilder companys = new StringBuilder();
//			for(ProjectSetupCustomerDO customerDO : list){
//				companys.append(customerDO.getCustomerId()).append(",");
//			}
//
//			if(StringUtil.isNotEmpty(companys.toString())){
////				model.addAttribute("companys",companys.toString().substring(0,companys.length()-1));
//			}
//
//		}

		model.addAttribute("catalogId", catalogId);
		model.addAttribute("businessConditionId", businessConditionId);
		if (StringUtil.isEmpty(priceTemplate)) {
			model.addAttribute("errCode", 1);
			return "redirect:/insurance/priceContactLetter/add.htm";
		}
		return VM_PATH + "add" + StringUtil.capitalize(priceTemplate) + ".vm";
	}
	
	@RequestMapping("forwardAdd.htm")
	public String forwardAdd(@RequestParam(value = "priceContactId", required = false,
			defaultValue = "0") long priceContactId, HttpServletRequest request, Model model) {
		PriceContactLetterInfo info = null;
		if (priceContactId > 0) {
			info = priceContactLetterService.forwardFindById(priceContactId);
			info.setId(0);
			info.setFormId(0);
			List<PriceContactLetterDemandInfo> demandInfoList = info.getDemandInfos();
			if (ListUtil.isNotEmpty(demandInfoList)) {
				for (PriceContactLetterDemandInfo demandInfo : demandInfoList) {
					demandInfo.setId(0);
					demandInfo.setContactLetterId(0);
					demandInfo.getSchemeInfo().setId(0);
					for (PriceContactLetterSchemeDetailInfo schemeDetailInfo : demandInfo
						.getSchemeInfo().getSchemeDetailInfoList()) {
						schemeDetailInfo.setId(0);
					}
					for (PriceContactLetterDemandDetailInfo demandDetailInfo : demandInfo
						.getDemandDetailInfoList()) {
						demandDetailInfo.setId(0);
					}
					
					for (PriceContactLetterDemandDetailTwoInfo demandDetailTwoInfo : demandInfo
						.getDemandDetailTwoInfoList()) {
						demandDetailTwoInfo.setId(0);
					}
					
				}
			}
			CustomerCertInfoDO certInfoDO = new CustomerCertInfoDO();
			certInfoDO.setUserId(info.getCustomerUserId());
			List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
				certInfoDO, null, null, 0, 100);
			for (CustomerCertInfoDO cert : customerCertInfoDOs) {
				cert.setCertTypeName(CertTypeEnum.getByCode(cert.getCertType()).getMessage());
			}
			model.addAttribute("isEdit", "Y");
			model.addAttribute("certTypes", customerCertInfoDOs);
			model.addAttribute("info", info);
		}
		return VM_PATH + "add" + StringUtil.capitalize(info.getPriceTemplate()) + ".vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
						HttpServletRequest request, Model model) {
		PriceContactLetterInfo info = null;
		if (formId > 0) {
			FormInfo formInfo = formService.findByFormId(formId);
			info = priceContactLetterService.findByFormId(formId);
			CustomerCertInfoDO certInfoDO = new CustomerCertInfoDO();
			certInfoDO.setUserId(info.getCustomerUserId());
			List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
				certInfoDO, null, null, 0, 100);
			for (CustomerCertInfoDO cert : customerCertInfoDOs) {
				cert.setCertTypeName(CertTypeEnum.getByCode(cert.getCertType()).getMessage());
			}
			model.addAttribute("certTypes", customerCertInfoDOs);
			model.addAttribute("info", info);
			model.addAttribute("form", formInfo);
			model.addAttribute("isEdit", "Y");
		} else {
			info = new PriceContactLetterInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "add" + StringUtil.capitalize(info.getPriceTemplate()) + ".vm";
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, PriceContactLetterOrder order) {
		JSONObject json = new JSONObject();
		
		String tipPrefix = "询价";
		try {
			setSessionLocalInfoCreatorOrder(order);
			setSessionLocalInfo2Order(order);
			order.setFormCode(FormCodeEnum.PRICESCHEME);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			InsuranceBaseResult result = priceContactLetterService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("audit.htm")
	public String audit(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		try {
			FormInfo form = formService.findByFormId(formId);
			if (null != form) {
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
			}
			PriceContactLetterInfo info = priceContactLetterService.findByFormId(formId);
			CustomerCertInfoDO certInfoDO = new CustomerCertInfoDO();
			certInfoDO.setUserId(info.getCustomerUserId());
			List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
				certInfoDO, null, null, 0, 100);
			for (CustomerCertInfoDO cert : customerCertInfoDOs) {
				cert.setCertTypeName(CertTypeEnum.getByCode(cert.getCertType()).getMessage());
			}
			model.addAttribute("isEdit", "Y");
			model.addAttribute("certTypes", customerCertInfoDOs);
			model.addAttribute("info", info);
			return VM_PATH + "audit"
					+ StringUtil.capitalize(priceContactLetterService.priceSchemeCode(info))
					+ ".vm";
		} catch (Exception e) {
			logger.error("查询出错");
		}
		return null;
	}
	
	@RequestMapping("reportPrice.htm")
	public String reportPrice(@RequestParam(value = "id", required = true) long id,
								HttpServletRequest request, Model model) {
		try {
			PriceContactLetterInfo priceContactLetterInfo = priceContactLetterService.findById(id);
			model.addAttribute("info", priceContactLetterInfo);
			model.addAttribute("isEdit", "Y");
			CustomerCertInfoDO certInfoDO = new CustomerCertInfoDO();
			certInfoDO.setUserId(priceContactLetterInfo.getCustomerUserId());
			List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
				certInfoDO, null, null, 0, 100);
			for (CustomerCertInfoDO cert : customerCertInfoDOs) {
				cert.setCertTypeName(CertTypeEnum.getByCode(cert.getCertType()).getMessage());
			}
			model.addAttribute("certTypes", customerCertInfoDOs);
			return VM_PATH
					+ "report"
					+ StringUtil.capitalize(priceContactLetterService
						.priceSchemeCode(priceContactLetterInfo)) + ".vm";
		} catch (Exception e) {
			logger.error("查询出错");
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("reportPriceSubmit.json")
	public Object reportPriceSubmit(PriceContactLetterReportPriceOrder priceOrder, Model model) {
		
		JSONObject json = new JSONObject();
		
		String tipPrefix = "报价";
		try {
			setSessionLocalInfo2Order(priceOrder);
			priceOrder.setFormCode(FormCodeEnum.REPORTPRICE);
			priceOrder.setCheckIndex(0);
			priceOrder.setCheckStatus(1);
			InsuranceBaseResult result = priceContactLetterService.saveReportPrice(priceOrder);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("view.htm")
	public String view(long id, HttpServletRequest request, Model model) {
		PriceContactLetterInfo priceContactLetterInfo = priceContactLetterService.findById(id);
		FormInfo form = formService.findByFormId(priceContactLetterInfo.getFormId());
		model.addAttribute("form", form);
		model.addAttribute("info", priceContactLetterInfo);
		model.addAttribute("isEdit", "Y");
		CustomerCertInfoDO certInfoDO = new CustomerCertInfoDO();
		certInfoDO.setUserId(priceContactLetterInfo.getCustomerUserId());
		List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
			certInfoDO, null, null, 0, 100);
		for (CustomerCertInfoDO cert : customerCertInfoDOs) {
			cert.setCertTypeName(CertTypeEnum.getByCode(cert.getCertType()).getMessage());
		}
		model.addAttribute("certTypes", customerCertInfoDOs);
		return VM_PATH
				+ "view"
				+ StringUtil.capitalize(priceContactLetterService
					.priceSchemeCode(priceContactLetterInfo)) + ".vm";
	}
	
	@RequestMapping("reportPriceDetail.htm")
	public String reportPriceDetail(long id, HttpServletRequest request, Model model) {
		PriceContactLetterInfo priceContactLetterInfo = priceContactLetterService.findById(id);
		FormInfo form = formService.findByFormId(priceContactLetterInfo.getFormId());
		model.addAttribute("form", form);
		model.addAttribute("info", priceContactLetterInfo);
		model.addAttribute("isEdit", "Y");
		return VM_PATH
				+ "viewReport"
				+ StringUtil.capitalize(priceContactLetterService
					.priceSchemeCode(priceContactLetterInfo)) + ".vm";
	}
	
	@RequestMapping("auditReportPriceDetail.htm")
	public String auditReportPriceDetail(	@RequestParam(value = "formId", required = true) long formId,
											HttpServletRequest request, Model model) {
		PriceContactLetterInfo priceContactLetterInfo = priceContactLetterService
			.findByFormId(formId);
		FormInfo form = formService.findByFormId(priceContactLetterInfo.getFormId());
		model.addAttribute("form", form);
		model.addAttribute("info", priceContactLetterInfo);
		model.addAttribute("isEdit", "Y");
		return VM_PATH
				+ "viewReport"
				+ StringUtil.capitalize(priceContactLetterService
					.priceSchemeCode(priceContactLetterInfo)) + ".vm";
	}
	
	@RequestMapping("reportAnalyse.htm")
	public String reportAnalyse(HttpServletRequest request, Model model) {
		model.addAllAttributes(WebUtil.getRequestMap(request));
		String id = request.getParameter("id");
		PriceContactLetterReportPriceDO priceDO = priceContactLetterReportPriceDAO
				.findByContactLetterId(NumberUtil.parseLong(id));
		queryCommonAttachmentData(model, id, CommonAttachmentTypeEnum.REPORT_ANALYSE);
		model.addAttribute("priceDO",priceDO);
		return VM_PATH + "reportAnalyse.vm";
	}
	
	@ResponseBody
	@RequestMapping("reportAnalyseSubmit.json")
	public Object reportAnalyseSubmit(HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			InsuranceBaseResult result = new InsuranceBaseResult();
			String contactLetterId = request.getParameter("contactLetterId");
			PriceContactLetterReportPriceDO priceDO = priceContactLetterReportPriceDAO
				.findByContactLetterId(NumberUtil.parseLong(contactLetterId));
			priceDO.setRemark(request.getParameter("remark"));
			priceContactLetterReportPriceDAO.update(priceDO);
			addAttachfile(contactLetterId, request, contactLetterId, null,
				CommonAttachmentTypeEnum.REPORT_ANALYSE);
			result.setSuccess(true);
			json = toJSONResult(result, "报价分析");
			System.out.println("as");
		} catch (Exception e) {
			logger.error("报价分析", e);
			json = toJSONResult("报价分析", e);
		}
		return json;
	}
	
}
