package com.born.insurance.ws.service.projectSetup;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.projectSetup.ProjectSetupFormInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.order.projectSetup.ProjectSetupOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupQueryOrder;

public interface ProjectSetupService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
	FormBaseResult save(ProjectSetupOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    ProjectSetupInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<ProjectSetupFormInfo> queryList(ProjectSetupQueryOrder queryOrder);

	ProjectSetupInfo findByFormId(long formId);
	
	/**
	 * 删除超权限申请
	 * @param order
	 * @return
	 */
	InsuranceBaseResult deleteProjectSetup(ProjectSetupOrder order);

	/**
	 * 撤销超权限申请
	 * @param order
	 * @return
	 */
	InsuranceBaseResult revoke(ProjectSetupOrder order);
	
}	