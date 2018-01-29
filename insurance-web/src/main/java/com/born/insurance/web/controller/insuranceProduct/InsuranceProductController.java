package com.born.insurance.web.controller.insuranceProduct;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.biz.service.insuranceProtocolCharge.InsuranceProtocolChargeServiceImpl;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.token.TokenProccessor;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.RiskEnum;
import com.born.insurance.ws.enums.SaleStatusEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogLiabilityInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogQueryOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductChargeOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import com.born.insurance.ws.service.common.BasicDataCacheService;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.born.insurance.ws.service.salesAreas.SalesAreasService;
import com.yjf.common.lang.util.money.Money;

import bsh.StringUtil;
import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/insuranceProduct")
public class InsuranceProductController extends BaseController {
	
	@Autowired
	protected InsuranceProductService insuranceProductService;
	
	@Autowired
	protected CustomerCompanyService customerCompanyService;
	
	@Autowired
	protected InsuranceCatalogService insuranceCatalogService;
	
	@Autowired
	protected BaseDataInfoService baseDataInfoService;
	
	@Autowired
	protected SalesAreasService salesAreasService;
	
	@Autowired
	BasicDataCacheService basicDataCacheService;
	
	

	private final static String VM_PATH = "/insurance/insuranceProduct/";
	
