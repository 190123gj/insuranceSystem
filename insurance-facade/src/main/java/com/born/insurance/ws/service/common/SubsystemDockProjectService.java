package com.born.insurance.ws.service.common;

import javax.jws.WebService;

import com.born.insurance.ws.info.common.SubsystemDockProjectInfo;
import com.born.insurance.ws.order.common.SubsystemDockProjectOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

/**
 * 子系统对接项目信息service
 * 
 * @author Ji
 * 
 */
@WebService
public interface SubsystemDockProjectService {
	
	/**
	 * 根据项目编号和对应表单类型
	 * @param projectCode 项目编号
	 * @param dockFormType 对应表单类型
	 * @return
	 */
	SubsystemDockProjectInfo findByProjectCodeAndDockFormType(String projectCode,
															  String dockFormType);
	
	/**
	 * 根据项目编号和对应表单类型 删除
	 * @param projectCode 项目编号
	 * @param dockFormType 对应表单类型
	 * @return
	 */
	int deleteByProjectCodeAndDockFormType(String projectCode, String dockFormType);
	
	/**
	 * 保存子系统对接项目信息
	 * @param order
	 * @return
	 */
	InsuranceBaseResult save(SubsystemDockProjectOrder order);
	
}
