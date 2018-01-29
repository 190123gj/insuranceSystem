package com.born.insurance.ws.service.demo;

import com.born.insurance.ws.info.demo.DemoInfo;
import com.born.insurance.ws.order.demo.DemoOrder;
import com.born.insurance.ws.order.demo.DemoQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

/**
 * Created by Administrator on 2016-11-18.
 */
public interface DemoService {
    /**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    FormBaseResult save(DemoOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    DemoInfo findById(long id);

    /**
     * 查询信息
     *
     * @param formId 表单id
     * @return 风险预警对象
     */
    DemoInfo findByFormId(long formId);

    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<DemoInfo> queryList(DemoQueryOrder queryOrder);
}
