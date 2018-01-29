package com.born.insurance.web.util;

import java.util.List;

import com.born.insurance.biz.service.common.SysParameterService;
import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.SysParamEnum;
import com.born.insurance.ws.info.bpm.Org;
import com.born.insurance.ws.info.bpm.Role;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.order.common.FormRelatedUserQueryOrder;
import com.born.insurance.ws.service.common.FormRelatedUserService;
import com.born.insurance.ws.service.common.FormService;
import rop.thirdparty.com.google.common.collect.Lists;

import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 数据权限工具
 * @author wuzj 2016年5月3日
 */
public class DataPermissionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DataPermissionUtil.class);
	
	private static FormRelatedUserService formRelatedUserServiceClient;
	
	private static SysParameterService sysParameterService;
	
	private static BpmUserQueryService bpmUserQueryService;
	

	
	//private static ProjectService projectServiceClient;
	
	static {
		formRelatedUserServiceClient = SpringUtil.getBean("formRelatedUserService");
		sysParameterService = SpringUtil.getBean("sysParameterService");
		bpmUserQueryService = SpringUtil.getBean("bpmUserQueryService");
	}
	
	/**
	 * 是否有表单查询权限
	 * @param formId
	 * @return
	 */
	public static boolean hasViewPermission(long formId) {
		boolean hasPermission = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			if (sessionLocal != null) {
				FormRelatedUserQueryOrder order = new FormRelatedUserQueryOrder();
				order.setFormId(formId);
				if (hasAllDataPermission()) { //拥有所有权限
					return true;
				} else if (hasPrincipalDataPermission()) { //拥有部门级别的权限
					UserInfo userInfo = sessionLocal.getUserInfo();
					if (userInfo == null) {
						return false;
					} else {
						order.setUserId(0);
						order.setDeptIdList(userInfo.getDeptIdList());
					}
				} else { //个人权限
					order.setUserId(sessionLocal.getUserId());
					order.setDeptIdList(null);
				}
				long count = formRelatedUserServiceClient.queryCount(order);
				if (count > 0) {
					hasPermission = true;
				}
			}
		} catch (Exception e) {
			logger.error("检查数据权限出错{}", e);
		}
		
		return hasPermission;
	}
	
	/**
	 * 是否拥有所有数据权限
	 * @return
	 */
	public static boolean hasAllDataPermission() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		UserInfo userInfo = sessionLocal.getUserInfo();
		boolean is = false;
		if (userInfo != null) {
			List<String> roles = userInfo.getRoleAliasList();
			if (ListUtil.isNotEmpty(roles)) {
				String roleName = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_ALL_DATA_PERMISSION_ROLE_NAME
						.code());
				if (StringUtil.isNotBlank(roleName)) {
					roleName = roleName.replaceAll("\r", "").replaceAll("\n", "")
						.replaceAll("，", ",").trim();
					String[] dpRoles = roleName.split(",");
					for (String role : roles) {
						
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						
						if (is)
							break;
						
						role = role.replaceAll("BusinessSys_", "");
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						if (is)
							break;
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 是拥有所负责数据权限
	 * @return
	 */
	public static boolean hasPrincipalDataPermission() {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		UserInfo userInfo = sessionLocal.getUserInfo();
		boolean is = false;
		if (userInfo != null) {
			List<String> roles = userInfo.getRoleAliasList();
			if (ListUtil.isNotEmpty(roles)) {
				String roleName = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_PRINCIPAL_DATA_PERMISSION_ROLE_NAME
						.code());
				if (StringUtil.isNotBlank(roleName)) {
					roleName = roleName.replaceAll("\r", "").replaceAll("\n", "")
						.replaceAll("，", ",").trim();
					String[] dpRoles = roleName.split(",");
					for (String role : roles) {
						
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						
						if (is)
							break;
						
						role = role.replaceAll("BusinessSys_", "");
						for (String dprole : dpRoles) {
							if (role.equals(dprole)) {
								is = true;
								break;
							}
						}
						if (is)
							break;
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 是否系统管理员
	 * @return
	 */
	public static boolean isSystemAdministrator() {
		String roleName = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_SYSTEM_ADMINISTRATOR_ROLE_NAME.code());
		return hasRole(roleName);
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasRole(String... roleAlias) {
		
		boolean is = false;
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null && roleAlias != null) {
			
			List<String> roleAliasList = Lists.newArrayList();
			//兼容传的角色为多个,分开的情况
			for (String r : roleAlias) {
				String[] rArr = r.split(",");
				for (String ra : rArr) {
					if (StringUtil.isNotBlank(ra))
						roleAliasList.add(ra);
				}
			}
			
			UserDetailInfo userDetail = sessionLocal.getUserDetailInfo();
			if (userDetail != null) {
				List<Role> roleList = userDetail.getRoleList();
				for (Role role : roleList) {
					for (String ra : roleAliasList) {
						String roleCode = role.getCode();
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
						//替换掉系统前缀
						roleCode = roleCode.replaceAll("BusinessSys_", "").replaceAll("bpm_", "");
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
					}
					if (is)
						break;
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param userId
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasRole(long userId, String... roleAlias) {
		return hasRole(bpmUserQueryService.findUserDetailByUserId(userId), roleAlias);
	}
	
	/**
	 * 判断人员是否有相关角色
	 * @param userDetail
	 * @param roleAlias
	 * @return
	 */
	public static boolean hasRole(UserDetailInfo userDetail, String... roleAlias) {
		
		boolean is = false;
		if (userDetail != null && roleAlias != null) {
			
			if (userDetail != null) {
				
				List<String> roleAliasList = Lists.newArrayList();
				//兼容传的角色为多个,分开的情况
				for (String r : roleAlias) {
					String[] rArr = r.split(",");
					for (String ra : rArr) {
						if (StringUtil.isNotBlank(ra))
							roleAliasList.add(ra);
					}
				}
				
				List<Role> roleList = userDetail.getRoleList();
				for (Role role : roleList) {
					for (String ra : roleAliasList) {
						String roleCode = role.getCode();
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
						//替换掉系统前缀
						roleCode = roleCode.replaceAll("BusinessSys_", "").replaceAll("bpm_", "");
						if (StringUtil.equals(roleCode, ra)) {
							is = true;
						}
						if (is)
							break;
					}
					if (is)
						break;
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断部门Id和当前登陆用户是否同一部门
	 * 
	 * */
	public static Boolean isOneDep(Long depId) {
		if (depId == null || depId == 0)
			return false;
		UserDetailInfo userDetail = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
		Org org = bpmUserQueryService.findDeptByOrgId(depId);
		if (userDetail.isBelong2Dept(org.getId()))
			return true;
		return false;
		
	}
	
}
