package com.born.insurance.ws.service.user;


import com.born.insurance.ws.order.UpdatePasswordOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

public interface PasswordManagerService {
	
	InsuranceBaseResult updateUserPassword(UpdatePasswordOrder passwordOrder);
	
}
