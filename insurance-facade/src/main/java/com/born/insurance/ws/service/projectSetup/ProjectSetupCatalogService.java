package com.born.insurance.ws.service.projectSetup;

import java.util.List;

import com.born.insurance.ws.info.projectSetup.ProjectSetupCatalogInfo;
import com.born.insurance.ws.order.projectSetup.ProjectSetupCataLogQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jenny
 *
 */
public interface ProjectSetupCatalogService {
	/**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<ProjectSetupCatalogInfo> queryList(ProjectSetupCataLogQueryOrder queryOrder);
    
    
    /**
     * 根据id查询记录列表
     * @param projectSetupId
     * @return
     */
   public  List<ProjectSetupCatalogInfo> findList(long projectSetupId);
}
