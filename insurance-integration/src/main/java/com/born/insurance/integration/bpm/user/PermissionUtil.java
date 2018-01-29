package com.born.insurance.integration.bpm.user;

import com.born.insurance.integration.bpm.info.menu.MenuInfo;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PermissionUtil {
	
	private static final String[] excludeUrls = { "/uploadfile/", "/styles/", "/images/",
													"/resources/", "/anon/", "/test/", "/login/",
													"/help", "/upload/", "/staticFiles/",
													"/baseDataLoad/", "/favicon.ico", "/userHome/",
													"/JumpTrust/", "/insurance/form/",
													"/insurance/basicData/", "/insurance/common/",
													"/systemMg/message/user/",
													"/userHome/modifyPassword.htm",
												 };
	
	static PermissionService permissionStaticService;
	
	public static synchronized void init(PermissionService permissionService) {
		if (permissionStaticService == null) {
			permissionStaticService = permissionService;
		}
	}
	
	public static boolean checkPermission(String url) {
		
		if (StringUtil.isBlank(url)) {
			return true;
		}
		for (String item : excludeUrls) {
			if (url.startsWith(item)) {
				return true;
			}
		}

		String newUrl = "";
		if (url.indexOf("?") > 0) {
			newUrl = url.substring(0, url.indexOf("?"));
			if (newUrl.endsWith(".json")) {
				return true;
			}
		} else {
			if (url.endsWith(".json")) {
				return true;
			}
		}

		url = filterUrl(url);
		String[] stringSplit = url.split("/");
		if (stringSplit.length > 1) {
			List<MenuInfo> permissionInfos = ShiroSessionUtils.getSessionLocal()
				.getUserMenuInfoListBySysAlias(stringSplit[1]);
			if (permissionInfos == null) {
				permissionStaticService.loadSystemPermission(stringSplit[1]);
				permissionInfos = ShiroSessionUtils.getSessionLocal()
					.getUserMenuInfoListBySysAlias(stringSplit[1]);
			}
			return checkUrl(url, permissionInfos);
			
		} else
			return false;
	}
	
	public static boolean checkUrl(String url, List<MenuInfo> permissionInfos) {
		if (permissionInfos == null)
			return false;
		for (MenuInfo permission : permissionInfos) {
			
			if (StringUtil.isEmpty(permission.getUrl()))
				continue;
			Pattern p = Pattern.compile(permission.getUrl().replace("*", ".*").replace("?", "\\?"));
			Matcher matcher = p.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	public static String filterUrl(String url) {
		if (StringUtil.isEmpty(url))
			return "";
		if (url.startsWith("http://") || url.startsWith("https://")) {
			url = url.substring(url.indexOf("://") + 3);
			url = url.substring(url.indexOf("/"));
		}
		return url;
	}
}
