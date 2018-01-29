package com.born.insurance.integration.bpm.user;

import java.util.List;


import com.born.insurance.ws.info.bpm.Org;
import com.born.insurance.ws.info.bpm.UserDetailInfo;

public interface BpmUserQueryService {
	
	/**
	 * 根据部门编号查询部门信息
	 * @param deptCode
	 * @return
	 */
	List<SysUser> findUserByDeptCode(String deptCode);
	
	/**
	 * 根据部门ID查询部门信息
	 * @param deptId
	 * @return
	 */
	List<SysUser> findUserByDeptId(String deptId);
	
	/**
	 * 根据角色别名查询用户信息
	 * @param roleAlias
	 * @return
	 */
	List<SysUser> findUserByRoleAlias(String roleAlias);
	
	/**
	 * 根据岗位编号查询用户
	 * @param jobCode
	 * @return
	 */
	List<SysUser> findUserByJobCode(String jobCode);
	
	/**
	 * 根据用户ID查询用户
	 * @param userId
	 * @return
	 */
	SysUser findUserByUserId(long userId);
	
	/**
	 * 根据用户账号查询用户
	 * @param account
	 * @return
	 */
	SysUser findUserByAccount(String account);
	
	/**
	 * 根据用户ID查询用户详细信息（组织、角色、职位、岗位）
	 * @param userId
	 * @return
	 */
	UserDetailInfo findUserDetailByUserId(long userId);
	
	/**
	 * 根据用户账号查询用户详细信息（组织、角色、职位、岗位）
	 * @param userId
	 * @return
	 */
	UserDetailInfo findUserDetailByAccount(String userAccount);
	
	/**
	 * 根据部门编号查询部门
	 * @param deptCode
	 * @return
	 */
	Org findDeptByCode(String deptCode);
	
	/**
	 * 根据部门ID查询部门
	 * @param deptId
	 * @return
	 */
	Org findDeptById(long deptId);
	
	/**
	 * 根据组织ID查询部门（如果是小组则返回上级部门，其他则返回自己）
	 * @param orgId
	 * @return
	 */
	Org findDeptByOrgId(long orgId);
	
	/**
	 * 根据组织编码查询部门（如果是小组则返回上级部门，其他则返回自己）
	 * @param orgId
	 * @return
	 */
	Org findDeptByOrgCode(String orgCode);
}
