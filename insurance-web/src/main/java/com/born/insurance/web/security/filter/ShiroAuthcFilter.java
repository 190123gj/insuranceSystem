package com.born.insurance.web.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.info.common.SysWebAccessTokenInfo;
import com.born.insurance.ws.service.common.SysWebAccessTokenService;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.born.insurance.web.util.SpringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 自定义权限检查
 *
 */
public class ShiroAuthcFilter extends FormAuthenticationFilter {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroAuthcFilter.class);
	
	private static SysWebAccessTokenService sysWebAccessTokenService;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
										Object mappedValue) {
		
		String accessToken = request.getParameter("accessToken");
		if (StringUtil.isNotBlank(accessToken)) {
			if (sysWebAccessTokenService == null)
				sysWebAccessTokenService = SpringUtil
					.getBean("sysWebAccessTokenService");
			SysWebAccessTokenInfo token = sysWebAccessTokenService.query(accessToken);
			if (token != null)
				logger.info("accessToken : {}", token);
			request.setAttribute("accessToken", token);
			return token != null;
		}
		
		return super.isAccessAllowed(request, response, mappedValue);
	}
}
