package com.born.insurance.integration.bpm.user;

import java.lang.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.insurance.integration.bpm.info.menu.MenuInfo;
import com.born.insurance.integration.bpm.resources.SystemResourcesServiceProxy;
import com.born.insurance.integration.common.impl.ClientBaseSevice;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.integration.bpm.info.PermissionMuster;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("permissionService")
public class PermissionServiceImpl extends ClientBaseSevice implements PermissionService,
															InitializingBean {
	@Override
	public InsuranceBaseResult loadSystemPermission(String systemAlias) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		List<MenuInfo> permissionInfos = ShiroSessionUtils.getSessionLocal()
			.getUserMenuInfoListBySysAlias(systemAlias);
		if (permissionInfos == null) {
			SystemResourcesServiceProxy resourcesServiceProxy = new SystemResourcesServiceProxy(
				propertyConfigService.getBmpServiceSystemResourcesService());
			try {
				List resourcesList = null;
				try {
					logger.info("加载菜单开始-----------"
								+ DateUtil.getFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					String resourcesJson = resourcesServiceProxy.getAllResByAccount("BusinessSys",
						ShiroSessionUtils.getSessionLocal().getUserName());
					HashMap<String, Object> map = MiscUtil.parseJSON(resourcesJson);
					resourcesList = (List) map.get("resourcesList");
					List<MenuInfo> menuInfoList = Lists.newArrayList();
					Map<String, List<MenuInfo>> userMenuInfoTreeMap = new HashMap<String, List<MenuInfo>>();
					if (resourcesList != null) {
						ShiroSessionUtils.getSessionLocal().setUserMenuInfoListMap(
							loadTreeMenu(resourcesList, userMenuInfoTreeMap, menuInfoList));
						ShiroSessionUtils.getSessionLocal().setUserMenuInfoTreeMap(
							userMenuInfoTreeMap);
					}
					logger.info("加载完成开始-----------"
								+ DateUtil.getFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
				} catch (RemoteException e) {
					// TODO: handle exception
					logger.error(e.getMessage(), e);
					baseResult.setMessage(e.getMessage());
				}
				
			} catch (java.lang.Exception e) {
				logger.error(e.getMessage(), e);
				baseResult.setMessage(e.getMessage());
			}
			return baseResult;
		} else {
			baseResult.setSuccess(true);
			return baseResult;
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private List<MenuInfo> loadTreeMenu(List resourcesList,
										Map<String, List<MenuInfo>> userMenuInfoTreeMap,
										List<MenuInfo> menuInfoList) {
		
		Map<String, List<MenuInfo>> userMenuInfoListMap = new HashMap<String, List<MenuInfo>>();
		Map<Long, PermissionMuster> menuTreeMap = new HashMap<Long, PermissionMuster>();
		List<MenuInfo> otherList = Lists.newArrayList();
		long depth = 0;
		for (Object res : resourcesList) {
			Map data = (Map) res;
			MenuInfo menuInfo = new MenuInfo();
			menuInfo.setAlias((String) data.get("alias"));
			menuInfo.setIconName((String) data.get("icon"));
			menuInfo.setMainName((String) data.get("resName"));
			menuInfo.setRank((long) data.get("sn"));
			menuInfo.setUrl((String) data.get("defaultUrl"));
			menuInfo.setResId((Long) data.get("resId"));
			menuInfo.setViewMenu("1".equals(String.valueOf(data.get("isDisplayInMenu"))));
			Long parentId = (Long) data.get("parentId");
			
			menuInfo.setParentId(parentId);
			menuInfoList.add(menuInfo);
			if (menuInfo.getParentId() == 0) {
				menuInfo.setTopAlias(menuInfo.getAlias());
				List<MenuInfo> menuInfoListList = Lists.newArrayList();
				List<MenuInfo> menuInfoTreeList = Lists.newArrayList();
				if (menuInfo.isViewMenu()) {
					menuInfoListList.add(menuInfo);
				}
				userMenuInfoListMap.put(menuInfo.getAlias(), menuInfoListList);
				userMenuInfoTreeMap.put(menuInfo.getAlias(), menuInfoTreeList);
				PermissionMuster permissionMuster = new PermissionMuster();
				permissionMuster.setParentAlias(menuInfo.getAlias());
				permissionMuster.setParentId(menuInfo.getParentId());
				permissionMuster.setPermissionInfo(menuInfo);
				permissionMuster.setRoot(true);
				menuTreeMap.put(menuInfo.getResId(), permissionMuster);
			} else {
				addTreeItem(userMenuInfoListMap, userMenuInfoTreeMap, menuTreeMap, otherList,
					menuInfo, parentId);
				
			}
			
		}
		if (ListUtil.isNotEmpty(otherList)) {
			addMenuTreeAll(userMenuInfoListMap, userMenuInfoTreeMap, menuTreeMap, otherList,
				depth + 1);
		}
		return menuInfoList;
	}
	
	private void addTreeItem(Map<String, List<MenuInfo>> userMenuInfoListMap,
								Map<String, List<MenuInfo>> userMenuInfoTreeMap,
								Map<Long, PermissionMuster> menuTreeMap, List<MenuInfo> otherList,
								MenuInfo menuInfo, Long parentId) {
		PermissionMuster permissionMusterChild = new PermissionMuster();
		permissionMusterChild.setParentAlias(menuInfo.getAlias());
		permissionMusterChild.setParentId(menuInfo.getParentId());
		permissionMusterChild.setPermissionInfo(menuInfo);
		permissionMusterChild.setRoot(false);
		
		PermissionMuster permissionMuster = menuTreeMap.get(parentId);
		if (permissionMuster == null) {
			otherList.add(menuInfo);
			return;
		}
		menuTreeMap.put(menuInfo.getResId(), permissionMusterChild);
		menuInfo.setTopAlias(permissionMuster.getPermissionInfo().getTopAlias());
		if (permissionMuster.isRoot()) {
			List<MenuInfo> menuInfoList = userMenuInfoListMap
				.get(permissionMuster.getParentAlias());
			if (menuInfoList == null) {
				otherList.add(menuInfo);
				return;
			} else {
				List<MenuInfo> menuInfoTreeList = userMenuInfoTreeMap.get(permissionMuster
					.getParentAlias());
				if (menuInfo.isViewMenu())
					menuInfoTreeList.add(menuInfo);
				menuInfoList.add(menuInfo);
				permissionMuster.getPermissionInfo().getSubclass().add(menuInfo);
			}
		} else {
			
			if (!menuInfo.isViewMenu())
				return;
			
			if ("systemMg".equals(permissionMuster.getPermissionInfo().getTopAlias())) {
				if (StringUtil.isNotBlank(menuInfo.getUrl())) {
					if (!menuInfo.getUrl().startsWith("/systemMg")
						&& !menuInfo.getUrl().startsWith("/js") &&!menuInfo.getUrl().contains("insurance")) {
						menuInfo.setUrl("/JumpTrust/gotobpm.htm?url=" + menuInfo.getUrl());
					}
				}
			}
			permissionMuster.getPermissionInfo().getSubclass().add(menuInfo);
		}
	}
	
	private void addMenuTreeAll(Map<String, List<MenuInfo>> userMenuInfoListMap,
								Map<String, List<MenuInfo>> userMenuInfoTreeMap,
								Map<Long, PermissionMuster> menuTreeMap, List<MenuInfo> menuInfos,
								long depth) {
		logger.info("depth:" + depth);
		if (depth > 30)
			return;
		List<MenuInfo> otherList = Lists.newArrayList();
		for (MenuInfo info : menuInfos) {
			addTreeItem(userMenuInfoListMap, userMenuInfoTreeMap, menuTreeMap, otherList, info,
				info.getParentId());
		}
		if (ListUtil.isNotEmpty(otherList)) {
			if (otherList.size() == menuInfos.size())
				return;
			addMenuTreeAll(userMenuInfoListMap, userMenuInfoTreeMap, menuTreeMap, otherList,
				depth + 1);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws java.lang.Exception {
		PermissionUtil.init(this);
	}
}
