package com.born.insurance.web.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.insurance.dal.daointerface.CustomerContactWayDAO;
import com.born.insurance.dal.dataobject.CustomerContactWayDO;
import com.born.insurance.ws.enums.*;
import com.born.insurance.ws.info.insuranceProtocol.InsuranceProtocolInfo;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolQueryOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.ReportPriceDetailQueryOrder;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.biz.service.customer.CustomerProtocolService;
import com.born.insurance.dal.daointerface.CustomerCertInfoDAO;
import com.born.insurance.dal.daointerface.InsuranceCatalogLiabilityDAO;
import com.born.insurance.dal.daointerface.InsuranceCatalogRelationDAO;
import com.born.insurance.dal.dataobject.CustomerCertInfoDO;
import com.born.insurance.dal.dataobject.InsuranceCatalogLiabilityDO;
import com.born.insurance.dal.dataobject.InsuranceCatalogRelationDO;
import com.born.insurance.dataobject.CustomerProtocolVo;
import com.born.insurance.integration.bpm.comparator.MenuComparator;
import com.born.insurance.integration.bpm.info.menu.MenuInfo;
import com.born.insurance.integration.bpm.info.org.OrgInfo;
import com.born.insurance.integration.bpm.org.OrgService;
import com.born.insurance.integration.bpm.user.PermissionUtil;
import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.bpm.Org;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.born.insurance.ws.info.common.IndustryInfo;
import com.born.insurance.ws.info.common.RegionInfo;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.customer.CustomerPersonInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceConditions.InsuranceConditionsInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceLiability.InsuranceLiabilityInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceDetailInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCatalogInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCustomerInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupFormInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.businessBill.BusinessBillQueryOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.order.customer.CustomerProtocolQueryOrder;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogQueryOrder;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsQueryOrder;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityQueryOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductQueryOrder;
import com.born.insurance.ws.order.insuranceProtocol.ProtocolChargeQueryOrder;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeQueryOrder;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterQueryOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.common.CommonAttachmentResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import com.born.insurance.ws.service.businessBill.BusinessBillService;
import com.born.insurance.ws.service.common.BasicDataCacheService;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.born.insurance.ws.service.insuranceConditions.InsuranceConditionsService;
import com.born.insurance.ws.service.insuranceContactLetter.InsuranceContactLetterService;
import com.born.insurance.ws.service.insuranceLiability.InsuranceLiabilityService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.born.insurance.ws.service.insuranceProtocol.InsuranceProtocolService;
import com.born.insurance.ws.service.insuranceProtocolCharge.InsuranceProtocolChargeService;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupCatalogService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import com.born.insurance.ws.service.projectSetup.ProjectsetupCustomerService;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping("baseDataLoad")
public class BaseDataLoadController extends BaseController {
	
	@Autowired
	protected OrgService orgService;
	
	@Autowired
	protected BasicDataCacheService basicDataCacheService;
	
	@Autowired
	protected BaseDataInfoService baseDataInfoService;
	
	@Autowired
	protected InsuranceLiabilityService insuranceLiabilityService;
	
	@Autowired
	protected CustomerProtocolService customerProtocolService;
	
	@Autowired
	protected CustomerPersonService customerPersonService;
	
	@Autowired
	private InsuranceProductService insuranceProductService;
	
	@Autowired
	protected InsuranceCatalogService insuranceCatalogService;
	
	@Autowired
	protected InsuranceConditionsService insuranceConditionsService;
	
	@Autowired
	protected InsuranceProtocolChargeService insuranceProtocolChargeService;
	
	@Autowired
	protected BusinessBillService businessBillService;
	
	@Autowired
	protected InsuranceCatalogLiabilityDAO insuranceCatalogLiabilityDAO;
	
	@Autowired
	protected ProjectSetupService projectSetupService;
	
	@Autowired
	protected ProjectsetupCustomerService projectsetupCustomerService;
	
	@Autowired
	protected ProjectSetupCatalogService projectSetupCatalogService;
	
	@Autowired
	protected CustomerCertInfoDAO customerCertInfoDAO;
	
	@Autowired
	protected InsuranceCatalogRelationDAO insuranceCatalogRelationDAO;
	
	@Autowired
	protected CustomerCompanyService customerCompanyService;
	
	@Autowired
	protected InsuranceProtocolService insuranceProtocolService;
	
	@Autowired
	protected PriceContactLetterService priceContactLetterService;
	
	@Autowired
	protected InsuranceContactLetterService insuranceContactLetterService;

	@Autowired
	protected CustomerContactWayDAO customerContactWayDAO;
	
	
	
