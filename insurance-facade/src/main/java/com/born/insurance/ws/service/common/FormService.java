package com.born.insurance.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.bpm.WorkflowTaskInfo;
import com.born.insurance.ws.order.common.CancelFormOrder;
import com.born.insurance.ws.order.common.FormQueryOrder;
import com.born.insurance.ws.order.common.SimpleFormAuditOrder;
import com.born.insurance.ws.order.common.SimpleFormOrder;
import com.born.insurance.ws.order.common.TaskAssignFormOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

@WebService
public interface FormService {
	
	/**
	 * 根据id查询表单信息
	 * @param formId
	 * @return
	 */
	FormInfo findByFormId(long formId);
	
	/**
	 * 更新表单信息
	 * @param formInfo
	 * @return
	 */
	InsuranceBaseResult updateForm(FormInfo formInfo);
	
	/**
	 * 查询表单
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormInfo> queryPage(FormQueryOrder order);
	
	/**
	 * 表单提交
	 * @param order
	 * @return
	 */
	FormBaseResult submit(SimpleFormOrder order);
	
	/**
	 * 删除表单
	 * @param order.userId 操作人
	 * @param order.formId 表单ID
	 * @return
	 */
	InsuranceBaseResult delete(SimpleFormOrder order);
	
	/**
	 * 撤销表单
	 * @param order.userId 操作人
	 * @param order.formId 表单ID
	 * @return
	 */
	InsuranceBaseResult cancel(CancelFormOrder order);
	
	/**
	 * 流程审核
	 * @param order
	 * @return
	 */
	InsuranceBaseResult auditProcess(SimpleFormAuditOrder order);
	
	/**
	 * 任务交办
	 * @param order
	 * @return
	 */
	InsuranceBaseResult taskAssign(TaskAssignFormOrder order);
	
	/**
	 * 手动结束流程
	 * @param order
	 * @return
	 */
	InsuranceBaseResult endWorkflow(SimpleFormAuditOrder order);
	
	/***
	 * @param userId
	 * @return
	 */
	List<WorkflowTaskInfo> backTaskList(long userId);
}
