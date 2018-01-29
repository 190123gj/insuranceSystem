package com.born.insurance.web.controller.customer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.CustomerProtocolService;
import com.born.insurance.dal.dataobject.CustomerProtocolDO;
import com.born.insurance.dataobject.CustomerProtocolVo;
import com.born.insurance.util.ExcelUtil;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.StatusEnum;
import com.born.insurance.ws.info.customer.CustomerProtocolInfo;
import com.born.insurance.ws.order.customer.CustomerProtocolOrder;
import com.born.insurance.ws.order.customer.CustomerProtocolQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
/**
 * 
 * <p>Title: CustomerProtocolController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	郭靖
 * @date	2016年12月28日下午3:15:05
 * @version 1.0
 */
@Controller
@RequestMapping("/insurance/customerProtocol")
public class CustomerProtocolController extends BaseController{
	
	private Logger logger= LoggerFactory.getLogger(getClass());
	@Autowired
	private CustomerProtocolService customerProtocolService;
	
	private final String VM_PATH = "/insurance/customerProtocol/";
	
	/**
	 * 查询所有的客户协议
	 * <p>Title: queryCustomerBaseInfoInfoByCondition</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("queryCustomerProtocol.htm")
	public String queryCustomerBaseInfoInfoByCondition(HttpServletRequest request,
			HttpServletResponse response,Model model){
		try {
			CustomerProtocolQueryOrder queryOrder = new CustomerProtocolQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			QueryBaseBatchResult<CustomerProtocolVo> baseBatchResult = customerProtocolService.queryCustomerProtocolList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(baseBatchResult));
			model.addAttribute("checkOrder", queryOrder);
			model.addAttribute("certTypeList", CertTypeEnum.getAllEnum());
			model.addAttribute("statusList", StatusEnum.getAllEnum());
		} catch (Exception e) {
			logger.error("查询客户协议失败:{}", e);
		}
		return VM_PATH + "customerProtocol.vm";
	}
	
	
	/**
	 * 新增或者编辑
	 * <p>Title: edit</p>
	 * <p>Description: </p>
	 * @param protocolId
	 * @param request
	 * @param model
	 * @return
	 */
	   @RequestMapping("editCustomerProtocol.htm")
	    public String editCustomerProtocol(	@RequestParam(value = "id", required = false, defaultValue = "0") long id,
	    HttpServletRequest request, Model model) {
			CustomerProtocolDO info = null;
			if (id > 0) {
				info = customerProtocolService.findById(id);
				//查询关联协议
				if (info.getRelationProtocolId() > 0) {
					CustomerProtocolDO relationProtocol = customerProtocolService.findById(info.getRelationProtocolId());
					model.addAttribute("relationProtocol", relationProtocol);
					model.addAttribute("no", relationProtocol.getNo());
				}
				//附件列表
				queryCommonAttachmentData(model, info.getId() + "",
						CommonAttachmentTypeEnum.CUSTOMER_PROTOCOL);
				model.addAttribute("info", info);	
		    }else{
				info = new CustomerProtocolDO();
				model.addAttribute("info", info);
			}
			return VM_PATH + "addCustomerProtocol.vm";
		}
	   
	   
	
	/**
	 * 确认新增客户协议
	 * <p>Title: addCustomerBaseInfoInfoByCondition</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @param customerProtocolDO
	 * @return
	 */
	@RequestMapping("addCustomerProtocol.json")
	@ResponseBody
	public JSONObject addCustomerBaseInfoInfoByCondition(HttpServletRequest request,
			HttpServletResponse response, CustomerProtocolOrder customerProtocolOrder){
		JSONObject json = new JSONObject();
		try {
			setSessionLocalInfo2Order(customerProtocolOrder);
			InsuranceBaseResult result = customerProtocolService.addCustomerProtocol(customerProtocolOrder);
			//添加附件
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.CUSTOMER_PROTOCOL);
			toJSONResult(json, result, "保存成功", null);
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "保存失败");
			logger.error("保存出错:{}", e);
		}
		return json;
	}
	
	/**
	 * 查看详情
	 * <p>Title: lookCustomerProtocolDetail</p>
	 * <p>Description: </p>
	 * @return
	 */
	@RequestMapping("lookCustomerProtocolDetail.htm")
	public String lookCustomerProtocolDetail(@RequestParam(value = "id", required = true) long id,Model model){
		CustomerProtocolDO info = null;
		try {
			info = customerProtocolService.findById(id);
			//查询关联协议
			if (null != info) {
				if (info.getRelationProtocolId() > 0){
					CustomerProtocolDO relationProtocol = customerProtocolService.findById(info.getRelationProtocolId());
					model.addAttribute("relationProtocol", relationProtocol);
				}
			}
		} catch (Exception e) {
			logger.error("查询协议详情出错:{}", e);
		}
		queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.CUSTOMER_PROTOCOL);
		model.addAttribute("info", info);
		return VM_PATH + "customerProtocolDetail.vm";
	}
	
	/**
	 * 导出客户协议
	 * @throws IOException 
	 */
	@RequestMapping("exportCustomerProtocol.htm")
	public void exportCustomerProtocol(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//协议标题
		String[] headTitle = {"协议编码","协议名称","客户类别","客户名称","证件类型","证件号码","协议状态","创建时间","协议期"};
		List<CustomerProtocolInfo> list = new ArrayList<CustomerProtocolInfo>() ;
		String fileName = "客户协议";
		HSSFWorkbook workbook = ExcelUtil.exportExcel(fileName, headTitle, list);
		/* 以excel的形式导出 */
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"), "ISO-8859-1")+ ".xls");
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		// 取得输出流
		OutputStream os = response.getOutputStream();
		workbook.write(os);
		os.flush();
		os.close(); 
	}
	
}
