package com.born.insurance.web.controller.insuranceContactLetter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.customer.ValueTaxInfoService;
import com.born.insurance.util.token.TokenProccessor;
import com.born.insurance.web.controller.base.WorkflowBaseController;
import com.born.insurance.web.util.PageUtil;
import com.born.insurance.web.util.WebUtil;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.PaymentTypeEnum;
import com.born.insurance.ws.enums.ProjectStatusEnum;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.info.businessBill.BusinessBillBeneficiaryInfo;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterFormInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.info.insuranceProtocolCharge.InsuranceProtocolChargeInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactLetterOrder;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactLetterQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import com.born.insurance.ws.service.businessBillCustomer.BusinessBillCustomerService;
import com.born.insurance.ws.service.insuranceContactLetter.InsuranceContactLetterService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.born.insurance.ws.service.insuranceProtocolCharge.InsuranceProtocolChargeService;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;
import com.yjf.common.lang.util.ListUtil;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * 投保申请
 * Created by wqh on 2016-11-18.
 */
@Controller
@RequestMapping("/insurance/insuranceContactLetter")
public class InsuranceContactLetterController extends WorkflowBaseController {
	@Autowired
	protected InsuranceContactLetterService insuranceContactLetterService;
	
	@Autowired
	protected BaseDataInfoService baseDataInfoService;
	
	@Autowired
	protected InsuranceProtocolChargeService insuranceProtocolChargeService;
	
	@Autowired
	protected BusinessBillCustomerService businessBillCustomerService;
	
	@Autowired
	protected ValueTaxInfoService valueTaxInfoService;
	
	private final static String VM_PATH = "/insurance/insuranceContactLetter/";
	
	@Autowired
	private InsuranceProductService insuranceProductService;
	
