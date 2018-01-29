package com.born.insurance.biz.service.bpm;

import java.util.List;

import com.born.insurance.integration.bpm.result.StartFlowResult;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.integration.result.WorkflowBatchProcessResult;
import com.born.insurance.ws.info.bpm.TaskTypeViewInfo;
import com.born.insurance.ws.info.bpm.WebNodeInfo;
import com.born.insurance.ws.order.WorkflowBatchProcessOrder;
import com.born.insurance.ws.order.bpm.BpmFinishTaskInfo;
import com.born.insurance.ws.order.bpm.TaskOpinion;
import com.born.insurance.ws.order.bpm.TaskSearchOrder;
import com.born.insurance.ws.order.bpm.WorkflowAssignOrder;
import com.born.insurance.ws.order.bpm.WorkflowDoNextOrder;
import com.born.insurance.ws.order.bpm.WorkflowProcessLog;
import com.born.insurance.ws.order.bpm.WorkflowRecoverOrder;
import com.born.insurance.ws.order.bpm.WorkflowStartOrder;
import com.born.insurance.ws.order.bpm.WorkflowTaskInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface WorkflowEngineClient {
	
	/**
	 * 启动流程
	 * @param startOrder
	 * @return
	 */
	StartFlowResult startFlow(WorkflowStartOrder startOrder);
	
	/**
	 * 获取任务列表
	 * @param runId
	 * @return
	 */
	List<WorkflowTaskInfo> getTaskList(String runId);
	
	/**
	 * 根据任务ID获取任务节点
	 * @param taskId
	 * @return
	 */
	WebNodeInfo getTaskNode(String taskId);
	
	/**
	 * 执行下一步
	 * @param doNextOrder
	 * @return
	 */
	WorkflowResult doNext(WorkflowDoNextOrder doNextOrder);
	
	/**
	 * 追回
	 * @param recoverOrder
	 * @return
	 */
	WorkflowResult defRecover(WorkflowRecoverOrder recoverOrder);
	
	/**
	 * 交办
	 * @param workflowAssignOrder
	 * @return
	 */
	WorkflowResult taskAssign(WorkflowAssignOrder workflowAssignOrder);
	
	/**
	 * 终止流程
	 * @param doNextOrder
	 * @return
	 */
	WorkflowResult endingWorkflow(WorkflowDoNextOrder doNextOrder);
	
	/**
	 * 获取任务信息
	 * @param taskId
	 * @return
	 */
	WorkflowTaskInfo getTaskInfo(String taskId);
	
	/**
	 * 获取已处理或当前任务的执行人
	 * @param strTaskId
	 * @return
	 */
	List<TaskOpinion> getTaskUsers(String strTaskId);
	
	/**
	 * 获取已处理或当前步骤执行人
	 * @param instanceId
	 * @return
	 */
	List<TaskOpinion> getTaskUsers(String instanceId, String nodeId);
	
	/**
	 * 获取代办任务
	 * @param taskSearchOrder
	 * @return
	 */
	QueryBaseBatchResult<WorkflowTaskInfo> getTasksByAccount(TaskSearchOrder taskSearchOrder);
	
	/**
	 * 查询审核历史
	 * @param actInstId
	 * @return
	 */
	QueryBaseBatchResult<WorkflowProcessLog> getProcessOpinionByActInstId(String actInstId);
	
	/**
	 * 查看任务
	 * @param actInstId
	 * @param taskId
	 * @return
	 */
	InsuranceBaseResult readTask(String actInstId, String taskId);
	
	/**
	 * 获取分类试图
	 * @param taskSearchOrder
	 * @return
	 */
	QueryBaseBatchResult<TaskTypeViewInfo> getTaskTypeView(TaskSearchOrder taskSearchOrder);
	
	/**
	 * 查询历史审核任务
	 * @param taskSearchOrder
	 * @return
	 */
	QueryBaseBatchResult<BpmFinishTaskInfo> getFinishTask(TaskSearchOrder taskSearchOrder);
	
	/**
	 * 批量审核
	 * @param order
	 * @return
	 */
	WorkflowBatchProcessResult batchProcess(WorkflowBatchProcessOrder order);
	
}
