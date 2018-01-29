package com.born.insurance.web.controller.insuranceProtocol;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.InsuranceProtocolTypeEnum;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolOrder;
import com.born.insurance.ws.service.common.BasicDataCacheService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceProtocol.InsuranceProtocolInfo;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.insuranceProtocol.InsuranceProtocolService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/insuranceProtocol")
public class InsuranceProtocolController extends BaseController {
	@Autowired
	protected InsuranceProtocolService insuranceProtocolService;

	@Autowired
	protected BasicDataCacheService basicDataCacheService;

	private final static String VM_PATH = "/insurance/insuranceProtocol/";

	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[]{"beginDate","endDate","signDate"};
	}
	
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
			InsuranceProtocolQueryOrder queryOrder = new InsuranceProtocolQueryOrder();
			queryOrder.setType(InsuranceProtocolTypeEnum.INSURANCE_PROTOCOL.getCode());
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InsuranceProtocolInfo> batchResult = insuranceProtocolService
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listInsuranceProtocol.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "protocolId", required = false, defaultValue = "0") long protocolId,
						HttpServletRequest request, Model model) {
		InsuranceProtocolInfo info = null;
		if (protocolId > 0) {
			info = insuranceProtocolService.findById(protocolId);
			model.addAttribute("info", info);
			queryCommonAttachmentData(model, info.getProtocolId() + "",
				CommonAttachmentTypeEnum.INSURANCE_PROTOCOL);
		} else {
			info = new InsuranceProtocolInfo();
			model.addAttribute("info", info);
		}
		model.addAttribute("provinceInfos",basicDataCacheService.queryProvinceCity());
		return VM_PATH + "addInsuranceProtocol.vm";
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceProtocolOrder order) {
		JSONObject json = new JSONObject();
		
		String tipPrefix = "操作";
		try {
			setSessionLocalInfo2Order(order);
			InsuranceBaseResult result = insuranceProtocolService.save(order);
			if (result.isSuccess()) {
				long keyId = result.getKeyId();
				//添加附件
				addAttachfile(keyId + "", request, keyId+"", null,
					CommonAttachmentTypeEnum.INSURANCE_PROTOCOL);
			}
			
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("view.htm")
	public String view(long protocolId, HttpServletRequest request, Model model) {
		InsuranceProtocolInfo insuranceProtocolInfo = insuranceProtocolService.findById(protocolId);
		model.addAttribute("info", insuranceProtocolInfo);
		//附件列表
		queryCommonAttachmentData(model, insuranceProtocolInfo.getProtocolId() + "",
			CommonAttachmentTypeEnum.INSURANCE_PROTOCOL);
		return VM_PATH + "viewInsuranceProtocol.vm";
	}
	
}
