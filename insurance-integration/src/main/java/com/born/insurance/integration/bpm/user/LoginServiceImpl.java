package com.born.insurance.integration.bpm.user;

import java.lang.Exception;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.born.insurance.integration.bpm.token.LoginDataToken;
import com.born.insurance.integration.common.impl.ClientBaseSevice;
import com.born.insurance.integration.result.LoginResult;
import com.born.insurance.integration.util.SessionConstant;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.bpm.UserDetailInfo;
import com.born.insurance.ws.order.LoginOrder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;


import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

@Service("loginService")
public class LoginServiceImpl extends ClientBaseSevice implements LoginService {
	
	final static int USER_LOGIN_ERROR_TIME = 5;
	
	final static boolean SIMULATE_LOGIN_SUCCESS = false;
	
	@Override
	public LoginResult login(LoginOrder loginData) {
		LoginResult result = new LoginResult();
		
		try {
			Subject currentUser = SecurityUtils.getSubject();
			LoginDataToken token = new LoginDataToken(loginData);
			currentUser.login(token);
			result = (LoginResult) ShiroSessionUtils
				.removeSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT);
			return result;
			
		} catch (IncorrectCredentialsException e) {
			result = (LoginResult) ShiroSessionUtils
				.removeSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT);
			logger.error("登录失败，密码错误" + e.getMessage());
			result.setMessage(e.getMessage());
			
		} catch (AuthenticationException e) {
			result = (LoginResult) ShiroSessionUtils
				.removeSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT);
			// 抓去需要激活的第一次登录失败
			logger.error("登录失败:" + e.getMessage());
			result.setMessage(e.getMessage() + ",[密码错误五次后锁定帐号！]");
			
		} catch (java.lang.Exception e) {
			logger.error("登录认证失败，系统错误," + e.getMessage(), e);
			result.setMessage(e.getMessage());
			result = (LoginResult) ShiroSessionUtils
				.removeSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT);
		}
		return result;
	}

	@Override
	public LoginResult validateLoginInfo(LoginOrder userLoginOrder) {

		LoginResult loginResult = new LoginResult();
		UserInfo info = new UserInfo();
		info.setPassword("888888");
		info.setUserName("admin");
		info.setUserId(1);
		loginResult.setSuccess(true);
		loginResult.setUserInfo(info);
		if (SIMULATE_LOGIN_SUCCESS) {
			ShiroSessionUtils.setSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT,
				loginResult);
			return loginResult;

		}
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());

		try {
			String result = serviceProxy.login(userLoginOrder.getUserName(),
				userLoginOrder.getPassword(), userLoginOrder.getLoginIp(),
				userLoginOrder.getAgent());
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			if ("1".equals(String.valueOf(resultMap.get("result")))) {
				String lastLoginTime = (String) resultMap.get("lastLoginTime");
				Map<String, Object> sysUserMap = (Map<String, Object>) resultMap.get("sysUser");
				long userId = (Long) sysUserMap.get("userId");
				String account = (String) sysUserMap.get("account");
				String fullname = (String) sysUserMap.get("fullname");
				String email = (String) sysUserMap.get("email");
				String mobile = (String) sysUserMap.get("mobile");
				long status = (long) sysUserMap.get("status");
				List<Map<String, Object>> sysOrgMap = Lists.newArrayList();
				Map<String, Object> primaryOrg = null;
				try {
					sysOrgMap = (List<Map<String, Object>>) resultMap.get("sysOrg");
				} catch (Exception e) {
					sysOrgMap.add((Map<String, Object>) resultMap.get("sysOrg"));
				}
				primaryOrg = (Map<String, Object>) resultMap.get("primaryOrg");
				List<Map<String, Object>> deptMap = (List<Map<String, Object>>) resultMap
					.get("deptOrg");
				List<Map<String, Object>> otherOrgMap = (List<Map<String, Object>>) resultMap
					.get("otherOrg");
				Object rolesObject = resultMap.get("roles");
				List<Map<String, Object>> rolesList = Lists.newArrayList();
				if (rolesObject != null) {
					rolesList = (List<Map<String, Object>>) rolesObject;
					
				}
				SessionLocal local = new SessionLocal(userId, account, fullname);
				local.setRemoteAddr(userLoginOrder.getLoginIp());
				UserInfo userInfo = new UserInfo();
				userInfo.setMoblie(mobile);
				userInfo.setEmail(email);
				local.setPassword(userLoginOrder.getPassword());
				SysOrg orgs = getOrgValue(primaryOrg);
				userInfo.setPrimaryOrg(orgs);
				getOrgValue(sysOrgMap, userInfo, 1);
				getOrgValue(deptMap, userInfo, 2);
				getOrgValue(otherOrgMap, userInfo, 3);
				
				local.setUserInfo(userInfo);
				String roleName = "";
				String aliasName = "";
				
				for (Map<String, Object> rolesMap : rolesList) {
					Iterator<Entry<String, Object>> iterator = rolesMap.entrySet().iterator();
					if (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						if (roleName.length() == 0) {
							roleName = String.valueOf(entry.getValue());
							aliasName = String.valueOf(entry.getKey());
						} else {
							roleName += "," + String.valueOf(entry.getValue());
							aliasName += "," + String.valueOf(entry.getKey());
						}
						userInfo.getRoleAliasList().add(entry.getKey());
						userInfo.setRoleAlisName(aliasName);
					}
				}
				
				//新版用户详细信息 by wuzj @2016-08-16
				UserDetailInfo userDetail = new UserDetailInfo();
				userDetail.setId(userInfo.getUserId());
				userDetail.setName(userInfo.getRealName());
				userDetail.setAccount(userInfo.getUserName());
				userDetail.setMobile(userInfo.getMoblie());
				userDetail.setEmail(userInfo.getEmail());
				BpmUserQueryServiceImpl.makeUser(userDetail, resultMap);
				local.setUserDetailInfo(userDetail);
				
				local.setRolesName(roleName);
				loginResult.setSuccess(true);
				loginSuccess(info, loginResult, local);
				ShiroSessionUtils.setSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT,
					loginResult);
				
				return loginResult;
			} else if ("2".equals(String.valueOf(resultMap.get("result")))) {
				loginResult.setInsuranceResultEnum(InsuranceResultEnum.PASSWORD_ERROR);
				loginResult.setMessage(String.valueOf(resultMap.get("message")));
			} else if ("3".equals(String.valueOf(resultMap.get("result")))) {
				loginResult.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
				loginResult.setMessage("账户名或密码错误");
			} else if ("8".equals(String.valueOf(resultMap.get("result")))) {
				loginResult.setInsuranceResultEnum(InsuranceResultEnum.FUNCTION_NOT_OPEN);
				loginResult.setMessage(String.valueOf(resultMap.get("message")));
			} else {
				loginResult.setInsuranceResultEnum(InsuranceResultEnum.USER_DISABLE);
				loginResult.setMessage(String.valueOf(resultMap.get("message")));
			}
			loginResult.setSuccess(false);
			
		} catch (RemoteException e) {
			logger.info(e.getMessage(), e);
			loginResult.setSuccess(false);
			loginResult.setMessage("远程调用失败");
			return loginResult;
		}
		ShiroSessionUtils.setSessionValue(SessionConstant.SESSSION_KEY_LOGIN_RESULT, loginResult);
		return loginResult;
		
	}
	
	/**
	 * 
	 * @param sysOrgMapList
	 * @param userInfo
	 * @param type 1公司，2部门，3小组
	 * @return
	 */
	private void getOrgValue(List<Map<String, Object>> sysOrgMapList, UserInfo userInfo, int type) {
		if (ListUtil.isEmpty(sysOrgMapList)) {
			return;
		}
		List<SysOrg> orgsList = Lists.newArrayList();
		List<Long> orgsIdList = Lists.newArrayList();
		for (Map<String, Object> sysOrgMap : sysOrgMapList) {
			if (sysOrgMap == null || sysOrgMap.size() == 0)
				continue;
			SysOrg orgs = new SysOrg();
			orgs.setCode(String.valueOf(sysOrgMap.get("code")));
			orgs.setDepth(NumberUtil.parseInt(String.valueOf(sysOrgMap.get("depth"))));
			orgs.setOrgId(NumberUtil.parseLong(String.valueOf(sysOrgMap.get("orgId"))));
			orgs.setOrgPathname(String.valueOf(sysOrgMap.get("orgPathname")));
			orgs.setOrgType(NumberUtil.parseLong(String.valueOf(sysOrgMap.get("orgType"))));
			orgs.setOrgName(String.valueOf(sysOrgMap.get("orgName")));
			orgs.setPath(String.valueOf(sysOrgMap.get("path")));
			orgs.setIsParent(String.valueOf(sysOrgMap.get("path")));
			orgsList.add(orgs);
			orgsIdList.add(orgs.getOrgId());
		}
		if (type == 1) {
			userInfo.setOrgList(orgsList);
			userInfo.setOrgIdList(orgsIdList);
		} else if (type == 2) {
			userInfo.setDeptList(orgsList);
			userInfo.setDeptIdList(orgsIdList);
		} else if (type == 3) {
			userInfo.setOtherOrgList(orgsList);
			userInfo.setOtherOrgIdList(orgsIdList);
		}
		
	}
	
	private SysOrg getOrgValue(Map<String, Object> sysOrgMap) {
		if (sysOrgMap == null || sysOrgMap.size() == 0)
			return null;
		SysOrg orgs = new SysOrg();
		copyData(sysOrgMap, orgs);
		return orgs;
	}
	
	private void copyData(Map<String, Object> sysOrgMap, SysOrg orgs) {
		orgs.setCode(String.valueOf(sysOrgMap.get("code")));
		orgs.setDepth(NumberUtil.parseInt(String.valueOf(sysOrgMap.get("depth"))));
		orgs.setOrgId(NumberUtil.parseLong(String.valueOf(sysOrgMap.get("orgId"))));
		orgs.setOrgPathname(String.valueOf(sysOrgMap.get("orgPathname")));
		orgs.setOrgType(NumberUtil.parseLong(String.valueOf(sysOrgMap.get("orgType"))));
		orgs.setOrgName(String.valueOf(sysOrgMap.get("orgName")));
		orgs.setPath(String.valueOf(sysOrgMap.get("path")));
		orgs.setIsParent(String.valueOf(sysOrgMap.get("path")));
	}
	
	private void loginSuccess(UserInfo userInfo, LoginResult loginResult, SessionLocal local) {
		
		loginResult.setSuccess(true);
		Session shiroSession = SecurityUtils.getSubject().getSession();
		shiroSession.setAttribute("sessionLocal", local);
		ShiroSessionUtils.setSessionLocal(local);
	}
}