	@Autowired
	private PriceContactLetterService priceContactLetterService;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "beginDate", "endDate" };
	}
	
	/**
	 * 
	 *投保申请查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		try {
			InsuranceContactLetterQueryOrder queryOrder = new InsuranceContactLetterQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			QueryBaseBatchResult<InsuranceContactLetterFormInfo> batchResult = insuranceContactLetterService.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("status", ProjectStatusEnum.getAllEnum());
			model.addAttribute("queryOrder", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return VM_PATH + "listInsuranceContactLetter.vm";
	}
	
	/**
	 * 新增、编辑
	 * @param letterId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,String type,String priceContactId,
						HttpServletRequest request, Model model) {
		InsuranceContactLetterInfo info = null;
		if (formId > 0) {
			info = insuranceContactLetterService.findByFormId(formId);
			if (null != info) {
				//查询产品的档次信息
				long productLevelId = info.getProductLevelId();
				long customerUserId = info.getCustomerUserId();
				if (productLevelId > 0  && info.getProductId() > 0) {
					InsuranceProductLevelInfo insuranceProductLevelInfo = insuranceContactLetterService.findProductLevelInfo(productLevelId);
					if (null != insuranceProductLevelInfo) {
						info.setProductLevelLevel(insuranceProductLevelInfo.getLevel());
						info.setPremiumAmount(insuranceProductLevelInfo.getPremiumAmount());
					}
				}
				List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(info.getLetterId());
				List<CustomerCertInfo> findCustomerCertList = insuranceContactLetterService.findCustomerCertList(customerUserId);
				//客户的证件信息
				model.addAttribute("findCustomerCertList", findCustomerCertList);
				//投保内容
				model.addAttribute("insuranceContactLetterDetails", insuranceContactLetterDetails);
				// 投保人 和 被保险人人 
				//TODO 传入业务单号
				List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(info.getBillNo());
				if (null != billCustomerInfos && billCustomerInfos.size()==2) {
					BusinessBillCustomerInfo businessBillCustomerInfo1= billCustomerInfos.get(0);
					BusinessBillCustomerInfo businessBillCustomerInfo2= billCustomerInfos.get(1);
					BusinessBillCustomerInfo policyHolder = businessBillCustomerInfo1.getType().equals("0")?businessBillCustomerInfo1:businessBillCustomerInfo2;
					BusinessBillCustomerInfo insuredPerson = businessBillCustomerInfo1.getType().equals("0")?businessBillCustomerInfo2:businessBillCustomerInfo1;
					model.addAttribute("policyHolder", policyHolder);
					model.addAttribute("insuredPerson", insuredPerson);
					List<CustomerCertInfo> customerCertInfo = insuranceProductService.getCustomerCertInfo(insuredPerson.getCustomerUserId());
					if (ListUtil.isNotEmpty(customerCertInfo)) {
						for (CustomerCertInfo customerCertInfo2 : customerCertInfo) {
							if (customerCertInfo2.getCertType().getCode().equals(CertTypeEnum.IDENTITY_CARD.getCode())) {
								String certNo = customerCertInfo2.getCertNo();
								Integer lastCap =Integer.parseInt(certNo.substring(certNo.length()-1));
								String sex = lastCap % 2 == 0?"woman":"man";
								model.addAttribute("certNo",certNo );
								model.addAttribute("sex",sex );
								for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
									long productId = insuranceContactLetterDetailInfo.getProductId();
									InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
									List<InsuranceProtocolChargeInfo> insuranceProductCharge = insuranceProtocolChargeService.getInsuranceProductCharge(String.valueOf(insuranceContactLetterDetailInfo.getProductId()), sex);
									insuranceContactLetterDetailInfo.setInsuranceProductCharges(insuranceProductCharge);
									InsuranceProtocolChargeInfo insuranceProductChargeRate = insuranceProtocolChargeService.getInsuranceProductChargeRate(String.valueOf(insuranceContactLetterDetailInfo.getProductId()), sex, certNo, insuranceContactLetterDetailInfo.getPeriod());
									if (null != insuranceProductChargeRate) {
										insuranceContactLetterDetailInfo.setPeriodRate(insuranceProductChargeRate.getVal());
									}
									if (null != insuranceProductInfo) {
										insuranceContactLetterDetailInfo.setPayType(PaymentTypeEnum.getMsgByCode(String.valueOf(insuranceProductInfo.getPayType())));
									}
								}
								break;
							}
						}
					}
				} else {
					for (InsuranceContactLetterDetailInfo insuranceContactLetterDetailInfo : insuranceContactLetterDetails) {
						long productId = insuranceContactLetterDetailInfo.getProductId();
						InsuranceProductInfo insuranceProductInfo = insuranceProductService.findById(productId);
						List<InsuranceProtocolChargeInfo> insuranceProductCharge = insuranceProtocolChargeService.getInsuranceProductChargeInfo(insuranceProductInfo.getCompanyUserId(), insuranceProductInfo.getCatalogId(), String.valueOf(productId));
						insuranceContactLetterDetailInfo.setInsuranceProductCharges(insuranceProductCharge);
						
					}
				}
				//受益顺序
				List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(info.getLetterId());
				if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
					BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
					if (businessBillBeneficiaryInfo.getType().equals("0")) {
						model.addAttribute("plan_order", 0);
					} else {
						model.addAttribute("plan_order", 1);
					}
				}
				model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
				//查询保险非寿险定额产品对应的档次信息
				List<InsuranceProductLevelInfo> productLevels = insuranceProductService.getInsuranceProductLevel(info.getProductId());
				model.addAttribute("productLevels", productLevels);
			}
			model.addAttribute("infoJson", JSONObject.toJSONString(info));
			model.addAttribute("type", type);
		} else {
			info = new InsuranceContactLetterInfo();
			if (!StringUtils.isBlank(priceContactId)) {
				PriceContactLetterInfo priceContactLetterInfo = priceContactLetterService.findById(Long.valueOf(priceContactId));
				info.setPriceContactId(Integer.parseInt(priceContactId));
				info.setBusinessUserId(StringUtils.isBlank(priceContactLetterInfo.getBusinessUserId()) ? 0 : Long.valueOf(priceContactLetterInfo.getBusinessUserId()));
				info.setBusinessUserName(priceContactLetterInfo.getBusinessUserName());
				info.setInsuranceType(LifeInsuranceTypeEnum.NOTLIFEINSURANCE.getCode());
				info.setCustomerUserType(priceContactLetterInfo.getCustomerType());
				info.setCustomerUserName(priceContactLetterInfo.getCustomerUserName());
				info.setIsQuota("NO");
			}
			String makeToken = TokenProccessor.getInstance().makeToken();
			request.getSession().setAttribute("token", makeToken);
		}
		model.addAttribute("httpRequest", request);
		queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_DATA);
		queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_OTHER_DATA);
		queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_DATA);
		queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_OTHER_DATA);
		queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_DATA);
		queryCommonAttachmentData(model, info.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_OTHER_DATA);
		model.addAttribute("info", info);
		model.addAttribute("certType", CertTypeEnum.getAllEnum());
		model.addAttribute("customerRelation", getBaseDataInfoInfos(BaseDataInfoTypeEnum.CUSTOMER_RELATION));
		return VM_PATH + "addInsuranceContactLetter.vm";
	}
	
	
	private List<BaseDataInfoInfo> getBaseDataInfoInfos(BaseDataInfoTypeEnum typeEnum) {
		BaseDataInfoQueryOrder queryOrder = new BaseDataInfoQueryOrder();
		queryOrder.setPageSize(99);
		queryOrder.setType(typeEnum);
		return baseDataInfoService.queryList(queryOrder).getPageList();
	}
	
	
	/**
	 * 设置投保申请跟踪提醒
	 * @param projectSetupId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("setFollowWarn.htm")
    public String set(long letterId, HttpServletRequest request, Model model) {
		return VM_PATH + "followingWarn.vm";
	}
	
	
	/**
	 * 投保申请
	 * @param request
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, InsuranceContactLetterOrder order) {
		JSONObject json = new JSONObject();
		
		String tipPrefix = "投保申请";
		try {
			setSessionLocalInfo2Order(order);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setFormCode(FormCodeEnum.CONTACTLETTER);
			/*if (TokenProccessor.isRepeatSubmit(request) && order.getLetterId() == 0) {
				json = toJSONResult(tipPrefix, new Exception("请不要重复提交"));
				return json;
			}*/
			FormBaseResult result = insuranceContactLetterService.save(order);
			request.getSession().removeAttribute("token");
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_DATA);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_OTHER_DATA);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.NOLIFEINSURANCE_DATA);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.NOLIFEINSURANCE_OTHER_DATA);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.LIFEINSURANCE_DATA);
			addAttachfile(result.getKeyId() + "", request, result.getKeyId()+"", null,CommonAttachmentTypeEnum.LIFEINSURANCE_OTHER_DATA);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		/*	request.getSession().removeAttribute("token");*/
		}
		return json;
	}
	
	
	/**
	 * 查看投保申请详情
	 * @param letterId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long letterId, HttpServletRequest request, Model model) {
		InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService
			.findById(letterId);
		if (null != insuranceContactLetterInfo) {
			insuranceContactLetterInfo.setCustomerCertType(CertTypeEnum.getMsgByCode(insuranceContactLetterInfo.getCustomerCertType()));
			//查询用户关联的证件信息
			List<CustomerCertInfo> customerCertInfos = insuranceProductService.getCustomerCertInfo(insuranceContactLetterInfo.getCustomerUserId());
			//查询该申请函所投保的内容
			List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(insuranceContactLetterInfo.getLetterId());
			// 投保人 和 被保险人人
			List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(insuranceContactLetterInfo.getBillNo());
			//受益人信息
			List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(insuranceContactLetterInfo.getLetterId());
			if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
				for (BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo : findBusinessBillBeneficiarys) {
					businessBillBeneficiaryInfo.setBeneficiaryCertType(CertTypeEnum.getMsgByCode(businessBillBeneficiaryInfo.getBeneficiaryCertType()));
				}
				BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
				if (businessBillBeneficiaryInfo.getType().equals("0")) {
					model.addAttribute("plan_order", 0);
				} else {
					model.addAttribute("plan_order", 1);
				}
			}
			
			queryCommonAttachmentData(model, letterId+ "",CommonAttachmentTypeEnum.LIFEINSURANCE_DATA);
			queryCommonAttachmentData(model, letterId + "",CommonAttachmentTypeEnum.LIFEINSURANCE_OTHER_DATA);
			queryCommonAttachmentData(model, letterId + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_DATA);
			queryCommonAttachmentData(model, letterId + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_OTHER_DATA);
			queryCommonAttachmentData(model, letterId + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_DATA);
			queryCommonAttachmentData(model, letterId + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_OTHER_DATA);
			//查看增值税信息
			ValueTaxInfo valueTaxInfo = valueTaxInfoService.findValueTaxInfo(String.valueOf(insuranceContactLetterInfo.getCompanyUserId()));
			model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
			model.addAttribute("billCustomers", billCustomerInfos);
			model.addAttribute("valueTaxInfo", valueTaxInfo);
			model.addAttribute("customerCertInfos",customerCertInfos);
			model.addAttribute("insuranceContactLetterDetails",insuranceContactLetterDetails);
		}
		model.addAttribute("info", insuranceContactLetterInfo);
		FormInfo form = formService.findByFormId(insuranceContactLetterInfo.getFormId());
		model.addAttribute("form", form);
		return VM_PATH + "viewInsuranceContactLetter.vm";
	}
	
	
	/**
	 * 投保申请相关人员审批
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	  @RequestMapping("audit.htm")
		public String audit(long formId, HttpServletRequest request, Model model) {
			FormInfo form = formService.findByFormId(formId);
			if (null != form) {
				InsuranceContactLetterInfo insuranceContactLetterInfo = insuranceContactLetterService.findByFormId(formId);
				insuranceContactLetterInfo.setCustomerCertType(CertTypeEnum.getMsgByCode(insuranceContactLetterInfo.getCustomerCertType()));
				if (null != insuranceContactLetterInfo) {
					//查询用户关联的证件信息
					List<CustomerCertInfo> customerCertInfos = insuranceProductService.getCustomerCertInfo(insuranceContactLetterInfo.getCustomerUserId());
					//查询该申请函所投保的内容
					List<InsuranceContactLetterDetailInfo> insuranceContactLetterDetails = insuranceContactLetterService.getInsuranceContactLetterDetails(insuranceContactLetterInfo.getLetterId());
					// 投保人 和 被保险人人
					List<BusinessBillCustomerInfo> billCustomerInfos = businessBillCustomerService.findBusinessBillCustomers(insuranceContactLetterInfo.getBillNo());
					//受益人信息
					List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys = businessBillCustomerService.findBusinessBillBeneficiarys(insuranceContactLetterInfo.getLetterId());
					if (null != findBusinessBillBeneficiarys && findBusinessBillBeneficiarys.size() > 0) {
						for (BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo : findBusinessBillBeneficiarys) {
							businessBillBeneficiaryInfo.setBeneficiaryCertType(CertTypeEnum.getMsgByCode(businessBillBeneficiaryInfo.getBeneficiaryCertType()));
						}
						BusinessBillBeneficiaryInfo businessBillBeneficiaryInfo = findBusinessBillBeneficiarys.get(0);
						if (businessBillBeneficiaryInfo.getType().equals("0")) {
							model.addAttribute("plan_order", 0);
						} else {
							model.addAttribute("plan_order", 1);
						}
					}
					queryCommonAttachmentData(model, insuranceContactLetterInfo.getLetterId()+ "",CommonAttachmentTypeEnum.LIFEINSURANCE_DATA);
					queryCommonAttachmentData(model, insuranceContactLetterInfo.getLetterId() + "",CommonAttachmentTypeEnum.LIFEINSURANCE_OTHER_DATA);
					queryCommonAttachmentData(model, insuranceContactLetterInfo.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_DATA);
					queryCommonAttachmentData(model, insuranceContactLetterInfo.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCEQUOTA_OTHER_DATA);
					queryCommonAttachmentData(model, insuranceContactLetterInfo.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_DATA);
					queryCommonAttachmentData(model, insuranceContactLetterInfo.getLetterId() + "",CommonAttachmentTypeEnum.NOLIFEINSURANCE_OTHER_DATA);
					model.addAttribute("findBusinessBillBeneficiarys", findBusinessBillBeneficiarys);
					model.addAttribute("billCustomers", billCustomerInfos);
					model.addAttribute("customerCertInfos",customerCertInfos);
					model.addAttribute("insuranceContactLetterDetails",insuranceContactLetterDetails);
				}
				model.addAttribute("info",insuranceContactLetterInfo);
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
			}
			return VM_PATH + "auditForm.vm";
		}
	
	
}
