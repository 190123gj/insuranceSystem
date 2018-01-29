package com.born.insurance.web.controller.insuranceProtocolCharge;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.InsuranceProtocolTypeEnum;
import com.born.insurance.ws.enums.PaymentTypeEnum;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProtocolCharge.InsuranceProtocolChargeInfo;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeOrder;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.born.insurance.ws.service.insuranceProtocolCharge.InsuranceProtocolChargeService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
* Created by wqh on 2016-11-18.
*/
@Controller
@RequestMapping("/insurance/insuranceProtocolCharge")
public class InsuranceProtocolChargeController extends BaseController {
	@Autowired
	protected InsuranceProtocolChargeService insuranceProtocolChargeService;
	
	@Autowired
	protected InsuranceCatalogService insuranceCatalogService;
	
	@Autowired
	protected InsuranceProductService insuranceProductService;
	private final static String VM_PATH = "/insurance/insuranceProtocolCharge/";

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
			InsuranceProtocolChargeQueryOrder queryOrder = new InsuranceProtocolChargeQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InsuranceProtocolChargeInfo> batchResult = insuranceProtocolChargeService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
			return VM_PATH + "listInsuranceProtocolCharge.vm";
	}

    @RequestMapping("edit.htm")
    public String edit(	@RequestParam(value = "chargeId", required = false, defaultValue = "0") String chargeId,
    HttpServletRequest request, Model model) {
		InsuranceProtocolChargeInfo info = null;
		if (StringUtil.isNotEmpty(chargeId)) {
			info = insuranceProtocolChargeService.findById(chargeId);
			model.addAttribute("info", info);
	    }else{
			info = new InsuranceProtocolChargeInfo();
			model.addAttribute("info", info);
		}
		return VM_PATH + "addInsuranceProtocolCharge.vm";
	}

	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceProtocolChargeOrder order) {
		JSONObject json = new JSONObject();

		String tipPrefix = "insuranceProtocolCharge";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceProtocolChargeService.save(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
    }

    @RequestMapping("view.htm")
    public String view(String chargeId, HttpServletRequest request, Model model) {
		InsuranceProtocolChargeInfo insuranceProtocolChargeInfo = insuranceProtocolChargeService.findById(chargeId);
		model.addAttribute("info",insuranceProtocolChargeInfo);
		return VM_PATH + "viewInsuranceProtocolCharge.vm";
    }
    
    /**
     * 查询某保险公司下产品的缴费期限
     * @return
     */
    @RequestMapping("getInsuranceProductChargeInfo.json")
    @ResponseBody
    public JSONObject getInsuranceProductChargeInfo(String productId){
    	String tipPrefix = "";
		JSONObject result = new JSONObject();
    	List<InsuranceProtocolChargeInfo> insuranceProtocolChargeInfos = null ;
    	try {
    		JSONObject data = new JSONObject();
    		InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(Integer.parseInt(productId));
			insuranceProtocolChargeInfos = insuranceProtocolChargeService.getInsuranceProductChargeInfo(insuranceProductInfo.getCompanyUserId(),insuranceProductInfo.getCatalogId(),productId);
			if (ListUtil.isNotEmpty(insuranceProtocolChargeInfos)) {
				JSONArray dataList = new JSONArray();
				for (InsuranceProtocolChargeInfo info : insuranceProtocolChargeInfos) {
					JSONObject json = new JSONObject();
					json.put("val", info.getVal());
					json.put("protocolInfoId", info.getProtocolInfoId());
					dataList.add(json);
				}
				data.put("insuranceProtocolChargeInfos", dataList);
			}
			result = toStandardResult(data, tipPrefix);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * 投保人查询该产品的缴费期限
     * @return
     */
    @RequestMapping("getInsuranceProductCharge.json")
    @ResponseBody
    public JSONObject getInsuranceProductCharge(String productId,String chargeType ){
    	String tipPrefix = "";
		JSONObject result = new JSONObject();
    	List<InsuranceProtocolChargeInfo> insuranceProtocolChargeInfos = null ;
    	try {
    		JSONObject data = new JSONObject();
    		InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(Integer.parseInt(productId));
			insuranceProtocolChargeInfos = insuranceProtocolChargeService.getInsuranceProductCharge(productId,chargeType);
			InsuranceCatalogInfo insuranceCatalogInfo = insuranceCatalogService.findById(insuranceProductInfo.getCatalogId());
		
			if (ListUtil.isNotEmpty(insuranceProtocolChargeInfos)) {
				JSONArray dataList = new JSONArray();
				JSONObject jsonObj = new JSONObject();
				for (InsuranceProtocolChargeInfo info : insuranceProtocolChargeInfos) {
					JSONObject json = new JSONObject();
					//期数
					json.put("val", info.getVal());
					//id
					json.put("protocolInfoId", info.getProtocolInfoId());
					dataList.add(json);
				}
				//该产品的缴费类型
				jsonObj.put("payType", PaymentTypeEnum.getMsgByCode(String.valueOf(insuranceProductInfo.getPayType())));
				//如果不是一次性 和 年交，若是月交、半年交、季度交，即存在分期比例
				jsonObj.put("periodRate", insuranceProductInfo.getPeriodRate());
				//该产品属于主险 还是 附加险
				jsonObj.put("catalogType", null != insuranceCatalogInfo ? insuranceCatalogInfo.getIsMain() : "");
				//该产品的基本保障金额
				jsonObj.put("unitPrice", insuranceProductInfo.getUnitPrice());
				data.put("insuranceProtocolChargeInfos", dataList);
				data.put("insuranceProducts", jsonObj);
			}
			result = toStandardResult(data, tipPrefix);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }

    
    /**
     * 投保人查询该产品的缴费期限
     * @return
     */
    @RequestMapping("getInsuranceProductChargeRate.json")
    @ResponseBody
    public JSONObject getInsuranceProductChargeRate(String productId,String chargeType,String certNo,String period ){
    	String tipPrefix = "";
		JSONObject result = new JSONObject();
		InsuranceProtocolChargeInfo insuranceProtocolChargeInfo = null ;
    	try {
    		JSONObject data = new JSONObject();
			insuranceProtocolChargeInfo = insuranceProtocolChargeService.getInsuranceProductChargeRate(productId,chargeType,certNo,period);
			if (null !=insuranceProtocolChargeInfo) {
				JSONObject json = new JSONObject();
				json.put("val", insuranceProtocolChargeInfo.getVal());
				json.put("protocolInfoId", insuranceProtocolChargeInfo.getProtocolInfoId());
				data.put("insuranceProtocolChargeInfo", json);
			}
			result = toStandardResult(data, tipPrefix);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    
    /**
     * 获取保险公司对应寿险产品的经纪费比例 和  佣金比例，如果产品没有设置，取该产品对应险种设置的比例，如果都没设置，则提示
     * @return
     */
    @RequestMapping("getBrokerageAndcommissionRate.json")
    @ResponseBody
    public JSONObject getBrokerageAndcommissionRate(String productId,String letterId,String appserialPeriod){
    	String tipPrefix = "";
		JSONObject result = new JSONObject();
		InsuranceProtocolChargeInfo brokerageChargeInfo  = null;
		InsuranceProtocolChargeInfo commissionInfo = null;
    	try {
    		JSONObject data = new JSONObject();
			if (!StringUtils.isBlank(productId)) {
				InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(Integer.parseInt(productId));
				if (null != insuranceProductInfo) {
					brokerageChargeInfo = insuranceProtocolChargeService.getBrokerageRate(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode(),insuranceProductInfo,letterId,appserialPeriod);
					commissionInfo = insuranceProtocolChargeService.getBrokerageRate(InsuranceProtocolTypeEnum.INSURANCE_EXPENSE.getCode(),insuranceProductInfo,letterId,appserialPeriod);
				}
				data.put("brokerageChargeInfo", brokerageChargeInfo);
				data.put("commissionInfo", commissionInfo);
			}
			
			result = toStandardResult(data, tipPrefix);
    	} catch (Exception e) {
			logger.error("获取产品经纪费比例和佣金比例失败", e.getMessage());
		}
    	return result;
    }
    

    
    /**
     * 获取产品对应缴费年限对应的期数
     * @return
     */
    @RequestMapping("getPeriodMax.json")
    @ResponseBody
    public JSONObject getPeriodMax(String productId,String letterId){
    	String tipPrefix = "";
		JSONObject result = new JSONObject();
		Integer maxVal = 0;
    	try {
    		JSONObject data = new JSONObject();
			if (!StringUtils.isBlank(productId)) {
				InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(Integer.parseInt(productId));
				if (null != insuranceProductInfo) {
					maxVal = insuranceProtocolChargeService.getPeriodMax(insuranceProductInfo,letterId);
				}
				data.put("maxVal", maxVal);
			}
			
			result = toStandardResult(data, tipPrefix);
    	} catch (Exception e) {
			logger.error("获取产品经纪费比例和佣金比例失败", e.getMessage());
		}
    	return result;
    }
}
