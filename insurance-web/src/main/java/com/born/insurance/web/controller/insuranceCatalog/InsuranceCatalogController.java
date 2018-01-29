package com.born.insurance.web.controller.insuranceCatalog;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.util.StringUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.InsuranceCatalogTypeEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogOrder;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import org.springframework.web.bind.annotation.ResponseBody;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/insuranceCatalog")
public class InsuranceCatalogController extends BaseController {
	@Autowired
	protected BaseDataInfoService baseDataInfoService;
	@Autowired
	protected InsuranceCatalogService insuranceCatalogService;
	private final static String VM_PATH = "/insurance/insuranceCatalog/";
	
	/**
	 * 风险预警处理表
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
		queryOrder.setPageSize(100);
		queryOrder.setType(BaseDataInfoTypeEnum.PRICE_TEMPLATE);
		List<BaseDataInfoInfo> BaseDataInfoInfos = baseDataInfoService.queryList(queryOrder)
			.getPageList();
		model.addAttribute("priceTemplates", BaseDataInfoInfos);
		return VM_PATH + "listInsuranceCatalog.vm";
	}
	
	/**
	 * 树形数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tree.json")
	@ResponseBody
	public JSONArray tree(HttpServletRequest request, Model model) {
		JSONArray finalData = new JSONArray();
		try {
			InsuranceCatalogQueryOrder queryOrder = new InsuranceCatalogQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			String parentCatalogId = request.getParameter("parentCatalogId");
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setPageSize(999999);
			List<InsuranceCatalogInfo> batchResult = insuranceCatalogService.queryList(queryOrder)
				.getPageList();
			for (InsuranceCatalogInfo info : batchResult) {
				JSONObject json = new JSONObject();
				json.put("id", info.getCatalogId());
				json.put("parentCatalogId", info.getCatalogId());
				json.put("name", info.getCatalogName());
				json.put("catalogCode",info.getCatalogCode());
				json.put("hasChildren", info.getLastDepth() == BooleanEnum.NO ? "YES" : "NO");
				json.put("isParent", info.getLastDepth() == BooleanEnum.NO);
				json.put("isMain", info.getIsMain());
				json.put("isLifeInsurance", info.getIsLifeInsurance());
				json.put("isLifeInsuranceText",
					StringUtil.equals(info.getIsLifeInsurance(), "YES") ? "寿险" : "非寿险");
				json.put("abbr1", info.getAbbr1());
				json.put("abbr2", info.getAbbr2());
				json.put("abbr3", info.getAbbr3());
				json.put("type", info.getType().getCode());
				json.put("depth", info.getDepth());
				json.put("parentIds",info.getParentIds());
				json.put("parentNames",info.getParentNames());
				json.put("childrenIds",info.getChildrenIds());
				json.put("childrenNames",info.getChildrenNames());
				json.put("priceTemplate",info.getPriceTemplate());
				
				finalData.add(json);
			}
		} catch (Exception e) {
			logger.error("加载数据出错", e);
		}
		return finalData;
	}
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public JSONObject save(HttpServletRequest request, Model model, InsuranceCatalogOrder order) {
		JSONObject json = new JSONObject();
		try {
			setSessionLocalInfoCreatorOrder(order);
			InsuranceBaseResult result = insuranceCatalogService.save(order);
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存失败");
			logger.error("保存出错{}", e);
		}
		return json;
	}
	
	/**
	 * 删除行政区域
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public JSONObject delete(int id, HttpServletRequest request, Model model) {
		JSONObject json = new JSONObject();
		try {
			InsuranceBaseResult result = insuranceCatalogService.deleteById(id);
			toJSONResult(json, result, "删除成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "删除出错");
			logger.error("删除出错 {}", e);
		}
		return json;
	}
	
}
