package com.born.insurance.ws.service.common;

import javax.jws.WebService;

import com.born.insurance.ws.enums.ExeStatusEnum;
import com.born.insurance.ws.info.common.FormRelatedUserInfo;
import com.born.insurance.ws.order.common.FormRelatedUserOrder;
import com.born.insurance.ws.order.common.FormRelatedUserQueryOrder;
import com.born.insurance.ws.order.common.TaskAssignFormOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * 表单相关人员
 *
 * @author wuzj 2016年8月6日
 */
@WebService
public interface FormRelatedUserService {
	
	/**
	 * 分页查询相关人员
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormRelatedUserInfo> queryPage(FormRelatedUserQueryOrder order);
	
	/**
	 * 统计数量
	 * @param order
	 * @return
	 */
	long queryCount(FormRelatedUserQueryOrder order);
	
	/**
	 * 更新任务执行状态
	 * @param exeStatus
	 * @param taskOpinion
	 * @param remark
	 * @param taskId
	 * @return
	 */
	InsuranceBaseResult updateExeStatus(ExeStatusEnum exeStatus, String taskOpinion, String remark,
										long taskId, long userId);
	
	/**
	 * 更新提交人的状态
	 * @param exeStatus
	 * @param taskOpinion
	 * @param formId
	 * @return
	 */
	InsuranceBaseResult updateSubmitExeStatus(ExeStatusEnum exeStatus, String taskOpinion, long formId);
	
	/**
	 * 删掉未执行的任务
	 * @param formId
	 * @return
	 */
	InsuranceBaseResult deleteWaiting(long formId);
	
	/**
	 * 删掉不是执行人的候选人
	 * @param processTaskId
	 * @param processManId
	 * @return
	 */
	InsuranceBaseResult deleteNotExecutor(long processTaskId, long processManId);
	
	/**
	 * 设置相关人员
	 * @param order
	 * @return
	 */
	InsuranceBaseResult setRelatedUser(FormRelatedUserOrder order);
	
	/**
	 * 任务交办
	 */
	InsuranceBaseResult taskAssign(TaskAssignFormOrder order);
}
