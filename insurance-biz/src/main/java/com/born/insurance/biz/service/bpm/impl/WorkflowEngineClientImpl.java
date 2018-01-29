package com.born.insurance.biz.service.bpm.impl;

import java.util.*;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.bpm.WorkflowEngineClient;
import com.born.insurance.integration.bpm.flow.FlowServiceProxy;

import com.born.insurance.integration.bpm.flow.ProcessServiceProxy;
import com.born.insurance.integration.bpm.user.WorkflowBatchProcessService;
import com.born.insurance.integration.result.WorkflowBatchProcessResult;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.info.bpm.BpmButtonInfo;
import com.born.insurance.ws.info.bpm.TaskTypeViewInfo;
import com.born.insurance.ws.info.bpm.WebNodeInfo;
import com.born.insurance.ws.info.common.FormExecuteInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.info.priceContactLetterScheme.PriceContactLetterSchemeInfo;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.order.WorkflowBatchProcessOrder;
import com.born.insurance.ws.order.common.SimpleFormAuditOrder;
import com.born.insurance.ws.order.common.SimpleUserInfo;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandQueryOrder;
import com.born.insurance.ws.order.priceContactLetterScheme.PriceContactLetterSchemeQueryOrder;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailQueryOrder;
import com.born.insurance.ws.service.common.FormService;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.born.insurance.ws.service.insuranceContactLetter.InsuranceContactLetterService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;
import com.born.insurance.ws.service.priceContactLetterDemand.PriceContactLetterDemandService;
import com.born.insurance.ws.service.priceContactLetterScheme.PriceContactLetterSchemeService;
import com.born.insurance.ws.service.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;

import com.born.insurance.integration.bpm.flow.TaskEntity;
import com.born.insurance.integration.bpm.result.StartFlowResult;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.integration.common.PropertyConfigService;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.XmlUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.order.bpm.BpmFinishTaskInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.TaskNodeInfo;
import com.born.insurance.ws.order.bpm.TaskOpinion;
import com.born.insurance.ws.order.bpm.TaskSearchOrder;
import com.born.insurance.ws.order.bpm.WorkflowAssignOrder;
import com.born.insurance.ws.order.bpm.WorkflowDoNextOrder;
import com.born.insurance.ws.order.bpm.WorkflowProcessLog;
import com.born.insurance.ws.order.bpm.WorkflowRecoverOrder;
import com.born.insurance.ws.order.bpm.WorkflowStartOrder;
import com.born.insurance.ws.order.bpm.WorkflowTaskInfo;
import com.born.insurance.ws.order.bpm.enums.RecoverStatusEnum;
import com.born.insurance.ws.order.bpm.enums.WorkflowStatusEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("workflowEngineClient")
public class WorkflowEngineClientImpl implements WorkflowEngineClient, ApplicationContextAware {
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private PriceContactLetterService priceContactLetterService;
	
	@Autowired
	protected InsuranceContactLetterService insuranceContactLetterService;
	
	@Autowired
	private PriceContactLetterDemandService priceContactLetterDemandService;
	
	@Autowired
	private PriceContactLetterSchemeService priceContactLetterSchemeService;
	
	@Autowired
	private PriceContactLetterSchemeDetailService priceContactLetterSchemeDetailService;
	
	@Autowired
	private InsuranceCatalogService insuranceCatalogService;

