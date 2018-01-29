package com.born.insurance.biz.service.bpm.impl;

import java.net.SocketTimeoutException;
import java.util.Map;

import com.born.insurance.integration.bpm.user.UserDetailsServiceProxy;
import com.born.insurance.integration.common.CallExternalInterface;
import com.born.insurance.integration.common.impl.ClientBaseSevice;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.order.UpdatePasswordOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.user.PasswordManagerService;
import org.springframework.stereotype.Service;

@Service("passwordManagerService")
public class PasswordManagerServiceImpl extends ClientBaseSevice implements PasswordManagerService {
	
	@Override
	public InsuranceBaseResult updateUserPassword(final UpdatePasswordOrder passwordOrder) {
		InsuranceBaseResult result = callInterface(new CallExternalInterface<InsuranceBaseResult>() {
			@Override
			public InsuranceBaseResult call() throws SocketTimeoutException {
				InsuranceBaseResult result = new InsuranceBaseResult();
				UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
				try {
					passwordOrder.check();
					String resultJson = serviceProxy.updatePwd(passwordOrder.getUserName(),
						passwordOrder.getNewPassword());
					Map<String, Object> resultMap = MiscUtil.parseJSON(resultJson);
					if ("1".equals(String.valueOf(resultMap.get("result")))) {
						result.setSuccess(true);
					} else {
						result.setSuccess(false);
						result.setMessage(String.valueOf(resultMap.get("message")));
					}
				} catch (java.rmi.RemoteException e) {
					logger.error(e.getMessage(), e);
					result.setSuccess(false);
					result.setInsuranceResultEnum(InsuranceResultEnum.CALL_REMOTE_SERVICE_ERROR);
					result.setMessage(e.getMessage());
				} catch (IllegalArgumentException e) {
					result.setSuccess(false);
					result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
					result.setMessage(e.getMessage());
				}
				
				return result;
			}
			
		});
		return result;
	}
}