	/**
	 * 产品管理
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			InsuranceProductQueryOrder queryOrder = new InsuranceProductQueryOrder();
			InsuranceCatalogQueryOrder insuranceCatalogQueryOrder = new InsuranceCatalogQueryOrder();
			CustomerCompanyQueryOrder customerCompanyQueryOrder = new CustomerCompanyQueryOrder();
			customerCompanyQueryOrder.setCustomerType(CustomerTypeEnum.INSURANCE_INSTITUTIONS);
			customerCompanyQueryOrder.setPageSize(100);
			QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult = customerCompanyService
				.queryList(customerCompanyQueryOrder);
			
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<InsuranceProductInfo> batchResult = insuranceProductService
				.queryList(queryOrder);
			QueryBaseBatchResult<InsuranceCatalogInfo> catalogInfoList = insuranceCatalogService
				.queryList(insuranceCatalogQueryOrder);
			
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("saleStatus", SaleStatusEnum.getAllEnum());
			model.addAttribute("riskStatus", RiskEnum.getAllEnum());
			model.addAttribute("institutions", baseBatchResult.getPageList());
			model.addAttribute("catalogInfoList", catalogInfoList.getPageList());
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listInsuranceProduct.vm";
	}
	
	/**
	 * 产品管理，返回json数据
	 * @param order
	 * @return
	 */
	@RequestMapping("list.json")
	@ResponseBody
	public JSONObject getListJsonData(InsuranceProductQueryOrder order) {
		String tipPrefix = "产品管理查询";
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
					json.put("productName", info.getProductName());
					json.put("comanyUserName", info.getCompanyUserName());
					json.put("saleStatus", info.getSaleStatus() == "0" ? "在售" : "退市");
					json.put("rawAddTime", DateUtil.simpleFormatYmdhms(info.getRawAddTime()));
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
	 * 进入新增编辑界面
	 * @param productId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "productId", required = false, defaultValue = "0") long productId,String isLifeInsurance,String catalogId,
						HttpServletRequest request, Model model) {
		InsuranceProductInfo info = null;
		//基础数据
		BaseDataInfoQueryOrder payTypeInfoQueryOrder = new BaseDataInfoQueryOrder();
		BaseDataInfoQueryOrder payPeriodInfoQueryOrder = new BaseDataInfoQueryOrder();
		payTypeInfoQueryOrder.setType(BaseDataInfoTypeEnum.PAY_TYPE);
		payPeriodInfoQueryOrder.setType(BaseDataInfoTypeEnum.PAY_PERIOD);
		QueryBaseBatchResult<BaseDataInfoInfo> payTypeList = baseDataInfoService
			.queryList(payTypeInfoQueryOrder);
		QueryBaseBatchResult<BaseDataInfoInfo> payPeriodList = baseDataInfoService
			.queryList(payPeriodInfoQueryOrder);
		
		//查询保险分类
		InsuranceCatalogQueryOrder insuranceCatalogQueryOrder = new InsuranceCatalogQueryOrder();
		QueryBaseBatchResult<InsuranceCatalogInfo> catalogInfoList = insuranceCatalogService
			.queryList(insuranceCatalogQueryOrder);
		if (productId > 0) {
			info = insuranceProductService.findById(productId);
			List<BaseDataInfoInfo> pageList = payPeriodList.getPageList();
		StringBuffer sb = new StringBuffer();
		StringBuffer period = new StringBuffer();
			String payPeriod = info.getPayPeriod();
			if (!StringUtils.isBlank(payPeriod)) {
				String[] s = payPeriod.split(",");
					for (String str : s) {
						boolean flag = false;
						for (BaseDataInfoInfo baseDataInfoInfo : pageList) {
							if (str.equals(String.valueOf(baseDataInfoInfo.getId()))) {
								period.append(str).append(",");
								flag = true;
								break;
							}
						}
						if (!flag){
							sb.append(str).append(",");
						}
				}
				String periodName = sb.toString();
				String periodName2 = period.toString();
				info.setPayPeriodName(!StringUtils.isBlank(periodName)?periodName.substring(0,periodName.length()-1):"");	
				info.setPayPeriod(!StringUtils.isBlank(periodName2)?periodName2.substring(0,periodName2.length()-1):"");
			}
			//查询保险档次
			List<InsuranceProductLevelInfo> queryProductLevelInfoList = insuranceProductService.queryProductLevelInfoList(productId);
			//查询保险责任
			List<InsuranceCatalogLiabilityInfo> findInsuranceCatalogLiabilitys = insuranceCatalogService.findInsuranceCatalogLiabilitys(info.getProductId());
			//附件列表
			queryCommonAttachmentData(model, info.getProductId() + "",CommonAttachmentTypeEnum.INSURANCE_PRODUCT__LIABILITY);
			queryCommonAttachmentData(model, info.getProductId() + "",CommonAttachmentTypeEnum.INSURANCE_PRODUCT__CHARGE);
			queryCommonAttachmentData(model, info.getProductId() + "",CommonAttachmentTypeEnum.INSURANCE_PRODUCT__OTHER);
			model.addAttribute("info", info);
			model.addAttribute("findInsuranceCatalogLiabilitys", findInsuranceCatalogLiabilitys);
			model.addAttribute("queryProductLevelInfoList", queryProductLevelInfoList);
			model.addAttribute("insuranceTypesInfo", JSONObject.toJSONString(info));
		} else {
			info = new InsuranceProductInfo();
			info.setIsLifeInsurance(isLifeInsurance);
			info.setCatalogId(!StringUtils.isBlank(catalogId) ? Long.valueOf(catalogId) :0);
			if (!StringUtils.isBlank(catalogId)) {
				List<InsuranceCatalogLiabilityInfo> findInsuranceCatalogLiabilitys = insuranceCatalogService.findInsuranceCatalogLiabilitys(Integer.parseInt(catalogId));
				model.addAttribute("findInsuranceCatalogLiabilitys", findInsuranceCatalogLiabilitys);
			}
			String makeToken = TokenProccessor.getInstance().makeToken();
			request.getSession().setAttribute("token", makeToken);
			model.addAttribute("info", info);
		}
		model.addAttribute("httpRequest", request);
		model.addAttribute("provinceInfos", basicDataCacheService.queryProvinceCity());
		model.addAttribute("saleStatus", SaleStatusEnum.getAllEnum());
		model.addAttribute("payTypeList", payTypeList.getPageList());
		model.addAttribute("catalogInfoList", catalogInfoList.getPageList());
		model.addAttribute("payPeriodList", payPeriodList.getPageList());
		return VM_PATH + "addInsuranceProduct.vm";
	}
	
	/**
	 * 新增保险产品
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceProductOrder order) {
		JSONObject json = new JSONObject();
		
		String tipPrefix = "操作";
		try {
			setSessionLocalInfo2Order(order);
			if (TokenProccessor.isRepeatSubmit(request) && order.getTypeId() == 0) {
				json = toJSONResult(tipPrefix, new Exception("请不要重复提交"));
				return json;
			}
			InsuranceBaseResult result = insuranceProductService.save(order);
			request.getSession().removeAttribute("token");
			//添加附件
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.INSURANCE_PRODUCT__LIABILITY);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.INSURANCE_PRODUCT__CHARGE);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.INSURANCE_PRODUCT__OTHER);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
			request.getSession().removeAttribute("token");
		}
		return json;
	}
	
	/**
	 * 查看产品详情
	 * @param typeId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long typeId, HttpServletRequest request, Model model) {
		InsuranceProductInfo insuranceTypesInfo = insuranceProductService.findById(typeId);	
		if (null != insuranceTypesInfo) {
			String payPeriod = insuranceTypesInfo.getPayPeriod();
			if (!StringUtils.isBlank(payPeriod)) {
				StringBuffer sb = new StringBuffer();
				String[] str = payPeriod.split(",");
				for (String s : str) {
					if (s.equals("20"))s="3";
					if (s.equals("21"))s="5";
					if (s.equals("22"))s="10";
					if (s.equals("23"))s="15";
					if (s.equals("24"))s="20";
					sb.append(s).append("年").append(",");
				}
				String sbString = sb.toString();
				insuranceTypesInfo.setPayPeriod(sbString.substring(0,sbString.length()-1));
			}
			//查询保险责任
			List<InsuranceCatalogLiabilityInfo> findInsuranceCatalogLiabilitys = insuranceCatalogService.findInsuranceCatalogLiabilitys(typeId);
			model.addAttribute("catalogLiabilityInfos", findInsuranceCatalogLiabilitys);
			List<InsuranceProductLevelInfo> list = insuranceProductService.getInsuranceProductLevel(typeId);
			//附件列表
			queryCommonAttachmentData(model, typeId + "",CommonAttachmentTypeEnum.INSURANCE_PRODUCT__LIABILITY);
			queryCommonAttachmentData(model, typeId + "",CommonAttachmentTypeEnum.INSURANCE_PRODUCT__CHARGE);
			queryCommonAttachmentData(model, typeId + "",CommonAttachmentTypeEnum.INSURANCE_PRODUCT__OTHER);
			model.addAttribute("infoList",list);
		}
		model.addAttribute("info", insuranceTypesInfo);		
		return VM_PATH + "viewInsuranceProduct.vm";
	}
	
	/**
	 * 根据险种的id查询对应的保险责任
	 * @param ownerId
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getInsuranceCatalogLiabilitys.json")
	public InsuranceBaseResult getInsuranceCatalogLiabilitys(long ownerId,
																HttpServletRequest request,
																Model model) {
		InsuranceBaseResult insuranceBaseResult = new InsuranceBaseResult();
		List<InsuranceCatalogLiabilityInfo> queryList = null;
		try {
			queryList = insuranceCatalogService.findInsuranceCatalogLiabilitys(ownerId);
			insuranceBaseResult.setSuccess(true);
			insuranceBaseResult.setReturnObject(queryList);
		} catch (Exception e) {
			insuranceBaseResult.setSuccess(false);
			insuranceBaseResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return insuranceBaseResult;
	}
	
    
    /**
     * 根据客户的id信息查询证件类型
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("getCustomerCertInfo.json")
    public InsuranceBaseResult getCustomerCertInfo(long customerUesrId, HttpServletRequest request, Model model){
    	InsuranceBaseResult insuranceBaseResult = new InsuranceBaseResult();
    	List<CustomerCertInfo> queryList  = null;
    	try {
			queryList = insuranceProductService.getCustomerCertInfo(customerUesrId);
			insuranceBaseResult.setSuccess(true);
			insuranceBaseResult.setReturnObject(queryList);
		} catch (Exception e) {
			insuranceBaseResult.setSuccess(false);
			insuranceBaseResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	
    	return insuranceBaseResult;
    }
    
    
    /**
     * 根据产品id获取档次信息
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("getInsuranceProductLevel.json")
    public InsuranceBaseResult getInsuranceProductLevel(long productId, HttpServletRequest request, Model model){
    	InsuranceBaseResult insuranceBaseResult = new InsuranceBaseResult();
    	List<InsuranceProductLevelInfo> queryList  = null;
    	try {
			queryList = insuranceProductService.getInsuranceProductLevel(productId);
			insuranceBaseResult.setSuccess(true);
			insuranceBaseResult.setReturnObject(queryList);
		} catch (Exception e) {
			insuranceBaseResult.setSuccess(false);
			insuranceBaseResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	
    	return insuranceBaseResult;
    }
    
    /**
     * 根据产品id获取档次信息
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("calcTotalPremiumAmount.json")
    public InsuranceBaseResult calcTotalPremiumAmount(InsuranceProductChargeOrder insuranceProductChargeOrder){
    	InsuranceBaseResult insuranceBaseResult = new InsuranceBaseResult();
    	try {
    		int age = StringUtils.isBlank(insuranceProductChargeOrder.getCertNo()) ? 0 : InsuranceProtocolChargeServiceImpl.getFullAge(insuranceProductChargeOrder.getCertNo());
    		long productId = StringUtils.isBlank(insuranceProductChargeOrder.getProductId()) ? 0 : Long.valueOf(insuranceProductChargeOrder.getProductId());
    		String insurancePeriod = StringUtils.isBlank(insuranceProductChargeOrder.getPeriod()) ? "0" : insuranceProductChargeOrder.getPeriod();
    		Map<String, String> map = insuranceProductService.queryInsuranceProtocolCharge(productId, insuranceProductChargeOrder.getChargeType(), Long.valueOf(insurancePeriod));
    		//主险和附加险产品的缴费类型相同，根据主险的缴费类型比例计算
    		Money totalMoney = new Money(0,0);
    		 //保额
    		Double insurance = Double.valueOf(insuranceProductChargeOrder.getInsuranceAmount());
    		Double unitPrice = Double.valueOf(insuranceProductChargeOrder.getUnitPrice());
    		
    		if (insuranceProductChargeOrder.getMainPayType().equals(insuranceProductChargeOrder.getPayType())) {
    			//缴费类型
    			String payType = insuranceProductChargeOrder.getMainPayType();
    			//分期比例
    			Double scale = Double.valueOf(StringUtils.isBlank(insuranceProductChargeOrder.getPeriodRate()) ? "0" :insuranceProductChargeOrder.getPeriodRate());
    			if (!StringUtils.isBlank(insuranceProductChargeOrder.getPeriod())) {
    				int period = Integer.parseInt(insuranceProductChargeOrder.getPeriod());
    				for (int i = 0; i < period; i++) {
    					int currentAge = age + i;
    					String val = null == map.get(String.valueOf(currentAge)) ? "0" :map.get(String.valueOf(currentAge));
    					Money data = new Money(val) ;
    					Money result = new Money(0,0);
    					if (payType.equals("季交")) {
    						result = data.multiply(insurance/unitPrice).multiply(scale).multiply(4);
    					} else if (payType.equals("月交")) {
    						result = data.multiply(insurance/unitPrice).multiply(scale).multiply(12);
    					} else if (payType.equals("半年交")) {
    						result = data.multiply(insurance/unitPrice).multiply(scale).multiply(2);
    					} else if (payType.equals("年交")) {
    						result = data.multiply(insurance/unitPrice).multiply(1);
    					}
    					totalMoney = totalMoney.add(result);
    				}
    			}
    		//不相同，即不分期
    		} else {
    		//基数比例
    		double val = insurance / unitPrice;
    		if (!StringUtils.isBlank(insuranceProductChargeOrder.getPeriod())) {
    			int period = Integer.parseInt(insuranceProductChargeOrder.getPeriod());
    			for (int i = 0; i < period; i++) {
					int currentAge = age + i;
					String mapV = map.get(String.valueOf(currentAge));
					Money data = new Money(StringUtils.isBlank(mapV) ? "0" :mapV) ;
					//对应年龄
					Money result = data.multiply(val);
					totalMoney = totalMoney.add(result);
				}
    		}
    		}
    		insuranceBaseResult.setSuccess(true);
    		insuranceBaseResult.setReturnObject(totalMoney);
		} catch (Exception e) {
			insuranceBaseResult.setSuccess(false);
			insuranceBaseResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	
    	return insuranceBaseResult;
    }
}
