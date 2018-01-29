package com.born.insurance.integration.bpm.user;


import com.born.insurance.integration.result.LoginResult;
import com.born.insurance.ws.order.LoginOrder;

public interface LoginService {
	
	LoginResult login(LoginOrder loginData);
	
	LoginResult validateLoginInfo(LoginOrder userLoginOrder);
	
}
