package com.born.insurance.integration.bpm.user;

import java.lang.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.integration.bpm.user.SysUser;
import com.born.insurance.integration.bpm.user.UserDetailsServiceProxy;
import com.born.insurance.integration.common.PropertyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.insurance.util.MiscUtil;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.ws.enums.bpm.OrgTypeEnum;
import com.born.insurance.ws.info.bpm.Job;
import com.born.insurance.ws.info.bpm.Org;
import com.born.insurance.ws.info.bpm.Position;
import com.born.insurance.ws.info.bpm.Role;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("bpmUserQueryService")
public class BpmUserQueryServiceImpl implements BpmUserQueryService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PropertyConfigService propertyConfigService;
	
	@Override
	public List<SysUser> findUserByDeptId(String deptId) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByDeptId(deptId);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (java.lang.Exception e) {
			logger.info("根据部门ID查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public List<SysUser> findUserByDeptCode(String deptCode) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByDeptCode(deptCode);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (java.lang.Exception e) {
			logger.info("根据部门编号查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public List<SysUser> findUserByJobCode(String jobCode) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByJobCode(jobCode);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (java.lang.Exception e) {
			logger.info("根据岗位编码查询用户集出错 {}", e);
			return null;
		}
	}
	
	@Override
	public List<SysUser> findUserByRoleAlias(String roleAlias) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		
		try {
			String result = serviceProxy.findUserByRoleAlias(roleAlias);
			List<SysUser> userList = Lists.newArrayList();
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUserList(userList, resultMap);
		} catch (java.lang.Exception e) {
			logger.info("根据角色别名查询用户集出错 {}", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<SysUser> makeUserList(List<SysUser> userList, HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			List<Map<String, Object>> sysUserMapList = (List<Map<String, Object>>) resultMap
				.get("sysUsers");
			
			for (Map<String, Object> sysUserMap : sysUserMapList) {
				if (sysUserMap == null || sysUserMap.size() == 0)
					continue;
				SysUser sysUser = new SysUser();
				sysUser.setAccount(String.valueOf(sysUserMap.get("account")));
				sysUser.setEmail(String.valueOf(sysUserMap.get("email")));
				sysUser.setIsLock(Short.valueOf(String.valueOf(sysUserMap.get("isLock"))));
				sysUser.setStatus(Short.valueOf(String.valueOf(sysUserMap.get("status"))));
				sysUser.setMobile(String.valueOf(sysUserMap.get("mobile")));
				sysUser.setFullname(String.valueOf(sysUserMap.get("fullname")));
				sysUser.setUserId(Long.valueOf(String.valueOf(sysUserMap.get("userId"))));
				sysUser.setSex(String.valueOf(sysUserMap.get("sex")));
				userList.add(sysUser);
			}
			
			return userList;
		} else {
			return userList;
		}
	}
	
	@SuppressWarnings("unchecked")
	private SysUser makeUser(HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			Map<String, Object> sysUserMap = (Map<String, Object>) resultMap.get("sysUsers");
			SysUser sysUser = new SysUser();
			sysUser.setAccount(String.valueOf(sysUserMap.get("account")));
			sysUser.setEmail(String.valueOf(sysUserMap.get("email")));
			sysUser.setIsLock(Short.valueOf(String.valueOf(sysUserMap.get("isLock"))));
			sysUser.setStatus(Short.valueOf(String.valueOf(sysUserMap.get("status"))));
			sysUser.setMobile(String.valueOf(sysUserMap.get("mobile")));
			sysUser.setFullname(String.valueOf(sysUserMap.get("fullname")));
			sysUser.setUserId(Long.valueOf(String.valueOf(sysUserMap.get("userId"))));
			sysUser.setSex(String.valueOf(sysUserMap.get("sex")));
			
			return sysUser;
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser findUserByUserId(long userId) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			String result = serviceProxy.findUserByUserId(userId);
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			return makeUser(resultMap);
		} catch (java.lang.Exception e) {
			logger.info("根据用户ID查询用户出错 userId {} ， {}", userId, e);
			return null;
		}
	}
	
	@Override
	public SysUser findUserByAccount(String account) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			return serviceProxy.loadUserByUsername(account);
		} catch (java.lang.Exception e) {
			logger.info("根据用户账号查询用户出错 userAccount {} ， {}", account, e);
			return null;
		}
	}
	
	@Override
	public Org findDeptById(long deptId) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			String result = serviceProxy.findDeptInfoByDeptId(String.valueOf(deptId));
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			Org org = makeOrg(resultMap);
			logger.info("Org:{}", org);
			return org;
		} catch (java.lang.Exception e) {
			logger.info("根据部门ID查询部门出错 deptId {} ， {}", deptId, e);
			return null;
		}
	}
	
	@Override
	public Org findDeptByOrgId(long orgId) {
		
		try {
			Org org = findDeptById(orgId);
			if (org == null)
				return null;
			
			if (org.getType() == OrgTypeEnum.TEAM && org.getParentId() > 0) {
				return findDeptByOrgId(org.getParentId());
			}
			
			return org;
		} catch (java.lang.Exception e) {
			logger.error("根据组织ID查询部门出错{}", e);
		}
		return null;
	}
	
	@Override
	public Org findDeptByOrgCode(String orgCode) {
		try {
			Org org = findDeptByCode(orgCode);
			if (org == null)
				return null;
			
			if (org.getType() == OrgTypeEnum.TEAM && org.getParentId() > 0) {
				return findDeptByOrgCode(findDeptById(org.getParentId()).getCode());
			}
			
			return org;
		} catch (java.lang.Exception e) {
			logger.error("根据组织编码查询部门出错{}", e);
		}
		return null;
	}
	
	@Override
	public Org findDeptByCode(String deptCode) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			String result = serviceProxy.findDeptInfoByDeptCode(deptCode);
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			Org org = makeOrg(resultMap);
			logger.info("Org:{}", org);
			return org;
		} catch (java.lang.Exception e) {
			logger.info("根据部门编码查询部门出错 deptCode {} ， {}", deptCode, e);
			return null;
		}
	}
	
	/**
	 * 解析组织
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Org makeOrg(HashMap<String, Object> resultMap) {
		if ("1".equals(String.valueOf(resultMap.get("result")))) {
			Map<String, Object> orgMap = (Map<String, Object>) resultMap.get("sysOrg");
			Org org = new Org();
			org.setCode((String) orgMap.get("code"));
			org.setId(NumberUtil.parseLong(String.valueOf(orgMap.get("orgId"))));
			org.setName((String) orgMap.get("orgName"));
			org.setPath((String) orgMap.get("path"));
			org.setPathName((String) orgMap.get("orgPathname"));
			org.setType(OrgTypeEnum.getByValue(String.valueOf(orgMap.get("orgType"))));
			return org;
		} else {
			return null;
		}
	}
	
	@Override
	public UserDetailInfo findUserDetailByUserId(long userId) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			
			UserDetailInfo user = new UserDetailInfo();
			String result = serviceProxy.findUserByUserId(userId);
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			SysUser sysUser = makeUser(resultMap);
			user.setId(sysUser.getUserId());
			user.setAccount(sysUser.getAccount());
			user.setName(sysUser.getFullname());
			user.setEmail(sysUser.getEmail());
			user.setMobile(sysUser.getMobile());
			
			//查询其他详细信息
			result = serviceProxy.findUserRelatedInfoByUserId(userId);
			resultMap = MiscUtil.parseJSON(result);
			
			UserDetailInfo userDetail = makeUser(user, resultMap);
			logger.info("UserDetailInfo:{}", userDetail);
			return userDetail;
		} catch (java.lang.Exception e) {
			logger.info("根据用户ID查询用户详细信息出错 userId {} ， {}", userId, e);
			return null;
		}
	}
	
	@Override
	public UserDetailInfo findUserDetailByAccount(String userAccount) {
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			
			UserDetailInfo user = new UserDetailInfo();
			SysUser sysUser = serviceProxy.loadUserByUsername(userAccount);
			user.setId(sysUser.getUserId());
			user.setAccount(sysUser.getAccount());
			user.setName(sysUser.getFullname());
			user.setEmail(sysUser.getEmail());
			user.setMobile(sysUser.getMobile());
			
			//查询其他详细信息
			String result = serviceProxy.findUserRelatedInfoByUserId(user.getId());
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			UserDetailInfo userDetail = makeUser(user, resultMap);
			logger.info("UserDetailInfo:{}", userDetail);
			return userDetail;
		} catch (java.lang.Exception e) {
			logger.info("根据用户账号查询用户详细信息出错 userAccount {} ， {}", userAccount, e);
			return null;
		}
	}
	
	/**
	 * 解析用户相关信息
	 * @param user
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static UserDetailInfo makeUser(UserDetailInfo user, HashMap<String, Object> resultMap) {
		//所有角色
		List<Role> roleList = Lists.newArrayList();
		List<HashMap<String, Object>> roleMaps = (List<HashMap<String, Object>>) resultMap
			.get("roles");
		if (roleMaps != null) {
			for (HashMap<String, Object> roleMap : roleMaps) {
				for (String code : roleMap.keySet()) {
					Role role = new Role();
					role.setCode(code);
					role.setName((String) roleMap.get(code));
					roleList.add(role);
				}
			}
		}
		//设置角色列表
		user.setRoleList(roleList);
		
		//所有组织去重
		HashMap<Long, Org> orgs = Maps.newHashMap();
		//组织1
		List<Map<String, Object>> sysOrg = Lists.newArrayList();
		try {
			sysOrg = (List<Map<String, Object>>) resultMap.get("sysOrg");
		} catch (java.lang.Exception e) {
			sysOrg.add((Map<String, Object>) resultMap.get("sysOrg"));
		}
		if (sysOrg != null) {
			for (Map<String, Object> orgMap : sysOrg) {
				Org org = new Org();
				org.setCode((String) orgMap.get("code"));
				org.setId(NumberUtil.parseLong(String.valueOf(orgMap.get("orgId"))));
				org.setName((String) orgMap.get("orgName"));
				org.setPath((String) orgMap.get("path"));
				org.setPathName((String) orgMap.get("orgPathname"));
				org.setType(OrgTypeEnum.getByValue(String.valueOf(orgMap.get("orgType"))));
				if (org.getId() > 0) {
					orgs.put(org.getId(), org);
				}
			}
		}
		
		//组织2
		List<HashMap<String, Object>> deptOrg = (List<HashMap<String, Object>>) resultMap
			.get("deptOrg");
		if (deptOrg != null) {
			for (HashMap<String, Object> orgMap : deptOrg) {
				Org org = new Org();
				org.setCode((String) orgMap.get("code"));
				org.setId(NumberUtil.parseLong(String.valueOf(orgMap.get("orgId"))));
				org.setName((String) orgMap.get("orgName"));
				org.setPath((String) orgMap.get("path"));
				org.setPathName((String) orgMap.get("orgPathname"));
				org.setType(OrgTypeEnum.getByValue(String.valueOf(orgMap.get("orgType"))));
				if (org.getId() > 0) {
					orgs.put(org.getId(), org);
				}
			}
		}
		
		//组织3
		List<HashMap<String, Object>> otherOrg = (List<HashMap<String, Object>>) resultMap
			.get("otherOrg");
		if (otherOrg != null) {
			for (HashMap<String, Object> orgMap : otherOrg) {
				Org org = new Org();
				org.setCode((String) orgMap.get("code"));
				org.setId(NumberUtil.parseLong(String.valueOf(orgMap.get("orgId"))));
				org.setName((String) orgMap.get("orgName"));
				org.setPath((String) orgMap.get("path"));
				org.setPathName((String) orgMap.get("orgPathname"));
				org.setType(OrgTypeEnum.getByValue(String.valueOf(orgMap.get("orgType"))));
				if (org.getId() > 0) {
					orgs.put(org.getId(), org);
				}
			}
		}
		
		//主组织
		HashMap<String, Object> primaryOrg = (HashMap<String, Object>) resultMap.get("primaryOrg");
		if (primaryOrg != null) {
			Org org = new Org();
			org.setCode((String) primaryOrg.get("code"));
			org.setId(NumberUtil.parseLong(String.valueOf(primaryOrg.get("orgId"))));
			org.setName((String) primaryOrg.get("orgName"));
			org.setPath((String) primaryOrg.get("path"));
			org.setPathName((String) primaryOrg.get("orgPathname"));
			org.setType(OrgTypeEnum.getByValue(String.valueOf(primaryOrg.get("orgType"))));
			if (org.getId() > 0) {
				orgs.put(org.getId(), org);
				//设置主组织
				user.setPrimaryOrg(org);
			}
		}
		
		//所有职位去重
		Map<Long, Job> jobs = Maps.newHashMap();
		List<HashMap<String, Object>> jobMaps = (List<HashMap<String, Object>>) resultMap
			.get("jobList");
		if (jobMaps != null) {
			for (HashMap<String, Object> jobMap : jobMaps) {
				Job job = new Job();
				job.setId(NumberUtil.parseLong(String.valueOf(jobMap.get("jobid"))));
				job.setCode((String) jobMap.get("jobcode"));
				job.setName((String) jobMap.get("jobname"));
				if (job.getId() > 0) {
					jobs.put(job.getId(), job);
				}
			}
		}
		
		//所有岗位去重
		Map<Long, Position> positions = Maps.newHashMap();
		List<HashMap<String, Object>> posMaps = (List<HashMap<String, Object>>) resultMap
			.get("posList");
		if (posMaps != null) {
			for (HashMap<String, Object> posMap : posMaps) {
				Position pos = new Position();
				pos.setId(NumberUtil.parseLong(String.valueOf(posMap.get("posId"))));
				pos.setJobId(NumberUtil.parseLong(String.valueOf(posMap.get("jobId"))));
				pos.setOrgId(NumberUtil.parseLong(String.valueOf(posMap.get("orgId"))));
				pos.setName((String) posMap.get("posName"));
				pos.setPrimary("1".equals(String.valueOf(posMap.get("isPrimary"))));
				Org org = orgs.get(pos.getOrgId());
				Job job = jobs.get(pos.getJobId());
				if (org != null && job != null) {
					pos.setCode(org.getCode().toLowerCase() + "_" + job.getCode().toLowerCase());
				}
				if (pos.getId() > 0) {
					positions.put(pos.getId(), pos);
					if (pos.isPrimary()) {
						//设置主岗位
						user.setPrimaryPos(pos);
					}
				}
			}
		}
		
		//添加职位列表
		List<Job> jobList = Lists.newArrayList();
		for (Long jobId : jobs.keySet()) {
			jobList.add(jobs.get(jobId));
		}
		user.setJobList(jobList);
		
		//添加岗位列表
		List<Position> posList = Lists.newArrayList();
		for (Long posId : positions.keySet()) {
			posList.add(positions.get(posId));
		}
		user.setPosList(posList);
		
		//添加所有组织列表
		List<Org> orgList = Lists.newArrayList();
		for (Long orgId : orgs.keySet()) {
			orgList.add(orgs.get(orgId));
		}
		//设置各种组织
		setOrgList(user, orgList);
		
		return user;
	}
	
	/**
	 * 设置组织列表
	 * @param user
	 * @param orgList
	 */
	private static void setOrgList(UserDetailInfo user, List<Org> orgList) {
		user.setOrgList(orgList);
		List<Org> groupList = Lists.newArrayList();
		List<Org> firmList = Lists.newArrayList();
		List<Org> deptList = Lists.newArrayList();
		List<Org> teamList = Lists.newArrayList();
		List<Org> otherList = Lists.newArrayList();
		List<Long> deptIds = Lists.newArrayList();
		Set<Long> belongDeptIds = new HashSet<Long>();
		Org pOrg = user.getPrimaryOrg();
		for (Org org : orgList) {
			if (pOrg != null && pOrg.getId() == org.getId())
				org.setPrimary(true);
			if (org.getType() == OrgTypeEnum.GROUP) {
				groupList.add(org);
			} else if (org.getType() == OrgTypeEnum.FIRM) {
				firmList.add(org);
			} else if (org.getType() == OrgTypeEnum.DEPT) {
				deptList.add(org);
			} else if (org.getType() == OrgTypeEnum.TEAM) {
				teamList.add(org);
			} else {
				otherList.add(org);
			}
			deptIds.add(org.getId());
			if (StringUtil.isNotEmpty(org.getPath())) {
				String[] ids = org.getPath().split("\\.");
				for (String id : ids) {
					if (StringUtil.isNotBlank(id)) {
						belongDeptIds.add(NumberUtil.parseLong(id));
					}
				}
			}
		}
		user.setGroupList(groupList);
		user.setFirmList(firmList);
		user.setDeptList(deptList);
		user.setTeamList(teamList);
		user.setOtherList(otherList);
		user.setDeptIds(deptIds);
		user.setBelongDeptIds(belongDeptIds);
	}
}