	@Autowired
	private InsuranceProductService insuranceProductService;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public StartFlowResult startFlow(WorkflowStartOrder startOrder) {
		StartFlowResult flowResult = new StartFlowResult();
		try {
			startOrder.check();
			
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			JSONObject json = new JSONObject();
			if (FormCodeEnum.PRICESCHEME == startOrder.getFormCodeEnum()) {
				PriceContactLetterInfo letterInfo = priceContactLetterService
					.findByFormId(startOrder.getFormInfo().getFormId());
				if (!StringUtil.equals(letterInfo.getPriceTemplate(), "currencyTemplate")) {
					PriceContactLetterDemandQueryOrder queryOrder = new PriceContactLetterDemandQueryOrder();
					queryOrder.setContactLetterId(letterInfo.getId());
					PriceContactLetterDemandInfo demandInfo = priceContactLetterDemandService
						.queryList(queryOrder).getPageList().get(0);
					PriceContactLetterSchemeQueryOrder schemeQueryOrder = new PriceContactLetterSchemeQueryOrder();
					schemeQueryOrder.setLetterDemandId(demandInfo.getId());
					PriceContactLetterSchemeInfo schemeInfo = priceContactLetterSchemeService
						.queryList(schemeQueryOrder).getPageList().get(0);
					PriceContactLetterSchemeDetailQueryOrder schemeDetailQueryOrder = new PriceContactLetterSchemeDetailQueryOrder();
					schemeDetailQueryOrder.setLetterSchemeId(schemeInfo.getId());
					List<PriceContactLetterSchemeDetailInfo> schemeDetailInfos = priceContactLetterSchemeDetailService
						.queryList(schemeDetailQueryOrder).getPageList();
					String typeId = "";
					for (PriceContactLetterSchemeDetailInfo schemeDetailInfo : schemeDetailInfos) {
						InsuranceCatalogInfo catalogInfo = insuranceCatalogService
							.findById(schemeDetailInfo.getParentCatalogId());
						if (StringUtil.isEmpty(typeId)) {
							typeId = catalogInfo.getCatalogCode();
						} else {
							if (!StringUtil.equalsIgnoreCase(typeId, catalogInfo.getCatalogCode())) {
								typeId = startOrder.getFormCodeEnum().flowCode();
							}
						}
					}
					if (StringUtil.isNotEmpty(typeId)) {
						json.put("typeId", typeId);
					} else {
						json.put("typeId", startOrder.getFormCodeEnum().flowCode());
					}

				} else {
					json.put("typeId", startOrder.getFormCodeEnum().flowCode());
				}
				
			} else if (FormCodeEnum.CONTACTLETTER == startOrder.getFormCodeEnum()) {
				InsuranceContactLetterInfo info = insuranceContactLetterService
					.findByFormId(startOrder.getFormInfo().getFormId());
				String typeId = "";
				//投保内容查看
				if(info.getProductId() >0 ){
					InsuranceProductInfo productInfo = insuranceProductService.findById(info
							.getProductId());
					InsuranceCatalogInfo catalogInfo = insuranceCatalogService.findById(productInfo.getCatalogId());
					if (StringUtil.isEmpty(typeId)) {
						typeId = catalogInfo.getCatalogCode();
					} else {
						if (!StringUtil.equalsIgnoreCase(typeId, catalogInfo.getCatalogCode())) {
							typeId = startOrder.getFormCodeEnum().flowCode();
						}
					}
				}else{
					List<InsuranceContactLetterDetailInfo> contactLetterDetails = insuranceContactLetterService
							.getInsuranceContactLetterDetails(info.getLetterId());

					for (InsuranceContactLetterDetailInfo detailInfo : contactLetterDetails) {
						InsuranceProductInfo productInfo = insuranceProductService.findById(detailInfo
								.getProductId());
						InsuranceCatalogInfo catalogInfo = insuranceCatalogService.findById(productInfo.getCatalogId());
						if(StringUtil.notEquals(catalogInfo.getIsMain(),"YES")){
							continue;
						}
						if (StringUtil.isEmpty(typeId)) {
							typeId = catalogInfo.getCatalogCode();
						} else {
							if (!StringUtil.equalsIgnoreCase(typeId, catalogInfo.getCatalogCode())) {
								typeId = startOrder.getFormCodeEnum().flowCode();
							}
						}
					}
				}

				
				if (StringUtil.isNotEmpty(typeId)) {
					json.put("typeId", typeId + "_s");
				} else {
					json.put("typeId", startOrder.getFormCodeEnum().flowCode());
				}
			} else {
				json.put("typeId", startOrder.getFormCodeEnum().flowCode());
			}
			json.put("account", startOrder.getProcessUserName());
			logger.info("加载可以用的流程传人参数:" + json.toJSONString());
			String jsonString = flowServiceProxy.getMyFlowListJson(json.toJSONString());
			logger.info("加载可以用的流程:" + jsonString);
			Map<String, Object> resultMap = MiscUtil.parseJSON(jsonString);
			if (resultMap == null) {
				flowResult.setInsuranceResultEnum(InsuranceResultEnum.CALL_REMOTE_SERVICE_ERROR);
				flowResult.setSuccess(false);
			} else {
				long totalCount = NumberUtil.parseLong(String.valueOf(resultMap.get("totalCount")));
				if (totalCount <= 0) {
					flowResult.setInsuranceResultEnum(InsuranceResultEnum.WORKFLOW_NO_FUND);
					flowResult.setSuccess(false);
				} else {
					List flowList = (List) resultMap.get("list");
					Map flowMap = (Map) flowList.get(0);
					Map bpmDefinitionMap = (Map) flowMap.get("obj");
					Long defId = (Long) bpmDefinitionMap.get("defId");
					String defKey = (String) bpmDefinitionMap.get("defKey");
					String subject = (String) bpmDefinitionMap.get("subject");
					if (StringUtil.isNotBlank(startOrder.getCustomTaskName())) {
						subject = startOrder.getCustomTaskName() + "|"
									+ DateUtil.simpleDate(new Date());
					} else {
						subject = subject + "-" + startOrder.getProcessRealName() + "|"
									+ DateUtil.simpleDate(new Date());
					}
					
					//特殊字符处理,防止bpm xml解析出错
					subject = subject.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
						.replaceAll("'", "&apos;");
					
					ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
						propertyConfigService.getBmpServiceProcessService());
					StringBuffer xmlBuffer = new StringBuffer();
					xmlBuffer
						.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><req actDefId=\"\" flowKey=\""
								+ defKey
								+ "\" subject=\""
								+ subject
								+ "\" account=\""
								+ startOrder.getProcessUserName()
								+ "\" businessKey=\""
								+ startOrder.getFormInfo().getUserId() + "\">");
					if (ListUtil.isNotEmpty(startOrder.getFields())) {
						for (FlowVarField field : startOrder.getFields()) {
							xmlBuffer.append("<var varName=\"" + field.getVarName()
												+ "\" varVal=\"" + field.getVarVal()
												+ "\" varType=\"" + field.getVarType().code()
												+ "\" dateFormat=\"\"/>");
						}
					}
					xmlBuffer.append("</req>");
					logger.info("开始流程启动发送的xml:{}", xmlBuffer);
					String xml = processServiceProxy.start(xmlBuffer.toString());
					logger.info("开始流程启动结果:" + xml);
					Document doc = XmlUtil.parseXML(xml);
					if (doc != null) {
						Element root = doc.getRootElement();
						long runId = NumberUtil.parseLong(root.attributeValue("runId"), -1);
						flowResult.setRunId(runId);
						long actInstId = NumberUtil.parseLong(root.attributeValue("actInstId"), -1);
						flowResult.setActInstId(actInstId);
						
						flowResult.setActDefId(root.attributeValue("actDefId"));
						long defId1 = NumberUtil.parseLong(root.attributeValue("defId"), -1);
						flowResult.setDefId(defId1);
						String businessUrl = root.attributeValue("businessUrl");
						flowResult.setBusinessUrl(businessUrl);
						String businessKey = root.attributeValue("businessKey");
						flowResult.setBusinessKey(businessKey);
						String creator = root.attributeValue("creator");
						flowResult.setCreator(creator);
						String startOrgName = root.attributeValue("startOrgName");
						flowResult.setStartOrgName(startOrgName);
						String processName = root.attributeValue("processName");
						flowResult.setProcessName(processName);
						String subject1 = root.attributeValue("subject");
						flowResult.setSubject(subject1);
						String strCreatetime = root.attributeValue("createtime");
						if (StringUtil.isNotBlank(strCreatetime)) {
							Date createtime = DateUtil.parse(strCreatetime);
							flowResult.setCreatetime(createtime);
							
						}
						long creatorId = NumberUtil.parseLong(root.attributeValue("creatorId"), -1);
						flowResult.setCreatorId(creatorId);
						long duration = NumberUtil.parseLong(root.attributeValue("duration"), -1);
						flowResult.setDuration(duration);
						if (StringUtil.isNotBlank(root.attributeValue("endTime"))) {
							Date endTime = DateUtil.parse(root.attributeValue("endTime"));
							flowResult.setEndTime(endTime);
						}
						long parentId = NumberUtil.parseLong(root.attributeValue("parentId"), -1);
						flowResult.setParentId(parentId);
						
						RecoverStatusEnum recover = RecoverStatusEnum.getByBpmStatus(NumberUtil
							.parseInt(root.attributeValue("recover")));
						flowResult.setRecover(recover);
						WorkflowStatusEnum status = WorkflowStatusEnum.getByBpmStatus(NumberUtil
							.parseInt(root.attributeValue("status")));
						flowResult.setStatus(status);
						WorkflowStatusEnum processAfterStatus = WorkflowStatusEnum
								.getByBpmStatus(NumberUtil.parseInt(root
										.attributeValue("processAfterStatus")));
						List<Element> taskList = root.elements("task");
						List<TaskEntity> taskEntities = Lists.newArrayList();
						if (ListUtil.isNotEmpty(taskList)) {
							for (Element e : taskList) {
								TaskEntity taskInfo = new TaskEntity();
								List attributes = e.attributes();
								Map<String, Object> datamap = new HashMap<String, Object>();
								for (Iterator it = attributes.iterator(); it.hasNext();) {
									Attribute attribute = (Attribute) it.next();
									if (StringUtil.equals(attribute.getName(), "taskId")) {
										taskInfo.setId(attribute.getValue());
									} else
										datamap.put(attribute.getName(), attribute.getValue());
									
								}
								
								MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
								taskEntities.add(taskInfo);
							}
						}
						if (flowResult.getRunId() > 0) {
							flowResult.setSuccess(true);
							flowResult.setSubject(subject1);
							flowResult.setNextTaskList(taskEntities);
						}
					}
					
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
	}
	
	@Override
	public List<WorkflowTaskInfo> getTaskList(String runId) {
		List<WorkflowTaskInfo> taskInfos = Lists.newArrayList();
		try {
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTasksByRunId(runId);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				List<Element> elements = root.elements("task");
				if (ListUtil.isNotEmpty(elements)) {
					for (Element e : elements) {
						WorkflowTaskInfo taskInfo = new WorkflowTaskInfo();
						List attributes = e.attributes();
						Map<String, Object> datamap = new HashMap<String, Object>();
						for (Iterator it = attributes.iterator(); it.hasNext();) {
							Attribute attribute = (Attribute) it.next();
							datamap.put(attribute.getName(), attribute.getValue());
						}
						MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
						if (StringUtil.isBlank(taskInfo.getAssignee())
							&& "0".equals(taskInfo.getAssignee()))
							taskInfos.add(taskInfo);
					}
				}
				
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
		}
		return taskInfos;
	}
	
	@Override
	public WorkflowTaskInfo getTaskInfo(String taskId) {
		WorkflowTaskInfo taskInfo = new WorkflowTaskInfo();
		try {
			if (StringUtil.isEmpty(taskId))
				return null;
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTaskByTaskId(taskId);
			logger.info("获取流程接点:" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				List attributes = root.attributes();
				Map<String, Object> datamap = new HashMap<String, Object>();
				for (Iterator it = attributes.iterator(); it.hasNext();) {
					Attribute attribute = (Attribute) it.next();
					datamap.put(attribute.getName(), attribute.getValue());
				}
				MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskInfo;
	}
	
	@Override
	public WebNodeInfo getTaskNode(String taskId) {
		WebNodeInfo taskNodeInfo = new WebNodeInfo();
		try {
			if (StringUtil.isEmpty(taskId))
				return null;
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xmlString = processServiceProxy.getTaskNode(taskId);
			logger.info("获取流程接点:" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				Element root = doc.getRootElement();
				addNodeAttributeValue(root, taskNodeInfo);
				List<Element> elementButtons = root.elements("bpmNodeButton");
				if (ListUtil.isNotEmpty(elementButtons)) {
					List<BpmButtonInfo> buttonInfos = Lists.newArrayList();
					for (Element bpmNodeButton : elementButtons) {
						BpmButtonInfo bpmNodeButtonInfo = new BpmButtonInfo();
						bpmNodeButtonInfo.setButtonName(bpmNodeButton.attributeValue("buttonName"));
						bpmNodeButtonInfo.setOperatortype(NumberUtil.parseInt(bpmNodeButton
							.attributeValue("operatortype")));
						buttonInfos.add(bpmNodeButtonInfo);
					}
					taskNodeInfo.setBpmButtonInfos(buttonInfos);
				}
				List<Element> elements = root.elements("nextFlowNode");
				if (ListUtil.isNotEmpty(elements)) {
					List<TaskNodeInfo> nodeInfos = Lists.newArrayList();
					for (Element taskNode : elements) {
						TaskNodeInfo childNodeInfo = new TaskNodeInfo();
						addNodeAttributeValue(taskNode, childNodeInfo);
						nodeInfos.add(childNodeInfo);
					}
					taskNodeInfo.setNextFlowNode(nodeInfos);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskNodeInfo;
	}
	
	private void addNodeAttributeValue(Element taskNode, TaskNodeInfo childNodeInfo) {
		childNodeInfo.setNodeId(taskNode.attributeValue("nodeId"));
		childNodeInfo.setNodeName(taskNode.attributeValue("nodeName"));
		childNodeInfo.setNodeType(taskNode.attributeValue("nodeType"));
		childNodeInfo.setFormUrl(taskNode.attributeValue("formUrl"));
		childNodeInfo.setInformType(taskNode.attributeValue("informType"));
		childNodeInfo.setCanSelectUser("true".equals(taskNode.attributeValue("canSelectedUser")));
	}
	
	@Override
	public WorkflowResult doNext(WorkflowDoNextOrder doNextOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			doNextOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			Element doc = XmlUtil.createDoc(null, "req");
			doc.addAttribute("taskId", doNextOrder.getTaskId());
			doc.addAttribute("account", doNextOrder.getUserAccount());
			doc.addAttribute("voteAgree", doNextOrder.getVoteAgree());
			doc.addAttribute("nextNodeId", StringUtil.defaultIfBlank(doNextOrder.getNextNodeId()));
			doc.addAttribute("voteContent", doNextOrder.getVoteContent());
			doc.addAttribute("isBack", StringUtil.defaultIfBlank(doNextOrder.getIsBack()));
			StringBuffer xmlBuffer = new StringBuffer();
			xmlBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><req taskId=\""
								+ doNextOrder.getTaskId() + "\" account=\""
								+ doNextOrder.getUserAccount() + "\" voteAgree=\""
								+ doNextOrder.getVoteAgree() + "\" nextNodeId=\""
								+ StringUtil.defaultIfBlank(doNextOrder.getNextNodeId())
								+ "\" voteContent=\"" + doNextOrder.getVoteContent()
								+ "\" isBack=\""
								+ StringUtil.defaultIfBlank(doNextOrder.getIsBack()) + "\">");
			if (ListUtil.isNotEmpty(doNextOrder.getFields())) {
				for (FlowVarField field : doNextOrder.getFields()) {
					
					Element docTag = XmlUtil.createDoc(doc, "var");
					docTag.addAttribute("varName", field.getVarName());
					docTag.addAttribute("varVal", StringUtil.defaultIfBlank(field.getVarVal()));
					docTag.addAttribute("varType", field.getVarType().code());
					docTag.addAttribute("dateFormat",
						StringUtil.defaultIfBlank(field.getVarType().getDateFormat()));
					
				}
			}
			String sendXml = doc.asXML();
			logger.info("流程处理发送:" + sendXml);
			String xml = processServiceProxy.doNext(sendXml);
			logger.info("流程处理接收:" + xml);
			Document xmlTarge = XmlUtil.parseXML(xml);
			if (xmlTarge != null) {
				if (doc != null) {
					Element root = xmlTarge.getRootElement();
					if (!StringUtil.equals(root.getName(), "run")) {
						flowResult
							.setInsuranceResultEnum(InsuranceResultEnum.DO_ACTION_STATUS_ERROR);
						flowResult.setSuccess(false);
						return flowResult;
					}
					long runId = NumberUtil.parseLong(root.attributeValue("runId"), -1);
					
					String businessUrl = root.attributeValue("businessUrl");
					flowResult.setBusinessUrl(businessUrl);
					String businessKey = root.attributeValue("businessKey");
					flowResult.setBusinessKey(businessKey);
					String creator = root.attributeValue("creator");
					flowResult.setCreator(creator);
					String startOrgName = root.attributeValue("startOrgName");
					flowResult.setStartOrgName(startOrgName);
					String processName = root.attributeValue("processName");
					flowResult.setProcessName(processName);
					String subject1 = root.attributeValue("subject");
					flowResult.setSubject(subject1);
					
					Date createtime = DateUtil.parse(root.attributeValue("createtime"));
					flowResult.setCreatetime(createtime);
					long creatorId = NumberUtil.parseLong(root.attributeValue("creatorId"), -1);
					flowResult.setCreatorId(creatorId);
					long duration = NumberUtil.parseLong(root.attributeValue("duration"), -1);
					flowResult.setDuration(duration);
					if (StringUtil.isNotBlank(root.attributeValue("endTime"))) {
						Date endTime = DateUtil.parse(root.attributeValue("endTime"));
						flowResult.setEndTime(endTime);
					}
					long parentId = NumberUtil.parseLong(root.attributeValue("parentId"), -1);
					flowResult.setParentId(parentId);
					
					RecoverStatusEnum recover = RecoverStatusEnum.getByBpmStatus(NumberUtil
						.parseInt(root.attributeValue("recover")));
					flowResult.setRecover(recover);
					WorkflowStatusEnum status = WorkflowStatusEnum.getByBpmStatus(NumberUtil
						.parseInt(root.attributeValue("status")));
					flowResult.setStatus(status);
					WorkflowStatusEnum processAfterStatus = WorkflowStatusEnum
						.getByBpmStatus(NumberUtil.parseInt(root
							.attributeValue("processAfterStatus")));
					flowResult.setProcessAfterStatus(processAfterStatus);
					List<Element> taskList = root.elements("task");
					List<TaskEntity> taskEntities = Lists.newArrayList();
					if (ListUtil.isNotEmpty(taskList)) {
						for (Element e : taskList) {
							TaskEntity taskInfo = new TaskEntity();
							List attributes = e.attributes();
							Map<String, Object> datamap = new HashMap<String, Object>();
							for (Iterator it = attributes.iterator(); it.hasNext();) {
								Attribute attribute = (Attribute) it.next();
								if (StringUtil.equals(attribute.getName(), "taskId")) {
									taskInfo.setId(attribute.getValue());
								} else
									datamap.put(attribute.getName(), attribute.getValue());
								
							}
							
							MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
							taskEntities.add(taskInfo);
						}
					}
					
					flowResult.setSuccess(true);
					flowResult.setNextTaskList(taskEntities);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public WorkflowResult defRecover(WorkflowRecoverOrder recoverOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			recoverOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("runId", recoverOrder.getRunId());
			jsonObject.put("informType", recoverOrder.getInformType());
			jsonObject.put("opinion", recoverOrder.getOpinion());
			jsonObject.put("backToStart", recoverOrder.getBackToStart());
			
			String json = jsonObject.toJSONString();
			logger.info("流程处理发送:userId={},json={}", recoverOrder.getUserId(), json);
			String returnJson = processServiceProxy.defRecover(
				String.valueOf(recoverOrder.getUserId()), json);
			logger.info("流程处理接收:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("0", String.valueOf(dataMap.get("state")))) {
					flowResult.setSuccess(true);
					flowResult.setMessage((String) dataMap.get("msg"));
					List<TaskEntity> nextTaskList = Lists.newArrayList();
					if (dataMap.get("taskId") != null) {
						TaskEntity taskEntity = new TaskEntity();
						taskEntity.setId(String.valueOf(dataMap.get("taskId")));
						nextTaskList.add(taskEntity);
					}
					flowResult.setNextTaskList(nextTaskList);
				} else {
					flowResult.setSuccess(false);
					flowResult.setMessage((String) dataMap.get("msg"));
				}
			} else {
				flowResult.setSuccess(false);
				flowResult.setMessage("撤回失败");
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public WorkflowResult taskAssign(WorkflowAssignOrder workflowAssignOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			workflowAssignOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("taskId", workflowAssignOrder.getTaskId());
			jsonObject.put("assigneeId", workflowAssignOrder.getAssigneeId());
			jsonObject.put("assigneeName", workflowAssignOrder.getAssigneeName());
			jsonObject.put("memo", workflowAssignOrder.getMemo());
			jsonObject.put("informType", workflowAssignOrder.getInformType());
			jsonObject.put("userId", workflowAssignOrder.getUserId());
			String json = jsonObject.toJSONString();
			logger.info("流程处理发送:userId={},json={}", json);
			String returnJson = processServiceProxy.taskAssign(json);
			logger.info("流程处理接收:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("0", String.valueOf(dataMap.get("state")))) {
					flowResult.setSuccess(true);
					flowResult.setMessage((String) dataMap.get("msg"));
				} else {
					flowResult.setSuccess(false);
					flowResult.setMessage((String) dataMap.get("msg"));
				}
			} else {
				flowResult.setSuccess(false);
				flowResult.setMessage("交办失败");
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public WorkflowResult endingWorkflow(WorkflowDoNextOrder doNextOrder) {
		WorkflowResult flowResult = new WorkflowResult();
		try {
			doNextOrder.check();
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("taskId", doNextOrder.getTaskId());
			jsonObject.put("memo", doNextOrder.getVoteContent());
			String userId = String.valueOf(doNextOrder.getUserId());
			String json = jsonObject.toJSONString();
			logger.info("流程处理发送:userId={} {}", userId, json);
			String returnJson = processServiceProxy.taskEndProcess(userId, json);
			logger.info("流程处理接收:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("0", String.valueOf(dataMap.get("state")))) {
					flowResult.setSuccess(true);
					flowResult.setMessage((String) dataMap.get("msg"));
				} else {
					flowResult.setSuccess(false);
					flowResult.setMessage((String) dataMap.get("msg"));
				}
			} else {
				flowResult.setSuccess(false);
				flowResult.setMessage("结束流程失败");
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			flowResult.setSuccess(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flowResult.setInsuranceResultEnum(InsuranceResultEnum.UN_KNOWN_EXCEPTION);
			flowResult.setSuccess(false);
		}
		return flowResult;
		
	}
	
	@Override
	public List<TaskOpinion> getTaskUsers(String strTaskId) {
		List<TaskOpinion> taskOpinions = Lists.newArrayList();
		try {
			Assert.hasText(strTaskId);
			logger.info("获取步骤执行人:strTaskId={}", strTaskId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			String returnJson = flowServiceProxy.getTaskUsers(strTaskId);
			
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("result")))) {
					List<Map> taskOpinionMapList = (List<Map>) dataMap.get("taskOpinionList");
					if (taskOpinionMapList != null) {
						for (Map item : taskOpinionMapList) {
							TaskOpinion taskOpinion = new TaskOpinion();
							MiscUtil.setInfoPropertyByMap(item, taskOpinion);
							Map taskExeStatus = (Map) item.get("taskExeStatus");
							if (taskExeStatus != null) {
								Boolean isRead = (Boolean) taskExeStatus.get("read");
								if (isRead != null)
									taskOpinion.setRead(isRead);
							}
							taskOpinions.add(taskOpinion);
						}
					}
					
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskOpinions;
		
	}
	
	@Override
	public List<TaskOpinion> getTaskUsers(String instanceId, String nodeId) {
		List<TaskOpinion> taskOpinions = Lists.newArrayList();
		try {
			Assert.hasText(instanceId);
			Assert.hasText(nodeId);
			logger.info("获取步骤执行人:instanceId={},nodeId={}", instanceId, nodeId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			String returnJson = flowServiceProxy.getTaskUsersByInstanceIdAndNodeId(instanceId,
				nodeId);
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("code")))) {
					List<Map> taskOpinionMapList = (List<Map>) dataMap.get("taskOpinionList");
					if (taskOpinionMapList != null) {
						for (Map item : taskOpinionMapList) {
							TaskOpinion taskOpinion = new TaskOpinion();
							MiscUtil.setInfoPropertyByMap(item, taskOpinion);
							Map taskExeStatus = (Map) item.get("taskExeStatus");
							if (taskExeStatus != null) {
								Boolean isRead = (Boolean) taskExeStatus.get("read");
								if (isRead != null)
									taskOpinion.setRead(isRead);
							}
							//查询候选人列表
							if (taskOpinion.getExeUserId() == null
								|| taskOpinion.getExeUserId() == 0) {
								
								List<Map> candidateUserStatusList = (List<Map>) item
									.get("candidateUserStatusList");
								if (ListUtil.isNotEmpty(candidateUserStatusList)) {
									List<Long> candidateUserList = Lists.newArrayList();
									for (Map candidateUser : candidateUserStatusList) {
										String executorIds = (String) candidateUser
											.get("executorAllId");
										if (executorIds != null) {
											String[] executorIdArr = executorIds.split(",");
											for (String excutorId : executorIdArr) {
												candidateUserList.add(NumberUtil
													.parseLong(excutorId));
											}
										}
									}
									taskOpinion.setCandidateUserList(candidateUserList);
								}
							}
							
							taskOpinions.add(taskOpinion);
						}
					}
					
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return taskOpinions;
		
	}
	
	@Override
	public InsuranceBaseResult readTask(String actInstId, String taskId) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		try {
			Assert.hasText(actInstId);
			Assert.hasText(taskId);
			logger.info("获取步骤执行人:actInstId={},taskId={}", actInstId, taskId);
			FlowServiceProxy flowServiceProxy = new FlowServiceProxy(
				propertyConfigService.getBmpServiceFlowService());
			String returnJson = flowServiceProxy.readTask(actInstId, taskId);
			logger.info("获取步骤执行人:" + returnJson);
			HashMap<String, Object> dataMap = MiscUtil.parseJSON(returnJson);
			if (dataMap != null) {
				if (StringUtil.equals("1", String.valueOf(dataMap.get("code")))) {
					baseResult.setSuccess(true);
				} else {
					baseResult.setSuccess(false);
					baseResult.setMessage(String.valueOf(dataMap.get("message")));
				}
			}
			
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return baseResult;
		
	}
	
	@Override
	public QueryBaseBatchResult<TaskTypeViewInfo> getTaskTypeView(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<TaskTypeViewInfo> batchResult = new QueryBaseBatchResult<TaskTypeViewInfo>();
		try {
			taskSearchOrder.check();
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><req account=\""
							+ taskSearchOrder.getUserName() + "\" taskNodeName=\"" + ""
							+ "\" subject=\"" + "" + "\" processName=\"\"" + "></req>";
			logger.info("获取代办任务发送xml:{}", xml);
			String xmlString = processServiceProxy.getTasksByAccountGroupByProcessName(xml);
			logger.info("获取代办任务结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				//root.attributeValue("notRead");
				//root.attributeValue("read");
				
				List<Element> tasks = root.elements("task");
				PageComponent component = new PageComponent(taskSearchOrder, tasks.size());
				Map<String, String> map = null;
				List<TaskTypeViewInfo> list = new ArrayList<TaskTypeViewInfo>();
				for (Element element : tasks) {
					TaskTypeViewInfo taskInfo = new TaskTypeViewInfo();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					list.add(taskInfo);
					//					}
					
				}
				batchResult.initPageParam(component);
				batchResult.setPageList(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<WorkflowTaskInfo> getTasksByAccount(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<WorkflowTaskInfo> batchResult = new QueryBaseBatchResult<WorkflowTaskInfo>();
		try {
			taskSearchOrder.check();
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><req account=\""
							+ taskSearchOrder.getUserName() + "\" taskNodeName=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getTaskNodeName())
							+ "\" subject=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getSubject())
							+ "\" processName=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getProcessName())
							+ "\" orderField=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getOrderField())
							+ "\" orderSeq=\""
							+ StringUtil.defaultIfBlank(taskSearchOrder.getProcessName())
							+ "\" pageSize=\"" + taskSearchOrder.getPageSize()
							+ "\" currentPage= \"" + taskSearchOrder.getPageNumber() + "\"></req>";
			logger.info("获取代办任务发送xml:{}", xml);
			String xmlString = processServiceProxy.getTasksByAccount(xml);
			logger.info("获取代办任务结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				//root.attributeValue("notRead");
				//root.attributeValue("read");
				PageComponent component = new PageComponent(taskSearchOrder,
					NumberUtil.parseLong(root.attributeValue("total")));
				List<Element> tasks = root.elements("task");
				Map<String, String> map = null;
				List<WorkflowTaskInfo> list = new ArrayList<WorkflowTaskInfo>();
				for (Element element : tasks) {
					WorkflowTaskInfo taskInfo = new WorkflowTaskInfo();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					if (datamap.get("createDate") != null) {
						taskInfo.setCreateDate(DateUtil.parse((String) datamap.get("createDate")));
						taskInfo.setCreateDateStr((String) datamap.get("createDate"));
					} else {
						if (StringUtil.isNotBlank(taskInfo.getCreateDateStr())) {
							taskInfo.setCreateDate(DateUtil.parse(taskInfo.getCreateDateStr()));
						} else {
							String[] tempArray = taskInfo.getSubject().split("-");
							if (tempArray.length == 5) {
								String createDateStr = tempArray[2] + "-" + tempArray[3] + "-"
														+ tempArray[4];
								taskInfo.setCreateDateStr(createDateStr);
								taskInfo.setCreateDate(DateUtil.parse(createDateStr));
							}
						}
					}
					taskInfo.setCreateTime(DateUtil.parse((String) datamap.get("createTime")));
					//					if (StringUtil.isNotBlank(taskInfo.getTaskUrl())) {
					list.add(taskInfo);
					//					}
					
				}
				batchResult.initPageParam(component);
				batchResult.setPageList(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<BpmFinishTaskInfo> getFinishTask(TaskSearchOrder taskSearchOrder) {
		QueryBaseBatchResult<BpmFinishTaskInfo> batchResult = new QueryBaseBatchResult<BpmFinishTaskInfo>();
		try {
			taskSearchOrder.check();
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			logger.info("获取已办任务:{}", taskSearchOrder);
			JSONObject queryJson = new JSONObject();
			queryJson.put("d-636069-p", taskSearchOrder.getPageNumber());
			queryJson.put("d-636069-z", taskSearchOrder.getPageSize());
			queryJson.put("d-636069-oz", taskSearchOrder.getPageSize());
			String jsonString = processServiceProxy.getAlreadyMattersList(
				String.valueOf(taskSearchOrder.getUserId()), queryJson.toString());
			logger.info("获取已办任务结果:{}" + jsonString);
			HashMap<String, Object> taskMap = MiscUtil.parseJSON(jsonString);
			if (taskMap == null || !StringUtil.equals("0", (String) taskMap.get("state"))) {
				batchResult.setSuccess(false);
				batchResult.setMessage("获取已办任务失败");
			} else {
				
				batchResult.setSuccess(true);
				batchResult.setTotalCount((Long) taskMap.get("totalCount"));
				batchResult.setPageCount((Long) taskMap.get("totalPage"));
				List<HashMap<String, Object>> taskList = (List<HashMap<String, Object>>) taskMap
					.get("list");
				
				List<BpmFinishTaskInfo> dataList = Lists.newArrayList();
				if (taskList != null) {
					for (HashMap<String, Object> task : taskList) {
						BpmFinishTaskInfo finishTask = new BpmFinishTaskInfo();
						finishTask.setActDefId((String) task.get("actInstId"));
						finishTask.setActInstId((String) task.get("actInstId"));
						finishTask.setBusinessUrl((String) task.get("businessUrl"));
						
						HashMap<String, Object> ctMap = (HashMap<String, Object>) task
							.get("createtime");
						if (ctMap != null) {
							Date createTime = new Date(((Long) ctMap.get("time")));
							finishTask.setCreatetime(createTime);
						}
						finishTask.setCreator((String) task.get("creator"));
						finishTask.setCreatorId((Long) task.get("creatorId"));
						finishTask.setDefId((Long) task.get("defId"));
						
						HashMap<String, Object> etMap = (HashMap<String, Object>) task
							.get("endTime");
						if (etMap != null) {
							Date endTime = new Date(((Long) etMap.get("time")));
							finishTask.setEndTime(endTime);
						}
						finishTask.setProcessName((String) task.get("processName"));
						finishTask.setRunId((Long) task.get("runId"));
						finishTask.setSubject((String) task.get("subject"));
						finishTask.setStatus((Long) task.get("status"));
						dataList.add(finishTask);
					}
				}
				batchResult.setPageList(dataList);
				batchResult.setPageNumber(taskSearchOrder.getPageNumber());
				batchResult.setPageSize(taskSearchOrder.getPageSize());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setMessage("查询已办任务出错");
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<WorkflowProcessLog> getProcessOpinionByActInstId(String actInstId) {
		QueryBaseBatchResult<WorkflowProcessLog> batchResult = new QueryBaseBatchResult<WorkflowProcessLog>();
		try {
			
			ProcessServiceProxy processServiceProxy = new ProcessServiceProxy(
				propertyConfigService.getBmpServiceProcessService());
			
			logger.info("获取审核历史发送xml:{}", actInstId);
			String xmlString = processServiceProxy.getProcessOpinionByActInstId(actInstId);
			logger.info("获取审核历史结果:{}" + xmlString);
			Document doc = XmlUtil.parseXML(xmlString);
			if (doc != null) {
				
				Element root = doc.getRootElement();
				//root.attributeValue("notRead");
				//root.attributeValue("read");
				
				List<Element> tasks = root.elements("task");
				PageComponent component = new PageComponent(1, tasks.size());
				Map<String, String> map = null;
				List<WorkflowProcessLog> list = new ArrayList<WorkflowProcessLog>();
				for (Element element : tasks) {
					WorkflowProcessLog taskInfo = new WorkflowProcessLog();
					List attributes = element.attributes();
					Map<String, Object> datamap = new HashMap<String, Object>();
					for (Iterator it = attributes.iterator(); it.hasNext();) {
						Attribute attribute = (Attribute) it.next();
						datamap.put(attribute.getName(), attribute.getValue());
					}
					MiscUtil.setInfoPropertyByMap(datamap, taskInfo);
					//taskInfo.setCheckStatus(checkStatus)
					
					if (StringUtil.isBlank(taskInfo.getEndTime())) {
						list.add(0, taskInfo);
					} else
						list.add(taskInfo);
					
				}
				batchResult.initPageParam(component);
				batchResult.setSuccess(true);
				batchResult.setPageList(list);
				logger.info(list.toString());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		return batchResult;
	}
	
	@Override
	public WorkflowBatchProcessResult batchProcess(WorkflowBatchProcessOrder order) {
		WorkflowBatchProcessResult result = new WorkflowBatchProcessResult();
		try {
			if (order == null) {
				throw ExceptionFactory.newFcsException(InsuranceResultEnum.WRONG_REQ_PARAM,
					"请求参数错误");
			}
			//检查order
			
			WorkflowBatchProcessService processService = null;
			if (StringUtil.isNotBlank(order.getProcessServiceName())) {
				processService = getBean(order.getProcessServiceName());
			}
			
			//成功
			List<Long> successFormIdList = Lists.newArrayList();
			List<String> successMessageList = Lists.newArrayList();
			//失败
			List<Long> failureFormIdList = Lists.newArrayList();
			//失败消息
			List<String> failureMessageList = Lists.newArrayList();
			//不支持
			List<Long> nonSupportFormIdList = Lists.newArrayList();
			
			//去重
			Set<Long> formIds = new HashSet<Long>();
			for (long formId : order.getProcessFormIds()) {
				formIds.add(formId);
			}
			
			for (long formId : formIds) { //批量处理开始
			
				try {
					
					FormInfo form = formService.findByFormId(formId);
					if (form == null) {
						failureFormIdList.add(formId);
						failureMessageList.add("未匹配到表单");
						continue;
					}
					
					//解析当前人员的任务
					String[] task = getUserTask(form, order.getUserId());
					String taskId = task[0];
					String taskUrl = task[1];
					if (StringUtil.isBlank(taskId) || StringUtil.isBlank(taskUrl)) { //任务不存在
						failureFormIdList.add(formId);
						failureMessageList.add("不是当前处理人");
						continue;
					}
					
					SimpleFormAuditOrder auditOrder = new SimpleFormAuditOrder();
					BeanCopier.staticCopy(order, auditOrder); //主要设置处理人员等信息
					auditOrder.setTaskId(NumberUtil.parseLong(taskId));
					auditOrder.setFormId(formId);
					auditOrder.setVoteContent(order.getProcessContent());
					
					/*驳回到发起人*/
					if ("back2start".equals(order.getProcessType())) {
						
						//查询是否有按钮
						WebNodeInfo node = getTaskNode(taskId);
						if (node == null) {
							failureFormIdList.add(formId);
							failureMessageList.add("查询任务节点出错");
							continue;
						}
						List<BpmButtonInfo> buttonList = node.getBpmButtonInfos();
						if (ListUtil.isEmpty(buttonList)) {
							failureFormIdList.add(formId);
							failureMessageList.add("无驳回权限");
							continue;
						} else {
							boolean hasBackButton = false;
							for (BpmButtonInfo button : buttonList) {
								//驳回按钮
								if (button.getOperatortype() == 4 || button.getOperatortype() == 5) {
									hasBackButton = true;
									break;
								}
							}
							if (!hasBackButton) {
								failureFormIdList.add(formId);
								failureMessageList.add("无驳回权限");
								continue;
							}
						}
						auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_REJECT_TOSTART));
						auditOrder.setIsBack("2");
						if (StringUtil.isBlank(auditOrder.getVoteContent())) {
							auditOrder.setVoteContent("驳回");
						}
					} else {//通过
					
						//审核前自定义参数
						Map<String, Object> customizeMap = null;
						//自定义处理类
						if (processService != null) {
							//检查是否支持批量审核
							boolean isSupport = true;
							String[] nonSupportTask = processService.nonSupportTask();
							if (nonSupportTask != null) {
								for (String t : nonSupportTask) {
									if (taskUrl.startsWith(t)) {
										isSupport = false;
										break;
									}
								}
							}
							if (!isSupport) {
								nonSupportFormIdList.add(formId);
								continue;
							}
							
							//设置当前执行人信息
							SimpleUserInfo excutor = new SimpleUserInfo();
							excutor.setUserId(order.getUserId());
							excutor.setUserName(order.getUserName());
							excutor.setUserAccount(order.getUserAccount());
							excutor.setMobile(order.getUserMobile());
							excutor.setEmail(order.getUserEmail());
							excutor.setDeptId(order.getDeptId());
							excutor.setDeptName(order.getDeptName());
							excutor.setDeptPath(order.getDeptPath());
							excutor.setDeptPathName(order.getDeptPathName());
							
							//验证是否支持
							isSupport = processService.isSupport(form, excutor);
							if (!isSupport) {
								nonSupportFormIdList.add(formId);
								continue;
							}
							//添加审核自定义参数
							customizeMap = processService.makeCustomizeMap(form, excutor);
						}
						
						auditOrder.setCustomizeMap(customizeMap);
						auditOrder.setVoteAgree(String.valueOf(TaskOpinion.STATUS_AGREE));
						if (StringUtil.isBlank(auditOrder.getVoteContent())) {
							auditOrder.setVoteContent("同意");
						}
					}
					
					InsuranceBaseResult auditResult = formService.auditProcess(auditOrder);
					if (auditResult != null && auditResult.isSuccess()) {
						successFormIdList.add(formId);
						
						//查看下步执行人
						String nextAuditor = "";
						String nextNode = "";
						String nextInfo = auditResult.getUrl();
						if (StringUtil.isNotBlank(nextInfo)) {
							String[] next = nextInfo.split(";");
							if (next.length > 0)
								nextNode = next[0];
							if (next.length > 1)
								nextAuditor = next[1];
						}
						
						String message = "处理成功 ";
						if ("FLOW_FINISH".equals(nextNode)) {
							message = "流程处理完成";
						} else {
							if (StringUtil.isNotBlank(nextNode)) {
								message += "[ " + nextNode + " ]";
							}
							if (StringUtil.isNotBlank(nextAuditor)) {
								message += "[ 待执行人：" + nextAuditor + " ]";
							}
						}
						successMessageList.add(message);
					} else {
						failureFormIdList.add(formId);
						if (StringUtil.isNotBlank(auditResult.getMessage())) {
							failureMessageList.add("处理失败[ " + auditResult.getMessage() + " ]");
						} else {
							failureMessageList.add("处理失败");
						}
					}
				} catch (Exception e) {
					logger.error("批量审核出错 formId：{}", formId);
				}
				
			}//循环处理结束
			result.setSuccessFormIdList(successFormIdList);
			result.setSuccessMessageList(successMessageList);
			result.setFailureFormIdList(failureFormIdList);
			result.setFailureMessageList(failureMessageList);
			result.setNonSupportFormIdList(nonSupportFormIdList);
			result.setSuccess(true);
			result.setMessage("批量处理成功");
		} catch (IllegalArgumentException ll) {
			result.setSuccess(false);
			result.setMessage(ll.getMessage());
			logger.error("批量处理异常：{}", ll);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("批量处理异常");
			logger.error("批量处理异常：{}", e);
		}
		return result;
	}
	
	/**
	 * 获取当前执行人任务
	 * @param form
	 * @param executor
	 * @return
	 */
	private String[] getUserTask(FormInfo form, long executor) {
		String[] task = new String[2];
		try {
			List<FormExecuteInfo> exeList = form.getFormExecuteInfo();
			if (ListUtil.isNotEmpty(exeList)) {
				for (FormExecuteInfo exeInfo : exeList) {
					if (StringUtil.isBlank(exeInfo.getTaskId()))
						continue;
					if (exeInfo.getUserId() > 0) {
						if (exeInfo.getUserId() == executor) {
							task[0] = exeInfo.getTaskId();
							task[1] = exeInfo.getTaskUrl();
							break;
						}
					} else if (ListUtil.isNotEmpty(exeInfo.getCandidateUserList())) {
						for (SimpleUserInfo user : exeInfo.getCandidateUserList()) {
							if (user.getUserId() == executor) {
								task[0] = exeInfo.getTaskId();
								task[1] = exeInfo.getTaskUrl();
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取执行人任务出错", e);
		}
		return task;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getBean(String beanName) {
		if (null == context) {
			throw new NullPointerException("spring 上下文对象未初始化，无法完成bean的查找！");
		}
		if (null != context) {
			Object obj = context.getBean(beanName);
			if (null != obj) {
				return (T) obj;
			}
		}
		return null;
	}
	
	private static ApplicationContext context = null;
	
	public ApplicationContext getApplicationContext() {
		return context;
	}
}