	/**
	 * 选择行业
	 *
	 * @param parentCode
	 * @param model
	 * @return
	 */
	@RequestMapping("industry.json")
	@ResponseBody
	public JSONObject industry(String parentCode, Model model) {
		String tipPrefix = "查询行业信息";
		JSONObject result = new JSONObject();
		try {
			
			List<IndustryInfo> data = basicDataCacheService.queryIndustry(parentCode);
			JSONArray finalData = new JSONArray();
			for (IndustryInfo info : data) {
				JSONObject json = new JSONObject();
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("level", info.getLevel());
				json.put("remark", info.getRemark() == null ? "" : info.getRemark());
				finalData.add(json);
			}
			result = toStandardResult(finalData, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 获取菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadMenuData.json")
	public String loadMenuJsonb(HttpServletRequest request, HttpServletResponse response,
								Model model) {
		String tipPrefix = "获取菜单";
		String currentString = request.getParameter("currentUrl");
		currentString = PermissionUtil.filterUrl(currentString);
		String[] stringSplit = currentString.split("/");
		JSONObject jsonObject = new JSONObject();
		if (stringSplit.length > 1) {
			List<MenuInfo> menuInfos = ShiroSessionUtils.getSessionLocal()
				.getUserMenuInfoTreeBySysAlias(stringSplit[1]);
			
			JSONObject dataObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			Collections.sort(menuInfos, new MenuComparator<MenuInfo>());
			makeMenuJsonTree(menuInfos, jsonArray, 0);
			dataObject.put("list", jsonArray);
			jsonObject = toStandardResult(dataObject, tipPrefix);
			
		} else {
			jsonObject = toStandardResult(null, tipPrefix);
		}
		printHttpResponse(response, jsonObject);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void makeMenuJsonTree(List<MenuInfo> menuInfos, JSONArray jsonArray, long index) {
		for (MenuInfo menuInfo : menuInfos) {
			menuInfo.setRank(index);
			Map<String, Object> map = MiscUtil.covertPoToMap(menuInfo);
			map.remove("parentId");
			map.remove("resId");
			List<MenuInfo> childMenuInfos = (List<MenuInfo>) map.get("subclass");
			Collections.sort(childMenuInfos, new MenuComparator<MenuInfo>());
			JSONArray childJsonArray = new JSONArray();
			makeMenuJsonTree(childMenuInfos, childJsonArray, index + 1);
			map.put("subclass", childMenuInfos);
			jsonArray.add(map);
		}
	}
	
	/**
	 * 获取部门/组织结构下的人员
	 * @param orgCode
	 * @param model
	 * @return
	 */
	@RequestMapping("loadOrgMenbers.json")
	@ResponseBody
	public JSONObject loadOrgMenbersJson(String orgCode, Model model) {
		
		String tipPrefix = "获取部门/组织结构下的人员";
		JSONObject result = new JSONObject();
		try {
			List<UserInfo> userList = orgService.findOrgMenbersByCode(orgCode);
			result = toStandardResult(makeOrgMenbersJson(userList), tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 获取部门/组织结构
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("loadOrgData.json")
	public String loadOrgJson(HttpServletRequest request, HttpServletResponse response,
								String orgCode, Model model) {
		String tipPrefix = "获取部门/组织结构";
		JSONObject jsonObject = new JSONObject();
		List<OrgInfo> orgInfos = Lists.newArrayList();
		
		OrgInfo orgInfo = orgService.findOrgInSystemByCode(orgCode);
		orgInfos.add(orgInfo);
		JSONObject dataObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		makeOrgJsonTree(orgInfos, jsonArray, 0);
		dataObject.put("list", jsonArray);
		jsonObject = toStandardResult(dataObject, tipPrefix);
		printHttpResponse(response, jsonObject);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void makeOrgJsonTree(List<OrgInfo> orgInfos, JSONArray jsonArray, long index) {
		for (OrgInfo orgInfo : orgInfos) {
			orgInfo.setRank(index);
			Map<String, Object> map = MiscUtil.covertPoToMap(orgInfo);
			List<OrgInfo> childOrgInfos = (List<OrgInfo>) map.get("subOrg");
			// Collections.sort(childOrgInfos, new MenuComparator<OrgInfo>());
			JSONArray childJsonArray = new JSONArray();
			makeOrgJsonTree(childOrgInfos, childJsonArray, index + 1);
			map.put("subOrg", childOrgInfos);
			jsonArray.add(map);
		}
	}
	
	private JSONArray makeOrgMenbersJson(List<UserInfo> userList) {
		JSONArray jsonArray = new JSONArray();
		for (UserInfo user : userList) {
			JSONObject jso = new JSONObject();
			jso.put("userId", user.getUserId());
			jso.put("userName", user.getUserName());
			jso.put("realName", user.getRealName());
			jsonArray.add(jso);
		}
		return jsonArray;
	}
	
	@RequestMapping("downLoad.htm")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, long id,
							String fileName, String path) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			File file = null;
			if (StringUtil.isNotEmpty(path)) {
				file = new File(path);
			}
			// 允许自定义上传ｆｉｌｅＮａｍｅ和　ｐａｔｈ,　如果有id 依然以id查出的属性为优先。
			if (id > 0) {
				CommonAttachmentResult result = commonAttachmentService.findById(id);
				if (null != result && null != result.getAttachmentInfo()) {
					CommonAttachmentInfo info = result.getAttachmentInfo();
					fileName = info.getFileName();
					file = new File(info.getFilePhysicalPath());
				} else {
					return;
				}
			}
			response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			response.setContentType("application/msexcel");
			response.setContentLength((int) file.length());
			byte[] buffer = new byte[4096];// 缓冲区
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			//遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); //不可少
			response.flushBuffer();//不可少
			
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("下载附件异常：" + e);
		} finally {
			//关闭流，不可少
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 查询用户明细
	 * @param userId
	 * @param userAccount
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("userDetail.json")
	@ResponseBody
	public JSONObject userDetail(Long userId, String userAccount, HttpServletRequest request,
									Model model) {
		String tipPrefix = "查询用户";
		JSONObject result = new JSONObject();
		try {
			UserDetailInfo userDetail = null;
			if (userId != null && userId > 0) {
				userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
			} else if (StringUtil.isNotEmpty(userAccount)) {
				userDetail = bpmUserQueryService.findUserDetailByAccount(userAccount);
			}
			if (userDetail != null) {
				result = toStandardResult((JSONObject) JSONObject.toJSON(userDetail), tipPrefix);
			} else {
				result = toStandardResult(new JSONObject(), tipPrefix);
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询用户信息出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询部门信息
	 * @param deptId
	 * @param deptCode
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("dept.json")
	@ResponseBody
	public JSONObject dept(Long deptId, String deptCode, HttpServletRequest request, Model model) {
		String tipPrefix = "查询部门";
		JSONObject result = new JSONObject();
		try {
			Org org = null;
			if (deptId != null && deptId > 0) {
				org = bpmUserQueryService.findDeptById(deptId);
			} else if (StringUtil.isNotEmpty(deptCode)) {
				org = bpmUserQueryService.findDeptByCode(deptCode);
			}
			
			if (org != null) {
				result = toStandardResult((JSONObject) JSONObject.toJSON(org), tipPrefix);
			} else {
				result = toStandardResult(new JSONObject(), tipPrefix);
			}
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询部门出错 {} ", e);
		}
		return result;
	}
	
	@RequestMapping("createDoc.json")
	@ResponseBody
	public JSONObject createDoc(HttpServletRequest request, long formId, String type,
								String htmlData) {
		String tipPrefix = "生成word文档";
		JSONObject result = new JSONObject();
		try {
			String fileName = DOC_MAP.get(type) + formId;
			if (StringUtil.isNotBlank(fileName)) {
				String filePath = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_WORD_DOWNLOAD_FOLDER.code())
									+ File.separator + fileName + ".doc";
				File file = new File(filePath);
				FileOutputStream fos = new FileOutputStream(file);
				Writer os = new OutputStreamWriter(fos, "UTF-8");
				String button = "<div style=\"position: fixed; right: 0;top:50%; background:#e15450;color:#fff;cursor: pointer;padding: 5px;border-radius: 5px;\"><a id=\"btn\">导出</a><br>";
				String button2 = "<a style=\"color: #fff;text-decoration: blink; margin-top:5px; display: block;\" href=\"javascript:history.back(-1)\">返回</a></div>";
				String button3 = "<a style=\"color: #fff;text-decoration: blink; margin-top:5px; display: block;\" href=\"history.back(-1)\">返回</a></div>";
				//保后
				String button4 = "<div id=\"btn\" style=\"position: fixed; right: 0;top:50%; background:#e15450;color:#fff;cursor: pointer;padding: 5px;border-radius: 5px;\">导出</div>";
				htmlData = htmlData.replace(button, "");
				htmlData = htmlData.replace(button2, "");
				htmlData = htmlData.replace(button3, "");
				htmlData = htmlData.replace(button4, "");
				os.write(htmlData);
				os.flush();
				fos.close();
			}
			
			result.put("success", true);
			result.put("message", "成功");
			result.put("url", "/baseDataLoad/exportDoc.htm?formId=" + formId + "&type=" + type);
			return result;
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	private static final Map<String, String> DOC_MAP = new HashMap<>();
	static {
		DOC_MAP.put("INVESTIGATING_PHASES", "尽调_");
		DOC_MAP.put("AFTERWARDS_PHASES", "保后_");
	}
	
	@RequestMapping("exportDoc.htm")
	public void exportDoc(HttpServletRequest request, HttpServletResponse response, long formId,
							String type) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			String fileName = DOC_MAP.get(type) + formId;
			if (StringUtil.isNotBlank(fileName)) {
				String filePath = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_WORD_DOWNLOAD_FOLDER.code())
									+ File.separator + fileName + ".doc";
				File file = new File(filePath);
				response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1")
							+ ".doc");
				response.setContentType("application/msword");
				response.setContentLength((int) file.length());
				byte[] buffer = new byte[4096];// 缓冲区
				output = new BufferedOutputStream(response.getOutputStream());
				input = new BufferedInputStream(new FileInputStream(file));
				int n = -1;
				//遍历，开始下载
				while ((n = input.read(buffer, 0, 4096)) > -1) {
					output.write(buffer, 0, n);
				}
				file.delete(); //删除临时文件
				output.flush(); //不可少
				response.flushBuffer();//不可少
			}
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("导出尽调异常：" + e);
		} finally {
			//关闭流，不可少
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 选择区域
	 *
	 * @param parentCode
	 * @param model
	 * @param edit 编辑有区域限制
	 * @return
	 */
	@RequestMapping("region.json")
	@ResponseBody
	public JSONObject region(String parentCode, Boolean edit, Model model) {
		String tipPrefix = "选择区域";
		JSONObject result = new JSONObject();
		try {
			List<RegionInfo> data = basicDataCacheService.queryRegion(parentCode);
			JSONArray finalData = new JSONArray();
			for (RegionInfo info : data) {
				
				JSONObject json = new JSONObject();
				json.put("code", info.getCode());
				json.put("name", info.getName());
				json.put("hasChildren", info.getHasChildren());
				json.put("level", info.getLevel());
				finalData.add(json);
			}
			result = toStandardResult(finalData, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 分类树
	 * @param parentCatalogId
	 * @param edit
	 * @param model
	 * @return
	 */
	@RequestMapping("insuranceCatalog.json")
	@ResponseBody
	public JSONObject insuranceCatalog(long parentCatalogId, Boolean edit, Model model) {
		String tipPrefix = "选择区域";
		JSONObject result = new JSONObject();
		try {
			InsuranceCatalogQueryOrder queryOrder = new InsuranceCatalogQueryOrder();
			queryOrder.setParentCatalogId(parentCatalogId);
			List<InsuranceCatalogInfo> data = insuranceCatalogService.queryList(queryOrder)
				.getPageList();
			JSONArray finalData = new JSONArray();
			for (InsuranceCatalogInfo info : data) {
				
				JSONObject json = new JSONObject();
				json.put("id", info.getCatalogId());
				json.put("name", info.getCatalogName());
				json.put("hasChildren", info.getLastDepth());
				json.put("level", info.getDepth());
				finalData.add(json);
			}
			result = toStandardResult(finalData, tipPrefix);
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	/**
	 * 查询基础数据
	 * @param order
	 * @return
	 */
	@RequestMapping("baseDataInfo.json")
	@ResponseBody
	public JSONObject baseDataInfo(BaseDataInfoQueryOrder order) {
		String tipPrefix = "查询基础数据";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<BaseDataInfoInfo> batchResult = null;
			batchResult = baseDataInfoService.queryList(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<BaseDataInfoInfo> list = batchResult.getPageList();
				for (BaseDataInfoInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("id", info.getId());
					json.put("name", info.getName());
					json.put("code", info.getCode());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询基础数据
	 * @param order
	 * @return
	 */
	@RequestMapping("insuranceLiability.json")
	@ResponseBody
	public JSONObject insuranceLiability(InsuranceLiabilityQueryOrder order) {
		String tipPrefix = "查询保险责任";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<InsuranceLiabilityInfo> batchResult = null;
			batchResult = insuranceLiabilityService.queryList(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<InsuranceLiabilityInfo> list = batchResult.getPageList();
				for (InsuranceLiabilityInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("id", info.getId());
					json.put("name", info.getName());
					json.put("code", info.getCode());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询基础数据关联协议
	 * @param order
	 * @return
	 */
	@RequestMapping("customerProtocol.json")
	@ResponseBody
	public JSONObject customerProtocol(CustomerProtocolQueryOrder order) {
		String tipPrefix = "查询客户关联协议";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<CustomerProtocolVo> batchResult = null;
			batchResult = customerProtocolService.queryCustomerProtocolList(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<CustomerProtocolVo> list = batchResult.getPageList();
				for (CustomerProtocolVo info : list) {
					JSONObject json = new JSONObject();
					json.put("id", info.getId());
					json.put("name", info.getName());
					json.put("no", info.getNo());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询客户信息
	 * @param order
	 * @return
	 */
	@RequestMapping("customer.json")
	@ResponseBody
	public JSONObject customer(CustomerPersonQueryOrder order) {
		String tipPrefix = "查询客户信息";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<CustomerPersonInfo> batchResult = null;
			batchResult = customerPersonService.queryCommonPersonByCondition(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<CustomerPersonInfo> list = batchResult.getPageList();
				for (CustomerPersonInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("userId", info.getUserId());
					json.put("customerId", info.getCustomerId());
					json.put("name", info.getCustomerName());
					json.put("birthday", info.getBirthDay() == null ? "" : info.getBirthDay());
					json.put("sex", info.getSex());
					if (info.getCertType() != null) {
						json.put("certType", info.getCertType().getMessage());
					}
					if (info.getCustomerType() != null) {
						json.put("customerType", info.getCustomerType().getMessage());
						json.put("customerTypeCode", info.getCustomerType().getCode());
					}
					List<CustomerCertInfo> findCustomerCertList = insuranceContactLetterService.findCustomerCertList(info.getUserId());
					json.put("certNo", info.getCertNo());
					json.put("findCustomerCertList", JSONArray.toJSONString(findCustomerCertList).replace("\"", "'"));
					json.put("address", info.getAddress());
					String provinceName = StringUtils.isBlank(info.getProvinceName()) ? "" :info.getProvinceName();
					String cityName = StringUtils.isBlank(info.getCityName()) ? "" :info.getCityName();
					String countyName = StringUtils.isBlank(info.getCountyName()) ? "" :info.getCountyName();
					json.put("unitAddress", provinceName+cityName+countyName);
					json.put("businessUserName",info.getBusinessUserName());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
				data.put("certTypeList", CertTypeEnum.getAllEnum());
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询主险产品
	 * @param order
	 * @return
	 */
	@RequestMapping("product.json")
	@ResponseBody
	public JSONObject product(InsuranceProductQueryOrder order) {
		String tipPrefix = "查询客户信息";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<InsuranceProductInfo> batchResult = null;
			batchResult = insuranceProductService.queryList(order);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<InsuranceProductInfo> list = batchResult.getPageList();
				for (InsuranceProductInfo info : list) {

					JSONObject json = new JSONObject();
					json.put("productId", info.getProductId());
					json.put("catalogId", info.getCatalogId());
					json.put("lifeInsurance",info.getIsLifeInsurance());
					json.put("payType",
						PaymentTypeEnum.getMsgByCode(String.valueOf(info.getPayType())));
					json.put("insurancePeriodType", info.getInsurancePeriodType());
					json.put("insurancePeriod", info.getInsurancePeriod());
					json.put("productName", info.getProductName());
					json.put("companyUserId", info.getCompanyUserId());
					json.put("companyUserName", info.getCompanyUserName());
					json.put("firstPeriod",insuranceProtocolService.queryInsuranceProtocolCharge(info));
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询业务条件
	 * @param catalogId
	 * @return
	 */
	@RequestMapping("condition.json")
	@ResponseBody
	public JSONObject condition(String catalogId) {
		String tipPrefix = "查询业务条件";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			InsuranceConditionsQueryOrder queryOrder = new InsuranceConditionsQueryOrder();
			queryOrder.setOwnerId(NumberUtil.parseLong(catalogId));
			queryOrder.setPageSize(999);
			QueryBaseBatchResult<InsuranceConditionsInfo> batchResult = null;
			batchResult = insuranceConditionsService.queryList(queryOrder);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<InsuranceConditionsInfo> list = batchResult.getPageList();
				for (InsuranceConditionsInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("conditionId", info.getConditionId());
					json.put("businessConditions", info.getBusinessConditions());
					dataList.add(json);
				}
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询协议费率
	 * @param request
	 * @return
	 */
	@RequestMapping("protocolCharge.json")
	@ResponseBody
	public JSONObject protocolCharge(HttpServletRequest request) {
		String tipPrefix = "查询协议费率";
		JSONObject result = new JSONObject();
		try {
			InsuranceProtocolChargeQueryOrder queryOrder = new InsuranceProtocolChargeQueryOrder();
			QueryBaseBatchResult<InsuranceConditionsInfo> batchResult = null;
			//			JSONArray data = insuranceProtocolChargeService.queryProtocolCharge(queryOrder);
			//			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询协议费率
	 * @param catalogId
	 * @return
	 */
	@RequestMapping("catalogKindsDetail.json")
	@ResponseBody
	public JSONObject catalogKindsDetail(String catalogId) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			InsuranceConditionsQueryOrder queryOrder = new InsuranceConditionsQueryOrder();
			queryOrder.setOwnerId(NumberUtil.parseLong(catalogId));
			queryOrder.setType(InsuranceCatalogTypeEnum.kinds.getCode());
			queryOrder.setPageSize(999);
			QueryBaseBatchResult<InsuranceConditionsInfo> batchResult = null;
			batchResult = insuranceConditionsService.queryList(queryOrder);
			data = new JSONObject();
			if (batchResult != null && batchResult.isSuccess()) {
				JSONArray dataList = new JSONArray();
				List<InsuranceConditionsInfo> list = batchResult.getPageList();
				for (InsuranceConditionsInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("conditionId", info.getConditionId());
					json.put("businessConditions", info.getBusinessConditions());
					json.put("priceTemplate", info.getPriceTemplate());
					json.put("priceTemplateName", info.getPriceTemplateName());
					dataList.add(json);
				}
				data.put("conditions", dataList);
			}
			
			InsuranceCatalogLiabilityDO liabilityDO = new InsuranceCatalogLiabilityDO();
			liabilityDO.setOwnerId(NumberUtil.parseLong(catalogId));
			liabilityDO.setType(InsuranceCatalogTypeEnum.kinds.getCode());
			List<InsuranceCatalogLiabilityDO> liabilityDOs = insuranceCatalogLiabilityDAO
				.findByCondition(liabilityDO, null, null, 0, 1000);
			if (ListUtil.isNotEmpty(liabilityDOs)) {
				JSONArray dataList = new JSONArray();
				
				for (InsuranceCatalogLiabilityDO info : liabilityDOs) {
					JSONObject json = new JSONObject();
					json.put("id", info.getId());
					json.put("liabilityId", info.getLiabilityId());
					json.put("liabilityName", info.getLiabilityName());
					dataList.add(json);
				}
				data.put("liabilitis", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 超权限审批单
	 * @param queryOrder
	 * @return
	 */
	@RequestMapping("projectSetup.json")
	@ResponseBody
	public JSONObject projectSetup(ProjectSetupQueryOrder queryOrder) {
		String tipPrefix = "超权限审批单";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			queryOrder.setStatus(FormStatusEnum.APPROVAL.getCode());
			queryOrder.setCurrentSystemTime(new Date());
			QueryBaseBatchResult<ProjectSetupFormInfo> batchResult = projectSetupService
				.queryList(queryOrder);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<ProjectSetupFormInfo> list = batchResult.getPageList();
				for (ProjectSetupFormInfo info : list) {
					JSONObject json = new JSONObject();
					List<ProjectSetupCustomerInfo> projectSetupCustomerInfos = projectsetupCustomerService.findList(info.getProjectSetupId());
					long[] customerUserIds = new long[projectSetupCustomerInfos.size()];
					if (ListUtil.isNotEmpty(projectSetupCustomerInfos)) {
						for (int i = 0; i < projectSetupCustomerInfos.size(); i++) {
							long customerId = projectSetupCustomerInfos.get(i).getCustomerId();
							customerUserIds[i] = customerId;
						}
					}
					List<ProjectSetupCatalogInfo> projectSetupCatalogInfos = projectSetupCatalogService.findList(info.getProjectSetupId());
					long[] catalogIds = new long[projectSetupCatalogInfos.size()];
					StringBuilder certainIds = new StringBuilder();
					if (ListUtil.isNotEmpty(projectSetupCatalogInfos)) {
						for (int i = 0; i < projectSetupCatalogInfos.size(); i++) {
							long catalogId = projectSetupCatalogInfos.get(i).getCatalogId();
							catalogIds[i] = catalogId;
							certainIds.append(catalogId).append(",");
						}
					}
					json.put("beginTime", DateUtil.dtSimpleFormat(info.getBeginDate()));
					json.put("endTime", DateUtil.dtSimpleFormat(info.getEndDate()));
					json.put("status", info.getFormStatus().getMessage());
					json.put("projectSetupId", info.getProjectSetupId());
					json.put("projectSetupName", info.getProjectSetupName());
					json.put("customers", JSONArray.toJSONString(customerUserIds));
					json.put("catalogIds", JSONArray.toJSONString(catalogIds));
					if(StringUtil.isNotEmpty(certainIds.toString())){
						json.put("certainIds",certainIds.toString().substring(0,certainIds.toString().length()-1));
					}else{
						json.put("certainIds","");
					}

					json.put("rawAddTime", DateUtil.simpleFormat(info.getRawAddTime()));
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	
	/**
	 * 客户证件
	 * @param request
	 * @return
	 */
	@RequestMapping("customerCert.json")
	@ResponseBody
	public JSONObject customerCert(HttpServletRequest request) {
		String tipPrefix = "客户证件";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			;
			CustomerCertInfoDO certInfoDO = new CustomerCertInfoDO();
			WebUtil.setPoPropertyByRequest(certInfoDO, request);
			List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
				certInfoDO, null, null, 0, 100);
			if (ListUtil.isNotEmpty(customerCertInfoDOs)) {
				JSONArray dataList = new JSONArray();
				for (CustomerCertInfoDO info : customerCertInfoDOs) {
					JSONObject json = new JSONObject();
					json.put("certNo", info.getCertNo());
					json.put("certType", info.getCertType());
					json.put("certTypeText", CertTypeEnum.getByCode(info.getCertType())
						.getMessage());
					dataList.add(json);
				}
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		return result;
	}
	
	/**
	 * 查询协议费率
	 * @param catalogId
	 * @return
	 */
	@RequestMapping("catalogConditions.json")
	@ResponseBody
	public JSONObject catalogConditions(String catalogId) {
		String tipPrefix = "查询协议费率";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			InsuranceConditionsQueryOrder queryOrder = new InsuranceConditionsQueryOrder();
			queryOrder.setOwnerId(NumberUtil.parseLong(catalogId));
			queryOrder.setPageSize(999);
			QueryBaseBatchResult<InsuranceConditionsInfo> batchResult = null;
			batchResult = insuranceConditionsService.queryList(queryOrder);
			data = new JSONObject();
			if (batchResult != null && batchResult.isSuccess()) {
				JSONArray dataList = new JSONArray();
				List<InsuranceConditionsInfo> list = batchResult.getPageList();
				for (InsuranceConditionsInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("conditionId", info.getConditionId());
					json.put("businessConditions", info.getBusinessConditions());
					dataList.add(json);
				}
				data.put("conditions", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询责任范围和附加险
	 * @param catalogId
	 * @return
	 */
	@RequestMapping("queryLiabilityAndExtraKind.json")
	@ResponseBody
	public JSONObject queryLiabilityAndExtraKind(String catalogId) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			InsuranceCatalogLiabilityDO liabilityDO = new InsuranceCatalogLiabilityDO();
			liabilityDO.setOwnerId(NumberUtil.parseLong(catalogId));
			List<InsuranceCatalogLiabilityDO> liabilityDOs = insuranceCatalogLiabilityDAO
				.findByCondition(liabilityDO, null, null, 0, 1000);
			if (ListUtil.isNotEmpty(liabilityDOs)) {
				JSONArray dataList = new JSONArray();
				for (InsuranceCatalogLiabilityDO info : liabilityDOs) {
					JSONObject json = new JSONObject();
					json.put("liabilityId", info.getLiabilityId());
					json.put("liabilityName", info.getLiabilityName());
					dataList.add(json);
				}
				data.put("liabilitis", dataList);
			}
			InsuranceCatalogRelationDO relationDO = new InsuranceCatalogRelationDO();
			relationDO.setParentId(NumberUtil.parseLong(catalogId));
			List<InsuranceCatalogRelationDO> relationDOs = insuranceCatalogRelationDAO
				.findByCondition(relationDO, null, null, 0, 1000);
			if (ListUtil.isNotEmpty(relationDOs)) {
				JSONArray dataList = new JSONArray();
				for (InsuranceCatalogRelationDO info : relationDOs) {
					JSONObject json = new JSONObject();
					json.put("catalogId", info.getChildId());
					json.put("catalogName", info.getChildName());
					dataList.add(json);
				}
				data.put("extraKinds", dataList);
			}
			
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询责任范围和附加险
	 * @param userId
	 * @return
	 */
	@RequestMapping("queryCompanyContactInfo.json")
	@ResponseBody
	public JSONObject queryCompanyContactInfo(String userId, HttpServletRequest request) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			CustomerCompanyInfo customerCompanyInfo = customerCompanyService
				.queryCustomerCompanyByUserId(NumberUtil.parseLong(userId));
			if (customerCompanyInfo != null) {
				data = new JSONObject();
				data.put("contactUserName", customerCompanyInfo.getContactMan());
				data.put("contactUserMobile", customerCompanyInfo.getContactMobile());
				data.put("contactUserEmail", customerCompanyInfo.getContactEmail());
			}
			ProtocolChargeQueryOrder queryOrder = new ProtocolChargeQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			queryOrder.setCompanyUserIds(userId);
			String charge = insuranceProtocolService.queryInsuranceProtocolCharge(queryOrder);
			data.put("charge", charge);
			
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询协议费率
	 * @param queryOrder
	 * @return
	 */
	@RequestMapping("queryInsuranceProtocolCharge.json")
	@ResponseBody
	public JSONObject queryInsuranceProtocolCharge(ProtocolChargeQueryOrder queryOrder) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			String charge = insuranceProtocolService.queryInsuranceProtocolCharge(queryOrder);
			data.put("charge", charge);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 查询责任范围和附加险
	 * @param productId
	 * @return
	 */
	@RequestMapping("queryProductLiability.json")
	@ResponseBody
	public JSONObject queryProductLiability(String productId) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			InsuranceCatalogLiabilityDO liabilityDO = new InsuranceCatalogLiabilityDO();
			liabilityDO.setOwnerId(NumberUtil.parseLong(productId));
			liabilityDO.setType(InsuranceCatalogTypeEnum.product.getCode());
			List<InsuranceCatalogLiabilityDO> liabilityDOs = insuranceCatalogLiabilityDAO
				.findByCondition(liabilityDO, null, null, 0, 1000);
			JSONObject dataList = new JSONObject();
			JSONArray dataListId = new JSONArray();
			if (ListUtil.isNotEmpty(liabilityDOs)) {
				
				for (InsuranceCatalogLiabilityDO info : liabilityDOs) {
					
					JSONObject json = new JSONObject();
					json.put("liabilityId", info.getLiabilityId());
					json.put("liabilityName", info.getLiabilityName());
					dataList.put(info.getLiabilityId() + "", json);
					dataListId.add(info.getLiabilityId());
				}
				
			}
			
			data.put("liabilityIds", dataListId);
			data.put("liabilitis", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 询价方案
	 * @param request
	 * @return
	 */
	@RequestMapping("queryPriceScheme.json")
	@ResponseBody
	public JSONObject queryPriceScheme(HttpServletRequest request) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			PriceContactLetterQueryOrder queryOrder = new PriceContactLetterQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			queryOrder.setStatus("REPORT_END");
			QueryBaseBatchResult<PriceContactLetterInfo> batchResult = priceContactLetterService
				.queryList(queryOrder);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<PriceContactLetterInfo> list = batchResult.getPageList();
				for (PriceContactLetterInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("id", info.getId());
					json.put("billNo", info.getBillNo());
					json.put("name", info.getName());
					json.put("customerType", info.getCustomerType());
					json.put("customerUserId", info.getCustomerUserId());
					json.put("customerUserName", info.getCustomerUserName());
					json.put("businessUserId",info.getBusinessUserId());
					json.put("businessUserName",info.getBusinessUserName());
					json.put("projectSetUpId", info.getProjectSetupId());
					json.put("projectSetName", info.getProjectSetupName());
					List<ProjectSetupCustomerInfo> projectSetupCustomerInfos = projectsetupCustomerService.findList(info.getProjectSetupId());
					long[] customerUserIds = new long[projectSetupCustomerInfos.size()];
					if (ListUtil.isNotEmpty(projectSetupCustomerInfos)) {
						for (int i = 0; i < projectSetupCustomerInfos.size(); i++) {
							long customerId = projectSetupCustomerInfos.get(i).getCustomerId();
							customerUserIds[i] = customerId;
						}
					}
					List<ProjectSetupCatalogInfo> projectSetupCatalogInfos = projectSetupCatalogService.findList(info.getProjectSetupId());
					long[] catalogIds = new long[projectSetupCatalogInfos.size()];
					if (ListUtil.isNotEmpty(projectSetupCatalogInfos)) {
						for (int i = 0; i < projectSetupCatalogInfos.size(); i++) {
							long catalogId = projectSetupCatalogInfos.get(i).getCatalogId();
							catalogIds[i] = catalogId;
						}
					}
					json.put("customers", JSONArray.toJSONString(customerUserIds));
					json.put("catalogIds", JSONArray.toJSONString(catalogIds));
					CustomerBaseInfo customerBaseInfo = customerPersonService
						.findByCustomerUserId(info.getCustomerUserId());
					if (null != customerBaseInfo) {
						json.put("sex", customerBaseInfo.getSex());
						json.put("birth", customerBaseInfo.getBirth());
						String provinceName = StringUtils.isBlank(customerBaseInfo.getProvinceName()) ? "" :customerBaseInfo.getProvinceName();
						String cityName = StringUtils.isBlank(customerBaseInfo.getCityName()) ? "" :customerBaseInfo.getCityName();
						String countyName = StringUtils.isBlank(customerBaseInfo.getCountyName()) ? "" :customerBaseInfo.getCountyName();
						json.put("unitAddress", provinceName+cityName+countyName);
					}
					List<PriceContactLetterCompanyReportPriceInfo> priceInfoList = priceContactLetterService
						.queryReportPriceCustomer(info.getId());
					JSONArray jsonArray = new JSONArray();
					if (ListUtil.isNotEmpty(priceInfoList)) {
						for (PriceContactLetterCompanyReportPriceInfo priceInfo : priceInfoList) {
							
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("companyUserId", priceInfo.getCustomerUserId());
							jsonObject.put("companyUserName", priceInfo.getCustomerUserName());
							
							jsonArray.add(jsonObject);
						}
						
					}
					json.put("company", jsonArray == null || jsonArray.size() == 0 ? "" : JSONArray
						.toJSONString(jsonArray).replace("\"", "'"));
					
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 询价方案
	 * @param request
	 * @return
	 */
	@RequestMapping("queryReportPrice.json")
	@ResponseBody
	public JSONObject queryReportPrice(HttpServletRequest request) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			List<PriceContactLetterCompanyReportPriceDetailInfo> detailInfoList = priceContactLetterService
				.queryReportPriceDetail(
					NumberUtil.parseLong(request.getParameter("priceContactLetterId")),
					NumberUtil.parseLong(request.getParameter("customerUserId")));
			JSONArray jsonArray = new JSONArray();
			if (ListUtil.isNotEmpty(detailInfoList)) {
				for (PriceContactLetterCompanyReportPriceDetailInfo priceDetailInfo : detailInfoList) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("productId", priceDetailInfo.getProductId());
					jsonObject.put("productName", priceDetailInfo.getProductName());
					jsonObject.put("insuranceAmount", priceDetailInfo.getInsuranceAmount());
					jsonObject.put("premiumAmount", priceDetailInfo.getPremiumAmount());
					jsonObject.put("brokerAmount", priceDetailInfo.getBorkerAmount());
					jsonArray.add(jsonObject);
				}
				
			}
			
			data.put("reportPrice", jsonArray == null || jsonArray.size() == 0 ? "" : JSONArray
				.toJSONString(jsonArray).replace("\"", "'"));
			
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	/**
	 * 询价方案
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCustomerByPriceScheme.json")
	@ResponseBody
	public JSONObject queryCustomerByPriceScheme(HttpServletRequest request) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			PriceContactLetterInfo info = priceContactLetterService.findById(NumberUtil
				.parseLong(request.getParameter("id")));
			if (info != null) {
				CustomerPersonInfo baseInfoDO = customerPersonService
					.queryCustomerPersonByUserId(info.getCustomerUserId());
				data.put("userId", info.getCustomerUserId());
				data.put("certNo", info.getCustomerCertNo());
				data.put("certType", info.getCustomerCertType());
				data.put("certTypeText", CertTypeEnum.getByCode(info.getCustomerCertType())
					.getMessage());
				data.put("sex", baseInfoDO.getSex());
				data.put("customerType", baseInfoDO.getCustomerType().getCode());
				data.put("customerTypeText", baseInfoDO.getCustomerType().getMessage());
				JSONArray jsonArray = new JSONArray();
				List<PriceContactLetterCompanyReportPriceInfo> companyReportPriceInfos = info
					.getReportPriceInfo().getCompanyReportPriceInfos();
				if (ListUtil.isNotEmpty(companyReportPriceInfos)) {
					for (PriceContactLetterCompanyReportPriceInfo priceInfo : companyReportPriceInfos) {
						List<PriceContactLetterCompanyReportPriceDetailInfo> priceDetailInfoList = priceInfo
							.getDetailInfoList();
						if (ListUtil.isNotEmpty(priceDetailInfoList)) {
							for (PriceContactLetterCompanyReportPriceDetailInfo priceDetailInfo : priceDetailInfoList) {
								JSONObject jsonObject = new JSONObject();
								jsonObject
									.put("productId", priceDetailInfo.getInsuranceProductId());
								jsonObject.put("productName",
									priceDetailInfo.getInsuranceProductName());
								jsonObject.put("insuranceAmount",
									priceDetailInfo.getInsuranceAmount());
								jsonObject.put("premiumAmount", priceDetailInfo.getPremiumAmount());
								jsonObject.put("brokerAmount", priceDetailInfo.getBorkerAmount());
								jsonArray.add(jsonObject);
							}
						}
					}
				}
				data.put("reportPrice", jsonArray);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}
	
	
	/**
	 * 保单批改中查询所有的保单信息
	 * @param queryOrder
	 * @return
	 */
	@RequestMapping("queryBusinessBill.json")
	@ResponseBody
	public JSONObject queryBusinessBill(BusinessBillQueryOrder queryOrder) {
		String tipPrefix = "保单选择";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			QueryBaseBatchResult<BusinessBillInfo> batchResult = businessBillService.queryList(queryOrder);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<BusinessBillInfo> list = batchResult.getPageList();
				for (BusinessBillInfo info : list) {
					JSONObject json = new JSONObject();
					InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findById(info.getLetterId());
					json.put("insuranceNo", info.getInsuranceNo());
					json.put("catalogId", info.getCatalogId());
					json.put("catalogName", info.getCatalogName());
					json.put("companyUserId", info.getInsuranceCompanyId());
					json.put("companyUserName", info.getInsuranceCompanyName());
					json.put("insuranceContactLetter", JSONArray.toJSONString(insuranceContactLetterInfo).replace("\"","'"));
					json.put("businessBillId", info.getBusinessBillId());
					json.put("billCustomerId", info.getBillCustomerId());
					json.put("billCustomerName",info.getBillCustomerName());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}
		
		return result;
	}


	/**
	 * 询价方案
	 * @param request
	 * @return
	 */
	@RequestMapping("queryReportPriceAmount.json")
	@ResponseBody
	public JSONObject queryReportPriceAmount(HttpServletRequest request) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			ReportPriceDetailQueryOrder queryOrder = new ReportPriceDetailQueryOrder();
			queryOrder.setPriceContactId(NumberUtil.parseLong(request.getParameter("priceContactLetterId")));
			queryOrder.setProductId(NumberUtil.parseLong(request.getParameter("productId")));
			List<PriceContactLetterCompanyReportPriceDetailInfo> detailInfoList = priceContactLetterService.queryReportPriceDetail(queryOrder);
			Money insuranceAmount = Money.zero();
			Money premiumAmount = Money.zero();
			Money brokerAmount = Money.zero();
			if (ListUtil.isNotEmpty(detailInfoList)) {
				for (PriceContactLetterCompanyReportPriceDetailInfo priceDetailInfo : detailInfoList) {
					insuranceAmount.addTo(new Money(priceDetailInfo.getInsuranceAmount()));
					premiumAmount.addTo(new Money(priceDetailInfo.getPremiumAmount()));
					brokerAmount.addTo(new Money(priceDetailInfo.getBorkerAmount()));
				}
			}
			data.put("insuranceAmount", insuranceAmount.toStandardString());
			data.put("premiumAmount", premiumAmount.toStandardString());
			data.put("brokerAmount", brokerAmount.toStandardString());
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}

		return result;
	}



	/**
	 * 联系电话
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCustomerMobile.json")
	@ResponseBody
	public JSONObject queryCustomerMobile(HttpServletRequest request) {
		String tipPrefix = "联系电话";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			CustomerContactWayDO wayDO = new CustomerContactWayDO();
			WebUtil.setPoPropertyByRequest(wayDO,request);
			wayDO.setType("mobile");
			List<CustomerContactWayDO> wayDOList = customerContactWayDAO.findByCondition(wayDO,null,null,0,100);
			if (ListUtil.isNotEmpty(wayDOList)) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				for (CustomerContactWayDO info : wayDOList) {
					JSONObject json = new JSONObject();
					json.put("mobile", info.getWay());
					dataList.add(json);
				}
				data.put("pageCount", 1);
				data.put("pageNumber", 1);
				data.put("pageSize", 100);
				data.put("totalCount", wayDOList.size());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}

		return result;
	}

	@RequestMapping("insuranceProtocol.json")
	@ResponseBody
	public JSONObject insuranceProtocol(HttpServletRequest request){
		String tipPrefix = "协议";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = null;
			InsuranceProtocolQueryOrder queryOrder = new InsuranceProtocolQueryOrder();
			queryOrder.setType(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode());
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			queryOrder.setName(request.getParameter("name1"));
			queryOrder.setProtocolUserName(request.getParameter("protocolUserName1"));
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InsuranceProtocolInfo> batchResult = insuranceProtocolService
					.queryList(queryOrder);
			if (batchResult != null && batchResult.isSuccess()) {
				data = new JSONObject();
				JSONArray dataList = new JSONArray();
				List<InsuranceProtocolInfo> list = batchResult.getPageList();
				for (InsuranceProtocolInfo info : list) {
					JSONObject json = new JSONObject();
					json.put("id",info.getProtocolId());
					json.put("name", info.getName());
					json.put("protocolUserName", info.getProtocolUserName());
					json.put("protocolUserId", info.getProtocolUserId());
					json.put("signDate",DateUtil.dtSimpleFormat(info.getSignDate()));
					json.put("contractingAgencyId",info.getContractingAgencyId());
					json.put("contractingAgencyName",info.getContractingAgencyName());
					dataList.add(json);
				}
				data.put("pageCount", batchResult.getPageCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}

		return result;
	}


	@RequestMapping("insuranceProtocolById.json")
	@ResponseBody
	public JSONObject insuranceProtocolById(HttpServletRequest request){
		String tipPrefix = "协议";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			InsuranceProtocolInfo insuranceProtocolInfo = insuranceProtocolService
					.findById(NumberUtil.parseLong(request.getParameter("id")));
			if (insuranceProtocolInfo != null) {
				data.put("protocolInfo", insuranceProtocolInfo);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}

		return result;
	}


	/**
	 * 询价方案
	 * @param request
	 * @return
	 */
	@RequestMapping("queryReportPriceInfo.json")
	@ResponseBody
	public JSONObject queryReportPriceInfo(HttpServletRequest request) {
		String tipPrefix = "";
		JSONObject result = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			long priceContactLetterId =NumberUtil.parseLong(request.getParameter("priceContactLetterId"));
			PriceContactLetterInfo letterInfo = new PriceContactLetterInfo();
			letterInfo.setId(priceContactLetterId);
			priceContactLetterService.initReportPriceInfo(letterInfo);
			if (ListUtil.isNotEmpty(letterInfo.getReportPriceInfo().getCompanyReportPriceInfos())) {
				JSONArray dataList = new JSONArray();
				for (PriceContactLetterCompanyReportPriceInfo priceDetailInfo : letterInfo.getReportPriceInfo().getCompanyReportPriceInfos()) {
					JSONObject json = new JSONObject();
					json.put("customerUserName", priceDetailInfo.getCustomerUserName());
					json.put("customerUserId", priceDetailInfo.getCustomerUserId());
					json.put("premiumAmount", priceDetailInfo.getPremiumAmount());
					json.put("brokerAmount",priceDetailInfo.getBrokerAmount());
					json.put("brokerAmountRate", priceDetailInfo.getBrokerAmountRate());
					json.put("expenseAmount", priceDetailInfo.getBrokerAmount());
					json.put("contactUserName", priceDetailInfo.getContactUserName());
					json.put("contactUserMobile", priceDetailInfo.getContactUserMobile());
					json.put("contactUserEmail",priceDetailInfo.getContactUserEmail());
					json.put("createDate",priceDetailInfo.getCreateDate());
					json.put("expiryDate", priceDetailInfo.getExpiryDate());
					json.put("detailInfos",priceDetailInfo.getDetailInfos());

					dataList.add(json);
				}
				data.put("pageCount", "1");
				data.put("pageNumber", "1");
				data.put("pageSize", "50");
				data.put("totalCount", letterInfo.getReportPriceInfo().getCompanyReportPriceInfos().size());
				data.put("pageList", dataList);
			}
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("查询基础数据出错 {} ", e);
		}

		return result;
	}


}
