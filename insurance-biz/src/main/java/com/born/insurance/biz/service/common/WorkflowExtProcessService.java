package com.born.insurance.biz.service.common;

import java.util.List;
import java.util.Map;

import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.common.SimpleUserInfo;
import com.born.insurance.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.common.FormNotifyAssginService;
import com.born.insurance.ws.service.common.FormNotifyNextService;
import com.born.insurance.ws.service.common.FormNotifyResultService;

public interface WorkflowExtProcessService {
	
	/**
	 * 流程变量
	 * @param formInfo
	 * @return
	 */
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo);
	
	/**
	 * 流程启动前处理
	 * @param formInfo
	 * @param workflowResult
	 */
	public InsuranceBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order);
	
	/**
	 * 启动流程后处理
	 * @param formInfo
	 * @param workflowResult
	 */
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult);
	
	/**
	 * 流程审核前处理
	 * @param formInfo
	 * @param workflowResult
	 */
	public InsuranceBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order);
	
	/**
	 * 流程审核后处理
	 * @param formInfo
	 * @param workflowResult
	 */
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult);
	
	/**
	 * 流程结束后处理
	 * @param formInfo
	 * @param workflowResult
	 */
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult);
	
	/**
	 * 手动结束流程后处理
	 * @param formInfo
	 * @param workflowResult
	 */
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult);
	
	/**
	 * 删除表单后处理
	 * @param formInfo
	 */
	public void deleteAfterProcess(FormInfo formInfo);
	
	/**
	 * 撤销表单后处理
	 * @param formInfo
	 */
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult);
	
	/**
	 * 提交人终止作废表单处理
	 * @param formInfo
	 */
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult);
	
	/**
	 * 获取结果通知人列表
	 * @param formInfo
	 * @return
	 */
	List<SimpleUserInfo> resultNotifyUserList(FormInfo formInfo);
	
	/**
	 * 获取消息变量值
	 * @param formInfo
	 * @return
	 */
	Map<String, String> makeMessageVar(FormInfo formInfo);
	
	/**
	 * 获取通知下步执行人实现
	 * @return
	 */
	FormNotifyNextService getNotifyNextService();
	
	/**
	 * 获取通知结果实现
	 * @return
	 */
	FormNotifyResultService getNotifyResultService();
	
	/**
	 * 通知交办人实现
	 * @return
	 */
	FormNotifyAssginService getNotifyAssignService();
}
